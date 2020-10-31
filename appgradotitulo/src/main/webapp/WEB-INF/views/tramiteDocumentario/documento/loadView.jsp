<script type="text/javascript">
	Ext.ns('APP.Portal.TramiteDocumentario.Documento.Load')
	APP.Portal.TramiteDocumentario.Documento.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.TramiteDocumentario.Documento.Load.DocumentoWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		
		var agregarPanel,
			saveButton, 
			cancelarButton,
			estado,
			itemsForPage = 25;
		
		cancelarButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-delete-icon',
			text : 'Cancelar',
			handler :function() {
				me.close();
				
			}
		});
		
		saveButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-register-icon',
			text : 'Grabar',
			name: 'grabar',
	        handler: function() {
	            var save = agregarPanel.getForm();
	            
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'tramiteDocumentario/documento/save',
	                    success: function(form, action) {
	                    	var globalPanel = APP.Portal.TramiteDocumentario.Documento.Load.GlobalPanel;
	                    	globalPanel.getTramiteDocumentoPanel().getListPanel().loadList();
							var jsonResponse = action.result;
	                    	var title='Mensaje';
							if (jsonResponse.success == true) {
								Ext.MessageBox.show({
									 title: title, 
									 msg: jsonResponse.message, 
									 width: 300, 
									 buttons: Ext.Msg.OK
								})
							}
							console.log('success');
							me.close();
	                    
	                    },
	                    failure: function(form, action) {
	                    	var jsonResponse = action.result;
	                    	var title='Alerta';
							if (jsonResponse.success == false) {
								Ext.MessageBox.show({
									 title: title, 
									 msg: jsonResponse.message, 
									 width: 300, 
									 buttons: Ext.Msg.OK
								})
							}
	                    }
	                });
	            }
	        }
		});
		
		
		
		var addButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-register-icon',
			text : 'Add',
	        handler: function() {
	        	var testField = new Ext.form.field.Text({
	    	        fieldLabel: 'test',
	    	        name: 'test'
	    		});
	        	agregarPanel.add(testField);
	        }
		});
		
		
		var archivoField = new Ext.form.field.File({
	        fieldLabel: 'Documento',
	        name:'archivo',
	        labelWidth: 80,
	        anchor: '100%',
	    });
		
		var URLBaseFieldLabel = new Ext.form.field.Text({
	        name: 'textoRuta'
        });
		
		var adjuntoCodigo = new Ext.form.field.Text({
			name: 'codigoEstudianteRegistroAdjuntoEscaneado',
        });
		
		var btnView = new Ext.button.Button({
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){
				var URLBase = URLBaseFieldLabel.getValue();
				console.log("URLBase "+URLBase)
		    	if(URLBase != null && URLBase != ''){
// 		    		window.open('http://192.168.1.4/'+URLBase)
		    		window.open('http://localhost/'+URLBase)
		    	}
			}
		})
		var panelFile=new Ext.form.Panel({
			fileUpload: true,
			width: 400,
			items:[archivoField]
		})
		
		var panelView=new Ext.form.Panel({
			padding : '0 0 0 10',
			items:[btnView]
		})
		
		
		var panelFoto = new Ext.panel.Panel({
			fileUpload: true,
// 			width: 450,
			layout:{
				type:'hbox',
				align:'stretch'
			},
			items:[
				{
			     	flex: 92,
			     	items:[panelFile]
			    },
			    {
			     	flex: 8,
			     	items:[panelView]
			    },
			]
		})
		
		
		var asuntoField = new Ext.form.field.TextArea({
	        fieldLabel: 'Asunto',
	        name: 'textoAsunto',
	        allowBlank: false,
	        height :50,
	        width: 400
        });
		
		var detalleField = new Ext.form.field.TextArea({
	        fieldLabel: 'Detalle',
	        name: 'textoDetalle',
	        allowBlank: false,
	        height :50,
	        width: 400
        });
		
		var nombreArchivoField = new Ext.form.field.Text({
	        fieldLabel: 'Nombre',
	        name: 'textoNombreRegistro',
	        width: 400
		});
		
		
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
			fieldDefaults: {
		        labelWidth : 80
		    },			
			items:[
				{	
					name: 'codigo',
					xtype:'hidden'
				},
				nombreArchivoField,
				asuntoField,
				detalleField,
				panelFoto,
				URLBaseFieldLabel.hide()
			]
			
		});	
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[agregarPanel],
			buttons:[cancelarButton,saveButton]
			,listeners :{
				afterrender:function(){
					if(uxData&&uxData.action=='edit'){
						agregarPanel.load({
							url:'tramiteDocumentario/documento/getArchivoTramiteDocumento',
							params:{
								codigo:uxData.codigo
							}
						})
					}else if(uxData&&uxData.action=='view'){
						console.log('uxData.tipo'+uxData.tipo);
						agregarPanel.load({
							url:'tramiteDocumentario/documento/getArchivoTramiteDocumento',
							params:{
								codigo:uxData.codigo,
							}
						})
						saveButton.disable()
					}
				
				}
			}
		});
		
		APP.Portal.TramiteDocumentario.Documento.Load.DocumentoWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Documento.Load.DocumentoWindowsMantenimiento,Ext.window.Window,{});

	APP.Portal.TramiteDocumentario.Documento.Load.DocumentoToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.TramiteDocumentario.Documento.Load.DocumentoFiltro({
			
		});
		
		me.getFiltroField=function(form){
			return filtroField;
		}
		Ext.apply(config,{
			items : [
				filtroField,
				'->',
				{
					text : 'Agregar Documento',
					height : 30,
					iconCls : 'btn-add-icon',
					cls : 'btn',
					handler:function(){
						
						var win = new APP.Portal.TramiteDocumentario.Documento.Load.DocumentoWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
				
			]
		});
		APP.Portal.TramiteDocumentario.Documento.Load.DocumentoToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Documento.Load.DocumentoToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.TramiteDocumentario.Documento.Load.DocumentoList=function(config){
		var me=this;
		var toolbar,
			store,
			grid,
			itemsForPage = 25;
			
		toolbar=new APP.Portal.TramiteDocumentario.Documento.Load.DocumentoToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
				
		
		var formNombreArchivo,
			formFechaAgregarInicio,
			formFechaAgregarFinal,
			formFlagCandado;
		
		me.loadListParam=function(form){
			var candado = "2";
			formFechaAgregarInicio = form.fechaAgregarInicio;
			formFechaAgregarFinal = form.fechaAgregarFinal;
			formNombreArchivo = form.nombreArchivo,
			formFlagCandado = candado;
			
 			console.log("RestarGrid");
		 	store.currentPage = 1;
		 	store.load({
				start: 0, 
				limit: itemsForPage
// 				,params: {
// 					"fechaAgregarInicio": form.fechaAgregarInicio,
// 					"fechaAgregarFinal": form.fechaAgregarFinal,
// 					"textoNombreRegistro": form.nombreArchivo,
// 					"flagCandado": candado

// 				}
			});
		}
		
		me.loadList=function(){
			store.currentPage = 1;
			store.load({
				start: 0, 
				limit: 25
			});
		}
		function eliminar(record){
			var codigo=record.get('codigo');
			console.info({action:'delete',codigo:codigo})
			Ext.Ajax.request({
				url : 'tramiteDocumentario/documento/delete',
				method: 'POST',
				params: {
					codigo:codigo
				},
				success: function (response){
					var respObj = Ext.JSON.decode(response.responseText);					
					
					if( respObj.success == false){
 	 					Ext.Msg.alert("Estado", respObj.message);
 					}else{
 						store.load();
 					}
				},
				failure: function (response){
					var respObj = Ext.JSON.decode(response.responseText);
                    Ext.Msg.alert("Estado", respObj.status.statusMessage);
				}
			});
			
			
		}
				
		function editar(record){
			var codigo = record.get('codigo');
			var win = new APP.Portal.TramiteDocumentario.Documento.Load.DocumentoWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'edit'
				}
			});
			win.show();
			
		}
		function visualizar(record){
			var codigo = record.get('codigo');
			var win = new APP.Portal.TramiteDocumentario.Documento.Load.DocumentoWindowsMantenimiento({
				title: 'Visualizar',
				uxData:{
					codigo:codigo,
					action:'view'
				}
			});
			win.show();
			
		}
		
		var fechaFormat='1000/01/01';
		
		if(isEmpty(formFechaAgregarInicio)){
			formFechaAgregarInicio = fechaFormat;
		}
		
		if(isEmpty(formFechaAgregarFinal)){
			formFechaAgregarFinal = fechaFormat;
		}
		
		store=new Ext.data.Store({
		    fields:[
				'codigo',
				'textoAsunto',
				'textoDetalle',
				'textoNombreRegistro',
				'textoRuta',
				'fechaAgregar'
				
			],
			autoLoad:true,
			pageSize: itemsForPage,
		    proxy: {
		        type: 'ajax',
				url: 'tramiteDocumentario/documento/listArchivoTramiteDocumento',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
		    ,listeners: {
	            beforeload: function(store, operation, options){
	            	operation.params = Ext.apply(operation.params || {}, {
	            		"textoNombreRegistro": formNombreArchivo,
						"flagCandado": formFlagCandado,
						"fechaAgregarInicio": formFechaAgregarInicio,
						"fechaAgregarFinal": formFechaAgregarFinal

	                });
	            }
	        }
		});
		
		
		
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [
				{ text: 'Nombre',  dataIndex: 'textoNombreRegistro',flex:1 },
				{ text: 'Asunto',  dataIndex: 'textoAsunto',flex:1 },
				{ text: 'Detalle',  dataIndex: 'textoDetalle',flex:1 },
				{ text: 'Ruta',  dataIndex: 'textoRuta',flex:2 },
				{ text: 'F/D creacion',  dataIndex: 'fechaAgregar',flex:1 }
	

				],
			dockedItems: [{
		        xtype: 'pagingtoolbar',
		        store: store,  
		        dock: 'bottom',
		        displayInfo: true
		    }]
// 			,viewConfig: {
// 		         getRowClass: function(record, rowIndex, rowParams, store) {
// 					console.log('fechaInicio '+record.get('fechaInicio'));
// // 		            CONDICION DE PRUEBA
// 					return (record.get('fechaInicio') && record.get('flagCandado') == "0") 
// 		                   ? 'y-grid3-different-row' 
// 		                   : 'y-grid3-not-so-different-row';
// 		         }
// 			}
		    ,listeners:{
		    	itemcontextmenu: function( gridObj, record, item, index, e, eOpts ){
		    		var menu=new Ext.menu.Menu({
		    			items: [
		    				{
		    					text:'Editar',
		    					handler: function(){
		    						editar(record);
	    						}
		    				},
		    				{
		    					text:'Visualizar',
		    					handler: function(){
		    						visualizar(record);
	    						}
		    				},
		    				{
		    					text:'Eliminar',
		    					handler: function(){
    		                        Ext.Msg.confirm("Confimacion", 
    		                        	"¿Desea Eliminar la solicitud?", 
    		                        		function(btnText){
    		                            if(btnText === "yes"){
    		                                eliminar(record);
    		                            }
    		                        }, this);
	    						}
		    				}
						]
		    		});
		    		var position = e.getXY();
                    e.stopEvent();
                    menu.showAt(position);
		    		 
		    	}
		    }
		});
		
		Ext.apply(config,{
			dockedItems : [toolbar],
			items : [grid],
			layout : 'fit'
		});
		APP.Portal.TramiteDocumentario.Documento.Load.DocumentoList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Documento.Load.DocumentoList,Ext.panel.Panel,{});

	APP.Portal.TramiteDocumentario.Documento.Load.DocumentoFiltro=function(config){
		var me=this;
		
		
	    	    
		Ext.apply(config,{
			width : 450,
			createPicker:function(){
				var me = this,
					storeautoridad,	
					estado,
					cancelarButton,
					findButton;
					
				cancelarButton = new Ext.Button({
					width : 80,
					iconCls : 'btn-delete-icon',
					text : 'Cancelar',
					handler :function() {
						pickerPanel.getForm().reset();
						me.collapse();
						
					}
				});
				
				findButton = new Ext.Button({
					width : 80,
					iconCls : 'btn-search-icon',
					text : 'Buscar',
			        handler: function() {
			        	
			        	if(isEmpty(fechaAgregarInicioField.getSubmitValue())){
			        		fechaAgregarInicioField.setValue(new Date('1000/01/01'))
						}
			        	
			        	if(isEmpty(fechaAgregarFinalField.getSubmitValue())){
			        		fechaAgregarFinalField.setValue(new Date('1000/01/01'))
						}
			        	
			        	console.log("fechaAgregarInicioField.getSubmitValue() "+fechaAgregarInicioField.getSubmitValue())
			        	console.log("fechaAgregarFinalField.getSubmitValue() "+fechaAgregarFinalField.getSubmitValue())
			        	
			        	
			        	var form = {
		        			fechaAgregarInicio: fechaAgregarInicioField.getSubmitValue(),
		        			fechaAgregarFinal: fechaAgregarFinalField.getSubmitValue(),
		        			nombreArchivo: nombreArchivoField.getValue()
						},
						findValue=[];
			        	
						var globalPanel = APP.Portal.TramiteDocumentario.Documento.Load.GlobalPanel;
							globalPanel.getTramiteDocumentoPanel().getListPanel().loadListParam(form);
						
						if(!isEmpty( form.numeroDocumento )){
							findValue.push('Nombre:'+posicionValue(form.numeroDocumento));
						}
						
						me.setRawValue(findValue);
						pickerPanel.getForm().reset();
						me.collapse();
			        }
				});
				
				var fechaAgregarInicioField = new Ext.form.field.Date({
			        fieldLabel: 'Fecha Inicio',
			        name: 'fechaAgregarInicio',
			        format : "d/m/Y",
			        submitFormat :"Y/m/d",
			        allowBlank: true
				})
				var fechaAgregarFinalField = new Ext.form.field.Date({
			        fieldLabel: 'Fecha Final',
			        name: 'fechaAgregarFinal',
			        format : "d/m/Y",
			        submitFormat :"Y/m/d",
			        allowBlank: true
				})
				
				var nombreArchivoField = new Ext.form.field.Text({
			        fieldLabel: 'Nombre',
			        name: 'textoNombreRegistro',
			        width: 400
				});
				
				me.valueNumeroDocumentoField = function(value){
					numeroDocumentoField.setValue(value);
				}
				
				me.valueProcedenciaField = function(value){
					procedenciaField.setValue(value);
				}

			    pickerPanel = new Ext.form.Panel({
			    	bodyPadding: 5,
					fieldDefaults: {
						width : 400,
						labelWidth : 200
					},
					border : true,
					defaultType: 'textfield',
					pickerField: me,
					floating: true,
					selectOnFocus:true,
					title: 'Filtro de Busqueda',
					items:[
						nombreArchivoField,
						fechaAgregarInicioField,
						fechaAgregarFinalField,
						
					],
					buttons:[cancelarButton,findButton]
				});

		        return pickerPanel;
			},
			enableKeyEvents:true,
			onKeyUp: function(e, t) {
				var key = e.getKey();
		        if (!me.readOnly && !me.disabled && me.editable) {
     	//if (key == e.ENTER) {
					var regularExpresion= /,(?=[^\)]*[\(]|[^\)]*$)/;
					var splitRawField =[];					
					var nameField= [
						'Nombre'
					],
					form={
						numeroDocumento:'Nombre de Autoridad',
						procedencia:'Procedencia'
					},
         			rawField=me.getRawValue().split(regularExpresion);
					
					for(i=0;i<rawField.length;i++){
						var convert =rawField[i].split(":");
						splitRawField.push(convert);
					}
				
					Object.keys(form).forEach(function(key) {
						for( x=0; x<splitRawField.length; x++){
							
							if(((splitRawField[x][0].trim()).toUpperCase()).indexOf(((form[key].trim()).toUpperCase())) != -1){
								
								if(splitRawField[x][1] !=null){
									
									if((splitRawField[x][1].trim()).indexOf("(")==0){
										var sinParentesis =(splitRawField[x][1].trim()).substring(1,(splitRawField[x][1].trim()).length-1);
					            		form[key]=sinParentesis.trim();
									
									}else if(((splitRawField[x][1]).trim()).length !=0){
										form[key]=splitRawField[x][1].trim();
									}
									
								}
								
							}
							
						}			
		   			});
				
					Object.keys(form).forEach(function(key) {
						
						for(i=0;i<nameField.length;i++){
							
							if((nameField[i].trim())== form[key]){
								form[key]=null;
							}
						}	
					});
	

					function getCatchValue(store,textoFind,column){
						var lengthStore=0;
						var result=null;
						var catchValue=[];
						console.log('textoFind '+textoFind);
						
						Ext.each(store.getRange(), function(item, idx) {
							var valueField=((item.get(column)).toUpperCase()).trim();
							lengthStore=lengthStore+1;
							console.log('lengthStore '+lengthStore);
							
							if((valueField).startsWith(textoFind)==true){
								catchValue.push(item.get(column));
								console.log('catchValue.length '+catchValue.length);
							}
							
							if(lengthStore==store.getCount()&&catchValue.length==1){
								console.log('fin de ciclo valor encontrado '+catchValue[0]);
								result =catchValue[0];		
							}
						});
						return result;
					}
	
					var globalPanel=APP.Portal.Autoridad.Load.GlobalPanel,
						textoFind,
						value,
						getIndice = globalPanel.getAutoridadPanel().getListPanel().getToolbar().getFiltroField();
					
					getIndice.valueNombreAutoridadFieldLabel(form.textoNombreAutoridad);
		       	}
			}
		});
		APP.Portal.TramiteDocumentario.Documento.Load.DocumentoFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Documento.Load.DocumentoFiltro,APP.form.field.Picker,{});
	
	APP.Portal.TramiteDocumentario.Documento.Load.DocumentoPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.TramiteDocumentario.Documento.Load.DocumentoList({})
		
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Tramite Documento',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.TramiteDocumentario.Documento.Load.DocumentoPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Documento.Load.DocumentoPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.TramiteDocumentario.Documento.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.TramiteDocumentario.Documento.Load.DocumentoPanel({});
		
		me.getTramiteDocumentoPanel=function(){
			return datoGeneralPanel;
		}
				
		Ext.apply(config,{
			layout:'fit',
			items: [ datoGeneralPanel ]
		});
		
		APP.Portal.TramiteDocumentario.Documento.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Documento.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.TramiteDocumentario.Documento.Load.Container({});
		APP.Portal.TramiteDocumentario.Documento.Load.GlobalPanel = globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
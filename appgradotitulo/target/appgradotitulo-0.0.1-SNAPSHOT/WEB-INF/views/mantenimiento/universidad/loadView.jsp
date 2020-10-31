<script type="text/javascript">
	Ext.ns('APP.Portal.Universidad.Load')
	APP.Portal.Universidad.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.Universidad.Load.UniversidadWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		
		var agregarPanel,
			saveButton, 
			cancelarButton,
			estado;
		
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
	        handler: function() {
	            var save = agregarPanel.getForm();
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'mantenimiento/universidad/saveUniversidad',
	                    success: function(form, action) {
	                    	var globalPanel = APP.Portal.Universidad.Load.GlobalPanel;
	                    	globalPanel.getUniversidadPanel().getListPanel().loadList();
							Ext.Msg.alert('Success', 'Se ha grabado la autiridad correctamente.');
							me.close();
	                    
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', 'error');
	                    }
	                });
	            }
	        }
		});
		var storePais= new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'gradoTitulo/getPaisList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		var storeTipoUniversidad= new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'mantenimiento/universidad/getTipoUniversidadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
	    var nombreUniversidadField=new Ext.form.field.Text({
	        fieldLabel: 'Nombre de la Univ.',
	        name: 'textoNombre',
	        allowBlank: false
	    });
		var paisField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Pais del BACH',
	        name: "pais.codigo",
			store: storePais,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
			allowBlank: false,
			editable:false
		});
		 var tipoUniversidadField=new Ext.form.field.ComboBox({
		    	fieldLabel: 'Tipo',
		        name: "tipoUniversidad.codigo",
				store: storeTipoUniversidad,
		        queryMode: 'local',
		        displayField: 'textoNombre',
		        valueField: 'codigo',
				allowBlank: false,
				forceSelection: true
				//editable:false

		});
		var siglasField=new Ext.form.field.Text({
		        fieldLabel: 'Siglas',
		        name: 'textoNombreAbreviado'
//	 	        ,allowBlank: false
		});
		var abreviaturaField=new Ext.form.field.Text({
	        fieldLabel: 'Abreviatura',
	        name: 'textoSiglas'
	// 	        ,allowBlank: false
		});
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
			fieldDefaults: {
		        width : 500,
		        labelWidth : 200
		    },
			defaultType: 'textfield',
			
			items:[
				{	
					name: 'codigo',
					xtype:'hidden'
				},
				nombreUniversidadField,
				paisField,
				tipoUniversidadField,
				siglasField,
				abreviaturaField
			]
			
		});	
		
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[agregarPanel],
			buttons:[cancelarButton,saveButton],
			listeners :{
				afterrender:function(){
	
					if(uxData&&uxData.action=='edit'){
						agregarPanel.load({
							url:'mantenimiento/universidad/getUniversidad',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.Universidad.Load.UniversidadWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Universidad.Load.UniversidadWindowsMantenimiento,Ext.window.Window,{});

	APP.Portal.Universidad.Load.UniversidadToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.Universidad.Load.UniversidadFiltro({
			
		});
		
		me.getFiltroField=function(form){
			return filtroField;
		}
		Ext.apply(config,{
			items : [
				filtroField,
				'->',
				{
					text : 'Agregar Agenda',
					height : 30,
					iconCls : 'btn-add-icon',
					cls : 'btn',
					handler:function(){
						
						var win = new APP.Portal.Universidad.Load.UniversidadWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.Universidad.Load.UniversidadToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Universidad.Load.UniversidadToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.Universidad.Load.UniversidadList=function(config){
		var me=this;
		var toolbar,
			store,
			grid,
			itemsForPage = 25;
			
		toolbar=new APP.Portal.Universidad.Load.UniversidadToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
				
		me.loadList=function(){
// 				store.load();
			store.currentPage = 1;
			store.load({
				start: 0, 
				limit: 25
			});
		}
		var formNombreUniversidad,
		formPais,
		formTipo,
		formSiglas,
		formAbreviatura;
		me.loadListParam=function(form){
			formNombreUniversidad = form.nombreUniversidad;
			formPais = form.pais;
			formTipo = form.tipo;
			formSiglas = form.siglas;
			formAbreviatura = form.abreviatura;
		 	store.currentPage = 1;
			store.load({
				start: 0, 
				limit: itemsForPage,
				params: {
					"textoNombre":form.nombreUniversidad,
					"pais.codigo":form.pais,
					"tipoUniversidad.codigo":form.tipo,
					"textoNombreSiglas":form.siglas,
					"textoNombreAbreviado":form.abreviatura
				}
			});
		}
		function eliminar(record){
			var codigo=record.get('codigo');
			console.info({action:'delete',codigo:codigo})
			Ext.Ajax.request({
				url : 'Universidad/delete',
				method: 'POST',
				params: {
					codigo:codigo
				},
				success: function (result, request){
					store.load();
				},
				failure: function (result, request){
					alert('Error in server' + result.responseText);
				}
			});
			
			
		}
		
		function editar(record){
			var codigo=record.get('codigo');
			var win = new APP.Portal.Universidad.Load.UniversidadWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'edit'
				}
			});
			win.show();
			
		}
		
		store=new Ext.data.Store({
		    fields:[
	            'codigo',
	            'textoNombre',
	            'textoNombreAbreviado',
	            'textoNombreSiglas',
	            'pais',
	            'tipoUniversidad'
			],
			autoLoad:true,
			pageSize: itemsForPage,
		    proxy: {
		        type: 'ajax',
		        url: 'mantenimiento/universidad/getUniversidadList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    },
		    listeners: {
	            beforeload: function(store, operation, options){
	            	operation.params = Ext.apply(operation.params || {}, {
	            		"textoNombre":formNombreUniversidad,
						"pais.codigo":formPais,
						"tipoUniversidad.codigo":formTipo,
						"textoNombreSiglas":formSiglas,
						"textoNombreAbreviado":formAbreviatura

	                });
	            }
	        }
		});
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [		       
				{ text: 'Nombre',  dataIndex: 'textoNombre',flex:3 },
				{ text: 'Abreviatura',  dataIndex: 'textoNombreAbreviado',flex:1 },
				{ text: 'Siglas',  dataIndex: 'textoNombreSiglas',flex:1 },
				{ text: 'Pais',  dataIndex: 'pais',flex:1 },
				{ text: 'Tipo Univ.',  dataIndex: 'tipoUniversidad',flex:1 }
		    ],
		    style: {borderColor: '#157fcc'},
		    dockedItems: [{
		        xtype: 'pagingtoolbar',
		        store: store,   // same store GridPanel is using
		        dock: 'bottom',
		        displayInfo: true
		    }],
		    listeners:{
		    	itemcontextmenu: function( gridObj, record, item, index, e, eOpts ){
		    		var menu=new Ext.menu.Menu({
		    			items: [
		    				{
		    					text:'Editar',
		    					handler: function(){
		    						editar(record);
	    						}
		    				}
// 		    				,{
// 		    					text:'Eliminar',
// 		    					handler: function(){
// 		    						//confirm messagebox
//     		                        Ext.Msg.confirm("Confimacion", "¿Desea eliminar el registro?", function(btnText){
//     		                            if(btnText === "yes"){
//     		                                eliminar(record);
//     		                            }
//     		                            else if(btnText === "no"){
    		                                
//     		                            }
//     		                        }, this);
// 	    						}
// 	    					}
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
		APP.Portal.Universidad.Load.UniversidadList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Universidad.Load.UniversidadList,Ext.panel.Panel,{});

	APP.Portal.Universidad.Load.UniversidadFiltro=function(config){
		var me=this;
		
		
	    	    
		Ext.apply(config,{
			width : 450,
			createPicker:function(){
				var me = this,
				storeautoridad,	estado,cancelarButton,findButton;
				
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
						var form={
							nombreUniversidad: nombreUniversidadField.getValue(),
		        			pais: paisField.getValue(),
		        			tipo: tipoUniversidadField.getValue(),
		        			siglas: siglasField.getValue(),
		        			abreviatura: abreviaturaField.getValue()
		        			
						},
						findValue=[];
// 						regularExpresion=/,|( )/;
						var globalPanel=APP.Portal.Universidad.Load.GlobalPanel;
						globalPanel.getUniversidadPanel().getListPanel().loadListParam(form);
						
						if(!isEmpty( form.nombreUniversidad )){
							findValue.push('Universidad:'+posicionValue(form.nombreUniversidad));
						}
// 						if(!isEmpty( form.pais )){
// 							findValue.push('Pais:'+posicionValue(form.pais));
// 						}
// 						if(!isEmpty( form.tipo )){
// 							findValue.push('Tipo:'+posicionValue(form.tipo));
// 						}
						if(!isEmpty( form.estado )){
							findValue.push('Estado:'+posicionValue(form.estado));
						}
						if(!isEmpty( form.abreviatura )){
							findValue.push('Abreviatura:'+posicionValue(form.abreviatura));
						}
						
// 						function posicionValue(rawValue){
// 							var result = null;
// 							if(regularExpresion.test(rawValue) == true) {
// 								result =' ('+rawValue+')';
// 							}else{
// 								result = rawValue;
// 							}
// 							return result;
// 						}
						
// 						if(form.textoNombreAutoridad !=null &&form.textoNombreAutoridad !=""){
// 							findValue.push('Nombre de Autoridad:'+posicionValue(form.textoNombreAutoridad));
// 						}
// 						if(form.gradoAcademico !=null && form.gradoAcademico  != ""){
// 							findValue.push('Grado Academico: '+posicionValue(form.gradoAcademico));
// 						}
// 						if(form.cargo !=null && form.cargo!=""){
// 							findValue.push('Cargo: '+posicionValue(form.cargo));
// 						}
						
// 						if(form.estado !=null && form.estado !=""){
// 							findValue.push('Estado: '+form.estado);
// 						}
						
						me.setRawValue(findValue);
						pickerPanel.getForm().reset();
						me.collapse();
			        }
				});
								  
				var storePais = new Ext.data.Store({
					fields:[
						'codigo',
						'textoNombre'
					],
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: 'gradoTitulo/getPaisList',
						reader: {
							type: 'json',
							root: 'data',
							idProperty : 'codigo',
							totalProperty :'totalCount'
						}
					}
				});
				var storeTipoUniversidad = new Ext.data.Store({
					fields:[
						'codigo',
						'textoNombre'
					],
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: 'mantenimiento/universidad/getTipoUniversidadList',
						reader: {
							type: 'json',
							root: 'data',
							idProperty : 'codigo',
							totalProperty :'totalCount'
						}
					}
				});
			    var nombreUniversidadField = new Ext.form.field.Text({
			        fieldLabel: 'Nombre de la Univ.',
			        name: 'textoNombre'
			    });
				var paisField=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Pais del BACH',
			        name: "pais.codigo",
					store: storePais,
			        queryMode: 'local',
			        displayField: 'textoNombre',
			        valueField: 'codigo',
			        allowBlank: false,
					forceSelection: true
// 					editable:false
				});
				var tipoUniversidadField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Tipo',
				        name: "tipoUniversidad.codigo",
						store: storeTipoUniversidad,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
						allowBlank: false,
						forceSelection: true

				});
				var siglasField=new Ext.form.field.Text({
				        fieldLabel: 'Siglas',
				        name: 'textoNombreAbreviado'
				});
				var abreviaturaField=new Ext.form.field.Text({
			        fieldLabel: 'Abreviatura',
			        name: 'textoSiglas'
				});
				me.valueNombreAutoridadFieldLabel=function(value){
					nombreAutoridadFieldLabel.setValue(value);
				}
				
				me.valueGradoAcademicoFieldLabel=function(value){
					gradoAcademicoFieldLabel.setValue(value);
				}
				
				me.valueCargoFieldLabel=function(value){
					cargoFieldLabel.setValue(value);
				}
				me.valueEstadoFieldLabel=function(value){
					estadoFieldLabel.setValue(value);
				}

				me.getStoreEstado=function(){
					return estado
				}
				
				me.getStoreGradoAcademico=function(){
					return storeGradoAcademico
				}
				me.getStoreCargo=function(){
					return storeCargo
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
		                //height: 300,
					items:[
					       nombreUniversidadField,
					       paisField,
					       tipoUniversidadField,
					       siglasField,
					       abreviaturaField
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
			        	var nameField= [
			        	                'Nombre de Autoridad',
			        	                'Grado Academico',
			        	                'Cargo',
			        	                'Estado'
			        		],
				        	form={
				        			textoNombreAutoridad:'Nombre de Autoridad',
				        			gradoAcademico:'Grado Academico',
				        			cargo:'Cargo',
				        			estado:'Estado'
				        	},
			            	rawField=me.getRawValue().split(regularExpresion);
						var splitRawField =[];					
						for(i=0;i<rawField.length;i++){
							var convert =rawField[i].split(":");
							splitRawField.push(convert);
						}
									
						Object.keys(form).forEach(function(key) {
							for(x=0;x<splitRawField.length;x++){ 
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
						getIndice =globalPanel.getAutoridadPanel().getListPanel().getToolbar().getFiltroField();
						
						
						getIndice.valueNombreAutoridadFieldLabel(form.textoNombreAutoridad);
						
// 						if(form.gradoAcademico != null){
// 							textoFind=((form.gradoAcademico).toUpperCase()).trim();
// 							value= getCatchValue(getIndice.getStoreGradoAcademico(),textoFind,'textoNombre');
// 							getIndice.valueGradoAcademicoFieldLabel(value);	
// 							form.gradoAcademico=value;
// 						}else{
// 							getIndice.valueGradoAcademicoFieldLabel(form.gradoAcademico)
// 						}
						
// 						if(form.cargo != null){
// 							textoFind=((form.cargo).toUpperCase()).trim();
// 							value= getCatchValue(getIndice.getStoreCargo(),textoFind,'textoNombre');
// 							getIndice.valueCargoFieldLabel(value);	
// 							form.cargo=value;
// 						}else{
// 							getIndice.valueCargoFieldLabel(form.cargo)
// 						}
// 						if(form.estado != null){
// 							textoFind=((form.estado).toUpperCase()).trim();
// 							value= getCatchValue(getIndice.getStoreEstado(),textoFind,'textoNombreEspanol');
// 							getIndice.valueEstadoFieldLabel(textoFind);	
// 							form.estado=value;
// 						}else{
// 							getIndice.valueEstadoFieldLabel(form.estado)
// 						}
// 						console.log('FORM FINAL '+form);	
// 						globalPanel.getAutoridadPanel().getListPanel().loadListParam(form);
// 						console.log('Presiono Enter');
			       
		       	}
			}

		});
		APP.Portal.Universidad.Load.UniversidadFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Universidad.Load.UniversidadFiltro,APP.form.field.Picker,{});
	
	APP.Portal.Universidad.Load.UniversidadPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Universidad.Load.UniversidadList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Universidad',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Universidad.Load.UniversidadPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Universidad.Load.UniversidadPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.Universidad.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.Universidad.Load.UniversidadPanel({});
		
		me.getUniversidadPanel=function(){
			return datoGeneralPanel;
		}
		
// 		var panelFullContainer = new Ext.panel.Panel({
// 			layout:{
// 				type:'hbox',
// 				align:'stretch'
// 			},
// 			items:[{
// 				 flex: 1, items:[datoGeneralPanel]
// 			}]
// 		})
		
		Ext.apply(config,{
			layout:'fit',
			items: [ datoGeneralPanel ]
// 			items: [panelFullContainer]
		});
		
		APP.Portal.Universidad.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Universidad.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.Universidad.Load.Container({});
		APP.Portal.Universidad.Load.GlobalPanel = globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
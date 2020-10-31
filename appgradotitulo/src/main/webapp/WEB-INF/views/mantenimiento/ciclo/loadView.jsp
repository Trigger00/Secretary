<script type="text/javascript">
	Ext.ns('APP.Portal.Ciclo.Load')
	APP.Portal.Ciclo.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.Ciclo.Load.CicloWindowsMantenimiento=function(config){
		
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
	            var data=[];
	            save.getFields().each(function(field) {
 	            	if( field.name == "fechaInicioCiclo" && field.value == null ){
	            		field.setDisabled(true)
	            	}else if(field.name == "fechaFinalCiclo" && field.value == null){
	            		field.setDisabled(true)
	            	}
 	            	data.push({field:field,validate:field.validate()});
 	            });
	            
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'mantenimiento/ciclo/saveCiclo',
	                    success: function(form, action) {
	                    	var globalPanel = APP.Portal.Ciclo.Load.GlobalPanel;
	                    	globalPanel.getCicloPanel().getListPanel().loadList();
							Ext.Msg.alert('Success', 'Se ha grabado el ciclo correctamente.');
							me.close();
	                    
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', 'error');
	                    }
	                });
	            }
	        }
		});

	    var cicloField = new Ext.form.field.Text({
	        fieldLabel: 'Nombre del Ciclo.',
	        name: 'textoCiclo',
	        allowBlank: false
	    });
	    var semestreField = new Ext.form.field.Text({
	        fieldLabel: 'Nombre del Semestre.',
	        name: 'textoSemestre',
	        allowBlank: false
	    });
	    var nombreEspanolField = new Ext.form.field.Text({
	        fieldLabel: 'Nombre en Español.',
	        name: 'textoNombreEspanol',
	        allowBlank: false
	    });
	    var fechaIncioCicloField = new Ext.form.field.Date({
	        fieldLabel: 'F/D Inicio',
	        name: 'fechaInicioCiclo',
	        format : "d/m/Y",
	        submitFormat :"Y/m/d"
	        ,allowBlank: true
		});
	    var fechaFinalCicloField = new Ext.form.field.Date({
	        fieldLabel: 'F/D Final',
	        name: 'fechaFinalCiclo',
	        format : "d/m/Y",
	        submitFormat :"Y/m/d"
	        ,allowBlank: true
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
				cicloField,
				semestreField,
				nombreEspanolField,
				fechaIncioCicloField,
				fechaFinalCicloField
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
							url:'mantenimiento/ciclo/getCiclo',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.Ciclo.Load.CicloWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Ciclo.Load.CicloWindowsMantenimiento,Ext.window.Window,{});

	APP.Portal.Ciclo.Load.CicloToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.Ciclo.Load.CicloFiltro({
			
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
						
						var win = new APP.Portal.Ciclo.Load.CicloWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.Ciclo.Load.CicloToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Ciclo.Load.CicloToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.Ciclo.Load.CicloList=function(config){
		var me = this;
		var toolbar,
			store,
			grid,
			itemsForPage = 25;
			
		toolbar=new APP.Portal.Ciclo.Load.CicloToolbar({
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
		var formCiclo,
		formNombreEspanol,
		formNombreSemestre,
		formFechaInicioCiclo,
		formFechaFinalCiclo;
		
		me.loadListParam = function(form){
			formCiclo = form.ciclo;
			formNombreEspanol = form.nombreEspanol;
			formNombreSemestre = form.nombreSemestre;
			formFechaInicioCiclo = form.fechaInicioCiclo;
			formFechaFinalCiclo = form.fechaFinalCiclo;

			
		 	store.currentPage = 1;
		 	
			store.load({
				start: 0, 
				limit: itemsForPage,
				params: {
					"textoCiclo":form.ciclo,
					"textoNombreEspanol":form.nombreEspanol,
					"textoSemestre":form.nombreSemestre,
// 					"fechaInicioCiclo":form.fechaInicioCiclo,
// 					"fechaFinalCiclo":form.finalCiclo,
				}
			});
		}
		function eliminar(record){
			var codigo=record.get('codigo');
			console.info({action:'delete',codigo:codigo})
			
			Ext.Ajax.request({
				url : 'mantenimiento/ciclo/delete',
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
			var win = new APP.Portal.Ciclo.Load.CicloWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'edit'
				}
			});
			win.show();
			
		}
		
		store = new Ext.data.Store({
		    fields:[
	            'codigo',
	            'textoCiclo',
	            'textoNombreEspanol',
	            'textoSemestre',
	            'fechaInicioCiclo',
	            'fechaFinalCiclo'
			],
			autoLoad:true,
			pageSize: itemsForPage,
		    proxy: {
		        type: 'ajax',
		        url: 'mantenimiento/ciclo/getCicloList',
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
	    	            "textoCiclo" : formCiclo,
	            		"textoNombreEspanol":formNombreEspanol,
						"textoSemestre":formNombreSemestre,
// 						"fechaInicioCiclo":formFechaInicioCiclo,
// 						"fechaFinalCiclo":formFechaFinalCiclo
	                });
	            }
	        }
		});
		grid = new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [	
				{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
				{ text: 'Ciclo',  dataIndex: 'textoCiclo',flex:3 },
				{ text: 'Nombre',  dataIndex: 'textoNombreEspanol',flex:3 },
				{ text: 'Semestre',  dataIndex: 'textoSemestre',flex:1 },
				{ text: 'F. Inicio',  dataIndex: 'fechaInicioCiclo',flex:1 },
				{ text: 'F. Fin',  dataIndex: 'fechaFinalCiclo',flex:1 },
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
		APP.Portal.Ciclo.Load.CicloList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Ciclo.Load.CicloList,Ext.panel.Panel,{});

	APP.Portal.Ciclo.Load.CicloFiltro=function(config){
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
						var findValue=[];
			        	var form={
							ciclo: cicloField.getValue()
						};					
						var globalPanel=APP.Portal.Ciclo.Load.GlobalPanel;
						globalPanel.getCicloPanel().getListPanel().loadListParam(form);
						
						if (!isEmpty(form.ciclo)) {
							findValue.push('Ciclo:'+posicionValue(form.ciclo));
						}
											
						me.setRawValue(findValue);
						pickerPanel.getForm().reset();
						me.collapse();
			        }
				});
								  
				
			    var cicloField = new Ext.form.field.Text({
			        fieldLabel: 'Nombre del ciclo.',
			        name: 'ciclo'
			    });
		
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
						cicloField
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
			        	                'Nombre del ciclo',
			        	               
			        		],
				        	form={
				        			ciclo:'Nombre del ciclo'
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
								       
		       	}
			}

		});
		APP.Portal.Ciclo.Load.CicloFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Ciclo.Load.CicloFiltro,APP.form.field.Picker,{});
	
	APP.Portal.Ciclo.Load.CicloPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Ciclo.Load.CicloList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Universidad',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Ciclo.Load.CicloPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Ciclo.Load.CicloPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.Ciclo.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.Ciclo.Load.CicloPanel({});
		
		me.getCicloPanel=function(){
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
		
		APP.Portal.Ciclo.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Ciclo.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.Ciclo.Load.Container({});
		APP.Portal.Ciclo.Load.GlobalPanel = globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
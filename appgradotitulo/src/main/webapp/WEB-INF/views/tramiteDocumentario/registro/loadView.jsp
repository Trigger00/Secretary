<script type="text/javascript">
	Ext.ns('APP.Portal.TramiteDocumentario.Registro.Load')
	APP.Portal.TramiteDocumentario.Registro.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.TramiteDocumentario.Registro.Load.RegistroWindowsMantenimiento=function(config){
		
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
	            save.getFields().each(function(field) {
	            	if( field.name == "fechaInicio" && field.value == null ){
	            		field.setDisabled(true)
	            	}else if(field.name == "fechaFinal" && field.value == null){
	            		field.setDisabled(true)
	            	}
	            });
	            
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'tramiteDocumentario/registro/saveSolicitudProceso',
	                    success: function(form, action) {
	                    	var globalPanel = APP.Portal.TramiteDocumentario.Registro.Load.GlobalPanel;
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
		var storeTipoTramiteDocumento = new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'tramiteDocumentario/registro/listTipoTramiteDocumento',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		var storeProceso = new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'tramiteDocumentario/registro/listProceso',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
// 		var storeRegistroProcedencia = new Ext.data.Store({
// 			pageSize: 5,
// 			fields:[
// 				'textoNombreProcedencia'
// 			],
// 			proxy: {
// 				type: 'ajax',
// 				url: 'tramiteDocumentario/registro/listSolicitudProceso',
// 				reader: {
// 					type: 'json',
// 					root: 'data',
// 					idProperty : 'codigo',
// 					totalProperty :'totalCount'
// 				}
// 			},
// 			start: 0, 
// 			limit: 25,
// 			flagCandado: '2'
// 		});
		
		Ext.define('TramiteDocumetario.distinctRegistro.Store',{
		   extend:'Ext.data.Store',
		   //all properties which you need
		   pageSize: 5,
			fields:[
				'codigo',
				'textoNumeroDocumento',
				'textoNombreProcedencia'
			],
			proxy: {
				type: 'ajax',
				url: 'tramiteDocumentario/registro/listSolicitudProceso',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				} 
			},
			start: 0, 
			limit: 25,
			test: 25,
		});
		var storeNumeroDocumento = Ext.create('TramiteDocumetario.distinctRegistro.Store');
// 		var storeProcedencia = Ext.create('TramiteDocumetario.distinctRegistro.Store');
		
		var storeProcedencia = new Ext.data.Store({
			pageSize: 5,
			fields:[
				'codigo',
				'textoNumeroDocumento',
				'textoNombreProcedencia'
			],
			proxy: {
				type: 'ajax',
				url: 'tramiteDocumentario/registro/listDistinctProcedencia',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				} 
			},
			start: 0, 
			limit: 25,
			test: 25,
		});		
// 		var storeTest = new Ext.data.Store({
// 			pageSize: 5,
// 			fields:[
// 				'codigo',
// 				'textoNumeroDocumento',
// 				'textoNombreProcedencia'
// 			],
// 			proxy: {
// 				type: 'ajax',
// 				url: 'tramiteDocumentario/registro/listSolicitudProceso',
// 				reader: {
// 					type: 'json',
// 					root: 'data',
// 					idProperty : 'codigo',
// 					totalProperty :'totalCount'
// 				} 
// 			},
// 			start: 0, 
// 			limit: 25,
// 			test: 25,
// 		});		
		
		var storeResponsabilidad = new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre',
				'responsableCodigo'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'tramiteDocumentario/registro/listResponsabilidad',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		var nombreRemitenteField = new Ext.form.field.Text({
	        fieldLabel: 'Nombre del Remitente',
	        name: 'textoNombreRemitente'
		});
		
		var codigoAsociacionField = new Ext.form.field.Text({
	        fieldLabel: 'Codigo Asociacion',
	        name: 'codigoAsociacion'
		});
		
// 		var procedenciaField = new Ext.form.field.Text({
// 	        fieldLabel: 'Procedencia',
// 	        name: 'textoNombreProcedencia'
// 		});

// 		var procedenciaStore =  Ext.create('storeTest');
		var procedenciaField = new Ext.form.field.ComboBox({
	        fieldLabel: 'Procedencia',
// 	        store:  storeTest,
	        store: storeProcedencia,
// 	        store: new procedenciaField.store.storeTest() ,
	        name: 'textoNombreProcedencia',
	        displayField: 'textoNombreProcedencia',
	        clearFilterOnBlur: false,
			typeAhead: false,
			hideTrigger:true,
			queryParam: 'textoNombreProcedencia',
			listConfig: {
		        loadingText: 'Buscando...',
		        emptyText: 'No hay registros que coincidan.',
		        getInnerTpl: function() {
		            return '<a >' +'{textoNombreProcedencia}'+'</a>';
		        }
		    },
		    pageSize: 5,
		    listeners: {
		        afterrender: function() {
// 		        	procedenciaField.getStore().clearFilter(true);
// 		        	procedenciaField.getStore().filter("textoNombreProcedencia");
		        	storeProcedencia.getProxy().extraParams = {
		        		distinct: 'textoNombreProcedencia'
		    		};
		        }
		    }
	        
		});
		
		var numeroDocumentoField = new Ext.form.field.ComboBox({
			fieldLabel: 'N° documento',
			store: storeNumeroDocumento,
	        name: "textoNumeroDocumento",
			displayField: 'textoNumeroDocumento',
			typeAhead: false,
			hideTrigger:true,
			typeAhead: false,
			listConfig: {
		        loadingText: 'Buscando...',
		        emptyText: 'No hay registros que coincidan.',
		        getInnerTpl: function() {
		            return '<a >' +'{textoNumeroDocumento}'+'</a>';
		        }
		    },
		    pageSize: 5,
		    listeners: {
		        afterrender: function() {
// 		        	procedenciaField.getStore().clearFilter(true);
// 		        	procedenciaField.getStore().filter("textoNumeroDocumento");
		        	storeNumeroDocumento.getProxy().extraParams = {
		        		distinct: 'textoNumeroDocumento'
		    		};
		        }
		    }
		});
		
		var tipoTramiteDocumentoCombo = new Ext.form.field.ComboBox({
	    	fieldLabel: 'Tipo de Documento',
	        name: "tipoTramiteDocumento.codigo",
			store: storeTipoTramiteDocumento,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
			allowBlank: false,
			forceSelection: true,
			listConfig : {
				dirtychange:function(){
					console.log('dirtychange ')
				},
				listeners : {
					itemclick : function(list,record) {
						nombreTipoDocumento =record.get('textoNombre');
						console.log('nombreTipoDocumento '+nombreTipoDocumento)
// 						if(nombreTipoDocumento=="CARTA"){								
// 							var testOneField= new Ext.form.field.Text({
// 						        fieldLabel: 'testone',
// 						        name: 'testone',
// 						        id : 'testone'
// 							});
// 							var testTwoField= new Ext.form.field.Text({
// 						        fieldLabel: 'testtwo',
// 						        name: 'testtwo',
// 						        id : 'testtwo'
// 							});
// 							var testTreeField= new Ext.form.field.Text({
// 						        fieldLabel: 'testtree',
// 						        name: 'testtree',
// 						        id : 'testtree'
// 							});
// 							var store = storeProceso;
// 							store.each(function(record,idx){
// 							 console.log(record.get('codigo'));
// 							 if(record.get('codigo')=='1'){
// 								 agregarPanel.add(testOneField);
// 							 }else if(record.get('codigo')=='2'){
// 								 agregarPanel.add(testTwoField);
// 							 }
// 							});
							
// 						}else{
// 							agregarPanel.remove('testone');
// 							agregarPanel.remove('testtwo');
// 							agregarPanel.doLayout();

// 						}
					},
			
				}
				
			}

		});
		var asuntoField = new Ext.form.field.TextArea({
	        fieldLabel: 'Asunto',
	        name: 'textoAsunto',
	        allowBlank: false,
	        height :50
        });
		var observacionField = new Ext.form.field.TextArea({
	        fieldLabel: 'Observacion',
	        name: 'textoObservacion',
	        allowBlank: false,
	        height :50
        });
		var fechaInicioField = new Ext.form.field.Date({
	        fieldLabel: 'Fecha Inicio',
	        name: 'fechaInicio',
// 	        format : "d/m/Y",
	        format : "d/m/Y g:i:s A",
// 	        submitFormat :"Y/m/d",
	        submitFormat :"Y/m/d g:i:s A",
	        allowBlank: true
		})
		var fechaFinalField = new Ext.form.field.Date({
	        fieldLabel: 'Fecha Final',
	        name: 'fechaFinal',
// 	        format : "d/m/Y",
	        format : "d/m/Y g:i:s A",
// 	        submitFormat :"Y/m/d",
	        submitFormat :"Y/m/d g:i:s A",
	        allowBlank: true
		})
		var procesoCombo = new Ext.form.field.ComboBox({
	    	fieldLabel: 'Enviar al Flujo',
	        name: "proceso.codigo",
			store: storeProceso,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
			allowBlank: false,
			forceSelection: true,
			listConfig : {
				listeners : {
					itemclick : function(list,record) {
						var codigoProceso = record.get('codigo');
						responsabilidadCombo.clearValue();
						storeResponsabilidad.load({
							params: {
								"responsable.definicionProceso.proceso.codigo":record.get('codigo'),
								"responsable.definicionProceso.flagInicio":"1"
							}
						});
						
					}
				}
								
			},
		});
		var responsabilidadCombo = new Ext.form.field.ComboBox({
	    	fieldLabel: 'Responsable',
	        name: 'seguridadUsuario.codigo',
 			store: storeResponsabilidad,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
			allowBlank: false,
			forceSelection: true,
			listConfig : {
				listeners : {
					itemclick : function(list,record) {
						responsableField.setValue(record.get('responsableCodigo'));
					}
				}
								
			}
			//editable:false
		});
		var responsableField = new Ext.form.field.Text({
	        name: "responsable.codigo"
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
				codigoAsociacionField,
				nombreRemitenteField,
				procedenciaField,
				numeroDocumentoField,
				tipoTramiteDocumentoCombo,
				asuntoField,
				observacionField,
				procesoCombo,
				responsabilidadCombo,
				fechaInicioField,
				fechaFinalField,
				responsableField.hide()
			]
			
		});	
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[agregarPanel],
// 			buttons:[cancelarButton,saveButton,addButton]
			buttons:[cancelarButton,saveButton]
			,listeners :{
				afterrender:function(){
					if(uxData&&uxData.action=='edit'){
						agregarPanel.load({
							url:'tramiteDocumentario/registro/getSolicitudProceso',
							params:{
								codigo:uxData.codigo
							}
						})
					}else if(uxData&&uxData.action=='view'){
						console.log('uxData.tipo'+uxData.tipo);
						agregarPanel.load({
							url:'tramiteDocumentario/registro/getSolicitudProceso',
							params:{
								codigo:uxData.codigo,
							}
						})
						saveButton.disable()
					}
				
				}
			}
		});
		
		APP.Portal.TramiteDocumentario.Registro.Load.RegistroWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Registro.Load.RegistroWindowsMantenimiento,Ext.window.Window,{});

	APP.Portal.TramiteDocumentario.Registro.Load.RegistroToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.TramiteDocumentario.Registro.Load.RegistroFiltro({
			
		});
		
		me.getFiltroField=function(form){
			return filtroField;
		}
		
		var valueFind = [];
		
		me.findExcel = function(form){
			
			var fechaFormat='1000/01/01';

			valueFind = form;
			
			if(isEmpty(valueFind.fechaAgregarInicio)){
				valueFind.fechaAgregarInicio = fechaFormat;
			}
			if(isEmpty(valueFind.fechaAgregarFinal)){
				valueFind.fechaAgregarFinal = fechaFormat;
			}
						
		}
		
		Ext.apply(config,{
			items : [
				filtroField,
				'->',
				{
					text : 'Reporte de busqueda',
					height : 30,
					iconCls : 'btn-export-icon',
					cls : 'btn',
					handler:function(){
						
						if(valueFind != ''){
							window.open(
								"tramiteDocumentario/registro/excel?textoNumeroDocumento=" + valueFind.numeroDocumento
								+"&textoNombreProcedencia="+valueFind.procedencia
								+"&fechaAgregarInicio="+valueFind.fechaAgregarInicio
								+"&fechaAgregarFinal="+valueFind.fechaAgregarFinal
								+"&flagCandado="+"2"
							);
						}
						
					}
				},
				{
					text : 'Agregar Tramite',
					height : 30,
					iconCls : 'btn-add-icon',
					cls : 'btn',
					handler:function(){
						
						var win = new APP.Portal.TramiteDocumentario.Registro.Load.RegistroWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
				
			]
		});
		APP.Portal.TramiteDocumentario.Registro.Load.RegistroToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Registro.Load.RegistroToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.TramiteDocumentario.Registro.Load.RegistroList=function(config){
		var me=this;
		var toolbar,
			store,
			grid,
			itemsForPage = 25;
			
		toolbar=new APP.Portal.TramiteDocumentario.Registro.Load.RegistroToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
				
		
		var formProcedencia,
			formNumeroDocumento,
			formFechaAgregarInicio,
			formFechaAgregarFinal,
			formFlagCandado;
		
		me.loadListParam=function(form){
			var candado = "2";
			formProcedencia = form.procedencia;
			formNumeroDocumento = form.numeroDocumento;
			formFechaAgregarInicio = form.fechaAgregarInicio;
			formFechaAgregarFinal = form.fechaAgregarFinal;
			formFlagCandado = candado;
			
		 	console.log("RestarGrid");
		 	store.currentPage = 1;
		 	store.load({
				start: 0, 
				limit: itemsForPage
// 				,params: {
// 					"textoNumeroDocumento": form.numeroDocumento,
// 					"textoNombreProcedencia": form.procedencia,
// 					"fechaAgregarInicio": form.fechaAgregarInicio,
// 					"fechaAgregarFinal": form.fechaAgregarFinal

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
				url : 'tramiteDocumentario/registro/delete',
				method: 'POST',
				params: {
					codigo:codigo
				},
// 				success: function (result, request){
				success: function (response){
					var respObj = Ext.JSON.decode(response.responseText);					
					
					if( respObj.success == false){
 	 					Ext.Msg.alert("Estado", respObj.message);
 					}else{
 						store.load();
 					}
				},
// 				failure: function (result, request){
				failure: function (response){
					var respObj = Ext.JSON.decode(response.responseText);
                    Ext.Msg.alert("Estado", respObj.status.statusMessage);
				}
			});
			
			
		}
				
		function desestimado(record){
			var codigo = record.get('codigo');
			console.info({action:'desestimado',codigo:codigo})
			Ext.Ajax.request({
				url : 'tramiteDocumentario/registro/desestimado',
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
			var win = new APP.Portal.TramiteDocumentario.Registro.Load.RegistroWindowsMantenimiento({
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
			var win = new APP.Portal.TramiteDocumentario.Registro.Load.RegistroWindowsMantenimiento({
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
		
		store = new Ext.data.Store({
		    fields:[
				'codigo',
				'codigoAsociacion',
				'textoAsunto',
				'textoNombreProcedencia',
				'textoNombreRemitente',
				'textoNumeroDocumento',
				'textoObservacion',
				'flagCandado',
				'tipoColor',
				'proceso',
				'tipoTramiteDocumento',
				'seguridadUsuario',
				'fechaInicio',
				'fechaFinal'
				
			],
			autoLoad:true,
			pageSize: itemsForPage,
		    proxy: {
		        type: 'ajax',
				url: 'tramiteDocumentario/registro/listSolicitudProceso',
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
	            		"textoNumeroDocumento": formNumeroDocumento,
						"textoNombreProcedencia": formProcedencia,
						"fechaAgregarInicio": formFechaAgregarInicio,
						"fechaAgregarFinal": formFechaAgregarFinal,
						"flagCandado": formFlagCandado

	                });
	            }
	        }
		});
		
		
		
		grid = new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [
				{ text: 'correlativo',  dataIndex: 'codigoAsociacion',flex:3 },
				{ text: 'Proceso',  dataIndex: 'proceso',flex:3 },
				{ text: 'Tipo',  dataIndex: 'tipoTramiteDocumento',flex:1 },
				{ text: 'Responsable',  dataIndex: 'seguridadUsuario',flex:1 },
				{ text: 'Asunto',  dataIndex: 'textoAsunto',flex:1 },
				{ text: 'Procedencia',  dataIndex: 'textoNombreProcedencia',flex:1 },
				{ text: 'remitente',  dataIndex: 'textoNombreRemitente',flex:1 },
				{ text: 'N° Documento',  dataIndex: 'textoNumeroDocumento',flex:1 },
				{ text: 'F/D Inicio',  dataIndex: 'fechaInicio',flex:1 },
				{ text: 'F/D Final',  dataIndex: 'fechaFinal',flex:1 }

				],
// 		    style: {borderColor: '#157fcc'},
			dockedItems: [{
		        xtype: 'pagingtoolbar',
		        store: store,   // same store GridPanel is using
		        dock: 'bottom',
		        displayInfo: true
		    }]
			,viewConfig: {
		         getRowClass: function(record, rowIndex, rowParams, store) {
					console.log('fechaInicio '+record.get('fechaInicio'));
// 		            CONDICION DE PRUEBA
// 					return (record.get('fechaInicio') && record.get('flagCandado') == "0") 
// 		                   ? 'y-grid3-different-row' 
// 		                   : 'y-grid3-not-so-different-row';
					console.log('tipoColor '+record.get('tipoColor'));
					tipoColor = record.get('tipoColor');
					var fuente;
					
					if(tipoColor == "V"){
						fuente = 'alert-green-cell-row';

					}else if(tipoColor == "A"){
						fuente = 'alert-yellow-cell-row';
						
					}else if(tipoColor == "R"){
						fuente = 'alert-red-cell-row';
					}
					
					
					return fuente;
		         }
			}
		    ,listeners:{
		    	itemcontextmenu: function( gridObj, record, item, index, e, eOpts ){
		    		var menu=new Ext.menu.Menu({
		    			items: [
		    				{
		    					text:'Editar',
		    					handler: function(){
		    						var flagCandado = record.get('flagCandado');
									if(flagCandado == "1"){
										Ext.MessageBox.alert('Alerta', 'No puede editar un registro cerrado ', function(){
									          return true;
									     });
									}else{
			    						editar(record);
									}
		    					
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
		    				},
		    				{
		    					text:'Sobrestimar',
		    					handler: function(){
    		                        Ext.Msg.confirm("Confimacion", 
    		                        	"¿ Desea desestimar la solicitud ? todos los registros trabajados se cerraran", 
    		                        		function(btnText){
    		                            if(btnText === "yes"){
    		                                desestimado(record);
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
		APP.Portal.TramiteDocumentario.Registro.Load.RegistroList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Registro.Load.RegistroList,Ext.panel.Panel,{});

	APP.Portal.TramiteDocumentario.Registro.Load.RegistroFiltro=function(config){
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
			        	
			        	var form = {
		        			numeroDocumento: numeroDocumentoField.getValue(),
		        			procedencia: procedenciaField.getValue(),
		        			fechaAgregarInicio: fechaAgregarInicioField.getSubmitValue(),
		        			fechaAgregarFinal: fechaAgregarFinalField.getSubmitValue(),
						},
						
						findValue=[];
			        	
						var globalPanel = APP.Portal.TramiteDocumentario.Registro.Load.GlobalPanel;
						
							globalPanel.getTramiteDocumentoPanel().getListPanel().getToolbar().findExcel(form);
							
							globalPanel.getTramiteDocumentoPanel().getListPanel().loadListParam(form);
						
						if(!isEmpty( form.numeroDocumento )){
							findValue.push('N° documento:'+posicionValue(form.numeroDocumento));
						}
						
						if(!isEmpty( form.procedencia )){
							findValue.push('Procedencia:'+posicionValue(form.procedencia));
						}
						
						me.setRawValue(findValue);
						pickerPanel.getForm().reset();
						me.collapse();
			        }
				});
				
				var procedenciaField = new Ext.form.field.Text({
			        fieldLabel: 'Procedencia',
			        name: 'textoNombreProcedencia'
				});
				var numeroDocumentoField = new Ext.form.field.Text({
			        fieldLabel: 'N° documento',
			        name: 'textoNumeroDocumento'
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
				
				me.valueNumeroDocumentoField = function(value){
					numeroDocumentoField.setValue(value);
				}
				
				me.valueProcedenciaField = function(value){
					procedenciaField.setValue(value);
				}

// 				me.getStoreEstado=function(){
// 					return estado
// 				}
				
// 				me.getStoreGradoAcademico=function(){
// 					return storeGradoAcademico
// 				}
// 				me.getStoreCargo=function(){
// 					return storeCargo
// 				}
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
						numeroDocumentoField,
						procedenciaField,
						fechaAgregarInicioField,
						fechaAgregarFinalField
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
						'N° documento',
						'Procedencia'

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
		APP.Portal.TramiteDocumentario.Registro.Load.RegistroFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Registro.Load.RegistroFiltro,APP.form.field.Picker,{});
	
	APP.Portal.TramiteDocumentario.Registro.Load.RegistroPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.TramiteDocumentario.Registro.Load.RegistroList({})
		
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Tramite Documento',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.TramiteDocumentario.Registro.Load.RegistroPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Registro.Load.RegistroPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.TramiteDocumentario.Registro.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.TramiteDocumentario.Registro.Load.RegistroPanel({});
		
		me.getTramiteDocumentoPanel=function(){
			return datoGeneralPanel;
		}
				
		Ext.apply(config,{
			layout:'fit',
			items: [ datoGeneralPanel ]
		});
		
		APP.Portal.TramiteDocumentario.Registro.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Registro.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.TramiteDocumentario.Registro.Load.Container({});
		APP.Portal.TramiteDocumentario.Registro.Load.GlobalPanel = globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
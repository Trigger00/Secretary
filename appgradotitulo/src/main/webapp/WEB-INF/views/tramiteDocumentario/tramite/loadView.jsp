<script type="text/javascript">
	Ext.ns('APP.Portal.TramiteDocumentario.Tramite.Load')
	APP.Portal.TramiteDocumentario.Tramite.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.TramiteDocumentario.Tramite.Load.TramiteWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		
		var agregarPanel,
			saveButton, 
			cancelarButton,
			estado,
			itemsForPage = 25;
				
		atributoListt = new APP.Portal.TramiteDocumentario.Tramite.Load.AtributosList({});
		
		me.getAtributoListt = function(){
			return atributoListt;
		}
		
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
	                	url: 'tramiteDocumentario/tramite/saveFlujoProceso',
	                    success: function(form, action) {
	                    	var globalPanel = APP.Portal.TramiteDocumentario.Tramite.Load.GlobalPanel;
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
		
		
		var storeDefinicionProceso = new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre',
				'definicionProcesoDestino',
				'textoDefinicionProcesoDestino'
			],
// 			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'tramiteDocumentario/tramite/listDecisionProceso',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		var storeAtributos = new Ext.data.Store({
		    fields:[
				'codigo',
				'tipoAtributoCodigo',
				'tipoAtributo.textoNombre'
			],
// 			autoLoad:true,
			pageSize: itemsForPage,
		    proxy: {
		        type: 'ajax',
				url: 'tramiteDocumentario/tramite/listAtributos',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
	    })
		
		var ids =[];
		var documentoPanel;
		
		function addArchivoField(codigoDefinicionProceso, actions){
			console.log("codigoDefinicionProceso "+codigoDefinicionProceso)
			
			storeAtributos.load({
				params:{
					"definicionProceso.codigo" : codigoDefinicionProceso
				},
				callback: function(records, operation, success) {
					console.log("storeAtributos.each");
					
					storeAtributos.each(function(record) {
						
						var codigo = record.get('codigo');
						var tipoAtributo = record.get('tipoAtributoCodigo');
						
						console.log("codigo");
						console.log(codigo);
						
						console.log("tipoAtributo");
						console.log(tipoAtributo);

						
// 						ids.push(tipoAtributo)
						ids.push(codigo)
						
					});
					
					if(ids.length > 0){
						var documentoPanel = new APP.Portal.TramiteDocumentario.Tramite.Load.AtributosList({
							codigos: ids,
							actions: actions
						});
						
						agregarPanel.add(documentoPanel);
					}
			    }
			})
		}

		var storeResponsabilidad = new Ext.data.Store({
			fields:[
			   	'codigo',
				'codigoSeguridadUsuario',
				'textoNombreSeguridadUsuario'
			],
// 			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'tramiteDocumentario/tramite/listResponsabilidad',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		
		var codigoField = new Ext.form.field.Text({
	        fieldLabel: 'codigo',
	        name: 'codigo'
		});
		
		var definicionProcesoDestinoField = new Ext.form.field.Text({
	        fieldLabel: 'Siguiente Proceso',
	        name: 'textoDefinicionProcesoDestino'
		});
		
		var flagCandadoField = new Ext.form.field.Text({
	        fieldLabel: 'flagCandado',								
	        name: 'flagCandado'
		});
		
		var detalleField = new Ext.form.field.TextArea({
	        fieldLabel: 'Detalle',
	        name: 'textoDetalle',
	        allowBlank: false,
	        height :50
        });
		
		var accionCombo = new Ext.form.field.ComboBox({
	    	fieldLabel: 'Accion',
	        name: "definicionProceso.codigo",
			store: storeDefinicionProceso,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'definicionProcesoDestino',
			allowBlank: false,
			forceSelection: true,
			listConfig : {
				listeners : {
					itemclick : function(list,record) {
						var definicionProcesoDestino = record.get('textoDefinicionProcesoDestino');
						definicionProcesoDestinoField.setValue(definicionProcesoDestino);
						
						usuarioCombo.clearValue();
						storeResponsabilidad.load({
							params: {
								"responsable.definicionProceso.codigo":record.get('definicionProcesoDestino'),
							}
						});
						
					}
				}
								
			},
		});
		var usuarioCombo = new Ext.form.field.ComboBox({
	    	fieldLabel: 'Usuarios',
	        name: 'responsabilidad.codigo',
 			store: storeResponsabilidad,
	        queryMode: 'local',
	        displayField: 'textoNombreSeguridadUsuario',
	        valueField: 'codigo',
			allowBlank: false
// 			forceSelection: true
		});
		
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
			fieldDefaults: {
		        width : 500,
		        labelWidth : 200
		    },
			defaultType: 'textfield',
			
			items:[
				codigoField.hide(),
				flagCandadoField.hide(),
				detalleField,
				accionCombo,
				definicionProcesoDestinoField,
				usuarioCombo
// 				,documentoPanel
				
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
					if(uxData&&uxData.action=='atender'){
						
						var textoDefinicionProceso = Ext.util.Format.substr(uxData.definicionProceso,0,4);
						console.log("uxData.definicionProceso "+uxData.definicionProceso);
						Ext.util.Format.substr(uxData.definicionProceso,0,2);
						
						console.log("format "+Ext.util.Format.substr(uxData.definicionProceso,0,4));
						if(textoDefinicionProceso == "PASO"){
							
							var codigoField = new Ext.form.field.Text({
						        fieldLabel: 'codigo',
						        name: 'PROCESO A REALIZAR'
							});
							
							agregarPanel.add(codigoField);
						}
						
						var codigoField = new Ext.form.field.Text({
					        fieldLabel: 'codigo',
					        name: 'codigo'
						});
						
						storeDefinicionProceso.load({
							params:{
								"definicionProcesoOrigen.codigo":uxData.codigoDefinicionProceso
							}
						})
						
						codigoField.setValue(uxData.codigo);
						flagCandadoField.setValue(uxData.codigoFlagCandado);
						addArchivoField(uxData.codigoDefinicionProceso,uxData)

					}else if(uxData&&uxData.action=='view'){
						agregarPanel.load({
							url:'tramiteDocumentario/registro/getSolicitudProceso',
							params:{
								codigo:uxData.codigo,
							}
						})
						
						saveButton.disable()
						
					}else if(uxData&&uxData.action == 'edit'){
						agregarPanel.load({
							url:'tramiteDocumentario/tramite/getFlujoProceso',
							params:{
								codigo:uxData.codigo,
							},
							success : function() {
								console.log('success')
								console.log("accionCombo.getValue() "+accionCombo.getValue())
								console.log("detalleField "+detalleField.getValue())
								
								storeResponsabilidad.load({
									params: {
											"responsable.definicionProceso.codigo": accionCombo.getValue()
									}
								});								
							}
						})
						
						storeDefinicionProceso.load({
							params:{
								"definicionProcesoOrigen.codigo":uxData.codigoDefinicionProceso
							}
						})
						
						addArchivoField(uxData.codigoDefinicionProceso,uxData);
					}
				
				}
			}
		});
		
		APP.Portal.TramiteDocumentario.Tramite.Load.TramiteWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.TramiteWindowsMantenimiento,Ext.window.Window,{});

	APP.Portal.TramiteDocumentario.Tramite.Load.TramiteToolbar=function(config){
		var me=this;
		var filtroField = new APP.Portal.TramiteDocumentario.Tramite.Load.TramiteFiltro({
			
		});

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
		
		me.getFiltroField = function(form){
			return filtroField;
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
								"tramiteDocumentario/tramite/excel?solicitudProceso.codigo=" + valueFind.solicitudProcesoCodigo
								+"&textoNombreProcedencia="+valueFind.procedencia
								+"&fechaAgregarInicio="+valueFind.fechaAgregarInicio
								+"&fechaAgregarFinal="+valueFind.fechaAgregarFinal
								+"&fechaAgregarFinal="+valueFind.fechaAgregarFinal
								+"&flagCandado="+"2"
							);
						}
					}
				}
			]
		});
		APP.Portal.TramiteDocumentario.Tramite.Load.TramiteToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.TramiteToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.TramiteDocumentario.Tramite.Load.RegistroFiltro=function(config){
		var me=this;
		var itemsForPage = 25;
		var formProcedencia,
	 		formNumeroDocumento,
			formFechaAgregarInicio,
			formFechaAgregarFinal,
			formFlagCandado; 
	
		Ext.apply(config,{
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){

				var numeroDocumento = null;
				var solicitudProcesoCodigo = null;
				var itemsPerPage = 25;
				
				var cancelarButton = new Ext.Button({
					width : 80,
					iconCls : 'btn-delete-icon',
					text : 'Cancelar',
					handler :function() {
						win.close();
						
					}
				});
				
				var addButton = new Ext.Button({
					width : 80,
					iconCls : 'btn-add-icon',
					text : 'Agregar',
			        handler: function() {
			        	
			        	if(numeroDocumento){
			        		var globalPanel = APP.Portal.TramiteDocumentario.Tramite.Load.GlobalPanel,
								getIndice = globalPanel.getTramiteDocumentoPanel().getListPanel().getToolbar().getFiltroField();
								
			        		getIndice.numeroDocumentoTramite(numeroDocumento);	
			        		getIndice.solicitudProcesoCodigo(solicitudProcesoCodigo);	
		        		
			        		win.close();
			        		
			        	}else{
			        		alert('Seleccione un registro')	
			        	}
			        }
				});
				
				var findButton = new Ext.Button({
					width : 80,
					iconCls : 'btn-search-icon',
					text : 'Buscar',
					handler :function() {
						var fechaInicio,
							fechaFinal;
						
						if(isEmpty(fechaAgregarInicioField.getSubmitValue())){
							fechaInicio = '1000/01/01';
						}else{
							fechaInicio = fechaAgregarInicioField.getSubmitValue();
						}
			        	
						if(isEmpty(fechaAgregarFinalField.getSubmitValue())){
							fechaFinal = '1000/01/01';
						}else{
							fechaFinal = fechaAgregarFinalField.getSubmitValue();
						}
						
						formProcedencia = procedenciaField.getValue(),
					 	formNumeroDocumento = numeroDocumentoField.getValue(),
						formFechaAgregarInicio = fechaInicio,
						formFechaAgregarFinal = fechaFinal
						
						var form={
							procedencia : procedenciaField.getValue(),
						 	numeroDocumento : numeroDocumentoField.getValue(),
							fechaAgregarInicio : fechaInicio,
							fechaAgregarFinal : fechaFinal
						}
						
						loadRegistroParam(form);		
					}
				});
				
				function loadRegistroParam(form){					
					storeRegistroSolicitud.currentPage = 1;
					storeRegistroSolicitud.load({
						start: 0, 
						limit: itemsPerPage,
						params: {
							"textoNumeroDocumento": form.numeroDocumento,
							"textoNombreProcedencia": form.procedencia,
							"fechaAgregarInicio": form.fechaAgregarInicio,
							"fechaAgregarFinal": form.fechaAgregarFinal,
							"flagCandado":"2"
						}
					});	
				}
		
				var storeRegistroSolicitud = new Ext.data.Store({
				    fields:[
						'codigo',
						'textoAsunto',
						'textoNombreProcedencia',
						'textoNombreRemitente',
						'textoNumeroDocumento',
						'textoObservacion',
						'flagCandado',
						'proceso',
						'tipoTramiteDocumento',
						'seguridadUsuario',
						'fechaInicio',
						'fechaFinal'
						
					],
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
				});
			
				var grid=new Ext.grid.Panel({
				    store: storeRegistroSolicitud,
				    xtype: 'grouped-header-grid',
				    columns: [
						{ text: 'Proceso',  dataIndex: 'proceso',flex:3, hidden: true},
						{ text: 'Tipo',  dataIndex: 'tipoTramiteDocumento',flex:1, hidden: true},
						{ text: 'Responsable',  dataIndex: 'seguridadUsuario',flex:1, hidden: true},
						{ text: 'Asunto',  dataIndex: 'textoAsunto',flex:1, hidden: true},
						{ text: 'Procedencia',  dataIndex: 'textoNombreProcedencia',flex:1 },
						{ text: 'remitente',  dataIndex: 'textoNombreRemitente',flex:1, hidden: true},
						{ text: 'N° Documento',  dataIndex: 'textoNumeroDocumento',flex:1 },
						{ text: 'F/D Inicio',  dataIndex: 'fechaInicio',flex:1 },
						{ text: 'F/D Final',  dataIndex: 'fechaFinal',flex:1 }
			        ],
				    style: {borderColor: '#157fcc'},
				    dockedItems: [{
				        xtype: 'pagingtoolbar',
				        store: storeRegistroSolicitud, 
				        dock: 'bottom',
				        displayInfo: true
				    }],
				    listeners :{
						
				    	select:function(dv, record, index, eOpts ){
							console.log('Seleccionado el N° Documento'+record.get('textoNumeroDocumento'));
							numeroDocumento = record.get('textoNumeroDocumento');
							solicitudProcesoCodigo = record.get('codigo');
						}
					}
				    
				});		
				
				var procedenciaField = new Ext.form.field.Text({
			        fieldLabel: 'Procedencia',
			        name: 'textoNombreProcedencia',
			        anchor: '70%',
				});
				var numeroDocumentoField = new Ext.form.field.Text({
			        fieldLabel: 'N° documento',
			        name: 'textoNumeroDocumento',
			        anchor: '70%',
				});
				
				var fechaAgregarInicioField = new Ext.form.field.Date({
			        fieldLabel: 'Fecha Inicio',
			        name: 'fechaAgregarInicio',
			        format : "d/m/Y",
			        submitFormat :"Y/m/d",
			        anchor: '70%',
			        allowBlank: true
				})
				var fechaAgregarFinalField = new Ext.form.field.Date({
			        fieldLabel: 'Fecha Final',
			        name: 'fechaAgregarFinal',
			        format : "d/m/Y",
			        submitFormat :"Y/m/d",
			        anchor: '70%',
			         allowBlank: true
				})
				
				var panelFindField=new Ext.form.Panel({
					height: 160,
					width : '100%',
					bodyPadding: 5,
					items:[
						procedenciaField,
						numeroDocumentoField,
						fechaAgregarInicioField,
						fechaAgregarFinalField
					],
					buttons:[findButton]
				})
			
				var panelLoader = new Ext.panel.Panel({
					width: 600,
				    height: 450,
					layout:{
						type:'border'
					},
					fileUpload: true,
					items:[
						{
							region: 'north', 
							height:160,
							width:'100%',
							border: true,
							items : [panelFindField]
						},
						{
							region: 'center',
							layout: 'fit',
							items : [grid]
						}
					]
				})
				var win =Ext.create('Ext.window.Window', {
				    title: 'Buscar',
				    closable:false,
				    modal: true,
				    items: [panelLoader],
				    buttons:[cancelarButton,addButton]
				    
				});
				
				win.show()
			}
		})
		
		APP.Portal.TramiteDocumentario.Tramite.Load.RegistroFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.RegistroFiltro,Ext.Button,{});
	
	APP.Portal.TramiteDocumentario.Tramite.Load.TramiteList=function(config){
		var me=this;
		var toolbar,
			mantenimiento,
			store,
			grid,
			itemsForPage = 25;
			
		toolbar = new APP.Portal.TramiteDocumentario.Tramite.Load.TramiteToolbar({
			dock:'top'
		});
		
		mantenimiento = new APP.Portal.TramiteDocumentario.Tramite.Load.TramiteWindowsMantenimiento({});
		
		me.getToolbar = function(form){
			return toolbar;
		}
		
		me.getMantenimiento = function(){
			return mantenimiento;
		}
				
		me.loadList=function(){
			store.currentPage = 1;
			store.load({
				start: 0, 
				limit: 25
			});
		}
		var formNombreUniversidad,
		formSolicitudProcesoCodigo,
		formPais,
		formTipo,
		formSiglas,
		formAbreviatura,
		formFlagCandado;
		
		me.loadListParam=function(form){
			var candado = "2";
			
			formNombreUniversidad = form.nombreUniversidad;
			formSolicitudProcesoCodigo = form.solicitudProcesoCodigo;
			formPais = form.pais;
			formTipo = form.tipo;
			formSiglas = form.siglas;
			formAbreviatura = form.abreviatura;
			formNumeroDocumento = form.numeroDocumento;
			formFlagCandado = candado;

		
		 	console.log("RestarGrid");
		 	store.currentPage = 1;
		 	store.load({
				start: 0, 
				limit: itemsForPage
// 				,params: {

// 					"solicitudProceso.codigo": form.solicitudProcesoCodigo,		
// 					"textoNombreProcedencia": form.procedencia,
// 					"fechaAgregarInicio": form.fechaAgregarInicio,
// 					"fechaAgregarFinal": form.fechaAgregarFinal,
// 					"flagCandado": candado
// 				}
			});
		}
		
		function atender(record){
			
			var definicionProceso = record.get('codigoDefinicionProceso');
			var flagCandado = record.get('flagCandado');
			var codigo = record.get('codigo');
			var textoDefinicionProceso = record.get('definicionProceso');
			
			var win = new APP.Portal.TramiteDocumentario.Tramite.Load.TramiteWindowsMantenimiento({
				title: 'Atender',
				uxData:{
					codigoDefinicionProceso: definicionProceso,
					codigoFlagCandado: flagCandado,
					codigo: codigo,
					action:'atender',
					definicionProceso: textoDefinicionProceso
				}
			});
			
			win.show();
			
		}
		
		function editar(record){
			var codigo = record.get('codigo');
			var definicionProceso = record.get('codigoDefinicionProceso');
			var win = new APP.Portal.TramiteDocumentario.Tramite.Load.TramiteWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigoDefinicionProceso: definicionProceso,
					codigo: codigo,
					action:'edit'
				}
			});
			win.show();
			
		}
		
		function visualizar(record){
			var codigo = record.get('codigo');
			var win = new APP.Portal.TramiteDocumentario.Tramite.Load.TramiteWindowsMantenimiento({
				title: 'Visualizar',
				uxData:{
					codigo:codigo,
					action:'view'
				}
			});
			win.show();
			
		}
		
		store=new Ext.data.Store({
		    fields:[
				'codigo',
				'solicitudProceso',
				'solicitudProcesoFlagCandado',
				'definicionProceso',
				'codigoDefinicionProceso',
				'responsable',
				'seguridadUsuario',
				'tipoColor',
				'textoRegistrosAdjuntos',
				'flagCandado',
				'flagUsuarioValido',
				'flagFinal',
				'fechaIncio',
				'fechaFinal'
				
			],
			autoLoad:true,
			pageSize: itemsForPage,
		    proxy: {
		        type: 'ajax',
				url: 'tramiteDocumentario/tramite/listFlujoProceso',
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
	            		"solicitudProceso.codigo":formSolicitudProcesoCodigo,
	            		"textoNombre":formNombreUniversidad,
						"pais.codigo":formPais,
						"tipoUniversidad.codigo":formTipo,
						"textoNombreSiglas":formSiglas,
						"textoNombreAbreviado":formAbreviatura,
						"flagCandado": formFlagCandado
				
	                });
	            }
	        }
		});
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [
				{ text: 'N° Tramite',  dataIndex: 'solicitudProceso',flex:1 },
				{ text: 'Definicion',  dataIndex: 'definicionProceso',flex:3 },
				{ text: 'Responsable',  dataIndex: 'responsable',flex:2 },
				{ text: 'Usuario',  dataIndex: 'seguridadUsuario',flex:2 },
				{ text: 'DOC Adjuntos',  dataIndex: 'textoRegistrosAdjuntos',flex:2 },
				{ text: 'F/D Inicio',  dataIndex: 'fechaIncio',flex:1 },
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
// 					console.log('solicitudProceso.flagCandado '+record.get('solicitudProcesoFlagCandado'));
// 		            return (record.get('solicitudProcesoFlagCandado') == "0") 
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
		    					text:'Atender',
		    					hidden : record.get('flagCandado') == '1' || record.get('flagUsuarioValido') == '0',
		    					handler: function(){
		    						atender(record);
	    						}
		    				},
		    				{
		    					text:'Editar',
		    					hidden : record.get('flagCandado') == '0',
// 		    					hidden : record.get('flagCandado') == '0',
		    					handler: function(){
		    						editar(record);
	    						}
		    				},
		    				{
		    					text:'Visualizar',
		    					hidden : record.get('flagCandado') == '0',
		    					handler: function(){
		    						visualizar(record);
	    						}
		    				},
		    		
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
		APP.Portal.TramiteDocumentario.Tramite.Load.TramiteList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.TramiteList,Ext.panel.Panel,{});
	
	
	APP.Portal.TramiteDocumentario.Tramite.Load.AtributosList = function(config){
		var me=this;
		var field;
		var ids =[];	
		var formPanelToLoad;	
		
		console.log('config');
		console.info(config);

		var codigos = config.codigos;
		var actions = config.actions;
		
		
// 		me.setValueField = function(nameField, value){
// 			var field =  formPanelToLoad.getForm();
// 	 		return  field.findField(nameField).setValue(value);
// 		}

		var getUxFields=function(data){
			var items=[];
			
			Ext.each(data.items,function(item){
				var panelEstudianteResolucion  = new APP.Portal.TramiteDocumentario.Tramite.Load.DocumentoFiltro({
					height:30,
					uxData:{
						nameField : item+'.name',
						codigoField : item+'.codigo',
						actions: actions
					}
				})
				items.push(panelEstudianteResolucion)
			})
			
			return items;
		}
		
		field = getUxFields({
			items:codigos
		})

		var formPanelToLoad = new Ext.form.Panel({
			items:field,
			name:'formPanelToLoad'
		})
		
		
		console.log('codigos '+codigos)
		console.log('field '+field)

// 		var f =  formPanelToLoad.getForm();
// 		console.log('f '+f)
// 		console.info({f : f})
		
// 		var f3 =  f.findField("resolucionUno");
// 		console.log('f3 '+f3)
// 		console.info({f3 : f3})
		
// 		var f2 =  f.findField("resolucionUno").setValue('test');
// 		console.log('f2 '+f2)
// 		console.info({f2 : f2})
		
		
		
		Ext.apply(config,{
			items  :formPanelToLoad,
			layout : 'fit'
		});
				
		APP.Portal.TramiteDocumentario.Tramite.Load.AtributosList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.AtributosList,Ext.panel.Panel,{});

	
	APP.Portal.TramiteDocumentario.Tramite.Load.DocumentoFiltro = function(config){
		
		var me=this,
		uxData = config.uxData;
		actions = uxData.actions;
		console.log('uxData '+uxData);
		console.log('uxData.nameField '+uxData.nameField);
		
		var storeAlumno;
		
		var matriculaFindField=new Ext.form.field.Text({
	        fieldLabel: 'Archivo',
	        name: uxData.nameField,
	        anchor: '100%',
	        readOnly :true,
        });
		
		var estudianteCodigoField=new Ext.form.field.Text({
	        name: uxData.codigoField,
        });
		var panelMatricula=new Ext.form.Panel({
			items:[
			     matriculaFindField,
			     estudianteCodigoField.hide()
			 ]
		})
		var btnView=new Ext.button.Button({
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){
				var itemsForPage = 25;
				var textoMatricula=null;
				var estudianteCodigo=null;
				var itemsPerPage = 25;
				var cancelarButton = new Ext.Button({
					width : 80,
					iconCls : 'btn-delete-icon',
					text : 'Cancelar',
					handler :function() {
						win.close();
						
					}
				});
				
				var formNombreArchivo,
					formFechaAgregarInicio,
					formFechaAgregarFinal;
				
				var addButton = new Ext.Button({
					width : 80,
					iconCls : 'btn-add-icon',
					text : 'Agregar',
			        handler: function() {
			        	if(textoMatricula){
			        		console.info('selecciono '+textoMatricula);
			        		
			        		matriculaFindField.setValue(textoMatricula);
			        		
			        		estudianteCodigoField.setValue(estudianteCodigo);
			        		
			        		win.close();

			        	}else{
			        		alert('Seleccione un registro')	
			        	}
			        }
				});
				var findButton = new Ext.Button({
					width : 80,
					iconCls : 'btn-search-icon',
					text : 'Buscar',
					handler :function() {
						var fechaInicio,
							fechaFin;
						
						if(isEmpty(fechaAgregarInicioField.getSubmitValue())){
							fechaInicio = '1000/01/01';
						}else{
							fechaInicio = fechaAgregarInicioField.getSubmitValue();
						}
			        	
			        	if(isEmpty(fechaAgregarFinalField.getSubmitValue())){
			        		fechaFin = '1000/01/01';
			        	}else{
			        		fechaFin = fechaAgregarFinalField.getSubmitValue();
			        	}
						
						formFechaAgregarInicio = fechaInicio,
						formFechaAgregarFinal = fechaFin,
						formNombreArchivo = nombreArchivoField.getValue()
						
						var form={
							fechaAgregarInicio: fechaInicio,
		        			fechaAgregarFinal: fechaFin,
		        			nombreArchivo: nombreArchivoField.getValue()
						
						}
						
						loadRegistroParam(form);		
					}
				});
				
				function loadRegistroParam(form){					
					store.currentPage = 1;
					
					store.load({
						start: 0, 
						limit: itemsPerPage,
						params: {
							"fechaAgregarInicio": form.fechaAgregarInicio,
							"fechaAgregarFinal": form.fechaAgregarFinal,
							"textoNombreRegistro": form.nombreArchivo,
							"flagCandado": "2"
						}
					});	
				}
				
				var store = new Ext.data.Store({
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
// 								"flagCandado": "",
								"fechaAgregarInicio": formFechaAgregarInicio,
								"fechaAgregarFinal": formFechaAgregarFinal

			                });
			            }
			        }
				});
				
				
				var grid=new Ext.grid.Panel({
				    store: store,
				    xtype: 'grouped-header-grid',
				    columns: [
						{ text: 'Nombre',  dataIndex: 'textoNombreRegistro',flex:1 },
						{ text: 'Asunto',  dataIndex: 'textoAsunto',flex:1 },
						{ text: 'Detalle',  dataIndex: 'textoDetalle',flex:1 },
						{ text: 'Ruta',  dataIndex: 'textoRuta',flex:2 },
						{ text: 'F/D creacion',  dataIndex: 'fechaAgregar',flex:1 }

				    ],
				    style: {borderColor: '#157fcc'},
				    dockedItems: [{
				        xtype: 'pagingtoolbar',
				        store: store, 
				        dock: 'bottom',
				        displayInfo: true
				    }],
				    listeners :{
						select:function(dv, record, index, eOpts ){
							console.log('selecccionado la matricula'+record.get('textoMatricula'));
							textoMatricula = record.get('textoNombreRegistro');
							estudianteCodigo = record.get('codigo');
						}
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
				
				var panelFindField=new Ext.form.Panel({
					height: 130,
					width : 600,
					bodyPadding: 5,
					items:[
						nombreArchivoField,
						fechaAgregarInicioField,
						fechaAgregarFinalField
// 						matriculaField
					],
					buttons:[findButton]
				})
			
				var panelLoader = new Ext.panel.Panel({
					width: 600,
				    height: 400,
					layout:{
						type:'border'
					},
					fileUpload: true,
					items:[
						{
							region: 'north', height:130,width:'100%',border: true,items : [panelFindField]
						},
						{
							region: 'center',layout: 'fit',items : [grid]
						}
					]
				})
				var win =Ext.create('Ext.window.Window', {
				    title: 'Buscar',
				    closable:false,
				    modal: true,
				    items: [panelLoader],
				    buttons:[cancelarButton,addButton]
				    
				});
				
				win.show()
			}
		})
		var panelView=new Ext.form.Panel({
			padding : '0 0 0 10',
			items:[btnView]
		})
		
		var panelEstudianteResolucion = new Ext.form.Panel({
			width: 450,
			layout:{
				type:'hbox',
				align:'stretch'
			},
			items:[
				{
			     	flex: 92,
			     	items:[panelMatricula]
			    },
			    {
			     	flex: 8,
			     	items:[panelView]
			    },
			]
		});
		
		Ext.apply(config,{
			dockedItems : [toolbar],
			items : [panelEstudianteResolucion],
			layout : 'fit'
			,listeners :{
				afterrender:function(){
					if(actions.action == 'edit'){
						panelEstudianteResolucion.load({
							url:'tramiteDocumentario/tramite/getFlujoProceso',
							params:{
								codigo: actions.codigo,
							},
							success : function() {
								console.log('success')
															
							}
						})	
					}
				
				}
			}
			
		});
		APP.Portal.TramiteDocumentario.Tramite.Load.DocumentoFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.DocumentoFiltro,Ext.panel.Panel,{});
	
	APP.Portal.TramiteDocumentario.Tramite.Load.TramiteFiltro=function(config){
		var me=this;
		
		
	    	    
		Ext.apply(config,{
			width : 450,
			createPicker:function(){
				var me = this,
					storeautoridad,	
					estado,
					cancelarButton,
					findButton;
				
				me.numeroDocumentoTramite = function(val){
		        	return numeroDocumentoField.setValue(val);
		        }
				
				me.solicitudProcesoCodigo = function(val){
		        	return solicitudProcesoCodigoField.setValue(val);
		        }
				
				
				
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
			        	
			        	var form={
		        			solicitudProcesoCodigo : solicitudProcesoCodigoField.getValue(),
		        			procedencia: procedenciaField.getValue(),
		        			fechaAgregarInicio: fechaAgregarInicioField.getSubmitValue(),
		        			fechaAgregarFinal: fechaAgregarFinalField.getSubmitValue(),
						};
			        	
						var globalPanel = APP.Portal.TramiteDocumentario.Tramite.Load.GlobalPanel;
							
							globalPanel.getTramiteDocumentoPanel().getListPanel().getToolbar().findExcel(form);
						
							globalPanel.getTramiteDocumentoPanel().getListPanel().loadListParam(form);
						
						var findValue=[];
							
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
				
				var numeroDocumentoField = new Ext.form.field.Text({
			        fieldLabel: 'Numero Documento',
			        name: 'solicitudProceso.textoNumeroDccumento',
			        anchor: '100%',
			        readOnly :true,
			        allowBlank: false
		        });	
				
				var solicitudProcesoCodigoField=new Ext.form.field.Text({
			        name: "solicitudProceso.codigo"
		        });
				
				var panelRegistro = new Ext.form.Panel({
					items:[
					     numeroDocumentoField,
					     solicitudProcesoCodigoField.hide()
					 ]
				})
				var btnView = new APP.Portal.TramiteDocumentario.Tramite.Load.RegistroFiltro({});
				
				var panelView = new Ext.form.Panel({
					padding : '0 0 0 10',
					items:[btnView]
				})
			
				var registroPanel = new Ext.form.Panel({
					width: 400,
					labelWidth : 200,
					layout:{
						type:'hbox',
						align:'stretch'
					},
					items:[
						{
					     	flex: 90,
					     	items:[panelRegistro]
					    },
					    {
					     	flex: 10,
					     	items:[panelView]
					    },
					]
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
		                //height: 300,
					items:[
						registroPanel,
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
		APP.Portal.TramiteDocumentario.Tramite.Load.TramiteFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.TramiteFiltro,APP.form.field.Picker,{});
	
	
	APP.Portal.TramiteDocumentario.Tramite.Load.TramitePanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel = new APP.Portal.TramiteDocumentario.Tramite.Load.TramiteList({})
		
		me.getListPanel = function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Tramite Documento',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.TramiteDocumentario.Tramite.Load.TramitePanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.TramitePanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.TramiteDocumentario.Tramite.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.TramiteDocumentario.Tramite.Load.TramitePanel({});
		
		me.getTramiteDocumentoPanel=function(){
			return datoGeneralPanel;
		}
				
		Ext.apply(config,{
			layout:'fit',
			items: [ datoGeneralPanel ]
		});
		
		APP.Portal.TramiteDocumentario.Tramite.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.TramiteDocumentario.Tramite.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.TramiteDocumentario.Tramite.Load.Container({});
		APP.Portal.TramiteDocumentario.Tramite.Load.GlobalPanel = globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
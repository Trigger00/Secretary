<script type="text/javascript">
	Ext.ns('APP.Portal.Diplomado.Load')
	APP.Portal.Diplomado.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.Diplomado.Load.GradoTituloWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		
		var agregarPanel,
			saveButton, 
			cancelarButton,
			storeAgendaGrupo,
			storeAlumno,
			storeAutoridad,
			storeAutoridadRector,
			storeAutoridadSecretarioGeneral,
			storeAutoridadDirectorPosgrado,
			storeAutoridadDecano,
			storeSexo,
			storeFacultad,
			storeEspecialidad,
			storeEspecialidadPregrado,
			storeTipoDocumento,
			storeTipiDiploma,
			storeModalidadEstudio,
			storeUniversidadRevalida,
			storeModalidadGradoTitulo;
		
		cancelarButton = new Ext.Button({
			width : 80,
			text : 'Cancelar',
			handler :function() {
				me.close();
				
			}
		});
		
		saveButton = new Ext.Button({
			width : 80,
			text : 'Grabar',
	        handler: function() {
	            var save = agregarPanel.getForm();
	            var data=[];
	            save.getFields().each(function(field) {
	            	data.push({field:field,validate:field.validate()});
	            });
	            console.info({data:data})
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'diplomado/saveDiplomado',
	                    success: function(form, action) {
	                    	var globalPanel=APP.Portal.Diplomado.Load.GlobalPanel;
	                    	globalPanel.getGradoTituloPanel().getListPanel().loadList();
							Ext.Msg.alert('Success', 'Se ha grabado el Registro correctamente.');
							me.close();
	                    
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', 'error');
	                    }
	                });
	            }
	        }
		});
		
		storeAlumno=new Ext.data.Store({
		    fields:[
				'codigo',    
				'textoMatricula',    
				'facultad',
	            'especialidad',
				'textoNombreCompleto',
	            'textoDocumento'
			],
		    proxy: {
		        type: 'ajax',
		        url: 'datoGeneral/getEstudianteList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
		});
		
		storeSexo = Ext.create('Ext.data.Store', {
		    fields: ['codigo', 'textoSexo'],
		    data : [
		        {"codigo":"F", "textoSexo":"Femenino"},
		        {"codigo":"M", "textoSexo":"Masculino"}
	
		    ]
		});
		storeAutoridadRector=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombreAutoridad'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'autoridad/getAutoridadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				},
				extraParams:{
					"cargo.codigoExterno": 'REC',
					"flagEstado": 'A'
		        }
			}
		});
		storeGradoAcademico=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			//autoLoad:true,
			proxy: {
					type: 'ajax',
					url: 'revalida/getRevalidaList',
					reader: {
						type: 'json',
						root: 'data',
						idProperty : 'codigo',
						totalProperty :'totalCount'
					}
			}
		});
		storeAutoridadSecretarioGeneral=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombreAutoridad'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'autoridad/getAutoridadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				},
				extraParams:{
					"cargo.codigoExterno": 'SG',
					"flagEstado": 'A'
		        }
			}
		});

		storeAutoridadDecano=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombreAutoridad'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'autoridad/getAutoridadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				},
				extraParams:{
					"cargo.codigoExterno": 'DEC',
					"flagEstado": 'A'
		        }
				
			}
		});
		storeAutoridadDirectorPosgrado=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombreAutoridad'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'autoridad/getAutoridadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				},
				extraParams:{
					"cargo.codigoExterno": 'DEP',
					"flagEstado": 'A'
		        }
			}
		});
		storePais=new Ext.data.Store({
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
		storeUniversidadBachiller=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'gradoTitulo/getUniversidadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		storeUniversidadMaestria=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'gradoTitulo/getUniversidadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		storeUniversidadRevalida=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'gradoTitulo/getUniversidadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		storeFacultad=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombreEspanol'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'facultades/getFacultadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		
		storeEspecialidad=new Ext.data.Store({
		    fields:[
				'codigo',
		        'textoNombreEspanol',
		        'textoNombreDenominacion',
		        'textoNombreBachiller'
			],
			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'facultades/getEspecialidadList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        },
		        extraParams:{
		        	"tipoEspecialidad.codigoExterno":'PRE'
		        }
		    }
		});
		
		storeModalidadGradoTitulo=new Ext.data.Store({
		    fields:[
				'codigo',
		        'textoNombre'
			],
			//autoLoad:true,
			proxy: {
		        type: 'ajax',
		        url: 'gradoTitulo/getModalidadGradoTituloList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		/*,
		        extraParams:{
		        	"gradoAcademico.codigoExterno":'BACH'
		        }
	     */   
		    }
		});
		storeModalidadEstudio=new Ext.data.Store({
		    fields:[
				'codigo',
		        'textoNombre'
			],
			autoLoad:true,
			proxy: {
		        type: 'ajax',
		        url: 'gradoTitulo/getModalidadEstudioList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
		});
		
		storeTipoDocumento=new Ext.data.Store({
		    fields:[
				'codigo',
		        'textoNombre'
			],
			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'datoGeneral/getTipoDocumentoList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
		});
		storeAgendaGrupo=new Ext.data.Store({
		    fields:[
	            'codigo',
	            'textoNombre'
			],
			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'agendaGrupo/getAgendaGrupoList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
		});
		storeTipoDiploma = Ext.create('Ext.data.Store', {
			fields:[
		            'codigo',
		            'codigoExterno',
		            'textoNombre'
				],
				autoLoad:true,
			    proxy: {
			        type: 'ajax',
			        url: 'diplomado/getTipoDiplomadoList',
			        reader: {
			            type: 'json',
			            root: 'data',
			            idProperty : 'codigo',
			            totalProperty :'totalCount'
			        }
			    }
			});	
		var documentoIdentidadFindField=new Ext.form.field.Text({
	        fieldLabel: 'N° Documento Identidad',
	        name: 'estudiante.textoNumeroDocumento',
	        labelWidth: 150,
	        anchor: '100%',
	        readOnly :true,
	        allowBlank: false
        });
		var estudianteField=new Ext.form.field.Text({
	        name: 'estudiante.codigo',
        });	
		var panelDocumentoIdentidad=new Ext.form.Panel({
			items:[
			       documentoIdentidadFindField,
			       estudianteField.hide()
			]
		})
		var btnView=new Ext.button.Button({
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){
				function loadListParam(form){
					storeAlumno.load({
						params: {
							textoNumeroDocumento:form.textoDocumento,
							textoNombreCompleto:form.textoNombreCompleto
						}
					});	
				}	
				var cancelarButton = new Ext.Button({
					width : 80,
					text : 'Cancelar',
					handler :function() {
						win.close();
						
					}
				});
				var findButton = new Ext.Button({
					width : 80,
					text : 'Buscar',
					handler :function() {
						var form={
								textoDocumento:documentoIdentidadField.getValue(),
								textoNombreCompleto:nombreField.getValue()
							}	
						loadListParam(form);		
					}
				});
				var addButton = new Ext.Button({
					width : 80,
					text : 'Agregar',
			        handler: function() {
			        	if(textoDocumentoIdentidad){
			        		console.info('selecciono '+textoDocumentoIdentidad);
			        		documentoIdentidadFindField.setValue(textoDocumentoIdentidad);
			        		estudianteField.setValue(codigo);
			        		win.close();
			        	}else{
			        		alert('Seleccione un registro con N° de Documento')	
			        	}
			        }
				});
				var documentoIdentidadField=new Ext.form.field.Text({
			        fieldLabel: 'N° Documento',
			        name: 'textoNumeroDocumento',
			        labelWidth: 150,
			        anchor: '100%'
		        });
				var nombreField=new Ext.form.field.Text({
			        fieldLabel: 'Nombre',
			        name: 'nombre',
			        labelWidth: 150,
			        anchor: '100%'
		        });
				var panelField=new Ext.form.Panel({
					height: 100,
					width : 600,
					bodyPadding: 5,
					items:[
						documentoIdentidadField,
						nombreField
					],
					buttons:[findButton]
				})
				var textoDocumentoIdentidad=null;
				var codigo=null;
				var grid=new Ext.grid.Panel({
				    store: storeAlumno,
				    xtype: 'grouped-header-grid',
				    columns: [
						{ text: 'codigo',  dataIndex: 'codigo',flex:1 },
						{ text: 'N° Documento',  dataIndex: 'textoDocumento',flex:1 },
				        { text: 'Nombre',  dataIndex: 'textoNombreCompleto',flex:1 },
				        { text: 'Facultad',  dataIndex: 'facultad',flex:1 },
				        { text: 'Especialidad',  dataIndex: 'especialidad',flex:1 }
				    ],
				    style: {borderColor: '#157fcc'},
				    listeners :{
						select:function(dv, record, index, eOpts ){
							console.log('selecccionado la N° de documento'+record.get('textoDocumento'));
							textoDocumentoIdentidad=record.get('textoDocumento');
							codigo=record.get('codigo');
						}
					}
				    
				});
				
				var panelLoader = new Ext.panel.Panel({
					width: 600,
				    height: 350,
					layout:{
						type:'border'
					},
					items:[
						{
							region: 'north', height:100,width:'100%',border: true,items : [panelField]
						},
						{
							region: 'center',border: true,items : [grid]
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
				/****************************/
				win.show()
			}
		})
		var panelView=new Ext.form.Panel({
			padding : '0 0 0 10',
			items:[btnView]
		})
		
		var panelEstudiante = new Ext.panel.Panel({
			width: 450,
			layout:{
				type:'hbox',
				align:'stretch'
			},
			items:[
				{
			     	flex: 92,
			     	items:[panelDocumentoIdentidad]
			    },
			    {
			     	flex: 8,
			     	items:[panelView]
			    },
			]
		});
		var tipoDiplomadoField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Tipo Diplomado',
	        name: "tipoDiplomado.codigo",
			store: storeTipoDiploma,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
			allowBlank: false,
			editable:false
		});
		var textoNombreDiplomadoField=new Ext.form.field.Text({
	        fieldLabel: 'Nombre del Diplomado',
	        name: 'textoNombreDiplomado',
	        allowBlank: false
        });
		var fechaInicioField=new Ext.form.field.Date({
	        fieldLabel: 'Fecha de Inicio',
	        name: 'fechaInicio',
	        format : "d/m/Y",
	        submitFormat :"Y/m/d",
	        allowBlank: false
		});
		var fechaFinalField=new Ext.form.field.Date({
	        fieldLabel: 'Fecha Final',
	        name: 'fechaFinal',
	        format : "d/m/Y",
	        submitFormat :"Y/m/d",
	        allowBlank: false
		});
		var horaLectivasField=new Ext.form.field.Number({
			name: 'numeroHorasLectivas',
	        fieldLabel: 'Horas Lectivas',
	        minValue: 0
        });
		var horaTeoricpPracticasField=new Ext.form.field.Number({
			name: 'numeroHorasTeoricoPtc',
	        fieldLabel: 'Horas T/P',
	        minValue: 0
        });
		var promedioFinalField=new Ext.form.field.Number({
			name: 'numeroPromedioFinal',
	        fieldLabel: 'Promedio Final',
	        minValue: 0
        });
		var textoResolucionFacultadField=new Ext.form.field.Text({
	        fieldLabel: 'Resolucion Facultad',
	        name: 'textoResolucionFacultad',
	        allowBlank: false
        });
		var fechaResolucionFacultadField=new Ext.form.field.Date({
	        fieldLabel: 'F/D Resolucion Facultad',
	        name: 'fechaResolucionFatultad',
	        format : "d/m/Y",
	        submitFormat :"Y/m/d",
	        allowBlank: false
		});
		var textoRegistroDiplomadoFacultadField=new Ext.form.field.Text({
	        fieldLabel: 'Registro Diplomado FAC',
	        name: 'textoRegistroDiplomadoFacultad',
	        allowBlank: false
        });
		var fechaAprobacionCUField=new Ext.form.field.Date({
	        fieldLabel: 'F/D Aprobación CU',
	        name: 'fechaAprobacionCu',
	        format : "d/m/Y",
	        submitFormat :"Y/m/d",
	        allowBlank: false
		});
		var textoResoluinCUField=new Ext.form.field.Text({
	        fieldLabel: 'Resolucion del CU',
	        name: 'textoResolucionRectoral',
	        allowBlank: false
        });
		var decanoField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Decano',
	        name: "autoridadRegistroDecano.codigo",
			store: storeAutoridadDecano,
	        queryMode: 'local',
	        displayField: 'textoNombreAutoridad',
	        valueField: 'codigo',
			allowBlank: false,
			editable:false
		});
		var rectorField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Rector',
	        name: "autoridadRegistroRector.codigo",
			store: storeAutoridadRector,
	        queryMode: 'local',
	        displayField: 'textoNombreAutoridad',
	        valueField: 'codigo',
			allowBlank: false,
			editable:false,
		});
		var especialidadField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Especialidad',
	        name: "especialidad.codigo",
			store: storeEspecialidad,
	        queryMode: 'local',
	        displayField: 'textoNombreEspanol',
	        valueField: 'codigo',
			listConfig : {
				listeners : {
					itemclick : function(list,record) {
						decanoField.clearValue();
						storeAutoridadDecano.load({
							params: {
								"cargo.especialidad":record.get('codigo')
							}
						});
						
					}
				}
								
			},
			allowBlank: false,
			editable:false
		});
		var folioField=new Ext.form.field.Number({
			name: 'numeroFolio',
	        fieldLabel: 'N° Folio',
	        minValue: 0
        });
		var libroField=new Ext.form.field.Number({
			name: 'numeroLibro',
	        fieldLabel: 'N° Libro',
	        minValue: 0
        });
		var agendaGrupoField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Agenda',
	        name: "agendaGrupo.codigo",
			store: storeAgendaGrupo,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
			allowBlank: false,
			editable:false
		});
		var codigoDiplomadoField=new Ext.form.field.Text({
	        name: "codigo"
        });
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
    		fieldDefaults: {
    	        width : 450,
    	        labelWidth : 150
    	    },
			items:[
				codigoDiplomadoField.hide(),
				panelEstudiante,
		     	tipoDiplomadoField,
		     	textoNombreDiplomadoField,
		     	fechaInicioField,
		     	fechaFinalField,
		     	horaLectivasField,
		     	horaTeoricpPracticasField,
		     	promedioFinalField,
		     	textoResolucionFacultadField,
		     	fechaResolucionFacultadField,
		     	textoRegistroDiplomadoFacultadField,
		     	fechaAprobacionCUField,
		     	textoResoluinCUField,
		     	especialidadField,
		     	decanoField,
		     	rectorField,
		     	folioField,
		     	libroField,
		     	agendaGrupoField
		     	
			],
			buttons:[cancelarButton,saveButton]
			
			
		});	
		/*
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
			fieldDefaults: {
    	        width : 450,
    	        labelWidth : 150
    	    },
			width: 950,
		    height: 850,
		    layout:'column',
		    items: [{
		       // title: 'Column 1',
		        columnWidth: 1,
		        items:[
					tipoDiplomaLabel,
					panelLoader
				]
		    }],
			buttons:[cancelarButton,saveButton]
		});
		*/
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[
				agregarPanel
			],
			listeners :{
				afterrender:function(){
					if(uxData&&uxData.action=='edit'){
						agregarPanel.load({
							url:'diplomado/getDiplomado',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.Diplomado.Load.GradoTituloWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Diplomado.Load.GradoTituloWindowsMantenimiento,Ext.window.Window,{});
	
	APP.Portal.Diplomado.Load.GradoTituloWindowsClose=function(config){
		
		var me=this,
		uxData = config.uxData;
	
		var agregarPanel,
			saveButton, 
			cancelarButton,
			storeSexo,
			storeFacultad,
			storeEspecialidad,
			storeTipoDocumento,
			grid;
		
		cancelarButton = new Ext.Button({
			width : 80,
			text : 'Cancelar',
			handler :function() {
				me.close();
				
			}
		});
		
		saveButton = new Ext.Button({
			width : 80,
			text : 'Cerrar',
	        handler: function() {
	        	var s = grid.getSelectionModel().getSelection();
	   		    var groups=[];
	        	Ext.each(s, function (item) {
	   			    console.log(item.data.codigo);
	   				groups.push(item.data.codigo)
	   		    });
	        	
	        	if(!isEmpty(fechaCierreField.getSubmitValue())&&groups.length>0){
		        	var groupJson=Ext.encode(groups); 
		        	Ext.Ajax.request({
		                url: 'diplomado/close',
		                method: 'POST',
		                params : {
		                	groupList : groupJson,
		                	fechaCierre:fechaCierreField.getSubmitValue()
		                },
		                success: function (response, options) {
		                	 Ext.MessageBox.show({
		                            title: 'Alerta'
		                            , msg: 'Cerrado los registros'
		                            , width: 300
		                            , buttons: Ext.Msg.OK
		                        });
		                	 var globalPanel=APP.Portal.Diplomado.Load.GlobalPanel;
		                	 globalPanel.getGradoTituloPanel().getListPanel().loadList();	
		                	 me.close();
	
		                },
		                failure: function (response, options) {
		                    Ext.MessageBox.show({
		                        title: 'Error'
		                        , msg:  'Fallo'
		                        , width: 300
		                        , buttons: Ext.Msg.OK
		                    });
		                }
		            });
	        	}else{
	        		console.log('ingreso al else')
	        		if(isEmpty(fechaCierreField.getSubmitValue())){
	        			Ext.MessageBox.show({
                            title: 'Alerta'
                            , msg: 'Ingese una fecha'
                            , width: 300
                            , buttons: Ext.Msg.OK
                        });
	        			
	        		}else if(groups.length==0){
	        			Ext.MessageBox.show({
                            title: 'Alerta'
                            , msg: 'Seleccione minimo una Agenda'
                            , width: 300
                            , buttons: Ext.Msg.OK
                        });
	        		}
	        	}
	        	
	        }
		});
		
		
		store=new Ext.data.Store({
		    fields:[
	            'codigo',
	            'textoNombre'
			],
			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'agendaGrupo/getAgendaGrupoList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
		});
		var sm = new Ext.selection.CheckboxModel({
		    checkOnly: true
		});
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    selModel: sm,
		    columns: [		       
				//{ text: 'Codigo',  dataIndex: 'codigo',flex:1 },
				{ text: 'Nombre',  dataIndex: 'textoNombre',flex:1 }
		    ],
		    style: {borderColor: '#157fcc'},
		});
		var fechaCierreField=new Ext.form.field.Date({
	        fieldLabel: 'Fecha de Cierre ',
	        name: 'fechaCierre',
	        format : "d/m/Y",
	        submitFormat :"Y/m/d",
	        allowBlank: false
        });
		var panelField=new Ext.form.Panel({
			height: 100,
			width : 600,
			bodyPadding: 5,
			items:[fechaCierreField]
		})
		/*
		var panelLoader=new Ext.panel.Panel({
			title:'contenedor',
			border:true,
			height: 250,
			width : 450,
			layout : 'fit',
			items : [grid]
		})
		*/
		var panelLoader = new Ext.panel.Panel({
			width: 450,
		    height: 250,
			layout:{
				type:'border'
			},
			items:[
				{
					region: 'north', height:50,width:'100%',border: true,items : [panelField]
				},
				{
					region: 'center',border: true,items : [grid]
				}
			]
		})
		/*
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[panelLoader],
			buttons:[cancelarButton,saveButton],
			listeners :{
				afterrender:function(){
					if(uxData&&uxData.action=='edit'){
						agregarPanel.load({
							url:'facultades/getFacultad',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		*/
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[panelLoader],
			buttons:[cancelarButton,saveButton]
		});
		APP.Portal.Diplomado.Load.GradoTituloWindowsClose.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Diplomado.Load.GradoTituloWindowsClose,Ext.window.Window,{});
	
	APP.Portal.Diplomado.Load.GradoTituloFiltro=function(config){
		var me=this;
		
		
	    	    
		Ext.apply(config,{
			width : 450,
			createPicker:function(){
				var me = this,
					storeTipoRegistro,
					storeTipoDocumento,
					storeAgendaGrupo,
					cancelarButton,
					findButton;
				
				cancelarButton = new Ext.Button({
					width : 80,
					text : 'Cancelar',
					handler :function() {
						pickerPanel.getForm().reset();
						me.collapse();
						
					}
				});
				
				
				findButton = new Ext.Button({
					width : 80,
					text : 'Buscar',
			        handler: function() {
						var form={
								matricula:matriculaField.getValue(),
								nombreCompleto:nombreCompletoField.getValue(),
								codigoExternoDocumento:codigoExternoDocumentoField.getValue(),
								numeroDocumento:numeroDocumentoField.getValue(),
								folio:folioField.getValue(),
								libro:libroField.getValue(),
								registro:registroField.getValue(),
								fechaInicioCu:fechaInicioCuField.getSubmitValue(),
								fechaFinalCu:fechaFinalCuField.getSubmitValue(),
								tipoRegistro:tipoRegistroField.getValue(),		        			
								agendaGrupo:agendaGrupoField.getValue()		        			
						},
						findValue=[];
						var globalPanel=APP.Portal.Diplomado.Load.GlobalPanel;
						
						//valores para excel
						globalPanel.getGradoTituloPanel().getListPanel().getToolbar().postDataFind(form);
						globalPanel.getGradoTituloPanel().getListPanel().loadListParam(form);

						if(!isEmpty(form.matricula)){
							findValue.push('Matricula:'+posicionValue(form.matricula));
						}
						if(!isEmpty(form.nombreCompleto)){
							findValue.push('Nombre: '+posicionValue(form.nombreCompleto));
						}
						if(!isEmpty(form.codigoExternoDocumento)){
							findValue.push('Documento de Identidad: '+form.codigoExternoDocumento);
						}
						
						if(!isEmpty(form.numeroDocumentoFieldLabel)){
							findValue.push('Numero de Documento: '+posicionValue(form.numeroDocumentoFieldLabel));
						}
						if(!isEmpty(form.folio)){
							findValue.push('N° Folio: '+form.folio);
						}
						if(!isEmpty(form.textoNombreEspanolEspecialidad)){
							findValue.push('N° Libro: '+form.libro);
						}
						if(!isEmpty(form.textoNumeroResolucion)){
							findValue.push('N° Registro: '+form.registro);
						}
						if(!isEmpty(form.tipoRegistro)){
							findValue.push('Tipo de Registro: '+form.tipoRegistro);
						}
						if(!isEmpty(form.agendaGrupo)){
							findValue.push('Agenda: '+form.agendaGrupo);
						}
						me.setRawValue(findValue);
						pickerPanel.getForm().reset();
						me.collapse();
			        }
				});
								  
				
				
				storeTipoRegistro=new Ext.data.Store({
					fields:[
						'codigo',
						'textoNombre'
					],
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: 'gradoTitulo/getGradoAcademicoList',
						reader: {
							type: 'json',
							root: 'data',
							idProperty : 'codigo',
							totalProperty :'totalCount'
						}
					}
				});
				
				storeTipoDocumento=new Ext.data.Store({
				    fields:[
						'codigo',
				        'textoNombre'
					],
					autoLoad:true,
				    proxy: {
				        type: 'ajax',
				        url: 'datoGeneral/getTipoDocumentoList',
				        reader: {
				            type: 'json',
				            root: 'data',
				            idProperty : 'codigo',
				            totalProperty :'totalCount'
				        }
				    }
				});
				storeAgendaGrupo=new Ext.data.Store({
				    fields:[
			            'codigo',
			            'textoNombre'
					],
					autoLoad:true,
				    proxy: {
				        type: 'ajax',
				        url: 'agendaGrupo/getAgendaGrupoList',
				        reader: {
				            type: 'json',
				            root: 'data',
				            idProperty : 'codigo',
				            totalProperty :'totalCount'
				        }
				    }
				});
				var matriculaField=new Ext.form.field.Text({
			        fieldLabel: 'Matricula',
			        name: 'estudiante.textoMatricula'
		        });	 
		        
		        var nombreCompletoField=new Ext.form.field.Text({
			        fieldLabel: 'Nombre',
			        name: 'estudiante.textoNombreCompleto'
			       
		        });
	
		        var codigoExternoDocumentoField=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Documento de Identidad',
			        name: 'estudiante.textoCodigoExternoDocumento',
					store: storeTipoDocumento,
			        queryMode: 'local',
			        displayField: 'textoNombre',
			        valueField: 'textoNombre',
					editable:false
		        });
		        var numeroDocumentoField=new Ext.form.field.Text({
			        fieldLabel: 'Numero de Documento',
			        name: 'estudiante.textoNumeroDocumento',
			        
		        });
		        var folioField=new Ext.form.field.Number({
					name: 'numeroFolio',
			        fieldLabel: 'N° Folio',
			        minValue: 0
		        });
		        var libroField=new Ext.form.field.Number({
					name: 'numeroLibro',
			        fieldLabel: 'N° Libro',
			        minValue: 0
		        }); 
		        var registroField=new Ext.form.field.Number({
					name: 'numeroRegistro',
			        fieldLabel: 'N° Registro',
			        minValue: 0
		        });
		        var fechaInicioCuField=new Ext.form.field.Date({
			        fieldLabel: 'Fecha Inicio CU',
			        name: 'fechaCierreInicial',
			        //format : "Y/m/d"
			        format : "d/m/Y",
			        submitFormat :"Y/m/d",
				});
			    var fechaFinalCuField=new Ext.form.field.Date({
			        fieldLabel: 'Fecha Final CU',
			        name: 'fechaCierreFinal',
			        //format : "Y/m/d"
			        format : "d/m/Y",
			        submitFormat :"Y/m/d",
				});
			    var tipoRegistroField=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Tipo de Registro',
			        name: 'gradoAcademico.textoNombre',
					store: storeTipoRegistro,
			        queryMode: 'local',
			        displayField: 'textoNombre',
			        valueField: 'textoNombre',
					editable:false
		        });
			    var agendaGrupoField=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Agenda',
			        name: 'agendaGrupo.textoNombre',
					store: storeAgendaGrupo,
			        queryMode: 'local',
			        displayField: 'textoNombre',
			        valueField: 'textoNombre',
					editable:false
		        });
			  
				me.matricula=function(val){
					return matriculaField.setValue(val)
				}; 
		        
		        me.nombreCompleto=function(val){
		        	return nombreCompletoField.setValue(val);
		        }
			   
		        me.codigoExternoDocumento=function(val){
		        	return codigoExternoDocumentoField.setValue(val);
		        }
		        me.numeroDocumento=function(val){
		        	return numeroDocumentoField.setValue(val);
		        }
		        me.folio=function(val){
		        	return folioField.setValue(val);
		        }
		        me.libro=function(val){
		        	return libroField.setValue(val); 
		        }
		        me.registro=function(val){
		        	return registroField.setValue(val);
		        }
		        me.fechaInicioCu=function(val){
		       		return fechaInicioCuField.setValue(val);
		        }
		        me.fechaFinalCu=function(val){
		        	return fechaFinalCuField.setValue(val);
		        }
		        me.tipoRegistro=function(val){
		        	return tipoRegistroField.setValue(val);
		        }
		        me.agendaGrupo=function(val){
		        	return agendaGrupoField.setValue(val);
		        }
		        
		        me.getStoreTipoRegistro=function(){
					return storeTipoRegistro;
				}
		        me.getstoreTipoDocumento=function(){
					return storeTipoDocumento;
				}
		        me.getStoreAgendaGrupo=function(){
					return storeAgendaGrupo;
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
						matriculaField,
						nombreCompletoField,
						codigoExternoDocumentoField,
						numeroDocumentoField,
						folioField,
						libroField,
						registroField,
						fechaInicioCuField,
						fechaFinalCuField,
						//tipoRegistroField,
						agendaGrupoField
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
			            //var regularExpresion= /,(?=[^\)]*[\(]|[^\)]*$)/;
			        	var nameField= [
			        	                'Matricula',
			        	                'Nombre',
			        	                'Documento de Identidad',
			        	                'Numero de Documento',
			        	                'N° Folio',
			        	                'N° Libro',
			        	                'N° Registro',
			        	                'Tipo de Registro',
			        	                'Agenda'
			        	],
			        	form={
			        
				        	matricula:'Matricula',
				        	nombreCompleto:'Nombre',
				        	codigoExternoDocumento:'Documento de Identidad',
				        	numeroDocumento:'Numero de Documento',
				        	folio:'N° Folio',
				        	libro:'N° Libro',
				        	registro:'N° Registro',
				        	tipoRegistro:'Tipo de Registro',
				        	agendaGrupo:'Agenda'
				        			
				        },
				       	textRaw=me.getRawValue()
			        	divider(form,nameField,textRaw);
			        	
			        	
						
						var globalPanel=APP.Portal.Diplomado.Load.GlobalPanel,
						textoFind,
						value,
						getIndice =globalPanel.getGradoTituloPanel().getListPanel().getToolbar().getFiltroField();

						if(!isEmpty(form.codigoExternoDocumento != null)){
							textoFind=((form.codigoExternoDocumento).toUpperCase()).trim();
							value= getCatchValue(getIndice.getstoreTipoDocumento(),textoFind,'textoNombre');
							getIndice.codigoExternoDocumento(value);	
							form.codigoExternoDocumento=value;
						}else{
							getIndice.codigoExternoDocumento(form.codigoExternoDocumento)
						}
						
						if(!isEmpty(form.agendaGrupo != null)){
							textoFind=((form.agendaGrupo).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreAgendaGrupo(),textoFind,'textoNombre');
							getIndice.agendaGrupo(value);	
							form.agendaGrupo=value;
						}else{
							getIndice.agendaGrupo(form.agendaGrupo)
						}
						if(!isEmpty(form.tipoRegistro)){
							textoFind=((form.tipoRegistro).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreTipoRegistro(),textoFind,'textoNombre');
							getIndice.tipoRegistro(value);	
							form.tipoRegistro=value;
						}else{
							getIndice.tipoRegistro(form.tipoRegistro)
						}
						getIndice.matricula(form.matricula);
						getIndice.nombreCompleto(form.nombreCompleto);
						getIndice.numeroDocumento(form.numeroDocumento);
						getIndice.folio(form.folio);
						getIndice.libro(form.libro);
						getIndice.registro(form.registro);
				
						console.info({form:form});
						globalPanel.getGradoTituloPanel().getListPanel().getToolbar().postDataFind(form);
						globalPanel.getGradoTituloPanel().getListPanel().loadListParam(form);
						console.log('Presiono Enter');
			       	//}
		       	}
		       
			}

		});
		APP.Portal.Diplomado.Load.GradoTituloFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Diplomado.Load.GradoTituloFiltro,APP.form.field.Picker,{});
	
	APP.Portal.Diplomado.Load.GradoTituloToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.Diplomado.Load.GradoTituloFiltro({
			
		});
		var mantenimiento = new APP.Portal.Diplomado.Load.GradoTituloWindowsMantenimiento({
			
		});
		me.getFiltroField=function(form){
			return filtroField;
		}
		me.getWinMantenimiento=function(){
			return mantenimiento;
		}
		
		var formExcel=[];
		me.postDataFind=function(form){
			formExcel=form;
			var fechaFormat='1000/01/01';
			if(isEmpty(formExcel.fechaInicioCu)){
				formExcel.fechaInicioCu=fechaFormat;
			}
			if(isEmpty(formExcel.fechaFinalCu)){
				formExcel.fechaFinalCu=fechaFormat;
			}
			if(isEmpty(formExcel.folio)){
				formExcel.folio="";
			}
			if(isEmpty(formExcel.libro)){
				formExcel.libro="";
			}
			if(isEmpty(formExcel.registro)){
				formExcel.registro="";
			}
			
			
		}
		Ext.apply(config,{
			items : [
				filtroField,
				{
					text : 'Cerrar Registros',
					height : 30,
					cls : 'btn',
					handler:function(){
						
						var win = new APP.Portal.Diplomado.Load.GradoTituloWindowsClose({
							title: 'Seleccionar'
						});
						win.show();
					}
				},
				'->',
				{
					text : 'Reporte de busqueda',
					height : 30,
					cls : 'btn',
					handler:function(){
						console.log("formExcel "+formExcel);
						console.log("formExcel.toSource() "+formExcel.toSource());
						if(formExcel != ''){
							window.open(
								"diplomado/excel?estudiante.textoMatricula="+formExcel.matricula
								+"&estudiante.textoNombreCompleto"+"="+formExcel.nombreCompleto
								+"&estudiante.textoNumeroDocumento"+"="+formExcel.numeroDocumento
								+"&numeroFolio="+formExcel.folio
								+"&numeroLibro="+formExcel.libro
								+"&numeroRegistro="+formExcel.registro
								+"&fechaCierreInicial="+formExcel.fechaInicioCu
								+"&fechaCierreFinal="+formExcel.fechaFinalCu
								+"&gradoAcademico.textoNombre"+"="+formExcel.tipoRegistro
								+"&agendaGrupo.textoNombre"+"="+formExcel.agendaGrupo
								/*
								+"&estudiante.textoCodigoExternoDocumento"+"="+formExcel.codigoExternoDocumento
								+"&estudiante.textoNumeroDocumento"+"="+formExcel.numeroDocumento
								+"&numeroFolio="+formExcel.folio
								+"&numeroLibro="+formExcel.libro
								+"&numeroRegistro="+formExcel.registro
								+"&fechaCierreInicial="+formExcel.fechaInicioCu
								+"&fechaCierreFinal="+formExcel.fechaFinalCu
								+"&gradoAcademico.textoNombre"+"="+formExcel.tipoRegistro
								+"&agendaGrupo.textoNombre"+"="+formExcel.agendaGrupo
								*/
								/*
								"&"+"'estudiante.textoMatricula'"+"="+formExcel.matricula+
								"&"+"'estudiante.textoNombreCompleto'"+"="+formExcel.nombreCompleto+
								"&"+"'estudiante.textoCodigoExternoDocumento'"+"="+formExcel.codigoExternoDocumento+
								"&"+"'estudiante.textoNumeroDocumento'"+"="+formExcel.numeroDocumento+
								"&numeroFolio="+formExcel.folio+
								"&numeroLibro="+formExcel.libro+
								"&numeroRegistro="+formExcel.registro+
								"&fechaCierreInicial="+formExcel.fechaInicioCu+
								"&fechaCierreFinal="+formExcel.fechaFinalCu+
								"&"+"'gradoAcademico.textoNombre'"+"="+formExcel.tipoRegistro+
								"&"+"'agendaGrupo.textoNombre'"+"="+formExcel.agendaGrupo	
								*/
							);
						}
					}
				},
				{
					text : 'Agregar Registro',
					height : 30,
					cls : 'btn',
					handler:function(){
						var win = new APP.Portal.Diplomado.Load.GradoTituloWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.Diplomado.Load.GradoTituloToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Diplomado.Load.GradoTituloToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.Diplomado.Load.GradoTituloList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
			
		toolbar=new APP.Portal.Diplomado.Load.GradoTituloToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
		
		me.loadListParam=function(form){
			
			var fechaFormat='1000/01/01';
			console.log('form.fechaInicioCu '+form.fechaInicioCu);
			console.log('form.fechaFinalCu '+form.fechaFinalCu);
			
			if(isEmpty(form.fechaInicioCu)){
				form.fechaInicioCu=fechaFormat;
			}
			if(isEmpty(form.fechaFinalCu)){
				form.fechaFinalCu=fechaFormat;
			}
			
			store.load({
				params: {
					"estudiante.textoMatricula":form.matricula,
					"estudiante.textoNombreCompleto":form.nombreCompleto,
					"estudiante.textoCodigoExternoDocumento":form.codigoExternoDocumento,
					"estudiante.textoNumeroDocumento":form.numeroDocumento,
					"numeroFolio":form.folio,
					"numeroLibro":form.libro,
					"numeroRegistro":form.registro,
					"fechaCierreInicial":form.fechaInicioCu,
					"fechaCierreFinal":form.fechaFinalCu,
					"gradoAcademico.textoNombre":form.tipoRegistro,
					"agendaGrupo.textoNombre":form.agendaGrupo,
					"flagCandado":"B"
				}
			});
		}
		
		me.loadList=function(){
				store.load();
		}
		
		function estado(v, record){
		   	var result;
			if(record.data.flagEstado=="A"){
		   		result= "Activo";
		   	}else if(record.data.flagEstado=="D"){
		   		result="Desactivo";
		   	}
			return result;
		}
		
		function eliminar(record){
			var codigo=record.get('codigo');
			console.info({action:'delete',codigo:codigo})
			Ext.Ajax.request({
				url : 'diplomado/delete',
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
		function enviarSunedu(record){
			var codigo=record.get('codigo');
			console.info({action:'enviarSunedu',codigo:codigo})
			Ext.Ajax.request({
				url : 'diplomado/enviarSunedu',
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
			var tipo=record.get('abreviaturaGradoTitulo');
			var win = new APP.Portal.Diplomado.Load.GradoTituloWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					tipo:tipo,
					action:'edit'
				}
			});
			win.show();
			
		}
		
		store=new Ext.data.Store({
		    fields:[        
        		'codigo',
        		'matricula',
        		'apellidoPaterno',
        		'apellidoMaterno',
        		'nombre',
        		'documentoTipo',
        		'documentoNumero',
        		'sexo',
        		'tipoDiplomado',
        		'fechaInicioDiplomado',
        		'fechaFinalDiplomado',
        		'numeroHorasLectivas',
        		'numeroHorasTeoricoPractico',
        		'numeroPromedioFinal',
        		'fechaResolucionFacultad',
        		'resolucionFacultad',
        		'resolucionDiplomaFacultad',
        		'cargoTres',
        		'autoridadTres',
        		'cargoUno',
        		'autoridadUno',
        		'resolucionNumero',
        		'registroRegistro',
        		'registroFolio',
        		'fechaCU',
        		'registroLibro',
        		'facultadNombre',
        		'especialidad',
        		'enviadoSunedu',
        		'agendaGrupo'
			],
			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'diplomado/getDiplomadoList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
		});	
		/*
		var sm = new Ext.selection.CheckboxModel({
		    checkOnly: true
		});
		*/
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    //selModel: sm,
		    columns: [
		        /*      
				{ text: 'Activo', 
					dataIndex: 'active',
					flex: 1,
					xtype: 'checkcolumn',
					listeners: {
				         'checkchange':function(checkbox, rowIndex, checked, eOpts) {
				        	
				        	 console.log('rowIndex '+rowIndex);
				         }
				     }
				},
				*/
				{ text: 'codigo',  dataIndex: 'codigo',flex:1},
				{ text: 'Apellido Paterno',  dataIndex: 'apellidoPaterno',flex:1 },
				{ text: 'Apellido Materno',  dataIndex: 'apellidoMaterno',flex:1 },
				{ text: 'Nombre',  dataIndex: 'nombre',flex:1 },
				{ text: 'Documento Tipo',  dataIndex: 'documentoTipo',flex:1, hidden: true},
				{ text: 'Documento Numero',  dataIndex: 'documentoNumero',flex:1, hidden: true},
				{ text: 'Sexo',  dataIndex: 'sexo',flex:1, hidden: true },
				{ text: 'Tipo Diplomado',  dataIndex: 'tipoDiplomado',flex:1},
				{ text: 'Fecha Inicio',  dataIndex: 'fechaInicioDiplomado',flex:1},
				{ text: 'Fecha Final.',  dataIndex: 'fechaFinalDiplomado',flex:1},
				{ text: 'N° Horas Lectivas',  dataIndex: 'numeroHorasLectivas',flex:1},
				{ text: 'N° Horas T/P',  dataIndex: 'numeroHorasTeoricoPractico',flex:1},
				{ text: 'N° Prom Final',  dataIndex: 'numeroPromedioFinal',flex:1},
				{ text: 'F/D RES FAC',  dataIndex: 'fechaResolucionFacultad',flex:1},
				{ text: 'RES FAC',  dataIndex: 'resolucionFacultad',flex:1},
				{ text: 'RES Diploma Fac',  dataIndex: 'resolucionDiplomaFacultad',flex:1},
				{ text: 'Decano',  dataIndex: 'autoridadTres',flex:1},
				{ text: 'Rector',  dataIndex: 'autoridadUno',flex:1},	
				{ text: 'N° Registro',  dataIndex: 'registroRegistro',flex:1},
				{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1},
				{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
				{ text: 'F/D RES CU',  dataIndex: 'fechaCU',flex:1, hidden: true},
				{ text: 'N° Folio',  dataIndex: 'registroFolio',flex:1, hidden: true},
				{ text: 'N° Libro',  dataIndex: 'registroLibro',flex:1, hidden: true},
				{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
				{ text: 'especialidad',  dataIndex: 'especialidad',flex:1 },
				{ text: 'Enviado SUNEDU',  dataIndex: 'enviadoSunedu',flex:1, hidden: true},
				{ text: 'GrupoAgenda',  dataIndex: 'agendaGrupo',flex:1, hidden: true},
				//{ text: 'registroOficio',  dataIndex: 'registroOficio',flex:1, hidden: true},
				//{ text: 'Tipo Documento',  dataIndex: 'gradoAcademico',flex:1, hidden: true}
				
		    ],
		    //border:true,
		    style: {borderColor: '#157fcc'},
		    listeners:{
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
		    					text:'Eliminar',
		    					handler: function(){
		    						//confirm messagebox
    		                        Ext.Msg.confirm("Confimacion", "¿Desea eliminar el registro?", function(btnText){
    		                            if(btnText === "yes"){
    		                                Ext.Msg.alert("Alerta", "A confirmado  'Si'.");
    		                                eliminar(record);
    		                            }
    		                            else if(btnText === "no"){
    		                                
    		                            }
    		                        }, this);
	    						}
	    					},
	    					{
		    					text:'Enviar SUNEDU',
		    					handler: function(){
		    						//confirm messagebox
    		                        Ext.Msg.confirm("Confimacion", "¿Desea enviar a la Sunedu?", function(btnText){
    		                            if(btnText === "yes"){
    		                               
    		                                enviarSunedu(record);
    		                            }
    		                            else if(btnText === "no"){
    		                                
    		                            }
    		                        }, this);
	    						}
	    					}
	    					/*,
	    					{
		    					text:'Reporte',
		    					handler: function(){
		    						window.open(
		    								"revalida/exportDiploma?codigo="+record.get('codigo')
		    								
		    						)
	    					
	    						}
	    					}
	    					*/
						]
		    		});
		    		var position = e.getXY();
                    e.stopEvent();
                    menu.showAt(position);
		    		 
		    	},
		    	select:function(dv, record, index, eOpts ){
		    		
					console.log('selecccionado la fila '+record.get('codigo'));
					/*
					var form={datoGeneral:record.get('codigo')};
                	var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel;
					globalPanel.getPeriodoPanel().getListPanel().loadListParam(form);
					*/
					
				}
		    }
		    
		});
		
		Ext.apply(config,{
			dockedItems : [toolbar],
			items : [grid],
			layout : 'fit'
		});
		APP.Portal.Diplomado.Load.GradoTituloList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Diplomado.Load.GradoTituloList,Ext.panel.Panel,{});
	
	APP.Portal.Diplomado.Load.GradoTituloPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Diplomado.Load.GradoTituloList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'GradoTitulo',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Diplomado.Load.GradoTituloPanel.superclass.constructor.call(this,config);
	}	
	Ext.extend(APP.Portal.Diplomado.Load.GradoTituloList,Ext.panel.Panel,{});
	
	APP.Portal.Diplomado.Load.GradoTituloPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Diplomado.Load.GradoTituloList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'GradoTitulo',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Diplomado.Load.GradoTituloPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Diplomado.Load.GradoTituloPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.Diplomado.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.Diplomado.Load.GradoTituloPanel({});
		
		me.getGradoTituloPanel=function(){
			return datoGeneralPanel;
		}
		
		var panelFullContainer = new Ext.panel.Panel({
			layout:{
				type:'hbox',
				align:'stretch'
			},
			items:[{
				 flex: 1, items:[datoGeneralPanel]
			}]
		})
		
		Ext.apply(config,{
			layout:'fit',
			items: [panelFullContainer]
		});
		
		APP.Portal.Diplomado.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Diplomado.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.Diplomado.Load.Container({});
		APP.Portal.Diplomado.Load.GlobalPanel=globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
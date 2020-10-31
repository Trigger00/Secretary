<script type="text/javascript">
	Ext.ns('APP.Portal.GradoTitulo.Load')
	APP.Portal.GradoTitulo.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		
		var agregarPanel,
			saveButton, 
			cancelarButton,
			storeAgendaGrupo,
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
			storeModalidadGradoTitulo,
			storeGradoAcademico;
		
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
	            var save = panelLoader.getForm();
	            var data=[];
	            save.getFields().each(function(field) {
	            	data.push({field:field,validate:field.validate()});
	            });
	            console.info({data:data})
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'gradoTitulo/saveEstudianteRegistro',
	                    success: function(form, action) {
	                    	var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel;
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
					url: 'gradoTitulo/getGradoAcademicoList',
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
			//autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'facultades/getEspecialidadList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
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
		        	"gradoAcademico.codigoExterno":'B'
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
		    fields: ['codigo', 'textoNombre'],
		    data : [
		        {"codigo":"B", "textoNombre":"Bachiller"},
		        {"codigo":"T", "textoNombre":"Titulo"},
		        {"codigo":"M", "textoNombre":"Maestria"},
		        {"codigo":"D", "textoNombre":"Doctorado"},

		    ]
		});
		
		var getUxFields=function(data){
			var items=[];
			Ext.each(data.items,function(item){
				if(item=='codigoRegistro'){
					var codigo=new Ext.form.field.Text({
				        name: "codigo"
			        });
					items.push(codigo.hide());
				}else if(item=='B'){
					var tipoRegistros=new Ext.form.field.Text({
				        name: "gradoAcademico.codigoExterno"
			        });
					tipoRegistros.setValue('B');
					items.push(tipoRegistros.hide())
				}else if(item=='T'){
					var tipoRegistros=new Ext.form.field.Text({
				        name: "gradoAcademico.codigoExterno"
			        });
					
					tipoRegistros.setValue('T');
					items.push(tipoRegistros.hide())
				}else if(item=='M'){
					storeGradoAcademico.load({
						params: {
							"codigoExterno":'M'
						}
					});
					
					var tipoRegistroField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Nombre de Registro',
				        name: "gradoAcademico.codigo",
						store: storeGradoAcademico,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					items.push(tipoRegistroField)
				}else if(item=='D'){
					storeGradoAcademico.load({
						params: {
							"codigoExterno":'D'
						}
					});
					
					var tipoRegistroField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Nombre de Registro',
				        name: "gradoAcademico.codigo",
						store: storeGradoAcademico,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					items.push(tipoRegistroField)
				}else if(item=='gradoBachiller'){
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
					var secretarioGeneralField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Secretario General',
				        name: "autoridadRegistroSecretarioGeneral.codigo",
						store: storeAutoridadSecretarioGeneral,
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					var gradoBachillerField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Grado Bachiller en',
				        name: "especialidad.codigo",
						store: storeEspecialidad,
				        queryMode: 'local',
				        displayField: 'textoNombreBachiller',
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
					
					items.push(gradoBachillerField)
					items.push(decanoField)
					items.push(rectorField)
					items.push(secretarioGeneralField)
				}else if(item=='tituloProfesional'){
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
					var secretarioGeneralField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Secretario General',
				        name: "autoridadRegistroSecretarioGeneral.codigo",
						store: storeAutoridadSecretarioGeneral,
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					var tituloProfesionalField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Titulo Profesional',
				        name: "especialidad.codigo",
						store: storeEspecialidad,
				        queryMode: 'local',
				        displayField: 'textoNombreDenominacion',
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
					items.push(tituloProfesionalField)
					items.push(decanoField)
					items.push(rectorField)
					items.push(secretarioGeneralField)
				}else if(item=='maestria'){
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Director',
				        name: "autoridadRegistroDirectorPostgrado.codigo",
						store: storeAutoridadDirectorPosgrado,
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
					var secretarioGeneralField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Secretario General',
				        name: "autoridadRegistroSecretarioGeneral.codigo",
						store: storeAutoridadSecretarioGeneral,
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					var maestriaField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Magister en',
				        name: "especialidadPostgrado.codigo",
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
					items.push(maestriaField)
					items.push(decanoField)
					items.push(rectorField)
					items.push(secretarioGeneralField)
				}else if(item=='doctorado'){
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Director',
				        name: "autoridadRegistroDirectorPostgrado.codigo",
						store: storeAutoridadDirectorPosgrado,
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
					var secretarioGeneralField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Secretario General',
				        name: "autoridadRegistroSecretarioGeneral.codigo",
						store: storeAutoridadSecretarioGeneral,
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					var doctoradoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Doctorado en',
				        name: "especialidadPostgrado.codigo",
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
					items.push(doctoradoField)
					items.push(decanoField)
					items.push(rectorField)
					items.push(secretarioGeneralField)
				}else if(item=='agendaGrupo'){
					
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
					items.push(agendaGrupoField)
				}else if(item=='matriculado20141'){
					var matriculado20141Field = new Ext.form.field.Checkbox({
			            fieldLabel: 'Matriculado 2014 - 1',
			            defaultType: 'checkboxfield',
			            items: [
			                {
			                    //boxLabel  : 'Anchovies',
			                    name      : 'flagMatricula20141',
			                    inputValue: '1',
			                    id        : 'checkbox1'
			                }
			            ]
					})
					items.push(matriculado20141Field)
				}else if(item=='semestreEgreso'){
					var semestreEgresoField=new Ext.form.field.Text({
				        fieldLabel: 'Semestre de Egreso',
				        name: 'textoSemestreEgreso',
				        allowBlank: false
			        });
					items.push(semestreEgresoField)
				}else if(item=='constanciaEgreso'){
					var constanciaEgresoField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha de Constansia de Egreso',
				        name: 'fechaConstanciaEgreso',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
					});
					items.push(constanciaEgresoField)
				}else if(item=='textoResolucionFacultad'){
					var textoResolucionFacultadField=new Ext.form.field.Text({
				        fieldLabel: 'Resolucion de Facultad',
				        name: 'textoResolucionFacultad',
				        allowBlank: false
			        });
					items.push(textoResolucionFacultadField)
				}else if(item=='fechaResolucionFacultad'){
					var fechaResolucionFacultadField=new Ext.form.field.Date({
				        fieldLabel: 'F/D Resolucion de Facultad',
				        name: 'fechaResolucionFacultad',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
					});
					items.push(fechaResolucionFacultadField)
				}else if(item=='resolucionRectoral'){
					var resolucionRectoralField=new Ext.form.field.Text({
				        fieldLabel: 'Resolucion de Rectoral',
				        name: 'textoResolucionRectoral',
				        allowBlank: false
			        });
					items.push(resolucionRectoralField)
				}else if(item=='aprobacioncu'){
					var aprobacioncuField=new Ext.form.field.Date({
				        fieldLabel: 'F/D Aprobacion CU',
				        name: 'fechaAprobacioncu',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
					});
					items.push(aprobacioncuField)
				}else if(item=='sustentacionTesis'){
					var sustentacionTesisField=new Ext.form.field.Date({
				        fieldLabel: 'F/D de Sustentacion ',
				        name: 'fechaSustentacionTesis',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					items.push(sustentacionTesisField)
				}else if(item=='nombreTrabajoInvestigacion'){
					var nombreTrabajoInvestigacionField=new Ext.form.field.Text({
				        fieldLabel: 'Trabajo de Investigacion',
				        name: 'textoNombreTrabajoInvestigacion',
				        allowBlank: false
			        });
					items.push(nombreTrabajoInvestigacionField)
				}else if(item=='nombreTesis'){
					var nombreTesisField=new Ext.form.field.Text({
				        fieldLabel: 'Nombre de Tesis',
				        name: 'textoNombreTesis',
				        allowBlank: false
			        });
					items.push(nombreTesisField)
				}else if(item=='detalle'){
					var detalleField=new Ext.form.field.TextArea({
				        fieldLabel: 'Detalle',
				        name: 'textoDetalle',
				        allowBlank: false
			        });
					items.push(detalleField)
				}else if(item=='modalidadGradoTitulo'){
					var modalidadGradoTituloField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Obtencion Grado/Titulo',
				        name: "modalidadGradoTitulo.codigo",
						store: storeModalidadGradoTitulo,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					items.push(modalidadGradoTituloField)
				}else if(item=='modalidadEstudio'){
					var modalidadEstudioField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Modalidad de Estudios',
				        name: "modalidadEstudio.codigo",
						store: storeModalidadEstudio,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					items.push(modalidadEstudioField)
				}else if(item=='rector'){
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
					items.push(rectorField)
				}else if(item=='secretarioGeneral'){
					var secretarioGeneralField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Secretario General',
				        name: "autoridadRegistroSecretarioGeneral.codigo",
						store: storeAutoridadSecretarioGeneral,
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					items.push(secretarioGeneralField)
				}else if(item=='decano'){
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
					items.push(decanoField)
				}else if(item=='folio'){
					var folioField=new Ext.form.field.Number({
						name: 'numeroFolio',
				        fieldLabel: 'N° Folio',
				        minValue: 0
			        });
					items.push(folioField)
				}else if(item=='libro'){
					var libroField=new Ext.form.field.Number({
						name: 'numeroLibro',
				        fieldLabel: 'N° Libro',
				        minValue: 0
			        });
					items.push(libroField)
				}else if(item=='registro'){
					var registroField=new Ext.form.field.Number({
						name: 'numeroRegistro',
				        fieldLabel: 'N° Registro',
				        minValue: 0
			        });
					items.push(registroField.hide())
				}else if(item=='codigoBarra'){
					var codigoBarraField=new Ext.form.field.Text({
				        fieldLabel: 'Codigo Barra',
				        name: 'textoCodigoBarra',
				        allowBlank: false
			        });
					items.push(codigoBarraField)
				}else if(item=='matriculaPost'){
					var matriculaPostField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula Posgrado',
				        name: 'textoMatriculaPost',
				        allowBlank: false
			        });
					items.push(matriculaPostField)
				}else if(item=='resolucionEpg'){
					var resolucionEpgField=new Ext.form.field.Text({
				        fieldLabel: 'Resolucion EPG',
				        name: 'textoResolucionEpg',
				        allowBlank: false
			        });
					items.push(resolucionEpgField)
				}else if(item=='fechaResolucionEpg'){
					var fechaResolucionEpgField=new Ext.form.field.Date({
				        fieldLabel: 'fecha resolcion EPG ',
				        name: 'fechaResolucionEpg',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					items.push(fechaResolucionEpgField)
				}else if(item=='universidadBachiller'){
					var universidadField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Universidad del BACH',
				        name: "universidadBachiller.codigo",
						store: storeUniversidadBachiller,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					var paisField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Pais del BACH',
				        name: "paisBachiller.codigo",
						store: storePais,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
				        listConfig : {
							listeners : {
								itemclick : function(list,record) {
									universidadField.clearValue();
									storeUniversidadBachiller.load({
										params: {
											"pais.codigo":record.get('codigo')
										}
									});
									
								}
							}
											
						},
						allowBlank: false,
						editable:false
					});
					items.push(paisField)
					items.push(universidadField)
				}else if(item=='universidadMaestria'){
					var universidadField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Universidad de la MA',
				        name: "universidadMaestria.codigo",
						store: storeUniversidadMaestria,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					var paisField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Pais de la MA',
				        name: "paisMaestria.codigo",
						store: storePais,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
				        listConfig : {
							listeners : {
								itemclick : function(list,record) {
									universidadField.clearValue();
									storeUniversidadMaestria.load({
										params: {
											"pais.codigo":record.get('codigo')
										}
									});
									
								}
							}
											
						},
						allowBlank: false,
						editable:false
					});
					items.push(paisField)
					items.push(universidadField)
				}else if(item=='fechaResolucionEpg'){
					var fechaResolucionEpgField=new Ext.form.field.Date({
				        fieldLabel: 'fecha resolcion EPG ',
				        name: 'fechaResolucionEpg',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					items.push(fechaResolucionEpgField)
				}else if(item=='directorPost'){
					
					var directorPosgradoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Dir Escuela de Posgrado',
				        name: "autoridadRegistroDirectorPostgrado.codigo",
						store: storeAutoridadDirectorPosgrado,
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
						allowBlank: false,
						editable:false
					});
					items.push(directorPosgradoField)
				}else if(item=='adjunto'){
					var archivoFieldLabel=new Ext.form.field.File({
				        fieldLabel: 'Foto',
				        name:'archivo',
				        labelWidth: 150,
				        anchor: '100%',
				        msgTarget: 'Ingrese Foto',
				        buttonText: 'Examinar',
				    });
					var URLBaseFieldLabel=new Ext.form.field.Text({
				        name: 'URLBase'
			        });
					var adjuntoCodigo=new Ext.form.field.Text({
						name: 'adjunto.codigo',
			        });
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
							var URLBase = URLBaseFieldLabel.getValue();
					    	if(URLBase !=null&&URLBase!=''){
					    		window.open('http://localhost/'+URLBase)
					    	}
						}
					})
					var panelFile=new Ext.form.Panel({
						items:[archivoFieldLabel]
					})
					
					var panelView=new Ext.form.Panel({
						padding : '0 0 0 10',
						items:[btnView]
					})
					
					var panelFoto = new Ext.panel.Panel({
						width: 450,
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
					items.push(adjuntoCodigo.hide())
					items.push(URLBaseFieldLabel.hide())
					items.push(panelFoto)
				}else if(item=='matricula'){

					
					var matriculaFindField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula',
				        name: 'estudiante.textoMatricula',
				        labelWidth: 150,
				        anchor: '100%',
				        readOnly :true,
				        allowBlank: false
			        });	
					
					var panelMatricula=new Ext.form.Panel({
						items:[matriculaFindField]
					})
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
							function loadListParam(form){
								storeAlumno.load({
									params: {
										textoMatricula:form.textoMatricula,
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
											textoMatricula:matriculaField.getValue(),
											textoNombreCompleto:nombreField.getValue()
										}	
									loadListParam(form);		
								}
							});
							var addButton = new Ext.Button({
								width : 80,
								text : 'Agregar',
						        handler: function() {
						        	if(textoMatricula){
						        		console.info('selecciono '+textoMatricula);
						        		matriculaFindField.setValue(textoMatricula);
						        		win.close();
						        	}else{
						        		alert('Seleccione un registro')	
						        	}
						        }
							});
							var storeAlumno=new Ext.data.Store({
							    fields:[
									'textoMatricula',    
									'facultad',
						            'especialidad',
									'textoNombreCompleto',
						            'textoNumeroResolucion'
								],
								//autoLoad:true,
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
							
							
							var matriculaField=new Ext.form.field.Text({
						        fieldLabel: 'Matricula',
						        name: 'matricula',
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
									matriculaField,
									nombreField
								],
								buttons:[findButton]
							})
							var textoMatricula=null;
							var grid=new Ext.grid.Panel({
							    store: storeAlumno,
							    xtype: 'grouped-header-grid',
							    columns: [
									{ text: 'Matricula',  dataIndex: 'textoMatricula',flex:1 },
							        { text: 'Nombre',  dataIndex: 'textoNombreCompleto',flex:1 },
							        { text: 'Facultad',  dataIndex: 'facultad',flex:1 },
							        { text: 'Especialidad',  dataIndex: 'especialidad',flex:1 }
							    ],
							    //border:true,
							    style: {borderColor: '#157fcc'},
							    listeners :{
									select:function(dv, record, index, eOpts ){
										console.log('selecccionado la matricula'+record.get('textoMatricula'));
										textoMatricula=record.get('textoMatricula');
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
							    //height: 200,
							    //width: 400,
							    //layout: 'fit',
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
						     	items:[panelMatricula]
						    },
						    {
						     	flex: 8,
						     	items:[panelView]
						    },
						]
					});
					/*****************************/
					items.push(panelEstudiante)
					//items.push(addButton)
					//items.push(panelEstudiante)
					
				}
			})
			return items;
		}
		var loaderConfig={
			'B':{
				load:function(){
					//matriculaField.reset();
					
					var fields=getUxFields({
						items:[
							'codigoRegistro',
							'B',
							'matricula',
							'gradoBachiller',
						    //'matriculado20141',
						    //'semestreEgreso',
						    //'constanciaEgreso',
						    'textoResolucionFacultad',
						    'fechaResolucionFacultad',
						    'resolucionRectoral',
						    'aprobacioncu',
						    'sustentacionTesis',
						    'nombreTrabajoInvestigacion',
						    'modalidadGradoTitulo',
						    'modalidadEstudio',
						    //'rector',
						    //'secretarioGeneral',
						    //'decano',
						    'folio',
						    'libro',
						    //'registro',
						    'codigoBarra',
						    'agendaGrupo',
						    'adjunto'
						    
						]})
					console.info({fields:fields})
					
					var formPanelToLoad=new Ext.form.Panel({
						items: fields
						
					})
					storeModalidadGradoTitulo.load({
						params:{
							"gradoAcademico.codigoExterno":'B'
						}
					})
					storeEspecialidad.load({
						params:{
							"tipoEspecialidad.codigoExterno":'PRE'
						}
					});
					panelLoader.getForm().clearManagedListeners();
					
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add(formPanelToLoad);
					panelLoader.doLayout();
					var test =panelLoader.getForm()
					console.info({test:test});
				}
			},
			'T':{
				load:function(){
					//matriculaField.reset();
					var fields=getUxFields({
						items:[
							'codigoRegistro',
							'T',
							'matricula',
							'tituloProfesional',
							'textoResolucionFacultad',
							'fechaResolucionFacultad',
							'resolucionRectoral',
							'aprobacioncu',
							'sustentacionTesis',
							'nombreTesis',
							'detalle',
							'modalidadGradoTitulo',
							'modalidadEstudio',
							//'rector',
							//'secretarioGeneral',
							//'decano',
							'folio',
							'libro',
							//'registro',
							'codigoBarra',
						    'agendaGrupo',
						    'adjunto'
						]
					})
					console.info({fields:fields})
					var formPanelToLoad=new Ext.form.Panel({
						items:fields
					})
					storeModalidadGradoTitulo.load({
						params:{
							"gradoAcademico.codigoExterno":'T'
						}
					})
					storeEspecialidad.load({
						params:{
							"tipoEspecialidad.codigoExterno":'PRE'
						}
					});
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add(formPanelToLoad);
					panelLoader.doLayout();
					var test =panelLoader.getForm()
					console.info({test:test});

				}
			},
			'M':{
				load:function(){
					//matriculaField.reset();
					var fields=getUxFields({
						items:[
							'codigoRegistro',
							'M',
							'matricula',
							'maestria',
							'matriculaPost',
							'resolucionEpg',
							'resolucionRectoral',
							'aprobacioncu',
							'sustentacionTesis',
							'nombreTesis',
							'detalle',
							//'pais',
							'universidadBachiller',
							'modalidadGradoTitulo',
							//'rector',
							//'secretarioGeneral',
							//'directorPost',
							'folio',
							'libro',
							//'registro',
							'codigoBarra',
						    'agendaGrupo',
						    'adjunto'
						]
					})
					var formPanelToLoad=new Ext.panel.Panel({
						items:fields
					})
					storeModalidadGradoTitulo.load({
						params:{
							"gradoAcademico.codigoExterno":'M'
						}
					})
					/*
					storeEspecialidad.load({
						params:{
							"gradoAcademico.codigoExterno":'B'
						}
					})
					*/
					storeEspecialidad.load({
						params:{
							"tipoEspecialidad.codigoExterno":'M'
						}
					})
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add(formPanelToLoad);
					panelLoader.doLayout();
				}
			},
			'D':{
				load:function(){
					//matriculaField.reset();
					var fields=getUxFields({
						items:[
							'codigoRegistro',
							'D',
							'matricula',
							'doctorado',
							'matriculaPost',
							'resolucionEpg',
							'resolucionRectoral',
							'aprobacioncu',
							'sustentacionTesis',
							'nombreTesis',
							'detalle',
							//'pais',
							'universidadBachiller',
							'universidadMaestria',
							'modalidadGradoTitulo',
							//'rector',
							//'secretarioGeneral',
							//'directorPost',
							'folio',
							'libro',
							//'registro',
							'codigoBarra',
						    'agendaGrupo',
						    'adjunto'
						]
					})
					var formPanelToLoad=new Ext.panel.Panel({
						items:fields
					})
					storeModalidadGradoTitulo.load({
						params:{
							"gradoAcademico.codigoExterno":'D'
						}
					})
					storeEspecialidad.load({
						params:{
							"tipoEspecialidad.codigoExterno":'D'
						}
					})
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add(formPanelToLoad);
					panelLoader.doLayout();
				}
			}
		}
		var tipoDiplomaLabel=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Tipo de Diploma',
	        name: "tipoDiploma",
			store: storeTipoDiploma,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
	        listeners : {
				select : function(field, newValue, oldValue, eOpts) {
					var tipoDiploma=tipoDiplomaLabel.getValue();
					loaderConfig[tipoDiploma].load();
				}
			},
	        allowBlank: false,
			editable:false
        });
		var panelLoader=new Ext.form.Panel({
			height: 730,
			width : 450
		})
		
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
    		fieldDefaults: {
    	        width : 450,
    	        labelWidth : 150
    	    },
			items:[
			       /*
				{	
					name: 'codigo',
					xtype:'hidden'
				}
			       ,*/
				tipoDiplomaLabel
				,
				panelLoader
				
			],
			buttons:[cancelarButton,saveButton]
			
			
		});	
		
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[
				agregarPanel
			],
			listeners :{
				afterrender:function(){
					if(uxData&&uxData.action=='edit'){
						console.log('uxData.tipo'+uxData.tipo);
						tipoDiplomaLabel.setValue(uxData.tipo);
						loaderConfig[uxData.tipo].load();
						agregarPanel.load({
							url:'gradoTitulo/getEstudianteRegistro',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento,Ext.window.Window,{});
	
	APP.Portal.GradoTitulo.Load.GradoTituloWindowsClose=function(config){
		
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
	        	console.log('fechaCierreField.getSubmitValue() '+fechaCierreField.getSubmitValue())
	        	console.log('groups.length '+groups.length)
	        	if(!isEmpty(fechaCierreField.getSubmitValue())&&groups.length>0){
		        	var groupJson=Ext.encode(groups); 
		        	Ext.Ajax.request({
		                url: 'gradoTitulo/close',
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
		                	 var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel;
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
	            /*
	        	var save = agregarPanel.getForm();
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'datoGeneral/saveEstudiante',
	                    success: function(form, action) {
	                    	var globalPanel=APP.Portal.DatoGeneral.Load.GlobalPanel;
	                    	globalPanel.getDatoGeneralPanel().getListPanel().loadList();
							Ext.Msg.alert('Success', 'Se ha grabado el Estudiante correctamente.');
							me.close();
	                    
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', 'error');
	                    }
	                });
	            }
	            */
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
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[panelLoader],
			buttons:[cancelarButton,saveButton]
			/*
			,
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
		*/
		});
		
		APP.Portal.GradoTitulo.Load.GradoTituloWindowsClose.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloWindowsClose,Ext.window.Window,{});
	
	APP.Portal.GradoTitulo.Load.GradoTituloWindowsFiltro=function(config){
		
		var me=this,
		uxData = config.uxData;
	
		var agregarPanel,
			saveButton, 
			cancelarButton,
			storeAlumno,
			storeSexo,
			storeFacultad,
			storeEspecialidad,
			storeTipoDocumento,
			grid;
		
		function loadListParam(form){
			storeAlumno.load({
				params: {
					textoMatricula:form.textoMatricula,
					textoNombreCompleto:form.textoNombreCompleto
				}
			});	
		}
		cancelarButton = new Ext.Button({
			width : 80,
			text : 'Cancelar',
			handler :function() {
				me.close();
				
			}
		});
		findButton = new Ext.Button({
			width : 80,
			text : 'Buscar',
			handler :function() {
				var form={
						textoMatricula:matriculaField.getValue(),
						textoNombreCompleto:nombreField.getValue()
					}	
				loadListParam(form);		
			}
		});
		addButton = new Ext.Button({
			width : 80,
			text : 'Agregar',
	        handler: function() {
	        	if(textoMatricula){
	        		console.info('selecciono '+textoMatricula);
	        		var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel;
					globalPanel.getGradoTituloPanel().getListPanel().getToolbar().getWinMantenimiento().setMatriculaFiltro(textoMatricula);
					me.close();
	        	}else{
	        		alert('Seleccione un registro')	
	        	}
	        }
		});
		storeAlumno=new Ext.data.Store({
		    fields:[
				'textoMatricula',    
				'facultad',
	            'especialidad',
				'textoNombreCompleto',
	            'textoNumeroResolucion'
			],
			//autoLoad:true,
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
		
		
		var matriculaField=new Ext.form.field.Text({
	        fieldLabel: 'Matricula',
	        name: 'matricula',
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
				matriculaField,
				nombreField
			],
			buttons:[findButton]
		})
		var textoMatricula=null;
		grid=new Ext.grid.Panel({
		    store: storeAlumno,
		    xtype: 'grouped-header-grid',
		    columns: [
				{ text: 'Matricula',  dataIndex: 'textoMatricula',flex:1 },
		        { text: 'Nombre',  dataIndex: 'textoNombreCompleto',flex:1 },
		        { text: 'Facultad',  dataIndex: 'facultad',flex:1 },
		        { text: 'Especialidad',  dataIndex: 'especialidad',flex:1 }
		    ],
		    //border:true,
		    style: {borderColor: '#157fcc'},
		    listeners :{
				select:function(dv, record, index, eOpts ){
					console.log('selecccionado la matricula'+record.get('textoMatricula'));
					textoMatricula=record.get('textoMatricula');
				}
			}
		    
		});
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
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[panelLoader],
			buttons:[cancelarButton,addButton]
		});
		
		APP.Portal.GradoTitulo.Load.GradoTituloWindowsFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloWindowsFiltro,Ext.window.Window,{});
	
	APP.Portal.GradoTitulo.Load.GradoTituloFiltro=function(config){
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
						var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel;
						
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
			    /*
				me.valueMatriculaFieldLabel=function(value){
					matriculaFieldLabel.setValue(value);
				}
				
				me.valueNombreCompletoFieldLabel=function(value){
					nombreCompletoFieldLabel.setValue(value);
				}
				
				me.valueCodigoExternoDocumentoFieldLabel=function(value){
					codigoExternoDocumentoFieldLabel.setValue(value);
				}
				me.valueNumeroDocumentoFieldLabel=function(value){
					numeroDocumentoFieldLabel.setValue(value);
				}
				me.valueFacultadFieldLabel=function(value){
					facultadFieldLabel.setValue(value);
				}
				me.valueEspecialidadFieldLabel=function(value){
					especialidadFieldLabel.setValue(value);
				}
				me.valueNumeroResolucionFieldLabel=function(value){
					numeroResolucionFieldLabel.setValue(value);
				}
				
				
				me.getStoreFacultad=function(){
					return storeFacultad
				}
				me.getStoreEspecialidad=function(){
					return storeEspecialidad
				}
				me.getStoreTipoDocumento=function(){
					return storeTipoDocumento
				}
				*/
				
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
						tipoRegistroField,
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
				        //rawField=me.getRawValue().split(regularExpresion);
				       	textRaw=me.getRawValue()
			        	divider(form,nameField,textRaw);
			        	
			        	
			        	/*
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
					
						*/
						
						var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel,
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
		APP.Portal.GradoTitulo.Load.GradoTituloFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloFiltro,APP.form.field.Picker,{});
	
	APP.Portal.GradoTitulo.Load.GradoTituloToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.GradoTitulo.Load.GradoTituloFiltro({
			
		});
		var mantenimiento = new APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento({
			
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
						
						var win = new APP.Portal.GradoTitulo.Load.GradoTituloWindowsClose({
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
								"gradoTitulo/excel?estudiante.textoMatricula="+formExcel.matricula
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
						var win = new APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.GradoTitulo.Load.GradoTituloToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.GradoTitulo.Load.GradoTituloList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
			
		toolbar=new APP.Portal.GradoTitulo.Load.GradoTituloToolbar({
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
				url : 'gradoTitulo/delete',
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
				url : 'gradoTitulo/enviarSunedu',
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
			var win = new APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento({
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
	            'codigoUniversidad',
        		'razonSocial',
        		'matriculaFecha',
        		'facultadNombre',
        		'escuelaCarrera',
        		'especialidadPostgrado',
        		'apellidoPaterno',
        		'apellidoMaterno',
        		'nombre',
        		'sexo',
        		'documentoTipo',
        		'documentoNumero',
        		'egresadoFecha',
        		'procedenciaBachiller',
        		'procedenciaMaestria',
        		'gradoTitulo',
        		'especialidad',
        		'abreviaturaGradoTitulo',
        		'actoTituloGrado',
        		'actoFecha',
        		'tesis',
        		'modadlidad',
        		'procedenciaRevalidadPais',
        		'procedenciaRevalidadUniversidad',
        		'procedenciaRevalidadGradoExtranjero',
        		'resolucionNumero',
        		'resolucionFacultad',
        		'diplomaFecha',
        		'diplomaNumero',
        		'diplomaTipoEmision',
        		'registroLibro',
        		'registroFolio',
        		'registroRegistro',
        		'cargoUno',
        		'autoridadUno',
        		'cargoDos',
        		'autoridadDos',
        		'cargoTres',
        		'autoridadTres',
        		'registroOficio',
        		'agendaGrupo',
        		'enviadoSunedu',
        		'duplicado',
        		'estudianteRegistroPadre',
        		'egresadoCiclo'
        		
        		//'gradoAcademico',
        		//'gradoAcademicoCodigoExterno'
        		/*,
        		{name: 'active', type: 'bool',
                    convert: function(v){
                        return (v === "A" || v === true) ? true : false;
                    }
                }
	            */
			],
			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'gradoTitulo/getEstudianteRegistroList',
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
				{ text: 'N° Registro',  dataIndex: 'registroRegistro',flex:1},
				{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1},
				{ text: 'Tipo Doc.',  dataIndex: 'gradoTitulo',flex:1},
				{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
				{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
				{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
				{ text: 'Fecha de 1° MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
				{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
				{ text: 'especialidad PRE',  dataIndex: 'escuelaCarrera',flex:1 },
				{ text: 'Especialidad POS',  dataIndex: 'especialidadPostgrado',flex:1 },
				{ text: 'Apellido Paterno',  dataIndex: 'apellidoPaterno',flex:1 },
				{ text: 'Apellido Materno',  dataIndex: 'apellidoMaterno',flex:1 },
				{ text: 'Nombre',  dataIndex: 'nombre',flex:1 },
				{ text: 'Sexo',  dataIndex: 'sexo',flex:1, hidden: true },
				{ text: 'Documento Tipo',  dataIndex: 'documentoTipo',flex:1, hidden: true},
				{ text: 'Documento Numero',  dataIndex: 'documentoNumero',flex:1, hidden: true},
				{ text: 'Fecha de Egreso',  dataIndex: 'egresadoFecha',flex:1, hidden: true},
				{ text: 'Procedencia de Bachiller',  dataIndex: 'procedenciaBachiller',flex:1, hidden: true},
				{ text: 'Procedencia de Maestria',  dataIndex: 'procedenciaMaestria',flex:1, hidden: true},
				{ text: 'Especialidad',  dataIndex: 'especialidad',flex:1, hidden: true},
				{ text: 'Abrev Tipo Documeto',  dataIndex: 'abreviaturaGradoTitulo',flex:1, hidden: true},
				{ text: 'Mod Grado y Titulo',  dataIndex: 'actoTituloGrado',flex:1, hidden: true},
				{ text: 'Fech Obtencion del G/T',  dataIndex: 'actoFecha',flex:1, hidden: true},
				{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
				{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
				{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
				{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
				{ text: 'Proc Revalidad Univ. Grado',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
				{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
				{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
				{ text: 'Fecha de Diploma',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
				{ text: 'Codigo de Barra',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
				{ text: 'Tipo de Emision',  dataIndex: 'diplomaTipoEmision',flex:1, hidden: true},
				{ text: 'N° Libro',  dataIndex: 'registroLibro',flex:1, hidden: true}, 
				{ text: 'N° Folio',  dataIndex: 'registroFolio',flex:1, hidden: true},
				{ text: 'Nom. Cargo 1',  dataIndex: 'cargoUno',flex:1, hidden: true},
				{ text: 'Cargo 1',  dataIndex: 'autoridadUno',flex:1, hidden: true},
				{ text: 'Nom Cargo 2',  dataIndex: 'cargoDos',flex:1, hidden: true},
				{ text: 'Cargo 2',  dataIndex: 'autoridadDos',flex:1, hidden: true},
				{ text: 'Nom Cargo 3',  dataIndex: 'cargoTres',flex:1, hidden: true},
				{ text: 'Cargo 3',  dataIndex: 'autoridadTres',flex:1, hidden: true},
				{ text: 'Enviado SUNEDU',  dataIndex: 'enviadoSunedu',flex:1, hidden: true},
				{ text: 'Duplicado',  dataIndex: 'duplicado',flex:1, hidden: true},
				{ text: 'N° registro duplicado',  dataIndex: 'estudianteRegistroPadre',flex:1, hidden: true},
				{ text: 'Ciclo de Egreso',  dataIndex: 'egresadoCiclo',flex:1, hidden: true}
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
	    					},
	    					{
		    					text:'Titulo',
		    					handler: function(){
		    						window.open(
		    								"gradoTitulo/exportDiploma?codigo="+record.get('codigo')
		    								//"gradoTitulo/exportDiploma"
		    						)
	    					
	    						}
	    					},
	    					{
		    					text:'Inscripcion',
		    					handler: function(){
		    						window.open(
		    								"gradoTitulo/exportInscripcion?codigo="+record.get('codigo')
		    								//"gradoTitulo/exportDiploma"
		    						)
	    					
	    						}
	    					}
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
		APP.Portal.GradoTitulo.Load.GradoTituloList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloList,Ext.panel.Panel,{});
	
	APP.Portal.GradoTitulo.Load.GradoTituloPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.GradoTitulo.Load.GradoTituloList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'GradoTitulo',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.GradoTitulo.Load.GradoTituloPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.GradoTitulo.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.GradoTitulo.Load.GradoTituloPanel({});
		
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
		
		APP.Portal.GradoTitulo.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.GradoTitulo.Load.Container({});
		APP.Portal.GradoTitulo.Load.GlobalPanel=globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
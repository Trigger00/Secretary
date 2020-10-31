<script type="text/javascript">
	Ext.ns('APP.Portal.Duplicado.Load')
	APP.Portal.Duplicado.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.Duplicado.Load.GradoTituloWindowsMantenimiento=function(config){
		
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
			//storeEstudiante,
			tipoDocunento,
			storeProgramaEstudio;
		var itemsPerPage = 25;
		var formTextoMatricula,
	 		formTextoNombreCompleto,
			formTextoDocumento,
			formGradoAcademicoCodigoExterno,
			formFlagCandado;		
		var columnGridAlumno=[
			{ text: 'N° Registro',  dataIndex: 'registroRegistro',flex:1},
			{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1, hidden: true},
			{ text: 'Grado o Titulo',  dataIndex: 'gradoTitulo',flex:1},
			{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
			{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
			{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
			{ text: 'Fecha de 1° MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
			{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
			{ text: 'Escuela Carrera',  dataIndex: 'escuelaCarrera',flex:1, hidden: true  },
			{ text: 'Especialidad POS',  dataIndex: 'especialidadPostgrado',flex:1, hidden: true  },
			{ text: 'Especialidad',  dataIndex: 'especialidad',flex:1},
			{ text: 'Matricula',  dataIndex: 'matricula',flex:1 },
			{ text: 'Apellido Paterno',  dataIndex: 'apellidoPaterno',flex:1 },
			{ text: 'Apellido Materno',  dataIndex: 'apellidoMaterno',flex:1 },
			{ text: 'Nombre',  dataIndex: 'nombre',flex:1 },
			{ text: 'Sexo',  dataIndex: 'sexo',flex:1, hidden: true },
			{ text: 'Documento Tipo',  dataIndex: 'documentoTipo',flex:1, hidden: true},
			{ text: 'Documento Numero',  dataIndex: 'documentoNumero',flex:1, hidden: true},
			{ text: 'Fecha de Egreso',  dataIndex: 'egresadoFecha',flex:1, hidden: true},
			{ text: 'Procedencia de Bachiller',  dataIndex: 'procedenciaBachiller',flex:1, hidden: true},
			{ text: 'Procedencia de Maestria',  dataIndex: 'procedenciaMaestria',flex:1, hidden: true},									{ text: 'Abrev Tipo Documeto',  dataIndex: 'abreviaturaGradoTitulo',flex:1, hidden: true},
			{ text: 'Mod Grado y Titulo',  dataIndex: 'actoTituloGrado',flex:1, hidden: true},
			{ text: 'Fecha de Sustentación',  dataIndex: 'actoFecha',flex:1, hidden: true},
			{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
			{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
			{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
			{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
			{ text: 'Denominación del GyT REV',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
			{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
			{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
			{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
			{ text: 'Numero de Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
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
		]
			
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
	        	
	        	function submit( save ){
	        		if (save.isValid()) {
     	            	save.submit({
     	                	url: 'duplicado/saveDuplicado',
     	                	method: 'POST',
     	                    success: function (form, action) {
     	                    	var jsonResponse = action.result;
     	                        if (jsonResponse.success == true) {
     	                        	var globalPanel=APP.Portal.Duplicado.Load.GlobalPanel;
     		                    	globalPanel.getGradoTituloPanel().getListPanel().loadList();
     		                    	//wait:true,
     								Ext.Msg.alert('Éxito', jsonResponse.message);
     								me.close();
     	                       }
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
	        	
	        	
	        	
	        	
	        	
	        	Ext.Msg.confirm("Confimacion", "¿Desea Grabar el registro?", function(btnText){
                    if(btnText === "yes"){
                    	var save = panelLoader.getForm();
         	            var data=[];
         	            save.getFields().each(function(field) {
         	            	if( field.name == "fechaEgresoPosgrado" && field.value == null ){
        	            		field.setDisabled(true)
        	            	}else if(field.name == "fechaMatriculaPosgrado" && field.value == null){
        	            		field.setDisabled(true)
        	            	}
         	            	data.push({field:field,validate:field.validate()});
         	            });
         	            console.info({data:data})
         	            if (save.isValid()) {
         	            	save.submit({
         	                	url: 'duplicado/saveDuplicado',
         	                	method: 'POST',
         	                    success: function (form, action) {
         	                    	var jsonResponse = action.result;
         	                        if (jsonResponse.success == true) {
         	                        	var globalPanel=APP.Portal.Duplicado.Load.GlobalPanel;
         		                    	globalPanel.getGradoTituloPanel().getListPanel().loadList();
         		                    	//wait:true,
         								Ext.Msg.alert('Éxito', jsonResponse.message);
         								me.close();
         	                       }
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
         	                   /*
         	                	success: function(form, action) {
         	                    	var globalPanel=APP.Portal.Duplicado.Load.GlobalPanel;
         	                    	globalPanel.getGradoTituloPanel().getListPanel().loadList();
         							Ext.Msg.alert('Success', 'Se ha grabado el Registro correctamente.');
         							me.close();
         	                    
         	                    },
         	                    failure: function(form, action) {
         	                        Ext.Msg.alert('Failed', 'error');
         	                    }
         	                    */
         	                });
         	            }
                    }
                    else if(btnText === "no"){
                        
                    }
                }, this);
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
					//"cargo.codigoExterno": 'DEC',
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
		storePaisMaestria = new Ext.data.Store({
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
				'textoNombre',
				'paisCodigo'
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
				'textoNombre',
				'paisCodigo'
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
		        },
				extraParams:{
					"flagEstado": 'A'
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
				}else if(item=='B'){
					var tipoRegistros=new Ext.form.field.Text({
				        name: "gradoAcademico.codigoExterno"
			        });
					tipoRegistros.setValue('B');
					items.push(tipoRegistros.hide())
					tipoDocunento='B';
				}else if(item=='T'){
					var tipoRegistros=new Ext.form.field.Text({
				        name: "gradoAcademico.codigoExterno"
			        });
					tipoRegistros.setValue('T');
					items.push(tipoRegistros.hide())
					tipoDocunento='T';
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
					
					tipoDocunento='M';
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
					tipoDocunento='D';
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
				}/*else if(item=='maestria'){
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
				}
				*/
				/*else if(item=='doctorado'){
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
				}*/else if(item=='agendaGrupo'){
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
				        fieldLabel: 'Resolucion CU',
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
				}else if(item=='fechaEgresoPosgrado'){
					var aprobacioncuField=new Ext.form.field.Date({
				        fieldLabel: 'F/D Egreso',
				        name: 'fechaEgresoPosgrado',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d"
				        ,allowBlank: true
					});
					items.push(aprobacioncuField)
				}else if ( item == 'fechaMatriculaPosgrado'){
					var fechaMatriculaPosgradoField=new Ext.form.field.Date({
				        fieldLabel: 'F/D Matricula Posgrado',
				        name: 'fechaMatriculaPosgrado',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: true
			        });
// 					items.push(fechaMatriculaProgramaField.setDisabled(true))
					items.push(fechaMatriculaPosgradoField)
					
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
				        allowBlank: false,
				        height :50
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
					items.push(registroField)
				}else if(item=='codigoBarra'){
					var codigoBarraField=new Ext.form.field.Text({
				        fieldLabel: 'Codigo Barra',
				        name: 'textoCodigoBarra'//,
				        //allowBlank: false
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
						forceSelection: true,
						listeners   : {
						    beforequery: function(record){  
						        record.query = new RegExp(record.query, 'i');
						        record.forceAll = true;
						    }
						},
						listConfig : {				        	
							listeners : {								
								select : function(list,record) {
									var universidadPais = record.get('paisCodigo')
									var paisCodigo = paisField.getValue();
								 	console.log( "universidadPais "+universidadPais )
								  	console.log( "paisCodigo "+paisCodigo )

									if(paisCodigo != universidadPais){
										paisField.clearValue();
									}									
								}
							}
											
						},
						caseSensitive: true
					
					});
					var paisField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Pais del BACH',
				        name: "paisBachiller.codigo",
						store: storePais,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
				        enableKeyEvents:true,
				        listeners: {
				        	Keypress: function(combo, event) {
					            var key = String.fromCharCode(event.getKey());
					            console.log( "key "+key );
					            console.info({universidadField:universidadField});
					            storeUniversidadBachiller.load({
										params: {
											"pais.codigo":0
										}
									});
					            console.log( "keyup reset" )

					        },
							beforequery: function(record){
								console.log( "record.query "+record.query)
						        record.query = new RegExp(record.query, 'i');
						        record.forceAll = true;
						    }
				        },
				        listConfig : {
							listeners : {
								select : function(list,record) {
									universidadField.clearValue();
									storeUniversidadBachiller.load({
										params: {
											"pais.codigo":record.get('codigo')
										}
									});
									
								}
							}
											
						},
						allowBlank: false
					});
					var procedenciaGradoExtranjeroField=new Ext.form.field.Text({
				        fieldLabel: 'Procedencia Grado EXT',
				        name: 'textoProcedenciaGradoExtranjero'
				        //allowBlank: false
			        });
					items.push(paisField)
					items.push(universidadField)
					items.push(procedenciaGradoExtranjeroField)
					
				}else if(item=='universidadMaestria'){
					var universidadField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Universidad de la MA',
				        name: "universidadMaestria.codigo",
						store: storeUniversidadMaestria,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
				        allowBlank: false,
						forceSelection: true,
						caseSensitive: true,
						listeners   : {
							
						    beforequery: function(record){  
						        record.query = new RegExp(record.query, 'i');
						        record.forceAll = true;
						    }
						},
						listConfig : {

							listeners : {
								
								select : function(list,record) {
									var universidadPais = record.get('paisCodigo')
									var paisCodigo = paisField.getValue();
								 	console.log( "universidadPais "+universidadPais )
								  	console.log( "paisCodigo "+paisCodigo )

									if(paisCodigo != universidadPais){
										paisField.clearValue();
									}
									
								}
							}
											
						}

					});
					var paisField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Pais de la MA',
				        name: "paisMaestria.codigo",
						store: storePaisMaestria,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
				        enableKeyEvents:true,
						allowBlank: false,
						listeners: {
				        	
							Keypress: function(combo, event) {
					            var key = String.fromCharCode(event.getKey());
					            console.log( "key "+key );
					            storeUniversidadMaestria.load({
										params: {
											"pais.codigo":0
										}
									});
					            console.log( "keyup reset" )

					        },
							beforequery: function(record){
								console.log( "record.query "+record.query)
						        record.query = new RegExp(record.query, 'i');
						        record.forceAll = true;
						    }
				        },
				        listConfig : {
							listeners : {
								select : function(list,record) {
									universidadField.clearValue();
									storeUniversidadMaestria.load({
										params: {
											"pais.codigo":record.get('codigo')
										}
									});
									
								}
							}
											
						}
					});
					var procedenciaMaestriaExtranjeroField=new Ext.form.field.Text({
				        fieldLabel: 'Procedencia MA EXT',
				        name: 'textoProcedenciaMaestriaExtranjero'
				        //allowBlank: false
			        });
					items.push(paisField)
					items.push(universidadField)
					items.push(procedenciaMaestriaExtranjeroField)
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
				}else if(item=='resolucionCambioNombre'){
					var resolucionEpgField=new Ext.form.field.Text({
				        fieldLabel: 'Resolucion C.N',
				        name: 'textoResolucionCambioNombre'
				        //allowBlank: false
			        });
					items.push(resolucionEpgField)
				}else if(item=='adjunto'){
					var codigoRegistroEscaneadoField = new Ext.form.field.Text({
				        name: "codigoRegistroEscaneado"
			        });
// 					var archivoFieldLabel=new Ext.form.field.File({
// 				        fieldLabel: 'Escaneado',
// 				        name:'adjuntoEscaneado',
// 				        labelWidth: 150,
// 				        anchor: '100%',
// 				        msgTarget: 'Ingrese Foto',
// 				        buttonText: 'Examinar',
// 				    });
					var archivoFieldLabel=new Ext.form.field.Text({
						fieldLabel: 'Escaneado',
			        });
					var URLBaseFieldLabel=new Ext.form.field.Text({
				        name: 'URLAdjuntoEscaneado'
			        });
					var adjuntoCodigo=new Ext.form.field.Text({
						name: 'codigoEstudianteRegistroAdjuntoEscaneado',
			        });
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
							console.log("codigoRegistroEscaneadoField.getValue() = "+codigoRegistroEscaneadoField.getValue());							
// 							var URLBase = URLBaseFieldLabel.getValue();
// 					    	if(URLBase !=null&&URLBase!=''){
// 					    		window.open('http://192.168.1.4/'+URLBase)
// 					    		//window.open('http://locahost/'+URLBase)
// 					    	}
							Ext.Ajax.request({
				                url: 'gradoTitulo/getFilePath',
				                method: 'POST',
				                params : {
				                	codigo: codigoRegistroEscaneadoField.getValue(),
				                	titpoAdjunto:"2",
				                	flagDuplicado:"1"

				                },
				                success: function (result,response, options,action) {
				                	console.log('success');
				                	console.info({responseText:result.responseText});
				                	
				                	var jsonData = Ext.decode(result.responseText);
				                    console.info({jsonData:jsonData});				                
									
    	                        	if (jsonData.success == true) {
//     						    		window.open('http://localhost/'+jsonData.path)
   	 					    			window.open('http://192.168.1.4/'+jsonData.path)

    	                       		}else if (jsonData.success == false) {
	    								Ext.Msg.alert('Alerta', jsonData.message);
    	                       		}

				                },
				                failure: function (response, options,result) {
				                	console.log('failure ');
				                }
				            });

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
					items.push(codigoRegistroEscaneadoField.hide())
				}else if(item=='adjuntoFoto'){
// 					var archivoFieldLabel=new Ext.form.field.File({
// 				        fieldLabel: 'Foto',
// 				        name:'adjuntoFoto',
// 				        labelWidth: 150,
// 				        anchor: '100%',
// 				        msgTarget: 'Ingrese Foto',
// 				        buttonText: 'Examinar',
// 				    });
					var codigoRegistroFotoField = new Ext.form.field.Text({
				        name: "codigoRegistroFoto"
			        });
					var archivoFieldLabel=new Ext.form.field.Text({
						fieldLabel: 'Foto',
			        });
					var URLBaseFieldLabel=new Ext.form.field.Text({
				        name: 'URLAdjuntoFoto'
			        });
					var adjuntoCodigo=new Ext.form.field.Text({
						name: 'codigoEstudianteRegistroAdjuntoFoto',
			        });
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
// 							var URLBase = URLBaseFieldLabel.getValue();
// 					    	if(URLBase !=null&&URLBase!=''){
// 					    		window.open('http://192.168.1.4/'+URLBase)
// 					    		//window.open('http://localhost/'+URLBase)
// 					    	}
							console.log("codigoRegistroFotoField.getValue() = "+codigoRegistroFotoField.getValue());							
							Ext.Ajax.request({
				                url: 'gradoTitulo/getFilePath',
				                method: 'POST',
				                params : {
				                	codigo: codigoRegistroFotoField.getValue(),
				                	titpoAdjunto:"1",
				                	flagDuplicado:"1"
				                },
				                success: function (result,response, options,action) {
				                	console.log('success');
				                	console.info({responseText:result.responseText});
				                	
				                	var jsonData = Ext.decode(result.responseText);
				                    console.info({jsonData:jsonData});				                
									
    	                        	if (jsonData.success == true) {
//     						    		window.open('http://localhost/'+jsonData.path)
   	 					    			window.open('http://192.168.1.4/'+jsonData.path)

    	                       		}else if (jsonData.success == false) {
	    								Ext.Msg.alert('Alerta', jsonData.message);
    	                       		}

				                },
				                failure: function (response, options,result) {
				                	console.log('failure ');
				                }
				            });
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
					items.push(codigoRegistroFotoField.hide())
				}else if(item=='matriculaBachiller'){
					console.log('tipoDocunento '+tipoDocunento );
					
					var textoMatriculaOriginal=null,
						codigo=null,
						codigoEspecialidad=null,
						fechaSustentacionTesis=null,
						textoDetalle=null,
						textoNombreTrabajoInvestigacion=null,
						estudianteCodigo=null;
					
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Decano',
				        name: "autoridadRegistroDecano.codigo",
						store: storeAutoridadDecano,
						anchor: '100%',
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
						anchor: '100%',
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
						anchor: '100%',
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
					
					
					var matriculaFindField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula',
				        name: 'estudiante.textoMatricula',
				        labelWidth: 150,
				        anchor: '100%'//,
				        //readOnly :true,
				        //allowBlank: false
			        });	
					var sustentacionTesisField=new Ext.form.field.Date({
				        fieldLabel: 'F/D de Sustentacion ',
				        name: 'fechaSustentacionTesis',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					var nombreTrabajoInvestigacionField=new Ext.form.field.Text({
				        fieldLabel: 'Trabajo de Investigacion',
				        name: 'textoNombreTrabajoInvestigacion'//,
				        //allowBlank: false
			        });
					var estudianteRegistoCodigo=new Ext.form.field.Text({
				        name: "estudianteRegistroPadre"
			        });
					var estudianteCodigoField=new Ext.form.field.Text({
				        name: "estudiante.codigo"
			        });
					var panelMatricula=new Ext.form.Panel({
						items:[matriculaFindField]
					})
					
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
							/*
							storeEstudiante.clearData();
							storeEstudiante.removeAll();
							*/
							function loadListParamRegistro(form){
								storeEstudiante.currentPage = 1;
								storeEstudiante.load({
									start: 0, 
									limit: itemsPerPage,
									params: {
										"estudiante.textoMatricula":form.textoMatricula,
										"estudiante.textoNombreCompleto":form.textoNombreCompleto,
										"gradoAcademico.codigoExterno": tipoDocunento,
										"estudiante.textoNumeroDocumento":form.textoDocumento,
										"flagCandado":"BuscarCerrado"
									}
								});	
							}	
							var cancelarButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-delete-icon',
								text : 'Cancelar',
								handler :function() {
									win.close();
									
								}
							});
							var findButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-search-icon',
								text : 'Buscar',
								handler :function() {
									formTextoMatricula=matriculaField.getValue(),
							 		formTextoNombreCompleto=nombreField.getValue(),
									formTextoDocumento=documentoIdentidadField.getValue(),
									formGradoAcademicoCodigoExterno=tipoDocunento,
									formFlagCandado="B";	
									var form={
											textoMatricula:matriculaField.getValue(),
											textoNombreCompleto:nombreField.getValue(),
											textoDocumento:documentoIdentidadField.getValue(),									
											"gradoAcademico.codigoExterno": tipoDocunento,
											"flagCandado":"B"
										}	
									loadListParamRegistro(form);		
								}
							});
							
							var addButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-add-icon',
								text : 'Agregar',
						        handler: function() {
					        		console.info('selecciono textoMatriculaOriginal '+textoMatriculaOriginal);
						        	if(textoMatriculaOriginal!=null){
						        		matriculaFindField.setValue(textoMatriculaOriginal);
						        		//console.info('seleccion codigo '+codigo);
						        		estudianteRegistoCodigo.setValue(codigo);
						        		//estudianteRegistoCodigo.setValue(codigoEspecialidad);
						        		gradoBachillerField.setValue(codigoEspecialidad);
						        		sustentacionTesisField.setValue(fechaSustentacionTesis);
						        		nombreTrabajoInvestigacionField.setValue(textoNombreTrabajoInvestigacion);
						        		estudianteCodigoField.setValue(estudianteCodigo);
						        		decanoField.clearValue();
										storeAutoridadDecano.load({
											params: {
												"cargo.especialidad":codigoEspecialidad
											}
										});
						        		win.close();
						        	}else{
						        		alert('Seleccione un registro Original')	
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
							
							var documentoIdentidadField=new Ext.form.field.Text({
						        fieldLabel: 'N° Documento',
						        name: 'estudiante.textoNumeroDocumento',
						        labelWidth: 150,
						        anchor: '100%'
					        });
							
							var panelField=new Ext.form.Panel({
								height: 130,
								width : 600,
								bodyPadding: 5,
								items:[
									matriculaField,
									nombreField,
									documentoIdentidadField
								],
								buttons:[findButton]
							})
							var storeEstudiante=new Ext.data.Store({
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
						        		'codigoEspecialidad',
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
						        		'egresadoCiclo',
						        		'matricula',
						        		'detalle',
						        		'estudianteCodigo',
						        		'textoNombreTrabajoInvestigacion'
									],
									//autoLoad:true,
									pageSize: itemsPerPage, 
								    proxy: {
								        type: 'ajax',
								        url: 'gradoTitulo/getEstudianteRegistroList',
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
							            		"estudiante.textoMatricula":formTextoMatricula,
												"estudiante.textoNombreCompleto":formTextoNombreCompleto,
												"estudiante.textoNumeroDocumento":formTextoDocumento,
												"gradoAcademico.codigoExterno": formGradoAcademicoCodigoExterno,
												"flagCandado":formFlagCandado
							                });
							            }
							        }
							});
							var gridEstudianteDuplicado=new Ext.grid.Panel({
							    store: storeEstudiante,
							    xtype: 'grouped-header-grid',
							    columns: [
										{ text: 'N° Registro',  dataIndex: 'registroRegistro',flex:1},
										{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1, hidden: true},
										{ text: 'Grado o Titulo',  dataIndex: 'gradoTitulo',flex:1},
										{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
										{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
										{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
										{ text: 'Fecha de 1° MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
										{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
										{ text: 'Escuela Carrera',  dataIndex: 'escuelaCarrera',flex:1, hidden: true  },
										{ text: 'Especialidad POS',  dataIndex: 'especialidadPostgrado',flex:1, hidden: true  },
										{ text: 'Especialidad',  dataIndex: 'especialidad',flex:1},
										{ text: 'Matricula',  dataIndex: 'matricula',flex:1 },
										{ text: 'Apellido Paterno',  dataIndex: 'apellidoPaterno',flex:1 },
										{ text: 'Apellido Materno',  dataIndex: 'apellidoMaterno',flex:1 },
										{ text: 'Nombre',  dataIndex: 'nombre',flex:1 },
										{ text: 'Sexo',  dataIndex: 'sexo',flex:1, hidden: true },
										{ text: 'Documento Tipo',  dataIndex: 'documentoTipo',flex:1, hidden: true},
										{ text: 'Documento Numero',  dataIndex: 'documentoNumero',flex:1, hidden: true},
										{ text: 'Fecha de Egreso',  dataIndex: 'egresadoFecha',flex:1, hidden: true},
										{ text: 'Procedencia de Bachiller',  dataIndex: 'procedenciaBachiller',flex:1, hidden: true},
										{ text: 'Procedencia de Maestria',  dataIndex: 'procedenciaMaestria',flex:1, hidden: true},									{ text: 'Abrev Tipo Documeto',  dataIndex: 'abreviaturaGradoTitulo',flex:1, hidden: true},
										{ text: 'Mod Grado y Titulo',  dataIndex: 'actoTituloGrado',flex:1, hidden: true},
										{ text: 'Fecha de Sustentación',  dataIndex: 'actoFecha',flex:1, hidden: true},
										{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
										{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
										{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
										{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
										{ text: 'Denominación del GyT REV',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
										{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
										{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
										{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
										{ text: 'Numero de Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
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
								],
							    //border:true,
							    style: {borderColor: '#157fcc'},
							    dockedItems: [{
							        xtype: 'pagingtoolbar',
							        store: storeEstudiante, 
							        dock: 'bottom',
							        displayInfo: true
							    }],
							    listeners:{
							    	select:function(dv, record, index, eOpts ){
										console.log('selecccionado la matricula original'+record.get('matricula'));
										console.log('selecccionado el codigo original '+record.get('registroRegistro'));
										textoMatriculaOriginal=record.get('matricula');
										codigo=record.get('registroRegistro');
										codigoEspecialidad=record.get('codigoEspecialidad');
										fechaSustentacionTesis=record.get('actoFecha');
										textoNombreTrabajoInvestigacion=record.get('textoNombreTrabajoInvestigacion');
										textoDetalle=record.get('detalle');
										estudianteCodigo=record.get('estudianteCodigo');
									}
							    }
							    
							});
							
							var panelLoaderDuplicado= new Ext.panel.Panel({
								width: 600,
							    height: 380,
								layout:{
									type:'border'
								},
								items:[
									{
										region: 'north', height:130,width:'100%',border: true,items : [panelField]
									},
									{
										region: 'center',layout: 'fit',items : [gridEstudianteDuplicado]
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
							    items: [panelLoaderDuplicado],
							    buttons:[cancelarButton,addButton]
							    
							});
							/****************************/
							
							win.show()
						}
					})
					var panelViewDuplicado=new Ext.form.Panel({
						padding : '0 0 0 10',
						items:[btnView]
					})
					
					var panelEstudianteDuplicado = new Ext.panel.Panel({
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
						     	items:[panelViewDuplicado]
						    },
						]
					});
					/*****************************/
					
					
					var panelDecano = new APP.Portal.Duplicado.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:decanoField
						}
					});
					var panelRector = new APP.Portal.Duplicado.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.Duplicado.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:secretarioGeneralField
						}
					});
					items.push(panelEstudianteDuplicado)
					items.push(estudianteRegistoCodigo.hide())
					items.push(estudianteCodigoField.hide())
					items.push(gradoBachillerField)
					items.push( panelDecano )
					items.push( panelRector )
					items.push( panelSecretarioGeneral )
					items.push(sustentacionTesisField)
					items.push(nombreTrabajoInvestigacionField)
					
					
				}else if(item=='matriculaTitulo'){
					console.log('tipoDocunento '+tipoDocunento );
					
					var textoMatriculaOriginal = null,
						codigo = null,
						codigoEspecialidad = null,
						fechaSustentacionTesis = null,
						textoDetalle = null,
						textoNombreTesis = null,
						estudianteCodigo = null,
						flagDiplomaSexo = null;
					
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
					
					
					var matriculaFindField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula',
				        name: 'estudiante.textoMatricula',
				        labelWidth: 150,
				        anchor: '100%'//,
				        //readOnly :true,
				        //allowBlank: false
			        });	
					var sustentacionTesisField=new Ext.form.field.Date({
				        fieldLabel: 'F/D de Sustentacion ',
				        name: 'fechaSustentacionTesis',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					var nombreTesisField=new Ext.form.field.Text({
				        fieldLabel: 'Nombre de Tesis',
				        name: 'textoNombreTesis',
				        allowBlank: false
			        });
					var detalleField=new Ext.form.field.TextArea({
				        fieldLabel: 'Detalle',
				        name: 'textoDetalle',
				        allowBlank: false,
				        height :50
			        });
					var estudianteRegistoCodigo=new Ext.form.field.Text({
				        name: "estudianteRegistroPadre"
			        });
					var estudianteCodigoField=new Ext.form.field.Text({
				        name: "estudiante.codigo"
			        });
					var flagDiplomaSexoField = new Ext.form.field.Text({
				        name: "flagDiplomaSexo"
			        });
					
					var panelMatricula=new Ext.form.Panel({
						items:[matriculaFindField]
					})
					
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
							
							function loadListParamRegistro(form){
								storeEstudiante.currentPage = 1;
								storeEstudiante.load({
									start: 0, 
									limit: itemsPerPage,
									params: {
										"estudiante.textoMatricula":form.textoMatricula,
										"estudiante.textoNombreCompleto":form.textoNombreCompleto,
										"gradoAcademico.codigoExterno": tipoDocunento,
										"estudiante.textoNumeroDocumento":form.textoDocumento,
										"flagCandado":"BuscarCerrado"
									}
								});	
							}	
							var cancelarButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-delete-icon',
								text : 'Cancelar',
								handler :function() {
									win.close();
									
								}
							});
							var findButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-search-icon',
								text : 'Buscar',
								handler :function() {
									formTextoMatricula=matriculaField.getValue(),
							 		formTextoNombreCompleto=nombreField.getValue(),
									formTextoDocumento=documentoIdentidadField.getValue(),
									formGradoAcademicoCodigoExterno=tipoDocunento,
									formFlagCandado="B";	
									var form={
											textoMatricula:matriculaField.getValue(),
											textoNombreCompleto:nombreField.getValue(),
											textoDocumento:documentoIdentidadField.getValue(),									
											"gradoAcademico.codigoExterno": tipoDocunento,
											"flagCandado":"B"
										}	
									loadListParamRegistro(form);		
								}
							});
							
							var addButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-add-icon',
								text : 'Agregar',
						        handler: function() {
					        		console.info('selecciono textoMatriculaOriginal '+textoMatriculaOriginal);
						        	if(textoMatriculaOriginal!=null){
						        		matriculaFindField.setValue(textoMatriculaOriginal);
						        		//console.info('seleccion codigo '+codigo);
						        		estudianteRegistoCodigo.setValue(codigo);
						        		//estudianteRegistoCodigo.setValue(codigoEspecialidad);
						        		tituloProfesionalField.setValue(codigoEspecialidad);
						        		sustentacionTesisField.setValue(fechaSustentacionTesis);
						        		nombreTesisField.setValue(textoNombreTesis);
						        		detalleField.setValue(textoDetalle);
						        		estudianteCodigoField.setValue(estudianteCodigo);
						        		flagDiplomaSexoField.setValue( flagDiplomaSexo );
						        		win.close();
						        	}else{
						        		alert('Seleccione un registro Original')	
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
							
							var documentoIdentidadField=new Ext.form.field.Text({
						        fieldLabel: 'N° Documento',
						        name: 'estudiante.textoNumeroDocumento',
						        labelWidth: 150,
						        anchor: '100%'
					        });
							
							var panelField=new Ext.form.Panel({
								height: 130,
								width : 600,
								bodyPadding: 5,
								items:[
									matriculaField,
									nombreField,
									documentoIdentidadField
								],
								buttons:[findButton]
							})
							var storeEstudiante=new Ext.data.Store({
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
						        		'codigoEspecialidad',
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
						        		'egresadoCiclo',
						        		'matricula',
						        		'detalle',
						        		'estudianteCodigo',
						        		'flagDiplomaSexo'
									],
									//autoLoad:true,
									pageSize: itemsPerPage, 
								    proxy: {
								        type: 'ajax',
								        url: 'gradoTitulo/getEstudianteRegistroList',
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
							            		"estudiante.textoMatricula":formTextoMatricula,
												"estudiante.textoNombreCompleto":formTextoNombreCompleto,
												"estudiante.textoNumeroDocumento":formTextoDocumento,
												"gradoAcademico.codigoExterno": formGradoAcademicoCodigoExterno,
												"flagCandado":formFlagCandado
							                });
							            }
							        }
							});
							var gridEstudianteDuplicado=new Ext.grid.Panel({
							    store: storeEstudiante,
							    xtype: 'grouped-header-grid',
							    columns: [
										{ text: 'N° Registro',  dataIndex: 'registroRegistro',flex:1},
										{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1, hidden: true},
										{ text: 'Grado o Titulo',  dataIndex: 'gradoTitulo',flex:1},
										{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
										{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
										{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
										{ text: 'Fecha de 1° MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
										{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
										{ text: 'Escuela Carrera',  dataIndex: 'escuelaCarrera',flex:1, hidden: true  },
										{ text: 'Especialidad POS',  dataIndex: 'especialidadPostgrado',flex:1, hidden: true  },
										{ text: 'Especialidad',  dataIndex: 'especialidad',flex:1},
										{ text: 'Matricula',  dataIndex: 'matricula',flex:1 },
										{ text: 'Apellido Paterno',  dataIndex: 'apellidoPaterno',flex:1 },
										{ text: 'Apellido Materno',  dataIndex: 'apellidoMaterno',flex:1 },
										{ text: 'Nombre',  dataIndex: 'nombre',flex:1 },
										{ text: 'Sexo',  dataIndex: 'sexo',flex:1, hidden: true },
										{ text: 'Documento Tipo',  dataIndex: 'documentoTipo',flex:1, hidden: true},
										{ text: 'Documento Numero',  dataIndex: 'documentoNumero',flex:1, hidden: true},
										{ text: 'Fecha de Egreso',  dataIndex: 'egresadoFecha',flex:1, hidden: true},
										{ text: 'Procedencia de Bachiller',  dataIndex: 'procedenciaBachiller',flex:1, hidden: true},
										{ text: 'Procedencia de Maestria',  dataIndex: 'procedenciaMaestria',flex:1, hidden: true},									{ text: 'Abrev Tipo Documeto',  dataIndex: 'abreviaturaGradoTitulo',flex:1, hidden: true},
										{ text: 'Mod Grado y Titulo',  dataIndex: 'actoTituloGrado',flex:1, hidden: true},
										{ text: 'Fecha de Sustentación',  dataIndex: 'actoFecha',flex:1, hidden: true},
										{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
										{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
										{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
										{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
										{ text: 'Denominación del GyT REV',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
										{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
										{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
										{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
										{ text: 'Numero de Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
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
										{ text: 'Ciclo de Egreso',  dataIndex: 'egresadoCiclo',flex:1, hidden: true},
										{ text: 'Doc sexo',  dataIndex: 'flagDiplomaSexo',flex:1, hidden: true}

								],
							    //border:true,
							    style: {borderColor: '#157fcc'},
							    dockedItems: [{
							        xtype: 'pagingtoolbar',
							        store: storeEstudiante, 
							        dock: 'bottom',
							        displayInfo: true
							    }],
							    listeners:{
							    	select:function(dv, record, index, eOpts ){
										console.log('selecccionado la matricula original'+record.get('matricula'));
										console.log('selecccionado el codigo original '+record.get('registroRegistro'));
										textoMatriculaOriginal=record.get('matricula');
										codigo=record.get('registroRegistro');
										codigoEspecialidad=record.get('codigoEspecialidad');
										fechaSustentacionTesis=record.get('actoFecha');
										textoNombreTesis=record.get('tesis');
										textoDetalle=record.get('detalle');
										estudianteCodigo=record.get('estudianteCodigo');
										flagDiplomaSexo = record.get( 'flagDiplomaSexo' );
									}
							    }
							    
							});
							
							var panelLoaderDuplicado= new Ext.panel.Panel({
								width: 600,
							    height: 380,
								layout:{
									type:'border'
								},
								items:[
									{
										region: 'north', height:130,width:'100%',border: true,items : [panelField]
									},
									{
										region: 'center',layout: 'fit',items : [gridEstudianteDuplicado]
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
							    items: [panelLoaderDuplicado],
							    buttons:[cancelarButton,addButton]
							    
							});
							/****************************/
							
							win.show()
						}
					})
					var panelViewDuplicado=new Ext.form.Panel({
						padding : '0 0 0 10',
						items:[btnView]
					})
					
					var panelEstudianteDuplicado = new Ext.panel.Panel({
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
						     	items:[panelViewDuplicado]
						    },
						]
					});
					/*****************************/
					
					
					
					items.push(panelEstudianteDuplicado)
					items.push(estudianteRegistoCodigo.hide())
					items.push(estudianteCodigoField.hide())
					items.push( flagDiplomaSexoField.hide() )
					items.push(tituloProfesionalField)
					items.push(decanoField)
					items.push(rectorField)
					items.push(secretarioGeneralField)
					items.push(sustentacionTesisField)
					items.push(nombreTesisField)
					items.push(detalleField)
					//items.push(addButton)
					//items.push(panelEstudiante)
					
				}else if(item=='matriculaMaestria'){
					console.log('tipoDocunento '+tipoDocunento );
					
					var textoMatriculaOriginal=null,
						codigo=null,
						codigoEspecialidad=null,
						fechaSustentacionTesis=null,
						textoDetalle=null,
						textoNombreTesis=null,
					 	estudianteCodigo=null,
						flagDiplomaSexo = null;

					
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
					
					var matriculaFindField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula',
				        name: 'estudiante.textoMatricula',
				        labelWidth: 150,
				        anchor: '100%'//,
				        //readOnly :true,
				        //allowBlank: false
			        });	
					var sustentacionTesisField=new Ext.form.field.Date({
				        fieldLabel: 'F/D de Sustentacion ',
				        name: 'fechaSustentacionTesis',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					var nombreTesisField=new Ext.form.field.Text({
				        fieldLabel: 'Nombre de Tesis',
				        name: 'textoNombreTesis',
				        allowBlank: false
			        });
					var detalleField=new Ext.form.field.TextArea({
				        fieldLabel: 'Detalle',
				        name: 'textoDetalle',
				        allowBlank: false,
				        height :50
			        });
					var estudianteRegistoCodigo=new Ext.form.field.Text({
				        name: "estudianteRegistroPadre"
			        });
					var estudianteCodigoField=new Ext.form.field.Text({
				        name: "estudiante.codigo"
			        });
					var panelMatricula=new Ext.form.Panel({
						items:[matriculaFindField]
					})
					
					var flagDiplomaSexoField = new Ext.form.field.Text({
				        name: "flagDiplomaSexo"
			        });
					
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
							
							function loadListParamRegistro(form){
								storeEstudiante.currentPage = 1;
								storeEstudiante.load({
									start: 0, 
									limit: itemsPerPage,
									params: {
										"estudiante.textoMatricula":form.textoMatricula,
										"estudiante.textoNombreCompleto":form.textoNombreCompleto,
										"gradoAcademico.codigoExterno": tipoDocunento,
										"estudiante.textoNumeroDocumento":form.textoDocumento,
										"flagCandado":"BuscarCerrado"
									}
								});	
							}	
							var cancelarButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-delete-icon',
								text : 'Cancelar',
								handler :function() {
									win.close();
									
								}
							});
							var findButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-search-icon',
								text : 'Buscar',
								handler :function() {
									formTextoMatricula=matriculaField.getValue(),
							 		formTextoNombreCompleto=nombreField.getValue(),
									formTextoDocumento=documentoIdentidadField.getValue(),
									formGradoAcademicoCodigoExterno=tipoDocunento,
									formFlagCandado="B";
									var form={
											textoMatricula:matriculaField.getValue(),
											textoNombreCompleto:nombreField.getValue(),
											textoDocumento:documentoIdentidadField.getValue(),									
											"gradoAcademico.codigoExterno": tipoDocunento,
											"flagCandado":"B"
										}	
									loadListParamRegistro(form);		
								}
							});
							
							var addButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-add-icon',
								text : 'Agregar',
						        handler: function() {
					        		console.info('selecciono textoMatriculaOriginal '+textoMatriculaOriginal);
						        	if(textoMatriculaOriginal!=null){
						        		matriculaFindField.setValue(textoMatriculaOriginal);
						        		//console.info('seleccion codigo '+codigo);
						        		estudianteRegistoCodigo.setValue(codigo);
						        		//estudianteRegistoCodigo.setValue(codigoEspecialidad);
						        		maestriaField.setValue(codigoEspecialidad);
						        		sustentacionTesisField.setValue(fechaSustentacionTesis);
						        		nombreTesisField.setValue(textoNombreTesis);
						        		detalleField.setValue(textoDetalle);
						        		estudianteCodigoField.setValue(estudianteCodigo);
						        		flagDiplomaSexoField.setValue( flagDiplomaSexo );
						        		win.close();
						        	}else{
						        		alert('Seleccione un registro Original')	
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
							
							var documentoIdentidadField=new Ext.form.field.Text({
						        fieldLabel: 'N° Documento',
						        name: 'estudiante.textoNumeroDocumento',
						        labelWidth: 150,
						        anchor: '100%'
					        });
							
							var panelField=new Ext.form.Panel({
								height: 130,
								width : 600,
								bodyPadding: 5,
								items:[
									matriculaField,
									nombreField,
									documentoIdentidadField
								],
								buttons:[findButton]
							})
							var storeEstudiante=new Ext.data.Store({
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
						        		'codigoEspecialidad',
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
						        		'egresadoCiclo',
						        		'matricula',
						        		'detalle',
						        		'estudianteCodigo',
						        		'flagDiplomaSexo'

									],
									//autoLoad:true,
									pageSize: itemsPerPage, 
								    proxy: {
								        type: 'ajax',
								        url: 'gradoTitulo/getEstudianteRegistroList',
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
							            		"estudiante.textoMatricula":formTextoMatricula,
												"estudiante.textoNombreCompleto":formTextoNombreCompleto,
												"estudiante.textoNumeroDocumento":formTextoDocumento,
												"gradoAcademico.codigoExterno": formGradoAcademicoCodigoExterno,
												"flagCandado":formFlagCandado
							                });
							            }
							        }
							});
							var gridEstudianteDuplicado=new Ext.grid.Panel({
							    store: storeEstudiante,
							    xtype: 'grouped-header-grid',
							    columns: [
										{ text: 'N° Registro',  dataIndex: 'registroRegistro',flex:1},
										{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1, hidden: true},
										{ text: 'Grado o Titulo',  dataIndex: 'gradoTitulo',flex:1},
										{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
										{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
										{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
										{ text: 'Fecha de 1° MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
										{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
										{ text: 'Escuela Carrera',  dataIndex: 'escuelaCarrera',flex:1, hidden: true  },
										{ text: 'Especialidad POS',  dataIndex: 'especialidadPostgrado',flex:1, hidden: true  },
										{ text: 'Especialidad',  dataIndex: 'especialidad',flex:1},
										{ text: 'Matricula',  dataIndex: 'matricula',flex:1 },
										{ text: 'Apellido Paterno',  dataIndex: 'apellidoPaterno',flex:1 },
										{ text: 'Apellido Materno',  dataIndex: 'apellidoMaterno',flex:1 },
										{ text: 'Nombre',  dataIndex: 'nombre',flex:1 },
										{ text: 'Sexo',  dataIndex: 'sexo',flex:1, hidden: true },
										{ text: 'Documento Tipo',  dataIndex: 'documentoTipo',flex:1, hidden: true},
										{ text: 'Documento Numero',  dataIndex: 'documentoNumero',flex:1, hidden: true},
										{ text: 'Fecha de Egreso',  dataIndex: 'egresadoFecha',flex:1, hidden: true},
										{ text: 'Procedencia de Bachiller',  dataIndex: 'procedenciaBachiller',flex:1, hidden: true},
										{ text: 'Procedencia de Maestria',  dataIndex: 'procedenciaMaestria',flex:1, hidden: true},									{ text: 'Abrev Tipo Documeto',  dataIndex: 'abreviaturaGradoTitulo',flex:1, hidden: true},
										{ text: 'Mod Grado y Titulo',  dataIndex: 'actoTituloGrado',flex:1, hidden: true},
										{ text: 'Fecha de Sustentación',  dataIndex: 'actoFecha',flex:1, hidden: true},
										{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
										{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
										{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
										{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
										{ text: 'Denominación del GyT REV',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
										{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
										{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
										{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
										{ text: 'Numero de Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
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
										{ text: 'Ciclo de Egreso',  dataIndex: 'egresadoCiclo',flex:1, hidden: true},
										{ text: 'Doc sexo',  dataIndex: 'flagDiplomaSexo',flex:1, hidden: true}

								],
							    //border:true,
							    style: {borderColor: '#157fcc'},
							    dockedItems: [{
							        xtype: 'pagingtoolbar',
							        store: storeEstudiante, 
							        dock: 'bottom',
							        displayInfo: true
							    }],
							    listeners:{
							    	select:function(dv, record, index, eOpts ){
										console.log('selecccionado la matricula original'+record.get('matricula'));
										console.log('selecccionado el codigo original '+record.get('registroRegistro'));
										textoMatriculaOriginal=record.get('matricula');
										codigo=record.get('registroRegistro');
										codigoEspecialidad=record.get('codigoEspecialidad');
										fechaSustentacionTesis=record.get('actoFecha');
										textoNombreTesis=record.get('tesis');
										textoDetalle=record.get('detalle');
										estudianteCodigo=record.get('estudianteCodigo');
										flagDiplomaSexo = record.get( 'flagDiplomaSexo' );

							    	}
							    }
							    
							});
							
							var panelLoaderDuplicado= new Ext.panel.Panel({
								width: 600,
							    height: 380,
								layout:{
									type:'border'
								},
								items:[
									{
										region: 'north', height:130,width:'100%',border: true,items : [panelField]
									},
									{
										region: 'center',layout: 'fit',items : [gridEstudianteDuplicado]
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
							    items: [panelLoaderDuplicado],
							    buttons:[cancelarButton,addButton]
							    
							});
							/****************************/
							
							win.show()
						}
					})
					var panelViewDuplicado=new Ext.form.Panel({
						padding : '0 0 0 10',
						items:[btnView]
					})
					
					var panelEstudianteDuplicado = new Ext.panel.Panel({
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
						     	items:[panelViewDuplicado]
						    },
						]
					});
					/*****************************/
					items.push(panelEstudianteDuplicado)
					items.push(estudianteRegistoCodigo.hide())
					items.push(estudianteCodigoField.hide())
					items.push( flagDiplomaSexoField.hide() )
					items.push(maestriaField)
					items.push(decanoField)
					items.push(rectorField)
					items.push(secretarioGeneralField)
					items.push(sustentacionTesisField)
					items.push(nombreTesisField)
					items.push(detalleField)
					//items.push(addButton)
					//items.push(panelEstudiante)
					
				}else if(item=='matriculaDoctorado'){
					console.log('tipoDocunento '+tipoDocunento );
					
					var textoMatriculaOriginal=null,
						codigo=null,
						codigoEspecialidad=null,
						fechaSustentacionTesis=null,
						textoDetalle=null,
						textoNombreTesis=null,
						estudianteCodigo=null,
						flagDiplomaSexo = null;

					
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
					
					var matriculaFindField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula',
				        name: 'estudiante.textoMatricula',
				        labelWidth: 150,
				        anchor: '100%'//,
				        //readOnly :true,
				        //allowBlank: false
			        });	
					var sustentacionTesisField=new Ext.form.field.Date({
				        fieldLabel: 'F/D de Sustentacion ',
				        name: 'fechaSustentacionTesis',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					var nombreTesisField=new Ext.form.field.Text({
				        fieldLabel: 'Nombre de Tesis',
				        name: 'textoNombreTesis',
				        allowBlank: false
			        });
					var detalleField=new Ext.form.field.TextArea({
				        fieldLabel: 'Detalle',
				        name: 'textoDetalle',
				        allowBlank: false,
				        height :50
			        });
					var estudianteRegistoCodigo=new Ext.form.field.Text({
				        name: "estudianteRegistroPadre"
			        });
					var estudianteCodigoField=new Ext.form.field.Text({
				        name: "estudiante.codigo"
			        });
					var panelMatricula=new Ext.form.Panel({
						items:[matriculaFindField]
					})
					
					var flagDiplomaSexoField = new Ext.form.field.Text({
				        name: "flagDiplomaSexo"
			        });
					
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
							
							function loadListParamRegistro(form){
								storeEstudiante.currentPage = 1;
								storeEstudiante.load({
									start: 0, 
									limit: itemsPerPage,
									params: {
										"estudiante.textoMatricula":form.textoMatricula,
										"estudiante.textoNombreCompleto":form.textoNombreCompleto,
										"gradoAcademico.codigoExterno": tipoDocunento,
										"estudiante.textoNumeroDocumento":form.textoDocumento,
										"flagCandado":"BuscarCerrado"
									}
								});	
							}	
							var cancelarButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-delete-icon',
								text : 'Cancelar',
								handler :function() {
									win.close();
									
								}
							});
							var findButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-search-icon',
								text : 'Buscar',
								handler :function() {
									formTextoMatricula=matriculaField.getValue(),
							 		formTextoNombreCompleto=nombreField.getValue(),
									formTextoDocumento=documentoIdentidadField.getValue(),
									formGradoAcademicoCodigoExterno=tipoDocunento,
									formFlagCandado="B";
									var form={
											textoMatricula:matriculaField.getValue(),
											textoNombreCompleto:nombreField.getValue(),
											textoDocumento:documentoIdentidadField.getValue(),									
											"gradoAcademico.codigoExterno": tipoDocunento,
											"flagCandado":"B"
										}	
									loadListParamRegistro(form);		
								}
							});
							
							var addButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-add-icon',
								text : 'Agregar',
						        handler: function() {
					        		console.info('selecciono textoMatriculaOriginal '+textoMatriculaOriginal);
						        	if(textoMatriculaOriginal!=null){
						        		matriculaFindField.setValue(textoMatriculaOriginal);
						        		//console.info('seleccion codigo '+codigo);
						        		estudianteRegistoCodigo.setValue(codigo);
						        		//estudianteRegistoCodigo.setValue(codigoEspecialidad);
						        		doctoradoField.setValue(codigoEspecialidad);
						        		sustentacionTesisField.setValue(fechaSustentacionTesis);
						        		nombreTesisField.setValue(textoNombreTesis);
						        		detalleField.setValue(textoDetalle);
						        		estudianteCodigoField.setValue(estudianteCodigo);
						        		console.log("agregar flagDiplomaSexo "+flagDiplomaSexo)
						        		flagDiplomaSexoField.setValue( flagDiplomaSexo );

						        		win.close();
						        	}else{
						        		alert('Seleccione un registro Original')	
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
							
							var documentoIdentidadField=new Ext.form.field.Text({
						        fieldLabel: 'N° Documento',
						        name: 'estudiante.textoNumeroDocumento',
						        labelWidth: 150,
						        anchor: '100%'
					        });
							
							var panelField=new Ext.form.Panel({
								height: 130,
								width : 600,
								bodyPadding: 5,
								items:[
									matriculaField,
									nombreField,
									documentoIdentidadField
								],
								buttons:[findButton]
							})
							var storeEstudiante=new Ext.data.Store({
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
						        		'codigoEspecialidad',
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
						        		'egresadoCiclo',
						        		'matricula',
						        		'detalle',
						        		'estudianteCodigo',
						        		'flagDiplomaSexo'

									],
									//autoLoad:true,
									pageSize: itemsPerPage, 
								    proxy: {
								        type: 'ajax',
								        url: 'gradoTitulo/getEstudianteRegistroList',
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
							            		"estudiante.textoMatricula":formTextoMatricula,
												"estudiante.textoNombreCompleto":formTextoNombreCompleto,
												"estudiante.textoNumeroDocumento":formTextoDocumento,
												"gradoAcademico.codigoExterno": formGradoAcademicoCodigoExterno,
												"flagCandado":formFlagCandado
							                });
							            }
							        }
							});
							var gridEstudianteDuplicado=new Ext.grid.Panel({
							    store: storeEstudiante,
							    xtype: 'grouped-header-grid',
							    columns: [
										{ text: 'N° Registro',  dataIndex: 'registroRegistro',flex:1},
										{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1, hidden: true},
										{ text: 'Grado o Titulo',  dataIndex: 'gradoTitulo',flex:1},
										{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
										{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
										{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
										{ text: 'Fecha de 1° MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
										{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
										{ text: 'Escuela Carrera',  dataIndex: 'escuelaCarrera',flex:1, hidden: true  },
										{ text: 'Especialidad POS',  dataIndex: 'especialidadPostgrado',flex:1, hidden: true  },
										{ text: 'Especialidad',  dataIndex: 'especialidad',flex:1},
										{ text: 'Matricula',  dataIndex: 'matricula',flex:1 },
										{ text: 'Apellido Paterno',  dataIndex: 'apellidoPaterno',flex:1 },
										{ text: 'Apellido Materno',  dataIndex: 'apellidoMaterno',flex:1 },
										{ text: 'Nombre',  dataIndex: 'nombre',flex:1 },
										{ text: 'Sexo',  dataIndex: 'sexo',flex:1, hidden: true },
										{ text: 'Documento Tipo',  dataIndex: 'documentoTipo',flex:1, hidden: true},
										{ text: 'Documento Numero',  dataIndex: 'documentoNumero',flex:1, hidden: true},
										{ text: 'Fecha de Egreso',  dataIndex: 'egresadoFecha',flex:1, hidden: true},
										{ text: 'Procedencia de Bachiller',  dataIndex: 'procedenciaBachiller',flex:1, hidden: true},
										{ text: 'Procedencia de Maestria',  dataIndex: 'procedenciaMaestria',flex:1, hidden: true},									{ text: 'Abrev Tipo Documeto',  dataIndex: 'abreviaturaGradoTitulo',flex:1, hidden: true},
										{ text: 'Mod Grado y Titulo',  dataIndex: 'actoTituloGrado',flex:1, hidden: true},
										{ text: 'Fecha de Sustentación',  dataIndex: 'actoFecha',flex:1, hidden: true},
										{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
										{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
										{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
										{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
										{ text: 'Denominación del GyT REV',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
										{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
										{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
										{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
										{ text: 'Numero de Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
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
										{ text: 'Ciclo de Egreso',  dataIndex: 'egresadoCiclo',flex:1, hidden: true},
										{ text: 'Doc sexo',  dataIndex: 'flagDiplomaSexo',flex:1, hidden: true}

								],
							    //border:true,
							    style: {borderColor: '#157fcc'},
							    dockedItems: [{
							        xtype: 'pagingtoolbar',
							        store: storeEstudiante, 
							        dock: 'bottom',
							        displayInfo: true
							    }],
							    listeners:{
							    	select:function(dv, record, index, eOpts ){
										console.log('selecccionado la matricula original'+record.get('matricula'));
										console.log('selecccionado el codigo original '+record.get('registroRegistro'));
										textoMatriculaOriginal=record.get('matricula');
										codigo=record.get('registroRegistro');
										codigoEspecialidad=record.get('codigoEspecialidad');
										fechaSustentacionTesis=record.get('actoFecha');
										textoNombreTesis=record.get('tesis');
										textoDetalle=record.get('detalle');
										estudianteCodigo=record.get('estudianteCodigo');
										flagDiplomaSexo = record.get( 'flagDiplomaSexo' );

									}
							    }
							    
							});
							
							var panelLoaderDuplicado= new Ext.panel.Panel({
								width: 600,
							    height: 380,
								layout:{
									type:'border'
								},
								items:[
									{
										region: 'north', height:130,width:'100%',border: true,items : [panelField]
									},
									{
										region: 'center',layout: 'fit',items : [gridEstudianteDuplicado]
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
							    items: [panelLoaderDuplicado],
							    buttons:[cancelarButton,addButton]
							    
							});
							/****************************/
							
							win.show()
						}
					})
					var panelViewDuplicado=new Ext.form.Panel({
						padding : '0 0 0 10',
						items:[btnView]
					})
					
					var panelEstudianteDuplicado = new Ext.panel.Panel({
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
						     	items:[panelViewDuplicado]
						    },
						]
					});
					/*****************************/
					items.push(panelEstudianteDuplicado)
					items.push(estudianteRegistoCodigo.hide())
					items.push(estudianteCodigoField.hide())
					items.push( flagDiplomaSexoField.hide() )
					items.push(doctoradoField)
					items.push(decanoField)
					items.push(rectorField)
					items.push(secretarioGeneralField)
					items.push(sustentacionTesisField)
					items.push(nombreTesisField)
					items.push(detalleField)
					//items.push(addButton)
					//items.push(panelEstudiante)
					
				}else if ( item == 'programaEstudio' ){
					var programaEstudioCombo=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Programa de Estudios',
				        name: "programaEstudio.codigo",
						store: storeProgramaEstudio,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
 						allowBlank: false,
						editable:false,
						listeners: {
					        change: function(field, selectedValue) {
					           console.log('selectedValue: '+field.getRawValue())
					           if( field.getRawValue() == "OTROS" ){
					        	   nombreProgramaEstudioField.setDisabled(false);
					           }
					        }
					    },
						listConfig : {
							dirtychange:function(){
								console.log('dirtychange ')
							},
							listeners : {
								itemclick : function(list,record) {
									nombreTipoDocumento =record.get('textoNombre');
									console.log('nombreTipoDocumento '+nombreTipoDocumento)
									if(nombreTipoDocumento=="OTROS"){
										nombreProgramaEstudioField.setDisabled(false);
									}else{
										nombreProgramaEstudioField.setDisabled(true);
									}
								},
						
							}
											
						}
					
					});
					var nombreProgramaEstudioField=new Ext.form.field.Text({
				        fieldLabel: 'Nombre del PROGRAMA',
				        name: 'textoNombreProgramaEstudio',
				       	allowBlank: false
			        });
					items.push(programaEstudioCombo)
					items.push(nombreProgramaEstudioField.setDisabled(true))
					
				}else if ( item == 'segundaEspecialidad' ){
					var segundaEspecialidadField=new Ext.form.field.Text({
				        fieldLabel: 'Segunda Especialidad',
				        name: 'textoSegundaEspecialidad'
				        //allowBlank: false
			        });
					items.push(segundaEspecialidadField.setDisabled(true))
				}else if ( item == 'numeroCreditos' ){
					var numeroCreditosField=new Ext.form.field.Number({
						name: 'numeroCreditos',
				        fieldLabel: 'N° Creditos',
				        minValue: 0
			        });
					items.push(numeroCreditosField)
				}else if ( item == 'registroMetadato'){
					var registroMetadatoField=new Ext.form.field.Text({
				        fieldLabel: 'Registro Metadato',
				        name: 'textoRegistroMetadato'
// 				        allowBlank: false
			        });
// 					items.push(registroMetadatoField.setDisabled(true))
					items.push(registroMetadatoField)
				}else if ( item == 'procedenciaTituloPedagogico'){
					var procedenciaTituloPedagogicoField=new Ext.form.field.Text({
				        fieldLabel: 'Procedencia TIT PDGC',
				        name: 'textoProcedenciaTituloPedagogico'
				        //allowBlank: false
			        });
					items.push(procedenciaTituloPedagogicoField.setDisabled(true))
				}else if ( item == 'fechaDiplomaDuplicado'){
					var fechaDiplomaDuplicadoField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha Diploma Duplicado',
				        name: 'fechaDiplomaDuplicado',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					items.push(fechaDiplomaDuplicadoField)
					
				}else if ( item == 'procedenciaGradoExtranjero'){
					var procedenciaGradoExtranjeroField=new Ext.form.field.Text({
				        fieldLabel: 'Procedencia Grado EXT',
				        name: 'textoProcedenciaGradoExtranjero'
				        //allowBlank: false
			        });
					items.push(procedenciaGradoExtranjeroField)
					
				}else if ( item == 'fechaMatriculaPrograma'){
					var fechaMatriculaProgramaField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha Matricula Programa',
				        name: 'fechaMatriculaPrograma',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					items.push(fechaMatriculaProgramaField.setDisabled(true))
					
				}else if ( item == 'fechaInicioPrograma'){
					var fechaInicioProgramaField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha Inicio Programa',
				        name: 'fechaInicioPrograma',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					items.push(fechaInicioProgramaField.setDisabled(true))
				}else if ( item == 'fechaTerminoPrograma'){
					var fechaTerminoProgramaField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha Termino Programa',
				        name: 'fechaTerminoPrograma',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: false
			        });
					items.push(fechaTerminoProgramaField.setDisabled(true))
				}else if(item=='matricula'){
					console.log('tipoDocunento '+tipoDocunento );
					var textoMatriculaOriginal=null,
						codigo=null,
						codigoEspecialidad=null,
						estudianteCodigo=null;
					
					var matriculaFindField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula',
				        name: 'estudiante.textoMatricula',
				        labelWidth: 150,
				        anchor: '100%'//,
				        //readOnly :true,
				        //allowBlank: false
			        });	
					var estudianteRegistoCodigo=new Ext.form.field.Text({
				        name: "estudianteRegistroPadre"
			        });
					
					var estudianteCodigoField=new Ext.form.field.Text({
				        name: "estudiante.codigo"
			        });
					var panelMatricula=new Ext.form.Panel({
						items:[matriculaFindField]
					})
					var btnView=new Ext.button.Button({
						height : 24,
						iconCls : 'btn-lupa-icon',
						handler : function(){
							function loadListParamRegistro(form){
								storeEstudiante.load({
									start: 0, 
									limit: itemsPerPage,
									params: {
										"estudiante.textoMatricula":form.textoMatricula,
										"estudiante.textoNombreCompleto":form.textoNombreCompleto,
										"gradoAcademico.codigoExterno": tipoDocunento,
										"estudiante.textoNumeroDocumento":form.textoDocumento,
										"flagCandado":"BuscarCerrado"
									}
								});	
							}	
							var cancelarButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-delete-icon',
								text : 'Cancelar',
								handler :function() {
									win.close();
									
								}
							});
							var findButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-search-icon',
								text : 'Buscar',
								handler :function() {
									var form={
											textoMatricula:matriculaField.getValue(),
											textoNombreCompleto:nombreField.getValue(),
											textoDocumento:documentoIdentidadField.getValue(),									
											"gradoAcademico.codigoExterno": tipoDocunento,
											"flagCandado":"B"
										}	
									loadListParamRegistro(form);		
								}
							});
							
							var addButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-add-icon',
								text : 'Agregar',
						        handler: function() {
					        		console.info('selecciono textoMatriculaOriginal '+textoMatriculaOriginal);
						        	if(textoMatriculaOriginal!=null){
						        		matriculaFindField.setValue(textoMatriculaOriginal);
						        		//console.info('seleccion codigo '+codigo);
						        		estudianteRegistoCodigo.setValue(codigo);
						        		estudianteCodigoField.setValue(estudianteCodigo);
						        		//estudianteRegistoCodigo.setValue(codigoEspecialidad);
						        		
						        		win.close();
						        	}else{
						        		alert('Seleccione un registro Original')	
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
							var documentoIdentidadField=new Ext.form.field.Text({
						        fieldLabel: 'N° Documento',
						        name: 'estudiante.textoNumeroDocumento',
						        labelWidth: 150,
						        anchor: '100%'
					        });
							var panelField=new Ext.form.Panel({
								height: 130,
								width : 600,
								bodyPadding: 5,
								items:[
									matriculaField,
									nombreField,
									documentoIdentidadField
								],
								buttons:[findButton]
							})
							var storeEstudiante=new Ext.data.Store({
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
						        		'codigoEspecialidad',
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
						        		'egresadoCiclo',
						        		'matricula',
						        		'detalle',
						        		'estudianteCodigo'
									],
									//autoLoad:true,
									pageSize: itemsPerPage, 
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
							var gridEstudianteDuplicado=new Ext.grid.Panel({
							    store: storeEstudiante,
							    xtype: 'grouped-header-grid',
							    columns: [
											{ text: 'N° Registro',  dataIndex: 'registroRegistro',flex:1},
											{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1, hidden: true},
											{ text: 'Grado o Titulo',  dataIndex: 'gradoTitulo',flex:1},
											{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
											{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
											{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
											{ text: 'Fecha de 1° MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
											{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
											{ text: 'Escuela Carrera',  dataIndex: 'escuelaCarrera',flex:1, hidden: true  },
											{ text: 'Especialidad POS',  dataIndex: 'especialidadPostgrado',flex:1, hidden: true  },
											{ text: 'Especialidad',  dataIndex: 'especialidad',flex:1},
											{ text: 'Matricula',  dataIndex: 'matricula',flex:1 },
											{ text: 'Apellido Paterno',  dataIndex: 'apellidoPaterno',flex:1 },
											{ text: 'Apellido Materno',  dataIndex: 'apellidoMaterno',flex:1 },
											{ text: 'Nombre',  dataIndex: 'nombre',flex:1 },
											{ text: 'Sexo',  dataIndex: 'sexo',flex:1, hidden: true },
											{ text: 'Documento Tipo',  dataIndex: 'documentoTipo',flex:1, hidden: true},
											{ text: 'Documento Numero',  dataIndex: 'documentoNumero',flex:1, hidden: true},
											{ text: 'Fecha de Egreso',  dataIndex: 'egresadoFecha',flex:1, hidden: true},
											{ text: 'Procedencia de Bachiller',  dataIndex: 'procedenciaBachiller',flex:1, hidden: true},
											{ text: 'Procedencia de Maestria',  dataIndex: 'procedenciaMaestria',flex:1, hidden: true},									{ text: 'Abrev Tipo Documeto',  dataIndex: 'abreviaturaGradoTitulo',flex:1, hidden: true},
											{ text: 'Mod Grado y Titulo',  dataIndex: 'actoTituloGrado',flex:1, hidden: true},
											{ text: 'Fecha de Sustentación',  dataIndex: 'actoFecha',flex:1, hidden: true},
											{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
											{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
											{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
											{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
											{ text: 'Denominación del GyT REV',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
											{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
											{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
											{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
											{ text: 'Numero de Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
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
								],
							    //border:true,
							    style: {borderColor: '#157fcc'},
							    dockedItems: [{
							        xtype: 'pagingtoolbar',
							        store: storeEstudiante, 
							        dock: 'bottom',
							        displayInfo: true
							    }],
							    listeners:{
							    	select:function(dv, record, index, eOpts ){
										textoMatriculaOriginal=record.get('matricula');
										codigo=record.get('registroRegistro');
										codigoEspecialidad=record.get('codigoEspecialidad');
										estudianteCodigo=record.get('estudianteCodigo');
									}
							    }
							    
							});
							
							var panelLoaderDuplicado= new Ext.panel.Panel({
								width: 600,
							    height: 380,
								layout:{
									type:'border'
								},
								items:[
									{
										region: 'north', height:130,width:'100%',border: true,items : [panelField]
									},
									{
										region: 'center',layout: 'fit',items : [gridEstudianteDuplicado]
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
							    items: [panelLoaderDuplicado],
							    buttons:[cancelarButton,addButton]
							    
							});
							/****************************/
							
							win.show()
						}
					})
					var panelViewDuplicado=new Ext.form.Panel({
						padding : '0 0 0 10',
						items:[btnView]
					})
					
					var panelEstudianteDuplicado = new Ext.panel.Panel({
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
						     	items:[panelViewDuplicado]
						    },
						]
					});
					/*****************************/
					items.push(panelEstudianteDuplicado)
					items.push(estudianteRegistoCodigo.hide())
					items.push(estudianteCodigoField.hide())
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
							'matriculaBachiller',
							//'gradoBachiller',
							//'textoResolucionFacultad',
							//'fechaResolucionFacultad',
							'resolucionRectoral',
							'aprobacioncu',
							//'sustentacionTesis',
							//'nombreTrabajoInvestigacion',
							'modalidadGradoTitulo',
							'modalidadEstudio',
							'folio',
							'libro',
							'codigoBarra',
							'agendaGrupo',
							'resolucionCambioNombre',
							'adjunto',
							'adjuntoFoto'
						]})
						
					var fieldsTwo=getUxFields({
						items:[

						    //nuevo
// 						    'programaEstudio',
						    'segundaEspecialidad',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',
							'fechaMatriculaPrograma',
							'fechaInicioPrograma',
							'fechaTerminoPrograma'
						]
					})
					console.info({fields:fields})
					
					var formPanelToLoad=new Ext.form.Panel({
						items: fields
						
					})
					var formPanelTwoToLoad=new Ext.form.Panel({
						items: fieldsTwo
						
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
// 					panelLoader.add(formPanelToLoad);
					panelLoader.add({
				        columnWidth: 0.5,
				        items:[
							formPanelToLoad
						]
				    },
				    {
				        columnWidth: 0.5,
				        items:[
							formPanelTwoToLoad
						]
				    });
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
							'matriculaTitulo',
							//'tituloProfesional',
							//'textoResolucionFacultad',
							//'fechaResolucionFacultad',
							'resolucionRectoral',
							'aprobacioncu',
							//'sustentacionTesis',
							//'nombreTesis',
							//'detalle',
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
							'resolucionCambioNombre'
// 							,'adjunto',
// 							'adjuntoFoto'
							
						       /*
							'codigoRegistro',
							'TIT',
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
							'rector',
							'secretarioGeneral',
							'decano',
							'folio',
							'libro',
							//'registro',
							'codigoBarra',
						    'agendaGrupo',
						    'adjunto'
						    */
						]
					})
					var fieldsTwo=getUxFields({
						items:[

						    //nuevo
						    'adjunto',
							'adjuntoFoto',
// 						    'programaEstudio',
						    'segundaEspecialidad',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',
							'fechaMatriculaPrograma',
							'fechaInicioPrograma',
							'fechaTerminoPrograma'
						]
					})
					console.info({fields:fields})
					var formPanelToLoad=new Ext.form.Panel({
						items:fields
					})
					var formPanelTwoToLoad=new Ext.form.Panel({
						items: fieldsTwo
						
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
// 					panelLoader.add(formPanelToLoad);
					panelLoader.add({
				        columnWidth: 0.5,
				        items:[
							formPanelToLoad
						]
				    },
				    {
				        columnWidth: 0.5,
				        items:[
							formPanelTwoToLoad
						]
				    });
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
							'matriculaMaestria',
							//'maestria',
							//'matriculaPost',
							//'resolucionEpg',
							'resolucionRectoral',
							'aprobacioncu',
							//'sustentacionTesis',
							//'nombreTesis',
							//'detalle',
							//'pais',
							'universidadBachiller',
							'modalidadGradoTitulo',
							//'rector',
							//'secretarioGeneral',
							//'directorPost',
							'folio',
							'libro',
							//'registro',
							'codigoBarra'
// 							,'agendaGrupo',
// 							'resolucionCambioNombre'
// 							,'adjunto',
// 							'adjuntoFoto'
						      /* 
							'codigoRegistro',
							'MT',
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
							'rector',
							'secretarioGeneral',
							'directorPost',
							'folio',
							'libro',
							//'registro',
							'codigoBarra',
						    'agendaGrupo',
						    'adjunto'
						    */
						]
					})
					var fieldsTwo=getUxFields({
						items:[
							'agendaGrupo',
							'resolucionCambioNombre',
						    //nuevo
						    'adjunto',
							'adjuntoFoto',
// 						    'programaEstudio',
						    'segundaEspecialidad',
							'numeroCreditos',
							'registroMetadato',
							'fechaEgresoPosgrado',
							'fechaMatriculaPosgrado',
							'procedenciaTituloPedagogico',
							'fechaMatriculaPrograma',
							'fechaInicioPrograma',
							'fechaTerminoPrograma'
						]
					})
					var formPanelToLoad=new Ext.panel.Panel({
						items:fields
					})
					var formPanelTwoToLoad=new Ext.form.Panel({
						items: fieldsTwo
						
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
// 					panelLoader.add(formPanelToLoad);
					panelLoader.add({
				        columnWidth: 0.5,
				        items:[
							formPanelToLoad
						]
				    },
				    {
				        columnWidth: 0.5,
				        items:[
							formPanelTwoToLoad
						]
				    });
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
							'matriculaDoctorado',
							//'doctorado',
							//'matriculaPost',
							//'resolucionEpg',
							'resolucionRectoral',
							'aprobacioncu',
							//'sustentacionTesis',
							//'nombreTesis',
							//'detalle',
							//'pais',
							'universidadBachiller',
							'universidadMaestria',
							'modalidadGradoTitulo',
							//'rector',
							//'secretarioGeneral',
							//'directorPost',
// 							'libro',
// 							'codigoBarra',
// 							'agendaGrupo',
// 							'resolucionCambioNombre'
// 							,'adjunto'    ,
// 							'adjuntoFoto'
						       
						       /*
							'codigoRegistro',
							'DOC',
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
							'rector',
							'secretarioGeneral',
							'directorPost',
							'folio',
							'libro',
							//'registro',
							'codigoBarra',
						    'agendaGrupo',
						    'adjunto'
						    */
						]
					})
					var fieldsTwo=getUxFields({
						items:[
							'folio',
							'libro',
							'codigoBarra',
							'agendaGrupo',
							'resolucionCambioNombre',
						    //nuevo
						    'adjunto',
							'adjuntoFoto',
// 						    'programaEstudio',
						    'segundaEspecialidad',
							'numeroCreditos',
							'registroMetadato',
							'fechaEgresoPosgrado',
							'fechaMatriculaPosgrado',
							'procedenciaTituloPedagogico',
							'fechaMatriculaPrograma',
							'fechaInicioPrograma',
							'fechaTerminoPrograma'
						]
					})
					var formPanelToLoad=new Ext.panel.Panel({
						items:fields
					})
					var formPanelTwoToLoad=new Ext.form.Panel({
						items: fieldsTwo
						
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
// 					panelLoader.add(formPanelToLoad);
					panelLoader.add({
				        columnWidth: 0.5,
				        items:[
							formPanelToLoad
						]
				    },
				    {
				        columnWidth: 0.5,
				        items:[
							formPanelTwoToLoad
						]
				    });
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
// 			height: 730,
// 			width : 450
			width: 950,
		    height: 550,
		    layout:'column'
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
							url:'duplicado/getDuplicado',
							params:{
								codigo:uxData.codigo
							}
						})
					}else if(uxData&&uxData.action=='view'){
						console.log('uxData.tipo'+uxData.tipo);
						tipoDiplomaLabel.setValue(uxData.tipo);
						loaderConfig[uxData.tipo].load();
						//agregarPanel.removeAll(true)
						agregarPanel.add({
							buttons:[saveButton]
						})
						agregarPanel.load({
							url:'duplicado/getDuplicado',
							params:{
								codigo:uxData.codigo,
							}
						})
						//agregarPanel.doLayout();
					}
				
				}
			}
		});
		
		APP.Portal.Duplicado.Load.GradoTituloWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.GradoTituloWindowsMantenimiento,Ext.window.Window,{});
	
	APP.Portal.Duplicado.Load.GradoTituloWindowsClose=function(config){
		
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
			iconCls : 'btn-delete-icon',
			text : 'Cancelar',
			handler :function() {
				me.close();
				
			}
		});
		
		saveButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-register-icon',
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
		                url: 'duplicado/close',
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
		                	 var globalPanel=APP.Portal.Duplicado.Load.GlobalPanel;
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
		APP.Portal.Duplicado.Load.GradoTituloWindowsClose.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.GradoTituloWindowsClose,Ext.window.Window,{});
	
	APP.Portal.Duplicado.Load.GradoTituloFiltro=function(config){
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
						var globalPanel=APP.Portal.Duplicado.Load.GlobalPanel;
						
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
			        valueField: 'codigoExternoDocumento',
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
						
						var globalPanel=APP.Portal.Duplicado.Load.GlobalPanel,
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
		APP.Portal.Duplicado.Load.GradoTituloFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.GradoTituloFiltro,APP.form.field.Picker,{});
	
	APP.Portal.Duplicado.Load.GradoTituloToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.Duplicado.Load.GradoTituloFiltro({
			
		});
		var mantenimiento = new APP.Portal.Duplicado.Load.GradoTituloWindowsMantenimiento({
			
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
					iconCls : 'btn-candado-icon',
					cls : 'btn',
					handler:function(){
						
						var win = new APP.Portal.Duplicado.Load.GradoTituloWindowsClose({
							title: 'Seleccionar'
						});
						win.show();
					}
				},
				'->',
				{
					text : 'Reporte de busqueda',
					height : 30,
					iconCls : 'btn-export-icon',
					cls : 'btn',
					handler:function(){
						console.log("formExcel "+formExcel);
						//console.log("formExcel.toSource() "+formExcel.toSource());
						if(formExcel != ''){
							window.open(
								"duplicado/excel?estudiante.textoMatricula="+formExcel.matricula
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
					iconCls : 'btn-add-icon',
					cls : 'btn',
					handler:function(){
						var win = new APP.Portal.Duplicado.Load.GradoTituloWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.Duplicado.Load.GradoTituloToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.GradoTituloToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.Duplicado.Load.GradoTituloList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
		var itemsPerPage = 25;	
		var formMatricula,
		formNombreCompleto,
		formCodigoExternoDocumento,
		formNumeroDocumento,
		formFolio,
		formLibro,
		formRegistro,
		formFechaInicioCu,
		formFechaFinalCu,
		formTipoRegistro,
		formAgendaGrupo,
		formFlagCandado;
		var fechaFormat='1000/01/01';
		toolbar=new APP.Portal.Duplicado.Load.GradoTituloToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
		
		me.loadListParam=function(form){
			console.log('form.fechaInicioCu '+form.fechaInicioCu);
			console.log('form.fechaFinalCu '+form.fechaFinalCu);
			
			if(isEmpty(form.fechaInicioCu)){
				form.fechaInicioCu=fechaFormat;
			}
			if(isEmpty(form.fechaFinalCu)){
				form.fechaFinalCu=fechaFormat;
			}
			formMatricula=form.matricula;
			formNombreCompleto=form.nombreCompleto,
			formCodigoExternoDocumento=form.codigoExternoDocumento,
			formNumeroDocumento=form.numeroDocumento,
			formFolio=form.folio,
			formLibro=form.libro,
			formRegistro=form.registro,
			formFechaInicioCu=form.fechaInicioCu,
			formFechaFinalCu=form.fechaFinalCu,
			formTipoRegistro=form.tipoRegistro,
			formAgendaGrupo=form.agendaGrupo,
			formFlagCandado ="B";
		 	store.currentPage = 1;
			store.load({
				start: 0, 
				limit: itemsPerPage,
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
			store.currentPage = 1;
			store.load({
				start: 0, 
				limit: itemsPerPage	
			});
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
				url : 'duplicado/delete',
				method: 'POST',
				params: {
					codigo:codigo
				},
				success: function (result, request){
					store.load({
						start: 0, 
						limit: itemsPerPage
					});
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
				url : 'duplicado/enviarSunedu',
				method: 'POST',
				params: {
					codigo:codigo
				},
				success: function (result, request){
					store.load({
						start: 0, 
						limit: itemsPerPage
					});
				},
				failure: function (result, request){
					alert('Error in server' + result.responseText);
				}
			});
			
			
		}
		function editar(record){
			var codigo=record.get('codigo');
			var tipo=record.get('abreviaturaGradoTitulo');
			var win = new APP.Portal.Duplicado.Load.GradoTituloWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					tipo:tipo,
					action:'edit'
				}
			});
			win.show();
			
		}
		function visualizar(record){
			var codigo=record.get('codigo');
			var tipo=record.get('abreviaturaGradoTitulo');
			var win = new APP.Portal.Duplicado.Load.GradoTituloWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					tipo:tipo,
					action:'view'
				}
			});
			win.show();
			
		}
		if(isEmpty(formFechaInicioCu)){
			formFechaInicioCu=fechaFormat;
		}
		if(isEmpty(formFechaFinalCu)){
			formFechaFinalCu=fechaFormat;
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
        		'estudianteRegistroPadre',
        		'anulado',
        		'egresadoCiclo',
        		'resolucionCambioNombre',
        		'flagDiplomaSexo',
        		'textoProcedenciaMaestriaExtranjero'
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
			pageSize: itemsPerPage, 
		    proxy: {
		        type: 'ajax',
		        url: 'duplicado/getDuplicadoList',
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
	            		"estudiante.textoMatricula":formMatricula,
	            		"estudiante.textoNombreCompleto":formNombreCompleto,
						"estudiante.textoCodigoExternoDocumento":formCodigoExternoDocumento,
						"estudiante.textoNumeroDocumento":formNumeroDocumento,
						"numeroFolio":formFolio,
						"numeroLibro":formLibro,
						"numeroRegistro":formRegistro,
						"fechaCierreInicial":formFechaInicioCu,
						"fechaCierreFinal":formFechaFinalCu,
						"gradoAcademico.textoNombre":formTipoRegistro,
						"agendaGrupo.textoNombre":formAgendaGrupo,
						"flagCandado":formFlagCandado

	                });
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
				{ text: 'Grado o Titulo',  dataIndex: 'gradoTitulo',flex:1},
				{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
				{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
				{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
				{ text: 'Fecha de 1° MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
				{ text: 'Facultad',  dataIndex: 'facultadNombre',flex:1 },
				{ text: 'Escuela Carrera',  dataIndex: 'escuelaCarrera',flex:1 },
				{ text: 'Escuela POS',  dataIndex: 'especialidadPostgrado',flex:1 },
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
				{ text: 'Fecha de Sustentación',  dataIndex: 'actoFecha',flex:1, hidden: true},
				{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
				{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
				{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
				{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
				{ text: 'Denominación del GyT REV',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
				{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
				{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
				{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
				{ text: 'Numero de Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
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
				{ text: 'N° registro original',  dataIndex: 'estudianteRegistroPadre',flex:1, hidden: true},
				{ text: 'Anulado',  dataIndex: 'anulado',flex:1, hidden: true},
				{ text: 'Ciclo de Egreso',  dataIndex: 'egresadoCiclo',flex:1, hidden: true},
				{ text: 'Resolucion C.N.',  dataIndex: 'resolucionCambioNombre',flex:1, hidden: true},
				{ text: 'Doc sexo',  dataIndex: 'flagDiplomaSexo',flex:1, hidden: true},
				{ text: 'Proc MA EXT',  dataIndex: 'textoProcedenciaMaestriaExtranjero',flex:1, hidden: true},
				//{ text: 'registroOficio',  dataIndex: 'registroOficio',flex:1, hidden: true},
				//{ text: 'Tipo Documento',  dataIndex: 'gradoAcademico',flex:1, hidden: true}
				
				
		    ],
		    //border:true,
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
								text:'Visualizar',
								handler: function(){
									visualizar(record);
								}
							},
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
    		                        Ext.Msg.confirm("Confimacion", "¿Desea enviar?", function(btnText){
    		                            if(btnText === "yes"){
    		                               
    		                                enviarSunedu(record);
    		                            }
    		                            else if(btnText === "no"){
    		                                
    		                            }
    		                        }, this);
	    						}
	    					},
	    					{
		    					text:'Diploma',
		    					handler: function(){
		    						window.open(
		    								"duplicado/exportDiploma?codigo="+record.get('codigo')
		    								//"gradoTitulo/exportDiploma"
		    						)
	    					
	    						}
	    					},
	    					{
		    					text:'Inscripcion',
		    					handler: function(){
		    						window.open(
		    								"duplicado/exportInscripcion?codigo="+record.get('codigo')
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
                	var globalPanel=APP.Portal.Duplicado.Load.GlobalPanel;
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
		APP.Portal.Duplicado.Load.GradoTituloList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.GradoTituloList,Ext.panel.Panel,{});
	
	APP.Portal.Duplicado.Load.GradoTituloPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Duplicado.Load.GradoTituloList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Duplicado',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Duplicado.Load.GradoTituloPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.GradoTituloPanel,Ext.panel.Panel,{});
	
	

	APP.Portal.Duplicado.Load.AutoridadWindows=function(config){	
		var me=this,
			uxData = config.uxData;

		var cancelarButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-delete-icon',
			text : 'Cancelar',
			handler :function() {
				me.close();
				
			}
		});
		var nombreAutoridadField=new Ext.form.field.Text({
	        fieldLabel: 'Nombre de Autoridad',
	        name: 'textoNombreAutoridad',
	        anchor: '100%',
	        allowBlank: false
        });
		
		var cargoField=new Ext.form.field.Text({
	        fieldLabel: 'Cargo',
	        name: 'cargo.textoNombre',
	        anchor: '100%',
	        allowBlank: false
        });	
		var gradoAcademicoField=new Ext.form.field.Text({
	        fieldLabel: 'Grado Academico',
	        name: 'gradoAcademico.textoNombre',
	        anchor: '100%',
	        allowBlank: false
        });	
		var panelField=new Ext.form.Panel({
			height: 150,
			width : 400,
			bodyPadding: 5,
			items:[
				nombreAutoridadField,
				cargoField,
				gradoAcademicoField
			]
		})
		
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[panelField],
			buttons:[cancelarButton],
			listeners :{
				afterrender:function(){
					if(uxData&&uxData.action=='edit'){
						panelField.load({
							url:'autoridad/getAutoridad',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.Duplicado.Load.AutoridadWindows.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.AutoridadWindows,Ext.window.Window,{});
	

	
	


	APP.Portal.Duplicado.Load.AutoridadDetallePanel=function(config){	
		var me=this,
			uxData = config.uxData;
		var btnView=new Ext.button.Button({
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){
				var codigo=uxData.autoridadField.getValue();
				if( codigo ){
					var win = new APP.Portal.Duplicado.Load.AutoridadWindows({
						title: 'Detalle',
						uxData:{
							codigo:codigo,
							action:'edit'
						}
					});
					win.show(
						console.log('decanoField'),
						console.log(codigo)
					);	
				}
			}
		})
		var panelAutoridad=new Ext.form.Panel({
			items:[uxData.autoridadField]
		})
		
		var panelView=new Ext.form.Panel({
			padding : '0 0 0 10',
			items:[btnView]
		})
		
// 		var panelContenedor = new Ext.panel.Panel({
// 			width: 450,
// 			layout:{
// 				type:'hbox',
// 				align:'stretch'
// 			},
// 			items:[
// 				{
// 			     	flex: 92,
// 			     	items:[panelAutoridad]
// 			    },
// 			    {
// 			     	flex: 8,
// 			     	items:[panelView]
// 			    },
// 			]
// 		});
		
		Ext.apply(config,{
			width: 450,
			layout:{
				type:'hbox',
				align:'stretch'
			},
			items:[
				{
			     	flex: 92,
			     	items:[panelAutoridad]
			    },
			    {
			     	flex: 8,
			     	items:[panelView]
			    },
			]		
		});
		
		APP.Portal.Duplicado.Load.AutoridadDetallePanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.AutoridadDetallePanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.Duplicado.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.Duplicado.Load.GradoTituloPanel({});
		
		me.getGradoTituloPanel=function(){
			return datoGeneralPanel;
		}
		/*
		var panelFullContainer = new Ext.panel.Panel({
			layout:{
				type:'hbox',
				align:'stretch'
			},
			items:[{
				 flex: 1, items:[datoGeneralPanel]
			}]
		})
		*/
		Ext.apply(config,{
			layout:'fit',
			//items: [panelFullContainer]
			items: [datoGeneralPanel]
		});
		
		APP.Portal.Duplicado.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Duplicado.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.Duplicado.Load.Container({});
		APP.Portal.Duplicado.Load.GlobalPanel=globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
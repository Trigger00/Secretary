<script type="text/javascript">
	Ext.ns('APP.Portal.Revalida.Load')
	APP.Portal.Revalida.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.Revalida.Load.GradoTituloWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		var itemsPerPage = 25;

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
			storeModalidadGradoTitulo,
			storeProgramaEstudio;
		var formTextoMatricula,
		 	formTextoNombreCompleto,
			formTextoDocumento;
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
	            var save = panelLoader.getForm();
	            var data=[];
	            save.getFields().each(function(field) {
	            	data.push({field:field,validate:field.validate()});
	            });
	            console.info({data:data})
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'revalida/saveRevalida',
	                	method: 'POST',
	                    success: function (form, action) {
	                    	var jsonResponse = action.result;
	                        if (jsonResponse.success == true) {
	                        	var globalPanel=APP.Portal.Revalida.Load.GlobalPanel;
		                    	globalPanel.getGradoTituloPanel().getListPanel().loadList();
								//wait:true,
								Ext.Msg.alert('�xito', jsonResponse.message);
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
	                    	var globalPanel=APP.Portal.Revalida.Load.GlobalPanel;
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
			pageSize: itemsPerPage,
		    proxy: {
		        type: 'ajax',
		        url: 'datoGeneral/getEstudianteRevalidaList',
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
	            		//textoMatricula:formTextoMatricula,
						textoNombreCompleto:formTextoNombreCompleto,
						textoNumeroDocumento:formTextoDocumento
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
					"cargo.codigoExterno": 'SG'
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
		        {"codigo":"B", "textoNombre":"Revalida Grado - Bachiller"},
		        {"codigo":"T", "textoNombre":"Revalida Titulo - Titulo"},
		        {"codigo":"M", "textoNombre":"Revalida Grado - Maestria"},
		        {"codigo":"D", "textoNombre":"Revalida Grado - Doctorado"},

		    ]
		});
		storeProgramaEstudio=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre',
			],
			autoLoad:true,
			proxy: {
					type: 'ajax',
					url: 'gradoTitulo/getListProgramaEstudio',
					reader: {
						type: 'json',
						root: 'data',
						idProperty : 'codigo',
						totalProperty :'totalCount'
					}
			}
		});
		var tipoDocunento
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
					tipoDocunento='B';
					var tipoRevalidaField=new Ext.form.field.Text({
				        name: "tipoRevalida.codigoExterno"
			        });
					tipoRegistros.setValue('B');
					tipoRevalidaField.setValue('RGRAD');
					items.push(tipoRegistros.hide())
					items.push(tipoRevalidaField.hide())
					tipoDocunento='B';
				}else if(item=='T'){
					var tipoRegistros=new Ext.form.field.Text({
				        name: "gradoAcademico.codigoExterno"
			        });
					tipoRegistros.setValue('T');
					items.push(tipoRegistros.hide())
					tipoDocunento='T';
					var tipoRevalidaField=new Ext.form.field.Text({
				        name: "tipoRevalida.codigoExterno"
			        });
					tipoRevalidaField.setValue('RTIT');
					tipoRegistros.setValue('T');
					items.push(tipoRegistros.hide())
					items.push(tipoRevalidaField.hide())
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
					var tipoRevalidaField=new Ext.form.field.Text({
				        name: "tipoRevalida.codigoExterno"
			        });
					tipoRevalidaField.setValue('RGRAD');
					items.push(tipoRegistroField)
					items.push(tipoRevalidaField.hide())
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
					var tipoRevalidaField=new Ext.form.field.Text({
				        name: "tipoRevalida.codigoExterno"
			        });
					tipoRevalidaField.setValue('RGRAD');
					items.push(tipoRegistroField)
					items.push(tipoRevalidaField.hide())
					tipoDocunento='D';
				}else if(item=='gradoBachiller'){
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Decano',
				        name: "autoridadRegistroDecano.codigo",
						store: storeAutoridadDecano,
						anchor: '100%',
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
// 						allowBlank: false,
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
// 						allowBlank: false,
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
// 						allowBlank: false,
						editable:false
					});
					var gradoBachillerField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'REV de Bach en',
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
					var panelDecano = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: decanoField
						}
					});
					var panelRector = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: secretarioGeneralField
						}
					});
					items.push(gradoBachillerField)
					items.push( panelDecano )
					items.push( panelRector )
					items.push( panelSecretarioGeneral )
				}else if(item=='tituloProfesional'){
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Decano',
				        name: "autoridadRegistroDecano.codigo",
						store: storeAutoridadDecano,
						anchor: '100%',
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
// 						allowBlank: false,
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
// 						allowBlank: false,
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
// 						allowBlank: false,
						editable:false
					});
					var tituloProfesionalField=new Ext.form.field.ComboBox({
						tpl: Ext.create('Ext.XTemplate',
						        '<tpl for=".">',
						            '<div class="x-boundlist-item">{textoNombreDenominacion} - {textoNombreEspanol}</div>',
						        '</tpl>'
						),
						fieldLabel: 'REV de Titulo Profesional en',
				        name: "especialidad.codigo",
						store: storeEspecialidad,
				        queryMode: 'local',
				        //displayField: 'textoNombreDenominacion',
				        displayTpl: Ext.create('Ext.XTemplate',
					        '<tpl for=".">',
					            '{textoNombreDenominacion} - {textoNombreEspanol}',
					        '</tpl>'
					    ),
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
					var panelDecano = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: decanoField
						}
					});
					var panelRector = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: secretarioGeneralField
						}
					});
					items.push(tituloProfesionalField)
					items.push( panelDecano )
					items.push( panelRector )
					items.push( panelSecretarioGeneral )
				}else if(item=='maestria'){
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Director',
				        name: "autoridadRegistroDirectorPostgrado.codigo",
						store: storeAutoridadDirectorPosgrado,
						anchor: '100%',
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
// 						allowBlank: false,
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
// 						allowBlank: false,
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
// 						allowBlank: false,
						editable:false
					});
					var maestriaField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'REV de Maestria en',
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
					var panelDecano = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: decanoField
						}
					});
					var panelRector = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: secretarioGeneralField
						}
					});
					items.push(maestriaField)
					items.push( panelDecano )
					items.push( panelRector )
					items.push( panelSecretarioGeneral )
				}else if(item=='doctorado'){
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Director',
				        name: "autoridadRegistroDirectorPostgrado.codigo",
						store: storeAutoridadDirectorPosgrado,
						anchor: '100%',
				        queryMode: 'local',
				        displayField: 'textoNombreAutoridad',
				        valueField: 'codigo',
// 						allowBlank: false,
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
// 						allowBlank: false,
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
// 						allowBlank: false,
						editable:false
					});
					var doctoradoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'REV de Doc en',
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
					var panelDecano = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: decanoField
						}
					});
					var panelRector = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.Revalida.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField: secretarioGeneralField
						}
					});
					items.push(doctoradoField)
					items.push( panelDecano )
					items.push( panelRector )
					items.push( panelSecretarioGeneral )
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
				}else if ( item == 'diplomaSexo'){
					var diplomaSexoField = new Ext.form.field.Checkbox({
				        fieldLabel: 'Denominacion Por Sexo',
				        name: 'flagDiplomaSexo',
				        inputValue: '1'
				        
			        });
					items.push(diplomaSexoField)
				}else if(item=='semestreEgreso'){
					var semestreEgresoField=new Ext.form.field.Text({
				        fieldLabel: 'Semestre de Egreso',
				        name: 'textoSemestreEgreso'/*,
				        allowBlank: false*/
			        });
					items.push(semestreEgresoField)
				}else if(item=='constanciaEgreso'){
					var constanciaEgresoField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha de Constansia de Egreso',
				        name: 'fechaConstanciaEgreso',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d"/*,
				        allowBlank: false*/
					});
					items.push(constanciaEgresoField)
				}else if(item=='textoResolucionFacultad'){
					var textoResolucionFacultadField=new Ext.form.field.Text({
				        fieldLabel: 'Resolucion de Facultad',
				        name: 'textoResolucionFacultad'/*,
				        allowBlank: false*/
			        });
					items.push(textoResolucionFacultadField)
				}else if(item=='fechaResolucionFacultad'){
					var fechaResolucionFacultadField=new Ext.form.field.Date({
				        fieldLabel: 'F/D Resolucion de Facultad',
				        name: 'fechaResolucionFacultad',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        getSubmitValue: function(){
				        	var format = this.submitFormat || this.format,
				        	value = this.getValue();
				        	Ext.Date.format(value, format)
				        	return value ? Ext.Date.format(value, format) : null;
				        }/*,
				        allowBlank: false*/
					});
					items.push(fechaResolucionFacultadField)
				}else if(item=='resolucionRectoral'){
					var resolucionRectoralField=new Ext.form.field.Text({
				        fieldLabel: 'Resolucion CU',
				        name: 'textoResolucionRectoral'/*,
				        allowBlank: false*/
			        });
					items.push(resolucionRectoralField)
				}else if(item=='aprobacioncu'){
					var aprobacioncuField=new Ext.form.field.Date({
				        fieldLabel: 'F/D Aprobacion CU',
				        name: 'fechaAprobacionConsejoUniversitario',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        getSubmitValue: function(){
				        	var format = this.submitFormat || this.format,
				        	value = this.getValue();
				        	Ext.Date.format(value, format)
				        	return value ? Ext.Date.format(value, format) : null;
				        }/*,
				        allowBlank: false*/
					});
					items.push(aprobacioncuField)
				}else if(item=='sustentacionTesis'){
					var sustentacionTesisField=new Ext.form.field.Date({
				        fieldLabel: 'F/D de Sustentacion ',
				        name: 'fechaSustentacionTesis',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        getSubmitValue: function(){
				        	var format = this.submitFormat || this.format,
				        	value = this.getValue();
				        	Ext.Date.format(value, format)
				        	return value ? Ext.Date.format(value, format) : null;
				        }//,
				        //allowBlank: false
			        });
					items.push(sustentacionTesisField)
				}else if(item=='nombreTrabajoInvestigacion'){
					var nombreTrabajoInvestigacionField=new Ext.form.field.Text({
				        fieldLabel: 'Trabajo de Investigacion',
				        name: 'textoNombreTrabajoInvestigacion'//,
				        //allowBlank: false
			        });
					items.push(nombreTrabajoInvestigacionField)
				}else if(item=='nombreTesis'){
					var nombreTesisField=new Ext.form.field.Text({
				        fieldLabel: 'Nombre de Tesis',
				        name: 'textoNombreTesis'/*,
				        allowBlank: false
				        */
			        });
					items.push(nombreTesisField)
				}else if(item=='detalle'){
					var detalleField=new Ext.form.field.TextArea({
				        fieldLabel: 'Detalle',
				        name: 'textoDetalle'/*,
				        allowBlank: false*/
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
// 						allowBlank: false,
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
// 						allowBlank: false,
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
// 						allowBlank: false,
						editable:false
					});
					items.push(decanoField)
				}else if(item=='folio'){
					var folioField=new Ext.form.field.Number({
						name: 'numeroFolio',
				        fieldLabel: 'N� Folio',
				        minValue: 0
			        });
					items.push(folioField)
				}else if(item=='libro'){
					var libroField=new Ext.form.field.Number({
						name: 'numeroLibro',
				        fieldLabel: 'N� Libro',
				        minValue: 0
			        });
					items.push(libroField)
				}else if(item=='registro'){
					var registroField=new Ext.form.field.Number({
						name: 'numeroRegistro',
				        fieldLabel: 'N� Registro',
				        minValue: 0
			        });
					items.push(registroField)
				}else if(item=='codigoBarra'){
					var codigoBarraField=new Ext.form.field.Text({
				        fieldLabel: 'Codigo Barra',
				        name: 'textoCodigoBarra'/*,
				        allowBlank: false*/
			        });
					items.push(codigoBarraField)
				}else if(item=='matriculaPost'){
					var matriculaPostField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula Posgrado',
				        name: 'textoMatriculaPost'/*,
				        allowBlank: false*/
			        });
					items.push(matriculaPostField)
				}else if(item=='resolucionEpg'){
					var resolucionEpgField=new Ext.form.field.Text({
				        fieldLabel: 'Resolucion EPG',
				        name: 'textoResolucionEpg'/*,
				        allowBlank: false*/
			        });
					items.push(resolucionEpgField)
				}else if(item=='fechaResolucionEpg'){
					var fechaResolucionEpgField=new Ext.form.field.Date({
				        fieldLabel: 'fecha resolcion EPG ',
				        name: 'fechaResolucionEpg',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d"/*,
				        allowBlank: false*/
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
						forceSelection: true
						//editable:false
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
						forceSelection: true
						//editable:false
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
						forceSelection: true
						//editable:false
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
						forceSelection: true
						//editable:false
					});
					items.push(paisField)
					items.push(universidadField)
				}else if(item=='fechaResolucionEpg'){
					var fechaResolucionEpgField=new Ext.form.field.Date({
				        fieldLabel: 'fecha resolcion EPG ',
				        name: 'fechaResolucionEpg',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d"/*,
				        allowBlank: false*/
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
						//allowBlank: false,
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
				}else if ( item == 'numeroCreditos' ){
					var numeroCreditosField=new Ext.form.field.Number({
						name: 'numeroCreditos',
				        fieldLabel: 'N� Creditos',
				        minValue: 0
			        });
					items.push(numeroCreditosField.setDisabled(true))
				}else if ( item == 'registroMetadato'){
					var registroMetadatoField=new Ext.form.field.Text({
				        fieldLabel: 'Registro Metadato',
				        name: 'textoRegistroMetadato'
				        //allowBlank: false
			        });
					items.push(registroMetadatoField.setDisabled(true))
				}else if ( item == 'procedenciaTituloPedagogico'){
					var procedenciaTituloPedagogicoField=new Ext.form.field.Text({
				        fieldLabel: 'Procedencia TIT PDGC',
				        name: 'textoProcedenciaTituloPedagogico'
				        //allowBlank: false
			        });
					items.push(procedenciaTituloPedagogicoField.setDisabled(true))
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
				}else if(item=='documentoIdentidad'){
					var documentoIdentidadFindField=new Ext.form.field.Text({
				        fieldLabel: 'N� Documento ID',
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
								storeAlumno.currentPage = 1;
								storeAlumno.load({
									start: 0, 
									limit: itemsPerPage,
									params: {
										textoNumeroDocumento:form.textoDocumento,
										textoNombreCompleto:form.textoNombreCompleto
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
									//formTextoMatricula=matriculaField.getValue(),
								 	formTextoNombreCompleto=nombreField.getValue()
									formTextoDocumento=documentoIdentidadField.getValue()
									var form={
											textoDocumento:documentoIdentidadField.getValue(),
											textoNombreCompleto:nombreField.getValue()
										}	
									loadListParam(form);		
								}
							});
							var addButton = new Ext.Button({
								width : 80,
								iconCls : 'btn-add-icon',
								text : 'Agregar',
						        handler: function() {
						        	if(textoDocumentoIdentidad){
						        		console.info('selecciono '+textoDocumentoIdentidad);
						        		documentoIdentidadFindField.setValue(textoDocumentoIdentidad);
						        		estudianteField.setValue(codigo);
						        		win.close();
						        	}else{
						        		alert('Seleccione un registro con N� de Documento')	
						        	}
						        }
							});
							var documentoIdentidadField=new Ext.form.field.Text({
						        fieldLabel: 'N� Documento',
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
									{ text: 'N� Documento',  dataIndex: 'textoDocumento',flex:1 },
							        { text: 'Nombre',  dataIndex: 'textoNombreCompleto',flex:1 }//,
							        //{ text: 'Facultad',  dataIndex: 'facultad',flex:1 },
							        //{ text: 'Especialidad',  dataIndex: 'especialidad',flex:1 }
							    ],
							    style: {borderColor: '#157fcc'},
							    dockedItems: [{
							        xtype: 'pagingtoolbar',
							        store: storeAlumno, 
							        dock: 'bottom',
							        displayInfo: true
							    }],
							    listeners :{
									select:function(dv, record, index, eOpts ){
										console.log('selecccionado la N� de documento'+record.get('textoDocumento'));
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
										region: 'center',layout:'fit',items : [grid]
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
					items.push(panelEstudiante)					
				}else if(item=='universidadRevalida'){
					var universidadRevalidaField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Universidad de Procedencia',
				        name: "universidadRevalida.codigo",
						store: storeUniversidadRevalida,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
// 						allowBlank: false,
						forceSelection: true
						//editable:false
					});
					var paisRevalidaField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Pais de Procedencia',
				        name: "paisRevalida.codigo",
						store: storePais,
				        queryMode: 'local',
				        displayField: 'textoNombre',
				        valueField: 'codigo',
				        listConfig : {
							listeners : {
								itemclick : function(list,record) {
									universidadRevalidaField.clearValue();
									storeUniversidadRevalida.load({
										params: {
											"pais.codigo":record.get('codigo')
										}
									});
									
								}
							}
											
						},
// 						allowBlank: false,
						forceSelection: true
						//editable:false
					});
					items.push(paisRevalidaField)
					items.push(universidadRevalidaField)
				}else if(item=='tituloRevalida'){
					var tituloRevalidaField=new Ext.form.field.Text({
				        fieldLabel: 'Titulo obtenido en Univ de Procedencia',
				        name: 'textoNombreTituloGrado'/*,
				        allowBlank: false*/
			        });
					items.push(tituloRevalidaField)
				}else if(item=='tituloRevalidaMaestria'){
					var tituloRevalidaField=new Ext.form.field.Text({
				        fieldLabel: 'Maestria obtenido en el extranjero',
				        name: 'textoNombreTituloGrado'/*,
				        allowBlank: false*/
			        });
					items.push(tituloRevalidaField)
				}else if(item=='tituloRevalidaDoctorado'){
					var tituloRevalidaField=new Ext.form.field.Text({
				        fieldLabel: 'Doctorado obtenido en el extranjero',
				        name: 'textoNombreTituloGrado'/*,
				        allowBlank: false*/
			        });
					items.push(tituloRevalidaField)
				}else if(item=='fechaInicio'){
					var fechaInicioField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha de Inicio',
				        name: 'fechaInicio',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        getSubmitValue: function(){
				        	var format = this.submitFormat || this.format,
				        	value = this.getValue();
				        	Ext.Date.format(value, format)
				        	return value ? Ext.Date.format(value, format) : null;
				        }
				        	 /*,
				        allowBlank: false*/
				        
					});
					items.push(fechaInicioField)
				}else if(item=='fechaFinal'){
					var fechaFinalField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha Final',
				        name: 'fechaFinal',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        getSubmitValue: function(){
				        	var format = this.submitFormat || this.format,
				        	value = this.getValue();
				        	Ext.Date.format(value, format)
				        	return value ? Ext.Date.format(value, format) : null;
				        }/*,
				        allowBlank: false*/
					});
					items.push(fechaFinalField)
				}else if(item=='fechaDiploma'){
					var fechaDiplomaField=new Ext.form.field.Date({
				        fieldLabel: 'Fecha de Diploma',
				        name: 'fechaDiploma',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        getSubmitValue: function(){
				        	var format = this.submitFormat || this.format,
				        	value = this.getValue();
				        	Ext.Date.format(value, format)
				        	return value ? Ext.Date.format(value, format) : null;
				        }/*,
				        allowBlank: false*/
					});
					items.push(fechaDiplomaField)
				}else if(item=='legalizado'){
					var legalizadoField=new Ext.form.field.Text({
				        fieldLabel: 'Legalizado en',
				        name: 'textoLegalizado'/*,
				        allowBlank: false*/
			        });
					items.push(legalizadoField)
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
							'documentoIdentidad',
							/***/
							'universidadRevalida',
							'tituloRevalida',
							'gradoBachiller',
							'fechaInicio',
							'fechaFinal',
							'fechaDiploma',
							'legalizado',
							
							/***/						
							'textoResolucionFacultad',
							'fechaResolucionFacultad'
						]
					})
					var fieldsTwo=getUxFields({
						items:[
							'resolucionRectoral',
							'aprobacioncu',
							'sustentacionTesis',
							'nombreTrabajoInvestigacion',
							//'modalidadGradoTitulo',
							'modalidadEstudio',
							'folio',
							'libro',
							'registro',
							'codigoBarra',
							'agendaGrupo',
							'programaEstudio',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico'
// 							'adjunto'   
						  
						    
						]})
					console.info({fields:fields})
					
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
					var formPanelToLoad=new Ext.form.Panel({
						items: fields
					})
					var formPanelToLoadTwo=new Ext.form.Panel({
						items:fieldsTwo
					})
					
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add({
				        columnWidth: 0.5,
				        items:[
							formPanelToLoad
						]
				    },
				    {
				        columnWidth: 0.5,
				        items:[
							formPanelToLoadTwo
						]
				    });
					panelLoader.doLayout();
				}
			},
			'T':{
				load:function(){
					//matriculaField.reset();
					var fields=getUxFields({
						items:[
							'codigoRegistro',
							'T',
							'documentoIdentidad',
							/***/
							'universidadRevalida',
							'tituloRevalida',
							'tituloProfesional',
							'fechaInicio',
							'fechaFinal',
							'fechaDiploma',
							'legalizado',
							/***/		
							'textoResolucionFacultad',
							'fechaResolucionFacultad',
							]
					})
					var fieldsTwo=getUxFields({
						items:[
							'resolucionRectoral',
							'aprobacioncu',
							'sustentacionTesis',
							'nombreTesis',
							'detalle',
							'modalidadGradoTitulo',
							//'modalidadEstudio',
							'folio',
							'libro',
							'registro',
							'codigoBarra',
							'agendaGrupo',
							'programaEstudio',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',
							'diplomaSexo'
							/*,
							'adjunto'
							*/
						]
					})
					console.info({fields:fields})
					var formPanelToLoad=new Ext.form.Panel({
						items:fields
					})
					var formPanelToLoadTwo=new Ext.form.Panel({
						items:fieldsTwo
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
					/*
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add(formPanelToLoad);
					panelLoader.doLayout();
					
					panelLoaderTwo.getForm().clearManagedListeners();
					panelLoaderTwo.getForm().monitor.unbind();
					panelLoaderTwo.removeAll(true)
					panelLoaderTwo.add(formPanelToLoadTwo);
					panelLoaderTwo.doLayout();
					*/
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add({
					       // title: 'Column 1',
				        columnWidth: 0.5,
				        items:[
							formPanelToLoad
						]
				    },
				    {
					       // title: 'Column 1',
				        columnWidth: 0.5,
				        items:[
							formPanelToLoadTwo
						]
				    });
					panelLoader.doLayout();
				}
			},
			'M':{
				load:function(){
					//matriculaField.reset();
					var fields=getUxFields({
						items:[
							'codigoRegistro',
							'M',
							'documentoIdentidad',
							/***/
							'universidadRevalida',
							'tituloRevalidaMaestria',
							'maestria',
							'fechaInicio',
							'fechaFinal',
							'fechaDiploma',
							'legalizado',
							/***/
							/*'matriculaPost',*/
							'resolucionEpg',
							'resolucionRectoral',
						]
					})
					var fieldsTwo=getUxFields({
						items:[	
							'aprobacioncu',
							'sustentacionTesis',
							'nombreTesis',
							'detalle',
							'universidadBachiller',
							//'modalidadGradoTitulo',
							'folio',
							'libro',
							'registro',
							'codigoBarra',
							'agendaGrupo',
							'programaEstudio',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',
							'diplomaSexo'
							/*,
							'adjunto'
							*/
						   
						]
					})
					var formPanelToLoad=new Ext.panel.Panel({
						items:fields
					})
					var formPanelToLoadTwo=new Ext.panel.Panel({
						items:fieldsTwo
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
					/*
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add(formPanelToLoad);
					panelLoader.doLayout();
					
					panelLoaderTwo.getForm().clearManagedListeners();
					panelLoaderTwo.getForm().monitor.unbind();
					panelLoaderTwo.removeAll(true)
					panelLoaderTwo.add(formPanelToLoadTwo);
					panelLoaderTwo.doLayout();
					*/
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add({
					       // title: 'Column 1',
				        columnWidth: 0.5,
				        items:[
							formPanelToLoad
						]
				    },
				    {
					       // title: 'Column 1',
				        columnWidth: 0.5,
				        items:[
							formPanelToLoadTwo
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
							'documentoIdentidad',
							'universidadRevalida',
							'tituloRevalidaDoctorado',
							'doctorado',
							'fechaInicio',
							'fechaFinal',
							'fechaDiploma',
							'legalizado',
							
							'resolucionEpg',
							'resolucionRectoral',
							'aprobacioncu',
							'sustentacionTesis',
						
						]
					})
					var fieldsTwo=getUxFields({
						items:[
// 							'aprobacioncu',
// 							'sustentacionTesis',
							'nombreTesis',
							'detalle',
							'universidadBachiller',
							'universidadMaestria',
							//'modalidadGradoTitulo',
							'folio',
							'libro',
							'registro',
							'codigoBarra',
							'agendaGrupo',
							'programaEstudio',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',
							'diplomaSexo'
							/*,
							'adjunto'
							*/
						]
					})
					var formPanelToLoad=new Ext.panel.Panel({
						items:fields
					})
					var formPanelToLoadTwo=new Ext.panel.Panel({
						items:fieldsTwo
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
					/*
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add(formPanelToLoad);
					panelLoader.doLayout();
					
					panelLoaderTwo.getForm().clearManagedListeners();
					panelLoaderTwo.getForm().monitor.unbind();
					panelLoaderTwo.removeAll(true)
					panelLoaderTwo.add(formPanelToLoadTwo);
					panelLoaderTwo.doLayout();
					*/
					panelLoader.getForm().clearManagedListeners();
					panelLoader.getForm().monitor.unbind();
					panelLoader.removeAll(true)
					panelLoader.add({
				        columnWidth: 0.5,
				        items:[
							formPanelToLoad
						]
				    },
				    {
				        columnWidth: 0.5,
				        items:[
							formPanelToLoadTwo
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
		/*
		var panelLoader=new Ext.form.Panel({
			height: 850,
			width : 450
		})
		var panelLoaderTwo=new Ext.form.Panel({
			height: 850,
			width : 450
		})
		*/
		var panelLoader= new Ext.form.Panel({
			bodyPadding: 5,
			fieldDefaults: {
    	        width : 450,
    	        labelWidth : 150
    	    },
			width: 950,
		    height: 550,
		    layout:'column'
		});
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
						console.log('uxData.tipo'+uxData.tipo);
						tipoDiplomaLabel.setValue(uxData.tipo);
						loaderConfig[uxData.tipo].load();
						agregarPanel.load({
							url:'revalida/getRevalida',
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
							url:'revalida/getRevalida',
							params:{
								codigo:uxData.codigo,
							}
						})
						//agregarPanel.doLayout();
					}
				
				}
			}
		});
		
		APP.Portal.Revalida.Load.GradoTituloWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Revalida.Load.GradoTituloWindowsMantenimiento,Ext.window.Window,{});
	
	APP.Portal.Revalida.Load.GradoTituloWindowsClose=function(config){
		
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
		                url: 'revalida/close',
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
		                	 var globalPanel=APP.Portal.Revalida.Load.GlobalPanel;
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
		APP.Portal.Revalida.Load.GradoTituloWindowsClose.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Revalida.Load.GradoTituloWindowsClose,Ext.window.Window,{});
	
	APP.Portal.Revalida.Load.GradoTituloFiltro=function(config){
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
						var globalPanel=APP.Portal.Revalida.Load.GlobalPanel;
						
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
							findValue.push('N� Folio: '+form.folio);
						}
						if(!isEmpty(form.textoNombreEspanolEspecialidad)){
							findValue.push('N� Libro: '+form.libro);
						}
						if(!isEmpty(form.textoNumeroResolucion)){
							findValue.push('N� Registro: '+form.registro);
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
			        fieldLabel: 'N� Folio',
			        minValue: 0
		        });
		        var libroField=new Ext.form.field.Number({
					name: 'numeroLibro',
			        fieldLabel: 'N� Libro',
			        minValue: 0
		        }); 
		        var registroField=new Ext.form.field.Number({
					name: 'numeroRegistro',
			        fieldLabel: 'N� Registro',
			        minValue: 0
		        });
		        var fechaInicioCuField=new Ext.form.field.Date({
			        fieldLabel: 'Fecha Inicio CU',
			        name: 'fechaCierreInicial',
			        //format : "Y/m/d"
			        format : "d/m/Y",
			        submitFormat :"Y/m/d"
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
			        	                'N� Folio',
			        	                'N� Libro',
			        	                'N� Registro',
			        	                'Tipo de Registro',
			        	                'Agenda'
			        	],
			        	form={
			        
				        	matricula:'Matricula',
				        	nombreCompleto:'Nombre',
				        	codigoExternoDocumento:'Documento de Identidad',
				        	numeroDocumento:'Numero de Documento',
				        	folio:'N� Folio',
				        	libro:'N� Libro',
				        	registro:'N� Registro',
				        	tipoRegistro:'Tipo de Registro',
				        	agendaGrupo:'Agenda'
				        			
				        },
				       	textRaw=me.getRawValue()
			        	divider(form,nameField,textRaw);
			        	
			        	
						
						var globalPanel=APP.Portal.Revalida.Load.GlobalPanel,
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
		APP.Portal.Revalida.Load.GradoTituloFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Revalida.Load.GradoTituloFiltro,APP.form.field.Picker,{});
	
	APP.Portal.Revalida.Load.GradoTituloToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.Revalida.Load.GradoTituloFiltro({
			
		});
		var mantenimiento = new APP.Portal.Revalida.Load.GradoTituloWindowsMantenimiento({
			
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
				/*
				{
					text : 'Cerrar Registros',
					height : 30,
					iconCls : 'btn-candado-icon',
					cls : 'btn',
					handler:function(){
						
						var win = new APP.Portal.Revalida.Load.GradoTituloWindowsClose({
							title: 'Seleccionar'
						});
						win.show();
					}
				},
				*/
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
								"revalida/excel?estudiante.textoMatricula="+formExcel.matricula
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
						var win = new APP.Portal.Revalida.Load.GradoTituloWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.Revalida.Load.GradoTituloToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Revalida.Load.GradoTituloToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.Revalida.Load.GradoTituloList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
		var itemsPerPage = 25;	
	
		toolbar=new APP.Portal.Revalida.Load.GradoTituloToolbar({
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
				url : 'revalida/delete',
				method: 'POST',
				params: {
					codigo:codigo
				},
				success: function (result, request){
					store.load({
						start: 0, 
						limit: itemsPerPage,
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
				url : 'revalida/enviarSunedu',
				method: 'POST',
				params: {
					codigo:codigo
				},
				success: function (result, request){
					store.load({
						start: 0, 
						limit: itemsPerPage,
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
			var win = new APP.Portal.Revalida.Load.GradoTituloWindowsMantenimiento({
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
			var win = new APP.Portal.Revalida.Load.GradoTituloWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					tipo:tipo,
					action:'view'
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
        		'egresadoCiclo',
        		'flagDiplomaSexo'
        		
			],
			autoLoad:true,
			pageSize: itemsPerPage, 
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
				{ text: 'N� Registro',  dataIndex: 'registroRegistro',flex:1},
				{ text: 'Agenda',  dataIndex: 'agendaGrupo',flex:1},
				{ text: 'Grado o Titulo',  dataIndex: 'gradoTitulo',flex:1},
				{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
				{ text: 'Codigo Univ.',  dataIndex: 'codigoUniversidad',flex:1, hidden: true },
				{ text: 'Razon Zocial',  dataIndex: 'razonSocial',flex:1, hidden: true },
				{ text: 'Fecha de 1� MAT.',  dataIndex: 'matriculaFecha',flex:1, hidden: true },
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
				{ text: 'Fecha de Sustentaci�n',  dataIndex: 'actoFecha',flex:1, hidden: true},
				{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
				{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
				{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
				{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
				{ text: 'Denominacion del GyT REV',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
				{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
				{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
				{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
				{ text: 'Numero Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
				{ text: 'Tipo de Emision',  dataIndex: 'diplomaTipoEmision',flex:1, hidden: true},
				{ text: 'N� Libro',  dataIndex: 'registroLibro',flex:1, hidden: true}, 
				{ text: 'N� Folio',  dataIndex: 'registroFolio',flex:1, hidden: true},
				{ text: 'Nom. Cargo 1',  dataIndex: 'cargoUno',flex:1, hidden: true},
				{ text: 'Cargo 1',  dataIndex: 'autoridadUno',flex:1, hidden: true},
				{ text: 'Nom Cargo 2',  dataIndex: 'cargoDos',flex:1, hidden: true},
				{ text: 'Cargo 2',  dataIndex: 'autoridadDos',flex:1, hidden: true},
				{ text: 'Nom Cargo 3',  dataIndex: 'cargoTres',flex:1, hidden: true},
				{ text: 'Cargo 3',  dataIndex: 'autoridadTres',flex:1, hidden: true},
				{ text: 'Enviado SUNEDU',  dataIndex: 'enviadoSunedu',flex:1, hidden: true},
				{ text: 'Ciclo de Egreso',  dataIndex: 'egresadoCiclo',flex:1, hidden: true},
				{ text: 'Doc sexo',  dataIndex: 'flagDiplomaSexo',flex:1, hidden: true},

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
    		                        Ext.Msg.confirm("Confimacion", "�Desea eliminar el registro?", function(btnText){
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
    		                        Ext.Msg.confirm("Confimacion", "�Desea enviar a la Sunedu?", function(btnText){
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
		APP.Portal.Revalida.Load.GradoTituloList.superclass.constructor.call(this,config);	
		}
	Ext.extend(APP.Portal.Revalida.Load.GradoTituloList,Ext.panel.Panel,{});
	
	APP.Portal.Revalida.Load.GradoTituloPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Revalida.Load.GradoTituloList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Revalida',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Revalida.Load.GradoTituloPanel.superclass.constructor.call(this,config);
	}	
	Ext.extend(APP.Portal.Revalida.Load.GradoTituloList,Ext.panel.Panel,{});
	
	APP.Portal.Revalida.Load.GradoTituloPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Revalida.Load.GradoTituloList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Revalida',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Revalida.Load.GradoTituloPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Revalida.Load.GradoTituloPanel,Ext.panel.Panel,{});
	APP.Portal.Revalida.Load.AutoridadWindows=function(config){	
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
		
		APP.Portal.Revalida.Load.AutoridadWindows.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Revalida.Load.AutoridadWindows,Ext.window.Window,{});
	
	APP.Portal.Revalida.Load.AutoridadDetallePanel=function(config){	
		var me=this,
			uxData = config.uxData;
		var btnView=new Ext.button.Button({
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){
				var codigo=uxData.autoridadField.getValue();
				if( codigo ){
					var win = new APP.Portal.Revalida.Load.AutoridadWindows({
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
		
		APP.Portal.Revalida.Load.AutoridadDetallePanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Revalida.Load.AutoridadDetallePanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.Revalida.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.Revalida.Load.GradoTituloPanel({});
		
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
		
		APP.Portal.Revalida.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Revalida.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.Revalida.Load.Container({});
		APP.Portal.Revalida.Load.GlobalPanel=globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
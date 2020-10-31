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
			storeAlumno,
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
			storeGradoAcademico,
			storeProgramaEstudio;
		var itemsPerPage = 25;
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
				
				function submit( save ){
					if (save.isValid()) {
    	            	save.submit({
    	                	url: 'gradoTitulo/saveEstudianteRegistro',
    	                	method: 'POST',
    	                    success: function (form, action) {
    	                    	var jsonResponse = action.result;
    	                        if (jsonResponse.success == true) {
    	                       		var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel;
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
				

				
				
// 				Ext.Msg.confirm("Confimacion", "¿Desea Grabar el registro?", function(btnText){
//                     if(btnText === "yes"){
                    	var save = panelLoader.getForm();
                    	var valueFechaEgresoPosgrado , valueFechaMatriculaPosgrado;
        	            var data=[];
        	            
        	            save.getFields().each(function(field) {
        	            	
       	            	
        	            	if( field.name == "fechaEgresoPosgrado" ){
        	            		
        	            		if(field.value == null ){
            	            		field.setDisabled(true)        	            			
        	            		}else{
        	            			valueFechaEgresoPosgrado = field.value;
        	            		} 
        	            		
        	            	}else if( field.name == "fechaMatriculaPosgrado" ){
        	            		
        	            		if( field.value == null ){
            	            		field.setDisabled(true)        	            			
        	            		}else{
        	            			valueFechaMatriculaPosgrado = field.value;
        	            		} 
        	            	}
        	            	
        	            	data.push({
        	            		field:field,
        	            		name:field.name,
        	            		validate:field.validate()}
        	            	);
        	            });
        	      
        	            console.info({data:data})
        	            
        	            var mensaje = validateAniosEstudio( valueFechaMatriculaPosgrado, valueFechaEgresoPosgrado );
        	        	
        	            Ext.Msg.confirm("Confimacion",mensaje, function(btnText){
                            if(btnText === "yes"){
                            	submit( save )
                            }
                         }, me );
        	            
        	            
//         	            if(mensaje.length > 0){
//     						console.log( " mensaje " + mensaje)
//     						console.log( " Confimacion ")
   						 	
//     						mensajeRevicion( save );
    				
    						
//     					}else{
//     						submit( save )
//     					}

//         	            if (save.isValid()) {
//         	            	save.submit({
//         	                	url: 'gradoTitulo/saveEstudianteRegistro',
//         	                	method: 'POST',
//         	                    success: function (form, action) {
//         	                    	var jsonResponse = action.result;
//         	                        if (jsonResponse.success == true) {
//         	                       		var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel;
//         		                    	globalPanel.getGradoTituloPanel().getListPanel().loadList();
//         		                    	//wait:true,
//         								Ext.Msg.alert('Éxito', jsonResponse.message);
//         								me.close();
//         	                       }
//         	                    },
//         	                    failure: function(form, action) {
//         	                    	var jsonResponse = action.result;
//         	                    	var title='Alerta';
//         	                    	 if (jsonResponse.success == false) {
//         	                    	   Ext.MessageBox.show({
//         	                      		 title: title, 
//         	                      		 msg: jsonResponse.message, 
//         	                      		 width: 300, 
//         	                      		 buttons: Ext.Msg.OK
//         	                          })
//         	                    	 }
//         	                    }
//         	                });
//         	            }
//                     }
//                     else if(btnText === "no"){
                        
//                     }
//                 }, me );

	        }
		});
		
		function validateAniosEstudio( dateInicio,dateFinal ){
			console.log("dateInicio "+dateInicio);
			console.log("dateFinal "+dateFinal);
			var mensaje = "";
			
			if(dateInicio != null && dateFinal != null){	
    			var delta = Date.parse(dateFinal) - Date.parse(dateInicio);
				var anios = parseInt(((delta / 86400000) / 350)).toString();
				console.log("anios " +anios);
				
				if(anios < 5){
					mensaje ="El estudiante tiene una fecha de ingreso y  una  fecha de egreso menor a 5 años" ;
				}
				
    		}
			
			mensaje +=" ¿Desea Grabar el registro?"
					
			return mensaje;

		}
		function mensajeRevicion( save ){
			console.log('mensajeRevicion');

// 			Ext.Msg.show({
// 			     title:'Save Changes?'
// 	    		 ,msg: 'You are closing a tab that has unsaved changes. Would you like to save your changes?'
// 	    		 ,buttons: Ext.Msg.YESNO
// 			});

			
// 			Ext.Msg.show({
// 	            title : 'Save',
// 	            msg : 'Do you want to Save the changes? ',
// 	            width : 300,
// 	            closable : false,
// 	            buttons : Ext.Msg.YESNOCANCEL,
// 	            buttonText : 
// 	            {
// 	                yes : 'Yes & Continue',
// 	                no : 'No & Continue',
// 	                cancel : 'Discard'
// 	            },
// 	            multiline : false,
// 	            fn : function(buttonValue, inputText, showConfig){
// 	                Ext.Msg.alert('Status', buttonValue);
// 	            },
// 	            icon : Ext.Msg.QUESTION
// 	        });

// 			Ext.Msg.confirm(
// 				'Changing Status', 
// 				'Are you sure you want to change the Status ?', 
// 				function (id, value) { 
// 					if (id === 'yes') { 
// 						combo.setValue(combo.currVal); 
// 					} 
// 				}, this
// 			); 

			Ext.Msg.confirm(
				'Changing Status' 
// 				,'Are you sure you want to change the Status ?', 
// 				function (id, value) { 
// 					if (id === 'yes') { 
// 						combo.setValue(combo.currVal); 
// 					} 
// 				}, this
			);
		}
		
		storeAlumno=new Ext.data.Store({
		    fields:[
		        'codigo',   
				'textoMatricula',    
				'facultad',
	            'especialidad',
				'textoNombreCompleto',
	            'textoNumeroResolucion',
	            'textoDocumento'
			],
			//autoLoad:true,
			pageSize: itemsPerPage, 
		    proxy: {
		        type: 'ajax',
		        url: 'datoGeneral/getEstudiantePregradoList',
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
	            		textoMatricula:formTextoMatricula,
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
				'textoNombre',
				'especialidad'
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
		storeAutoridadSecretarioGeneral=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombreAutoridad',
				'flagEstado'
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
				}
				,extraParams:{
					"cargo.codigoExterno": 'SG'
					,"flagEstado": 'A'
		        }
			}
// 			,filters: [function(record, id){
// 				console.log("record.data.flagEstado")
// 				console.log(record.data.flagEstado)
// 				if(record.data.flagEstado == "A" ){
// 					console.log("es habil");
// 				}
				
				
// 	            return (record.data.flagEstado == "A");
// 	        }]
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
		function loadAlumnoListParam(form){
			storeAlumno.currentPage = 1;
			storeAlumno.load({
				start: 0, 
				limit: itemsPerPage,
				params: {
					textoMatricula:form.textoMatricula,
					textoNombreCompleto:form.textoNombreCompleto,
					textoNumeroDocumento:form.textoDocumento
				}
			});	
		}
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
					
					//items.push(tipoRegistroField)
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
				        anchor: '100%',
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
// 						submitValue : true,
// 						hiddenName:'autoridadRegistroSecretarioGeneral.textoNombreAutoridad',
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
					var panelDecano = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:decanoField
						}
					});
					var panelRector = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:secretarioGeneralField
						}
					});
					items.push(gradoBachillerField)
					items.push(panelDecano)
					items.push(panelRector)
					items.push(panelSecretarioGeneral)
				}else if(item=='tituloProfesional'){
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
					var tituloProfesionalField=new Ext.form.field.ComboBox({
						
						tpl: Ext.create('Ext.XTemplate',
						        '<tpl for=".">',
						            '<div class="x-boundlist-item">{textoNombreDenominacion} - {textoNombreEspanol}</div>',
						        '</tpl>'
						),
						
				    	fieldLabel: 'Titulo Profesional',
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
					var panelDecano = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:decanoField
						}
					});
					var panelRector = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:secretarioGeneralField
						}
					});
					items.push( tituloProfesionalField )
					items.push( panelDecano )
					items.push( panelRector )
					items.push( panelSecretarioGeneral )
				}else if(item=='maestria'){
					
					
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
				        listConfig : {
							listeners : {
								itemclick : function(list,record) {
									maestriaField.clearValue();
									storeEspecialidad.load({
										params: {
											"tipoEspecialidad.codigo":record.get('especialidad')
										}
									});
									
								}
							}
											
						},
						allowBlank: false,
						editable:false
					});
					
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Director',
				        name: "autoridadRegistroDirectorPostgrado.codigo",
						store: storeAutoridadDirectorPosgrado,
				        queryMode: 'local',
				        anchor: '100%',
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
					
					
					
// 					var btnView=new Ext.button.Button({
// 						height : 24,
// 						iconCls : 'btn-lupa-icon',
// 						handler : function(){
							
// 							var codigo=decanoField.getValue();
// 							var win = new APP.Portal.GradoTitulo.Load.TipoDocumentoWindowsMantenimiento({
// 								title: 'Modificar',
// 								uxData:{
// 									codigo:codigo,
// 									action:'edit'
// 								}
// 							});
// 							win.show(
// 								console.log('decanoField'),
// 								console.log(codigo)
// 							);
// 						}
// 					})
// 					var panelTipoDocumento=new Ext.form.Panel({
// 						items:[decanoField]
// 					})
					
// 					var panelView=new Ext.form.Panel({
// 						padding : '0 0 0 10',
// 						items:[btnView]
// 					})
					
// 					var panelTipoDocumentoMantenimiento = new Ext.panel.Panel({
// 						width: 450,
// 						layout:{
// 							type:'hbox',
// 							align:'stretch'
// 						},
// 						items:[
// 							{
// 						     	flex: 92,
// 						     	items:[panelTipoDocumento]
// 						    },
// 						    {
// 						     	flex: 8,
// 						     	items:[panelView]
// 						    },
// 						]
// 					});
					var panelDecano = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:decanoField
						}
					});
					var panelRector = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:secretarioGeneralField
						}
					});
					
					items.push(tipoRegistroField)
					items.push(maestriaField)
// 					items.push(decanoField)
					items.push(panelDecano)
					items.push(panelRector)
					items.push(panelSecretarioGeneral)
				}else if(item=='doctorado'){
					var decanoField=new Ext.form.field.ComboBox({
				    	fieldLabel: 'Director',
				        name: "autoridadRegistroDirectorPostgrado.codigo",
						store: storeAutoridadDirectorPosgrado,
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
					
					var panelDecano = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:decanoField
						}
					});
					var panelRector = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:rectorField
						}
					});
					var panelSecretarioGeneral = new APP.Portal.GradoTitulo.Load.AutoridadDetallePanel({
						uxData:{
							autoridadField:secretarioGeneralField
						}
					});
					items.push( doctoradoField )
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
				        name: 'textoCodigoBarra'
				        //allowBlank: false
			        });
					items.push(codigoBarraField)
				}else if(item=='matriculaPost'){
					var matriculaPostField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula Posgrado',
				        name: 'textoMatriculaPost'
// 				        allowBlank: false
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
// 									universidadField.clearValue();
// 									storeUniversidadBachiller.load({
// 										params: {
											
// 											"pais.codigo":record.get('codigo')
// 										}
// 									});
									
								}
							}
											
						},
						caseSensitive: true
					
						//editable:false

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
// 						autoSelect:false,
// 						caseSensitive: true,
// 						matchFieldWidth: false
						allowBlank: false
// 						editable:false
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
// 				        msgTarget: 'Ingrese Escaneado',
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
// 					    		//prueba
// 					    		//window.open('http://localhost/'+URLBase)
// 					    	}
							Ext.Ajax.request({
				                url: 'gradoTitulo/getFilePath',
				                method: 'POST',
				                params : {
				                	codigo: codigoRegistroEscaneadoField.getValue(),
				                	titpoAdjunto:"2"
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
						headers: {'Content-type':'application/x-www-form-urlencoded; charset=UTF-8'},
						fileUpload: true,
						items:[archivoFieldLabel]
					})
					
					var panelView=new Ext.form.Panel({
						padding : '0 0 0 10',
						items:[btnView]
					})
					
					var panelFoto = new Ext.panel.Panel({
						headers: {'Content-type':'application/x-www-form-urlencoded; charset=UTF-8'},
						fileUpload: true,
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
					items.push(codigoRegistroEscaneadoField.hide())
					items.push(panelFoto)
					
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
				                	titpoAdjunto:"1"
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
						headers: {'Content-type':'application/x-www-form-urlencoded; charset=UTF-8'},
						fileUpload: true,
						items:[archivoFieldLabel]
					})
					
					var panelView=new Ext.form.Panel({
						padding : '0 0 0 10',
						items:[btnView]
					})
					
					var panelFoto = new Ext.panel.Panel({
						headers: {'Content-type':'application/x-www-form-urlencoded; charset=UTF-8'},
						fileUpload: true,
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
					items.push( segundaEspecialidadField.setDisabled(true) )
				}else if ( item == 'numeroCreditos' ){
					var numeroCreditosField=new Ext.form.field.Number({
						name: 'numeroCreditos',
				        fieldLabel: 'N° Creditos',
				        minValue: 0
			        });
					items.push( numeroCreditosField )
				}else if ( item == 'registroMetadato'){
					var registroMetadatoField=new Ext.form.field.Text({
				        fieldLabel: 'Registro Metadato',
				        name: 'textoRegistroMetadato'
				        //allowBlank: false
			        });
// 					items.push( registroMetadatoField.setDisabled(true) )
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
					
				}else if ( item == 'fechaMatriculaPrograma'){
					var fechaMatriculaProgramaField=new Ext.form.field.Date({
				        fieldLabel: 'F/D Matricula Programa',
				        name: 'fechaMatriculaPrograma',
				        format : "d/m/Y",
				        submitFormat :"Y/m/d",
				        allowBlank: true
			        });
// 					items.push(fechaMatriculaProgramaField.setDisabled(true))
					items.push(fechaMatriculaProgramaField)
					
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
				}else if ( item == 'diplomaSexo'){
					var diplomaSexoField = new Ext.form.field.Checkbox({
				        fieldLabel: 'Denominacion Por Sexo',
				        name: 'flagDiplomaSexo',
				        inputValue: '1'
				        
			        });
					items.push(diplomaSexoField)
				}else if(item=='matricula'){
					var matriculaFindField=new Ext.form.field.Text({
				        fieldLabel: 'Matricula',
				        name: 'estudiante.textoMatricula',
// 				        labelWidth: 150,
				        anchor: '100%',
				        readOnly :true,
				        allowBlank: false
			        });	
					var estudianteCodigoField=new Ext.form.field.Text({
				        name: "estudiante.codigo"
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
							storeAlumno.clearData();
							storeAlumno.removeAll();
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
									formTextoMatricula=matriculaField.getValue(),
								 	formTextoNombreCompleto=nombreField.getValue(),
									formTextoDocumento=documentoIdentidadField.getValue()
									var form={
											textoMatricula:matriculaField.getValue(),
											textoNombreCompleto:nombreField.getValue(),
											textoDocumento:documentoIdentidadField.getValue()
										}	
									loadAlumnoListParam(form);		
								}
							});
							var grid=new Ext.grid.Panel({
							    store: storeAlumno,
							    xtype: 'grouped-header-grid',
							    columns: [
									{ text: 'codigo',  dataIndex: 'codigo',flex:1,hidden: true },
									{ text: 'Matricula',  dataIndex: 'textoMatricula',flex:1 },
							        { text: 'Nombre',  dataIndex: 'textoNombreCompleto',flex:1 },
							        { text: 'N° Documento',  dataIndex: 'textoDocumento',flex:1 }
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
										console.log('selecccionado la matricula'+record.get('textoMatricula'));
										textoMatricula=record.get('textoMatricula');
										estudianteCodigo=record.get('codigo');
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
						        name: 'textoNumeroDocumento',
						        labelWidth: 150,
						        anchor: '100%'
					        });
							var panelFindField=new Ext.form.Panel({
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
						   
						    'textoResolucionFacultad',
						    'fechaResolucionFacultad',
						    'resolucionRectoral',
						    'aprobacioncu',
						    'sustentacionTesis',
						    'nombreTrabajoInvestigacion',
						    'modalidadGradoTitulo',
						    'modalidadEstudio',
						    'folio',
						    'libro'
// 						    ,'codigoBarra',
// 						    'agendaGrupo',
// 						    'resolucionCambioNombre',
// 						    'adjunto',
// 						    'adjuntoFoto'
						    
						]})
						
					var fieldsTwo=getUxFields({
						items:[
							'codigoBarra',
						    'agendaGrupo',
						    'resolucionCambioNombre',
						    'adjunto',
						    'adjuntoFoto',
						    //nuevo
						    'programaEstudio',
						    'segundaEspecialidad',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',
// 							'fechaMatriculaPrograma',
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
					
// 					panelLoader.add(formPanelToLoad);
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
							'folio',
							'libro'
// 							,'codigoBarra',
// 						    'agendaGrupo',
// 						    'resolucionCambioNombre',
// 						    'adjunto',
// 						    'adjuntoFoto',
						]
					})
					var fieldsTwo=getUxFields({
						items:[
							'codigoBarra',
						    'agendaGrupo',
						    'resolucionCambioNombre',
						    'adjunto',
						    'adjuntoFoto',
						    //nuevo
						    'programaEstudio',
						    'segundaEspecialidad',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',
// 							'fechaMatriculaPrograma',
							'fechaInicioPrograma',
							'fechaTerminoPrograma',
							'diplomaSexo'
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
					
// 					panelLoader.add(formPanelToLoad);
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
// 							'libro',
							//'registro',
// 							'codigoBarra',
// 						    'agendaGrupo',
// 						    'resolucionCambioNombre',
// 						    'adjunto',
// 						    'adjuntoFoto'
						]
					})
					var fieldsTwo=getUxFields({
						items:[
							'libro',
							'codigoBarra',
						    'agendaGrupo',
						    'resolucionCambioNombre',
						    'adjunto',
						    'adjuntoFoto',
						    //nuevo
						    'programaEstudio',
						    'segundaEspecialidad',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',							
							'fechaMatriculaPosgrado',
							'fechaEgresoPosgrado',
// 							'fechaMatriculaPrograma',
							'fechaInicioPrograma',
							'fechaTerminoPrograma',
							'diplomaSexo'
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
// 							'modalidadGradoTitulo',
							//'rector',
							//'secretarioGeneral',
							//'directorPost',
// 							'folio',
// 							'libro',
							//'registro',
// 							'codigoBarra',
// 						    'agendaGrupo',
// 						    'resolucionCambioNombre',
// 						    'adjunto',
// 						    'adjuntoFoto'
						]
					})
					var fieldsTwo=getUxFields({
						items:[
							'modalidadGradoTitulo',
							'folio',
							'libro',
							'codigoBarra',
						    'agendaGrupo',
						    'resolucionCambioNombre',
						    'adjunto',
						    'adjuntoFoto',
						    //nuevo
						    'programaEstudio',
						    'segundaEspecialidad',
							'numeroCreditos',
							'registroMetadato',
							'procedenciaTituloPedagogico',
							'fechaEgresoPosgrado',
// 							'fechaMatriculaPrograma',
							'fechaMatriculaPosgrado',
							'fechaInicioPrograma',
							'fechaTerminoPrograma',
							'diplomaSexo'
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
			headers: {'Content-type':'application/x-www-form-urlencoded; charset=UTF-8'},
			fileUpload: true,
			bodyPadding: 5,
			fieldDefaults: {
    	        width : 450,
    	        labelWidth : 150
    	    },
    	    width: 950,
		    height: 600,
// 			width: 450,
// 		    height: 730,
		    layout:'column'
		})
		
		agregarPanel= new Ext.form.Panel({
			headers: {'Content-type':'application/x-www-form-urlencoded; charset=UTF-8'},
			fileUpload: true,
			bodyPadding: 5,
			fileUpload: true,
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
								codigo:uxData.codigo,
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
							url:'gradoTitulo/getEstudianteRegistro',
							params:{
								codigo:uxData.codigo,
							}
						})
						//agregarPanel.doLayout();
					}
				
				}
			}
		});
		
		APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento,Ext.window.Window,{});
	
	APP.Portal.GradoTitulo.Load.GradoTituloWindowsNumeroRegistro=function(config){
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
	        	Ext.Msg.confirm("Confimacion", "¿Desea Cambiar el numero de Registro?", function(btnText){
	                if(btnText === "yes"){
	                	var save = agregarPanel.getForm();
	    	            if (save.isValid()) {
	    	            	save.submit({
	    	                	url: 'gradoTitulo/updateNumeroRegistro',
	    	                    success: function(form, action) {
	    	                    	var globalPanel = APP.Portal.GradoTitulo.Load.GlobalPanel;
	    	                    	globalPanel.getGradoTituloPanel().getListPanel().loadList();
	    							Ext.Msg.alert('Success', 'Se ha grabado correctamente.');
	    							me.close();
	    	                    
	    	                    },
	    	                    failure: function(form, action) {
	    	                        Ext.Msg.alert('Failed', 'error');
	    	                    }
	    	                });
	    	            }
                	}
	        	})
	            
	        }
		});
		var registroField=new Ext.form.field.Number({
			name: 'numeroRegistro',
	        fieldLabel: 'N° Registro',
	        minValue: 0
        });
		var codigo = new Ext.form.field.Text({
	        name: "codigo"
        });
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
			fieldDefaults: {
		        width : 500,
		        labelWidth : 200
		    },
			defaultType: 'textfield',
			items:[
				codigo.hide(),
				registroField
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
							url:'gradoTitulo/getEstudianteRegistro',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
	
		
		APP.Portal.GradoTitulo.Load.GradoTituloWindowsNumeroRegistro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloWindowsNumeroRegistro,Ext.window.Window,{});
	
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
	        fieldLabel: 'Fecha de Cierre',
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
			iconCls : 'btn-delete-icon',
			text : 'Cancelar',
			handler :function() {
				me.close();
				
			}
		});
		findButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-search-icon',
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
			iconCls : 'btn-add-icon',
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
					iconCls : 'btn-candado-icon',
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
					iconCls : 'btn-export-icon',
					cls : 'btn',
					handler:function(){
						console.log("formExcel "+formExcel);
						//console.log("formExcel.toSource() "+formExcel.toSource());
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
					iconCls : 'btn-add-icon',
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
		var itemsPerPage = 25;	
		toolbar=new APP.Portal.GradoTitulo.Load.GradoTituloToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
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
				limit: 25,
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
				limit: 25
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
				url : 'gradoTitulo/delete',
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
				url : 'gradoTitulo/enviarSunedu',
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
		function visualizar(record){
			var codigo=record.get('codigo');
			var tipo=record.get('abreviaturaGradoTitulo');
			var win = new APP.Portal.GradoTitulo.Load.GradoTituloWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo : codigo,
					tipo : tipo,
					action : 'view'
				}
			});
			win.show();
			
		}
		function editarNumeroRegitro(record){
			var codigo=record.get('codigo');
			var candado = record.get('flagcandado');
			console.log('candado')
			console.log(candado)
			if( candado == "1" ){
				var win = new APP.Portal.GradoTitulo.Load.GradoTituloWindowsNumeroRegistro({
					title: 'Modificar',
					uxData:{
						codigo:codigo,
						action:'edit'
					}
				});
				win.show();	
			}else{
				 Ext.Msg.alert('Alerta', 'El registro tiene que estar Cerrado');
			}
			
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
        		'duplicado',
        		'anulado',
        		'estudianteRegistroPadre',
        		'egresadoCiclo',
        		'resolucionCambioNombre',
        		'flagcandado',
        		'flagDiplomaSexo',
        		'textoProcedenciaMaestriaExtranjero',
        		
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
				{ text: 'Escuela Post',  dataIndex: 'especialidadPostgrado',flex:1 },
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
				//{ text: 'Fech Obtencion del G/T',  dataIndex: 'actoFecha',flex:1, hidden: true},
				{ text: 'Fecha de Sustentacion',  dataIndex: 'actoFecha',flex:1, hidden: true},
				{ text: 'Tesis',  dataIndex: 'tesis',flex:1, hidden: true},
				{ text: 'Mod Estudio',  dataIndex: 'modadlidad',flex:1, hidden: true},
				{ text: 'Proc Revalida Pais',  dataIndex: 'procedenciaRevalidadPais',flex:1, hidden: true},
				{ text: 'Proc Revalidad Univ. ',  dataIndex: 'procedenciaRevalidadUniversidad',flex:1, hidden: true},
				//{ text: 'Proc Revalidad Univ. Grado',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
				{ text: 'Proc Revalidad Univ. Grado',  dataIndex: 'procedenciaRevalidadGradoExtranjero',flex:1, hidden: true},
				{ text: 'Resolucion Rectoral',  dataIndex: 'resolucionNumero',flex:1, hidden: true},
				{ text: 'Resolucion Facultad',  dataIndex: 'resolucionFacultad',flex:1, hidden: true},
				//{ text: 'Fecha de Diploma',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
				{ text: 'Fecha CU',  dataIndex: 'diplomaFecha',flex:1, hidden: true},
				//{ text: 'Codigo de Barra',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
				{ text: 'Numero Diploma',  dataIndex: 'diplomaNumero',flex:1, hidden: true},
				{ text: 'Tipo de Emision',  dataIndex: 'diplomaTipoEmision',flex:1, hidden: true},
				{ text: 'N° Libro',  dataIndex: 'registroLibro',flex:1, hidden: true}, 
				{ text: 'N° Folio',  dataIndex: 'registroFolio',flex:1, hidden: true},
				{ text: 'Nom. Cargo 1',  dataIndex: 'autoridadUno',flex:1, hidden: true},
				{ text: 'Cargo 1',  dataIndex: 'cargoUno',flex:1, hidden: true},
				{ text: 'Nom Cargo 2',  dataIndex: 'autoridadDos',flex:1, hidden: true},
				{ text: 'Cargo 2',  dataIndex: 'cargoDos',flex:1, hidden: true},
				{ text: 'Nom Cargo 3',  dataIndex: 'autoridadTres',flex:1, hidden: true},
				{ text: 'Cargo 3',  dataIndex: 'cargoTres',flex:1, hidden: true},
				{ text: 'Enviado SUNEDU',  dataIndex: 'enviadoSunedu',flex:1, hidden: true},
				{ text: 'Duplicado',  dataIndex: 'duplicado',flex:1, hidden: true},
				{ text: 'Anulado',  dataIndex: 'anulado',flex:1, hidden: true},
				{ text: 'N° registro duplicado',  dataIndex: 'estudianteRegistroPadre',flex:1, hidden: true},
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
								text:'Cambiar N° Registro',
								handler: function(){
									editarNumeroRegitro(record);
								}
							},
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
		    					text:'Diploma',
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
			height:800,
			title : 'GradoTitulo',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.GradoTitulo.Load.GradoTituloPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.GradoTituloPanel,Ext.panel.Panel,{});

	

	APP.Portal.GradoTitulo.Load.AutoridadWindows=function(config){	
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
		
		APP.Portal.GradoTitulo.Load.AutoridadWindows.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.AutoridadWindows,Ext.window.Window,{});
	

	
	


	APP.Portal.GradoTitulo.Load.AutoridadDetallePanel=function(config){	
		var me=this,
			uxData = config.uxData;
		var btnView=new Ext.button.Button({
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){
				var codigo=uxData.autoridadField.getValue();
				if( codigo ){
					var win = new APP.Portal.GradoTitulo.Load.AutoridadWindows({
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
		
		APP.Portal.GradoTitulo.Load.AutoridadDetallePanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.GradoTitulo.Load.AutoridadDetallePanel,Ext.panel.Panel,{});
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</script>

<script type="text/javascript">
	APP.Portal.GradoTitulo.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.GradoTitulo.Load.GradoTituloPanel({});
		
		me.getGradoTituloPanel=function(){
			return datoGeneralPanel;
		}
		/*
		var panelFullContainer = new Ext.panel.Panel({
			layout:{
				type:'hbox',
				align:'stretch'
			},
			style: {
			       borderColor: 'yellow',
			       borderStyle: 'solid'
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
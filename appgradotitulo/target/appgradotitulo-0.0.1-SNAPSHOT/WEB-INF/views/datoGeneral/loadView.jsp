<script type="text/javascript">
	Ext.ns('APP.Portal.DatoGeneral.Load')
	APP.Portal.DatoGeneral.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.DatoGeneral.Load.DatoGeneralWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		
		var agregarPanel,
			saveButton, 
			cancelarButton,
			storeSexo,
			storeFacultad,
			storeEspecialidad,
			storeTipoDocumento,
			storeCiclo,
			storeAlumno,
			storeEstado,
			nombreTipoDocumento;
		var selectDateInicioCiclo;
		var selectDateFinalCiclo;
		cancelarButton = new Ext.Button({
			width : 80,
			text : 'Cancelar',
			iconCls : 'btn-delete-icon',
			handler :function() {
				me.close();
				
			}
		});
		function validadTipoDocumento(name){
			var result;
			console.log("name "+name)
			if(name =="DNI"){
				console.log("Es un DNI")
				result=validadNumeroDocumento();
			}else{
				result=true;
			}
			console.log('validadTipoDocumento result '+result)
			return result;
		}
		function validadNumeroDocumento(){
			var  result= false;
			var numeroDocumento = documentoFieldLabel.getValue();
			var textoError= 'Ingrese un  numero de DNI correcto';
			console.log('numeroDocumento '+numeroDocumento)
			var sizeNumeroDocumento= numeroDocumento.length;
			console.log('sizeNumeroDocumento '+sizeNumeroDocumento)
			if(sizeNumeroDocumento==8){
				if(isNumero(numeroDocumento)){
					result=true;
				}else{
					Ext.Msg.alert('Failed', textoError);
				}
			}else{
				Ext.Msg.alert('Failed', textoError);
			}
			console.log('validadNumeroDocumento result '+result)
			return result;

		}
// 		saveButton = new Ext.Button({
// 			width : 80,
// 			iconCls : 'btn-register-icon',
// 			text : 'Grabar',
// 	        handler: function() {
// 	        	if(validadTipoDocumento(nombreTipoDocumento)){
// 	        	Ext.Msg.confirm("Confimacion", "¿Desea Grabar el registro?", function(btnText){
//                     if(btnText === "yes"){
//                     	//if(validadTipoDocumento(nombreTipoDocumento)){
//         		            var save = agregarPanel.getForm();
//         		            if (save.isValid()) {
//         		            	save.submit({
//         		                	url: 'datoGeneral/saveEstudiante',
//         		                    success: function(form, action) {
//         		                    	var globalPanel=APP.Portal.DatoGeneral.Load.GlobalPanel;
//         		                    	globalPanel.getDatoGeneralPanel().getListPanel().loadList();
//         								Ext.Msg.alert('Success', 'Se ha grabado el Estudiante correctamente.');
//         								me.close();
        		                    
//         		                    },
//         		                    failure: function(form, action) {
//         		                        Ext.Msg.alert('Failed', 'error');
//         		                    }
//         		                });
//         		            }	        	
//         	            }
//                     //}
//                     else if(btnText === "no"){
                        
//                     }
//                 }, this);
// 	        }
// 	        }
// 		});
		
		saveButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-register-icon',
			text : 'Grabar',
			name: 'grabar',
	        handler: function() {

	        	function submit( save ){
	        		if (save.isValid()) {
        				save.submit({	
    		        		url: 'datoGeneral/saveEstudiante',
    	                    success: function(form, action) {
    	                    	var globalPanel=APP.Portal.DatoGeneral.Load.GlobalPanel;
    	                    	globalPanel.getDatoGeneralPanel().getListPanel().loadList();
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
	        	
	        	if(validadTipoDocumento(nombreTipoDocumento)){
	        		var save = agregarPanel.getForm();
	        		
// 		            save.getFields().each(function(field) {
		            	
// 		            	if( field.name == "fechaInicio" && field.value == null ){
// 		            		field.setDisabled(true)
		            	
// 		            	}else if(field.name == "fechaFinal" && field.value == null){
// 		            		field.setDisabled(true)
		            	
// 		            	}
// 		            });

					var mensaje = validateDateEgreso();
					if (mensaje.length > 0) {
						Ext.Msg.alert('Alerta', mensaje);
					} else {
						mensaje = validateAniosEstudio( fechaIngresanteMatriculaField, fechaTramiteConstanciaField );
						if(mensaje.length > 0){
							console.log( " mensaje " + mensaje)
							
							Ext.Msg.confirm("Confimacion", mensaje, function(btnText){

								if(btnText === "yes"){
									submit( save )
	                            }
	                            else if(btnText === "no"){
	                                
	                            }
	                        }, this);
							
						}else{
							submit( save )
						}
					}

	        	}
	        }
		});
		
		function validateAniosEstudio( fechaIncio,FechaFinal ){
			var dateInicio = fechaIncio.value;
			console.log("dateInicio "+dateInicio);
        	var dateFinal = FechaFinal.value;
			console.log("dateFinal "+dateFinal);
			var mensaje = "";
			
			if(dateInicio != null && dateFinal != null){	
    			var delta = Date.parse(dateFinal) - Date.parse(dateInicio);
				var anios = parseInt(((delta / 86400000) / 350)).toString();
				console.log("anios " +anios);
				
				if(anios < 5){
					mensaje ="el estudiante fecha de ingreso y  una  fecha de egreso menor a 5 años, desea guardarlo de todas maneras ? " ;
				}	        			
    		}
			
			return mensaje;

		}
		
		function validateDateEgreso() {
			var mensaje = "";
			
			if(!Ext.isEmpty(selectDateInicioCiclo)  && !Ext.isEmpty(selectDateFinalCiclo) && !Ext.isEmpty(fechaTramiteConstanciaField.getValue())){
        		console.log("selectDateInicioCiclo = "+selectDateInicioCiclo);	        		
        		console.log("selectDateFinalCiclo = "+selectDateFinalCiclo);		        		
        		var dateInicioCiclo = new Date(selectDateInicioCiclo);
        		console.log("dateInicioCiclo = "+dateInicioCiclo);
        		var dateFinalCiclo = new Date(selectDateFinalCiclo);
        		console.log("dateFinalCiclo = "+dateFinalCiclo);
        		var currentDate = fechaTramiteConstanciaField.getValue();
        		console.log("fechaTramiteConstanciaField.getValue() = " + currentDate);

        		if (currentDate > dateInicioCiclo && currentDate < dateFinalCiclo ){

        	    } else {
        	    	mensaje = "La fecha de egreso no se encuentra dentro el rango de fecha de duracion del ciclo";
        	    }

    		}
			return mensaje;
		}
		
		storeSexo = Ext.create('Ext.data.Store', {
		    fields: ['codigo', 'textoSexo'],
		    data : [
		        {"codigo":"F", "textoSexo":"Femenino"},
		        {"codigo":"M", "textoSexo":"Masculino"}
	
		    ]
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
		        'textoNombreEspanol'
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
		storeCiclo = new Ext.data.Store({
		    fields:[
				'codigo',
		        'textoSemestre',
		        'fechaInicioCiclo',
	            'fechaFinalCiclo'
			],
			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'datoGeneral/getCicloList',
// 		        url: 'mantenimiento/ciclo/getCicloList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount',
		            limit:''
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
		
		me.loadStoreTipoDocumento=function(){
			console.log('cargando Store de APP.Portal.DatoGeneral.Load.DatoGeneralWindowsMantenimiento')
			storeTipoDocumento.load();
		}
		
		tipoDocumentoList = new APP.Portal.DatoGeneral.Load.TipoDocumentoList({})
		me.getTipoDocumentoList =function(){
			return tipoDocumentoList;
		}
		
        var matriculaFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Matricula',
	        name: 'textoMatricula'
        });

        var nombreFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Nombre',
	        name: 'textoNombre',
	        allowBlank: false
        });
        var apellidoPaternoFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Apellido Paterno',
	        name: 'textoPaterno'/*,
	        allowBlank: false*/
        });
        var apellidoMaternoFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Apellido Materno',
	        name: 'textoMaterno'/*,
	        allowBlank: false
	        */
        });
        var sexoFieldLabel=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Sexo',
	        name: 'textoSexo',
			store: storeSexo,
	        queryMode: 'local',
	        displayField: 'textoSexo',
	        valueField: 'codigo',
	        allowBlank: false,
			editable:false
        });	
        var tipoDocumentoField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Doc. de Identidad',
	        name: "tipoDocumento.codigo",
	        labelWidth: 150,
	        anchor: '100%',
	        store: storeTipoDocumento,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
	        allowBlank: false,
			editable:false,
			listConfig : {
				listeners : {
					itemclick : function(list,record) {
						nombreTipoDocumento =record.get('textoNombre');
						console.log('nombreTipoDocumento '+nombreTipoDocumento)
						/*
						if(nombreTipoDocumento=="DNI"){
							console.log('Do')							
							documentoFieldLabel.add({
								maxLength:7
							})
						}
						*/
						
						if(nombreTipoDocumento=="NO DEFINIDO"){
							documentoFieldLabel.setDisabled(true);
						}
					}
				}
								
			},
        });	
        var btnView=new Ext.button.Button({
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){
				
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
			        	var win = new APP.Portal.DatoGeneral.Load.TipoDocumentoWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
			        }
				});
				grid=new APP.Portal.DatoGeneral.Load.TipoDocumentoList({})
				me.getGrid=function(){
					return grid;
				}
				
				var panelLoader = new Ext.panel.Panel({
					width: 400,
				    height: 250,
					items:[grid]
				})
				var win =Ext.create('Ext.window.Window', {
				    title: 'Tipo Documento',
				    closable:false,
				    modal: true,
				    items: [panelLoader],
				    buttons:[cancelarButton,addButton]
				    
				});
				win.show();
			}
		})
        
		var panelTipoDocumento=new Ext.form.Panel({
			items:[tipoDocumentoField]
		})
		var panelView=new Ext.form.Panel({
			padding : '0 0 0 10',
			items:[btnView]
		})
		
		var panelTipoDocumentoMantenimiento = new Ext.panel.Panel({
			width: 450,
			layout:{
				type:'hbox',
				align:'stretch'
			},
			items:[
				{
			     	flex: 92,
			     	items:[panelTipoDocumento]
			    },
			    {
			     	flex: 8,
			     	items:[panelView]
			    },
			]
		});
        var documentoFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Numero de Documento',
	        name: 'textoDocumento'
        	,allowBlank: false
        });
        var codigoField=new Ext.form.field.Text({
	        name: 'codigo'
        });
		
		var codigoTelefonoFijoField=new Ext.form.field.Text({
	        name: 'codigoTelefonoFijo'
        });
		
		var codigoTelefonoCelularField=new Ext.form.field.Text({
	        name: 'codigoTelefonoCelular'
        });
		
		var adjuntoCodigoField=new Ext.form.field.Text({
	        name: 'adjunto.codigo'
        });
        var matriculaFindField=new Ext.form.field.Text({
	        fieldLabel: 'Matricula',
	        name: 'estudiante.textoMatricula',
	        labelWidth: 150,
	        anchor: '100%',
	        readOnly :true,
	        allowBlank: false
        });	
		
        function loadListParam(form){
			storeAlumno.load({
				params: {
					textoMatricula:form.textoMatricula,
					textoNombreCompleto:form.textoNombreCompleto
				}
			});	
		}
        
        var telefonoFijoFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Telefono Fijo',
	        name: 'textoNumeroTelefonoFijo'
        });
        var telefonoCelularFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Telefono Celular',
	        name: 'textoNumeroTelefonoCelular'
        });		
        var facultadFieldLabel=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Facultad',
	        name: "facultad.codigo",
			store: storeFacultad,
	        queryMode: 'local',
	        displayField: 'textoNombreEspanol',
	        valueField: 'codigo',
			listConfig : {
						listeners : {
							itemclick : function(list,record) {
								especialidadFieldLabel.clearValue();
								storeEspecialidad.load({
									params: {
										"facultad.codigo":record.get('codigo')
									}
								});
								
							}
						}
										
					},
	        allowBlank: false,
			editable:false
        });	
        var especialidadFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Especialidad',
			        name: "especialidad.codigo",
					store: storeEspecialidad,
			        queryMode: 'local',
			        displayField: 'textoNombreEspanol',
			        valueField: 'codigo',
					allowBlank: false,
					editable:false
		});
        var cicloField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Ciclo de Egreso',
	        name: "textoCicloEgreso",
			store: storeCiclo,
	        queryMode: 'local',
	        displayField: 'textoSemestre',
	        valueField: 'codigo',
			/*allowBlank: false,*/
			listConfig : {
				listeners : {
					itemclick : function(list,record) {
						console.info({recordcombo:record});
						console.log("itemclick fechaInicioCiclo = "+record.get('fechaInicioCiclo'));
						console.log("itemclick fechaFinalCiclo = "+record.get('fechaFinalCiclo'));
						console.log();
						selectDateInicioCiclo = record.get('fechaInicioCiclo');
        				selectDateFinalCiclo = record.get('fechaFinalCiclo');
					}
				}
										
			},
			editable:false
		});
        var fechaTramiteConstanciaField=new Ext.form.field.Date({
        	fieldLabel: 'Fecha de Egreso',
	        name: 'fechaTramiteConstancia',
	        //format : "Y/m/d",
	        format : "d/m/Y",
	        submitFormat :"Y/m/d",
	        getSubmitValue: function(){
	        	var format = this.submitFormat || this.format,
	        	value = this.getValue();
	        	Ext.Date.format(value, format)
	        	return value ? Ext.Date.format(value, format) : null;
	        }
	        //allowBlank: false
		});
        var fechaIngresanteMatriculaField=new Ext.form.field.Date({
        	fieldLabel: 'Fecha matricula Ingr.',
	        name: 'fechaIngresanteMatricula',
	        //format : "Y/m/d",
	        format : "d/m/Y",
	        submitFormat :"Y/m/d",
	        getSubmitValue: function(){
	        	var format = this.submitFormat || this.format,
	        	value = this.getValue();
	        	Ext.Date.format(value, format)
	        	return value ? Ext.Date.format(value, format) : null;
	        } 
         	//allowBlank: false
        
		});
		var numeroResolucionFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Numero de Resolucion',
	        name: 'textoNumeroResolucion',
	        allowBlank: false
        });		

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
		/*
		var viewButtonFieldLabel;
		if(uxData&&uxData.action=='edit'){
			viewButtonFieldLabel=new Ext.Button({
			    text: 'Ver imagen',
			    width :'100%',
			    handler: function(){
			    	var URLBase = URLBaseFieldLabel.getValue();
			    	console.log('URLBase '+URLBase);
			    	if(URLBase !=null&&URLBase!=''){
			    		window.open('http://localhost/'+URLBase)
			    	}
			    	
			    }
			})	
		}else{
			viewButtonFieldLabel=null
		}
		*/
		
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
    		fieldDefaults: {
    	        width : 450,
    	        labelWidth : 150
    	    },		
			items:[
				codigoField.hide(),
				codigoTelefonoFijoField.hide(),
				codigoTelefonoCelularField.hide(),
				adjuntoCodigoField.hide(),
		        matriculaFieldLabel,
		        nombreFieldLabel,
		        apellidoPaternoFieldLabel,
		        apellidoMaternoFieldLabel,
		        sexoFieldLabel,
		        //tipoDocumentoField,
		        panelTipoDocumentoMantenimiento,
		        documentoFieldLabel,
		        telefonoFijoFieldLabel,
		        telefonoCelularFieldLabel,
		        //facultadFieldLabel,
		       	//especialidadFieldLabel,
		        cicloField,
		        fechaIngresanteMatriculaField,
		        fechaTramiteConstanciaField,
		        //URLBaseFieldLabel.hide(),
		        //panelFoto
		        
			],
			buttons:[cancelarButton,saveButton]
			
		});	
		
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[agregarPanel],
			listeners :{
				afterrender:function(){
					if(uxData&&uxData.action=='edit'){
						agregarPanel.load({
							url:'datoGeneral/getEstudiante',
							params:{
								codigo:uxData.codigo
							}
						})
					}else if(uxData&&uxData.action=='view'){
						
						agregarPanel.add({
							buttons:[saveButton]
						})
						
						agregarPanel.load({
							url:'datoGeneral/getEstudiante',
							params:{
								codigo:uxData.codigo,
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.DatoGeneral.Load.DatoGeneralWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.DatoGeneral.Load.DatoGeneralWindowsMantenimiento,Ext.window.Window,{});
	
	APP.Portal.DatoGeneral.Load.TipoDocumentoWindowsMantenimiento=function(config){	
		var me=this,
			uxData = config.uxData;
		
		var storeEstado;
		storeEstado = Ext.create('Ext.data.Store', {
		    fields: ['codigo', 'textoNombreEspanol'],
		    data : [
		        {"codigo":"1", "textoNombreEspanol":"Si"},
		        {"codigo":"0", "textoNombreEspanol":"No"}
	
		    ]
		});
		var cancelarButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-delete-icon',
			text : 'Cancelar',
			handler :function() {
				me.close();
				
			}
		});
		
		var addButton = new Ext.Button({
			width : 80,
			iconCls : 'btn-register-icon',
			text : 'Grabar',
	        handler: function() {
	        	var save = panelField.getForm();
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'datoGeneral/saveTipoDocumentoIdentidad',
	                    success: function(form, action) {
							var globalPanel=APP.Portal.DatoGeneral.Load.GlobalPanel;
							//var rutaStoreTipoDocumento=globalPanel.getDatoGeneralPanel().getDatoGeneralWindowsMantenimiento();
							//rutaStoreTipoDocumento.loadStoreTipoDocumento();
							//globalPanel.getDatoGeneralPanel().getDatoGeneralWindowsMantenimiento().getTipoDocumentoList().loadStoreTipoDocumento();
							//globalPanel.getDatoGeneralPanel().getDatoGeneralWindowsMantenimiento().loadStoreTipoDocumento();
							globalPanel.getDatoGeneralPanel().getListPanel().getToolbar().getWin().getGrid().loadStoreTipoDocumento();
							globalPanel.getDatoGeneralPanel().getListPanel().getToolbar().getWin().loadStoreTipoDocumento();;
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
		var codigoField=new Ext.form.field.Text({
	        name: "codigo"
        });		
		var codigoSuneduField=new Ext.form.field.Text({
	        fieldLabel: 'Codigo Sunedu',
	        name: 'codigoExterno',
	        labelWidth: 150,
	        anchor: '100%'
        });
		var nombreDocumentoField=new Ext.form.field.Text({
	        fieldLabel: 'Nombre',
	        name: 'textoNombre',
	        labelWidth: 150,
	        anchor: '100%'
        });
		var estadoField=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Valido para la SUNEDU',
	        name: 'flagSunedu',
	        labelWidth: 150,
	        anchor: '100%',
			store: storeEstado,
	        queryMode: 'local',
	        displayField: 'textoNombreEspanol',
	        valueField: 'codigo',
	        allowBlank: false,
			editable:false
        });	
		var panelField=new Ext.form.Panel({
			height: 100,
			width : 400,
			bodyPadding: 5,
			items:[
				codigoField.hide(),
				codigoSuneduField,
				nombreDocumentoField,
				estadoField
			]
		})
		
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[panelField],
			buttons:[cancelarButton,addButton],
			listeners :{
				afterrender:function(){
					if(uxData&&uxData.action=='edit'){
						panelField.load({
							url:'datoGeneral/getTipoDocumento',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.DatoGeneral.Load.TipoDocumentoWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.DatoGeneral.Load.TipoDocumentoWindowsMantenimiento,Ext.window.Window,{});
	
	
	APP.Portal.DatoGeneral.Load.DatoGeneralFiltro=function(config){
		var me=this;
		
		
	    	    
		Ext.apply(config,{
			width : 450,
			createPicker:function(){
				var me = this,
				storeFacultad,	estado,cancelarButton,findButton;
				
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
							textoMatricula:matriculaFieldLabel.getValue(),
							textoNombreCompleto:nombreCompletoFieldLabel.getValue(),
							textoCodigoExternoDocumento:codigoExternoDocumentoFieldLabel.getValue(),
							textoNumeroDocumento:numeroDocumentoFieldLabel.getValue(),
		        			textoNombreEspanolFacultad:facultadFieldLabel.getValue(),
		        			textoNombreEspanolEspecialidad:especialidadFieldLabel.getValue(),
		        			textoNumeroResolucion:numeroResolucionFieldLabel.getValue(),
		        			fechaCreacion:fechaCreacionFieldLabel.getSubmitValue()
		        			
						},
						findValue=[],
						regularExpresion=/,|( )/;
						var globalPanel=APP.Portal.DatoGeneral.Load.GlobalPanel;
						globalPanel.getDatoGeneralPanel().getListPanel().getToolbar().postDataFind(form);
						globalPanel.getDatoGeneralPanel().getListPanel().loadListParam(form);
						
						function posicionValue(rawValue){
							var result = null;
							if(regularExpresion.test(rawValue) == true) {
								result =' ('+rawValue+')';
							}else{
								result = rawValue;
							}
							return result;
						}
						
						if(form.textoMatricula !=null &&form.textoMatricula !=""){
							findValue.push('Matricula:'+posicionValue(form.textoMatricula));
						}
						if(form.textoNombreCompleto !=null && form.textoNombreCompleto  != ""){
							findValue.push('Nombre: '+posicionValue(form.textoNombreCompleto));
						}
						if(form.textoCodigoExternoDocumento !=null && form.textoCodigoExternoDocumento!=""){
							findValue.push('Documento de Identidad: '+posicionValue(form.textoCodigoExternoDocumento));
						}
						
						if(form.textoNumeroDocumento!=null && form.textoNumeroDocumento !=""){
							findValue.push('Numero de Documento: '+form.textoNumeroDocumento);
						}
						if(form.textoNombreEspanolFacultad !=null && form.textoNombreEspanolFacultad !=""){
							findValue.push('Facultad: '+form.textoNombreEspanolFacultad);
						}
						if(form.textoNombreEspanolEspecialidad !=null && form.textoNombreEspanolEspecialidad !=""){
							findValue.push('Especialidad: '+form.textoNombreEspanolEspecialidad);
						}
						if(form.textoNumeroResolucion !=null && form.textoNumeroResolucion !=""){
							findValue.push('Numero de Resolucion: '+form.textoNumeroResolucion);
						}
						
						me.setRawValue(findValue);
						pickerPanel.getForm().reset();
						me.collapse();
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
				        'textoNombreEspanol'
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
				
		        var matriculaFieldLabel=new Ext.form.field.Text({
			        fieldLabel: 'Matricula',
			        name: 'textoMatricula'
			       
		        });
		        
		        var nombreCompletoFieldLabel=new Ext.form.field.Text({
			        fieldLabel: 'Nombre',
			        name: 'textoNombreCompleto'
			       
		        });
	
		        var codigoExternoDocumentoFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Documento de Identidad',
			        name: 'textoCodigoExternoDocumento',
					store: storeTipoDocumento,
			        queryMode: 'local',
			        displayField: 'textoNombre',
			        valueField: 'codigoExternoDocumento',
					editable:false
		        });	
		        var numeroDocumentoFieldLabel=new Ext.form.field.Text({
			        fieldLabel: 'Numero de Documento',
			        name: 'textoNumeroDocumento',
			        
		        });
		      
		        var facultadFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Facultad',
			        name: 'textoNombreEspanolFacultad',
					store: storeFacultad,
			        queryMode: 'local',
			        displayField: 'textoNombreEspanol',
			        valueField: 'textoNombreEspanol',
					listConfig : {
								listeners : {
									itemclick : function(list,record) {
										especialidadFieldLabel.clearValue();
										storeEspecialidad.load({
											params: {
												"facultad.codigo":record.get('codigo')
											}
										});
										
									}
								}
												
							},
					editable:false
		        });	
		        var especialidadFieldLabel=new Ext.form.field.ComboBox({
					fieldLabel: 'Especialidad',
					name: 'textoNombreEspanolEspecialidad',
					store: storeEspecialidad,
					queryMode: 'local',
					displayField: 'textoNombreEspanol',
					valueField: 'textoNombreEspanol',
					editable:false
				});
		        
				var numeroResolucionFieldLabel=new Ext.form.field.Text({
			        fieldLabel: 'Numero de Resolucion',
			        name: 'textoNumeroResolucion'
		        });	  
				var fechaCreacionFieldLabel=new Ext.form.field.Date({
			        fieldLabel: 'Fecha de Registro',
			        name: 'fechaCreacion',
			        //format : "Y/m/d",
			       	format : "d/m/Y",
			        allowBlank: false
				});	  
			    
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
					       matriculaFieldLabel,
					       nombreCompletoFieldLabel,
					       codigoExternoDocumentoFieldLabel,
					       numeroDocumentoFieldLabel//,
					      //facultadFieldLabel,
					      //especialidadFieldLabel,
					      //numeroResolucionFieldLabel,
					      //fechaCreacionFieldLabel
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
			        	                'Matricula',
			        	                'Nombre',
			        	                'Documento de Identidad',
			        	                'Numero de Documento',
			        	                'Facultad',
			        	                'Especialidad',
			        	                'Numero de Resolucion'
			        		],
				        	form={
				        			textoMatricula:'Matricula',
				        			textoNombreCompleto:'Nombre',
				        			textoCodigoExternoDocumento:'Documento de Identidad',
				        			textoNumeroDocumento:'Numero de Documento',
				        			textoNombreEspanolFacultad:'Facultad',
				        			textoNombreEspanolEspecialidad:'Especialidad',
				        			textoNumeroResolucion:'Numero de Resolucion'
				        			
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
					
						
						
						var globalPanel=APP.Portal.DatoGeneral.Load.GlobalPanel,
						textoFind,
						value,
						getIndice =globalPanel.getDatoGeneralPanel().getListPanel().getToolbar().getFiltroField();
						
						
						getIndice.valueMatriculaFieldLabel(form.textoMatricula);
						getIndice.valueNombreCompletoFieldLabel(form.textoNombreCompleto);
						getIndice.valueNumeroDocumentoFieldLabel(form.textoNumeroDocumento);
						
						if(form.textoCodigoExternoDocumento != null){
							textoFind=((form.textoCodigoExternoDocumento).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreTipoDocumento(),textoFind,'textoNombre');
							getIndice.valueCodigoExternoDocumentoFieldLabel(value);	
							form.textoCodigoExternoDocumento=value;
						}else{
							getIndice.valueCodigoExternoDocumentoFieldLabel(form.gradoAcademico)
						}
						
						if(form.textoNombreEspanolFacultad != null){
							textoFind=((form.textoNombreEspanolFacultad).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreFacultad(),textoFind,'textoNombreEspanol');
							getIndice.valueFacultadFieldLabel(value);	
							form.textoNombreEspanolFacultad=value;
						}else{
							getIndice.valueFacultadFieldLabel(form.textoNombreEspanolFacultad)
						}
						if(form.textoNombreEspanolEspecialidad != null){
							textoFind=((form.textoNombreEspanolEspecialidad).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreEspecialidad(),textoFind,'textoNombreEspanol');
							getIndice.valueEspecialidadFieldLabel(value);	
							form.textoNombreEspanolEspecialidad=value;
						}else{
							getIndice.valueEspecialidadFieldLabel(form.textoNombreEspanolEspecialidad)
						}
						console.log('FORM FINAL '+form);
						globalPanel.getDatoGeneralPanel().getListPanel().getToolbar().postDataFind(form);
						globalPanel.getDatoGeneralPanel().getListPanel().loadListParam(form);
						console.log('Presiono Enter');
			       	//}
		       	}
			}

		});
		APP.Portal.DatoGeneral.Load.DatoGeneralFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.DatoGeneral.Load.DatoGeneralFiltro,APP.form.field.Picker,{});
	
	APP.Portal.DatoGeneral.Load.DatoGeneralToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.DatoGeneral.Load.DatoGeneralFiltro({
			
		});
		
		me.getFiltroField=function(form){
			return filtroField;
		}
		
		
		var dataFind=[];
		me.postDataFind=function(form){
			dataFind=form;
			var fechaFormat='1000/01/01';
			if(isEmpty(dataFind.fechaCreacion)){
				dataFind.fechaCreacion=fechaFormat;
			}
			if(isEmpty(dataFind.fechaCreacion)){
				dataFind.fechaCreacion=fechaFormat;
			}
			if(isEmpty(dataFind.textoCodigoExternoDocument)){
				dataFind.textoCodigoExternoDocumento="";
			}
			if(isEmpty(dataFind.textoNombreEspanolFacultad)){
				dataFind.textoNombreEspanolFacultad="";
			}
			if(isEmpty(dataFind.textoNombreEspanolEspecialidad)){
				dataFind.textoNombreEspanolEspecialidad="";
			}
	
		}
		
		Ext.apply(config,{
			items : [
				filtroField,
				'->',
				{
					text : 'Reporte de busqueda',
					height : 30,
					cls : 'btn',
					iconCls : 'btn-export-icon',
					handler:function(){
						console.log("dataFind "+dataFind);
						console.log("dataFind.toSource() "+dataFind.toSource());
						if(dataFind != ''){
							//window.open("datoGeneral/excel");
							window.open(
									"datoGeneral/excel?textoMatricula="+dataFind.textoMatricula+
									"&textoNombreCompleto="+dataFind.textoNombreCompleto+
									"&textoCodigoExternoDocumento="+dataFind.textoCodigoExternoDocumento+
									"&textoNumeroDocumento="+dataFind.textoNumeroDocumento+
									//"&"+"'facultad.TextoNombreEspanol'"+"="+dataFind.textoNombreEspanolFacultad+
									"&facultad.TextoNombreEspanol"+"="+dataFind.textoNombreEspanolFacultad+
									//"&"+"'especialidad.TextoNombreEspanol'"+"="+dataFind.textoNombreEspanolEspecialidad+
									"&especialidad.TextoNombreEspanol"+"="+dataFind.textoNombreEspanolEspecialidad+
									"&textoNumeroResolucion="+dataFind.textoNumeroResolucion+
									"&fechaFinal="+dataFind.fechaCreacion+
									"&fechaCreacion="+dataFind.fechaCreacion
							);
						}/*
						else{
							window.open("datoGeneral/excel");
						}*/
					}
				},
				{
					text : 'Agregar',
					height : 30,
					iconCls : 'btn-add-icon',
					cls : 'btn',
					handler:function(){
						var win = new APP.Portal.DatoGeneral.Load.DatoGeneralWindowsMantenimiento({
							title: 'Agregar'
						});
						me.getWin=function(){
							return win;
						}
						win.show();
					}
				}
			]
		});
		APP.Portal.DatoGeneral.Load.DatoGeneralToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.DatoGeneral.Load.DatoGeneralToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.DatoGeneral.Load.DatoGeneralList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
		var itemsPerPage = 25;
		var formTextoMatricula,
			formTextoNombreCompleto,
			formTextoCodigoExternoDocumento,
			formTextoNumeroDocumento,
			formTextoNombreEspanolFacultad,
			formTextoNombreEspanolEspecialidad,
			formTextoNumeroResolucion,
			formTechaCreacion,
			formFechaCreacion;
		var fechaFormat='1000/01/01';
		toolbar=new APP.Portal.DatoGeneral.Load.DatoGeneralToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
		
		me.loadListParam=function(form){
			if(form.fechaCreacion==null||form.fechaCreacion==""){
				form.fechaCreacion=fechaFormat;
			}
			if(form.fechaCreacion==null||form.fechaCreacion==""){
				form.fechaCreacion=fechaFormat;
			}
			formTextoMatricula=form.textoMatricula,
			formTextoNombreCompleto=form.textoNombreCompleto,
			formTextoCodigoExternoDocumento=form.textoCodigoExternoDocumento,
			formTextoNumeroDocumento=form.textoNumeroDocumento,
			formTextoNombreEspanolFacultad=form.textoNombreEspanolFacultad,
			formTextoNombreEspanolEspecialidad=form.textoNombreEspanolEspecialidad,
			formTextoNumeroResolucion=form.textoNumeroResolucion,
			/*
			formTechaCreacion=form.fechaCreacion,
			formFechaCreacion=form.fechaCreacion;
			*/
		 	store.currentPage = 1;
			store.load({
				start: 0, 
				limit: itemsPerPage,
				params: {
					textoMatricula:form.textoMatricula,
					textoNombreCompleto:form.textoNombreCompleto,
					textoCodigoExternoDocumento:form.textoCodigoExternoDocumento,
					textoNumeroDocumento:form.textoNumeroDocumento,
					"facultad.TextoNombreEspanol":form.textoNombreEspanolFacultad,
					"especialidad.TextoNombreEspanol":form.textoNombreEspanolEspecialidad,
					textoNumeroResolucion:form.textoNumeroResolucion
			/*
					fechaFinal:form.fechaCreacion,
					fechaCreacion:form.fechaCreacion
			*/		
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
				url : 'datoGeneral/deleteEstudiante',
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
			var win = new APP.Portal.DatoGeneral.Load.DatoGeneralWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'edit'
				}
			});
			win.show();
			
		}
		function visualizar(record){
			var codigo=record.get('codigo');
			var win = new APP.Portal.DatoGeneral.Load.DatoGeneralWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'view'
				}
			});
			win.show();
			
		}
		if(isEmpty(formFechaCreacion)){
			formFechaCreacion=fechaFormat;
		}
		store=new Ext.data.Store({
		    fields:[
	            'codigo',
	            'codigoTelefonoFijo',
	            'codigoTelefonoCelular',
	            'textoNombreCompleto',
	            'facultad',
	            'especialidad',
	            'textoMatricula',
	            'textoNumeroResolucion',
	            'fechaCreacion',
	            'textoSexo',
	            'flagEnviadoSunedu',
	            'textoNumeroDuplicado',
	            'tipoDocumentoTextoNombre',
	            'textoDocumento',
	            'textoNumeroTelefonoFijo',
	            'textoNumeroTelefonoCelular'
			],
			autoLoad:true,
			pageSize: itemsPerPage, 
		    proxy: {
		        type: 'ajax',
		        url: 'datoGeneral/getEstudianteList',
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
						textoCodigoExternoDocumento:formTextoCodigoExternoDocumento,
						textoNumeroDocumento:formTextoNumeroDocumento,
						"facultad.TextoNombreEspanol":formTextoNombreEspanolFacultad,
						"especialidad.TextoNombreEspanol":formTextoNombreEspanolEspecialidad,
						textoNumeroResolucion:formTextoNumeroResolucion//,
						//fechaFinal:formFechaCreacion,
						//fechaCreacion:formFechaCreacion

	                });
	            }
	        }
		});

		
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [
				{ text: 'Matricula',  dataIndex: 'textoMatricula',flex:1 },
		        { text: 'Nombre',  dataIndex: 'textoNombreCompleto',flex:1 },
		        { text: 'Facultad',  dataIndex: 'facultad',flex:1, hidden: true },
		        { text: 'Especialidad',  dataIndex: 'especialidad',flex:1, hidden: true },
		        //{ text: 'Resolucion',  dataIndex: 'textoNumeroResolucion',flex:1 },
		        { text: 'Fecha de Registro',  dataIndex: 'fechaCreacion',flex:1 },
		        { text: 'Sexo',  dataIndex: 'textoSexo', flex:1, hidden: true},
		        //{ text: 'Enviado Sunedu',  dataIndex: 'flagEnviadoSunedu', flex:1, hidden: true},
		        //{ text: 'Resolucion Duplicado',  dataIndex: 'textoNumeroDuplicado', flex:1, hidden: true },
		        { text: 'Documento de Ide  ntidad',  dataIndex: 'tipoDocumentoTextoNombre', flex:1},
		        { text: 'Numero de Documento',  dataIndex: 'textoDocumento', flex:1},
		        { text: 'Telefono Fijo',  dataIndex: 'textoNumeroTelefonoFijo', flex:1},
		        { text: 'Telefono Celular',  dataIndex: 'textoNumeroTelefonoCelular', flex:1}
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
	    					}
		    				/*,
		    				{
		    					text:'Reporte Historial',
		    					handler: function(){
		    					
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
                	var globalPanel=APP.Portal.DatoGeneral.Load.GlobalPanel;
					globalPanel.getPeriodoPanel().getListPanel().loadListParam(form);
					*/
					
				}
		    }
		    
		});
		
		Ext.apply(config,{
			/*
			style: {
			       borderColor: 'green',
			       borderStyle: 'solid'
			},
			*/
			dockedItems : [toolbar],
			items : [grid],
			layout : 'fit'
		});
		APP.Portal.DatoGeneral.Load.DatoGeneralList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.DatoGeneral.Load.DatoGeneralList,Ext.panel.Panel,{});
	
	APP.Portal.DatoGeneral.Load.TipoDocumentoList=function(config){
		var me=this;
		var toolbar,
			storeTipoDocumento,
			grid;
		
		me.loadStoreTipoDocumento=function(){
			console.log('cargando Store de APP.Portal.DatoGeneral.Load.TipoDocumentoList')
			storeTipoDocumento.load();
		}
		
		function setEstadoNombre(v, record){
		   	var result;
			if(record.data.flagSunedu=="1"){
		   		result= "Si";
		   	}else if(record.data.flagSunedu=="0"){
		   		result="No";
		   	}
			return result;
		}
		
		function eliminar(record){
			var codigo=record.get('codigo');
			console.info({action:'delete',codigo:codigo})
			Ext.Ajax.request({
				url : 'datoGeneral/deleteTipoDocumento',
				method: 'POST',
				params: {
					codigo:codigo
				},
				success: function (result, request){
					storeTipoDocumento.load();
				},
				failure: function (result, request){
					alert('Error in server' + result.responseText);
				}
			});
			
			
		}
		
		function editar(record){
			var codigo=record.get('codigo');
			var win = new APP.Portal.DatoGeneral.Load.TipoDocumentoWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'edit'
				}
			});
			win.show();
			
		}
		
		storeTipoDocumento=new Ext.data.Store({
		    fields:[
	            'codigo',
	            'codigoExterno',
	            'textoNombre',
	            'flagSunedu',
	            {name: 'flagSuneduNombre',  convert: setEstadoNombre}
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
		
		grid=new Ext.grid.Panel({
		    store: storeTipoDocumento,
		    xtype: 'grouped-header-grid',
		    columns: [
				{ text: 'codigo',  dataIndex: 'codigo',flex:1, hidden: true },
				{ text: 'Codigo Documento',  dataIndex: 'codigoExterno',flex:1 },
		        { text: 'Nombre',  dataIndex: 'textoNombre',flex:1 },
		        { text: 'Valido Sunedu',  dataIndex: 'flagSuneduNombre',flex:1 }
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
    		                        Ext.Msg.confirm("Confimacion", "¿Desea eliminar?", function(btnText){
    		                            if(btnText === "yes"){
    		                                Ext.Msg.alert("Alerta", "A confirmado  'Si'.");
    		                                eliminar(record);
    		                            }
    		                            else if(btnText === "no"){
    		                                
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
		APP.Portal.DatoGeneral.Load.TipoDocumentoList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.DatoGeneral.Load.TipoDocumentoList,Ext.panel.Panel,{});
	
	APP.Portal.DatoGeneral.Load.DatoGeneralPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.DatoGeneral.Load.DatoGeneralList({})
		me.getListPanel=function(){
			return listPanel;
		}
		datoGeneralWindowsMantenimiento = new APP.Portal.DatoGeneral.Load.DatoGeneralWindowsMantenimiento({})
		me.getDatoGeneralWindowsMantenimiento =function(){
			return datoGeneralWindowsMantenimiento;
		}
		Ext.apply(config,{
			title : 'DatoGeneral',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.DatoGeneral.Load.DatoGeneralPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.DatoGeneral.Load.DatoGeneralPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.DatoGeneral.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.DatoGeneral.Load.DatoGeneralPanel({});
		
		me.getDatoGeneralPanel=function(){
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
		
		APP.Portal.DatoGeneral.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.DatoGeneral.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.DatoGeneral.Load.Container({});
		APP.Portal.DatoGeneral.Load.GlobalPanel=globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
<script type="text/javascript">
	Ext.ns('APP.Portal.Autoridad.Load');
	APP.Portal.Autoridad.Load.GlobalPanel=null;
</script>

<script type="text/javascript">
	APP.Portal.Autoridad.Load.AutoridadWindowsMantenimiento=function(config){
		
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
	                	url: 'autoridad/saveAutoridad',
	                    success: function(form, action) {
	                    	var globalPanel=APP.Portal.Autoridad.Load.GlobalPanel;
	                    	globalPanel.getAutoridadPanel().getListPanel().loadList();
							Ext.Msg.alert('Success', 'Se ha grabado la autiridad correctamente.');
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
		
		
		estado = Ext.create('Ext.data.Store', {
		    fields: ['codigo', 'textoNombreEspanol'],
		    data : [
		        {"codigo":"A", "textoNombreEspanol":"Activo"},
		        {"codigo":"D", "textoNombreEspanol":"Desactivo"}
	
		    ]
		});
			
		storeGradoAcademico=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'autoridad/getGradoAcademicoList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		
		storeCargo=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombre'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'autoridad/getCargoList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		var codigoField=new Ext.form.field.Text({
	        name: 'codigo'
        });
		var adjuntoField=new Ext.form.field.Text({
	        name: 'adjunto.codigo'
        });
	    var nombreAutoridadFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Nombre de Autoridad',
	        name: 'textoNombreAutoridad',
	        allowBlank: false
        });
	    
        var cargoFieldLabel=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Cargo',
	        name: 'cargo.codigo',
			store: storeCargo,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
	        allowBlank: false,
			editable:false
        });	
 
        var gradoAcademicoFieldLabel=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Grado Academico',
	        name: 'gradoAcademico.codigo',
			store: storeGradoAcademico,
	        queryMode: 'local',
	        displayField: 'textoNombre',
	        valueField: 'codigo',
	        allowBlank: false,
			editable:false
        });	
	    var estadoFieldLabel=new Ext.form.field.ComboBox({
	    	fieldLabel: 'Estado',
	        name: 'flagEstado',
			store: estado,
	        queryMode: 'local',
	        displayField: 'textoNombreEspanol',
	        valueField: 'codigo',
	        allowBlank: false,
			editable:false
        });	    
		
	    var archivoField=new Ext.form.field.File({
	        fieldLabel: 'Firma',
	        name:'archivo',
	        labelWidth: 150,
	        anchor: '100%',
	        msgTarget: 'Ingrese Firma',
	        buttonText: 'Examinar',
	    });
		var URLBaseField=new Ext.form.field.Text({
	        name: 'URLBase'
        });
		var btnView=new Ext.button.Button({
			height : 24,
			iconCls : 'btn-lupa-icon',
			handler : function(){
				var URLBase = URLBaseField.getValue();
		    	if(URLBase !=null&&URLBase!=''){
		    		window.open('http://192.168.1.4/'+URLBase)
		    		//window.open('http://localhost/'+URLBase)
		    	}
			}
		})
		var panelFile=new Ext.form.Panel({
			items:[archivoField]
		})
		
		var panelView=new Ext.form.Panel({
			padding : '0 0 0 10',
			items:[btnView]
		})
		
		var panelArchivo = new Ext.panel.Panel({
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
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
    		fieldDefaults: {
    	        width : 450,
    	        labelWidth : 150
    	    },
			defaultType: 'textfield',
			
			items:[
				codigoField.hide(),
				adjuntoField.hide(),
				URLBaseField.hide(),
		       	nombreAutoridadFieldLabel,
		       	gradoAcademicoFieldLabel,
		     	estadoFieldLabel,
		     	cargoFieldLabel,
		     	panelArchivo
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
							url:'autoridad/getAutoridad',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.Autoridad.Load.AutoridadWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.AutoridadWindowsMantenimiento,Ext.window.Window,{});
	
	
	APP.Portal.Autoridad.Load.AutoridadFiltro=function(config){
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
							textoNombreAutoridad:nombreAutoridadFieldLabel.getValue(),
		        			gradoAcademico:gradoAcademicoFieldLabel.getValue(),
		        			cargo:cargoFieldLabel.getValue(),
		        			estado:estadoFieldLabel.getValue(),
		        			fechaInicio:fechaInicioFieldLabel.getSubmitValue(),
		        			fechaFinal:fechaFinalFieldLabel.getSubmitValue()
		        			
						},
						findValue=[],
						regularExpresion=/,|( )/;
						var globalPanel=APP.Portal.Autoridad.Load.GlobalPanel;
						if(form.fechaInicio == null){
							console.log('Es NULOOOO');
						}
						if(form.fechaInicio == ""){
							console.log('Es NULOOOO  ""');
						}
						globalPanel.getAutoridadPanel().getListPanel().loadListParam(form);
						
						console.log('form.textoNombreAutoridad '+form.textoNombreAutoridad);
						console.log('form.gradoAcademico '+form.gradoAcademico);
						console.log('form.cargo '+form.cargo);
						console.log('form.estado '+form.estado);
						

						function posicionValue(rawValue){
							var result = null;
							if(regularExpresion.test(rawValue) == true) {
								result =' ('+rawValue+')';
							}else{
								result = rawValue;
							}
							return result;
						}
						
						if(form.textoNombreAutoridad !=null &&form.textoNombreAutoridad !=""){
							findValue.push('Nombre de Autoridad:'+posicionValue(form.textoNombreAutoridad));
						}
						if(form.gradoAcademico !=null && form.gradoAcademico  != ""){
							findValue.push('Grado Academico: '+posicionValue(form.gradoAcademico));
						}
						if(form.cargo !=null && form.cargo!=""){
							findValue.push('Cargo: '+posicionValue(form.cargo));
						}
						
						if(form.estado !=null && form.estado !=""){
							findValue.push('Estado: '+form.estado);
						}
						
						me.setRawValue(findValue);
						pickerPanel.getForm().reset();
						me.collapse();
			        }
				});
								  
				estado = Ext.create('Ext.data.Store', {
				    fields: ['codigo', 'textoNombreEspanol'],
				    data : [
				        {"codigo":"A", "textoNombreEspanol":"Activo"},
				        {"codigo":"D", "textoNombreEspanol":"Desactivo"},

				    ]
				});
				storeGradoAcademico=new Ext.data.Store({
					fields:[
						'codigo',
						'textoNombre'
					],
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: 'autoridad/getGradoAcademicoList',
						reader: {
							type: 'json',
							root: 'data',
							idProperty : 'codigo',
							totalProperty :'totalCount'
						}
					}
				});
				
				storeCargo=new Ext.data.Store({
					fields:[
						'codigo',
						'textoNombre'
					],
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: 'autoridad/getCargoList',
						reader: {
							type: 'json',
							root: 'data',
							idProperty : 'codigo',
							totalProperty :'totalCount'
						}
					}
				});
			    var nombreAutoridadFieldLabel=new Ext.form.field.Text({
			        fieldLabel: 'Nombre de Autoridad',
			        name: 'textoNombreAutoridad'
		        });
			    
		        var gradoAcademicoFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Grado Academico',
			        name: 'gradoAcademico',
					store: storeGradoAcademico,
			        queryMode: 'local',
			        displayField: 'textoNombre',
			        valueField: 'textoNombre'
		        });				    
		        
		        var cargoFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Cargo',
			        name: 'cargo',
					store: storeCargo,
			        queryMode: 'local',
			        displayField: 'textoNombre',
			        valueField: 'textoNombre'
		        });	
		 

			    var estadoFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Estado',
			        name: 'flagEstado',
					store: estado,
			        queryMode: 'local',
			        displayField: 'textoNombreEspanol',
			        valueField: 'codigo'
			      
		        });	    
			    var fechaInicioFieldLabel=new Ext.form.field.Date({
			        fieldLabel: 'Fecha Inicio',
			        name: 'fechaInicio',
			        format : "d/m/Y",
			        submitFormat :"Y/m/d",
			        allowBlank: false
				});
			    var fechaFinalFieldLabel=new Ext.form.field.Date({
			        fieldLabel: 'Fecha Final',
			        name: 'fechaFinal',
			        format : "d/m/Y",
			        submitFormat :"Y/m/d",
			        allowBlank: false
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
					       nombreAutoridadFieldLabel,
					       gradoAcademicoFieldLabel,
					       cargoFieldLabel,
					       estadoFieldLabel,
					       fechaInicioFieldLabel,
					       fechaFinalFieldLabel
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
						
						if(form.gradoAcademico != null){
							textoFind=((form.gradoAcademico).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreGradoAcademico(),textoFind,'textoNombre');
							getIndice.valueGradoAcademicoFieldLabel(value);	
							form.gradoAcademico=value;
						}else{
							getIndice.valueGradoAcademicoFieldLabel(form.gradoAcademico)
						}
						
						if(form.cargo != null){
							textoFind=((form.cargo).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreCargo(),textoFind,'textoNombre');
							getIndice.valueCargoFieldLabel(value);	
							form.cargo=value;
						}else{
							getIndice.valueCargoFieldLabel(form.cargo)
						}
						if(form.estado != null){
							textoFind=((form.estado).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreEstado(),textoFind,'textoNombreEspanol');
							getIndice.valueEstadoFieldLabel(textoFind);	
							form.estado=value;
						}else{
							getIndice.valueEstadoFieldLabel(form.estado)
						}
						console.log('FORM FINAL '+form);	
						globalPanel.getAutoridadPanel().getListPanel().loadListParam(form);
						console.log('Presiono Enter');
			       	//}
		       	}
			}

		});
		APP.Portal.Autoridad.Load.AutoridadFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.AutoridadFiltro,APP.form.field.Picker,{});
	
	APP.Portal.Autoridad.Load.AutoridadToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.Autoridad.Load.AutoridadFiltro({
			
		});
		
		me.getFiltroField=function(form){
			return filtroField;
		}
		
		Ext.apply(config,{
			items : [
				filtroField,
				'->',
				{
					text : 'Agregar',
					height : 30,
					iconCls : 'btn-add-icon',
					cls : 'btn',
					handler:function(){
						var win = new APP.Portal.Autoridad.Load.AutoridadWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.Autoridad.Load.AutoridadToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.AutoridadToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.Autoridad.Load.AutoridadList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
			
		toolbar=new APP.Portal.Autoridad.Load.AutoridadToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
		
		me.loadListParam=function(form){
			var fechaFormat='1000-01-01';
			if(form.fechaInicio==null||form.fechaInicio==""){
				form.fechaInicio=fechaFormat;
			}
			if(form.fechaFinal==null||form.fechaFinal==""){
				form.fechaFinal=fechaFormat;
			}
			store.load({
				params: {
					textoNombreAutoridad: form.textoNombreAutoridad,
					"gradoAcademico.textoNombre": form.gradoAcademico,
					"cargo.textoNombre": form.cargo,
					flagEstado: form.estado,
					fechaInicio:form.fechaInicio,
					fechaFinal:form.fechaFinal
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
				url : 'autoridad/deleteAutoridad',
// 				method: 'POST',
				params: {
					codigo:codigo
				},
// 				headers: {
// 			        'Content-Type': 'application/json'
// 			    },
// 				success: function (re sult, request){
// 					store.load();
// 				},
// 				failure: function (result, request){
// 					alert('Error in server' + result.responseText);
// 				}
//  				success: function (responsem) {
 				success: function (response) {
//                     alert('response : ' + responsem.responseText);
//                     console.info({responsem:responsem})

                	var respObj = Ext.JSON.decode(response.responseText);
 					console.log(respObj.success)
 					console.info({respObj:respObj});
 					if( respObj.success == false){
 	 					Ext.Msg.alert("Success", respObj.message);
 					}else{
 						store.load();
 					}
 					
 					
                },
                failure: function (response) {
//                 	alert('error: ' + action);
                
//                     console.log('server-side failure with status code ' + action);
                	 var respObj = Ext.JSON.decode(response.responseText);
                     Ext.Msg.alert("Error", respObj.status.statusMessage);
                }
			});
			
			
		}
		
		function editar(record){
			var codigo=record.get('codigo');
			var win = new APP.Portal.Autoridad.Load.AutoridadWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'edit'
				}
			});
			win.show();
			
		}
		function periodo(record){
			var codigo=record.get('codigo');
			var win = new APP.Portal.Autoridad.Load.PeriodoWindowsMantenimiento({
				uxData:{
					title: 'Agregar',
					codigo: codigo,
					action: 'add'
				}
			});
			win.show();
			
		}
		store=new Ext.data.Store({
		    fields:[
	            'codigo',
	            'textoNombreAutoridad',
	            'cargo',
	            'gradoAcademico',
	            'fechaAgregar',
	            'flagEstado',
	            {name: 'flagEstadoNombre',  convert: estado}
	         
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
		    }
		});
	
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [
		        { text: 'Cargo',  dataIndex: 'cargo',flex:1 },
		        { text: 'Nombre',  dataIndex: 'textoNombreAutoridad',flex:1  },
		        { text: 'Grado Academico',  dataIndex: 'gradoAcademico',flex:1  },
		        { text: 'Fecha de Registro',  dataIndex: 'fechaAgregar',flex:1 },
		        { text: 'Estado',  dataIndex: 'flagEstadoNombre',flex:1 }
		    ],
		    border:true,
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
    		                                eliminar(record);
    		                            }
    		                            else if(btnText === "no"){
    		                                
    		                            }
    		                        }, this);
		    						
	    						}
	    					},
		    				{
		    					text:'Periodo',
		    					handler: function(){
		    						periodo(record);
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
					var form={autoridad:record.get('codigo')};
                	var globalPanel=APP.Portal.Autoridad.Load.GlobalPanel;
					globalPanel.getPeriodoPanel().getListPanel().loadListParam(form);
					
				}
		    }
		    
		});
		
		Ext.apply(config,{
			dockedItems : [toolbar],
			items : [grid],
			layout : 'fit'
		});
		APP.Portal.Autoridad.Load.AutoridadList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.AutoridadList,Ext.panel.Panel,{});
	
	APP.Portal.Autoridad.Load.AutoridadPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Autoridad.Load.AutoridadList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Autoridad',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Autoridad.Load.AutoridadPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.AutoridadPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">

	APP.Portal.Autoridad.Load.PeriodoWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		
		var agregarPanel, 
			saveButton, 
			cancelarButton,
			estado,
			storeTipoEspecialidad;
		
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
	                	url: 'autoridad/savePeriodo',
	                    success: function(form, action) {
// 	                    	var jsonResponse = action.result;
// 	                        if (jsonResponse.success == true) {
// 	                       		var globalPanel=APP.Portal.GradoTitulo.Load.GlobalPanel;
// 		                    	globalPanel.getGradoTituloPanel().getListPanel().loadList();
// 		                    	Ext.Msg.alert('Éxito', jsonResponse.message);
// 								me.close();
// 	                       }
	                    	var globalPanel=APP.Portal.Autoridad.Load.GlobalPanel;
	                    	globalPanel.getPeriodoPanel().getListPanel().loadList();
							Ext.Msg.alert('Exito', 'Se ha grabado el Periodo correctamente.');
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
// 	                    failure: function(form, action) {
// 	                        Ext.Msg.alert('Failed', 'error');
// 	                    }
	                });
	            }
	        }
		});
		
		storeAutoridadRegistro=new Ext.data.Store({
		    fields:[
	            'codigo',
	            'textoNombreAutoridad',
	            'cargo'
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
		    }
		});
		var fechaInicioFieldLabel=new Ext.form.field.Date({
	        fieldLabel: 'Fecha Inicio',
	        name: 'fechaInicio',
	        format : "Y-m-d",
	        allowBlank: false
		});
	    var fechaFinalFieldLabel=new Ext.form.field.Date({
	        fieldLabel: 'Fecha Final',
	        name: 'fechaFinal',
	        format : "Y-m-d",
	        allowBlank: false
		});
	    var codigoAutoridadField = new Ext.form.field.Text({
	        name: "autoridadRegistro.codigo"
        });
	    var codigoPeriodoField = new Ext.form.field.Text({
	        name: "codigo"
        });
	    
	    var codigoAutoridadPeriodoField = new Ext.form.field.Text({
	        fieldLabel: 'Codigo Periodo',
	        name: 'textoCodigoPeriodo'
	        //allowBlank: false
        });
	    
		var cargoFieldLabel=new Ext.form.field.ComboBox({
// 			tpl: Ext.create('Ext.XTemplate',
// 			        '<tpl for=".">',
// 			            '<div class="x-boundlist-item">{cargo} - {textoNombreAutoridad}</div>',
// 			        '</tpl>'
// 			),
	    	fieldLabel: 'Autoridad',
	        name: 'autoridadRegistro.codigo',
			store: storeAutoridadRegistro,
	        queryMode: 'local',
	        displayField: 'textoNombreAutoridad',
	        valueField: 'codigo',
// 			displayTpl: Ext.create('Ext.XTemplate',
// 					'<tpl for=".">',
// 					'{cargo} - {textoNombreAutoridad}',
// 					'</tpl>'
// 			),
	        allowBlank: false,
			forceSelection: true
        });	
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
// 			height: 150,
			fieldDefaults: {
		        width : 500,
		        labelWidth : 100
		    },
			defaultType: 'textfield',
			items:[
				codigoAutoridadField.hide(),
				codigoPeriodoField.hide(),
// 				cargoFieldLabel,
				codigoAutoridadPeriodoField,
		       	fechaInicioFieldLabel,
		       	fechaFinalFieldLabel
				
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
							url:'autoridad/getPeriodo',
							params:{
								codigo:uxData.codigo
							}
						})
					}else if(uxData&&uxData.action=='add'){
						codigoAutoridadField.setValue( uxData.codigo )
					}
				
				}
			}
		});
		
		APP.Portal.Autoridad.Load.PeriodoWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.PeriodoWindowsMantenimiento,Ext.window.Window,{});

	
	APP.Portal.Autoridad.Load.PeriodoToolbar=function(config){
		var me=this;
		
		Ext.apply(config,{
			items : [
				'->'
// 				,{
// 					text : 'Agregar',
// 					height : 30,
// 					iconCls : 'btn-add-icon',
// 					cls : 'btn',
// 					handler:function(){
// 						var win = new APP.Portal.Autoridad.Load.PeriodoWindowsMantenimiento({
// 							title: 'Agregar'
// 						});
// 						win.show();
// 					}
// 				}
			]
		});
		APP.Portal.Autoridad.Load.PeriodoToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.PeriodoToolbar,Ext.toolbar.Toolbar,{});

	APP.Portal.Autoridad.Load.PeriodoList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
	
		
		toolbar=new APP.Portal.Autoridad.Load.PeriodoToolbar({
			dock:'top'
		});
	
		
		me.getToolbar=function(form){
			return toolbar;
		}
		
		me.loadListParam=function(form){
			
				store.load({
					params: {
						"autoridadRegistro.codigo":form.autoridad
					}
				});
		}
		
		me.loadList=function(){
				store.load();
		}
		
		function eliminar(record){
			var codigo=record.get('codigo');
			console.info({action:'delete',codigo:codigo})
			Ext.Ajax.request({
				url : 'autoridad/deletePeriodo',
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

			var win = new APP.Portal.Autoridad.Load.PeriodoWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'edit'
				}
			});
			win.show();
			
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
	
		store=new Ext.data.Store({
		    fields:[
				'codigo',
				'fechaInicio',
				'fechaFinal',
				'textoCodigoPeriodo'
	         
			],
			autoLoad:true,
		    proxy: {
		        type: 'ajax',
		        url: 'autoridad/getPeriodoList',
		        reader: {
		            type: 'json',
		            root: 'data',
		            idProperty : 'codigo',
		            totalProperty :'totalCount'
		        }
		    }
		});
	
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [
		        { text: 'Fecha Inicio',  dataIndex: 'fechaInicio',flex:1 },
		        { text: 'Fecha Final',  dataIndex: 'fechaFinal',flex:1  },
		        { text: 'Codigo Periodo',  dataIndex: 'textoCodigoPeriodo',flex:1  }
		    ],
		    border:true,
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
		    						eliminar(record);
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
		APP.Portal.Autoridad.Load.PeriodoList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.PeriodoList,Ext.panel.Panel,{});

	APP.Portal.Autoridad.Load.PeriodoPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Autoridad.Load.PeriodoList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Periodo',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Autoridad.Load.PeriodoPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.PeriodoPanel,Ext.panel.Panel,{});
</script>


<script type="text/javascript">
	APP.Portal.Autoridad.Load.Container=function(config){
		var me=this;
		var autoridadesPanel,
			especialidadesPanel;
		
		autoridadesPanel=new APP.Portal.Autoridad.Load.AutoridadPanel({});
		especialidadesPanel=new APP.Portal.Autoridad.Load.PeriodoPanel({});
		
		me.getAutoridadPanel=function(){
			return autoridadesPanel;
		}
		me.getPeriodoPanel=function(){
			return especialidadesPanel;
		}
		var panelFullContainer=new Ext.panel.Panel({
			layout: {
		        type: 'vbox',
		        align: 'center'
		    },
			items : [
				{
					width : '100%', flex: 1, items : [autoridadesPanel],layout : 'fit'
				},
				{
					width : '100%', flex: 1, items : [especialidadesPanel],layout : 'fit'
				}
			]
		})
		Ext.apply(config,{
			layout: 'fit',
			items : [panelFullContainer]
		});
		APP.Portal.Autoridad.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Autoridad.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam = {
			codigoTab : '<%=request.getParameter("codigoTab")%>',
	        containerID : '<%=request.getParameter("containerID")%>'
		};
		var globalPanel =new APP.Portal.Autoridad.Load.Container({});
		APP.Portal.Autoridad.Load.GlobalPanel=globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
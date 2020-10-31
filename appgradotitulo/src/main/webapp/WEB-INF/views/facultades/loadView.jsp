<script type="text/javascript">
	Ext.ns('APP.Portal.Facultades.Load');
	APP.Portal.Facultades.Load.GlobalPanel=null;
</script>

<script type="text/javascript">
	APP.Portal.Facultades.Load.FacultadesWindowsMantenimiento=function(config){
		
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
	                	url: 'facultades/save',
	                    success: function(form, action) {
	                    	var globalPanel=APP.Portal.Facultades.Load.GlobalPanel;
	                    	globalPanel.getFacultadesPanel().getListPanel().loadList();
							Ext.Msg.alert('Success', 'Se ha grabado la facultad correctamente.');
							me.close();
	                    
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', 'error');
	                    }
	                });
	            }
	        }
		});
		
		estado = Ext.create('Ext.data.Store', {
		    fields: ['flagEstado', 'nombre'],
		    data : [
		        {"flagEstado":"A", "nombre":"Activo"},
		        {"flagEstado":"D", "nombre":"Desactivo"},

		    ]
		});
		
		var nombreFacultadFieldLabel=new Ext.form.field.Text({
			fieldLabel: 'Nombre de facultad',
	        name: 'textoNombreEspanol',
	        allowBlank: false
		});
		var codigoFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Codigo de Facultad',
	        name: 'codigoExterno',
	        allowBlank: false
		});
		
		var facultadFieldLabel=new Ext.form.field.Text({
	        fieldLabel: 'Nombre de Facultad en Ingles',
	        name: 'textoNombreIngles',
	        allowBlank: false,
			editable:false
		});
		var estadoFieldLabel=new Ext.form.field.ComboBox({
			xtype:'combobox',
			fieldLabel: 'Estado',
	        name: 'flagEstado',
			store: estado,
	        queryMode: 'local',
	        displayField: 'nombre',
	        valueField: 'flagEstado',
			editable:false,
			allowBlank: false
	        
		});
		var nombreAbreviadoFieldLabel=new Ext.form.field.Text({	
			fieldLabel: 'Abreviatura',
	        name: 'textoNombreAbreviado',
	        allowBlank: false
		});
		
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
    		fieldDefaults: {
    	        width : 400,
    	        labelWidth : 200
    	    },
			defaultType: 'textfield',
			
			items:[
				{	
					name: 'codigo',
					xtype:'hidden'
				},
	        	nombreFacultadFieldLabel,
				codigoFieldLabel,
				facultadFieldLabel,
				estadoFieldLabel,
				nombreAbreviadoFieldLabel
			]
			
		});	
		
		Ext.apply(config,{
			closable:false,
			modal: true,
			items:[agregarPanel],
			buttons:[cancelarButton,saveButton],
			listeners :{
				afterrender:function(){
					/*
					agregarPanel.loadRecord({
						getData :function(){
							return {
								codigo:'1',
								textoNombreEspanol : 'Prueba',
								textoNombreAbreviado : 'Prueba',
								flagEstado : 'Prueba',
								textoNombreIngles : 'Prueba',
								codigoExterno : 'Prueba'
							};
						} 
					})
					*/
					
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
		
		APP.Portal.Facultades.Load.FacultadesWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.FacultadesWindowsMantenimiento,Ext.window.Window,{});
	
	
	APP.Portal.Facultades.Load.FacultadesFiltro=function(config){
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
							textoNombreEspanol: facultadFieldLabel.getValue(),
							codigoExterno: codigoFieldLabel.getValue(),
							flagEstado: estadoFieldLabel.getValue()
						},
						findValue=[],
						regularExpresion=/,|( )/;
						var globalPanel=APP.Portal.Facultades.Load.GlobalPanel;
						globalPanel.getFacultadesPanel().getListPanel().loadListParam(form);
						
						console.log('form.codigo '+form.textoNombreEspanol);
						console.log('form.codigoExterno '+form.codigoExterno);
						console.log('form.flagEstado '+form.flagEstado);
						if(form.textoNombreEspanol !=null){
							if(regularExpresion.test(form.textoNombreEspanol) == true) {
								findValue.push('Facultad: ('+form.textoNombreEspanol+')');
							}else{
								findValue.push('Facultad: '+form.textoNombreEspanol);
							}
						}
						if(form.codigoExterno  !=null &&form.codigoExterno.length !=0){
							if(regularExpresion.test(form.codigoExterno) == true) {
								findValue.push('Codigo: ('+form.codigoExterno+')');
							}else{
								findValue.push('Codigo: '+form.codigoExterno);
							}
						}
						if(form.flagEstado !=null){
							findValue.push('Estado: '+form.flagEstado);
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
				
				estado = Ext.create('Ext.data.Store', {
				    fields: ['codigo', 'textoNombreEspanol'],
				    data : [
				        {"codigo":"A", "textoNombreEspanol":"Activo"},
				        {"codigo":"D", "textoNombreEspanol":"Desactivo"},

				    ]
				});
			    var facultadFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Facultad',
			        name: 'textoNombreEspanol',
					store: storeFacultad,
			        queryMode: 'local',
			        displayField: 'textoNombreEspanol',
			        valueField: 'textoNombreEspanol'
		        });
			    
			    var codigoFieldLabel =new Ext.form.field.Text({
			        fieldLabel: 'Codigo',
			        name: 'codigoExterno',
			        allowBlank: true
		        });
			    
			    var estadoFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Estado',
			        name: 'flagEstado',
					store: estado,
			        queryMode: 'local',
			        displayField: 'textoNombreEspanol',
			        valueField: 'codigo'
		        });
				
				me.valueFacultadFieldLabel=function(value){
					facultadFieldLabel.setValue(value);
				}
				
				me.valueCodigoField=function(value){
					codigoFieldLabel.setValue(value);
				}
				
				me.valueEstadoFieldLabel=function(value){
					estadoFieldLabel.setValue(value);
				}
				me.getStoreFacultad=function(){
					return storeFacultad
				}
		
				me.getStoreEstado=function(){
					return estado
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
					       facultadFieldLabel,
					       codigoFieldLabel,
					       estadoFieldLabel
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
			        	var nameField= ['Facultad','Codigo','Estado'],
			        		form={textoNombreEspanol:'Facultad',codigoExterno:'Codigo',flagEstado:'Estado'},
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
						

						function getCatchValue(store,textoFind){
								var lengthStore=0;
								var result=null;
								var catchValue=[];
								console.log('textoFind '+textoFind);
								
								Ext.each(store.getRange(), function(item, idx) {
									var valueField=((item.get('textoNombreEspanol')).toUpperCase()).trim();
									
									lengthStore=lengthStore+1;
									console.log('lengthStore '+lengthStore);
									if((valueField).startsWith(textoFind)==true){
										catchValue.push(item.get('textoNombreEspanol'));
										console.log('catchValue.length '+catchValue.length);
									}
									if(lengthStore==store.getCount()&&catchValue.length==1){
										console.log('fin de ciclo valor encontrado '+catchValue[0]);
										result =catchValue[0];		
									}
								
								});
								return result;
						}
						
						
						var globalPanel=APP.Portal.Facultades.Load.GlobalPanel,
							getStore,
							textoFind,
							value;
						
						if(form.textoNombreEspanol != null){
							textoFind=((form.textoNombreEspanol).toUpperCase()).trim();
							getStore = globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().getStoreFacultad(); 
							value= getCatchValue(getStore,textoFind);
							globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().valueFacultadFieldLabel(value);	
							form.textoNombreEspanol=value;
						}else{
							globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().valueFacultadFieldLabel(form.textoNombreEspanol)
						}
						//globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().valueFacultadFieldLabel(form.textoNombreEspanol);
						globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().valueCodigoField(form.codigoExterno);
						//globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().valueEstadoFieldLabel(form.flagEstado);
						
						if(form.flagEstado != null){
							textoFind=((form.flagEstado).toUpperCase()).trim();
							getStore = globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().getStoreEstado(); 
							value= getCatchValue(getStore,textoFind);
							globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().valueEstadoFieldLabel(textoFind);	
							form.flagEstado=value;
						}else{
							globalPanel.getFacultadesPanel().getListPanel().getToolbar().getFiltroField().valueEstadoFieldLabel(form.flagEstado)
						}
						globalPanel.getFacultadesPanel().getListPanel().loadListParam(form);
						console.log('Presiono Enter');
			       	//}
		       	}
			}

		});
		APP.Portal.Facultades.Load.FacultadesFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.FacultadesFiltro,APP.form.field.Picker,{});
	
	APP.Portal.Facultades.Load.FacultadesToolbar=function(config){
		var me=this;
		var filtroField=new APP.Portal.Facultades.Load.FacultadesFiltro({
			
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
						var win = new APP.Portal.Facultades.Load.FacultadesWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.Facultades.Load.FacultadesToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.FacultadesToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.Facultades.Load.FacultadesList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
			
		toolbar=new APP.Portal.Facultades.Load.FacultadesToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
		
		me.loadListParam=function(form){
				store.load({
					params: {
						textoNombreEspanol: form.textoNombreEspanol,
						codigoExterno: form.codigoExterno,
						flagEstado: form.flagEstado
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
				url : 'facultades/delete',
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
			var textoNombreEspanol=record.get('textoNombreEspanol');
			var textoNombreIngles=record.get('textoNombreIngles');
			var textonNombreAbreviado=record.get('textonNombreAbreviado');
			var flagEstado=record.get('flagEstado');
			var win = new APP.Portal.Facultades.Load.FacultadesWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					textoNombreEspanol:textoNombreEspanol,
					textoNombreIngles:textoNombreIngles,
					textonNombreAbreviado:textonNombreAbreviado,
					flagEstado:flagEstado,
					action:'edit'
				}
			});
			win.show();
			
		}
		store=new Ext.data.Store({
		    fields:[
	            'codigo',
	            'textoNombreEspanol',
	            'textoNombreIngles',
	            'textonNombreAbreviado',
	            'codigoExterno',
	            'flagEstado',
	            {name: 'flagEstadoNombre',  convert: estado}
	         
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
	
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [
		        { text: 'Código',  dataIndex: 'codigoExterno' },
		        { 
		        	text : 'Nombre',
		        	width : 300,
		        	columns : [
						{ text: 'Español', dataIndex: 'textoNombreEspanol', width : 150 },
						{ text: 'Inglés', dataIndex: 'textoNombreIngles', width : 150 }        
					]
		       	},
		        { text: 'Nombre corto', dataIndex: 'textonNombreAbreviado',flex : 1 },
		        { text: 'Estado', dataIndex: 'flagEstadoNombre',flex : 1 }
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
		    		 
		    	},
		    	select:function(dv, record, index, eOpts ){
		    		
					console.log('selecccionado la fila '+record.get('codigo'));
					var form={facultad:record.get('codigo')};
                	var globalPanel=APP.Portal.Facultades.Load.GlobalPanel;
					globalPanel.getEspecialidadesPanel().getListPanel().loadLisEspecialidadestParam(form);
					
				}
		    }
		    
		});
		
		Ext.apply(config,{
			dockedItems : [toolbar],
			items : [grid],
			layout : 'fit'
		});
		APP.Portal.Facultades.Load.FacultadesList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.FacultadesList,Ext.panel.Panel,{});
	
	APP.Portal.Facultades.Load.FacultadesPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Facultades.Load.FacultadesList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Facultades',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Facultades.Load.FacultadesPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.FacultadesPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">

	APP.Portal.Facultades.Load.EspecialidadesWindowsMantenimiento=function(config){
		
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
	                	url: 'facultades/saveEspecialidad',
	                    success: function(form, action) {
	                    	var globalPanel=APP.Portal.Facultades.Load.GlobalPanel;
	                    	globalPanel.getEspecialidadesPanel().getListPanel().loadLisEspecialidadest();
							Ext.Msg.alert('Success', 'Se ha grabado la Especialidad correctamente.');
							me.close();
	                    
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', 'error');
	                    }
	                });
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
		
		storeTipoEspecialidad=new Ext.data.Store({
			fields:[
				'codigo',
				'textoNombreEspanol'
			],
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: 'facultades/getTipoEspecialidadList',
				reader: {
					type: 'json',
					root: 'data',
					idProperty : 'codigo',
					totalProperty :'totalCount'
				}
			}
		});
		
		estado = Ext.create('Ext.data.Store', {
		    fields: ['flagEstado', 'nombre'],
		    data : [
		        {"flagEstado":"A", "nombre":"Activo"},
		        {"flagEstado":"D", "nombre":"Desactivo"},
	
		    ]
		});
		

		var facultadFieldLabel=new Ext.form.field.ComboBox({
			fieldLabel: 'Facultad',
			name: 'facultad.codigo',
			store: storeFacultad,
			queryMode: 'local',
			displayField: 'textoNombreEspanol',
			valueField: 'codigo',
			editable:false,
			allowBlank: false
		
		});
		
		var tipoEspecialidadFieldLabel=new Ext.form.field.ComboBox({
			fieldLabel: 'Tipo de Especialidad',
			name: 'tipoEspecialidad.codigo',
			store: storeTipoEspecialidad,
			queryMode: 'local',
			displayField: 'textoNombreEspanol',
			valueField: 'codigo',
			editable:false,
			allowBlank: false
		
		});
		
		var codigoFieldLabel =new Ext.form.field.Text({
			fieldLabel: 'Codigo Especialidad',
			name: 'codigoExterno',
			allowBlank: false
		});
		
		var especialidadFieldLabel =new Ext.form.field.Text({
			fieldLabel: 'Nombre de Especialidad',
			name: 'textoNombreEspanol',
			allowBlank: false
		});
		
		var especialidadInglesFieldLabel =new Ext.form.field.Text({
			fieldLabel: 'Nombre de Especialidad en Ingles',
			name: 'textoNombreIngles',
			allowBlank: false
		});
		
		var estadoFieldLabel=new Ext.form.field.ComboBox({
			fieldLabel: 'Estado',
			name: 'flagEstado',
			store: estado,
			queryMode: 'local',
			displayField: 'nombre',
			valueField: 'flagEstado',
			editable:false,
			allowBlank: false
		});
		
		agregarPanel= new Ext.form.Panel({
			bodyPadding: 5,
			fieldDefaults: {
		        width : 400,
		        labelWidth : 200
		    },
			defaultType: 'textfield',
			items:[
				{	
					name: 'codigo',
					xtype:'hidden'
				},
				facultadFieldLabel,
				codigoFieldLabel,
				especialidadFieldLabel,
				especialidadInglesFieldLabel,
				tipoEspecialidadFieldLabel,
				estadoFieldLabel
				
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
							url:'facultades/getEspecialidad',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.Facultades.Load.EspecialidadesWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.EspecialidadesWindowsMantenimiento,Ext.window.Window,{});

	
	APP.Portal.Facultades.Load.EspecialidadesFiltro=function(config){
		var me=this;
		
		
	    	    
		Ext.apply(config,{
			width : 450,
			createPicker:function(){
				var me = this,
				storeFacultad,storeTipoEspecialidad,estado,cancelarButton,findButton;
				
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
							textoNombreEspanolFacultad: facultadFieldLabel.getValue(),
							textoNombreEspanol: especialidadFieldLabel.getValue(),
							codigoExterno: codigoFieldLabel.getValue(),
							flagEstado: estadoFieldLabel.getValue(),
							tipoEspecialidad: tipoEspecialidadFieldLabel.getValue()
						},
						findValue=[],
						regularExpresion=/,|( )/;
						var globalPanel=APP.Portal.Facultades.Load.GlobalPanel;
						globalPanel.getEspecialidadesPanel().getListPanel().loadLisEspecialidadestParam(form);
						
						console.log('form.textoNombreEspanolFacultad '+form.textoNombreEspanolFacultad);
						console.log('form.textoNombreEspanol '+form.textoNombreEspanol);
						console.log('form.codigoExterno '+form.codigoExterno);
						console.log('form.flagEstado '+form.flagEstado);
						console.log('form.tipoEspecialidad '+form.tipoEspecialidad);

						function posicionValue(rawValue){
							var result = null;
							if(regularExpresion.test(rawValue) == true) {
								result =' ('+rawValue+')';
							}else{
								result = rawValue;
							}
							return result;
						}
						
						if(form.textoNombreEspanolFacultad !=null &&form.textoNombreEspanolFacultad !=""){
							findValue.push('Facultad:'+posicionValue(form.textoNombreEspanolFacultad));
						}
						if(form.textoNombreEspanol !=null && form.textoNombreEspanol  != ""){
							findValue.push('Especialidad: '+posicionValue(form.textoNombreEspanol));
						}
						if(form.codigoExterno !=null && form.codigoExterno!=""){
							findValue.push('Codigo: '+posicionValue(form.codigoExterno));
						}
						if(form.tipoEspecialidad !=null && form.tipoEspecialidad !=""){
							findValue.push('Tipo de Especialidad: '+posicionValue(form.tipoEspecialidad));
						}
						if(form.flagEstado !=null && form.flagEstado !=""){
							findValue.push('Estado: '+form.flagEstado);
						}

						me.setRawValue(findValue);
						pickerPanel.getForm().reset();
						me.collapse();
			        }
				});
				
				storeTipoEspecialidad=new Ext.data.Store({
					fields:[
						'codigo',
						'textoNombreEspanol'
					],
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: 'facultades/getTipoEspecialidadList',
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
				estado = Ext.create('Ext.data.Store', {
				    fields: ['codigo', 'textoNombreEspanol'],
				    data : [
				        {"codigo":"A", "textoNombreEspanol":"Activo"},
				        {"codigo":"D", "textoNombreEspanol":"Desactivo"},

				    ]
				});
			    var facultadFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Facultad',
			        name: 'facultad.textoNombreEspanol',
					store: storeFacultad,
			        queryMode: 'local',
			        displayField: 'textoNombreEspanol',
			        valueField: 'textoNombreEspanol',
				    listConfig : {
						listeners : {
							itemclick : function(list,record) {
								storeEspecialidad.load({
									params: {
										"facultad.codigo":record.get('codigo')
									}
								});
								
							}
						}
										
					}
		        });
			    
			    var especialidadFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Especialidad',
			        name: 'textoNombreEspanol',
					store: storeEspecialidad,
			        queryMode: 'local',
			        displayField: 'textoNombreEspanol',
			        valueField: 'textoNombreEspanol'
		        });
			    
			    var codigoFieldLabel =new Ext.form.field.Text({
			        fieldLabel: 'Codigo',
			        name: 'codigoExterno',
			        allowBlank: true
		        });
			    
			    var estadoFieldLabel=new Ext.form.field.ComboBox({
			    	fieldLabel: 'Estado',
			        name: 'flagEstado',
					store: estado,
			        queryMode: 'local',
			        displayField: 'textoNombreEspanol',
			        valueField: 'codigo'
		        });
				
				var tipoEspecialidadFieldLabel=new Ext.form.field.ComboBox({
					fieldLabel: 'Tipo de Especialidad',
					name: 'tipoEspecialidad.textoNombreEspanol',
					store: storeTipoEspecialidad,
					queryMode: 'local',
					displayField: 'textoNombreEspanol',
					valueField: 'textoNombreEspanol'
				
				});
				
				me.valueFacultadFieldLabel=function(value){
					facultadFieldLabel.setValue(value);
				}
				me.valueEspecialidadFieldLabel=function(value){
					especialidadFieldLabel.setValue(value);
				}
				me.valueTipoEspecialidadFieldLabel=function(value){
					tipoEspecialidadFieldLabel.setValue(value);
				}
				me.valueCodigoField=function(value){
					codigoFieldLabel.setValue(value);
				}
				
				me.valueEstadoFieldLabel=function(value){
					estadoFieldLabel.setValue(value);
				}
				me.getStoreFacultad=function(){
					return storeFacultad
				}
				me.getStoreTipoEspecialidad=function(){
					return storeTipoEspecialidad
				}
				me.getStoreEspecialidad=function(){
					return storeEspecialidad
				}
		
				me.getStoreEstado=function(){
					return estado
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
					       facultadFieldLabel,
					       especialidadFieldLabel,
					       estadoFieldLabel,
					       tipoEspecialidadFieldLabel,
					       codigoFieldLabel
					],
					buttons:[cancelarButton,findButton]
				});

		        return pickerPanel;
			},
			enableKeyEvents:true,
			onKeyUp: function(e, t) {
				var key = e.getKey();
		        if (!me.readOnly && !me.disabled && me.editable) {
			            var regularExpresion= /,(?=[^\)]*[\(]|[^\)]*$)/;
			        	var nameField= ['Facultad','Especialidad','Codigo','Tipo de Especialidad','Estado'],
			        		form={
			        				textoNombreEspanolFacultad:'Facultad',
			        				textoNombreEspanol:'Especialidad',
			        				codigoExterno:'Codigo',
			        				tipoEspecialidad:'Tipo de Especialidad',
			        				flagEstado:'Estado'
			        		},
			            	rawField=me.getRawValue().split(regularExpresion),
							splitRawField =[];		
							console.log('rawField '+rawField)
							console.log('rawField.length '+rawField.length)
						for(i=0;i<rawField.length;i++){
							var convert =rawField[i].split(":");
							console.log('convert '+convert)
							splitRawField.push(convert);
						}
						/*
						Object.keys(form).forEach(function(key) {
							for(x=0;x<splitRawField.length;x++){
								var formColumna=(form[key].trim()).toUpperCase();
								var splitRawFieldZero=(splitRawField[x][0].trim()).toUpperCase();
								var splitRawFieldOne=(splitRawField[x][0].trim());
								
								if((splitRawFieldZero).indexOf(formColumna) != -1){
									if(splitRawField[x][1] !=null){
										if((splitRawFieldOne).indexOf("(")==0){
						            		var sinParentesis =(splitRawFieldOne).substring(1,(splitRawFieldOne).length-1);
						            		form[key]=sinParentesis.trim();
						            		console.log('form[key] ='+'form['+key+'] '+' sinParentesis= '+sinParentesis )
										}else if((splitRawFieldOne).length !=0){
											form[key]=splitRawFieldOne;
											console.log('form[key] ='+'form['+key+'] '+' splitRawFieldOne= '+splitRawFieldOne )
										}
									}
								}
							}			
	        			});
						*/				
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
	        				console.log('Nueva variable de form '+key+' '+form[key]); 
						});
						Object.keys(form).forEach(function(key) {
							for(i=0;i<nameField.length;i++){
								if((nameField[i].trim())== form[key]){
									form[key]=null;
								}
							}	
						});
						

						function getCatchValue(store,textoFind){
								var lengthStore=0;
								var result=null;
								var catchValue=[];
								console.log('textoFind '+textoFind);
								Ext.each(store.getRange(), function(item, idx) {
									var valueField=((item.get('textoNombreEspanol')).toUpperCase()).trim();
									
									lengthStore=lengthStore+1;
									//console.log('lengthStore '+lengthStore);
									if((valueField).startsWith(textoFind)==true){
										catchValue.push(item.get('textoNombreEspanol'));
										console.log('catchValue.length '+catchValue.length);
									}
									if(lengthStore==store.getCount()&&catchValue.length==1){
										console.log('fin de ciclo valor encontrado '+catchValue[0]);
										result =catchValue[0];		
									}
								
								});
								return result;
						}
						
						
						var globalPanel=APP.Portal.Facultades.Load.GlobalPanel,
							textoFind,
							value,
							getIndice =globalPanel.getEspecialidadesPanel().getListPanel().getToolbar().getFiltroField();
						
						if(form.textoNombreEspanolFacultad != null){
							textoFind=((form.textoNombreEspanolFacultad).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreFacultad(),textoFind);
							getIndice.valueFacultadFieldLabel(value);	
							form.textoNombreEspanolFacultad=value;
						}else{
							getIndice.valueFacultadFieldLabel(form.textoNombreEspanolFacultad)
						}
						
						if(form.textoNombreEspanol != null){
							textoFind=((form.textoNombreEspanol).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreEspecialidad(),textoFind);
							getIndice.valueEspecialidadFieldLabel(value);	
							form.textoNombreEspanol=value;
						}else{
							getIndice.valueEspecialidadFieldLabel(form.textoNombreEspanol)
						}
						if(form.tipoEspecialidad != null){
							textoFind=((form.tipoEspecialidad).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreTipoEspecialidad(),textoFind);
							getIndice.valueTipoEspecialidadFieldLabel(value);	
							form.tipoEspecialidad=value;
						}else{
							getIndice.valueTipoEspecialidadFieldLabel(form.tipoEspecialidad)
						}
						if(form.flagEstado != null){
							textoFind=((form.flagEstado).toUpperCase()).trim();
							value= getCatchValue(getIndice.getStoreEstado(),textoFind);
							getIndice.valueEstadoFieldLabel(textoFind);	
							form.flagEstado=value;
						}else{
							getIndice.valueEstadoFieldLabel(form.flagEstado)
						}
						getIndice.valueCodigoField(form.codigoExterno);	
						console.log('FORM FINAL '+form);	
						globalPanel.getEspecialidadesPanel().getListPanel().loadLisEspecialidadestParam(form);
						console.log('Presiono Enter');			       
		       	}
			}

		});
		APP.Portal.Facultades.Load.EspecialidadesFiltro.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.EspecialidadesFiltro,APP.form.field.Picker,{});

	APP.Portal.Facultades.Load.EspecialidadesToolbar=function(config){
		var me=this;
		
		var filtroField=new APP.Portal.Facultades.Load.EspecialidadesFiltro({});
		
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
						var win = new APP.Portal.Facultades.Load.EspecialidadesWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.Facultades.Load.EspecialidadesToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.EspecialidadesToolbar,Ext.toolbar.Toolbar,{});

	APP.Portal.Facultades.Load.EspecialidadesList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
	
		
		toolbar=new APP.Portal.Facultades.Load.EspecialidadesToolbar({
			dock:'top'
		});
	
		
		me.getToolbar=function(form){
			return toolbar;
		}
		
		me.loadLisEspecialidadestParam=function(form){
			
				store.load({
					params: {
						"facultad.codigo":form.facultad,
						textoNombreEspanol:form.textoNombreEspanol,
						"facultad.textoNombreEspanol":form.textoNombreEspanolFacultad,
						codigoExterno:form.codigoExterno,
						flagEstado:form.flagEstado,
						"tipoEspecialidad.textoNombreEspanol":form.tipoEspecialidad
						/*
						textoNombreEspanol: form.textoNombreEspanol,
						codigoExterno: form.codigoExterno,
						flagEstado: form.flagEstado
						*/
					}
				});
		}
		
		me.loadLisEspecialidadest=function(){
				store.load();
		}
		
		function eliminar(record){
			var codigo=record.get('codigo');
			console.info({action:'delete',codigo:codigo})
			Ext.Ajax.request({
				url : 'facultades/deleteEspecialidad',
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
			var textoNombreEspanol=record.get('textoNombreEspanol');
			var textoNombreIngles=record.get('textoNombreIngles');
			var textonNombreAbreviado=record.get('textonNombreAbreviado');
			var codigoExterno=record.get('codigoExterno');
			var textoNombreEspanolFacutald=record.get('textoNombreEspanolFacutald');
			var flagEstado=record.get('flagEstado');
			var win = new APP.Portal.Facultades.Load.EspecialidadesWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					textoNombreEspanol:textoNombreEspanol,
					textoNombreIngles:textoNombreIngles,
					textonNombreAbreviado:textonNombreAbreviado,
					flagEstado:flagEstado,
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
	            'textoNombreEspanol',
	            'textoNombreIngles',
	            'textonNombreAbreviado',
	            'textoNombreEspanolFacutald',
	            'codigoExterno',
	            'flagEstado',
	            {name: 'flagEstadoNombre',  convert: estado}
	         
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
	
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [
		        { text: 'Código',  dataIndex: 'codigoExterno' },
		        { 
		        	text : 'Nombre',
		        	width : 600,
		        	columns : [
						{ text: 'Español', dataIndex: 'textoNombreEspanol', width : 300 },
						{ text: 'Inglés', dataIndex: 'textoNombreIngles', width : 300 }        
					]
		       	},
		        { text: 'Nombre corto', dataIndex: 'textonNombreAbreviado',flex : 1 },
		        { text: 'Facultad', dataIndex: 'textoNombreEspanolFacutald',flex : 1 },
		        { text: 'Estado', dataIndex: 'flagEstadoNombre',flex : 1 }
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
		APP.Portal.Facultades.Load.EspecialidadesList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.EspecialidadesList,Ext.panel.Panel,{});

	APP.Portal.Facultades.Load.EspecialidadesPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.Facultades.Load.EspecialidadesList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'Especialidades',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.Facultades.Load.EspecialidadesPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.EspecialidadesPanel,Ext.panel.Panel,{});
</script>


<script type="text/javascript">
	APP.Portal.Facultades.Load.Container=function(config){
		var me=this;
		var facultadesPanel,
			especialidadesPanel;
		
		facultadesPanel=new APP.Portal.Facultades.Load.FacultadesPanel({});
		especialidadesPanel=new APP.Portal.Facultades.Load.EspecialidadesPanel({});
		
		me.getFacultadesPanel=function(){
			return facultadesPanel;
		}
		me.getEspecialidadesPanel=function(){
			return especialidadesPanel;
		}
		var panelFullContainer=new Ext.panel.Panel({
			layout: {
		        type: 'vbox',
		        align: 'center'
		    },
			items : [
				{
					width : '100%', flex: 1, items : [facultadesPanel],layout : 'fit'
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
		APP.Portal.Facultades.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.Facultades.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam = {
			codigoTab : '<%=request.getParameter("codigoTab")%>',
	        containerID : '<%=request.getParameter("containerID")%>'
		};
		var globalPanel =new APP.Portal.Facultades.Load.Container({});
		APP.Portal.Facultades.Load.GlobalPanel=globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
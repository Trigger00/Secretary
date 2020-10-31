<script type="text/javascript">
	Ext.ns('APP.Portal.AgendaGrupo.Load')
	APP.Portal.AgendaGrupo.Load.GlobalPanel=null;

</script>
<script type="text/javascript">
	APP.Portal.AgendaGrupo.Load.AgendaGrupoWindowsMantenimiento=function(config){
		
		var me=this,
			uxData = config.uxData;
		
		var agregarPanel,
			saveButton, 
			cancelarButton,
			estado;
		
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
	            if (save.isValid()) {
	            	save.submit({
	                	url: 'agendaGrupo/saveAgendaGrupo',
	                    success: function(form, action) {
	                    	var globalPanel=APP.Portal.AgendaGrupo.Load.GlobalPanel;
	                    	globalPanel.getAgendaGrupoPanel().getListPanel().loadList();
							Ext.Msg.alert('Success', 'Se ha grabado la autiridad correctamente.');
							me.close();
	                    
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', 'error');
	                    }
	                });
	            }
	        }
		});
		
	    var nombreAgendaGrupoField=new Ext.form.field.Text({
	        fieldLabel: 'Nombre de AgendaGrupo',
	        name: 'textoNombre',
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
				nombreAgendaGrupoField
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
							url:'agendaGrupo/getAgendaGrupo',
							params:{
								codigo:uxData.codigo
							}
						})
					}
				
				}
			}
		});
		
		APP.Portal.AgendaGrupo.Load.AgendaGrupoWindowsMantenimiento.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.AgendaGrupo.Load.AgendaGrupoWindowsMantenimiento,Ext.window.Window,{});

	APP.Portal.AgendaGrupo.Load.AgendaGrupoToolbar=function(config){
		var me=this;
		Ext.apply(config,{
			items : [
				'->',
				{
					text : 'Agregar Agenda',
					height : 30,
					cls : 'btn',
					handler:function(){
						
						var win = new APP.Portal.AgendaGrupo.Load.AgendaGrupoWindowsMantenimiento({
							title: 'Agregar'
						});
						win.show();
					}
				}
			]
		});
		APP.Portal.AgendaGrupo.Load.AgendaGrupoToolbar.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.AgendaGrupo.Load.AgendaGrupoToolbar,Ext.toolbar.Toolbar,{});
	
	APP.Portal.AgendaGrupo.Load.AgendaGrupoList=function(config){
		var me=this;
		var toolbar,
			store,
			grid;
			
		toolbar=new APP.Portal.AgendaGrupo.Load.AgendaGrupoToolbar({
			dock:'top'
		});
		
		me.getToolbar=function(form){
			return toolbar;
		}
				
		me.loadList=function(){
				store.load();
		}
		
		function eliminar(record){
			var codigo=record.get('codigo');
			console.info({action:'delete',codigo:codigo})
			Ext.Ajax.request({
				url : 'agendaGrupo/delete',
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
			var win = new APP.Portal.AgendaGrupo.Load.AgendaGrupoWindowsMantenimiento({
				title: 'Modificar',
				uxData:{
					codigo:codigo,
					action:'edit'
				}
			});
			win.show();
			
		}
		
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
		grid=new Ext.grid.Panel({
		    store: store,
		    xtype: 'grouped-header-grid',
		    columns: [		       
				{ text: 'Nombre',  dataIndex: 'textoNombre',flex:1 }
		    ],
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
		APP.Portal.AgendaGrupo.Load.AgendaGrupoList.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.AgendaGrupo.Load.AgendaGrupoList,Ext.panel.Panel,{});
	
	APP.Portal.AgendaGrupo.Load.AgendaGrupoPanel=function(config){
		var me=this;
		var listPanel;
		
		listPanel=new APP.Portal.AgendaGrupo.Load.AgendaGrupoList({})
		me.getListPanel=function(){
			return listPanel;
		}
		
		Ext.apply(config,{
			title : 'AgendaGrupo',
			items  : [listPanel],
			layout : 'fit'
		});
		
		APP.Portal.AgendaGrupo.Load.AgendaGrupoPanel.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.AgendaGrupo.Load.AgendaGrupoPanel,Ext.panel.Panel,{});
</script>

<script type="text/javascript">
	APP.Portal.AgendaGrupo.Load.Container=function(config){
		var me = this;
		var datoGeneralPanel;
		
		datoGeneralPanel = new APP.Portal.AgendaGrupo.Load.AgendaGrupoPanel({});
		
		me.getAgendaGrupoPanel=function(){
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
		
		APP.Portal.AgendaGrupo.Load.Container.superclass.constructor.call(this,config);
	}
	Ext.extend(APP.Portal.AgendaGrupo.Load.Container,Ext.panel.Panel,{});
</script>
<script type="text/javascript">

	(function(){
		var reqParam ={
				codigoTab : '<%=request.getParameter("codigoTab")%>',
		        containerID : '<%=request.getParameter("containerID")%>'				
		};
		var globalPanel = new APP.Portal.AgendaGrupo.Load.Container({});
		APP.Portal.AgendaGrupo.Load.GlobalPanel=globalPanel;
		var container = APP.Portal.Workspace.ContainerManager.getContainer(reqParam.containerID);
		var panel = container.getTab(reqParam.codigoTab);
		
		panel.removeAll(true)
		panel.add(globalPanel);
		panel.doLayout();
	})()
</script>
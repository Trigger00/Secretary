<script type="text/javascript">
	Ext.ns('APP.Portal.Workspace');
	APP.Portal.Workspace.GlobalPanel = null;
	APP.Portal.Workspace.data = null;
</script>

<script type="text/javascript">
	Ext.define('APP.Portal.Workspace.Module', {
	    extend: 'Ext.tab.Panel',
	    activePanel : null,
	    openTab : function(config){
	        var me = this;
	        var isNuevo = true;
	        me.items.each(function (item, index, list) {
	            if (item.codigoTab === config.codigo) {
	                me.setActiveTab(item);
	                item.items.items[0].items.items[0].setActiveTab(0);
	                isNuevo = false;
	            }
	        }, this);
	        if (isNuevo) {
	            var url =config.url;
	            url+= '?codigoTab='+config.codigo;
	            url+= '&containerID='+config.containerID;
	            if(config.parameters){
	                url+='&'+config.parameters;
	            }
	            var panelContainer = new Ext.Panel({
	                loadScripts: true, 
	                codigoTab: config.codigo, 
	                layout: 'fit', 
	                autoLoad: {
	                    url: url, 
	                    border: false, 
	                    scripts: true, 
	                    scope: this
	                }, 
	                border: false
	            })
	                
	            var panelTab = new Ext.Panel({
	                title: config.nombreTab, 
	                items: [panelContainer], 
	                codigoTab: config.codigo, 
	                contenedorPanel: panelContainer, 
	                layout: 'fit', 
	                border: false, 
	                closable: config.closable==undefined?true:config.closable
	            });
	            me.add(panelTab);
	            me.setActiveTab(panelTab);
	        }
	    },
	    getTab : function(codigo){
	        var me = this;
	        var panel = null;
	        me.items.each(function (item, index, list) {
	            if (item.codigoTab == codigo) {
	                panel = item.contenedorPanel;
	            }
	        }, this)
	        return panel;
	    },
	    closeTab : function(codigo){
	        var me = this;
	        
	        me.items.each(function (item, index, list) {
	            if (item.codigoTab === codigo) {
	                item.close();
	            }
	        }, this)
	        
	    }
	})
</script>

<script type="text/javascript">
	APP.Portal.Workspace.ContainerManager = {
		    items : [],
		    loadContainer : function(config){
		        var me=this,
		            items = me.items,
		            panel = config.panel,
		            key = panel.containerID,
		            findPanel = null;
		        Ext.each(items,function(item,index,list){
		            if(item.key == key){
		                if(item.panel==null){
		                    findPanel = panel;    
		                }
		                
		            }
		        })
		        if(findPanel == null){
		            findPanel = panel;
		            items.push({
		                key : key,
		                panel : findPanel
		            })
		        }
		        return findPanel;
		    },
		    getContainer : function(codigo){
		        var me = this,
		            items = me.items;
		        var panel = null;
		        Ext.each(items,function (item, index, list) {
		            if (item.key == codigo) {
		                panel = item.panel;
		            }
		        })
		        return panel;
		    }
		}
</script>
<script type="text/javascript">
	APP.Portal.Workspace.Menu = function (config) {
		var me=this,
			root = APP.Portal.Workspace.data.userMenus;
		console.info({root:root})
	    var onItemclick=function (tree, record, item, index, e, options) {
	        var uxInfo = record.raw;
	        if (record.data.leaf) {
		        var workspace = APP.Portal.Workspace.ContainerManager.getContainer('WORKSPACE');
		        workspace.openTab({
		            codigo: uxInfo.codigo,
		            url: uxInfo.urlPage,
		            nombreTab: uxInfo.nombre,
		            containerID : 'WORKSPACE'
		        });
	        }
	    };
	    this.reloadUserData=function(){
	        Ext.Ajax.request({
	            url: myBasePath+'getUserData.htm',
	            method: 'POST',
	            success: function (response, options) {
	                var jsonResponse = Ext.JSON.decode(response.responseText);
	                if (jsonResponse.success) {
	                	APP.Portal.Workspace.data = jsonResponse.data;
	                    me.setRootNode(APP.Portal.Workspace.data.userMenus);
	                } else {
	                    Ext.MessageBox.show({
	                        title: 'Alerta',
	                        msg: jsonResponse.message,
	                        width: 300,
	                        buttons: Ext.Msg.OK
	                    });
	                }
	
	            },
	            failure: function (response, options) {
	                var jsonResponse = Ext.JSON.decode(response.responseText);
	                Ext.MessageBox.show({
	                    title: 'Error',
	                    msg: jsonResponse.message,
	                    width: 300,
	                    buttons: Ext.Msg.OK
	                });
	            }
	        });
	    }
	    Ext.apply(config,{
	        root : root,
	        rootVisible : false,
	        bodyCls: 'menu-vertical',
	        listeners : {
	            itemclick: onItemclick
	        }
	    });
	    APP.Portal.Workspace.Menu.superclass.constructor.call(this, config);
	};
	
	Ext.extend(APP.Portal.Workspace.Menu,Ext.tree.Panel, {});
</script>

<script type="text/javascript">
	
	APP.Portal.Workspace.Header = function (config) {
		var me=this;
		var logoutAction=function(){
			Ext.Ajax.request({
			    url: 'auth/logout',
			    success: function(resp){
			    	var response = Ext.decode(resp.responseText);
			        if(response.success){
	                    var panel = new Ext.Panel({
	                        border: false,
	                        layout: 'fit',
	                        loadScripts: true,
	                    	autoLoad: {
	                            url: 'workspace',
	                            border: false,
	                            scripts: true,
	                            scope: this
	                        }
	                    });
	                    APP.Portal.GlobalPanel.removeAll();
	                    APP.Portal.GlobalPanel.add(panel);
	                    APP.Portal.GlobalPanel.doLayout();
			        }
			        
			    }
			});
		}
		
		var logoutButton = new Ext.Button({
            height : 30,
            cls : 'btn',
            handler: logoutAction,
            text : 'Cerrar sesión'
        });
	    Ext.apply(config,{
	    	layout: 'border',
	        items : [
				{
					region : 'center',
					title : 'Sistema'
				},
				{
					region : 'east',
					width : 450,
					layout : 'border',
					bodyStyle:'background-color: #157FCC;color:#FFF',
                	items : [
	                    {
	                    	region :'center',
	                    	bodyStyle:'background-color: #157FCC;color:#FFF',
	                        html :{
	                            tag : 'div',
	                            style : 'background-color: #157FCC;color:#FFFFFF;text-align:right;padding:5px;padding-top:10px;font-family :arial,helvetica,verdana,sans-serif;font-size:13px;font-weight:bold;',
	                            html : APP.Portal.Workspace.data.userInfo.nombreUsuario
	                        }
	                    },
	                    {
	                    	region :'east',
	                    	width :110,
	                        html :{
	                            tag : 'div',
	                            style : 'text-align:left;padding:10px;padding-top:3px;background-color: #157FCC;'
	                            ,id : 'id-btn-cerra-session'
	                        }
	                    }
                    ]
				}
			],
			listeners : {
                afterrender : function(){
                	logoutButton.render('id-btn-cerra-session');
                }
            } 
	    });
	    APP.Portal.Workspace.Header.superclass.constructor.call(this, config);
	};
	
	Ext.extend(APP.Portal.Workspace.Header, Ext.Panel, {});
</script>

<script type="text/javascript">
	
	APP.Portal.Workspace.Container = function (config) {
		var me=this;
		
		var menuContainer=new APP.Portal.Workspace.Menu({
			
		})
		var headerContainer=new APP.Portal.Workspace.Header({
			
		})
		var moduleContainer=new APP.Portal.Workspace.Module({
			layout: 'fit',
			containerID : 'WORKSPACE'
		})
		
		APP.Portal.Workspace.ContainerManager.loadContainer({
            panel : moduleContainer
        })
		var menuPanel=new Ext.panel.Panel({
			region: 'west',
			width : 280,
			margins : '2 0 2 2',
			split: true,
			layout : 'fit',
			items : [menuContainer]
		})
		var headerPanel=new Ext.panel.Panel({
			region: 'north',
			height: 36,
			layout : 'fit',
			items:[headerContainer]
		})
		var modulePanel=new Ext.panel.Panel({
			region: 'center',
			margins : '2 2 2 0',
			layout : 'fit',
			items : [moduleContainer]
		})
		
	    Ext.apply(config,{
	        layout : 'border',
	        items : [headerPanel,menuPanel,modulePanel]
	    });
	    APP.Portal.Workspace.Container.superclass.constructor.call(this, config);
	};
	
	Ext.extend(APP.Portal.Workspace.Container, Ext.Panel, {});
</script>


<script type="text/javascript">
    (function () {
    	Ext.Ajax.request({
            url: 'auth/getUserData',
            method: 'POST',
            success: function (response, options) {
                var jsonResponse = Ext.decode(response.responseText);
                if (jsonResponse.success) {
                	APP.Portal.Workspace.data = jsonResponse.data;
                	APP.Portal.Workspace.GlobalPanel = new APP.Portal.Workspace.Container({});
                    APP.Portal.GlobalPanel.removeAll(true);
                    APP.Portal.GlobalPanel.add(APP.Portal.Workspace.GlobalPanel);
                    APP.Portal.GlobalPanel.doLayout();
                } else {
                    Ext.MessageBox.show({
                        title: 'Alerta'
                        , msg: jsonResponse.message
                        , width: 300
                        , buttons: Ext.Msg.OK
                    });
                }

            },
            failure: function (response, options) {
                var jsonResponse = Ext.JSON.decode(response.responseText);
                Ext.MessageBox.show({
                    title: 'Error'
                    , msg: jsonResponse.message
                    , width: 300
                    , buttons: Ext.Msg.OK
                });
            }
        });
    	
    	
        
    })();
</script>
<script type="text/javascript">
	Ext.ns('APP.Portal.Login');

	APP.Portal.Login.Window = function(config) {
		var me = this;

		var usuarioField,
			claveField,
			ingresarButton,
			loginPanel;

		var loginAction;
		
		usuarioField = new Ext.form.field.Text({
			fieldLabel : 'Usuario',
			allowBlank : false,
			listeners : {
				specialkey : function(field, e) {
					if (e.getKey() == e.ENTER) {
						if (claveField.getValue() == '') {
							claveField.focus();
						} else {
							loginAction();
						}
					}
				}
			}
		});

		claveField = new Ext.form.field.Text({
			fieldLabel : 'Clave',
			allowBlank : false,
			inputType : 'password',
			listeners : {
				specialkey : function(field, e) {
					if (e.getKey() == e.ENTER) {
						if (usuarioField.getValue() == '') {
							usuarioField.focus();
						} else {
							loginAction();
						}
					}
				}
			}
		});
		
		loginAction = function() {
			Ext.Ajax.request({
			    url: 'auth/login',
			    params: {
			        u: $.md5(claveField.getValue()),
			        p: $.md5(usuarioField.getValue())
			    },
			    success: function(resp){
			        var response = Ext.decode(resp.responseText);
			        if(response.success){
			        	me.close();
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
			        }else{
			        	Ext.Msg.alert('Login', response.message);
			        }
			        
			    }
			});
		}
		
		ingresarButton = new Ext.Button({
			width : 80,
			text : 'Ingresar',
			listeners : {
				click : loginAction
			}
		})

		
		loginPanel = new Ext.form.Panel({
			items : [ usuarioField, claveField ],
			fieldDefaults : {
				msgTarget : 'side',
				labelWidth : 50,
				witdh : 400
			},
			bodyStyle : 'padding:20px;',
			buttons : [ ingresarButton ],
			border : false
		});


		
		console.info({config:config})
		Ext.apply(config,{
			title : 'Sistema',
			closable : false,
			items : [ loginPanel ]
		})

		
		APP.Portal.Login.Window.superclass.constructor.call(this, config);
	};

	Ext.extend(APP.Portal.Login.Window, Ext.Window, {});
</script>

<script type="text/javascript">
	(function() {
		
		var win = new APP.Portal.Login.Window({});
		win.show();

	})();
</script>
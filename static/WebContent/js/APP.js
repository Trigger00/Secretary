Ext.ns('APP.Portal');
Ext.ns('APP.LOG');
APP.Portal.GlobalPanel = null;
APP.LOG.debug = function(obj){
    if( Ext.firefoxVersion && Ext.firefoxVersion > 0){
        console.info(obj);
    }
}
Ext.onReady(function(){
    Ext.QuickTips.init();
    var panel = new Ext.Panel({
        border: false,
        loadScripts: true,
        autoLoad: {
            url: 'workspace',
            border: false,
            scripts: true,
            scope: this
        }
    })

    APP.Portal.GlobalPanel = new Ext.Panel({
        border: false
        , region: 'center'
        , layout: 'fit'
        , items: [panel]
    })

    var viewPort = new Ext.Viewport({
        layout: 'border'
        , items: [
            APP.Portal.GlobalPanel
        ]
    })
    
});


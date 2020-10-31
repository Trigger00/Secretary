Ext.define('APP.form.field.Picker', {
		extend : 'Ext.form.field.Picker',
		alias : 'widget.apppicker',
		initComponent : function() {
			this.createPicker({
				pickerField : this
			});
			this.callParent(arguments);
		},
		withChildPicker : function(e) {
			var withChild = false;
			if (this.picker.rendered) {
				var fields = this.picker.query('pickerfield');
				if (fields) {
					Ext.Array.each(fields, function(combo) {
						var picker = combo.getPicker();
						if (picker && picker.rendered && e.within(picker.el)) {
							withChild = true;
							return false;
						}
					});
				}
			}
			return withChild;
		},

		mimicBlur : function(e) {
			if (this.withChildPicker(e))
				return;
			this.callParent(arguments);
		},

		collapseIf : function(e) {
			if (this.withChildPicker(e))
				return;
			this.callParent(arguments);
		},
		picker : null
	});
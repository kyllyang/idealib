Ext.define("Base.ux.DictComboBox", {
	extend: 'Ext.form.field.ComboBox',

	invokeCode: null,
	initAutoLoad: true,

	initComponent: function() {
		Ext.define("SysItem", {
			extend: 'Ext.data.Model',
			fields: [
				{name: 'key', mapping: 'key', type: 'string'},
				{name: 'value', mapping: 'value', type: 'string'}
			]
		});

		var store = Ext.create('Ext.data.Store', {
			model: 'SysItem',
			proxy: {
				type: 'ajax',
				url: ctx + '/sysmanager/dictitem/list.ctrl',
				extraParams: {
					'qc.invokeCode': this.invokeCode,
					limit: 65535
				},
				reader: {
					type: 'json',
					root: 'dataList',
					totalProperty: 'paginated.totalRecord'
				}
			},
			autoLoad: this.initAutoLoad
		});

		Ext.apply(this, {
			store: store,
			editable: false,
			emptyText: '请选择...',
			displayField: 'value',
			valueField: 'key',
			queryMode: 'remote'
		});
		this.callParent();
	}
});

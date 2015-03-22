Ext.define("Base.ux.TextComboBox", {
	extend: 'Ext.form.field.ComboBox',

	itemData: [],
	emptyText: '请选择...',
	initAutoLoad: true,

	initComponent: function() {
		Ext.define("DataItem", {
			extend: 'Ext.data.Model',
			fields: [
				{name: 'key', mapping: 'key', type: 'string'},
				{name: 'value', mapping: 'value', type: 'string'}
			]
		});
// 修改为远程模式
		var store = Ext.create('Ext.data.Store', {
			fields: ['key', 'value'],
			data: this.itemData
		});

		Ext.apply(this, {
			store: store,
			editable: true,
			emptyText: this.emptyText,
			displayField: 'value',
			valueField: 'key',
			queryMode: 'local'
		});
		this.callParent();
	}
});

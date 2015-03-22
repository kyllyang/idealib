Ext.define('Base.MenuTreePanel', {
	extend: 'Ext.tree.Panel',

	itemId: 'menuTreePanel',

	initComponent: function() {
		Ext.define('TreeModel', {
			extend: 'Ext.data.Model',
			fields: ['id', 'text', 'funcType', 'funcCode']
		});

		var store = Ext.create('Ext.data.TreeStore', {
			model: 'TreeModel',
			proxy: {
				type: 'ajax',
				url: ctx + '/sysmanager/module/leftMenu.ctrl'
			},
			reader: {
				type: 'json'
			},
			root: {
				id: null,
				text: '应用菜单'
			}
		});

		Ext.apply(this, {
			store: store,
			useArrows: true
		});
		this.callParent();

		this.on('itemclick', function(treePanel, record, item, index, e, eOpts) {
			var funcCode = record.get('funcCode');
			if (funcCode && funcCode.className) {
				myServer.setMainContent(Ext.create(funcCode.className, funcCode.config));
			}
		}, this);

		this.store.on('load', function(store, node, records, successful, eOpts) {
			this.expandAll();
		}, this);
	},
	loadTree: function(moduleId) {
		this.store.proxy.actionMethods = {read: 'POST'};
		Ext.apply(this.store.proxy.extraParams, {
			moduleId: moduleId
		});

		this.getStore().load();
	}
});

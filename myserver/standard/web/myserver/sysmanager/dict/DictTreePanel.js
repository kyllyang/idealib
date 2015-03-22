Ext.define('SysManager.dict.DictTreePanel', {
	extend: 'Ext.tree.Panel',

	currentNodeId: null,

	itemId: 'dictTreePanel',
	autoScroll: true,

	initComponent: function() {
		Ext.define('TreeModel', {
			extend: 'Ext.data.Model',
			fields: ['id', 'text', 'name']
		});

		var store = Ext.create('Ext.data.TreeStore', {
			model: 'TreeModel',
			proxy: {
				type: 'ajax',
				url: ctx + '/sysmanager/dict/tree.ctrl'
			},
			reader: {
				type: 'json'
			},
			root: {
				id: null,
				text: '数据字典'
			}
		});

		Ext.apply(this, {
			store: store,
			useArrows: true
		});
		this.callParent();

		this.on('itemcontextmenu', function(treePanel, record, item, index, e, eOpts) {
			Ext.create('SysManager.dict.DictMenu').showAt(e.getXY());
			e.stopEvent();
		}, this);

		this.on('select', function(treePanel, record, index, eOpts) {
			myServer.getMainContent().getComponent('dictItemGridPanel').queryData();
		}, this);

		this.getStore().on('load', function(store, node, records, successful, eOpts) {
			var selectNode = this.currentNodeId ? store.getNodeById(this.currentNodeId) : store.getRootNode();

			var tempNode = selectNode;
			while (tempNode) {
				this.expandNode(tempNode);
				tempNode = tempNode.parentNode;
			}

			this.getSelectionModel().select(selectNode);
		}, this);

		this.getStore().load();
	},
	loadTreeNode: function(id) {
		this.currentNodeId = id;
		this.getStore().load();
	},
	getSelectedRecord: function() {
		return this.getSelectionModel().getLastSelected();
	}
});

Ext.define('Meaord.dishes.DishesTreePanel', {
	extend: 'Ext.tree.Panel',

	restaurantId: null,
	itemId: 'dishesTreePanel',
	autoScroll: true,
	useArrows: true,

	initComponent: function() {
		Ext.define('TreeModel', {
			extend: 'Ext.data.Model',
			fields: ['id', 'text']
		});

		var store = Ext.create('Ext.data.TreeStore', {
			model: 'TreeModel',
			proxy: {
				type: 'ajax',
				url: ctx + '/meaord/dishes/tree.ctrl',
				extraParams: {
					restaurantId: this.restaurantId
				}
			},
			reader: {
				type: 'json'
			},
			root: {
				id: null,
				text: '全部'
			}
		});

		Ext.apply(this, {
			store: store
		});
		this.callParent();

		this.on('select', function(tree, record, index, eOpts) {
			this.ownerCt.getComponent('dishesDataView').loadDataView(record.get('id'));
		}, this);

		this.loadTree(null);
	},
	loadTree: function(type) {
		var store = this.getStore();
		store.load({
			callback: function(records, operation, success) {
				var selectionModel = this.getSelectionModel();
				var selectNode;
				if (type) {
					selectNode = store.getNodeById(type);
					if (!selectNode) {
						selectNode = store.getRootNode();
						this.expandNode(selectNode);
					}
				} else {
					selectNode = store.getRootNode();
					this.expandNode(selectNode);
				}
				selectionModel.select(selectNode);

				var itemData = [];
				for (var i = 0; i < records.length; i++) {
					itemData.push({key: records[i].get('id'), value: records[i].get('text')});
				}

				var typeComboBox = this.ownerCt.getComponent('dishesFormPanel').getForm().findField('type');
				typeComboBox.getStore().loadData(itemData);
			},
			scope: this
		});
	},
	getChildNodes: function() {
		return this.getStore().getRootNode().childNodes;
	}
});

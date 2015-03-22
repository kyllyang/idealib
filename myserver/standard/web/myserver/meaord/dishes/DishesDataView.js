Ext.define('Meaord.dishes.DishesDataView', {
	extend: 'Ext.view.View',

	restaurantId: null,
	type: null,
	itemId: 'dishesDataView',

	initComponent: function() {
		Ext.define('DataViewModel', {
			extend: 'Ext.data.Model',
			fields: ['id', 'name', 'price']
		});

		var store = Ext.create('Ext.data.Store', {
			model: 'DataViewModel',
			proxy: {
				type: 'ajax',
				url: ctx + '/meaord/dishes/list.ctrl'
			},
			reader: {
				type: 'json'
			}
		});

		Ext.apply(this, {
			store: store,
			tpl: [
				'<tpl for=".">',
				'<div class="thumb-wrap" id="{id}">',
				'<div class="thumb"><img src="' + ctx + '/sysmanager/attachment/view.ctrl?qc.entityName=org.kyll.myserver.business.meaord.entity.MeaordDishes&qc.entityId={id}&r=' + Math.random() + '" title="{name}"></div>',
				'<span class="x-editable">{name}({price}元)</span></div>',
				'</tpl>',
				'<div class="x-clear"></div>'
			],
			autoScroll: true,
			trackOver: true,
			overItemCls: 'x-item-over',
			itemSelector: 'div.thumb-wrap',
			listeners: {
				selectionchange: function(selectionModel, dataModels, eOpts) {
					if (dataModels.length > 0) {
						this.ownerCt.getComponent('dishesFormPanel').loadForm(dataModels[0].get('id'));
					}
				},
				scope: this
			}
		});
		this.callParent();

		this.store.on('beforeload', function(store, operation, eOpts) {
			this.store.proxy.actionMethods = {read: 'POST'};
			Ext.apply(this.store.proxy.extraParams, {
				'qc.restaurantId': this.restaurantId,
				'qc.type': this.type
			});
		}, this);
	},
	loadDataView: function(type) {
		this.type = type;
		this.tpl = new Ext.XTemplate(
			'<tpl for=".">',
			'<div class="thumb-wrap" id="{id}">',
			'<div class="thumb"><img src="' + ctx + '/sysmanager/attachment/view.ctrl?qc.entityName=org.kyll.myserver.business.meaord.entity.MeaordDishes&qc.entityId={id}&r=' + Math.random() + '" title="{name}"></div>',
			'<span class="x-editable">{name}({price}元)</span></div>',
			'</tpl>',
			'<div class="x-clear"></div>'
		);
		var store = this.getStore();
		store.load({
			callback: function(records, operation, success) {
				this.getSelectionModel().select(store.getAt(0));
			},
			scope: this
		});
	}
});

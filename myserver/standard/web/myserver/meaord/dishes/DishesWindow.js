Ext.define('Meaord.dishes.DishesWindow', {
	extend: 'Ext.window.Window',

	restaurantId: null,
	restaurantName: null,

	title: '菜品信息',
	width: 1000,
	height: 600,
	border: false,
	modal: true,
	resizable: false,
	layout: 'border',

	initComponent: function() {
		this.callParent();

		this.add(Ext.create('Meaord.dishes.DishesTreePanel', {
			region: 'west',
			width: 200,
			restaurantId: this.restaurantId
		}));
		this.add(Ext.create('Meaord.dishes.DishesDataView', {
			id: 'images-view',
			region: 'center',
			width: 470,
			restaurantId: this.restaurantId
		}));
		this.add(Ext.create('Meaord.dishes.DishesFormPanel', {
			region: 'east',
			width: 330,
			restaurantId: this.restaurantId,
			restaurantName: this.restaurantName
		}));
	}
});

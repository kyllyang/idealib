Ext.define('Meaord.restaurant.RestaurantGridPanel', {
	extend: 'Base.ux.GridPanel',

	initComponent: function() {
		Ext.apply(this, {
			url: ctx + '/meaord/restaurant/list.ctrl',
			sortProperty: 'sort',
			sortDirection: 'asc',
			idProperty: 'id',
			columns: [{
				dataIndex: 'id',
				hidden: true
			}, {
				text: '餐厅名称',
				dataIndex: 'name',
				flex: 1
			}, {
				text: '联系人',
				dataIndex: 'linkman',
				flex: 1
			}, {
				text: '联系电话1',
				dataIndex: 'phone1',
				flex: 1
			}, {
				text: '联系电话2',
				dataIndex: 'phone2',
				flex: 1
			}],
			tbar: [{
				text: '菜品',
				icon: ctx + '/resource/image/icon/dishes.png',
				handler: this.dishesList,
				scope: this
			}],
			dockedItems: [Ext.create('Ext.form.Panel', {
				itemId: 'queryForm',
				dock: 'top',
				autoHeight: true,
				bodyPadding: 5,
				layout: 'hbox',
				defaults: {
					labelAlign: 'right',
					labelWidth: 80,
					labelSeparator: '：',
					width: 250
				},
				items: [{
					xtype: 'textfield',
					fieldLabel: '餐厅名称',
					name: 'name'
				}, {
					xtype: 'textfield',
					fieldLabel: '联系人',
					name: 'linkman'
				}, {
					xtype: 'textfield',
					fieldLabel: '联系电话',
					name: 'phone'
				}, {
					xtype: 'button',
					text: '查询',
					width: 80,
					margin: '0 0 0 10',
					handler: this.queryData,
					scope: this
				}, {
					xtype: 'button',
					text: '清空',
					width: 80,
					margin: '0 0 0 10',
					handler: this.clear,
					scope: this
				}]
			})],
			listeners: {
				afterRender: function(form, eOpts){
					this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
						enter: this.queryData,
						scope: this
					});
				},
				scope: this
			}
		});
		this.callParent();

		this.on('itemdblclick', function(grid, record, item, index, e, eOpts){
			this.viewEventHandler();
		});

		this.store.on('beforeload', function(store, operation, eOpts) {
			var form = this.getComponent('queryForm').getForm();

			this.store.proxy.actionMethods = {read: 'POST'};
			Ext.apply(this.store.proxy.extraParams, {
				'qc.name': form.findField('name').getValue(),
				'qc.linkman': form.findField('linkman').getValue(),
				'qc.phone': form.findField('phone').getValue()
			});
		}, this);

		this.queryData();
	},
	doAddEvent: function() {
		Ext.create('Meaord.restaurant.RestaurantFormWindow').show();
	},
	doEditEvent: function(id) {
		Ext.create('Meaord.restaurant.RestaurantFormWindow', {entityId: id}).show();
	},
	doViewEvent: function(id) {
		Ext.create('Meaord.restaurant.RestaurantFormWindow', {entityId: id, readOnlyForm: true}).show();
	},
	doDeleteEvent: function(ids) {
		Ext.Ajax.request({
			url: ctx + '/meaord/restaurant/delete.ctrl',
			params: {
				ids: ids
			},
			success: function(response, opts) {
				Ext.Msg.alert("系统提示", "数据删除成功！");
				this.queryData();
			},
			failure: function(response, opts) {
				Ext.Msg.alert("系统提示", "数据删除失败！");
			},
			scope: this
		});
	},
	dishesList: function() {
		var records = this.getSelectedRecords();
		if (records.length == 1) {
			Ext.create('Meaord.dishes.DishesWindow', {
				restaurantId: records[0].get('id'),
				restaurantName: records[0].get('name')
			}).show();
		} else {
			Ext.Msg.alert("系统提示", "请选择一条数据！");
		}
	},
	queryData: function() {
		this.getStore().load();
	},
	clear: function() {
		var form = this.getComponent('queryForm').getForm();
		form.findField('name').setValue('');
		form.findField('linkman').setValue('');
		form.findField('phone').setValue('');
		this.queryData();
	}
});

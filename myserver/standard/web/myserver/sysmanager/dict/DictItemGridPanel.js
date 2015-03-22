Ext.define('SysManager.dict.DictItemGridPanel', {
	extend: 'Base.ux.GridPanel',

	itemId: 'dictItemGridPanel',

	initComponent: function() {
		Ext.apply(this, {
			url: ctx + '/sysmanager/dictitem/list.ctrl',
			sortProperty: 'sort',
			sortDirection: 'asc',
			enableEditBtn: false,
			enableViewBtn: false,
			enableColumnEditBtn: false,
			enableColumnViewBtn: false,
			enableColumnDelBtn: false,
			idProperty: 'id',
			columns: [{
				dataIndex: 'id',
				hidden: true
			}, {
				dataIndex: 'dictId',
				hidden: true
			}, {
				text: '键名',
				dataIndex: 'key',
				field: {
					maxLength: 100,
					allowBlank: false
				},
				flex: 1
			}, {
				text: '键值',
				dataIndex: 'value',
				field: {
					maxLength: 100,
					allowBlank: false
				},
				flex: 1
			}, {
				text: '排序',
				dataIndex: 'sort',
				field: {
					xtype: 'numberfield',
					minValue: 1,
					allowDecimals: false,
					allowBlank: false
				}
			}],
			tbar: [{
				text: '保存',
				icon: ctx + '/resource/image/icon/save.png',
				handler: this.saveGrid,
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
					fieldLabel: '键名',
					name: 'key'
				}, {
					xtype: 'textfield',
					fieldLabel: '键值',
					name: 'value'
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
				afterRender: function(form, eOpts) {
					this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
						enter: function() {
							this.queryData();
						},
						scope: this
					});
				},
				scope: this
			},
			plugins: [Ext.create('Ext.grid.plugin.CellEditing', {
				pluginId: 'cellEditingPlugin',
				clicksToEdit: 1
			})]
		});
		this.callParent();

		this.store.on('beforeload', function(store, operation, eOpts) {
			var form = this.getComponent('queryForm').getForm();

			this.store.proxy.actionMethods = {read: 'POST'};
			Ext.apply(this.store.proxy.extraParams, {
				'qc.dictId': myServer.getMainContent().getComponent('dictTreePanel').getSelectedRecord().get('id'),
				'qc.key': form.findField('key').getValue(),
				'qc.value': form.findField('value').getValue()
			});
		}, this);
	},
	doAddEvent: function() {
		if (myServer.getMainContent().getComponent('dictTreePanel').getSelectedRecord().get('id')) {
			var record = Ext.ModelManager.create({
				key: '键',
				value: '值',
				sort: 1
			}, Ext.getClassName(this.getStore().model));
			this.getStore().insert(0, record);
			this.getPlugin('cellEditingPlugin').startEditByPosition({row: 0, column: 0});
		} else {
			Ext.Msg.alert("系统提示", "请选择一个数据字典！");
		}
	},
	doDeleteEvent: function(ids) {
		var validIds = [];
		for (var i = 0; i < ids.length; i++) {
			if (ids[i]) {
				validIds.push(ids[i]);
			}
		}

		Ext.Ajax.request({
			url: ctx + '/sysmanager/dictitem/delete.ctrl',
			params: {
				ids: validIds
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
	saveGrid: function() {
		var records = this.getStore().getModifiedRecords();
		var datas = [];
		for (var i = 0; i < records.length; i++) {
			datas.push({
				id: records[i].get('id'),
				dictId: myServer.getMainContent().getComponent('dictTreePanel').getSelectedRecord().get('id'),
				key: records[i].get('key'),
				value: records[i].get('value'),
				sort: records[i].get('sort')
			});
		}

		if(datas.length > 0) {
			Ext.Ajax.request({
				url: ctx + '/sysmanager/dictitem/save.ctrl',
				params: {
					dictItemJson: Ext.encode(datas)
				},
				success: function(response, opts) {
					Ext.Msg.alert('系统提示', '数据保存成功！');
					this.queryData();
				},
				failure: function(response, opts) {
					Ext.Msg.alert('系统提示', '无法保存数据！');
				},
				scope: this
			});
		}
	},
	queryData: function() {
		this.getStore().load();
	},
	clear: function() {
		var form = this.getComponent('queryForm').getForm();
		form.findField('key').setValue('');
		form.findField('value').setValue('');
		this.queryData();
	}
});

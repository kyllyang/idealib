Ext.define('SysManager.user.RoleTreePanel', {
	extend: 'Ext.tree.Panel',

	itemId: 'roleTreePanel',
	autoScroll: true,

	initComponent: function() {
		Ext.define('TreeModel', {
			extend: 'Ext.data.Model',
			fields: ['id', 'text']
		});

		var store = Ext.create('Ext.data.TreeStore', {
			model: 'TreeModel',
			proxy: {
				type: 'ajax',
				url: ctx + '/sysmanager/role/tree.ctrl'
			},
			reader: {
				type: 'json'
			},
			root: {
				id: null,
				text: '角色',
				expand: true
			}
		});

		Ext.apply(this, {
			store: store,
			useArrows: true,
			buttonAlign: 'center',
			tbar: [{
				xtype: 'button',
				icon: ctx + '/resource/image/icon/expandall.png',
				handler: this.expandAll,
				scope: this
			}, {
				xtype: 'button',
				icon: ctx + '/resource/image/icon/collapseall.png',
				handler: this.collapseAll,
				scope: this
			}],
			buttons:[{
				xtype: 'button',
				text: '保存',
				handler: this.saveTree,
				scope:this,
				hidden: this.readOnlyForm
			}]
		});
		this.callParent();

		this.on('checkchange', function(node, checked) {
			node.expand();
			node.checked = checked;
			node.eachChild(function(child) {
				child.set('checked', checked);
				child.fireEvent('checkchange', child, checked);
			});
		});

		this.getStore().load();
	},
	loadTree: function(userId) {
		this.store.proxy.actionMethods = {read: 'POST'};
		Ext.apply(this.store.proxy.extraParams, {
			userId: userId
		});

		this.getStore().load();
	},
	saveTree: function() {
		var record = myServer.getMainContent().getComponent('userGridPanel').getSelectedRecord();
		if (record) {
			var records = this.getChecked();
			var moduleIds = [];
			for (var i = 0; i < records.length; i++) {
				moduleIds.push(records[i].get('id'));
			}

			Ext.Ajax.request({
				url: ctx + '/sysmanager/user/saveRole.ctrl',
				params: {
					userId: record.get('id'),
					roleIds: moduleIds
				},
				success: function(response, opts) {
					Ext.Msg.alert('系统提示', '数据保存成功！');
				},
				failure: function(response, opts) {
					Ext.Msg.alert("系统提示", "数据删除失败！");
				},
				scope: this
			});
		} else {
			Ext.Msg.alert("系统提示", "请选择一个用户！");
		}
	}
});

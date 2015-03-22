Ext.define('SysManager.user.UserFormWindow', {
	extend: 'Ext.window.Window',

	entityId: null,
	readOnlyForm: false,

	title: '用户信息',
	width: 400,
	height: 200,
	border: false,
	modal: true,
	resizable: false,
	layout: 'fit',

	initComponent: function() {
		this.callParent();

		Ext.define('FormModel', {
			extend: 'Ext.data.Model',
			fields: [
				{name: 'id'},
				{name: 'username'},
				{name: 'password'},
				{name: 'email'},
				{name: 'freeze'},
				{name: 'sort'}
			]
		});

		var usernameText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '<span style="color: #FF0000;">*</span>用户名称',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'username',
			maxLength: 50,
			allowBlank: false,
			readOnly: this.readOnlyForm
		});
		var passwordText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '<span style="color: #FF0000;">*</span>密码',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'password',
			maxLength: 50,
			allowBlank: false,
			readOnly: this.readOnlyForm
		});
		var emailText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '电子邮箱',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'email',
			maxLength: 50,
			vtype: 'email',
			readOnly: this.readOnlyForm
		});
		var freezeCheckbox = Ext.create('Ext.form.field.Checkbox', {
			fieldLabel: '冻结',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'freeze',
			inputValue: '1',
			uncheckedValue: '0',
			readOnly: this.readOnlyForm
		});
		var sortNumber = Ext.create('Ext.form.field.Number', {
			fieldLabel: '排序',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'sort',
			value: 1,
			minValue: 1,
			allowDecimals: false,
			readOnly: this.readOnlyForm
		});

		this.add(Ext.create('Ext.form.Panel', {
			itemId: 'editForm',
			frame: true,
			autoHeight: true,
			autoScroll: true,
			buttonAlign: 'center',
			reader: Ext.create('Ext.data.JsonReader', {
				model: 'FormModel'
			}),
			layout: 'form',
			items: [{
				xtype: 'hidden',
				name: 'id'
			},
				usernameText,
				passwordText,
				emailText,
				freezeCheckbox,
				sortNumber
			],
			buttons:[{
				xtype: 'button',
				text: '保存',
				handler: this.saveForm,
				scope:this,
				hidden: this.readOnlyForm
			}, {
				xtype: 'button',
				text: '关闭',
				handler: this.closeForm,
				scope:this
			}]
		}));

		if (this.entityId) {
			this.getComponent('editForm').getForm().load({
				url: ctx + '/sysmanager/user/input.ctrl',
				params: {
					id: this.entityId
				},
				waitMsg: '正在载入数据...',
				success: function(form, action) {
				},
				failure: function() {
					Ext.Msg.alert('系统提示', '无法加载信息！');
				},
				scope: this
			});
		}
	},
	saveForm: function() {
		var form = this.getComponent('editForm').getForm();
		if (form.isValid()) {
			form.submit({
				url: ctx + '/sysmanager/user/save.ctrl',
				waitMsg: '正在保存数据，请稍候...',
				success: function(form, action) {
					Ext.Msg.alert('系统提示', '数据保存成功！');
					this.closeForm();
					myServer.getMainContent().getComponent('userGridPanel').queryData();
				},
				failure: function(form, action) {
					Ext.Msg.alert('系统提示', '无法保存数据！ 请检查用户名称是否重复。');
				},
				scope: this
			});
		}
	},
	closeForm: function() {
		this.close();
	}
});

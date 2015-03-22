Ext.define('Meaord.dishes.DishesFormPanel', {
	extend: 'Ext.form.Panel',

	restaurantId: null,
	restaurantName: null,
	itemId: 'dishesFormPanel',

	initComponent: function() {
		Ext.define('FormModel', {
			extend: 'Ext.data.Model',
			fields: [
				{name: 'id'},
				{name: 'name'},
				{name: 'description'},
				{name: 'type'},
				{name: 'price'},
				{name: 'sort'},
				{name: 'meaordRestaurantId'},
				{name: 'meaordRestaurantName'}
			]
		});

		var meaordRestaurantNameText = Ext.create('Ext.form.field.Display', {
			fieldLabel: '餐厅名称',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'meaordRestaurantName',
			value: this.restaurantName
		});
		var nameText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '<span style="color: #FF0000;">*</span>菜品名称',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'name',
			maxLength: 50,
			allowBlank: false
		});
		var descriptionTextarea = Ext.create('Ext.form.field.TextArea', {
			fieldLabel: '描述',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'description',
			maxLength: 100
		});
		var typeComboBox = Ext.create('Base.ux.TextComboBox', {
			fieldLabel: '<span style="color: #FF0000;">*</span>分类',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'type',
			allowBlank: false
		});
		var priceNumber = Ext.create('Ext.form.field.Number', {
			fieldLabel: '<span style="color: #FF0000;">*</span>价格',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'price',
			value: 1,
			minValue: 0.1,
			allowBlank: false
		});
		var sortNumber = Ext.create('Ext.form.field.Number', {
			fieldLabel: '排序',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'sort',
			value: 1,
			minValue: 1,
			allowDecimals: false
		});
		var photoFile = Ext.create('Ext.form.field.File', {
			fieldLabel: '菜品照片',
			labelAlign: 'right',
			labelSeparator: '：',
			buttonText: '选择文件',
			name: 'photo',
			listeners: {
				change: function(field, value, eOpts) {
					this.getComponent('photoContainer').update('<img src="' + value + '" border="0" width="328px" height="310px"/>');
				},
				scope: this
			}
		});
		var photoContainer = Ext.create('Ext.container.Container', {
			itemId: 'photoContainer'
		});

		Ext.apply(this, {
			autoScroll: true,
			buttonAlign: 'center',
			reader: Ext.create('Ext.data.JsonReader', {
				model: 'FormModel'
			}),
			layout: 'form',
			items: [{
				xtype: 'hidden',
				name: 'id'
			}, {
				xtype: 'hidden',
				name: 'meaordRestaurantId',
				value: this.restaurantId
			},
				meaordRestaurantNameText,
				nameText,
				descriptionTextarea,
				typeComboBox,
				priceNumber,
				sortNumber,
				photoFile,
				photoContainer
			],
			buttons:[{
				xtype: 'button',
				text: '新建',
				handler: this.clearForm,
				scope: this
			}, {
				xtype: 'button',
				text: '保存',
				handler: this.saveForm,
				scope: this
			}, {
				xtype: 'button',
				text: '删除',
				handler: this.deleteForm,
				scope: this,
				hidden: true
			}, {
				xtype: 'button',
				text: '关闭',
				handler: this.closeForm,
				scope:this
			}]
		});
		this.callParent();
	},
	loadForm: function(id) {
		var form = this.getForm();

		form.load({
			url: ctx + '/meaord/dishes/input.ctrl',
			params: {
				id: id
			},
			waitMsg: '正在载入数据...',
			success: function(form, action) {
				this.getComponent('photoContainer').update('<img src="' + ctx + '/sysmanager/attachment/view.ctrl?qc.entityName=org.kyll.myserver.business.meaord.entity.MeaordDishes&qc.entityId=' + id + '&r=' + Math.random() + '" border="0" width="328px" height="310px"/>');
				this.getDockedComponent(0).items.get(2).show();
			},
			failure: function() {
				Ext.Msg.alert('系统提示', '无法加载信息！');
			},
			scope: this
		});
	},
	clearForm: function() {
		var form = this.getForm();
		form.findField('id').setValue('');
		form.findField('name').setValue('');
		form.findField('description').setValue('');
		form.findField('type').setValue('');
		form.findField('price').setValue(1);
		form.findField('sort').setValue(1);
		form.findField('photo').setValue('');
		this.getComponent('photoContainer').update('');
		this.getDockedComponent(0).items.get(2).hide();
	},
	saveForm: function() {
		var form = this.getForm();
		if (form.isValid()) {
			form.submit({
				url: ctx + '/meaord/dishes/save.ctrl',
				waitMsg: '正在保存数据，请稍候...',
				success: function(form, action) {
					Ext.Msg.alert('系统提示', '数据保存成功！');
					this.ownerCt.getComponent('dishesTreePanel').loadTree(form.findField('type').getValue());
				},
				failure: function(form, action) {
					Ext.Msg.alert('系统提示', '无法保存数据！');
				},
				scope: this
			});
		}
	},
	deleteForm: function() {
		Ext.MessageBox.confirm("系统提示", "确定要删除数据吗？", function (btn) {
			if ('yes' == btn) {
				var form = this.getForm();
				Ext.Ajax.request({
					url: ctx + '/meaord/dishes/delete.ctrl',
					params: {
						ids: form.findField('id').getValue()
					},
					success: function(response, opts) {
						Ext.Msg.alert("系统提示", "数据删除成功！");
						this.ownerCt.getComponent('dishesTreePanel').loadTree(form.findField('type').getValue());
						this.clearForm();
					},
					failure: function(response, opts) {
						Ext.Msg.alert("系统提示", "数据删除失败！");
					},
					scope: this
				});
			}
		}, this);
	},
	closeForm: function() {
		this.ownerCt.close();
	}
});

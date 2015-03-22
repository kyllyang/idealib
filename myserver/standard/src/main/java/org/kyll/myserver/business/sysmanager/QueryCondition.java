package org.kyll.myserver.business.sysmanager;

/**
 * User: Kyll
 * Date: 2014-11-05 14:04
 */
public class QueryCondition {
	private Long id;
	private Long parentId;
	private String username;
	private String email;
	private String name;
	private Integer type;
	private Integer funcType;
	private String description;
	private String entityName;
	private String entityId;
	private Long dictId;
	private String invokeCode;

	public QueryCondition() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFuncType() {
		return funcType;
	}

	public void setFuncType(Integer funcType) {
		this.funcType = funcType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getInvokeCode() {
		return invokeCode;
	}

	public void setInvokeCode(String invokeCode) {
		this.invokeCode = invokeCode;
	}
}

package org.kyll.myserver.business.sysmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2014-12-02 13:24
 */
@Entity
@Table(name = "SYS_ATTACHMENT")
public class Attachment implements Serializable {
	private Long id;
	private String entityName;
	private String entityId;
	private String contentType;
	private String originalFilename;
	private String randomFilename;
	private String extensionName;
	private Long fileSize;

	public Attachment() {
	}

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "ID_")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ENTITY_NAME_")
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "ENTITY_ID_")
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	@Column(name = "CONTENT_TYPE_")
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(name = "ORIGINAL_FILENAME_")
	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	@Column(name = "RANDOM_FILENAME_")
	public String getRandomFilename() {
		return randomFilename;
	}

	public void setRandomFilename(String randomFilename) {
		this.randomFilename = randomFilename;
	}

	@Column(name = "EXTENSION_NAME_")
	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	@Column(name = "FILE_SIZE_")
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
}

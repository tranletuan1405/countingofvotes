package vn.edu.uit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Barcode")
public class Barcode extends AbstractEntity {
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

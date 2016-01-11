package vn.edu.uit.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Banner")
public class Banner extends AbstractEntity{

	@Column(name = "image_path", nullable = false)
	private String imagePath;
	
	@ManyToOne(cascade = CascadeType.ALL ,fetch =  FetchType.EAGER)
	@JoinColumn(name = "congress_id")
	private Congress congress;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Congress getCongress() {
		return congress;
	}

	public void setCongress(Congress congress) {
		this.congress = congress;
	}
}

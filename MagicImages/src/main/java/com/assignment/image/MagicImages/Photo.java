package com.assignment.image.MagicImages;

import java.io.Serializable;

public class Photo implements Serializable {
	// {"id":"12","createdAt":1524158544,"name":"name
	// 12","imageUrl":"https://unsplash.it/500?image=12"}
	private long id;
	private long createdAt;
	private String name;
	private String imageUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}

package com.example.taskthis;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	private String description;
	private Status status;
	private long id;

	public Task(String texto, long id) {
		this.id = id;
		this.description = texto;
		this.status = Status.TODO;
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTitle() {
		return description.length() <= 30 ? description : description
				.substring(0, 30).concat("...");
	}

	public void clone(Task other) {
		this.description = other.description;
		this.status = other.status;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof Task)) {
			return false;
		}

		Task other = (Task) obj;
		return this.id == other.id;
	}
}
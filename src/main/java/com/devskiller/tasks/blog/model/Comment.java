package com.devskiller.tasks.blog.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	private Long id;
	@Column(length = 4096)
	private String content;

	@Column(length = 4096)
	private String author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "post_id")
	private Post post;

	public Comment() {
	}



	@Column
	private LocalDateTime creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

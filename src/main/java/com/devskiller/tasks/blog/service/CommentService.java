package com.devskiller.tasks.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;

@Service
public class CommentService {
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public CommentService(final PostRepository postRepository,
						  final CommentRepository commentRepository) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {
		List<Comment> commentList = new ArrayList<>();
		//check if post exists:
		Optional<Post> postOptional = postRepository.findById(postId);
		if(postOptional.isPresent()){
			commentList = commentRepository.findAllByPostId(postId,Sort.by(Sort.Direction.DESC, "creationDate"));
			if(commentList.isEmpty()){
				throw new IllegalArgumentException();
			}
		}
		//mapping to dto
		return  commentList.stream().map(comment ->
			new CommentDto(comment.getId(),comment.getContent(),comment.getAuthor(),comment.getCreationDate())).
			collect(Collectors.toList());
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed postId
	 */
	public Long addComment(Long postId, NewCommentDto newCommentDto) {
		Optional<Post> postOptional = postRepository.findById(postId);
		Comment comment = new Comment();
		if(postOptional.isPresent()){
			comment.setContent(newCommentDto.content());
			comment.setAuthor(newCommentDto.author());
			comment.setPost(postOptional.get());
			commentRepository.save(comment);
		} else {
			throw new IllegalArgumentException();
		}
		return comment.getId();
	}
}

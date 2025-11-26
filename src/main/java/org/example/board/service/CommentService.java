package org.example.board.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.board.entity.Comment;
import org.example.board.entity.Post;
import org.example.board.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional
    public Comment createComment(Long postId, Comment comment) {
        Post post = postService.getPostById(postId);

        System.out.println("==== 댓글 추가전====");
        System.out.println("댓글 수 : " + post.getComments().size());

//        comment.setPost(post);
        post.addComment(comment);
        Comment saved = commentRepository.save(comment);

        System.out.println("==== 댓글 추가후 ====");
        System.out.println("댓글 수 : " + post.getComments().size());

        return saved;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow();

        // 고아 객체 삭제
//        Post post = comment.getPost();
//        post.removeComment(comment);

        commentRepository.delete(comment);
    }
}
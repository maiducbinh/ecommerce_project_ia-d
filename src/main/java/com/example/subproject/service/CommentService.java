package com.example.subproject.service;

import com.example.subproject.entity.Comment;
import com.example.subproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByItemId(Long itemId) {
        return commentRepository.findByItemId(itemId);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Additional methods if needed
}


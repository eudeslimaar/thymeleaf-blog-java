package com.eudeslima.blogbackend.service;

import com.eudeslima.blogbackend.model.Post;
import com.eudeslima.blogbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Page<Post> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return postRepository.findAll(pageable);
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> updatePost(Long id, Post postDetails) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            return postRepository.save(post);
        });
    }

    public Optional<Post> deletePost(Long id) {
        return postRepository.findById(id).map(post -> {
            postRepository.delete(post);
            return null;
        });
    }
}

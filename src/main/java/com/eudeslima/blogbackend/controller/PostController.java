package com.eudeslima.blogbackend.controller;

import com.eudeslima.blogbackend.model.Post;
import com.eudeslima.blogbackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public Page<Post> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return postService.getAllPosts(page, size);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(post -> ResponseEntity.ok().body(post))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestBody Post postDetails) {
        return postService.updatePost(id, postDetails)
                .map(updatedPost -> ResponseEntity.ok().body(updatedPost))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return postService.deletePost(id)
                .map(deletePost -> ResponseEntity.ok().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }
}

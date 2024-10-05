package com.eudeslima.blogbackend.controller;

import com.eudeslima.blogbackend.model.Post;
import com.eudeslima.blogbackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    PostService postService;

    @GetMapping("/posts")
    public String getAdminPosts(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {
        Page<Post> postsPage = postService.getAllPosts(page, size);
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        return "admin/posts";
    }

    @GetMapping("/posts/new")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        return "admin/create_post";
    }

    @PostMapping("/posts")
    public String savePost(@ModelAttribute("post") Post post) {
        postService.createPost(post);
        return "redirect:/admin/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("Post inválido: " + id));
        model.addAttribute("post", post);
        return "admin/edit_post";
    }

    @PostMapping("/posts/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute("post") Post postDetails) {
        postService.updatePost(id, postDetails);
        return "redirect:/admin/posts";
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/admin/posts";
    }
}

package com.marondal.marondalgram.post;

import com.marondal.marondalgram.post.domain.Post;
import com.marondal.marondalgram.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/timeline")
    public String timeline(Model model) {

        List<Post> postList = postService.getPostList();

        model.addAttribute("postList", postList);

        return "post/timeline";
    }
}

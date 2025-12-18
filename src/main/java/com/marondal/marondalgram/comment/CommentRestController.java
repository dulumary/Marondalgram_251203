package com.marondal.marondalgram.comment;

import com.marondal.marondalgram.comment.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("/post/comment/write")
    public Map<String, String> writeComment(
            @RequestParam long postId
            , @RequestParam String contents
            , HttpSession session) {

        long userId = (Long)session.getAttribute("userId");

        Map<String, String> resultMap = new HashMap<>();

        if(commentService.createComment(postId, userId, contents)) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }
}

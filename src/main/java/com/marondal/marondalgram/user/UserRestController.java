package com.marondal.marondalgram.user;

import com.marondal.marondalgram.user.repository.UserRepository;
import com.marondal.marondalgram.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join-process")
    public Map<String, String> join(
            @RequestParam String loginId
            , @RequestParam String password
            , @RequestParam String name
            , @RequestParam String email) {

        Map<String, String> resultMap = new HashMap<>();
        if(userService.createUser(loginId, password, name, email)) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

    @GetMapping("/duplicate-id")
    public Map<String, Boolean> isDuplicateId(@RequestParam String loginId) {

        Map<String, Boolean> resultMap = new HashMap<>();

        if(userService.isDuplicateId(loginId)) {
            resultMap.put("isDuplicate", true);
        } else {
            resultMap.put("isDuplicate", false);
        }

        return resultMap;
    }

}

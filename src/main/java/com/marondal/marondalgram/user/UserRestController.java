package com.marondal.marondalgram.user;

import com.marondal.marondalgram.common.dto.ApiResponse;
import com.marondal.marondalgram.user.domain.User;
import com.marondal.marondalgram.user.repository.UserRepository;
import com.marondal.marondalgram.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<Void>> join(
            @RequestParam
            @NotBlank (message="로그인 아이디는 필수 입니다.")
            @Size(min=4, max=20, message="아이디는 4자 이상 20자 미만이여야 합니다.")
            String loginId
            , @RequestParam String password
            , @RequestParam String name
            , @RequestParam
            @Email (message="이메일 규격이 잘못되었습니다")
            String email) {

        return ResponseEntity.ok(ApiResponse.success("회원가입 성공"));

    }

    @Operation(
            summary = "아이디 중복확인",
            description="회원 가입 과정에서 아이디 중복 여부를 확인"
    )
    @GetMapping("/duplicate-id")
    public ApiResponse<Boolean> isDuplicateId(
            @Parameter(description = "중복확인할 로그인아이디")
            @RequestParam String loginId) {

        return ApiResponse.success("중복확인 성공", true);
    }


    @PostMapping("/login-process")
    public ResponseEntity<ApiResponse<Void>> login(
            @RequestParam String loginId
            , @RequestParam String password
            , HttpSession session) {

        User user = userService.getUser(loginId, password);

        Map<String, String> resultMap = new HashMap<>();

        if(user != null) {

            session.setAttribute("userId", user.getId());
            session.setAttribute("userLoginId", user.getLoginId());
            return ResponseEntity.ok(ApiResponse.success("로그인 성공"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.fail("로그인 실패"));
        }

    }

}

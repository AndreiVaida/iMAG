package controller;

import dto.UserLoginRequestDto;
import dto.UserLoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;

import javax.naming.AuthenticationException;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody final UserLoginRequestDto userLoginRequestDto) throws AuthenticationException {
        final UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        return new ResponseEntity<>(userLoginResponseDto, OK);
    }
}

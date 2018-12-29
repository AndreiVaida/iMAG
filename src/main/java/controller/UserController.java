package controller;

import domain.User;
import dto.UserDto;
import dto.UserLoginRequestDto;
import dto.UserLoginResponseDto;
import dto.WishlistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;
import service.WishlistService;

import javax.naming.AuthenticationException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
    private final UserService userService;
    private final WishlistService wishlistService;

    @Autowired
    public UserController(UserService userService, WishlistService wishlistService) {
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final UserDto userDto) {
        final User user = userService.createUser(userDto);
        wishlistService.createWishlist(user.getId());
        return new ResponseEntity<>(null, CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody final UserLoginRequestDto userLoginRequestDto) throws AuthenticationException {
        final UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        return new ResponseEntity<>(userLoginResponseDto, OK);
    }

    @GetMapping("/wishlist")
    public ResponseEntity<WishlistDto> getWishList(@RequestHeader final Integer userId) {
        final WishlistDto wishlistDto = wishlistService.getWishlist(userId);
        return new ResponseEntity<>(wishlistDto, OK);
    }

    @PostMapping("/wishlist")
    public ResponseEntity<?> addWishlistProduct(@RequestHeader final Integer userId, @RequestBody final Integer productId) {
        wishlistService.addWishlistProduct(userId, productId);
        return new ResponseEntity<>(null, OK);
    }

    @DeleteMapping("/wishlist")
    public ResponseEntity<?> removeWishlistProduct(@RequestHeader final Integer userId, @RequestBody final Integer productId) {
        wishlistService.removeWishlistProduct(userId, productId);
        return new ResponseEntity<>(null, OK);
    }
}

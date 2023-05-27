package com.moviecollection.controllers;

import com.moviecollection.models.LoginRequest;
import com.moviecollection.models.User;
import com.moviecollection.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String handleUserRegistration(User user) {
        try{
            this.userService.createUser(user);
            return "redirect:login?status=REGISTER_SUCCESS";
        } catch (Exception exception){
            exception.printStackTrace();
            return "redirect:register?status=REGISTER_FAILED&message="+exception.getMessage();
        }
    }

    @GetMapping("login")
    public String displayLoginPage(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "message", required = false) String message,
            Model model
    ){
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "login";
    }

   @PostMapping("/login")
    public String handleLogin(LoginRequest loginRequest, HttpServletResponse response, HttpSession session) {
        try {
            User loggedInUser = this.userService.verifyUser(loginRequest.getEmail(), loginRequest.getPassword());
            if (loggedInUser == null) throw new RuntimeException("User not found");

            Cookie cookie = new Cookie("loggedInUserId", loggedInUser.getId().toString());
            cookie.setMaxAge(60*60*3); //time in seconds how long cookie will be saved in users browser
            response.addCookie(cookie);

            session.setAttribute("loggedIn", true);

            if (loggedInUser.getRole().equals("admin")) return "redirect:/movie-list";
            return "redirect:/";
        } catch (Exception exception) {
            return "redirect:login?status=LOGIN_FAILED&message=" + exception.getMessage();
        }
    }


    @GetMapping("/logout")
    public String logout(@CookieValue( value="loggedInUserId", defaultValue = "") String userId, HttpServletResponse response, HttpSession session){
        try{
            Cookie cookie = new Cookie("loggedInUserId", userId);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            session.setAttribute("loggedIn", false);

            return "redirect:login?status=LOGOUT_SUCCESS&message=You_logged_out_successfully";
        } catch (Exception exception) {
            return "redirect:login?status=LOGOUT_FAILED&message=" + exception.getMessage();
        }
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }
    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }
}

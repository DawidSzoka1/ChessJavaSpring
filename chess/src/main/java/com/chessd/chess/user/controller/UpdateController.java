package com.chessd.chess.user.controller;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.user.web.ChangePasswordUser;
import com.chessd.chess.user.web.UpdateUser;
import com.chessd.chess.webSocket.utils.SessionHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UpdateController {
    private final UserService userService;
    private final SessionHelper sessionHelper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String RETURN_TYPE = "error";
    private static final String UPDATE_PASSWORD_URL = "/users/update/password";

    @Autowired
    public UpdateController(UserService userService, SessionHelper sessionHelper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.sessionHelper = sessionHelper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private String validEmail(String email, User user) {
        if (email == null || email.trim().isEmpty()) {
            return "";
        }
        User emailTaken = userService.findByEmail(email);
        if (emailTaken != null && emailTaken.getId() != user.getId()) {
            return "Email jest zajęty!!";
        }
        return "";
    }

    private String validUpdateUser(UpdateUser updateUser, User user) {
        String email = updateUser.getEmail();
        String validResult = this.validEmail(email, user);
        if (!validResult.isEmpty()) {
            return validResult;
        }
        User userNameTaken = userService.findByUserName(updateUser.getUserName());
        if (userNameTaken != null && userNameTaken.getId() != user.getId()) {
            return "Pseudonim jest zajęty!!";
        }
        return "";
    }


    @GetMapping("/update")
    public String updateInfo(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        UpdateUser updateUser = UpdateUser.fromUser(user);

        model.addAttribute("user", updateUser)
                .addAttribute("profilePicture", user.getProfilePictureFilename());
        return "users/update";
    }

    @PostMapping("/update")
    public RedirectView updateProcess(
            @Valid @ModelAttribute("user") UpdateUser updateUser,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute(RETURN_TYPE, errorMessages);
            return new RedirectView("/users/update");
        }
        User user = sessionHelper.getUserFromPrincipal(session);
        String check = this.validUpdateUser(updateUser, user);

        if (!check.isEmpty()) {
            redirectAttributes.addFlashAttribute(RETURN_TYPE, check);
            return new RedirectView("/users/update");
        }
        if (!updateUser.getUserName().equals(user.getUserName())) {
            sessionHelper.updateUserInSession(updateUser, session);
        }
        userService.update(user.getId(), updateUser);

        redirectAttributes.addFlashAttribute("success", "Dane zostały zmienione pomyślnie");
        return new RedirectView("/users/profile/" + user.getUserName());
    }

    @GetMapping("/update/password")
    public String updatePassword(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        ChangePasswordUser updateUser = new ChangePasswordUser();
        model.addAttribute("user", updateUser)
                .addAttribute("userName", user.getUserName());
        return "users/changePassword";
    }

    @PostMapping("/update/password")
    public RedirectView updatePasswordProcess(
            @Valid @ModelAttribute("user") ChangePasswordUser updateUser,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute(RETURN_TYPE, errorMessages);
            return new RedirectView(UPDATE_PASSWORD_URL);
        }
        User user = sessionHelper.getUserFromPrincipal(session);
        if(!bCryptPasswordEncoder.matches(updateUser.getCurrentPassword(), user.getPassword())){
            redirectAttributes.addFlashAttribute(RETURN_TYPE, "Obecne hasło jest inne niż podane!");
            return new RedirectView(UPDATE_PASSWORD_URL);
        }
        if(!updateUser.getNewPassword().equals(updateUser.getNewPasswordRepeat())){
            redirectAttributes.addFlashAttribute(RETURN_TYPE,
                    "Hasła nie są takie same!");
            return new RedirectView(UPDATE_PASSWORD_URL);
        }
        user.setPassword(bCryptPasswordEncoder.encode(updateUser.getNewPassword()));
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "Hasło zmieniono pomyślnie");
        return new RedirectView("/users/profile/" + user.getUserName());
    }
}

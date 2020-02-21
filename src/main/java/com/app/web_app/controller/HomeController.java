package com.app.web_app.controller;

import com.app.web_app.model.dto.UserDto;
import com.app.web_app.model.dto.VerificationTokenDto;
import com.app.web_app.model.user.PasswordDto;
import com.app.web_app.model.user.User;
import com.app.web_app.model.user.enums.Authority;
import com.app.web_app.service.*;
import com.app.web_app.utils.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
@Slf4j
@SessionAttributes(names = {"loggedUsers"})
@EnableAsync
@RequiredArgsConstructor
public class HomeController {

    private final ControllerUtil controllerUtil;
    private final UserService userService;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final LoggedUsersService loggedUsersService;

    @RequestMapping(value = {"/", "/home"})
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/userPage", method = {RequestMethod.POST, RequestMethod.GET})
    public String getUserPage(Model model, HttpSession session, @AuthenticationPrincipal org.springframework.security.core.userdetails.User activeUser) {

        String photoUrlForUsername = userService.getPhotoUrlForUsername(activeUser.getUsername());
        session.setAttribute("userPhoto", photoUrlForUsername);

        model.addAttribute("loggedUsers", loggedUsersService.getLoggedUsers());

        return "user_page";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register_form";
    }

    @PostMapping("/register")
    public String postRegister(Model model, @Valid UserDto
            userDto, BindingResult bindingResult, HttpServletRequest request) {

        model.addAttribute("errors", controllerUtil.bindErrorsHibernateFields(bindingResult));

        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            model.addAttribute("usernameTaken", "There is already a user registered with the username provided.");
        }

        userService.findByEmail(userDto.getEmail())
                .ifPresentOrElse(
                        (userFromDb) -> {
                            model.addAttribute("emailTaken", "There is already a user registered with the email provided.");
                            bindingResult.reject("emailTaken");
                        },
                        () -> {

                            if (!bindingResult.hasErrors() && userService.findByUsername(userDto.getUsername()).isEmpty()) {
                                userDto.setEnabled(false);

                                if (userDto.getFile().getSize() == 0) {
                                    File maleImage = new File("src/main/resources/static/images/male.jpg");
                                    File femaleImage = new File("src/main/resources/static/images/female.jpg");

                                    userDto.setFile(switch (userDto.getGender()) {

                                        case MALE -> controllerUtil.saveFile(maleImage);
                                        case FEMALE -> controllerUtil.saveFile(femaleImage);

                                    });
                                }

                                userDto.setAuthorities(new HashSet<>(Set.of(Authority.ROLE_USER)));

                                String token = UUID.randomUUID().toString();
                                VerificationTokenDto verificationToken = VerificationTokenDto.builder().token(token).userDto(userDto).build();
                                userService.save(userDto);
                                verificationTokenService.saveOrUpdateToken(verificationToken);
                                model.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + userDto.getEmail());

                                String mailMessage = String.format(EmailService.REGISTRATION_MESSAGE, request.getScheme(), request.getServerName(), request.getServerPort(), verificationTokenService.getTokenForUser(userDto.getUsername()).orElse(null));

                                emailService.sendEmail(EmailType.REGISTRATION, userDto.getEmail(), mailMessage, null);
                            }
                        }
                );
        return "register_form";
    }

    @RequestMapping(value = "/login")
    public String login(HttpSession session) {

        session.setAttribute("loggedUsers2", loggedUsersService.getLoggedUsers());
        return "login_page";

    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession httpSession) {

        httpSession.invalidate();
        return "login_page";
    }

    @GetMapping(value = "/confirm")
    public String showConfirmationPage(Model model, @RequestParam String token) {

        verificationTokenService.getUserAssociatedWithToken(token)
                .ifPresentOrElse((userFromDB) -> {
                            model.addAttribute("password", new PasswordDto())
                                    .addAttribute("token", token)
                                    .addAttribute("userName", userFromDB.getUsername());
                            if (userFromDB.getEnabled()) {
                                model.addAttribute("activatedAlready", true);
                            }
                            if (verificationTokenService.getExpirationDateForToken(token).compareTo(LocalDateTime.now()) < 0) {
                                model.addAttribute("tokenExpired", true);
                            }
                        }
                        , () -> model.addAttribute("invalidToken", "This is an invalid confirmation link"));

        return "confirm";
    }

    @PostMapping(value = "/confirm")
    public String processConfirmationForm(Model model, @RequestParam String token, @ModelAttribute PasswordDto
            password) {


        verificationTokenService.getUserAssociatedWithToken(token).ifPresent(userFromDb -> {

                    model.addAttribute("password", new PasswordDto())
                            .addAttribute("token", token)
                            .addAttribute("userName", userFromDb.getUsername());

                    if (!password.getPassword().equals(password.getConfirmationPassword())) {
                        model.addAttribute("NotSamePasswords", "passwords must be the same");

                    } else if (userService.doPasswordMatches(password.getPassword(), userFromDb.getPassword())) {
                        userService.update(userFromDb.getUsername(), User.builder().enabled(true).build());
                        model.addAttribute("successMessage", "Your account has been activated!")
                                .addAttribute("activatedAlready", true);

                    } else {
                        model.addAttribute("passwordError", "Provided password is not correct");
                    }
                }
        );
        return "confirm";
    }

    @GetMapping("/accessDenied")
    public String showAccessDenied(@RequestHeader(name = "REQUESTED-URL", required = false) String header, Model model) {
        model.addAttribute("headerInfo", header);
        return "access_denied";
    }


    @PostMapping("/changePassword")
    public String changePasswordPost(@Valid PasswordDto password, BindingResult bindingResult, Model model, @RequestParam String token) {

        var errors = controllerUtil.bindErrorsHibernateType(bindingResult);
        model.addAttribute("errors", errors);

        verificationTokenService.getUserAssociatedWithToken(token).ifPresentOrElse(userFromDb -> {

                    model.addAttribute("password", new PasswordDto())
                            .addAttribute("token", token)
                            .addAttribute("userName", userFromDb.getUsername());

                    if (!password.getPassword().equals(password.getConfirmationPassword())) {
                        model.addAttribute("NotSamePasswords", "passwords must be the same");

                    } else if (errors.isEmpty()) {
                        userService.changePassword(userFromDb.getUsername(), password.getPassword());
                        model.addAttribute("successMessage", "Your password has been changed!");
                    }
                },
                () -> model.addAttribute("badToken", "Invalid token. Please provide a valid one")
        );

        return "change_password";
    }

    @GetMapping("/changePassword")
    public String changePasswordGet(Model model, @RequestParam String token) {

        verificationTokenService.getUserAssociatedWithToken(token)
                .ifPresentOrElse(user -> {
                            model.addAttribute("password", new PasswordDto())
                                    .addAttribute("token", token)
                                    .addAttribute("userName", user.getUsername());
                        }
                        ,
                        () -> model.addAttribute("invalid link", "this is an invalid link")

                );

        return "change_password";
    }

    @GetMapping("/forgotPassword")
    public String changePassword(Model model) {
        model.addAttribute("email", "");
        return "forgot";
    }

    @PostMapping("/sendLinkToChangePassword")
    public String sendLinkToChangePassword(HttpServletRequest request, Model model, @RequestParam String email) {

        model.addAttribute("email", email);

        userService.getUserDtoByEmail(email)
                .ifPresentOrElse((user) -> {
                            model.addAttribute("confirmationMessage", "Link has been sent to " + email);
                            String token = UUID.randomUUID().toString();
                            VerificationTokenDto verificationToken = VerificationTokenDto.builder().token(token).userDto(user).build();
                            verificationTokenService.saveOrUpdateToken(verificationToken);

                            String mailMessage = String.format(EmailService.CHANGE_PASSWORD_MESSAGE, request.getScheme(), request.getServerName(), request.getServerPort(), token);


                            emailService.sendEmail(EmailType.REQUEST_ADMIN, emailService.ADMIN_EMAIL, mailMessage, user.getEmail());

                        },
                        () -> model.addAttribute("wrongEmail", "Email " + email + " doesn't exist"));

        return "forgot";
    }
}


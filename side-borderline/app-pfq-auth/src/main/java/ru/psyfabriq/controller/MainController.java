package ru.psyfabriq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.psyfabriq.AppAccountForm;
import ru.psyfabriq.utils.WebUtils;

import java.security.Principal;

@Controller
public class MainController {

    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final UsersConnectionRepository connectionRepository;

    @Autowired
    public MainController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("User Name: " + userName);
        UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "adminPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("User Name: " + userName);
        UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("userInfo", userInfo);
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }
        return "403Page";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model) {
        return "loginPage";
    }

    @RequestMapping(value = {"/signin"}, method = RequestMethod.GET)
    public String signInPage(Model model) {
        return "redirect:/login";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String signupPage(WebRequest request, Model model) {
        ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        AppAccountForm myForm = null;
        if (connection != null) {
            myForm = new AppAccountForm(connection);
        } else {
            myForm = new AppAccountForm();
        }
        model.addAttribute("myForm", myForm);
        return "signupPage";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public String signupSave(WebRequest request, //
                             Model model, //
                             @ModelAttribute("myForm") @Validated AppAccountForm appForm, //
                             BindingResult result, //
                             final RedirectAttributes redirectAttributes) {
/**
 // Validation error.
 if (result.hasErrors()) {
 return "signupPage";
 }

 List<String> roleNames = new ArrayList<>();
 roleNames.add(Role.ROLE_USER);

 Account registered = null;

 try {
 registered = appUserDAO.registerNewUserAccount(appForm, roleNames);
 } catch (Exception ex) {
 ex.printStackTrace();
 model.addAttribute("errorMessage", "Error " + ex.getMessage());
 return "signupPage";
 }

 if (appForm.getSignInProvider() != null) {
 ProviderSignInUtils providerSignInUtils //
 = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
 providerSignInUtils.doPostSignUp(registered.getUsername(), request);
 }

 SecurityUtil.logInUser(registered, roleNames);
 */
        return "redirect:/userInfo";
    }

}

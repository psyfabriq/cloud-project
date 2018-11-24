package ru.psyfabriq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import ru.psyfabriq.entity.Account;
import ru.psyfabriq.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

//import org.springframework.social.connect.web.ProviderSignInUtils;

@Controller
public class MainController {

    private final LoadBalancerClient loadBalancerClient;

    //private final ConnectionFactoryLocator connectionFactoryLocator;
    // private final UsersConnectionRepository connectionRepository;
    private final AccountService accountService;

    /*
        @Autowired
        public MainController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository, AccountService accountService) {
            this.connectionFactoryLocator = connectionFactoryLocator;
            this.connectionRepository = connectionRepository;
            this.accountService = accountService;
        }
    */
    @Autowired
    public MainController(LoadBalancerClient loadBalancerClient, AccountService accountService) {
        this.loadBalancerClient = loadBalancerClient;
        this.accountService = accountService;
    }

    @RequestMapping(value = {"/exit"}, method = RequestMethod.GET)
    public String exit(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        return "redirect:/";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String signupPage(WebRequest request, Model model) {
        //   ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
        //  Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        AppAccountForm myForm = null;
            /*
            if (connection != null) {
                myForm = new AppAccountForm(connection);
            } else {
                */

        myForm = new AppAccountForm();

        //  }
        model.addAttribute("myForm", myForm);
        return "signupPage";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public String signupSave(WebRequest request,
                             Model model,
                             @ModelAttribute("myForm") @Validated AppAccountForm appForm,
                             BindingResult result,
                             final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "signupPage";
        }
        Account account = accountService.convertDtoToEntity(appForm).get();
        accountService.add(account);
/*
        if (appForm.getSignInProvider() != null) {
            ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
            providerSignInUtils.doPostSignUp(account.getUsername(), request);
        }


        List<String> strings = account.getRoles().stream()
                .map(object -> Objects.toString(object.getName(), null))
                .collect(Collectors.toList());
        SecurityUtil.logInUser(account, strings);
 */
        return "redirect:/";
    }

}

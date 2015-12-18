package com.nikita.pischik.orderlunch.controller;

import com.nikita.pischik.orderlunch.model.Deposit;
import com.nikita.pischik.orderlunch.model.User;
import com.nikita.pischik.orderlunch.model.UserRole;
import com.nikita.pischik.orderlunch.model.UserRoleType;
import com.nikita.pischik.orderlunch.service.DepositService;
import com.nikita.pischik.orderlunch.service.UserRoleService;
import com.nikita.pischik.orderlunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    DepositService depositService;
    @Autowired
    MessageSource messageSource;


    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        model.addAttribute("greeting", "Hi, Welcome to Lunch Order site");
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();

        } else {
            userName = principal.toString();
        }
        return userName;
    }

    private boolean isUserAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            boolean isAdmin = ((UserDetails)principal).getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return isAdmin;
        }
        return false;

    }

    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        //if (isUserAdmin()) {
            User user = new User();
            model.addAttribute("user", user);
            model.addAttribute("edit", false);
            return "registration";
        //} else {
        //    return "redirect:Access_Denied";
       // }
    }

    @RequestMapping(value = { "/deposits" }, method = RequestMethod.GET)
    public String depositsPage(ModelMap model) {
        List<Deposit> deposits = depositService.findAllDeposits();
        model.addAttribute("deposits", deposits);
        List<User> usersForDeposit = new ArrayList<User>();
        for (Deposit deposit : deposits) {
            usersForDeposit.add(deposit.getUser());
        }
        model.addAttribute("users", usersForDeposit);
        return "deposits";
    }


    @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }


        if(!userService.isUserLoginUnique(user.getId(), user.getLogin())){
            FieldError ssoError =new FieldError("user","login",messageSource.getMessage("non.unique.login", new
                    String[]{user.getLogin()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }

        Set<UserRole> userRoles = new HashSet<UserRole>();
        UserRole userRole = userRoleService.findByType(UserRoleType.USER.getUserRoleType());
        userRoles.add(userRole);
        user.setUserRoles(userRoles);
        Deposit deposit = new Deposit();
        deposit.setInvoice(0);
        deposit.setTomorrow_cost(0);
        deposit.setResidue(0);
        deposit.setUser(user);
        user.setDeposit(deposit);
        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getName() + " registered " +
                "successfully");
                //return "success";
        return "registrationsuccess";
    }

    @RequestMapping(value = { "/edit-user-{login}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String login, ModelMap model) {
        User user = userService.findByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }


    @RequestMapping(value = { "/edit-user-{login}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String login) {

        if (result.hasErrors()) {
            return "registration";
        }

        if(!userService.isUserLoginUnique(user.getId(), user.getLogin())){
            FieldError ssoError =new FieldError("user","login",
                    messageSource.getMessage("non.unique.login",
                            new String[]{user.getLogin()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }

        userService.updateUser(user);

        model.addAttribute("success", "User " + user.getName() + " updated successfully");
        return "registrationsuccess";
    }

    @RequestMapping(value = { "/delete-user-{login}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String login) {
        userService.deleteUserByLogin(login);
        return "redirect:/home";
    }

    @RequestMapping(value = { "/userslist" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }
}

package com.nikita.pischik.orderlunch.controller;

import com.google.gson.Gson;
import com.nikita.pischik.orderlunch.configuration.MailConfiguration;
import com.nikita.pischik.orderlunch.model.*;
import com.nikita.pischik.orderlunch.model.MenuItem;
import com.nikita.pischik.orderlunch.notification.NotificationManager;
import com.nikita.pischik.orderlunch.notification.SimpleNotificationManager;
import com.nikita.pischik.orderlunch.service.*;
import com.nikita.pischik.orderlunch.utils.MenuDownloader;
import com.nikita.pischik.orderlunch.utils.MenuViewModel;
import com.nikita.pischik.orderlunch.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    DepositService depositService;
    @Autowired
    MenuService menuService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderListService orderListService;


    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String homePage(ModelMap model) throws Exception {
        MenuDownloader menuDownloader = new MenuDownloader();
        MenuDownloader.MenuObject menu =
                menuDownloader.fromJSONParser("http://www.cafebaluk.by/Orders/GetTodayDishes?day=1");
        List<MenuDownloader.ItemMenu> menuList = menu.getDishes();
        List<Integer> dishesInCategory = new ArrayList<Integer>();
        for (int i=0; i<20; i++) {
            dishesInCategory.add(0);
        }

        List<MenuItem> newMenu = new ArrayList<MenuItem>();
        for (int i=0; i<menuList.size(); i++) {
            MenuItem menuItem = new MenuItem();
            int q = dishesInCategory.get(menuList.get(i).getCategoryId()) + 1;
            dishesInCategory.set(menuList.get(i).getCategoryId(), q);
            menuItem.setIn_category_id(dishesInCategory.get(menuList.get(i).getCategoryId()));
            menuItem.setCategory(menuList.get(i).getCategory().getName());
            menuItem.setCost(Integer.toString(menuList.get(i).getPrice()));
            menuItem.setDescription(menuList.get(i).getDescription());
            menuItem.setTitle(menuList.get(i).getName());
            menuItem.setWeight(menuList.get(i).getWeight());
            menuItem.setCategory_id(Integer.toString(menuList.get(i).getCategoryId()));
            menuItem.setImage("http://www.cafebaluk.by" + menuList.get(i).getDishImage());
            menuItem.setDish_id(menuList.get(i).getDishId());
            newMenu.add(menuItem);
        }

        List<MenuItem> menuItemList = menuService.findAllMenu();
        if (Utils.updateMenu(menuItemList, newMenu)) {
            menuService.deleteAllMenu();
            for (int i=0; i<newMenu.size(); i++) {
                menuService.saveMenu(newMenu.get(i));
            }
        }
        List<MenuItem> menuFromDB = menuService.findAllMenu();

        List<MenuViewModel> menuToView = Utils.formingMenuListView(menuFromDB);
        model.addAttribute("menu", menuToView);
        return "main";
    }

    /*@RequestMapping(value ={ "/update-deposit-{id}" }, method = RequestMethod.GET)
    public void depositUpdate(@RequestParam Integer value,
                                @PathVariable Integer id) {
        Deposit deposit1 = depositService.findById(id);
        deposit1.setInvoice(deposit1.getInvoice() + value);
        //depositService.saveDeposit(deposit);
        depositService.updateDeposit(deposit1);

    }*/

    @RequestMapping(value ={ "/update-deposit-{id}" }, method = RequestMethod.POST)
    public @ResponseBody String depositUpdate(@RequestParam("value") Integer value,
                              @PathVariable Integer id) {
        Deposit deposit1 = depositService.findById(id);
        deposit1.setInvoice(deposit1.getInvoice() + value);
        depositService.updateDeposit(deposit1);
        return "abacaba";
    }

    @RequestMapping(value = {"/save-order"}, method = RequestMethod.POST)
    public @ResponseBody String saveOrder(@RequestBody String data) {
        Gson gson = new Gson();
        Utils.orderFromBasket dishes = gson.fromJson(data, Utils.orderFromBasket.class);
        String login = getPrincipal();
        List<Integer> dishesList = dishes.getDishes();
        List<Integer> countsList = dishes.getCounts();
        OrderModel orderModel = new OrderModel();
        OrderList orderList = new OrderList();
        orderModel.setIs_send(false);
        orderModel.setUser(userService.findByLogin(login));
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        Set<OrderItem> orderItemSet = new HashSet<OrderItem>();
        int summ_cost = 0;
        for (int i=0; i<dishesList.size(); i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCount(countsList.get(i));
            orderItem.setDish_id(dishesList.get(i));
            MenuItem menuItem = menuService.findByDishId(dishesList.get(i));
            orderItem.setDishName(menuItem.getTitle());
            orderItem.setCost(Integer.parseInt(menuItem.getCost()));
            orderItem.setOrderList(orderList);
            orderItems.add(orderItem);
            orderItemSet.add(orderItem);
            summ_cost = summ_cost + Integer.parseInt(menuItem.getCost()) * countsList.get(i);
        }
        orderList.setCost(summ_cost);
        orderList.setOrderItems(orderItemSet);
        orderModel.setOrderList(orderList);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        orderModel.setDate(dateFormat.format(date));
        orderService.save(orderModel);
        for (int i=0; i<orderItems.size(); i++) {
            orderItemService.save(orderItems.get(i));
        }
        return "redirect:/userorderlist";
    }

    @RequestMapping(value = "/userorderlist", method = RequestMethod.GET)
    public String userOrderList(ModelMap model) {
        String login = getPrincipal();
        List<OrderModel> list = orderService.findAllOrders();
        Set<OrderModel> orderModelList = new HashSet<OrderModel>();
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).getUser().getLogin().equals(login)) {
                orderModelList.add(list.get(i));
            }
        }
        model.addAttribute("orders", orderModelList);
        return "userorderlist";
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
        if (isUserAdmin()) {
            User user = new User();
            model.addAttribute("user", user);
            model.addAttribute("edit", false);
            return "registration";
        } else {
            return "redirect:Access_Denied";
        }
    }

    @RequestMapping(value = { "/deposits" }, method = RequestMethod.GET)
    public String depositsPage(ModelMap model) {
        List<Deposit> deposits = depositService.findAllDeposits();
        List<User> usersForDeposit = new ArrayList<User>();
        for (Deposit deposit : deposits) {
            usersForDeposit.add(deposit.getUser());
        }
        List<DepositViewModel> depositViewModels = new ArrayList<DepositViewModel>();
        for (int i=0; i<deposits.size(); i++) {
            depositViewModels.add(new DepositViewModel(usersForDeposit.get(i), deposits.get(i)));
        }
        model.addAttribute("deposits", depositViewModels);
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
        SimpleNotificationManager simpleNotificationManager = new SimpleNotificationManager();

        simpleNotificationManager.placeOrder(user);
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

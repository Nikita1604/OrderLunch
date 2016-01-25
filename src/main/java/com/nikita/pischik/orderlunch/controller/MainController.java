package com.nikita.pischik.orderlunch.controller;

import com.google.gson.Gson;
import com.nikita.pischik.orderlunch.configuration.MailConfiguration;
import com.nikita.pischik.orderlunch.model.*;
import com.nikita.pischik.orderlunch.model.MenuItem;
import com.nikita.pischik.orderlunch.notification.NotificationManager;
import com.nikita.pischik.orderlunch.notification.SimpleNotificationManager;
import com.nikita.pischik.orderlunch.service.*;
import com.nikita.pischik.orderlunch.utils.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
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


    @RequestMapping(value ={ "/update-deposit-{id}" }, method = RequestMethod.POST)
    public @ResponseBody String depositUpdate(@RequestParam("value") Integer value,
                              @PathVariable Integer id) {
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
        Deposit deposit1 = depositService.findById(id);
        deposit1.setInvoice(deposit1.getInvoice() + value);
        depositService.updateDeposit(deposit1);
        return "abacaba";
    }

    @RequestMapping(value = {"/save-order"}, method = RequestMethod.POST)
    public @ResponseBody String saveOrder(@RequestBody String data) {
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        /*if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }*/
        Gson gson = new Gson();
        Utils.orderFromBasket dishes = gson.fromJson(data, Utils.orderFromBasket.class);
        String login = getPrincipal();
        List<Integer> dishesList = dishes.getDishes();
        List<Integer> countsList = dishes.getCounts();
        OrderModel orderModel = new OrderModel();
        OrderList orderList = new OrderList();
        orderModel.setIs_send(false);
        User user = userService.findByLogin(login);
        orderModel.setUser(user);
        int deposit = user.getDeposit().getInvoice();
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
        deposit -= summ_cost;
        Deposit deposit1 = user.getDeposit();
        deposit1.setInvoice(deposit);
        deposit1.setTomorrow_cost(summ_cost);
        deposit1.setResidue(deposit);
        depositService.updateDeposit(deposit1);
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

    @RequestMapping(value = "/orderhistory", method = RequestMethod.GET)
    public String userOrderList(ModelMap model) {
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
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

    /*@RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {

        model.addAttribute("user", getPrincipal());
        return "admin";
    }*/

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String orders(ModelMap model) {
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
        List<OrderModel> list = orderService.findAllOrders();
        Set<OrderModel> orderModelList = new HashSet<OrderModel>();
        for (int i=0; i<list.size(); i++) {
            if (!list.get(i).is_send()) {
                orderModelList.add(list.get(i));
            }
        }
        model.addAttribute("orders", orderModelList);
        ManagerModel manager = new ManagerModel();
        model.addAttribute("manager", manager);
        return "orders";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public String acceptedOrders(@Valid @ModelAttribute("manager")ManagerModel manager,
                                 BindingResult result, ModelMap model) {
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
        if (result.hasErrors()) {
            return "redirect:/orders";
        }
        CafeOrderEntity orderEntity = new CafeOrderEntity();
        orderEntity.setOrderId(0);
        orderEntity.setAdminName(manager.getName());
        orderEntity.setAddress(manager.getAddress());
        orderEntity.setCustomerName(manager.getCompany());
        orderEntity.setPhone(manager.getPhone());
        orderEntity.setEmail(manager.getEmail());
        orderEntity.setNote("");
        orderEntity.setDay(1);
        List<CafeOrderEntity.GuyOrders> guyOrdersList = new ArrayList<CafeOrderEntity.GuyOrders>();
        List<OrderModel> list = new ArrayList<OrderModel>();
        List<OrderModel> listOrders = orderService.findAllOrders();
        for (int i=0; i<listOrders.size(); i++) {
            if (!listOrders.get(i).is_send()) {
                //listOrders.get(i).setIs_send(true);
                //orderService.update(listOrders.get(i));
                list.add(listOrders.get(i));
            }
        }
        List<User> users = userService.findAllUsers();
        for (int i=0; i<users.size(); i++) {
            List<OrderModel> currentUserOrders = new ArrayList<OrderModel>();
            for (int j=0; j<list.size(); j++) {
                if (list.get(j).getUser().getLogin().equals(users.get(i).getLogin())) {
                    /*if (!list.get(j).is_send()) {*/
                        currentUserOrders.add(list.get(j));
                    /*}*/
                }
            }
            if (currentUserOrders.size() > 0) {
                CafeOrderEntity.GuyOrders guyOrders = new CafeOrderEntity.GuyOrders();
                guyOrders.setGuyOrderId(0);
                guyOrders.setName(users.get(i).getName());
                guyOrders.setOrderId(guyOrdersList.size());
                StringBuilder dishes = new StringBuilder();
                StringBuilder counts = new StringBuilder();
                for (int j=0; j<currentUserOrders.size(); j++) {
                    List<OrderItem> orderItems = new ArrayList<OrderItem>();
                    orderItems.addAll(currentUserOrders.get(j).getOrderList().getOrderItems());
                    for (int q=0; q<orderItems.size()-1; q++) {
                        dishes.append(orderItems.get(q).getDish_id());
                        dishes.append('|');
                        counts.append(orderItems.get(q).getCount());
                        counts.append('|');
                    }
                    dishes.append(orderItems.get(orderItems.size()-1).getDish_id());
                    counts.append(orderItems.get(orderItems.size()-1).getCount());
                    if (j<currentUserOrders.size()-1) {
                        dishes.append('|');
                        counts.append('|');
                    }
                    //notSentOrders.add(currentUserOrders.get(j));
                }
                guyOrders.setDishes(dishes.toString());
                guyOrders.setCounts(counts.toString());
                guyOrdersList.add(guyOrders);
            }
            currentUserOrders = null;
        }
        orderEntity.setGuysOrders(guyOrdersList);
        Gson gson = new Gson();
        String data = gson.toJson(orderEntity);
        String postUrl = "http://www.cafebaluk.by/Orders/SubmitOrder";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(postUrl);
        try {
            StringEntity postingString = new StringEntity(data);
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/orderaccepted";
    }

    @RequestMapping(value = "/orderaccepted", method = RequestMethod.GET)
    public String orderAccepted(ModelMap model) {
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
        model.addAttribute("success", "Заказы успешно отправлены в кафе!");
        List<OrderModel> orderModelList = orderService.findNotSentOrders();
        for (int i=0; i<orderModelList.size(); i++) {
            orderModelList.get(i).setIs_send(true);
            orderService.update(orderModelList.get(i));
        }
        return "ordersaccepted";
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
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
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
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
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
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
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
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
        User user = userService.findByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }


    @RequestMapping(value = { "/edit-user-{login}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String login) {
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
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
        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
        User user = userService.findByLogin(login);
        List<OrderModel> orderModelList = orderService.findAllOrders();
        for (int i=0; i<orderModelList.size(); i++) {
            if (orderModelList.get(i).getUser().getLogin().equals(user.getLogin())) {
                orderListService.delete(orderModelList.get(i).getOrderList());
                orderService.delete(orderModelList.get(i));
            }
        }
        userService.deleteUserByLogin(login);
        return "redirect:/home";
    }

    @RequestMapping(value = { "/userslist" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {

        if (getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (!isUserAdmin()) {
            return "redirect:/Access_Denied";
        }
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }
}

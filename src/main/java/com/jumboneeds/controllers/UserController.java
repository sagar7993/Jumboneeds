package com.jumboneeds.controllers;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.User;
import com.jumboneeds.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody User findById(@RequestBody IdBean idBean){
        return userService.findById(idBean.getId());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody LoginResultBean login(@RequestBody LoginBean loginResult){
        return userService.login(loginResult);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody LoginResultBean signUp(@RequestBody User user){
        return userService.signUp(user);
    }

    @RequestMapping(value = "/updateBuildingAndFlat", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean updateBuildingAndFlat(@RequestBody UpdateUserBuildingBean updateUserBuildingBean){
        return userService.updateBuildingAndFlat(updateUserBuildingBean);
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean updateProfile(@RequestBody UpdateProfileBean updateProfileBean){
        return userService.updateProfile(updateProfileBean);
    }

    @RequestMapping(value = "/updateUserDetails", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean updateUserDetails(@RequestBody User user){
        return userService.updateUserDetails(user);
    }

    @RequestMapping(value = "updateNotificationToken", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean updateNotificationToken(@RequestBody NotificationTokenBean notificationTokenBean) {
        return userService.updateNotificationToken(notificationTokenBean);
    }

    @RequestMapping(value = "/findUserForRefund", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<RefundUserBean> findUserForRefund(@RequestBody User user){
        return userService.findUserForRefund(user);
    }

    @RequestMapping(value = "/findUsersWithBalanceBelowMinimumBuildingAmount", method = RequestMethod.GET)
    public @ResponseBody List<User> findUsersWithBalanceBelowMinimumBuildingAmount(){
        return userService.findUsersWithBalanceBelowMinimumBuildingAmount();
    }

}
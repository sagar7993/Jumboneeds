package com.jumboneeds.controllers;

import com.jumboneeds.beans.*;
import com.jumboneeds.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionLogService transactionLogService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/contactUs", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean contactUs(@RequestBody ContactUsBean contactUsBean){
        return adminService.contactUs(contactUsBean);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody AdminLoginStatusBean login(@RequestBody LoginBean loginBean){
        return adminService.login(loginBean);
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StoreMaster fetchStore(@RequestBody PartnerLogin partnerLogin){
        return storeService.fetchStore(partnerLogin, false, false);
    }

    @RequestMapping(value = "/analytics", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody AnalyticsMaster fetchAnalytics(@RequestBody PartnerLogin partnerLogin){
        return analyticsService.fetchAnalytics(partnerLogin, false);
    }

    @RequestMapping(value = "/inventory", method = RequestMethod.POST, consumes = "application/json")
    private @ResponseBody InventoryMaster inventory(@RequestBody NoAuthLogin noAuthLogin){
        return inventoryService.fetchInventory(noAuthLogin);
    }

    @RequestMapping(value = "/transactionLogs", method = RequestMethod.POST, consumes = "application/json")
    private @ResponseBody TransactionLogMaster fetchTransactionLogs(@RequestBody PartnerLogin partnerLogin){
        return transactionLogService.fetchTransactionLogs(partnerLogin, false);
    }

    @RequestMapping(value = "/userDetails", method = RequestMethod.POST, consumes = "application/json")
    private @ResponseBody UserDetailBean fetchUserDetailsList(@RequestBody AddProductBean addProductBean){
        return userService.fetchUserDetails(addProductBean);
    }

    @RequestMapping(value = "/partnerDashboard", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody PartnerDashboardBean partnerDashboard(@RequestBody PartnerLogin partnerLogin){
        return partnerService.fetchPartnerDashboard(partnerLogin);
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean refund(@RequestBody PaymentBean paymentBean){
        return paymentService.rechargeWallet(paymentBean);
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Map<Long, List<SubscriptionBean>> calendar(@RequestBody IdBean idBean){
        return userService.calendar(idBean.getId());
    }

    @RequestMapping(value = "/getUserSubscriptions", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<SubscriptionBean> getUserSubscriptions(@RequestBody IdBean idBean){
        return subscriptionService.findActiveSubscriptionBeansByUser(idBean.getId());
    }

    @RequestMapping(value = "/partnerPayments", method = RequestMethod.GET)
    private @ResponseBody List<PartnerPaymentsBean> partnerPayments(){
        return partnerService.partnerPayments();
    }

}
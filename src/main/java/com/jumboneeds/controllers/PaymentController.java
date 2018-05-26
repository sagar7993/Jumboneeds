package com.jumboneeds.controllers;

import com.jumboneeds.beans.IdBean;
import com.jumboneeds.beans.PaymentBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/rechargeWallet", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean rechargeWallet(@RequestBody PaymentBean paymentBean){
        return paymentService.rechargeWallet(paymentBean);
    }

    @RequestMapping(value = "/makePayment", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean makePayment(@RequestBody PaymentBean paymentBean){
        return paymentService.makePayment(paymentBean);
    }

    @RequestMapping(value = "/makePickupRequest", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean makePickupRequest(@RequestBody IdBean idBean){
        return paymentService.makePickupRequest(idBean);
    }

}
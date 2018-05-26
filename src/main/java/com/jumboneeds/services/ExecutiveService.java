package com.jumboneeds.services;

import com.jumboneeds.beans.ExecutiveBean;
import com.jumboneeds.beans.LoginResultBean;
import com.jumboneeds.beans.OtpLoginBean;
import com.jumboneeds.entities.Executive;
import com.jumboneeds.repositories.ExecutiveRepository;
import com.jumboneeds.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutiveService {

    @Autowired
    private ExecutiveRepository executiveRepository;

    public Executive findById(String id){
        return executiveRepository.findById(id);
    }

    public LoginResultBean login(OtpLoginBean otpLoginBean){
        LoginResultBean loginResultBean = new LoginResultBean();

        ExecutiveBean executiveBean = executiveRepository.findByMobileNumber(otpLoginBean.getMobileNumber());

        if (executiveBean != null) {
            loginResultBean.setUserId(executiveBean.getId());
            loginResultBean.setUserName(executiveBean.getExecutiveName());
            loginResultBean.setMobileNumber(executiveBean.getMobileNumber());
            loginResultBean.setProfilePicUrl(executiveBean.getProfilePicUrl());
            loginResultBean.setStatus(1);
        } else {
            //Logout Partner from Executive App
            loginResultBean.setStatus(2);
            loginResultBean.setMessage(Constants.UNREGISTERED_EXECUTIVE);
        }

        return loginResultBean;
    }

}
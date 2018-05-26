package com.jumboneeds.services;

import com.jumboneeds.beans.AdminLoginStatusBean;
import com.jumboneeds.beans.ContactUsBean;
import com.jumboneeds.beans.LoginBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.entities.Admin;
import com.jumboneeds.repositories.AdminRepository;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminService {
    private static final String TAG = "AdminService : ";

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MailService mailService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public AdminLoginStatusBean login(LoginBean loginBean) {
        AdminLoginStatusBean adminLoginStatusBean = new AdminLoginStatusBean();
        adminLoginStatusBean.setStatus(0); adminLoginStatusBean.setMessage(Constants.INVALID_ADMIN_CREDENTIALS);
        Admin admin = adminRepository.login(loginBean.getEmail(), loginBean.getPassword());
        if(admin == null) {
            return adminLoginStatusBean;
        } else {
            adminLoginStatusBean.setStatus(1); adminLoginStatusBean.setMessage(Constants.LOGIN_SUCCESSFUL);
            adminLoginStatusBean.setEmail(admin.getEmail()); adminLoginStatusBean.setName(admin.getUserName());
            return adminLoginStatusBean;
        }
    }

    public StatusBean contactUs(ContactUsBean contactUsBean) {
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        String[] toArray = new String[]{Constants.SUPPORT_MAIL};
        String from = contactUsBean.getEmail(); String replyTo = contactUsBean.getEmail();
        Date date = DateOperations.getTodayStartDate();
        String subject = "Contact Us request received on " + date;
        String htmlText = "<html><body>"
                + "<h3>Contact Us request received from on <b>" + date + "</b> from <b>" + contactUsBean.getUserName()
                + " (" + contactUsBean.getEmail() + ")</b> regarding <b>" + contactUsBean.getSubject() + "</b>.</h3>"
                + "<h2>Message Text : <br/><br/>" + contactUsBean.getMessage() + "</h2>"
                + "</body></html>";
        mailService.buildEmail(toArray, from, replyTo, subject, htmlText, null, null);
        statusBean.setStatus(1); statusBean.setMessage("Your message has been sent successfully.");
        return statusBean;
    }
}
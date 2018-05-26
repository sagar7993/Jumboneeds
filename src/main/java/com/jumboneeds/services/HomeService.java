package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Executive;
import com.jumboneeds.entities.Partner;
import com.jumboneeds.entities.User;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    private static final String TAG = "HomeService : ";

    @Autowired
    private UserService userService;

    @Autowired
    private BillingMasterService billingMasterService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private FilterSubscriptionBeanService filterSubscriptionBeanService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ExecutiveService executiveService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public HomeBean getHome(String userId){
        HomeBean homeBean = new HomeBean();

        User retrievedUser = userService.findById(userId);

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id : " + userId);

            homeBean.setStatus(2);
            homeBean.setMessage(Constants.AUTHORIZATION_FAILED);
            return homeBean;
        } else {
            homeBean.setUserBean(new UserBean(retrievedUser));
            homeBean.setWalletBalance(billingMasterService.getAmountForBillingMaster(retrievedUser.getBillingMasterId()));
            homeBean.setBanners(bannerService.fetchActiveBanners());
            homeBean.setSubscriptions(subscriptionService.findActiveSubscriptionBeansByUser(userId));

            List<FilteredSubscriptionBean> filteredSubscriptionBeans = filterSubscriptionBeanService.fetchFilteredSubscriptionsForUser(userId, DateOperations.getTomorrowStartDate());

            for (FilteredSubscriptionBean filteredSubscriptionBean : filteredSubscriptionBeans) {
                homeBean.getComingTomorrowSubscriptions().add(new SubscriptionBean(filteredSubscriptionBean));
            }

            homeBean.setFlashSaleProduct(productService.findFlashSaleProductByBuilding(retrievedUser.getBuilding().getId()));

            homeBean.setAppConfigBean(new AppConfigBean(appConfigService.getAppConfig()));

            homeBean.setContactNumber("7338526657");
            homeBean.setNotificationToken(retrievedUser.getFcmAndroidToken());

            homeBean.setStatus(1);
            homeBean.setProductSubCategories(productService.fetchProductSubCategoryBeansForBuilding(retrievedUser.getBuilding().getId()));
        }

        return homeBean;
    }

    public PartnerHomeBean getPartnerHome(String userId){
        PartnerHomeBean partnerHomeBean = new PartnerHomeBean();

        Partner retrievedPartner = partnerService.findById(userId);

        if(retrievedPartner == null){
            debugLogger.debug(TAG + "No Partner found for Id : " + userId);

            partnerHomeBean.setStatus(2);
            partnerHomeBean.setMessage(Constants.AUTHORIZATION_FAILED);
            return partnerHomeBean;
        } else {
            partnerHomeBean.setAppConfigBean(new AppConfigBean(appConfigService.getAppConfig()));
            partnerHomeBean.setContactNumber(retrievedPartner.getPartnerDetail().getMobileNumber());
            partnerHomeBean.setAdmin(retrievedPartner.getAdmin());
            partnerHomeBean.setStatus(1);
        }

        return partnerHomeBean;
    }

    public ExecutiveHomeBean getExecutiveHome(String userId){
        ExecutiveHomeBean executiveHomeBean = new ExecutiveHomeBean();

        Executive retrievedExecutive = executiveService.findById(userId);

        if(retrievedExecutive == null){
            debugLogger.debug(TAG + "No Executive found for Id : " + userId);

            executiveHomeBean.setStatus(2);
            executiveHomeBean.setMessage(Constants.AUTHORIZATION_FAILED);
            return executiveHomeBean;
        } else {
            executiveHomeBean.setAppConfigBean(new AppConfigBean(appConfigService.getAppConfig()));
            executiveHomeBean.setContactNumber(retrievedExecutive.getMobileNumber());
            executiveHomeBean.setStatus(1);
        }

        return executiveHomeBean;
    }

}
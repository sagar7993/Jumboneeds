package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Product;
import com.jumboneeds.entities.Subscription;
import com.jumboneeds.entities.SubscriptionType;
import com.jumboneeds.entities.User;
import com.jumboneeds.repositories.SubscriptionRepository;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SubscriptionService {
    private static final String TAG = "SubscriptionService : ";

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SubscriptionTypeService subscriptionTypeService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Subscription findById(String id){
        return subscriptionRepository.findById(id);
    }

    public StatusBean saveSubscription(SubscriptionBean subscriptionBean){
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userService.findById(subscriptionBean.getUserId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id :" + subscriptionBean.getUserId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        Product retrievedProduct = productService.findById(subscriptionBean.getProductId());

        if(retrievedProduct == null){
            debugLogger.debug(TAG + "No Product found for Id :" + subscriptionBean.getProductId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        Subscription subscription = new Subscription();
        subscription.setUser(retrievedUser);
        subscription.setStartDate(DateOperations.getClientToServerCustomStartDateFromLong(subscriptionBean.getStartDate(), 0));
        subscription.setEndDate(DateOperations.getClientToServerCustomEndDateFromLong(subscriptionBean.getEndDate(), 0));
        subscription.setProductQuantity(subscriptionBean.getProductQuantity());
        subscription.setProduct(retrievedProduct);
        subscription.setProductUnitPrice(retrievedProduct.getProductUnitPrice());
        subscription.setFlashSaleProduct(false);

        SubscriptionType retrievedSubscriptionType = subscriptionTypeService.findByType(subscriptionBean.getSubscriptionType());
        subscription.setSubscriptionType(retrievedSubscriptionType);

        try {
            subscriptionRepository.save(subscription);
            statusBean.setMessage("Subscription created.");
            statusBean.setStatus(1);
        } catch (Exception e){
            errorLogger.error(TAG + "Subscription creation failed for User : " + subscriptionBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return statusBean;
    }

    public StatusBean updateSubscription(SubscriptionBean subscriptionBean){
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        Subscription retrievedSubscription = subscriptionRepository.findById(subscriptionBean.getId());

        if(retrievedSubscription == null){
            debugLogger.debug(TAG + "No Subscription found for Id :" + subscriptionBean.getId());
            return statusBean;
        }

        SubscriptionType retrievedSubscriptionType = subscriptionTypeService.findByType(subscriptionBean.getSubscriptionType());

        if(retrievedSubscription.getStartDate().after(DateOperations.getTodayStartDate())){
            try {
                subscriptionRepository.updateSubscription(retrievedSubscription.getId(), DateOperations.getClientToServerCustomStartDateFromLong(subscriptionBean.getStartDate(), 0), DateOperations.getClientToServerCustomEndDateFromLong(subscriptionBean.getEndDate(), 0), subscriptionBean.getProductQuantity(), retrievedSubscriptionType);
                statusBean.setMessage("Subscription updated."); statusBean.setStatus(1);
            } catch (Exception e){
                errorLogger.error(TAG + "Subscription update failed for Subscription : " + retrievedSubscription.getId());
            }
        } else {
            Subscription newSubscription = new Subscription();
            newSubscription.setStartDate(DateOperations.getTomorrowStartDate());
            newSubscription.setEndDate(DateOperations.getClientToServerCustomEndDateFromLong(subscriptionBean.getEndDate(), 0));
            newSubscription.setProductQuantity(subscriptionBean.getProductQuantity());
            newSubscription.setProductUnitPrice(retrievedSubscription.getProductUnitPrice());
            newSubscription.setFlashSaleProduct(false);
            newSubscription.setUser(retrievedSubscription.getUser());
            newSubscription.setProduct(retrievedSubscription.getProduct());
            newSubscription.setSubscriptionType(retrievedSubscriptionType);

            try {
                subscriptionRepository.endSubscription(retrievedSubscription.getId(), DateOperations.getTodayEndDate());

                try {
                    subscriptionRepository.save(newSubscription);
                    statusBean.setMessage("Subscription updated.");
                    statusBean.setStatus(1);
                } catch (Exception e){
                    errorLogger.error(TAG + "Subscription creation failed for User : " + subscriptionBean.getUserId());
                }
            } catch (Exception e){
                errorLogger.error(TAG + "Subscription end failed for Subscription : " + retrievedSubscription.getId());
            }
        }

        return statusBean;
    }

    public StatusBean endSubscription(IdBean idBean){
        StatusBean statusBean = new StatusBean();

        Subscription retrievedSubscription = subscriptionRepository.findById(idBean.getId());

        if(retrievedSubscription == null){
            debugLogger.debug(TAG + "No Subscription found for Id :" + idBean.getId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        try {
            subscriptionRepository.endSubscription(retrievedSubscription.getId(), DateOperations.getTodayEndDate());

            statusBean.setStatus(1);
        } catch (Exception e){
            errorLogger.error(TAG + "Subscription end failed for Subscription : " + retrievedSubscription.getId());

            statusBean.setStatus(0);
        }

        return statusBean;
    }

    public FlashSaleResponseBean saveFlashSaleSubscription(SubscriptionBean subscriptionBean){
        FlashSaleResponseBean flashSaleResponseBean = new FlashSaleResponseBean();

        User retrievedUser = userService.findById(subscriptionBean.getUserId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id :" + subscriptionBean.getUserId());
            flashSaleResponseBean.setStatus(0);
            flashSaleResponseBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return flashSaleResponseBean;
        }

        Product retrievedProduct = productService.findById(subscriptionBean.getProductId());

        if(retrievedProduct == null){
            debugLogger.debug(TAG + "No Product found for Id :" + subscriptionBean.getProductId());
            flashSaleResponseBean.setStatus(0);
            flashSaleResponseBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return flashSaleResponseBean;
        }

        Subscription subscription = new Subscription();
        subscription.setUser(retrievedUser);
        subscription.setStartDate(DateOperations.getTomorrowStartDate());
        subscription.setEndDate(DateOperations.getTomorrowEndDate());
        subscription.setProductQuantity(subscriptionBean.getProductQuantity());
        subscription.setProduct(retrievedProduct);
        subscription.setProductUnitPrice(retrievedProduct.getProductUnitPrice());
        subscription.setFlashSaleProduct(true);

        SubscriptionType retrievedSubscriptionType = subscriptionTypeService.findByType(4);
        subscription.setSubscriptionType(retrievedSubscriptionType);

        try {
            Subscription savedSubscription = subscriptionRepository.save(subscription);

            flashSaleResponseBean.setStatus(1);
            flashSaleResponseBean.setSubscriptionBean(new SubscriptionBean(savedSubscription));
        } catch (Exception e){
            errorLogger.error(TAG + "Subscription creation failed for User : " + subscriptionBean.getUserId());

            flashSaleResponseBean.setStatus(0);
            flashSaleResponseBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return flashSaleResponseBean;
    }

    public List<SubscriptionBean> findActiveSubscriptionBeansByUser(String userId){
        Date date = DateOperations.getTodayEndDate();
        return subscriptionRepository.findActiveSubscriptionBeansByUser(userId, date);
    }

    public List<Subscription> findSubscriptionsByUserListAndDate(List<String> userIds, Date date){
        return subscriptionRepository.findActiveSubscriptionsByUserList(userIds, date);
    }

    public List<Subscription> findByProductsAndDate(List<Product> products, Date date){
        return subscriptionRepository.findByProductsAndDate(products, date);
    }

    public List<Subscription> findByProductsAndDateWithoutPartnerProducts(List<Product> products, Date date){
        return subscriptionRepository.findByProductsAndDateWithoutPartnerProducts(products, date);
    }

    public List<Subscription> fetchActiveSubscriptionsByDate(Date date){
        return subscriptionRepository.fetchActiveSubscriptionsByDate(date);
    }

    public List<Subscription> fetchActiveSubscriptionsByDateWithoutPartnerProducts(Date date){
        return subscriptionRepository.fetchActiveSubscriptionsByDateWithoutPartnerProducts(date);
    }

    public List<FilteredSubscriptionBean> findSubscriptionBeansByUser(String userId, Date date){
        return subscriptionRepository.findSubscriptionBeansByUser(userId, date);
    }

    public List<FilteredSubscriptionBean> findSubscriptionBeansByProductsAndDate(List<Product> products, Date date){
        return subscriptionRepository.findSubscriptionBeansByProductsAndDate(products, date);
    }

    public List<FilteredSubscriptionBean> findSubscriptionBeansByProductsAndDateWithoutPartnerProducts(List<Product> products, Date date){
        return subscriptionRepository.findSubscriptionBeansByProductsAndDateWithoutPartnerProducts(products, date);
    }

    public List<FilteredSubscriptionBean> fetchSubscriptionBeansByDate(Date date){
        return subscriptionRepository.fetchSubscriptionBeansByDate(date);
    }

    public List<FilteredSubscriptionBean> fetchSubscriptionBeansByDateWithoutPartnerProducts(Date date){
        return subscriptionRepository.fetchSubscriptionBeansByDateWithoutPartnerProducts(date);
    }

    public List<SubscriptionBean> findScheduledSubscriptionByUser(String userId, int pastLimit){
        Date date = DateOperations.getClientToServerCustomStartDateFromLong(new Date().getTime(), -pastLimit);
        return subscriptionRepository.findScheduledSubscriptionByUser(userId, date);
    }

    @Transactional
    public StatusBean createAdminSubscription(AdminSubscriptionBean adminSubscriptionBean) {
        Date startDate; Date endDate;
        SubscriptionType retrievedSubscriptionType = subscriptionTypeService.findById(adminSubscriptionBean.getSubscriptionType());
        if(retrievedSubscriptionType.getType() == 4) {
            startDate = DateOperations.getCustomStartDateFromString(adminSubscriptionBean.getSubscriptionDate());
            startDate = new Date(startDate.getTime() - Constants.UTC_TO_IST_DIFFERENCE);
            endDate = new Date(startDate.getTime() + Constants.START_DATE_TO_START_DATE_DIFFERENCE);
        } else {
            startDate = DateOperations.getCustomStartDateFromString(adminSubscriptionBean.getStartDate());
            startDate = new Date(startDate.getTime() - Constants.UTC_TO_IST_DIFFERENCE);
            endDate = DateOperations.getCustomStartDateFromString(adminSubscriptionBean.getEndDate());
            endDate = new Date(endDate.getTime() - Constants.UTC_TO_IST_DIFFERENCE);
            endDate = new Date(endDate.getTime() + Constants.START_DATE_TO_START_DATE_DIFFERENCE);
        }
        SubscriptionBean subscriptionBean = new SubscriptionBean();
        subscriptionBean.setId(adminSubscriptionBean.getSubscriptionId());
        subscriptionBean.setProductQuantity(adminSubscriptionBean.getProductQuantity());
        subscriptionBean.setStartDate(startDate.getTime());
        subscriptionBean.setEndDate(endDate.getTime());
        subscriptionBean.setSubscriptionType(retrievedSubscriptionType.getType());
        subscriptionBean.setUserId(adminSubscriptionBean.getUser());
        subscriptionBean.setProductId(adminSubscriptionBean.getProduct());
        return saveSubscription(subscriptionBean);
    }

    @Transactional
    public StatusBean updateAdminSubscription(AdminSubscriptionBean adminSubscriptionBean) {
        Date startDate; Date endDate;
        SubscriptionType retrievedSubscriptionType = subscriptionTypeService.findById(adminSubscriptionBean.getSubscriptionType());
        if(retrievedSubscriptionType.getType() == 4) {
            startDate = DateOperations.getCustomStartDateFromString(adminSubscriptionBean.getSubscriptionDate());
            startDate = new Date(startDate.getTime() - Constants.UTC_TO_IST_DIFFERENCE);
            endDate = new Date(startDate.getTime() + Constants.START_DATE_TO_START_DATE_DIFFERENCE);
        } else {
            startDate = DateOperations.getCustomStartDateFromString(adminSubscriptionBean.getStartDate());
            startDate = new Date(startDate.getTime() - Constants.UTC_TO_IST_DIFFERENCE);
            endDate = DateOperations.getCustomStartDateFromString(adminSubscriptionBean.getEndDate());
            endDate = new Date(endDate.getTime() - Constants.UTC_TO_IST_DIFFERENCE);
            endDate = new Date(endDate.getTime() + Constants.START_DATE_TO_START_DATE_DIFFERENCE);
        }
        SubscriptionBean subscriptionBean = new SubscriptionBean();
        subscriptionBean.setId(adminSubscriptionBean.getSubscriptionId());
        subscriptionBean.setProductQuantity(adminSubscriptionBean.getProductQuantity());
        subscriptionBean.setStartDate(startDate.getTime());
        subscriptionBean.setEndDate(endDate.getTime());
        subscriptionBean.setSubscriptionType(retrievedSubscriptionType.getType());
        return updateSubscription(subscriptionBean);
    }

}
package com.jumboneeds.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

@Service
public class DatabaseInitializeService implements ApplicationListener<ContextRefreshedEvent> {
    private static final String TAG = "DatabaseInitializeService : ";

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();

        List<BigInteger> admins = entityManager.createNativeQuery("SELECT COUNT(*) FROM admin").getResultList();
        Integer adminCount = admins.get(0).intValue();
        if(adminCount == 0) {
            entityManager.createNativeQuery("DELETE FROM admin WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO admin (id, created_at, email, mobile_number, password, updated_at, user_name) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'support@jumboneeds.com', '9833495587', 'Milano', '2016-08-29 00:00:00', 'Sagar Jain');").executeUpdate();
        }

        List<BigInteger> appConfigs = entityManager.createNativeQuery("SELECT COUNT(*) FROM app_config").getResultList();
        Integer appConfigCount = appConfigs.get(0).intValue();
        if(appConfigCount == 0) {
            entityManager.createNativeQuery("DELETE FROM app_config WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO app_config (id, created_at, android_version, partner_app_version, executive_app_version, ios_version, update_message, updated_at) VALUES " +
                    "('1', '2016-08-29 00:00:00', 9, 1, 1, '1.0', 'IT SEEMS THAT YOU HAVE THE OLD VERSION OF OUR APP. PLEASE UPDATE TO NEW VERSION SO WE CAN SERVE YOU BETTER.', '2016-08-29 00:00:00');").executeUpdate();
        }

        List<BigInteger> billingLogTypes = entityManager.createNativeQuery("SELECT COUNT(*) FROM billing_log_type").getResultList();
        Integer billingLogTypeCount = billingLogTypes.get(0).intValue();
        if(billingLogTypeCount == 0) {
            entityManager.createNativeQuery("DELETE FROM billing_log_type WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO billing_log_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'Daily Bill Deduction via Billing Job', 1, '2016-08-29 00:00:00')," +
                    "('2', '2016-08-29 00:00:00', 'Products Ordered', 2, '2016-08-29 00:00:00')," +
                    "('3', '2016-08-29 00:00:00', 'Payments', 3, '2016-08-29 00:00:00')," +
                    "('4', '2016-08-29 00:00:00', 'Service discontinued due to low balance.', 4, '2016-08-29 00:00:00');").executeUpdate();
        }

        List<BigInteger> bannerTypes = entityManager.createNativeQuery("SELECT COUNT(*) FROM banner_type").getResultList();
        Integer bannerTypeCount = bannerTypes.get(0).intValue();
        if(bannerTypeCount == 0) {
            entityManager.createNativeQuery("DELETE FROM banner_type WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO banner_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'Payment', 1, '2016-08-29 00:00:00')," +
                    "('2', '2016-08-29 00:00:00', 'Product Offer', 2, '2016-08-29 00:00:00')," +
                    "('3', '2016-08-29 00:00:00', 'Advertisement', 3, '2016-08-29 00:00:00');").executeUpdate();
        }

        List<BigInteger> notificationTypes = entityManager.createNativeQuery("SELECT COUNT(*) FROM notification_type").getResultList();
        Integer notificationTypeCount = notificationTypes.get(0).intValue();
        if(notificationTypeCount == 0) {
            entityManager.createNativeQuery("DELETE FROM notification_type WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO notification_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'Payment', 1, '2016-08-29 00:00:00')," +
                    "('2', '2016-08-29 00:00:00', 'Billing History', 2, '2016-08-29 00:00:00')," +
                    "('3', '2016-08-29 00:00:00', 'Product Offer', 3, '2016-08-29 00:00:00');").executeUpdate();
        }

        List<BigInteger> paymentTypes = entityManager.createNativeQuery("SELECT COUNT(*) FROM payment_type").getResultList();
        Integer paymentTypeCount = paymentTypes.get(0).intValue();
        if(paymentTypeCount == 0) {
            entityManager.createNativeQuery("DELETE FROM payment_type WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO payment_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'Cash Collected', 1, '2016-08-29 00:00:00')," +
                    "('2', '2016-08-29 00:00:00', 'Coupon Refund', 2, '2016-08-29 00:00:00')," +
                    "('3', '2016-08-29 00:00:00', 'Online Payment', 3, '2016-08-29 00:00:00')," +
                    "('4', '2016-08-29 00:00:00', 'Non-Delivery Refund', 4, '2016-08-29 00:00:00')," +
                    "('5', '2016-08-29 00:00:00', 'Others', 5, '2016-08-29 00:00:00')," +
                    "('6', '2016-08-29 00:00:00', 'Billing Job', 6, '2016-08-29 00:00:00')," +
                    "('7', '2016-08-29 00:00:00', 'Bill Regulator Job', 7, '2016-08-29 00:00:00');").executeUpdate();
        }

        List<BigInteger> reasonTypes = entityManager.createNativeQuery("SELECT COUNT(*) FROM reason_type").getResultList();
        Integer reasonTypeCount = reasonTypes.get(0).intValue();
        if(reasonTypeCount == 0) {
            entityManager.createNativeQuery("DELETE FROM reason_type WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO reason_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'N/A', 1, '2016-08-29 00:00:00')," +
                    "('2', '2016-08-29 00:00:00', 'Later', 2, '2016-08-29 00:00:00')," +
                    "('3', '2016-08-29 00:00:00', 'Windows', 3, '2016-08-29 00:00:00')," +
                    "('4', '2016-08-29 00:00:00', 'iOS', 4, '2016-08-29 00:00:00')," +
                    "('5', '2016-08-29 00:00:00', 'Subscribed', 5, '2016-08-29 00:00:00')," +
                    "('6', '2016-08-29 00:00:00', 'Not Subscribed', 6, '2016-08-29 00:00:00')," +
                    "('7', '2016-08-29 00:00:00', 'DND', 7, '2016-08-29 00:00:00')," +
                    "('8', '2016-08-29 00:00:00', 'Other', 8, '2016-08-29 00:00:00');").executeUpdate();
        }

        List<BigInteger> subscriptionExceptionTypes = entityManager.createNativeQuery("SELECT COUNT(*) FROM subscription_exception_type").getResultList();
        Integer subscriptionExceptionTypeCount = subscriptionExceptionTypes.get(0).intValue();
        if(subscriptionExceptionTypeCount == 0) {
            entityManager.createNativeQuery("DELETE FROM subscription_exception_type WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO subscription_exception_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'Billing Regulator Job Generated Exception', 1, '2016-08-29 00:00:00')," +
                    "('2', '2016-08-29 00:00:00', 'User Generated Exception', 2, '2016-08-29 00:00:00');").executeUpdate();
        }

        List<BigInteger> subscriptionTypes = entityManager.createNativeQuery("SELECT COUNT(*) FROM subscription_type").getResultList();
        Integer subscriptionTypeCount = subscriptionTypes.get(0).intValue();
        if(subscriptionTypeCount == 0) {
            entityManager.createNativeQuery("DELETE FROM subscription_type WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO subscription_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'Everyday', 1, '2016-08-29 00:00:00')," +
                    "('2', '2016-08-29 00:00:00', 'Alternate Days', 2, '2016-08-29 00:00:00')," +
                    "('3', '2016-08-29 00:00:00', 'Every 3 Days', 3, '2016-08-29 00:00:00')," +
                    "('4', '2016-08-29 00:00:00', 'Single Day', 4, '2016-08-29 00:00:00');").executeUpdate();
        }

        List<BigInteger> productCategories = entityManager.createNativeQuery("SELECT COUNT(*) FROM product_category").getResultList();
        Integer productCategoryCount = productCategories.get(0).intValue();
        if(productCategoryCount == 0) {
            entityManager.createNativeQuery("DELETE FROM product_category WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO product_category (id, created_at, product_category_name, product_category_image_url, type, updated_at, weight, status) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'Milk & Dairy Products', 'http://jumboneeds.com/admin/images/category/milk.png', 1, '2016-08-29 00:00:00', 99, 1)," +
                    "('2', '2016-08-29 00:00:00', 'Bread and Bakery', 'http://jumboneeds.com/admin/images/category/milk.png', 2, '2016-08-29 00:00:00', 99, 1)," +
                    "('3', '2016-08-29 00:00:00', 'Batter and Mixes', 'http://jumboneeds.com/admin/images/category/batter_mixes.png', 3, '2016-08-29 00:00:00', 99, 1)," +
                    "('4', '2016-08-29 00:00:00', 'Water Can', 'http://jumboneeds.com/admin/images/category/water_can.png', 4, '2016-08-29 00:00:00', 99, 1);").executeUpdate();
        }

        List<BigInteger> productSubCategories = entityManager.createNativeQuery("SELECT COUNT(*) FROM product_sub_category").getResultList();
        Integer productSubCategoryCount = productSubCategories.get(0).intValue();
        if(productSubCategoryCount == 0) {
            entityManager.createNativeQuery("DELETE FROM product_sub_category WHERE 1").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO product_sub_category (id, created_at, product_sub_category_name, product_sub_category_image_url, product_sub_category_image_url_small, type, updated_at, weight, product_category, status, folder_name) VALUES " +
                    "('1', '2016-08-29 00:00:00', 'Milk', 'http://jumboneeds.com/admin/images/sub_category/milk.png', 'http://jumboneeds.com/admin/images/category/milk.png', 1, '2016-08-29 00:00:00', 99, '1', 1, 'milk')," +
                    "('2', '2016-08-29 00:00:00', 'Curd', 'http://jumboneeds.com/admin/images/sub_category/curd.png', 'http://jumboneeds.com/admin/images/category/curd.png', 2, '2016-08-29 00:00:00', 99, '1', 1, 'curd')," +
                    "('3', '2016-08-29 00:00:00', 'Paneer', 'http://jumboneeds.com/admin/images/sub_category/paneer.png', 'http://jumboneeds.com/admin/images/category/paneer_butter.png', 3, '2016-08-29 00:00:00', 99, '1', 1, 'paneer')," +
                    "('4', '2016-08-29 00:00:00', 'Bread', 'http://jumboneeds.com/admin/images/sub_category/bread.png', 'http://jumboneeds.com/admin/images/category/bread.png', 4, '2016-08-29 00:00:00', 99, '2', 1, 'bread')," +
                    "('5', '2016-08-29 00:00:00', 'Biscuits', 'http://jumboneeds.com/admin/images/sub_category/biscuit.png', 'http://jumboneeds.com/admin/images/category/biscuit.png', 5, '2016-08-29 00:00:00', 99, '2', 1, 'biscuit')," +
                    "('6', '2016-08-29 00:00:00', 'Idly / Dosa Batter', 'http://jumboneeds.com/admin/images/sub_category/batter.png', 'http://jumboneeds.com/admin/images/category/batter_mixes.png', 6, '2016-08-29 00:00:00', 99, '3', 1, 'batter')," +
                    "('7', '2016-08-29 00:00:00', 'Mixes / Ready-To-Eat', 'http://jumboneeds.com/admin/images/sub_category/ready_to_eat.png', 'http://jumboneeds.com/admin/images/category/ready_to_eat.png', 7, '2016-08-29 00:00:00', 99, '3', 1, 'ready_to_eat')," +
                    "('8', '2016-08-29 00:00:00', 'Water Can', 'http://jumboneeds.com/admin/images/sub_category/water_can.png', 'http://jumboneeds.com/admin/images/category/water_can.png', 8, '2016-08-29 00:00:00', 99, '4', 1, 'water_can');").executeUpdate();
        }

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();

    }

}
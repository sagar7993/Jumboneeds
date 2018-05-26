package com.jumboneeds.services;

import com.jumboneeds.beans.LowBalanceUserBean;
import com.jumboneeds.beans.NotificationBean;
import com.jumboneeds.beans.ProductMasterBean;
import com.jumboneeds.beans.ProductSubCategoryBean;
import com.jumboneeds.utils.NotificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by akash.mercer on 20-Oct-16.
 */

@Service
public class MiscellaneousService {
    private static final String TAG = "MiscellaneousService : ";

    @Autowired
    private ProductMasterService productMasterService;

    @Autowired
    private ProductSubCategoryService productSubCategoryService;

    @Autowired
    private BillingMasterService billingMasterService;

    @Scheduled(cron = "0 30 16 * * *")
    public void disableNonInventoryProductStatus() {
        List<ProductSubCategoryBean> productSubCategoryBeans = productSubCategoryService.fetchAll();

        Set<String> productSubCategoryIdSet = getProductSubCategoryIdSet();

        for (ProductSubCategoryBean productSubCategoryBean : productSubCategoryBeans) {
            if (productSubCategoryIdSet.contains(productSubCategoryBean.getId())) {
                productSubCategoryService.updateProductSubCategoryStatus(2, productSubCategoryBean.getId());
            }
        }

        List<ProductMasterBean> productMasterBeans = productMasterService.fetchAll();

        Set<String> productMasterIdSet = getProductMasterIdSet();

        for (ProductMasterBean productMasterBean : productMasterBeans) {
            if (productMasterIdSet.contains(productMasterBean.getId())) {
                productMasterService.updateProductMasterStatus(2, productMasterBean.getId());
            }
        }
    }

    @Scheduled(cron = "0 30 18 * * *")
    public void enableNonInventoryProductStatus() {
        List<ProductSubCategoryBean> productSubCategoryBeans = productSubCategoryService.fetchAll();

        Set<String> productSubCategoryIdSet = getProductSubCategoryIdSet();

        for (ProductSubCategoryBean productSubCategoryBean : productSubCategoryBeans) {
            if (productSubCategoryIdSet.contains(productSubCategoryBean.getId())) {
                productSubCategoryService.updateProductSubCategoryStatus(1, productSubCategoryBean.getId());
            }
        }

        List<ProductMasterBean> productMasterBeans = productMasterService.fetchAll();

        Set<String> productMasterIdSet = getProductMasterIdSet();

        for (ProductMasterBean productMasterBean : productMasterBeans) {
            if (productMasterIdSet.contains(productMasterBean.getId())) {
                productMasterService.updateProductMasterStatus(1, productMasterBean.getId());
            }
        }
    }

    private Set<String> getProductSubCategoryIdSet() {
        Set<String> productSubCategoryIdSet = new HashSet<>();
        productSubCategoryIdSet.add("3");                                           //Cheese and Paneer
        productSubCategoryIdSet.add("5");                                           //Biscuits
        productSubCategoryIdSet.add("6");                                           //Idly / Dosa Batter
        productSubCategoryIdSet.add("7");                                           //Mixes / Ready-To-Eat
        productSubCategoryIdSet.add("12");                                          //Ghee And Butter
        productSubCategoryIdSet.add("13");                                          //Groceries and Staples
        return productSubCategoryIdSet;
    }

    private Set<String> getProductMasterIdSet() {
        Set<String> productMasterIdSet = new HashSet<>();
        productMasterIdSet.add("f60c65e2-0eef-402f-a24f-c5671f14b401");             //Amul Cheese (5 Slices)
        productMasterIdSet.add("ac98432a-99c2-44cc-870a-4734868dc7cf");             //Britannia Milk Bread
        productMasterIdSet.add("c69a1baa-bd42-44c2-8b4b-f7d33a4fa817");             //Amul Gold Homogenised Standardised Milk
        productMasterIdSet.add("46042d39-2992-4cf6-825e-5d1af62a1270");             //Amul Taaza Homogenised Toned Milk
        productMasterIdSet.add("5f50cf26-1164-413a-8d7a-baa9af2d656f");             //Nestle A+ Slim Milk
        productMasterIdSet.add("6ced7b84-2264-48c1-9649-9fc974a3a14c");             //Nestle a+ Nourish Toned Milk
        productMasterIdSet.add("ab1149ce-eb0b-4c63-9506-2abb55a1948e");             //Nandini Goodlife Slim Skimmed Milk
        productMasterIdSet.add("4ded8093-65c3-4145-8d61-377c0a22f2ea");             //Nandini Goodlife Cow Milk
        productMasterIdSet.add("18197576-2188-4698-8fc2-44ad65772c56");             //Milky Mist Set Curd (200 gm)
        productMasterIdSet.add("7248711f-8d12-4b6e-b19c-929bf885ff11");             //Milky Mist Set Curd (400 gm)
        productMasterIdSet.add("380c3220-2059-4125-ae38-ac148c398411");             //Milky Mist Curd (500 gm)
        productMasterIdSet.add("a703279c-db6a-433c-aa4b-707a07af7407");             //Amul Masti Spiced Buttermilk 200 ml
        productMasterIdSet.add("7c941e36-f444-4481-9b39-9af68093c63e");             //Amul Masti Spiced Buttermilk 1 L
        productMasterIdSet.add("456331bf-0fdf-4d95-8572-dc14c8e7e9a6");             //Fortune - Rice Bran Health Oil 1 L
        productMasterIdSet.add("b730f706-ec64-40c1-a35a-8a95d398463a");             //Fortune Soya Health - Refined Soyabean Oil 1 L
        productMasterIdSet.add("12328785-bec5-47e5-8db6-057144d98723");             //Fortune Sunlite - Refined Sunflower Oil 1 L
        productMasterIdSet.add("1a2b1588-0c50-478d-b1fd-3db1646703e1");             //Dhara Health - Refined Sunflower Oil 1 L
        productMasterIdSet.add("8d12058f-8c41-42a8-b5e2-10ac3cf075c9");             //Dhara - Filtered Groundnut Oil 1 L
        productMasterIdSet.add("76dca884-1df2-4b93-8b72-64188c4858b4");             //Saffola Oil - Gold 1 L
        productMasterIdSet.add("ba57718a-7969-4dbc-9366-cedc0a2666f9");             //Saffola Oil - Tasty 1 L
        productMasterIdSet.add("0e24aa4d-7bf5-432d-b68b-53a6db7974c5");             //Saffola Oil - Active 1 L
        productMasterIdSet.add("b3e1a6f4-ce3f-4ea1-ac13-59b36eb35ad3");             //Sundrop Oil - Gold Lite 1 L
        productMasterIdSet.add("4bb5f8f3-5b01-420f-8005-a30422d41b43");             //Sundrop Oil - Nutrilite 1 L
        productMasterIdSet.add("11e110ed-b83e-4c4f-8240-21ef285d5439");             //Sundrop Oil - Heart 1 L
        productMasterIdSet.add("c9476b79-2daa-4dd9-a13c-3aed98e0ffea");             //Amul Butter - Pasteurized 100 gm
        productMasterIdSet.add("e9b60fab-3d7f-4acb-8516-1c8644dc9636");             //Amul Butter - Pasteurized 500 gm
        productMasterIdSet.add("080ec109-f624-4545-9d90-4c20a98ebbf8");             //Amul Buttery Spread - Garlic & Herbs 100 gm
        productMasterIdSet.add("cdfd7272-b0f6-45a3-a1f0-0d0026d364b5");             //Kellogg's Chocos - Moon and Stars 350 gm
        productMasterIdSet.add("b2bec92b-f175-42c1-929e-43593c5aab94");             //Kellogg's Honey Loops 300 gm
        productMasterIdSet.add("e37c5f11-cfd1-4817-bea8-0303816c3234");             //Kellogg's Corn Flakes - Real Almond & Honey 300 gm
        productMasterIdSet.add("9426803e-d74e-4085-9593-e06c6e4fbda1");             //Kellogg's Chocos 300 gm
        productMasterIdSet.add("3fadfb1a-f65b-4382-b4f8-f60386c19e96");             //Kellogg's Corn Flakes - Original 250 gm
        return productMasterIdSet;
    }

    @Scheduled(cron = "0 30 14 * * *")
    public void sendLowBalanceNotification() {
        List<LowBalanceUserBean> lowBalanceUserBeans = billingMasterService.findByLowAmount(50.0);

        List<NotificationBean> notificationBeans = new ArrayList<>();

        for (LowBalanceUserBean lowBalanceUserBean : lowBalanceUserBeans) {
            NotificationBean notificationBean = new NotificationBean();
            notificationBean.setTitle("Low Balance!!");
            notificationBean.setShortDescription("Only Rs." + lowBalanceUserBean.getAmount() + " left in your wallet.");
            notificationBean.setLongDescription("Dear Customer, the balance in your wallet is Rs." + lowBalanceUserBean.getAmount() + ". Please make cash pickup request to recharge your wallet.");
            notificationBean.setNotificationType(1);
            notificationBean.setReceiverId(lowBalanceUserBean.getFcmAndroidToken());
            notificationBeans.add(notificationBean);
        }

        NotificationManager.sendNotification(notificationBeans);
    }

}

package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Partner;
import com.jumboneeds.entities.PartnerDetail;
import com.jumboneeds.repositories.PartnerRepository;
import com.jumboneeds.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartnerService {
    private static final String TAG = "PartnerService : ";

    @Autowired
    private UserService userService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private TransactionLogService transactionLogService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Partner findById(String partnerId){
        return partnerRepository.findById(partnerId);
    }

    public Partner save(Partner partner){
        return partnerRepository.save(partner);
    }

    public List<Partner> fetchAll(){
        return (List<Partner>) partnerRepository.findAll();
    }

    public Partner partnerWebLogin(String userName, String password){
        return partnerRepository.login(userName, password);
    }

    public LoginResultBean login(OtpLoginBean otpLoginBean){
        LoginResultBean loginResultBean = new LoginResultBean();

        PartnerBean partnerBean = partnerRepository.findByMobileNumber(otpLoginBean.getMobileNumber());

        if (partnerBean != null) {
            loginResultBean.setUserId(partnerBean.getId());
            loginResultBean.setUserName(partnerBean.getPartnerName());
            loginResultBean.setMobileNumber(partnerBean.getMobileNumber());
            loginResultBean.setProfilePicUrl(partnerBean.getProfilePicUrl());
            loginResultBean.setStatus(1);
        } else {
            //Logout Partner from Partner App
            loginResultBean.setStatus(2);
            loginResultBean.setMessage(Constants.UNREGISTERED_PARTNER);
        }

        return loginResultBean;
    }

    public List<ParentPartnersBean> getParentPartners() {
        String parentPartnerQuery = "SELECT partner.id, partner_detail.partner_name, partner_detail.mobile_number FROM partner " +
            "LEFT JOIN partner_detail ON partner_detail.id = partner.partner_detail " +
            "WHERE partner.id = partner.parent_partner_id";
        List<ParentPartnersBean> parentPartners = entityManager.createNativeQuery(parentPartnerQuery, "ParentPartners").getResultList();

        if(!CollectionUtils.isEmpty(parentPartners)) {
            return parentPartners;
        } else {
            return new ArrayList<>();
        }
    }

    public List<Partner> checkUniquePartner(String buildingName, String userName) {
        return partnerRepository.checkUniquePartner(buildingName, userName);
    }

    public List<Partner> checkUniquePartnerBuildingName(String buildingName) {
        return partnerRepository.checkUniquePartnerBuildingName(buildingName);
    }

    public List<Partner> checkUniquePartnerUserName(String userName) {
        return partnerRepository.checkUniquePartnerUserName(userName);
    }

    public void updateParentPartner(String partnerId, String parentPartnerId) {
        partnerRepository.updateParentPartner(partnerId, parentPartnerId);
    }

    public List<PartnerPaymentsBean> partnerPayments() {

        String partnerAmountQuery = "SELECT partner.parent_partner_id, partner.partner_name, DATE_FORMAT(CURRENT_DATE, '%D %M %Y') AS date, COALESCE(SUM(cashTable.amount), 0) AS cashAmount, COALESCE(SUM(couponRefundTable.amount), 0) AS couponRefundAmount, COALESCE(SUM(nonDeliveryRefundTable.amount), 0) AS nonDeliveryAmount, COALESCE(SUM(onlineTable.amount), 0) AS onlineAmount, COALESCE(SUM(othersTable.amount), 0) AS othersAmount FROM user " +
            "LEFT JOIN (SELECT amount, user FROM billing_master WHERE payment_date = CURRENT_DATE AND payment_type = 1 AND billing_master.is_current = 0) AS cashTable on cashTable.user = user.id " +
            "LEFT JOIN (SELECT amount, user FROM billing_master WHERE payment_date = CURRENT_DATE AND payment_type = 2 AND billing_master.is_current = 0) AS couponRefundTable on couponRefundTable.user = user.id " +
            "LEFT JOIN (SELECT amount, user FROM billing_master WHERE payment_date = CURRENT_DATE AND payment_type = 3 AND billing_master.is_current = 0) AS onlineTable on onlineTable.user = user.id " +
            "LEFT JOIN (SELECT amount, user FROM billing_master WHERE payment_date = CURRENT_DATE AND payment_type = 4 AND billing_master.is_current = 0) AS nonDeliveryRefundTable on nonDeliveryRefundTable.user = user.id " +
            "LEFT JOIN (SELECT amount, user FROM billing_master WHERE payment_date = CURRENT_DATE AND payment_type = 5 AND billing_master.is_current = 0) AS othersTable on othersTable.user = user.id " +
            "LEFT JOIN building on user.building = building.id LEFT JOIN partner ON partner.id = building.partner " +
            "GROUP BY partner.parent_partner_id";

        String partnerQuantityQuery = "SELECT partner.parent_partner_id, partner.partner_name, DATE_FORMAT(CURRENT_DATE, '%D %M %Y') AS date, COALESCE(SUM(milk.quantity), 0) AS milk_quantity, COALESCE(SUM(nonMilk.quantity), 0) AS non_milk_quantity FROM subscription sub " +
            "LEFT JOIN product ON product.id = sub.product " +
            "LEFT JOIN building ON building.id = product.building " +
            "LEFT JOIN partner ON partner.id = building.partner " +
            "LEFT JOIN (SELECT s.id AS sId, COALESCE(e.product_quantity, s.product_quantity) AS quantity FROM subscription s " +
            "JOIN user u ON u.id = s.user " +
            "JOIN building b ON b.id = u.building " +
            "JOIN product p ON p.id = s.product " +
            "JOIN product_master pm ON pm.id = p.product_master " +
            "LEFT JOIN subscription_exception e ON e.subscription_master = s.id AND DATE(e.date) = CURDATE() " +
            "WHERE CURRENT_DATE BETWEEN s.start_date AND s.end_date AND MOD(ABS(DATEDIFF(CURRENT_DATE, s.start_date)), s.subscription_type) = 0 " +
            "AND p.product_sub_category = 1) AS milk ON milk.sId = sub.id " +
            "LEFT JOIN (SELECT nms.id AS sId, COALESCE(nme.product_quantity, nms.product_quantity) AS quantity FROM subscription nms " +
            "JOIN user nmu ON nmu.id = nms.user " +
            "JOIN building nmb ON nmb.id = nmu.building " +
            "JOIN product nmp ON nmp.id = nms.product " +
            "JOIN product_master nmpm ON nmpm.id = nmp.product_master " +
            "LEFT JOIN subscription_exception nme ON nme.subscription_master = nms.id AND DATE(nme.date) = CURDATE() " +
            "WHERE CURRENT_DATE BETWEEN nms.start_date AND nms.end_date AND MOD(ABS(DATEDIFF(CURRENT_DATE, nms.start_date)), nms.subscription_type) = 0 " +
            "AND nmp.product_sub_category != 1) AS nonMilk ON nonMilk.sId = sub.id " +
            "GROUP BY partner.parent_partner_id";

        List<PartnerAmountsBean> partnerAmountsBeans = entityManager.createNativeQuery(partnerAmountQuery, "PartnerAmountsBean").getResultList();
        List<PartnerQuantityBean> partnerQuantityBeans = entityManager.createNativeQuery(partnerQuantityQuery, "PartnerQuantityBean").getResultList();
        List<PartnerPaymentsBean> partnerPaymentsBeans = new ArrayList<>();
        for(PartnerAmountsBean partnerAmountsBean : partnerAmountsBeans) {
            PartnerPaymentsBean partnerPaymentsBean = new PartnerPaymentsBean(partnerAmountsBean);
            partnerPaymentsBean.setMilkQuantity(BigDecimal.ZERO);
            partnerPaymentsBean.setNonMilkQuantity(BigDecimal.ZERO);
            for(PartnerQuantityBean partnerQuantityBean : partnerQuantityBeans) {
                if(partnerQuantityBean.getParentPartnerId().equals(partnerAmountsBean.getParentPartnerId())) {
                    partnerPaymentsBean.setMilkQuantity(partnerQuantityBean.getMilkQuantity());
                    partnerPaymentsBean.setNonMilkQuantity(partnerQuantityBean.getNonMilkQuantity());
                }
            }
            partnerPaymentsBeans.add(partnerPaymentsBean);
        }
        return partnerPaymentsBeans;
    }

    public List<PartnerDetailBean> getPartnerDetails() {
        String partnerDetailQuery = "SELECT partner_detail.partner_name, partner_detail.mobile_number, partner.partner_charges, " +
            "partner.user_name, partner.password, building.id, COUNT(child_building.id) AS building_count FROM building " +
            "LEFT JOIN (SELECT id, partner, parent_partner FROM building) AS child_building ON child_building.parent_partner = building.partner " +
            "JOIN partner ON partner.id = building.partner " +
            "JOIN partner_detail ON partner_detail.id = partner.partner_detail " +
            "GROUP BY building.id";
        List<PartnerDetailBean> partnerDetailBeans = entityManager.createNativeQuery(partnerDetailQuery, "PartnerDetailBean").getResultList();
        return partnerDetailBeans;
    }

    public PartnerDetailBean getPartnerDetails(PartnerLogin partnerLogin) {
        String partnerDetailQuery = "SELECT partner_detail.partner_name, partner_detail.mobile_number, partner.partner_charges, " +
            "partner.user_name, partner.password, building.id, COUNT(child_building.id) AS building_count FROM building " +
            "LEFT JOIN (SELECT id, partner, parent_partner FROM building) AS child_building ON child_building.parent_partner = building.partner " +
            "JOIN partner ON partner.id = building.partner " +
            "JOIN partner_detail ON partner_detail.id = partner.partner_detail " +
            "WHERE partner.user_name = :userName AND partner.password = :password " +
            "GROUP BY building.id";
        List<PartnerDetailBean> partnerDetailBeans = entityManager.createNativeQuery(partnerDetailQuery, "PartnerDetailBean").setParameter("userName", partnerLogin.getUserName().trim()).setParameter("password", partnerLogin.getPassword().trim()).getResultList();
        PartnerDetailBean partnerDetailBean = partnerDetailBeans.get(0);
        return partnerDetailBean;
    }

    public PartnerDashboardBean fetchPartnerDashboard(PartnerLogin partnerLogin) {

        PartnerDashboardBean partnerDashboardBean = new PartnerDashboardBean();
        partnerDashboardBean.setStatus(0); partnerDashboardBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        NoAuthLogin noAuthLogin = new NoAuthLogin(); noAuthLogin.setDateString(partnerLogin.getDateString());

        if(!StringUtils.isEmpty(partnerLogin.getUserName()) && !StringUtils.isEmpty(partnerLogin.getPassword())) {

            Partner partner = partnerWebLogin(partnerLogin.getUserName().trim(), partnerLogin.getPassword().trim());

            if (partner == null) {
                debugLogger.debug(TAG + Constants.INVALID_PARTNER_CREDENTIALS);
                partnerDashboardBean.setMessage(Constants.INVALID_PARTNER_CREDENTIALS); return partnerDashboardBean;
            } else {
                PartnerDetailBean partnerDetailBean = getPartnerDetails(partnerLogin);
                StoreMaster storeMaster = storeService.fetchStore(partnerLogin, partnerLogin.getIsPartner(), false);
                if(storeMaster.getStatus() == 0) { storeMaster = storeService.fetchStore(partnerLogin, !partnerLogin.getIsPartner(), false); }
                AnalyticsMaster analyticsMaster = analyticsService.fetchAnalytics(partnerLogin, partnerLogin.getIsPartner());
                if(analyticsMaster.getStatus() == 0) { analyticsMaster = analyticsService.fetchAnalytics(partnerLogin, !partnerLogin.getIsPartner()); }
                UserDetailBean userDetailBean = userService.fetchUserDetails(partnerLogin, partnerLogin.getIsPartner());
                if(userDetailBean.getStatus() == 0) { userDetailBean = userService.fetchUserDetails(partnerLogin, !partnerLogin.getIsPartner()); }
                partnerDetailBean.setBuildingBeanList(buildingService.findAllBuildings(partnerLogin, partnerLogin.getIsPartner()));
                partnerDashboardBean.setStoreMaster(storeMaster);
                partnerDashboardBean.setAnalyticsMaster(analyticsMaster);
                partnerDashboardBean.setInventoryMaster(inventoryService.fetchInventory(noAuthLogin));
                partnerDashboardBean.setTransactionLogMaster(transactionLogService.fetchTransactionLogs(partnerLogin, partnerLogin.getIsPartner()));
                partnerDashboardBean.setUserDetailBean(userDetailBean);
                partnerDashboardBean.setPartnerDetailBean(partnerDetailBean);
                partnerDashboardBean.setPartnerName(partner.getPartnerDetail().getPartnerName() + " ( " + partner.getPartnerDetail().getMobileNumber() + " )");
                partnerDashboardBean.setDate(partnerLogin.getDateString()); partnerDashboardBean.setStatus(1);
                partnerDashboardBean.setMessage("Partner Dashboard successfully fetched"); return partnerDashboardBean;
            }

        } else {
            debugLogger.debug(TAG + "Missing userName or password for Partner");
            partnerDashboardBean.setMessage("Missing userName or password for Partner"); return partnerDashboardBean;
        }

    }

}
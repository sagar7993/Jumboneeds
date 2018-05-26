package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.*;
import com.jumboneeds.repositories.UserRepository;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class UserService {
    private static final String TAG = "UserService : ";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private BillingMasterService billingMasterService;

    @Autowired
    private FilterSubscriptionBeanService filterSubscriptionBeanService;

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public User findById(String userId){
        return userRepository.findById(userId);
    }

    public User findByMobileNumber(String mobileNumber){
        return userRepository.findFirstByMobileNumber(mobileNumber);
    }

    public List<User> findByBlock(Block block){
        return userRepository.findByBlock(block);
    }

    public LoginResultBean login(LoginBean loginBean){
        User retrievedUser = userRepository.findFirstByMobileNumber(loginBean.getMobileNumber());

        LoginResultBean loginResultBean = new LoginResultBean();

        if(retrievedUser == null) {
            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.NOT_A_REGISTERED_USER);
            return loginResultBean;
        } else {
            if(retrievedUser.getBillingMasterId() == null) {
                debugLogger.debug(TAG + "BillingMaster wasn't created for User : " + retrievedUser.getId());

                boolean isSuccess = createUserBillingMaster(retrievedUser);

                if(!isSuccess){
                    loginResultBean.setStatus(0);
                    loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                    return loginResultBean;
                }
            }

            try {
                userRepository.updateDeviceAndVersion(loginBean.getAppVersion(), loginBean.getDeviceType(), loginBean.getFcmAndroidToken(), retrievedUser.getId());

                loginResultBean = new LoginResultBean(retrievedUser);
                loginResultBean.setStatus(1);
                loginResultBean.setMessage(Constants.LOGIN_SUCCESSFUL);
                return loginResultBean;
            } catch (Exception e){
                errorLogger.error(TAG + "Version and Device update failed for User : " + loginResultBean.getUserId());

                loginResultBean.setStatus(0);
                loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                return loginResultBean;
            }
        }
    }

    public LoginResultBean signUp(User user){
        LoginResultBean loginResultBean = new LoginResultBean();

        User retrievedMobileNumberUser = userRepository.findFirstByMobileNumber(user.getMobileNumber());

        if(retrievedMobileNumberUser != null) {
            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.REGISTERED_MOBILE_NUMBER);
            return loginResultBean;
        }

        User retrievedEmailUser = userRepository.findFirstByEmail(user.getEmail());

        if(retrievedEmailUser != null) {
            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.REGISTERED_EMAIL);
            return loginResultBean;
        }

        try {
            user.setStatus(1);

            User savedUser = userRepository.save(user);

            boolean isSuccess = createUserBillingMaster(savedUser);

            if(!isSuccess){
                loginResultBean.setStatus(0);
                loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                return loginResultBean;
            }

            loginResultBean = new LoginResultBean(savedUser);
            loginResultBean.setStatus(1);
            return loginResultBean;
        } catch (Exception e) {
            errorLogger.error(TAG + "User creation failed for User with Email : " + user.getEmail() + " & " + " Mobile Number : " + user.getMobileNumber());

            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return loginResultBean;
        }
    }

    private boolean createUserBillingMaster(User user) {
        BillingMaster billingMaster = new BillingMaster();
        billingMaster.setCurrent(true);
        billingMaster.setStartDate(DateOperations.getTodayStartDate());
        billingMaster.setPaymentDate(DateOperations.getTodayStartDate());
        billingMaster.setUser(user);

        try {
            BillingMaster savedBillingMaster =  billingMasterService.save(billingMaster);

            try {
                userRepository.updateBillingMasterId(savedBillingMaster.getId(), user.getId());

                return true;
            } catch (Exception e){
                errorLogger.error(TAG + "BillingMaster Id update failed for User : " + user.getId());

                return false;
            }
        } catch (Exception e){
            errorLogger.error(TAG + "Billing Master creation failed for User : " + user.getId());

            return false;
        }
    }

    public StatusBean updateBuildingAndFlat(UpdateUserBuildingBean updateUserBuildingBean) {
        User retrievedUser = userRepository.findById(updateUserBuildingBean.getUserId());

        StatusBean statusBean = new StatusBean();

        if(retrievedUser == null) {
            errorLogger.error(TAG + "Tried to update Building and Flat for non existing User : " + updateUserBuildingBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        Building retrievedBuilding = buildingService.findById(updateUserBuildingBean.getBuildingId());

        if(retrievedBuilding == null) {
            errorLogger.error(TAG + "Tried to find non existing Building : " + updateUserBuildingBean.getBuildingId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        Block retrievedBlock = blockService.findById(updateUserBuildingBean.getBlockId());

        if(blockService.countByBuilding(retrievedBuilding) != 0 && retrievedBlock == null) {
            errorLogger.error(TAG + "Tried to find non existing Block : " + updateUserBuildingBean.getBlockId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        try {
            userRepository.updateBuildingAndFlat(updateUserBuildingBean.getFlat().trim(), retrievedBlock, retrievedBuilding, updateUserBuildingBean.getUserId());

            statusBean.setStatus(1);
            return statusBean;
        } catch (Exception e) {
            errorLogger.error(TAG + "Building and Flat update failed for User : " + retrievedUser.getId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }
    }

    public StatusBean updateProfile(UpdateProfileBean updateProfileBean) {
        User retrievedUser = userRepository.findById(updateProfileBean.getUserId());

        StatusBean statusBean = new StatusBean();

        if(retrievedUser == null) {
            errorLogger.error(TAG + "Tried to update profile of non existing User : " + updateProfileBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        } else {
            User retrievedMobileNumberUser = userRepository.findFirstByMobileNumber(updateProfileBean.getMobileNumber());

            if(retrievedMobileNumberUser != null) {
                statusBean.setStatus(0);
                statusBean.setMessage(Constants.REGISTERED_MOBILE_NUMBER);
                return statusBean;
            }

            User retrievedEmailUser = userRepository.findFirstByEmail(updateProfileBean.getEmail());

            if(retrievedEmailUser != null) {
                statusBean.setStatus(0);
                statusBean.setMessage(Constants.REGISTERED_EMAIL);
                return statusBean;
            }

            try {
                userRepository.updateProfile(updateProfileBean.getMobileNumber(), updateProfileBean.getEmail(), updateProfileBean.getUserId());

                statusBean.setStatus(1);
                return statusBean;
            } catch (Exception e) {
                errorLogger.error(TAG + "Profile Update failed for User with Email : " + retrievedUser.getEmail() + " & " + " Mobile Number : " + retrievedUser.getMobileNumber());

                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                return statusBean;
            }
        }
    }

    public StatusBean updateNotificationToken(NotificationTokenBean notificationTokenBean) {
        User retrievedUser = userRepository.findById(notificationTokenBean.getUserId());

        StatusBean statusBean = new StatusBean();

        if(retrievedUser == null) {
            errorLogger.error(TAG + "Tried to update notification token of non existing User : " + notificationTokenBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        } else {
            try {
                if (Constants.DEVICE_TYPE_ANDROID.equals(retrievedUser.getDeviceType())){
                    userRepository.updateFcmAndroidToken(notificationTokenBean.getToken(), retrievedUser.getId());
                }

                statusBean.setStatus(1);
                return statusBean;
            } catch (Exception e) {
                if (Constants.DEVICE_TYPE_ANDROID.equals(retrievedUser.getDeviceType())){
                    errorLogger.error(TAG + "FCM Android Token update failed for User : " + retrievedUser.getId());
                }

                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                return statusBean;
            }
        }
    }

    public List<RefundUserBean> findUserForRefund(User user) {
        String query = "SELECT user.id, user.user_name, building.building_name, block.block_name, " +
                "user.flat, user.mobile_number, user.email, billingMaster.amount FROM user user " +
                "LEFT JOIN billing_master billingMaster ON billingMaster.id = user.billing_master_id " +
                "LEFT JOIN building building ON building.id = user.building " +
                "LEFT JOIN block block ON block.id = user.block WHERE ";
        if(!StringUtils.isEmpty(user.getId())) {
            query += "user.id = '" + user.getId() + "'";
        }
        else if(!StringUtils.isEmpty(user.getFlat())) {
            if(user.getBuilding() != null && !StringUtils.isEmpty(user.getBuilding().getId())) {
                query += "user.building = '" + user.getBuilding().getId() + "' AND user.flat LIKE '%" + user.getFlat() + "%'";
            } else {
                query += "user.flat LIKE '%" + user.getFlat() + "%'";
            }
        }
        else if(!StringUtils.isEmpty(user.getMobileNumber())) {
            query += "user.mobile_number = '" + user.getMobileNumber() + "'";
        }
        else {
            return new ArrayList<>();
        }
        List<RefundUserBean> users = entityManager.createNativeQuery(query, "RefundUserBean").getResultList();
        return users;
    }

    public List<User> findByBuildings(List<Building> buildings){
        if(buildings != null && buildings.size() > 0){
            return userRepository.findByBuildings(buildings);
        } else {
            return new ArrayList<>();
        }
    }

    public List<User> findUsersWithBalanceBelowMinimumBuildingAmount() {
        return userRepository.findUsersWithBalanceBelowMinimumBuildingAmount();
    }

    public Map<Long, List<SubscriptionBean>> calendar(String userId) {
        Map<Long, List<SubscriptionBean>> calendarSubscriptions = new HashMap<>();
        List<Date> dates = DateOperations.getWeekDatesList();
        for(Date date : dates) {
            List<FilteredSubscriptionBean> filteredSubscriptionBeans = filterSubscriptionBeanService.fetchFilteredSubscriptionsForUser(userId, date);
            List<SubscriptionBean> dateSubscriptions = new ArrayList<>();
            for (FilteredSubscriptionBean filteredSubscriptionBean : filteredSubscriptionBeans) {
                dateSubscriptions.add(new SubscriptionBean(filteredSubscriptionBean));
            }
            calendarSubscriptions.put(date.getTime(), dateSubscriptions);
        }
        return calendarSubscriptions;
    }

    public UserDetailBean fetchUserDetails(PartnerLogin partnerLogin, Boolean isPartner) {
        UserDetailBean userDetailBean = new UserDetailBean();
        userDetailBean.setStatus(0);
        userDetailBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        List<Building> buildings = new ArrayList<>();

        if(!StringUtils.isEmpty(partnerLogin.getUserName()) && !StringUtils.isEmpty(partnerLogin.getPassword())) {
            Partner partner = partnerService.partnerWebLogin(partnerLogin.getUserName().trim(), partnerLogin.getPassword().trim());

            if(partner == null){
                return userDetailBean;
            }

            if(isPartner){
                buildings = buildingService.findByParentPartner(partner.getId());
            } else {
                buildings.add(buildingService.findByPartner(partner.getId()));
            }

            if(CollectionUtils.isEmpty(buildings)){
                debugLogger.debug(TAG + "No Building(s) fond for Partner : " + partner.getId());
                return userDetailBean;
            }
        } else {
            debugLogger.debug(TAG + "Missing userName or password for Partner");
            return userDetailBean;
        }

        return getUserDetails(buildings, userDetailBean);

    }

    public UserDetailBean fetchUserDetails(AddProductBean addProductBean) {
        UserDetailBean userDetailBean = new UserDetailBean();
        userDetailBean.setStatus(0);
        userDetailBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        List<Building> buildings;

        List<String> buildingIds = Arrays.asList(addProductBean.getBuildingIdList().split("\\s*,\\s*"));
        List<String> partnerIds = Arrays.asList(addProductBean.getPartnerIdList().split("\\s*,\\s*"));

        if (buildingIds.size() == 1 && buildingIds.get(0).equals("0") && partnerIds.size() == 1 && partnerIds.get(0).equals("0")) {
            buildings = buildingService.fetchAll();
        } else {
            if (buildingIds.size() == 1 && buildingIds.get(0).equals("0") && partnerIds.size() > 0 && !partnerIds.get(0).equals("0")) {
                buildings = buildingService.fetchBuildingsByParentPartnerIdList(partnerIds);
            }
            else if (partnerIds.size() == 1 && partnerIds.get(0).equals("0") && buildingIds.size() > 0 && !buildingIds.get(0).equals("0")) {
                buildings = buildingService.fetchBuildingsByIdList(buildingIds);
            } else {
                return userDetailBean;
            }
        }
        return getUserDetails(buildings, userDetailBean);
    }

    public UserDetailBean getUserDetails(List<Building> buildings, UserDetailBean userDetailBean) {

        List<String> buildingIdList = new ArrayList<>(); String title = "";

        for(Building building : buildings) {
            buildingIdList.add(building.getId()); title += ", " + building.getBuildingName();
        }

        String activeUserDetailsQuery = "SELECT user.id, user.user_name, building.building_name, block.block_name, user.flat, " +
                "user.email, user.mobile_number, user.status, billing_master.amount, COUNT(subscription.id) AS subscription_count FROM user " +
                "JOIN building ON building.id = user.building " +
                "LEFT JOIN block ON block.id = user.block " +
                "JOIN billing_master ON billing_master.id = user.billing_master_id " +
                "JOIN subscription ON subscription.user = user.id WHERE building.id IN :buildingIdList " +
//              "AND CURRENT_DATE BETWEEN subscription.start_date AND subscription.end_date " +
                "GROUP BY user.id ORDER BY building.building_name, block.block_name, user.flat DESC";
        List<UserBean> activeUserDetailsList = entityManager.createNativeQuery(activeUserDetailsQuery, "UserDetailsList").setParameter("buildingIdList", buildingIdList).getResultList();

        String inActiveUserDetailsQuery = "SELECT user.id, user.user_name, building.building_name, block.block_name, user.flat, " +
                "user.email, user.mobile_number, user.status, billing_master.amount, 0 AS subscription_count FROM user " +
                "LEFT JOIN building ON building.id = user.building " +
                "LEFT JOIN block ON block.id = user.block " +
                "JOIN billing_master ON billing_master.id = user.billing_master_id " +
                "WHERE building.id IN :buildingIdList AND user.id NOT IN (SELECT user FROM subscription) " +
                "GROUP BY user.id ORDER BY building.building_name, block.block_name, user.flat DESC";
        List<UserBean> inActiveUserDetailsList = entityManager.createNativeQuery(inActiveUserDetailsQuery, "UserDetailsList").setParameter("buildingIdList", buildingIdList).getResultList();

        userDetailBean.setStatus(1); userDetailBean.setMessage("User Details Fetched Successfully");
        userDetailBean.setActiveUserBeans(activeUserDetailsList);
        userDetailBean.setInActiveUserBeans(inActiveUserDetailsList);
        userDetailBean.setTitle(title.substring(2)); return userDetailBean;

    }

    public StatusBean updateUserDetails(User user) {

        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        UpdateProfileBean updateProfileBean = new UpdateProfileBean(user);

        User retrievedUser = userRepository.findById(updateProfileBean.getUserId());

        if(retrievedUser == null) {
            errorLogger.error(TAG + "Tried to update profile of non existing User : " + updateProfileBean.getUserId());
            return statusBean;
        } else {

            User retrievedMobileNumberUser = userRepository.findFirstByMobileNumber(updateProfileBean.getMobileNumber());
            if(retrievedMobileNumberUser != null && !retrievedUser.getMobileNumber().equals(updateProfileBean.getMobileNumber())) {
                statusBean.setMessage(Constants.REGISTERED_MOBILE_NUMBER);
                return statusBean;
            }
            User retrievedEmailUser = userRepository.findFirstByEmail(updateProfileBean.getEmail());
            if(retrievedEmailUser != null && !retrievedUser.getEmail().equals(updateProfileBean.getEmail())) {
                statusBean.setMessage(Constants.REGISTERED_EMAIL);
                return statusBean;
            }

            try {
                userRepository.updateProfile(updateProfileBean.getMobileNumber(), updateProfileBean.getEmail(), updateProfileBean.getUserName(), updateProfileBean.getFlat(), updateProfileBean.getUserId());
                statusBean.setStatus(1); statusBean.setMessage("User Successfully Updated");
                return statusBean;
            } catch (Exception e) {
                errorLogger.error(TAG + "Profile Update failed for User with Email : " + retrievedUser.getEmail() + " & " + " Mobile Number : " + retrievedUser.getMobileNumber());
                return statusBean;
            }
        }

    }

}
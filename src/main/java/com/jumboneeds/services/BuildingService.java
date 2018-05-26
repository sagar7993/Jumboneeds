package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.*;
import com.jumboneeds.repositories.BuildingRepository;
import com.jumboneeds.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingService {
    private static final String TAG = "BuildingService : ";

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMasterService productMasterService;

    @Autowired
    private PartnerDetailService partnerDetailService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(Building building){
        Partner retrievedPartner = partnerService.findById(building.getPartner().getId());
        building.setPartner(retrievedPartner);

        Partner retrievedParentPartner = partnerService.findById(building.getParentPartner().getId());
        building.setParentPartner(retrievedParentPartner);

        try {
            buildingRepository.save(building);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Building for Partner :" + retrievedPartner.getId());
            return;
        }
    }

    public Building findById(String buildingId){
        return buildingRepository.findById(buildingId);
    }

    public Building findById(Building building){
        Building retrievedBuilding = buildingRepository.findById(building.getId());
        List<Building> buildings = new ArrayList<>(); buildings.add(retrievedBuilding);
        List<ProductBean> productBeans = productMasterService.productMasterListNotAddedInBuilding(buildings);
        retrievedBuilding.setProductBeanList(productBeans);
        return retrievedBuilding;
    }

    public BuildingsBean getAllActiveBuildings(){
        BuildingsBean buildingsBean = new BuildingsBean();

        try {
            buildingsBean.setBuildingBeans(buildingRepository.fetchAllActiveBuildingBeans());
            buildingsBean.setStatus(1);
        } catch (Exception e){
            debugLogger.debug(TAG + "Error in fetching all active BuildingBeans");

            buildingsBean.setStatus(0);
            buildingsBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }
        return buildingsBean;
    }

    public List<BuildingBean> getActiveBuildings(){
        return buildingRepository.fetchAllActiveBuildingBeans();
    }

    public List<AddBuildingBean> findAllBuildings(PartnerLogin partnerLogin, Boolean isPartner){

        List<AddBuildingBean> buildingProductMasterUserCountList; List<Building> buildings = new ArrayList<>();

        String selectSubQuery = "SELECT building.id, building.building_name, building.address, partner_detail.partner_name, partner_detail.mobile_number, " +
            "partner.partner_charges, building.minimum_allowed_amount, partner.balance, partner.user_name, partner.password, building.status, " +
            "COUNT(product_master.id) AS product_count, (SELECT COUNT(id) FROM block where building = building.id) AS block_count, " +
            "(SELECT COUNT(id) FROM user where building = building.id) AS user_count FROM building ";

        String joinSubQuery = "LEFT JOIN product ON product.building = building.id " +
            "LEFT JOIN partner ON partner.id = building.partner " +
            "LEFT JOIN partner_detail ON partner_detail.id = partner.partner_detail " +
            "LEFT JOIN product_master ON product_master.id = product.product_master AND product.product_master = product_master.id ";

        String groupBySubQuery = "GROUP BY building.id";

        if(partnerLogin == null) {

            String buildingProductMasterUserCountQuery = selectSubQuery + joinSubQuery + groupBySubQuery;
            buildingProductMasterUserCountList = entityManager.createNativeQuery(buildingProductMasterUserCountQuery, "BuildingProductMasterUserCountList").getResultList();

        } else {

            Partner partner = partnerService.partnerWebLogin(partnerLogin.getUserName().trim(), partnerLogin.getPassword().trim());
            buildings = buildingRepository.findByParentPartner(partner.getId());
            List<String> buildingIdList = new ArrayList<>(); String whereSubQuery = "WHERE building.id IN :buildingIdList ";
            for(Building building : buildings) {
                buildingIdList.add(building.getId());
            }
            if(buildingIdList.size() == 0) {
                buildingIdList.add(buildingRepository.findByPartner(partner.getId()).getId());
            }
            String buildingProductMasterUserCountQuery = selectSubQuery + joinSubQuery + whereSubQuery + groupBySubQuery;
            buildingProductMasterUserCountList = entityManager.createNativeQuery(buildingProductMasterUserCountQuery, "BuildingProductMasterUserCountList").setParameter("buildingIdList", buildingIdList).getResultList();

        }

        return buildingProductMasterUserCountList;

    }

    public BuildingBean findBuildingBeanById(String buildingId){
        return buildingRepository.findBuildingBeanById(buildingId);
    }

    public BuildingsBean fetchBuildingBeansByUser(IdBean idBean){
        BuildingsBean buildingsBean = new BuildingsBean();

        User retrievedUser = userService.findById(idBean.getId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id :" + idBean.getId());
            buildingsBean.setStatus(0);
            buildingsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return buildingsBean;
        }

        if(retrievedUser.getBuilding() != null && !StringUtils.isEmpty(retrievedUser.getFlat())){
            buildingsBean.setBuildingId(retrievedUser.getBuilding().getId());
            buildingsBean.setFlat(retrievedUser.getFlat());
        }

        if (retrievedUser.getBlock() != null){
            buildingsBean.setBlockId(retrievedUser.getBlock().getId());
        }

        buildingsBean.setBuildingBeans(buildingRepository.fetchAllActiveBuildingBeans());

        buildingsBean.setStatus(1);
        return buildingsBean;
    }

    public Building findByPartner(String partnerId){
        return buildingRepository.findByPartner(partnerId);
    }

    public BuildingsBean findBuildingBeansByParentPartner(IdBean idBean){
        BuildingsBean buildingsBean = new BuildingsBean();

        Partner retrievedPartner = partnerService.findById(idBean.getId());

        if(retrievedPartner == null){
            debugLogger.debug(TAG + "No Partner found foe Id : " + idBean.getId());

            buildingsBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return buildingsBean;
        }

        if (retrievedPartner.getAdmin()){
            buildingsBean.setBuildingBeans(buildingRepository.fetchAllActiveBuildingBeans());
        } else {
            buildingsBean.setBuildingBeans(buildingRepository.findBuildingBeansByParentPartner(idBean.getId()));
        }

        buildingsBean.setStatus(1);
        return buildingsBean;
    }

    public List<Building> findByParentPartner(String partnerId){
        return buildingRepository.findByParentPartner(partnerId);
    }

    public List<Building> fetchAll() {
        return buildingRepository.fetchAll();
    }

    public List<Building> fetchBuildingsByParentPartnerIdList(List<String> partnerIdList) {
        return buildingRepository.fetchBuildingsByParentPartnerIdList(partnerIdList);
    }

    public List<Building> fetchBuildingsByIdList(List<String> buildingIdList) {
        return buildingRepository.fetchBuildingsByIdList(buildingIdList);
    }

    public List<BuildingBean> buildingListNotHavingProductMaster(String productMasterId) {
        ProductMaster productMaster = productMasterService.findById(productMasterId);
        return buildingRepository.buildingListNotHavingProductMaster(productMaster);
    }

    public List<Building> findBuildingsByBuildingAndPartnerIdList(List<String> buildingIds, List<String> partnerIds) {
        List<Building> buildings = new ArrayList<>();
        if (buildingIds.size() == 1 && buildingIds.get(0).equals("0") && partnerIds.size() == 1 && partnerIds.get(0).equals("0")) {
            buildings = buildingRepository.fetchAll();
        } else {
            if (buildingIds.size() == 1 && buildingIds.get(0).equals("0") && partnerIds.size() > 0 && !partnerIds.get(0).equals("0")) {
                buildings = buildingRepository.fetchBuildingsByParentPartnerIdList(partnerIds);
            }
            if (partnerIds.size() == 1 && partnerIds.get(0).equals("0") && buildingIds.size() > 0 && !buildingIds.get(0).equals("0")) {
                buildings = buildingRepository.fetchBuildingsByIdList(buildingIds);
            }
        }
        return buildings;
    }

    @Transactional
    public StatusBean addBuilding(AddBuildingBean addBuildingBean) {

        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG); Integer count = 0;

        Partner partner = new Partner(); Building building = new Building();
        Building buildingCopy = buildingRepository.findById(addBuildingBean.getBuildingCopy().getId());

        List<Building> buildingCopies = new ArrayList<>(); buildingCopies.add(buildingCopy);
        List<Product> products = productService.findByBuildings(buildingCopies);
        List<Block> blocks = addBuildingBean.getBlockList();

        String buildingName = addBuildingBean.getBuildingName().trim();
        String userName = addBuildingBean.getUserName().trim();

        partner.setBuildingName(buildingName);
        partner.setUserName(userName);

        List<Partner> uniquePartner = partnerService.checkUniquePartner(partner.getBuildingName(), partner.getUserName());

        if(CollectionUtils.isEmpty(uniquePartner)) {

            partner.setBalance(0.0);
            partner.setPassword(addBuildingBean.getPassword().trim());

            if(addBuildingBean.getParentPartnerId().equals("0")) {

                String mobileNumber = addBuildingBean.getMobileNumber().trim();
                String partnerName = addBuildingBean.getPartnerName().trim();
                PartnerDetail uniquePartnerDetail = partnerDetailService.findByMobileNumber(mobileNumber);

                if (uniquePartnerDetail == null) {

                    PartnerDetail partnerDetail = new PartnerDetail();
                    partnerDetail.setPartnerName(partnerName);
                    partnerDetail.setMobileNumber(mobileNumber);
                    partnerDetail.setAlternateNumber(mobileNumber);

                    try {
                        partnerDetailService.save(partnerDetail);
                    } catch (Exception e) {
                        errorLogger.error(TAG + "Error in creating PartnerDetail for Building : " + addBuildingBean.getBuildingName());
                        return statusBean;
                    }

                    partner.setPartnerCharges(addBuildingBean.getPartnerCharges());
                    partner.setPartnerDetail(partnerDetail);

                    try {
                        partnerService.save(partner);
                    } catch (Exception e){
                        errorLogger.error(TAG + "Error in creating Partner for Building : " + addBuildingBean.getBuildingName());
                        return statusBean;
                    }

                    try {
                        partnerService.updateParentPartner(partner.getId(), partner.getId());
                    } catch (Exception e){
                        errorLogger.error(TAG + "Error in updating Partner : " + partner.getId());
                        return statusBean;
                    }

                    building.setParentPartner(partner);

                } else {
                    errorLogger.error(TAG + "PartnerDetail MobileNumber must be unique : " + mobileNumber);
                    statusBean.setMessage("Partner Mobile Number is already registered.");
                    return statusBean;
                }

            } else {

                Partner parentPartner = partnerService.findById(addBuildingBean.getParentPartnerId());
                partner.setPartnerCharges(parentPartner.getPartnerCharges());
                partner.setPartnerDetail(parentPartner.getPartnerDetail());
                partner.setParentPartnerId(parentPartner.getId());

                try {
                    partnerService.save(partner);
                } catch (Exception e){
                    errorLogger.error(TAG + "Error in creating Partner for Building : " + addBuildingBean.getBuildingName());
                    return statusBean;
                }

                building.setParentPartner(parentPartner);

            }

            building.setAddress(addBuildingBean.getAddress().trim());
            building.setBuildingName(buildingName);
            building.setLat(addBuildingBean.getLat());
            building.setLng(addBuildingBean.getLng());
            building.setMinimumAllowedAmount(addBuildingBean.getMinimumAllowedAmount());
            building.setPartnerName(partner.getPartnerDetail().getPartnerName());
            building.setPartner(partner);
            building.setStatus(addBuildingBean.getStatus());

            try {
                buildingRepository.save(building);
            } catch (Exception e){
                errorLogger.error(TAG + "Error in creating Building : " + building.getBuildingName());
                return statusBean;
            }

            if(CollectionUtils.isEmpty(blocks)) {

                Block block = new Block();
                block.setActive(true);
                block.setBlockName("~");
                block.setBuilding(building);

                try {
                    blockService.save(block);
                } catch (Exception e){
                    errorLogger.error(TAG + "Error in creating Block for Building :" + building.getId());
                    return statusBean;
                }

            } else {

                for (Block b : blocks) {
                    Block block = new Block();
                    block.setActive(b.getActive());
                    block.setBlockName(b.getBlockName().trim());
                    block.setBuilding(building);

                    try {
                        blockService.save(block);
                    } catch (Exception e){
                        errorLogger.error(TAG + "Error in creating Block for Building :" + building.getId());
                        return statusBean;
                    }
                }
            }

            for(Product product : products) {
                Product p = new Product();
                p.setFulfilledByPartner(product.getFulfilledByPartner());
                p.setProductAlias(product.getProductAlias());
                if(product.getFlashStatus() == 0) {
                    p.setProductUnitPrice(product.getProductUnitPrice());
                } else {
                    p.setProductUnitPrice(product.getStrikePrice());
                    p.setSpecialText(product.getSpecialText());
                    p.setStrikePrice(product.getProductUnitPrice());
                    p.setFlashStatus(1);
                }
                p.setStatus(product.getStatus());
                p.setBuilding(building);
                p.setProductMaster(product.getProductMaster());
                p.getProductMaster().setProductSubCategory(product.getProductMaster().getProductSubCategory());

                try {
                    productService.save(p); count ++;
                } catch (Exception e){
                    errorLogger.error(TAG + "Error in creating Product for Building :" + building.getId());
                    return statusBean;
                }

            }

            if (count == products.size()) {
                statusBean.setStatus(1); statusBean.setMessage("Building Added Successfully and " + count + " products added to it.");
            } else {
                statusBean.setMessage("Building Added Successfully but only " + count + " products added to it out of " + products.size());
            }

        } else {

            if(uniquePartner.get(0).getUserName().equalsIgnoreCase(partner.getUserName())) {
                statusBean.setMessage(Constants.REGISTERED_PARTNER_NAME);
            } else if(uniquePartner.get(0).getBuildingName().equalsIgnoreCase(partner.getBuildingName())) {
                statusBean.setMessage(Constants.REGISTERED_BUILDING_NAME);
            }

        }

        return statusBean;

    }

    @Transactional
    public StatusBean editBuilding(AddBuildingBean addBuildingBean) {

        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        String buildingName = addBuildingBean.getBuildingName().trim();
        String userName = addBuildingBean.getUserName().trim();

        List<Partner> uniquePartnerUserName = partnerService.checkUniquePartnerUserName(userName);
        List<Partner> uniquePartnerBuildingName = partnerService.checkUniquePartnerBuildingName(buildingName);

        Building building = buildingRepository.findById(addBuildingBean.getBuildingId());
        Partner partner = building.getPartner();
        PartnerDetail partnerDetail = partner.getPartnerDetail();

        if((CollectionUtils.isEmpty(uniquePartnerUserName) || userName.equalsIgnoreCase(uniquePartnerUserName.get(0).getUserName()))
                && (CollectionUtils.isEmpty(uniquePartnerBuildingName) || buildingName.equalsIgnoreCase(uniquePartnerBuildingName.get(0).getBuildingName()))) {

            String mobileNumber = addBuildingBean.getMobileNumber().trim();
            String partnerName = addBuildingBean.getPartnerName().trim();
            PartnerDetail uniquePartnerDetail = partnerDetailService.findByMobileNumber(mobileNumber);

            building.setAddress(addBuildingBean.getAddress().trim());
            building.setBuildingName(buildingName);
            building.setLat(addBuildingBean.getLat());
            building.setLng(addBuildingBean.getLng());
            building.setMinimumAllowedAmount(addBuildingBean.getMinimumAllowedAmount());
            building.setStatus(addBuildingBean.getStatus());
            building.setPartnerName(partnerName);

            partner.setBuildingName(buildingName);
            partner.setPassword(addBuildingBean.getPassword().trim());
            partner.setUserName(userName);
            partner.setBalance(addBuildingBean.getBalance());

            if (addBuildingBean.getParentPartnerId().equals("0")) {

                if (uniquePartnerDetail == null || partnerDetail.getMobileNumber() == mobileNumber) {
                    partnerDetail.setPartnerName(partnerName);
                    partnerDetail.setMobileNumber(mobileNumber);
                    partnerDetail.setAlternateNumber(mobileNumber);

                    try {
                        partnerDetailService.save(partnerDetail);
                    } catch (Exception e) {
                        errorLogger.error(TAG + "Error in creating PartnerDetail for Building : " + addBuildingBean.getBuildingName());
                        return statusBean;
                    }

                } else {
                    errorLogger.error(TAG + "PartnerDetail MobileNumber must be unique : " + mobileNumber);
                    statusBean.setMessage("Partner Mobile Number is already registered.");
                    return statusBean;
                }

                partner.setPartnerCharges(addBuildingBean.getPartnerCharges());
                partner.setParentPartnerId(partner.getId());
                building.setParentPartner(partner);

            } else {

                Partner parentPartner = partnerService.findById(addBuildingBean.getParentPartnerId());
                partner.setPartnerCharges(parentPartner.getPartnerCharges());
                partner.setPartnerDetail(parentPartner.getPartnerDetail());
                partner.setParentPartnerId(parentPartner.getId());
                building.setParentPartner(parentPartner);

            }

            try {
                partnerService.save(partner);
            } catch (Exception e){
                errorLogger.error(TAG + "Error in creating Building for : " + addBuildingBean.getBuildingName());
                return statusBean;
            }

            try {
                buildingRepository.save(building);
            } catch (Exception e){
                errorLogger.error(TAG + "Error in creating Partner for : " + addBuildingBean.getBuildingName());
                return statusBean;
            }

            statusBean.setStatus(1); statusBean.setMessage("Building / Partner updated successfully");

        } else {

            if(!CollectionUtils.isEmpty(uniquePartnerUserName) && !userName.equalsIgnoreCase(partner.getUserName())) {
                statusBean.setMessage(Constants.REGISTERED_PARTNER_NAME);
            } else if(!CollectionUtils.isEmpty(uniquePartnerBuildingName) && !buildingName.equalsIgnoreCase(partner.getBuildingName())) {
                statusBean.setMessage(Constants.REGISTERED_BUILDING_NAME);
            }

        }

        return statusBean;

    }

}
package com.jumboneeds.services;

import com.jumboneeds.beans.AddProductBean;
import com.jumboneeds.beans.ProductBean;
import com.jumboneeds.beans.ProductMasterBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.Product;
import com.jumboneeds.entities.ProductMaster;
import com.jumboneeds.entities.ProductSubCategory;
import com.jumboneeds.repositories.ProductMasterRepository;
import com.jumboneeds.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductMasterService {
    private static final String TAG = "ProductMasterService : ";

    @Autowired
    private ProductMasterRepository productMasterRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ProductSubCategoryService productSubCategoryService;

    @Autowired
    MailService mailService;

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(ProductMaster productMaster){
        productMasterRepository.save(productMaster);
    }

    public ProductMaster findById(String id){
        return productMasterRepository.findById(id);
    }

    public List<AddProductBean> findAll(){
        String productMasterBuildingCountQuery = "SELECT product_master.id, product_master.product_name, product_master.product_alias, " +
            "product_master.product_image_url, product_master.product_unit_size, product_master.status, product_master.weight, " +
            "product_sub_category.product_sub_category_name AS product_sub_category, product_category.product_category_name AS product_category, " +
            "COUNT(product.building) AS building_count FROM product_master " +
            "LEFT JOIN product ON product.product_master = product_master.id " +
            "LEFT JOIN building ON building.id = product.building " +
            "LEFT JOIN product_sub_category ON product_sub_category.id = product_master.product_sub_category " +
            "LEFT JOIN product_category ON product_category.id = product_sub_category.product_category " +
            "GROUP BY product_master.id";
        List<AddProductBean> productMasterBuildingCountList = entityManager.createNativeQuery(productMasterBuildingCountQuery, "ProductMasterBuildingCountList").getResultList();
        return productMasterBuildingCountList;
    }

    public List<ProductMaster> checkUniqueProductMaster(String productName, String productUnitSize) {
        return productMasterRepository.checkUniqueProductMaster(productName, productUnitSize);
    }

    public List<ProductBean> productMasterListNotAddedInBuilding(List<Building> buildingList) {
        return productMasterRepository.productMasterListNotAddedInBuilding(buildingList);
    }

    public List<ProductBean> productMasterListNotAddedInBuilding(String buildingId) {
        Building building = buildingService.findById(buildingId);
        return productMasterRepository.productMasterListNotAddedInBuilding(building);
    }

    public List<ProductMasterBean> fetchAll(){
        return productMasterRepository.fetchAll();
    }

    public void updateProductMasterStatus(Integer status, String id){
        productMasterRepository.updateProductMasterStatus(status, id);
    }

    @Transactional
    public StatusBean addProductMaster(AddProductBean addProductBean) {
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        String productName = addProductBean.getProductName().trim();
        String productUnitSize = addProductBean.getProductUnitSize().trim();
        String productImage = addProductBean.getProductImage().trim();

        List<ProductMaster> uniqueProduct = productMasterRepository.checkUniqueProductMaster(productName, productUnitSize);

        if(CollectionUtils.isEmpty(uniqueProduct)) {

            String productSubCategoryId = addProductBean.getProductSubCategory().getId(); Integer count = 0;
            ProductSubCategory productSubCategory = productSubCategoryService.findById(productSubCategoryId);

            List<String> buildingIds = Arrays.asList(addProductBean.getBuildingIdList().split("\\s*,\\s*"));
            List<String> partnerIds = Arrays.asList(addProductBean.getPartnerIdList().split("\\s*,\\s*"));

            List<Building> buildings = buildingService.findBuildingsByBuildingAndPartnerIdList(buildingIds, partnerIds);

            String OSName = System.getProperty("os.name");
            String filePath = OSName.startsWith("Windows") ? Constants.WINDOWS_PRODUCT_FILE_PATH : Constants.UBUNTU_PRODUCT_FILE_PATH;
            filePath += productSubCategory.getFolderName() + "/";

            String fileName = addProductBean.getFileName().trim(); String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            fileName = (productName + " " + productUnitSize).replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9.-]", "_").replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();

            try {
                if(mailService.saveImage(productImage, fileName, extension, filePath)) {
                    productImage = OSName.startsWith("Windows") ? Constants.LOCAL_PRODUCT_IMAGE_URL : Constants.JUMBONEEDS_PRODUCT_IMAGE_URL;
                    productImage += productSubCategory.getFolderName() + "/" + fileName + "." + extension;
                } else {
                    errorLogger.error(TAG + "Error in saving image for ProductMaster with name : " + productName + " : " + productUnitSize);
                    statusBean.setMessage("Failed to save Image."); return statusBean;
                }
            } catch (IOException e) {
                errorLogger.error(TAG + "Error in saving image for ProductMaster with name : " + productName + " : " + productUnitSize);
                statusBean.setMessage("Failed to save Image."); return statusBean;
            }

            ProductMaster productMaster = new ProductMaster();
            productMaster.setProductAlias(addProductBean.getProductAlias().trim());
            productMaster.setProductImageUrl(productImage);
            productMaster.setProductName(productName);
            productMaster.setProductUnitSize(productUnitSize);
            productMaster.setProductSubCategory(productSubCategory);
            productMaster.setStatus(1);
            productMaster.setWeight(99);

            try {
                productMasterRepository.save(productMaster);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in creating ProductMaster for " + productMaster.getProductName());
                return statusBean;
            }

            for (Building building : buildings) {

                Product product = new Product();
                product.setFulfilledByPartner(addProductBean.getFulfilledByPartner());
                product.setProductAlias(addProductBean.getProductAlias().trim());
                product.setProductUnitPrice(addProductBean.getProductUnitPrice());
                product.setStatus(1);
                product.setBuilding(building);
                product.setProductMaster(productMaster);
                product.getProductMaster().setProductSubCategory(productSubCategory);

                try {
                    productService.save(product); count++;
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in creating Product for Building :" + building.getId());
                    return statusBean;
                }

            }

            if (count == buildings.size()) {
                statusBean.setStatus(1);
                statusBean.setMessage("Product successfully added to all " + count + " buildings");
            } else {
                statusBean.setMessage("Product added to only " + count + " out of " + buildings.size() + " buildings");
            }

        } else {
            statusBean.setMessage(Constants.PRODUCT_MASTER_EXISTS);
        }

        return statusBean;

    }

    @Transactional
    public StatusBean editProductMaster(AddProductBean addProductBean) {
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        String productName = addProductBean.getProductName().trim();
        String productUnitSize = addProductBean.getProductUnitSize().trim();

        List<ProductMaster> uniqueProduct = productMasterRepository.checkUniqueProductMaster(productName, productUnitSize);

        if(CollectionUtils.isEmpty(uniqueProduct) || (productName.equalsIgnoreCase(uniqueProduct.get(0).getProductName()) && productUnitSize.equalsIgnoreCase(uniqueProduct.get(0).getProductUnitSize()))) {

            ProductMaster productMaster = productMasterRepository.findById(addProductBean.getProductMasterId());
            String productSubCategoryId = addProductBean.getProductSubCategory().getId();
            ProductSubCategory productSubCategory = productSubCategoryService.findById(productSubCategoryId);

            productMaster.setProductAlias(addProductBean.getProductAlias().trim());
            productMaster.setProductImageUrl(addProductBean.getProductImage().trim());
            productMaster.setProductName(productName);
            productMaster.setProductUnitSize(productUnitSize);
            productMaster.setStatus(addProductBean.getStatus());
            productMaster.setWeight(addProductBean.getWeight());
            productMaster.setProductSubCategory(productSubCategory);

            try {
                productMasterRepository.save(productMaster);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in updating ProductMaster for Id : " + productMaster.getId());
                return statusBean;
            }

            try {
                productService.updateProductAliasForProductByProductMaster(productMaster, productMaster.getProductAlias());
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in updating ProductAlias for ProductMaster Id : " + productMaster.getId());
                return statusBean;
            }

            statusBean.setStatus(1); statusBean.setMessage("ProductMaster updated successfully");

        } else {
            statusBean.setMessage(Constants.PRODUCT_MASTER_EXISTS);
        }

        return statusBean;

    }

}
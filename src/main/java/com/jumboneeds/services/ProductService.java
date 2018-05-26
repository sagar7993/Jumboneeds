package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.Product;
import com.jumboneeds.entities.ProductMaster;
import com.jumboneeds.entities.User;
import com.jumboneeds.repositories.ProductRepository;
import com.jumboneeds.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class ProductService {
    private static final String TAG = "ProductService : ";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMasterService productMasterService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(Product product){
        productRepository.save(product);
    }

    public Product findById(String id){
        return productRepository.findById(id);
    }

    public List<Product> findByBuildings(List<Building> buildings){
        return productRepository.findByBuildings(buildings);
    }

    public List<ProductSubCategoryBean> fetchProductSubCategoryBeansForBuilding(String buildingId){
        return productRepository.findProductSubCategoryBeansByBuilding(buildingId);
    }

    public List getBuildingProducts(IdBean idBean) {
        String buildingId = userService.findById(idBean.getId()).getBuilding().getId();
        List products = entityManager.createNativeQuery("SELECT product.id, productMaster.product_name, productMaster.product_unit_size, product.product_unit_price FROM product product LEFT JOIN product_master productMaster ON productMaster.id = product.product_master WHERE product.building = '" + buildingId + "'", "BuildingProductsList").getResultList();
        if(!CollectionUtils.isEmpty(products)) {
            return products;
        } else {
            return new ArrayList<>();
        }
    }

    public ProductSubCategoriesBean fetchProductSubCategoryBeansForUser(IdBean idBean){
        ProductSubCategoriesBean productSubCategoriesBean = new ProductSubCategoriesBean();

        User retrievedUser = userService.findById(idBean.getId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id :" + idBean.getId());
            productSubCategoriesBean.setStatus(0);
            productSubCategoriesBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return productSubCategoriesBean;
        }

        productSubCategoriesBean.setProductSubCategoryBeans(productRepository.findProductSubCategoryBeansByBuilding(retrievedUser.getBuilding().getId()));
        productSubCategoriesBean.setStatus(1);

        return productSubCategoriesBean;
    }

    public ProductBean findFlashSaleProductByBuilding(String buildingId){
        return productRepository.findFlashSaleProductByBuilding(buildingId);
    }

    public ProductsBean findByUser(ProductSubCategoryIdBean productSubCategoryIdBean){
        ProductsBean productsBean = new ProductsBean();

        User retrievedUser = userService.findById(productSubCategoryIdBean.getUserId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id :" + productSubCategoryIdBean.getUserId());
            productsBean.setStatus(0);
            productsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return productsBean;
        }

        List<Product> products = productRepository.findByBuilding(retrievedUser.getBuilding());

        Map<String, ProductSubCategoryBean> productSubCategoryBeanMap = new LinkedHashMap<>();

        for (Product product : products) {
            String key = product.getProductMaster().getProductSubCategory().getId();

            if(productSubCategoryBeanMap.containsKey(key)){
                List<ProductBean> productBeans = productSubCategoryBeanMap.get(key).getProductBeans();
                productBeans.add(new ProductBean(product));
            } else {
                ProductSubCategoryBean productSubCategoryBean = new ProductSubCategoryBean(product.getProductMaster().getProductSubCategory());
                productSubCategoryBean.getProductBeans().add(new ProductBean(product));
                productSubCategoryBeanMap.put(productSubCategoryBean.getId(), productSubCategoryBean);
            }
        }

        if (productSubCategoryIdBean.getProductSubCategoryId() != null) {
            for (String key : productSubCategoryBeanMap.keySet()) {
                if (key.equals(productSubCategoryIdBean.getProductSubCategoryId())){
                    productsBean.getProductSubCategoryBeans().add(0, productSubCategoryBeanMap.get(key));
                } else {
                    productsBean.getProductSubCategoryBeans().add(productSubCategoryBeanMap.get(key));
                }
            }
        } else {
            productsBean.getProductSubCategoryBeans().addAll(productSubCategoryBeanMap.values());
        }

        productsBean.setStatus(1);

        return productsBean;
    }

    public ProductsBean findByBuilding(String buildingId) {
        ProductsBean productsBean = new ProductsBean();
        productsBean.setStatus(0);
        productsBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Building building = buildingService.findById(buildingId);
        if(building == null) {
            debugLogger.debug(TAG + "No Building found for Id :" + buildingId);
            return productsBean;
        }
        List<ProductBean> productBeans = productRepository.findByBuilding(buildingId);
        productsBean.setBuildingName(building.getBuildingName());
        if(!CollectionUtils.isEmpty(productBeans)) {
            productsBean.setProductBeans(productBeans);
        }
        productsBean.setStatus(1);
        productsBean.setMessage("Successful");
        return productsBean;
    }

    public ProductsBean findByProductMaster(String productMasterId) {
        ProductsBean productsBean = new ProductsBean();
        productsBean.setStatus(0); productsBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        ProductMaster productMaster = productMasterService.findById(productMasterId);
        if(productMaster == null) {
            debugLogger.debug(TAG + "No ProductMaster found for Id :" + productMasterId);
            return productsBean;
        }
        List<ProductBean> productBeans = productRepository.findByProductMaster(productMasterId);
        productsBean.setProductName(productMaster.getProductName() + " (" + productMaster.getProductUnitSize() + ")");
        productsBean.setProductAlias(productMaster.getProductAlias());
        if(!CollectionUtils.isEmpty(productBeans)) {
            productsBean.setProductBeans(productBeans);
        }
        productsBean.setStatus(1); productsBean.setMessage("Successful");
        return productsBean;
    }

    public ProductsBean findByProductMasterAndBuilding(String productMasterId, String buildingId) {
        ProductsBean productsBean = new ProductsBean();
        productsBean.setStatus(0); productsBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        ProductMaster productMaster = productMasterService.findById(productMasterId);
        Building building = buildingService.findById(buildingId);
        if(productMaster == null) {
            debugLogger.debug(TAG + "No ProductMaster found for Id :" + productMasterId);
            return productsBean;
        }
        if(building == null) {
            debugLogger.debug(TAG + "No Building found for Id :" + buildingId);
            return productsBean;
        }
        List<ProductBean> productBeans = productRepository.findByProductMasterAndBuilding(productMasterId, buildingId);
        productsBean.setBuildingName(building.getBuildingName());
        productsBean.setProductName(productMaster.getProductName() + " (" + productMaster.getProductUnitSize() + ")");
        productsBean.setProductAlias(productMaster.getProductAlias());
        if(!CollectionUtils.isEmpty(productBeans)) {
            productsBean.setProductBeans(productBeans);
        }
        productsBean.setStatus(1); productsBean.setMessage("Successful");
        return productsBean;
    }

    @Transactional
    public void updateProductAliasForProductByProductMaster(ProductMaster productMaster, String productAlias) {
        productRepository.updateProductAliasForProductByProductMaster(productMaster, productAlias);
    }

    public FlashSaleProductBean viewFlashSaleProducts() {

        FlashSaleProductBean flashSaleProductBean = new FlashSaleProductBean();

        String buildingFlashSaleQuery = "SELECT building.id AS building_id, building.building_name, building.address, " +
            "COUNT(building.id) AS count FROM product " +
            "LEFT JOIN building ON building.id = product.building " +
            "LEFT JOIN product_master ON product_master.id = product.product_master " +
            "WHERE product.flash_status = 1 GROUP BY building.id";
        List<ProductBean> buildingFlashSaleProductsList = entityManager.createNativeQuery(buildingFlashSaleQuery, "FlashSaleBuildingList").getResultList();

        String productFlashSaleQuery = "SELECT product_master.id AS product_id, product_master.product_name, product_master.product_unit_size, " +
            "product.product_unit_price, product.strike_price, product.special_text, product_sub_category.product_sub_category_name, " +
            "product_category.product_category_name, product_master.product_image_url, COUNT(product_master.id) AS count FROM product " +
            "LEFT JOIN product_master ON product_master.id = product.product_master " +
            "LEFT JOIN product_sub_category ON product_sub_category.id = product_master.product_sub_category " +
            "LEFT JOIN product_category ON product_category.id = product_sub_category.product_category " +
            "WHERE product.flash_status = 1 GROUP BY product_master.id";
        List<ProductBean> productFlashSaleProductsList = entityManager.createNativeQuery(productFlashSaleQuery, "FlashSaleProductList").getResultList();

        flashSaleProductBean.setBuildingFlashSaleList(buildingFlashSaleProductsList);
        flashSaleProductBean.setProductFlashSaleList(productFlashSaleProductsList);
        return flashSaleProductBean;

    }

    public List<ProductBean> viewBuildingFlashSales(Building building) {
        return productRepository.findFlashProductBeansByBuilding(buildingService.findById(building.getId()));
    }

    public List<ProductBean> viewProductMasterFlashSales(ProductMaster productMaster) {
        return productRepository.findFlashProductBeansByProductMaster(productMasterService.findById(productMaster.getId()));
    }

    @Transactional
    public StatusBean createFlashSale(AddProductBean addProductBean) {

        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG); Integer count = 0;

        ProductMaster productMaster = productMasterService.findById(addProductBean.getProductMasterId());
        if(productMaster == null) {
            debugLogger.debug(TAG + "No ProductMaster found for id : " + addProductBean.getProductMasterId());
            return statusBean;
        }

        List<String> buildingIds = Arrays.asList(addProductBean.getBuildingIdList().split("\\s*,\\s*"));
        List<String> partnerIds = Arrays.asList(addProductBean.getPartnerIdList().split("\\s*,\\s*"));

        List<Building> buildings = buildingService.findBuildingsByBuildingAndPartnerIdList(buildingIds, partnerIds);
        List<Product> products = productRepository.findByProductMasterAndBuildings(productMaster, buildings);

        for (Product product : products) {
            product.setSpecialText(addProductBean.getSpecialText());
            if(product.getFlashStatus() == 0) {
                product.setFlashStatus(1);
                product.setStrikePrice(product.getProductUnitPrice());
            }
            product.setProductUnitPrice(addProductBean.getProductUnitPrice());
            try {
                productRepository.save(product); count ++;
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in creating Flash Sale for ProductMaster : " + productMaster.getProductName() + " and Building : " + product.getBuilding().getBuildingName());
                return statusBean;
            }
        }

        if (count == products.size()) {
            statusBean.setStatus(1);
            statusBean.setMessage("Flash Sale successfully added for all " + count + " products in " + buildings.size() + " buildings");
        } else {
            statusBean.setMessage("Flash Sale added for only " + count + " out of " + products.size() + " products in " + buildingIds.size() + " buildings");
        }

        return statusBean;

    }

    @Transactional
    public StatusBean endBuildingFlashSale(String buildingId) {
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Building building = buildingService.findById(buildingId);
        if(building == null) {
            debugLogger.debug(TAG + "No Building found for id: " + buildingId);
            return statusBean;
        }
        List<Product> products = productRepository.findFlashProductsByBuilding(building);
        if(endFlashSale(products)) {
            statusBean.setStatus(1); statusBean.setMessage("Flash sale ended.");
        }
        return statusBean;
    }

    @Transactional
    public StatusBean endProductMasterFlashSale(String productMasterId) {
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        ProductMaster productMaster = productMasterService.findById(productMasterId);
        if(productMaster == null) {
            debugLogger.debug(TAG + "No ProductMaster found for id: " + productMasterId);
            return statusBean;
        }
        List<Product> products = productRepository.findFlashProductsByProductMaster(productMaster);
        if(endFlashSale(products)) {
            statusBean.setStatus(1); statusBean.setMessage("Flash sale ended.");
        }
        return statusBean;
    }

    @Transactional
    public StatusBean endProductFlashSale(String productId) {
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Product product = productRepository.findById(productId);
        if(product == null) {
            debugLogger.debug(TAG + "No Product found for id: " + productId);
            return statusBean;
        }
        List<Product> products = new ArrayList<>(); products.add(product);
        if(endFlashSale(products)) {
            statusBean.setStatus(1); statusBean.setMessage("Flash sale ended.");
        }
        return statusBean;
    }

    @Transactional
    public StatusBean editProductFlashSale(Product product) {
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Product retrievedProduct = productRepository.findById(product.getId());
        if(retrievedProduct == null) {
            debugLogger.debug(TAG + "No Product found for id: " + product.getId());
            return statusBean;
        }
        retrievedProduct.setProductUnitPrice(product.getProductUnitPrice());
        retrievedProduct.setSpecialText(product.getSpecialText());
        try {
            productRepository.save(retrievedProduct);
            statusBean.setStatus(1); statusBean.setMessage("Flash sale updated.");
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in editing Flash Sale for Product : " + product.getId());
            return statusBean;
        }
        return statusBean;
    }

    @Transactional
    public Boolean endFlashSale(List<Product> products) {
        for (Product product : products) {
            product.setFlashStatus(0); product.setProductUnitPrice(product.getStrikePrice());
            product.setStrikePrice(null); product.setSpecialText(null);
            try {
                productRepository.save(product);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in ending Flash Sale for Product : " + product.getId());
                return false;
            }
        }
        return true;
    }

    @Transactional
    public StatusBean addProduct(AddProductBean addProductBean) {

        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        String productMasterId = addProductBean.getProductMasterId();
        String buildingId = addProductBean.getBuildingIdList();
        Product product = new Product();

        ProductMaster productMaster = productMasterService.findById(productMasterId);
        if(productMaster == null) {
            debugLogger.debug(TAG + "No ProductMaster found for id: " + productMasterId);
            return statusBean;
        }

        Building building = buildingService.findById(buildingId);
        if(building == null) {
            debugLogger.debug(TAG + "No Building found for id: " + buildingId);
            return statusBean;
        }

        product.setFulfilledByPartner(addProductBean.getFulfilledByPartner());
        product.setProductAlias(addProductBean.getProductAlias());
        product.setProductUnitPrice(addProductBean.getProductUnitPrice());
        product.setStatus(addProductBean.getStatus());
        product.setBuilding(building);
        product.setProductMaster(productMaster);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in creating Product for ProductMaster : " + productMasterId + " and Building : " + buildingId);
            return statusBean;
        }

        statusBean.setStatus(1); statusBean.setMessage("Product added successfully");

        return statusBean;

    }

    @Transactional
    public StatusBean editProduct(ProductBean productBean) {

        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        Product product = productRepository.findById(productBean.getId());
        if(product == null) {
            debugLogger.debug(TAG + "No Product found for Id :" + productBean.getId());
            return statusBean;
        }

        product.setProductAlias(productBean.getProductAlias());
        product.setProductUnitPrice(productBean.getProductUnitPrice());
        product.setStatus(productBean.getStatus());
        product.setFulfilledByPartner(productBean.getFulfilledByPartner());

        try {
            productRepository.save(product);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in updating Product for " + product.getId());
            return statusBean;
        }

        statusBean.setStatus(1); statusBean.setMessage("Product updated successfully");

        return statusBean;

    }

}
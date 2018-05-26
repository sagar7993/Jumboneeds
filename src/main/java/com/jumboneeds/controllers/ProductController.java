package com.jumboneeds.controllers;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.Product;
import com.jumboneeds.entities.ProductMaster;
import com.jumboneeds.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public void save(@RequestBody Product product){
        productService.save(product);
    }

    @RequestMapping(value = "/findByUser", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ProductsBean findByUser(@RequestBody ProductSubCategoryIdBean productSubCategoryIdBean){
        return productService.findByUser(productSubCategoryIdBean);
    }

    @RequestMapping(value = "/fetchProductSubCategoryBeansForUser", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ProductSubCategoriesBean fetchProductSubCategoryBeansForUser(@RequestBody IdBean idBean){
        return productService.fetchProductSubCategoryBeansForUser(idBean);
    }

    @RequestMapping(value = "/getBuildingProducts", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List getBuildingProducts(@RequestBody IdBean idBean){
        return productService.getBuildingProducts(idBean);
    }

    @RequestMapping(value = "/findByBuilding", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ProductsBean findByBuilding(@RequestBody IdBean idBean){
        return productService.findByBuilding(idBean.getId());
    }

    @RequestMapping(value = "/findByProductMaster", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ProductsBean findByProductMaster(@RequestBody IdBean idBean){
        return productService.findByProductMaster(idBean.getId());
    }

    @RequestMapping(value = "/findByProductMasterAndBuilding", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ProductsBean findByProductMasterAndBuilding(@RequestBody Product product){
        return productService.findByProductMasterAndBuilding(product.getProductMaster().getId(), product.getBuilding().getId());
    }

    @RequestMapping(value = "/viewFlashSaleProducts", method = RequestMethod.GET)
    public @ResponseBody FlashSaleProductBean viewFlashSaleProducts(){
        return productService.viewFlashSaleProducts();
    }

    @RequestMapping(value = "/viewBuildingFlashSales", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<ProductBean> viewBuildingFlashSales(@RequestBody Building building){
        return productService.viewBuildingFlashSales(building);
    }

    @RequestMapping(value = "/viewProductMasterFlashSales", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<ProductBean> viewProductMasterFlashSales(@RequestBody ProductMaster productMaster){
        return productService.viewProductMasterFlashSales(productMaster);
    }

    @RequestMapping(value = "/createFlashSale", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean createFlashSale(@RequestBody AddProductBean addProductBean){
        return productService.createFlashSale(addProductBean);
    }

    @RequestMapping(value = "/editProductFlashSale", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean editProductFlashSale(@RequestBody Product product){
        return productService.editProductFlashSale(product);
    }

    @RequestMapping(value = "/endBuildingFlashSale", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean endBuildingFlashSale(@RequestBody Building building){
        return productService.endBuildingFlashSale(building.getId());
    }

    @RequestMapping(value = "/endProductMasterFlashSale", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean endProductMasterFlashSale(@RequestBody ProductMaster productMaster){
        return productService.endProductMasterFlashSale(productMaster.getId());
    }

    @RequestMapping(value = "/endProductFlashSale", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean endProductFlashSale(@RequestBody Product product){
        return productService.endProductFlashSale(product.getId());
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean addProduct(@RequestBody AddProductBean addProductBean){
        return productService.addProduct(addProductBean);
    }

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean editProduct(@RequestBody ProductBean productBean){
        return productService.editProduct(productBean);
    }

}
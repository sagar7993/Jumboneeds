package com.jumboneeds.controllers;

import com.jumboneeds.beans.GraphLoginBean;
import com.jumboneeds.beans.ProductAnalysisBean;
import com.jumboneeds.beans.RevenueAnalysisBean;
import com.jumboneeds.beans.UserAnalysisBean;
import com.jumboneeds.services.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/graph")
public class GraphController {

    @Autowired
    private GraphService graphService;

    @RequestMapping(value = "/revenueAnalysis", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<RevenueAnalysisBean> revenueAnalysis(@RequestBody GraphLoginBean graphLoginBean){
        return graphService.revenueAnalysisByDateRange(graphLoginBean.getStartDate(), graphLoginBean.getEndDate());
    }

    @RequestMapping(value = "/revenueAnalysisByDateRange", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Map<String, List<RevenueAnalysisBean>> revenueAnalysisByDateRange(@RequestBody GraphLoginBean graphLoginBean){
        return graphService.revenueAnalysis(graphLoginBean);
    }

    @RequestMapping(value = "/productSubCategoryAnalysis", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Map<String, List<ProductAnalysisBean>> productSubCategoryAnalysisByDateRange(@RequestBody GraphLoginBean graphLoginBean){
        return graphService.productAnalysis(graphLoginBean, "product_sub_category");
    }

    @RequestMapping(value = "/productCategoryAnalysis", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Map<String, List<ProductAnalysisBean>> productCategoryAnalysisByDateRange(@RequestBody GraphLoginBean graphLoginBean){
        return graphService.productAnalysis(graphLoginBean, "product_category");
    }

    @RequestMapping(value = "/userAnalysis", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Map<String, List<UserAnalysisBean>> userAnalysisByDateRange(@RequestBody GraphLoginBean graphLoginBean){
        return graphService.userAnalysis(graphLoginBean);
    }

}
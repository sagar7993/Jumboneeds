package com.jumboneeds.services;

import com.jumboneeds.beans.GraphLoginBean;
import com.jumboneeds.beans.ProductAnalysisBean;
import com.jumboneeds.beans.RevenueAnalysisBean;
import com.jumboneeds.beans.UserAnalysisBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GraphService {
    private static final String TAG = "GraphService : ";

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    @Autowired
    private EntityManager entityManager;

    public List<RevenueAnalysisBean> revenueAnalysisByDateRange(String startDate, String endDate) {

        String revenueAnalysisQuery = "SELECT COUNT(inventory.id) AS jumbo_order_count, COUNT(DISTINCT(inventory.user_id)) AS jumbo_user_count, " +
            "COALESCE(SUM(inventory.cost), 0) AS jumbo_revenue, COUNT(vendor.id) AS vendor_order_count, COUNT(DISTINCT(vendor.user_id)) AS vendor_user_count, " +
            "COALESCE(SUM(vendor.cost), 0) AS vendor_revenue, (COUNT(inventory.id) + COUNT(vendor.id)) AS total_order_count, " +
            "COUNT(DISTINCT(user)) AS total_user_count, COALESCE(SUM(inventory.cost), 0) + COALESCE(SUM(vendor.cost), 0) AS total_revenue FROM billing_log bill " +
            "LEFT JOIN (SELECT billing_log.id, subscription.product_quantity AS quantity, subscription.subscription_type, product.flash_status, " +
            "subscription.product_unit_price AS subscription_price, product.product_unit_price AS product_price, SUM(billing_log.amount) AS cost, " +
            "user.id AS user_id FROM billing_log " +
            "JOIN subscription ON subscription.id = billing_log.subscription " +
            "JOIN user ON user.id = billing_log.user " +
            "JOIN product ON product.id = subscription.product " +
            "JOIN product_master ON product_master.id = product.product_master " +
            "WHERE date BETWEEN :startDate AND :endDate AND billing_log_type = '2' AND subscription IS NOT NULL AND product.fulfilled_by_partner = '0' " +
            "GROUP BY billing_log.id) AS inventory ON inventory.id = bill.id " +
            "LEFT JOIN (SELECT billing_log.id, subscription.product_quantity AS quantity, subscription.subscription_type, product.flash_status, " +
            "subscription.product_unit_price AS subscription_price, product.product_unit_price AS product_price, SUM(billing_log.amount) AS cost, user.id AS user_id FROM billing_log " +
            "JOIN subscription ON subscription.id = billing_log.subscription " +
            "JOIN user ON user.id = billing_log.user " +
            "JOIN product ON product.id = subscription.product " +
            "JOIN product_master ON product_master.id = product.product_master " +
            "WHERE date BETWEEN :startDate AND :endDate AND billing_log_type = '2' AND subscription IS NOT NULL AND product.fulfilled_by_partner = '1' " +
            "GROUP BY billing_log.id) AS vendor ON vendor.id = bill.id " +
            "WHERE bill.date BETWEEN :startDate AND :endDate AND billing_log_type = '2' AND subscription IS NOT NULL";

        List<RevenueAnalysisBean> revenueAnalysisQueryList = entityManager.createNativeQuery(revenueAnalysisQuery, "RevenueAnalysisQueryList")
            .setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
        return revenueAnalysisQueryList;

    }

    public List<ProductAnalysisBean> productAnalysisByDateRange(String date, String groupBy) {

        String productAnalysisQuery = "SELECT " + groupBy + "." + groupBy + "_name AS category, " + groupBy + ".id, SUM(COALESCE(subscription_exception.product_quantity, " +
            "subscription.product_quantity)) AS quantity, COUNT(subscription.id) AS order_count, COUNT(DISTINCT(billing_log.user)) AS user_count FROM billing_log " +
            "JOIN subscription ON subscription.id = billing_log.subscription " +
            "LEFT JOIN subscription_exception ON subscription_exception.subscription = subscription.id AND subscription_exception.date = :date " +
            "JOIN product ON product.id = subscription.product " +
            "JOIN product_master ON product_master.id = product.product_master " +
            "JOIN product_sub_category ON product_sub_category.id = product_master.product_sub_category " +
            "JOIN product_category ON product_category.id = product_sub_category.product_category " +
            "WHERE billing_log.date = :date AND billing_log.billing_log_type = '2' AND billing_log.subscription IS NOT NULL " +
            "AND :date BETWEEN subscription.start_date AND subscription.end_date AND MOD(ABS(DATEDIFF(:date, subscription.start_date)), subscription.subscription_type) = 0 " +
            "GROUP BY " + groupBy + ".id";

        List<ProductAnalysisBean> productAnalysisQueryList = entityManager.createNativeQuery(productAnalysisQuery, "ProductAnalysisQueryList")
                .setParameter("date", date).getResultList();
        return productAnalysisQueryList;

    }

    public List<UserAnalysisBean> userAnalysisByDateRange(String date) {

        String userAnalysisQuery = "SELECT user.id AS user_id, user.user_name AS user_name, building.building_name AS building_name, block.block_name, user.flat, " +
                "user.mobile_number, SUM(COALESCE(subscription_exception.product_quantity, subscription.product_quantity)) AS quantity, COUNT(subscription.id) AS order_count, " +
                "SUM(COALESCE(subscription_exception.product_quantity, subscription.product_quantity) * subscription.product_unit_price) AS revenue FROM billing_log " +
                "JOIN subscription ON subscription.id = billing_log.subscription " +
                "JOIN user ON user.id = subscription.user " +
                "JOIN building ON building.id = user.building " +
                "JOIN block ON block.id = user.block " +
                "LEFT JOIN subscription_exception ON subscription_exception.subscription = subscription.id AND subscription_exception.date = :date " +
                "JOIN product ON product.id = subscription.product " +
                "JOIN product_master ON product_master.id = product.product_master " +
                "JOIN product_sub_category ON product_sub_category.id = product_master.product_sub_category " +
                "JOIN product_category ON product_category.id = product_sub_category.product_category " +
                "WHERE billing_log.date = :date AND billing_log.billing_log_type = '2' AND billing_log.subscription IS NOT NULL " +
                "AND :date BETWEEN subscription.start_date AND subscription.end_date AND MOD(ABS(DATEDIFF(:date, subscription.start_date)), subscription.subscription_type) = 0 " +
                "AND product_category.id != '1' GROUP BY user.id ORDER BY revenue DESC LIMIT 10";

        List<UserAnalysisBean> userAnalysisQueryList = entityManager.createNativeQuery(userAnalysisQuery, "UserAnalysisQueryList")
                .setParameter("date", date).getResultList();
        return userAnalysisQueryList;

    }

    public Map<String, List<RevenueAnalysisBean>> revenueAnalysis(GraphLoginBean graphLoginBean) {

        Map<String, List<RevenueAnalysisBean>> map = new HashMap<>();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = formatter.parse(graphLoginBean.getStartDate());
            Date endDate = formatter.parse(graphLoginBean.getEndDate());
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            end.add(Calendar.DATE, 1);
            Long duration = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
            if (duration > 6) {
                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 7), date = start.getTime()) {
                    String startDateString = formatter.format(date);
                    Calendar temp = Calendar.getInstance();
                    temp.setTime(date);
                    temp.add(Calendar.DATE, 7);
                    String endDateString = formatter.format(temp.getTime());
                    List<RevenueAnalysisBean> revenueAnalysis = revenueAnalysisByDateRange(startDateString, endDateString);
                    start.add(Calendar.DATE, 1);
                    map.put(date.toString(), revenueAnalysis);
                }
            } else {
                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    String dateString = formatter.format(date);
                    List<RevenueAnalysisBean> revenueAnalysis = revenueAnalysisByDateRange(dateString, dateString);
                    map.put(date.toString(), revenueAnalysis);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, List<ProductAnalysisBean>> productAnalysis(GraphLoginBean graphLoginBean, String type) {
        Map<String, List<ProductAnalysisBean>> map = new HashMap<>();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = formatter.parse(graphLoginBean.getStartDate());
            Date endDate = formatter.parse(graphLoginBean.getEndDate());
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            end.add(Calendar.DATE, 1);
            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                String dateString = formatter.format(date);
                List<ProductAnalysisBean> productAnalysis = productAnalysisByDateRange(dateString, type);
                map.put(date.toString(), productAnalysis);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, List<UserAnalysisBean>> userAnalysis(GraphLoginBean graphLoginBean) {
        Map<String, List<UserAnalysisBean>> map = new HashMap<>();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = formatter.parse(graphLoginBean.getStartDate());
            Date endDate = formatter.parse(graphLoginBean.getEndDate());
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            end.add(Calendar.DATE, 1);
            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                String dateString = formatter.format(date);
                List<UserAnalysisBean> userAnalysis = userAnalysisByDateRange(dateString);
                map.put(date.toString(), userAnalysis);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

}
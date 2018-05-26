package com.jumboneeds.services;

import com.jumboneeds.beans.PartnerLogin;
import com.jumboneeds.beans.StoreBuilding;
import com.jumboneeds.beans.StoreMaster;
import com.jumboneeds.beans.StoreProduct;
import com.jumboneeds.entities.JobStatus;
import com.jumboneeds.entities.Partner;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import com.jumboneeds.utils.ZipUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static com.jumboneeds.utils.StorePrintingUtils.*;

/**
 * Created by akash.mercer on 19-Oct-16.
 */

@Service
public class StorePrintingService {
    private static final String TAG = "StorePrintingService : ";

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MailService mailService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    @Autowired
    private JobStatusService jobStatusService;

    @Scheduled(cron = "0 40 18 * * *")
    public void runStorePrints(){
        JobStatus jobStatus = new JobStatus();
        jobStatus.setDate(DateOperations.getTodayStartDate());
        jobStatus.setJobName("Store Printing Job");
        jobStatus.setMessage("Running");
        jobStatus.setStatus(false);
        jobStatusService.saveJob(jobStatus);

        try{
            List<Partner> partners = partnerService.fetchAll();

            for(Partner partner : partners){
                PartnerLogin partnerLogin = new PartnerLogin();
                partnerLogin.setUserName(partner.getUserName());
                partnerLogin.setPassword(partner.getPassword());
                partnerLogin.setDateString(DateOperations.getDateString(DateOperations.getTodayStartDate()));

                storeService.fetchStore(partnerLogin, false, true);
            }

            ZipUtils.makeZipFile();

            mailService.sendStoreZipFile();

            jobStatus.setStatus(true);
            jobStatus.setMessage("Successful");
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + DateOperations.getTodayStartDate() + " : Store Printing Job FAILED on " + DateOperations.getTodayStartDate() + " due to "+ e);

            String subject = "Store Printing Job FAILED on " + DateOperations.getTodayStartDate();

            String htmlText = "<html><body>"
                    + "</h2>" + "On " + DateOperations.getTodayStartDate() + ", "
                    + "Store Printing Job FAILED.</h2>"
                    + "<h3>Failure was caused due to : " + e + "</h3>"
                    + "</body></html>";

            jobStatus.setMessage("Failed");
            jobStatusService.saveJob(jobStatus);

            sendMail(subject, htmlText);
        }
    }

    private void sendMail(String subject, String htmlText){
        String[] toArray = new String[]{"tech@jumboneeds.com"};

        mailService.buildEmail(toArray, Constants.TECH_EMAIL, Constants.TECH_EMAIL, subject, htmlText, null, null);
    }

    private void printStore(StoreMaster storeMaster){
        if (!CollectionUtils.isEmpty(storeMaster.getStoreBuildings()) && !CollectionUtils.isEmpty(storeMaster.getStoreProducts())){
            File directory = new File(Constants.STORE_PATH_FINANCE + DateOperations.getMonthStringFromDate(DateOperations.getTodayStartDate()));

            if (!directory.exists() && !directory.mkdirs()){
                errorLogger.error(TAG + "Error in creating directory : " + directory.getAbsolutePath());
                return;
            }

            generateBuildingsFileForFinance(storeMaster);

            generateProductsFileForFinance(storeMaster);
        }
    }

    private void generateBuildingsFileForFinance(StoreMaster storeMaster){
        List<StoreBuilding> storeBuildings = storeMaster.getStoreBuildings();

        Collections.sort(storeBuildings, new StoreBuilding().getFlatComparator());

        File file = new File(Constants.STORE_PATH_FINANCE + DateOperations.getMonthStringFromDate(DateOperations.getTodayStartDate()) + File.separator + storeMaster.getBuildingName() + " - Building" + ".xlsx");
        File newFile = new File(Constants.STORE_PATH_FINANCE + DateOperations.getMonthStringFromDate(DateOperations.getTodayStartDate()) + File.separator + storeMaster.getBuildingName() + " - Building" + "_New.xlsx");

        XSSFWorkbook xssfWorkbook = getXssfWorkbook(file);

        if (xssfWorkbook == null){
            return;
        }

        //Create a blank sheet
        XSSFSheet xssfSheet = xssfWorkbook.createSheet(DateOperations.getDateStringForStore(DateOperations.getTodayStartDate()));

        initializeHeadersForWorksheet(xssfWorkbook, xssfSheet, getStoreBuildingPropertiesHeaderArray());

        for (int i = 1; i < storeBuildings.size() + 1; i++) {
            XSSFRow xssfRow = xssfSheet.createRow(i);

            String[] storeBuildingPropertiesArray = getStoreBuildingPropertiesArray(storeBuildings.get(i - 1));

            for (int j = 0; j < storeBuildingPropertiesArray.length; j++) {
                Cell cell = xssfRow.createCell(j);
                cell.setCellValue(storeBuildingPropertiesArray[j]);
            }
        }

        writeToWorkbook(file, newFile, xssfWorkbook, storeMaster.getBuildingName());
    }

    private void generateProductsFileForFinance(StoreMaster storeMaster){
        List<StoreProduct> storeProducts = storeMaster.getStoreProducts();

        Collections.sort(storeProducts, new StoreProduct().getProductNameComparator());

        File file = new File(Constants.STORE_PATH_FINANCE + DateOperations.getMonthStringFromDate(DateOperations.getTodayStartDate()) + File.separator + storeMaster.getBuildingName() + " - Product" + ".xlsx");
        File newFile = new File(Constants.STORE_PATH_FINANCE + DateOperations.getMonthStringFromDate(DateOperations.getTodayStartDate()) + File.separator + storeMaster.getBuildingName() + " - Product" + "_New.xlsx");

        XSSFWorkbook xssfWorkbook = getXssfWorkbook(file);

        if (xssfWorkbook == null){
            return;
        }

        //Create a blank sheet
        XSSFSheet xssfSheet = xssfWorkbook.createSheet(DateOperations.getDateStringForStore(DateOperations.getTodayStartDate()));

        initializeHeadersForWorksheet(xssfWorkbook, xssfSheet, getStoreProductPropertiesHeaderArray());

        for (int i = 1; i < storeProducts.size() + 1; i++) {
            XSSFRow xssfRow = xssfSheet.createRow(i);

            String[] storeProductPropertiesArray = getStoreProductPropertiesArray(storeProducts.get(i - 1));

            for (int j = 0; j < storeProductPropertiesArray.length; j++) {
                Cell cell = xssfRow.createCell(j);
                cell.setCellValue(storeProductPropertiesArray[j]);
            }
        }

        writeToWorkbook(file, newFile, xssfWorkbook, storeMaster.getBuildingName());
    }

}

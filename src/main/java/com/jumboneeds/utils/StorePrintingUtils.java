package com.jumboneeds.utils;

import com.jumboneeds.beans.StoreBuilding;
import com.jumboneeds.beans.StoreMaster;
import com.jumboneeds.beans.StoreProduct;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;

import static com.jumboneeds.utils.Constants.COUPONS;

/**
 * Created by akash.mercer on 21-Oct-16.
 */
public class StorePrintingUtils {
    private static final String TAG = "StorePrintingUtils " ;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public static final int DATA_START_ROW = 3;

    private static final String[] storeBuildingPropertiesHeaderArray = new String[]{"FLAT", "PRODUCT", "BALANCE"};

    private static final String[] storeProductPropertiesHeaderArray = new String[]{"PRODUCT", "SIZE", "QTY", "Total"};

    private static short[] storeBuildingCellWidthArray = new short[]{2800, 15000, 3200};

    private static short[] storeProductCellWidthArray = new short[]{10000, 5000, 2400, 3600};

    private static Color colorPrimary = new Color(135, 33, 99);

    private static XSSFCellStyle firstRowBorderCellStyle;

    private static XSSFCellStyle firstRowNonBorderCellStyle;

    private static XSSFCellStyle middleRowCellStyle;

    private static XSSFCellStyle lastRowBorderCellStyle;

    private static XSSFCellStyle lastRowNonBorderCellStyle;

    public static XSSFWorkbook getXssfWorkbook(File file){
        if (file.exists()) {
            try {
                return new XSSFWorkbook(file);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in opening existing workbook : " + file.getAbsolutePath());
                return null;
            }
        } else {
            return new XSSFWorkbook();
        }
    }

    public static void setFileHeaderForBuildingFile(XSSFWorkbook xssfWorkbook, XSSFSheet xssfSheet, StoreMaster storeMaster){
        xssfSheet.addMergedRegion(new CellRangeAddress(0, DATA_START_ROW - 1, 0, storeBuildingPropertiesHeaderArray.length - 1));

        setFileHeader(xssfWorkbook, xssfSheet, storeMaster);
    }

    public static void setFileHeaderForProductFile(XSSFWorkbook xssfWorkbook, XSSFSheet xssfSheet, StoreMaster storeMaster){
        xssfSheet.addMergedRegion(new CellRangeAddress(0, DATA_START_ROW - 1, 0, storeProductPropertiesHeaderArray.length - 1));

        setFileHeader(xssfWorkbook, xssfSheet, storeMaster);
    }

    private static XSSFCellStyle setFileHeader(XSSFWorkbook xssfWorkbook, XSSFSheet xssfSheet, StoreMaster storeMaster){
        XSSFRow xssfRow = xssfSheet.createRow(0);

        XSSFCellStyle xssfCellStyle = xssfWorkbook.createCellStyle();
        xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
        xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFFont xssfFont = xssfWorkbook.createFont();
        xssfFont.setFontHeightInPoints((short) 16);
        xssfFont.setBold(true);

        xssfCellStyle.setFont(xssfFont);

        XSSFCell xssfCell = xssfRow.createCell(0);
        xssfCell.setCellStyle(xssfCellStyle);
        xssfCell.setCellValue(storeMaster.getBuildingName() + " : " + storeMaster.getDate());

        //Nullify all the previous RowCellStyles
        firstRowBorderCellStyle = null;
        firstRowNonBorderCellStyle = null;
        middleRowCellStyle = null;
        lastRowBorderCellStyle = null;
        lastRowNonBorderCellStyle = null;

        return xssfCellStyle;
    }

    public static void initializeHeadersForWorksheet(XSSFWorkbook xssfWorkbook, XSSFSheet xssfSheet, String[] propertiesHeaderArray){
        XSSFRow zerothXssfRow = xssfSheet.createRow(StorePrintingUtils.DATA_START_ROW);

        XSSFFont xssfFont = xssfWorkbook.createFont();
        xssfFont.setBold(true);

        XSSFCellStyle xssfCellStyle = xssfWorkbook.createCellStyle();
        xssfCellStyle.setFont(xssfFont);

        for (int i = 0; i < propertiesHeaderArray.length; i++) {
            Cell cell = zerothXssfRow.createCell(i);
            cell.setCellValue(propertiesHeaderArray[i]);
            cell.setCellStyle(xssfCellStyle);
        }
    }

    public static void mergeBuildingRows(XSSFWorkbook xssfWorkbook, XSSFSheet xssfSheet, int firstRow, int lastRow, int firstCol, int lastCol){
        xssfSheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, firstCol));
        xssfSheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, lastCol, lastCol));

        for (int i = firstRow; i <= lastRow; i++) {
            XSSFRow xssfRow = xssfSheet.getRow(i);

            for (int j = firstCol; j <= lastCol; j++) {
                XSSFCell xssfCell = xssfRow.getCell(j);

                if (i == firstRow){
                    if (j == firstCol || j == lastCol){
                        xssfCell.setCellStyle(getFirstRowBorderCellStyle(xssfWorkbook));
                    } else {
                        xssfCell.setCellStyle(getFirstRowNonBorderCellStyle(xssfWorkbook));
                    }
                } else if (i == lastRow){
                    if (j == firstCol || j == lastCol){
                        xssfCell.setCellStyle(getLastRowBorderCellStyle(xssfWorkbook));
                    } else {
                        xssfCell.setCellStyle(getLastRowNonBorderCellStyle(xssfWorkbook));
                    }
                } else {
                    xssfCell.setCellStyle(getMiddleRowNonBorderCellStyle(xssfWorkbook));
                }
            }
        }
    }

    public static String[] getStoreBuildingPropertiesArray(StoreBuilding storeBuilding){
        String[] storeBuildingPropertiesArray = new String[storeBuildingPropertiesHeaderArray.length];
        storeBuildingPropertiesArray[0] = storeBuilding.getMergedBlockAndFlat();

        if (!StringUtils.isEmpty(storeBuilding.getProductName())) {
            storeBuildingPropertiesArray[1] = storeBuilding.getProductName() + " (" + storeBuilding.getProductUnitSize() + ")" + " X " + storeBuilding.getProductQuantity();
            storeBuildingPropertiesArray[2] = String.valueOf(storeBuilding.getUserBalance());
        } else {
            storeBuildingPropertiesArray[1] = COUPONS;
            storeBuildingPropertiesArray[2] = "0.0";
        }

        return storeBuildingPropertiesArray;
    }

    public static String[] getStoreProductPropertiesArray(StoreProduct storeProduct){
        String[] storeProductPropertiesArray = new String[storeProductPropertiesHeaderArray.length];
        storeProductPropertiesArray[0] = storeProduct.getProductName();
        storeProductPropertiesArray[1] = storeProduct.getProductUnitSize();
        storeProductPropertiesArray[2] = String.valueOf(storeProduct.getProductQuantity());
        storeProductPropertiesArray[3] = String.valueOf(storeProduct.getTotalAmount());

        return storeProductPropertiesArray;
    }

    public static XSSFSheet applyPrintSettings(XSSFSheet xssfSheet, StoreMaster storeMaster){
        Header header = xssfSheet.getHeader();
        header.setCenter(storeMaster.getBuildingName() + " : " + storeMaster.getDate());

        Footer footer = xssfSheet.getFooter();
        footer.setRight("Page " + org.apache.poi.hssf.usermodel.HeaderFooter.page() + " of " + org.apache.poi.hssf.usermodel.HeaderFooter.numPages());

        xssfSheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
        xssfSheet.setDisplayGridlines(true);
        xssfSheet.setPrintGridlines(true);

        return xssfSheet;
    }

    public static void writeToWorkbook(File file, File newFile, XSSFWorkbook xssfWorkbook, String buildingName){
        try {
            FileOutputStream fileOutputStream;

            if (file.exists()){
                fileOutputStream = new FileOutputStream(newFile);
            } else {
                fileOutputStream = new FileOutputStream(file);
            }

            xssfWorkbook.write(fileOutputStream);
            xssfWorkbook.close();
            fileOutputStream.close();

            if (newFile.exists() && !newFile.delete()){
                errorLogger.error(TAG + "Error in deleting workbook : " + newFile.getAbsolutePath());
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in writing content to workbook for Building : " + buildingName);
        }
    }

    private static XSSFCellStyle getFirstRowBorderCellStyle(XSSFWorkbook xssfWorkbook) {
        if (firstRowBorderCellStyle == null){
            firstRowBorderCellStyle = xssfWorkbook.createCellStyle();
            firstRowBorderCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            firstRowBorderCellStyle.setBorderTop(BorderStyle.THICK);
            firstRowBorderCellStyle.setBorderLeft(BorderStyle.THICK);
            firstRowBorderCellStyle.setBorderRight(BorderStyle.THICK);
            firstRowBorderCellStyle.setBorderBottom(BorderStyle.NONE);

            firstRowBorderCellStyle.setTopBorderColor(new XSSFColor(colorPrimary));
            firstRowBorderCellStyle.setLeftBorderColor(new XSSFColor(colorPrimary));
            firstRowBorderCellStyle.setRightBorderColor(new XSSFColor(colorPrimary));
        }

        return firstRowBorderCellStyle;
    }

    private static XSSFCellStyle getFirstRowNonBorderCellStyle(XSSFWorkbook xssfWorkbook) {
        if (firstRowNonBorderCellStyle == null){
            firstRowNonBorderCellStyle = xssfWorkbook.createCellStyle();

            firstRowNonBorderCellStyle.setBorderTop(BorderStyle.THICK);
            firstRowNonBorderCellStyle.setBorderLeft(BorderStyle.NONE);
            firstRowNonBorderCellStyle.setBorderRight(BorderStyle.NONE);
            firstRowNonBorderCellStyle.setBorderBottom(BorderStyle.NONE);

            firstRowNonBorderCellStyle.setTopBorderColor(new XSSFColor(colorPrimary));
        }

        return firstRowNonBorderCellStyle;
    }

    public static XSSFCellStyle getMiddleRowNonBorderCellStyle(XSSFWorkbook xssfWorkbook) {
        if (middleRowCellStyle == null){
            middleRowCellStyle = xssfWorkbook.createCellStyle();
            middleRowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            middleRowCellStyle.setBorderTop(BorderStyle.NONE);
            middleRowCellStyle.setBorderLeft(BorderStyle.THICK);
            middleRowCellStyle.setBorderRight(BorderStyle.THICK);
            middleRowCellStyle.setBorderBottom(BorderStyle.NONE);

            middleRowCellStyle.setLeftBorderColor(new XSSFColor(colorPrimary));
            middleRowCellStyle.setRightBorderColor(new XSSFColor(colorPrimary));
            middleRowCellStyle.setBottomBorderColor(new XSSFColor(colorPrimary));
        }

        return middleRowCellStyle;
    }

    private static XSSFCellStyle getLastRowBorderCellStyle(XSSFWorkbook xssfWorkbook) {
        if (lastRowBorderCellStyle == null){
            lastRowBorderCellStyle = xssfWorkbook.createCellStyle();
            lastRowBorderCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            lastRowBorderCellStyle.setBorderTop(BorderStyle.NONE);
            lastRowBorderCellStyle.setBorderLeft(BorderStyle.THICK);
            lastRowBorderCellStyle.setBorderRight(BorderStyle.THICK);
            lastRowBorderCellStyle.setBorderBottom(BorderStyle.THICK);

            lastRowBorderCellStyle.setLeftBorderColor(new XSSFColor(colorPrimary));
            lastRowBorderCellStyle.setRightBorderColor(new XSSFColor(colorPrimary));
            lastRowBorderCellStyle.setBottomBorderColor(new XSSFColor(colorPrimary));
        }

        return lastRowBorderCellStyle;
    }

    private static XSSFCellStyle getLastRowNonBorderCellStyle(XSSFWorkbook xssfWorkbook) {
        if (lastRowNonBorderCellStyle == null){
            lastRowNonBorderCellStyle = xssfWorkbook.createCellStyle();

            lastRowNonBorderCellStyle.setBorderTop(BorderStyle.NONE);
            lastRowNonBorderCellStyle.setBorderLeft(BorderStyle.NONE);
            lastRowNonBorderCellStyle.setBorderRight(BorderStyle.NONE);
            lastRowNonBorderCellStyle.setBorderBottom(BorderStyle.THICK);

            lastRowNonBorderCellStyle.setBottomBorderColor(new XSSFColor(colorPrimary));
        }

        return lastRowNonBorderCellStyle;
    }

    public static XSSFRichTextString getBuildingProductCellStyle(XSSFWorkbook xssfWorkbook, String content) {
        XSSFFont xssfFont = xssfWorkbook.createFont();
        xssfFont.setBold(true);

        XSSFRichTextString xssfRichTextString = new XSSFRichTextString(content);
        xssfRichTextString.applyFont(content.lastIndexOf("X") + 2, content.length(), xssfFont);

        return xssfRichTextString;
    }

    public static String[] getStoreBuildingPropertiesHeaderArray() {
        return storeBuildingPropertiesHeaderArray;
    }

    public static String[] getStoreProductPropertiesHeaderArray() {
        return storeProductPropertiesHeaderArray;
    }

    public static short[] getStoreBuildingCellWidthArray() {
        return storeBuildingCellWidthArray;
    }

    public static short[] getStoreProductCellWidthArray() {
        return storeProductCellWidthArray;
    }
}

package com.hitales.national.migrate.service;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:13:51
 */

@Component
@Slf4j
public class ExcelToolService {
    @Value("${excel.sourceFile}")
    private String sourceFile;

    @Value("${excel.verifyResultFile}")
    private String verifyResultFile;


    public static final Integer MAX_READ_SIZE = 1000;
    private XSSFWorkbook xssfSourceWorkbook;


    private ExcelToolService(){
        if(Strings.isNullOrEmpty(sourceFile)){
            throw new RuntimeException("excel路径为空！");
        }
        try {
            xssfSourceWorkbook = new XSSFWorkbook(sourceFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public XSSFSheet getSourceSheetByName(String sheetName){
        if(Strings.isNullOrEmpty(sheetName)){
            throw new RuntimeException("sheetName不能为空！");
        }
        XSSFSheet xssfSheet = xssfSourceWorkbook.getSheet(sheetName);
        if(Objects.isNull(xssfSheet)){
            return xssfSourceWorkbook.createSheet(sheetName);
        }
        return xssfSheet;
    }


    public void saveExcelFile(SXSSFWorkbook sxssfWorkbook, String saveType){
        String savePath = getVerifyExcelName(saveType);
        File file = new File(savePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(savePath);
            sxssfWorkbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private String getVerifyExcelName(String saveType){
        return  verifyResultFile + "_" + saveType +".xlsx";
    }
}

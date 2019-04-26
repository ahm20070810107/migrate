package com.hitales.national.migrate.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:14:37
 */
@Service
@Slf4j
public class DoctorService implements BasicService{
    @Autowired
    private ExcelToolService excelToolService;


    @Override
    public void verify(String sheetName){
        SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolService.MAX_READ_SIZE);

        excelToolService.saveExcelFile(verifyWorkbook, sheetName);
    }

    @Override
    public void importToDb(String sheetName){

    }


    private boolean verifyDoctor(String doctorSheet, SXSSFWorkbook verifyWorkbook){
        int verifyRowCount = 1;
        boolean result = true;

        XSSFSheet sourceDataSheet = excelToolService.getSourceSheetByName(doctorSheet);

        Sheet verifySheet = excelToolService.getNewSheet(verifyWorkbook, doctorSheet, "原始行号,身份证号,身份证姓名,民族,家庭住址,手机号,所属医疗机构,备注",",");

        for(int i = 1; i < sourceDataSheet.getLastRowNum(); i++) {

            StringBuilder sb = new StringBuilder();
            Row row = sourceDataSheet.getRow(i);
        }
        return result;
    }

}

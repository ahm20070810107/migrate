package com.hitales.national.migrate.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:13:56
 */
@Service
@Slf4j
public class CommonService {
    @Autowired
    private ExcelToolService excelToolService;


    public void verify(String operatorSheet, String clinicSheet,String countySheet, String villageSheet){
            SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolService.MAX_READ_SIZE);


            excelToolService.saveExcelFile(verifyWorkbook, "公共信息");
    }

    public void importToDb(String operatorSheet, String clinicSheet,String countySheet, String villageSheet){

    }
}

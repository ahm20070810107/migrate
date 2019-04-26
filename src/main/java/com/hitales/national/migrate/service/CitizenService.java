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
 * @time:14:30
 */

@Service
@Slf4j
public class CitizenService implements BasicService {

    @Autowired
    private ExcelToolAndCommonService excelToolAndCommonService;

    @Override
    public boolean verify(String sheetName){
         SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolAndCommonService.MAX_READ_SIZE);


         excelToolAndCommonService.saveExcelFile(verifyWorkbook, sheetName);
         return true;
    }

    @Override
    public void importToDb(String sheetName){

    }


}

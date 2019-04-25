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
    private ExcelToolService excelToolService;

    @Override
    public void verify(String sheetName){
         SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolService.MAX_READ_SIZE);


         excelToolService.saveExcelFile(verifyWorkbook, sheetName);
    }

    @Override
    public void importToDb(String sheetName){

    }


}

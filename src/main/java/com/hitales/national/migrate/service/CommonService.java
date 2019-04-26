package com.hitales.national.migrate.service;

import com.google.common.base.Strings;
import com.hitales.national.migrate.dao.OperatorDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
    @Autowired
    private OperatorDao operatorDao;

    @Autowired
    private PasswordEncoder passEncoder;

    public boolean verify(String operatorSheet, String clinicSheet,String countySheet, String villageSheet){
        SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolService.MAX_READ_SIZE);
        boolean operatorResult = verifyOperator(operatorSheet, verifyWorkbook);

        excelToolService.saveExcelFile(verifyWorkbook, "公共信息");
        return operatorResult;
    }

    public void importToDb(String operatorSheet, String clinicSheet,String countySheet, String villageSheet){

    }


    private boolean verifyOperator(String operatorSheet, SXSSFWorkbook verifyWorkbook){
        int headIndex = 0;
        boolean verifyResult = true;
        Set<String> userNameSet = new HashSet<>();
        XSSFSheet sourceDataSheet = excelToolService.getSourceSheetByName(operatorSheet);
        if(sourceDataSheet.getLastRowNum() < 2){
            log.warn("【{}】sheet的数据为空！", operatorSheet);
            return verifyResult;
        }
        Sheet verifySheet = excelToolService.getNewSheet(verifyWorkbook, operatorSheet, "原始行号,用户名,密码,姓名,备注",",");
        int verifyRowCount = 1;
        for(int i = headIndex +1; i < sourceDataSheet.getLastRowNum(); i++){
            Row row = sourceDataSheet.getRow(i);
            String loginName = row.getCell(0).getStringCellValue();
            userNameSet.add(loginName);
            String password = row.getCell(1).getStringCellValue();
            String userName = row.getCell(2).getStringCellValue();
            Integer count = 1;
            StringBuilder sb = new StringBuilder();
            if(Strings.isNullOrEmpty(loginName) || Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(userName)) {
                sb.append(count++).append("、用户名,密码,姓名均不能为空！\r\n");
            }
            if(loginName.length() > 30){
                sb.append(count++).append("、用户名长度不能超过30\r\n");
            }
            if(userName.length() > 35){
                sb.append(count++).append("、姓名长度不能超过35\r\n");
            }
            if(userNameSet.contains(loginName)){
                sb.append(count++).append("、用户名在excel中有重复\r\n");
            }
            if(operatorDao.findByUsername(loginName).size() > 0){
                sb.append(count++).append("、用户名在数据库中有重复\r\n");
            }
            if(count.compareTo(1) > 0){
                verifyResult = false;
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                verifyRow.createCell(0).setCellValue(i +1);
                verifyRow.createCell(1).setCellValue(loginName);
                verifyRow.createCell(2).setCellValue(password);
                verifyRow.createCell(3).setCellValue(userName);
                verifyRow.createCell(4).setCellValue(sb.toString());
            }
        }
        return verifyResult;

    }
}

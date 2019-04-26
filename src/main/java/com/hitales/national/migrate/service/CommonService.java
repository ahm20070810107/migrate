package com.hitales.national.migrate.service;

import com.google.common.base.Strings;
import com.hitales.national.migrate.dao.DoctorClinicDao;
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
    private DoctorClinicDao doctorClinicDao;

    @Autowired
    private PasswordEncoder passEncoder;

    int headIndex = 0;

    public boolean verify(String operatorSheet, String clinicSheet,String countySheet, String villageSheet){
        SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolService.MAX_READ_SIZE);
        Set<String> villageSet = new HashSet<>();
        boolean operatorResult = verifyOperator(operatorSheet, verifyWorkbook);
        boolean clinicResult = verifyClinic(clinicSheet,villageSet,verifyWorkbook);

        excelToolService.saveExcelFile(verifyWorkbook, "公共信息");
        return operatorResult && clinicResult;
    }

    public void importToDb(String operatorSheet, String clinicSheet,String countySheet, String villageSheet){

    }

    private boolean verifyVillage(String villageSheet,  Set<String> villageSet, SXSSFWorkbook verifyWorkbook){
        boolean result = true;
        XSSFSheet sourceDataSheet = excelToolService.getSourceSheetByName(villageSheet);
        if(sourceDataSheet.getLastRowNum() < 2){
            log.warn("【{}】sheet的数据为空！", villageSheet);
            return result;
        }
        Sheet verifySheet = excelToolService.getNewSheet(verifyWorkbook, villageSheet, "原始行号,自然村名称,所属行政村编码,备注",",");
        int verifyRowCount = 1;

        for(int i = headIndex +1; i < sourceDataSheet.getLastRowNum(); i++) {
            Row row = sourceDataSheet.getRow(i);
            StringBuilder sb = new StringBuilder();
            Integer count = 1;
            String villageName = row.getCell(0).getStringCellValue();
            String govVillageCode = row.getCell(1).getStringCellValue();
            if(Strings.isNullOrEmpty(villageName) || Strings.isNullOrEmpty(govVillageCode)){
                sb.append(count++).append("、自然村名称,所属行政村编码均不能为空！\r\n");
            }

        }


        return result;
    }

    private boolean verifyClinic(String clinicSheet, Set<String> villageSet, SXSSFWorkbook verifyWorkbook){

        Set<String> clinicNameSet = new HashSet<>();
        XSSFSheet sourceDataSheet = excelToolService.getSourceSheetByName(clinicSheet);
        boolean result = true;

        if(sourceDataSheet.getLastRowNum() < 2){
            log.warn("【{}】sheet的数据为空！", clinicSheet);
            return result;
        }
        Sheet verifySheet = excelToolService.getNewSheet(verifyWorkbook, clinicSheet, "原始行号,名称,上级医疗机构,级别,管辖自然村,备注",",");
        int verifyRowCount = 1;
        for(int i = headIndex +1; i < sourceDataSheet.getLastRowNum(); i++){
            Row row = sourceDataSheet.getRow(i);
            Integer count = 1;
            StringBuilder sb = new StringBuilder();

            String clinicName = row.getCell(0).getStringCellValue();
            String upClinicName = row.getCell(1).getStringCellValue();
            String clinicClass = row.getCell(2).getStringCellValue();
            String scopeVillage = row.getCell(3).getStringCellValue();

            if(Strings.isNullOrEmpty(clinicName) || Strings.isNullOrEmpty(clinicClass) || Strings.isNullOrEmpty(scopeVillage)){
                sb.append(count++).append("、名称,级别,管辖自然村均不能为空！\r\n");
            }
            if(clinicNameSet.contains(clinicName)){
                sb.append(count++).append("、名称在excel中有重复\r\n");
            }
            clinicNameSet.add(clinicName);
            if(doctorClinicDao.findByName(clinicName).size() > 0){
                sb.append(count++).append("、名称在数据库中有重复\r\n");
            }
            if(!Strings.isNullOrEmpty(clinicName) && clinicName.length() > 20){
                sb.append(count++).append("、名称的长度不能超过20\r\n");
            }
            if(doctorClinicDao.findByName(upClinicName).size() < 0 && !clinicNameSet.contains(upClinicName) ){
                sb.append(count++).append("、上级医疗机构在数据库以及excel中均不存在\r\n");
            }
            String[] villages = scopeVillage.split(";|；");
            String villageResult = checkVillage(villageSet, villages);
            if(!Strings.isNullOrEmpty(villageResult)) {
                sb.append(count++).append("、").append(villageResult).append("在自然村中不存在\r\n");
            }
            if(count.compareTo(1) > 0){
                result = false;
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                fillSheetRow(i+1,verifyRow,clinicName,upClinicName,clinicClass,scopeVillage,sb.toString());
            }
        }
        return result;
    }

    private String checkVillage(Set<String> villageSet,String[] villages){
        String result = "";
        for(String village : villages){
            if(!villageSet.contains(village)){
                result += village + "、";
            }
        }
       if(result.length() > 1){
           return result.substring(0,result.length() - 1);
       }
       return result;
    }
    private boolean verifyOperator(String operatorSheet, SXSSFWorkbook verifyWorkbook){
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
            String password = row.getCell(1).getStringCellValue();
            String userName = row.getCell(2).getStringCellValue();
            Integer count = 1;
            StringBuilder sb = new StringBuilder();
            if(Strings.isNullOrEmpty(loginName) || Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(userName)) {
                sb.append(count++).append("、用户名,密码,姓名均不能为空！\r\n");
            }
            if(!Strings.isNullOrEmpty(loginName) && loginName.length() > 30){
                sb.append(count++).append("、用户名长度不能超过30\r\n");
            }
            if(!Strings.isNullOrEmpty(userName) && userName.length() > 35){
                sb.append(count++).append("、姓名长度不能超过35\r\n");
            }
            if(userNameSet.contains(loginName)){
                sb.append(count++).append("、用户名在excel中有重复\r\n");
            }
            userNameSet.add(loginName);
            if(operatorDao.findByUsername(loginName).size() > 0){
                sb.append(count++).append("、用户名在数据库中有重复\r\n");
            }
            if(count.compareTo(1) > 0){
                verifyResult = false;
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                fillSheetRow(i+1,verifyRow,loginName,password,userName,sb.toString());
            }
        }
        return verifyResult;

    }


    private void fillSheetRow(int index,Row row, String ... params){
        int cellIndex = 1;
        row.createCell(0).setCellValue(index);
        for(String param : params){
            row.createCell(cellIndex++).setCellValue(param);
        }
    }
}

package com.hitales.national.migrate.service;

import com.google.common.base.Strings;
import com.hitales.national.migrate.dao.CountyDao;
import com.hitales.national.migrate.dao.DoctorClinicDao;
import com.hitales.national.migrate.dao.GB2260Dao;
import com.hitales.national.migrate.dao.OperatorDao;
import com.hitales.national.migrate.entity.County;
import com.hitales.national.migrate.entity.DoctorClinic;
import com.hitales.national.migrate.entity.GB2260;
import com.hitales.national.migrate.entity.Operator;
import com.hitales.national.migrate.enums.OperatorAccountState;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private ExcelToolAndCommonService excelToolAndCommonService;
    @Autowired
    private OperatorDao operatorDao;
    @Autowired
    private DoctorClinicDao doctorClinicDao;
    @Autowired
    private GB2260Dao gb2260Dao;
    @Autowired
    private CountyDao countyDao;

    private String countyPrefix;

    @Autowired
    private PasswordEncoder passEncoder;

    int headIndex = 0;

    public boolean verify(String operatorSheet, String clinicSheet,String countySheet, String villageSheet){
        if(Strings.isNullOrEmpty(operatorSheet) || Strings.isNullOrEmpty(clinicSheet) || Strings.isNullOrEmpty(countySheet) || Strings.isNullOrEmpty(villageSheet)){
            throw new RuntimeException("自然村、行政县、医疗机构、运营用户的sheet名称均不能为空");
        }
        SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolAndCommonService.MAX_READ_SIZE);
        Set<String> villageSet = new HashSet<>();
        countyPrefix = "";
        boolean countyResult = verifyCounty(countySheet, verifyWorkbook);
        boolean operatorResult = verifyOperator(operatorSheet, verifyWorkbook);
        boolean villageResult = verifyVillage(villageSheet,villageSet,verifyWorkbook);
        boolean clinicResult = verifyClinic(clinicSheet,villageSet,verifyWorkbook);

        excelToolAndCommonService.saveExcelFile(verifyWorkbook, "公共信息");
        return operatorResult && clinicResult && villageResult && countyResult;
    }

    public boolean importToDb(String operatorSheet, String clinicSheet,String countySheet, String villageSheet){
        if(!verify(operatorSheet,clinicSheet,countySheet,villageSheet)){
            return false;
        }
        XSSFSheet operatorDataSheet = excelToolAndCommonService.getSourceSheetByName(operatorSheet);
        List<Operator> operators = sheetToOperators(1,operatorDataSheet);

        XSSFSheet countyDataSheet = excelToolAndCommonService.getSourceSheetByName(countySheet);
        List<County> counties = sheetToCounties(1,countyDataSheet);

        XSSFSheet villageDataSheet = excelToolAndCommonService.getSourceSheetByName(villageSheet);
        List<GB2260> gb2260s = sheetToVillage(1,villageDataSheet);

        XSSFSheet clinicDataSheet = excelToolAndCommonService.getSourceSheetByName(clinicSheet);
        List<DoctorClinic> doctorClinics = sheetToClinic(1,clinicDataSheet);

        operatorDao.saveAll(operators);
        countyDao.saveAll(counties);
        gb2260Dao.saveAll(gb2260s);
        doctorClinicDao.saveAll(doctorClinics);
        return true;
    }

    //TODO
    private List<DoctorClinic> sheetToClinic(Integer startRowIndex, Sheet clinicSheet){
        List<DoctorClinic> doctorClinics = new ArrayList<>();
        for(int i = startRowIndex; i < clinicSheet.getLastRowNum(); i++) {
            Row row = clinicSheet.getRow(i);

            String clinicName = row.getCell(0).getStringCellValue();
            String upClinicName = row.getCell(1).getStringCellValue();
            String clinicClass = row.getCell(2).getStringCellValue();
            String scopeVillage = row.getCell(3).getStringCellValue();

            DoctorClinic doctorClinic = new DoctorClinic();
            doctorClinics.add(doctorClinic);
            doctorClinic.setName(clinicName);


        }
        return doctorClinics;
    }

    private List<GB2260> sheetToVillage(Integer startRowIndex, Sheet villageSheet){

        Map<Long,Long> canonicalCodeMap = new HashMap<>();
        List<GB2260> gb2260s = new ArrayList<>();
        for(int i = startRowIndex; i < villageSheet.getLastRowNum(); i++) {
            Row row = villageSheet.getRow(i);
            String villageName = row.getCell(0).getStringCellValue();
            String govVillageCode = row.getCell(1).getStringCellValue();
            Long govVillageCodeL = Long.parseLong(govVillageCode);
            GB2260 gb2260 = new GB2260();
            gb2260s.add(gb2260);
            gb2260.setDepth(6);
            gb2260.setCanonicalCode(getVillageCanonicalCode(govVillageCodeL,canonicalCodeMap));
            gb2260.setName(villageName);
        }
        return gb2260s;
    }
    private Long getVillageCanonicalCode(Long canonicalCode,Map<Long,Long> canonicalCodeMap) throws RuntimeException {
        Long lastCanonicalCode = canonicalCodeMap.get(canonicalCode);
        if(!Objects.isNull(lastCanonicalCode)){
            canonicalCodeMap.put(canonicalCode,lastCanonicalCode +1);
            return canonicalCodeMap.get(canonicalCode);
        }
        for (int i = 1; i < 999; i++) {
            Long currentCanonicalCode = canonicalCode + i;
            List<GB2260> gb2260s = gb2260Dao.findByCanonicalCode(currentCanonicalCode);
            if(gb2260s.isEmpty()){
               canonicalCodeMap.put(canonicalCode,currentCanonicalCode);
               return currentCanonicalCode;
            }
        }
        throw new RuntimeException(String.format("找不到合适的自然村编码，因为%s的容量已经超过999",canonicalCode));
    }
    private List<County> sheetToCounties(Integer startRowIndex, Sheet countySheet){
        List<County> counties = new ArrayList<>();
        for(int i = startRowIndex; i < countySheet.getLastRowNum(); i++) {
            Row row = countySheet.getRow(i);
            String countyName = row.getCell(0).getStringCellValue();
            String countyPrefix = row.getCell(1).getStringCellValue();
            String govCountyCode = row.getCell(2).getStringCellValue();
            County county = new County();
            counties.add(county);
            county.setDomainPrefix(countyPrefix);
            county.setLocation(Long.parseLong(govCountyCode));
            county.setName(countyName);

        }
        return counties;
    }


    private List<Operator> sheetToOperators(Integer startRowIndex, Sheet operatorSheet){
        List<Operator> operators = new ArrayList<>();
        for(int i = startRowIndex; i < operatorSheet.getLastRowNum(); i++) {
            Row row = operatorSheet.getRow(i);
            String loginName = row.getCell(0).getStringCellValue();
            String password = row.getCell(1).getStringCellValue();
            String userName = row.getCell(2).getStringCellValue();

            Operator operator = new Operator();
            operators.add(operator);
            operator.setAccountState(OperatorAccountState.AVAILABLE);
            operator.setUsername(loginName);
            operator.setIdName(userName);
            operator.setPassword(passEncoder.encode(password));
        }
        return operators;
    }


    private boolean verifyCounty(String countySheet, SXSSFWorkbook verifyWorkbook){
        boolean verifyResult = true;

        XSSFSheet sourceDataSheet = excelToolAndCommonService.getSourceSheetByName(countySheet);
        int verifyRowCount = 1;
        Sheet verifySheet = excelToolAndCommonService.getNewSheet(verifyWorkbook, countySheet, "原始行号,名称,域名前缀,对应县行政区划编码,备注",",");
        if(sourceDataSheet.getLastRowNum() > 2){
            Row verifyRow = verifySheet.createRow(verifyRowCount++);
            excelToolAndCommonService.fillSheetRow(0, verifyRow, "", "", "","行政县中只能填写一条记录");
            return false;
        }
        for(int i = headIndex +1; i < sourceDataSheet.getLastRowNum(); i++) {

            StringBuilder sb = new StringBuilder();
            Row row = sourceDataSheet.getRow(i);

            String countyName = row.getCell(0).getStringCellValue();
            String countyPrefix = row.getCell(1).getStringCellValue();
            Integer count = 1;
            String govCountyCode = row.getCell(2).getStringCellValue();
            if(Strings.isNullOrEmpty(countyName) || Strings.isNullOrEmpty(countyPrefix)|| Strings.isNullOrEmpty(govCountyCode)){
                sb.append(count++).append("、名称,域名前缀,所属行政村编码均不能为空！\r\n");
            }else{
                Long govCountLong = null;
                try {
                    govCountLong = Long.parseLong(govCountyCode);
                } catch (NumberFormatException e) {
                    sb.append(count++).append("、行政区划编码必须是15位数字\r\n");
                }
                if(!Objects.isNull(govCountLong)) {
                    if (gb2260Dao.findByNameAndCanonicalCode(countyName, govCountLong).size() < 1) {
                        sb.append(count++).append("、行政区划编码及对应名称在标准数据库中不存在\r\n");
                    }else {
                        countyPrefix = govCountyCode.substring(0,6);
                    }
                }
                if(!Objects.isNull(govCountLong) && countyDao.findByNameAndLocation(countyName, govCountLong).size() > 0){
                    sb.append(count++).append("、行政区划编码及对应名称在数据库中已存在");
                }
            }
            if(!Strings.isNullOrEmpty(countyName) && countyName.length() > 64){
                sb.append(count++).append("、名称的长度不能超过64");
            }
            if(!Strings.isNullOrEmpty(countyPrefix) && countyPrefix.length() > 20){
                sb.append(count++).append("、域名前缀的长度不能超过20");
            }
            if(count.compareTo(1) > 0){
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                excelToolAndCommonService.fillSheetRow(i+1, verifyRow, countyName, countyPrefix, govCountyCode,sb.toString());
                verifyResult = false;

            }
        }
        return verifyResult;
    }


    private boolean verifyVillage(String villageSheet,  Set<String> villageSet, SXSSFWorkbook verifyWorkbook){
        boolean resultVerify = true;

        XSSFSheet sourceDataSheet = excelToolAndCommonService.getSourceSheetByName(villageSheet);
        Sheet verifySheet = excelToolAndCommonService.getNewSheet(verifyWorkbook, villageSheet, "原始行号,自然村名称,所属行政村编码,备注",",");
        int verifyRowCount = 1;

        for(int i = headIndex +1; i < sourceDataSheet.getLastRowNum(); i++) {
            Row row = sourceDataSheet.getRow(i);
            StringBuilder sb = new StringBuilder();
            Integer count = 1;
            String villageName = row.getCell(0).getStringCellValue();
            String govVillageCode = row.getCell(1).getStringCellValue();
            if(Strings.isNullOrEmpty(villageName) || Strings.isNullOrEmpty(govVillageCode)){
                sb.append(count++).append("、自然村名称,所属行政村编码均不能为空！\r\n");
            }else {
                Long govVillageCodeL = null;

                try {
                    govVillageCodeL = Long.parseLong(govVillageCode);
                } catch (NumberFormatException e) {
                    sb.append(count++).append("、所属行政村编码必须是15位数字\r\n");
                }
                if(!Objects.isNull(govVillageCodeL) && !govVillageCode.startsWith(countyPrefix)){
                    sb.append(count++).append(String.format("、所属行政村编码不属于行政县，其编码不为%s开头",countyPrefix));
                }
                if(!Objects.isNull(govVillageCodeL) && gb2260Dao.findByCanonicalCodeAndDepth(govVillageCodeL, 5).size() < 1){
                    sb.append(count++).append("、所属行政村编码在数据库中不存在或不为行政村级别\r\n");
                }
                if(!Objects.isNull(govVillageCodeL) && gb2260Dao.findByNameAndCanonicalCode(villageName,govVillageCodeL).size() > 0){
                    sb.append(count++).append("、自然村名称在数据库相同行政村编码中有重复\r\n");
                }

            }
            if(villageSet.contains(villageName)){
                sb.append(count++).append("、自然村名称在excel中有重复\r\n");
            }
            villageSet.add(villageName);

            if(count.compareTo(1) > 0){
                resultVerify = false;
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                excelToolAndCommonService.fillSheetRow(i+1,verifyRow,villageName,govVillageCode,sb.toString());
            }
        }
        return resultVerify;
    }

    private boolean verifyClinic(String clinicSheet, Set<String> villageSet, SXSSFWorkbook verifyWorkbook){

        Set<String> clinicNameSet = new HashSet<>();
        XSSFSheet sourceDataSheet = excelToolAndCommonService.getSourceSheetByName(clinicSheet);
        boolean result = true;

        Sheet verifySheet = excelToolAndCommonService.getNewSheet(verifyWorkbook, clinicSheet, "原始行号,名称,上级医疗机构,级别,管辖自然村,备注",",");
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
            String[] villages = scopeVillage.split("[;；]");
            String villageResult = checkVillage(villageSet, villages);
            if(!Strings.isNullOrEmpty(villageResult)) {
                sb.append(count++).append("、").append(villageResult).append("在自然村中不存在\r\n");
            }
            if(count.compareTo(1) > 0){
                result = false;
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                excelToolAndCommonService.fillSheetRow(i+1,verifyRow,clinicName,upClinicName,clinicClass,scopeVillage,sb.toString());
            }
        }
        return result;
    }

    private String checkVillage(Set<String> villageSet,String[] villages){
        StringBuilder result = new StringBuilder();
        for(String village : villages){
            if(!villageSet.contains(village)){
                result.append(village).append("、");
            }
        }
       if(result.length() > 1){
           return result.substring(0,result.length() - 1);
       }
       return result.toString();
    }
    private boolean verifyOperator(String operatorSheet, SXSSFWorkbook verifyWorkbook){
        boolean verifyResult = true;
        Set<String> userNameSet = new HashSet<>();
        XSSFSheet sourceDataSheet = excelToolAndCommonService.getSourceSheetByName(operatorSheet);
        Sheet verifySheet = excelToolAndCommonService.getNewSheet(verifyWorkbook, operatorSheet, "原始行号,用户名,密码,姓名,备注",",");
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
                excelToolAndCommonService.fillSheetRow(i+1,verifyRow,loginName,password,userName,sb.toString());
            }
        }
        return verifyResult;

    }

}

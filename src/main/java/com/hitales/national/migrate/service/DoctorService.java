package com.hitales.national.migrate.service;

import com.google.common.base.Strings;
import com.hitales.national.migrate.common.IdCard;
import com.hitales.national.migrate.common.Phone;
import com.hitales.national.migrate.dao.DoctorClinicDao;
import com.hitales.national.migrate.dao.DoctorDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private ExcelToolAndCommonService excelToolAndCommonService;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private DoctorClinicDao doctorClinicDao;

    @Override
    public boolean verify(String sheetName){
        SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolAndCommonService.MAX_READ_SIZE);
        boolean verifyResult = verifyDoctor(sheetName,verifyWorkbook);
        excelToolAndCommonService.saveExcelFile(verifyWorkbook, sheetName);
        return verifyResult;
    }

    @Override
    public boolean importToDb(String sheetName){

        return true;
    }


    private boolean verifyDoctor(String doctorSheet, SXSSFWorkbook verifyWorkbook){
        int verifyRowCount = 1;
        boolean result = true;
        Set<String> idcardSet = new HashSet<>();
        Set<String> phoneSet = new HashSet<>();
        XSSFSheet sourceDataSheet = excelToolAndCommonService.getSourceSheetByName(doctorSheet);

        Sheet verifySheet = excelToolAndCommonService.getNewSheet(verifyWorkbook, doctorSheet, "原始行号,身份证号,身份证姓名,民族,家庭住址,手机号,所属医疗机构,备注",",");

        for(int i = 1; i < sourceDataSheet.getLastRowNum(); i++) {
            Row row = sourceDataSheet.getRow(i);
            Integer count = 1;
            String idCard = row.getCell(0).getStringCellValue();
            String idName = row.getCell(1).getStringCellValue();
            String nation = row.getCell(2).getStringCellValue();
            String address = row.getCell(3).getStringCellValue();
            String phone = row.getCell(4).getStringCellValue();
            String clinic = row.getCell(5).getStringCellValue();
            StringBuilder sb = new StringBuilder();
            if(Objects.isNull(IdCard.tryParse(idCard))){
                sb.append(count++).append("、身份证号码为空或格式不正确\r\n");
            }
            if(idcardSet.contains(idCard)){
                sb.append(count++).append("、身份证号码在excel中重复\r\n");
            }
            idcardSet.add(idCard);
            if(doctorDao.findByIdNo(idCard).size() > 0){
                sb.append(count++).append("、身份证号码在数据库中重复\r\n");
            }
            if(Strings.isNullOrEmpty(idName) || idName.length() > 35){
                sb.append(count++).append("、身份证姓名为空或长度大于35\r\n");
            }
            if(Strings.isNullOrEmpty(nation) || Objects.isNull(excelToolAndCommonService.getNation(nation))){
                sb.append(count++).append("、民族为空或所填值不在56个民族中\r\n");
            }
            if(Strings.isNullOrEmpty(address) || address.length() > 100){
                sb.append(count++).append("、家庭住址为空或长度大于100\n");
            }
            if(Strings.isNullOrEmpty(phone) || Phone.match(phone)){
                sb.append(count++).append("、电话号码为空或格式不正确\n");
            }
            if(Strings.isNullOrEmpty(clinic) || doctorClinicDao.findByName(clinic).size() < 1){
                sb.append(count++).append("、所属医疗机构为空或在数据库中不存在\r\n");
            }
            if(count.compareTo(1) > 0){
                result = false;
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                excelToolAndCommonService.fillSheetRow(i+1,verifyRow,idCard,idName,nation,address,phone,clinic,sb.toString());
            }
        }
        return result;
    }

}

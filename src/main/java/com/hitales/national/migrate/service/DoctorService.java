package com.hitales.national.migrate.service;

import com.google.common.base.Strings;
import com.hitales.national.migrate.common.IdCard;
import com.hitales.national.migrate.common.Phone;
import com.hitales.national.migrate.dao.DoctorClinicDao;
import com.hitales.national.migrate.dao.DoctorDao;
import com.hitales.national.migrate.entity.Doctor;
import com.hitales.national.migrate.entity.DoctorClinic;
import com.hitales.national.migrate.enums.DoctorGender;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:14:37
 */
@Service
@Slf4j
public class DoctorService{
    @Autowired
    private CommonToolsService commonToolsService;

    @Autowired
    private DoctorDao doctorDao;

    private Map<String,Integer> clinicMap;

    @Autowired
    private DoctorClinicDao doctorClinicDao;

    public boolean verify(String sheetName){
        SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(CommonToolsService.MAX_READ_SIZE);
        boolean verifyResult = verifyDoctor(sheetName,verifyWorkbook);
        commonToolsService.saveExcelFile(verifyWorkbook, sheetName);
        return verifyResult;
    }

    public boolean importToDb(String sheetName){
        if(!verify(sheetName)){
            return false;
        }

        clinicMap = new HashMap<>();

        XSSFSheet sourceDataSheet = commonToolsService.getSourceSheetByName(sheetName);
        List<Doctor> doctors = sheetToDoctors(1,sourceDataSheet);

        doctorDao.saveAll(doctors);

        return true;
    }


    private List<Doctor> sheetToDoctors(Integer startRowIndex, Sheet doctorSheet){
        List<Doctor> doctors = new ArrayList<>();
        for(int i = startRowIndex; i <= doctorSheet.getLastRowNum(); i++) {
            Row row = doctorSheet.getRow(i);
            Doctor doctor = new Doctor();
            doctors.add(doctor);
            String idCard = row.getCell(0).getStringCellValue();
            String idName = row.getCell(1).getStringCellValue();
            String nation = row.getCell(2).getStringCellValue();
            String address = row.getCell(3).getStringCellValue();
            String phone = row.getCell(4).getStringCellValue();
            String clinic = row.getCell(5).getStringCellValue();

            doctor.setIdNo(idCard);
            doctor.setIdName(idName);
            IdCard cardInfo = IdCard.tryParse(idCard);
            if(Objects.isNull(cardInfo)){
                throw new RuntimeException(String.format("身份证号码【%s】格式错误！",idCard));
            }
            doctor.setGender(getDoctorGender(cardInfo.getGender()));
            doctor.setNation(commonToolsService.getNation(nation));
            doctor.setBirthday(cardInfo.getBirthday().toDate());
            doctor.setAddress(address);
            doctor.setPhone(phone);
            doctor.setClinicId(getClinicCode(clinic));
        }

        return doctors;
    }

    private DoctorGender getDoctorGender(Integer gender){
        if(gender.equals(1)){
            return DoctorGender.MALE;
        }
        if(gender.equals(2)){
            return DoctorGender.FEMALE;
        }
        return DoctorGender.NOT_SPECIFIED;
    }
    private Integer getClinicCode(String clinic){
        Integer clinicCode = clinicMap.get(clinic);
        if(Objects.isNull(clinicCode)){
            List<DoctorClinic> doctorClinics = doctorClinicDao.findByName(clinic);
            if(doctorClinics.isEmpty()){
                throw new RuntimeException(String.format("医疗机构信息[%s]在数据库中不存在！",clinic));
            }
            if(doctorClinics.size() > 1){
                throw new RuntimeException(String.format("医疗机构信息[%s]在数据库中存在%s条",clinic,doctorClinics.size()));
            }
            clinicMap.put(clinic,doctorClinics.get(0).getClinicId());
            return clinicMap.get(clinic);
        }
        return clinicCode;
    }

    private boolean verifyDoctor(String doctorSheet, SXSSFWorkbook verifyWorkbook){
        int verifyRowCount = 1;
        boolean result = true;
        Set<String> idcardSet = new HashSet<>();
        Set<String> phoneSet = new HashSet<>();
        XSSFSheet sourceDataSheet = commonToolsService.getSourceSheetByName(doctorSheet);

        Sheet verifySheet = commonToolsService.getNewSheet(verifyWorkbook, doctorSheet, "原始行号,身份证号,身份证姓名,民族,家庭住址,手机号,所属医疗机构,备注",",");

        for(int i = 1; i <= sourceDataSheet.getLastRowNum(); i++) {
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
            if(Strings.isNullOrEmpty(nation) || Objects.isNull(commonToolsService.getNation(nation))){
                sb.append(count++).append("、民族为空或所填值不在56个民族中\r\n");
            }
            if(Strings.isNullOrEmpty(address) || address.length() > 100){
                sb.append(count++).append("、家庭住址为空或长度大于100\r\n");
            }
            if(Strings.isNullOrEmpty(phone) || Phone.match(phone)){
                sb.append(count++).append("、电话号码为空或格式不正确\r\n");
            }
            if(phoneSet.contains(phone)){
                sb.append(count++).append("、电话号码在excel中重复\r\n");
            }
            phoneSet.add(phone);
            if(doctorDao.findByPhone(phone).size() > 0){
                sb.append(count++).append("、电话号码在数据库中重复\r\n");
            }
            if(Strings.isNullOrEmpty(clinic) || doctorClinicDao.findByName(clinic).size() < 1){
                sb.append(count++).append("、所属医疗机构为空或在数据库中不存在\r\n");
            }
            if(count.compareTo(1) > 0){
                result = false;
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                commonToolsService.fillSheetRow(i+1,verifyRow,idCard,idName,nation,address,phone,clinic,sb.toString());
            }
        }
        return result;
    }

}

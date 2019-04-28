package com.hitales.national.migrate.service;

import com.google.common.base.Strings;
import com.hitales.national.migrate.common.IdCard;
import com.hitales.national.migrate.common.Phone;
import com.hitales.national.migrate.dao.CitizenDao;
import com.hitales.national.migrate.dao.GB2260Dao;
import com.hitales.national.migrate.entity.Citizen;
import com.hitales.national.migrate.entity.GB2260;
import com.hitales.national.migrate.enums.CitizenGender;
import com.hitales.national.migrate.enums.IdType;
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
 * @time:14:30
 */

@Service
@Slf4j
public class CitizenService implements BasicService {

    @Autowired
    private ExcelToolAndCommonService excelToolAndCommonService;

    @Autowired
    private CitizenDao citizenDao;

    @Autowired
    private GB2260Dao gb2260Dao;

    private Map<String,Long> villageMap;

    @Override
    public boolean verify(String sheetName){
         SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(ExcelToolAndCommonService.MAX_READ_SIZE);
         boolean verifyResult = verifyCitizen(sheetName,verifyWorkbook);

         excelToolAndCommonService.saveExcelFile(verifyWorkbook, sheetName);
         return verifyResult;
    }

    @Override
    public boolean importToDb(String sheetName){
        if(!verify(sheetName)){
            return false;
        }
        villageMap = new HashMap<>();
        List<Citizen> citizens = new ArrayList<>();
        XSSFSheet sourceDataSheet = excelToolAndCommonService.getSourceSheetByName(sheetName);
        // 将excel中数据全部取出转换为citizen再统一存储
        for (int i = 1; i < sourceDataSheet.getLastRowNum(); i++) {
            Row row = sourceDataSheet.getRow(i);
            citizens.add(sheetRowToCitizen(row));
        }

        // 存储入数据库
        int SAVE_COUNT = 500;
        int i = 0;
        for(; (i+1)*SAVE_COUNT <= citizens.size(); i ++){
            citizenDao.saveAll(citizens.subList(i*SAVE_COUNT, (i+1)*SAVE_COUNT));
        }
        if(i*SAVE_COUNT != citizens.size()) {
            citizenDao.saveAll(citizens.subList(i * SAVE_COUNT, citizens.size()));
        }
        return true;
    }

    private Citizen sheetRowToCitizen(Row citizenRow){
        Citizen citizen = new Citizen();

        String cardType = citizenRow.getCell(0).getStringCellValue();
        String idCard = citizenRow.getCell(1).getStringCellValue();
        String idName = citizenRow.getCell(2).getStringCellValue();
        String nation = citizenRow.getCell(3).getStringCellValue();
        String address = citizenRow.getCell(4).getStringCellValue();
        String phone = citizenRow.getCell(5).getStringCellValue();
        String village = citizenRow.getCell(6).getStringCellValue();

        citizen.setIdType( cardType.equals(IdType.BIRTH.getDesc())? IdType.BIRTH : IdType.ID);
        IdCard cardInfo = IdCard.tryParse(idCard);
        if(Objects.isNull(cardInfo)){
            throw new RuntimeException(String.format("身份证号码【{}】格式错误！",idCard));
        }
        citizen.setIdNo(idCard);
        citizen.setGender(getGender(cardInfo.getGender()));
        citizen.setNation(excelToolAndCommonService.getNation(nation));
        citizen.setBirthday(cardInfo.getBirthday().toDate());
        citizen.setAddress(address);
        citizen.setPhone(phone);
        citizen.setIdName(idName);
        citizen.setLocation(getVillageCode(village));
        return citizen;
    }

    public CitizenGender getGender(Integer gender){
        if(gender.equals(1)){
            return CitizenGender.MALE;
        }
        if(gender.equals(2)){
            return CitizenGender.FEMALE;
        }
        return CitizenGender.NOT_SPECIFIED;
    }

    private Long getVillageCode(String villageName){
        Long villageCode = villageMap.get(villageName);
        if(Objects.isNull(villageCode)){
            List<GB2260> gb2260s = gb2260Dao.findByNameAAndDepth(villageName,6);
            if(gb2260s.isEmpty()){
                throw new RuntimeException(String.format("村信息[{}]在数据库中不存在！",villageName));
            }
            if(gb2260s.size() > 1){
                throw new RuntimeException(String.format("村信息[{}]在数据库中存在{}条",villageName,gb2260s.size()));
            }
            villageMap.put(villageName,gb2260s.get(0).getCanonicalCode());
            return villageMap.get(villageName);
        }
        return villageCode;
    }


    private boolean verifyCitizen(String citizenSheet, SXSSFWorkbook verifyWorkbook) {
        int verifyRowCount = 1;
        Set<String> idcardSet = new HashSet<>();
        boolean result = true;

        XSSFSheet sourceDataSheet = excelToolAndCommonService.getSourceSheetByName(citizenSheet);

        Sheet verifySheet = excelToolAndCommonService.getNewSheet(verifyWorkbook, citizenSheet, "原始行号,证件类型,证件号码,证件姓名,民族,家庭住址,本人电话,所属自然村,备注", ",");

        for (int i = 1; i < sourceDataSheet.getLastRowNum(); i++) {
            Row row = sourceDataSheet.getRow(i);
            Integer count = 1;
            String cardType = row.getCell(0).getStringCellValue();
            String idCard = row.getCell(1).getStringCellValue();
            String idName = row.getCell(2).getStringCellValue();
            String nation = row.getCell(3).getStringCellValue();
            String address = row.getCell(4).getStringCellValue();
            String phone = row.getCell(5).getStringCellValue();
            String village = row.getCell(6).getStringCellValue();
            StringBuilder sb = new StringBuilder();
            if(!"身份证".equals(cardType) && !"出生证明".equals(cardType)){
                sb.append(count++).append("、证件类型只能为【身份证】或【出生证明】且不能为空");
            }
            if(Objects.isNull(IdCard.tryParse(idCard))){
                sb.append(count++).append("、身份证号码为空或格式不正确\r\n");
            }
            if(citizenDao.findByIdNo(idCard).size() > 0){
                sb.append(count++).append("、身份证号码在数据库中重复\r\n");
            }
            if(idcardSet.contains(idCard)){
                sb.append(count++).append("、身份证号码在excel中重复\r\n");
            }
            if(Strings.isNullOrEmpty(idName) || idName.length() > 30){
                sb.append(count++).append("、身份证姓名为空或长度大于30\r\n");
            }
            idcardSet.add(idCard);
            if(Strings.isNullOrEmpty(nation) || Objects.isNull(excelToolAndCommonService.getNation(nation))){
                sb.append(count++).append("、民族为空或所填值不在56个民族中\r\n");
            }
            if(Strings.isNullOrEmpty(phone) || Phone.match(phone)){
                sb.append(count++).append("、电话号码为空或格式不正确\r\n");
            }
            if(Strings.isNullOrEmpty(address) || address.length() > 200){
                sb.append(count++).append("、家庭住址为空或长度大于200\r\n");
            }
            if(Strings.isNullOrEmpty(village) || gb2260Dao.findByNameAAndDepth(village, 6).size() < 1){
                sb.append(count++).append("、所属自然村为空或在数据库中不存在\r\n");
            }

            if(count.compareTo(1) > 0){
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                result = false;
                excelToolAndCommonService.fillSheetRow(i+1,verifyRow,idCard,idName,nation,address,phone,village,sb.toString());
            }
        }
        return result;
    }

}

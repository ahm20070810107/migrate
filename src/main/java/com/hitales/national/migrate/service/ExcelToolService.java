package com.hitales.national.migrate.service;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:13:51
 */

@Component
@Slf4j
public class ExcelToolService {
    @Value("${excel.sourceFile}")
    private String sourceFile;

    @Value("${excel.verifyResultFile}")
    private String verifyResultFile;


    public static final Integer MAX_READ_SIZE = 1000;
    private XSSFWorkbook xssfSourceWorkbook;

    @PostConstruct
    private void init(){
        if(Strings.isNullOrEmpty(sourceFile)){
            throw new RuntimeException("excel路径为空！");
        }
        try {
            xssfSourceWorkbook = new XSSFWorkbook(sourceFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public XSSFSheet getSourceSheetByName(String sheetName){
        if(Strings.isNullOrEmpty(sheetName)){
            throw new RuntimeException("sheetName不能为空！");
        }
        XSSFSheet xssfSheet = xssfSourceWorkbook.getSheet(sheetName);
        if(Objects.isNull(xssfSheet)){
            throw new RuntimeException(String.format("【{}】sheet在excel中不存在！", sheetName));
        }
        if(xssfSheet.getLastRowNum() < 2){
            throw new RuntimeException(String.format("【{}】sheet的数据为空！", sheetName));
        }
        return xssfSheet;
    }


    public void saveExcelFile(SXSSFWorkbook sxssfWorkbook, String saveType){
        String savePath = verifyResultFile + "_" + saveType +".xlsx";
        File file = new File(savePath);
        if(file.exists()) {
            if(!file.delete()){
                throw new RuntimeException(String.format("旧校验结果【{}】删除失败，不能写入新校验结果！",savePath));
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(savePath);
            sxssfWorkbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Sheet getNewSheet(SXSSFWorkbook workbook, String sheetName, String headerDes, String splitChar){
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        String[] headers = headerDes.split(splitChar);
        Row row = sheet.createRow(0);
        for(int i = 0; i<headers.length; i++){
            row.createCell(i).setCellValue(headers[i]);
        }
        return sheet;
    }

    public void fillSheetRow(int index,Row row, String ... params){
        int cellIndex = 1;
        row.createCell(0).setCellValue(index);
        for(String param : params){
            row.createCell(cellIndex++).setCellValue(param);
        }
    }
}

package com.hitales.national.migrate.common;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
public class ReadExcelTool {
    @Value("${excel.pathFileName}")
    private String pathFileName;

    private final Integer MAX_READ_SIZE = 10000;
    private SXSSFWorkbook sxssfWorkbook;

    private static ReadExcelTool readExcelTool;
    private ReadExcelTool(){
        if(Strings.isNullOrEmpty(pathFileName)){
            throw new RuntimeException("excel路径为空！");
        }
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(pathFileName);
            sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook,MAX_READ_SIZE);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static ReadExcelTool getInstance(){
        if(Objects.isNull(readExcelTool)){
            initInstance();
        }
        return readExcelTool;
    }

    private synchronized static void initInstance(){
        if(Objects.isNull(readExcelTool)){
            readExcelTool = new ReadExcelTool();
        }
    }

    public SXSSFSheet getSheetByName(String sheetName){
        if(Strings.isNullOrEmpty(sheetName)){
            throw new RuntimeException("sheetName不能为空！");
        }
        SXSSFSheet sxssfSheet = sxssfWorkbook.getSheet(sheetName);
        if(Objects.isNull(sxssfSheet)){
            return sxssfWorkbook.createSheet(sheetName);
        }
        return sxssfSheet;
    }

}

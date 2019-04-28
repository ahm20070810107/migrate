package com.hitales.national.migrate.service;

import com.hitales.national.migrate.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-26
 * @time:19:00
 */

@Component
@Slf4j
public class SheetEntityService {

    public List<County> sheetToCounty(Integer startRowIndex, Sheet countySheet){
        return null;
    }

    public List<GB2260> sheetToVillage(Integer startRowIndex, Sheet villageSheet){
        return null;
    }

    public List<DoctorClinic> sheetToClinic(Integer startRowIndex, Sheet clinicSheet){
        return null;
    }

    public List<Operator> sheetToOperator(Integer startRowIndex, Sheet operatorSheet){
        return null;
    }

}

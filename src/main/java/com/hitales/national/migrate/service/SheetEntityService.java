package com.hitales.national.migrate.service;

import com.hitales.national.migrate.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

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


    public Citizen sheetToCitizen(Sheet citizenSheet){
        return null;
    }

    public Doctor sheetToDoctor(Sheet doctorSheet){
        return null;
    }

    public County sheetToCounty(Sheet countySheet){
        return null;
    }

    public GB2260 sheetToVillage(Sheet villageSheet){
        return null;
    }

    public DoctorClinic sheetToClinic(Sheet clinicSheet){
        return null;
    }

    public Operator sheetToOperator(Sheet operatorSheet){
        return null;
    }

}

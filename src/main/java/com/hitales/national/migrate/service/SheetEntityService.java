package com.hitales.national.migrate.service;

import com.hitales.national.migrate.entity.DoctorClinic;
import com.hitales.national.migrate.entity.GB2260;
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




    public List<DoctorClinic> sheetToClinic(Integer startRowIndex, Sheet clinicSheet){
        return null;
    }



}

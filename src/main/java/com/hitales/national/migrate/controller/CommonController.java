package com.hitales.national.migrate.controller;

import com.hitales.national.migrate.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:10:35
 */

@RestController
@Slf4j
@Validated
@RequestMapping("common")
public class CommonController {

    @Value("${excel.operatorSheet}")
    private String operatorSheet;

    @Value("${excel.clinicSheet}")
    private String clinicSheet;

    @Value("${excel.countySheet}")
    private String countySheet;

    @Value("${excel.villageSheet}")
    private String villageSheet;

    @Autowired
    private CommonService commonService;

    private static Object lock = new Object();
    private static Boolean operateFlag = false;

    @GetMapping("verify")
    public String verify(){

        synchronized (lock){
            if(operateFlag){
                return "公共信息校验或入库正在执行中 。。。";
            }
            operateFlag = true;
        }

        try {
            commonService.verify(operatorSheet, clinicSheet, countySheet, villageSheet);
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally {
            synchronized (lock){
                operateFlag = false;
            }
        }
        return "公共信息校验完成，请到excel.path配置下查看校验结果。";
    }

    @GetMapping("importDb")
    public String importToDb(){
        synchronized (lock){
            if(operateFlag){
                return "公共信息校验或入库正在执行中 。。。";
            }
            operateFlag = true;
        }
        try {
            commonService.importToDb(operatorSheet, clinicSheet, countySheet, villageSheet);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            synchronized (lock){
                operateFlag = false;
            }
        }
        return "公共信息入库完成！";
    }

}

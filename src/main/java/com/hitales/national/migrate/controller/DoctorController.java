package com.hitales.national.migrate.controller;

import com.hitales.national.migrate.service.BasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * @time:10:43
 */

@RestController
@Slf4j
@Validated
@RequestMapping("doctor")
public class DoctorController {
    @Value("${excel.doctorSheet}")
    private String doctorSheet;

    @Autowired
    @Qualifier("doctorService")
    private BasicService basicService;

    private static Object lock = new Object();

    private static Boolean operateFlag = false;

    @GetMapping("verify")
    public String verify(){

        synchronized (lock){
            if(operateFlag){
                return "医生信息校验或入库正在执行中 。。。";
            }
            operateFlag = true;
        }

        try {
            basicService.verify(doctorSheet);
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally {
            synchronized (lock){
                operateFlag = false;
            }
        }
        return "医生信息校验完成，请到excel.path配置下查看校验结果。";
    }

    @GetMapping("importDb")
    public String importToDb(){
        synchronized (lock){
            if(operateFlag){
                return "医生信息校验或入库正在执行中 。。。";
            }
            operateFlag = true;
        }
        try {
            if(basicService.importToDb(doctorSheet)){
                return "医生信息入库完成！";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            synchronized (lock){
                operateFlag = false;
            }
        }
        return "医生信息入库未完成，请查看日志！";
    }

}

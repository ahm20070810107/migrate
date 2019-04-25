package com.hitales.national.migrate.controller;

import com.google.common.base.Strings;
import com.hitales.national.migrate.service.BasicService;
import com.hitales.national.migrate.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:10:34
 */
@RestController
@Slf4j
@Validated
@RequestMapping("citizen/")
public class CitizenController {
    private final String DEFAULT_SHEET_NAME = "居民信息";

    @Autowired
    @Qualifier("citizenService")
    private BasicService basicService;

    @Autowired
    private CitizenService citizenService;

    @GetMapping("verify")
    public String verify(@RequestParam(value = "sheetName", required = false)String sheetName){
        if(Strings.isNullOrEmpty(sheetName)){
            sheetName = DEFAULT_SHEET_NAME;
        }
        citizenService.verity(sheetName);
        return "校验完成，请到excel.path配置下查看校验结果。";
    }


}

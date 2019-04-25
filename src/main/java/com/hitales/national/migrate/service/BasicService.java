package com.hitales.national.migrate.service;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:14:31
 */
public interface BasicService {

    void verity(String sheetName);
    void importToDb(String sheetName);
}

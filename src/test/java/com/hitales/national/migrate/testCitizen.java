package com.hitales.national.migrate;

import com.hitales.national.migrate.dao.CitizenDao;
import com.hitales.national.migrate.entity.Citizen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-24
 * @time:16:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MigrateApplication.class)
public class testCitizen {

    @Autowired
    private CitizenDao citizenDao;
    @Test
    public void testGetCitizen1(){
        List<Citizen> citizenList = citizenDao.findAll();
        for (Citizen citizen :citizenList) {
            System.out.println(citizen);
        }
    }


}

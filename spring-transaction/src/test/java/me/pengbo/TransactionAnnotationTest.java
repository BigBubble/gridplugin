package me.pengbo;


import me.pengbo.entity.Transaction;
import me.pengbo.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * <p>
 *   测试使用注解配置
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 11:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-onlyannotation.xml")
public class TransactionAnnotationTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private TransactionService transactionService;

    @Before
    public void insertTwo(){
        transactionService.deleteAll();
        Transaction t = new Transaction();
        t.setId(1);
        t.setValue(1);
        transactionService.insert(t);
        t.setId(2);
        t.setValue(2);
        transactionService.insert(t);
    }

    @Test
    public void update() {
        Transaction t = new Transaction();
        t.setId(1);
        t.setValue(2);
        try {
            transactionService.updateNo(t);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t = transactionService.getById(t.getId());
        assert t.getValue() == 1;
        System.out.println("id:" + t.getId() + ",value:" + t.getValue());

        t.setId(2);
        t.setValue(4);

        try {
            transactionService.update(t);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t = transactionService.getById(t.getId());
        assert t.getValue() == 4;
        System.out.println("id:" + t.getId() + ",value:" + t.getValue());

    }
}

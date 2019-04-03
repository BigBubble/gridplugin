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
 *   xml中没有配置方法是否使用事务，加了注解
 *   事务起作用
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 11:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-exception-annotation.xml")
public class TransactionXmlExceptionAnnotationTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private TransactionService transactionService;

    @Before
    public void insertTwo(){
        transactionService.deleteAll();
        Transaction t = new Transaction();
        t.setId(1);
        t.setValue(1);
        transactionService.insert(t);
    }

    @Test
    public void update() {
        //更新id = 1 使用注解事务， 应该为更新后的值1
        Transaction t = new Transaction();
        t.setId(1);
        t.setValue(2);
        try {
            transactionService.updateWithAnnotation(t);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t = transactionService.getById(t.getId());
        assert t.getValue() == 1;
        System.out.println("id:" + t.getId() + ",value:" + t.getValue());

    }
}

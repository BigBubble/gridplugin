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
 *   调用一个无注解的方法，但是此方法内调用了一个本类内有注解的方法，事务无效
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 11:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-onlyannotation.xml")
public class TransactionAnnotationSameClassTest extends AbstractJUnit4SpringContextTests {

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
        Transaction t = new Transaction();
        t.setId(1);
        t.setValue(2);
        try {
            transactionService.withoutAnnotationInvokeAnnotationMethod(t);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t = transactionService.getById(t.getId());
        assert t.getValue() == 2;
        System.out.println("id:" + t.getId() + ",value:" + t.getValue());

    }
}

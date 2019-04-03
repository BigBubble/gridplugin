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
 *   xml中没有配置方法是否使用事务，没有配置注解驱动，使用@transactional注解
 *   <!--支持注解驱动的事务管理，指定事务管理器 -->
 *     <!-- <tx:annotation-driven transaction-manager="transactionManager"/> -->
 *   事务不起作用
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 11:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-exception-annotation-without-config-annotation.xml")
public class TransactionXmlExceptionAnnotationWithoutConfigAnnotationTest extends AbstractJUnit4SpringContextTests {

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
            transactionService.updateWithAnnotation(t);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t = transactionService.getById(t.getId());
        assert t.getValue() == 2;
        System.out.println("id:" + t.getId() + ",value:" + t.getValue());

    }
}

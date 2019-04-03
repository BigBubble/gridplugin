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
 *   测试使用xml配值和使用注解配置 哪个优先级高
 *   xml中配置updateNo方法不使用事务，在updateNo方法上加@Transational注解
 *   先插入一个（1，1）的记录，然后改变成（1，2） 抛出异常，最终数据库结果为（1，2），不回滚
 *   xml 优先级高于 注解
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 11:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class TransactionPriorityTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private TransactionService transactionService;

    @Before
    public void insertOne(){
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
            transactionService.updateNo(t);
        } catch (Exception e) {
            e.printStackTrace();
        }


        t = transactionService.getById(t.getId());

        assert t.getValue() == 2;
        System.out.println(t.getId() + "-" + t.getValue());
    }
}

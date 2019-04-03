package me.pengbo;


import me.pengbo.entity.Transaction;
import me.pengbo.service.OtherService;
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
 *   如果外层方法没事务，则当前方法新建一个事务；
 *   如果外层方法有事务，则把当前方法当成外层事务的一部分（使用savepoint实现），
 *   外层方法事务的rolback都会影响当前方法(与Require_new区别),
 *   而当前方法的rolback外层不try catch会导致外层事务回滚
 *
 * @author pengb
 * @since 2019-04-03 11:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-onlyannotation.xml")
public class TransactionPropogationNestedTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private TransactionService transactionService;

    @Resource
    private OtherService otherService;

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
        t.setId(3);
        t.setValue(3);
        transactionService.insert(t);
    }

    @Test
    public void update1() {
        Transaction t1 = new Transaction();
        t1.setId(1);
        t1.setValue(2);


        Transaction t2 = new Transaction();
        t2.setId(2);
        t2.setValue(4);

        try {
            otherService.propogationNestedCase1(t1, t2);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t1 = transactionService.getById(t1.getId());
        assert t1.getValue() == 1;
        System.out.println("id:" + t1.getId() + ",value:" + t1.getValue());

        t2 = transactionService.getById(t2.getId());
        assert t2.getValue() == 2;
        System.out.println("id:" + t2.getId() + ",value:" + t2.getValue());

    }


    @Test
    public void update2() {
        Transaction t1 = new Transaction();
        t1.setId(1);
        t1.setValue(2);


        Transaction t2 = new Transaction();
        t2.setId(2);
        t2.setValue(4);

        try {
            otherService.propogationNestedCase2(t1, t2);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t1 = transactionService.getById(t1.getId());
        assert t1.getValue() == 1;
        System.out.println("id:" + t1.getId() + ",value:" + t1.getValue());

        t2 = transactionService.getById(t2.getId());
        assert t2.getValue() == 2;
        System.out.println("id:" + t2.getId() + ",value:" + t2.getValue());

    }

    @Test
    public void update3() {
        Transaction t1 = new Transaction();
        t1.setId(1);
        t1.setValue(2);


        Transaction t2 = new Transaction();
        t2.setId(2);
        t2.setValue(4);

        Transaction t3 = new Transaction();
        t3.setId(3);
        t3.setValue(6);

        try {
            otherService.propogationNestedCase3(t1, t2, t3);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t1 = transactionService.getById(t1.getId());
        assert t1.getValue() == 1;
        System.out.println("id:" + t1.getId() + ",value:" + t1.getValue());

        t2 = transactionService.getById(t2.getId());
        assert t2.getValue() == 2;
        System.out.println("id:" + t2.getId() + ",value:" + t2.getValue());

        t3 = transactionService.getById(t3.getId());
        assert t3.getValue() == 3;
        System.out.println("id:" + t3.getId() + ",value:" + t3.getValue());

    }
}

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
 *   当前方法总是开启一个新的事务，如果外层方法有事务，则将外层事务挂起，先执行当前方法的事务（外层事务和当前方法的事务是两个不同的事务）。
 * 1.当当前方法发生回滚并抛出RuntimeException时，如果该异常被捕获，则外层方法的事务不会因此回滚；
 * 2.如果该异常没有被捕获，则外层方法的事务就会因此而回滚。
 * 3.当外层方法发生回滚时，如果其回滚发生在当前方法前，则当前方法得不到执行；
 * 4.如果其回滚发生在当前方法之后，则当前方法不会因此而回滚。
 *
 * @author pengb
 * @since 2019-04-03 11:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-onlyannotation.xml")
public class TransactionPropogationRequireNewTest extends AbstractJUnit4SpringContextTests {

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
            otherService.propogationRequireNewCase1(t1, t2);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t1 = transactionService.getById(t1.getId());
        assert t1.getValue() == 2;
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
            otherService.propogationRequireNewCase2(t1, t2);
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

        try {
            otherService.propogationRequireNewCase3(t1, t2);
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
    public void update4() {
        Transaction t1 = new Transaction();
        t1.setId(1);
        t1.setValue(2);


        Transaction t2 = new Transaction();
        t2.setId(2);
        t2.setValue(4);

        try {
            otherService.propogationRequireNewCase4(t1, t2);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        t1 = transactionService.getById(t1.getId());
        assert t1.getValue() == 1;
        System.out.println("id:" + t1.getId() + ",value:" + t1.getValue());

        t2 = transactionService.getById(t2.getId());
        assert t2.getValue() == 4;
        System.out.println("id:" + t2.getId() + ",value:" + t2.getValue());

    }
}

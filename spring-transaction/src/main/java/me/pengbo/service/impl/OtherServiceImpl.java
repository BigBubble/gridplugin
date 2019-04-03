package me.pengbo.service.impl;

import me.pengbo.entity.Transaction;
import me.pengbo.mapper.TransactionMapper;
import me.pengbo.service.OtherService;
import me.pengbo.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 15:30
 */
@Service
public class OtherServiceImpl implements OtherService {

    @Resource
    private TransactionMapper transactionMapper;

    @Resource
    private TransactionService transactionService;



    @Override
    @Transactional
    public void propogationRequireNewCase1(Transaction t1, Transaction t2) {

        transactionMapper.updateByPrimaryKey(t1);
        try{
            transactionService.updaterRqNew(t2);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void propogationRequireNewCase2(Transaction t1, Transaction t2) {
        transactionMapper.updateByPrimaryKey(t1);
        transactionService.updaterRqNew(t2);
    }

    @Override
    @Transactional
    public void propogationRequireNewCase3(Transaction t1, Transaction t2) {
        transactionMapper.updateByPrimaryKey(t1);
        if(4%2 == 0) {
            throw new RuntimeException("外层出错");
        }
        transactionService.updaterRqNewNoException(t2);
    }

    @Override
    @Transactional
    public void propogationRequireNewCase4(Transaction t1, Transaction t2) {
        transactionService.updaterRqNewNoException(t2);
        transactionMapper.updateByPrimaryKey(t1);
        if(4%2 == 0) {
            throw new RuntimeException("外层出错");
        }
    }

    @Override
    @Transactional
    public void propogationNestedCase1(Transaction t1, Transaction t2) {
        transactionService.updateNestedWithoutException(t1);
        transactionMapper.updateByPrimaryKey(t2);
        if(4%2 == 0) {
            throw new RuntimeException("外层出错");
        }
    }

    @Override
    @Transactional
    public void propogationNestedCase2(Transaction t1, Transaction t2) {
        transactionMapper.updateByPrimaryKey(t1);
        transactionService.updateNestedWithException(t2);
    }

    @Override
    @Transactional
    public void propogationNestedCase3(Transaction t1, Transaction t2, Transaction t3) {
        transactionMapper.updateByPrimaryKey(t1);
        transactionService.updateNestedWithoutException(t2);
        transactionMapper.updateByPrimaryKey(t3);
        int i = 1 / 0;
    }
}

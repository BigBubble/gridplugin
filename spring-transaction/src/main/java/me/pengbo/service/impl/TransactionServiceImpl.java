package me.pengbo.service.impl;

import javafx.scene.control.Pagination;
import me.pengbo.entity.Transaction;
import me.pengbo.entity.TransactionExample;
import me.pengbo.mapper.TransactionMapper;
import me.pengbo.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 11:12
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private TransactionMapper transactionMapper;

    @Override
    public Transaction getById(Integer id) {
        return transactionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(Transaction transaction){
        return transactionMapper.insert(transaction);
    }


    @Override
    public int update(Transaction transaction) {
        int i = transactionMapper.updateByPrimaryKey(transaction);

        if( 4 % 2 == 0) {
            throw new RuntimeException("我抛出了一个异常");
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        TransactionExample e = new TransactionExample();
        TransactionExample.Criteria c = e.createCriteria();
        c.andIdIsNotNull();
        transactionMapper.deleteByExample(e);
    }

    @Override
    @Transactional
    public int updateNo(Transaction transaction) {
        int i = transactionMapper.updateByPrimaryKey(transaction);

        if( 4 % 2 == 0) {
            throw new RuntimeException("我抛出了一个异常");
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateWithAnnotation(Transaction t) {
        int i = transactionMapper.updateByPrimaryKey(t);

        if( 4 % 2 == 0) {
            throw new RuntimeException("我抛出了一个异常");
        }
        return 0;
    }

    @Transactional
    private int doVisibilityPrivate(Transaction t) {
        transactionMapper.updateByPrimaryKey(t);
        if( 4 % 2 == 0) {
            throw new RuntimeException("我抛出了一个异常");
        }
        return 0;
    }

    @Transactional
    protected int doVisibilityProtected(Transaction t) {
        transactionMapper.updateByPrimaryKey(t);
        if( 4 % 2 == 0) {
            throw new RuntimeException("我抛出了一个异常");
        }
        return 0;
    }

    @Override
    public int withoutAnnotationInvokeAnnotationMethod(Transaction t) {
        return updateWithAnnotation(t);
    }


    // 传播机制

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    @Override
    public int updaterRqNew(Transaction t) {
        transactionMapper.updateByPrimaryKey(t);
        if( 4 % 2 == 0) {
            throw new RuntimeException("我抛出了一个异常");
        }
        return 0;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    @Override
    public int updaterRqNewNoException(Transaction t) {
        System.out.println("i'm executed...");
        return transactionMapper.updateByPrimaryKey(t);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void updateNestedWithoutException(Transaction t1) {
        transactionMapper.updateByPrimaryKey(t1);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void updateNestedWithException(Transaction t2) {
        transactionMapper.updateByPrimaryKey(t2);
        if(4 % 2 == 0) {
            throw new RuntimeException("我一场了");
        }
    }
}

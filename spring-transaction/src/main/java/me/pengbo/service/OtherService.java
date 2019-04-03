package me.pengbo.service;

import me.pengbo.entity.Transaction;

/**
 * <p>
 *
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 15:30
 */
public interface OtherService {

    /**
     * 传播 REQUIRE_NEW 内部事务异常 外层try catch
     * @param t1
     * @param t2
     */
    void propogationRequireNewCase1(Transaction t1, Transaction t2);

    /**
     * 传播 REQUIRE_NEW 内部事务异常 外层不try catch
     * @param t1
     * @param t2
     */
    void propogationRequireNewCase2(Transaction t1, Transaction t2);


    /**
     * 传播 REQUIRE_NEW 外部事务异常， 在内部事务之前
     * @param t1
     * @param t2
     */
    void propogationRequireNewCase3(Transaction t1, Transaction t2);


    /**
     * 传播 REQUIRE_NEW 外部事务异常，在内部事务之后
     * @param t1
     * @param t2
     */
    void propogationRequireNewCase4(Transaction t1, Transaction t2);


    /**
     * 外部事务对内部事务的影响
     * @param t1
     * @param t2
     */
    void propogationNestedCase1(Transaction t1, Transaction t2) ;


    /**
     * 内部事务对外部事务的影响
     * @param t1
     * @param t2
     */
    void propogationNestedCase2(Transaction t1, Transaction t2) ;

    /**
     *
     * @param t1
     * @param t2
     * @param t3
     */
    void propogationNestedCase3(Transaction t1, Transaction t2, Transaction t3);
}

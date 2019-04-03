package me.pengbo.service;

import me.pengbo.entity.Transaction;

/**
 * <p>
 *
 * </p >
 *
 * @author pengb
 * @since 2019-04-03 11:11
 */
public interface TransactionService {

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    Transaction getById(Integer id);

    /**
     * 插入
     * @param transaction
     * @return
     */
    public int insert(Transaction transaction);

    /**
     * xml 中配置 不使用用事务 更新，使用@transactional注解
     * @param transaction
     * @return
     */
    public int updateNo(Transaction transaction);

    /**
     * 更新
     * @param transaction
     * @return
     */
    public int update(Transaction transaction);

    /**
     * 删除所有
     */
    void deleteAll();

    /**
     * xml 中没有配置，加transactioinal注解
     * @param t
     * @return
     */
    int updateWithAnnotation(Transaction t);

    /**
     * 没有注解的方法，调用本类内一个有注解的方法
     * @param t
     * @return
     */
    int withoutAnnotationInvokeAnnotationMethod(Transaction t);


    /**
     * 抛异常 REQUIRE_NEW
     * @param t
     * @return
     */
    int updaterRqNew(Transaction t);


    /**
     * 不抛异常 REQUIRE_NEW
     * @param t
     * @return
     */
    int updaterRqNewNoException(Transaction t);

    /**
     * nested 不抛出异常
     * @param t1
     */
    void updateNestedWithoutException(Transaction t1);

    /**
     * nested 抛出异常
     * @param t2
     */
    void updateNestedWithException(Transaction t2);
}

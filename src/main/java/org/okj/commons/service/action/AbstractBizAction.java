/**
 * @(#)AbstractBizAction.java 2013-1-30
 *
 * Copyright (c) 2004-2013 Lakala, Inc.
 * zhongjiang Road, building 22, Lane 879, shanghai, china 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Lakala, Inc.  
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Lakala.
 */
package org.okj.commons.service.action;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;

/**
 * 抽象实现
 * @author Administrator
 * @version $Id: AbstractBizAction.java, v 0.1 2013-1-30 上午11:26:56 Administrator Exp $
 */
public abstract class AbstractBizAction<E> implements BizAction {
    /* logger */
    protected static final Logger LOGGER = Logger.getLogger(BizAction.class);

    /** 
     * @see org.storevm.commons.service.action.BizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        return false;
    }

    /** 
     * @see org.storevm.commons.service.action.BizAction#before(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void before(ActionContext context) throws ActionException {
    }

    /** 
     * @see org.storevm.commons.service.action.BizAction#after(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void after(ActionContext context) throws ActionException {
    }

    /** 
     * @see org.storevm.commons.service.action.BizAction#execute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void execute(ActionContext context) throws ActionException {
        //检查是否跳过执行
        if (!skip(context)) {
            StopWatch watch = new StopWatch();
            watch.start();

            //开始执行action，打印日志
            LogUtils.info(LOGGER, "action开始执行，action={0}, context.id={1}, bizCode={2}", this
                .getClass().getSimpleName(), context.getUid(), context.getBizCode());

            //1. 执行前置处理
            before(context);

            //2. 执行实际的业务逻辑实现
            Object result = doExecute(context);
            //3. 设置返回值
            context.setBizLoader(result);

            //4. 后置处理
            after(context);

            watch.stop();

            //结束当前action执行，打印日志
            LogUtils.info(LOGGER, "action执行结束[耗时：{0} ms]，，action={1}, context.id={2}, bizCode={3}",
                watch.getTime(), this.getClass().getSimpleName(), context.getUid(),
                context.getBizCode());
        } else {
            //跳过当前action的执行，需要打印日志
            LogUtils.info(LOGGER, "当前action被跳过，action={0}, context.id={1}, bizCode={2}", this
                .getClass().getSimpleName(), context.getUid(), context.getBizCode());
        }
    }

    /**
     * 从上下文中获取对象模型
     * @param <T>
     * @param context
     * @param key
     * @return
     */
    protected <T> T getContextAttribute(ActionContext context, String key) {
        return (T) context.getAttribute(key);
    }

    /**
     * 实际执行的方法，有子类实现
     * @param context
     * @return
     * @throws ServiceException
     */
    protected abstract E doExecute(ActionContext context) throws ActionException;
}

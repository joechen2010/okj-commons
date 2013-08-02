/**
 * @(#)HeartbeatTask.java 2013-1-21
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
package org.okj.commons.task;



/**
 * 心跳包任务
 * @author Administrator
 * @version $Id: HeartbeatTask.java, v 0.1 2013-1-21 下午12:40:36 Administrator Exp $
 * 
 *  
 */
public class Task implements Runnable {
  
    /* 任务执行的CRON表达式 */
    private String              cronExpression = "0/5 * * * * ?";

    /** 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
    }

    
    /**
     * Getter method for property <tt>cronExpression</tt>.
     * 
     * @return property value of cronExpression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * Setter method for property <tt>cronExpression</tt>.
     * 
     * @param cronExpression value to be assigned to property cronExpression
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

}

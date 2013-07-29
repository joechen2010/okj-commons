/**
 * @(#)Pagination.java 2013-1-30
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
package org.okj.commons.model;

import java.io.Serializable;

/**
 * ��ҳ����
 * @author Administrator
 * @version $Id: Pagination.java, v 0.1 2013-1-30 ����10:50:31 Administrator Exp $
 */
public class Pagination implements Serializable {
    /** UID */
    private static final long serialVersionUID       = 3364928961447824005L;

    /* �ܼ�¼ */
    private int               recordCount            = 0;

    /* ��ҳ�� */
    private int               pageCount              = 0;

    /* ÿҳ��¼�� */
    private int               recordPerPage          = 10;

    /* ��ǰҳ */
    private int               currentPage            = 1;

    /* ��ǰҳ��¼�� */
    private int               recordCountCurrentPage = 10;

    /* ��ʼ�� */
    private int               startRow;

    /* ������ */
    private int               endRow;

    /**
     * ���캯��
     */
    public Pagination() {
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Pagination [recordCount=" + recordCount + ", pageCount=" + pageCount
               + ", currentPage=" + currentPage + "]";
    }

    /**
     * 
     * @param recordPerPage
     * @param currentPage
     */
    public Pagination(int recordPerPage, int currentPage) {
        if (recordPerPage <= 0) {
            throw new IllegalArgumentException("ÿҳ��¼���С��0��");
        }
        if (currentPage <= 0) {
            throw new IllegalArgumentException("��ǰҳ����С��0��");
        }
        this.recordPerPage = recordPerPage;
        this.currentPage = currentPage;
    }

    /**
     * 
     * @param currentPage
     */
    public Pagination(int currentPage) {
        if (currentPage < 0) {
            throw new IllegalArgumentException("��ǰҳ����С��0��");
        }
        this.currentPage = currentPage;
    }

    /**
     * 
     * 
     * @return
     */
    public int getRecordCountCurrentPage() {
        return this.recordCountCurrentPage;
    }

    /**
     * 
     * 
     * @param recordCountCurrentPage
     */
    public void setRecordCountCurrentPage(int recordCountCurrentPage) {
        if (recordCountCurrentPage < 0) {
            throw new IllegalArgumentException("��ǰҳ��¼���С��0��");
        }
        this.recordCountCurrentPage = recordCountCurrentPage;
    }

    /**
     * 
     * 
     * @return
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * 
     * @param recordCount
     */
    public void setRecordCount(int recordCount) {
        if (recordCount < 0) {
            throw new IllegalArgumentException("�ܼ�¼���С��0��");
        }
        this.recordCount = recordCount;
    }

    /**
     * 
     * 
     * @return
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * 
     * 
     * @param pageCount
     */
    public void setPageCount(int pageCount) {
        if (pageCount < 0) {
            throw new IllegalArgumentException("��ҳ���С��0��");
        }
        this.pageCount = pageCount;
    }

    /**
     * 
     * 
     * @return
     */
    public int getRecordPerPage() {
        return recordPerPage;
    }

    /**
     * 
     * 
     * @param recordPerPage
     */
    public void setRecordPerPage(int recordPerPage) {
        if (recordPerPage < 0) {
            throw new IllegalArgumentException("ÿҳ�ļ�¼���С��0��");
        }
        this.recordPerPage = recordPerPage;
    }

    /**
     * 
     * 
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 
     * 
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage < 0) {
            throw new IllegalArgumentException("��ǰҳ����С��0��");
        }
        this.currentPage = currentPage;
    }

    /**
     * 
     * 
     * @return
     */
    public boolean getHasPre() {
        if (this.currentPage > 1)
            return true;
        return false;
    }

    /**
     * 
     * 
     * @return
     */
    public boolean getHasNext() {
        if (this.currentPage < this.pageCount)
            return true;
        return false;
    }

    /**
     * 
     * 
     * @return
     */
    public int getPrePage() {
        if (getHasPre())
            return this.currentPage - 1;
        return 1;
    }

    /**
     * 
     * 
     * @return
     */
    public int getNextPage() {
        if (getHasNext())
            return this.currentPage + 1;
        return this.pageCount;
    }

    /**
     * @return Returns the startRow.
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * @param startRow The startRow to set.
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * Getter method for property <tt>endRow</tt>.
     * 
     * @return property value of endRow
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * Setter method for property <tt>endRow</tt>.
     * 
     * @param endRow value to be assigned to property endRow
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }
}

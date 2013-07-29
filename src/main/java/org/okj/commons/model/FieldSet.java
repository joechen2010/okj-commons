/**
 * @(#)FieldSet.java 2013-1-30
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
 * �ֶμ���
 * @author Administrator
 * @version $Id: FieldSet.java, v 0.1 2013-1-30 ����11:36:24 Administrator Exp $
 */
public class FieldSet implements Serializable {
    /** UID */
    private static final long serialVersionUID = -5891181953339323981L;

    /** ��� */
    private String            name;

    /** �ֶμ��� */
    private Field[]           fields;

    /**
     * ���췽��
     * @param fieldNames
     */
    public FieldSet(String name, String[] fieldNames) {
        this.name = name;
        if (fieldNames != null && fieldNames.length > 0) {
            fields = new Field[fieldNames.length];
            for (int i = 0, n = fieldNames.length; i < n; i++) {
                fields[i] = new Field(fieldNames[i]);
            }
        }
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>fields</tt>.
     * 
     * @return property value of fields
     */
    public Field[] getFields() {
        return fields;
    }

    public int length() {
        return fields.length;
    }

    /**
     * �ֶ�
     * @author ̸����
     * @version $Id: FieldSet.java, v 0.1 2011-10-7 ����09:52:58 ̸���� Exp $
     */
    public class Field implements Serializable {
        /** UID */
        private static final long serialVersionUID = 7990985567950170738L;

        /** �ֶ���� */
        private String            name;

        /**
         * @param name
         */
        public Field(String name) {
            super();
            this.name = name;
        }

        /** 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Field [name=" + name + "]";
        }

        /** 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        /** 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Field other = (Field) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

        /**
         * Getter method for property <tt>name</tt>.
         * 
         * @return property value of name
         */
        public String getName() {
            return name;
        }

        /**
         * Setter method for property <tt>name</tt>.
         * 
         * @param name value to be assigned to property name
         */
        public void setName(String name) {
            this.name = name;
        }

        private FieldSet getOuterType() {
            return FieldSet.this;
        }
    }
}

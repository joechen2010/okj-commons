package org.okj.commons.dao.hibernate.serviceLocator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.okj.commons.validation.ReturnStatus;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.google.common.collect.Lists;


/**
* The ServiceLocator is a singleton class that provides Spring-enabled services such
* as Spring-configured bean instances, messaging services, and database access services.
* 
* Creation date: 12/12/2006 9 AM
* Copyright (c) 2006-2007 MEDecision, Inc.  All rights reserved.
*/
public class ServiceLocatorDefaultImpl extends ServiceLocator
{
    static Log log = LogFactory.getLog(ServiceLocatorDefaultImpl.class);

    private static ClassPathXmlApplicationContext applicationContext = null;
    private DataSource dataSource = null;
    private JdbcTemplate jdbcTemplate = null;
    private SessionFactory hibernateSessionFactory = null;
    private HibernateTemplate hibernateTemplate = null;
    private List<String> contextFiles = Lists.newArrayList();

    /**
     * Initialize the service locator using all *applicationContext.xml files on the current classpath. 
     */
    protected ServiceLocatorDefaultImpl ()
    {
    	/*applicationContext = new ClassPathXmlApplicationContext(getApplicationContextList());
        dataSource = (DataSource)applicationContext.getBean("dataSource");
        hibernateSessionFactory = (SessionFactory)applicationContext.getBean("hibernateSessionFactory");
        hibernateTemplate = (HibernateTemplate)applicationContext.getBean("hibernateTemplate");*/
    }
    
    /** 
     * Returns the list of Application context xml files that should be loaded. 
     * @return The list of application context xml files that should be loaded into Spring. 
     */
    @SuppressWarnings({ "unused" })
    private String [] getApplicationContextList(){
    	String[] contextList = null;
    	
    	if (contextList != null){
    		return contextList;
    	}
    	return contextFiles.toArray(new String[contextFiles.size()]);
    }
    
    
    /**
     * Method initialize. Initializes the service locator from specific context file paths on the classpath.
     * By default, the application context is configured in the service locator constructor using all 
     * applicationContext files on the classpath.  
     * @param contextFiles 
     * @return void
     */
    public void initialize()
    {   
        applicationContext = new ClassPathXmlApplicationContext(getApplicationContextList());
        dataSource = (DataSource)applicationContext.getBean("dataSource");
        hibernateSessionFactory = (SessionFactory)applicationContext.getBean("hibernateSessionFactory");
        hibernateTemplate = (HibernateTemplate)applicationContext.getBean("hibernateTemplate");
    }
    
    /**
     * Method getBeanFactory. Returns a reference to the application context bean factory. 
     * @return BeanFactory
     */
    public BeanFactory getBeanFactory()
    {
        return applicationContext;
    }

    /**
     * Method getApplicationContext. 
     * @return ApplicationContext
     */
    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    /**
     * Method getBean. 
     * @param beanName
     * @return Object
     */
    public Object getBean(String beanName)
    {
        return applicationContext.getBean(beanName);
    }
    
    /**
     * Method getJdbcTemplate. 
     * @return JdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate()
    {
        if (jdbcTemplate == null) 
            jdbcTemplate = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
        return jdbcTemplate;
    }

    /**
     * Method getConnection. 
     * @throws ServiceLocatorException 
     * @return Connection
     */
    public Connection getConnection () throws ServiceLocatorException
    {
        Connection conn = null;
        try
        {
            conn = dataSource.getConnection();
        }
        catch (SQLException e)
        {
            log.error(e, e);
            throw new ServiceLocatorException("Unable to obtain connection from the DataSource.",
                    e);
        }

        return conn;
    }

    /**
     * Method getHibernateSession. 
     * @return
     * @throws ServiceLocatorException 
     * @return Session
     */
    public Session getHibernateSession () throws ServiceLocatorException
    {
        Session hibernateSession = null;
        try
        {
            hibernateSession = hibernateSessionFactory.openSession();
        }
        catch (HibernateException e)
        {
            log.error(e, e);
            throw new ServiceLocatorException("Unable to obtain Hibernate Session.", e);
        }

        return hibernateSession;
    }

    /**
     * Method getHibernateTemplate. 
     * @return HibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate()
    {
        return hibernateTemplate;
    }

    /**
     * Method getMessage. 
     * @param messageName
     * @throws ServiceLocatorException 
     * @return String
     */
    public String getMessage(String messageName) throws ServiceLocatorException
    {
        return getMessage(messageName, new Object[0], Locale.US);
    }

    /**
     * Method getMessage. 
     * @param messageName
     * @param params
     * @throws ServiceLocatorException 
     * @return String
     */
    public String getMessage(String messageName, Object[] params) throws ServiceLocatorException
    {
        return getMessage(messageName, params, Locale.US);
    }

    /**
     * Method getMessage. 
     * @param messageName
     * @param params
     * @param locale
     * @throws ServiceLocatorException 
     * @return String
     */
    public String getMessage(String messageName, Object[] params, Locale locale) throws ServiceLocatorException
    {
        return applicationContext.getMessage(messageName, params, locale);
    }

    /* (non-Javadoc)
     * @see com.med.utilities.core.serviceLocator.ServiceLocator#validate()
     */
    @Override
    public ReturnStatus validate() {
        ReturnStatus status = new ReturnStatus();
        if (this.dataSource == null) {
            status.addError("SVCL-001", "Unable to create default database");
        }
        if (this.hibernateSessionFactory == null) {
            status.addError("SVCL-002", "Unable to create default database factory");
        }
        if (this.hibernateTemplate == null) {
            status.addError("SVCL-003", "unable to create default database template");
       }
        return status;
    }
    
    public void closeContext() {
		applicationContext.close();
		applicationContext = null;
	}
    
    public void addContextFile(String contextFile){
    	contextFiles.add(contextFile);
    }

	@Override
	public void initialize(String[] contextFiles) {
		// TODO Auto-generated method stub
		
	}
}

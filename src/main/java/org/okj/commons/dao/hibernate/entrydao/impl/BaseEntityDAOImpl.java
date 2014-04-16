package org.okj.commons.dao.hibernate.entrydao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.okj.commons.dao.hibernate.entrydao.BaseEntityDAO;
import org.okj.commons.domain.BaseEntity;
import org.okj.commons.model.PageRequest;
import org.okj.commons.model.PageResponse;
import org.okj.commons.web.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

/**
 * 
 * @author xchezhe
 * @version $Revision: $
 */
public abstract class BaseEntityDAOImpl<T extends BaseEntity> extends HibernateDaoSupport implements BaseEntityDAO<T> {

    protected final Log log = LogFactory.getLog(getClass());

    protected Class<T> entityClass;

    public BaseEntityDAOImpl() {
        super();
        this.entityClass = getClassGenricType(getClass());
    }

    @Autowired
    public void setSuperHibernateTemplate(HibernateTemplate hibernateTemplate) {
        super.setHibernateTemplate(hibernateTemplate);
    }

    protected String getDefalutOrderFileds() {
        return null;
    }

    public void saveEntity(T o) {
        getHibernateTemplate().save(o);
    }
    
    public void updateEntity(T o) {
        getHibernateTemplate().update(o);
    }

    public void deleteEntity(T o) {
        getHibernateTemplate().delete(o);
    }

    public T getByID(String id) {
        Object o = this.getHibernateTemplate().get(entityClass, id);
        if (o == null) {
            return null;
        }
        return (T) o;
    }

    public T getByID(Long id) {
        Object o = this.getHibernateTemplate().get(entityClass, id);
        if (o == null) {
            return null;
        }
        return (T) o;
    }
    
    public T getByID(Integer id) {
        Object o = this.getHibernateTemplate().get(entityClass, id);
        if (o == null) {
            return null;
        }
        return (T) o;
    }

    public List findByDetachedCriteria(final DetachedCriteria dc) {
        return getHibernateTemplate().findByCriteria(dc);
    }

    public List<T> findByProperty(String propertyName, Object value) {
        if (value != null) {
            String queryString = "from " + entityClass.getName() + " as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } else {
            String queryString = "from " + entityClass.getName() + " as model where model." + propertyName + " is null";
            return getHibernateTemplate().find(queryString);
        }
    }

    public PageResponse<T> findByPropertyByPage(String propertyName, Object value, PageRequest pageRequest) {
        PageResponse<T> pageResponse = new PageResponse<T>();
        if (value != null) {
            String queryString = "from " + entityClass.getName() + " as model where model." + propertyName + "= :value";
            Query query = getSession().createQuery(queryString);
            query.setEntity("value", value);

            Long totalCount = getHQLReulstCount(queryString);
            pageResponse.setTotalCount(totalCount);

            query.setFirstResult(pageRequest.getStart());
            query.setMaxResults(pageRequest.getLimit());
            pageResponse.setList(query.list());
            pageResponse.setPageNumber(pageRequest.getPageNumber());
            pageResponse.setStart(pageRequest.getStart() + 1);
            pageResponse.setEnd(pageResponse.getStart() + pageResponse.getList().size() - 1);

            return pageResponse;
        } else {
            String queryString = "from " + entityClass.getName() + " as model where model." + propertyName + " is null";

            Query query = getSession().createQuery(queryString);

            Long count = getHQLReulstCount(queryString.toString());
            pageResponse.setTotalCount(count);

            query.setFirstResult(pageRequest.getStart());
            query.setMaxResults(pageRequest.getLimit());

            pageResponse.setPageNumber(pageRequest.getPageNumber());
            pageResponse.setList(query.list());
            pageResponse.setStart(pageRequest.getStart() + 1);
            pageResponse.setEnd(pageResponse.getStart() + pageResponse.getList().size() - 1);
            return pageResponse;
        }
    }

    public PageResponse<T> findAllByPage(PageRequest pageRequest) {
        PageResponse<T> pageResponse = new PageResponse<T>();
        StringBuffer hql = new StringBuffer();
        hql.append(" FROM ").append(entityClass.getSimpleName());
        String defalutOrderFileds = getDefalutOrderFileds();
        if (StringUtils.isNotEmpty(defalutOrderFileds)) {
            hql.append(" order by " + defalutOrderFileds);
        }

        String queryString = hql.toString();
        Query query = getSession().createQuery(queryString);

        Long count = getHQLReulstCount(hql.toString());
        pageResponse.setTotalCount(count);

        query.setFirstResult(pageRequest.getStart());
        query.setMaxResults(pageRequest.getLimit());
        pageResponse.setList(query.list());

        pageResponse.setPageNumber(pageRequest.getPageNumber());
        pageResponse.setStart(pageRequest.getStart() + 1);
        pageResponse.setEnd(pageResponse.getStart() + pageResponse.getList().size() - 1);
        return pageResponse;
    }

    public List<T> findAll() {
        StringBuffer hql = new StringBuffer();
        hql.append(" FROM ").append(entityClass.getSimpleName());
        String defalutOrderFileds = getDefalutOrderFileds();
        if (StringUtils.isNotEmpty(defalutOrderFileds)) {
            hql.append(" order by " + defalutOrderFileds);
        }

        String queryString = hql.toString();
        return getHibernateTemplate().find(queryString);
    }

    public Long getHQLReulstCount(String hql) {
        String countSql = removeSelect(hql);
        countSql = removeOrders(countSql);
        countSql = removeFetchKeyword(countSql);
        countSql = " select count(*) " + countSql;
        Query query = getSession().createQuery(countSql);

        Long count = (Long) query.uniqueResult();
        return count;
    }

    protected Long getSQLReulstCount(String hql) {
        String countSql = removeSelect(hql);
        countSql = removeOrders(countSql);
        countSql = removeFetchKeyword(countSql);
        countSql = " select count(*) " + countSql;
        Query query = getSession().createSQLQuery(countSql);

        Long count = (Long) query.uniqueResult();
        return count;
    }

    private static String removeSelect(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }

    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String removeFetchKeyword(String hql) {
        return hql.replaceAll("(?i)fetch", "");
    }

    protected DetachedCriteria getEntityDetachedCriteria() {
        return DetachedCriteria.forClass(entityClass);
    }

    @SuppressWarnings("unchecked")
    public List<T> findBy(String name, Object value) {
        Assert.hasText(name);
        DetachedCriteria dc = getEntityDetachedCriteria();
        if (value == null) {
            dc.add((Restrictions.isNull(name)));
        } else {
            dc.add(Restrictions.eq(name, value));
        }
        return (List<T>) getHibernateTemplate().findByCriteria(dc);
    }

    @SuppressWarnings("unchecked")
    public Page pagedQuery(final DetachedCriteria dc, int pageNo, int pageSize) {

        Assert.notNull(dc, "DetachedCriteria must not be null");
        CriteriaImpl c = (CriteriaImpl) dc.getExecutableCriteria(getSession());
        Projection projection = c.getProjection();
        List<OrderEntry> orderEntries;
        Field field = null;
        boolean accessible = false;
        try {
            field = c.getClass().getDeclaredField("orderEntries");
            accessible = field.isAccessible();
            field.setAccessible(true);
            orderEntries = (List<OrderEntry>) field.get(c);
            field.set(c, new ArrayList());
            field.setAccessible(accessible);
        } catch (Exception e) {
            throw new RuntimeException("Internal error");
        }
        int totalRow = ((Integer) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        if (totalRow < 1) {
            return new Page();
        }

        c.setProjection(projection);
        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        try {
            field.setAccessible(true);
            field.set(c, orderEntries);
            field.setAccessible(accessible);
        } catch (Exception e) {
            logger.error("BaseGenericDAO.findByCriteria CriteriaImpl error");
            throw new RuntimeException("Internal error");
        }

        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List list = c.setFirstResult(startIndex).setMaxResults(pageSize).list();
        return new Page(startIndex, totalRow, pageSize, list);
    }

    public Object batchExecute(final String hql, final Object... values) {
        return getHibernateTemplate().execute(new HibernateCallback() {
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                return createQuery(session, hql, values).executeUpdate();
            }
        });
    }

    private Query createQuery(Session session, final String hql, final Object... values) {
        Query query = session.createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    public List<Object[]> getListBySql(Integer onePageRows, Integer currentPage, String sql, Object values[]) {
        List list = null;
        Session session = getSession();
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = session.connection();
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            rs = getData(sql, onePageRows * (currentPage - 1) + 1, onePageRows, connection);
            list = getListByResultSet(rs);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                connection.close();
                rs = null;
                connection = null;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return list;
    }

    private List<Object[]> getListByResultSet(ResultSet rs) {
        ResultSetMetaData rsmd = null;
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Object objList[] = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    objList[i - 1] = rs.getObject(i);
                }
                list.add(objList);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    private ResultSet getData(String sql, int startNo, int maxCount, Connection conn) {
        ResultSet rs = null;
        try {
            PreparedStatement pstat = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            pstat.setMaxRows(startNo + maxCount - 1);
            rs = pstat.executeQuery();
            rs.first();
            rs.relative(startNo - 2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public void flush(){
        getSession().flush();
    }
    
}

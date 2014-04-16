package org.okj.commons.dao.hibernate.entrydao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.okj.commons.domain.BaseEntity;
import org.okj.commons.model.PageRequest;
import org.okj.commons.model.PageResponse;
import org.okj.commons.web.pagination.Page;

/**
 * 
 * @author xchezhe
 * @version $Revision: $
 */
public interface BaseEntityDAO<T extends BaseEntity> {

    public void saveEntity(T o);

    public void deleteEntity(T o);

    public T getByID(String id);

    public T getByID(Long id);

    public List findByDetachedCriteria(final DetachedCriteria dc);

    public List<T> findBy(String name, Object value);

    public Page pagedQuery(final DetachedCriteria dc, int pageNo, int pageSize);

    public List<T> findByProperty(String propertyName, Object value);

    public PageResponse<T> findByPropertyByPage(String propertyName, Object value, PageRequest pageRequest);

    public PageResponse<T> findAllByPage(PageRequest pageRequest);

    public List<T> findAll();
    
    public Long getHQLReulstCount(String hql);

    public Object batchExecute(final String hql, final Object... values);
    
    public List<Object[]> getListBySql(Integer onePageRows, Integer currentPage, String sql, Object values[]);
}

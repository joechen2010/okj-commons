package org.okj.commons.db.mongo;

import java.util.List;

import org.okj.commons.web.pagination.Pagination;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract class MongodbBaseDao<T> {

	/**
	 *  
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 */
	public Pagination<T> getPage(int pageNo, int pageSize, Query query) {
		long totalCount = this.mongoTemplate.count(query, this.getEntityClass());
		Pagination<T> page = new Pagination<T>(pageNo, pageSize, totalCount);
		query.skip(page.getFirstResult()); 
		query.limit(pageSize); 
		List<T> datas = this.find(query);
		page.setDatas(datas);
		return page;
	}

	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	/**
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	/**
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public T findAndModify(Query query, Update update) {

		return this.mongoTemplate.findAndModify(query, update, this.getEntityClass());
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public T findAndRemove(Query query) {
		return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
	}

	/**
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFirst(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, this.getEntityClass());
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	public T save(T bean) {
		mongoTemplate.save(bean);
		return bean;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public T findById(String id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	/**
	 * 
	 * @param id
	 * @param collectionName
	 * @return
	 */
	public T findById(String id, String collectionName) {
		return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
	}

	/**
	 * 
	 * @return
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * 
	 * @param mongoTemplate
	 */
	protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);

	protected MongoTemplate mongoTemplate;
}

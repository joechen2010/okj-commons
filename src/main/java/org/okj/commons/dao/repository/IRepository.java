package org.okj.commons.dao.repository;

import java.util.List;

public interface IRepository{

	public int getDataCount();
	
	public List<?> findPage(String sortColumnName, boolean sortAscending, int startRow, int maxResults);
	
}

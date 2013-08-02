package org.okj.commons.spring.ws;


import java.util.Set;

public interface PersonService {

	public Set<Person> findPersons(String name);
}
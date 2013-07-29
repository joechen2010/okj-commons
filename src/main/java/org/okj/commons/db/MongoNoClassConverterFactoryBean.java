/**
 * 
 */
package org.okj.commons.db;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;

/**
 * Use this one to remove _class in mongodb document which is injected by spring data.
 * 
 * @author toy
 * 
 */
public class MongoNoClassConverterFactoryBean implements FactoryBean<MappingMongoConverter> {

    private MappingMongoConverter converter;

    public void setConverter(MappingMongoConverter converter) {
        this.converter = converter;
    }

    /**
     * Create special convert don't convert the java class to _class
     */
    public MappingMongoConverter getObject() throws Exception {
        MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
        converter.setTypeMapper(typeMapper);
        return converter;
    }

    public Class<?> getObjectType() {
        return MappingMongoConverter.class;
    }

    public boolean isSingleton() {
        return true;
    }

}

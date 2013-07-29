package org.okj.commons.db;

import java.io.InputStream;

import javax.sql.DataSource;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;

public class DbUnitUtils {

    public static void loadDbUnitData(DataSource h2DataSource, String... xmlPaths) throws Exception {
        IDatabaseConnection connection = new H2Connection(h2DataSource.getConnection(), "");
        for (String xmlPath : xmlPaths) {
            InputStream input = DbUnitUtils.class.getResourceAsStream(xmlPath);
            IDataSet dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(input);
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        }
    }

    public static void removeDbUnitData(DataSource h2DataSource, String... xmlPaths) throws Exception {
        IDatabaseConnection connection = new H2Connection(h2DataSource.getConnection(), "");
        for (String xmlPath : xmlPaths) {
            InputStream input = DbUnitUtils.class.getResourceAsStream(xmlPath);
            IDataSet dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(input);
            DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
        }
    }

    public static void appendDbUnitData(DataSource h2DataSource, String... xmlPaths) throws Exception {
        IDatabaseConnection connection = new H2Connection(h2DataSource.getConnection(), "");
        for (String xmlPath : xmlPaths) {
            InputStream input = DbUnitUtils.class.getResourceAsStream(xmlPath);
            IDataSet dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(input);
            DatabaseOperation.INSERT.execute(connection, dataSet);
        }
    }
}

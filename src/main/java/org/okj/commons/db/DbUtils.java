package org.okj.commons.db;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.okj.commons.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.google.common.collect.Lists;

@SuppressWarnings("deprecation")
public class DbUtils {

	private static Logger logger = LoggerFactory.getLogger(DbUtils.class);
	public static final String SQL_SCRIPT_EXTENDTION = "sql";
	public static final String SQL_SCRIPT_RENAME_EXTENDTION = ".excuted";

	public static void dbUpgrade(String transactionManagerName,
			String jdbcTemplateName, String scriptFolder, boolean renameFile) {
		try {
			ApplicationContext context = SpringContextHolder
					.getApplicationContext();
			SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) SpringContextHolder
					.getBean(jdbcTemplateName);
			DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) SpringContextHolder
					.getBean(transactionManagerName);
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = transactionManager.getTransaction(def);
			for (File script : getScripts(scriptFolder)) {
				excuteSqlFile(context, jdbcTemplate, script.getAbsolutePath());
				if (renameFile) {
					script.renameTo(new File(scriptFolder + script.getName()
							+ "." + getExcuteDate() +SQL_SCRIPT_RENAME_EXTENDTION));
				}
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			logger.info("excuting sql files failure" + e);
		}
	}

	public static void excuteSqlFile(ApplicationContext context,
			SimpleJdbcTemplate jdbcTemplate, String scriptName) {
		try {
			final Resource script = context.getResource("file:" + scriptName);
			logger.info("Executing " + scriptName);
			SimpleJdbcTestUtils.executeSqlScript(jdbcTemplate, script, false);
		} catch (final DataAccessResourceFailureException e) {
			logger.info("Could not find " + scriptName);
		} catch (Exception e) {
			logger.info("Executing " + scriptName + " failure");
		}
	}
	
	private static List<File> getScripts(String scriptFolder) {
		@SuppressWarnings("unchecked")
		Collection<File> files = FileUtils.listFiles(new File(
				scriptFolder), new String[] { SQL_SCRIPT_EXTENDTION }, true);
		List<File> scripts = Lists.newArrayList();
		scripts.addAll(files);
		Collections.sort(scripts, new Comparator<File>() {
			  public int compare(File o1, File o2) {
			      if (o1 == null || o2 == null)
			        return 0;
			      return o1.getName().compareTo(o2.getName());
			  }
			});
		return scripts;
	}
	
	private static String getExcuteDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		return dateFormat.format(new Date());
	}

}

/**
 * @(#)FtpUtils.java 2013-2-8
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
package org.okj.commons.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;

/**
 * 
 * @author Administrator
 * @version $Id: FtpUtils.java, v 0.1 2013-2-8 ����10:17:48 Administrator Exp $
 */
public class FtpUtils {
    /* LOGGER */
    private static final Logger LOGGER                = Logger.getLogger(FtpUtils.class);

    /* �ϴ�����(����ϴ�ģʽΪftp������Ҫָ��FTP�������������Ϣ����������˿ڣ��û�������) */
    public static final String  UPLOAD_HOST           = "upload.host";

    /* �ϴ�����Ķ˿� */
    public static final String  UPLOAD_PORT           = "upload.port";

    /* �ϴ�������û��� */
    public static final String  UPLOAD_USER           = "upload.user";

    /* �ϴ���������� */
    public static final String  UPLOAD_PASSWORD       = "upload.password";

    /* �ϴ��ļ��ĸ�Ŀ¼ */
    public static final String  UPLOAD_ROOT_PATH      = "upload.root.path";

    /* �ϴ���������(����ϴ�ģʽΪftp������Ҫָ��FTP�������������Ϣ�����������û�������) */
    public static final String  UPLOAD_PROXY_HOST     = "upload.proxy.host";

    /* �ϴ�����Ķ˿� */
    public static final String  UPLOAD_PROXY_PORT     = "upload.proxy.port";

    /* �ϴ���������û��� */
    public static final String  UPLOAD_PROXY_USER     = "upload.proxy.user";

    /* �ϴ������������ */
    public static final String  UPLOAD_PROXY_PASSWORD = "upload.proxy.password";

    /* FTP�ͻ��� */
    private FTPClient           ftp;

    private static FtpUtils     instance;

    /* �� */
    private static Object       lock                  = new Object();

    /**
     * �����Ĺ��캯��
     */
    protected FtpUtils() {
        //ʵ��FTP�ͻ��˶���
        this.ftp = new FTPClient();
        this.ftp.configure(getFTPClientConfig());
    }

    /**
     * ���ص������
     * @return
     */
    public static FtpUtils getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new FtpUtils();
            }
        }
        return instance;
    }

    /**
     * ����FTP������
     */
    public boolean connect() {
        int reply;
        try {
            if (ftp != null) {
                ftp.connect(getServer(), getPort());

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("[FTPHelper] Connected to " + getServer() + " on "
                                + (getPort() > 0 ? getPort() : ftp.getDefaultPort()));
                }

                reply = ftp.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                    return false;
                }

                return true;
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "FTP����ʱ�����쳣", ex);
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                    LogUtils.error(LOGGER, "�Ͽ�FTP����ʱ�����쳣", f);
                }
            }
        }
        return false;
    }

    /**
     * ��¼FTP������
     * @return
     */
    public boolean login() {
        boolean success = false;
        try {
            success = ftp.login(getUsername(), getPassword());
            if (success) {
                //����FTP��һЩ����
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE); //����������ļ�
                ftp.enterLocalPassiveMode(); //����Ϊ����ģʽ
                ftp.setConnectTimeout(2000); //��ʱʱ��
                //ftp.setControlEncoding("GBK"); //�ַ�

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("[FTPHelper] ��¼FTP�������ɹ���success=" + success + ", username="
                                + getUsername());
                }
            } else {
                ftp.logout();
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "��¼FTP������ʱ�����쳣", ex);
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                    LogUtils.error(LOGGER, "�Ͽ�FTP����ʱ�����쳣", f);
                }
            }
        }

        return success;
    }

    /**
     * ��FTPָ��·���ϴ��ļ�
     * 
     * @param localFile
     * @param newName
     *            ���ļ���
     * @param remoteFoldPath
     * @throws Exception
     */
    public boolean uploadFile(InputStream input, String remotePath) {
        boolean success = false;
        try {
            String filename = FilenameUtils.getName(remotePath);
            String path = FilenameUtils.getPath(remotePath);
            // ����Ŀ¼
            createDirectory(path);

            success = ftp.storeFile(filename, input);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("[FTPHelper] �ϴ��ļ��ɹ���success=" + success + ", filename=" + filename
                            + ", path=" + path);
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "�ϴ��ļ�ʱ�����쳣", ex);
        } finally {
            IOUtils.closeQuietly(input);
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                    LogUtils.error(LOGGER, "�Ͽ�FTP����ʱ�����쳣", f);
                }
            }
        }
        return success;
    }

    /**
     * ɾ���ļ�
     * @param remotePath
     * @throws UploadFileException
     */
    public void deleteFile(String remotePath) {
        try {
            //��ȡ�ļ����Ŀ¼
            String filename = FilenameUtils.getName(remotePath);
            String path = FilenameUtils.getPath(remotePath);

            //�任Ŀ¼
            changeWorkingDirectory(path);

            //ɾ���ļ�
            boolean success = ftp.deleteFile(filename);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("[FTPHelper] ɾ���ϴ��ļ���ɣ�success=" + success + ", remotePath="
                            + remotePath);
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "ɾ��FTP�������ļ�ʱ�����쳣", ex);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                    LogUtils.error(LOGGER, "�Ͽ�FTP����ʱ�����쳣", f);
                }
            }
        }
    }

    /**
     * �任Զ��FTPĿ¼
     * @param path
     */
    public void changeWorkingDirectory(String path) {
        try {
            boolean flag = ftp.changeWorkingDirectory(path);
            if (flag) {
                //�����ǰĿ¼�ɹ������ʾ��ǰĿ¼����
                return;
            } else {
                //����ͽ�������Ŀ¼�任
                if (StringUtils.startsWith(path, File.separator)) {
                    path = StringUtils.substring(path, 1);
                }
                String[] dirs = StringUtils.split(path, File.separator);
                if (dirs.length <= 0) {
                    //���Ŀ¼Ϊ�գ����˳��ݹ�
                    return;
                } else {
                    boolean success = ftp.changeWorkingDirectory(dirs[0]);
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("[FTPHelper] �任�ļ�����ɣ�current.path=" + dirs[0] + ", success="
                                    + success + ", working.dir=" + ftp.printWorkingDirectory());
                    }
                    //�ݹ�
                    String next = StringUtils.substring(path, StringUtils.indexOf(path, dirs[0])
                                                              + dirs[0].length());
                    changeWorkingDirectory(next);
                }
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "�ı������Ŀ¼ʱ�����쳣", ex);
        }
    }

    /**
     * ����Զ��FTPĿ¼
     * @param path
     */
    public void createDirectory(String path) {
        try {
            boolean flag = ftp.changeWorkingDirectory(path);
            if (flag) {
                //�����ǰĿ¼�ɹ������ʾ��ǰĿ¼����
                return;
            } else {
                //����ʹ���Ŀ¼
                if (StringUtils.startsWith(path, File.separator)) {
                    path = StringUtils.substring(path, 1);
                }
                String[] dirs = StringUtils.split(path, File.separator);
                if (dirs.length <= 0) {
                    //���Ŀ¼Ϊ�գ����˳��ݹ�
                    return;
                } else {
                    if (!ftp.changeWorkingDirectory(dirs[0])) {
                        ftp.makeDirectory(dirs[0]);
                        ftp.changeWorkingDirectory(dirs[0]); //���봴����Ŀ¼
                    }

                    //�ݹ�
                    String next = StringUtils.substring(path, StringUtils.indexOf(path, dirs[0])
                                                              + dirs[0].length());
                    createDirectory(next);
                }
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "�ı������Ŀ¼ʱ�����쳣", ex);
        }
    }

    /** 
     * ����FTP���Ӳ��� 
     *  
     * @return 
     * @throws Exception 
     */
    protected FTPClientConfig getFTPClientConfig() {
        String systemKey = FTPClientConfig.SYST_UNIX;
        //String serverLanguageCode = "zh";
        FTPClientConfig conf = new FTPClientConfig(systemKey);
        //conf.setServerLanguageCode(serverLanguageCode);
        conf.setDefaultDateFormatStr("yyyy-MM-dd");
        return conf;
    }

    /**
     * ���������ַ
     * @return
     */
    public String getServer() {
        return System.getProperty(UPLOAD_HOST);
    }

    public String getProxyServer() {
        return System.getProperty(UPLOAD_PROXY_HOST);
    }

    public int getPort() {
        String config = System.getProperty(UPLOAD_PORT);
        return NumberUtils.toInt(config, 21);
    }

    public String getRootPath() {
        return System.getProperty(UPLOAD_ROOT_PATH);
    }

    public int getProxyPort() {
        String config = System.getProperty(UPLOAD_PROXY_PORT);
        return NumberUtils.toInt(config, 21);
    }

    public String getUsername() {
        return System.getProperty(UPLOAD_USER);
    }

    public String getProxyUsername() {
        return System.getProperty(UPLOAD_PROXY_USER);
    }

    public String getPassword() {
        return System.getProperty(UPLOAD_PASSWORD);
    }

    public String getProxyPassword() {
        return System.getProperty(UPLOAD_PROXY_PASSWORD);
    }
}

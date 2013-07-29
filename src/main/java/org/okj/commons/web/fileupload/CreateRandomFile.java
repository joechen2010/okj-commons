/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package org.okj.commons.web.fileupload;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * ��������ļ���ƵĹ�����
 * @author xiangqing.tan
 * @version $Id: CreateRandomFile.java, v 0.1 2012-9-25 ����04:04:28 xiangqing.tan Exp $
 */
public class CreateRandomFile {
    private final static String RANDOM_ALGORITHM     = "SHA1PRNG";
    private final static String RANDOM_ALGORITHM_ALT = "IBMSecureRandom";
    private Random              random;
    private boolean             weakRandom;
    private HttpServletRequest  request;

    /**
     * ���췽��
     */
    public CreateRandomFile(HttpServletRequest request) {
        if (random == null) {
            try {
                random = SecureRandom.getInstance(RANDOM_ALGORITHM);
            } catch (NoSuchAlgorithmException e) {
                try {
                    random = SecureRandom.getInstance(RANDOM_ALGORITHM_ALT);
                    weakRandom = false;
                } catch (NoSuchAlgorithmException e_alt) {
                    random = new Random();
                    weakRandom = true;
                }
            }
        }
        random.setSeed(random.nextLong() ^ System.currentTimeMillis() ^ hashCode()
                       ^ Runtime.getRuntime().freeMemory());
        this.request = request;
    }

    /** 
     * @see com.lakala.bmcp.share.io.CreateRandomFile#create(java.lang.String)
     */
    public File create(String parent, String suffix) {
        String name = getName(); //��ȡһ�������ļ���
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(suffix)) {
            name = name + "." + suffix;
        }

        return new File(parent, name);
    }

    protected String getName() {
        String name = null;
        while (name == null || name.length() == 0) {
            long r = weakRandom ? (hashCode() ^ Runtime.getRuntime().freeMemory()
                                   ^ random.nextInt() ^ (((long) request.hashCode()) << 32))
                : random.nextLong();
            r ^= System.currentTimeMillis();
            if (request != null && request.getRemoteAddr() != null)
                r ^= request.getRemoteAddr().hashCode();
            if (r < 0)
                r = -r;
            name = Long.toString(r, 36);
        }
        return name;
    }
}

/**
 * 
 */
package org.okj.commons.file;

import org.apache.commons.lang.StringUtils;

/**
 * @author toy
 * 
 */
public class FileUtils {
    public static String getFileNameFromPath(String filePath) {
        String fileName = filePath;
        // it's windows file path.
        if (filePath.contains(":")) {
            String[] seps = StringUtils.split(filePath, "\\");
            fileName = seps[seps.length - 1];
        }

        if (fileName.contains("/")) {
            String[] seps = StringUtils.split(filePath, "/");
            fileName = seps[seps.length - 1];
        }
        return fileName;
    }

    public static String createPath(String position, String fileName) {
        return position.endsWith("/") ? position + fileName : position + "/" + fileName;
    }
}

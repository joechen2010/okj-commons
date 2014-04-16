/**
 * 
 * 
 */
package org.okj.commons.spring.mvc.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

public class MonitoredCommonsMultipartResolver extends CommonsMultipartResolver {
    /**
     * Add ProgressListener resolveMultipart
     * 
     * @see org.springframework.web.multipart.commons.CommonsMultipartResolver#resolveMultipart(javax.servlet.http.HttpServletRequest)
     */
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        ProgressListener pListener = new DefaultProgressListener(request);
        fileUpload.setProgressListener(pListener);
        try {
            @SuppressWarnings("rawtypes")
            List fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            @SuppressWarnings("unchecked")
            MultipartParsingResult parsingResult = parseFileItems(fileItems, encoding);
            return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(),
                    parsingResult.getMultipartParameters(), parsingResult.getMultipartParameterContentTypes());
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch (FileUploadException ex) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }
    }

}

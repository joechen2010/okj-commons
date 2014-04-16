/**
 * 
 * 
 */
package org.okj.commons.spring.mvc.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.okj.commons.cache.SpringCacheAdapter;
import org.okj.commons.cache.SpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultProgressListener implements ProgressListener {

    private static final Log log = LogFactory.getLog(DefaultProgressListener.class);

    @Autowired
    private SpringCacheManager cacheManager;
    
    @Autowired
    private SpringCacheAdapter uploadStatusAdapter;
    
    private String clientId;

    public DefaultProgressListener() {
    }

    public DefaultProgressListener(HttpServletRequest request) {
    	setClientId(request.getSession(true).getId());
        MultipartFileStatus multipartFileStatus = new MultipartFileStatus();
        getCacheManager().put(uploadStatusAdapter.getName(), getClientId(), multipartFileStatus);
    }

    /**
     * update
     * 
     * @see org.apache.commons.fileupload.ProgressListener#update(long, long, int)
     */
    public void update(long bytesRead, long contentLength, int items) {
        if (log.isDebugEnabled())
            log.debug("bytesRead=" + bytesRead + ",contentLength=" + contentLength + ",items=" + items);
        ;
        MultipartFileStatus multipartFileStatus = (MultipartFileStatus) getCacheManager().getCache(uploadStatusAdapter.getName()).get(getClientId());
        multipartFileStatus.setStatus(bytesRead, contentLength);
        getCacheManager().put(uploadStatusAdapter.getName(), getClientId(), multipartFileStatus);
    }

    public SpringCacheAdapter getUploadStatusAdapter() {
		return uploadStatusAdapter;
	}

	public void setUploadStatusAdapter(SpringCacheAdapter uploadStatusAdapter) {
		this.uploadStatusAdapter = uploadStatusAdapter;
	}

	public SpringCacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(SpringCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

    public String getClientId(){
    	return clientId;
    }

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

    
}

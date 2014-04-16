/**
 * 
 * 
 */
package org.okj.commons.spring.mvc.upload;

public class MultipartFileStatus {

    private double contentLength = 0.0;

    private double bytesRead = 0.0;

    private double percent = 0.0;

    public MultipartFileStatus() {
    }

    public double getContentLength() {
        return contentLength;
    }

    public void setContentLength(double contentLength) {
        this.contentLength = contentLength;
    }

    public double getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(double bytesRead) {
        this.bytesRead = bytesRead;
    }

    public double getPercent() {
        this.percent = bytesRead / contentLength * 100;
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * 
     * @param pBytesRead
     * @param pContentLength
     */
    public void setStatus(double pBytesRead, double pContentLength) {
        this.bytesRead = pBytesRead;
        this.contentLength = pContentLength;
    }

    /**
     * Return Json style string
     * 
     * @return
     */
    public String getJsonStyleStatus() {
        StringBuffer buf = new StringBuffer();
        buf.append("{bytesRead:").append(this.bytesRead).append(",contentLength:").append(this.contentLength)
                .append("}");
        return buf.toString();
    }
}
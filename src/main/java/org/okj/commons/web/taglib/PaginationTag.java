package org.okj.commons.web.taglib;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.constants.CommonsKeys;
import org.okj.commons.model.Pagination;

/**
 * ��ݿ��ҳ��ǩ<br>
 * 
 * @author Administrator
 * 
 */
public class PaginationTag extends TagSupport {
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -2847978397079808458L;

    /** ��־ */
    private Logger            log              = Logger.getLogger(PaginationTag.class);

    /** ��ҳ���� */
    private Pagination        page;

    /** �ص����� */
    private String            callbackFunction;

    private ResourceBundle    res;

    public String getCallbackFunction() {
        return callbackFunction;
    }

    public void setCallbackFunction(String callbackFunction) {
        this.callbackFunction = callbackFunction;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            // ��ȡֵ��ջ�е�PageVariable����
            this.page = (Pagination) pageContext.getRequest().getAttribute(CommonsKeys.PAGER_KEY);
            // log.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$ :: " + page);
            if (this.page != null) {
                try {
                    writer.write(getHtml());
                } catch (Exception de) {
                    throw new JspException(de);
                }
            } else {
                throw new JspException("�޷���ȡֵ��ջ�е�Pagination����");
            }
        } catch (JspException e) {
            log.error(e);
        }

        return SKIP_BODY;
    }

    protected String getHtml() throws Exception {
        res = ResourceBundle.getBundle("resources", Locale.CHINA);
        StringBuffer sb = new StringBuffer();
        sb.append(createFirstPageHtml());
        sb.append(createPreviousPageHtml());
        sb.append(createPageHtml());
        sb.append(createNextPageHtml());
        sb.append(createLastPageHtml());
        sb.append(createRecordCountPageHtml());
        sb.append(createRecordPerPageHtml());
        return sb.toString();
    }

    protected String createPageHtml() throws Exception {
        StringBuffer sb = new StringBuffer();
        // sb.append("<div id=\"current-page\">Page");
        sb.append("<div id=\"current-page\">" + res.getString("page") + ":&nbsp;");
        sb.append("<input id=\"current-page-text\" name=\"currentPage\" "
                  + "type=\"text\" value=\"" + page.getCurrentPage()
                  + "\" onkeydown=\"keyDownSubmit(event)\"");
        if (page.getRecordCount() <= 0) {
            sb.append("disabled /> ");
        } else {
            sb.append(" /> ");
        }
        // sb.append("&nbsp;Of&nbsp;" + page.getPageCount());
        sb.append("&nbsp;" + res.getString("total.page") + ":&nbsp;<b>" + page.getPageCount());
        sb.append("</b></div>");
        // log.debug(sb.toString());
        return sb.toString();
    }

    protected String createFirstPageHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<div id=\"page-first\">");
        if (page.getRecordCount() <= 0 || page.getPageCount() <= 1 || page.getCurrentPage() == 1) {
            sb.append("<input id=\"page-first-disabled-btn\" type=\"button\" "
                      + "onfocus=\"this.blur()\" title=\"" + res.getString("first.page")
                      + "\" disabled ");
        } else {
            sb.append("<input id=\"page-first-btn\" type=\"button\" "
                      + "onfocus=\"this.blur()\" title=\"" + res.getString("first.page") + "\" ");
        }
        sb.append("onclick=\"turnPage(1," + callbackFunction + ")\"/>");
        sb.append("</div>");
        // log.debug(sb.toString());
        return sb.toString();
    }

    protected String createPreviousPageHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<div id=\"page-prev\">");
        if (page.getRecordCount() <= 0 || page.getPageCount() <= 1 || page.getCurrentPage() == 1) {
            sb.append("<input id=\"page-prev-disabled-btn\" type=\"button\" "
                      + "onfocus=\"this.blur()\" title=\"" + res.getString("pre.page")
                      + "\" disabled ");
        } else {
            sb.append("<input id=\"page-prev-btn\" type=\"button\" "
                      + "onfocus=\"this.blur()\" title=\"" + res.getString("pre.page") + "\" ");
        }
        sb.append("onclick=\"turnPage(" + page.getPrePage() + "," + callbackFunction + ")\"/>");
        sb.append("</div>");
        return sb.toString();
    }

    protected String createNextPageHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<div id=\"page-next\">");
        if (page.getRecordCount() <= 0 || page.getCurrentPage() == page.getPageCount()) {
            sb.append("<input id=\"page-next-disabled-btn\" type=\"button\" "
                      + "onfocus=\"this.blur()\" title=\"" + res.getString("next.page")
                      + "\" disabled ");
        } else {
            sb.append("<input id=\"page-next-btn\" type=\"button\" "
                      + "onfocus=\"this.blur()\" title=\"" + res.getString("next.page") + "\" ");
        }
        sb.append("onclick=\"turnPage(" + page.getNextPage() + "," + callbackFunction + ")\"/>");
        sb.append("</div>");
        return sb.toString();
    }

    protected String createLastPageHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<div id=\"page-last\">");
        if (page.getRecordCount() <= 0 || page.getCurrentPage() == page.getPageCount()) {
            sb.append("<input id=\"page-last-disabled-btn\" type=\"button\" "
                      + "onfocus=\"this.blur()\" title=\"" + res.getString("last.page")
                      + "\" disabled ");
        } else {
            sb.append("<input id=\"page-last-btn\" type=\"button\" "
                      + "onfocus=\"this.blur()\" title=\"" + res.getString("last.page") + "\" ");
        }
        sb.append("onclick=\"turnPage(" + page.getPageCount() + "," + callbackFunction + ")\"/>");
        sb.append("</div>");
        return sb.toString();
    }

    /**
     * ��ʾ����������¼�õ�html�ַ�
     * 
     * @return
     */
    protected String createRecordCountPageHtml() {
        StringBuffer sb = new StringBuffer();
        // sb.append("<div id=\"page-total\">Total: " + page.getRecordCount()
        // + " record(s)</div>");
        sb.append("<div id=\"page-total\">" + res.getString("total.recordcount") + ": <b>"
                  + page.getRecordCount() + "</b> " + res.getString("tail") + "</div>");
        return sb.toString();
    }

    /**
     * ��ʾÿҳ��¼���õ�html�ַ�
     * 
     * @return
     */
    protected String createRecordPerPageHtml() {
        StringBuffer sb = new StringBuffer();
        // ���㵱ǰѡ��ļ�¼��
        int selectedCount = 0;
        String selected = (String) pageContext.getRequest().getAttribute("alreadySelected");
        if (StringUtils.isNotBlank(selected)) {
            selectedCount = StringUtils.split(selected, ",").length;
        }
        sb.append("<div id=\"page-total\">(" + res.getString("per.page") + ": <b>"
                  + page.getRecordPerPage() + "</b> " + res.getString("tail") + ","
                  + res.getString("seleced.count") + ": <b>" + selectedCount + "</b>"
                  + res.getString("tail") + ")</div>");
        return sb.toString();
    }
}

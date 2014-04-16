package org.okj.commons.web.html;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTemplate {
	/**
	 * 根據元素id獲取HTML元素
	 * 
	 * @param document
	 * @param id
	 * @return
	 */
	public  Element getElementById(Document document, String id) {
		Element element = null;
		if (document != null && id != null && !"".equals(id.trim())) {
			element = document.getElementById(id);
		}
		return element;
	}

	/**
	 * 根據id獲取HTML元素
	 * 
	 * @param element
	 * @param id
	 * @return
	 */
	public  Element getElementById(Element element, String id) {
		Element resultElement = null;
		if (element != null) {
			resultElement = element.getElementById(id);
		}
		return resultElement;
	}

	/**
	 * 根據元素標簽(tagName)獲取HTMl元素
	 * 
	 * @param document
	 * @param tagName
	 * @return
	 */
	public  Elements getElementsByTagName(Document document,
			String tagName) {
		Elements elements = null;
		if (document != null && tagName != null && !"".equals(tagName)) {
			elements = document.getElementsByTag(tagName);
		}
		return elements;
	}

	/**
	 * 根據元素標簽(tagName)獲取HTMl元素
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	public  Elements getElementsByTagName(Element element, String tagName) {
		Elements resultElements = null;
		if (element != null && tagName != null && !"".equals(tagName)) {
			resultElements = element.getElementsByTag(tagName);
		}
		return resultElements;
	}

	/**
	 * 根據className(样式名稱)獲取HTML元素集合
	 * 
	 * @param document
	 * @param className
	 * @return
	 */
	public  Elements getElementsByClassName(Document document,
			String className) {
		Elements elements = null;
		if (document != null && className != null
				&& !"".equals(className.trim())) {
			elements = document.getElementsByClass(className);
		}
		return elements;
	}

	/**
	 * 根據className(样式名稱)獲取HTML元素集合
	 * 
	 * @param element
	 * @param className
	 * @return
	 */
	public  Elements getElementsByClassName(Element element,
			String className) {
		Elements resultElements = null;
		if (element != null && className != null && !"".equals(className)) {
			resultElements = element.getElementsByClass(className);
		}
		return resultElements;
	}

	/**
	 * 根據元素是否具有屬性元素key返回元素集合
	 * 
	 * @param document
	 * @param attributeNameKey
	 *            元素屬性key值
	 * @return
	 */
	public  Elements getElementsByAttributeNameKey(Document document,
			String attributeNameKey) {
		Elements elements = null;
		if (document != null && attributeNameKey != null
				&& !"".equals(attributeNameKey)) {
			elements = document.getElementsByAttribute(attributeNameKey);
		}
		return elements;
	}

	/**
	 * 根據元素是否具有屬性元素key返回元素集合
	 * 
	 * @param element
	 * @param attributeNameKey
	 *            元素屬性key值
	 * @return
	 */
	public  Elements getElementsByAttributeNameKey(Element element,
			String attributeNameKey) {
		Elements resultElements = null;
		if (element != null && attributeNameKey != null
				&& !"".equals(attributeNameKey)) {
			resultElements = element.getElementsByAttribute(attributeNameKey);
		}
		return resultElements;
	}

	/**
	 * 根據元素是否具有屬性元素key並且key對應的值为value獲取元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param attributeValue
	 * @return
	 */
	public  Elements getElementsByAttributeNameValue(Document document,
			String attributeKey, String attributeValue) {
		Elements elements = null;
		if (document != null && attributeKey != null
				&& !"".equals(attributeKey.trim()) && attributeValue != null
				&& !"".equals(attributeValue.trim())) {
			elements = document.getElementsByAttributeValue(attributeKey,
					attributeValue);
		}
		return elements;
	}

	/**
	 * 根據元素是否具有屬性元素key並且key對應的值为value獲取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param attributeValue
	 * @return
	 */
	public  Elements getElementsByAttributeNameValue(Element element,
			String attributeKey, String attributeValue) {
		Elements resultElements = null;
		if (element != null && attributeKey != null
				&& !"".equals(attributeKey.trim()) && attributeValue != null
				&& !"".equals(attributeValue.trim())) {
			resultElements = element.getElementsByAttributeValue(attributeKey,
					attributeValue);
		}
		return resultElements;
	}

	/**
	 * 根據屬性key值是否以特定字符串開頭獲取元素集合
	 * 
	 * @param document
	 * @param attributeValue
	 * @return
	 */
	public  Elements getElementsByAttributeNameStartWithValue(
			Document document, String keyValue) {
		Elements elements = null;
		if (document != null && keyValue != null && !"".equals(keyValue.trim())) {
			elements = document.getElementsByAttributeStarting(keyValue);
		}
		return elements;
	}

	/**
	 * 根據屬性key值是否以特定字符串開頭獲取元素集合
	 * 
	 * @param element
	 * @param attributeValue
	 * @return
	 */
	public  Elements getElementsByAttributeNameStartWithValue(
			Element element, String keyValue) {
		Elements elements = null;
		if (element != null && keyValue != null && !"".equals(keyValue.trim())) {
			elements = element.getElementsByAttributeStarting(keyValue);
		}
		return elements;
	}

	/**
	 * 根據屬性value值是否被包含在某個元素的某個屬性中獲取元素集合
	 * 
	 * @param document
	 * @param containValue
	 * @return
	 */
	public  Elements getElementsByAttributeValueContaining(
			Document document, String attributeKey, String containValue) {
		Elements elements = null;
		if (document != null && containValue != null
				&& !"".equals(containValue)) {
			elements = document.getElementsByAttributeValueContaining(
					attributeKey, containValue);
		}
		return elements;
	}

	/**
	 * 根據屬性value值是否被包含在某個元素的某個屬性中獲取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param containValue
	 * @return
	 */
	public  Elements getElementsByAttributeValueContaining(
			Element element, String attributeKey, String containValue) {
		Elements elements = null;
		if (element != null && containValue != null && !"".equals(containValue)) {
			elements = element.getElementsByAttributeValueContaining(
					attributeKey, containValue);
		}
		return elements;
	}

	/**
	 * 根據屬性的value值是否以某個字符串結尾獲取元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param valueSuffix
	 * @return
	 */
	public  Elements getElementsByAttributeValueEnding(Document document,
			String attributeKey, String valueSuffix) {
		Elements elements = null;
		if (document != null && attributeKey != null
				&& !"".equals(attributeKey) && valueSuffix != null
				&& !"".equals(valueSuffix)) {
			elements = document.getElementsByAttributeValueEnding(attributeKey,
					valueSuffix);
		}
		return elements;
	}

	/**
	 * 根據屬性的value值是否以某個字符串結尾獲取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param valueSuffix
	 * @return
	 */
	public  Elements getElementsByAttributeValueEnding(Element element,
			String attributeKey, String valueSuffix) {
		Elements elements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey)
				&& valueSuffix != null && !"".equals(valueSuffix)) {
			elements = element.getElementsByAttributeValueEnding(attributeKey,
					valueSuffix);
		}
		return elements;
	}

	/**
	 * 根據屬性值value的正則表達式獲取元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param pattern
	 * @return
	 */
	public  Elements getElementsByAttributeValueMatching(
			Document document, String attributeKey, Pattern pattern) {
		Elements elements = null;
		if (document != null && attributeKey != null
				&& !"".equals(attributeKey) && pattern != null) {
			elements = document.getElementsByAttributeValueMatching(
					attributeKey, pattern);
		}
		return elements;
	}

	/**
	 * 根據屬性值value的正則表達式獲取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param pattern
	 * @return
	 */
	public  Elements getElementsByAttributeValueMatching(Element element,
			String attributeKey, Pattern pattern) {
		Elements elements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey)
				&& pattern != null) {
			elements = element.getElementsByAttributeValueMatching(
					attributeKey, pattern);
		}
		return elements;
	}

	/**
	 * 根據屬性值的value的正則表達式獲取元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param regualRegx
	 * @return
	 */
	public  Elements getElementsByAttributeValueMatching(
			Document document, String attributeKey, String regualRegx) {
		Elements elements = null;
		if (document != null && attributeKey != null
				&& !"".equals(attributeKey) && regualRegx != null
				&& !"".equals(regualRegx)) {
			elements = document.getElementsByAttributeValueMatching(
					attributeKey, regualRegx);
		}
		return elements;
	}

	/**
	 * 根據屬性值的value的正則表達式獲取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param regualRegx
	 * @return
	 */
	public  Elements getElementsByAttributeValueMatching(Element element,
			String attributeKey, String regualRegx) {
		Elements elements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey)
				&& regualRegx != null && !"".equals(regualRegx)) {
			elements = element.getElementsByAttributeValueMatching(
					attributeKey, regualRegx);
		}
		return elements;
	}

	/**
	 * 返回屬性鍵attributeKey不等於值attributeValue的元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param attributeValue
	 * @return
	 */
	public  Elements getElementsByAttributeValueNot(Document document,
			String attributeKey, String attributeValue) {
		Elements elements = null;
		if (document != null && attributeKey != null
				&& !"".equals(attributeKey) && attributeValue != null
				&& !"".equals(attributeValue)) {
			elements = document.getElementsByAttributeValueNot(attributeKey,
					attributeValue);
		}
		return elements;
	}

	/**
	 * 返回屬性鍵attributeKey不等於值attributeValue的元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param attributeValue
	 * @return
	 */
	public  Elements getElementsByAttributeValueNot(Element element,
			String attributeKey, String attributeValue) {
		Elements elements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey)
				&& attributeValue != null && !"".equals(attributeValue)) {
			elements = element.getElementsByAttributeValueNot(attributeKey,
					attributeValue);
		}
		return elements;
	}

	/**
	 * 根據選擇器匹配的字符串返回 Elements(元素集合)
	 * 
	 * @param document
	 * @param selectStr
	 *            選擇器(類似於JQuery)
	 * @return
	 */
	public  Elements getMoreElementsBySelectStr(Document document,
			String selectStr) {
		if (document == null || selectStr == null
				|| "".equals(selectStr.trim())) {
			return null;
		} else {
			Elements elements = document.select(selectStr);
			if (elements != null && elements.size() > 0) {
				return elements;
			} else {
				return null;
			}
		}
	}

	/**
	 * 根據選擇器匹配的字符串返回 Elements(元素集合)
	 * 
	 * @param element
	 * @param selectStr
	 * @return
	 */
	public  Elements getMoreElementsBySelectStr(Element element,
			String selectStr) {
		if (element == null || selectStr == null || "".equals(selectStr.trim())) {
			return null;
		} else {
			Elements elements = element.select(selectStr);
			if (elements != null && elements.size() > 0) {
				return elements;
			} else {
				return null;
			}
		}
	}

	/**
	 * 根據選擇器匹配的字符串返回 Element(單個元素)
	 * 
	 * @param document
	 * @param selectStr
	 *            選擇器(類似於JQuery)
	 * @return
	 */
	public  Element getSingleElementBySelectStr(Document document,
			String selectStr) {
		Elements elements = getMoreElementsBySelectStr(document, selectStr);
		if (elements != null && elements.size() > 0) {
			return elements.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根據選擇器匹配的字符串返回 Element(單個元素)
	 * 
	 * @param element
	 * @param selectStr
	 * @return
	 */
	public  Element getSingleElementBySelectStr(Element element,
			String selectStr) {
		Elements elements = getMoreElementsBySelectStr(element, selectStr);
		if (elements != null && elements.size() > 0) {
			return elements.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根據選擇器匹配的字符串返回單個元素的Html字符串
	 * 
	 * @param document
	 * @param selectStr
	 *            選擇器(類似於JQuery)
	 * @return
	 */
	public  String getSingleElementHtmlBySelectStr(Document document,
			String selectStr) {
		Element element = getSingleElementBySelectStr(document, selectStr);
		if (element != null) {
			return element.html();
		} else {
			return null;
		}
	}

	/**
	 * 根據選擇器匹配的字符串返回單個元素的Html字符串
	 * 
	 * @param element
	 * @param selectStr
	 * @return
	 */
	public  String getSingleElementHtmlBySelectStr(Element element,
			String selectStr) {
		Element ele = getSingleElementBySelectStr(element, selectStr);
		if (ele != null) {
			return ele.html();
		} else {
			return null;
		}
	}

	/**
	 * 根據元素屬性名key獲取元素屬性名value
	 * 
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public  String getAttributeValue(Element element, String attributeName) {
		String attributeValue = null;
		if (element != null && attributeName != null
				&& !"".equals(attributeName)) {
			attributeValue = element.attr(attributeName);
		}
		return attributeValue;
	}

	/**
	 * 從elements集合中獲取element並解析成HTML字符串
	 * 
	 * @param elements
	 * @return
	 */
	public  String getSingElementHtml(Elements elements) {
		Element ele = null;
		String htmlStr = null;
		if (elements != null && elements.size() > 0) {
			ele = elements.get(0);
			htmlStr = ele.html();
		}
		return htmlStr;
	}

	/**
	 * 從elements集合中獲取element並解析成Text字符串
	 * 
	 * @param elements
	 * @return
	 */
	public  String getSingElementText(Elements elements) {
		Element ele = null;
		String htmlStr = null;
		if (elements != null && elements.size() > 0) {
			ele = elements.get(0);
			htmlStr = ele.text();
		}
		return htmlStr;
	}

public static void readUrl( String strURL ) {
    int chunksize = 4096;
  byte[] chunk = new byte[chunksize];
  int count;
  try  {    
      URL pageUrl = new URL(strURL );
 
       
      BufferedInputStream bis = new BufferedInputStream(pageUrl.openStream());
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("URL1.txt", false));
      System.out.println("read1() running " );
      while ((count = bis.read(chunk, 0, chunksize)) != -1) {
          bos.write(chunk, 0, count);  
      }
      bos.close();
      bis.close();     
    
    System.out.println("Done");   
   }catch (IOException e) {
       e.printStackTrace();
   }
}


	public static void main(String[] args) {
		/*JsoupTemplate jt = new JsoupTemplate();
		File file = new File("F:/example.htm");
		try {
			Document document = Jsoup.parse(file, "GB2312");
			Pattern pattern = Pattern.compile("");
			// document.getElementsByAttributeValueMatching("", pattern);
			Element element = jt.getElementById(document, "personal-uplayer");

		} catch (IOException e) {
			e.printStackTrace();
		}*/
    
    readUrl("http://app.zhcw.com/wwwroot/zhcw/jsp/MediaArena2/leitai.jsp?issueId=new&utilType=1");
	}

}

package org.okj.commons.jna;

import java.util.concurrent.TimeUnit;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
  
public class AlipayPasswordInputTest {  
  
       
    public  static void testAlipayPasswordInput() {  
        String password = "your password";  
        //HWND alipayEdit = findHandle("Chrome_RenderWidgetHostHWND", "Edit"); //Chrome浏览器，使用Spy++可以抓取句柄的参�? 
        HWND alipayEdit = findHandle("TXGuiFoundation", "Edit");
        if(alipayEdit == null){
        	System.out.println("获取支付宝密码控件失");
        }
        boolean isSuccess = Win32Util.simulateCharInput(alipayEdit, password);  
        if(!isSuccess){
        	System.out.println("输入支付宝密码["+ password +"]失败");
        }
    }  
      
    private static WinDef.HWND findHandle(String browserClassName, String alieditClassName) {  
        WinDef.HWND browser = Win32Util.findHandleByClassName(browserClassName, 10, TimeUnit.SECONDS);  
        return Win32Util.findHandleByClassName(browser, alieditClassName, 10, TimeUnit.SECONDS);  
    }  
    
    public static void main(String[] args){
    	AlipayPasswordInputTest.testAlipayPasswordInput();
    }
}  

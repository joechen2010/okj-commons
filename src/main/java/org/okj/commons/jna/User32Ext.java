package org.okj.commons.jna;

import com.sun.jna.Native;  
import com.sun.jna.platform.win32.User32;  
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.W32APIOptions;  
  
public interface User32Ext extends User32 {  
  
    User32Ext USER32EXT = (User32Ext) Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);  
      
    /** 
     * 查找窗口 
     * @param lpParent �?��查找窗口的父窗口 
     * @param lpChild �?��查找窗口的子窗口 
     * @param lpClassName 类名 
     * @param lpWindowName 窗口�?
     * @return 找到的窗口的句柄 
     */  
    HWND FindWindowEx(HWND lpParent, HWND lpChild, String lpClassName, String lpWindowName);  
  
    /** 
     * 获取桌面窗口，可以理解为�?��窗口的root 
     * @return 获取的窗口的句柄 
     */  
    HWND GetDesktopWindow();  
      
    /** 
     * 发�?事件消息 
     * @param hWnd 控件的句�?
     * @param dwFlags 事件类型 
     * @param bVk 虚拟按键�?
     * @param dwExtraInfo 扩展信息，传0即可 
     * @return 
     */  
    int SendMessage(HWND hWnd, int dwFlags, byte bVk, int dwExtraInfo);  
  
    /** 
     * 发�?事件消息 
     * @param hWnd 控件的句�? 
     * @param Msg 事件类型 
     * @param wParam �?即可 
     * @param lParam �?��发�?的消息，如果是点击操作传null 
     * @return 
     */  
    int SendMessage(HWND hWnd, int Msg, int wParam, String lParam);  
      
    /** 
     * 发�?键盘事件 
     * @param bVk 虚拟按键�?
     * @param bScan �?((byte)0) 即可 
     * @param dwFlags 键盘事件类型 
     * @param dwExtraInfo �?即可 
     */  
    void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);  
      
    /** 
     * �?��指定窗口（将鼠标焦点定位于指定窗口） 
     * @param hWnd �?��活的窗口的句�?
     * @param fAltTab 是否将最小化窗口还原 
     */  
    void SwitchToThisWindow(HWND hWnd, boolean fAltTab); 
    
    HWND FindWindow(String lpClassName, String lpWindowName);
      
}  

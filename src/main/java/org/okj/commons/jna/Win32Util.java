package org.okj.commons.jna;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
  
/** 
 * Window组件操作工具�?
 *  
 * @author sunju 
 *  
 */  
public class Win32Util {  
  
    private static final int N_MAX_COUNT = 512;  
  
    private Win32Util() {  
    }  
  
    /** 
     * 从桌面开始查找指定类名的组件，在超时的时间范围内，如果未找到任何匹配的组件则反复查找 
     * @param className 组件的类�?
     * @param timeout 超时时间 
     * @param unit 超时时间的单�?
     * @return 返回匹配的组件的句柄，如果匹配的组件大于�?��，返回第�?��查找的到的；如果未找到或超时则返�?code>null</code> 
     */  
    public static HWND findHandleByClassName(String className, long timeout, TimeUnit unit) {  
        return findHandleByClassName(User32Ext.USER32EXT.USER32EXT.GetDesktopWindow(), className, timeout, unit);  
    }  
  
    /** 
     * 从桌面开始查找指定类名的组件 
     * @param className 组件的类�?
     * @return 返回匹配的组件的句柄，如果匹配的组件大于�?��，返回第�?��查找的到的；如果未找到任何匹配则返回<code>null</code> 
     */  
    public static HWND findHandleByClassName(String className) {  
        return findHandleByClassName(User32Ext.USER32EXT.GetDesktopWindow(), className);  
    }  
  
    /** 
     * 从指定位置开始查找指定类名的组件 
     * @param root 查找组件的起始位置的组件的句柄，如果�?code>null</code>则从桌面�?��查找 
     * @param className 组件的类�?
     * @param timeout 超时时间 
     * @param unit 超时时间的单�?
     * @return 返回匹配的组件的句柄，如果匹配的组件大于�?��，返回第�?��查找的到的；如果未找到或超时则返�?code>null</code> 
     */  
    public static HWND findHandleByClassName(HWND root, String className, long timeout, TimeUnit unit) {  
        if(null == className || className.length() <= 0) {  
            return null;  
        }  
        long start = System.currentTimeMillis();  
        HWND hwnd = findHandleByClassName(root, className);  
        while(null == hwnd && (System.currentTimeMillis() - start < unit.toMillis(timeout))) {  
            hwnd = findHandleByClassName(root, className);  
        }  
        return hwnd;  
    }  
  
    /** 
     * 从指定位置开始查找指定类名的组件 
     * @param root 查找组件的起始位置的组件的句柄，如果�?code>null</code>则从桌面�?��查找 
     * @param className 组件的类�?
     * @return 返回匹配的组件的句柄，如果匹配的组件大于�?��，返回第�?��查找的到的；如果未找到任何匹配则返回<code>null</code> 
     */  
    public static HWND findHandleByClassName(HWND root, String className) {  
        if(null == className || className.length() <= 0) {  
            return null;  
        }  
        HWND[] result = new HWND[1];  
        findHandle(result, root, className);  
        return result[0];  
    }  
    
    public static HWND findHandleByClassNameBaseRoot(HWND root, String className) {  
        if(null == className || className.length() <= 0) {  
            return null;  
        }  
        HWND[] result = new HWND[1];
        HWND found = null;
        while(null == found) {  
        	findHandle(result, root, className); 
            found = result[0]; 
        } 
        
        return found;  
    }  
  
    private static boolean findHandle(final HWND[] target, HWND root, final String className) {  
        if(null == root) {  
            root = User32Ext.USER32EXT.GetDesktopWindow();  
        }  
        return User32Ext.USER32EXT.EnumChildWindows(root, new WNDENUMPROC() {  
  
            @Override  
            public boolean callback(HWND hwnd, Pointer pointer) {  
                char[] winClass = new char[N_MAX_COUNT];  
                User32Ext.USER32EXT.GetClassName(hwnd, winClass, N_MAX_COUNT);  
                System.out.println(winClass);
                if(User32Ext.USER32EXT.IsWindowVisible(hwnd) && className.equals(Native.toString(winClass))) {  
                    target[0] = hwnd;  
                    return false;  
                } else {  
                    return target[0] == null || findHandle(target, hwnd, className);  
                }  
            }  
  
        }, Pointer.NULL);  
    }  
  
    /** 
     * 模拟键盘按键事件，异步事件�?使用win32 keybd_event，每次发送KEYEVENTF_KEYDOWN、KEYEVENTF_KEYUP两个事件。默�?0秒超�?
     * @param hwnd 被键盘操作的组件句柄 
     * @param keyCombination 键盘的虚拟按键码�?a href="http://msdn.microsoft.com/ZH-CN/library/windows/desktop/dd375731.aspx">Virtual-Key Code</a>），或�?使用{@link java.awt.event.KeyEvent}</br> 
     *                      二维数组第一维中的一个元素为�?��按键操作，包含组合操作，第二维中的一个元素为�?��按键事件，即�?��虚拟按键�?
     * @return 键盘按键事件放入windows消息队列成功返回<code>true</code>，键盘按键事件放入windows消息队列失败或超时返�?code>false</code> 
     */  
    public static boolean simulateKeyboardEvent(HWND hwnd, int[][] keyCombination) {  
        if(null == hwnd) {  
            return false;  
        }  
        User32Ext.USER32EXT.SwitchToThisWindow(hwnd, true);  
        User32Ext.USER32EXT.SetFocus(hwnd);  
        for(int[] keys : keyCombination) {  
            for(int i = 0; i < keys.length; i++) {  
                User32Ext.USER32EXT.keybd_event((byte) keys[i], (byte) 0, Win32MessageConstants.KEYEVENTF_KEYDOWN, 0); // key down  
            }  
            for(int i = keys.length - 1; i >= 0; i--) {  
                User32Ext.USER32EXT.keybd_event((byte) keys[i], (byte) 0, Win32MessageConstants.KEYEVENTF_KEYUP, 0); // key up  
            }  
        }  
        return true;  
    }  
  
    /** 
     * 模拟字符输入，同步事件�?使用win32 SendMessage API发�?WM_CHAR事件。默�?0秒超�?
     * @param hwnd 被输入字符的组件的句�?
     * @param content 输入的内容�?字符串会被转换成<code>char[]</code>后�?个字符输�?
     * @return 字符输入事件发�?成功返回<code>true</code>，字符输入事件发送失败或超时返回<code>false</code> 
     */  
    public static boolean simulateCharInput(final HWND hwnd, final String content) {  
        if(null == hwnd) {  
            return false;  
        }  
        try {  
            return execute(new Callable<Boolean>() {  
  
                @Override  
                public Boolean call() throws Exception {  
                    User32Ext.USER32EXT.SwitchToThisWindow(hwnd, true);  
                    User32Ext.USER32EXT.SetFocus(hwnd);  
                    for(char c : content.toCharArray()) {  
                        Thread.sleep(5);  
                        User32Ext.USER32EXT.SendMessage(hwnd, Win32MessageConstants.WM_CHAR, (byte) c, 0);  
                    }  
                    return true;  
                }  
  
            });  
        } catch(Exception e) {  
            return false;  
        }  
    }  
      
    public static boolean simulateCharInput(final HWND hwnd, final String content, final long sleepMillisPreCharInput) {  
        if(null == hwnd) {  
            return false;  
        }  
        try {  
            return execute(new Callable<Boolean>() {  
  
                @Override  
                public Boolean call() throws Exception {  
                    User32Ext.USER32EXT.SwitchToThisWindow(hwnd, true);  
                    User32Ext.USER32EXT.SetFocus(hwnd);  
                    for(char c : content.toCharArray()) {  
                        Thread.sleep(sleepMillisPreCharInput);  
                        User32Ext.USER32EXT.SendMessage(hwnd, Win32MessageConstants.WM_CHAR, (byte) c, 0);  
                    }  
                    return true;  
                }  
  
            });  
        } catch(Exception e) {  
            return false;  
        }  
    }  
  
    /** 
     * 模拟文本输入，同步事件�?使用win32 SendMessage API发�?WM_SETTEXT事件。默�?0秒超�?
     * @param hwnd 被输入文本的组件的句�?
     * @param content 输入的文本内�?
     * @return 文本输入事件发�?成功返回<code>true</code>，文本输入事件发送失败或超时返回<code>false</code> 
     */  
    public static boolean simulateTextInput(final HWND hwnd, final String content) {  
        if(null == hwnd) {  
            return false;  
        }  
        try {  
            return execute(new Callable<Boolean>() {  
  
                @Override  
                public Boolean call() throws Exception {  
                    User32Ext.USER32EXT.SwitchToThisWindow(hwnd, true);  
                    User32Ext.USER32EXT.SetFocus(hwnd);  
                    User32Ext.USER32EXT.SendMessage(hwnd, Win32MessageConstants.WM_SETTEXT, 0, content);  
                    return true;  
                }  
  
            });  
        } catch(Exception e) {  
            return false;  
        }  
    }  
  
    /** 
     * 模拟鼠标点击，同步事件�?使用win32 SendMessage API发�?BM_CLICK事件。默�?0秒超�?
     * @param hwnd 被点击的组件的句�?
     * @return 点击事件发�?成功返回<code>true</code>，点击事件发送失败或超时返回<code>false</code> 
     */  
    public static boolean simulateClick(final HWND hwnd) {  
        if(null == hwnd) {  
            return false;  
        }  
        try {  
            return execute(new Callable<Boolean>() {  
  
                @Override  
                public Boolean call() throws Exception {  
                    User32Ext.USER32EXT.SwitchToThisWindow(hwnd, true);  
                    User32Ext.USER32EXT.SendMessage(hwnd, Win32MessageConstants.BM_CLICK, 0, null);  
                    return true;  
                }  
  
            });  
        } catch(Exception e) {  
            return false;  
        }  
    }  
  
    private static <T> T execute(Callable<T> callable) throws Exception {  
        ExecutorService executor = Executors.newSingleThreadExecutor();  
        try {  
            Future<T> task = executor.submit(callable);  
            return task.get(10, TimeUnit.SECONDS);  
        } finally {  
            executor.shutdown();  
        }  
    }  
    
    public static void moveClickMouse(WinDef.HWND hWnd, int msg, int x, int y) {
        WinDef.WPARAM wParam = new WinDef.WPARAM(0);
        int temp = y << 16 | (x << 16 >> 16);
        WinDef.LPARAM lParam = new WinDef.LPARAM(temp);
        WinDef.UINT_PTR uint = new WinDef.UINT_PTR(msg);
        User32.MSG msg1 = new User32.MSG();
        User32.INSTANCE.PostMessage(hWnd, msg, wParam, lParam);
    }
    
    public static void getWindowContent(WinDef.HWND hwnd, int margin){
		   try {
	            Thread.sleep(10000);
	            User32.INSTANCE.ShowWindow(hwnd, 1);
	            Rect rect = new Rect(); //窗口位置
	              User32.INSTANCE.GetWindowRect(hwnd, rect); //获得窗口位置
		       /* moveClickMouse(hwnd, Win32MessageConstants.WM_RBUTTONDOWN, margin, +0xb4);  //点击 右键
		        moveClickMouse(hwnd, Win32MessageConstants.WM_RBUTTONUP, margin, +0xb4);
		            Thread.sleep(1000);
		         com.sun.jna.platform.win32.WinDef.HWND childHwnd =
		        		User32.INSTANCE.WindowFromPoint(rect.getLeft() +margin + 0x22, rect.getTop() + 0xb4 + 0x24);
		        moveClickMouse(childHwnd, Win32MessageConstants.WM_LBUTTONDOWN, +0x22, 0x24);   //
		        moveClickMouse(childHwnd, Win32MessageConstants.WM_LBUTTONUP, 0x22, 0x24);   //

		        Thread.sleep(1000);
		        moveClickMouse(hwnd, Win32MessageConstants.WM_RBUTTONDOWN, margin, +0xb4);  //点击 右键
		        moveClickMouse(hwnd, Win32MessageConstants.WM_RBUTTONUP, margin, +0xb4);
		        Thread.sleep(1000);
		        childHwnd =
		        		User32.INSTANCE.WindowFromPoint(rect.getLeft() + margin + 0x22, rect.getTop() + 0xb4 + 0x12);
		        moveClickMouse(childHwnd, Win32MessageConstants.WM_LBUTTONDOWN, +0x22, +0x12);
		        moveClickMouse(childHwnd, Win32MessageConstants.WM_LBUTTONUP, +0x22, +0x12);
		        Thread.sleep(1000); */
		   }catch(Exception e){
			   
		   }
    }
}  

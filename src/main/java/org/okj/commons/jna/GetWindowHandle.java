package org.okj.commons.jna;


import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class GetWindowHandle {

	
	public interface User32 extends StdCallLibrary {
	      User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
	 
	      HWND FindWindow(String lpClassName, String lpWindowName);
	}
	 
	   public static HWND findWindow(String className, String windowName) throws WindowNotFoundException {
	      HWND hwnd = User32.INSTANCE.FindWindow(className, windowName);
	      if (hwnd == null) {
	         throw new WindowNotFoundException(className, windowName);
	      }
	      //return hwnd.hashCode();
	      return hwnd;
	   }
	    
	   @SuppressWarnings("serial")
	   public static class WindowNotFoundException extends Exception {
	      public WindowNotFoundException(String className, String windowName) {
	         super(String.format("Window null for className: %s; windowName: %s", className, windowName));
	      }
	   }
	 
	   public static void main(String[] args) {
	      try {
	          
	    	   HWND win = findWindow("TXGuiFoundation", "查找联系");
	    	   User32Ext.INSTANCE.ShowWindow(win, 1);
	           Rect rect = new Rect();  
	           User32Ext.INSTANCE.GetWindowRect(win, rect); 
	           System.out.println("�?= "+ rect.getLeft()+" . �?= "+rect.getRight());	
	           Win32Util.moveClickMouse(win, Win32MessageConstants.WM_RBUTTONDOWN, 0x24, +0xb4);
	      } catch (WindowNotFoundException e) {
	         e.printStackTrace();
	      }
	   }
}

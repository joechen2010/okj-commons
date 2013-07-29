package org.okj.commons.jna;

public class Win32MessageConstants {  
	  
    public static final int WM_SETTEXT = 0x000C; //输入文本  
      
    public static final int WM_CHAR = 0x0102; //输入字符  
  
    public static final int BM_CLICK = 0xF5; //点击事件，即按下和抬起两个动�? 
  
    public static final int KEYEVENTF_KEYUP = 0x0002; //键盘按键抬起  
      
    public static final int KEYEVENTF_KEYDOWN = 0x0; //键盘按键按下  
    
    static final byte enter = 13 & 0xff;  
  
    
 // 鼠标钩子函数里判断按键类型的常数
 	public static final int WM_LBUTTONUP = 514;
 	public static final int WM_LBUTTONDOWN = 513;
 	public static final int WM_RBUTTONUP = 517;
 	public static final int WM_RBUTTONDOWN = 516;
 	public static final int WM_MOUSEHWHEEL = 526;
 	public static final int WM_MOUSEWHEEL = 522;
 	public static final int WM_MOUSEMOVE = 512;
 	
    public static final int WM_MBUTTONDOWN = 519;
    public static final int WM_MBUTTONUP = 520;
}  

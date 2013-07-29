package org.okj.commons.jna;

import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;
  
/**  
 * 低级键盘鼠标事件监听 
 * 此程序使用JNA实现类似键盘记录器程序或网页游戏傻瓜点击录制  
 */  
public class TrackUserInput {  
    private static volatile boolean quit;  
    private static HHOOK hhkKeyBoard;  
    private static HHOOK hhkMouse;  
    private static LowLevelKeyboardProc keyboardHook;  
    private static HOOKPROC mouseHook;  
  
    public static void main(String[] args) {  
        final User32 lib = User32.INSTANCE;  
        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);  
          
        keyboardHook = new LowLevelKeyboardProc() {  
            public LRESULT callback(int nCode, WPARAM wParam,  
                    KBDLLHOOKSTRUCT info) {  
                if (nCode >= 0) {  
                    switch (wParam.intValue()) {  
                        case WinUser.WM_KEYUP:  
                        case WinUser.WM_KEYDOWN:  
                        case WinUser.WM_SYSKEYUP:  
                        case WinUser.WM_SYSKEYDOWN:  
                            int keyCode = info.vkCode;  
      
                            System.err.println(keyCode);  
                            if (info.vkCode == 123) {  
                                quit = true;  
                            }  
                            break;  
                    }  
                }  
                return lib.CallNextHookEx(hhkKeyBoard, nCode, wParam,  
                        info.getPointer());  
            }  
        };  
  
        mouseHook = new LowLevelMouseProc() {  
            public LRESULT callback(int nCode, WPARAM wParam,  
                    MOUSEHOOKSTRUCT info) {  
                if (nCode >= 0) {  
                    switch (wParam.intValue()) {  
                    case Win32MessageConstants.WM_LBUTTONDOWN:  
                        System.err.println("mouse left key down");  
                        break;  
                    case Win32MessageConstants.WM_LBUTTONUP:  
                        System.err.println("mouse left key up");  
                        break;  
                    case Win32MessageConstants.WM_MBUTTONDOWN:  
                        System.err.println("mouse middle key down");  
                        break;  
                    case Win32MessageConstants.WM_MBUTTONUP:  
                        System.err.println("mouse middle key up");  
                        break;  
                    case Win32MessageConstants.WM_RBUTTONDOWN:  
                        System.err.println("mouse right key down");  
                        break;  
                    case Win32MessageConstants.WM_RBUTTONUP:  
                        System.err.println("mouse right key up");  
                        break;  
                    case Win32MessageConstants.WM_MOUSEMOVE:  
                        // System.err.println("mouse mouse");  
                        break;  
                    }  
                }  
                return lib  
                        .CallNextHookEx(hhkMouse, nCode, wParam, info.getPointer());  
            }  
  
        };  
  
        hhkKeyBoard = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL,  
                keyboardHook, hMod, 0);  
        hhkMouse = lib  
                .SetWindowsHookEx(WinUser.WH_MOUSE_LL, mouseHook, hMod, 0);  
  
        System.out.println("Enter '<F12>' to quit");  
          
        new Thread() {  
            public void run() {  
                while (!quit) {  
                    try {  
                        Thread.sleep(10);  
                    } catch (Exception e) {  
                    }  
                }  
                  
                System.err.println("unhook and exit");  
                lib.UnhookWindowsHookEx(hhkKeyBoard);  
                lib.UnhookWindowsHookEx(hhkMouse);  
                System.exit(0);  
            }  
        }.start();  
  
        // 处理消息  
        int result;  
        MSG msg = new MSG();  
        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {  
            if (result == -1) {  
                System.err.println("error in get message");  
                break;  
            } else {  
                lib.TranslateMessage(msg);  
                lib.DispatchMessage(msg);  
            }  
        }  
          
        lib.UnhookWindowsHookEx(hhkKeyBoard);  
        lib.UnhookWindowsHookEx(hhkMouse);  
    }  
      
    public interface LowLevelMouseProc extends HOOKPROC {  
        LRESULT callback(int nCode, WPARAM wParam, MOUSEHOOKSTRUCT lParam);  
    }  
      
    public static class MOUSEHOOKSTRUCT extends Structure {  
        public static class ByReference extends MOUSEHOOKSTRUCT implements  
                Structure.ByReference {  
        };  
  
        public WinUser.POINT pt;  
        public WinDef hwnd;  
        public int wHitTestCode;  
        public BaseTSD.ULONG_PTR dwExtraInfo;
		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return null;
		}  
    }  
      
}  

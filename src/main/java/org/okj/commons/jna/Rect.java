package org.okj.commons.jna;

public class Rect extends com.sun.jna.platform.win32.WinDef.RECT
{

    public Rect()
    {
    }

    public int getLeft()
    {
        return left;
    }

    public void setLeft(int left)
    {
        this.left = left;
    }

    public int getRight()
    {
        return right;
    }

    public void setRight(int right)
    {
        this.right = right;
    }

    public int getTop()
    {
        return top;
    }

    public void setTop(int top)
    {
        this.top = top;
    }

    public int getButtom()
    {
        return bottom;
    }

    public void setButtom(int bottom)
    {
        this.bottom = bottom;
    }
}

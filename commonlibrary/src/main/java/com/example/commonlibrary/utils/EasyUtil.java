package com.example.commonlibrary.utils;

import android.content.Context;
import android.view.WindowManager;

public class EasyUtil {
    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
}

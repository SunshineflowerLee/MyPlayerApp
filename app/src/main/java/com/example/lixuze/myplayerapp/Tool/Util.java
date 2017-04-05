package com.example.lixuze.myplayerapp.Tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Util {
    /**
     * dpתpx
     *
     */
    public static int dip2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     *	pxתdp
     */
    public static int px2dip(Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }



    /**
     * 添加半透明矩形条
     *
     * @param activity       需要设置的 activity
     * @param color 颜色值
     */
    private static void addStatusBarView(Activity activity, int color) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        if (contentView.getChildCount() > 1) {
            contentView.getChildAt(1).setBackgroundColor(color);
        } else {
            contentView.addView(createTranslucentStatusBarView(activity, color));
        }
    }

    /**
     * 创建半透明矩形 View
     *
     * @param color 颜色值
     * @return 半透明 View
     */
    private static View createTranslucentStatusBarView(Context context, int color) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(context);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getsatsubarsize(context));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }


    public static void setsatsubar(Context context, View view){
/*        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) view.getLayoutParams();
            linearParams.height = context.getResources().getDimensionPixelSize(resourceId);
        }*/
        if (getsatsubarsize(context)>0){
            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) view.getLayoutParams();
            linearParams.height = getsatsubarsize(context);
        }
    }

    public static int getsatsubarsize(Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId>0){
            int height = context.getResources().getDimensionPixelSize(resourceId);
            return height;
        }
        return 0;
    }
}
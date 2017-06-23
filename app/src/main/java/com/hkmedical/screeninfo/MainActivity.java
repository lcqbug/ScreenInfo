package com.hkmedical.screeninfo;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    WindowManager windowManager;
    ConstraintLayout constraintLayout;
    StringBuffer stringBuffer;
    Display defaultDisplay;
    DisplayMetrics displayMetrics;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stringBuffer = new StringBuffer();

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        defaultDisplay = windowManager.getDefaultDisplay();
        displayMetrics = new DisplayMetrics();

        DisplayMetrics metrics = new DisplayMetrics();
        try {
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            String name = defaultDisplay.getName();
        }

        constraintLayout = (ConstraintLayout) findViewById(R.id.contener);




    }

    @Override
    protected void onResume() {
        super.onResume();
        getStatusBarHeigh();
        getVirtualBarHeigh();
        tvShow();

    }

    private void sbAppend(String tag,String s) {
        stringBuffer.append(tag + s);
        stringBuffer.append("\n");
    }
    private void sbAppend(String tag,int s) {
        stringBuffer.append(tag + s);
        stringBuffer.append("\n");
    }


    private void tvShow() {
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
         textView = new TextView(this);
        stringBuffer.append("width:" + width);
        stringBuffer.append("\n");
        stringBuffer.append("height:" + height);
        stringBuffer.append("\n");
//        stringBuffer.append("getVirtualBarHeigh:" + getVirtualBarHeigh());
//        stringBuffer.append("\n");
        stringBuffer.append("displayMetrics.density:" + displayMetrics.density);
        stringBuffer.append("\n");

        getStatusBarHeigh();
        getStatusBarHeight();
        textView.setText(stringBuffer);

        constraintLayout.addView(textView, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }


    private void getStatusBarHeigh() {
        float density = displayMetrics.density;
        int densityDpi = displayMetrics.densityDpi;
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

//        windowManager.getDefaultDisplay().get

        stringBuffer.append("density:" + density);
        stringBuffer.append("\n");
        stringBuffer.append("densityDpi:" + densityDpi);
        stringBuffer.append("\n");
        stringBuffer.append("widthPixels:" + widthPixels);
        stringBuffer.append("\n");
        stringBuffer.append("heightPixels:" + heightPixels);
        stringBuffer.append("\n");
    }

    private int getStatusBarHeight() {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = this.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }
        stringBuffer.append("sbar:" + sbar);
        stringBuffer.append("\n");

        return sbar;
    }
    /**
     * 获取虚拟功能键高度
     */
    public int getVirtualBarHeigh() {
        int vh = 0;
//        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
            Log.e("lc","dm.heightPixels"+dm.heightPixels);
            stringBuffer.append("getVirtualBarHeigh:"+vh);
            stringBuffer.append("\n");
            stringBuffer.append("dm.heightPixels:"+dm.heightPixels);
            stringBuffer.append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stringBuffer.setLength(0);
    }
}

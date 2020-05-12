package com.donfyy.helpcenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donfyy.helpcenter.databinding.ActivityMainBinding;

import java.lang.reflect.Proxy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mViewDataBinding;
//    private List<TextView> mTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewDataBinding.getRoot().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
       /* int layerType = ((ViewGroup) mViewDataBinding.getRoot()).getChildAt(0).getLayerType();
        Log.e(MainActivity.class.getSimpleName(), String.valueOf(layerType != View.LAYER_TYPE_SOFTWARE));

        VectorDrawable 在MIUI上不显示的问题
        Drawable drawable = getResources().getDrawable(R.drawable.background_gradient_dark, getTheme());
        mTextViews = new LinkedList<>();
        for (int i = 0; i < 50000; i++) {
            TextView textView = new TextView(this);
            textView.setBackground(getResources().getDrawable(R.drawable.background_gradient_dark, getTheme()));
            mTextViews.add(textView);
        }
        Drawable d2 = getResources().getDrawable(R.drawable.background_gradient_dark, getTheme());

        Log.e(MainActivity.class.getSimpleName(), drawable.getClass().getName() + ":" + drawable.toString());
        Log.e(MainActivity.class.getSimpleName(), d2.getClass().getName() + ":" + d2.toString());

        mViewDataBinding.nestedScrollView.setBackground(d2);
*/
       /* SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        try {
            Date d = date.parse("20001111");
            Log.e(MainActivity.class.getSimpleName(), date.getCalendar().toString()
                    + " long:" + d.getTime()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }
}
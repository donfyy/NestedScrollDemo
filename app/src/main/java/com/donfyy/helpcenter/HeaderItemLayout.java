package com.donfyy.helpcenter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HeaderItemLayout extends LinearLayout {
    private View mIconView;
    private View mTextView;

    public HeaderItemLayout(@NonNull Context context) {
        super(context);
    }

    public HeaderItemLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeaderItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public View getIconView() {
        return mIconView;
    }

    public View getTextView() {
        return mTextView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIconView = getChildAt(0);
        mTextView = getChildAt(0);
    }
}

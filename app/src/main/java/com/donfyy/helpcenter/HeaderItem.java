package com.donfyy.helpcenter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HeaderItem extends FrameLayout {
    private View mIconView;
    private View mTextView;

    public HeaderItem(@NonNull Context context) {
        super(context);
    }

    public HeaderItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeaderItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public View getIconView() {
        return mIconView;
    }

    public View getTextView() {
        return mTextView;
    }
}

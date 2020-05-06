package com.donfyy.helpcenter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.appbar.AppBarLayout;

public class CollapsingHeaderLayout extends FrameLayout {

    private com.donfyy.helpcenter.databinding.CollapsingHeaderLayoutBinding mBinding;

    public CollapsingHeaderLayout(Context context) {
        super(context);
        init();
    }

    public CollapsingHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CollapsingHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CollapsingHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private TransitionInfo mIcFaqTransition = new TransitionInfo();
    private float verticalOffsetMax;

    private void init() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.collapsing_header_layout, this, true);

        final ImageView icFaq = mBinding.icFaq;
        icFaq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
            }
        });
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateTransitionInfo(icFaq);
                ViewParent parent = getParent();
                if (parent instanceof AppBarLayout) {
                    ((AppBarLayout) parent).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                            mBinding.title.setTranslationY(-verticalOffset);
                            icFaq.setTranslationY(mIcFaqTransition.getTranslationOffset().y / verticalOffsetMax * verticalOffset);
                            icFaq.setTranslationX(mIcFaqTransition.getTranslationOffset().x / verticalOffsetMax * verticalOffset);
                            icFaq.setScaleX(1 - mIcFaqTransition.getScaleOffset().x / verticalOffsetMax * verticalOffset);
                            icFaq.setScaleY(1 - mIcFaqTransition.getScaleOffset().y / verticalOffsetMax * verticalOffset);
                        }
                    });
                } else {
                    throw new IllegalArgumentException("CollapsingHeaderLayout must be used with com.google.android.material.appbar.AppBarLayout");
                }

                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void calculateTransitionInfo(ImageView icFaq) {
        verticalOffsetMax = getMinimumHeight() - getHeight();
        float yEnd = mBinding.title.getTop() - verticalOffsetMax + mBinding.title.getHeight() / 2f;
        float yStart = icFaq.getTop() + icFaq.getHeight() / 2f;
        float yOffsetMax = yEnd - yStart;
        float xOffsetMax = getWidth() / 2f - icFaq.getLeft();
        float yScaleOffset = -(36 - 40) / 40f;
        float xScaleOffset = -(36 - 40) / 40f;
        mIcFaqTransition.setTranslationOffset(xOffsetMax, yOffsetMax);
        mIcFaqTransition.setScaleOffset(xScaleOffset, yScaleOffset);
    }
}

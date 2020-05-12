package com.donfyy.helpcenter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;

import java.util.LinkedList;
import java.util.List;

public class CollapsingHeaderLayout extends FrameLayout {

    //    private CollapsingHeaderLayoutBinding mBinding;
    private float verticalOffsetMax;
    private List<HeaderItemLayout> mHeaderItemLayouts = new LinkedList<>();
    private List<TransitionInfo> mTransitionInfoList = new LinkedList<>();
    private View mTitle;
    private float mMenuPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            30, getResources().getDisplayMetrics());
    private float mMenuScaleFactor = 0.8f;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int maxHeight = 0;
        int childState = 0;

        int size = MeasureSpec.getSize(widthMeasureSpec);
        int itemWidth = (size - getPaddingLeft() - getPaddingRight()) / mHeaderItemLayouts.size();
        int itemWidthSpec = MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.AT_MOST);
        for (HeaderItemLayout headerItemLayout : mHeaderItemLayouts) {
            measureChild(headerItemLayout, itemWidthSpec, heightMeasureSpec);

            MarginLayoutParams params = (MarginLayoutParams) headerItemLayout.getLayoutParams();
            maxHeight = Math.max(maxHeight, headerItemLayout.getMeasuredHeight() + params.topMargin + params.bottomMargin);
            childState = combineMeasuredStates(childState, headerItemLayout.getMeasuredState());
        }

        View title = getChildAt(0);
        childState = combineMeasuredStates(childState, title.getMeasuredState());
        MarginLayoutParams layoutParams = (MarginLayoutParams) title.getLayoutParams();
        int titleHeight = layoutParams.topMargin + title.getMeasuredHeight() + layoutParams.bottomMargin;
        maxHeight += titleHeight;


        setMeasuredDimension(resolveSizeAndState(size, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View title = getChildAt(0);

        MarginLayoutParams lp = (MarginLayoutParams) title.getLayoutParams();
        title.layout(
                lp.leftMargin,
                lp.topMargin,
                lp.leftMargin + title.getMeasuredWidth(),
                lp.topMargin + title.getMeasuredHeight()
        );

        int itemWidth = (r - l - getPaddingLeft() - getPaddingRight()) / mHeaderItemLayouts.size();

        int maxTopMargin = 0;
        for (HeaderItemLayout layout : mHeaderItemLayouts) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) layout.getLayoutParams();
            maxTopMargin = Math.max(maxTopMargin, layoutParams.topMargin);
        }

        int childTop = lp.topMargin + title.getMeasuredHeight() + lp.bottomMargin + maxTopMargin;

        int childLeft = getPaddingLeft();
        int size = mHeaderItemLayouts.size();
        for (int i = 0; i < size; i++) {
            HeaderItemLayout layout = mHeaderItemLayouts.get(i);
            int childLeftReal = childLeft + itemWidth / 2 - layout.getMeasuredWidth() / 2 + i * itemWidth;
            layout.layout(
                    childLeftReal,
                    childTop,
                    childLeftReal + layout.getMeasuredWidth(),
                    childTop + layout.getMeasuredHeight()

            );
        }
    }

    private void init() {
//        register scroll listener
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                ViewParent parent = getParent();
                if (!(parent instanceof AppBarLayout)) {
                    return;
                }

                calculateTransitionInfo();
                AppBarLayout appBarLayout = (AppBarLayout) parent;
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        mTitle.setTranslationY(-verticalOffset);

                        if (verticalOffset == 0) {
                            for (HeaderItemLayout headerItemLayout : mHeaderItemLayouts) {
                                headerItemLayout.getTextView().setTransitionAlpha(1);
                            }
                        } else {
                            for (HeaderItemLayout headerItemLayout : mHeaderItemLayouts) {
                                headerItemLayout.getTextView().setTransitionAlpha(0);
                            }
                        }

                        for (int i = 0; i < mHeaderItemLayouts.size(); i++) {
                            HeaderItemLayout layout = mHeaderItemLayouts.get(i);
                            TransitionInfo mIcFaqTransition = mTransitionInfoList.get(i);

                            layout.setTranslationY(mIcFaqTransition.getTranslationOffset().y / verticalOffsetMax * verticalOffset);
                            layout.setTranslationX(mIcFaqTransition.getTranslationOffset().x / verticalOffsetMax * verticalOffset);
                            layout.getIconView().setScaleX(1 - mIcFaqTransition.getScaleOffset().x / verticalOffsetMax * verticalOffset);
                            layout.getIconView().setScaleY(1 - mIcFaqTransition.getScaleOffset().y / verticalOffsetMax * verticalOffset);
                        }

                    }
                });
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitle = getChildAt(0);
        findAllHeaderItems();
    }

    private void findAllHeaderItems() {
        mHeaderItemLayouts.clear();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof HeaderItemLayout) {
                mHeaderItemLayouts.add(((HeaderItemLayout) view));
            }
        }
    }

    private void calculateTransitionInfo() {
        mTransitionInfoList = new LinkedList<>();
        for (int i = 0; i < mHeaderItemLayouts.size(); i++) {
            HeaderItemLayout headerItemLayout = mHeaderItemLayouts.get(i);
            TransitionInfo mIcFaqTransition = new TransitionInfo();
            View icFaq = headerItemLayout.getIconView();
            verticalOffsetMax = getMinimumHeight() - getHeight();
            float yEnd = mTitle.getTop() - verticalOffsetMax + mTitle.getHeight() / 2f;
            float yStart = headerItemLayout.getTop() + icFaq.getHeight() / 2f;
            float yOffsetMax = yEnd - yStart;
            float width = (icFaq.getWidth() * mMenuScaleFactor) * (mHeaderItemLayouts.size() - i - 1) + (mHeaderItemLayouts.size() - i - 1) * mMenuPadding;
            float xOffsetMax = getWidth() - getPaddingRight() - icFaq.getWidth() * mMenuScaleFactor / 2 - headerItemLayout.getWidth() / 2f - width - headerItemLayout.getLeft();
            float yScaleOffset = 1 - mMenuScaleFactor;
            float xScaleOffset = 1 - mMenuScaleFactor;
            mIcFaqTransition.setTranslationOffset(xOffsetMax, yOffsetMax);
            mIcFaqTransition.setScaleOffset(xScaleOffset, yScaleOffset);

            mTransitionInfoList.add(mIcFaqTransition);
        }
    }
}

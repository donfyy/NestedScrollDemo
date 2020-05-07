package com.donfyy.helpcenter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

public class CollapsingHeaderLayout extends FrameLayout {

    //    private CollapsingHeaderLayoutBinding mBinding;
    private TransitionInfo mIcFaqTransition = new TransitionInfo();
    private float verticalOffsetMax;
    private List<HeaderItemLayout> mHeaderItemLayouts = new LinkedList<>();

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
        for (HeaderItemLayout layout : mHeaderItemLayouts) {
            int childLeftReal = childLeft + itemWidth / 2 - layout.getMeasuredWidth() / 2;
            layout.layout(
                    childLeftReal,
                    childTop,
                    childLeftReal + layout.getMeasuredWidth(),
                    childTop + layout.getMeasuredHeight()

            );
        }
    }

    private void init() {
        /*mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.collapsing_header_layout, this, true);

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
        });*/
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

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

    private void calculateTransitionInfo(ImageView icFaq) {
       /* verticalOffsetMax = getMinimumHeight() - getHeight();
        float yEnd = mBinding.title.getTop() - verticalOffsetMax + mBinding.title.getHeight() / 2f;
        float yStart = icFaq.getTop() + icFaq.getHeight() / 2f;
        float yOffsetMax = yEnd - yStart;
        float xOffsetMax = getWidth() / 2f - icFaq.getLeft();
        float yScaleOffset = -(36 - 40) / 40f;
        float xScaleOffset = -(36 - 40) / 40f;
        mIcFaqTransition.setTranslationOffset(xOffsetMax, yOffsetMax);
        mIcFaqTransition.setScaleOffset(xScaleOffset, yScaleOffset);*/
    }
}

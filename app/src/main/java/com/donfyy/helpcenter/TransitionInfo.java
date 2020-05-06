package com.donfyy.helpcenter;

import android.graphics.PointF;

public class TransitionInfo {
    private PointF mTranslationOffset;
    private PointF mScaleOffset;

    public PointF getTranslationOffset() {
        return mTranslationOffset;
    }

    public void setTranslationOffset(PointF translationOffset) {
        this.mTranslationOffset = translationOffset;
    }

    public void setTranslationOffset(float offsetX, float offsetY) {
        this.mTranslationOffset = new PointF(offsetX, offsetY);
    }

    public PointF getScaleOffset() {
        return mScaleOffset;
    }

    public void setScaleOffset(PointF scaleOffset) {
        this.mScaleOffset = scaleOffset;
    }

    public void setScaleOffset(float offsetX, float offsetY) {
        this.mScaleOffset = new PointF(offsetX, offsetY);
    }
}

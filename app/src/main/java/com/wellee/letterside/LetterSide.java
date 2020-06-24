package com.wellee.letterside;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LetterSide extends View {

    private String[] mLetters = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int mTextSizeSp;
    private int mNormalColor;
    private int mLightColor;
    private Paint mNormalPaint, mLightPaint;

    private int mItemHeight;
    private String mPreLetter;
    private String mCurrentLetter;

    private OnTouchItemListener mOnTouchItemListener;

    public LetterSide(Context context) {
        this(context, null);
    }

    public LetterSide(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSide(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterSide);
        mTextSizeSp = array.getInt(R.styleable.LetterSide_textSizeSp, 18);
        mNormalColor = array.getColor(R.styleable.LetterSide_normalColor, Color.GRAY);
        mLightColor = array.getColor(R.styleable.LetterSide_lightColor, Color.BLUE);
        array.recycle();
    }

    private void initPaint() {
        mNormalPaint = initPaint(mNormalColor);
        mLightPaint = initPaint(mLightColor);
    }

    private Paint initPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(sp2px(mTextSizeSp));
        paint.setColor(color);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int letterWidth = (int) mNormalPaint.measureText("A");
        int width = getPaddingLeft() + getPaddingRight() + letterWidth;
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mItemHeight = (height- getPaddingTop() - getPaddingBottom()) / mLetters.length;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLetters.length; i++) {
            int dx = (int) (getWidth() / 2 - mNormalPaint.measureText(mLetters[i]) / 2);
            Paint.FontMetricsInt fontMetrics = mNormalPaint.getFontMetricsInt();
            int letterCenterY = getPaddingTop() + mItemHeight * i + mItemHeight / 2;
            int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            int baseline = letterCenterY + dy;
            if (mLetters[i].equals(mCurrentLetter)) {
                canvas.drawText(mLetters[i], dx, baseline, mLightPaint);
            } else {
                canvas.drawText(mLetters[i], dx, baseline, mNormalPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY() - getPaddingTop();
                int currentPosition = (int) (y / mItemHeight);
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > mLetters.length - 1) {
                    currentPosition = mLetters.length - 1;
                }
                mCurrentLetter = mLetters[currentPosition];
                if (!mCurrentLetter.equals(mPreLetter)) {
                    invalidate();
                    if (mOnTouchItemListener != null) {
                        mOnTouchItemListener.onTouchItem(mCurrentLetter, true);
                    }
                    mPreLetter = mCurrentLetter;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mOnTouchItemListener != null) {
                    mOnTouchItemListener.onTouchItem(mCurrentLetter, false);
                }
                mPreLetter = mCurrentLetter = null;
                invalidate();
                break;
        }
        return true;
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getContext().getResources().getDisplayMetrics());
    }

    public interface OnTouchItemListener {
        void onTouchItem(String letter, boolean isTouching);
    }

    public void setOnTouchItemListener(OnTouchItemListener onTouchItemListener) {
        this.mOnTouchItemListener = onTouchItemListener;
    }

}

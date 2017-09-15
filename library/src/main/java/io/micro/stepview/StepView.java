package io.micro.stepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * StepView include startLine,marker,endLine.
 *
 * @author act262@gmail.com
 */
public class StepView extends View {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private Drawable mMarker;
    private int mMarkerSize;
    private int mMarkerPadding;
    private boolean mInCenter;
    private int mMarkerOffset;
    private Drawable mStartLine;
    private Drawable mEndLine;
    private int mLineSize;
    private int mOrientation;

    @StepType
    private int mType;

    public StepView(Context context) {
        super(context);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @StepType
    public static int getStepType(int totalSize, int position) {
        if (totalSize == 1) {
            return StepType.ONLY_ONE;
        } else if (position == 0) {
            return StepType.START;
        } else if (position == totalSize - 1) {
            return StepType.END;
        } else {
            return StepType.NORMAL;
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        mMarker = typedArray.getDrawable(R.styleable.StepView_marker);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.StepView_markerSize,
                mMarker == null ? 0 : mMarker.getMinimumWidth());
        mMarkerPadding = typedArray.getDimensionPixelSize(R.styleable.StepView_markerPadding, 0);

        if (typedArray.hasValue(R.styleable.StepView_markerOffset)) {
            mMarkerOffset = typedArray.getDimensionPixelSize(R.styleable.StepView_markerOffset, 0);
        } else {
            mInCenter = typedArray.getBoolean(R.styleable.StepView_markerInCenter, true);
        }

        if (typedArray.hasValue(R.styleable.StepView_startLine)) {
            mStartLine = typedArray.getDrawable(R.styleable.StepView_startLine);
        } else {
            mStartLine = typedArray.getDrawable(R.styleable.StepView_line);
        }

        if (typedArray.hasValue(R.styleable.StepView_endLine)) {
            mEndLine = typedArray.getDrawable(R.styleable.StepView_endLine);
        } else {
            mEndLine = typedArray.getDrawable(R.styleable.StepView_line);
        }

        mLineSize = typedArray.getDimensionPixelOffset(R.styleable.StepView_lineSize, 5);
        mOrientation = typedArray.getInt(R.styleable.StepView_lineOrientation, VERTICAL);

        mType = typedArray.getInt(R.styleable.StepView_type, StepType.NORMAL);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = mMarkerSize + getPaddingLeft() + getPaddingRight();
        int h = mMarkerSize + getPaddingTop() + getPaddingBottom();

        int widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);

        setMeasuredDimension(widthSize, heightSize);
        initDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initDrawable();
    }

    public int getType() {
        return mType;
    }

    private void initDrawable() {
        int pLeft = getPaddingLeft();
        int pTop = getPaddingTop();
        int pRight = getPaddingRight();
        int pBottom = getPaddingBottom();

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int cWidth = width - pLeft - pRight;
        int cHeight = height - pTop - pBottom;
        int markerSize = Math.min(mMarkerSize, Math.min(cWidth, cHeight));

        // evaluate marker bound
        if (mInCenter) {
            mMarker.setBounds((width - markerSize) / 2, (height - markerSize) / 2,
                    (width + markerSize) / 2, (height + markerSize) / 2);
        } else {
            if (VERTICAL == mOrientation) {
                mMarker.setBounds((width - markerSize) / 2, pTop + mMarkerOffset,
                        (width + markerSize) / 2, pTop + mMarkerOffset + markerSize);
            } else {
                mMarker.setBounds(mMarkerOffset + pLeft, (height - markerSize) / 2,
                        mMarkerOffset + pLeft + markerSize, (height + markerSize) / 2);
            }
        }

        // evaluate start/end line bound
        Rect bounds = mMarker.getBounds();
        if (mOrientation == VERTICAL) {
            int lineLeft = bounds.centerX() - mLineSize / 2;
            int lineRight = lineLeft + mLineSize;

            if (mStartLine != null) {
                mStartLine.setBounds(lineLeft, pTop, lineRight, bounds.top - mMarkerPadding);
            }
            if (mEndLine != null) {
                mEndLine.setBounds(lineLeft, bounds.bottom + mMarkerPadding, lineRight, height - pBottom);
            }
        } else {
            int lineTop = bounds.centerY() - mLineSize / 2;
            int lineBottom = lineTop + mLineSize;

            if (mStartLine != null) {
                mStartLine.setBounds(pLeft, lineTop, bounds.left - mMarkerPadding, lineBottom);
            }
            if (mEndLine != null) {
                mEndLine.setBounds(bounds.right + mMarkerPadding, lineTop, width - pRight, lineBottom);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mStartLine != null) {
            mStartLine.draw(canvas);
        }
        if (mMarker != null) {
            mMarker.draw(canvas);
        }
        if (mEndLine != null) {
            mEndLine.draw(canvas);
        }
    }

    public void setup(Drawable marker, Drawable startLine, Drawable endLine) {
        this.mMarker = marker;
        this.mStartLine = startLine;
        this.mEndLine = endLine;
    }

    public void setMarker(Drawable drawable) {
        this.mMarker = drawable;
    }

    public void setStartLine(Drawable drawable) {
        this.mStartLine = drawable;
    }

    public void setEndLine(Drawable drawable) {
        this.mEndLine = drawable;
    }

    public void bindStep(@StepType int type) {
        switch (type) {
            case StepType.START:
                setStartLine(null);
                break;
            case StepType.NORMAL:
                break;
            case StepType.END:
                setEndLine(null);
                break;
            case StepType.ONLY_ONE:
                setStartLine(null);
                setEndLine(null);
                break;
            default:
                // Not happen
        }
        initDrawable();
    }

    public static class Builder {
    }
}

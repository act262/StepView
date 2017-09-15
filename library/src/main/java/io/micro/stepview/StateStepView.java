package io.micro.stepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * StepView use different state.
 *
 * @author act262@gmail.com
 */
public class StateStepView extends StepView {

    private Drawable inactiveStartLine;
    private Drawable inactiveMarker;
    private Drawable inactiveEndLine;
    private Drawable activeStartLine;
    private Drawable activeMarker;
    private Drawable activeEndLine;
    private Drawable completedStartLine;
    private Drawable completedMarker;
    private Drawable completedEndLine;

    public StateStepView(Context context) {
        super(context);
    }

    public StateStepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StateStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateStepView);

        inactiveStartLine = typedArray.getDrawable(R.styleable.StateStepView_inactiveStartLine);
        inactiveEndLine = typedArray.getDrawable(R.styleable.StateStepView_inactiveEndLine);
        inactiveMarker = typedArray.getDrawable(R.styleable.StateStepView_inactiveMarker);

        activeStartLine = typedArray.getDrawable(R.styleable.StateStepView_activeStartLine);
        activeEndLine = typedArray.getDrawable(R.styleable.StateStepView_activeEndLine);
        activeMarker = typedArray.getDrawable(R.styleable.StateStepView_activeMarker);

        completedStartLine = typedArray.getDrawable(R.styleable.StateStepView_completedStartLine);
        completedEndLine = typedArray.getDrawable(R.styleable.StateStepView_completedEndLine);
        completedMarker = typedArray.getDrawable(R.styleable.StateStepView_completedMarker);

        int state = typedArray.getInt(R.styleable.StateStepView_state, State.COMPLETED);
        typedArray.recycle();

        bindState(state, getType());
    }

    public void bindState(@State int state, @StepType int type) {
        switch (state) {
            case State.INACTIVE:
                setup(inactiveMarker, inactiveStartLine, inactiveEndLine);
                break;
            case State.ACTIVE:
                setup(activeMarker, activeStartLine, activeEndLine);
                break;
            case State.COMPLETED:
                setup(completedMarker, completedStartLine, completedEndLine);
                break;
            default:
                // Could not happen.
        }
        bindStep(type);
    }

}

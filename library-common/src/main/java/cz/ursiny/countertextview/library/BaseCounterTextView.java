package cz.ursiny.countertextview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.text.NumberFormat;

import cz.ursiny.countertextview.R;

/**
 * Animated CounterTextView
 * Created by Michal Ursiny on 28.03.2017.
 */

abstract class BaseCounterTextView extends AppCompatTextView {

    private static final int DEFAULT_SPEED = 25;
    public static final int STEP_10 = 10;
    private final NumberFormat numberFormat = NumberFormat.getInstance();
    private long mTarget = 0;
    private long mCurrent = 0;
    private boolean mNumberFormat = true;
    private int mSpeed;

    BaseCounterTextView(Context context) {
        super(context);
        initView(null);
    }

    BaseCounterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    BaseCounterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @SuppressWarnings("ResourceType")
    private void initView(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            int[] set = {R.attr.numberFormat, R.attr.speed};
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, set);
            mNumberFormat = attributes.getBoolean(0, true);
            mSpeed = attributes.getInt(1, DEFAULT_SPEED);
            attributes.recycle();
        }
    }

    protected int getSpeed() {
        return mSpeed;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unsubscribe();
    }

    abstract void unsubscribe();

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mTarget, mCurrent);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mTarget = savedState.getTarget();
        mCurrent = savedState.getCurrent();
        subscribeIfNeeded();
    }

    abstract void subscribeIfNeeded();

    protected void tick() {
        if (mCurrent != mTarget) {
            proceedTowardsTarget((mCurrent < mTarget));
        } else {
            // mCurrent == mTarget
            unsubscribe();
        }
        display(mCurrent);
    }

    private void proceedTowardsTarget(boolean increase) {
        long diff = 0;
        if (mTarget > mCurrent) {
            diff = mTarget - mCurrent;
        } else {
            diff = mCurrent - mTarget;
        }
        boolean stepFound = false;
        int step = STEP_10;
        while (!stepFound) {
            if (diff < step) {
                stepFound = true;

                if (step == STEP_10) {
                    if (increase) {
                        mCurrent++;
                    } else {
                        mCurrent--;
                    }
                } else {
                    final long oneStep = Math.round(Math.random() * (step / 10 / 2) + (step / 10 / 2));
                    if (increase) {
                        mCurrent += oneStep;
                    } else {
                        mCurrent -= oneStep;
                    }
                }

            } else {
                step *= STEP_10;
            }
        }
    }

    private void display(long current) {
        if (mNumberFormat) {
            setText(numberFormat.format(current));
        } else {
            setText(String.valueOf(current));
        }
    }

    public void setTarget(long target) {
        if (mTarget != target) {
            mTarget = target;
        }
        subscribeIfNeeded();
    }

    protected static class SavedState extends BaseSavedState {

        private final long mTarget, mCurrent;

        SavedState(Parcelable superState, long target, long current) {
            super(superState);
            mTarget = target;
            mCurrent = current;
        }

        SavedState(Parcel source) {
            super(source);
            mTarget = source.readLong();
            mCurrent = source.readLong();
        }

        long getTarget() {
            return mTarget;
        }

        long getCurrent() {
            return mCurrent;
        }

        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeLong(mTarget);
            destination.writeLong(mCurrent);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

    }

}

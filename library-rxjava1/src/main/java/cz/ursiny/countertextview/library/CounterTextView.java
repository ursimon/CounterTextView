package cz.ursiny.countertextview.library;

import android.content.Context;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import cz.ursiny.countertextview.library.common.BaseCounterTextView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * RxJava1 implementation
 * Created by Michal Ursiny on 12.11.2017.
 */

public class CounterTextView extends BaseCounterTextView {

    private Subscription mSubscription;
    private Action1<Long> mAction;

    public CounterTextView(Context context) {
        super(context);
    }

    public CounterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CounterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void unsubscribe() {
        if ((mSubscription != null) && (!mSubscription.isUnsubscribed())) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected void subscribeIfNeeded() {
        if (mSubscription == null || mSubscription.isUnsubscribed()) {
            mSubscription = Observable.interval(getSpeed(), TimeUnit.MILLISECONDS)
                    .onBackpressureDrop()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> tick());
        }
    }

    public Action1<Long> targetAction() {
        if (mAction == null) {
            mAction = this::setTarget;
        }
        return mAction;
    }

}

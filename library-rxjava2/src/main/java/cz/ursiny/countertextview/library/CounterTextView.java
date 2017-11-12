package cz.ursiny.countertextview.library;

import android.content.Context;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * RxJava2 implementation
 * Created by Michal Ursiny on 12.11.2017.
 */

public class CounterTextView extends BaseCounterTextView {

    private Disposable mDisposable;
    private Consumer<Long> mConsumer;

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
    void unsubscribe() {
        if ((mDisposable != null) && (!mDisposable.isDisposed())) {
            mDisposable.dispose();
        }
    }

    @Override
    void subscribeIfNeeded() {
        if (mDisposable == null || mDisposable.isDisposed()) {
            mDisposable = Observable.interval(getSpeed(), TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> tick());
        }
    }

    public Consumer<Long> targetConsumer() {
        if (mConsumer == null) {
            mConsumer = new Consumer<Long>() {
                @Override
                public void accept(Long target) throws Exception {
                    setTarget(target);
                }
            };
        }
        return mConsumer;
    }

}

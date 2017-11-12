package cz.ursiny.countertextview.sample2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.ursiny.countertextview.R;
import cz.ursiny.countertextview.library.CounterTextView;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.counter)
    CounterTextView mCounter;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.target)
    TextView mTarget;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDisposable = RxSeekBar.userChanges(mSeekBar)
                .map(Long::valueOf)
                .doOnNext(aLong -> mTarget.setText(String.valueOf(aLong)))
                .subscribe(mCounter.targetConsumer());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDisposable.dispose();
    }
}

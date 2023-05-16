package cz.ursiny.countertextview.sample2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.jakewharton.rxbinding3.widget.RxSeekBar;

import cz.ursiny.countertextview.databinding.ActivityMainBinding;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private Disposable mDisposable;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDisposable = RxSeekBar.userChanges(binding.seekBar)
                .map(Long::valueOf)
                .doOnNext(aLong -> binding.target.setText(String.valueOf(aLong)))
                .subscribe(binding.counter.targetConsumer());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDisposable.dispose();
    }
}

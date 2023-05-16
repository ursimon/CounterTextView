package cz.ursiny.countertextview.sample1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.jakewharton.rxbinding.widget.RxSeekBar;

import cz.ursiny.countertextview.sample1.databinding.ActivityMainBinding;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {
    private Subscription mSubscription;
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
        mSubscription = RxSeekBar.userChanges(binding.seekBar)
                .map(Long::valueOf)
                .doOnNext(aLong -> binding.target.setText(String.valueOf(aLong)))
                .subscribe(binding.counter.targetAction());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSubscription.unsubscribe();
    }
}

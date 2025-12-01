package houlei.andriod.sfk.ui.bluetooth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class BluetoothViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> mMessage;

    private final MutableLiveData<Integer> mProgress;

    private final MutableLiveData<String> mEnableBtText;
    private final MutableLiveData<String> mDisableBtText;

    public BluetoothViewModel() {
        mMessage = new MutableLiveData<>();
        mMessage.setValue("Hello");
        mProgress = new MutableLiveData<>();
        mProgress.setValue(0);
        mEnableBtText = new MutableLiveData<>();
        mDisableBtText = new MutableLiveData<>();
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage.setValue(message);
    }

    public LiveData<Integer> getProgress() {
        return mProgress;
    }

    public void startProgress() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mProgress.postValue(Optional.ofNullable(mProgress.getValue()).orElse(0) + 10);
                if (mProgress.getValue() >= 100) {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }

    public LiveData<String> getEnableBtText() {
        return mEnableBtText;
    }

    public void setEnableBtText(String text) {
        mEnableBtText.setValue(text);
    }

    public LiveData<String> getDisableBtText() {
        return mDisableBtText;
    }

    public void setDisableBtText(String text) {
        mDisableBtText.setValue(text);
    }

}
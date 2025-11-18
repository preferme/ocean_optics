package houlei.andriod.sfk.ui.settings;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import houlei.andriod.sfk.utils.LanguageUtils;

public class SettingsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> mLanguageIndex;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
        mLanguageIndex = new MutableLiveData<>(0);
    }

    public void loadPreferences(Context context) {
        int index = LanguageUtils.loadLanguageIndex(context, 0);
        mLanguageIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Integer> getLanguageIndex() {
        return mLanguageIndex;
    }

}
package houlei.andriod.sfk.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import houlei.andriod.sfk.ApplicationContext;
import houlei.andriod.sfk.databinding.FragmentSettingsBinding;
import houlei.andriod.sfk.utils.LanguageUtils;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        Log.d("MyTag", "[SettingsFragment][onCreateView] begin.");
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        mViewModel.loadPreferences(requireActivity());

        binding = FragmentSettingsBinding.inflate(inflater, container, false);


        final TextView textView = binding.textSettings;
        mViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // 语言选择框
        final Spinner spinner = binding.spinnerLanguages;
        mViewModel.getLanguageIndex().observe(getViewLifecycleOwner(), spinner::setSelection);
        ArrayAdapter<Language> arrayAdapter = new ArrayAdapter<>(this.requireContext(), android.R.layout.simple_spinner_item, LanguageUtils.requireLanguages);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getLanguageIndex().setValue(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing yet.
            }
        });

        // 应用按钮
        Button button = binding.buttonApply;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Apply Language Changes.
                if (languageChanged()) {
                    //noinspection DataFlowIssue
                    int languageIndex = mViewModel.getLanguageIndex().isInitialized() ? mViewModel.getLanguageIndex().getValue() : 0;
                    Language selectedLanguage = LanguageUtils.requireLanguages[languageIndex];
                    LanguageUtils.storeLanguage(requireActivity(), selectedLanguage);
                    requireActivity().recreate();
                }

                Toast.makeText(requireContext(), "按下了应用按钮", Toast.LENGTH_SHORT).show();
                Log.d("MyTag", "按下了应用按钮, languageIndex: " + mViewModel.getLanguageIndex().getValue());
            }
        });
//        Log.d("MyTag", "[SettingsFragment][onCreateView] end.");
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
//        Log.d("MyTag", "[SettingsFragment][onDestroyView] begin.");
        super.onDestroyView();
        binding = null;
        mViewModel = null;
//        Log.d("MyTag", "[SettingsFragment][onDestroyView] end.");
    }

    @Override
    public void onStop() {
//        Log.d("MyTag", "[SettingsFragment][onStop] begin.");
        super.onStop();
        ApplicationContext.getInstance().setCurrentFragment(null);
//        Log.d("MyTag", "[SettingsFragment][onStop] end.");
    }

    private boolean languageChanged() {
        //noinspection DataFlowIssue
        int languageIndex = mViewModel.getLanguageIndex().isInitialized() ? mViewModel.getLanguageIndex().getValue() : 0;
        Language selectedLanguage = LanguageUtils.requireLanguages[languageIndex];
        Language loadedLanguage = LanguageUtils.loadLanguage(requireActivity());
        return  !selectedLanguage.equals(loadedLanguage);
    }

    public boolean settingsChanged() {
        return languageChanged();
    }

    @Override
    public void onStart() {
//        Log.d("MyTag", "[SettingsFragment][onStart] begin.");
        super.onStart();
        ApplicationContext.getInstance().setCurrentFragment(this);
//        Log.d("MyTag", "[SettingsFragment][onStart] end.");
    }

    public void clickApply() {
        binding.buttonApply.callOnClick();
    }
}
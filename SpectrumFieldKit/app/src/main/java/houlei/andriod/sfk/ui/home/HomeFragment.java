package houlei.andriod.sfk.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import houlei.andriod.sfk.ApplicationContext;
import houlei.andriod.sfk.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        Log.d("MyTag", "[HomeFragment][onCreateView] begin.");
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        Log.d("MyTag", "[HomeFragment][onCreateView] end.");
        return root;
    }

    @Override
    public void onDestroyView() {
//        Log.d("MyTag", "[HomeFragment][onDestroyView] begin.");
        super.onDestroyView();
        binding = null;
//        Log.d("MyTag", "[HomeFragment][onDestroyView] end.");
    }

    @Override
    public void onStart() {
//        Log.d("MyTag", "[HomeFragment][onStart] begin.");
        super.onStart();
        ApplicationContext.getInstance().setCurrentFragment(this);
//        Log.d("MyTag", "[HomeFragment][onStart] end.");
    }

    @Override
    public void onStop() {
//        Log.d("MyTag", "[HomeFragment][onStop] begin.");
        super.onStop();
        ApplicationContext.getInstance().setCurrentFragment(null);
//        Log.d("MyTag", "[HomeFragment][onStop] end.");
    }

}
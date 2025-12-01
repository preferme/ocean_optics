package houlei.andriod.sfk.ui.bluetooth;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import houlei.andriod.sfk.ApplicationContext;
import houlei.andriod.sfk.R;
import houlei.andriod.sfk.databinding.FragmentBluetoothBinding;

public class BluetoothFragment extends Fragment {

    private static final String TAG = BluetoothFragment.class.getSimpleName();

    private BluetoothViewModel mViewModel;
    private FragmentBluetoothBinding binding;


//    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(BluetoothViewModel.class);
        binding = FragmentBluetoothBinding.inflate(inflater, container, false);

        // TODO: Use the ViewModel
        final TextView textView = binding.textBluetooth;
//        mViewModel.setMessage("更改内容");
        mViewModel.getMessage().observe(getViewLifecycleOwner(), textView::setText);

        // 进度条
        final ProgressBar progressBar = binding.progressScanBluetooth;
        mViewModel.getProgress().observe(getViewLifecycleOwner(), progressBar::setProgress);
        mViewModel.startProgress();

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 开启蓝牙功能
        Intent enableBtIntent  = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        ActivityResultLauncher<Intent> enableBluetoothActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            Log.d(TAG, "[BluetoothFragment][onActivityResult] result: " + result);
            if (RESULT_OK == result.getResultCode()) {
                mViewModel.setMessage(getString(R.string.fragment_bluetooth_enabled));
                binding.btnDisableBt.setVisibility(View.VISIBLE);
                binding.btnEnableBt.setVisibility(View.GONE);
                binding.btnScanDevice.setVisibility(View.VISIBLE);
            } else {
                mViewModel.setMessage(getString(R.string.fragment_bluetooth_open_failed));
            }
        });
        final Button btnEnableBT = binding.btnEnableBt;
        mViewModel.getEnableBtText().observe(getViewLifecycleOwner(), btnEnableBT::setText);
        btnEnableBT.setOnClickListener(v -> {
            if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
                enableBluetoothActivityLauncher.launch(enableBtIntent);
            }
        });
        // 关闭蓝牙功能
        Intent disableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_DISABLE");
        ActivityResultLauncher<Intent> disableBluetoothActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            Log.d(TAG, "[BluetoothFragment][onActivityResult] result: " + result);
            if (RESULT_OK == result.getResultCode()) {
                mViewModel.setMessage(getString(R.string.fragment_bluetooth_disabled));
                binding.btnEnableBt.setVisibility(View.VISIBLE);
                binding.btnDisableBt.setVisibility(View.GONE);
                binding.btnScanDevice.setVisibility(View.GONE);
            } else {
                mViewModel.setMessage(getString(R.string.fragment_bluetooth_close_failed));
            }
        });
        final Button btnDisableBT = binding.btnDisableBt;
        mViewModel.getDisableBtText().observe(getViewLifecycleOwner(), btnDisableBT::setText);
        btnDisableBT.setOnClickListener(v -> {
            if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
                disableBluetoothActivityLauncher.launch(disableBtIntent);
            }
        });

//        Log.d(TAG, "[BluetoothFragment][onCreateView] bluetoothAdapter: " + bluetoothAdapter);
        // 检测蓝牙的功能状态
        if (bluetoothAdapter == null) {
            mViewModel.setMessage(getString(R.string.fragment_bluetooth_not_support));
            binding.btnEnableBt.setVisibility(View.GONE); // View.INVISIBLE
            binding.btnDisableBt.setVisibility(View.GONE); // View.INVISIBLE
            binding.btnScanDevice.setVisibility(View.GONE);
        } else {
            if (bluetoothAdapter.isEnabled()) {
                mViewModel.setMessage(getString(R.string.fragment_bluetooth_enabled));
                binding.btnDisableBt.setVisibility(View.VISIBLE);
                binding.btnEnableBt.setVisibility(View.GONE);
                binding.btnScanDevice.setVisibility(View.VISIBLE);
            } else {
                mViewModel.setMessage(getString(R.string.fragment_bluetooth_disabled));
                binding.btnEnableBt.setVisibility(View.VISIBLE);
                binding.btnDisableBt.setVisibility(View.GONE);
                binding.btnScanDevice.setVisibility(View.GONE);
            }
        }

        final ActivityResultLauncher<String[]> bluetoothPermissionsActivityLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), o -> {
            ArrayList<String> permissions = new ArrayList<>();
            for (Map.Entry<String, Boolean> entry : o.entrySet()) {
                if (!entry.getValue()) {
                    permissions.add(entry.getKey());
                }
            }
            mViewModel.setMessage("Permission denied: " + String.join(",", permissions));
            Log.d(TAG, "[BluetoothFragment][onActivityResult] result: " + o);
        });
        bluetoothPermissionsActivityLauncher.launch(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN});

        // 扫描设备
        final Button btnScanDevices = binding.btnScanDevice;
        btnScanDevices.setOnClickListener(v -> {
            if (bluetoothAdapter != null) {


                if (ActivityCompat.checkSelfPermission(BluetoothFragment.this.requireContext(), Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Log.e(TAG, "[BluetoothFragment][OnClickListener] checkSelfPermission " + ActivityCompat.checkSelfPermission(BluetoothFragment.this.requireContext(), Manifest.permission.BLUETOOTH_ADMIN) );
//                    return;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        ActivityCompat.requestPermissions(BluetoothFragment.this.requireActivity(), new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 1);
//                    }

//                    ActivityCompat.requestPermissions(BluetoothFragment.this.requireActivity(), new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 1);
                    ActivityCompat.shouldShowRequestPermissionRationale(BluetoothFragment.this.requireActivity(), Manifest.permission.BLUETOOTH_ADMIN);
                }

                bluetoothAdapter.getBluetoothLeScanner().startScan(new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        Log.d(TAG, "[BluetoothFragment][ScanCallback][onScanResult] callbackType: " + callbackType + ", result: " + result);
                    }

                    @Override
                    public void onBatchScanResults(List<ScanResult> results) {
                        Log.d(TAG, "[BluetoothFragment][ScanCallback][onScanResult] results: " + results);
                    }

                    @Override
                    public void onScanFailed(int errorCode) {
                        Log.d(TAG, "[BluetoothFragment][ScanCallback][onScanResult] errorCode: " + errorCode);
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
//        Log.d("MyTag", "[BluetoothFragment][onDestroyView] begin.");
        super.onDestroyView();
        binding = null;
//        Log.d("MyTag", "[BluetoothFragment][onDestroyView] end.");
    }

    @Override
    public void onStart() {
//        Log.d("MyTag", "[BluetoothFragment][onStart] begin.");
        super.onStart();
        ApplicationContext.getInstance().setCurrentFragment(this);
//        Log.d("MyTag", "[BluetoothFragment][onStart] end.");
    }

    @Override
    public void onStop() {
//        Log.d("MyTag", "[BluetoothFragment][onStop] begin.");
        super.onStop();
        ApplicationContext.getInstance().setCurrentFragment(null);
//        Log.d("MyTag", "[BluetoothFragment][onStop] end.");
    }

}
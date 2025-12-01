package houlei.andriod.sfk;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import houlei.andriod.sfk.databinding.ActivityMainBinding;
import houlei.andriod.sfk.ui.settings.SettingsFragment;
import houlei.andriod.sfk.utils.LanguageUtils;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // 界面左上角 唤出菜单的按钮 设置
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_bluetooth,
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // 调出设置界面的代码片段
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavDestination currentDest = navController.getCurrentDestination();
            if (currentDest == null || currentDest.getId() != R.id.nav_settings) {
                navController.navigate(R.id.nav_settings);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // 应用多国语言立即生效的代码片段
        Configuration configuration = newBase.getResources().getConfiguration();
        Locale locale = LanguageUtils.loadLanguage(newBase).getLocale();
        configuration.setLocale(locale);

        super.attachBaseContext(newBase.createConfigurationContext(configuration));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        Fragment fragment = ApplicationContext.getInstance().getCurrentFragment();
        // 设置界面中，点击左上角的退回按钮时，如果设置没有保存则弹窗进行提示
        if (fragment instanceof SettingsFragment && ((SettingsFragment)fragment).settingsChanged()) {
            Log.d("MyTag", "onSupportNavigateUp() Settings Changed");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.settings_dialog_title).setMessage(R.string.settings_dialog_message)
                    .setPositiveButton(R.string.settings_dialog_apply, (dialog, which) -> {
                        ((SettingsFragment)fragment).clickApply();
                        if(!NavigationUI.navigateUp(navController, mAppBarConfiguration)) {
                            MainActivity.super.onSupportNavigateUp();
                        }
                    }).setNegativeButton(R.string.settings_dialog_cancel, (dialog, which) -> {
                        dialog.cancel();
                        if(!NavigationUI.navigateUp(navController, mAppBarConfiguration)) {
                            MainActivity.super.onSupportNavigateUp();
                        }
                    }).create().show();
            return false; // 拦截回退操作
        }

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //TODO 拦截系统按下 返回键 时的操作：1. 退出Settings界面时，未应用/保存的弹窗提示；2.多次返回直到退出程序时的弹窗提示。
        Log.d("MyTag", "onKeyDown: "+ keyCode);
        return super.onKeyDown(keyCode, event);
    }

}
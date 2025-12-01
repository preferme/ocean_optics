package houlei.andriod.sfk.ble;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BleService extends Service {

    public class BleBinder extends Binder {
        public BleService getService() {
            return BleService.this;
        }
    }

    private final IBinder mBinder = new BleBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}

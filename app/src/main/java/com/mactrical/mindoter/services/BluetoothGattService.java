package  com.mactrical.mindoter.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BluetoothGattService extends Service {
    public BluetoothGattService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

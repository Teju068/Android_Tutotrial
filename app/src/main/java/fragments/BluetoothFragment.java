package fragments;

import android.Manifest;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHealth;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
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

import com.mactrical.mindoter.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import services.BluetoothGattService;

import static common.MindoterConstants.REQUEST_ENABLE_BT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BluetoothFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BluetoothFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BluetoothFragment extends Fragment implements View.OnClickListener , AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public  static  final String TAG = "BluetoothFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private BluetoothAdapter mBluetoothAdapter;

    public BluetoothHeadset mBluetoothHeadSet;
    public BluetoothHealth  mBluetoothHealth;

    IntentFilter mIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

    TextView DeviceTxtVw;

    Spinner DeviceListSpnnr;

    BluetoothGattService mBluetoothGattService;


    protected ArrayAdapter<String> mArrayAdapter;
    List<String> list = new ArrayList<String>();

    Handler mDeviceSearchHandler;

    private boolean mScanning;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    private String connectedDeviceAddress;


    public BluetoothFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BluetoothFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BluetoothFragment newInstance() {
        BluetoothFragment fragment = new BluetoothFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View BTFragmentView;

        BTFragmentView =  inflater.inflate(R.layout.fragment_bluetooth, container, false);

        Button mRefreshBtn = (Button)BTFragmentView.findViewById(R.id.Btn_RefreshDeviceList);
        mRefreshBtn.setOnClickListener(this);

        Button mConnectionBtn = (Button)BTFragmentView.findViewById(R.id.Btn_ConnectBTDevice);
        mConnectionBtn.setOnClickListener(this);


        DeviceTxtVw = (TextView)BTFragmentView.findViewById(R.id.TxtVw_DeviceLbl);

        DeviceListSpnnr = (Spinner)BTFragmentView.findViewById(R.id.Spnnr_BTDeviceList);

        mDeviceSearchHandler = new Handler();

        //DeviceListSpnnr.setOnItemClickListener(this);

        return BTFragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address


                if(!CheckIfItemExist(deviceHardwareAddress))
                    list.add(deviceHardwareAddress);

                mArrayAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                DeviceListSpnnr.setAdapter(mArrayAdapter);

            }
        }
    };

    private boolean CheckIfItemExist(String strDeviceAddress){
        for(String strDeviceAddressInList : list){
            if(strDeviceAddressInList.equals(strDeviceAddress)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public  void onStart(){
        super.onStart();

        if(mBluetoothAdapter == null){
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        if(mBluetoothAdapter != null){
            if(!mBluetoothAdapter.isEnabled()){
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            Set<BluetoothDevice> mBTdevices = mBluetoothAdapter.getBondedDevices();
            if(mBTdevices.size() > 0){
                for(BluetoothDevice mBTDevice : mBTdevices) {
                    String deviceName = mBTDevice.getName();
                    String deviceHardwareAddress = mBTDevice.getAddress(); // MAC address

                    Toast.makeText(BluetoothFragment.this.getActivity(),deviceName,Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this.getActivity(),"Your phone doesn't support Bluetooth.",Toast.LENGTH_LONG).show();
        }

        this.getActivity().registerReceiver(receiver,mIntentFilter);

    }



    private BluetoothProfile.ServiceListener mBluetoothProfilelister = new BluetoothProfile.ServiceListener(){

        /**
         * Called to notify the client when the proxy object has been
         * connected to the service.
         *
         */
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if(profile == BluetoothProfile.HEADSET){
                mBluetoothHeadSet = (BluetoothHeadset) proxy;
            }

            if(profile == BluetoothProfile.HEALTH){
                mBluetoothHealth    = (BluetoothHealth)proxy;
            }
        }

        /**
         * Called to notify the client that this proxy object has been
         * disconnected from the service.
         */
        @Override
        public void onServiceDisconnected(int profile) {
            if(profile == BluetoothProfile.HEADSET)
                mBluetoothHeadSet = null;

            if(profile == BluetoothProfile.HEALTH)
                mBluetoothHealth = null;
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        int nBtnID;

        nBtnID = v.getId();
        switch (nBtnID){
            case R.id.Btn_RefreshDeviceList:
                Toast.makeText(this.getActivity(),"Refreshed",Toast.LENGTH_LONG).show();
                //startBTDiscovery();
                startBLEDiscovery(true);
                break;
            case R.id.Btn_ConnectBTDevice:
                Toast.makeText(this.getActivity(), "Connected",Toast.LENGTH_LONG).show();
                if(mBluetoothAdapter.isDiscovering()){
                    mBluetoothAdapter.cancelDiscovery();
                }

                initGattService();
                break;
        }
    }

    private void startBTDiscovery(){
        /**
         * This code provides permission for Bluetooth discovery
         * This code is required only on 6.0 onwards
         */
        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this.getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

        /**
         * This starts the discovery of visible bluetooth
         */
        if(!mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.startDiscovery();
    }


    private void startBLEDiscovery(boolean enable){

        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mDeviceSearchHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    private void initGattService(){
        
        Intent mIntent = new Intent(this.getActivity() , BluetoothGattService.class);
        bindService(mIntent,serviceGattConnection,Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceGattConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothGattService = ((BluetoothGattService.LocalBinder) service).getService();

            mBluetoothGattService.initialize();

            Log.d(TAG,"service Connnected and Initialized");

           // mBluetoothGattService.connectToDevice(connectedDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi,
                                     byte[] scanRecord) {
                       getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!CheckIfItemExist(device.getAddress()))
                                list.add(device.getAddress());
                            connectedDeviceAddress = device.getAddress();
                            mArrayAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            DeviceListSpnnr.setAdapter(mArrayAdapter);
                        }
                    });
                }
            };


    private void connectDevice(){
        if(mBluetoothAdapter != null)
            mBluetoothAdapter.cancelDiscovery();
    }
    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

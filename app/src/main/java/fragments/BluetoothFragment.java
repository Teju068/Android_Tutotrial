package fragments;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHealth;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mactrical.mindoter.R;

import java.util.Set;

import static common.MindoterConstants.REQUEST_ENABLE_BT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BluetoothFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BluetoothFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BluetoothFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private BluetoothAdapter mBluetoothAdapter;

    public BluetoothHeadset mBluetoothHeadSet;
    public BluetoothHealth  mBluetoothHealth;

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
        return inflater.inflate(R.layout.fragment_bluetooth, container, false);
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

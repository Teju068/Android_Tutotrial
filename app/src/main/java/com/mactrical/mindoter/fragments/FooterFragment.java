package com.mactrical.mindoter.fragments;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mactrical.mindoter.R;
import com.tv.common.BaseFragment;

/**
 * A simple {@link BaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FooterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FooterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FooterFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    public FooterFragment() {
        // Required empty public constructor
    }

    public static FooterFragment newInstance() {
        return new FooterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_footer, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

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
    }
}

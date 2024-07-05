package com.example.kolokvijum_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kolokvijum_1.Utils.FragmentTransition;

public class NoviKomentarFragment extends Fragment {

    public NoviKomentarFragment() {
        // Required empty public constructor
    }

    public static NoviKomentarFragment newInstance() {
        return new NoviKomentarFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.novi_komentar, container, false);
        return view;
    }



}

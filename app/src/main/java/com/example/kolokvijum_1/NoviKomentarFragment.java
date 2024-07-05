package com.example.kolokvijum_1;

import static com.example.kolokvijum_1.MainActivity.KEY_USERNAME_SHARED;
import static com.example.kolokvijum_1.MainActivity.PREFS_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kolokvijum_1.Utils.FragmentTransition;
import com.example.kolokvijum_1.database.SQLiteHelper;

import java.util.Objects;

public class NoviKomentarFragment extends Fragment {

    public NoviKomentarFragment() {
        // Required empty public constructor
    }
    private SQLiteHelper sqLiteHelper;

    public static NoviKomentarFragment newInstance() {
        return new NoviKomentarFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.novi_komentar, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username_shared", "default_username");
        System.out.println("Username je"+username);
        sqLiteHelper = new SQLiteHelper(getContext());
        Button btnKomentar = view.findViewById(R.id.btnKomentar);
        EditText txtKomentar = view.findViewById(R.id.txtkomentar);

        btnKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the EditText
                String komentar = txtKomentar.getText().toString();
                // Print the text
                System.out.println("Komentar is: " + komentar);
                sqLiteHelper.insertComment(username,komentar);
                FragmentTransition.to(KomentariFragment.newInstance(), getActivity(), true, R.id.fragmentContainerView);
            }
        });

        return view;
    }







}

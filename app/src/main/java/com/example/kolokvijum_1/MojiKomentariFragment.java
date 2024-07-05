package com.example.kolokvijum_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kolokvijum_1.Utils.FragmentTransition;
import com.example.kolokvijum_1.database.SQLiteHelper;

import java.util.List;

public class MojiKomentariFragment extends Fragment {
    private ListView listView;
    private SQLiteHelper sqLiteHelper;
    private Button dodajButton;
    private Button myComments;

    public MojiKomentariFragment() {
        // Required empty public constructor
    }

    public static MojiKomentariFragment newInstance() {
        return new MojiKomentariFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Entered onCreate");
        // Initialize the database helper here for use later in onCreateView or onViewCreated
        sqLiteHelper = new SQLiteHelper(getContext());





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_komentari, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);  // Ensure this ID matches your ListView ID
        dodajButton = view.findViewById(R.id.dodaj);  // Initialize the button (ensure this ID matches your button ID in the layout)

        // Set the click listener for the button
        dodajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Transition to NoviKomentarFragment
                FragmentTransition.to(NoviKomentarFragment.newInstance(), getActivity(), true, R.id.fragmentContainerView);
            }
        });

        myComments = view.findViewById(R.id.myComments);
        myComments.setBackgroundColor(Color.RED);
        loadComments();
        return view;
    }

    private void loadComments() {

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username_shared", "default_username");
        if (getContext() != null) {
            List<String> comments = sqLiteHelper.getCommentsByUser(username);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, comments);
            listView.setAdapter(adapter);
        }
    }

}

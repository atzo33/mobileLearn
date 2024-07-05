package com.example.kolokvijum_1;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.Manifest;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.kolokvijum_1.Utils.FragmentTransition;
import com.example.kolokvijum_1.database.SQLiteHelper;

import java.util.List;

public class KomentariFragment extends Fragment {
    private ListView listView;
    private SQLiteHelper sqLiteHelper;
    private Button dodajButton;
    private Button myComments;
    private Button camAllow;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    public KomentariFragment() {
        // Required empty public constructor
    }

    public static KomentariFragment newInstance() {
        return new KomentariFragment();
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
        myComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myComments.setBackgroundColor(Color.RED);
                // Transition to NoviKomentarFragment
                FragmentTransition.to(MojiKomentariFragment.newInstance(), getActivity(), true, R.id.fragmentContainerView);
            }
        });

        camAllow = view.findViewById(R.id.camAllow);
        camAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request the CAMERA permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION);
                } else {
                    // Permission has already been granted, you can proceed with the camera functionality
                    Toast.makeText(getActivity(), "Already granted", Toast.LENGTH_SHORT).show();
                }

            }
        });

        loadComments();
        return view;
    }

    private void loadComments() {
        if (getContext() != null) {
            List<String> comments = sqLiteHelper.getAllComments();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, comments);
            listView.setAdapter(adapter);
        }
    }
}

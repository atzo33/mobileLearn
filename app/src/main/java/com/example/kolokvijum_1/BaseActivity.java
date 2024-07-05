package com.example.kolokvijum_1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kolokvijum_1.Utils.FragmentTransition;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base); // Make sure you have a layout file `activity_base.xml`
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((Objects.requireNonNull(item.getTitle())).toString()) {

            case "Komentari":
                TextView textView = findViewById(R.id.textView);
                if (textView != null) {
                    textView.setVisibility(View.GONE);
                }
                FragmentTransition.to(KomentariFragment.newInstance(), BaseActivity.this, true, R.id.fragmentContainerView);
                System.out.println("Uslo u Komentari");


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

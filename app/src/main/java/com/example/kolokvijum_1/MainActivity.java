package com.example.kolokvijum_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kolokvijum_1.database.SQLiteHelper;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    static final String PREFS_NAME = "MyPrefs";
    static final String KEY_USERNAME_SHARED = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(this); // Assuming you instantiate SQLiteHelper here

        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Process the login inside the button click listener
            if (sqLiteHelper.checkUserExists(username, password)) {

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("username_shared", username);
                editor.apply();
                System.out.println("EDITOR JE"+ sharedPreferences.getString("username_shared","LOS STRING"));


                // User exists, handle login success
                Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                startActivity(intent);
                finish();
            } else {
                // User does not exist, handle login failure
                Toast.makeText(MainActivity.this, "Login failed. Incorrect username or password.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
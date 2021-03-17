package io.lewiscodes.interacticestory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import io.lewiscodes.interacticestory.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button startButton;
    private EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        nameField = findViewById(R.id.nameEditText);

        startButton.setOnClickListener((v) -> {
            startStory(nameField.getText().toString());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        nameField.setText("");
    }

    private void startStory(String name) {
        startActivity(new Intent(this, StoryActivity.class).putExtra("name", name));
    }
}

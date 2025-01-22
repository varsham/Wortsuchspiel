package com.example.wortsuchspiel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wortsuchspiel.fragments.EasyFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int[] fragmentIDs = {R.id.easyFragment, R.id.mediumFragment, R.id.hardFragment};
    TextView textView;

    String getLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // initialize elements attached to the fragments
        textView = findViewById(R.id.textView);
        for (int element : fragmentIDs) {
            Button button = findViewById(element);
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Button button = findViewById(id);
        getLevel = button.getText().toString();
        if (getLevel.equals("Easy")) getSupportFragmentManager().beginTransaction().replace(R.id.container, new EasyFragment()).commit();
        // else if (getLevel.equals("Medium")) getSupportFragmentManager().beginTransaction().replace(R.id.container, new MediumFragment()).commit();

        // textView.setText(getLevel);
        textView.setVisibility(View.GONE);
        for (int element : fragmentIDs) {
            Button b = findViewById(element);
            b.setVisibility(View.GONE);
        }
    }

    public String getLevel() {
        return getLevel;
    }


}
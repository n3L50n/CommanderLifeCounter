package com.node_coyote.commanderlifecounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button to start a normal game
        ImageButton normalButton = (ImageButton) findViewById(R.id.start_normal_game);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NormalGameActivity.class);
                startActivity(intent);
            }
        });

        // Find the button to start a commander game
        ImageButton commanderButton = (ImageButton) findViewById(R.id.start_commander_game);
        commanderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CommanderActivity.class);
                startActivity(intent);
            }
        });

    }

}

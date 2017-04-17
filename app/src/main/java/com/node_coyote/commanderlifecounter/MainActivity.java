package com.node_coyote.commanderlifecounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewStub;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewStub poisonEnergyExperienceContainer = (ViewStub) findViewById(R.id.poison_experience_energy_stub);
        poisonEnergyExperienceContainer.inflate();
    }
}

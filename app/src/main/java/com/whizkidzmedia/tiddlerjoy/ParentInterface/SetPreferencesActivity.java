package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whizkidzmedia.tiddlerjoy.R;

public class SetPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_preferences);
    }
}

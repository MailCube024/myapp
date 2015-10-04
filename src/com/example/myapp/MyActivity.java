package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyActivity extends Activity {


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registry);

    }

    public void StartTicTacToe(View view){
        Intent intent = new Intent(this,BoardActivity.class);
        EditText txtPlayerX = (EditText) findViewById(R.id.txtPlayerX);
        String playerXName = txtPlayerX.getText().toString();
        EditText txtPlayerO = (EditText) findViewById(R.id.txtPlayerO);
        String playerOName = txtPlayerO.getText().toString();

        intent.putExtra(Constants.PLAYER_X_NAME,playerXName);
        intent.putExtra(Constants.PLAYER_O_NAME,playerOName);

        startActivity(intent);
    }
}

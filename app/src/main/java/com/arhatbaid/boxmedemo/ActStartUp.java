package com.arhatbaid.boxmedemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ActStartUp extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgMountain = null;
    private Button btnSignUp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_start_up);
        findViews();
    }

    private void findViews() {
        imgMountain = (ImageView) findViewById(R.id.imgMountain);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignUp) {
            Intent i = new Intent(this, ActLogin.class);
            Pair<View, String> p1 = Pair.create((View) imgMountain, getString(R.string.tran_mountain));
            Pair<View, String> p2 = Pair.create((View) btnSignUp, getString(R.string.tran_sign_up));
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, p1, p2);
            startActivity(i, transitionActivityOptions.toBundle());
        }
    }
}

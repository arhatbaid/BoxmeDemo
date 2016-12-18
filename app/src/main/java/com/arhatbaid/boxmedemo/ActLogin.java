package com.arhatbaid.boxmedemo;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arhatbaid.boxmedemo.utils.AppHelper;
import com.arhatbaid.boxmedemo.webapi.model.UserData;
import com.arhatbaid.boxmedemo.webapi.restapi.ApiClient;
import com.arhatbaid.boxmedemo.webapi.restapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActLogin extends AppCompatActivity implements
        View.OnClickListener,
        Transition.TransitionListener {


    private Button btnDone = null;
    private TextInputLayout ipUsername, ipPassword;
    private EditText txtUsername, txtPassword;
    private ProgressDialog progressDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        findViews();
    }

    private void findViews() {
        btnDone = (Button) findViewById(R.id.btnDone);

        ipUsername = (TextInputLayout) findViewById(R.id.ip_username);
        txtUsername = (EditText) findViewById(R.id.txt_username);
//        ipPassword = (TextInputLayout) findViewById(R.id.ip_password);
//        txtPassword = (EditText) findViewById(R.id.txt_password);

        btnDone.setOnClickListener(this);
        final Transition transition = getWindow().getSharedElementEnterTransition();
        transition.addListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDone) {
            if (verify()) {
                if (AppHelper.isNetConnected(this)) {
                    gitLogin();
                } else {
                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onTransitionStart(Transition transition) {
        // No-op
    }

    @Override
    public void onTransitionEnd(Transition transition) {
        // As the transition has ended, we can now load the full-size image
        btnDone.setVisibility(View.VISIBLE);
        AppHelper.animation(btnDone, this, R.anim.fade_in_and_scale_in);

        ipUsername.setVisibility(View.VISIBLE);
        AppHelper.animation(ipUsername, this, R.anim.fade_in);

//        ipPassword.setVisibility(View.VISIBLE);
//        AppHelper.animation(ipPassword, this, R.anim.fade_in);
        // Make sure we remove ourselves as a listener
        transition.removeListener(this);
    }

    @Override
    public void onTransitionCancel(Transition transition) {
        // Make sure we remove ourselves as a listener
        transition.removeListener(this);
    }

    @Override
    public void onTransitionPause(Transition transition) {
        // No-op
    }

    @Override
    public void onTransitionResume(Transition transition) {
        // No-op
    }

    private void gitLogin() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String username = txtUsername.getText().toString().trim();
//        String password = txtPassword.getText().toString().trim();
//        String encode = Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT).replace("\n", "");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserData> call = apiService.getUserDetails(username);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                try {
                    progressDialog.dismiss();
                    UserData userData = response.body();
                    String username = userData.getLogin();
                    Toast.makeText(ActLogin.this, "Welcome back " + username, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ActLogin.this, ActList.class);
                    i.putExtra(ActList.EXTRA_USERDATA, userData);
                    Pair<View, String> p1 = Pair.create(findViewById(R.id.rlBody), getString(R.string.tran_list_body));
                    Pair<View, String> p2 = Pair.create(findViewById(R.id.viewDummy), getString(R.string.tran_toolbar));
                    Pair<View, String> p3 = Pair.create(findViewById(R.id.btnDone), getString(R.string.tran_fab));
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(ActLogin.this, p1, p2, p3);
                    startActivity(i, transitionActivityOptions.toBundle());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActLogin.this, "Something went wrong. Please Try again!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean verify() {
        if (txtUsername.getText().toString().trim().isEmpty()) {
            ipUsername.setError("Enter valid email address ");
            return false;
        }
       /* if (txtPassword.getText().toString().trim().isEmpty()) {
            ipPassword.setError("Enter valid password");
            return false;
        }*/
        return true;
    }

}

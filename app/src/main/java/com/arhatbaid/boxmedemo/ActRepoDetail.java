package com.arhatbaid.boxmedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arhatbaid.boxmedemo.customview.RoundedImageView;
import com.arhatbaid.boxmedemo.utils.AppHelper;
import com.arhatbaid.boxmedemo.webapi.model.RepoData;
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

import static com.arhatbaid.boxmedemo.ActList.EXTRA_USERDATA;

public class ActRepoDetail extends AppCompatActivity implements View.OnClickListener {

    private TextView lblRepoTitle = null, lblRepoDesc = null, lblUserName = null,
            lblStarCount = null, lblWatchCount = null, lblForkCount = null;
    private RoundedImageView imgUser = null;
    private RepoData repoData = null;
    private View viewDummy = null;
    private Button btnBrowser = null;

    private int position = 0;
    public final static String EXTRA_DRAWABLE = "EXTRA_DRAWABLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_repo_detail);
        getIntentData();
        findViews();
        setupView();
    }

    private void setupView() {

        viewDummy.setBackground(getResources().getDrawable(AppHelper.getColorDrawable(position % 5)));
        Picasso.with(this).load(repoData.getOwner().getAvatarUrl()).into(imgUser);
        lblRepoTitle.setText(repoData.getName());
        lblUserName.setText(repoData.getOwner().getLogin());
        lblRepoDesc.setText(repoData.getDescription());
        lblStarCount.setText(repoData.getStargazersCount() + "");
        lblWatchCount.setText(repoData.getWatchersCount() + "");
        lblForkCount.setText(repoData.getForksCount() + "");
    }


    private void findViews() {
        viewDummy = findViewById(R.id.viewDummy);
        lblRepoTitle = (TextView) findViewById(R.id.lblRepoTitle);
        lblRepoDesc = (TextView) findViewById(R.id.lblRepoDesc);
        imgUser = (RoundedImageView) findViewById(R.id.imgUser);
        lblUserName = (TextView) findViewById(R.id.lblUserName);
        lblStarCount = (TextView) findViewById(R.id.lblStarCount);
        lblWatchCount = (TextView) findViewById(R.id.lblWatchCount);
        lblForkCount = (TextView) findViewById(R.id.lblForkCount);
        btnBrowser = (Button) findViewById(R.id.btnBrowser);

        btnBrowser.setOnClickListener(this);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            repoData = bundle.getParcelable(EXTRA_USERDATA);
            position = bundle.getInt(EXTRA_DRAWABLE);
        }
    }

    @Override
    public void onClick(View view) {
        new FinestWebView.Builder(this).show(repoData.getHtmlUrl());
    }
}

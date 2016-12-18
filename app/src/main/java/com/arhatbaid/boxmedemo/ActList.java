package com.arhatbaid.boxmedemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arhatbaid.boxmedemo.adapter.AdapterRepo;
import com.arhatbaid.boxmedemo.utils.AppHelper;
import com.arhatbaid.boxmedemo.webapi.model.RepoData;
import com.arhatbaid.boxmedemo.webapi.model.UserData;
import com.arhatbaid.boxmedemo.webapi.restapi.ApiClient;
import com.arhatbaid.boxmedemo.webapi.restapi.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActList extends AppCompatActivity implements
        Transition.TransitionListener,
        ListView.OnItemClickListener {

    private ListView listRepo = null;
    private RelativeLayout rlBody = null;
    private UserData userData = null;
    private AdapterRepo adapterRepo = null;
    private ProgressBar progressBar = null;

    private ArrayList<RepoData> arrayRepo = new ArrayList<>();
    public final static String EXTRA_USERDATA = "EXTRA_USERDATA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list);
        getIntentData();
        findViews();
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userData = bundle.getParcelable(EXTRA_USERDATA);
        }
    }

    private void findViews() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rlBody = (RelativeLayout) findViewById(R.id.rlBody);

        final Transition transition = getWindow().getSharedElementEnterTransition();
        transition.addListener(this);
    }

    @Override
    public void onTransitionStart(Transition transition) {

    }

    @Override
    public void onTransitionEnd(Transition transition) {
        transition.removeListener(this);
        if (AppHelper.isNetConnected(this)) {
            gitFetchRepoList();
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTransitionCancel(Transition transition) {
        transition.removeListener(this);
    }

    @Override
    public void onTransitionPause(Transition transition) {

    }

    @Override
    public void onTransitionResume(Transition transition) {

    }


    private void gitFetchRepoList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<RepoData>> call = apiService.getReposDetails(userData.getLogin());
        call.enqueue(new Callback<ArrayList<RepoData>>() {
            @Override
            public void onResponse(Call<ArrayList<RepoData>> call, Response<ArrayList<RepoData>> response) {
                arrayRepo.clear();
                arrayRepo.addAll(response.body());
                listRepo = new ListView(ActList.this);
                listRepo.setDivider(null);
                listRepo.setDividerHeight(0);
                setListCellAnimation();
                rlBody.addView(listRepo);
                listRepo.setOnItemClickListener(ActList.this);

                adapterRepo = new AdapterRepo(ActList.this, 0, arrayRepo);
                listRepo.setAdapter(adapterRepo);
                progressBar.setVisibility(View.GONE);
                adapterRepo.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<RepoData>> call, Throwable t) {
                Toast.makeText(ActList.this, "Something went wrong. Please Try again!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListCellAnimation() {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new TranslateAnimation(0, 0, 800, 0);
        animation.setDuration(400);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.7f);

        listRepo.setLayoutAnimation(controller);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        Intent i = new Intent(ActList.this, ActRepoDetail.class);
        i.putExtra(ActList.EXTRA_USERDATA, arrayRepo.get(pos));
        i.putExtra(ActRepoDetail.EXTRA_DRAWABLE, pos);
        Pair<View, String> p1 = Pair.create(view, getString(R.string.tran_view));
        Pair<View, String> p2 = Pair.create(view.findViewById(R.id.viewDummy), getString(R.string.tran_dummy_view));
        Pair<View, String> p3 = Pair.create(view.findViewById(R.id.lblRepoTitle), getString(R.string.tran_title));
        Pair<View, String> p4 = Pair.create(view.findViewById(R.id.lblRepoDesc), getString(R.string.tran_decs));
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(ActList.this, p1, p2, p3, p4);
        startActivity(i, transitionActivityOptions.toBundle());
    }

    @Override
    public void onBackPressed() {
        rlBody.removeView(listRepo);
        super.onBackPressed();
    }
}

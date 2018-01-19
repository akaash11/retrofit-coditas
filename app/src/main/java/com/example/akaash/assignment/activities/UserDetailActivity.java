package com.example.akaash.assignment.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akaash.assignment.R;
import com.example.akaash.assignment.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbatTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String login;
    private String id;
    private String avatarUrl;
    private String url;
    private String followerList;
    private String followingList;
    private String subscripitionsUrl;
    private String organizationUrl;
    private String type;
    private String score;

/*    public UserDetailActivity(String login, String id, String avatarUrl, String url, String followerList, String followingList, String subscripitionsUrl, String organizationUrl, String type, String score) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.url = url;
        this.followerList = followerList;
        this.followingList = followingList;
        this.subscripitionsUrl = subscripitionsUrl;
        this.organizationUrl = organizationUrl;
        this.type = type;
        this.score = score;
    }*/
    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.ivUserImage)
    ImageView ivUserImage;

    @BindView(R.id.tvUserUrl)
    TextView tvUserUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        initToolbar();
        User user= (User) getIntent().getSerializableExtra("profile");
        tvUserName.setText(user.getLogin());
        tvUserUrl.setText(user.getUrl());


        Picasso.with(this).load(user.getAvatarUrl()).into(ivUserImage);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        tvToolbatTitle.setText(getString(R.string.toolbar_title_user_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


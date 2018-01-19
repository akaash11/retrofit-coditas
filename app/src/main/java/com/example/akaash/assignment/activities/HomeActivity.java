package com.example.akaash.assignment.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.akaash.assignment.R;
import com.example.akaash.assignment.adapters.ProfileAdapter;
import com.example.akaash.assignment.api.ApiClient;
import com.example.akaash.assignment.api.ApiInterface;
import com.example.akaash.assignment.model.User;
import com.example.akaash.assignment.model.UserResponse;
import com.example.akaash.assignment.utils.CommonUtil;
import com.example.akaash.assignment.utils.SnackBarUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.rvHome)
    RecyclerView rvHome;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    @BindView(R.id.tvProfileCount)
    TextView tvProfileCount;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.llParent)
    LinearLayout llParent;
//    @BindView(R.id.search_bar)
//    SearchView searchView;

    @BindView(R.id.search_button)
    ImageView ivSearchIcon;

    @BindView(R.id.etSearchFrame)
    EditText etSearchFrame;

    ProfileAdapter profileAdapter;
    static final List<User> profileList = new ArrayList<>();
    private static final String TAG = HomeActivity.class.getSimpleName();
    private ApiInterface apiServise;
    List<User> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initToolbar();
        if (isNetworkAvailable()) {
            initRecyclerView();
        } else {
            SnackBarUtils.showSnackBar(rvHome, "No Internet Connection");
            showCustomDialogBox();
        }
        setUpSort(profileList);

    }

    private void showCustomDialogBox() {
/*        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View promtView=layoutInflater.inflate(R.layout.custom_dialog_box,null);

        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promtView);*/

        Dialog customDialogBox = new Dialog(this);
        customDialogBox.setContentView(R.layout.custom_dialog_box);
        customDialogBox.setTitle("Warning");

        TextView tvDialogMsg = customDialogBox.findViewById(R.id.tvDialogMsg);
        Button btnTryAgain = customDialogBox.findViewById(R.id.btnTryAgain);
        Button btnCancel = customDialogBox.findViewById(R.id.btnCancel);

        tvDialogMsg.setText("No Internet connection\n please connect to internet");

        customDialogBox.show();
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    initRecyclerView();
                    customDialogBox.dismiss();
                } else {
                    SnackBarUtils.showSnackBar(rvHome, "No Internet Connection");
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogBox.dismiss();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    private void requestUsers() {
        progressBar.setVisibility(View.VISIBLE);
        apiServise = ApiClient.getCLient().create(ApiInterface.class);
        Call<List<User>> call = apiServise.getGitHubUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (null != response) {
                    List<User> responseData = response.body();
                    if (null != responseData && responseData.size() > 0) {
                        tvProfileCount.setText(getString(R.string.total_result_, responseData.size()));
                        profileList.addAll(responseData);
                        profileAdapter.notifyDataSetChanged();
                        setUpSort(profileList);
                        progressBar.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

    private void initRecyclerView() {
        rvHome.setLayoutManager(new LinearLayoutManager(this));
        //Hardcoded data
        /*profileCount = 112;
        for (int i = 0; i <= profileCount; i++) {
            profileList.add(new Profile("A" + i, "http://coditas.com/" + i, "Pune :4110" + i, "India"));
        }
        tvProfileCount.setText("Total result: " + profileCount);*/

        profileAdapter = new ProfileAdapter(profileList, new ProfileAdapter.Listener() {
            @Override
            public void onClickProfile(User profile) {
                Intent intent = new Intent(HomeActivity.this, UserDetailActivity.class);
                //new UserDetailActivity(profile.getLogin(),profile.getId(),profile.getAvatarUrl(),profile.getUrl(),profile.getFollowerUrl(),profile.getFollowingUrl(),profile.getSubscripitionsUrl(),profile.getOrganizationsUrl(),profile.getType(),profile.getScore());
                intent.putExtra("profile",profile);
                startActivity(intent);
            }
        });
        rvHome.setAdapter(profileAdapter);
        requestUsers();
    }

    private void setUpSort(List sortList) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        sortById(sortList);
                        break;
                    case 1:
                        sortByName(sortList);
                        break;
                }
                profileAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void sortById(List sortList) {
        Collections.sort(sortList, new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                return Integer.compare(Integer.parseInt(user.getId()), Integer.parseInt(t1.getId()));
            }
        });
    }

    private void sortByName(List sortList) {
        Collections.sort(sortList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getLogin().toLowerCase().compareTo(o2.getLogin().toLowerCase());
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        tvToolbarTitle.setText("Home");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @OnClick(R.id.search_button)
    public void onClickSearchIcon() {

        etSearchFrame.setVisibility(View.VISIBLE);
        ivSearchIcon.setVisibility(View.GONE);

        etSearchFrame.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    rvHome.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    callSearchApi(etSearchFrame.getText().toString());
                    CommonUtil.hideKeyboard(HomeActivity.this);
                    return true;
                }
                return false;
            }
        });
//search in given list(30 items)
/*
        etSearchFrame.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                charSequence = charSequence.toString().toLowerCase();
                final List<User> tempList = new ArrayList<>();
                for (int a = 0; a < profileList.size(); a++) {
                    final String text = profileList.get(a).getLogin().toLowerCase();
                    if (text.contains(charSequence)) {
                        tempList.add(profileList.get(a));
                    }
                }
                rvHome.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                profileAdapter = new ProfileAdapter(tempList, HomeActivity.this);
                rvHome.setAdapter(profileAdapter);
                profileAdapter.notifyDataSetChanged();
            }

        });
*/

    }

    private void callSearchApi(String keyword) {
        apiServise = ApiClient.getCLient().create(ApiInterface.class);
        Call<UserResponse> call = apiServise.getSearchResult(keyword);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                tempList = new ArrayList<>();
                if (null != response) {
                    UserResponse data = response.body();
                    if (null != data && data.getUsers().size() > 0) {
                        tvProfileCount.setText(getString(R.string.total_result_, data.getTotalCount()));
                        tempList.addAll(profileList);
                        profileAdapter.updateList(data.getUsers());
                        rvHome.setAdapter(profileAdapter);
                        progressBar.setVisibility(View.GONE);
                        rvHome.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (null != tempList && tempList.size() > 0) {
            etSearchFrame.setVisibility(View.GONE);
            ivSearchIcon.setVisibility(View.VISIBLE);
            profileAdapter.updateList(tempList);
            tempList.clear();
        } else {
            super.onBackPressed();
        }

    }
}

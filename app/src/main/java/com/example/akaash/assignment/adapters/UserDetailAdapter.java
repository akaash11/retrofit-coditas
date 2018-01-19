package com.example.akaash.assignment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.akaash.assignment.R;
import com.example.akaash.assignment.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akaash on 15/1/18.
 */

public class UserDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int USERDETAILONE = 1, USERDETAILTWO = 2;
    private List<Object> items;

    public UserDetailAdapter(List<Object> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case USERDETAILONE:
                View userDetailOneView = layoutInflater.inflate(R.layout.list_items_user_details_1, parent, false);
                viewHolder = new UserProfileOneViewHolder(userDetailOneView);
            case USERDETAILTWO:
                View userdetailTwoView =layoutInflater.inflate(R.layout.list_items_user_details_2,parent,false);
                viewHolder=new UserProfileTwoViewHolder(userdetailTwoView);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof User) {
            return USERDETAILONE;
        }
        else if(items.get(position)instanceof User) {
            return USERDETAILTWO;
        }
        return -1;
    }
        @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case USERDETAILONE:
                UserProfileOneViewHolder userProfileOneViewHolder= (UserProfileOneViewHolder) holder;
                configureUserProfileViewHolder(userProfileOneViewHolder,position);
                break;
            case USERDETAILTWO:
                UserProfileTwoViewHolder userProfileTwoViewHolder= (UserProfileTwoViewHolder) holder;
                configureUserprofileViewHolder(userProfileTwoViewHolder,position);
                break;
        }
    }

    private void configureUserprofileViewHolder(UserProfileTwoViewHolder userProfileTwoViewHolder, int position) {
        User itemsProfile= (User) items.get(position);
        userProfileTwoViewHolder.tvDataValueOne.setText("Following: "+itemsProfile.getFollowingUrl());
        userProfileTwoViewHolder.tvDataValueTwo.setText("Followers: "+itemsProfile.getFollowerUrl());
        userProfileTwoViewHolder.tvDataValueThree.setText("Events: "+itemsProfile.getEventsUrl());
        userProfileTwoViewHolder.tvDataValueFour.setText("URL: "+itemsProfile.getUrl());
        userProfileTwoViewHolder.tvDataValueFive.setText("SubscripitionsUrl: "+itemsProfile.getSubscripitionsUrl());
        userProfileTwoViewHolder.tvDataValueSix.setText("Type: "+itemsProfile.getType());

    }

    private void configureUserProfileViewHolder(UserProfileOneViewHolder userProfileOneViewHolder, int position) {
        User itemsProfile = (User) items.get(position);
        userProfileOneViewHolder.tvUserName.setText("Name: "+itemsProfile.getLogin());
        userProfileOneViewHolder.tvUserUrl.setText("URL: "+itemsProfile.getAvatarUrl());
    }

    @Override
    public int getItemCount() {
        int count=0;
        if(null!=items){
            count=items.size();
        }
        return count;
    }

    public class UserProfileOneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rlUserParentDetail)
        RelativeLayout rlUserParentDetail;

        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.tvUserUrl)
        TextView tvUserUrl;

        public UserProfileOneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public class UserProfileTwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDataLableOne)
        TextView tvDataLableOne;

        @BindView(R.id.tvDataValueOne)
        TextView tvDataValueOne;

        @BindView(R.id.tvDataLableTwo)
        TextView tvDataLableTwo;

        @BindView(R.id.tvDataValueTwo)
        TextView tvDataValueTwo;

        @BindView(R.id.tvDataLableThree)
        TextView tvDataLableThree;

        @BindView(R.id.tvDataValueThree)
        TextView tvDataValueThree;

        @BindView(R.id.tvDataLableFour)
        TextView tvDataLableFour;

        @BindView(R.id.tvDataValueFour)
        TextView tvDataValueFour;

        @BindView(R.id.tvDataLableFive)
        TextView tvDataLableFive;

        @BindView(R.id.tvDataValueFive)
        TextView tvDataValueFive;

        @BindView(R.id.tvDataLableSix)
        TextView tvDataLableSix;

        @BindView(R.id.tvDataValueSix)
        TextView tvDataValueSix;

        public UserProfileTwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

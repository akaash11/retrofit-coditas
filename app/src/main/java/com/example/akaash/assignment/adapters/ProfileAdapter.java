package com.example.akaash.assignment.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akaash.assignment.R;
import com.example.akaash.assignment.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akaash on 12/1/18.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    public ProfileAdapter(List<User> profileList, Context context) {
        this.profileList = profileList;
        this.context = context;
    }

    private List<User> profileList;
    private Context context;

    public ProfileAdapter(List<User> profileList, Listener listener) {
        this.profileList = profileList;
        this.listener = listener;
    }

    private Listener listener;

    public interface Listener {
        void onClickProfile(User profile);
    }


    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ProfileViewHolder viewHolder = new ProfileViewHolder(inflater.inflate(R.layout.list_item_profile, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        User itemsProfile = profileList.get(position);

        if (null != itemsProfile.getAvatarUrl()) {
            Picasso.with(context).load(itemsProfile.getAvatarUrl()).into(holder.ivProfileIcon);
        }

        holder.tvProfileName.setText("Name: " + itemsProfile.getLogin());
        holder.tvProfileUrl.setText("URL: " + itemsProfile.getUrl());
        holder.tvDataOne.setText("ID: " + itemsProfile.getId());
        if (itemsProfile.isSiteAdmin()) {
            holder.tvDataTwo.setText("Admin");
        } else {
            holder.tvDataTwo.setText("Not admin");
        }


        //holder.ivProfileIcon.setImageResource(itemsProfile.getProfileImage());
    }

    public void updateList(List<User> newList) {
        if (null != profileList && null != newList) {
            profileList.clear();
            profileList.addAll(newList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (profileList != null) {
            count = profileList.size();
        }
        return count;
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cvProfoileCard)
        CardView cvProfoileCard;

        @BindView(R.id.tvName)
        TextView tvProfileName;

        @BindView(R.id.tvProfileURL)
        TextView tvProfileUrl;

        @BindView(R.id.tvDataone)
        TextView tvDataOne;

        @BindView(R.id.tvDatatwo)
        TextView tvDataTwo;

        @BindView(R.id.ivProfileIcon)
        ImageView ivProfileIcon;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cvProfoileCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == cvProfoileCard) {
                if (listener != null && profileList.get(getAdapterPosition()) instanceof User) {
                    listener.onClickProfile(profileList.get(getAdapterPosition()));
                }
            }
        }
    }
}

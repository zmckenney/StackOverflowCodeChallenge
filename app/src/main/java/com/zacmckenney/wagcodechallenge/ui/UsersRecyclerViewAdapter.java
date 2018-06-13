package com.zacmckenney.wagcodechallenge.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zacmckenney.wagcodechallenge.GlideApp;
import com.zacmckenney.wagcodechallenge.R;
import com.zacmckenney.wagcodechallenge.model.UserModel;

import java.util.Collections;
import java.util.List;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.UserViewHolder> {

    /**
     * ViewHolder to create and bind views in recyclerview
     */
    class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView userName;
        private TextView goldBadges;
        private TextView silverBadges;
        private TextView bronzeBadges;
        private CircularProgressDrawable imageLoadingProgressBar;

        private Context context;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            thumbnail = itemView.findViewById(R.id.user_thumbnail);
            userName = itemView.findViewById(R.id.user_name);
            goldBadges = itemView.findViewById(R.id.gold_data);
            silverBadges = itemView.findViewById(R.id.silver_data);
            bronzeBadges = itemView.findViewById(R.id.bronze_data);

            imageLoadingProgressBar = new CircularProgressDrawable(itemView.getContext());
            imageLoadingProgressBar.setStrokeWidth(5f);
            imageLoadingProgressBar.setCenterRadius(30f);
            imageLoadingProgressBar.start();
        }

        public void bind(UserModel user) {
            if (user != null) {
                userName.setText(user.getDisplayName());
                goldBadges.setText(user.getBadgeModel().getGoldString());
                silverBadges.setText(user.getBadgeModel().getSilverString());
                bronzeBadges.setText(user.getBadgeModel().getBronzeString());

                //Glide for loading and storing images - NOTE: Glide does store for offline access but default diskCacheStrategy is AUTOMATIC instead of all to save on expensive downloading (and instead resizes current)
                GlideApp.with(context)
                        .load(user.getThumbnailUrl())
                        .placeholder(imageLoadingProgressBar)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Could use this instead but not necessary
                        .error(R.drawable.ic_launcher_foreground)
                        .centerInside()
                        .into(thumbnail);

                final UserModel userFinal = user;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userFinal.getLink()));
                        view.getContext().startActivity(intent);
                    }
                });
            } else {
                //Show something else
                Log.e("emptyUser", "Empty User in Adapter");
            }
        }

    }

    private List<UserModel> userModels = Collections.emptyList();

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_item, viewGroup, false);
        return new UserViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        if (!userModels.isEmpty() && i < userModels.size()) {
            userViewHolder.bind(userModels.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }


    //Set the data for Users
    public void setUserData(List<UserModel> users) {
        this.userModels = users;
        notifyDataSetChanged();
    }


}

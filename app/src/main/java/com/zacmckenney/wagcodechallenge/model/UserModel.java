package com.zacmckenney.wagcodechallenge.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public class UserModel {

    public UserModel(){}

    @PrimaryKey
    @SerializedName("user_id")
    private int userId;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("profile_image")
    private String thumbnailUrl;

    @SerializedName("link")
    private String link;

    @SerializedName("badge_counts")
    @Embedded
    private BadgeResponse badgeResponse;



    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BadgeResponse getBadgeResponse() {
        return badgeResponse;
    }

    public void setBadgeResponse(BadgeResponse badgeResponse) {
        this.badgeResponse = badgeResponse;
    }
}

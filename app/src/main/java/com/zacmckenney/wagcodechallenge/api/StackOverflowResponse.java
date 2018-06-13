package com.zacmckenney.wagcodechallenge.api;

import com.google.gson.annotations.SerializedName;
import com.zacmckenney.wagcodechallenge.model.UserModel;

import java.util.Collections;
import java.util.List;


public class StackOverflowResponse {

    @SerializedName("items")
    private List<UserModel> users = Collections.emptyList();

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public List<UserModel> getUsers() {
        return users;
    }

}

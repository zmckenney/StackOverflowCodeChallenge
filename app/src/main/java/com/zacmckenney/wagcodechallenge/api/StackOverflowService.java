package com.zacmckenney.wagcodechallenge.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StackOverflowService {
    //Get Users from Stack Overflow
    //FIXME use a Query annotation instead
    @GET("/2.2/users?site=stackoverflow")
    Call<StackOverflowResponse> getUsers();
}

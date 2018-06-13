package com.zacmckenney.wagcodechallenge.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * OKhttp and Retrofit setup and functions
 */
public class StackOverflowApi {

    private StackOverflowApi() {
        //Dont instantiate
    }


    public static StackOverflowService createService() {

        final String BASE_URL = "https://api.stackexchange.com/";

        //Create a logging for our HTTP requests
        HttpLoggingInterceptor httpLogger = new HttpLoggingInterceptor();
        httpLogger.setLevel(HttpLoggingInterceptor.Level.BASIC);

        //Create the client for the http requests
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLogger)
                .build();

        //Create the retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // return the StackOverflowService
        return retrofit.create(StackOverflowService.class);
    }

}

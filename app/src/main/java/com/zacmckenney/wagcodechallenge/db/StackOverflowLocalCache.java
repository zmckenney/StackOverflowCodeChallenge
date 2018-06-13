package com.zacmckenney.wagcodechallenge.db;

import android.util.Log;

import com.zacmckenney.wagcodechallenge.api.StackOverflowResponse;
import com.zacmckenney.wagcodechallenge.api.StackOverflowService;
import com.zacmckenney.wagcodechallenge.model.UserModel;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class StackOverflowLocalCache {

    /**
     * Inserts the users into the database
     * @param users List of UserModels
     * @param usersDb db to enter data into
     * @param ioExecutor single thread to run all db transactions
     */
    private static void insertUsersIntoDb(final List<UserModel> users, final UsersDb usersDb, Executor ioExecutor) {
        if (!users.isEmpty()) {
            Log.d("StackOverflowLocalCache", "Inserting User from response : " + users.get(users.size() - 1).getDisplayName());
            ioExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    usersDb.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            usersDb.userDao().insertUsers(users);
                        }
                    });
                }
            });
        }
    }

    public static void getUsersFromApi(final UsersDb db, StackOverflowService apiService, final Executor ioExecutor) {
        Call<StackOverflowResponse> users = apiService.getUsers();
        users.enqueue(new Callback<StackOverflowResponse>() {
            @Override
            public void onResponse(Call<StackOverflowResponse> call, Response<StackOverflowResponse> response) {

                if (response.body() != null) {
                    List<UserModel> userList = response.body().getUsers();

                    StackOverflowLocalCache.insertUsersIntoDb(userList, db, ioExecutor);
                }
            }

            @Override
            public void onFailure(Call<StackOverflowResponse> call, Throwable t) {
                Log.e("responseFailure", "Stack Overflow error : " + t.getMessage());
            }
        });
    }


    public static void deleteAllUsers(final UsersDb usersDb, Executor ioExecutor) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                usersDb.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        usersDb.userDao().deleteAll();
                    }
                });
            }
        });
    }


    public static void insertSingleUser(final UserModel user, final UsersDb usersDb, Executor ioExecutor) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                usersDb.userDao().insertSingleUser(user);
            }
        });
    }

}

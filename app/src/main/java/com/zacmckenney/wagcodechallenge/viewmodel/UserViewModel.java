package com.zacmckenney.wagcodechallenge.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.zacmckenney.wagcodechallenge.model.BadgeResponse;
import com.zacmckenney.wagcodechallenge.api.StackOverflowApi;
import com.zacmckenney.wagcodechallenge.api.StackOverflowService;
import com.zacmckenney.wagcodechallenge.db.StackOverflowLocalCache;
import com.zacmckenney.wagcodechallenge.db.UsersDb;
import com.zacmckenney.wagcodechallenge.model.UserModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserViewModel extends AndroidViewModel {
    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    private LiveData<List<UserModel>> usersResult;

    //What is observed for the database - A PagedListAdapter could easily use this
    public LiveData<List<UserModel>> getUsersResult() {
        return usersResult;
    }

    //ioExecutor to run db in background
    private Executor ioExecutor = Executors.newSingleThreadExecutor();

    private UsersDb usersDb;
    private StackOverflowService api;

    public void createDb() {
        // init the db
        usersDb = UsersDb.getInstance(getApplication());

        //init the StackOverflowService
        api = StackOverflowApi.createService();

        queryUsers();

        subscribeToDbChange();
    }

    public void queryUsers() {
        StackOverflowLocalCache.getUsersFromApi(usersDb, api, ioExecutor);
    }

    private void subscribeToDbChange() {
        usersResult = usersDb.userDao().findAllUsers();
    }

    //Just some test data thats easily implemented and good for debugging
    public void testData() {
        BadgeResponse badges = new BadgeResponse();
        badges.setBronze(1234);
        badges.setGold(1234);
        badges.setSilver(1235);

        UserModel test = new UserModel();
        test.setDisplayName("testing");
        test.setBadgeResponse(badges);
        test.setThumbnailUrl("https://blasdf");
        test.setUserId(4124123);

        StackOverflowLocalCache.insertSingleUser(test, usersDb, ioExecutor);
    }

    //Delete the users then reget (on same ioExecutor)
    public void refresh() {
        StackOverflowLocalCache.deleteAllUsers(usersDb, ioExecutor);

        StackOverflowLocalCache.getUsersFromApi(usersDb, api, ioExecutor);
    }

}

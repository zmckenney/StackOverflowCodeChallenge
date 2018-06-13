package com.zacmckenney.wagcodechallenge;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zacmckenney.wagcodechallenge.db.UsersDb;
import com.zacmckenney.wagcodechallenge.model.UserModel;
import com.zacmckenney.wagcodechallenge.ui.UsersRecyclerViewAdapter;
import com.zacmckenney.wagcodechallenge.viewmodel.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private UsersRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.createDb();

        adapter = new UsersRecyclerViewAdapter();
        RecyclerView recyclerView = findViewById(R.id.user_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        subscribeUiUsers();

        setFloatingButtonClickListener();
    }

    private void subscribeUiUsers() {
        userViewModel.getUsersResult().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(@Nullable List<UserModel> userModels) {
                adapter.setUserData(userModels);
            }
        });
    }

    private void setFloatingButtonClickListener() {
        FloatingActionButton fab = findViewById(R.id.refresh_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.refresh();
            }
        });
    }

    @Override
    protected void onDestroy() {
        UsersDb.destroyInstance();
        super.onDestroy();
    }
}

package com.zacmckenney.wagcodechallenge.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zacmckenney.wagcodechallenge.model.UserModel;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    LiveData<List<UserModel>> findAllUsers();

    @Query("DELETE FROM users")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<UserModel> user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSingleUser(UserModel user);
}

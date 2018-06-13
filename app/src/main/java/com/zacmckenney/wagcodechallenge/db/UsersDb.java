package com.zacmckenney.wagcodechallenge.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.zacmckenney.wagcodechallenge.dao.UserDao;
import com.zacmckenney.wagcodechallenge.model.UserModel;


@Database(entities = {UserModel.class},
version = 1)
public abstract class UsersDb extends RoomDatabase {

    public abstract UserDao userDao();
    private static UsersDb INSTANCE = null;

    //Singleton Pattern
    public static UsersDb getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private static UsersDb buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                UsersDb.class, "StackOverflowUsers.db")
                .fallbackToDestructiveMigration()
                .build();
        }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}

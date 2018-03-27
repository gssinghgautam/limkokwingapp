package com.android.limkokwingapp.data.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.android.limkokwingapp.data.dao.UserDao;
import com.android.limkokwingapp.data.entity.User;

/**
 * Created by gautam on 25/03/18.
 */

@Database(entities = {User.class}, version = LimkokWingDatabase.VERSION, exportSchema = false)
public abstract class LimkokWingDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract UserDao userDao();
}

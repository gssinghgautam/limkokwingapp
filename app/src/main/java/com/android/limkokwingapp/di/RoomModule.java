package com.android.limkokwingapp.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.android.limkokwingapp.data.dao.UserDao;
import com.android.limkokwingapp.data.repository.local.LimkokWingDatabase;
import com.android.limkokwingapp.data.repository.local.UserDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gautam on 26/03/18.
 */

@Module(includes = ActivityBuilderModule.class)
public class RoomModule {

    @AppScope
    @Provides
    LimkokWingDatabase provideLimkokWingDatabase(Application application) {
        return Room.databaseBuilder(application, LimkokWingDatabase.class, "myapp.db").fallbackToDestructiveMigration().build();
    }

    @AppScope
    @Provides
    UserDao provideUserDao(LimkokWingDatabase mDatabase) {
        return mDatabase.userDao();
    }


    @AppScope
    @Provides
    UserDataSource provideUserDataSource(UserDao userDao) {
        return new UserDataSource(userDao);
    }

}

package com.elegion.test.behancer.di;

import android.arch.persistence.room.Room;

import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.database.BehanceDatabase;
import com.elegion.test.behancer.utils.PicassoLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanchuev on 23.04.2018.
 * ready for Custom Scope new branch
 */

@Module
public class AppModule {

    private final AppDelegate mApp;

    public AppModule(AppDelegate mApp) {
        this.mApp = mApp;
    }

    @Provides
    @Singleton
    AppDelegate provideApp() {
        return mApp;
    }

    @Provides
    @Singleton
    Storage provideStorage() {
        final BehanceDatabase database = Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        return new Storage(database.getBehanceDao());
    }
    // Picasso Loader нужен для отображения  userpic в ProfileFragment
    @Provides
    @Singleton
    PicassoLoader providePicassoLoader(){
        return new PicassoLoader();
    }
}

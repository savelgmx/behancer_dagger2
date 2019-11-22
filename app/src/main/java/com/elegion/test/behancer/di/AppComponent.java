package com.elegion.test.behancer.di;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.utils.PicassoLoader;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    Storage provideStorage();
    BehanceApi provideApiService();
    PicassoLoader providePicassoLoader();

    void inject(ProjectsFragment injector);
  //  void inject(ProfileFragment injector);
}

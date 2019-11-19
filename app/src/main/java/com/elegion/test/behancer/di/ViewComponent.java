package com.elegion.test.behancer.di;

import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.profile.ProfileView;

import dagger.Component;


@PerFragmentScope
@Component(dependencies = AppComponent.class, modules = {ViewModule.class})
public interface ViewComponent {
   // ProfileView provideView();

    void inject(ProfileFragment injector);
}

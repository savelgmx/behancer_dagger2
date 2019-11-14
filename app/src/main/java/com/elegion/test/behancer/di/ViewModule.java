package com.elegion.test.behancer.di;
import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.ui.profile.ProfileView;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {
    private final BaseView mBaseView;

    public ViewModule(BaseView mBaseView) {
        this.mBaseView = mBaseView;
    }

    @Provides
    @PerFragmentScope
    ProfileView provideView() {
        return (ProfileView)mBaseView;
    }
}

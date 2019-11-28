package com.elegion.test.behancer.ui.profile;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.ui.projects.ProjectsView;
import com.elegion.test.behancer.utils.ApiUtils;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    @Inject
    ProfileView mView;
    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;


    @Inject
    public ProfilePresenter(){

    }
    public void setView(ProfileView view){
        mView = view;
    }


    public void getProfile(@NonNull String username){
        Log.d("behancer_dagger2","username: "+username);
        mCompositeDisposable.add(
                mApi.getUserInfo(username)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                        mStorage.getUser(username) : null)
                .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> mView.showRefresh())
                        .doFinally(mView::hideRefresh)
                .subscribe(
                                response -> mView.showProfile(response.getUser()),
                                throwable -> mView.showError()));
    }

}

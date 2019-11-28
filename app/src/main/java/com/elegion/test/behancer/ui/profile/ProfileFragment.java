package com.elegion.test.behancer.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.R;

import com.elegion.test.behancer.common.PresenterFragment;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.di.DaggerViewComponent;
import com.elegion.test.behancer.di.ViewModule;
import com.elegion.test.behancer.utils.DateUtils;
import com.elegion.test.behancer.utils.PicassoLoader;


import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Vladislav Falzan.
 */

public class ProfileFragment extends PresenterFragment<ProfilePresenter> implements Refreshable,ProfileView {
//public class ProfileFragment extends PresenterFragment implements Refreshable,ProfileView {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    @Inject
    PicassoLoader mPicassoLoader;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mProfileView;
    private String mUsername;
    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileCreatedOn;
    private TextView mProfileLocation;

    @InjectPresenter
    ProfilePresenter mProfilePresenter;

    @ProvidePresenter
    ProfilePresenter providePresenter(){return new ProfilePresenter();}

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRefreshOwner = context instanceof RefreshOwner ? (RefreshOwner) context : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mErrorView = view.findViewById(R.id.errorView);
        mProfileView = view.findViewById(R.id.view_profile);

        mProfileImage = view.findViewById(R.id.iv_profile);
        mProfileName = view.findViewById(R.id.tv_display_name_details);
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        mProfileLocation = view.findViewById(R.id.tv_location_details);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mUsername = getArguments().getString(PROFILE_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle(mUsername);
        }

        DaggerViewComponent
                .builder()
                .appComponent(AppDelegate.getAppComponent())
                .viewModule(new ViewModule(this))
                .build()
                .inject(this);
        mProfileView.setVisibility(View.VISIBLE);


        onRefreshData();
    }

    @Override
    protected ProfilePresenter getPresenter() {
        return mProfilePresenter;
    }
    @Override
    public void onRefreshData() {
        mProfilePresenter.getProfile(mUsername);
    }


    @Override
    public void showProfile(@NonNull User user) {
        mErrorView.setVisibility(View.GONE);
        mProfileView.setVisibility(View.VISIBLE);


        /*Picasso.with(getContext())
                .load(user.getImage().getPhotoUrl())
                .fit()
                .into(mProfileImage);*/

        mPicassoLoader.load(getContext(), user.getImage().getPhotoUrl(), mProfileImage);
        mProfileName.setText(user.getDisplayName());
        mProfileCreatedOn.setText(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.setText(user.getLocation());
    }
    @Override
    public void showRefresh() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshOwner.setRefreshState(false);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mProfileView.setVisibility(View.GONE);
    }
    @Override
    public void onDetach() {
         mRefreshOwner = null;
        super.onDetach();
    }


}

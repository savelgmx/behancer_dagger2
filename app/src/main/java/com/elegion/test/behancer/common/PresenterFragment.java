package com.elegion.test.behancer.common;

import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;

/**
 * Created by Vladislav Falzan.
 */

public abstract class PresenterFragment<P extends BasePresenter> extends MvpAppCompatFragment {

    protected abstract P getPresenter();

    @Override
    public void onDetach() {
        if (getPresenter() != null) {
            getPresenter().disposeAll();
        }
        super.onDetach();
    }
}
/*
package com.elegion.test.behancer.common;

import com.arellomobile.mvp.MvpAppCompatFragment;

/**
 * Created by andrew on 07.06.2019.



public abstract class PresenterFragment extends MvpAppCompatFragment {

    protected abstract BasePresenter getPresenter();

    @Override
    public void onDetach() {
        if (getPresenter() != null) {
            getPresenter().disposeAll();
        }
        super.onDetach();
    }
}
 */
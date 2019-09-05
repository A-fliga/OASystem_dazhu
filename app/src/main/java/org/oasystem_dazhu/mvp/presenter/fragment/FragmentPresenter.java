package org.oasystem_dazhu.mvp.presenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


import org.oasystem_dazhu.mvp.presenter.IPresenter;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

import java.io.Serializable;


/**
 * Presenter base class for Fragment
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 * @author kymjs (http://www.kymjs.com/) on 10/23/15.
 */
public abstract class FragmentPresenter<T extends ViewDelegate> extends Fragment implements IPresenter<T> {
    protected boolean mViewCreated = false;
    protected T mViewDelegate;
    public int mPage = 0;
    public boolean mIsRefreshMode = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mViewDelegate = ViewDelegate.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = mViewDelegate.getRootView();
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
        mViewDelegate.create(inflater, container, savedInstanceState);
        return mViewDelegate.getRootView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewCreated = true;
        mViewDelegate.initWidget();
        bindEvenListener();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewCreated = false;
    }

    public void startMyActivity(Class<?> pClass, Bundle pBundle) {
        mViewDelegate.startMyActivity(pClass, pBundle);
    }

    public void startMyActivity(Class<?> pClass, String key, Serializable pBundle) {
        mViewDelegate.startMyActivity(pClass, key, pBundle);
    }

    public void startMyActivityWithFinish(Class<?> pClass, Bundle pBundle) {
        mViewDelegate.startMyActivityWithFinish(pClass, pBundle);
    }

    public void startMyActivityForResult(Intent intent, int requestCode) {
        mViewDelegate.startMyActivityForResult(intent, requestCode);
    }

    public void startMyActivityForResult(Class<?> pClass, String action, int requestCode) {
        mViewDelegate.startMyActivityForResult(pClass, action, requestCode);
    }

    public void startMyActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        mViewDelegate.startMyActivityForResult(clazz, requestCode, bundle);
    }

    protected void bindEvenListener() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mViewDelegate.createMenu(menu, inflater);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mViewDelegate == null) {
            mViewDelegate = ViewDelegate.newInstance(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewDelegate.onDestroy();
        mViewDelegate = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onFragmentVisible();
//            FileUtil.writeLog(getClass().getName() + "->visibleToUser");
        } else {
            onFragmentHidden();
//            FileUtil.writeLog(getClass().getName() + "->invisibleToUser");
        }
    }

    protected abstract void onFragmentVisible();

    protected abstract void onFragmentHidden();

    public void setRecycler(final RecyclerView recycler, final RecyclerView.Adapter adapter, final boolean scroll) {
        LinearLayoutManager linearLayoutManager;
        if (scroll) {
            linearLayoutManager = new LinearLayoutManager(getActivity());
        } else {
            linearLayoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
        }
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
    }

    public void setRecycler(final RecyclerView recycler, final RecyclerView.Adapter adapter, final int count, final boolean scroll) {
        GridLayoutManager gridLayoutManager;
        if (scroll) {
            gridLayoutManager = new GridLayoutManager(getActivity(), count);
        } else {
            gridLayoutManager = new GridLayoutManager(getActivity(), count) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
        }
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
    }
}
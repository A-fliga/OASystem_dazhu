/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.oasystem_dazhu.mvp.view.baseDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.presenter.IPresenter;

import java.io.Serializable;


/**
 * View delegate base class
 * 视图层代理的基类
 *
 * @author kymjs (http://www.kymjs.com/) on 10/23/15.
 */
public abstract class ViewDelegate implements IDelegate {
    protected final SparseArray<View> mViews = new SparseArray<View>();
    protected View mRootView;

    public abstract int getRootLayoutId();

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = getRootLayoutId();
        mRootView = inflater.inflate(rootLayoutId, container, false);
    }

    public static <T extends ViewDelegate> T newInstance(IPresenter presenter) {
        T viewDelegate;
        try {
            viewDelegate = (T) presenter.getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create ViewDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create ViewDelegate error");
        }
        return viewDelegate;
    }

    public void startMyActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null)
            intent.putExtras(pBundle);
        getActivity().startActivity(intent);
    }

    public void startMyActivity(Class<?> pClass, String key, Serializable pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null && key != null)
            intent.putExtra(key, pBundle);
        getActivity().startActivity(intent);
    }

    public void startMyActivityWithFinish(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null)
            intent.putExtras(pBundle);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    public void startMyActivityForResult(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent, requestCode);
    }




    public EditText getEt(int id){
        return get(id);
    }

    public TextView getTv(int id){
        return get(id);
    }

    public void startMyActivityForResult(Class<?> pClass, String action, int requestCode) {
        Intent intent;
        if (action != null)
            intent = new Intent(action);
        else
            intent = new Intent(getActivity(), pClass);
        getActivity().startActivityForResult(intent, requestCode);
    }

    public void startMyActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        try {
            Intent intent = new Intent(getActivity(), clazz);
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            getActivity().startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认toolbar
     */
    public Toolbar getToolbar() {
        return get(R.id.toolbar);
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public TextView getTitleView() {
        return get(R.id.toolbar_title);
    }

    public void setTitleColor(int id) {
        getTitleView().setTextColor(getActivity().getResources().getColor(id));
    }

    public RelativeLayout getToolbarBackGround(){
        return get(R.id.mToolbar_rl);
    }
    /**
     * 设置toolbar背景色
     */
    public void setToolBarColor(int color) {
        getToolbar().setBackgroundColor(getActivity().getResources().getColor(color));
    }

    public TextView getToolBarLeftTv() {
        return get(R.id.toolbar_left_tv);
    }

    /**
     * 设置toolbar左边图标
     *
     * @param content
     */
    public void setToolBarLeftTv(String content) {
        getToolBarLeftTv().setVisibility(View.VISIBLE);
        getToolBarLeftTv().setText(content);
    }


    public ImageView getToolBarLeftImg() {
//        return get(R.id.toolBar_img_left);
        return null;
    }

    public ImageView getToolBarRightImg() {
        return get(R.id.toolBar_img_right);
    }

    public void setToolBarRightImg(int resId) {
        ImageView imageView = getToolBarRightImg();
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(resId);
    }

    /**
     * 设置toolbar左边图标
     *
     * @param imgRes
     */
    public void setToolBarLeftImg(int imgRes) {
        ImageView imageView = getToolBarLeftImg();
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(imgRes);
//        getToolBarLeftImg().setBackgroundColor(getActivity().getResources().getColor(R.color.color_00000000));
    }

    public TextView getToolBarRightTv() {
        return get(R.id.toolbar_right_tv);
    }
    public TextView getToolBarRightTv2() {
        return get(R.id.toolbar_right_tv2);
    }


    /**
     * 设置个人中心页面toolbar右边的点击文字
     *
     * @param text
     */
    public void setToolBarRightTv(String text) {
        get(R.id.toolbar_right_rl).setVisibility(View.VISIBLE);
        getToolBarRightTv().setText(text);
    }

    public void setToolBarRightTv2(String text) {
        get(R.id.toolbar_right_rl).setVisibility(View.VISIBLE);
        getToolBarRightTv2().setText(text);
    }

    public RelativeLayout getToolBarRight() {
        return get(R.id.toolbar_right_rl);
    }


    @Override
    public View getRootView() {
        return mRootView;
    }

    public void setRootView(View rootView) {
        this.mRootView = rootView;
    }

    @Override
    public abstract void initWidget();


    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) mRootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    public void createMenu(Menu menu, MenuInflater inflater) {
        if (getOptionsMenuId() != 0) {
            inflater.inflate(getOptionsMenuId(), menu);
        }
    }

    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    public void toast(CharSequence msg) {
        Toast.makeText(mRootView.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public <T extends Activity> T getActivity() {
        return (T) mRootView.getContext();
    }


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

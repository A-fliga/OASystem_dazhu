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
package org.oasystem_dazhu.mvp.presenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import org.oasystem_dazhu.mvp.presenter.IPresenter;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

import java.io.Serializable;
import java.util.Stack;


/**
 * Presenter base class for Activity
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 */
public abstract class ActivityPresenter<T extends ViewDelegate> extends AppCompatActivity implements IPresenter<T> {
    private static Stack<Activity> sActivityStack;
    protected T mViewDelegate;
    protected Toolbar mToolbar;

    public ActivityPresenter() {
        mViewDelegate = ViewDelegate.newInstance(this);
    }

    public static Stack<Activity> getActivityStack() {
        return sActivityStack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mViewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        addActivity(this);
        setContentView(mViewDelegate.getRootView());
        initToolbar();
        mViewDelegate.initWidget();
        bindEvenListener();
    }


    protected void initToolbar() {
        mToolbar = mViewDelegate.getToolbar();
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            //禁止显示title
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //是否显示返回箭头
            if (isSetDisplayHomeAsUpEnabled()) {
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public <T extends View> T get(int id) {
        return (T) mViewDelegate.get(id);
    }

    /**
     * 是否设置返回箭头
     *
     * @return
     */
    public abstract boolean isSetDisplayHomeAsUpEnabled();


    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : sActivityStack) {
            if (activity.getClass().equals(cls) && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static int getActivityCount() {
        return sActivityStack.size();
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public static Activity getTopActivity() {
        return sActivityStack.lastElement();
    }


    /**
     * 结束所有Activity
     */
    public synchronized static void finishAllActivity() {
        for (Activity activity : sActivityStack) {
            if (activity != null && sActivityStack.size() != 1) {
                activity.finish();
            }
        }
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<>();
        }
        sActivityStack.add(activity);
    }

    protected void bindEvenListener() {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mViewDelegate == null) {
            try {
                mViewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mViewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(mViewDelegate.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void startMyActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null)
            intent.putExtras(pBundle);
        startActivity(intent);
    }


    public void startMyActivity(Class<?> pClass) {
        Intent intent = new Intent(this, pClass);
        startActivity(intent);
    }

    public void startMyActivity(Class<?> pClass, String key, Serializable pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null && key != null)
            intent.putExtra(key, pBundle);
        startActivity(intent);
    }

    public void startMyActivityWithFinish(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null)
            intent.putExtras(pBundle);
        startActivity(intent);
        finish();
    }

    public synchronized void startMyActivityWithFinish(Class<?> pClass) {
        Intent intent = new Intent(this, pClass);
        startActivity(intent);
        finish();
    }

    public void startMyActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    public void startMyActivityForResult(Class<?> pClass, String action, int requestCode) {
        Intent intent;
        if (action != null)
            intent = new Intent(action);
        else
            intent = new Intent(this, pClass);
        startActivityForResult(intent, requestCode);
    }

    public void startMyActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        try {
            Intent intent = new Intent(this, clazz);
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sActivityStack == null) {
            sActivityStack = new Stack<>();
        }
        sActivityStack.remove(this);
        mViewDelegate = null;
    }

}

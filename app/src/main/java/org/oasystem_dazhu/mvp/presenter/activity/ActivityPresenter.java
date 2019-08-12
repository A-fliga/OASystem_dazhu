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
    protected T viewDelegate;
    private static Stack<Activity> activityStack;
    protected Toolbar toolbar;

    public ActivityPresenter() {
        viewDelegate = ViewDelegate.newInstance(this);
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        addActivity(this);
        setContentView(viewDelegate.getRootView());
        initToolbar();
        viewDelegate.initWidget();
        bindEvenListener();
    }


    protected void initToolbar() {
        toolbar = viewDelegate.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //禁止显示title
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //是否显示返回箭头
            if (isSetDisplayHomeAsUpEnabled()) {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        return (T) viewDelegate.get(id);
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
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls) && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static int getActivityCount() {
        return activityStack.size();
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public static Activity getTopActivity() {
        return activityStack.lastElement();
    }


    /**
     * 结束所有Activity
     */
    public synchronized static void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null && activityStack.size() != 1) {
                activity.finish();
            }
        }
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    protected void bindEvenListener() {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(), menu);
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
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.remove(this);
        viewDelegate = null;
    }

}

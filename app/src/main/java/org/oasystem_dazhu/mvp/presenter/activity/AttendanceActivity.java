package org.oasystem_dazhu.mvp.presenter.activity;

import android.app.AppOpsManager;
import android.content.Context;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.AppOpsManagerCompat;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AttendanceBean;
import org.oasystem_dazhu.mvp.view.AttendanceDelegate;
import org.oasystem_dazhu.utils.AppUtil;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.InitDateUtil;
import org.oasystem_dazhu.utils.ProgressDialogUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import static org.oasystem_dazhu.R.id.toolbar_right_tv2;


/**
 * Created by www on 2019/3/25.
 * 考勤管理
 */

public class AttendanceActivity extends ActivityPresenter<AttendanceDelegate> {
    private LocationClient mLocationClient;
    private Timer mTimer;
    private TextView mTextView;
    private TimerTask mTask;
    private long mCurrentTime;

    @Override
    public Class<AttendanceDelegate> getDelegateClass() {
        return AttendanceDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnClickListener();
        initWeight();
    }

    private void initWeight() {
        getAttendanceInfo();
        mCurrentTime = System.currentTimeMillis();
        mTextView = mViewDelegate.get(R.id.sign_in_time);
        mTextView.setText(InitDateUtil.initHours(mCurrentTime));

        mTask = new TimerTask() {
            @Override
            public void run() {
                mCurrentTime = mCurrentTime + 1000;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(InitDateUtil.initHours(mCurrentTime));
                    }
                });

            }
        };
        if (mTimer != null) {
            mTimer.purge();
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new Timer();
        mTimer.schedule(mTask, 0, 1000);
    }

    private void setOnClickListener() {
        mViewDelegate.setOnClickListener(onClickListener, R.id.to_sign_in, R.id.toolbar_right_tv, toolbar_right_tv2);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.to_sign_in:
                    if (AppUtil.isFastDoubleClick(10000))
                        ToastUtil.s("请勿打卡太快");
                    else {
                        ProgressDialogUtil.instance().startLoad();
                        checkLocationPermission();
                    }
                    break;

                //请假页面
                case R.id.toolbar_right_tv:
                    toLeavePage();
                    break;
                //考勤统计页面
                case toolbar_right_tv2:
                    toCountPage();
                    break;
            }
        }
    };

    private void toCountPage() {
        startMyActivity(AttendanceStatisticsActivity.class, null);
    }

    private void toLeavePage() {
        startMyActivity(AskForLeaveActivity.class, null);
    }

    private void toSignIn(String latitude, String longitude) {
        PublicModel.getInstance().
                addAttendance(new MSubscribe<BaseEntity>() {
                                  @Override
                                  public void onNext(BaseEntity bean) {
                                      if (bean.getCode() == 0) {
                                          ToastUtil.s("成功");
                                          initWeight();
                                      } else {
                                          ToastUtil.s(bean.getMsg());
                                      }
                                  }
                              }, String.valueOf(mViewDelegate.getSignType()),
                        InitDateUtil.getDate(System.currentTimeMillis()).toString(), latitude, longitude);
    }


    private void checkLocationPermission() {
        if (!isLocServiceEnable(this)) {//检测是否开启定位服务
            DialogUtil.showLocServiceDialog(this);
        } else {//检测用户是否将当前应用的定位权限拒绝
            int checkResult = checkOp(this, 2, AppOpsManager.OPSTR_FINE_LOCATION);//其中2代表AppOpsManager.OP_GPS，如果要判断悬浮框权限，第二个参数需换成24即AppOpsManager。OP_SYSTEM_ALERT_WINDOW及，第三个参数需要换成AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW
            int checkResult2 = checkOp(this, 1, AppOpsManager.OPSTR_FINE_LOCATION);
            if (AppOpsManagerCompat.MODE_IGNORED == checkResult || AppOpsManagerCompat.MODE_IGNORED == checkResult2) {
                DialogUtil.showLocIgnoredDialog(this);
            } else {
                startLocation();
            }
        }
        ProgressDialogUtil.instance().stopLoad();
    }

    private void startLocation() {
        mLocationClient = new LocationClient(this);
        MyLocationListener locationListener = new MyLocationListener();
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(0);
        option.setOpenGps(true);
        mLocationClient.registerLocationListener(locationListener);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            toSignIn(String.valueOf(bdLocation.getLatitude()), String.valueOf(bdLocation.getLongitude()));
        }
    }


    /**
     * 检查权限列表
     *
     * @param context
     * @param op       这个值被hide了，去AppOpsManager类源码找，如位置权限  AppOpsManager.OP_GPS==2
     * @param opString 如判断定位权限 AppOpsManager.OPSTR_FINE_LOCATION
     * @return @see 如果返回值 AppOpsManagerCompat.MODE_IGNORED 表示被禁用了
     */
    public static int checkOp(Context context, int op, String opString) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
//            Object object = context.getSystemService("appops");
            Class c = object.getClass();
            try {
                Class[] cArg = new Class[3];
                cArg[0] = int.class;
                cArg[1] = int.class;
                cArg[2] = String.class;
                Method lMethod = c.getDeclaredMethod("checkOp", cArg);
                return (Integer) lMethod.invoke(object, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                if (Build.VERSION.SDK_INT >= 23) {
                    return AppOpsManagerCompat.noteOp(context, opString, context.getApplicationInfo().uid,
                            context.getPackageName());
                }

            }
        }
        return -1;
    }


    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }


    private void getAttendanceInfo() {
        PublicModel.getInstance().getAttendanceInfo(new MSubscribe<BaseEntity<AttendanceBean>>() {
            @Override
            public void onNext(BaseEntity<AttendanceBean> bean) {
                super.onNext(bean);
                mViewDelegate.initView(bean.getData());
            }
        }, InitDateUtil.getDate(System.currentTimeMillis()).toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.purge();
            mTimer.cancel();
        }
        if (mLocationClient != null)
            mLocationClient.stop();
    }
}

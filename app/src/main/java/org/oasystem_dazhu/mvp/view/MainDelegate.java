package org.oasystem_dazhu.mvp.view;

import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

/**
 * Created by www on 2018/12/29.
 */

public class MainDelegate extends ViewDelegate {
    private ImageView home_official_img, home_user_img;
    private TextView home_official_tv, home_user_tv;

    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        home_official_img = get(R.id.home_official_img);
        home_user_img = get(R.id.home_user_img);
        home_official_tv = get(R.id.home_official_tv);
        home_user_tv = get(R.id.home_user_tv);
    }

    public void setCheck(int position) {
        if (position == 0) {
            home_official_img.setImageResource(R.mipmap.official_selected);
            home_user_img.setImageResource(R.mipmap.official_normal);
            home_official_tv.setTextColor(getActivity().getResources().getColor(R.color.color_f0000));
            home_user_tv.setTextColor(getActivity().getResources().getColor(R.color.color_666666));
        } else {
            home_user_img.setImageResource(R.mipmap.official_selected);
            home_official_img.setImageResource(R.mipmap.official_normal);
            home_user_tv.setTextColor(getActivity().getResources().getColor(R.color.color_f0000));
            home_official_tv.setTextColor(getActivity().getResources().getColor(R.color.color_666666));
        }
    }
}

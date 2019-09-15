package org.oasystem_dazhu.mvp.view;

import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

/**
 * Created by www on 2018/12/29.
 */

public class MainDelegate extends ViewDelegate {
    private ImageView mHomeOfficialImg, mHomeUserImg;
    private TextView mHomeOfficialTv, mHomeUserTv;

    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        mHomeOfficialImg = get(R.id.home_official_img);
        mHomeUserImg = get(R.id.home_user_img);
        mHomeOfficialTv = get(R.id.home_official_tv);
        mHomeUserTv = get(R.id.home_user_tv);
    }

    public void setCheck(int position) {
        if (position == 0) {
            mHomeOfficialImg.setImageResource(R.mipmap.official_selected);
            mHomeUserImg.setImageResource(R.mipmap.official_normal);
            mHomeOfficialTv.setTextColor(getActivity().getResources().getColor(R.color.color_f0000));
            mHomeUserTv.setTextColor(getActivity().getResources().getColor(R.color.color_666666));
        } else {
            mHomeUserImg.setImageResource(R.mipmap.official_selected);
            mHomeOfficialImg.setImageResource(R.mipmap.official_normal);
            mHomeUserTv.setTextColor(getActivity().getResources().getColor(R.color.color_f0000));
            mHomeOfficialTv.setTextColor(getActivity().getResources().getColor(R.color.color_666666));
        }
    }
}

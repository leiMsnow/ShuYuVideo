package com.shuyu.video.adapter;

import android.content.Context;

import com.shuyu.video.R;
import com.shuyu.video.model.SettingsInfo;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/9/11.
 */

public class SettingAdapter extends SuperAdapter<SettingsInfo> {

    public SettingAdapter(Context context, List<SettingsInfo> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SettingsInfo item) {
        holder.setImageResource(R.id.iv_setting_icon, item.getResId());
        holder.setText(R.id.tv_setting_title, item.getTitle());
    }
}

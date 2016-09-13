package com.shuyu.video.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.video.R;
import com.shuyu.video.model.AppInfoListEntity;
import com.shuyu.video.utils.ImageUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/9/13.
 */
public class AppSoreAdapter extends SuperAdapter<AppInfoListEntity> {
    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public AppSoreAdapter(Context context, List<AppInfoListEntity> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AppInfoListEntity item) {
        holder.setText(R.id.tv_app_title, item.getTitle())
                .setText(R.id.tv_app_desc, item.getSummary());
        ImageView imageView = holder.findViewById(R.id.iv_app_icon);
        ImageUtils.showImage(mContext, item.getIconUrl(), imageView);
        holder.setOnClickListener(R.id.btn_down, mOnClickListener);
    }
}

package com.shuyu.video.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.video.R;
import com.shuyu.video.model.AppStore;
import com.shuyu.video.model.DownloadEntity;
import com.shuyu.video.utils.ImageShowUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by Azure on 2016/9/13.
 */
public class AppSoreAdapter extends SuperAdapter<AppStore> {
    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public AppSoreAdapter(Context context, List<AppStore> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AppStore item) {
        holder.setText(R.id.tv_app_title, item.getTitle())
                .setText(R.id.tv_app_desc, item.getSummary());
        ImageView imageView = holder.findViewById(R.id.iv_app_icon);
        ImageShowUtils.showImage(mContext, item.getIconUrl(), imageView);
        holder.setTag(R.id.btn_down, item);
        holder.setOnClickListener(R.id.btn_down, mOnClickListener);


        switch (item.getDownloadState()) {
            case DownloadEntity.PENDING:
                holder.setText(R.id.btn_down, "准备中");
                holder.setEnabled(R.id.btn_down, false);
                break;
            case DownloadEntity.PAUSED:
                holder.setText(R.id.btn_down, "继续");
                holder.setEnabled(R.id.btn_down, true);
                break;
            case DownloadEntity.PROGRESS:
                holder.setText(R.id.btn_down, "下载中");
                holder.setEnabled(R.id.btn_down, false);
                break;
            case DownloadEntity.CONNECTED:
                holder.setText(R.id.btn_down, "开始下载");
                holder.setEnabled(R.id.btn_down, false);
                break;
            case DownloadEntity.COMPLETED:
                holder.setText(R.id.btn_down, "安装");
                holder.setEnabled(R.id.btn_down, true);
                break;
            case DownloadEntity.ERROR:
            case DownloadEntity.WARN:
                holder.setText(R.id.btn_down, "下载出错");
                holder.setEnabled(R.id.btn_down, true);
                break;
            case DownloadEntity.NORMAL:
                holder.setText(R.id.btn_down, "下载");
                holder.setEnabled(R.id.btn_down, true);
                break;
            case DownloadEntity.INSTALLED:
                holder.setText(R.id.btn_down, "已安装");
                holder.setEnabled(R.id.btn_down, false);
                break;
        }
    }
}

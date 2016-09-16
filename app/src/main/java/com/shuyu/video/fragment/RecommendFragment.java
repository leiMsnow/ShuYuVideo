package com.shuyu.video.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.shuyu.core.BaseFragment;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.video.R;
import com.shuyu.video.adapter.AppSoreAdapter;
import com.shuyu.video.db.helper.AppInfoHelper;
import com.shuyu.video.model.AppInfoListEntity;
import com.shuyu.video.model.DownloadEntity;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.MyFileDownloadListener;

import java.util.List;

import butterknife.Bind;

public class RecommendFragment extends BaseFragment {

    @Bind(R.id.rv_container)
    RecyclerView mRvContainer;

    private AppSoreAdapter mAppSoreAdapter;

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initData() {
        mAppSoreAdapter = new AppSoreAdapter(mContext, null, R.layout.item_recommend);
        mRvContainer.setLayoutManager(new LinearLayoutManager(mContext));
        mRvContainer.setAdapter(mAppSoreAdapter);
        mAppSoreAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfoListEntity entity = (AppInfoListEntity) v.getTag();
                if (entity.getDownloadState() == DownloadEntity.NORMAL) {
                    createDownloadTask(entity).start();
                } else if (entity.getDownloadState() == DownloadEntity.COMPLETED) {
                    AppUtils.install(mContext, entity.getSavePath());
                }
            }
        });
        getAppStoreInfo();
    }

    private void getAppStoreInfo() {
        List<AppInfoListEntity> entities = AppInfoHelper.getHelper().getDataAll();
        mAppSoreAdapter.replaceAll(entities);
    }

    private BaseDownloadTask createDownloadTask(AppInfoListEntity downloadEntity) {
        return CommonUtils.createDownloadTask(downloadEntity.getDownloadUrl(), downloadEntity,
                new MyFileDownloadListener() {
                    @Override
                    public void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.PENDING);
                        ((AppInfoListEntity) task.getTag()).setTotalSize(totalBytes);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.PROGRESS);
                        ((AppInfoListEntity) task.getTag()).setCurrentSize(soFarBytes);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void error(BaseDownloadTask task, Throwable e) {
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.ERROR);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void connected(BaseDownloadTask task, String etag, boolean isContinue,
                                          int soFarBytes, int totalBytes) {
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.CONNECTED);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.PAUSED);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void completed(BaseDownloadTask task) {
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.COMPLETED);
                        ((AppInfoListEntity) task.getTag()).setCurrentSize(task.getSmallFileSoFarBytes());
                        ((AppInfoListEntity) task.getTag()).setTotalSize(task.getSmallFileTotalBytes());
                        AppInfoListEntity appInfo = ((AppInfoListEntity) task.getTag());
                        AppInfoHelper.getHelper().update(appInfo);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void warn(BaseDownloadTask task) {
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.WARN);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }
                });
    }
}

package com.shuyu.video.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.shuyu.core.BaseFragment;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.R;
import com.shuyu.video.adapter.AppSoreAdapter;
import com.shuyu.video.db.helper.AppInfoHelper;
import com.shuyu.video.model.AppInfoListEntity;
import com.shuyu.video.model.DownloadEntity;

import java.io.File;
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

        String fileName = downloadEntity.getDownloadUrl().substring(
                downloadEntity.getDownloadUrl().lastIndexOf("/") + 1);
        String savePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator
                + "downloadApk" + File.separator + fileName;
        downloadEntity.setSavePath(savePath);
        return FileDownloader.getImpl().create(downloadEntity.getDownloadUrl())
                .setPath(savePath, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setTag(downloadEntity)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        LogUtils.d("download", "PENDING");
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.PENDING);
//                        ((AppInfoListEntity) task.getTag()).setCurrentSize(soFarBytes);
                        ((AppInfoListEntity) task.getTag()).setTotalSize(totalBytes);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        LogUtils.d("download", "soFarBytes：" + soFarBytes + "   totalBytes: " + totalBytes);
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.PROGRESS);
                        ((AppInfoListEntity) task.getTag()).setCurrentSize(soFarBytes);
//                        ((AppInfoListEntity) task.getTag()).setTotalSize(totalBytes);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        LogUtils.d("download", "ERROR");
                        e.printStackTrace();
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.ERROR);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue,
                                             int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        LogUtils.d("download", "CONNECTED");
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.CONNECTED);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                        LogUtils.d("download", "PAUSED");
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.PAUSED);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        LogUtils.d("download", "COMPLETED");
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.COMPLETED);
                        ((AppInfoListEntity) task.getTag()).setCurrentSize(task.getSmallFileSoFarBytes());
                        ((AppInfoListEntity) task.getTag()).setTotalSize(task.getSmallFileTotalBytes());
                        AppInfoListEntity appInfo = ((AppInfoListEntity) task.getTag());
                        AppInfoHelper.getHelper().update(appInfo);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        LogUtils.d("download", "WARN");
                        ((AppInfoListEntity) task.getTag()).setDownloadState(DownloadEntity.WARN);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }
                });
    }
}

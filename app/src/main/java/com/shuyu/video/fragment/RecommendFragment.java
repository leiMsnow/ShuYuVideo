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
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.R;
import com.shuyu.video.adapter.AppSoreAdapter;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.AppStoreEntity;
import com.shuyu.video.model.DownloadEntity;

import java.io.File;

import butterknife.Bind;

import static com.shuyu.core.api.BaseApi.createApi;

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
                DownloadEntity entity = (DownloadEntity) v.getTag();
                if (entity.getDownloadState() == DownloadEntity.NORMAL) {
                    createDownloadTask(entity).start();
                } else if (entity.getDownloadState() == DownloadEntity.COMPLETED) {
                    AppUtils.install(mContext, entity.getFilePath());
                }
            }
        });
        getAppStoreInfo();
    }


    private void getAppStoreInfo() {
        BaseApi.request(createApi(IMainApi.class).getAppStoreList(1),
                new BaseApi.IResponseListener<AppStoreEntity>() {
                    @Override
                    public void onSuccess(AppStoreEntity data) {
                        mAppSoreAdapter.replaceAll(data.getAppInfoList());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private BaseDownloadTask createDownloadTask(DownloadEntity downloadEntity) {

        final String downloadPath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator
                + "downloadApk" + File.separator + downloadEntity.getFileName();
        downloadEntity.setFilePath(downloadPath);
        return FileDownloader.getImpl().create(downloadEntity.getUrl())
                .setPath(downloadPath + downloadEntity.getFileName(), false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setTag(downloadEntity)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        LogUtils.d("download", "PENDING");
                        ((DownloadEntity) task.getTag()).setDownloadState(DownloadEntity.PENDING);
                        ((DownloadEntity) task.getTag()).setCurrentSize(soFarBytes);
                        ((DownloadEntity) task.getTag()).setTotalSize(totalBytes);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        LogUtils.d("download", "PROGRESS");
                        ((DownloadEntity) task.getTag()).setDownloadState(DownloadEntity.PROGRESS);
                        ((DownloadEntity) task.getTag()).setCurrentSize(soFarBytes);
                        ((DownloadEntity) task.getTag()).setTotalSize(totalBytes);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        LogUtils.d("download", "ERROR");
                        e.printStackTrace();
                        ((DownloadEntity) task.getTag()).setDownloadState(DownloadEntity.ERROR);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue,
                                             int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        LogUtils.d("download", "CONNECTED");
                        ((DownloadEntity) task.getTag()).setDownloadState(DownloadEntity.CONNECTED);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                        LogUtils.d("download", "PAUSED");
                        ((DownloadEntity) task.getTag()).setDownloadState(DownloadEntity.PAUSED);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        LogUtils.d("download", "COMPLETED");
                        ((DownloadEntity) task.getTag()).setDownloadState(DownloadEntity.COMPLETED);
                        ((DownloadEntity) task.getTag()).setCurrentSize(task.getSmallFileSoFarBytes());
                        ((DownloadEntity) task.getTag()).setTotalSize(task.getSmallFileSoFarBytes());
                        mAppSoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        LogUtils.d("download", "WARN");
                        ((DownloadEntity) task.getTag()).setDownloadState(DownloadEntity.WARN);
                        mAppSoreAdapter.notifyDataSetChanged();
                    }
                });
    }
}

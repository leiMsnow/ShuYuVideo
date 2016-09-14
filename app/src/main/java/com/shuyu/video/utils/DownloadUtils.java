package com.shuyu.video.utils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.shuyu.core.uils.LogUtils;

import java.io.File;

/**
 * Created by Azure on 2016/9/14.
 */
public class DownloadUtils {

    public static <T> BaseDownloadTask createDownloadTask(String url,T tag) {

        final String fileName = url.substring(url.lastIndexOf("/") + 1);
        final String downloadPath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator
                + "downloadApk" + File.separator + fileName;

        return FileDownloader.getImpl().create(url)
                .setPath(downloadPath + fileName, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setTag(tag)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        LogUtils.d("download","PENDING"+"task:------------"+task.getTag().getClass().getName());
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        LogUtils.d("download","PROGRESS"+"task:------------"+task.getTag().getClass().getName());
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        LogUtils.d("download","ERROR");
                        e.printStackTrace();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue,
                                             int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        LogUtils.d("download","CONNECTED");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                        LogUtils.d("download","PAUSED");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        LogUtils.d("download","COMPLETED");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        LogUtils.d("download","WARN");
                    }
                });
    }
}

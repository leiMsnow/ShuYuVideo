package com.shuyu.video.utils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.shuyu.core.CoreApplication;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.LogUtils;

import java.io.File;

/**
 * Created by zhangleilei on 9/10/16.
 */

public class CommonUtils {

    public static <T> BaseDownloadTask createDownloadTask(String url, T tag
            , final MyFileDownloadListener fileDownloadSampleListener) {

        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String savePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator
                + "downloadApk" + File.separator + fileName;
        return FileDownloader.getImpl().create(url) .setPath(savePath, false)
                .setCallbackProgressTimes(300) .setMinIntervalUpdateSpeed(400)
                .setTag(tag) .setListener(new FileDownloadSampleListener() {
                    @Override
                    public void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtils.d("download", "PENDING");
                        if (fileDownloadSampleListener != null)
                            fileDownloadSampleListener.pending(task, soFarBytes, totalBytes);

                    }

                    @Override
                    public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtils.d("download", "soFarBytes：" + soFarBytes + "   totalBytes: " + totalBytes);
                        if (fileDownloadSampleListener != null)
                            fileDownloadSampleListener.progress(task, soFarBytes, totalBytes);

                    }

                    @Override
                    public void error(BaseDownloadTask task, Throwable e) {
                        LogUtils.d("download", "ERROR");
                        e.printStackTrace();
                        if (fileDownloadSampleListener != null)
                            fileDownloadSampleListener.error(task, e);

                    }

                    @Override
                    public void connected(BaseDownloadTask task, String etag, boolean isContinue,
                                          int soFarBytes, int totalBytes) {
                        LogUtils.d("download", "CONNECTED");
                        if (fileDownloadSampleListener != null)
                            fileDownloadSampleListener.connected(task, etag, isContinue, soFarBytes, totalBytes);

                    }

                    @Override
                    public void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtils.d("download", "PAUSED");
                        if (fileDownloadSampleListener != null)
                            fileDownloadSampleListener.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    public void completed(BaseDownloadTask task) {
                        LogUtils.d("download", "COMPLETED: " + task.getPath());
                        AppUtils.install(CoreApplication.getApplication().getApplicationContext(),
                                task.getPath());
                        if (fileDownloadSampleListener != null)
                            fileDownloadSampleListener.completed(task);
                    }

                    @Override
                    public void warn(BaseDownloadTask task) {
                        LogUtils.d("download", "WARN");
                        if (fileDownloadSampleListener != null)
                            fileDownloadSampleListener.warn(task);
                    }
                });
    }
}

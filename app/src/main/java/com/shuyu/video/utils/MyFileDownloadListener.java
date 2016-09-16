package com.shuyu.video.utils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;

/**
 * Created by Azure on 2016/9/16.
 */

public class MyFileDownloadListener extends FileDownloadSampleListener {
    @Override
    public void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    public void blockComplete(BaseDownloadTask task) {

    }

    @Override
    public void completed(BaseDownloadTask task) {

    }

    @Override
    public void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
    }

    @Override
    public void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    public void error(BaseDownloadTask task, Throwable e) {

    }

    @Override
    public void warn(BaseDownloadTask task) {

    }
}

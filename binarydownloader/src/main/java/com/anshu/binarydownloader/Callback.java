package com.anshu.binarydownloader;

import androidx.annotation.NonNull;

import com.anshu.binarydownloader.exceptions.SyncFailureException;

public interface Callback {

    void pathFound(@NonNull String downloadedPath);

    void onError(@NonNull SyncFailureException exception);
}

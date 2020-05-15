package com.anshu.binarydownloader;

import androidx.annotation.NonNull;

public interface DeviceConfigProvider {

    @NonNull
    String getDeviceId();

    @NonNull
    String getBundleNameSpace();

    @NonNull
    String getPlatform();

    @NonNull
    String getAppVersion();

    @NonNull
    String getBundleEnvironment();
}

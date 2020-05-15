package com.anshu.binarydownloader.bundleconfig.models;

import androidx.annotation.NonNull;

import com.vimeo.stag.UseStag;

@UseStag
public class ConfigRequestPayload {

    @NonNull
    String environment;
    @NonNull
    String nameSpace;
    @NonNull
    String platform;
    @NonNull
    String appVersion;
    @NonNull
    String deviceID;
    @NonNull
    ClientBundleState clientBundleState;
}

package com.anshu.binarydownloader.bundleconfig.models;

import androidx.annotation.NonNull;

import com.vimeo.stag.UseStag;

@UseStag
public class ClientBundleMeta {
    @NonNull
    String bundleName;
    @NonNull
    String bundleVersion;
}

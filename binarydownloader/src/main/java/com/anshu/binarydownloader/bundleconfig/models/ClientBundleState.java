package com.anshu.binarydownloader.bundleconfig.models;

import androidx.annotation.NonNull;

import com.vimeo.stag.UseStag;

import java.util.List;

@UseStag
public class ClientBundleState {
    @NonNull
    public List<ClientBundleMeta> arrayOfClientBundleMeta;
}

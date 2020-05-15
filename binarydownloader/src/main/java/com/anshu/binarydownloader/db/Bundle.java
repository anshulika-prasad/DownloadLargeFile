package com.anshu.binarydownloader.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "bundles", indices = {@Index(value = {"name", "is_active"},
        unique = true)})
public class Bundle {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    @NonNull
    public String bundleName;

    @ColumnInfo(name = "downloaded_path")
    @Nullable
    public String downloadedPath;

    @ColumnInfo(name = "is_active")
    int status; //1, 0

    @ColumnInfo(name = "bundle_version")
    int bundleVersion;

    @NonNull
    @ColumnInfo(name = "bundle_url")
    String bundleUrl;

    long patchAppliedTimestamp;

    @NonNull
    @ColumnInfo(name = "bundle_hash")
    String bundleHash;

    @NonNull
    String checksum;

    @NonNull
    String pathcUrl;

    @NonNull
    String patchDownloadedPath;

}

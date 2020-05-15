package com.anshu.binarydownloader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BundleHelper {


    public static BundleHelper.Builder bundleBuilder(@Nullable DeviceConfigProvider deviceConfigProvider) {
        return new Builder(deviceConfigProvider);
    }

    public void getBundleForPath(@NonNull String bundleName, @NonNull Context context,
                                 @Nullable DeviceConfigProvider deviceConfigProvider, @NonNull Callback callback) {
        // query in the DB, for
    }


    public static class Builder {
        @Nullable
        private DeviceConfigProvider deviceConfigProvider;

        public Builder(@Nullable DeviceConfigProvider deviceConfigProvider) {
            this.deviceConfigProvider = deviceConfigProvider;
        }

        public BundleHelper build() {
            return new BundleHelper();
        }
    }
}

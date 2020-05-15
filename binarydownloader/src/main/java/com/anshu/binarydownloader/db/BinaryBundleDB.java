package com.anshu.binarydownloader.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Bundle.class}, version = 1)
public abstract class BinaryBundleDB extends RoomDatabase {

    public static final String DATABASE_NAME = "bundle.db";

    abstract BundleDao bundleDao();

    private static BinaryBundleDB sInstance;

    /**
     *
     * @param context
     * @return
     */
    public static synchronized BinaryBundleDB getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), BinaryBundleDB.class, DATABASE_NAME)
                    .build();
        }
        return sInstance;
    }
}

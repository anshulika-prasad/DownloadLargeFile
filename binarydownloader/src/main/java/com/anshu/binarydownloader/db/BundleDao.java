package com.anshu.binarydownloader.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BundleDao {

    @Insert
    void insert(Bundle bundle);

    @Update
    void update(Bundle bundle);

    @Delete
    void delete(Bundle bundle);

    @Query("SELECT * FROM bundles WHERE name = :bundleName AND is_active = 1")
    Bundle getActiveBundle(String bundleName);

    /**
     * Counts the number of bundles in the table.
     *
     * @return The number of bundle.
     */
    @Query("SELECT COUNT(*) FROM bundles")
    int count();
}

package com.anshu.downloadlargefile;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.anshu.binarydownloader.BundleHelper;
import com.anshu.binarydownloader.Callback;
import com.anshu.binarydownloader.DeviceConfigProvider;
import com.anshu.binarydownloader.exceptions.SyncFailureException;
import com.anshu.binarydownloader.utils.HashUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DeviceConfigProvider {

    private long downloadId;
    private DownloadManager downloadManager;
    public static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadId == id) {
                Log.d(TAG, " download complete downloadId :" + downloadId);
                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "ids dont match downloadId : " + downloadId + "  intent id : " + id);
            }

            Log.e("INSIDE", "" + id);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("GadgetSaint")
                            .setContentText("All Download completed");


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(455, mBuilder.build());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startDownloadFile();
            }
        });

        FloatingActionButton error = findViewById(R.id.checkError);
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
                //set the query filter to our previously Enqueued download
                myDownloadQuery.setFilterById(downloadId);

                //Query the download manager about downloads that have been requested.
                Cursor cursor = downloadManager.query(myDownloadQuery);
                if (cursor.moveToFirst()) {
                    checkStatus(cursor);
                }
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpDownload okHttpDownload = new OkHttpDownload();
                File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                File fileName = new File(file, "FebEnd.ota");
                // /storage/emulated/0/Android/data/com.anshu.downloadlargefile/files/Download/font3449363372202539561.tmp
                okHttpDownload.downloadFile("FebEnd.ota", "http://10.47.2.22/linchpin-web/IOS/PROD/TEST/multiWidget/bundles/df35b1c0e09568118e9fe5383848b32b-3.ota", file);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fileName = new File("/storage/emulated/0/Android/data/com.anshu.downloadlargefile/files/Download/font3449363372202539561.tmp");

                if (fileName.exists()) {
                    Log.d(TAG, "file exists : " + fileName.getAbsolutePath() + " file size " + fileName.length());
                    // "checksum": "a4bd3af88a716f264384298294901465",
                    boolean calculateMD5 = HashUtils.checkMD5("a4bd3af88a716f264384298294901465", fileName);

                    Log.d(TAG, "calculateMD5 : " + calculateMD5);
                } else {
                    Log.d(TAG, "file not present : ");
                }


            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BundleHelper.bundleBuilder(MainActivity.this).build().getBundleForPath("multiWidget", MainActivity.this, MainActivity.this, new Callback() {

                    @Override
                    public void pathFound(@NonNull String downloadedPath) {
                        File fileName = new File(downloadedPath);

                        if (fileName.exists()) {
                            Log.d(TAG, "file exists : " + fileName.getAbsolutePath() + " file size " + fileName.length());
                            // "checksum": "a4bd3af88a716f264384298294901465",
                            boolean calculateMD5 = HashUtils.checkMD5("a4bd3af88a716f264384298294901465", fileName);

                            Log.d(TAG, "calculateMD5 : " + calculateMD5);
                        } else {
                            Log.d(TAG, "file not present : ");
                        }
                    }

                    @Override
                    public void onError(@NonNull SyncFailureException exception) {

                    }
                });
            }
        });
    }

    private void startDownloadFile() {
//        Uri downloadUri = Uri.parse("http://10.47.2.22/linchpin-web/IOS/PROD/TEST/multiWidget/bundles/df35b1c0e09568118e9fe5383848b32b-3.ota");
        Uri downloadUri = Uri.parse("http://speedtest.ftp.otenet.gr/files/test10Mb.db");
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager == null) {
            return;
        }
        try {


            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "binary.ota")
                    .setTitle("binary1.ota")
                    .setDescription("feb end release binary")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                    .setMimeType("application/octet-stream")
                    .allowScanningByMediaScanner();

            downloadId = downloadManager.enqueue(request);
            Log.d(TAG, "starting download : " + downloadId);
        } catch (SecurityException | IllegalStateException e) {
            e.printStackTrace();
            Toast.makeText(this, "some problem", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    private void checkStatus(Cursor cursor) {

        //column for status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename
        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
//        String filename = cursor.getString(filenameIndex);

        String statusText = "";
        String reasonText = "";

        switch (status) {
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch (reason) {
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch (reason) {
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                reasonText = "Filename:\n" + "filename";
                break;
        }

        Toast toast = Toast.makeText(MainActivity.this,
                statusText + "\n" +
                        reasonText,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();

        Log.d(TAG, statusText + "\n" +
                reasonText);


    }

    @NonNull
    @Override
    public String getDeviceId() {
        return "2F11EA41CB904831A750F355F2AC71C0";
    }

    @NonNull
    @Override
    public String getBundleNameSpace() {
        return "TEST";
    }

    @NonNull
    @Override
    public String getPlatform() {
        return "ANDROID";
    }

    @NonNull
    @Override
    public String getAppVersion() {
        return "7.4";
    }

    @NonNull
    @Override
    public String getBundleEnvironment() {
        return "PROD";
    }

    //Another option is creating a HandlerThread and set its priority
}

package com.anshu.downloadlargefile;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

import static com.anshu.downloadlargefile.MainActivity.TAG;

public class OkHttpDownload {

    private final OkHttpClient client;

    public OkHttpDownload() {
        client = new OkHttpClient();
    }

    public boolean downloadFile(final String fileName, final String url, final File file) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                String url1 = "https://publicobject.com/helloworld.txt";

                try {
                    download(url, fileName, file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return false;
    }


    public void download(String url, String fileName, File file) throws Exception {

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                Log.d(TAG, "headers : " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                try {
                    File tempFile = File.createTempFile("font", null, file); //NON-NLS
                    BufferedSink sink = Okio.buffer(Okio.sink(tempFile));
                    sink.writeAll(responseBody.source());
                    sink.close();

                    Log.d(TAG, "file written at path : " + tempFile.getPath()); //NON-NLS

                } catch (IOException e) {
                    Log.d(TAG, "Failed to write : " + url); //NON-NLS
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "response body null "); //NON-NLS
            }
        }

    }
}

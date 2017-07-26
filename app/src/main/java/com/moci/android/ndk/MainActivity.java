package com.moci.android.ndk;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    // 成功
    private static final int WHAT_SUCCESS = 0;
    // 合成失败
    private static final int WHAT_FAIL_PATCH = 1;
    public static final String PATH = Environment.getExternalStorageDirectory() + File.separator;

    //合成得到的新版apk
    public static final String NEW_APK_PATH = PATH + "update.apk";

    //从服务器下载来的差分包
    public static final String PATCH_PATH = PATH + "update.patch";
    TextView tv;
    Button button;
    // Used to load the 'apkpatch-lib' library on application startup.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PatchTask().execute();
            }
        });
    }

    class PatchTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            String oldApkSource = ApkUtils.getSourceApkPath(MainActivity.this, "com.moci.android.ndk");
            int result = PatchUtils.getInstance().patch(oldApkSource, NEW_APK_PATH, PATCH_PATH);
            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            switch (integer) {
                case WHAT_SUCCESS: {

                    String text = "新apk已合成成功：" + NEW_APK_PATH;
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                    ApkUtils.installApk(MainActivity.this, NEW_APK_PATH);
                    break;
                }
                case WHAT_FAIL_PATCH: {
                    String text = "新apk已合成失败！";
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }
}


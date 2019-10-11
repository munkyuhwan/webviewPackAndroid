package com.WebviewPack.CustomWebviewClient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.WebviewPack.WebPackMain;
import com.webviewpackaging.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moon on 2018. 5. 12..
 */

public class CustomWebChrome extends WebChromeClient {

    Activity mActivity;
    WebPackMain mMainActivity;

    public CustomWebChrome(Activity activity, WebPackMain mainActivity) {
        //super();
        this.mActivity = activity;
        this.mMainActivity = mainActivity;
    }

    public void openFileChooser(ValueCallback<Uri[]> uploadMsg) {

        mMainActivity.setmUploadMsg(uploadMsg);

        final File root = new File(Environment.getExternalStorageDirectory() + "/"+Environment.DIRECTORY_DCIM + "/Camera/" );
        root.mkdir();
        final String fname = "img_" + System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        mMainActivity.setOutputFileUri(Uri.fromFile(sdImageMainDirectory));

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        final PackageManager packageManager = mMainActivity.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);

        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, mMainActivity.getOutputFileUri());
            intent.setData(mMainActivity.getOutputFileUri());
            cameraIntents.add(intent);
            Log.d("df", "camera intent get: " + cameraIntents.get(0));
        }

        //FileSystem
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        //galleryIntent.addCategory(Intent.CATEGORY_APP_GALLERY);
        galleryIntent.setType("image/*");


        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "File Browser");

        // Add the camera options.
        Log.d("intent", "choose to array: " + cameraIntents.toArray(new Parcelable[]{}));

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        mActivity.startActivityForResult(chooserIntent, mMainActivity.getRcFileChoose());

    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        openFileChooser(filePathCallback);

        return true;
    }


}

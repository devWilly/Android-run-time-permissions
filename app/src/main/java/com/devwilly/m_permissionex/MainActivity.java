package com.devwilly.m_permissionex;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 0;
    private static final int REQUEST_CODE_MULTI_PERMISSION = 1;
    private static final String[] MANIFEST_ONE_PERMISSION = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String[] MANIFEST_MULTI_PERMISSION = new String[] {Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button checkPermissionBtn = findViewById(R.id.permission_btn);

        checkPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasExternalStoragePermission()) {
                    showToast(getString(R.string.permission_success_message));
                } else {
                    if (hasShowRequestPermissionRational()) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(getString(R.string.alert_dialog_title))
                                .setMessage(getString(R.string.alert_dialog_message))
                                .setPositiveButton(getString(R.string.alert_dialog_positive_button_text), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestExternalStoragePermission();
                                    }
                                }).setCancelable(false).show();
                    } else {
                        requestExternalStoragePermission();
                    }
                }
            }
        });

        Button checkMultiPermissionBtn = findViewById(R.id.multi_permission_btn);
        checkMultiPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasReadPhoneStatePermission() && hasReceiveSMSPermission()) {
                    showToast(getString(R.string.permission_success_message));
                } else {
                    if (hasShowMultiRequestPermissionRational()) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(getString(R.string.alert_dialog_title))
                                .setMessage(getString(R.string.alert_dialog_message))
                                .setPositiveButton(getString(R.string.alert_dialog_positive_button_text), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestMultiPermission();
                                    }
                                }).setCancelable(false).show();
                    } else {
                        requestMultiPermission();
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showToast(getString(R.string.permission_success_message));
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private boolean hasExternalStoragePermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasReadPhoneStatePermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasReceiveSMSPermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasShowRequestPermissionRational() {
        return ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private boolean hasShowMultiRequestPermissionRational() {
        return ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this, Manifest.permission.READ_PHONE_STATE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this, Manifest.permission.RECEIVE_SMS);
    }

    private void showToast(String toastMsg) {
        Toast.makeText(MainActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
    }

    private void requestExternalStoragePermission() {
        ActivityCompat.requestPermissions(
                MainActivity.this,
                MANIFEST_ONE_PERMISSION,
                REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
    }

    private void requestMultiPermission() {
        ActivityCompat.requestPermissions(
                MainActivity.this,
                MANIFEST_MULTI_PERMISSION,
                REQUEST_CODE_MULTI_PERMISSION);
    }
}

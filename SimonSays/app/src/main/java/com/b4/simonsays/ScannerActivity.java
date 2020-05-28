package com.b4.simonsays;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private int requestCamera;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if (!checkPermission()) {
            requestPermission();
        }
    }

    //checks if ScannerAcivity has permission to use camera
    public boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(ScannerActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, requestCamera);
    }

    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResults) {
        if (requestCode == requestCamera) {
            if (grantResults.length > 0) {
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (!cameraAccepted) {
                    if (shouldShowRequestPermissionRationale(CAMERA)) {
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{CAMERA}, requestCamera);
                            }
                        };
                    }
                }
            }
        }
    }

    //checks if there is a cscannerview, if not it creates one
    @Override
    public void onResume() {
        super.onResume();
        if (checkPermission()) {
            if (scannerView == null) {
                scannerView = new ZXingScannerView(this);
                setContentView(scannerView);
            }

            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }
    }

    //stops the camera
    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    /*public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        AlertDialog.Builder acceptCamera = new AlertDialog.Builder(ScannerActivity.this);
        acceptCamera.setMessage(message);
        acceptCamera.setPositiveButton("OK", listener);
        acceptCamera.setNegativeButton("No", null);
        acceptCamera.create();
        acceptCamera.show();
    }*/

    @Override
    public void handleResult(Result result) {
        if (result.getText().equals("Shining Saphires")) {
            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
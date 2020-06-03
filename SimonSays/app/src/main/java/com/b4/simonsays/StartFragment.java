package com.b4.simonsays;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class StartFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scannerView = view.findViewById(R.id.scannerView);
        scannerView.setVisibility(View.GONE);

        view.findViewById(R.id.scan_button).setOnClickListener(view1 -> {
            startScannerView();
        });
    }

    private void startScannerView() {
        if (!checkPermission()) {
            requestPermission();
        }

        scannerView.setVisibility(View.VISIBLE);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    //checks if ScannerAcivity has permission to use camera
    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, 3);
    }

//    //checks if there is a scannerview, if not it creates one
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (checkPermission()) {
//            scannerView.setVisibility(View.VISIBLE);
//            scannerView.setResultHandler(this);
//            scannerView.startCamera();
//        }
//    }
//
//    //stops the camera
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        scannerView.stopCamera();
//    }

    @Override
    public void handleResult(Result result) {
        if (result.getText().equals("Shining Saphires")) {
            Toast.makeText(getContext(), result.getText(), Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getContext(), "geen goede qr-code", Toast.LENGTH_SHORT).show();
            onResume();
        }
    }
}
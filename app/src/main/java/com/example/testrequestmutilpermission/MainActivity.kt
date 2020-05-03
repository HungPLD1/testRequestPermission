package com.example.testrequestmutilpermission

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Log.d("Hungpld1","onResume")
        if (isPermissionGranted()) {
            //ok
        } else {
            requestCodePermission()
        }
    }

    private fun requestCodePermission() {
        ActivityCompat.requestPermissions(
                this,
                permissions,
                REQUEST_CODE_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.isNotEmpty()) {
            grantResults.forEach {
                when (it) {
                    PackageManager.PERMISSION_GRANTED -> {
                        return
                    }
                    else -> {
                        setupPermissionGuideline(permissions.all { permission ->
                            isDeniedPermanently(permission)
                        })
                    }
                }
            }
        }
    }

    private fun setupPermissionGuideline(shouldOpenSetting: Boolean) {
        Log.d("Hungpld1","setupPermissionGuideline")
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        if (shouldOpenSetting) {
            builder1.setMessage("Mở cài đặt")
            builder1.setCancelable(true)
            builder1.setPositiveButton(
                    "Yes"
            ) { _, _ ->
                openPermissionSettings()
            }

            builder1.setNegativeButton(
                    "No"
            ) { _, _ ->
                finish()
            }

            val alert11: AlertDialog = builder1.create()
            alert11.show()
            return
        } else {
            builder1.setMessage("cấp quyền cho app")
            builder1.setCancelable(true)
            builder1.setPositiveButton(
                    "Yes"
            ) { _, _ ->
                requestCodePermission()
            }

            builder1.setNegativeButton(
                    "No"
            ) { _, _ ->
                finish()
            }
            val alert11: AlertDialog = builder1.create()
            alert11.show()
            return
        }
    }

    private fun isPermissionGranted(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                    this
                    , it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    companion object {
        const val REQUEST_CODE_PERMISSION = 0
        val permissions = arrayOf(
                Manifest.permission.CAMERA
                , Manifest.permission.CHANGE_NETWORK_STATE
                , Manifest.permission.MODIFY_AUDIO_SETTINGS
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.BLUETOOTH
                , Manifest.permission.INTERNET
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_NETWORK_STATE
        )
    }
}

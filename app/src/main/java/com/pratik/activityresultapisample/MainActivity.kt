package com.pratik.activityresultapisample

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pratik.activityresultapisample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Single Permission
        binding.btnSinglePermission.setOnClickListener {
            requestLocationPermission()
        }

        //Multiple Permission
        val multiplePermissionsArray = arrayOf(Constant.READ_CONTACT_PERMISSION, Constant.READ_CALENDAR)
        binding.btnMultiplePermission.setOnClickListener {
            multiplePermission.launch(multiplePermissionsArray)

        }

    }

    val multiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
    { map ->
        for (entry in map.entries) {
            Toast.makeText(this, "${entry.key} = ${entry.value}", Toast.LENGTH_SHORT).show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    val singlePermission = registerForActivityResult(ActivityResultContracts.RequestPermission())
    { isPermissionGranted ->
        when (isPermissionGranted) {
            true -> {
                showToast("Permission Granted")
            }
            false -> {
                if (shouldShowRequestPermissionRationale(Constant.LOCATION_PERMISSION)) {
                    showRationaleDialog(
                            "Permission",
                            "This is much needed permission"
                    )
                } else {

                }
            }
        }
    }

    fun showSettingsPanel() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    fun requestLocationPermission() = singlePermission.launch(Constant.LOCATION_PERMISSION)

    private fun showRationaleDialog(
            title: String,
            message: String
    ) {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", { dialog, which ->
                    singlePermission.launch(Constant.LOCATION_PERMISSION)
                })
        builder.create().show()
    }
}

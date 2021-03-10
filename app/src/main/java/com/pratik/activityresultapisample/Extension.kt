package com.pratik.activityresultapisample

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.showToast(resource: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resource, duration).show()
}
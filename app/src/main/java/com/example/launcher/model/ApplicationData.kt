package com.example.launcher.model

import android.graphics.drawable.Drawable

/**
 * This will help to store application necessary attributes and comparable used for sorting with application name
 */
data class ApplicationData(val appName: String, val packageName: String?, val mainActivity: String?, val versionName: String?,
val versionCode: Long?, val icon: Drawable?): Comparable<ApplicationData>{
    override fun compareTo(other: ApplicationData): Int {
        if (this.appName > other.appName) return 1
        if (this.appName < other.appName) return -1
        return 0
    }
}

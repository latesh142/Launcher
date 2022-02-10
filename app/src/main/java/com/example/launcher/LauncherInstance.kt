package com.example.launcher

import android.content.Context
import android.content.pm.ApplicationInfo

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.pm.PackageInfoCompat
import com.example.launcher.model.ApplicationData
import java.util.*
import kotlin.collections.ArrayList
import android.content.pm.ResolveInfo

import android.content.Intent
import android.content.pm.ActivityInfo


object LauncherInstance {

    fun getApplicationList(mContext: Context):TreeSet<ApplicationData> {
        val packageManager: PackageManager = mContext.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val applications = TreeSet<ApplicationData>()

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pkgAppsList: List<ResolveInfo> =  packageManager.queryIntentActivities(mainIntent, 0)
        for(app in pkgAppsList){
            val application = getApplication(app.activityInfo, packageManager)
            application.mainActivity?.apply {
                applications.add(application)
            }
        }

        return applications
    }

    private fun getApplication(appInfo: ActivityInfo, packageManager: PackageManager): ApplicationData{
        val packageName = appInfo.packageName
        val appInfoData = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val packageInfoData = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA)
        val appName = packageManager.getApplicationLabel(appInfoData).toString()
        val icon = packageManager.getApplicationIcon(packageName)
        val activityName = appInfo.name
        val versionCode = PackageInfoCompat.getLongVersionCode(packageInfoData)
        val versionName = packageInfoData.versionName

        return ApplicationData(
            appName, packageName, activityName,
            versionName, versionCode, icon
        )
    }
}
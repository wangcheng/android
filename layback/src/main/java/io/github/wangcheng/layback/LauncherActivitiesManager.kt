package io.github.wangcheng.layback

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.media.tv.TvContract
import android.media.tv.TvInputInfo
import android.media.tv.TvInputManager
import android.provider.Settings
import android.util.Log

class LauncherActivitiesManager(private val context: Context) {
    private val packageManager = context.packageManager
    private val tvInputManager =
        context.getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager

    fun getAllLauncherActivities(): List<LauncherItem> {
        val leanbackActivitiesList = getLauncherItems(leanback = true)
        val activityMap = leanbackActivitiesList.associateBy { it.id }.toMutableMap()
        val additionalList = getLauncherItems(leanback = false) + getSettingsActivities()
        for (app in additionalList) {
            if (activityMap[app.id] == null) {
                activityMap[app.id] = app
            }
        }

        val appList = activityMap.toList().map { it.second }.sortedBy { it.label }
        val inputList = getInputItems()

        return inputList + appList
    }

    fun launchActivity(item: LauncherItem) {
        val intent = item.launchIntent
        if (intent.resolveActivity(packageManager) != null) {
            context.startActivity(intent)
        } else {
            Log.d(TAG, "No Intent available to handle action")
        }
    }

    private fun loadBanner(activityInfo: ActivityInfo): Drawable {
        val banner = activityInfo.loadBanner(packageManager)
        if (banner != null) {
            return banner
        }
        return activityInfo.loadIcon(packageManager)
    }

    private fun getLauncherItems(leanback: Boolean): List<LauncherItem> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        val category = if (leanback) Intent.CATEGORY_LEANBACK_LAUNCHER else Intent.CATEGORY_LAUNCHER
        intent.addCategory(category)
        return packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
            .map {
                Pair(
                    it.activityInfo, if (leanback)
                        packageManager.getLeanbackLaunchIntentForPackage(it.activityInfo.packageName)
                    else packageManager.getLaunchIntentForPackage(it.activityInfo.packageName)
                )
            }
            .filter { it.first.isEnabled && it.first.packageName != context.packageName && it.second != null }
            .map {
                LauncherItem(
                    id = it.first.packageName,
                    label = it.first.loadLabel(packageManager).toString(),
                    loadBanner = { loadBanner(it.first) },
                    launchIntent = it.second as Intent,
                    isInput = false,
                    description = null,
                )
            }
    }

    private fun getSettingsActivities(): List<LauncherItem> {
        val intent = Intent(Settings.ACTION_SETTINGS)
        return packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
            .map { it.activityInfo }.map {
                LauncherItem(
                    id = it.packageName,
                    label = it.loadLabel(packageManager).toString(),
                    loadBanner = { loadBanner(it) },
                    launchIntent = intent,
                    isInput = false,
                    description = null,
                )
            }
    }

    private fun getInputItems(): List<LauncherItem> {
        val tvInputList =
            tvInputManager.tvInputList.filter { it.isPassthroughInput && !it.isHidden(context) }
        val tvInputMap = tvInputList.associateBy { it.id }
        return removeInputWithChildren(tvInputList).sortedBy { it.parentId ?: it.id }.map {
            val parentInput = tvInputMap[it.parentId]
            val selfLabel = getInputLabel(context, it)
            val parentLabel = if (parentInput != null) getInputLabel(context, parentInput) else null
            val inputType = getInputType(it.type)
            LauncherItem(
                id = it.id,
                label = parentLabel ?: selfLabel,
                loadBanner = { it.loadIcon(context) },
                launchIntent = createInputIntent(it),
                isInput = true,
                description = if (parentInput == null) inputType else "$inputType ($selfLabel)"
            )
        }
    }

    private fun isInputConnected(info: TvInputInfo): Boolean {
        return tvInputManager.getInputState(info.id) != TvInputManager.INPUT_STATE_DISCONNECTED
    }

    private fun removeInputWithChildren(list: List<TvInputInfo>): List<TvInputInfo> {
        val parentIds = mutableSetOf<String>()
        for (i in list) {
            val parentId = i.parentId
            if (i.parentId != null) {
                parentIds.add(parentId)
            }
        }
        return list.filter { !parentIds.contains(it.id) }
    }

    private fun createInputIntent(inputInfo: TvInputInfo): Intent {
        val uri = if (inputInfo.isPassthroughInput) TvContract.buildChannelUriForPassthroughInput(
            inputInfo.id
        ) else TvContract.buildChannelsUriForInput(inputInfo.id)
        return Intent(Intent.ACTION_VIEW, uri)
    }

    private fun getInputLabel(context: Context, info: TvInputInfo): String {
        val label = info.loadLabel(context)
        val customLabel = info.loadCustomLabel(context)
        if (customLabel != null) {
            return "$customLabel ($label)"
        }
        return label.toString()
    }

    private fun getInputType(inputType: Int): String {
        return when (inputType) {
            TvInputInfo.TYPE_COMPONENT -> "COMPONENT"
            TvInputInfo.TYPE_COMPOSITE -> "COMPOSITE"
            TvInputInfo.TYPE_DISPLAY_PORT -> "DISPLAY_PORT"
            TvInputInfo.TYPE_DVI -> "DVI"
            TvInputInfo.TYPE_HDMI -> "HDMI"
            TvInputInfo.TYPE_OTHER -> "OTHER"
            TvInputInfo.TYPE_SCART -> "SCART"
            TvInputInfo.TYPE_SVIDEO -> "S VIDEO"
            TvInputInfo.TYPE_TUNER -> "TUNER"
            TvInputInfo.TYPE_VGA -> "VGA"
            else -> "UNKNOWN INPUT"
        }
    }

    companion object {
        private const val TAG = "LauncherActivitiesManager"
    }
}

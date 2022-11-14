package com.app.immediateedge.base

import android.content.res.Resources
import androidx.core.net.toUri
import com.app.immediateedge.R
import java.util.*

class FileDataCreator {
    companion object {
        fun create(
            res: Resources,
            baseFileData: String,
            gadid: String,
            apps: MutableMap<String, Any>?,
            deep: String,
            uid: String?
        ): String {
            val url = baseFileData.toUri().buildUpon().apply {
                appendQueryParameter(
                    res.getString(R.string.secure_get_parametr),
                    res.getString(R.string.secure_key)
                )
                appendQueryParameter(
                    res.getString(R.string.dev_tmz_key),
                    TimeZone.getDefault().id
                )
                appendQueryParameter(res.getString(R.string.gadid_key_may), gadid)
                appendQueryParameter(res.getString(R.string.dipleenk_key), deep)
                appendQueryParameter(
                    res.getString(R.string.source_mource_key),
                    if (deep != "null") "deeplink" else apps?.get("media_source").toString()
                )
                appendQueryParameter(
                    res.getString(R.string.af_auf_key),
                    uid
                )
                appendQueryParameter(
                    res.getString(R.string.adset_alarm),
                    apps?.get("adset_id").toString()
                )
                appendQueryParameter(
                    res.getString(R.string.tamp_bayden_id_key),
                    apps?.get("campaign_id").toString()
                )
                appendQueryParameter(
                    res.getString(R.string.allan_key),
                    apps?.get("campaign").toString()
                )
                appendQueryParameter(
                    res.getString(R.string.adset_key),
                    apps?.get("adset").toString()
                )
                appendQueryParameter(
                    res.getString(R.string.adgroup_key),
                    apps?.get("adgroup").toString()
                )
                appendQueryParameter(
                    res.getString(R.string.orig_cost_key),
                    apps?.get("orig_cost").toString()
                )
                appendQueryParameter(
                    res.getString(R.string.af_siteid_key),
                    apps?.get("af_siteid").toString()
                )
            }.toString()
            return "https://$url"
        }
    }
}
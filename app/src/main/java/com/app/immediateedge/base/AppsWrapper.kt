package com.app.immediateedge.base

import com.appsflyer.AppsFlyerConversionListener

interface AppsWrapper : AppsFlyerConversionListener {
    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
    override fun onAttributionFailure(p0: String?) {}
}
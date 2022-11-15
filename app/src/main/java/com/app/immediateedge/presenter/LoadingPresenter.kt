package com.app.immediateedge.presenter

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.immediateedge.base.Const
import com.app.immediateedge.base.App
import com.app.immediateedge.base.AppsWrapper
import com.app.immediateedge.base.FileDataCreator
import com.app.immediateedge.base.OneWrapper
import com.app.immediateedge.base.NewService
import com.app.immediateedge.presentation.GameActivity
import com.app.immediateedge.presentation.LoadingActivity
import com.app.immediateedge.presentation.LoadingActivity.Companion.BROADCAST_RECEIVER_ACTION
import com.app.immediateedge.presentation.WebViewActivity
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoadingPresenter(private val activity: LoadingActivity) {


    val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras
            Log.e("MainActivity", "BROADCAST_RECEIVER_KEY")

            if (bundle != null) {
                if (bundle.containsKey(LoadingActivity.BROADCAST_RECEIVER_KEY)) {
                    val isNotADB = bundle.getBoolean(LoadingActivity.BROADCAST_RECEIVER_KEY)
                    Log.e("MainActivity", "BROADCAST_RECEIVER_KEY--$isNotADB")
                    if (isNotADB) {
                        initPresenter(activity)

                    } else {
                        val intent = Intent(context, GameActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        activity.startActivity(intent)
                        activity.finish()
                    }
                }
            }
        }
    }


    fun createBroadcastManager() {
        LocalBroadcastManager.getInstance(activity)
            .registerReceiver(
                mReceiver, IntentFilter(
                    BROADCAST_RECEIVER_ACTION
                )
            )
        activity.startService(Intent(activity, NewService::class.java))
    }

    fun initPresenter(activity: Activity) {
        init(activity.application).subscribe {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("web_url", it)
            Log.e("LoadingUrl", "url is $it")

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }

    fun init(app: Application): Observable<String> {
        return Observable.create { subscriber ->

            val dataUrls = (app as App).dataBase.dataUrlDao().getAll()

            if (dataUrls.isNotEmpty()) {
                Log.e("Initialization", "File exists")

                subscriber.onNext(dataUrls.first().uri)
            } else {
                getAppsflyer(activity).subscribe { apps ->
                    val deep = "null"
                    val adId = AdvertisingIdClient.getAdvertisingIdInfo(activity).id.toString()

                    val uId = AppsFlyerLib.getInstance().getAppsFlyerUID(activity)!!

                    OneWrapper(app, adId).send(apps?.get("campaign").toString(), deep)

                    subscriber.onNext(
                        FileDataCreator.create(
                            res = app.resources,
                            baseFileData = MainPresenter.BASE_API_URL,
                            gadid = adId,
                            apps = if (deep == "null") apps else null,
                            deep = deep,
                            uid = if (deep == "null") uId else null
                        )
                    )
                }
            }
        }.subscribeOn(Schedulers.newThread())
    }

    fun getAppsflyer(activity: LoadingActivity): Observable<MutableMap<String, Any>> =
        Observable.create { subs ->
            val callback = object : AppsWrapper {
                override fun onConversionDataSuccess(convData: MutableMap<String, Any>?) {
                    subs.onNext(convData)
                }

                override fun onConversionDataFail(p0: String?) {
                    subs.onNext(mutableMapOf())
                }
            }
            AppsFlyerLib.getInstance().init(Const.APPS_FLYER_KEY, callback, activity)
            AppsFlyerLib.getInstance().start(activity)
        }


}
package com.omar.fuzzer.caracteristicasdinamicasejemplo

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompatApplication

class AppBase : SplitCompatApplication() {
companion object {
   lateinit var context: Context

    fun getAppContext(): Context {
        return context
    }
}
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}
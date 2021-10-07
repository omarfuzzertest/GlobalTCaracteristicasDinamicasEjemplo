package com.omar.fuzzer.caracteristicasdinamicasejemplo.ondemand.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.omar.fuzzer.caracteristicasdinamicasejemplo.AppBase
import com.omar.fuzzer.caracteristicasdinamicasejemplo.R
import kotlinx.android.synthetic.main.custom_dialog.*

class OnDemandInstallManagerUtil(var activity: AppCompatActivity,var context: Context) {

    private lateinit var className: String
    private lateinit var packageName: String
    private lateinit var nameModule: String
    private var manager: SplitInstallManager = SplitInstallManagerFactory.create(context)
    private var requesrCode: Int = 0
    lateinit var mensaje:AlertDialog
    //  private var events: InstallerModulesEvents = context as InstallerModulesEvents

    fun openModule(nameModule: String, packageName:String, className: String, requesrCode:Int) {

        mensaje = dialogo("Iniciando $nameModule")



        manager.registerListener(listener)
        if (manager.installedModules.contains(nameModule)) { //la caracteristica ya esta instalada y se prcede a abrirla
            Log.e("#--->","Modulo instalado")
            onSuccessfulLoad(nameModule, packageName,className ,requesrCode)
            return
        }

        this.nameModule = nameModule
        this.packageName = packageName
        this.className = className
        this.requesrCode = requesrCode

        val request = SplitInstallRequest.newBuilder()
            .addModule(nameModule)
            .build()
        manager.startInstall(request)
    }


    private val listener = SplitInstallStateUpdatedListener { state ->
        val multiInstall = state.moduleNames().size > 1
        val names = state.moduleNames().joinToString(" - ")
        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                displayLoadingState(state, "DESCARGANDO")
                mensaje.text_progress_bar.text="Descargando >>> ${
                    converttoMB(
                        state.bytesDownloaded().toInt()
                    )
                } de ${converttoMB(state.totalBytesToDownload().toInt())} <<<"
            }
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                displayLoadingState(state, "CONFIRMACION DE  INSTALACION")
                activity.startIntentSender(state.resolutionIntent()?.intentSender, null, 0, 0, 0)
            }
            SplitInstallSessionStatus.INSTALLED -> {
                displayLoadingState(state, "INSTALADO")
                onSuccessfulLoad(nameModule, packageName, className, requesrCode)
                mensaje.text_progress_bar.text="Intslando"
            }
            SplitInstallSessionStatus.INSTALLING -> {
                displayLoadingState(state, "INSTALANDO")
                mensaje.text_progress_bar.text="Intslando"
            }
            SplitInstallSessionStatus.CANCELED -> {
                mensaje.dismiss()
            }
            SplitInstallSessionStatus.FAILED -> {
                mensaje.dismiss()
                displayLoadingState(state, "FALLO INSTALACION")
            }
        }
    }

    private fun onSuccessfulLoad(nameModule: String, packageName: String, className: String, requesrCode: Int) {
        Log.e("#--->","Lanzando")
        mensaje.text_progress_bar.text="Lanzando"
        launchActivity(activity, packageName,  "$packageName.$className",requesrCode)
    }

    fun launchActivity(activity: AppCompatActivity, packageName:String, className: String, requesrCode:Int) {
        mensaje.dismiss()
        Intent().setClassName(activity.packageName, className)
            .also {
                activity.startActivityForResult(it, requesrCode)
            }
    }

    private fun displayLoadingState(state: SplitInstallSessionState, message: String) {
        Log.e(">>>   $message   <<<", ">>> ${converttoMB(state.bytesDownloaded().toInt())} de ${converttoMB(state.totalBytesToDownload().toInt())}  <<<")
    }

    private fun converttoMB(bytes:Int):Float{
        return (bytes.toFloat()/1024F)/1024F
    }

    var dialogo : (String) -> AlertDialog = { t  ->
        AlertDialog.Builder(activity).run {
            setView(LayoutInflater.from(activity).inflate(R.layout.custom_dialog,null))
            this.create().apply {
                setCanceledOnTouchOutside(false)
                this.show()
                text_progress_bar.text = t
            }
        }
    }
}
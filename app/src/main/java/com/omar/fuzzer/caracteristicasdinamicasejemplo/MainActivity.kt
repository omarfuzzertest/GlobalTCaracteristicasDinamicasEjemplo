package com.omar.fuzzer.caracteristicasdinamicasejemplo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.omar.fuzzer.caracteristicasdinamicasejemplo.ondemand.utils.OnDemandInstallManagerUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var onDemandInstaller: OnDemandInstallManagerUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onDemandInstaller = OnDemandInstallManagerUtil(this,applicationContext)
        btn_capturar.setOnClickListener {
            onDemandInstaller.openModule(
                nameModule  = "caracteristica_camara",
                packageName = "omar.fuzzer.caracteristica_camara",
                className   = "CamaraActivity",
                requesrCode = 21 )
        }
        btn_capturar_tarjeta.setOnClickListener {
            onDemandInstaller.openModule(
                nameModule  = "caracteristica_tarjeta",
                packageName = "omar.fuzzer.modulo_tarjeta",
                className   = "CapturaTarjetaActivity",
                requesrCode = 22 )
        }

        btn_video.setOnClickListener {
            onDemandInstaller.openModule(
                nameModule  = "caracteristica_video_player",
                packageName = "com.omar.fuzzer.caracteristica.video.player",
                className   = "ReproductorActivity",
                requesrCode = 23 )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            21 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        data!!.data
                        Glide
                            .with(this)
                            .load(data.data)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .fitCenter()
                            .into(img_foto)
                    }
                    else -> {
                    }
                }
            }
            22 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        tv_num_tarjeta.text =
                            "cardNumber = ${data!!.extras!!.get("cardNumber")} \nredactedCardNumber = ${data.extras!!.get(
                                "redactedCardNumber"
                            )} "
                    }
                    else -> {
                        tv_num_tarjeta.text = "no se puede obtener la tarjeta"
                    }
                }
            }
        }
    }
}
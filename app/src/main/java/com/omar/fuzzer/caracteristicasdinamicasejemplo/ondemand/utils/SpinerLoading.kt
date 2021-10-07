package com.omar.fuzzer.caracteristicasdinamicasejemplo.ondemand.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.omar.fuzzer.caracteristicasdinamicasejemplo.R
import kotlinx.android.synthetic.main.custom_dialog.*

class SpinerLoading(var context: Context){


    private fun ss(){

    }

    companion object {
        var dialogo : (String,Context ) -> AlertDialog  = { t,c  ->
            AlertDialog.Builder(c).run {
                setView(LayoutInflater.from(context).inflate(R.layout.custom_dialog,null))
                this.create().apply {
                    setCanceledOnTouchOutside(false)
                    text_progress_bar.text = t
                }
            }
        }
    }
}
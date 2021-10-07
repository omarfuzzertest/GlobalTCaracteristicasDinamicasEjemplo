package com.omar.fuzzer.caracteristica.video.player

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.omar.fuzzer.caracteristica_video_player.R
import com.omar.fuzzer.caracteristicasdinamicasejemplo.ondemand.utils.BaseSplitActivity

class ReproductorActivity : BaseSplitActivity()  ,MediaPlayer.OnCompletionListener{
    lateinit var vw:VideoView

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor)
        vw = findViewById(R.id.vidvw)
        vw.setMediaController( MediaController(this))
        vw.setOnCompletionListener(this)
        setVideo(R.raw.simpsons)
    }
    private fun setVideo(id: Int) {
        val uriPath = ("android.resource://"
                + packageName) + "/" + id
        val uri: Uri = Uri.parse(uriPath)
        vw.setVideoURI(uri)
        vw.start()
    }

    override fun onCompletion(p0: MediaPlayer?) {
        setResult(RESULT_OK)
        finish()
    }
}


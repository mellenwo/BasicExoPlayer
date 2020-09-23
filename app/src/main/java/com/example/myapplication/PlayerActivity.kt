package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    private var exoPlayer : SimpleExoPlayer? = null

    companion object {
        private const val DISNEY_MEDIA_SOURCE_URI = "https://html5demos.com/assets/dizzy.mp4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        player_view.player = exoPlayer

        val dataSource = DefaultDataSourceFactory(this, Util.getUserAgent(this, "exo-demo"))
        val mediaSource = ExtractorMediaSource.Factory(dataSource).createMediaSource(Uri.parse(
            DISNEY_MEDIA_SOURCE_URI))

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        exoPlayer?.prepare(mediaSource)
        exoPlayer?.playWhenReady = true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    override fun onDestroy() {
        super.onDestroy()
        player_view.player = null
        exoPlayer?.release()
        exoPlayer = null
    }
}
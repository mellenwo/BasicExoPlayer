package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    private var exoPlayer : SimpleExoPlayer? = null

    companion object {
        private const val DISNEY_MEDIA_SOURCE_URI = "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/motion-20120802-manifest.mpd"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        preparePlayer()
    }

    private fun preparePlayer() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        player_view.player = exoPlayer

        val dataSource = createDataSource()
        val mediaSource = createMediaSource(dataSource)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        exoPlayer?.prepare(mediaSource)
        exoPlayer?.playWhenReady = true
    }

    private fun createMediaSource(dataSource: DefaultDataSourceFactory): DashMediaSource? {
        return DashMediaSource(Uri.parse(DISNEY_MEDIA_SOURCE_URI), dataSource, DefaultDashChunkSource.Factory(dataSource), null, null)
    }

    private fun createDataSource() =
        DefaultDataSourceFactory(this, Util.getUserAgent(this, "exo-demo"))

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
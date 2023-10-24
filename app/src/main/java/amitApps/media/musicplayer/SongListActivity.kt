package amitApps.media.musicplayer

import amitApps.media.musicplayer.databinding.ActivitySongListBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager

class SongListActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySongListBinding
    private lateinit var wheelAnimation: Animation

    private val backHandler = Handler(Looper.getMainLooper())
    private var loadingDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding =ActivitySongListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // setup background UI
        setBackground()

        // initialize Element Animation
        initElementAnimation()
    }

    private fun setBackground() {
        viewBinding.root.background = ContextCompat.getDrawable(this, R.drawable.background_music)
        viewBinding.root.background.alpha = 30
    }

    private fun initElementAnimation() {
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
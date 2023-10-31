package amitApps.media.musicplayer

import amitApps.media.musicplayer.databinding.ActivitySongListBinding
import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager

class SongListActivity : BaseSongActivity<SongListPresenter>(), SongListView {
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

        // initialize Animation
        initElementAnimation()


        // initialization of rv
        initRecyclerView()

/*        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) { ... }):
        This code sets up a callback to handle the back button press. It uses the OnBackPressedCallback class
        and is added to the onBackPressedDispatcher for the current activity or fragment.  */

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(backHandler.hasMessages(0)) finish()
                else {
                    showToast(getString(R.string.press_again_to_exit))
                    backHandler.removeCallbacksAndMessages(null)
                    backHandler.postDelayed({}, 2000)
                }
            }
        })
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        loadingDialog = null
        super.onDestroy()
    }

    private fun setBackground() {
        viewBinding.root.background = ContextCompat.getDrawable(this, R.drawable.background_music)
        viewBinding.root.background.alpha = 30
    }

    private fun initElementAnimation() {
        wheelAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation_wheel)
        wheelAnimation.duration = 1000
        wheelAnimation.repeatCount = ValueAnimator.INFINITE
    }

    private fun initRecyclerView() {
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SongListAdapter(presenter)
    }

    override fun playerBound(player: PlayerService) {
        presenter.setPlayerManager(player)

        setListen()
    }

    private fun setListen() {
        viewBinding.edName.addTextChangedListener {
            presenter.filterSong(it.toString())
        }

        viewBinding.imgInfo.setOnClickListener {
            openGithub()
        }

        viewBinding.btnPlay.setOnClickListener {
            presenter.onSongPlay()

            viewBinding.bottomAppBar.performShow()
        }

        viewBinding.bottomAppBar.setOnClickListener {
            if (viewBinding.tvName.text.isNotEmpty() || viewBinding.tvArtist.text.isNotEmpty()) {
                val p1: Pair<View, String> =
                    Pair.create(viewBinding.imgDisc, viewBinding.imgDisc.transitionName)
                val p2: Pair<View, String> =
                    Pair.create(viewBinding.tvName, viewBinding.tvName.transitionName)
                val p3: Pair<View, String> =
                    Pair.create(viewBinding.btnPlay, viewBinding.btnPlay.transitionName)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3)

                startActivity(Intent(this, PlaySongActivity::class.java), options.toBundle())
            }
        }
    }

    private fun openGithub() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/amit/MusicPlayer"))

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(
                this,
                getString(R.string.cant_open_browser),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
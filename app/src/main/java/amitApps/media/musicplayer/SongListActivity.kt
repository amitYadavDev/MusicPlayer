package amitApps.media.musicplayer

import amitApps.media.musicplayer.databinding.ActivitySongListBinding
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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

}
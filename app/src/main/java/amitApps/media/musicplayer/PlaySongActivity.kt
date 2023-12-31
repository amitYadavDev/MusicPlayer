package amitApps.media.musicplayer

import amitApps.media.musicplayer.databinding.ActivityPlaySongBinding
import amitApps.media.musicplayer.player.PlayerManager
import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.beans.PropertyChangeEvent

class PlaySongActivity: BaseSongActivity<PlaySongPresenter>(), PlaySongView {
    //companion object - It is similar to a static member in Java or a class method
    companion object {
        private val STATE_PLAY = intArrayOf(R.attr.state_pause)
        private val STATE_PAUSE = intArrayOf(-R.attr.state_pause)
    }

    private lateinit var viewBinding: ActivityPlaySongBinding
    private lateinit var wheelAnimation: Animation
    private lateinit var scaleAnimation: Animation

/*    "Runnable" is an interface that represents a block of code that can be executed.
    It's often used to perform tasks in a separate thread or to schedule tasks to run after
    a certain delay. You can create a Runnable and then run it on a separate thread using various Android APIs,
    such as Handlers, Threads, or Executors.*/

    private lateinit var seekBarUpdateRunnable: Runnable
    private val seekBarUpdateDelayMillis: Long = 1000

    private lateinit var favoriteAnimationRunnable: Runnable
    private val favoriteAnimationDelayMillis: Long = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPlaySongBinding.inflate(layoutInflater)

        setScreenHigh()
        setContentView(viewBinding.root)

        viewBinding.tvName.isSelected = true

        initWindowAnimations()
    }

    private fun initWindowAnimations() {
//        Shared element transitions allow smooth animations when transitioning
//                between two Activities or Fragments by animating shared views between them.
        val enterTransition = ChangeBounds()
        enterTransition.duration = 1000
        enterTransition.interpolator = DecelerateInterpolator()
        window.sharedElementEnterTransition = enterTransition
    }

    private fun setScreenHigh() {
/*        this code is an example of how you can customize the layout of your Android app to
        work seamlessly with system bars and window insets,
        ensuring a good user experience on devices with various screen sizes and aspect ratios*/
        ViewCompat.setOnApplyWindowInsetsListener(
            viewBinding.root
        ) { view: View, windowInsets: WindowInsetsCompat ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
                // draw on top of the bottom navigation bar
                bottomMargin = insets.bottom
            }

            // Return CONSUMED if you don't want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun playerBound(player: PlayerService) {
        initElementAnimation()

        initFavoriteRunnable()

        initSeekBarUpdateRunnable()

        presenter.setPlayerManager(player)

        setListen()
    }

    private fun setListen() {
        viewBinding.imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initSeekBarUpdateRunnable() {

    }

    private fun initFavoriteRunnable() {
        //Animation for heart in playing song activity
        val position = IntArray(2)
        viewBinding.imgFavorite.getLocationInWindow(position)
        val startPoint = Point((position[0]), (position[1]))

        val favoriteDrawable = viewBinding.imgFavorite.drawable

        favoriteAnimationRunnable = Runnable {
            with(FloatingAnimationView(this)) {
                setImageDrawable(favoriteDrawable)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                layoutParams = LinearLayout.LayoutParams(80, 80)
                startPosition = startPoint
                endPosition = Point(0, 0)

                viewBinding.root.addView(this)

                this.startAnimation()
            }

            viewBinding.imgFavorite.postDelayed(
                favoriteAnimationRunnable,
                favoriteAnimationDelayMillis
            )
        }
    }

    private fun initElementAnimation() {
        //Animation for wheel, when song is playing
        wheelAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation_wheel)
        wheelAnimation.duration = 1000
        wheelAnimation.repeatCount = ValueAnimator.INFINITE

        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        scaleAnimation.duration = 200
        scaleAnimation.repeatCount = 1
        scaleAnimation.repeatMode = Animation.REVERSE
    }

    override fun updateState() {
        presenter.fetchSongState()
    }

    override fun createPresenter(): PlaySongPresenter = PlaySongPresenter(this)

    override fun propertyChange(event: PropertyChangeEvent) {
        when (event.propertyName) {
            PlayerManager.ACTION_PLAY, PlayerManager.ACTION_PAUSE -> {
                updateState()
            }
        }
    }

    override fun updateSongState(song: Song, isPlaying: Boolean, progress: Int) {
        /*In Android, "remove callback" refers to the action of canceling a previously
        scheduled action or task that was set to run on a message queue. A message queue is used to
        manage the order of execution of tasks or actions within a particular thread.
        It's often associated with the UI thread, where user interface operations and animations are handled.*/


        viewBinding.imgFavorite.removeCallbacks(favoriteAnimationRunnable)
        viewBinding.seekBar.removeCallbacks(seekBarUpdateRunnable)

        viewBinding.tvName.text = song.name
        viewBinding.tvDuration.text = TimeUtil.timeMillisToTime(song.duration)
        viewBinding.seekBar.max = (song.duration / 1000).toInt()
        viewBinding.seekBar.progress = progress
        viewBinding.tvProgress.text =
            TimeUtil.timeMillisToTime((viewBinding.seekBar.progress * 1000).toLong())
        viewBinding.imgPlay.setImageState(if (isPlaying) STATE_PLAY else STATE_PAUSE, false)
    }

    override fun showRepeat(isRepeat: Boolean) {
        viewBinding.imgRepeat.imageAlpha = if (isRepeat) 255 else 80
    }

    override fun showRandom(isRandom: Boolean) {
        viewBinding.imgRandom.imageAlpha = if (isRandom) 255 else 80
    }
    override fun onStop() {
        super.onStop()
        viewBinding.imgFavorite.removeCallbacks(favoriteAnimationRunnable)
        viewBinding.seekBar.removeCallbacks(seekBarUpdateRunnable)
    }

}

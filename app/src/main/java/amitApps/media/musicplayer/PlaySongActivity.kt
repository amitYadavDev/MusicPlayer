package amitApps.media.musicplayer

import amitApps.media.musicplayer.databinding.ActivityPlaySongBinding
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
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

    fun playBound(player: PlayerService) {
        TODO("Not yet implemented")
    }

    override fun playerBound(player: PlayerService) {
        TODO("Not yet implemented")
    }

    override fun updateState() {
        TODO("Not yet implemented")
    }

    override fun createPresenter(): PlaySongPresenter {
        TODO("Not yet implemented")
    }

    override fun propertyChange(p0: PropertyChangeEvent?) {
        TODO("Not yet implemented")
    }

    override fun updateSongState(song: Song, isPlaying: Boolean, progress: Int) {
        TODO("Not yet implemented")
    }

    override fun showRepeat(isRepeat: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showRandom(isRandom: Boolean) {
        TODO("Not yet implemented")
    }

}

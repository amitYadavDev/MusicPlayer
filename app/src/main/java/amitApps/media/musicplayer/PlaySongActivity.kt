package amitApps.media.musicplayer

import amitApps.media.musicplayer.databinding.ActivityPlaySongBinding
import java.beans.PropertyChangeEvent

class PlaySongActivity: BaseSongActivity<PlaySongPresenter>(), PlaySongView {
    //companion object - It is similar to a static member in Java or a class method
    companion object {
        private val STATE_PLAY = intArrayOf(R.attr.state_pause)
        private val STATE_PAUSE = intArrayOf(-R.attr.state_pause)
    }

    private lateinit var viewBinding: ActivityPlaySongBinding
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

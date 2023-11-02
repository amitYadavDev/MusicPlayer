package amitApps.media.musicplayer

import java.beans.PropertyChangeEvent

class PlaySongActivity: BaseSongActivity<PlaySongPresenter>(), PlaySongView {
    override fun playBound(player: PlayerService) {
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

package amitApps.media.musicplayer.player

import android.media.MediaPlayer
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class PlayerManager : PropertyChangeSupport(this) {
    companion object {
        const val ACTION_COMPLETE = "action.COMPLETE"
        const val ACTION_PLAY = "action.PLAY"
        const val ACTION_PAUSE = "action.PAUSE"
        const val ACTION_STOP = "action.STOP"
    }

    private val mediaPlayer: MediaPlayer = MediaPlayer()

    // player progress
    private var playerProgress: Int = 0
    init {
        setListen()
    }

    private fun setListen() {
        // FOR MUSIC PLAYING
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.seekTo(playerProgress)
            mediaPlayer.start()
            setChangedNotify(ACTION_PLAY)
        }
    }
}
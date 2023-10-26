package amitApps.media.musicplayer.player

import android.media.MediaPlayer
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.io.FileDescriptor
import timber.log.Timber

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
        mediaPlayer.setOnCompletionListener {
            setChangedNotify(ACTION_COMPLETE)
        }
        mediaPlayer.setOnErrorListener { mediaPlayer, what, extra ->
            Timber.e("MediaPlayer error type:$what, code:$extra, currentPosition:${mediaPlayer.currentPosition}")
            return@setOnErrorListener false
        }
    }
    private fun setChangedNotify(event: String) {
        Timber.i("setChangedNotify  $event")
    }
    fun setPlayerProgress(): Int {
        return if (mediaPlayer.isPlaying) {
            mediaPlayer.currentPosition / 1000
        } else {
            playerProgress / 1000
        }
    }

    private fun play(fileDescriptor: FileDescriptor) {
        if(mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
        mediaPlayer.setDataSource(fileDescriptor)
        mediaPlayer.prepareAsync()
    }

    private fun seekTo(progress: Int) {
        playerProgress = progress * 1000
        mediaPlayer.seekTo(playerProgress)
    }

    private fun pause() {
        playerProgress = mediaPlayer.currentPosition
        if(mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
        setChangedNotify(ACTION_PAUSE)
    }

    private fun stop() {
        if(mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}
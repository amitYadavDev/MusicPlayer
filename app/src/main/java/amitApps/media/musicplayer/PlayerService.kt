package amitApps.media.musicplayer

import amitApps.media.musicplayer.player.PlayerManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.RemoteViews
import timber.log.Timber
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.io.FileDescriptor
import java.nio.channels.Channel
import java.security.Provider.Service

class PlayerService: android.app.Service(), PropertyChangeListener {

    companion object {
        const val CHANNEL_ID_MUSIC = "app.music"
        const val CHANNEL_NAME_MUSIC = "Music"
        const val NOTIFICATION_ID_MUSIC =101

        const val BROADCAST_ID_MUSIC = 201
        const val NOTIFICATION_PREVIOUS = "notification.PREVIOUS"
        const val NOTIFICATION_PLAY = "notification.PLAY"
        const val NOTIFICATION_NEXT = "notification.NEXT"
        const val NOTIFICATION_CANCEL = "notification.CANCEL"

        const val ACTION_FIND_NEW_SONG = "action.FIND_NEW_SONG"
        const val ACTION_NOT_SONG_FOUND = "action.NOT_FOUND"
    }
    // For showing notification
    private lateinit var smallRemoteView: RemoteViews
    private lateinit var largeRemoteViews: RemoteViews

    //
    private lateinit var intentPREVIOUS: PendingIntent
    private lateinit var intentPLAY: PendingIntent
    private lateinit var intentNEXT: PendingIntent
    private lateinit var intentCANCEL: PendingIntent

    private val songList: MutableList<Song> = mutableListOf()
    private val playerManager = PlayerManager()
    private var isPlaying: Boolean = false // mediaPlayer.isPlaying may take some time update status
    private var playerPosition: Int = 0    // song queue position

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            when(intent?.action) {
                NOTIFICATION_PREVIOUS -> skipToPrevious()
                NOTIFICATION_PLAY -> {
                    if(isPlaying) {
                        pause()
                    } else {
                        play()
                    }
                }
                NOTIFICATION_NEXT -> skipToNext()
                NOTIFICATION_CANCEL -> {
                    pause()
                    stopForeground(true)

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        stopForeground(STOP_FOREGROUND_DETACH)
                    }

                    // release service from the background
                    stopSelf()
                }

            }
        }
    }

    private fun pause() {
        isPlaying = false
        playerManager.pause()
    }

    private fun play(position: Int = playerPosition) {
        isPlaying = true

        // is diff song
        if(position != playerPosition) {
            playerManager.setPlayerProgress(0)
        }

        playerPosition = when {
            songList.size < 1 -> {
                playerManager.setChangedNotify(ACTION_NOT_SONG_FOUND)
                return
            }
            position >= songList.size -> 0
            position < 0 ->songList.lastIndex
            else -> position
        }

        val audioUri = Uri.withAppendedPath(uriExternal, songList[playerPosition].id)

        contentResolver.openFileDescriptor(audioUri, "r")?.use {
            playerManager.play(it.fileDescriptor)
        } ?: kotlin.run {
            songList.removeAt(playerPosition)
            playerManager.setChangedNotify(ACTION_NOT_SONG_FOUND)

            play()
        }
    }

    // Accessing Audio file from local storage
    private val metadataRetriever = MediaMetadataRetriever()
    private val uriExternal: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI



    private val mHandler: Handler = Handler(Looper.getMainLooper()) { msg ->
        val id = msg.data.getString("songID")
        val audioUri = Uri.withAppendedPath(uriExternal, id)

        try {
            contentResolver.openFileDescriptor(audioUri, "r")?.use {
                if(addSong(it.fileDescriptor, id!!, getSongTitle(audioUri))) {
                    playerManager.setChangedNotify(ACTION_FIND_NEW_SONG)
                }
            }
        } catch (e : Exception) {
            Timber.e(e)
        }
        true
    }

    private fun getSongTitle(audioUri: Uri): String {
        var title = audioUri.lastPathSegment

        // contentResolver is way to interact with data stored on the device.
        //It acts as a bridge between your Android application and the data stored in various content providers.

        contentResolver.query(audioUri, null, null, null, null)?.use {
            if(it.moveToNext()) {
                title = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
            }
        }
        return title ?: ""

    }


    private fun skipToPrevious() {
        TODO("Not yet implemented")
    }
    private fun addSong(fileDescriptor: FileDescriptor, id: String, songTitle: String): Boolean {
        try {
            if(fileDescriptor.valid()) {
                return false
            }
            // fileDescriptor is media file to retrieve metadata
            metadataRetriever.setDataSource(fileDescriptor)

            // retrieving the metadata from storage
            val duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            val author = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR)

            if(duration.isNullOrEmpty()) {
                return false
            }

            // storing data using data class
            val song = Song(
                id,
                songTitle,
                artist ?: author ?: getString(R.string.unknown),
                duration.toLong()
            )
            // adding song to the list
            if(!songList.contains(song)) {
                songList.add(song)
            }
        } catch (e: Exception) {
            Timber.e(e)
            return false
        }
        return true
    }

}

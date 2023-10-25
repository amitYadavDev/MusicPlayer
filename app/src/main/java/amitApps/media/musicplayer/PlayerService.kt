package amitApps.media.musicplayer

import android.app.PendingIntent
import android.content.Intent
import android.widget.RemoteViews
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
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







}

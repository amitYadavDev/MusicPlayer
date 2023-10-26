package amitApps.media.musicplayer

import android.database.ContentObservable
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message

/* In Android, a ContentObserver is a class used to monitor changes to
data that is managed by a content provider. Content providers
are used to store and manage structured data, such as contacts,
calendar events, or media files, and they allow different apps to share and access this data.
A ContentObserver allows you to listen for changes in this data and react accordingly.*/
class AudioObserver (private val handler: Handler): ContentObserver(handler) {

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)

        if(selfChange) return

        uri?.lastPathSegment?.let {
            val b = Bundle()
            b.putString("songId", it)
            val msg = Message()
            msg.data = b

            handler.sendMessage(msg)
        }
    }

}

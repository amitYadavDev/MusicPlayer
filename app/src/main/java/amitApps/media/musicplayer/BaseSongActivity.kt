package amitApps.media.musicplayer

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import java.beans.PropertyChangeListener

open class BaseSongActivity<P: BasePresenter<*>>: BaseActivity<P>(), PropertyChangeListener {
    private val REQUEST_WRITE_EXTERNAL_STORAGE: Int = 10
    private val REQUEST_READ_MEDIA_AUDIO: Int = 11

    private lateinit var player: PlayerService

    private var isBound: Boolean = false

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
            val localBinder = binder as PlayerService.LocalBinder

            localBinder.service
        }
    }

}

package amitApps.media.musicplayer

import android.Manifest
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import java.beans.PropertyChangeListener

abstract class BaseSongActivity<P: BasePresenter<*>>: BaseActivity<P>(), PropertyChangeListener {
    private val REQUEST_WRITE_EXTERNAL_STORAGE: Int = 10
    private val REQUEST_READ_MEDIA_AUDIO: Int = 11

    private lateinit var player: PlayerService

    private var isBound: Boolean = false

//    In Android, a ServiceConnection is used to establish a connection between a
//    client component (such as an Activity or Service) and a bound service

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
            val localBinder = binder as PlayerService.LocalBinder

            localBinder.service?.let {
                player = it
                //this@BaseSongActivity is typically used to refer to the instance of the BaseSongActivity class
                player.addPlayerObserver(this@BaseSongActivity)
                isBound = true
                playerBound(player)
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!hasPermission(Manifest.permission.READ_MEDIA_AUDIO)) {
                requestPermission(REQUEST_READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_AUDIO)
            }
        } else if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermission(REQUEST_WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    abstract fun playerBound(player: PlayerService)

    abstract fun updateState()

}

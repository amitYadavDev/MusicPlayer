package amitApps.media.musicplayer

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<P : BasePresenter<*>> : AppCompatActivity(),
    BaseView {

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

/*    lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) checks
    if the component's current state is at least in the "STARTED" state or a more advanced state
    (e.g., "RESUMED"). If it is, the condition will evaluate to true. This is often used to determine
    if certain actions should be taken when a component is in a certain state, like starting or stopping background
    tasks when an activity is at least in the "STARTED" state.    */
    override fun isActive(): Boolean = lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    override fun showSnackBar(v: View, msg: String) {
        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun context(): Context = this

    protected fun hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    protected fun requestPermission(requestCode: Int, vararg permissions: String) {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    protected abstract fun createPresenter(): P

}
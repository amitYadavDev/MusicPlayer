package amitApps.media.musicplayer

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<P : BasePresenter<*>> : AppCompatActivity(),
    BaseView {

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    override fun showSnackBar(v: View, msg: String) {
        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun context(): Context = this


    protected abstract fun createPresenter(): P

}
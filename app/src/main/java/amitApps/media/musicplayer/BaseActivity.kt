package amitApps.media.musicplayer

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<P : BasePresenter<*>> : AppCompatActivity(),
    BaseView {

    protected lateinit var presenter: P

}
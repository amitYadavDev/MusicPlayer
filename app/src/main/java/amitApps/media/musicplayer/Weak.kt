package amitApps.media.musicplayer

import timber.log.Timber
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class Weak<T : Any>(initializer: () -> T?) {
    var weakReference = WeakReference<T?>(initializer())

    constructor() : this({ null })


}
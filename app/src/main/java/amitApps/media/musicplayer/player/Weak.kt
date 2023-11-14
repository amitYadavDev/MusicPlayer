package amitApps.media.musicplayer.player

import timber.log.Timber
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty


/*Weak<T : Any>: This is the class definition. It's defining a class named Weak that takes
a single generic type parameter T. The : Any constraint means that T must be a non-nullable reference type,
which effectively allows any non-nullable class as T. This constraint ensures that T is not a nullable type.*/
class Weak <T : Any>(initializer: () -> T?){
    var weakReference = WeakReference<T?>(initializer())

    constructor() : this({ null })

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        Timber.d("Weak Delegate getValue")
        return weakReference.get()
    }


    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        Timber.d("Weak Delegate setValue")
        weakReference = WeakReference(value)
    }
}
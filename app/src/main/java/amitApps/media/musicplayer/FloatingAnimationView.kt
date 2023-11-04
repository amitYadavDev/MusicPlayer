package amitApps.media.musicplayer

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.AttributeSet
import java.util.Random

class FloatingAnimationView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    lateinit var startPosition: Point
    lateinit var endPosition: Point

    private val random = Random()





    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

}

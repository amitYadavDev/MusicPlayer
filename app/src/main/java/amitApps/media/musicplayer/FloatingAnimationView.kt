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

    fun startAnimation() {
        val width = getScreenWidth()
        val height = getScreenHeight()

//        random.nextInt(width): This part generates a random integer
//        between 0 (inclusive) and the value of the width variable (exclusive)
        val endPointRandom =
            Point(random.nextInt(width), endPosition.y)

//        bezierTypeEvaluator is an instance of a BezierEvaluator class or
//        function that uses random control points for defining Bezier curves.
//        Bezier curves are often used in graphics and animation to create smooth and curved paths
//        for objects or animations

        val bezierTypeEvaluator = BezierEvaluator(
            Point(random.nextInt(width), random.nextInt(height)),
            Point(random.nextInt(width / 2), random.nextInt(height / 2))
        )
    }


//    This function is designed to return the width of the screen in pixels
    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

}

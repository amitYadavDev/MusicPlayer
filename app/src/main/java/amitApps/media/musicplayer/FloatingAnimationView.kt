package amitApps.media.musicplayer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
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

/*        ValueAnimator: This is a class in Android used for animating between different values over time.
        It can animate various types of values, and in this case, it's animating between the
        startPosition and endPointRandom using a custom evaluator.*/
        val animator = ValueAnimator.ofObject(bezierTypeEvaluator, startPosition, endPointRandom)
        animator.addUpdateListener { valueAnimator ->
            val point = valueAnimator.animatedValue as Point
            val fraction = valueAnimator.animatedFraction

            x = point.x.toFloat()
            y = point.y.toFloat()
            alpha = 1 - fraction

            invalidate()
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                val viewGroup = parent as ViewGroup
                viewGroup.removeView(this@FloatingAnimationView)
            }
        })

        animator.duration = 2000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }


//    This function is designed to return the width of the screen in pixels
    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

}

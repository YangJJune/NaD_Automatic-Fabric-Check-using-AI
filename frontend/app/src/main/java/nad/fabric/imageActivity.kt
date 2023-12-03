package nad.fabric

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import nad.fabric.databinding.ActivityImageBigBinding
import nad.fabric.databinding.ActivityInfoBinding

class imageActivity: AppCompatActivity() {
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f
    lateinit var binding: ActivityImageBigBinding
    private var isGrey = false
    private var isInvert = false

    var contrast = 1f // 0f..10f (1 should be default)
    var brightness = 0f // -255f..255f (0 should be default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBigBinding.inflate(layoutInflater)
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        setContentView(binding.root)
        val path = intent.getStringExtra("image_path")

        Glide.with(this).load("http://49.173.62.69:8080/"+path).into(binding.imageView7)
        binding.button2.setOnClickListener {
            finish()
        }
        binding.button7.setOnClickListener {
            if(isGrey){
                binding.imageView7.setColorFilter(null)
                isGrey = false
            }else{
                val matrix:ColorMatrix = ColorMatrix()
                matrix.setSaturation(0f);

                val filter = ColorMatrixColorFilter(matrix)
                binding.imageView7.setColorFilter(filter)
                isGrey = true
            }
        }
        binding.button8.setOnClickListener {
            brightness+=20f
            if(brightness>=255f){
                brightness=-255f
            }
            val colorMatrix = floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
            val filter = ColorMatrixColorFilter(colorMatrix)
            binding.imageView7.setColorFilter(filter)
        }
        binding.button6.setOnClickListener {
            if(isInvert){
                binding.imageView7.setColorFilter(null)
                isInvert = false
            }else {
                val colorMatrix = floatArrayOf(
                    -1f, 0f, 0f, 0f, 255f,
                    0f, -1f, 0f, 0f, 255f,
                    0f, 0f, -1f, 0f, 255f,
                    0f, 0f, 0f, 1f, 0f
                )
                val filter = ColorMatrixColorFilter(colorMatrix)
                binding.imageView7.setColorFilter(filter)
                var brightness = 0f
                isInvert = true
            }
        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {

        if (motionEvent != null) {
            mScaleGestureDetector!!.onTouchEvent(motionEvent)
        }
        return true
    }


    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {

            scaleFactor *= scaleGestureDetector.scaleFactor

            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 2.0f))

            binding.imageView7.scaleX = scaleFactor
            binding.imageView7.scaleY = scaleFactor
            return true
        }
    }
}
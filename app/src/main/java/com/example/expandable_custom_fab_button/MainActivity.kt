package com.example.expandable_custom_fab_button

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import com.example.expandable_custom_fab_button.databinding.ActivityMainBinding
import java.time.Duration

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isExpanded = false

    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_clock_wise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_anti_clock_wise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFabBtn.setOnClickListener {
            if (isExpanded) {
                shrinkFab()
            } else {
                expandFab()
            }
        }

        binding.galleryFabBtnLayout.setOnClickListener {
            "Gallery".toast(this)
        }
        binding.shareFabBtnLayout.setOnClickListener {
            "Share".toast(this)
        }
        binding.sendFabBtnLayout.setOnClickListener {
            "Send".toast(this)
        }

    }

    private fun expandFab() {
        binding.transparentBg.startAnimation(fromBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateClockWiseFabAnim)
        binding.galleryFabBtnLayout.startAnimation(fromBottomFabAnim)
        binding.shareFabBtnLayout.startAnimation(fromBottomFabAnim)
        binding.sendFabBtnLayout.startAnimation(fromBottomFabAnim)

        isExpanded = !isExpanded
    }

    private fun shrinkFab() {
        binding.transparentBg.startAnimation(toBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        binding.galleryFabBtnLayout.startAnimation(toBottomFabAnim)
        binding.shareFabBtnLayout.startAnimation(toBottomFabAnim)
        binding.sendFabBtnLayout.startAnimation(toBottomFabAnim)

        isExpanded = !isExpanded
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            if (isExpanded) {
                val outRect = Rect()
                binding.fabConstraint.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    shrinkFab()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        if (isExpanded) {
            shrinkFab()
        } else {
            super.onBackPressed()
        }
    }


}

fun String.toast(ctx: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(ctx, this, duration).show()
}
package com.app.immediateedge.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.immediateedge.presenter.MainPresenter
import com.app.immediateedge.R
import com.app.immediateedge.databinding.ActivityGameBinding
import com.wajahatkarim3.easyflipview.EasyFlipView
import kotlin.random.Random


class GameActivity : AppCompatActivity() {


//    private val viewModel: MainPresenter by viewModels()
    private lateinit var binding: ActivityGameBinding
    private var left: Int? = null
    private var centre: Int? = null
    private var right: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val easyFlipViewOne = binding.easyFlipViewOne
        val easyFlipViewTwo = binding.easyFlipViewTwo
        val easyFlipViewThree = binding.easyFlipViewThree

        val slotItems = listOf(
            R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
            R.drawable.i4,
            R.drawable.i5,
            R.drawable.i6,
            R.drawable.i7
        )



        binding.apply {
            btnFirst.setOnClickListener {
                easyFlipViewOne.flipTheView()
                if (easyFlipViewOne.currentFlipState == EasyFlipView.FlipState.BACK_SIDE) {
                    left = slotItems[Random.nextInt(slotItems.size)]
                    includesBackOne.imageView
                        .setImageResource(left!!)
                    btnFirst.isEnabled = false
                    checkWin()
                }

            }

            btnSecond.setOnClickListener {
                easyFlipViewTwo.flipTheView()
                if (easyFlipViewTwo.currentFlipState == EasyFlipView.FlipState.BACK_SIDE) {
                    centre = slotItems[Random.nextInt(slotItems.size)]
                    includesBackTwo.imageView.setImageResource(centre!!)
                    btnSecond.isEnabled = false
                    checkWin()
                }
            }

            btnFree.setOnClickListener {
                easyFlipViewThree.flipTheView()
                if (easyFlipViewThree.currentFlipState == EasyFlipView.FlipState.BACK_SIDE) {
                    right = slotItems[Random.nextInt(slotItems.size)]
                    includesBackThree.imageView.setImageResource(right!!)
                    btnFree.isEnabled = false
                    checkWin()
                }

                btnPlay.setOnClickListener {
                    binding.easyFlipViewOne.flipTheView()
                    binding.easyFlipViewTwo.flipTheView()
                    binding.easyFlipViewThree.flipTheView()

                    binding.btnFirst.isEnabled = true
                    binding.btnSecond.isEnabled = true
                    binding.btnFree.isEnabled = true
                }
            }
        }
    }

    private fun checkWin() {
        if (left == centre && centre == right) {
            binding.txtPlay.text = "You win !!!"
        } else {
            binding.txtPlay.text = "Play"
        }

        if (left == null || centre == null || right == null)
            return

        left = null
        centre = null
        right = null

    }

}
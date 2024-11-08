package com.example.rolldicekotlin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rolldicekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val diceViewModel: DiceViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())
    private val rollRunnable = object : Runnable {
        override fun run() {
            diceViewModel.rollDice()
            if (diceViewModel.isRolling.value == true) {
                handler.postDelayed(this, 300)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        diceViewModel.diceImages.observe(this) { diceImages ->
            binding.imageView1.setImageResource(diceImages[0])
            binding.imageView2.setImageResource(diceImages[1])
            binding.imageView3.setImageResource(diceImages[2])
            binding.imageView4.setImageResource(diceImages[3])
            binding.imageView5.setImageResource(diceImages[4])
        }

        binding.button.setOnClickListener {
            diceViewModel.startRolling()
            handler.post(rollRunnable)
            binding.button.isEnabled = false
            binding.button2.isEnabled = true
        }

        binding.button2.setOnClickListener {
            diceViewModel.stopRolling()
            handler.removeCallbacks(rollRunnable)
            binding.button.isEnabled = true
            binding.button2.isEnabled = false
        }

        if (savedInstanceState != null) {
            val isRolling = savedInstanceState.getBoolean("isRolling", false)
            if (isRolling) {
                diceViewModel.startRolling()
                handler.post(rollRunnable)
                binding.button.isEnabled = false
                binding.button2.isEnabled = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isRolling", diceViewModel.isRolling.value == true)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(rollRunnable)
    }
}

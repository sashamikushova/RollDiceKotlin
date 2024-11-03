package com.example.rolldicekotlin
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rolldicekotlin.databinding.ActivityMainBinding
import android.content.res.Configuration

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
        }

        binding.button2.setOnClickListener {
            diceViewModel.stopRolling()
            handler.removeCallbacks(rollRunnable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(rollRunnable)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }
}

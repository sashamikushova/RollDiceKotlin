package com.example.rolldicekotlin
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class DiceViewModel : ViewModel() {

    private val _diceImages = MutableLiveData<List<Int>>()
    val diceImages: LiveData<List<Int>> get() = _diceImages

    private val _isRolling = MutableLiveData(false)
    val isRolling: LiveData<Boolean> get() = _isRolling

    private val diceDrawables = listOf(
        R.drawable.one, R.drawable.two, R.drawable.three,
        R.drawable.four, R.drawable.five
    )

    fun rollDice() {
        _diceImages.value = List(5) { diceDrawables[Random.nextInt(diceDrawables.size)] }
    }

    fun startRolling() {
        _isRolling.value = true
    }

    fun stopRolling() {
        _isRolling.value = false
    }
}


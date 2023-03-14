package com.hegesoftware.snookerscore.data

import android.content.Context
import com.hegesoftware.snookerscore.domain.models.Settings
import javax.inject.Inject

class SharedPref @Inject constructor(
    context: Context
) {
    private val fileKey = "fi.hegesoft.snookerscore.SHARED_PREFERENCES"
    private val sharedPref = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE)

    fun getPlayerNames(): Pair<String, String> {
        val player1 = sharedPref.getString("player1Name", "") ?: ""
        val player2 = sharedPref.getString("player2Name", "") ?: ""
        return Pair(player1, player2)
    }

    fun setPlayerNames(player1Name: String, player2Name: String) {
        with(sharedPref.edit()) {
            putString("player1Name", player1Name)
            putString("player2Name", player2Name)
            apply()
        }
    }

    fun getGameScore(): Pair<Int, Int> {
        val player1 = sharedPref.getInt("player1GameScore", 0)
        val player2 = sharedPref.getInt("player2GameScore", 0)
        return Pair(player1, player2)
    }

    fun addFrameWin(playerWhoWon: Int) {
        var (player1GameScore, player2GameScore) = getGameScore()
        if (playerWhoWon == 1) player1GameScore += 1 else player2GameScore += 1
        with(sharedPref.edit()) {
            putInt("player1GameScore", player1GameScore)
            putInt("player2GameScore", player2GameScore)
            apply()
        }
    }

    fun getGameExistence(): Boolean {
        return sharedPref.getBoolean("gameExists", false)
    }

    fun setGameToExist() {
        with(sharedPref.edit()) {
            putBoolean("gameExists", true)
            apply()
        }
    }

    fun getFrameExistence(): Boolean {
        return sharedPref.getBoolean("frameExists", false)
    }

    fun setFrameExistance(frameExists: Boolean) {
        with(sharedPref.edit()) {
            putBoolean("frameExists", frameExists)
            apply()
        }
    }

    fun resetGame() {
        with(sharedPref.edit()) {
            putInt("player1GameScore", 0)
            putInt("player2GameScore", 0)
            putString("player1Name", "")
            putString("player2Name", "")
            putBoolean("gameExists", false)
            putBoolean("frameExists", false)
            apply()
        }
    }

    fun getSettings(): Settings {
        val dragEnabled = sharedPref.getBoolean("dragEnabled", true)
        val sloppyPressEnabled = sharedPref.getBoolean("sloppyPressEnabled", true)
        val showRedsRemaining = sharedPref.getBoolean("showRedsRemaining", false)

        return Settings(
            swipingEnabled = dragEnabled,
            sloppyPressEnabled = sloppyPressEnabled,
            showRedsRemaining = showRedsRemaining
        )
    }

    fun setSettings(settings: Settings) {
        with(sharedPref.edit()) {
            putBoolean("dragEnabled", settings.swipingEnabled)
            putBoolean("sloppyPressEnabled", settings.sloppyPressEnabled)
            putBoolean("showRedsRemaining", settings.showRedsRemaining)
            apply()
        }
    }
}

fun Pair<Int, Int>.sum() = this.first + this.second
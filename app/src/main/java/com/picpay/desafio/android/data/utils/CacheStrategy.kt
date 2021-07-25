package com.picpay.desafio.android.data.utils

import android.util.Log

private const val TAG = "CacheStrategy"

object CacheStrategy {
    private const val cacheTime =  60000 //300000

    fun isCacheExpired(lastUpdate: Long?): Boolean {
        if (lastUpdate == null) return true

        val now = System.currentTimeMillis()
        val compare = now - lastUpdate
        Log.d(TAG, "Time from last update: $compare milliseconds. (${compare/1000} seconds)")
        return compare >= cacheTime
    }
}
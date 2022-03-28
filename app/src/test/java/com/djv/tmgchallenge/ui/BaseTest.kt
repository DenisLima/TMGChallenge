package com.djv.tmgchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

open class BaseTest {

    inline fun <reified T > LiveData<T>.captureValues(): List<T?> {
        val mockObserver = mockk<Observer<T>>()
        val list = mutableListOf<T?>()
        every { mockObserver.onChanged(captureNullable(list))} just runs
        this.observeForever(mockObserver)
        return list
    }

    fun <T> LiveData<T>.blockingObserve(): T? {
        val countDownLatch = CountDownLatch(1)

        var result: T? = null
        val observer = Observer<T> {
            result = it
            countDownLatch.countDown()
        }

        observeForever(observer)

        countDownLatch.await(2, TimeUnit.SECONDS)
        return result
    }
}
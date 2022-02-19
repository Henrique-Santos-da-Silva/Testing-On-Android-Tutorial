package com.youtube.tutorials.testingonandroidtutorial

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class HomeWorkTest {

    @Test
    fun `addition is incorrect`() {
        val homeWorkFib: Long = HomeWork.fib(2)

        assertThat(homeWorkFib).isNotEqualTo(2)
    }

    @Test
    fun `addition is correct`() {
        val homeWorkFib: Long = HomeWork.fib(2)
        assertThat(homeWorkFib).isEqualTo(1)
    }

    @Test
    fun `incorrectly braces return false`() {
        val homeWorkBraces: Boolean = HomeWork.checkBraces("(a * b))")
        assertThat(homeWorkBraces).isFalse()
    }

    @Test
    fun `correctly braces return true`() {
        val homeWorkBraces: Boolean = HomeWork.checkBraces("(a * b)")
        assertThat(homeWorkBraces).isTrue()
    }

    @Test
    fun `is inverted braces return false`() {
        val homeWorkBraces: Boolean = HomeWork.checkBraces(")a * b(")
        assertThat(homeWorkBraces).isFalse()
    }

}
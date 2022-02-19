package com.youtube.tutorials.testingonandroidtutorial

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result: Boolean = RegistrationUtil.validateRegistrationInput("", "123", "123")
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result: Boolean = RegistrationUtil.validateRegistrationInput("Philipp", "123", "123")
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exists returns false`() {
        val result: Boolean = RegistrationUtil.validateRegistrationInput("Carl", "123", "123")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result: Boolean = RegistrationUtil.validateRegistrationInput("Phillipp", "", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly confirmed password returns false`() {
        val result: Boolean = RegistrationUtil.validateRegistrationInput("Phillipp", "12345678", "abcdefg")
        assertThat(result).isFalse()
    }

    @Test
    fun `less than 2 digit password returns false`() {
        val result: Boolean = RegistrationUtil.validateRegistrationInput("Phillipp", "abcdefg5", "abcdefg5")
        assertThat(result).isFalse()
    }

}
package com.youtube.tutorials.testingonandroidtutorial

object RegistrationUtil {

    private val existingUsers: List<String> = listOf("Peter", "Carl")

    /*
    * Como será usado o conceito de TDD, todos os teste de inicio irá falhar,
    * porém é preciso imaginar quando um entrada é válida ou inválida.
    *
    * ... Nesse caso a entrada é inválida, caso o username/password sejam vazios
    * ... username já foi usado
    * ... confirmedPassword não é igual a password
    * ... password contem menos de 2 números digitados no password
    * */

    fun validateRegistrationInput(username: String, password: String, confimedPassword: String): Boolean {
        if (username.isEmpty() || password.isEmpty()) return false
        if (username in existingUsers) return false
        if (password != confimedPassword)  return false
        if (password.count { it.isDigit() } < 2) return false

        return true
    }
}
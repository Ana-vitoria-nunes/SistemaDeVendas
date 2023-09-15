package model

class InputUserModel {
    companion object {
        fun readIntFromUser(mensagem: String): Int {
            while (true) {
                println(mensagem)
                val input = readlnOrNull()
                try {
                    return input?.toInt() ?: throw NumberFormatException()
                } catch (e: NumberFormatException) {
                    println("Entrada inválida. Por favor, digite um número inteiro válido.")
                }
            }
        }
        fun readStringFromUser(mensagem: String): String {
            while (true) {
                println(mensagem)
                val input = readlnOrNull()
                try {
                    return input ?: throw NumberFormatException()
                } catch (e: NumberFormatException) {
                    println("Entrada inválida. Por favor, digite um número válido.")
                }
            }
        }
        fun readDoubleFromUser(mensagem: String): Double {
            while (true) {
                println(mensagem)
                val input = readlnOrNull()
                try {
                    return input?.toDouble() ?: throw NumberFormatException()
                } catch (e: NumberFormatException) {
                    println("Entrada inválida. Por favor, digite um número válido.")
                }
            }
        }
    }
}

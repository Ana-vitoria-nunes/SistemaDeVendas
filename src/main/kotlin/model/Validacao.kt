package model

import connection.Connect
import java.sql.SQLException

class Validacao {
    private val connection = Connect().creatConnect()

    // Validar ID
    fun isValidVendedorId(id: Int): Boolean {
        val sql = "SELECT COUNT(*) FROM vendedor WHERE id_vendedor=?"

        try {
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            val count = resultSet.getInt(1)

            resultSet.close()
            preparedStatement.close()

            return count > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return false
    }

    fun isValidClienteId(id: Int): Boolean {
        val sql = "SELECT COUNT(*) FROM cliente WHERE id_cliente=?"

        try {
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            val count = resultSet.getInt(1)

            resultSet.close()
            preparedStatement.close()

            return count > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return false
    }

    fun isValidProdutoId(id: Int): Boolean {
        val sql = "SELECT COUNT(*) FROM produto WHERE id_produto=?"

        try {
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            val count = resultSet.getInt(1)

            resultSet.close()
            preparedStatement.close()

            return count > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return false
    }

    fun isValidVendaId(id: Int): Boolean {
        val sql = "SELECT COUNT(*) FROM venda WHERE id_venda = ?"

        try {
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            val count = resultSet.getInt(1)

            resultSet.close()
            preparedStatement.close()

            return count > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return false
    }


    //Validar Indormações nulas ou vazias
    fun isValidClienteInfo(nome: String, email: String, cpf: String, endereco: String): Boolean {
        return nome.isNotBlank() && email.isNotBlank() && cpf.isNotBlank() && endereco.isNotBlank()
    }

    fun isValidVendedorInfo(nome: String, email: String, cpf: String): Boolean {
        return cpf.isNotBlank() && nome.isNotBlank() && email.isNotBlank() && cpf.isNotBlank()
    }

    fun isValidProdutoInfo(nome: String): Boolean {
        return nome.isNotBlank()
    }

    // Validar entrada de email
//    fun isValidEmail(email: String): Boolean {
//        return email.contains("@")
//    }
    fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@(.+)\$")
        return regex.matches(email)
    }

    // Validar crdencial de Usuario e Bibliotecario
    private fun isAdmin(alias: String): Boolean {
        return alias == "Danilo" || alias == "Maria"
    }

    fun isValidLibrarianCredentials(alias: String, senha: String): Boolean {
        if (alias.isBlank() || senha.isBlank()) {
           println("O nome de Gerente e a senha não podem estar vazios.")
        }
        if (isAdmin(alias)) {
            val sql = "SELECT COUNT(*) FROM vendedor WHERE nome_vendedor=? AND senha_vendedor=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setString(1, alias)
                preparedStatement.setString(2, senha)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                if (count > 0) {
                    return true
                } else {
                   println("Credenciais de gerente inválidas.")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
               println("Erro ao verificar as credenciais do usuário.")
            }
        } else {
            println("Acesso não autorizado. Você não é um gerente.")
        }
        return false
    }

    fun isValidUserCredentials(alias: String, senha: String): Boolean {
        if (alias.isBlank() || senha.isBlank()) {
            println("O nome de Vendedor e a senha não podem estar vazios.")
            return false
        }

        val sql = "SELECT COUNT(*) FROM vendedor WHERE nome_vendedor=? AND senha_vendedor=?"

        try {
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, alias)
            preparedStatement.setString(2, senha)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            val count = resultSet.getInt(1)

            resultSet.close()
            preparedStatement.close()

            return count > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return false
    }
}

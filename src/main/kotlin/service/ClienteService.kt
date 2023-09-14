package service

import connection.Connect
import java.sql.SQLException

class ClienteService {
    var connection = Connect().creatConnect()
   // private val getDataBaseService = GetDataBaseService()

    fun addCliente( nome:String,email:String,cpf:String,endereco:String) {
        try {
//            if (!isValidBookInfo(isbn, name_book)) {
//                println("As informações do livro não podem estar vazias ou nulas.")
//                return
//            }
            val sql =
                "INSERT INTO cliente (nome_cliente, email_cliente, cpf_cliente,endereco_cliente) VALUES ('$nome', '$email', '$cpf','$endereco')"

            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Cliente $nome adicionado com sucesso!")

        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun deleteCliente(id: Int) {
//        if (!isValidBookId(id)) {
//            println("ID de livro inválido!")
//            return
//        }
        val sql =
            "DELETE FROM cliente WHERE id=$id"

        try {
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Cliente deletado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun updateCliente(id: Int,email:String,endereco:String) {
        try {
//            if (!isValidBookId(id)) {
//                println("ID de livro inválido!")
//                return
//            }
//            if (!isValidBookInfo(isbn, name_book)) {
//                println("As informações do livro não podem estar vazias ou nulas.")
//                return
//            }
//            if (!isValidAuthorId(id)) {
//                println("ID de autor inválido!")
//                return
//            }
            val sql =
                "UPDATE cliente SET email_cliente='$email', endereco_cliente='$endereco' WHERE id=$id"
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Cliente com id $id atualizado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun listCliente() {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT id_cliente, nome_cliente, email_cliente, cpf_cliente,endereco_cliente FROM cliente")

        try {
            while (resultSet.next()) {
                val id=resultSet.getInt("id_cliente")
                val nome = resultSet.getString("nome_cliente")
                val email = resultSet.getString("email_cliente")
                val cpf = resultSet.getString("cpf_cliente")
                val endereco = resultSet.getString("endereco_cliente")

               // val authorName = getDataBaseService.getAuthorName(id_author)

                println("ID: $id | Nome : $nome | E-mail : $email |Cpf: $cpf  | Endereço: $endereco")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        println()
    }

    fun listSpecificBook(id: Int) {
//        if (!isValidBookId(idBook)) {
//            println("ID de livro inválido!")
//            return
//        }
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM cliente WHERE id=$id")

        try {
            while (resultSet.next()) {
                val id=resultSet.getInt("id_cliente")
                val nome = resultSet.getString("nome_cliente")
                val email = resultSet.getString("email_cliente")
                val cpf = resultSet.getString("cpf_cliente")
                val endereco = resultSet.getString("endereco_cliente")

                // val authorName = getDataBaseService.getAuthorName(id_author)

                println("ID: $id | Nome : $nome | E-mail : $email |Cpf: $cpf  | Endereço: $endereco")
            }
            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
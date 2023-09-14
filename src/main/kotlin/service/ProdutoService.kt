package service

import connection.Connect
import java.sql.SQLException

class ProdutoService {
    var connection = Connect().creatConnect()
    // private val getDataBaseService = GetDataBaseService()

    fun addProduto( nome:String,preco:Int) {
        try {
//            if (!isValidBookInfo(isbn, name_book)) {
//                println("As informações do livro não podem estar vazias ou nulas.")
//                return
//            }
            val sql =
                "INSERT INTO produto (nome_produto, preco_unit) VALUES ('$nome', '$preco')"

            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Produto $nome adicionado com sucesso!")

        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun deleteProduto(id: Int) {
//        if (!isValidBookId(id)) {
//            println("ID de livro inválido!")
//            return
//        }
        val sql =
            "DELETE FROM produto WHERE id=$id"

        try {
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Produto deletado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun updateProduto(id: Int,preco:Int) {
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
                "UPDATE produto SET preco_unit='$preco' WHERE id=$id"
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Produto com id $id atualizado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun listProduto() {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT id_produto,nome_produto, preco_unit FROM produto")

        try {
            while (resultSet.next()) {
                val id=resultSet.getInt("id_produto")
                val nome = resultSet.getString("nome_produto")
                val preco = resultSet.getInt("preco_unit")

                // val authorName = getDataBaseService.getAuthorName(id_author)

                println("ID: $id | Nome : $nome | Preço : $preco")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        println()
    }

    fun listSpecificProduto(id: Int) {
//        if (!isValidBookId(idBook)) {
//            println("ID de livro inválido!")
//            return
//        }
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM produto WHERE id=$id")

        try {
            while (resultSet.next()) {
                val id=resultSet.getInt("id_produto")
                val nome = resultSet.getString("nome_produto")
                val preco = resultSet.getInt("preco_unit")

                // val authorName = getDataBaseService.getAuthorName(id_author)

                println("ID: $id | Nome : $nome | Preço : $preco")
            }
            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
package service

import connection.Connect
import model.Validacao
import java.sql.SQLException

class ProdutoService {
    companion object {
        var connection = Connect().creatConnect()
        var validacao = Validacao()
        fun addProduto(nome: String, preco: Double) {
            try {
                if (!validacao.isValidProdutoInfo(nome)) {
                    println("As informações do Produto não podem estar vazias ou nulas.")
                    return
                }
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
            if (!validacao.isValidProdutoId(id)) {
                println("ID de Produto inválido!")
                return
            }
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

        fun updateProduto(id: Int, preco: Double) {
            try {
                if (!validacao.isValidProdutoId(id)) {
                    println("ID de Produto inválido!")
                    return
                }
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
                    val id = resultSet.getInt("id_produto")
                    val nome = resultSet.getString("nome_produto")
                    val preco = resultSet.getDouble("preco_unit")

                    println("ID: $id | Nome : $nome | Preço : $preco")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            println()
        }
    }
}

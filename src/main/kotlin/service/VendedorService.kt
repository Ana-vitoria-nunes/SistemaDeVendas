package service

import connection.Connect
import model.Validacao
import java.sql.SQLException

class VendedorService {
    companion object {
        var connection = Connect().creatConnect()
        var validacao = Validacao()
        fun addVendedor(nome: String, email: String, senha: String, cpf: String, salario: Double) {
            try {
                if (!validacao.isValidVendedorInfo(nome, email, cpf)) {
                    println("As informações do livro não podem estar vazias ou nulas.")
                    return
                }
                if (!validacao.isValidEmail(email)) {
                    println("Seu e-mail precisa conter @")
                }
                val sql =
                    "INSERT INTO vendedor (nome_vendedor, email_vendedor, senha_vendedor, cpf_vendedor,salario_vendedor) VALUES ('$nome', '$email', '$senha', '$cpf','$salario')"

                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Vendedor $nome adicionado com sucesso!")

            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun deleteVendedor(id: Int) {
            if (!validacao.isValidVendedorId(id)) {
                println("ID de Vendedor inválido!")
                return
            }
            val sql =
                "DELETE FROM vendedor WHERE id_vendedor=$id"

            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Vendedor deletado com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun updateVendedor(id: Int, email: String, senha: String) {
            if (!validacao.isValidVendedorId(id)) {
                println("ID de Vendedor inválido!")
                return
            }
            if (!validacao.isValidEmail(email)) {
                println("Seu e-mail precisa conter @")
                return
            }
            val sql =
                "UPDATE vendedor SET email_vendedor='$email', senha_vendedor='$senha' WHERE id_vendedor=$id"
            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Vendedor com id $id atualizado com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun listVendedor() {
            val statement = connection.createStatement()
            val resultSet =
                statement.executeQuery("SELECT id_vendedor,nome_vendedor, email_vendedor, senha_vendedor, cpf_vendedor,salario_vendedor FROM vendedor")

            try {
                while (resultSet.next()) {
                    val id = resultSet.getInt("id_vendedor")
                    val nome = resultSet.getString("nome_vendedor")
                    val email = resultSet.getString("email_vendedor")
                    val cpf = resultSet.getString("cpf_vendedor")
                    val salario = resultSet.getDouble("salario_vendedor")


                    println("ID: $id | Nome : $nome | E-mail : $email |Cpf: $cpf  | Salario: $salario")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            println()
        }
    }
}

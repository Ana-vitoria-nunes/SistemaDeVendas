package service

import connection.Connect
import model.Validacao
import java.sql.SQLException

class ClienteService {
    companion object {
        var connection = Connect().creatConnect()
        var validacao = Validacao()

        fun addCliente(nome: String, email: String, cpf: String, endereco: String) {
            try {
                if (!validacao.isValidClienteInfo(nome, email, cpf, endereco)) {
                    println("As informações do Cliente não podem estar vazias ou nulas.")
                    return
                }
                if (!validacao.isValidEmail(email)) {
                    println("Seu e-mail precisa conter @")
                }

                val sql =
                    "INSERT INTO cliente (nome_cliente, email_cliente, cpf_cliente,endereco_cliente) VALUES ('$nome', '$email', '$cpf','$endereco')"

                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Cliente $nome adicionado com sucesso!")

            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
//        fun deleteCliente(id: Int) {
//            if (!validacao.isValidClienteId(id)) {
//                println("ID de Cliente inválido!")
//                return
//            }
//            val sql =
//                "DELETE FROM cliente WHERE id_cliente=$id"
//
//            try {
//                val statement = connection.createStatement()
//                statement.executeUpdate(sql)
//                println("Cliente deletado com sucesso!")
//                statement.close()
//            } catch (e: SQLException) {
//                e.printStackTrace()
//            }
//        }
        fun deleteCliente(id: Int) {
            if (!validacao.isValidClienteId(id)) {
                println("ID de Cliente inválido!")
                return
            }

            try {
                // Passo 1: Identificar registros de vendas relacionados ao cliente
                val consultaVendasSql = "SELECT id_venda FROM venda WHERE id_cliente = $id"
                val statementConsulta = connection.createStatement()
                val resultadoConsulta = statementConsulta.executeQuery(consultaVendasSql)

                val idsVendasRelacionadas = mutableListOf<Int>()

                while (resultadoConsulta.next()) {
                    val idVendaRelacionada = resultadoConsulta.getInt("id_venda")
                    idsVendasRelacionadas.add(idVendaRelacionada)
                }

                // Passo 2: Atualizar registros de vendas relacionados ao cliente
                for (idVenda in idsVendasRelacionadas) {
                    val atualizarVendaSql = "UPDATE venda SET id_cliente = NULL WHERE id_venda = $idVenda"
                    val statementAtualizacao = connection.createStatement()
                    statementAtualizacao.executeUpdate(atualizarVendaSql)
                }

                // Passo 3: Excluir o cliente
                val sql = "DELETE FROM cliente WHERE id_cliente = $id"
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Cliente deletado com sucesso!")

            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun updateCliente(id: Int, email: String, endereco: String) {
            try {
                if (!validacao.isValidClienteId(id)) {
                    println("ID de Cliente inválido!")
                    return
                }
                if (!validacao.isValidEmail(email)) {
                    println("Seu e-mail precisa conter @")
                    return
                }
                val sql =
                    "UPDATE cliente SET email_cliente='$email', endereco_cliente='$endereco' WHERE id_cliente=$id"
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
            val resultSet =
                statement.executeQuery("SELECT id_cliente, nome_cliente, email_cliente, cpf_cliente,endereco_cliente FROM cliente")

            try {
                while (resultSet.next()) {
                    val id = resultSet.getInt("id_cliente")
                    val nome = resultSet.getString("nome_cliente")
                    val email = resultSet.getString("email_cliente")
                    val cpf = resultSet.getString("cpf_cliente")
                    val endereco = resultSet.getString("endereco_cliente")

                    println("ID: $id | Nome : $nome | E-mail : $email |Cpf: $cpf  | Endereço: $endereco")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            println()
        }
    }
}

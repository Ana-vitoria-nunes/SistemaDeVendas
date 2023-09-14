package service

import connection.Connect
import model.Validacao
import java.sql.SQLException

class VendaService {
    companion object {
        var connection = Connect().creatConnect()
        var validacao = Validacao()

        fun addVenda(id_cliente: Int, id_vendedor: Int, id_produto: Int, qtd_produto: Int) {
            try {
                if (!validacao.isValidClienteId(id_cliente)) {
                    println("ID de Cliente inválido!")
                    return
                }
                if (!validacao.isValidVendedorId(id_vendedor)) {
                    println("ID de Vendedor inválido!")
                    return
                }
                if (!validacao.isValidProdutoId(id_produto)) {
                    println("ID de Cliente inválido!")
                    return
                }
                val consultaPrecoSql = "SELECT preco_unit FROM produto WHERE id_produto = $id_produto"
                val statementConsulta = connection.createStatement()
                val resultadoConsulta = statementConsulta.executeQuery(consultaPrecoSql)

                if (resultadoConsulta.next()) {
                    val preco = resultadoConsulta.getDouble("preco")
                    val valorTotal = qtd_produto * preco
                    val sql =
                        "INSERT INTO venda (id_cliente, id_vendedor, id_produto, qtd_produto,preco_total) VALUES ('$id_cliente', '$id_vendedor','$id_produto','$qtd_produto','$valorTotal')"

                    val statement = connection.createStatement()
                    statement.executeUpdate(sql)
                    println("Venda adicionado com sucesso!")
                }

            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun deleteVenda(id: Int) {
            if (!validacao.isValidVendaId(id)) {
                println("ID de Venda inválido!")
                return
            }
            val sql =
                "DELETE FROM venda WHERE id=$id"

            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Venda deletado com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun listVenda() {
            val statement = connection.createStatement()
            val resultSet =
                statement.executeQuery("SELECT id_venda, id_cliente, id_vendedor, id_produto, qtd_produto,preco_total FROM venda")

            try {
                while (resultSet.next()) {
                    val idVenda = resultSet.getInt("id_venda")
                    val idCliente = resultSet.getInt("id_cliente")
                    val idVendedor = resultSet.getInt("id_vendedor")
                    val idProduto = resultSet.getInt("id_produto")
                    val quant = resultSet.getInt("qtd_produto")
                    val total = resultSet.getDouble("preco_total")
                    // val authorName = getDataBaseService.getAuthorName(id_author)

                    println("ID-Venda: $idVenda |ID-Cliente: $idCliente |ID-Vendedor: $idVendedor |ID-Produto: $idProduto| Quantidade : $quant | Valor total : $total")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            println()
        }
    }
}

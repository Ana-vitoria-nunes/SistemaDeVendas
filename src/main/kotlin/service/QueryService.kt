package service

import connection.Connect
import java.sql.SQLException

class QueryService {

    companion object {
        var connection = Connect().creatConnect()
        fun listItensVendidosAcimaDe10() {
            val sql = """
            SELECT Venda.id_venda AS venda, Venda.preco_total AS preco
            FROM Venda
            WHERE preco_total > 10.00
        """.trimIndent()
            try {
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                while (resultSet.next()) {
                    val id_venda = resultSet.getInt("venda")
                    val preco_total = resultSet.getDouble("preco")
                    println("Venda: $id_venda | Preço: $preco_total")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun updateValoresNulosParaZero() {
            val sql = """
            UPDATE venda 
            SET preco_total = 0.00, qtd_produto = 0.00
            WHERE preco_total IS NULL OR qtd_produto IS NULL
            """.trimIndent()
            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Valores nulos de VALOR_TOTAL atualizados para zero com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun listSalarioVendedoresOrdenados() {
            val sql = "SELECT salario_vendedor FROM vendedor ORDER BY salario_vendedor DESC"
            try {
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                while (resultSet.next()) {
                    val salario = resultSet.getDouble("salario_vendedor")
                    println("Salário: $salario")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun UserEmailZup() {
            val sql = "SELECT COUNT(*) as total FROM cliente WHERE email_cliente LIKE '%zup.com.br'"
            try {
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                if (resultSet.next()) {
                    val total = resultSet.getInt("total")
                    println("Total de usuários com email zup.com.br: $total")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun moveVendasComIdClienteNulo() {
            try {
                // Passo 1: Selecionar vendas com id_cliente nulo
                val consultaVendasComIdClienteNulo = """
            SELECT *
            FROM venda
            WHERE id_cliente IS NULL
        """.trimIndent()

                val statementConsulta = connection.createStatement()
                val resultadoConsulta = statementConsulta.executeQuery(consultaVendasComIdClienteNulo)

                // Passo 2: Mover vendas para uma tabela temporária
                val criarTabelaTemporariaSql = """
            CREATE TEMPORARY TABLE vendas_temp AS
            SELECT * FROM venda WHERE id_cliente IS NULL
        """.trimIndent()

                val statementCriacaoTemporaria = connection.createStatement()
                statementCriacaoTemporaria.executeUpdate(criarTabelaTemporariaSql)

                // Passo 3: Excluir vendas da tabela principal
                val excluirVendasComIdClienteNuloSql = "DELETE FROM venda WHERE id_cliente IS NULL"
                val statementExclusao = connection.createStatement()
                statementExclusao.executeUpdate(excluirVendasComIdClienteNuloSql)

                println("Vendas com id_cliente nulo foram movidas para uma tabela temporária e excluídas da tabela principal.")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun listarDadosTabelaTemporaria() {
            try {
                // Consulta para selecionar todos os dados da tabela temporária
                val consultaSql = "SELECT * FROM vendas_temp"

                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(consultaSql)

                while (resultSet.next()) {
                    // Recuperar os dados de cada coluna
                    val idVenda = resultSet.getInt("id_venda")
                    val idCliente = resultSet.getInt("id_cliente")
                    val idVendedor = resultSet.getInt("id_vendedor")
                    val idProduto = resultSet.getInt("id_produto")
                    val qtdProduto = resultSet.getInt("qtd_produto")
                    val precoTotal = resultSet.getDouble("preco_total")

                    println("ID Venda: $idVenda | ID Cliente: $idCliente | ID Vendedor: $idVendedor | ID Produto: $idProduto | Quantidade: $qtdProduto | Preço Total: $precoTotal")
                }

                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}

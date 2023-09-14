package service
import connection.Connect
import java.sql.SQLException

class QueryService {
    var connection = Connect().creatConnect()

    // Consulta: Itens vendidos acima de 10,00
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

    // Consulta: Altere o valor do VALOR_TOTAL para zero onde for nulo
    fun updateValoresNulosParaZero() {
        val sql = "UPDATE venda SET preco_total = 0.00 WHERE preco_total IS NULL"
        try {
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Valores nulos de VALOR_TOTAL atualizados para zero com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    // Consulta: Salário dos vendedores, ordenados do maior para o menor
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

    // Desafio: Pesquisar quantos usuários têm o email zup.com.br
    fun countUsersWithZupEmail() {
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
}

package connection

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Connect {
    fun creatConnect(): Connection {
        return try {
            val url = "jdbc:postgresql://localhost:5432/SistemaDeVendas"
            DriverManager.getConnection(url,"postgres","123456")
        }catch (e: SQLException){
            e.printStackTrace()
            throw RuntimeException("Erro ao conectar ao banco de dados")
        }
    }
}
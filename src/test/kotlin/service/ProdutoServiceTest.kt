package service
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class ProdutoServiceTest{
    private lateinit var produtoService: ProdutoService.Companion
    private lateinit var mockConnection: Connection
    private lateinit var mockStatement: Statement
    private lateinit var mockPreparedStatement: PreparedStatement
    private lateinit var mockResultSet: ResultSet

    @BeforeEach
    fun setUp() {
        mockConnection = Mockito.mock(Connection::class.java)
        mockStatement = Mockito.mock(Statement::class.java)
        mockPreparedStatement = Mockito.mock(PreparedStatement::class.java)
        mockResultSet = Mockito.mock(ResultSet::class.java)

        produtoService = ProdutoService
        produtoService.connection = mockConnection
    }

    @Test
    fun testAddProductValid() {
        Mockito.`when`(mockConnection.createStatement()).thenReturn(mockStatement)
        Mockito.`when`(mockStatement.executeUpdate(ArgumentMatchers.anyString())).thenReturn(1)

        produtoService.addProduto("Uva", 0.50)
        Mockito.verify(mockStatement, Mockito.times(1)).executeUpdate(ArgumentMatchers.anyString())
    }
    @Test
    fun testAddProductInvalidInfo() {
        produtoService.addProduto("", 0.50)
        Mockito.verify(mockStatement, Mockito.never()).executeUpdate(ArgumentMatchers.anyString())
    }
    @Test
    fun testDeleteProductValid() {
        Mockito.`when`(mockConnection.createStatement()).thenReturn(mockStatement)
        Mockito.`when`(mockStatement.executeUpdate(ArgumentMatchers.anyString())).thenReturn(1)

        produtoService.deleteProduto(1)
        Mockito.verify(mockStatement, Mockito.times(1)).executeUpdate(ArgumentMatchers.anyString())
    }
    @Test
    fun testDeleteProductInvalidId() {
        produtoService.deleteProduto(-1)
        Mockito.verify(mockStatement, Mockito.never()).executeUpdate(ArgumentMatchers.anyString())
    }
    @Test
    fun testListProducts() {
        Mockito.`when`(mockConnection.createStatement()).thenReturn(mockStatement)
        Mockito.`when`(mockStatement.executeQuery(ArgumentMatchers.anyString())).thenReturn(mockResultSet)

        // Configurar o ResultSet simulado com os dados esperados
        Mockito.`when`(mockResultSet.next()).thenReturn(true, true, false)
        Mockito.`when`(mockResultSet.getInt("id_produto")).thenReturn(1, 2)
        Mockito.`when`(mockResultSet.getString("nome_produto")).thenReturn("Maçã", "Banana")
        Mockito.`when`(mockResultSet.getDouble("preco_unit")).thenReturn(1.0, 1.5)

        produtoService.listProduto()

        Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(ArgumentMatchers.anyString())
    }
    @Test
    fun testUpdateProductValid() {
        Mockito.`when`(mockConnection.createStatement()).thenReturn(mockStatement)
        Mockito.`when`(mockStatement.executeUpdate(ArgumentMatchers.anyString())).thenReturn(1)

        produtoService.updateProduto(1, 2.0)
        Mockito.verify(mockStatement, Mockito.times(1)).executeUpdate(ArgumentMatchers.anyString())
    }
    @Test
    fun testUpdateProductInvalidId() {
        produtoService.updateProduto(-1, 2.0)
        Mockito.verify(mockStatement, Mockito.never()).executeUpdate(ArgumentMatchers.anyString())
    }
}
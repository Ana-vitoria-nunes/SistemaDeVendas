package service
import model.Validacao
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class VendaServiceTest{
    private lateinit var vendaService: VendaService.Companion
    private lateinit var validacao: Validacao
    private lateinit var mockConnection: Connection
    private lateinit var mockStatement: Statement
    private lateinit var mockPreparedStatement: PreparedStatement
    private lateinit var mockResultSet: ResultSet

    @BeforeEach
    fun setUp() {
        mockConnection = mock(Connection::class.java)
        mockStatement = mock(Statement::class.java)
        mockPreparedStatement = mock(PreparedStatement::class.java)
        mockResultSet = mock(ResultSet::class.java)
        validacao= mock(Validacao::class.java)

        vendaService = VendaService
        vendaService.connection = mockConnection
    }

    @Test
    fun testAddVendaValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        vendaService.addVenda(1, 2, 3, 5)
        //verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteVendaValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        vendaService.deleteVenda(1)
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteVendaInvalidId() {
        vendaService.deleteVenda(-1)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testListVendas() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id_venda")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("id_cliente")).thenReturn(3, 4)
        `when`(mockResultSet.getInt("id_vendedor")).thenReturn(5, 6)
        `when`(mockResultSet.getInt("id_produto")).thenReturn(7, 8)
        `when`(mockResultSet.getInt("qtd_produto")).thenReturn(9, 10)
        `when`(mockResultSet.getDouble("preco_total")).thenReturn(11.0, 12.0)

        vendaService.listVenda()

        verify(mockStatement, times(1)).executeQuery(anyString())
    }

}
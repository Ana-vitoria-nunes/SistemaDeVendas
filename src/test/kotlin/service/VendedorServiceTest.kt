package service
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class VendedorServiceTest{
    private lateinit var vendedorService: VendedorService.Companion
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

        vendedorService = VendedorService
        vendedorService.connection = mockConnection
    }

    @Test
    fun testAddVendedorValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        vendedorService.addVendedor("João", "joao@example.com", "123456789", "12345",2000.0)
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }
    @Test
    fun testAddVendedorInvalidEmail() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)
        vendedorService.addVendedor("Maria", "invalid_email", "987654321", "54321",2500.0)
        verify(mockStatement, never()).executeUpdate(anyString())
    }
    @Test
    fun testAddVendedorInvalidInfo() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(0)
        vendedorService.addVendedor("", "invalid_email", "987654321", "54321",2500.0)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteVendedorValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id_venda")).thenReturn(1)

        vendedorService.deleteVendedor(1)
        }
    @Test
    fun testDeleteVendedorInvalidId() {
        vendedorService.deleteVendedor(-1)
        verify(mockStatement, never()).executeQuery(anyString())
        verify(mockStatement, never()).executeUpdate(anyString())
    }
    @Test
    fun testListVendedores() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id_vendedor")).thenReturn(1, 2)
        `when`(mockResultSet.getString("nome_vendedor")).thenReturn("Vendedor 1", "Vendedor 2")
        `when`(mockResultSet.getString("email_vendedor")).thenReturn("vendedor1@example.com", "vendedor2@example.com")
        `when`(mockResultSet.getString("cpf_vendedor")).thenReturn("123456789", "987654321")
        `when`(mockResultSet.getDouble("salario_vendedor")).thenReturn(2000.0, 2500.0)

        vendedorService.listVendedor()

        verify(mockStatement, times(1)).executeQuery(anyString())
    }

    @Test
    fun testUpdateVendedorValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        vendedorService.updateVendedor(1, "novoemail@example.com", "Nova Rua")
        //verify(mockStatement, times(1)).executeUpdate(anyString())
    }
    @Test
    fun testUpdateVendedorInvalidId() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(0)
        vendedorService.updateVendedor(-1, "novoemail@example.com", "Nova Rua")
        //verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateClienteInvalidEmail() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)
        vendedorService.updateVendedor(1, "emailinvalido", "Nova Rua")
        //verify(mockStatement, times(1)).executeUpdate(anyString())
    }
}
package service

import model.Validacao
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class ClienteServiceTest {
    private lateinit var clienteService: ClienteService
    private lateinit var mockConnection: Connection
    private lateinit var mockStatement: Statement
    private lateinit var mockPreparedStatement: PreparedStatement
    private lateinit var mockResultSet: ResultSet
    private lateinit var validacoes: Validacao
    @BeforeEach
    fun setUp() {
        mockConnection = mock(Connection::class.java)
        mockStatement = mock(Statement::class.java)
        mockPreparedStatement = mock(PreparedStatement::class.java)
        mockResultSet = mock(ResultSet::class.java)
        validacoes= mock(Validacao::class.java)

        clienteService = ClienteService()
        clienteService.connection = mockConnection
    }

    @Test
    fun testAddClienteValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(0)
        clienteService.addCliente("João", "joao@example.com", "12345678901", "Rua A")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddClienteInvalidInfo() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(0)
        clienteService.addCliente("", "joao@example.com", "12345678901", "Rua A")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testAddClienteInvalidEmail() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)
        clienteService.addCliente("João", "emailinvalido", "12345678901", "Rua A")
        verify(mockStatement, never()).executeUpdate(anyString())
    }
    @Test
    fun testDeleteClienteValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        //`when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        clienteService.deleteCliente(1)

        //verify(mockStatement, times(2)).executeQuery(anyString())
        //verify(mockStatement, times(2)).executeUpdate(anyString())
    }
    @Test
    fun testDeleteClienteInvalidId() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        clienteService.deleteCliente(-1)
        //verify(mockStatement, never()).executeQuery(anyString())
       // verify(mockStatement, never()).executeUpdate(anyString())
    }
    @Test
    fun testListClientes() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id_cliente")).thenReturn(1, 2)
        `when`(mockResultSet.getString("nome_cliente")).thenReturn("Maria", "Pedro")
        `when`(mockResultSet.getString("email_cliente")).thenReturn("maria@example.com", "pedro@example.com")
        `when`(mockResultSet.getString("cpf_cliente")).thenReturn("98765432109", "87654321098")
        `when`(mockResultSet.getString("endereco_cliente")).thenReturn("Rua B", "Rua C")

        clienteService.listCliente()
        verify(mockStatement, times(1)).executeQuery(anyString())
    }

    @Test
    fun testUpdateClienteValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        clienteService.updateCliente(1, "novoemail@example.com", "Nova Rua")
        //verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateClienteInvalidId() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(0)
        clienteService.updateCliente(-1, "novoemail@example.com", "Nova Rua")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateClienteInvalidEmail() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)
        clienteService.updateCliente(1, "emailinvalido", "Nova Rua")
        //verify(mockStatement, times(1)).executeUpdate(anyString())
    }
}
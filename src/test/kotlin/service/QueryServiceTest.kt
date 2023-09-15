package service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class QueryServiceTest{
    private lateinit var queryService: QueryService.Companion
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

        queryService = QueryService
        queryService.connection = mockConnection
    }

    @Test
    fun testListItensVendidosAcimaDe10() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("venda")).thenReturn(1, 2)
        `when`(mockResultSet.getDouble("preco")).thenReturn(15.0, 12.0)

        queryService.listItensVendidosAcimaDe10()
        verify(mockStatement, times(1)).executeQuery(anyString())
    }
    @Test
    fun testCountUsersWithZupEmail() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true)
        `when`(mockResultSet.getInt("total")).thenReturn(5)

        queryService.UserEmailZup()
        verify(mockStatement, times(1)).executeQuery(anyString())
    }
    @Test
    fun testListSalarioVendedoresOrdenados() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getDouble("salario_vendedor")).thenReturn(2500.0, 2000.0)
        `when`(mockResultSet.getString("nome_vendedor")).thenReturn("Vendedor 1", "Vendedor 2")

        queryService.listSalarioVendedoresOrdenados()
        verify(mockStatement, times(1)).executeQuery(anyString())
    }
}
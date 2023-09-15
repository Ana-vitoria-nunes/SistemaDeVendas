package view

import model.InputUserModel
import model.Validacao
import service.*

class LoginVendedorView {
    val clienteService=ClienteService()
    fun menuInicia() {
        println("\nMenu vendedor")
        println("0. Volra menu inicial\n1. Menu Cliente\n2. Menu Venda\n3. Menu especial ")
    }
    fun CaseLoginVendedor() {
        var option: Int
        do {
            menuInicia()
            option = InputUserModel.readIntFromUser("Qual opção deseja")
            when (option) {
                0 -> MenuView.iniciar()
                1 -> CaseCliente()
                2 -> CaseVenda()
                3 -> CaseEspecial()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun menuCliente() {
        println("\nMenu do Cliente")
        println("0. Volta menu Vendedor\n1. Listar Clientes\n2. Adicionar um cliente\n3. Atualiza um cliente\n4. Deletar Cliente ")
    }
    fun CaseCliente() {
        var option: Int
        do {
            menuCliente()
            option = InputUserModel.readIntFromUser("Qual opção deseja")

            when (option) {
                0 -> CaseLoginVendedor()
                1 -> clienteService.listCliente()
                2 -> adicionarCliente()
                3 -> atualizarCliente()
                4 -> deletarCliente()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun adicionarCliente() {
        val nome = InputUserModel.readStringFromUser("Qual o nome do Cliente: ")
        val email = InputUserModel.readStringFromUser("Qual email do Cliente: ")
        val cpf = InputUserModel.readStringFromUser("Qual o cpf do Cliente: ")
        val endereco = InputUserModel.readStringFromUser("Qual o endereço do Cliente: ")
        clienteService.addCliente(nome, email, cpf, endereco)
    }
    fun deletarCliente() {
        println("===Clientes===")
        clienteService.listCliente()
        val id = InputUserModel.readIntFromUser("Qual o id do cliente que deseja deletar: ")
        clienteService.deleteCliente(id)
    }
    fun atualizarCliente() {
        println("===Clientes===")
        clienteService.listCliente()
        val id = InputUserModel.readIntFromUser("Qual o id do Cliente que deseja Atualizar: ")
        val email = InputUserModel.readStringFromUser("Qual o novo e-mail: ")
        val endereco = InputUserModel.readStringFromUser("Qual o novo endereço: ")
        clienteService.updateCliente(id, email, endereco)
    }
    fun menuVenda() {
        println("\nMenu de Venda")
        println("0. Volra menu vendedor\n1. Listar vendas\n2.Efetuar uma venda\n3. Deletar uma venda ")
    }
    fun CaseVenda() {
        var option: Int
        do {
            menuVenda()
            option = InputUserModel.readIntFromUser("Qual opção deseja")

            when (option) {
                0 -> CaseLoginVendedor()
                1 -> VendaService.listVenda()
                2 -> adicionarVenda()
                3 -> deletarVenda()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun adicionarVenda() {
        println("===Clientes===")
        clienteService.listCliente()
        println("===Produto===")
        ProdutoService.listProduto()
        val id_cliente = InputUserModel.readIntFromUser("Qual o id do Cliente:")
        val id_vendedor = InputUserModel.readIntFromUser("Qual seu id de vendedor:")
        val id_produto = InputUserModel.readIntFromUser("Qual o id do produto:")
        val quant = InputUserModel.readIntFromUser("Quantos produto o cliente vai querer:")
        VendaService.addVenda(id_cliente, id_vendedor, id_produto, quant)
    }
    fun deletarVenda() {
        println("===Vendas===")
        VendaService.listVenda()
        val id = InputUserModel.readIntFromUser("Qual o id da Venda que deseja deletar: ")
        VendaService.deleteVenda(id)
    }
    fun menuEspecial() {
        println("\nMenu Especial")
        println("0. Volra Adm\n1. Listar vendas acima de 10.0\n2.Listar Salario dos vendedores Ordenados\n3. Ver quantos clients contem zup.com.br no e-mail ")
    }
    fun CaseEspecial() {
        var option: Int
        do {
            menuEspecial()
            option = InputUserModel.readIntFromUser("Qual opção deseja")

            when (option) {
                0 -> CaseLoginVendedor()
                1 -> QueryService.listItensVendidosAcimaDe10()
                2 -> QueryService.listSalarioVendedoresOrdenados()
                3 -> QueryService.UserEmailZup()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
}

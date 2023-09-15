package view

import model.InputUserModel
import model.Validacao
import service.ProdutoService
import service.QueryService
import service.VendedorService
class LoginAdmView {
    fun menuInicia() {
        println("\nMenu Gerente")
        println("0. Volta menu inicial\n1. Menu Vendedor\n2. Menu produto\n3. Menu especial ")
    }
    fun CaseLoginAdm() {
        var option: Int
        do {
            menuInicia()
            option = InputUserModel.readIntFromUser("Qual opção deseja")

            when (option) {
                0 -> MenuView.iniciar()
                1 -> CaseVendedor()
                2 -> CaseProduto()
                3 -> CaseEspecial()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun menuVendedor() {
        println("\nMenu do Vendedor")
        println("0. Volta menu Gerente\n1. Listar vendedors\n2. Atualiza um vendedor\n3. Deletar vendedor ")
    }
    fun CaseVendedor() {
        var option: Int
        do {
            menuVendedor()
            option = InputUserModel.readIntFromUser("Qual opção deseja")

            when (option) {
                0 -> CaseLoginAdm()
                1 -> VendedorService.listVendedor()
                2 -> atualizarVendedor()
                3 -> deletarVendedor()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun deletarVendedor() {
        println("===Vendedores===")
        VendedorService.listVendedor()
        val id = InputUserModel.readIntFromUser("Qual o id do vendedor que deseja deletar: ")
        VendedorService.deleteVendedor(id)
    }
    fun atualizarVendedor() {
        println("===Vendedores===")
        VendedorService.listVendedor()
        val id = InputUserModel.readIntFromUser("Qual o id do vendedor que deseja Atualizar: ")
        val email = InputUserModel.readStringFromUser("Qual o novo e-mail: ")
        val senha = InputUserModel.readStringFromUser("Qual a nova senha: ")
        VendedorService.updateVendedor(id, email, senha)
    }
    fun menuProduto() {
        println("\nMenu do Produto")
        println("0. Volra menu gerente\n1. Listar os produtos\n2.Adicionar um produto\n3. Atualizar um produto\n4. Deletar um produto ")
    }
    fun CaseProduto() {
        var option: Int
        do {
            menuProduto()
            option = InputUserModel.readIntFromUser("Qual opção deseja")

            when (option) {
                0 -> CaseLoginAdm()
                1 -> ProdutoService.listProduto()
                2 -> adicionarProduto()
                3 -> atualizarProduto()
                4 -> deletarProduto()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun adicionarProduto() {
        val nome = InputUserModel.readStringFromUser("Qual o nome do produto: ")
        val preco = InputUserModel.readDoubleFromUser("Qual o preço do produto: ")
        ProdutoService.addProduto(nome, preco)
    }
    fun deletarProduto() {
        println("===Produto===")
        ProdutoService.listProduto()
        val id = InputUserModel.readIntFromUser("Qual o id do Produto que deseja deletar: ")
        ProdutoService.deleteProduto(id)
    }
    fun atualizarProduto() {
        println("===Produtos===")
        ProdutoService.listProduto()
        val id = InputUserModel.readIntFromUser("Qual o id do produto que deseja atualizar")
        val preco = InputUserModel.readDoubleFromUser("Qual o novo preço do produto: ")
        ProdutoService.updateProduto(id, preco)
    }
    fun menuEspecial() {
        println("\nMenu Especial")
        println("0. Volra gerente\n1. Listar vendas acima de 10.0\n2.Listar Salario dos vendedores Ordenados\n3. Ver quantos clients contem zup.com.br no e-mail ")
    }
    fun CaseEspecial() {
        var option: Int
        do {
            menuEspecial()
            option = InputUserModel.readIntFromUser("Qual opção deseja")

            when (option) {
                0 -> CaseLoginAdm()
                1 -> QueryService.listItensVendidosAcimaDe10()
                2 -> QueryService.listSalarioVendedoresOrdenados()
                3 -> QueryService.UserEmailZup()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
}

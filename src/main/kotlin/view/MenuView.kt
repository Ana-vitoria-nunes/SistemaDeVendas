package view

import model.InputUserModel
import model.Validacao
import service.VendaService
import service.VendedorService

class MenuView {
    companion object {
        val loginAdm = LoginAdmView()
        val loginVendedor = LoginVendedorView()
        fun iniciar() {
                var option: Int
                do {
                    menuInicia()
                    option = InputUserModel.readIntFromUser("Qual opção deseja")

                    when (option) {
                        0 -> println("Encerrando o programa...")
                        1 -> {
                            val name = InputUserModel.readStringFromUser("Digite seu nome: ")
                            val password = InputUserModel.readStringFromUser("Digite sua senha: ")

                            if (Validacao().isValidLibrarianCredentials(name, password)) {
                                println("\n========================== Bem-Vindo $name ==========================")
                                loginAdm.CaseLoginAdm()
                            }
                        }

                        2 -> {
                            val name = InputUserModel.readStringFromUser("Digite seu nome: ")
                            val password = InputUserModel.readStringFromUser("Digite sua senha: ")

                            if (Validacao().isValidUserCredentials(name, password)) {
                                println("\n========================== Bem-Vindo $name ==========================")
                                loginVendedor.CaseLoginVendedor()
                            } else {
                                println("Senha ou nome invalido!")
                            }
                        }

                        3 -> addUser()
                        else -> println("Opção inválida, tente novamente!")
                    }
                } while (option != 0)

        }

        private fun addUser() {
            val name = InputUserModel.readStringFromUser("Qual seu nome: ")
            val email = InputUserModel.readStringFromUser("Qual seu e-mail: ")
            val senha = InputUserModel.readStringFromUser("Qual sua senha: ")
            val cpf = InputUserModel.readStringFromUser("Digite seu CPF sem os caracteres: ")
            val salario = InputUserModel.readDoubleFromUser("Digite seu salario: ")
            VendedorService.addVendedor(name, email, senha, cpf, salario)
        }

        fun menuInicia() {
            println("0. Sair | 1. Login Adiministrador | 2. Login Vendedor | 3. Cadastrar Usuario ")
        }
    }
}

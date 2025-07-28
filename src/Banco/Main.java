package Banco;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.println("===== ABRIR CONTA =====");
        System.out.println("Para abrir a conta é preciso fazer um depósito inicial.");
        System.out.print("Digite o valor do depósito: ");
        double valor = scanner.nextDouble();
        Conta conta = new Conta(valor);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Consultar saldo");
            System.out.println("2 - Consultar cheque especial");
            System.out.println("3 - Depositar");
            System.out.println("4 - Sacar");
            System.out.println("5 - Pagar boleto");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\n===== SALDO =====");
                    System.out.println(conta.mostrarInfoSaldo());
                    break;
                case 2:
                    System.out.println("\n===== CHEQUE ESPECIAL =====");
                    System.out.println(conta.mostrarInfoChequeEspecial());
                    break;
                case 3:
                    System.out.println("\n===== DEPOSITO =====");
                    System.out.print("Digite o valor do depósito: ");
                    double deposito = scanner.nextDouble();
                    conta.depositar(deposito);
                    System.out.println(conta.mostrarInfoSaldo());
                    break;
                case 4:
                    System.out.println("\n===== SAQUE =====");
                    System.out.print("Digite o valor do saque: ");
                    double saque = scanner.nextDouble();
                    conta.sacar(saque);
                    System.out.println(conta.mostrarInfoSaldo());
                    break;
                case 5:
                    System.out.println("\n===== PAGAR BOLETO =====");
                    System.out.print("Digite o valor do boleto: ");
                    double valorBoleto = scanner.nextDouble();
                    conta.pagarBoleto(valorBoleto);
                    System.out.println(conta.mostrarInfoSaldo());
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

    }
}

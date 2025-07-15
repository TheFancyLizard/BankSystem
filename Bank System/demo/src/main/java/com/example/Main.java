package com.example;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static final String RED_STRING= "\u001B[31m";
    public static final String GREEN_STRING = "\u001B[32m";
    public static final String RESET_STRING = "\u001B[0m";

    private static User[] listaDeUsuarios;
    private static Account[] listaDeContas;

    public User[] getUsuarios(){
        return listaDeUsuarios;
    }

    public static void Escrever(String msg){
        System.out.println(msg);
    }

    public static void Transacionar(Account conta, double ammount, Account conta2, String pin){
        Transaction transacao = new Transaction(conta, ammount, conta2);
        boolean transacaoLiberada = transacao.makeTransaction(conta, conta2, ammount, pin);
        
        if(transacaoLiberada){
            conta.addTransactionOut(transacao);
            conta2.addTransactionIn(transacao);
        }
    }

    public static User RegisterUser(Scanner leitor){
        System.out.print("Insira o nome para o usuário da conta: ");
        String nome = leitor.nextLine();
        System.out.print("Insira a renda do usuário da conta: ");
        double renda = leitor.nextDouble();
        if (renda >= 0){
            leitor.nextLine();
            System.out.print("Insira o CPF para o usuário da conta: ");
            String cpf = leitor.nextLine();
            return new User(nome, renda, cpf);
        }
        else{
            System.out.printf(RED_STRING+"%nValor de renda inválido! (não pode ser negativo)"+RESET_STRING);
            return null;
        }
    }
    public static User[] addUser(User user, User[] listaDeUsuarios){
        if(listaDeUsuarios == null){
            User[] newArray = new User[1];
            newArray[0] = user;
            listaDeUsuarios = newArray;
            System.out.printf(GREEN_STRING+"%nUsuário registrado!%n"+RESET_STRING);
            return listaDeUsuarios;
        }
        else{
            User[] newArray = Arrays.copyOf(listaDeUsuarios, listaDeUsuarios.length+1);
            newArray[newArray.length-1] = user;
            listaDeUsuarios = newArray;
            System.out.printf("%nUsuário registrado!%n");
            return listaDeUsuarios;
        }
    }
    public static User FindUser(User[] listaDeUsuarios, Scanner leitor){
        System.out.printf("%nInforme o CPF do usuário da conta que gostaria de criar: ");
        String cpfInput = leitor.nextLine();
        for (int i = 0; i<listaDeUsuarios.length; i++){
            if (listaDeUsuarios[i].getCPF().equals(cpfInput)){
                return listaDeUsuarios[i];
            }
        }
        return null;
    }
    
    public static Account RegisterAccount(User[] usuarios, Scanner leitor){
        User testUser = new User("Test Test", 0, "123.456.789.00");

        User usuario = FindUser(usuarios, leitor);
        if(usuario != null){
            System.out.print("Informe o PIN: ");
            String pin = leitor.nextLine();
            Account testAccount = new Account(testUser, pin);
            if (testAccount.setPin(pin)){
                return new Account(usuario, pin);
            }
            else{
                System.out.printf(RED_STRING+"%nConta não pode ser criada.%n"+RESET_STRING);
                return null;
            }
        }
        else{
            System.out.printf(RED_STRING+"%nUsuário não encontrado.%n"+RESET_STRING);
            return null;
        }

    }
    public static Account[] AddAccount(Account conta, Account[] listaDeContas){
        if(listaDeContas == null){
            Account[] newArray = new Account[1];
            newArray[0] = conta;
            listaDeContas = newArray;
            System.out.printf(GREEN_STRING+"%nConta de %s registrada!%n"+RESET_STRING,conta.getUser().getName());
            return listaDeContas;
        }
        else{
            Account[] newArray = Arrays.copyOf(listaDeContas, listaDeContas.length+1);
            newArray[newArray.length-1] = conta;
            listaDeContas = newArray;
            System.out.printf("%nConta de %s registrada!%n",conta.getUser().getName());
            return listaDeContas;
        }
    }
    public static Account FindAccount(Account[]listaDeContas, Scanner leitor){
        System.out.printf("%nInforme o CPF do usuário da conta que gostaria de encontrar: ");
        String cpfInput = leitor.nextLine();
        for (int i = 0; i<listaDeContas.length;i++){
            if (listaDeContas[i].getUser().getCPF().equals(cpfInput)){
                return listaDeContas[i];
            }    
        }
        System.out.printf(RED_STRING+"%nConta não encontrada!%n"+RESET_STRING);
        return null;
    }
    
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        OUTER:
        while (true) {
            System.out.printf("%nMENU%n1 - Registrar Usuário%n2 - Registrar Conta%n3 - Observar Transações%n4 - Realizar Transação%n5 - Ver Balanço%n6 - Fechar%n: ");
            String input = leitor.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            switch (input) {
                case "6" -> {
                    break OUTER;
                }
                case "1" -> {
                    User usuario = RegisterUser(leitor);
                    if (usuario!=null){
                        listaDeUsuarios = addUser(usuario, listaDeUsuarios);
                    }
                }
                case "2" -> {
                    if(listaDeUsuarios!= null){
                        Account conta = RegisterAccount(listaDeUsuarios, leitor);
                        if(conta != null){
                            listaDeContas = AddAccount(conta, listaDeContas);
                            conta.ReceivePay();
                        }
                    }
                    else{
                        System.out.printf("%nNenhum usuário registrado!%n");
                    }
                }
                case "3" -> {
                    if (listaDeContas!=null){
                        Account conta = FindAccount(listaDeContas, leitor);
                        if (conta!= null){
                            conta.DisplayAllAccountTransactions();
                        }
                    }
                    else{
                        System.out.printf("%nNenhuma conta registrada!%n");
                    }
                }
                case "4" -> {
                    System.out.printf("%nConta que pagará: %n");
                    Account conta = FindAccount(listaDeContas, leitor);
                    if(conta != null){
                        System.out.printf("Qual o valor que deseja transferir da conta de %s: ",conta.getUser().getName());
                        double valor = leitor.nextDouble();
                        System.out.printf("%nConta que receberá: %n");
                        leitor.nextLine();
                        Account conta2 = FindAccount(listaDeContas, leitor);
                        if(conta2 != null){
                            System.out.printf("%nInforme o PIN da conta que pagará: ");
                            String pin = leitor.next();
                            Transacionar(conta, valor, conta2, pin);
                        }
                    }
                }
                case "5" -> {
                    if (listaDeContas!=null){
                        Account conta = FindAccount(listaDeContas, leitor);
                        if (conta != null){
                            conta.getBalance();
                        }
                    }
                    else{
                        System.out.printf("%nNenhuma conta registrada!%n");
                    }
                }
                default -> System.out.printf("%nValor não foi reconhecido%n");
            }
        }
        leitor.close();
    }
}
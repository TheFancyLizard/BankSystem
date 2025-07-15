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
        leitor.nextLine();
        System.out.print("Insira o CPF para o usuário da conta: ");
        String cpf = leitor.nextLine();
        return new User(nome, renda, cpf);
    }
    public static User[] addUser(User user, User[] listaDeUsuarios){
        if(listaDeUsuarios == null){
            User[] newArray = new User[1];
            newArray[0] = user;
            listaDeUsuarios = newArray;
            return listaDeUsuarios;
        }
        else{
            User[] newArray = Arrays.copyOf(listaDeUsuarios, listaDeUsuarios.length+1);
            newArray[newArray.length-1] = user;
            listaDeUsuarios = newArray;
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
                System.out.printf("%nConta não pode ser criada.%n");
                return null;
            }
        }
        else{
            System.out.printf("%nUsuário não encontrado.%n");
            return null;
        }

    }
    public static Account[] AddAccount(Account conta, Account[] listaDeContas){
        if(listaDeContas == null){
            Account[] newArray = new Account[1];
            newArray[0] = conta;
            listaDeContas = newArray;
            return listaDeContas;
        }
        else{
            Account[] newArray = Arrays.copyOf(listaDeContas, listaDeContas.length+1);
            newArray[newArray.length-1] = conta;
            listaDeContas = newArray;
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
        while (true) {
            System.out.printf("%nMENU%n1 - Registrar Usuário%n2 - Registrar Conta%n3 - Observar Transações%n4 - Fechar%n: ");
            String input = leitor.nextLine();
            if (input.equals("1")){
                User usuario = RegisterUser(leitor);
                listaDeUsuarios = addUser(usuario, listaDeUsuarios);
            }
            else if(input.equals("2")){
                if(listaDeUsuarios!= null){
                    Account conta = RegisterAccount(listaDeUsuarios, leitor);
                    listaDeContas = AddAccount(conta, listaDeContas);
                }
                else{
                    System.out.printf("%nNenhum usuário registrado!%n");
                }
            }
            else if(input.equals("3")){
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
            else if(input.equals("4")){
                break;
            }
            else{
                System.out.printf("%nValor não foi reconhecido%n");
            }
        }
        /* 
        User usuario2 = new User("Roberto Burnham", 4000.00, "000.111.000.11");
        listaDeUsuarios = addUser(usuario2, listaDeUsuarios);
        
        Account conta2 = RegisterAccount(usuario2, leitor);
            
        if(conta != null && conta2 != null){
            conta.ReceivePay();
            Escrever(""+conta.getUser().getName() +": R$"+ conta.getBalance() +", CPF: " + usuario.getCPF());
            
            
            conta2.ReceivePay();
            Escrever(""+conta2.getUser().getName() +": R$"+conta2.getBalance()+", CPF:"+usuario2.getCPF());
                
            
            Transacionar(conta, 150, conta2, "0000");
            Transacionar(conta2, 300.60, conta, "0111");
            Transacionar(conta2, 10000, conta, "1111");
            
            conta.DisplayAllAccountTransactions();
            conta2.DisplayAllAccountTransactions();
            
            System.out.println(conta2.getBalance());
            
            for (int i = 0; i<listaDeUsuarios.length; i++){
                System.out.println(listaDeUsuarios[i].getName());
            }
            
        }
        */
        leitor.close();
    }
}
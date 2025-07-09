package com.example;

public class Main {

    public static final String RED_STRING= "\u001B[31m";
    public static final String GREEN_STRING = "\u001B[32m";
    public static final String RESET_STRING = "\u001B[0m";

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

    public static void main(String[] args) {
        try{
            User testUser = new User("Test Test", 0, "123.456.789.00");
            User usuario = new User("Jorge Carlin", 3000.00, "000.000.000.00");
            User usuario2 = new User("Roberto Burnham", 4000.00, "000.111.000.11");
            String pin1 = "0000";
            Account testAccount = new Account(testUser, pin1);
            boolean pinIsSet = testAccount.setPin(pin1);
            Account conta = null;
            Account conta2 = null;
            if (pinIsSet){
                conta = new Account(usuario, pin1);
            }
            else{
                System.out.printf("%nConta não pode ser criada.%n");
            }
            String pin2 = "1111";
            boolean pinIsSet2 = testAccount.setPin(pin2);
            if (pinIsSet2) {
                conta2 = new Account(usuario2, pin2);
            }
            else{
                System.out.printf("%nConta não pode ser criada.%n");
            }
            
            if(conta != null && conta2 != null){
                conta.ReceivePay();
                Escrever(""+conta.getUser().getName() +": R$"+ conta.getBalance());
                
                
                conta2.ReceivePay();
                Escrever(""+conta2.getUser().getName() +": R$"+conta2.getBalance());
                
                
                
                Transacionar(conta, 150, conta2, "0000");
                Transacionar(conta2, 300.60, conta, "0111");
                Transacionar(conta2, 10000, conta, "1111");
                    
                conta.DisplayAllAccountTransactions();
                conta2.DisplayAllAccountTransactions();
                    
                System.out.println(conta2.getBalance());
                
            }
        }
        catch(Exception e){
            System.out.println("Conta inxesistente.");
        }
        
    }
}
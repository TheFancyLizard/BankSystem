package com.example;

import java.util.Arrays;

public class Account {
    private User user;
    private Transaction[] transactionsIn;
    private Transaction[] transactionsOut;
    private double balance;
    private double credit;
    private String pin;

    public Account(User user, String pin){
        this.user = user;
        this.balance = 0;
        this.credit = 0;
        this.pin = pin;
    }

    public void Deposit(double ammount){
        this.balance += ammount;
    }
    public void Withdrawl(double ammount){
        this.balance -= ammount;
    }
    public void ReceivePay(){
        this.balance += this.user.getIncome();
    }

    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }

    public double getBalance(){
        return this.balance;
    }
    public double getCredit(){
        return this.credit;
    }
    
    public boolean setPin(String pin){
        if ((String.valueOf(pin).length() == 4)){
            this.pin = pin;
            return true;
        }
        else{
            System.out.printf(Main.RED_STRING+"%nTamanho de PIN inválido.(Utilizar 4 dígitos.)"+Main.RESET_STRING);
            return false;
        }
    }
    public String getPin(){
        return this.pin;
    }

    public int getTransactionInAmmounts(){
        if (this.transactionsIn != null){
            return this.transactionsIn.length;
        }
        else{
            return 0;
        }
    }
    public int getTransactionOutAmmounts(){
        if (this.transactionsOut != null){
            return this.transactionsOut.length;
        }
        else{
            return 0;
        }
    }

    public Transaction[] getTransactionsIn(){
        return this.transactionsIn;
    }
    public Transaction[] getTransactionsOut(){
        return this.transactionsOut;
    }    
    public Transaction[] addTransactionOut(Transaction transaction){
        if(transactionsOut == null){
            Transaction[] newArray = new Transaction[1];
            newArray[0] = transaction;
            transactionsOut = newArray;
            return transactionsOut;
        }
        else{
            Transaction[] newArray = Arrays.copyOf(transactionsOut, transactionsOut.length+1);
            newArray[newArray.length-1] = transaction;
            transactionsOut = newArray;
            return transactionsOut;
        }
    }
    public Transaction[] addTransactionIn(Transaction transaction){
        if(transactionsIn == null){
            Transaction[] newArray = new Transaction[1];
            newArray[0] = transaction;
            transactionsIn = newArray;
            return transactionsIn;
        }
        else{
            Transaction[] newArray = Arrays.copyOf(transactionsIn, transactionsIn.length+1);
            newArray[newArray.length-1] = transaction;
            transactionsIn = newArray;
            return transactionsIn;
        }
    }

    public void DisplayAccountTransactionsOut(){
        if (getTransactionOutAmmounts()>0){
            System.out.printf("%nPagamentos:%n");
            for(int i=0;i<getTransactionOutAmmounts();i++){
                System.out.printf("%.2f",getTransactionsOut()[i].getAmmount());
                System.out.println(" Para "+getTransactionsOut()[i].getReceiver().getUser().getName());
            }
        }
        else{
            System.out.printf("%nNenhuma transação efetuada.%n");
        }
    }
    public  void DisplayAccountTransactionsIn(){
        if (getTransactionInAmmounts()>0){
            System.out.printf("%nRecebimentos:%n");
            for(int i=0;i<getTransactionInAmmounts();i++){
                System.out.printf("%.2f de ",getTransactionsIn()[i].getAmmount());
                System.out.printf(getTransactionsIn()[i].getPayer().getUser().getName()+"%n");
            }
        }
        else{
            System.out.printf("%nNenhum valor recebido.%n");
        }
    }

    public void DisplayAllAccountTransactions(){
        DisplayAccountTransactionsIn();
        DisplayAccountTransactionsOut();
    }
}

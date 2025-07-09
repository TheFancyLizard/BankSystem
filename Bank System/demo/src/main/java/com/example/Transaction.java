package com.example;

public class Transaction {
    private Account payingAccount;
    private Account receivingAccount;
    private double ammount;

    public Transaction(Account account1, double ammount, Account account2){
        this.payingAccount = account1;
        this.ammount = ammount;
        this.receivingAccount = account2;
    }

    public void setPayer(Account account){
        this.payingAccount = account;
    }
    public Account getPayer(){
        return this.payingAccount;
    }

    public void setReceiver(Account account){
        this.receivingAccount = account;
    }
    public Account getReceiver(){
        return this.receivingAccount;
    }

    public void setAmmount(float ammount){
        this.ammount = ammount;
    }
    public double getAmmount(){
        return this.ammount;
    }

    public boolean makeTransaction(Account payingAccount, Account receivingAccount, double ammount, String pin){
        if (payingAccount.getBalance()>=ammount){
            if (pin.equals(payingAccount.getPin())){
                payingAccount.Withdrawl(ammount);
                receivingAccount.Deposit(ammount);
                System.out.printf(Main.GREEN_STRING+"%nTransação de R$%.2f Realizada com sucesso!%n"+Main.RESET_STRING, ammount);
                return true;
            }
            else{
                System.out.printf(Main.RED_STRING+"%nPIN errado! Transação Cancelada.%n"+Main.RESET_STRING);
                return false;
            }
        }
        else{
            System.out.printf(Main.RED_STRING+"%nValor de transação inválido. Balanço da conta insuficiente.%n"+Main.RESET_STRING);
            return false;
        }
    }
}

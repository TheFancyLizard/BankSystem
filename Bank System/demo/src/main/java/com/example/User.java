package com.example;

public class User {
    private String name;
    private double income;
    private String cpf;

    public User(String name, double income, String cpf){
        this.name = name;
        this.income = income;
        this.cpf = cpf;
    }

    public void setName(String givenName){
        this.name = givenName;
    }
    public String getName(){
        return this.name;
    }

    public double getIncome(){
        return this.income;
    }
    public void setIncome(float ammount){
        this.income = ammount;
    }

    public String getCPF(){
        return this.cpf;
    }
    public void setCPF(String givenCPF){
        this.cpf = givenCPF;
    }
}

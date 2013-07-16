package com.qsoft.bank;

public class BankAccountDTO {

	private String accountNumber;
	private Double balance;
	private String description;

	public BankAccountDTO(String accountNumber, Double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public Double getBalance() {
		return balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

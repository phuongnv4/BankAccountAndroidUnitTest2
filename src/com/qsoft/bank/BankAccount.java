package com.qsoft.bank;

import java.util.Calendar;

public class BankAccount {
	static Calendar calendar;
	private BankAccountDAO bankAccountDAO;

	public BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO.setAccountNumber(accountNumber);
		bankAccountDAO.save(bankAccountDTO, calendar.getTimeInMillis());
		return bankAccountDTO;
	}

	public void setDao(BankAccountDAO bankAccountDAO) {
		this.bankAccountDAO = bankAccountDAO;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public void deposit(BankAccountDTO bAccountDto, int amount,
			String description) {
		bAccountDto.setBalance(amount + bAccountDto.getBalance());
		bankAccountDAO.save(bAccountDto, 0L);
	}

	public void deposit(BankAccountDTO bAccountDto, int amount,
			String description, long l) {
		bAccountDto.setBalance(amount + bAccountDto.getBalance());
		bankAccountDAO.save(bAccountDto, l);

	}

	public void withdraw(BankAccountDTO bAccountDto, int amount,
			String description) {
		bAccountDto.setBalance(amount + bAccountDto.getBalance());
		bankAccountDAO.save(bAccountDto, 0L);
	}

	public void getTransactionsOccurred(String accountNumber) {
		bankAccountDAO.getListTransactions(accountNumber);
	}

	public void getTransactionsOccurred(String accountNumber, long l, long m) {
		bankAccountDAO.getListTransactions(accountNumber, l, m);

	}

	public void getNTransactions(BankAccountDTO bAccountDto, int i) {
		bankAccountDAO.getNTransactions(bAccountDto, i);
	}

	public BankAccountDTO getAccountByNumber(String accountNumber) {
		// TODO Auto-generated method stub
		return bankAccountDAO.getAccount(accountNumber);
	}

}

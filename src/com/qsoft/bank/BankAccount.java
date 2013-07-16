package com.qsoft.bank;

import java.util.Calendar;

public class BankAccount {
	static BankAccountDAO BankAccountDAO;
	static Calendar calendar;

	public static BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO accountDTO = new BankAccountDTO(accountNumber, 0.0);
		BankAccountDAO.save(accountDTO, calendar.getTimeInMillis());
		return accountDTO;
	}

	public static BankAccountDTO getAccount(String accountNumber) {
		BankAccountDTO result = BankAccountDAO
				.getAccountbyAccountNumber(accountNumber);
		return result;
	}

	public static void deposit(String accountNumber, double amount,
			String description) {
		BankAccountDAO.saveTransaction(accountNumber, amount, description,
				calendar.getTimeInMillis());

		BankAccountDTO accountDTO = getAccount(accountNumber);
		accountDTO.setBalance(amount);
		accountDTO.setDescription(description);
		BankAccountDAO.save(accountDTO, calendar.getTimeInMillis());
	}

	public static void withDraw(String accountNumber, double amount,
			String description) {
		deposit(accountNumber, amount, description);
	}

	public static void setBankAccountDAO(BankAccountDAO mockAccountDao) {
		BankAccountDAO = mockAccountDao;
	}

	public static void setCalendar(Calendar mockCalendar) {
		calendar = mockCalendar;
	}

}

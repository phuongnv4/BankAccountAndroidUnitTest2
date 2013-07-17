package com.qsoft.bank;

import java.util.List;

public class BankAccountDAO {

	public void save(BankAccountDTO accountDTO, long timeInMillis) {

	}

	public BankAccountDTO getAccountbyAccountNumber(String string) {
		return null;
	}

	public void saveTransaction(String accountNumber, double amount,
			String description, long timeInMillis) {

	}

	public List<TransactionDTO> getListTransactionOccurred(String accountNumber) {

		return null;
	}

	public List<TransactionDTO> getListTransactionOccurred(
			String accountNumber, Long startTime, Long stopTime) {
		return null;
	}

}

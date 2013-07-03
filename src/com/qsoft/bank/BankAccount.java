package com.qsoft.bank;

public class BankAccount {
	private BankAccountDAO bankAccountDAO;
	public BankAccountDTO openAccount(String accountNumber, long l) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO.setAccountNumber(accountNumber);
		bankAccountDAO.save(bankAccountDTO,l);
		return bankAccountDTO;
	}

	public void setDao(BankAccountDAO bankAccountDAO) {
		this.bankAccountDAO = bankAccountDAO;
	}

}

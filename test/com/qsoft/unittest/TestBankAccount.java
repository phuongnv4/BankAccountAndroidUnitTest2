package com.qsoft.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import com.qsoft.bank.BankAccount;
import com.qsoft.bank.BankAccountDAO;
import com.qsoft.bank.BankAccountDTO;

public class TestBankAccount extends TestCase {
	BankAccount bAccount;
	BankAccountDAO bankAccountDAO;
	BankAccountDTO bAccountDto;
	ArgumentCaptor<Long> argumentTimeStamp;

	protected void setUp() {
		bAccount = new BankAccount();
		bankAccountDAO = mock(BankAccountDAO.class);
		bAccount.setDao(bankAccountDAO);
		bAccountDto = bAccount.openAccount("123456789", 2L);
		argumentTimeStamp = ArgumentCaptor.forClass(Long.class);
	}

	// 1
	public void testOpenAccount() {
		assertEquals(0, bAccountDto.getBalance(), 0.001);
		assertEquals("123456789", bAccountDto.getAccountNumber());
	}

	// 2
	public void testGetAccount() {

		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		verify(bankAccountDAO).save(argumentDTO.capture(),
				argumentTimeStamp.capture());
		assertEquals(0.0, argumentDTO.getAllValues().get(0).getBalance(), 0.01);
		assertEquals("123456789", argumentDTO.getAllValues().get(0)
				.getAccountNumber());

	}

	// 3
	public void testDeposit() {
		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		bAccount.deposit(bAccountDto, 10, "phuongnv save money", 0L);
		verify(bankAccountDAO, times(2)).save(argumentDTO.capture(),
				argumentTimeStamp.capture());

		List<BankAccountDTO> savedAccountRecords = argumentDTO.getAllValues();

		assertEquals(10, savedAccountRecords.get(1).getBalance(), 0.001);

		assertEquals(bAccountDto.getAccountNumber(), savedAccountRecords.get(1)
				.getAccountNumber());

	}

	// 4
	public void testDepositWithTimeStamp() {
		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		bAccount.deposit(bAccountDto, 10, "phuongnv save money", 1L);
		verify(bankAccountDAO, times(2)).save(argumentDTO.capture(),
				argumentTimeStamp.capture());

		assertEquals(1L, argumentTimeStamp.getAllValues().get(1).longValue());
	}

	// 5
	public void testWithDraw() {
		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		// deposit
		bAccount.deposit(bAccountDto, 60, "phuongnv save money");
		verify(bankAccountDAO, times(2)).save(argumentDTO.capture(),
				argumentTimeStamp.capture());
		List<BankAccountDTO> savedAccountRecords = argumentDTO.getAllValues();
		assertEquals(60, savedAccountRecords.get(1).getBalance(), 0.001);

		// withdraw
		bAccount.withdraw(bAccountDto, -50, "Phuongnv rut tien");
		verify(bankAccountDAO, times(3)).save(argumentDTO.capture(),
				argumentTimeStamp.capture());
		List<BankAccountDTO> withDraw = argumentDTO.getAllValues();
		assertEquals(10, withDraw.get(2).getBalance(), 0.001);
	}

	// 7
	public void testGetTransactionsOccurred() {
		//
		bAccount.getTransactionsOccurred(bAccountDto.getAccountNumber());
		verify(bankAccountDAO).getListTransactions(
				bAccountDto.getAccountNumber());
	}

}

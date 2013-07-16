package com.qsoft.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import com.qsoft.bank.BankAccount;
import com.qsoft.bank.BankAccountDAO;
import com.qsoft.bank.BankAccountDTO;

public class TestBankAccount extends TestCase {
	private BankAccountDAO mockAccountDao = mock(BankAccountDAO.class);
	private Calendar mockCalendar = mock(Calendar.class);

	public void setUp() {
		reset(mockAccountDao);
		BankAccount.setBankAccountDAO(mockAccountDao);
		reset(mockCalendar);
		BankAccount.setCalendar(mockCalendar);
	}

	// 1
	public void testOpenAccount() {
		ArgumentCaptor<BankAccountDTO> accountDTOCaptor = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ArgumentCaptor<Long> timeStempCaptor = ArgumentCaptor
				.forClass(Long.class);
		Long nowTime = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(nowTime);

		BankAccount.openAccount("1234567890");

		verify(mockAccountDao, times(1)).save(accountDTOCaptor.capture(),
				timeStempCaptor.capture());
		assertEquals(timeStempCaptor.getValue(), nowTime);
		assertEquals(accountDTOCaptor.getValue().getBalance(), 0.0, 0.01);
		assertEquals(accountDTOCaptor.getValue().getAccountNumber(),
				"1234567890");
	}

	// 2
	public void testCanGetAccountByAccountNumber() {
		BankAccountDTO accountDTOPush = BankAccount.openAccount("0123456789");
		when(mockAccountDao.getAccountbyAccountNumber("0123456789"))
				.thenReturn(accountDTOPush);

		BankAccountDTO accountPop = BankAccount.getAccount("0123456789");

		verify(mockAccountDao, times(1))
				.getAccountbyAccountNumber("0123456789");
		assertEquals(accountPop, accountDTOPush);
	}

	// 3
	public void testAfterDepositBalanceWillIncrease() {
		ArgumentCaptor<BankAccountDTO> accountDTOCaptor = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		BankAccountDTO accountDTO = BankAccount.openAccount("0123456789");

		when(mockAccountDao.getAccountbyAccountNumber("0123456789"))
				.thenReturn(accountDTO);

		ArgumentCaptor<Long> timeStempCaptor = ArgumentCaptor
				.forClass(Long.class);
		Long nowTime = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(nowTime);

		BankAccount.deposit("0123456789", 200.0, "gui tien");

		verify(mockAccountDao, times(2)).save(accountDTOCaptor.capture(),
				timeStempCaptor.capture());

		assertEquals(timeStempCaptor.getValue(), nowTime);

		assertEquals(200.0, accountDTOCaptor.getValue().getBalance(), 0.01);
		assertEquals("0123456789", accountDTOCaptor.getValue()
				.getAccountNumber());
		assertEquals("gui tien", accountDTOCaptor.getValue().getDescription());
	}

	// 4
	public void testAfterDepositTransactionShouldBeSaveToDB() {
		BankAccountDTO bankAccount = BankAccount.openAccount("0123456789");
		when(mockAccountDao.getAccountbyAccountNumber("0123456789"))
				.thenReturn(bankAccount);
		Long timeStamp = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);

		BankAccount.deposit("0123456789", 200.0, "gui tien");

		verify(mockAccountDao, times(1)).saveTransaction("0123456789", 200.0,
				"gui tien", timeStamp);
	}

	// 5
	public void testAfterWithDrawBalanceHasIncrease() {
		ArgumentCaptor<BankAccountDTO> accountDTOCaptor = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		BankAccountDTO accountDTO = BankAccount.openAccount("0123456789");

		when(mockAccountDao.getAccountbyAccountNumber("0123456789"))
				.thenReturn(accountDTO);

		ArgumentCaptor<Long> timeStempCaptor = ArgumentCaptor
				.forClass(Long.class);
		Long nowTime = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(nowTime);

		BankAccount.withDraw("0123456789", 100.0, "gui tien");

		verify(mockAccountDao, times(2)).save(accountDTOCaptor.capture(),
				timeStempCaptor.capture());

		assertEquals(timeStempCaptor.getValue(), nowTime);

		assertEquals(100.0, accountDTOCaptor.getValue().getBalance(), 0.01);

		assertEquals("0123456789", accountDTOCaptor.getValue()
				.getAccountNumber());

		assertEquals("gui tien", accountDTOCaptor.getValue().getDescription());
	}

	// 6
	public void testAfterWithDrawTransactionShouldBeSaveToDB() {
		BankAccountDTO bankAccount = BankAccount.openAccount("0123456789");

		when(mockAccountDao.getAccountbyAccountNumber("0123456789"))
				.thenReturn(bankAccount);

		Long timeStamp = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);

		BankAccount.withDraw("0123456789", 200.0,
				"gui tien");

		verify(mockAccountDao, times(1)).saveTransaction("0123456789", 200.0,
				"gui tien", timeStamp);
	}
	// //7
	// public void testGetListTransactionOccurred() {
	// BankAccount.getListTransactionOccurred("0123456789");
	//
	// verify(mockAccountDao,
	// times(1)).getListTransactionOccurred("0123456789");
	// }
	// //8
	// public void testGetListTransactionOccurredInTime() {
	// Long startTime= System.currentTimeMillis();
	// Long stopTime= startTime+5000000L;
	//
	// BankAccount.getListTransactionOccurred("0123456789", startTime,
	// stopTime);
	//
	// verify(mockAccountDao, times(1)).getListTransactionOccurred("0123456789",
	// startTime, stopTime);
	// }
}

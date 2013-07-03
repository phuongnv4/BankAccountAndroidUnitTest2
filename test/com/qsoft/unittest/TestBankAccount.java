package com.qsoft.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import com.qsoft.bank.BankAccount;
import com.qsoft.bank.BankAccountDAO;
import com.qsoft.bank.BankAccountDTO;

public class TestBankAccount extends TestCase {
	BankAccount bAccount;
	BankAccountDAO bankAccountDAO;
	BankAccountDTO bAccountDto;

	protected void setUp() {
		bAccount = new BankAccount();
		bankAccountDAO = mock(BankAccountDAO.class);
		bAccount.setDao(bankAccountDAO);
		bAccountDto = bAccount.openAccount("123456789", 2L);
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
		ArgumentCaptor<Long> argumentTimeStamp = ArgumentCaptor
				.forClass(Long.class);
		verify(bankAccountDAO).save(argumentDTO.capture(),
				argumentTimeStamp.capture());
		assertEquals(argumentDTO.getAllValues().get(0).getBalance(), 0.0, 0.01);
		assertEquals("123456789", argumentDTO.getAllValues().get(0)
				.getAccountNumber());

	}
}

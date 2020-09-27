package library.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatronTest {
    IPatron mockPatron;
    String lastName = "Smith";
    String firstName = "JOhn";
    String email = "john@kool.com";
    long phoneNo = 1233123;
    int id = 123;


    @Mock ILoan mockLoan;

    @Test
    void testTakeOutLoanFailsIfRestricted() {
        IPatron.PatronState state = IPatron.PatronState.RESTRICTED;
        mockPatron = new Patron(lastName, firstName, email, phoneNo, id, state);
        Executable e = () ->  mockPatron.takeOutLoan(mockLoan);
        Throwable t = assertThrows(RuntimeException.class, e);
        final String msg = "Patron cannot borrow in RESTRICTED state";
        assertEquals(msg, t.getMessage());
    }

    @Test
    void testTakeOutLoanSucceedsIfNotRestricted() {
        IPatron.PatronState state = IPatron.PatronState.CAN_BORROW;
        mockPatron = new Patron(lastName, firstName, email, phoneNo, id, state);
        when(mockLoan.getId()).thenReturn(1);
        Executable e = () ->  mockPatron.takeOutLoan(mockLoan);
        assertDoesNotThrow(e);
    }

    @Test
    void testReturnsTrueWhenLoansOverdue() {
        IPatron.PatronState state = IPatron.PatronState.CAN_BORROW;
        HashMap loans = new HashMap<>();
        loans.put(1, mockLoan);
        mockPatron = new Patron(lastName, firstName, email, phoneNo, id, state, loans);
        when(mockLoan.isOverDue()).thenReturn(true);
        boolean hasOverdueLoans = mockPatron.hasOverDueLoans();
        assertTrue(hasOverdueLoans);
    }

    @Test
    void testReturnsFalseWhenLoansNotOverdue() {
        IPatron.PatronState state = IPatron.PatronState.CAN_BORROW;
        HashMap loans = new HashMap<>();
        loans.put(1, mockLoan);
        mockPatron = new Patron(lastName, firstName, email, phoneNo, id, state, loans);
        when(mockLoan.isOverDue()).thenReturn(false);
        boolean hasOverdueLoans = mockPatron.hasOverDueLoans();
        assertFalse(hasOverdueLoans);
    }
}
package library.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoanTest {
    ILoan loan;
    int loanId = 123;
    Date date;

    @Mock
    IPatron mockPatron;
    @Mock
    IBook mockBook;

    @Test
    void testCommitThrowsErrorIfStateCurrent() {
        ILoan.LoanState state = ILoan.LoanState.CURRENT;
        loan = new Loan(mockBook, mockPatron, state);
        Executable e = () ->  loan.commit(loanId, date);
        Throwable t = assertThrows(RuntimeException.class, e);
        final String msg = "Cannot commit a non PENDING loan";
        assertEquals(msg, t.getMessage());
    }

    @Test
    void testCommitThrowsErrorIfStateOverdue() {
        ILoan.LoanState state = ILoan.LoanState.OVER_DUE;
        loan = new Loan(mockBook, mockPatron, state);
        Executable e = () ->  loan.commit(loanId, date);
        Throwable t = assertThrows(RuntimeException.class, e);
        final String msg = "Cannot commit a non PENDING loan";
        assertEquals(msg, t.getMessage());
    }

    @Test
    void testCommitThrowsErrorIfStateDischarged() {
        ILoan.LoanState state = ILoan.LoanState.DISCHARGED;
        loan = new Loan(mockBook, mockPatron, state);
        Executable e = () ->  loan.commit(loanId, date);
        Throwable t = assertThrows(RuntimeException.class, e);
        final String msg = "Cannot commit a non PENDING loan";
        assertEquals(msg, t.getMessage());
    }

    @Test
    void testCommitSucceedsIfStatePending() {
        ILoan.LoanState state = ILoan.LoanState.PENDING;
        loan = new Loan(mockBook, mockPatron, state);
        Executable e = () ->  loan.commit(loanId, date);
        assertDoesNotThrow(e);
    }

    @Test
    void testCommitCallsBookBorrowOnSuccess() {
        ILoan.LoanState state = ILoan.LoanState.PENDING;
        loan = new Loan(mockBook, mockPatron, state);
        loan.commit(loanId, date);
        verify(mockBook).borrowFromLibrary();
    }

    @Test
    void testCommitCallsTakeOutLoanSuccess() {
        ILoan.LoanState state = ILoan.LoanState.PENDING;
        loan = new Loan(mockBook, mockPatron, state);
        loan.commit(loanId, date);
        verify(mockPatron).takeOutLoan(loan);
    }

    @Test
    void testCheckOverdueReturnsFalseWhenNotOverdue() throws ParseException {
        ILoan.LoanState state = ILoan.LoanState.CURRENT;
        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-22");
        loan = new Loan(mockBook, mockPatron, state, dueDate);
        boolean isOverdue = loan.checkOverDue(dueDate);
        assertFalse(isOverdue);
    }

    @Test
    void testCheckOverdueReturnsTrueWhenOverdue() throws ParseException {
        ILoan.LoanState state = ILoan.LoanState.CURRENT;
        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-22");
        Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-23");
        loan = new Loan(mockBook, mockPatron, state, dueDate);
        boolean isOverdue = loan.checkOverDue(currentDate);
        assertTrue(isOverdue);
    }
}
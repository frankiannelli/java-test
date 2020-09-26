package library.entities;

import java.util.List;

public class PatronStub2 implements IPatron {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public List<ILoan> getLoans() {
        return null;
    }

    @Override
    public double getFinesPayable() {
        return ILibrary.MAX_FINES_OWED + 1;
    }

    @Override
    public boolean hasOverDueLoans() {
        return false;
    }

    @Override
    public void restrictBorrowing() {

    }

    @Override
    public void allowBorrowing() {

    }

    @Override
    public int getNumberOfCurrentLoans() {
        return 0;
    }

    @Override
    public void takeOutLoan(ILoan loan) {

    }

    @Override
    public void dischargeLoan(ILoan loan) {

    }

    @Override
    public void incurFine(double fine) {

    }

    @Override
    public double payFine(double paymentAmount) {
        return 0;
    }
}

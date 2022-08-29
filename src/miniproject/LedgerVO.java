package miniproject;

public class LedgerVO {
	private int stdNo;
	private int bkNo;
	private String borrowDate;
	private String expireDate;
	private String returnDate;
	private boolean bookOut;
	
	public LedgerVO(int stdNo, int bkNo, String borrowDate, String expireDate, String returnDate, boolean bookOut) {
		this.stdNo = stdNo;
		this.bkNo = bkNo;
		this.borrowDate = borrowDate;
		this.expireDate = expireDate;
		this.returnDate = returnDate;
		this.bookOut = bookOut;
	}
	
	public int getStdNo() {
		return stdNo;
	}
	public void setStdNo(int stdNo) {
		this.stdNo = stdNo;
	}
	public int getBkNo() {
		return bkNo;
	}
	public void setBkNo(int bkNo) {
		this.bkNo = bkNo;
	}
	public String getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public boolean isBookOut() {
		return bookOut;
	}

	public void setBookOut(boolean bookOut) {
		this.bookOut = bookOut;
	}

	public int getOverdueDate() {
		
		return 1;
	}
}
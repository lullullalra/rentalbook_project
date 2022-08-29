package miniproject;

import java.util.Date;

public class BookVO {
	private int bkNo;
	private String bkName;
	private String author;
	private String prchDate;
	private int price;
	
	public BookVO(int bkNo, String bkName, String author, String prchDate, int price) {
		this.bkNo = bkNo;
		this.bkName = bkName;
		this.author = author;
		this.prchDate = prchDate;
		this.price = price;
	}

	public int getBkNo() {
		return bkNo;
	}

	public void setBkNo(int bkNo) {
		this.bkNo = bkNo;
	}

	public String getBkName() {
		return bkName;
	}

	public void setBkName(String bkName) {
		this.bkName = bkName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrchDate() {
		return prchDate;
	}

	public void setPrchDate(String prchDate) {
		this.prchDate = prchDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return bkNo + "\t" + bkName + "\t" + author + "\t" + prchDate
				+ "\t" + price;
	}
	
	
}
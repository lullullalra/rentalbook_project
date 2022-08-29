package miniproject;

public class StudentVO {
	private int stdNo;
	private String stdName;
	private String major;
	private int age;
	private int overdueInfo;
	private int ban;

	public StudentVO(int stdNo, String stdName, String major, int age) {
		this.stdNo = stdNo;
		this.stdName = stdName;
		this.major = major;
		this.age = age;
		this.overdueInfo = 0;
		this.ban = 0;
	}
	
	public StudentVO(int stdNo, String stdName, String major, int age, int overdueInfo, int ban) {
		this.stdNo = stdNo;
		this.stdName = stdName;
		this.major = major;
		this.age = age;
		this.overdueInfo = overdueInfo;
		this.ban = ban;
	}


	public int getStdNo() {
		return stdNo;
	}


	public void setStdNo(int stdNo) {
		this.stdNo = stdNo;
	}


	public String getStdName() {
		return stdName;
	}


	public void setStdName(String stdName) {
		this.stdName = stdName;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getOverdueInfo() {
		return overdueInfo;
	}


	public void setOverdueInfo(int overdueInfo) {
		this.overdueInfo = overdueInfo;
	}


	public int getBan() {
		return ban;
	}


	public void setBan(int ban) {
		this.ban = ban;
	}

	@Override
	public String toString() {
		return stdNo + "\t" + stdName + "\t" + major + "\t" + age;
	}			
	
}
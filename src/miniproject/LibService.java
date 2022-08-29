package miniproject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LibService {
	private int expireDate;
	private int maxBook;
	private int maxResBook;
	private int maxResStudent;
	private Date today;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public LibService() {
		this.expireDate = 7;
		this.maxBook = 5;
		this.maxResBook = 3;
		this.maxResStudent = 2;
		today = new Date();
	}
	
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	String tmp = "2012-11-19";
//	Date date = formatter.parse(tmp);
//	Calendar cal = Calendar.getInstance();
//	cal.setTime(date);
//
//	System.out.println(sdf.format(cal.getTime()));
//	cal.add(Calendar.DATE, 7);
//	System.out.println(sdf.format(cal.getTime()));
	
//	1)대출에 대한 트랜잭션은 자동으로 500건을 생성하여 처리한다. 
	public void autoSet() throws SQLException {
		LibDAO dao = new LibDAO();
		List<StudentVO> stdList = dao.selectAllStudentData();
		List<BookVO> bookList = dao.selectAllBookData();
		List<LedgerVO> ledgerList = new ArrayList<>();
		Random r = new Random();
		//2022년 3~7월 1~30일 사이의 랜덤 대출데이터 생성
		Calendar cal = Calendar.getInstance();
		Date date = null;
		for(int i=0; i<500; i++) {
			int tmp = r.nextInt(30)+1;
			String dateString = "2022-0"+(r.nextInt(5)+3)+"-";
			if(tmp < 10) {
				dateString += ("0"+tmp);
			}else {
				dateString += tmp;
			}
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//expireDate 만큼 날짜를 증가시켜 반납예정일 계산
			cal.setTime(date);
			cal.add(Calendar.DATE, expireDate);
			String exDate = sdf.format(cal.getTime());
			cal.add(Calendar.DATE, -expireDate);
			
			ledgerList.add(new LedgerVO(stdList.get(r.nextInt(stdList.size())).getStdNo(),
					bookList.get(r.nextInt(bookList.size())).getBkNo(),
					dateString,
					exDate,
					"0",
					false));
		}

		setNewLedger(ledgerList);
	}
	
//	- 알고자 하는 대출 정보는 아래와 같다
//	1) 현재 대출중인 책의 정보, 대출자, 반납일, 연체여부에 대한 정보를 제공한다.
	public void solve1() {
		
	}
	
// 대출
	public void solve3(StudentVO sv, BookVO bv) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();        
		String nowTime = sdf.format(now);
		List<LedgerVO> lv = new ArrayList<>();
	    lv.add(new LedgerVO(sv.getStdNo(), bv.getBkNo(), nowTime, "2022-10-20", "2022-10-20", false));
	    setNewLedger(lv);
	}
	
//	반납
	public void solve3_2(int stdNo, int bkNo) throws SQLException {
		LibDAO dao = new LibDAO();
		Date now = new Date();
		List<LedgerVO> lv = dao.selectAllLedgerData();
		String sql = null;
		for(int i=0; i<lv.size(); i++ ) {
			if((lv.get(i).getStdNo() == stdNo) && (lv.get(i).getBkNo() == bkNo)) {
				sql = "update legder set return_date = "+now+", book_out = 0 where (bk_no = "+lv.get(i).getBkNo()+") and (std_no = "+lv.get(i).getStdNo()+") and (book_out = 1)";
				break;
			}
		}
		dao.updateData(sql);
	}
	
	// 학번으로 학생 정보 조회
	public void getFindStd(int stdNo) throws SQLException {
		LibDAO dao = new LibDAO();
		StudentVO student = dao.selectStudent1Data(stdNo);
		System.out.print("조회 결과: ");
		System.out.println(student);
	}
	
//	6) 대출반납이 가장 늦은 학과에 대한 정보
	public void solve9() throws SQLException {
		LibDAO dao = new LibDAO();
		System.out.println("대출반납이 가장 늦은 학과는 "+dao.selectLatesttMajor()+" 입니다.");
		dao.selectLatesttMajor();
	}
	
//	5) 대출을 가장 많이 하는 학과에 대한 정보
	public void solve8() throws SQLException {
		LibDAO dao = new LibDAO();
		String[] str = dao.selectLargestMajor();
		System.out.println("대출을 가장 많이 하는 학과는 "+str[0]+" , "+str[1]+"건 입니다.");
	}
	
//	4) 대출반납이 가장 빠른 학생에 대한 정보
	public void solve7() throws SQLException {
		LibDAO dao = new LibDAO();
		System.out.println("학번\t이름\t전공\t나이");
		System.out.println(dao.selectFastestStudent());
	}
	
//	3) 대출기간이 가장 짧은 도서에 대한 정보
	public void solve6() throws SQLException {
		LibDAO dao = new LibDAO();
		System.out.println("분류번호\t도서명\t저자\t구입일\t가격");
		System.out.println(dao.selectShortestBook());
	}

//	2) 대출자 상위 5위에 대한 정보
	public void solve5() throws SQLException {
		LibDAO dao = new LibDAO();
		List<StudentVO> list = dao.selectStudentData(5);
		System.out.println("학번\t이름\t전공\t나이");
		for(StudentVO vo : list) {
			System.out.println(vo);
		}
	}
	 
//	1) 대출 도서 상위 5위에 대한 정보
	public void solve4() throws SQLException {
		LibDAO dao = new LibDAO();
		List<BookVO> list = dao.selectBookData(5);
		System.out.println("분류번호\t도서명\t저자\t구입일\t가격");
		for(BookVO vo : list) {
			System.out.println(vo);
		}	
	}
	
	//대출 기간 설정
	public void setExpireDate(int expireDate) {
		this.expireDate = expireDate;
	}

	//최대 대출 권수 설정
	public void setMaxBook(int maxBook) {
		this.maxBook = maxBook;
	}

	//최대 예약 권수 설정
	public void setMaxResBook(int maxResBook) {
		this.maxResBook = maxResBook;
	}

	//도서당 최대 예약 인원 설정
	public void setMaxResStudent(int maxResStudent) {
		this.maxResStudent = maxResStudent;
	}
	
	//데이터베이스에 책 정보 입력 8
	public void setNewBook(List<BookVO> books) throws SQLException {
		LibDAO dao = new LibDAO();
		String sql = "";
		for(BookVO vo : books) {
			sql = "book values ("+vo.getBkNo()+","+"'"+vo.getBkName()+"'"+","+
		"'"+vo.getAuthor()+"'"+","+"'"+vo.getPrchDate()+"'"+","+vo.getPrice()+")";
			dao.insertData(sql);
		}
	}
	
	//데이터베이스에 학생 정보 입력 9
	public void setNewStudent(List<StudentVO> students) throws SQLException {
		LibDAO dao = new LibDAO();
		String sql = "";
		for(StudentVO vo : students) {
			sql = "student values ("+vo.getStdNo()+","+"'"+vo.getStdName()+"'"+","+
		"'"+vo.getMajor()+"'"+","+vo.getAge()+","+vo.getOverdueInfo()+","+vo.getBan()+")";
			dao.insertData(sql);
		}	
	}
	
	//데이터베이스에 대출 정보 입력
	public void setNewLedger(List<LedgerVO> Ledgers) throws SQLException {
		LibDAO dao = new LibDAO();
		String sql = "";
		for(LedgerVO vo : Ledgers) {
			int bookOut = 0;
			if(vo.isBookOut()) {
				bookOut = 1;
			}
			sql = "Ledger values ("+vo.getStdNo()+","+vo.getBkNo()+","+"'"+vo.getBorrowDate()+"'"+","+
			"'"+vo.getExpireDate()+"'"+","+"'"+vo.getReturnDate()+"'"+","+bookOut+")";
			dao.insertData(sql);
		}
	}
	
	//student 테이블 초기화
	public void truncateStudent() throws SQLException {
		LibDAO dao = new LibDAO();
		dao.truncateTable("student");
	}
	
	//book 테이블 초기화
	public void truncateBook() throws SQLException {
		LibDAO dao = new LibDAO();
		dao.truncateTable("book");
	}
	
	//ledger 테이블 초기화
	public void truncateLedger() throws SQLException {
		LibDAO dao = new LibDAO();
		dao.truncateTable("ledger");
	}
	
}


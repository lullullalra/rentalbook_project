package miniproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibDAO {
	
	// 추가) 회원 정보 조회
	public StudentVO selectStudent1Data(int stdNo) throws SQLException {
		StudentVO student = null;
		Connection con = ConnectionManager.getConnection();
		String sql = "select*from student where student.std_no = ? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, stdNo);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			student = new StudentVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));	
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return student;
	}
	
	//저장된 모든 책 정보
	public List<BookVO> selectAllBookData() throws SQLException {
		List<BookVO> list = new ArrayList<>();
		Connection con = ConnectionManager.getConnection();
		String sql = "select*from book";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			list.add(new BookVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5)));	
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return list;
	}
	
	
	//저장된 모든 학생 정보
	public List<StudentVO> selectAllStudentData() throws SQLException {
		List<StudentVO> list = new ArrayList<>();
		Connection con = ConnectionManager.getConnection();
		String sql = "select*from student";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			list.add(new StudentVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));	
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return list;
	}
	
	//저장된 모든 대출 정보
	public List<LedgerVO> selectAllLedgerData() throws SQLException {
		List<LedgerVO> list = new ArrayList<>();
		Connection con = ConnectionManager.getConnection();
		String sql = "select*from student";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			boolean bookOut = false;
			if(rs.getInt(5) == 1) {
				bookOut = true;
			}
			list.add(new LedgerVO(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),bookOut));	
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return list;
	} 
	
//	6) 대출반납이 가장 늦은 학과에 대한 정보
	public String selectLatesttMajor() throws SQLException {
		String str = null;
		Connection con = ConnectionManager.getConnection();
		String sql = "select student.major, sum(ledger.borrow_date-ledger.return_date) from student, ledger "+
		"where student.std_no = ledger.std_no group by student.major order by sum(ledger.borrow_date-ledger.return_date) desc, count(student.major)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			str = rs.getString(1);
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return str;
	}
	
//	5) 대출을 가장 많이 하는 학과에 대한 정보
	public String[] selectLargestMajor() throws SQLException {
		String[] str = new String[2];
		Connection con = ConnectionManager.getConnection();
		String sql = "select student.major, count(student.major) from student, ledger "
				+ "where student.std_no = ledger.std_no group by student.major order by count(student.major) desc limit 1";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			str[0] = rs.getString(1);
			str[1] = rs.getString(2);
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return str;
	}
	
	
//	4) 대출반납이 가장 빠른 학생에 대한 정보
	public StudentVO selectFastestStudent() throws SQLException {
		StudentVO student = null;
		Connection con = ConnectionManager.getConnection();
		String sql = "select*from student, ledger where student.std_no = ledger.std_no "+
		"group by student.std_no order by (expire_date-return_date), count(*) desc";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			student = new StudentVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));	
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return student;
	}
	
//	3) 대출기간이 가장 짧은 도서에 대한 정보
	public BookVO selectShortestBook() throws SQLException {
		BookVO book = null;
		Connection con = ConnectionManager.getConnection();
		String sql = "select min(borrow_date-return_date) from ledger where book_out = 0";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			book = new BookVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));	
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return book;
	}
	
//	2) 대출자 상위 5위에 대한 정보
	public List<StudentVO> selectStudentData(int top) throws SQLException {
		List<StudentVO> list = new ArrayList<>();
		Connection con = ConnectionManager.getConnection();
		String sql = "select*from student, ledger where student.std_no = ledger.std_no group by student.std_no order by count(*) desc limit ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, top);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			list.add(new StudentVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));	
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return list;
	}
	
//	1) 대출 도서 상위 5위에 대한 정보
	public List<BookVO> selectBookData(int top) throws SQLException {
		List<BookVO> list = new ArrayList<>();
		Connection con = ConnectionManager.getConnection();
		String sql = "select*from book, ledger where book.bk_no = ledger.bk_no group by book.bk_no order by count(*) desc, book.bk_no limit ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, top);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			list.add(new BookVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5)));	
		}
		
		ConnectionManager.closeConnection(con, pstmt, rs);
		return list;
	}
	
	// 테이블 행 추가
	public boolean insertData(String data) throws SQLException {
		boolean flag = false;
		Connection con = ConnectionManager.getConnection();
		String sql = "insert into "+data;
		PreparedStatement pstmt = con.prepareStatement(sql);
		int affectedCount = pstmt.executeUpdate();
		if(affectedCount != 0) {
			flag = true;
		}
		
		ConnectionManager.closeConnection(con, pstmt, null);
		return flag;
	}
	
	//테이블 행 삭제
//	public boolean deleteData() throws SQLException {
//		boolean flag = false;
//		Connection con = ConnectionManager.getConnection();
//		String sql = "";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		int affectedCount = pstmt.executeUpdate();
//		if(affectedCount != 0) {
//			flag = true;
//		}
//		
//		ConnectionManager.closeConnection(con, pstmt, null);
//		return flag;
//	}
	
	//테이블 업데이트
	public boolean updateData(String sql) throws SQLException {
		boolean flag = false;
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		int affectedCount = pstmt.executeUpdate();
		if(affectedCount != 0) {
			flag = true;
		}
		
		ConnectionManager.closeConnection(con, pstmt, null);
		return flag;
	}
	
	//테이블 초기화
	public void truncateTable(String table) throws SQLException {
		Connection con = ConnectionManager.getConnection();
		String sql = "truncate "+table;
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.executeUpdate();
		ConnectionManager.closeConnection(con, pstmt, null);
	}
}

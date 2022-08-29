package miniproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LibMain {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LibMain lm = new LibMain();
		try {
			lm.menu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void menu() throws SQLException, Exception {
		Scanner sc = new Scanner(System.in);
		boolean isStop= true;
		LibService ls = new LibService();
		//프로그램 기본셋팅, 파일에서 읽어온 30건의 도서정보, 10건의 학생정보 및
		//자동으로 생성된 500건의 대출정보를 데이터베이스에 입력한다.
		ls.truncateBook();
		ls.setNewBook(readBookFile());
		ls.truncateStudent();
		ls.setNewStudent(readStudentFile());
		ls.truncateLedger();
		ls.autoSet();
		
		List<StudentVO> sList = new ArrayList<StudentVO>();
		List<BookVO> bList = new ArrayList<BookVO> ();
		String[] s = new String[5];
		int findStd = 0;
		
		
		while(isStop) {
			System.out.println("----------------------");
			System.out.println("1. 대출조회\n2. 이용현황\n3. 신규도서\n4. 신규회원\n5. 회원조회\n6. 종료");
			System.out.println("----------------------");
			
			System.out.println("메뉴 중 하나를 선택하세요 > ");
			int cmd = sc.nextInt();
			
			switch (cmd) {
			case 1:
				System.out.println("현재 대출중인 책에 대한 정보를 조회합니다.\n");
				
				break;
			case 2:
				System.out.println("회원들의 도서 이용현황 정보를 조회합니다.\n");
				
				System.out.println("대출 도서 상위 5위에 대한 정보");
				ls.solve4();
				System.out.println();
				
				System.out.println("대출자 상위 5위에 대한 정보");
				ls.solve5();
				System.out.println();
				
//				ls.solve6();
				
				System.out.println("대출반납이 가장 빠른 학생에 대한 정보");
				ls.solve7();
				System.out.println();
				
				System.out.println("대출을 가장 많이 하는 학과에 대한 정보");
				ls.solve8();
				System.out.println();
				
				System.out.println("대출반납이 가장 늦은 학과에 대한 정보");
				ls.solve9();
				System.out.println();
				break;
			case 3:
				System.out.println("신규 도서 등록을 진행합니다.\n");
				System.out.println("'분류번호,제목,저자,구매일(YYYY-MM-DD),가격' 순서대로 하나씩 입력하세요.\n");
				for(int i=0; i<5; i++) {
					s[i] = sc.next();
				}
				bList.add(new BookVO(Integer.parseInt(s[0]),s[1],s[2],s[3],Integer.parseInt(s[4])));
				ls.setNewBook(bList);
				break;
			case 4:
				System.out.println("신규 회원 등록을 진행합니다.\n");
				System.out.println("'학번,이름,전공,나이' 순서대로 하나씩 입력해주세요.\n");
				for(int i=0; i<4; i++) {
					s[i] = sc.next();
				}
				sList.add(new StudentVO(Integer.parseInt(s[0]),s[1],s[2],Integer.parseInt(s[3])));
				ls.setNewStudent(sList);
				break;
			case 5:
				System.out.println("회원 정보를 조회합니다.\n");
				System.out.println("조회할 학생의 학번을 입력해주세요.\n");
				findStd = sc.nextInt();
				ls.getFindStd(findStd);
				break;
			case 6:
				System.out.println("업무를 종료합니다.\n");
				isStop = false;
				
			}
		}
		sc.close();
	}
	
	public List<StudentVO> readStudentFile() throws IOException {
		File f = new File("./Student.csv");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String tmp = "";
		List<StudentVO> sv = new ArrayList<>();
		while ((tmp=br.readLine()) != null){
			String[] tmp2 = tmp.split(",");
			for(int i=0; i<tmp2.length; i++) {
				tmp2[i] = tmp2[i].trim();
			}
			sv.add(new StudentVO(Integer.parseInt(tmp2[0].replace("\uFEFF", "")), tmp2[1], tmp2[2], Integer.parseInt(tmp2[3])));
		}
		
		br.close();
		fr.close();
		return sv;
	}
	
	public List<BookVO> readBookFile() throws IOException {
		File f = new File("./BookData.csv");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String tmp = "";
		List<BookVO> bv = new ArrayList<>();
		while ((tmp=br.readLine()) != null){
			String[] tmp2 = tmp.split(",");
			for(int i=0; i<tmp2.length; i++) {
				tmp2[i] = tmp2[i].trim();
			}
			bv.add(new BookVO(Integer.parseInt(tmp2[0].replace("\uFEFF", "")), tmp2[1], tmp2[2], tmp2[3], Integer.parseInt(tmp2[4])));
		}
		
		br.close();
		fr.close();
		return bv;
	}
	
}
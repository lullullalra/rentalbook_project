package miniproject;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateCal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			String now = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			now = sdf.format(new Date());
			Date getToday = sdf.parse(now);

			Calendar cal = Calendar.getInstance();
			cal.setTime(getToday);
			cal.add(Calendar.DATE, 7);
			System.out.println("반납 기한: "+sdf.format(cal.getTime())+"입니다.");
			
			System.out.println("반납일: " + sdf.format(getToday));
			System.out.println("반납기한: " + sdf.format(cal.getTime()));
			
			if(getToday.compareTo(cal.getTime()) > 0) {
				System.out.println("연체");
			} else if(getToday.compareTo(cal.getTime()) < 0) {
				System.out.println("반납 완료");
			} else if(getToday.compareTo(cal.getTime()) == 0) {
				System.out.println("같은 날");
			}
			
			long result = cal.getTime().getTime() - getToday.getTime();
			System.out.println("연체일수: "+result/(24*60*60*1000));
			
			
		} catch (ParseException ex) {
		}
	}
}
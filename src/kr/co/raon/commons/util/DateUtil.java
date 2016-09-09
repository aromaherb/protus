package kr.co.raon.commons.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

	public static final  int YEAR       = 1;
    public static final  int MONTH      = 2;
    public static final  int DATE       = 3;
    public static final  int MONTHFIRST = 4;
    public static final  int MONTHEND   = 5;
    
	/**
	  * <p>현재 날짜와 시각을  yyyyMMdd 형태로 변환 후 return.
	  *
	  * @param null
	  * @return yyyyMMdd
	  * 
	  * <pre> 
	  *  - 사용 예
	  * String date = DateUtil.getYyyymmdd()
	  * </pre>
	  */
    public static String getYyyymmdd(Calendar cal) {
    	Locale currentLocale = new Locale("KOREAN", "KOREA");
    	String pattern = "yyyyMMdd";
    	SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
    	return formatter.format(cal.getTime());
    }

	/**
	  * <p>GregorianCalendar 객체를 반환함.
	  * 
	  * @param yyyymmdd 날짜 인수
	  * @return GregorianCalendar
	  * @see java.util.Calendar 
	  * @see java.util.GregorianCalendar
	  * <p><pre>
	  *  - 사용 예
	  * Calendar cal = DateUtil.getGregorianCalendar(DateUtil.getCurrentYyyymmdd())
	  * </pre>
	  */
    public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {
    	if(yyyymmdd == null || yyyymmdd.length() < 8) return null;
    	int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
    	int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
    	int dd = Integer.parseInt(yyyymmdd.substring(6));

    	GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0, 0, 0);

    	return calendar;

    }

    /**
    * <p>현재 날짜와 시각을  yyyyMMddhhmmss 형태로 변환 후 return.
    * 
    * @param null
    * @return yyyyMMddhhmmss
	* @see java.util.Date
    * @see java.util.Locale
    * <p><pre> 
    *  - 사용 예
    * String date = DateUtil.getCurrentDateTime()
    * </pre>
    */
	public static String getCurrentDateTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}

	/**
	* <p>현재  시각을  hhmmss 형태로 변환 후 return.
	* 
	* @param null
	* @return hhmmss
	* @see java.util.Date
    * @see java.util.Locale
    * <p><pre>
    *  - 사용 예
    *   String date = DateUtil.getCurrentDateTime()
    * </pre>
    */
	public static String getCurrentTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "HHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}
	
	/**
	* <p>현재  시각을  hmmss 형태(시분초 사이는 구분자르르 넣는다)로 변환 후 return.
	* 
	* @param delimiter - 구분자
	* @return hhmmss
	* @see java.util.Date
    * @see java.util.Locale
    * <p><pre>
    *  - 사용 예
    *   String date = DateUtil.getCurrentDateTime()
    * </pre>
    */
	public static String getCurrentTimeAddDelimeter(String delimiter) {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "HH" + delimiter + "mm" + delimiter + "ss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}

	/**
	* <p>현재 날짜를 yyyyMMdd 형태로 변환 후 return.
	* 
	* @param null
	* @return yyyyMMdd * 
    * <p><pre> 
    *  - 사용 예
    * String date = DateUtil.getCurrentYyyymmdd()
    * </pre>
    */
	public static String getCurrentYyyymmdd() {
		return getCurrentDateTime().substring(0, 8);
	}
	
	
	/**
     * <p>주로 일자를 구하는 메소드.
     *
     * @param yyyymm 년월
     * @param week 몇번째 주
     * @param pattern 리턴되는 날짜패턴 (ex:yyyyMMdd)
     * @return 연산된 날짜
     * @see java.util.Calendar
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.getWeekToDay("200801" , 1, "yyyyMMdd")
     * </pre>
     */
	public static String getWeekToDay(String yyyymm, int week, String pattern) {

		Calendar cal = Calendar.getInstance(Locale.FRANCE);

		int new_yy = Integer.parseInt(yyyymm.substring(0,4));
		int new_mm = Integer.parseInt(yyyymm.substring(4,6));
		int new_dd = 1;

		cal.set(new_yy,new_mm-1,new_dd);
  
		// 임시 코드
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			week = week - 1;
		}  
  
		cal.add(Calendar.DATE, (week-1)*7+(cal.getFirstDayOfWeek()-cal.get(Calendar.DAY_OF_WEEK)));
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.FRANCE);
        
		return formatter.format(cal.getTime());
        
	}

    /**
     * <p>지정된 플래그에 따라 연도 , 월 , 일자를 연산한다.
     *
     * @param field 연산 필드
     * @param amount 더할 수
     * @param date 연산 대상 날짜
     * @return 연산된 날짜
     * @see java.util.Calendar
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.getOpDate(java.util.Calendar.DATE , 1, "20080101")
     * </pre>
     */
	public static String getOpDate(int field, int amount, String date) {
		GregorianCalendar calDate = getGregorianCalendar(date);
		if (field == Calendar.YEAR) {
			calDate.add(GregorianCalendar.YEAR, amount);
		} else if (field == Calendar.MONTH) {
			calDate.add(GregorianCalendar.MONTH, amount);
		} else {
			calDate.add(GregorianCalendar.DATE, amount);
		}
		return getYyyymmdd(calDate);
	}

	/**
     *  <p>입력된 일자를 더한 주를 구하여 return한다
     *  
     * @param yyyymmdd 년도별 
     * @param addDay 추가일 
     * @return 연산된 주
     * @see java.util.Calendar
     * <p><pre>
     *  - 사용 예
     * int date = DateUtil.getWeek(DateUtil.getCurrentYyyymmdd() , 0)
     * </pre>
     */
	public static int getWeek(String yyyymmdd, int addDay){
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		int new_yy = Integer.parseInt(yyyymmdd.substring(0,4));
		int new_mm = Integer.parseInt(yyyymmdd.substring(4,6));
		int new_dd = Integer.parseInt(yyyymmdd.substring(6,8));

		cal.set(new_yy,new_mm-1,new_dd);
		cal.add(Calendar.DATE, addDay);

		int week = cal.get(Calendar.DAY_OF_WEEK);
		return week;
	}

    /** 
     * <p>입력된 년월의 마지막 일수를 return 한다.
     * 
     * @param year
     * @param month
     * @return 마지막 일수 
     * @see java.util.Calendar
     * <p><pre>
     *  - 사용 예
     * int date = DateUtil.getLastDayOfMon(2008 , 1)
     * </pre>
     */
	public static int getLastDayOfMon(int year, int month) { 

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
     
	}//:

    /** 
     * <p>입력된 년월의 마지막 일수를 return한다
     * 
     * @param year
     * @param month
     * @return 마지막 일수  
     * <p><pre>
     *  - 사용 예
     * int date = DateUtil.getLastDayOfMon("2008")
     * </pre>
     */ 
	public static int getLastDayOfMon(String yyyymm) {

		Calendar cal = Calendar.getInstance();
		int yyyy = Integer.parseInt(yyyymm.substring(0, 4));
		int mm = Integer.parseInt(yyyymm.substring(4)) - 1;

		cal.set(yyyy, mm, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
    
    /** 
     * <p>입력된 날자가 올바른지 확인합니다. 
     * 
     * @param yyyymmdd
     * @return boolean 
     * <p><pre>
     *  - 사용 예
     * boolean b = DateUtil.isCorrect("20080101")
     * </pre>
     */ 
	public static boolean isCorrect(String yyyymmdd){     
		boolean flag  =  false;
		if(yyyymmdd.length() < 8 ) return false; 
		try{
			int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			int mm   = Integer.parseInt(yyyymmdd.substring(4, 6));
			int dd   = Integer.parseInt(yyyymmdd.substring(6));
			flag     = DateUtil.isCorrect( yyyy,  mm,  dd);
		}catch(Exception ex){
			return false; 
		}
		return flag;
	}//:
    
    /** 
     * <p>입력된 날자가 올바른 날자인지 확인합니다. 
     * 
     * @param yyyy
     * @param mm
     * @param dd
     * @return boolean
     * <p><pre>
     *  - 사용 예
     * boolean b = DateUtil.isCorrect(2008,1,1)
     * </pre>
     */
    public static boolean isCorrect(int  yyyy, int mm, int dd){
    	if(yyyy < 0 || mm < 0 || dd < 0) return false;
    	if(mm > 12 || dd > 31) return false; 
     
    	String year     = "" + yyyy; 
    	String month    = "00" + mm;
    	String year_str = year + month.substring(month.length() - 2);
    	int endday      = DateUtil.getLastDayOfMon(year_str);
     
    	if(dd > endday) return false; 
     
    	return true; 
    }//:
    
    /** 
     * <p>현재 일자를 입력된 type의 날짜로 반환합니다.
     * 
     * @param type
     * @return String
     * @see java.text.DateFormat
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.getThisDay("yyyymmddhhmmss")
     * </pre>
     */    
    public static String getThisDay(String type){
    	Date date = new Date();
    	SimpleDateFormat sdf = null;
        
    	try{
    		if(type.toLowerCase().equals("yyyymmdd")){
    			sdf = new SimpleDateFormat("yyyyMMdd");
    			return sdf.format(date);
    		}
    		if(type.toLowerCase().equals("yyyymmddhh")){
    			sdf = new SimpleDateFormat("yyyyMMddHH");
    			return sdf.format(date);
    		}
    		if(type.toLowerCase().equals("yyyymmddhhmm")){
    			sdf = new SimpleDateFormat("yyyyMMddHHmm");
    			return sdf.format(date);
    		}
    		if(type.toLowerCase().equals("yyyymmddhhmmss")){
             sdf = new SimpleDateFormat("yyyyMMddHHmmss");
             return sdf.format(date);
    		}
    		if(type.toLowerCase().equals("yyyymmddhhmmssms")){
    			sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    			return sdf.format(date);
    		} else {
    			sdf = new SimpleDateFormat(type);
    			return sdf.format(date);
    		}
    	}catch(Exception e){
    		return "[ ERROR ]: parameter must be 'YYYYMMDD', 'YYYYMMDDHH', 'YYYYMMDDHHSS'or 'YYYYMMDDHHSSMS'";
    	}
	}

    /** 
     * <p>입력된 시간을 '99:99' 형태로 변환하여 반환한다.
     * 
     * @param yyyymmdd
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat("20080101")
     * </pre>
     */      
    public static String changeTimeFormat(String hhss){
    	String rtnTime=null;    

    	String hh = hhss.substring(0, 2);
    	String ss = hhss.substring(2,4);
    	rtnTime = hh+":"+ss;  
     
		return rtnTime;
    }
    
    /**
     * 입력받은 년월일시분초를 시:분:초 형식으로 변환
     * @param yyyyMMddhhmmss
     * @return hh:mm:ss 형식
     */
    public static String changeTimeFormat2(String yyyyMMddhhmmss){
    	String rtnTime=null;    

    	String hh = yyyyMMddhhmmss.substring(8,10);
    	String mm = yyyyMMddhhmmss.substring(10,12);
    	String ss = yyyyMMddhhmmss.substring(12,14);
    	rtnTime = hh+":"+mm+":"+ss;  
     
		return rtnTime;
    }

    /**
     * 입력받은 년월일시분초를 시:분 형식으로 변환
     * @param yyyyMMddhhmmss
     * @return hh:mm:ss 형식
     */
    public static String changeTimeFormat3(String yyyyMMddhhmmss){
    	String rtnTime=null;    

    	String hh = yyyyMMddhhmmss.substring(8,10);
    	String mm = yyyyMMddhhmmss.substring(10,12);
    	rtnTime = hh+":"+mm;  
     
		return rtnTime;
    }
    
    /** 
     * <p>입력된 일자를 '9999년 99월 99일' 형태로 변환하여 반환한다.
     * 
     * @param yyyymmdd
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat("20080101")
     * </pre>
     */      
    public static String changeDateFormat(String yyyymmdd){
    	String rtnDate=null;    

    	String yyyy = yyyymmdd.substring(0, 4);
    	String mm   = yyyymmdd.substring(4,6);
		String dd   = yyyymmdd.substring(6,8);
		rtnDate=yyyy+" 년 "+mm + " 월 "+dd + " 일";  
     
		return rtnDate;
    }
    
    /** 
     * <p>입력된 일자를 '9999-99-99일' 형태로 변환하여 반환한다.
     * 
     * @param yyyymmdd
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat("20080101")
     * </pre>
     */      
    public static String changeDateFormat2(String yyyymmdd){
    	String rtnDate=null;    
    	if(yyyymmdd == null || yyyymmdd.length() < 8) return "";
    	String yyyy = yyyymmdd.substring(0, 4);
    	String mm   = yyyymmdd.substring(4,6);
		String dd   = yyyymmdd.substring(6,8);
		rtnDate=yyyy+"-"+mm + "-"+dd;  
     
		return rtnDate;
    }
    
    /** 
     * <p>입력된 일자를 '9999-99-99일' 형태로 변환하여 반환한다.
     * 
     * @param yyyymmdd
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat("20080101")
     * </pre>
     */      
    public static String changeDateFormat4(String yyyyMMddHHmmss){
    	String rtnDate=null;    
    	if(yyyyMMddHHmmss == null || yyyyMMddHHmmss.length() < 14) return "";
    	String yyyy = yyyyMMddHHmmss.substring(0, 4);
    	String MM   = yyyyMMddHHmmss.substring(4,6);
		String dd   = yyyyMMddHHmmss.substring(6,8);
		String HH   = yyyyMMddHHmmss.substring(8,10);
		String mm   = yyyyMMddHHmmss.substring(10,12);
		String ss   = yyyyMMddHHmmss.substring(12,14);
		rtnDate=yyyy+"년 "+MM+ "월 "+dd+"일 "+HH+"시 "+mm+"분 "+ss+"초";  
     
		return rtnDate;
    }
    
    /** 
     * <p>입력된 일자를 '9999-99-99일' 형태로 변환하여 반환한다.
     * 
     * @param yyyymmddhhmiss
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateTimeFormat2("20080101010101")
     * </pre>
     */    
    public static String changeDateTimeFormat2(String yyyymmddhhmiss){
    	String rtnDate=null;    
    	if(yyyymmddhhmiss == null || yyyymmddhhmiss.length() < 8) return "";
    	String yyyy = yyyymmddhhmiss.substring(0, 4);
    	String mm   = yyyymmddhhmiss.substring(4,6);
		String dd   = yyyymmddhhmiss.substring(6,8);
		rtnDate=yyyy+"."+mm + "." +dd;  
     
		return rtnDate;
    }
    

    /** 
     * <p>입력된 일자를 '9999-99-99일' 형태로 변환하여 반환한다.
     * 
     * @param yyyymmddhhmiss
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateTimeFormat2("20080101010101")
     * </pre>
     */    
    public static String changeDateTimeFormat4(String yyyymmddhhmi){
    	String rtnDate=null; 
    	try{
	    	String yyyy = yyyymmddhhmi.substring(0, 4);
	    	String mm   = yyyymmddhhmi.substring(4,6);
			String dd   = yyyymmddhhmi.substring(6,8);
			String hh   = yyyymmddhhmi.substring(8,10);
			String mi   = yyyymmddhhmi.substring(10,12);
			rtnDate=yyyy+"."+mm + "." +dd + " " + hh + ":" + mi;  
    	}catch(Exception e){
    		rtnDate = yyyymmddhhmi;
    	}
		return rtnDate;
    }
    
    public static String changeDateTimeFormat3(String yyyymmddhhmiss){
    	String rtnDate=null;    
    	if(yyyymmddhhmiss == null || yyyymmddhhmiss.length() < 14) return "";
    	String yyyy = yyyymmddhhmiss.substring(0, 4);
    	String mm   = yyyymmddhhmiss.substring(4,6);
		String dd   = yyyymmddhhmiss.substring(6,8);
		String hh   = yyyymmddhhmiss.substring(8,10);
		String mi   = yyyymmddhhmiss.substring(10,12);
		String ss   = yyyymmddhhmiss.substring(12,14);
		rtnDate=yyyy+"."+mm + "." +dd + " " + hh + ":" + mi + ":" + ss;  
     
		return rtnDate;
    }
    /** 
     * <p>입력된 일자를 '9999-99-99일' 형태로 변환하여 반환한다.
     * 
     * @param yyyymmdd
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat("20080101")
     * </pre>
     */      
    public static String changeDateFormat3(String yyyymmdd){
    	String rtnDate=null;    
    	try{
	    	String yyyy = yyyymmdd.substring(0, 4);
	    	String mm   = yyyymmdd.substring(4,6);
			String dd   = yyyymmdd.substring(6,8);
			rtnDate=yyyy+"."+mm + "."+dd;  
    	}catch(Exception e){
    		rtnDate = yyyymmdd;
		}
		return rtnDate;
    }
    /** 
     * <p>입력된 일자를 '9999.99.99(요일)' 형태로 변환하여 반환한다.
     * 
     * @param yyyy-MM-dd-hh:mm
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat5("2008-01-01 00:00")
     * </pre>
     */      
    public static String changeDateFormat5(String yyyyMMddhhmm){
    	String rtnDate="";    
        if(yyyyMMddhhmm!=null&&!yyyyMMddhhmm.equals("")){
    	String yyyy = yyyyMMddhhmm.substring(0, 4);
    	String mm   = yyyyMMddhhmm.substring(4,6);
		String dd   = yyyyMMddhhmm.substring(6,8);
		int day = getDayOfWeek(yyyy+mm+dd);
		String sday = "";
		switch(day){
		case 1: sday = "(일)"; break;
		case 2: sday = "(월)"; break; 
		case 3: sday = "(화)"; break; 
		case 4: sday = "(수)"; break; 
		case 5: sday = "(목)"; break; 
		case 6: sday = "(금)"; break;
		case 7: sday = "(토)"; break;
		default: sday = "";
		}
	    rtnDate=yyyy +"."+ Integer.parseInt(mm) +"."+ Integer.parseInt(dd) + sday;  
        }
		return rtnDate;
    }
    /** 
     * <p>입력된 시간를 '12:12일' 형태로 변환하여 반환한다.
     * 
     * @param hhmi
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat("1122")
     * </pre>
     */      
    public static String changeTimeFormat1(String hhmi){
    	String rtnDate=null;    
    	try{
    	String hh = hhmi.substring(0, 2);
    	String mi   = hhmi.substring(2,4);
		rtnDate=hh + "."+mi;  
    	}catch(Exception e){
    		rtnDate = hhmi; 
    	}
		return rtnDate;
    }
    /** 
     * <p>입력된 일자를 '99999999일' 형태로 변환하여 반환한다.
     * 
     * @param yyyy-mm-dd
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat2Inverse("20080101")
     * </pre>
     */    
    public static String changeDateFormat2Inverse(String yyyymmdd){
    	String rtnDate=null;    

    	String yyyy = yyyymmdd.substring(0, 4);
    	String mm   = yyyymmdd.substring(5,7);
		String dd   = yyyymmdd.substring(8,10);
		rtnDate=yyyy + mm + dd;  
     
		return rtnDate;
    }
    

    /** 
     * <p>두 날짜간의 날짜수를 반환(윤년을 감안함)
     * 
     * @param startDate 시작 날짜
     * @param endDate 끝 날짜
     * @return 날수
     * @see java.util.GregorianCalendar 
     * <p><pre>
     *  - 사용 예
     * long date = DateUtil.getDifferDays("20080101","20080202")
     * </pre>
     */ 
    public static long getDifferDays(String startDate, String endDate) {
    	GregorianCalendar StartDate = getGregorianCalendar(startDate);
    	GregorianCalendar EndDate = getGregorianCalendar(endDate);
    	long difer = (EndDate.getTime().getTime() - StartDate.getTime().getTime()) / 86400000;
    	return difer;
	}

    /** 
     * <p>현재의 요일을 구한다.
     * 
     * @param
     * @return 요일
     * @see java.util.Calendar 
     * <p><pre>
     *  - 사용 예
     * int day = DateUtil.getDayOfWeek()
     *  SUNDAY    = 1
     *  MONDAY    = 2
     *  TUESDAY   = 3
     *  WEDNESDAY = 4
     *  THURSDAY  = 5
     *  FRIDAY    = 6
     * </pre>
     */
	public static int getDayOfWeek(){
		Calendar rightNow = Calendar.getInstance();
		int day_of_week = rightNow.get(Calendar.DAY_OF_WEEK);
		return day_of_week;
	}
	
	public static int getDayOfWeek(String yyyymmdd){
		Calendar rightNow = DateUtil.getGregorianCalendar(yyyymmdd);
		int day_of_week = rightNow.get(Calendar.DAY_OF_WEEK);
		return day_of_week;
	}

    /** 
     * <p>현재주가 올해 전체의 몇째주에 해당되는지 계산한다. 
     * 
     * @param
     * @return 요일
     * @see java.util.Calendar 
     * <p><pre>
     *  - 사용 예
     * int day = DateUtil.getWeekOfYear()
     * </pre>
     */    
	public static int getWeekOfYear(){
		Locale LOCALE_COUNTRY = Locale.KOREA;
		Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
		int week_of_year = rightNow.get(Calendar.WEEK_OF_YEAR);
		return week_of_year;
	}
    
    /** 
     * <p>현재주가 현재월에 몇째주에 해당되는지 계산한다. 
     * 
     * @param
     * @return 요일
     * @see java.util.Calendar 
     * <p><pre>
     *  - 사용 예
     * int day = DateUtil.getWeekOfMonth()
     * </pre>
     */     
	public static int getWeekOfMonth(){
		Locale LOCALE_COUNTRY = Locale.KOREA;
		Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
		int week_of_month = rightNow.get(Calendar.WEEK_OF_MONTH);
		return week_of_month;
	}
    
    /** 
     * <p>해당 p_date날짜에 Calendar 객체를 반환함.
     * 
     * @param p_date
     * @return Calendar
     * @see java.util.Calendar 
     * <p><pre>
     *  - 사용 예
     * Calendar cal = DateUtil.getCalendarInstance(DateUtil.getCurrentYyyymmdd())
     * </pre>
     */ 
	public static Calendar getCalendarInstance(String p_date){
		//Locale LOCALE_COUNTRY = Locale.KOREA;
		Locale LOCALE_COUNTRY = Locale.FRANCE;
		Calendar retCal = Calendar.getInstance(LOCALE_COUNTRY);

		if(p_date != null && p_date.length() == 8){
			int year  = Integer.parseInt(p_date.substring(0,4));
			int month = Integer.parseInt(p_date.substring(4,6))-1;
			int date  = Integer.parseInt(p_date.substring(6));

			retCal.set(year, month, date);
		}
		return retCal;
	}
	
	public static String date2String(Date date){
		return date2String(date, "yyyy.MM.dd");
	}
	
	public static String date3String(Date date){
		return date2String(date, "yyyyMMddHHmmss");
	}
	 
	public static String date2String(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
		String convertDateString = new String("");
		if(date != null)
			convertDateString = sdf.format(date);
		
		return convertDateString;
	}
	
	 /** 
     * YYYY-MM-DD HH24:MI:SS 형태로 각 항목의 데이터를 가져옴. (임시..)
     */ 
	public static String getYearDateFormat(String date, String preFix){
		if(date == null || date.length() < 19) return "";
		
		if(preFix.equalsIgnoreCase("YYYY")){
			return date.substring(0, 4);
		}else if(preFix.equalsIgnoreCase("MM")){
			return date.substring(5, 7);
		}else if(preFix.equalsIgnoreCase("DD")){
			return date.substring(8, 10);
		}else if(preFix.equalsIgnoreCase("HH")){
			return date.substring(11, 13);
		}else if(preFix.equalsIgnoreCase("MI")){
			return date.substring(14, 16);
		}else if(preFix.equalsIgnoreCase("SS")){
			return date.substring(17, 19);
		}else if(preFix.equalsIgnoreCase("YYYY-MM-DD")){
			return date.substring(0, 10);
		}
		return "";
		
	}
    
	//yyyyMMddHHmmss
	public static Date changeStringToDate(String value, String format){
		try{
			SimpleDateFormat formatter = new SimpleDateFormat (format);
			return formatter.parse(value);
		}catch(ParseException e){
			return null;
		}
	}
	
	/*
	public static void main(String[] arge){
		DateUtil util = new DateUtil();
		String x = DateUtil.getYearDateFormat("1111-22-33 44:55:66","MI");
		System.out.println(x);
	}
	*/
	
}

package com.KoreaIT.java.AM;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static String getNowDateStr() {
	//현재 날짜 시
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년 MM월 dd일");

        Date now = new Date();

        String nowTime1 = sdf1.format(now);
        String nowTime2 = sdf2.format(now);
        
        return sdf1.format(now);

	}

}

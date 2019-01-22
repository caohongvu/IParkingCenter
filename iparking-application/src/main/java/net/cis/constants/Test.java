package net.cis.constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.cis.common.util.DateTimeUtil;

public class Test {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(simple.parse("2019-01-21 00:00:00").before(DateTimeUtil.getCurrentDateTime()));

	}

}

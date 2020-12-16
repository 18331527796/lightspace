package com.threefriend.lightspace.xcx.util;

public class NumToChinese {
	
	public static String toChinese(String str) {
	    String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	    String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
	    String result = "";
	    int n = str.length();
	    for (int i = 0; i < n; i++) {
	        int num = str.charAt(i) - '0';
	        if (i != n - 1 && num != 0) {
	            result += s1[num] + s2[n - 2 - i];
	        } else {
	            result += s1[num];
	        }
	    }
	    return result;
	}
	public static String toChinese2(String str) {
		String[] s2 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
		StringBuffer sb=new StringBuffer();
		for (char c : str.toCharArray()) {
			sb.append(s2[Integer.parseInt(String.valueOf(c))]);
		}
	       return sb.toString();
	}
}

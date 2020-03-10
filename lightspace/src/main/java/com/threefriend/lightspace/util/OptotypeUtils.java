package com.threefriend.lightspace.util;

public class OptotypeUtils {

	public static String vision2vision5(Double vision) {
		if(vision==0.1)return "4.0/0.1";
		if(vision==0.12)return "4.1/0.12";
		if(vision==0.15)return "4.2/0.15";
		if(vision==0.2)return "4.3/0.2";
		if(vision==0.25)return "4.4/0.25";
		if(vision==0.3)return "4.5/0.3";
		if(vision==0.4)return "4.6/0.4";
		if(vision==0.5)return "4.7/0.5";
		if(vision==0.6)return "4.8/0.6";
		if(vision==0.8)return "4.9/0.8";
		if(vision==1.0)return "5.0/1.0";
		if(vision==1.2)return "5.1/1.2";
		if(vision==1.5)return "5.2/1.5";
		if(vision==2.0)return "5.3/2.0";
		return null;
	}
	
	public static String vision2onlyvision5(Double vision) {
		if(vision==0.1)return "4.0";
		if(vision==0.12)return "4.1";
		if(vision==0.15)return "4.2";
		if(vision==0.2)return "4.3";
		if(vision==0.25)return "4.4";
		if(vision==0.3)return "4.5";
		if(vision==0.4)return "4.6";
		if(vision==0.5)return "4.7";
		if(vision==0.6)return "4.8";
		if(vision==0.8)return "4.9";
		if(vision==1.0)return "5.0";
		if(vision==1.2)return "5.1";
		if(vision==1.5)return "5.2";
		if(vision==2.0)return "5.3";
		return null;
	}

	public static Double vision52vision(Double vision5) {
		if(vision5==4.0)return 0.1;
		if(vision5==4.1)return 0.12;
		if(vision5==4.2)return 0.15;
		if(vision5==4.3)return 0.2;
		if(vision5==4.4)return 0.25;
		if(vision5==4.5)return 0.3;
		if(vision5==4.6)return 0.4;
		if(vision5==4.7)return 0.5;
		if(vision5==4.8)return 0.6;
		if(vision5==4.9)return 0.8;
		if(vision5==5.0)return 1.0;
		if(vision5==5.1)return 1.2 ;
		if(vision5==5.2)return 1.5;
		if(vision5==5.3)return 2.0;
		return null;
	}
}

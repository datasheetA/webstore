package com.risen.common.utils;

import java.util.UUID;

public class UUIDGenerator {
	public static String[] chars=new String[]{
		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", 
		"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", 
		"u", "v", "w", "x", "y", "z",
	    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
	    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
	    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", 
	    "U", "V","W", "X", "Y", "Z"	
	};
	
	/*
	 * 获得8位UUID
	 */
	public static String getShort(){
		StringBuilder sb=new StringBuilder();
		String uuid=UUID.randomUUID().toString().replace("-", "");
		for(int i=0;i<8;i++){
			String str=uuid.substring(i*4,i*4+4);
			int n=Integer.parseInt(str,16);
			sb.append(chars[n % 0x3e]);
		}
		return sb.toString();
	}
	/*
	 * 获得32位UUID
	 */
	public static String getLong(){
		String uuid=UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	public static void main(String[] args) {
		System.out.println(getShort());
	}
}

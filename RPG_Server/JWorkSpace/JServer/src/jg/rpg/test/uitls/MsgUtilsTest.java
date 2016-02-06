package jg.rpg.test.uitls;

import static org.junit.Assert.*;

import org.junit.Test;

import jg.rpg.utils.MsgUtils;

public class MsgUtilsTest {

	@Test
	public void testByte() {
		byte[] buff = new byte[1024];
		byte b = (byte)130;
		System.out.println(buff[2]);
	}

	@Test
	public void testMsgLenEncoding() {
		int num = 1234567;
		byte[] buff = MsgUtils.intToBytes(num, 4);
		for(int i =0 ; i<buff.length ; i++){
			System.out.println(buff[i]);
		}
	}
	@Test
	public void testType(){
		int a = 4;
		//boolean b = a instanceof Integer;
		this.add(a);
	}
	
	public <T> void add(T t){
		boolean b = t instanceof Integer;
		System.out.print(t);
	}
}
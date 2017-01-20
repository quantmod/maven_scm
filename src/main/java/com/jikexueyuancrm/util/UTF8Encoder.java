package com.jikexueyuancrm.util;
/**
 * 
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author levi
 *
 */
public class UTF8Encoder {

	public static void main(String[] args) throws IOException {
	    String str = "Hello, 我们是中国人。";
	    byte[] utf8Bytes = toUTF8Bytes(str);
	    FileOutputStream fos = new FileOutputStream("f.txt");
	    fos.write(utf8Bytes);
	    fos.close();
	}
	
	public static byte[] toUTF8Bytes(String str) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0;i < str.length(); i++) {
			int chari = (int)str.charAt(i);
			if(chari <= 127){
				result.add((127 ^ leftShiftN(1)) & chari);
			}else{
				//count n first
				int n = 2;
				if(chari < 0x7ff){//2047
					n = 2;
				}else if(chari < 0xffff){//65535
					n = 3;
				}else if(chari < 0x10ffff){//1114111
					n = 4;
				}
				int lastByte = 0;
				List<Integer> rr = new ArrayList<Integer>();
				for(int j = 0; j < n; j++){
					int low = chari & 255;
					chari = chari >> 8;
					int x = 0;
					if(j == (n - 1)){
						int leftShiftN = leftShiftN(n);
						x = leftShiftN | lastByte;
					}else{
						x = (2 << 6) | (((low << (2 * j)) & (leftShiftN(2) ^ 255)) | lastByte);
						lastByte = (low & leftShiftN(2 * (j + 1))) >> 8 - 2 * (j + 1);
					}
					rr.add(x);
				}
				Collections.reverse(rr);
				result.addAll(rr);
			}
		}
		byte [] finalResult = new byte[result.size()];
		for (int i = 0;i < result.size();i++) {
			finalResult[i] = (byte)result.get(i).intValue();
		}
	    return finalResult; 
	}
	
	private static int leftShiftN(int n){
		int orginal = 0;
		for(int i = 0; i < 7; i++){
			if(i < n){
				orginal |= 1;
			}
			orginal = orginal << 1;
		}
		return orginal;
	}
	
}
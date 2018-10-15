package kz.edu.nu.cs;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyInputStream extends FilterInputStream {
	
	InputStream in = null;

	protected MyInputStream(InputStream in) {
		super(in);
		this.in = in;
	}
	
	
	public int read(byte[] bytes) {
		
		byte[] mydata = new byte[100];
		int bytesRead = 0;
    	
    	try {
    		bytesRead = in.read(mydata);
			String str = new String(mydata);
			int length = str.length();
			char[] letters = new char[length];
			char ch;
									
			for(int i=0; i<length; i++) {
			
			    ch = str.charAt(i);

				if(Character.isUpperCase(ch)) {
					letters[i] = Character.toLowerCase(ch);
				} else {
					letters[i] = Character.toUpperCase(ch);
				}
			}
			
			String s = String.valueOf(letters);
			//System.out.println(bytes);
			//System.out.println(letters);
			
			in = new ByteArrayInputStream(s.getBytes());   //convert String to bytes
			in.read(bytes);   //send bytes to inputstream
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytesRead;
	}


}

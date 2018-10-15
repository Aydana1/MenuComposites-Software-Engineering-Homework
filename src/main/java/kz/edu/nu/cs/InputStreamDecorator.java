package kz.edu.nu.cs;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class InputStreamDecorator 
{
    public static void main( String[] args )
    {
    	File file = new File("myfile.txt");
    	byte[] bytes = new byte[100];
    	try {
    		
    		DataInputStream ds = new DataInputStream(new MyInputStream(new FileInputStream(file)));
    		InputStream is = new MyInputStream(new FileInputStream(file));
    		
    		is.read(bytes); //read bytes 
    		String str1 = new String(bytes);  //convert bytes into String
    		System.out.println(str1);
    		
//    		ds.read(bytes);
//    		String str2 = new String(bytes);
//    		System.out.println(str2);
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}


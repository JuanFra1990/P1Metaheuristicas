/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1metaheuristicas;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author juanf
 */
public class LogText {
    private static FileWriter fw;
    private static String nameTFile = "";
	
    public static FileWriter getFW() {
        return fw;
    }
	

    /*
     * @description: this function have a function of create the log document 
     * @param: String (nameFile) Is a part of document name 
    */
	public static void init(String nameFile) throws IOException {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		nameTFile = nameFile + "_" + hourdateFormat.format(date) + ".log";
                fw = new FileWriter(nameTFile ); // TODO Auto-generated catch block
		
	}
	
	/*
	 * @description: this function write and edit of document
	 * @param: String (text) Text to write in the document
	 */
	 public static void LogWriter(String text) throws IOException{
	        fw.write(text);
	        fw.flush();
	    }
	 
	 /*
	  * @description: Reader of document(NO TEST)
	  */
	 public static void LogReader() throws IOException{
		 FileReader fr = new FileReader(nameTFile);
		int valor=fr.read();
	    while(valor!=-1){
	    	System.out.print((char)valor);
	        valor=fr.read();
	    }
	 }
	 
	 /*
	  * @description: this function close the document
	  */
	 public static void FileClose() throws IOException {
             fw.close(); // TODO Auto-generated catch block
	 }
}

package bugelli;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JFileChooser;
/**
 * The ReadFile class is used to read the file chosen and perform the needed counts. 
 * @since 10/11/2020
 * @author Lauren Bugelli
 * @version 1
 */
public class ReadFile {
	JFileChooser fileChooser = new JFileChooser();
	//MADE VARIABLES OPEN SO THE MAIN COULD CALL AND SHOW THE OUTPUT TO USER IF DESIRED
	public int count;
	public int wordCount;
	public int singleLineC;
	public int multiLineC;
	public int emptyLineC;
	//STOPWATCH CREATION
	public Stopwatch timer = new Stopwatch();
	//CREATE HASHMAP TO POPULATE
	public HashMap<String, Integer> hp = new HashMap<String, Integer>();
	
	/**
	 * pickMe method handles if a file selected is the correct type
	 * @throws Exception
	 */
	public void pickMe() throws Exception{
		//TEST IF A FILE IS SELECTED
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			//GET FILE
			java.io.File file = fileChooser.getSelectedFile();
			//SEND FILE TO COUNTLINES
			countLines(file);
	
			
		}else {
			System.out.print("No File Selected");
		}
	}
	/**
	 * This method performs line count and counts comments as well as control structures
	 * @param file File this is the file being read in and the lines of which are being counted
	 * @return count int this will return the total number of executable lines of code
	 * @throws IOException
	 */
	public int countLines(File file) throws IOException {
//RESERVE WORD STRING ARRAY
		String[] reservedWords = {"abstract","assert","boolean","break", "byte", "switch","case", "try", "catch", "finally",
				"char","class", "continue", "default","do", "double","if","else", "enum", "extends", "final", "float", 
				"for", "implements", "import", "instanceOf","int","interface","long", "native", "new","package","private",
				"protected","public","return", "short", "static", "strictfp", "super", "synchronized","throw", "throws", 
				"void","volatile", "while","const", "String"};
//FILE READERS FROM LAST ASSIGNMENT
	    InputStream br = new BufferedInputStream(new FileInputStream(file));
	    Scanner in = new Scanner(file);
//HASMAP COUNTER 
	    Scanner two = new Scanner(file);
	    try {
//START TIMER
	    	timer.start();
	    	
//READ AND COUNT EVERY LINE IN THE FILE AS FAST AND EFFICIENTLY AS POSSIBLE
	        byte[] c = new byte[1024];
	        Integer readChars = 0;
	        boolean endsWithoutNewLine = false; //Make sure every line is counted
	        while ((readChars = br.read(c)) != -1) {
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    count++;
	              
	            }
	            endsWithoutNewLine = (c[readChars - 1] != '\n');
	        }
	        if(endsWithoutNewLine) {
	            count++;
	        }
//SUBTRACT COMMENTED LINES FROM TOTAL COUNT
   //USING A SEPERATE SCANNER TO PEFORM THIS ENSURES ACCURACY 
	        while(in.hasNextLine()){
	            String line = in.nextLine();
   //REMOVES EMPTY SPACES BEFORE LINES IN THE FILE
	            line = line.trim();
   //SUBTRACT FROM TOTAL IF LINE IS EMPTY
	            if(line.isEmpty()){
	                count--;
	                emptyLineC++;
   //SUBTRACT FROM TOTAL IF LINE IS A SINGLE LINE COMMENT
	            }else if (line.startsWith("//")) {
	            	singleLineC++;
	            	count--;
   //SUBTRACT FROM TOTAL IF LINE IS A MULTI LINE COMMENT -works if file code is correctly formated and variable names always appear first
	            }else if (line.startsWith("/*") || line.startsWith("*") || line.startsWith("*/")) {
	            	multiLineC++;
	            	count--;
	            }	            
	        }
//POPULATE A HASHMAP AND COUNT KEY WORDS	        
	        while(two.hasNext()) {
	        	String check = two.next();//TEST ONE WORD AT A TIME
	        	check= check.trim(); //REMOVE EXTRA SPACE AROUND WORD
	        	for(int i = 0; i < reservedWords.length;i++) {
					String key = reservedWords[i];//POPULATE KEYS
					if(check.contains(key)) { //PERFORM COUNT
						if(hp.get(key) == null)
							hp.put(key, 1);
						else
							hp.put(key, hp.get(key)+1);
					}
				}
	        }
//PRINT HASHMAP TO CONSOLE FOR TESTING PURPOSES
	        for (String i :hp.keySet()) {
	        	 System.out.println("Key:\t" + i + "\tValue:\t" + hp.get(i) + "\n");
			}
//PRINT NUMBER OF EMPTY AND COMMENTED OUT LINES TO CONSOLE
	        System.out.println("Empty Lines: " + emptyLineC); //22
	        System.out.println("Single Comment Lines: " + singleLineC); //4
	        System.out.println("Multi Comment Lines: " + multiLineC); //42
	        
	        
	        return count;
	    } finally {
//CLOSE ALL FILE READERS
	        br.close();
	        in.close();
	        two.close();
//STOP TIMER BEFORE WRITING FILE
	        timer.stop();
	        writeFile();
	    }
	}
	/**
	 * This mathod is called to write a file forr the user
	 * @throws IOException
	 */
	public void writeFile()throws IOException {
		File file = new File("out.txt");		//create a File object
		FileWriter fw = new FileWriter(file);	//create a FileWriter
		PrintWriter pw = new PrintWriter(fw);	//Create a PrintWriter
		String value = String.valueOf(count);
		pw.println("This File Contains the LOC, number of keywords, and time it took to read your file");
		pw.println("The number of executable lines of code (LOC) is: " + value);
		//count total number of instances a reserved word is used
		  for (String i :hp.keySet()) {
			  wordCount += hp.get(i);
			}
		String keyCount = String.valueOf(wordCount);
		pw.println("The number of key words in your code (LOC) is: " + keyCount);
	
		pw.println("The time it took to read the file and perform the counts is: " + timer.getMilliseconds() +" milliseconds");
		
		//YOU HAVE TO CLOSE THE PRINT WRITER OR ELSE THE FILE WILL BE EMPTY
		pw.close();
	}
}

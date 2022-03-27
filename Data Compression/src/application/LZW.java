package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LZW {
	String input;
	FileInputStream in;
	FileOutputStream out;
	
	public LZW(FileInputStream in, FileOutputStream out) {
		this.in = in;
		this.out = out;
	}
	
	public List<Integer> compress(){
		int dictionarySize = 256;
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
		for(int i=0;i<dictionarySize;i++) {
			dictionary.put(String.valueOf((char)i), i);
		}
		String chars = "";
		List<Integer> returnList = new ArrayList<Integer>();
		for(char ch : input.toCharArray()) {
			String addChars = chars + ch;
			if(dictionary.containsKey(addChars)) {
				chars = addChars;
			}
			else {
				returnList.add(dictionary.get(chars));
				dictionary.put(addChars, dictionarySize++);
				chars = String.valueOf(ch);
			}
		}
		if(!chars.isEmpty()) {
			returnList.add(dictionary.get(chars));
		}
		return returnList;
		
	}

}

package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Huffman {
	
	private static final int MAX = 256;
	
	File in;
	int[] byteFreqs = new int[MAX]; 
	HashMap<Integer, String> key = new HashMap<Integer, String>();
	String[] hCodes = new String[MAX];
	int [] inputBytes;
	String encodedString;
	
	public Huffman(File in) {
		this.in = in;
	}
	
	public void inputToByteArray() throws IOException {
		FileInputStream ins = new FileInputStream(in);
		inputBytes = new int[(int)in.length()];
	    int index = 0;
	    while(ins.available()!=0) {
	    	int b = ins.read();
	    	inputBytes[index] = (b & 0xff) ; // converting signed bytes to unsigned using a bitwise operator
	    	index++;
	    }
	    ins.close();
	}
		
	public void operate() throws IOException {
		inputToByteArray();
		for(int i : inputBytes) {
			byteFreqs[i]++;
		}
		Node root = buildHTree();
		buildCodes(hCodes, root, "");
//		encodedString = encode();
//		byte[] outBytes = encodedString.getBytes();
		String outStr = encode();
		FileWriter outs = new FileWriter("output.txt");
		outs.write(outStr);
		outs.close();
		
//		to display compression rate
//		String inputInBits = getUncompressedInputInBinary();
//		String compressedInBits = getCompressedInput();
//		System.out.println(inputInBits);
//		System.out.println(compressedInBits);
//		float percent = getCompressionRate(inputInBits,compressedInBits);
//		System.out.println("Compression rate: " + percent + "%.");
		
//		out.write(bytes);
//		out.close();
		
//		to check huffman codes
//		for(int i=0;i<hCodes.length;i++) {
//			if(hCodes[i]!=null) System.out.println(i + ": " + hCodes[i]);
//		}
//		for(int i=0;i<500;i++) {
//			System.out.print((char)inputBytes[i]);
//		}
	}

	private Node buildHTree() {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		for(int c=0;c<MAX;c++) {
			if(byteFreqs[c]>0) pq.add(new Node(c, byteFreqs[c], null, null));
		}
		
		while (pq.size() > 1) {
            Node left  = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(0, left.frequency + right.frequency, left, right);
            pq.add(parent);
       }
        return pq.peek();
	}
	
	public void buildCodes(String[] hCodes, Node  n, String s) {
		if(n==null) return;
		buildCodes(hCodes, n.left, s + "0");
		buildCodes(hCodes, n.right, s + "1");
		hCodes[n.value] = s;
		key.put(n.value, s);
	}
	
	public String encode() {
		StringBuilder sb = new StringBuilder();
		String ch;
		for (int i = 0; i < inputBytes.length; i++) {
			ch = key.get(inputBytes[i]);
//			ch = (byte) (Integer.parseInt(key.get(inputBytes[i]),2) & 0xff)
			sb.append(ch);
		}
		return sb.toString();
	}
	
//	public String getUncompressedInputInBinary() {
//		String returnStr = "";
//		for(int i=0;i<hCodes.length();i++) {
//			returnStr += Integer.toBinaryString(input.charAt(i));
//		}
//		return returnStr;
//	}
	
//	public String getCompressedInput() {
//		String returnStr = "";
//		for(int i=0;i<input.length();i++) {
//			returnStr += hCodes[input.charAt(i)];
//		}
//		return returnStr;
//	}

	private float getCompressionRate(String inputInBits, String huffInBits) {
		float inputLength = inputInBits.length();
		float huffLength = huffInBits.length();
		return (inputLength-huffLength)/inputLength*100;
	}
}
    
	


//	String uncompressedInput = "aabbcd";
//	String compressedInput;
//	String binary = new BigInteger(s.getBytes()).toString(2);
//	char[] inputInChars = uncompressedInput.toCharArray();
//	int[] freqs = new int[MAX];
//	String[] hCodes = new String[MAX];
//	HashMap<Character, String> key = new HashMap<Character, String>();

/*
	public void buildHCode() {	
		calculating frequencies of the characters
		for(int i=0;i<inputInChars.length;i++) {
			freqs[inputInChars[i]]++;
		}
		Node root = buildTree(freqs);
		buildHuffmanTree(hCodes, root, "");
		
		to display compression rate
		String inputInBits = getUncompressedInputInBinary();
		String compressedInBits = getCompressedInput();
		System.out.println(inputInBits);
		System.out.println(compressedInBits);
		float percent = getCompressionRate(inputInBits,compressedInBits);
		System.out.println("Compression rate: " + percent + "%.");
		
		
		to check huffman codes
		for(int i=0;i<hCodes.length;i++) {
			if(hCodes[i]!=null) System.out.println((char)i + ": " + hCodes[i]);
		}
	}
	

	to print the tree
	private void printi(Node x) {
        if (x.isLeaf()) {
        	System.out.println(x.character);
            return;
        }
        System.out.println(x.character);
        printi(x.left);
        printi(x.right);
    }

	private Node buildTree(int[] f) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		for(int c=0;c<MAX;c++) {
			if(freqs[c]>0) pq.add(new Node((char)c,freqs[c],null, null));
		}
		
		to check if queue is correct
		int ss = pq.size();
		for(int i=0;i<ss;i++) {
			System.out.println(pq.poll().character);
		}
		
		while (pq.size() > 1) {
            Node left  = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.frequency + right.frequency, left, right);
            pq.add(parent);
       }
        return pq.poll();		
	}
	
	public void buildHuffmanTree(String[] hCodes, Node n, String s) {
		if(n==null) return;
		buildHuffmanTree(hCodes, n.left, s + "0");
		buildHuffmanTree(hCodes, n.right, s + "1");
		hCodes[n.character] = s;
		key.put(n.character, s);
	}
	
	public String getUncompressedInputInBinary() {
		String returnStr = "";
		for(char ch : inputInChars) {
			returnStr += Integer.toBinaryString(ch);
		}
		return returnStr;
	}
	
	public String getCompressedInput() {
		String returnStr = "";
		for(char ch : inputInChars) {
			returnStr += key.get(ch);
		}
		return returnStr;	
	}

	private float getCompressionRate(String inputInBits, String huffInBits) {
		float inputLength = inputInBits.length();
		float huffLength = huffInBits.length();
		return (inputLength-huffLength)/inputLength*100;
	}
 */
	
	



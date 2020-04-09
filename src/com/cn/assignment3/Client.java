package com.cn.assignment3;
//A Java program for a Client 
import java.net.*;
import java.util.Scanner;
import java.io.*; 

public class Client 
{ 
	// initialize socket and input output streams 
	private Socket socket		 = null; 
	private DataInputStream input = null; 
	private DataOutputStream out	 = null; 

	// constructor to put ip address and port 
	public Client(String address, int port) 
	{ 
		// establish a connection 
		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 

			

			// sends output to the socket 
			out = new DataOutputStream(socket.getOutputStream()); 
		

		// string to read message from input 
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputText = reader.readLine();
		System.out.println(inputText);
		out.writeUTF(inputText); 
		 
		BufferedReader br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
		System.out.println("Output from server: "+br.readLine());
	
		 BufferedReader kb 
         = new BufferedReader( 
             new InputStreamReader(System.in)); 
     String str, str1; 

     // repeat as long as exit 
     // is not typed at client 
     while (!(str = kb.readLine()).equals("Over")) { 
         // receive from the server 
         str1 = br.readLine(); 

         System.out.println("Output from: "+str1); 
     } 

		// close the connection 
		
			input.close(); 
			out.close(); 
			socket.close(); 
		} 
		catch(Exception i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		Client client = new Client("127.0.0.1", 5000); 
	} 
} 

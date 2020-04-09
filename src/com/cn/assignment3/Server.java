package com.cn.assignment3;

//A Java program for a Server 
import java.net.*; 
import java.io.*; 

public class Server  implements Runnable
{ 
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in	 = null; 
	private DataOutputStream out = null;

	// constructor with port 
//	public Server(int port) 
//	{ 
//		// starts server and waits for a connection 
//		try
//		{ 
//			server = new ServerSocket(port); 
//			System.out.println("Server started"); 
//
//			System.out.println("Waiting for a client ..."); 
//
//			socket = server.accept(); 
//			System.out.println("Client accepted"); 
//
//			// takes input from the client socket 
//			in = new DataInputStream( 
//				new BufferedInputStream(socket.getInputStream())); 
//
//			String line = ""; 
//
//			// reads message from client until "Over" is sent 
//			while (!line.equals("Over")) 
//			{ 
//				try
//				{ 
//					line = in.readUTF(); 
//					System.out.println(line); 
//
//				} 
//				catch(IOException i) 
//				{ 
//					System.out.println(i); 
//				} 
//			} 
//			System.out.println("Closing connection"); 
//
//			// close connection 
//			socket.close(); 
//			in.close(); 
//		} 
//		catch(IOException i) 
//		{ 
//			System.out.println(i); 
//		} 
//	} 

	
	public Server(Socket socket)
	{
		this.socket = socket;
	}
	public static void main(String args[]) 
	{ 
		ServerSocket server;
		try {
			server = new ServerSocket(5000);
		
			Socket connectionSocket = null;
	        while ((connectionSocket = server.accept()) != null) {
	          
	            Thread thread = new Thread(new Server(connectionSocket));
	            System.out.println("Starting Thread...");
	            thread.start();
	        }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
   
	}

	@Override
	public void run() {
		System.out.println("in run");
		BufferedReader inBufferedReader = null;
		String line = "";
		String inputString = "";
		String outputString ="";
		try {
			in = new DataInputStream( new BufferedInputStream(socket.getInputStream())); 
	
				// reads message from client until "Over" is sent 
				while (!line.equals("Over")) 
				{ 
						line = in.readUTF();
						inputString = line;
						System.out.println(inputString); 
			            String[] inputFields = inputString.split(" "); // Splits the user input string as string arrays.
			            String url = "http://"+inputFields[1];//+"/"+inputFields[4];
			            System.out.println(url);
			            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
			            
			            // GET method
			            if(inputFields[3].equals("GET")){
			            	System.out.println("in get");
			                httpClient.setRequestMethod(inputFields[3]);
			                httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
			                int responseCode = httpClient.getResponseCode();
			                System.out.println(responseCode);
			                BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
			                StringBuilder response = new StringBuilder();
			                String line1;
			                while ((line1 = in.readLine()) != null) {
			                    response.append(line1);
			                }
			                outputString = response.toString();
			                System.out.println("Output from HTTP request is: "+outputString);
			                // to send data to the client 
			                PrintStream ps = new PrintStream(socket.getOutputStream()); 
			                ps.println(outputString);
			            
			            }
				} 
				
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	} 
} 

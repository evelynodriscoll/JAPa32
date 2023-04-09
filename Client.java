package picross;



import java.net.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.*;
import java.awt.*;

/**
 * Class Client
 *
 */
public class Client {
	
	/**
	 * Default port.
	 */
	static int PORT = 3000;
	
	/**
	 * Number of port.
	 */
	static int portNumber = 0;
	
	/**
	 * Default hostname.
	 */
	static String HOSTNAME = "localhost";
	
	/**
	 * Variable for hostname.
	 */
	static String hostName = "";
	
	/**
	 * Default username
	 */
	static String USER = "user";
	
	/**
	 * JFrame for the client GUI
	 */
	private static JFrame f;
	
	/**
	 * Text field for the username.
	 */
	private static JTextField userField;
	
	/**
	 * Text field for the server name
	 */
	private static JTextField serverField;
	
	/**
	 * Text field for the custom port number.
	 */
	private static JTextField portField;
	
	/**
	 * Connect button
	 */
	private static JButton connectButton;
	
	/**
	 * Button to disconnect from server.
	 */
	private static JButton endButton;
	
	/**
	 * Button that generates new random game and holds it in memory.
	 */
	private static JButton newGameButton;
	
	/**
	 * Button that sends current game in memory to the server.
	 */
	private static JButton sendGameButton;
	
	/**
	 * Button that receives game from the server.
	 */
	private static JButton receiveGameButton;
	
	/**
	 * Button that sends user name and # of points to the server.
	 */
	private static JButton sendDataButton;
	
	/**
	 * Button to begin game session.
	 */
	private static JButton playButton;
	
	/**
	 * TextArea that displays message log.
	 */
	private static JTextArea messageArea;
	
	/**
	 * Scroll pane to contain messageArea.
	 */
	private static JScrollPane scrollPane;
	
	/**
	 * List of messages in the log.
	 */
	private static ArrayList<String> messageList;
	
	/**
	 * GameView instance.
	 */
	private static GameView gameView = new GameView();
	
	/**
	 * Custom username variable.
	 */
	private static String userName;
	
	/**
	 * Socket connection.
	 */
	private static Socket sock;
	
	/**
	 * User's current score.
	 */
	private static int points;
	
	/**
	 * Boolean for whether or not a game is stored in memory.
	 */
	private static boolean gameMem = false;
	
	/**
	 * Boolean array that helps create the game board.
	 */
	private static boolean[][] boolArray;
	
	/**
	 * Boolean for whether or not server is connected to client.
	 */
	private static boolean connected = false;
	


	/**
	 * Default constructor.
	 */
	public Client() {
		
		
	}
	
	/**
	 * Main method.
	 * @param args Param arguments.
	 */
	public static void main(String args[]) {
		
		//show the splash screen and create the client gui 
		gameView.showSplashScreen();
        f = new JFrame();
        f.setTitle("PICROSS Client");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.PINK);
        JLabel titleLabel = new JLabel("PICROSS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        f.add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        JPanel dataPanel = new JPanel(new FlowLayout());
        userField = new JTextField(10);
        dataPanel.add(new JLabel("User:"));
        dataPanel.add(userField);
        serverField = new JTextField(10);
        dataPanel.add(new JLabel("Server:"));
        dataPanel.add(serverField);
        portField = new JTextField(10);
        dataPanel.add(new JLabel("Port:"));
        dataPanel.add(portField);
        connectButton = new JButton("Connect");
        dataPanel.add(connectButton);
        endButton = new JButton("End");
        dataPanel.add(endButton);;
        inputPanel.add(dataPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        newGameButton = new JButton("New Game");
        buttonPanel.add(newGameButton);
        sendGameButton = new JButton("Send Game");
        buttonPanel.add(sendGameButton);
        receiveGameButton = new JButton("Receive Game");
        buttonPanel.add(receiveGameButton);
        sendDataButton = new JButton("Send Data");
        buttonPanel.add(sendDataButton);
        playButton = new JButton("Play");
        buttonPanel.add(playButton);
        
        inputPanel.add(buttonPanel);
        f.add(inputPanel, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messagePanel.setPreferredSize(new Dimension(500, 100));
        scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messagePanel.add(scrollPane, BorderLayout.CENTER);
        f.add(messagePanel, BorderLayout.SOUTH);
        messageList = new ArrayList<>();


        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(connected) {
                	addMessage("End button");
                try {
                	PrintStream output = new PrintStream(sock.getOutputStream());
                	output.println("end");
                	output.close();
					sock.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                }
                else {
                	addMessage("You are not connected to the network. Please connect.");
                }
                
                
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                if(connected) {
                addMessage("New Game button");
                gameMem = true;
                boolArray = gameView.clientGame();
               
                
                try {
                	PrintStream output = new PrintStream(sock.getOutputStream());
                	output.println("New random game generated");
                	
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                }
                else {
                	addMessage("You are not connected to the network. Please connect.");
                }
                
            }
        });

        sendGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(connected) {
                	addMessage("Send Game button");
                try {
                	PrintStream output = new PrintStream(sock.getOutputStream());
                	String sendGame = sendGame();
                	output.println(sendGame);
                	
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                }
                
                else {
                	addMessage("You are not connected to the network. Please connect.");
                }
            
            }
        });

        receiveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(connected) {
            	gameMem = true;
            	try {
            		PrintStream output = new PrintStream(sock.getOutputStream());
            		output.println("receive");
            	
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	}
            	
                else {
                	addMessage("You are not connected to the network. Please connect.");
                }	
            	
                
            }
        });
        
        sendDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(connected) {
                	addMessage("Send data button");
                try {
                	PrintStream output = new PrintStream(sock.getOutputStream());
                	output.println("User: " + userName + ", points: " + points);
                	
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            else {
            	addMessage("You are not connected to the network. Please connect.");
            }
            }
        });
        
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(connected) {
            	gameView.createWindow(gameMem);
                addMessage("Game started");
                try {
                	PrintStream output = new PrintStream(sock.getOutputStream());
                	output.println("New game started");
                	
                	
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                gameMem = true;
               
            }
             else {
            	addMessage("You are not connected to the network. Please connect.");
            }
            }
        });


        f.setSize(700, 300);
        f.setVisible(true);
		
		
		if (args == null) {
			// System.err.println("Usage: java EchoClient <host name> <port number>");
			hostName = HOSTNAME;
			portNumber = PORT;
			userName = USER;
		} else if (args.length != 2) {
			// System.err.println("Usage: java EchoClient <host name> <port number>");
			hostName = HOSTNAME;
			portNumber = PORT;
			userName = USER;
		} else {
			hostName = args[0];
			portNumber = Integer.parseInt(args[1]);
		}
		
		
		
		
		connectButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        addMessage("Connect button");
		        if(!userField.getText().isEmpty()) {
		        userName = userField.getText();
		        }
		        hostName = serverField.getText();
		        if(!portField.getText().isEmpty()) {
		        portNumber = Integer.parseInt(portField.getText());
		        }
		        try {
		            sock = new Socket(hostName, portNumber);
		            addMessage("Connected to server " + hostName + " on port " + portNumber);
		            new Thread(new ServerListener(sock)).start();
		            connected = true;
		        } catch (UnknownHostException ex) {
		            addMessage("Server not found: " + ex.getMessage());
		        } catch (IOException ex) {
		            addMessage("I/O error: " + ex.getMessage());
		        }
		    }
		});
	                
	            	}
	                
	      
	
	
	/**
	 * updatePoints method is called from gameController every time the user gets or loses a point
	 * @param newPoints - new value of points
	 */
	public void updatePoints(int newPoints) {
		points = newPoints;
	}
		

		
	
	
	/**
	 * addMessage adds message to the log and displays it
	 * @param message - message to be added to the log and shown in the gui
	 */
	public static void addMessage(String message) {
        messageList.add(message);
        messageArea.setText(String.join("\n", messageList));
    }

	
	/**receive Game accepts a game string from the server and sends it to gameView which sends it to controller and model to create new bool array
	 * @param currentGame - string of ones and zeroes representing a game board
	 */
	public static void receiveGame(String currentGame) {
		
		gameView.receiveGame(currentGame);

		
	}
	
	
	/**
	 * sendGame gets current game in memory as a bool array, converts to string which it sends to server
	 * @return game string of ones and zeroes
	 */
	public static String sendGame() {
		
		StringBuilder sb = new StringBuilder();
		
		boolArray = gameView.sendCurrentGame(gameMem);
		
		
		for(int i = 0; i < boolArray.length; i++) {
			for(int j = 0; j < boolArray[0].length; j++) {
				String str = "0";
				if(boolArray[i][j]) {
					str = "1";
				}
				sb.append(str);
			}
			 if (i != boolArray.length - 1) {
			sb.append(",");
			 }
		}
		
		
		return sb.toString();
		
		
	}
		



	/**

	A class that listens for input from the server and updates the client accordingly.
	*/
	private static class ServerListener implements Runnable {

	/**
	The socket for communication with the server.
	*/
		private Socket sock;
	/**

	Constructs a new ServerListener with the specified socket.
	@param sock the socket for communication with the server
	*/
	public ServerListener(Socket sock) {
		this.sock = sock;
		}
	/**

	Runs the server listener thread, listening for input from the server and updating the client.
	*/
	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				addMessage(line);
			if(line.charAt(0) == '0' || line.charAt(0)=='1') {
					receiveGame(line);
			}
			}
			} catch (IOException ex) {
					addMessage("Socket closed");
	}
	}
	}
}

	

				

	
	


package server;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



/**
 * Class that runs the server portion of the program
 * @author evely
 *
 */

public class Server extends JFrame {
	/**
	 * serial version ID
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Text area to display log
     */
    private static JTextArea messageArea = new JTextArea();
    
    /**
     * List of messages in the log
     */
    private static List<String> messageList = new ArrayList<>();
    
    /**
     * Variables for number clients/client id
     */
    static int nclient = 0, nclients = 0;
    /**
     * Default port.
     */
    static int PORT = 3000;
    /**
     * Number of port.
     */
    static int portNumber = 0;

    /**
     * Field to enter custom port
     */
    private JTextField portField;
    
    /**
     * Field to start server
     */
    private JButton executeButton;
    
    /**
     Button to check number of clients connected
     */
    private JButton resultsButton;
    
    /**
     * Checkbox to finalize the server
     */
    private JCheckBox finalizeCheckBox;
    
    /**
     * Button the end server connection
     */
    private JButton endButton;
    
    /**
     * Scroll pane to display log
     */
    private JScrollPane scrollPane;
    
    /**
     * Custom port number
     */
    private int customPort;
    
    /**
     * Boolean for whether server is finalized
     */
    private static boolean finalize = false;
    
    /**
     * String of ones and zeros for current game.
     */
    private static String currentGame = null;

    
    
    /** Main method runs the Server program
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Server serverGUI = new Server();
            serverGUI.createAndShowGUI();
        });
    }

    
    /**
     * createAndShowGUI - creates and displays the server GUI w/ action listeners
     */
    private void createAndShowGUI() {
        setTitle("PICROSS Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.PINK);
        JLabel titleLabel = new JLabel("PICROSS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        portField = new JTextField(10);
        inputPanel.add(new JLabel("Port:"));
        inputPanel.add(portField);
        executeButton = new JButton("Execute");
        inputPanel.add(executeButton);
        resultsButton = new JButton("Results");
        inputPanel.add(resultsButton);
        finalizeCheckBox = new JCheckBox("Finalize");
        inputPanel.add(finalizeCheckBox);
        endButton = new JButton("End");
        inputPanel.add(endButton);
        add(inputPanel, BorderLayout.CENTER);
        addMessage("Starting server... ");

        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (portField.getText().isEmpty()) {
                    customPort = PORT;
                } else {
                    customPort = Integer.parseInt(portField.getText());
                }

                Thread serverThread = new Thread(new Runnable() {
                    public void run() {
                        try (ServerSocket serverSocket = new ServerSocket(customPort)) {
                            addMessage("Server started on port " + customPort + ".");
                            while (true) {
                                Socket clientSocket = serverSocket.accept();
                                addMessage("Client connected: " + clientSocket.getInetAddress().getHostName());
                                ClientHandler clientHandler = new ClientHandler(clientSocket);
                                clientHandler.start();
                            }
                        } catch (IOException ex) {
                            addMessage("Error starting server: " + ex.getMessage());
                        }
                    }
                });
                serverThread.start();
            }
        });

        resultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMessage("Number of clients: " + nclients);
            }
                
            });
        
        finalizeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalize = finalizeCheckBox.isSelected();
                addMessage("Finalize is " + (finalize ? "on" : "off"));
            }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        messageArea.setEditable(false);
        scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        add(scrollPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    /** addMessage gets current message adds it to log and displays it in the GUI
     * @param message - current message to add to log
     */
    public static void addMessage(String message) {
        messageList.add(message);
        messageArea.append(message + "\n");
    }

    
    
    

    /**
	 * A thread that handles communication with a single client.
     */
    static class ClientHandler extends Thread {

    /**
    The socket for communication with the client.
    */
    private Socket clientSocket;
    
    /**
    The input stream for reading data from the client.
    */
    private BufferedReader in;
    
    /**
    The output stream for writing data to the client.
    */
    private PrintStream out;
    
    /**
     * Constructs a new ClientHandler with the specified client socket.
     * @param socket the socket for communication with the client
     */
    
    public ClientHandler(Socket socket) {
    	clientSocket = socket;
    }
    
    /**
     *Runs the client handler thread, listening for input from the client and sending responses.
    */
        public void run() {
            nclient++;
            nclients++;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintStream(clientSocket.getOutputStream());

                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        break;
                    }

                    
                    if(input.equals("end")) {
                    	   try {
                    		   out.println("end");
                               clientSocket.close();
                               nclients--;
                               addMessage(clientSocket.getInetAddress().getHostName() + "closed");
                           } catch (IOException ex) {
                               addMessage("Error closing client socket: " + ex.getMessage());
                           }
                    	   break;
                    }
                    
                    if(input.charAt(0) == '1' || input.charAt(0) == '0') {
                    	currentGame = input;
                    	out.println("Game received by server");
                    }
                    
                    if(input.equals("receive")) {
                    	if(currentGame != null) {
                    		out.println(currentGame);
                    		
                    	}
                    	
                    	else {
                    		currentGame = "00000, 11111, 00000, 11111, 00000";
                    		out.println(currentGame);
                    	}
                    }
                    
                    addMessage("Message from client " + nclient + ": " + input);
                    
                    if (finalize) {
                        out.println("Server finalized!");
                        out.close();
                        in.close();
                        clientSocket.close();
                        
                        break;
                    }
                }
            } catch (IOException ex) {
                addMessage("Error handling client: " + ex.getMessage());
            
             
            }
        }
    }

    }

                
                
                
                

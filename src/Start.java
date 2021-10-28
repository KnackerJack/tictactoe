import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Start {
	private JFrame mainFrame;
	private JPanel imgPanel;
	private JButton startGame;
	private JLabel title; 
	private JLabel welcome;
	public String input;
	private JFrame small;
	private JPanel smallPanel;
	private JLabel enter;
	private JTextField name;
	private JButton submit;
	
	public Start() {
		enterName();
	}
	
	public void enterName() {
		
		small = new JFrame();
		small.setSize(new Dimension(800, 800));
		small.setLayout(null);
		small.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		small.setResizable(false);
		
		smallPanel = new JPanel();
		smallPanel.setSize(new Dimension(800, 800));
		smallPanel.setBackground(Color.BLACK);
		smallPanel.setLayout(null);
		
		enter = new JLabel("Enter your name : ");
		enter.setBounds(150, 100, 500, 150);
		enter.setForeground(Color.white);
		enter.setFont(new Font("MV Boli", Font.BOLD, 50));
		
		name = new JTextField();
		name.setSize(new Dimension(250, 40));
		name.setFont(new Font("Consolas", Font.PLAIN, 35));
		name.setForeground(Color.black);
		name.setBackground(Color.green);
		name.setBounds(180, 250, 400, 50);
		
		submit = new JButton("Enter");
		submit.setBounds(300, 340, 150, 50);
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				small.setVisible(false);
				input = name.getText();
				startScreen();
				
			}
		});
		
		smallPanel.add(enter);
		smallPanel.add(name);
		smallPanel.add(submit);
		
		small.add(smallPanel);
		small.setVisible(true);
		
	}
	
	public void startScreen() {
		Image img = null;
//		try {
			String filePath = new File(getClass().getResource("ttt1.png").getPath()).getAbsolutePath();
			filePath = filePath.replace("%20", " "); 
			img = Toolkit.getDefaultToolkit().getImage(filePath); //ImageIO.read(new File(filePath));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
		Image dimg = img.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		JLabel imgLabel = new JLabel(imageIcon);
		imgLabel.setVisible(true);
		imgLabel.setOpaque(true);
		
		imgPanel = new JPanel();
		imgPanel.setSize(new Dimension(800, 600));
		imgPanel.add(imgLabel);
		
		startGame = new JButton("START A NEW GAME");
		startGame.setBounds(300, 190, 200, 20);
		startGame.setBackground(Color.CYAN);
		startGame.setOpaque(true);
		
		startGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				GameBoard Board = new GameBoard();
				Board.chooseSide();
				
			}
		});
		
		title = new JLabel("Tic Tac Toe");
		title.setForeground(Color.GREEN);
		title.setBounds(245, 2, 360, 150);
		title.setFont(new Font("Showcard Gothic", Font.BOLD, 50));
		
		welcome = new JLabel("Welcome  " + input);
		welcome.setForeground(Color.cyan);
		welcome.setSize(50, 50);
		welcome.setBounds(300, 100, 250, 40);
		welcome.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0,0,800,600);
		
		layeredPane.add(imgPanel, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(title, JLayeredPane.DRAG_LAYER);
		layeredPane.add(welcome, JLayeredPane.DRAG_LAYER);
		layeredPane.add(startGame, JLayeredPane.DRAG_LAYER);
		
		mainFrame = new JFrame();
		mainFrame.setTitle("TicTacToe_HD.exe");
		mainFrame.setSize(new Dimension(800, 600));
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
		mainFrame.add(layeredPane);
	}

	public static void main(String args[]) {
		new Start();
	}

}

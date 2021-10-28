import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameBoard {
	
	private JFrame gameFrame;
	public JButton[] button = new JButton[9];
	public String[] buttonSymbols = new String[9]; 
	private JPanel buttonPanel;
	private JFrame choiceFrame;
	private JPanel choicePanel;
	private JButton circle;
	private JButton x;
	public JLabel finalMessage;
	boolean xChosen;
	boolean circleChosen;
	boolean p1_turn;
	boolean cpu;
	String human;
	String ai;
	int score;
	boolean playerWon;
	boolean cpuWon;
	boolean isTie;
	boolean gameOver;
	
	public void chooseSide() {
		xChosen = false;
		circleChosen = false;
		choiceFrame = new JFrame("CHOOSE YOUR SIDE");
		choiceFrame.setSize(new Dimension(500, 500));
		choiceFrame.setLayout(null);
		choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		choiceFrame.setResizable(true);
		
		choicePanel = new JPanel(new GridLayout(1, 2));
		choicePanel.setSize(new Dimension(500,500));
		
		x = new JButton("X");
		x.setForeground(Color.RED);
		x.setBackground(Color.BLACK);
		x.setFont(new Font("Comic Sans MS", Font.BOLD, 120));
		x.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xChosen = true;
				choiceFrame.setVisible(false);
				startGame();
			}
		});
		
		circle = new JButton("O");
		circle.setForeground(Color.BLUE);
		circle.setBackground(Color.white);
		circle.setFont(new Font("Comic Sans MS", Font.BOLD, 120));
		circle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				circleChosen = true;
				choiceFrame.setVisible(false);
				startGame();
			}
		});
		
		choicePanel.add(x);
		choicePanel.add(circle);
		
		choiceFrame.add(choicePanel);
		choiceFrame.setVisible(true);
		
	}
	
	public void startGame() {
		playerWon = false;
		cpuWon = false;
		isTie = false;
		gameOver = false;
		gameFrame = new JFrame("TTT_HD.exe");
		gameFrame.setSize(new Dimension(1366, 768));
		gameFrame.setVisible(true);
		gameFrame.setLayout(null);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		
		Image gameImg = null;
//		try {
			String filePath = new File(getClass().getResource("glow2.jpg").getPath()).getAbsolutePath();
			filePath = filePath.replace("%20", " "); 
			gameImg = Toolkit.getDefaultToolkit().getImage(filePath);// ImageIO.read(new File(filePath));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
		Image dimg = gameImg.getScaledInstance(1366, 768, Image.SCALE_SMOOTH);
		ImageIcon backImage = new ImageIcon(dimg);
		JLabel backLabel = new JLabel(backImage);
		backLabel.setVisible(true);
		backLabel.setOpaque(true);
		
		JPanel backPanel = new JPanel();
		backPanel.setSize(new Dimension(1366, 768));
		backPanel.add(backLabel);
		
		JLayeredPane lPane = new JLayeredPane();
		lPane.setBounds(0,0,1366,768);
		
		buttonPanel = new JPanel();
		buttonPanel.setSize(new Dimension(500, 500));
		buttonPanel.setBounds(430, 130, 500, 500);
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setLayout(new GridLayout(3, 3));
		
		for(int i=0; i<9; i++) {
			button[i] = new JButton();
			String c = button[i].getText();
			buttonSymbols[i] = c;
			button[i].setBackground(Color.magenta);
			button[i].setBorder(BorderFactory.createLineBorder(Color.black, 5));
			button[i].setFont(new Font("Comic Sans MS", Font.BOLD, 120));
			buttonPanel.add(button[i]);
			button[i].setFocusable(false);
			button[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(foundWinner()) {
						p1_turn = false;
						cpu = false;
						isTie = false;
						for(int i=0; i< 9; i++) {
							if(button[i].getText() == "") {
								button[i].setText(" ");
							}
						}
					}
					p1_turn = true;
					cpu = false;
					for (int i=0; i<9; i++) {
						if(e.getSource()==button[i]) {
							if(p1_turn) {
								if(button[i].getText()=="") { 
									button[i].setForeground(Color.red);
									if (xChosen) {
										human = "X";
										ai = "O";
										button[i].setText("X");
										buttonSymbols[i] = "X";
										p1_turn = false;
										cpu = true;
									} else {
										human = "O";
										ai = "X";
										button[i].setText("O");
										buttonSymbols[i] = "O";
										button[i].setForeground(Color.blue);
										p1_turn = false;
										cpu = true;
									}
									playerWon = checkWinner(buttonSymbols, human);
									if(playerWon) {
										System.out.println("You Win!");
										finalMessage.setText("You Win!");
										finalMessage.setForeground(Color.green);
										score = -1;
										gameOver = true;
									} 
									if(checkTie()) {
										p1_turn = false;
										cpu = false;
										finalMessage.setText("TIE");
										score = 0;
										gameOver = true;
									}
									cpuBestMove();
								} else {
									System.out.println("PLEASE SELECT VALID CELL");
								}
							}
						}
					}
					
				}
			});
		}
		
		JButton refresh = new JButton("REFRESH");
		refresh.setSize(new Dimension(350, 400));
		refresh.setBounds(550, 660, 260, 50);
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearStatus();
				
			}
		});
		
		finalMessage = new JLabel("");
		finalMessage.setSize(new Dimension(500, 500));
		finalMessage.setBounds(560, 50, 300, 50);
		finalMessage.setForeground(Color.CYAN);
		finalMessage.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		
		lPane.add(backPanel, JLayeredPane.DEFAULT_LAYER);
		lPane.add(buttonPanel, JLayeredPane.DRAG_LAYER);
		lPane.add(finalMessage, JLayeredPane.DRAG_LAYER);
		lPane.add(refresh, JLayeredPane.DRAG_LAYER);
		
		gameFrame.add(lPane);
	}
	
	public int MiniMax(String board[], boolean isMaximizing) { 
		if(checkWinner(buttonSymbols, ai)) {
			return 1;
		}
		if(checkWinner(buttonSymbols, human)) {
			return -1;
		}
		if(checkTie()) {
			return 0;
		}
		if(isMaximizing) {
			int optimalScore = (int) Double.NEGATIVE_INFINITY;
			for(int i=0; i<9; i++) {
				if(buttonSymbols[i] == "") {
					buttonSymbols[i] = ai;
					int score = MiniMax(buttonSymbols, false);
					buttonSymbols[i] = "";
					optimalScore = Math.max(score, optimalScore);
				}
			}
			return optimalScore;
		} else {
			int optimalScore = (int) Double.POSITIVE_INFINITY;
			for(int i=0; i<9; i++) {
				if(buttonSymbols[i] == "") {
					buttonSymbols[i] = human;
					int score = MiniMax(buttonSymbols, true);
					buttonSymbols[i] = "";
					optimalScore = Math.min(score, optimalScore);
				}
			}
			return optimalScore;
		}
	}
	
	public void cpuBestMove() {
		if(foundWinner()) {
			p1_turn = false;
			cpu = false;
			isTie = false;
			for(int i=0; i< 9; i++) {
				if(button[i].getText() == "") {
					button[i].setText(" ");
				}
			}
		}
		int bestMove = 0;
		int bestScore = (int) Double.NEGATIVE_INFINITY;
		if(cpu) {
			for(int i =0; i< 9; i++) {
				if(buttonSymbols[i] == "") {
					buttonSymbols[i] = ai;
					int score = MiniMax(buttonSymbols, false);
					buttonSymbols[i] = "";
					if(score > bestScore) {
						bestScore = score;
						bestMove = i;
					}
				}
			}
			button[bestMove].setText(ai);
			if(ai == "O") {
				button[bestMove].setForeground(Color.blue);
			} if(ai == "X") {
				button[bestMove].setForeground(Color.red);
			}
			buttonSymbols[bestMove] = ai;
			cpu = false;
			p1_turn = true;
		}
		cpuWon = checkWinner(buttonSymbols, ai);
		if(cpuWon) {
			finalMessage.setText("You Lose!");
			finalMessage.setForeground(Color.RED);
			score = 1;
			gameOver = true;
		} if(checkTie()) {
			p1_turn = false;
			cpu = false;
			finalMessage.setText("TIE");
			score = 0;
			gameOver = true;
		} 
	}
	
	public boolean checkWinner(String[] slots, String player) {
		 if (
		 (slots[0] == player && slots[1] == player && slots[2] == player) ||
		 (slots[3] == player && slots[4] == player && slots[5] == player) ||
		 (slots[6] == player && slots[7] == player && slots[8] == player) ||
		 (slots[0] == player && slots[3] == player && slots[6] == player) ||
		 (slots[1] == player && slots[4] == player && slots[7] == player) ||
		 (slots[2] == player && slots[5] == player && slots[8] == player) ||
		 (slots[0] == player && slots[4] == player && slots[8] == player) ||
		 (slots[2] == player && slots[4] == player && slots[6] == player)
		 ) {
		 return true;
		 } else {
		 return false;
		 }
	}
	
	public boolean checkTie() {
		int openSpots = 0;
		for(int i=0; i<9; i++) {
			if(buttonSymbols[i] == "") {
				openSpots++;
			}
		}
		if(openSpots == 0) {
			if(!checkWinner(buttonSymbols, human) && !checkWinner(buttonSymbols, ai)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean foundWinner() {
		if (cpuWon || playerWon) {
			return true;
		} else {
			return false;
		}
	}
	
	public void clearStatus() {
		score = 0;
		finalMessage.setText("");
		for(int j=0; j< 9; j++) {
			buttonSymbols[j] = "";
			button[j].setText("");
		}
		p1_turn = true;
		cpu = false;
		playerWon = false;
		cpuWon = false;
		isTie = false;
	}

}

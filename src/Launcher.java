import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
import javax.swing.*;

public class Launcher {
	
	private final static int ONE_SECOND = 1000;
	private static int INPUT_TIME = 0;
	private static int CURRENT_TIME = 0;
	private static Timer timer;
	
	// J Variables
	private static JLabel countdown = new JLabel("00:00:00");
	private static JButton pauseButton = new JButton("Pause");
	private static JTextField inputField = new JTextField(10);
	
	// Sound File
	private static String soundName = "sound.wav";
	private static Clip clip;
	private static Sound playSound = new Sound();
	
	// Misc
	private static final String delimiter = ":";
	
	public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		// Create and Set up Frame
		JFrame frame = new JFrame("Timer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 200);
		frame.setMinimumSize(new Dimension(500, 200));
		frame.setVisible(true);	
		
		// ======================= TOP =======================
		// Panel for Top
		JPanel topPanel = new JPanel();
		frame.getContentPane().add(BorderLayout.CENTER, topPanel);
		
		// Add countdown label
		topPanel.add(countdown);
		countdown.setFont(new Font("Arial", Font.BOLD, 100));
		
		// ======================= SETUP TIMER =======================
		timer = new Timer(ONE_SECOND, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				CURRENT_TIME -= 1;
				// If CURRENT_TIME reaches 0
				if (CURRENT_TIME <= 0) 
					try {
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								playSound.PlaySound(soundName, clip, inputField);
							}
							
						}) { }.start();
						timer.stop();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Error Playing Sound");
					}					
				countdown.setText(FormatSeconds.formatSeconds(CURRENT_TIME, delimiter));			
			}
		});			

		// ======================= CENTER =======================
		// Panel for Center
		JPanel centerPanel = new JPanel();
		frame.getContentPane().add(BorderLayout.SOUTH, centerPanel);
		
		// Text field for input
		centerPanel.add(inputField);
		
		// Add Start Button 
		JButton startButton = new JButton("Start");
		centerPanel.add(startButton);
		startButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CURRENT_TIME = Integer.parseInt(inputField.getText());
					if (CURRENT_TIME >= 99999 || CURRENT_TIME <= 0) throw new Exception();	
					INPUT_TIME = CURRENT_TIME;
					countdown.setText(FormatSeconds.formatSeconds(CURRENT_TIME, delimiter));
					pauseButton.setText("Pause");
					timer.restart();
				} catch (Exception e2) {
					inputField.setText("");
				}				
			}
		});
		
		// Add Pause Button 
		centerPanel.add(pauseButton);
		pauseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if (pauseButton.getText().equalsIgnoreCase("Pause") && timer.isRunning() && CURRENT_TIME > 0) {
					timer.stop();
					pauseButton.setText("Continue");
				}
				else if (pauseButton.getText().equalsIgnoreCase("Continue") && !timer.isRunning() && CURRENT_TIME > 0) {
					timer.start();
					pauseButton.setText("Pause");
				}
				
			}
		});
		
		// Add Restart Button 
		JButton restartButton = new JButton("Restart");
		centerPanel.add(restartButton);
		restartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.restart();
				CURRENT_TIME = INPUT_TIME;
				countdown.setText(FormatSeconds.formatSeconds(CURRENT_TIME, delimiter));
				pauseButton.setText("Pause");
			}
			
		});
		
		// Add Clear Button
		JButton clearButton = new JButton("Clear");
		centerPanel.add(clearButton);
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				CURRENT_TIME = 0;
				INPUT_TIME = 0;
				countdown.setText(FormatSeconds.formatSeconds(CURRENT_TIME, delimiter));
				pauseButton.setText("Pause");
			}
		});
		
		// Refresh Frame
		frame.invalidate();
		frame.validate();
		frame.repaint();
    }	
}



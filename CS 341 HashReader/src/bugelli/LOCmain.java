package bugelli;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * @version 1
 * @author Lauren Bugelli
 * @since 10/11/2020
 */
public class LOCmain {

	private JFrame frame;
	private JTextArea textArea;
	private JButton btnForFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LOCmain window = new LOCmain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LOCmain() {
		initialize();
		createEvent();
	}
	public void createEvent() {
		btnForFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildOutput();
			}
		});
	}
	/**
	 * buildOutput handles the output shown to the user
	 * 
	 */
	public void buildOutput() {
		ReadFile rf = new ReadFile();
		//MAKE SURE VALID FILE
		try {
			rf.pickMe();
		}catch (Exception e) {
			e.printStackTrace();
		}
		//OUTPUT TO USER
		String value = String.valueOf(rf.count);
		String wc = String.valueOf(rf.wordCount);
		textArea.append("Your New File Will Contain A Description And The Following Lines...\n");
		textArea.append("The number of executable lines of code (LOC) is: " + value + "\n");
		textArea.append("The number of Keywords in the file is: " + wc + "\n");
		textArea.append("The time it took to read the file and perform the counts is: " + rf.timer.getMilliseconds() +"milliseconds");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("HashCounter");
		lblTitle.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblTitle.setBounds(176, 11, 85, 29);
		frame.getContentPane().add(lblTitle);
		
		btnForFile = new JButton("Choose File");
		btnForFile.setBounds(163, 35, 111, 23);
		frame.getContentPane().add(btnForFile);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 81, 414, 169);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	}
}

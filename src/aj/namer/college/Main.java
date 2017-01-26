package aj.namer.college;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import aj.namer.college.model.Person;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {

	protected static final int NO_AIM_MODE = 0;
	protected static final int AIM_MODE = 1;
	
	/**
	 * The Main Frame
	 */
	private JFrame frmNamer;
	private JTextField textFieldAim;
	private JButton btnSelectFiles;
	private JButton btnGo;
	
	
	private JLabel statusMessage;
	
	/**
	 * Source File
	 */
	private File mFile;
	private File[] mFiles;
	private JMenuBar menuBar;
	private JMenu mnNew;
	private JMenuItem mntmNewUser;
	private JMenuItem mntmSelectUsers;
	
	/**
	 * List of Names
	 */
	private ArrayList<String> mNames;
	private JMenu mnMode;
	private JMenuItem mAimMode;
	private JMenuItem mNoAimMode;
	private JLabel lblAim;
	protected int mMode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmNamer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNamer = new JFrame();
		frmNamer.setTitle("Namer");
		frmNamer.setBounds(100, 100, 450, 300);
		frmNamer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lblAim = new JLabel("Aim");
		
		textFieldAim = new JTextField();
		textFieldAim.setColumns(10);
		
		btnSelectFiles = new JButton("Select File");
		btnGo = new JButton("Go");
		
		statusMessage = new JLabel("");
		GroupLayout groupLayout = new GroupLayout(frmNamer.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(lblAim)
					.addGap(66)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(statusMessage)
						.addComponent(textFieldAim, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(195, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnGo, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelectFiles))
					.addGap(151))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(48)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAim)
						.addComponent(textFieldAim, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addComponent(btnSelectFiles)
					.addGap(18)
					.addComponent(btnGo, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
					.addGap(65)
					.addComponent(statusMessage))
		);
		frmNamer.getContentPane().setLayout(groupLayout);
		
		menuBar = new JMenuBar();
		frmNamer.setJMenuBar(menuBar);
		
		mnNew = new JMenu("Edit");
		menuBar.add(mnNew);
		
		mntmNewUser = new JMenuItem("New User");
		mntmNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewUserDialog dialog = new NewUserDialog();
				dialog.setVisible(true);
			}
		});
		mnNew.add(mntmNewUser);
		
		mntmSelectUsers = new JMenuItem("Select Users");
		mntmSelectUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSelectedUsers();
			}
		});
		mnNew.add(mntmSelectUsers);
		
		mnMode = new JMenu("Mode");
		menuBar.add(mnMode);
		
		mAimMode = new JMenuItem("Aim Mode");
		mnMode.add(mAimMode);
		
		mNoAimMode = new JMenuItem("No Aim Mode");
		mnMode.add(mNoAimMode);
		setupView();
	}


	/**
	 * Setup the link between view & object
	 */
	private void setupView() {
		btnSelectFiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isAimMode()) {
					JFileChooser fileChooser = new JFileChooser();
					//fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setVisible(true);
					int returnVal = fileChooser.showOpenDialog(frmNamer);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						mFile = fileChooser.getSelectedFile();
						statusMessage.setText("Selected File = " + mFile.getName());
					}
				} else {
					JFileChooser fileChooser = new JFileChooser();
					//fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					fileChooser.setMultiSelectionEnabled(true);
					fileChooser.setVisible(true);
					int returnVal = fileChooser.showOpenDialog(frmNamer);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						mFiles = fileChooser.getSelectedFiles();
						statusMessage.setText("Selected Files : [");
						String text = null;
						boolean isFirst = true;
						for (File file : mFiles) {
							if (isFirst) {
								text = file.getName();
								isFirst = false;
							} else {
								text = text + ", " + file.getName();
							}
						}
					}
				}
			}
		});
		btnGo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (String personString : mNames) {
					CreateNamerThread createNamerThread = new CreateNamerThread();
					createNamerThread.setAim(isAimMode() ? textFieldAim.getText() : null);
					createNamerThread.setPerson(personString);
					if (isAimMode()) {
						createNamerThread.setFile(mFile);
					} else {
						createNamerThread.setFiles(mFiles);
					}
					Thread thread = new Thread(createNamerThread);
					thread.start();
				}
			}
		});
		mAimMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldAim.setVisible(true);
				btnSelectFiles.setText("Select File");
				lblAim.setVisible(true);
			}
		});
		mNoAimMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldAim.setVisible(false);
				btnSelectFiles.setText("Select Files/Dirs");
				lblAim.setVisible(false);
				mMode = NO_AIM_MODE;
			}
		});
	}
	
	private void showSelectedUsers() {
		try {
			File usersConfig = new File(".users");
			System.out.println("Exist = " + usersConfig.getAbsolutePath());
			FileReader fileReader = new FileReader(usersConfig);
			Gson gson = new Gson();
			ArrayList<Person> persons = gson.fromJson(fileReader, new TypeToken<ArrayList<Person>>() {}.getType());
			String[] elements = new String[persons.size()];
			for (Person person : persons) {
				System.out.println(person);
			}
			for (int i = 0; i < elements.length; i++) {
				Person person = persons.get(i);
				elements[i] = person.getPersonName() + ", " + person.getPersonRoll();
			}
			final JList<String> list = new JList<String>(elements);
	        final JScrollPane pane = new JScrollPane(list);
	        final JFrame frame = new JFrame("Select Users");
	 
	        // create a button and add action listener
	        final JButton btnGet = new JButton("OK");
	        btnGet.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                mNames = (ArrayList<String>) list.getSelectedValuesList();
	                frame.setVisible(false);
	            }
	        });
	 
	        frame.getContentPane().setLayout(new BorderLayout());
	        frame.getContentPane().add(pane, BorderLayout.CENTER);
	        frame.getContentPane().add(btnGet, BorderLayout.SOUTH);
	        frame.setSize(250, 200);
	        frame.setVisible(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public boolean isAimMode() {
		return mMode == AIM_MODE;
	}
	
}

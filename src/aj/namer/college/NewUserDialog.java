package aj.namer.college;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import aj.namer.college.model.Person;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NewUserDialog extends JFrame {

	/**
	 * Default ID
	 */
	private static final long serialVersionUID = 7984841741419617367L;
	
	
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField rollField;

	/**
	 * Create the frame.
	 */
	public NewUserDialog() {
		setTitle("New User");
		setBounds(100, 100, 303, 183);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblName = new JLabel("Name");
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		JLabel lblRoll = new JLabel("Roll");
		
		rollField = new JTextField();
		rollField.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gson gson = new Gson();
				ArrayList<Person> persons;
				try {
					File usersConfig = new File(".users");
					boolean createdNow = usersConfig.createNewFile();
					if (!createdNow) {
						FileReader fileReader = new FileReader(usersConfig);
						persons = gson.fromJson(fileReader, new TypeToken<ArrayList<Person>>() {}.getType());
					} else {
						persons = new ArrayList<Person>();
					}
					Person person = new Person();
					person.setPersonName(nameField.getText());
					person.setPersonRoll(Integer.parseInt(rollField.getText()));
					persons.add(person);
					FileWriter fileWriter = new FileWriter(usersConfig);
					fileWriter.write(gson.toJson(persons));
					fileWriter.close();
					NewUserDialog.this.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
					NewUserDialog.this.dispose();
				}
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewUserDialog.this.dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblRoll)
						.addComponent(lblName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnOk)
							.addPreferredGap(ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
							.addComponent(btnCancel))
						.addComponent(nameField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addComponent(rollField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
					.addGap(41))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rollField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRoll))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOk)
						.addComponent(btnCancel))
					.addContainerGap(128, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

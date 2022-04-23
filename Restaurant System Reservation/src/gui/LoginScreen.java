package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import tablesanddishes.RestDish;
import tablesanddishes.RestTable;
import users.Users;

public class LoginScreen extends javax.swing.JFrame
{

	private javax.swing.JButton btnLogin;
	private javax.swing.JLabel lbluName;
	private javax.swing.JLabel lblPassword;
	private javax.swing.JPasswordField txtPassword;
	private javax.swing.JTextField txtuName;
	private JLabel lblNewLabel;

	private static List<Users> u = new ArrayList<>();
	private static List<RestTable> t = new ArrayList<>();
	private static List<RestDish> d = new ArrayList<>();

	public LoginScreen(List<Users> u, List<RestTable> t, List<RestDish> d)
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginScreen.class.getResource("/resources/icon.png")));
		setResizable(false);
		setTitle("Restaurant Reservation System");
		initComponents();
		this.u = u;
		this.t = t;
		this.d = d;
	}

	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					LoginScreen frame = new LoginScreen(u, t, d);
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void initComponents()
	{

		btnLogin = new javax.swing.JButton();
		txtuName = new javax.swing.JTextField();
		lbluName = new javax.swing.JLabel();
		lblPassword = new javax.swing.JLabel();
		txtPassword = new javax.swing.JPasswordField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		btnLogin.setText("Login");
		btnLogin.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				int i;
				String username = txtuName.getText();
				String password = txtPassword.getText();

				Users currentUser = null;
				for (i = 0; i < u.size(); i++)
				{
					if (u.get(i).getUsername().equals(username) && u.get(i).getPassword().equals(password))
					{
						currentUser = u.get(i);
						break;
					}
				}

				if (currentUser != null)
				{
					JOptionPane.showMessageDialog(rootPane, "Welcome");
					if (currentUser.getRole().equalsIgnoreCase("Waiter"))
					{
						WaiterDashboard w = new WaiterDashboard(currentUser, u, t, d);
						w.setVisible(true);
					} else if (currentUser.getRole().equalsIgnoreCase("Client"))
					{
						ClientDashboard cl = new ClientDashboard(currentUser, u, t, d);
						cl.setVisible(true);
					} else if (currentUser.getRole().equalsIgnoreCase("Manager"))
					{
						ManagerDashboard m = new ManagerDashboard(currentUser, u, t, d);
						m.setVisible(true);
					} else if (currentUser.getRole().equalsIgnoreCase("Cooker"))
					{
						CookerDashboard co = new CookerDashboard(currentUser, u, t, d);
						co.setVisible(true);
					}
					dispose();
				} else
				{
					JOptionPane.showMessageDialog(rootPane, "Invalid user name or password");
				}
			}
		});

		lbluName.setText("Username");

		lblPassword.setText("Password");

		lblNewLabel = new JLabel("Welcome to the RRS");
		lblNewLabel.setFont(new Font("SansSerif", Font.ITALIC, 28));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(txtuName,
								GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
						.addComponent(lbluName, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup().addGap(10)
								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
								.addGap(52)
								.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(20)
				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE).addGap(12)
				.addComponent(lbluName).addGap(13)
				.addComponent(txtuName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblPassword)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnLogin).addComponent(
						txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
		pack();
	}

}

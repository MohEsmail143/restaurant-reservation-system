package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import tablesanddishes.RestDish;
import tablesanddishes.RestTable;
import users.Users;
import xml.MainXML;

public class WaiterDashboard extends JFrame
{
	private JPanel contentPane;
	private JScrollPane jScrollPane = new JScrollPane();;
	private JTable jTable = new JTable();;

	private static Users currentUser;
	private static List<Users> usersList = new ArrayList<>();
	private static List<RestTable> tablesList = new ArrayList<>();
	private static List<RestDish> dishesList = new ArrayList<>();

	public WaiterDashboard(Users currentUser, List<Users> usersList, List<RestTable> tablesList,
			List<RestDish> dishesList)
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(WaiterDashboard.class.getResource("/resources/icon.png")));
		setResizable(false);
		setTitle("Waiter");
		this.currentUser = currentUser;
		this.usersList = usersList;
		this.tablesList = tablesList;
		this.dishesList = dishesList;
		initComponents();
		addRowsToJTable();
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					WaiterDashboard frame = new WaiterDashboard(currentUser, usersList, tablesList, dishesList);
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public void addRowsToJTable()
	{
		DefaultTableModel model = (DefaultTableModel) jTable.getModel();
		Object rowData[] = new Object[2];
		for (int i = 0; i < tablesList.size(); i++)
		{
			rowData[0] = tablesList.get(i).getNumber();
			rowData[1] = tablesList.get(i).getReservedBy();
			model.addRow(rowData);
		}

	}

	private void initComponents()
	{
		jTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Table No.", "Reserved By" }));
		jScrollPane.setViewportView(jTable);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 594);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel helloLabel = new JLabel("Hello, " + currentUser.getName() + "!");
		helloLabel.setForeground(Color.RED);
		helloLabel.setFont(new Font("Times New Roman", Font.ITALIC, 22));

		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MainXML mainXML = new MainXML();
				try
				{
					MainXML.marshalRest(usersList, tablesList, dishesList);
				} catch (JAXBException | FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
				LoginScreen logScreen = new LoginScreen(usersList, tablesList, dishesList);
				logScreen.setVisible(true);
				dispose();
			}
		});

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		jTable.setDefaultRenderer(String.class, centerRenderer);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(42).addComponent(jScrollPane,
								GroupLayout.PREFERRED_SIZE, 374, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(helloLabel,
								GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(48, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addContainerGap(355, Short.MAX_VALUE).addComponent(btnLogOut).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(helloLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(btnLogOut)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

}

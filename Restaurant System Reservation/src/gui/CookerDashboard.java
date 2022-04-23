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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import tablesanddishes.RestDish;
import tablesanddishes.RestTable;
import users.Users;
import xml.MainXML;

public class CookerDashboard extends JFrame
{
	private JPanel contentPane;
	private JScrollPane scrollPaneTables = new javax.swing.JScrollPane();;
	private JTable tblTables = new javax.swing.JTable();;
	private JButton btnLogOut = new JButton("Log out");
	private JScrollPane scrollPaneDishes = new JScrollPane();
	private JTable tblDishes = new JTable();

	private static Users currentUser;
	private static List<Users> usersList = new ArrayList<>();
	private static List<RestTable> tablesList = new ArrayList<>();
	private static List<RestDish> dishesList = new ArrayList<>();

	public CookerDashboard(Users currentUser, List<Users> usersList, List<RestTable> tablesList,
			List<RestDish> dishesList)
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(CookerDashboard.class.getResource("/resources/icon.png")));
		setResizable(false);
		setTitle("Cooker");
		this.currentUser = currentUser;
		this.usersList = usersList;
		this.tablesList = tablesList;
		this.dishesList = dishesList;
		initComponents();
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					CookerDashboard frame = new CookerDashboard(currentUser, usersList, tablesList, dishesList);
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public void addRowsToJTable_tblTables()
	{
		DefaultTableModel model = (DefaultTableModel) tblTables.getModel();
		Object rowData[] = new Object[2];
		for (RestTable t : tablesList)
		{
			rowData[0] = t.getNumber();
			model.addRow(rowData);
		}

	}

	public void initComponents()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblHello = new JLabel("Hello, " + currentUser.getName() + "!");
		lblHello.setForeground(Color.RED);
		lblHello.setFont(new Font("Times New Roman", Font.ITALIC, 22));

		tblDishes.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Dishes ordered" }));
		scrollPaneDishes.setViewportView(tblDishes);

		tblTables.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Table No." }));
		scrollPaneTables.setViewportView(tblTables);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tblTables.setDefaultRenderer(String.class, centerRenderer);
		addRowsToJTable_tblTables();
		tblTables.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent event)
			{
				DefaultTableModel model = (DefaultTableModel) tblDishes.getModel();
				model.setRowCount(0);
				Object rowData[] = new Object[1];

				String tableNo = tblTables.getValueAt(tblTables.getSelectedRow(), tblTables.getSelectedColumn())
						.toString();
				RestTable selectedTable = null;

				for (RestTable t : tablesList)
				{
					if (tableNo.equals(Byte.toString(t.getNumber())))
					{
						selectedTable = t;
						break;
					}
				}

				if (selectedTable.getOrderedDishes() != null)
				{
					for (RestDish d : selectedTable.getOrderedDishes())
					{
						rowData[0] = d.getName();
						model.addRow(rowData);
					}
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblHello, GroupLayout.PREFERRED_SIZE, 269,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(183, Short.MAX_VALUE))
								.addComponent(btnLogOut, Alignment.TRAILING)))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup().addGap(27)
						.addComponent(scrollPaneTables, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(scrollPaneDishes, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
						.addGap(40)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addComponent(lblHello, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneTables, GroupLayout.PREFERRED_SIZE, 382, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneDishes, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE))
				.addGap(56).addComponent(btnLogOut).addContainerGap()));
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
		contentPane.setLayout(gl_contentPane);
	}
}

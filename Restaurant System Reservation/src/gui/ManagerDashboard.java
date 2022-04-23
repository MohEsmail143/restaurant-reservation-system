package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
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

public class ManagerDashboard extends JFrame
{
	private JPanel contentPane;;
	private JTable tblTables;
	private JTable tblUsers;
	private JTable tblDishes;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	CardLayout cl = new CardLayout();

	private double todayTotal;
	private static Users currentUser;
	private static List<Users> usersList = new ArrayList<>();
	private static List<RestTable> tablesList = new ArrayList<>();
	private static List<RestDish> dishesList = new ArrayList<>();

	public ManagerDashboard(Users currentUser, List<Users> usersList, List<RestTable> tablesList,
			List<RestDish> dishesList)
	{
		this.currentUser = currentUser;
		this.usersList = usersList;
		this.tablesList = tablesList;
		this.dishesList = dishesList;
		initComponents();
		addRowsToJTable_tblUsers();
		addRowsToJTable_tblTables();
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ManagerDashboard frame = new ManagerDashboard(currentUser, usersList, tablesList, dishesList);
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public void addRowsToJTable_tblUsers()
	{
		DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
		Object rowData[] = new Object[2];
		for (int i = 0; i < usersList.size(); i++)
		{
			rowData[0] = usersList.get(i).getName();
			rowData[1] = usersList.get(i).getRole();
			model.addRow(rowData);
		}
	}

	public void addRowsToJTable_tblTables()
	{
		DefaultTableModel model = (DefaultTableModel) tblTables.getModel();
		Object rowData[] = new Object[3];
		for (int i = 0; i < tablesList.size(); i++)
		{
			rowData[0] = tablesList.get(i).getNumber();
			rowData[1] = tablesList.get(i).getReservedBy();
			rowData[2] = tablesList.get(i).getTotal();
			model.addRow(rowData);
		}
	}

	public void initComponents()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManagerDashboard.class.getResource("/resources/icon.png")));
		setResizable(false);
		setTitle("Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 480, 594);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel helloLabel = new JLabel("Hello, " + currentUser.getName() + "!");
		helloLabel.setForeground(Color.RED);
		helloLabel.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		JPanel panelTables = new JPanel();
		tabbedPane.addTab("Tables", null, panelTables, null);

		JPanel panelUsers = new JPanel();
		tabbedPane.addTab("Users", null, panelUsers, null);

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

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE)
								.addComponent(helloLabel, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addContainerGap(357, Short.MAX_VALUE).addComponent(btnLogOut).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(helloLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE).addComponent(btnLogOut)
						.addContainerGap()));

		JScrollPane scrollPaneTables = new JScrollPane();

		JLabel lblTotalToday = new JLabel("Total Earned Today");

		JLabel lblShowTotalToday = new JLabel("");

		for (RestTable t : tablesList)
			todayTotal += t.getTotal();

		lblShowTotalToday.setText(Double.toString(todayTotal));

		JScrollPane scrollPaneDishes = new JScrollPane();
		GroupLayout gl_panelTables = new GroupLayout(panelTables);
		gl_panelTables.setHorizontalGroup(gl_panelTables.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTables.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelTables.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelTables.createSequentialGroup()
										.addComponent(scrollPaneTables, GroupLayout.PREFERRED_SIZE, 262,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(scrollPaneDishes, GroupLayout.PREFERRED_SIZE, 134,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelTables.createSequentialGroup().addComponent(lblTotalToday)
										.addPreferredGap(ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
										.addComponent(lblShowTotalToday)))
						.addContainerGap()));
		gl_panelTables.setVerticalGroup(gl_panelTables.createParallelGroup(Alignment.LEADING).addGroup(gl_panelTables
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelTables.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneTables, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneDishes, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panelTables.createParallelGroup(Alignment.BASELINE).addComponent(lblTotalToday)
						.addComponent(lblShowTotalToday))
				.addContainerGap(16, Short.MAX_VALUE)));

		tblDishes = new JTable();
		tblDishes.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Dishes Ordered" })
		{
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		tblDishes.getColumnModel().getColumn(0).setResizable(false);
		tblDishes.getColumnModel().getColumn(0).setPreferredWidth(107);
		scrollPaneDishes.setViewportView(tblDishes);

		tblTables = new JTable();
		tblTables.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "No.", "Reserved by", "Total" })
		{
			Class[] columnTypes = new Class[] { Object.class, String.class, Object.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, true, false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		tblTables.getColumnModel().getColumn(0).setPreferredWidth(34);
		tblTables.getColumnModel().getColumn(1).setResizable(false);
		tblTables.getColumnModel().getColumn(1).setPreferredWidth(87);
		tblTables.getColumnModel().getColumn(2).setResizable(false);
		tblTables.getColumnModel().getColumn(2).setPreferredWidth(45);
		scrollPaneTables.setViewportView(tblTables);
		panelTables.setLayout(gl_panelTables);
		tblTables.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent event)
			{
				DefaultTableModel model = (DefaultTableModel) tblDishes.getModel();
				model.setRowCount(0);
				Object rowData[] = new Object[1];

				String tableNo = tblTables.getValueAt(tblTables.getSelectedRow(), 0).toString();
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

		JScrollPane scrollPaneUsers = new JScrollPane();

		tblUsers = new JTable();
		tblUsers.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Role" })
		{
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		tblUsers.getColumnModel().getColumn(0).setPreferredWidth(124);
		scrollPaneUsers.setViewportView(tblUsers);

		JPanel panelDisplayUsers = new JPanel();
		panelUsers.add(panelDisplayUsers, "1");

		panelUsers.setLayout(cl);

		cl.show(panelUsers, "1");

		GroupLayout gl_panelDisplayUsers = new GroupLayout(panelDisplayUsers);
		gl_panelDisplayUsers.setHorizontalGroup(gl_panelDisplayUsers.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelDisplayUsers.createSequentialGroup().addContainerGap(96, Short.MAX_VALUE)
						.addComponent(scrollPaneUsers, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
						.addGap(94)));
		gl_panelDisplayUsers.setVerticalGroup(gl_panelDisplayUsers.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelDisplayUsers.createSequentialGroup().addGap(23)
						.addComponent(scrollPaneUsers, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE).addGap(78)));
		panelDisplayUsers.setLayout(gl_panelDisplayUsers);
		contentPane.setLayout(gl_contentPane);
	}
}

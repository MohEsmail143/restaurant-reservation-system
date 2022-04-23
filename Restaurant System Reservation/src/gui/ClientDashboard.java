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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import tablesanddishes.RestDish;
import tablesanddishes.RestTable;
import users.Users;
import xml.MainXML;

public class ClientDashboard extends JFrame
{

	private JPanel contentPane;
	private JTextField txtNoOfSeats;
	private JTextArea txtAreaResDetails;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable tDishes;
	DefaultTableModel model;
	CardLayout cl = new CardLayout();

	private double tax = 0;
	private double total = 0;
	private static RestTable currentTable;
	private static Users currentUser;
	private static List<Users> usersList = new ArrayList<>();
	private static List<RestTable> tablesList = new ArrayList<>();
	private static List<RestDish> menuItems = new ArrayList<>();

	public ClientDashboard(Users currentUser, List<Users> usersList, List<RestTable> tablesList,
			List<RestDish> dishesList)
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientDashboard.class.getResource("/resources/icon.png")));
		this.currentUser = currentUser;
		this.usersList = usersList;
		this.tablesList = tablesList;
		this.menuItems = dishesList;
		currentTable = null;
		initComponents();

		model = new DefaultTableModel();
		tDishes.setModel(model);
		model.addColumn("Dish Name");
		model.addColumn("Price");
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClientDashboard frame = new ClientDashboard(currentUser, usersList, tablesList, menuItems);
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
		setTitle("Client");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 586, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(cl);

		JPanel dashboard = new JPanel();
		contentPane.add(dashboard, "1");

		JPanel chooseTable = new JPanel();
		contentPane.add(chooseTable, "2");

		JPanel chooseDishes = new JPanel();
		contentPane.add(chooseDishes, "3");

		cl.show(contentPane, "1");

		JLabel lblResDetails = new JLabel("Reservation Details");
		lblResDetails.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtAreaResDetails = new JTextArea();
		txtAreaResDetails.setLineWrap(true);
		txtAreaResDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txtAreaResDetails.setEditable(false);
		if (currentUser.getTable() == null)
		{
			txtAreaResDetails.setText("You have not made a reservation yet");
		} else
		{
			txtAreaResDetails.setText("Table No.: " + currentUser.getTable().getNumber() + "\n\n" + "Number of Seats: "
					+ currentUser.getTable().getNumberOfSeats() + "\n\n" + "Smoking: "
					+ currentUser.getTable().isSmoking() + "\n\n" + "Total= " + currentUser.getTable().getTotal()
					+ "\n\n" + "Ordered Dishes:\n");

			for (RestDish d : currentUser.getTable().getOrderedDishes())
			{
				txtAreaResDetails.append("- " + d.getName() + "\n");
			}
		}

		JLabel lblHello = new JLabel("Hello, " + currentUser.getName() + "!");
		lblHello.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblHello.setForeground(Color.RED);

		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				if (currentTable != null)
				{
					for (RestTable t : tablesList)
					{
						if (currentTable.getNumber() == t.getNumber())
						{
							t = currentTable;
							t.setReservedBy(currentUser.getName());
							currentUser.setTable(currentTable);
							break;
						}
					}
					for (Users u : usersList)
					{
						if (u.getName().equals(currentUser.getName()))
						{
							u = currentUser;
							break;
						}
					}
				}

				MainXML mainXML = new MainXML();
				try
				{
					MainXML.marshalRest(usersList, tablesList, menuItems);
				} catch (JAXBException | FileNotFoundException e1)
				{
					e1.printStackTrace();
				}

				LoginScreen logScreen = new LoginScreen(usersList, tablesList, menuItems);
				logScreen.setVisible(true);
				dispose();
			}
		});

		JButton btnReserve = new JButton("Reserve Table");
		btnReserve.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (currentUser.getTable() == null && currentTable == null)
					cl.show(contentPane, "2");
				else
					JOptionPane.showMessageDialog(rootPane, "You have already made a reservation");
			}
		});

		GroupLayout gl_dashboard = new GroupLayout(dashboard);
		gl_dashboard.setHorizontalGroup(gl_dashboard.createParallelGroup(Alignment.TRAILING).addGroup(gl_dashboard
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_dashboard.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_dashboard.createSequentialGroup()
								.addComponent(txtAreaResDetails, GroupLayout.PREFERRED_SIZE, 320,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
								.addGroup(gl_dashboard.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnLogOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnReserve, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
						.addComponent(lblHello).addComponent(lblResDetails))
				.addContainerGap()));
		gl_dashboard.setVerticalGroup(gl_dashboard.createParallelGroup(Alignment.TRAILING).addGroup(gl_dashboard
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_dashboard.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_dashboard.createSequentialGroup().addComponent(lblHello).addGap(18)
								.addComponent(lblResDetails).addPreferredGap(ComponentPlacement.RELATED).addComponent(
										txtAreaResDetails, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_dashboard.createSequentialGroup().addComponent(btnReserve)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnLogOut)))
				.addGap(162)));
		dashboard.setLayout(gl_dashboard);

		JLabel lblPreferences = new JLabel("Choose your preferences");

		JLabel lblNoOfSeats = new JLabel("Number of Seats");

		JLabel lblSmoking = new JLabel("Smoking");

		txtNoOfSeats = new JTextField();
		txtNoOfSeats.setColumns(10);

		JRadioButton yesSmoke = new JRadioButton("Yes");
		buttonGroup.add(yesSmoke);

		JRadioButton noSmoke = new JRadioButton("No");
		buttonGroup.add(noSmoke);

		JButton btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					byte ns = Byte.parseByte(txtNoOfSeats.getText());
					boolean smokingChoice = false;

					if (yesSmoke.isSelected())
						smokingChoice = true;
					else if (noSmoke.isSelected())
						smokingChoice = false;

					int i;
					for (i = 0; i < tablesList.size(); i++)
					{
						if (tablesList.get(i).getNumberOfSeats() == ns && tablesList.get(i).isSmoking() == smokingChoice
								&& tablesList.get(i).isAvailable() == true)
						{
							currentTable = tablesList.get(i);
							break;
						}
					}

					if (currentTable != null)
					{
						currentTable.setReservedBy(currentUser.getName());
						currentTable.setAvailable(false);
						JOptionPane.showMessageDialog(rootPane,
								"Table No. " + tablesList.get(i).getNumber() + " is available");

						cl.show(contentPane, "3");
					} else
					{
						JOptionPane.showMessageDialog(rootPane,
								"Sorry, but there are no available tables with your preferences");
					}
				} catch (NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(rootPane, "Please enter a number in the text box");
				}
			}
		});

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "1");
			}
		});

		GroupLayout groupLayout = new GroupLayout(chooseTable);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblPreferences)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblNoOfSeats)
										.addComponent(lblSmoking))
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addComponent(yesSmoke)
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(noSmoke))
										.addComponent(txtNoOfSeats, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(309, Short.MAX_VALUE)).addGroup(
						groupLayout.createSequentialGroup().addContainerGap(368, Short.MAX_VALUE).addComponent(btnBack)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnCheck).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap().addComponent(lblPreferences).addGap(41)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNoOfSeats).addComponent(
						txtNoOfSeats, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(13)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(yesSmoke)
						.addComponent(lblSmoking).addComponent(noSmoke))
				.addPreferredGap(ComponentPlacement.RELATED, 305, Short.MAX_VALUE).addGroup(groupLayout
						.createParallelGroup(Alignment.BASELINE).addComponent(btnCheck).addComponent(btnBack))
				.addContainerGap()));
		chooseTable.setLayout(groupLayout);

		JLabel lblDishesList = new JLabel("Menu");

		JComboBox cmboxDishes = new JComboBox();

		for (int i = 0; i < menuItems.size(); i++)
			cmboxDishes.addItem(menuItems.get(i).getName());

		cmboxDishes.setSelectedItem(null);

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblTax = new JLabel("Tax");

		JLabel lblTotal = new JLabel("Total");

		JLabel lblShowTax = new JLabel("");

		JLabel lblShowTotal = new JLabel("");

		JButton btnDeleteItem = new JButton("Delete Item");
		btnDeleteItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RestDish selectedDish = null;

				try
				{
					int r = tDishes.getSelectedRow();
					String dishName = (String) tDishes.getValueAt(r, 0);
					for (int i = 0; i < menuItems.size(); i++)
					{
						if (dishName.equals(menuItems.get(i).getName()))
						{
							selectedDish = menuItems.get(i);
							break;
						}
					}

					tax -= selectedDish.getPrice() * selectedDish.getTax();
					total -= selectedDish.getPrice() + selectedDish.getPrice() * selectedDish.getTax();

					lblShowTax.setText(String.valueOf(tax));
					lblShowTotal.setText(String.valueOf(total));

					model.removeRow(r);
				} catch (Exception e1)
				{
					JOptionPane.showMessageDialog(rootPane, "Please select an item from the table.");
				}

			}
		});

		JButton btnSaveRes = new JButton("Save Reservation");
		btnSaveRes.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				List<RestDish> orderedItems = new ArrayList<>();

				for (int i = 0; i < tDishes.getRowCount(); i++)
				{
					String dishName = (String) tDishes.getValueAt(i, 0);
					for (int j = 0; j < menuItems.size(); j++)
					{
						if (dishName.equals(menuItems.get(j).getName()))
						{
							orderedItems.add(menuItems.get(j));
							break;
						}
					}
				}
				currentTable.setOrderedDishes(orderedItems);
				currentTable.setTotal(total);
				JOptionPane.showMessageDialog(rootPane, "You have successfully made a reservation!");

				txtAreaResDetails.setText("Table No.: " + currentTable.getNumber() + "\n\n" + "Number of Seats: "
						+ currentTable.getNumberOfSeats() + "\n\n" + "Smoking: " + currentTable.isSmoking() + "\n\n"
						+ "Total= " + currentTable.getTotal() + "\n\n" + "Ordered Dishes:\n");

				for (RestDish d : currentTable.getOrderedDishes())
				{
					txtAreaResDetails.append("- " + d.getName() + "\n");
				}

				cl.show(contentPane, "1");
			}
		});

		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int i;
				String selectedCmbox = cmboxDishes.getSelectedItem().toString();
				RestDish selectedDish = null;

				try
				{
					for (i = 0; i < menuItems.size(); i++)
					{
						if (menuItems.get(i).getName().equals(selectedCmbox))
						{
							selectedDish = menuItems.get(i);
							break;
						}
					}

					model.addRow(new Object[] { selectedDish.getName(), selectedDish.getPrice(), selectedDish.getType(),
							selectedDish.getTax() });

					tax += selectedDish.getPrice() * selectedDish.getTax();
					total += selectedDish.getPrice() + selectedDish.getPrice() * selectedDish.getTax();

					lblShowTax.setText(String.valueOf(tax));
					lblShowTotal.setText(String.valueOf(total));
				} catch (Exception e1)
				{
					JOptionPane.showMessageDialog(rootPane, "Please select an item from the list.");
				}
			}
		});

		GroupLayout groupLayout_1 = new GroupLayout(chooseDishes);
		groupLayout_1.setHorizontalGroup(groupLayout_1.createParallelGroup(Alignment.LEADING).addGroup(groupLayout_1
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout_1.createSequentialGroup().addComponent(lblDishesList).addContainerGap(508,
								Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout_1.createSequentialGroup()
								.addGroup(groupLayout_1.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(cmboxDishes, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(groupLayout_1.createSequentialGroup().addComponent(btnDeleteItem)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAddItem))
										.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 333,
												Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout_1.createParallelGroup(Alignment.TRAILING)
										.addGroup(Alignment.LEADING,
												groupLayout_1.createSequentialGroup().addComponent(lblTotal)
														.addPreferredGap(ComponentPlacement.RELATED, 154,
																Short.MAX_VALUE)
														.addComponent(lblShowTotal))
										.addGroup(Alignment.LEADING,
												groupLayout_1.createSequentialGroup().addComponent(lblTax)
														.addPreferredGap(ComponentPlacement.RELATED, 162,
																Short.MAX_VALUE)
														.addComponent(lblShowTax))
										.addComponent(btnSaveRes))
								.addGap(16)))));
		groupLayout_1
				.setVerticalGroup(
						groupLayout_1.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout_1.createSequentialGroup().addContainerGap()
										.addComponent(lblDishesList).addGap(18)
										.addComponent(cmboxDishes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(43)
										.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout_1.createSequentialGroup().addGap(37)
														.addGroup(groupLayout_1.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblTax).addComponent(lblShowTax))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(groupLayout_1.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblTotal).addComponent(lblShowTotal)))
												.addGroup(groupLayout_1.createSequentialGroup().addGap(1)
														.addGroup(groupLayout_1.createParallelGroup(Alignment.TRAILING)
																.addComponent(btnAddItem).addComponent(btnDeleteItem))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(groupLayout_1.createParallelGroup(Alignment.TRAILING)
																.addComponent(btnSaveRes).addComponent(scrollPane,
																		GroupLayout.PREFERRED_SIZE, 234,
																		GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(293, Short.MAX_VALUE)));

		tDishes = new JTable();
		scrollPane.setViewportView(tDishes);
		chooseDishes.setLayout(groupLayout_1);

	}
}

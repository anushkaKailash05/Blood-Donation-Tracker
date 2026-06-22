package dbms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class BloodBankDashboard extends JFrame {
	private static final String URL = "jdbc:mysql://localhost:3306/blood_donation";
	private static final String USER = "root";
	private static final String PASS = "kiran@123";
	private Connection con;

	public BloodBankDashboard() {
		setTitle("� Blood Bank Management Dashboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(240, 248, 255));
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);
			System.out.println(" Connected to Database!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e.getMessage());
			System.exit(1);
		}
		JLabel Ɵtle = new JLabel("BLOOD BANK MANAGEMENT SYSTEM", JLabel.CENTER);
		Ɵtle.setFont(new Font("Segoe UI", Font.BOLD, 22));
		Ɵtle.setForeground(new Color(128, 0, 0));
		JButton insertBtn = new JButton(" Insert Data");
		JButton viewBtn = new JButton(" View Data");
		JButton updateBtn = new JButton(" Update Data");
		JButton deleteBtn = new JButton(" Delete Data");
		JButton queryBtn = new JButton(" Run Analysis & Reports");
		JButton exitBtn = new JButton(" Exit");
		JButton[] buttons = { insertBtn, viewBtn, updateBtn, deleteBtn, queryBtn, exitBtn };
		for (JButton b : buttons) {
			b.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			b.setFocusPainted(false);
			b.setBackground(new Color(220, 53, 69));
			b.setForeground(Color.WHITE);
			b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		JPanel centerPanel = new JPanel(new GridLayout(6, 1, 15, 15));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150));
		for (JButton b : buttons)
			centerPanel.add(b);
		add(Ɵtle, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		insertBtn.addActionListener(e -> showInsertDialog());
		viewBtn.addActionListener(e -> showViewDialog());
		updateBtn.addActionListener(e -> showUpdateDialog());
		deleteBtn.addActionListener(e -> showDeleteDialog());
		queryBtn.addActionListener(e -> showQueryDashboard());
		exitBtn.addActionListener(e -> System.exit(0));
	}

//  INSERT DATA (dynamic columns)
	private void showInsertDialog() {
		String table = chooseTable();
		if (table == null)
			return;
		try (Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM `" + table + "` LIMIT 1")) {
			ResultSetMetaData md = rs.getMetaData();
			int cols = md.getColumnCount();
			JPanel panel = new JPanel(new GridLayout(cols, 2, 10, 10));
			JTextField[] fields = new JTextField[cols];
			for (int i = 1; i <= cols; i++) {
				panel.add(new JLabel(md.getColumnName(i) + ":"));
				fields[i - 1] = new JTextField();
				panel.add(fields[i - 1]);
			}
			int opƟon = JOptionPane.showConfirmDialog(this, panel, "Insert into " + table, JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (opƟon == JOptionPane.OK_OPTION) {
				StringBuilder sql = new StringBuilder("INSERT INTO `" + table + "` VALUES (");
				for (int i = 0; i < cols; i++) {
					sql.append("?");
					if (i < cols - 1)
						sql.append(",");
				}
				sql.append(")");
				try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
					for (int i = 0; i < cols; i++)
						ps.setString(i + 1, fields[i].getText().trim());
					ps.executeUpdate();
					JOptionPane.showMessageDialog(this, " Record inserted successfully!");
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
		}
	}

//  VIEW TABLE
	private void showViewDialog() {
		String table = chooseTable();
		if (table == null)
			return;
		String sql = "SELECT * FROM `" + table + "`";
		try (Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery(sql)) {
			ResultSetMetaData md = rs.getMetaData();
			int cols = md.getColumnCount();
			String[] colNames = new String[cols];
			for (int i = 1; i <= cols; i++)
				colNames[i - 1] = md.getColumnName(i);
			rs.last();
			int rows = rs.getRow();
			rs.beforeFirst();
			String[][] data = new String[rows][cols];
			int r = 0;
			while (rs.next()) {
				for (int i = 1; i <= cols; i++)
					data[r][i - 1] = rs.getString(i);
				r++;
			}
			JTable tableDisplay = new JTable(data, colNames);
			JScrollPane scrollPane = new JScrollPane(tableDisplay);
			scrollPane.setPreferredSize(new Dimension(700, 400));
			JOptionPane.showMessageDialog(this, scrollPane, "Viewing " + table, JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
		}
	}

//  UPDATE DATA 
	private void showUpdateDialog() {
		String table = chooseTable();
		if (table == null)
			return;
		try (Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM `" + table + "` LIMIT 1")) {
			ResultSetMetaData md = rs.getMetaData();
			int cols = md.getColumnCount();
			ArrayList<String> colNames = new ArrayList<>();
			for (int i = 1; i <= cols; i++)
				colNames.add(md.getColumnName(i));
			String idCol = (String) JOptionPane.showInputDialog(this, "Select ID Column:", "Select Key Column",
					JOptionPane.QUESTION_MESSAGE, null, colNames.toArray(), colNames.get(0));
			if (idCol == null)
				return;
			String idVal = JOptionPane.showInputDialog(this, "Enter ID Value:");
			if (idVal == null)
				return;
			String colToUpdate = (String) JOptionPane.showInputDialog(this, "Select Column to Update:", "Select Column",
					JOptionPane.QUESTION_MESSAGE, null, colNames.toArray(), colNames.get(0));
			if (colToUpdate == null)
				return;
			String newVal = JOptionPane.showInputDialog(this, "Enter New Value:");
			if (newVal == null)
				return;
			String sql = "UPDATE `" + table + "` SET `" + colToUpdate + "`=? WHERE `" + idCol + "`=?";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, newVal);
				ps.setString(2, idVal);
				int updated = ps.executeUpdate();
				JOptionPane.showMessageDialog(this, updated > 0 ? " Record Updated!" : " No record found!");
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
		}
	}

// DELETE DATA 
	private void showDeleteDialog() {
		String table = chooseTable();
		if (table == null)
			return;
		try (Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM `" + table + "` LIMIT 1")) {
			ResultSetMetaData md = rs.getMetaData();
			int cols = md.getColumnCount();
			ArrayList<String> colNames = new ArrayList<>();
			for (int i = 1; i <= cols; i++)
				colNames.add(md.getColumnName(i));
			String idCol = (String) JOptionPane.showInputDialog(this, "Select ID Column:", "Select Key Column",
					JOptionPane.QUESTION_MESSAGE, null, colNames.toArray(), colNames.get(0));
			if (idCol == null)
				return;
			String idVal = JOptionPane.showInputDialog(this, "Enter Value to Match:");
			if (idVal == null)
				return;
			String sql = "DELETE FROM `" + table + "` WHERE `" + idCol + "`=?";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, idVal);
				int deleted = ps.executeUpdate();
				JOptionPane.showMessageDialog(this, deleted > 0 ? " Record Deleted!" : " No record found!");
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
		}
	}

//  QUERY DASHBOARD 
	private void showQueryDashboard() {
		JFrame queryFrame = new JFrame(" Run Analysis & Reports");
		queryFrame.setSize(600, 500);
		queryFrame.setLocationRelativeTo(this);
		queryFrame.setLayout(new BorderLayout());
		queryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel btnPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		JScrollPane scrollPane = new JScrollPane(btnPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		String[][] queries = {
				{ "Find all donors with blood group O+",
						"SELECT donor_name, contact_no, city FROM Donor WHERE blood_group='O+'" },
				{ "List all donations made in 2025",
						"SELECT donation_id, donor_id, donation_date, quantity FROM Donation WHERE YEAR(donation_date)=2025" },
				{ "Count total blood bags per blood bank",
						"SELECT b.name, COUNT(bg.bag_id) AS total_bags FROM BloodBank b LEFT JOIN BloodBag bg ON b.bloodbank_id=bg.bloodbank_id GROUP BY b.bloodbank_id" },
				{ "Find top 3 donors with highest donations",
						"SELECT d.donor_name, SUM(o.quantity) AS total FROM Donor d JOIN Donation o ON d.donor_id=o.donor_id GROUP BY d.donor_name ORDER BY total DESC LIMIT 3" },
				{ "Show pending requests older than 30 days",
						"SELECT request_id, status, request_date FROM Request WHERE status='Pending' AND request_date<DATE_SUB(CURDATE(), INTERVAL 30 DAY)" } };
		for (String[] q : queries) {
			JButton b = new JButton(q[0]);
			b.setBackground(new Color(255, 99, 71));
			b.setForeground(Color.WHITE);
			b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			b.addActionListener(e -> runQuery(q[1]));
			btnPanel.add(b);
		}
		queryFrame.add(new JLabel("Select a report to view:", JLabel.CENTER), BorderLayout.NORTH);
		queryFrame.add(scrollPane, BorderLayout.CENTER);
		queryFrame.setVisible(true);
	}

// Run query and show JTable
	private void runQuery(String sql) {
		try (Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery(sql)) {
			ResultSetMetaData md = rs.getMetaData();
			int cols = md.getColumnCount();
			String[] colNames = new String[cols];
			for (int i = 1; i <= cols; i++)
				colNames[i - 1] = md.getColumnName(i);
			rs.last();
			int rows = rs.getRow();
			rs.beforeFirst();
			String[][] data = new String[rows][cols];
			int r = 0;
			while (rs.next()) {
				for (int i = 1; i <= cols; i++)
					data[r][i - 1] = rs.getString(i);
				r++;
			}
			JTable table = new JTable(data, colNames);
			JScrollPane scroll = new JScrollPane(table);
			scroll.setPreferredSize(new Dimension(600, 400));
			JOptionPane.showMessageDialog(this, scroll, "Query Results", JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage());
		}
	}

//  Helper — Table chooser
	private String chooseTable() {
		String[] tables = { "Hospital", "Donor", "DonaƟonCamp", "DonaƟon", "BloodBank", "BloodBag", "Recipient",
				"Request" };
		return (String) JOptionPane.showInputDialog(this, "Select Table:", "Choose Table", JOptionPane.QUESTION_MESSAGE,
				null, tables, tables[0]);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new BloodBankDashboard().setVisible(true));
	}
}

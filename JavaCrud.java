import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTextField txtbid;

	/*
	String url = "jdbc:mysql://localhost:3306/javacrud";
	String user = "root";
	String password = "root";
	*/
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
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
	
	public JavaCrud() {
		initialize();
		
		//call METHOD
		Connect();
		table_load();
	}
	
	//Declare a variable
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	private JTable table;
	 
	
	//CONNECTING START
	
	public void Connect()
	    {
	        try 
	        {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javacrud", "root","root");
	        }
	        catch (ClassNotFoundException ex)
	        {
	          ex.printStackTrace();
	        }
	        catch (SQLException ex)
	        {
	            ex.printStackTrace();
	        }
	 
	    }

	//CONNECTING END
	
	
	
	
	
	//TABLE SHOW START
	public void table_load()
    {
    	try 
    	{
    		pst = con.prepareStatement("select * from book");
    		rs = pst.executeQuery();
    		table.setModel(DbUtils.resultSetToTableModel(rs));
    	}	 
    	catch (SQLException e) 
    	 {
    		e.printStackTrace();
    	 } 
    }
	
	////TABLE SHOW END
	
	
	/**
	 * Initialize the contents of the frame.
	*/
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 744, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setBounds(307, 32, 97, 19);
		lblNewLabel.setFont(new Font("Superspace Bold", Font.BOLD, 20));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(44, 98, 289, 189);
		panel.setBorder(new TitledBorder(null, "Registation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setBounds(35, 37, 65, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setBounds(35, 76, 65, 13);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setBounds(35, 115, 65, 13);
		panel.add(lblNewLabel_1_1_1);
		
		txtbname = new JTextField();
		txtbname.setBounds(118, 34, 96, 19);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(118, 73, 96, 19);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(118, 112, 96, 19);
		panel.add(txtprice);
		
		
		
		
		//Save START
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBounds(47, 305, 85, 32);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String bname,edition,price;
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				
				try 
				{
					pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
				          
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}
				 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		//Save END
		
		
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(152, 305, 85, 32);
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(248, 305, 85, 32);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnClear);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(44, 358, 289, 94);
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Book Id");
		lblNewLabel_1_1_2.setBounds(34, 37, 52, 13);
		panel_1.add(lblNewLabel_1_1_2);
		
		
		
		//SEARCH START
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) 
			{
				/*
				try 
				{
			          
		            String id = txtbid.getText();
		 
		            pst = con.prepareStatement("select name,edition,price from book where id = ?");
		            pst.setString(1, id);
		            ResultSet rs = pst.executeQuery();
		 
		            if(rs.next()==true)
		            {
		              
		                String name = rs.getString(1);
		                String edition = rs.getString(2);
		                String price = rs.getString(3);
		                
		                txtbname.setText(name);
		                txtedition.setText(edition);
		                txtprice.setText(price); 
		            }  
		            
		            else
		            {
		             txtbname.setText("");
		             txtedition.setText("");
		             txtprice.setText(""); 
		            }
		        }
				catch (SQLException ex) 
				{
		          
		        }
		        */
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            try {
		                String id = txtbid.getText();
		                pst = con.prepareStatement("select * from book where id=?");
		                pst.setString(1, id);
		                rs = pst.executeQuery();
		                if (rs.next()) {
		                    String bname = rs.getString("name");
		                    String edition = rs.getString("edition");
		                    String price = rs.getString("price");
		                    txtbname.setText(bname);
		                    txtedition.setText(edition);
		                    txtprice.setText(price);
		                } 
		                else 
		                {
		                    JOptionPane.showMessageDialog(null, "No book found with id " + id);
		                }
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }
				}
			}
			
		});
		
		//SEARCH END
		
		
		
		
		txtbid.setBounds(96, 34, 96, 19);
		txtbid.setColumns(10);
		panel_1.add(txtbid);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				try {
		            String id = txtbid.getText();
		            pst = con.prepareStatement("select * from book where id=?");
		            pst.setString(1, id);
		            rs = pst.executeQuery();
		            if (rs.next()) {
		                String bname = rs.getString("name");
		                String edition = rs.getString("edition");
		                String price = rs.getString("price");
		                txtbname.setText(bname);
		                txtedition.setText(edition);
		                txtprice.setText(price);
		            } else {
		                JOptionPane.showMessageDialog(null, "No book found with id " + id);
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }try {
		            String id = txtbid.getText();
		            pst = con.prepareStatement("select * from book where id=?");
		            pst.setString(1, id);
		            rs = pst.executeQuery();
		            if (rs.next()) {
		                String bname = rs.getString("name");
		                String edition = rs.getString("edition");
		                String price = rs.getString("price");
		                txtbname.setText(bname);
		                txtedition.setText(edition);
		                txtprice.setText(price);
		            } else {
		                JOptionPane.showMessageDialog(null, "No book found with id " + id);
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
			}
		});
		
		
		
		btnSearch.setBounds(179, 63, 85, 21);
		panel_1.add(btnSearch);
		
		
		
		
		//Update START
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String bname,edition,price,bid;
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid  = txtbid.getText();
				try 
				{
					pst = con.prepareStatement("update book set name= ?,edition=?,price=? where id =?");
					pst.setString(1, bname);
					pst.setString(2, edition);
				    pst.setString(3, price);
				    pst.setString(4, bid);
				    pst.executeUpdate();
				    JOptionPane.showMessageDialog(null, "Record Update!");
				    table_load();
				          
				    txtbname.setText("");
				    txtedition.setText("");
				    txtprice.setText("");
				    txtbname.requestFocus();
				}
				 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
			}
		});
		
		//Update END
		
		
		
		
		
		
		btnUpdate.setBounds(418, 400, 85, 32);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnUpdate);
		
		
		//Delete START
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{

                String bid;
                bid  = txtbid.getText();
                
                try 
                {
                	pst = con.prepareStatement("delete from book where id =?");
                	pst.setString(1, bid);
                	pst.executeUpdate();
                	JOptionPane.showMessageDialog(null, "Record Deleted!");
                	table_load();
  
                	txtbname.setText("");
                	txtedition.setText("");
                	txtprice.setText("");
                	txtbname.requestFocus();
                }

                catch (SQLException e1) 
                {
                	e1.printStackTrace();
                }
			}
		});
		
		//Delete END
		
		
		
		
		btnDelete.setBounds(552, 400, 85, 32);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(389, 96, 310, 267);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}

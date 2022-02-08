package pms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;


 public class Login {
	    int id;
		String name;
		String desig;
		float salary;

	public static void main(String[] args) throws Exception {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			// login screen
			System.out.println("Enter User Name:");
			String username = br.readLine();
			System.out.println("Enter Password:");
			String password = br.readLine();
			if ("admin".equals(username) && "admin123".equals(password)) {
				System.out.println("Enter your Role:");
				System.out.println("1.Administrator\n2.Employee");
				int role = Integer.parseInt(br.readLine());
				switch (role) {
				case 1:
					adminlogin();
					break;
				case 2:
					employeeLogin();
					break;
				}
				
			} else {
				System.out.println("Invalid Username 'or' Password !!");
			}
			
			br.close();}
	}

	private static void employeeLogin() {
		// TODO Auto-generated method stub

	}

	public static void adminlogin() throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int response;
		do {
			System.out.println("\nMenu for Administrator");
			System.out.println(
					"1.Add Employee\n2.Search Employee\n3.Delete Employee\n4.View Employees List\n5.Manage Payroll\n6.Generate Payslip\n7.saiRecord Employee Attendance\n8.Logout");
			response = Integer.parseInt(br.readLine());
			switch (response) {
			case 1:
				addemployee();
				break;
			case 2:
				searchemployee();
				break;
			case 3:
				deleteemployee();
				break;
			case 4:
				viewemployees();
				break;
			case 5:
				managepayroll();
				break;
			case 6:
				generatepayslip();
				break;
			case 7:
				recordattendance();
				break;
			case 8:
				System.out.println("----Logged out Succesfully----");
				break;
				default :
					System.out.println("Invalid Selection...!");
					break;
			}
		} while (response != 8);
	}

	private static void addemployee() throws Exception {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");   
	        String ename,edesig,eaddress,eemail;
	        int empId=0,phone=0;
	        float salary=0;
			
			System.out.println("Enter empoloyee Id:");
			empId=Integer.parseInt(br.readLine());
			System.out.println("ENter Employee name:");
			ename=br.readLine();
			System.out.println("Enter Employee Designtion :");
			edesig=br.readLine();
			System.out.println("Enter Employee Salary:");
			salary=Float.parseFloat(br.readLine());
			System.out.println("Enter Employee Address:");
			eaddress=br.readLine();
			System.out.println("Enter Employee Email:");
			eemail=br.readLine();
			System.out.println("Enter Contact Num:");
			phone=Integer.parseInt(br.readLine());
			
			

			
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pms","root","40540633@Sai");
			
			PreparedStatement stmt=con.prepareStatement("Insert into employee Values(?,?,?,?,?,?,?)");

		    stmt.setInt(1, empId);
		    stmt.setString(2, ename);
		    stmt.setString(3, edesig);
		    stmt.setFloat(4, salary);
		    stmt.setString(5, eaddress);
		    stmt.setString(6, eemail);
		    stmt.setInt(7, phone);
		    
		   
		    int i=stmt.executeUpdate();
		    System.out.println("Employee Details are Added Successfully......");
		   
		    
	}catch(Exception e) {System.out.println(e);
	}
	}

	private static void searchemployee() throws Exception,IOException {
		// TODO Auto-generated method stub
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");   
	        String ename,edesig,eaddress,eemail;
	        int empId=0,phone=0;
	        float salary=0;
	        
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pms","root","40540633@Sai");
			System.out.println("Connection Established");
			System.out.println("Enter Employee ID:");
			empId=Integer.parseInt(br.readLine());
			PreparedStatement stmt=con.prepareStatement("select * from employee where emp_id = "+empId+"");
		
			ResultSet rs = stmt.executeQuery();
			
			
			System.out.println("--------Your Search Result--------");
			System.out.println("Emp Id  Emo_Name  Emp_Desig       Emp_Salary    Emp_Address   Emp_Email       Emp_Phone");
			while(rs.next()) 
			{	
				System.out.printf(rs.getInt(1)+"        "+rs.getString(2)+"       "+rs.getString(3)+"      "+rs.getFloat(4)+"      "+rs.getString(5)+"     "+rs.getString(6)+"     "+rs.getInt(7));
			}   
		 
		 
		    
	}catch(Exception e) {System.out.println(e);
	}

	}

	private static void deleteemployee() {
		// TODO Auto-generated method stub

	}

	private static void viewemployees() {
		

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");   
        String ename,edesig,eaddress,eemail;
        int empId=0,phone=0;
        float salary=0;
		
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pms","root","40540633@Sai");
		
		PreparedStatement stmt=con.prepareStatement("select * from employee");
		
		ResultSet rs = stmt.executeQuery();
		System.out.println("Emp Id  Emo_Name  Emp_Desig       Emp_Salary    Emp_Address   Emp_Email       Emp_Phone");
		while(rs.next()) 
			
			System.out.println(rs.getInt(1)+"        "+rs.getString(2)+"       "+rs.getString(3)+"      "+rs.getFloat(4)+"      "+rs.getString(5)+"     "+rs.getString(6)+"     "+rs.getInt(7));
		    
	 
	   
	    int i=stmt.executeUpdate();
	    System.out.println("Employees list retrieved Successfully......");
	   
	    
}catch(Exception e) {System.out.println(e);
}
		// TODO Auto-generated method stub

	}

	public static void managepayroll()  throws IOException {
		

			MPayroll marray[] = new MPayroll[6];
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			MPayroll m = new MPayroll(0, 0, 0, 0, 0, 0);
			System.out.println("Enter Employee ID:");
			try { m.setEmployeeId(Integer.parseInt(br.readLine()));
			}catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Enter Basic Pay:");
			try { m.setBasicpay(Float.parseFloat(br.readLine()));
			}catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Enter House Rent Allowance:");
			try { m.setHra(Float.parseFloat(br.readLine()));
			}catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Enter Special Allowance:");
			try {
				m.setSp_allowance(Float.parseFloat(br.readLine()));
			}catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Enter Bonus:");
			try {
				m.setBonus(Float.parseFloat(br.readLine()));
			}catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Enter Provident Fund:");
			try {
				m.setPf_amnt(Float.parseFloat(br.readLine()));
			}catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("EmployeeID     :  " + m.getEmployeeId());
			System.out.println("Basic_Pay      :  " + m.getBasicpay());
			System.out.println("HRA            :  " + m.getHra());
			System.out.println("Spcl_Allowance :  " + m.getSp_allowance());
			System.out.println("Bonus          :  " + m.getBonus());
			System.out.println("Provident Fund :  " + m.getPf_amnt());

			

			System.out.print("------------------------------------------------------------"
					       + "|   ***Above Payroll details are updated Successfully***    |"
					       + " -----------------------------------------------------------");                                                                                              

		}
	

	

	private static void generatepayslip() {
		// TODO Auto-generated method stub

	}

	private static void recordattendance() {
		// TODO Auto-generated method stub

	}
 }
 
 

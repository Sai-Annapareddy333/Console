package pms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

	public static void main(String[] args) throws Exception {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			// login screen
			System.out.println("****************************************************");
			System.out.println("|------------PAYROLL-MANAGEMENT-SYSTEM-------------|");
			System.out.println("****************************************************");
			System.out.println("Enter User Name:");
			String username = br.readLine();
			System.out.println("Enter Password:");
			String password = br.readLine();
			if ("user".equals(username) && "user123".equals(password)) {
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
				default:
					System.out.println("!!!!Please Enter a Valid Option!!! ");
					break;
				}

			} else {
				System.out.println("Invalid Username 'or' Password !!");
				System.out.println("!!!!Please Enter a Valid Details!!! ");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static void employeeLogin() throws Exception, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter User Name:");
		String username = br.readLine();
		System.out.println("Enter Password:");
		String password = br.readLine();
		if ("employee".equals(username) && "employee123".equals(password))

		{
			int response1;
			do {
				System.out.println("\n                         ");
				System.out.println("|-------------------------|");
				System.out.println("| Menu for Employee       | ");
				System.out.println("|-------------------------|");
				System.out.println("1.View Profile \n2.View Working days and Working Hours\n3.View Payslip\n4.Logout");
				System.out.println("****Select an Option to Proceed****");
				response1 = Integer.parseInt(br.readLine());
				switch (response1) {
				case 1:
					viewprofile();
					break;
				case 2:
					viewattendance();
					break;
				case 3:
					viewpayslip();
					break;
				case 4:
					System.out.println("----Logged out Succesfully----");
					break;
				default:
					System.out.println("Invalid Selection...!");
					break;
				}
			} while (response1 != 4);
		} else {
			System.out.println("Invalid Username 'or' Password !!");
			System.out.println("!!!!Please Enter a Valid Details!!! ");
		}

	}

	private static void viewpayslip() {
		// TODO Auto-generated method stub
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");

			int EmpId = 0;
			String Emp_Dept;
			float Basic_pay, Hra, Da, Bonus, Pf;

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");
			System.out.println("Connection Established");
			System.out.println("Enter your Employee ID to View Payslip:");
			EmpId = Integer.parseInt(br.readLine());

			PreparedStatement stmt1 = con.prepareStatement(
					"update managepayroll set Gross=(basic_pay + hra + da + bonus) -pf where emp_id=" + EmpId + "");
			PreparedStatement stmt2 = con.prepareStatement("select * from managepayroll where emp_id=" + EmpId + "");
			stmt1.executeLargeUpdate();
			System.out.println("*************************************************");
			System.out.println("|------------PAY SLIP FOR EMPLOYEE--------------|");
			System.out.println("************************************************");
			ResultSet rs = stmt2.executeQuery("select * from managepayroll where emp_id=" + EmpId + "");

			while (rs.next()) {
				System.out.printf("\nEmployeee ID           :" + rs.getInt(1));
				System.out.printf("\nEmployeee Department   :" + rs.getString(2));
				System.out.printf("\nEmployeee Basic Pay    :" + rs.getFloat(3));
				System.out.printf("\nEmployeee HRA          :" + rs.getFloat(4));
				System.out.printf("\nEmployeee DA           :" + rs.getFloat(5));
				System.out.printf("\nEmployeee Bonus        :" + rs.getFloat(6));
				System.out.printf("\nEmployeee PF           :" + rs.getFloat(7));
				System.out.printf("\nEmployeee Net Payable  :" + rs.getFloat(8));
				System.out.println("(In Hand Amount)");
				System.out.println("\n**************************************************");
				System.out.println("|------------******FSS-Corp*******---------------|");
				System.out.println("**************************************************");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static void viewattendance() {
		// TODO Auto-generated method stub

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");
			float emp_wd, emp_wh;
			int emp_id = 0;
			System.out.println("Enter Employee Id :");
			emp_id = Integer.parseInt(br.readLine());

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");

			PreparedStatement stmt = con.prepareStatement("select * from empatendance where emp_id=" + emp_id + "");

			ResultSet rs = stmt.executeQuery();

			System.out.println("Emp Id     Emp_Working Hours  Emp_Working Days    Leaves_Taken");
			while (rs.next())

				System.out.println(rs.getInt(1) + "          " + rs.getFloat(2) + "               " + rs.getFloat(3)
						+ "                 " + rs.getFloat(4) + "");

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static void viewprofile() {
		// TODO Auto-generated method stub

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String ename, edesig, eaddress, eemail;
			int empId = 0, phone = 0;
			float salary = 0;

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");
			System.out.println("Connection Established");
			System.out.println("Enter your Employee ID:");
			empId = Integer.parseInt(br.readLine());
			PreparedStatement stmt = con.prepareStatement("select * from employee where emp_id = " + empId + "");

			ResultSet rs = stmt.executeQuery();

			System.out.println("|---------------Employee Profile-----------------|");
			System.out
					.println("Emp Id  Emo_Name  Emp_Desig       Emp_Salary    Emp_Address   Emp_Email       Emp_Phone");
			while (rs.next()) {
				System.out.printf(rs.getInt(1) + "        " + rs.getString(2) + "       " + rs.getString(3) + "      "
						+ rs.getFloat(4) + "      " + rs.getString(5) + "     " + rs.getString(6) + "     "
						+ rs.getInt(7));
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void adminlogin() throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter User Name:");
		String username = br.readLine();
		System.out.println("Enter Password:");
		String password = br.readLine();
		if ("admin".equals(username) && "admin123".equals(password))

		{
			int response;
			do {
				System.out.println("\n                         ");
				System.out.println("|-------------------------|");
				System.out.println("| Menu for Administrator  | ");
				System.out.println("|-------------------------|");
				System.out.println(
						"1.Add Employee\n2.Search Employee\n3.Delete Employee\n4.View Employees List\n5.Manage Payroll\n6.Generate Payslip\n7.saiRecord Employee Attendance\n8.Logout");
				System.out.println("****Select an Option to Proceed****");
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
				default:
					System.out.println("Invalid Selection...!");
					break;
				}
			} while (response != 8);
		} else {
			System.out.println("Invalid Username 'or' Password !!");
			System.out.println("!!!!Please Enter a Valid Details!!! ");
		}
	}

	private static void addemployee() throws Exception {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String ename, edesig, eaddress, eemail;
			int empId = 0, phone = 0;
			float salary = 0;

			System.out.println("Enter empoloyee Id:");
			empId = Integer.parseInt(br.readLine());
			System.out.println("ENter Employee name:");
			ename = br.readLine();
			System.out.println("Enter Employee Designtion :");
			edesig = br.readLine();
			System.out.println("Enter Employee Salary:");
			salary = Float.parseFloat(br.readLine());
			System.out.println("Enter Employee Address:");
			eaddress = br.readLine();
			System.out.println("Enter Employee Email:");
			eemail = br.readLine();
			System.out.println("Enter Contact Num:");
			phone = Integer.parseInt(br.readLine());

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");

			PreparedStatement stmt = con.prepareStatement("Insert into employee Values(?,?,?,?,?,?,?)");

			stmt.setInt(1, empId);
			stmt.setString(2, ename);
			stmt.setString(3, edesig);
			stmt.setFloat(4, salary);
			stmt.setString(5, eaddress);
			stmt.setString(6, eemail);
			stmt.setInt(7, phone);

			int i = stmt.executeUpdate();
			System.out.println("Employee Details are Added Successfully......");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void searchemployee() throws Exception, IOException {
		// TODO Auto-generated method stub
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String ename, edesig, eaddress, eemail;
			int empId = 0, phone = 0;
			float salary = 0;

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");
			System.out.println("Connection Established");
			System.out.println("Enter Employee ID:");
			empId = Integer.parseInt(br.readLine());
			PreparedStatement stmt = con.prepareStatement("select * from employee where emp_id = " + empId + "");

			ResultSet rs = stmt.executeQuery();

			System.out.println("|---------------Your Search Result-----------------|");
			System.out
					.println("Emp Id  Emo_Name  Emp_Desig       Emp_Salary    Emp_Address   Emp_Email       Emp_Phone");
			while (rs.next()) {
				System.out.printf(rs.getInt(1) + "        " + rs.getString(2) + "       " + rs.getString(3) + "      "
						+ rs.getFloat(4) + "      " + rs.getString(5) + "     " + rs.getString(6) + "     "
						+ rs.getInt(7));
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static void deleteemployee() {
		// TODO Auto-generated method stub

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");

			int empId = 0;

			System.out.println("Enter empoloyee Id:");
			empId = Integer.parseInt(br.readLine());

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");
			PreparedStatement stmt = con.prepareStatement("delete from employee where emp_id=" + empId + "");

			int i = stmt.executeUpdate();
			System.out.println("Employee Record Deleted Successfully......");

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static void viewemployees() {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String ename, edesig, eaddress, eemail;
			int empId = 0, phone = 0;
			float salary = 0;

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");

			PreparedStatement stmt = con.prepareStatement("select * from employee");

			ResultSet rs = stmt.executeQuery();
			System.out.println("|---------------List of all Employee's-----------------|");
			System.out
					.println("Emp Id  Emo_Name  Emp_Desig       Emp_Salary    Emp_Address   Emp_Email       Emp_Phone");
			while (rs.next())

				System.out.println(rs.getInt(1) + "        " + rs.getString(2) + "       " + rs.getString(3) + "      "
						+ rs.getFloat(4) + "      " + rs.getString(5) + "     " + rs.getString(6) + "     "
						+ rs.getInt(7));

			System.out.println("Employees list retrieved Successfully......");

		} catch (Exception e) {
			System.out.println(e);
		}
		// TODO Auto-generated method stub

	}

	public static void managepayroll() throws IOException {

		MPayroll marray[] = new MPayroll[6];
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		MPayroll m = new MPayroll(0, 0, 0, 0, 0, 0);
		System.out.println("Enter Employee ID:");
		try {
			m.setEmployeeId(Integer.parseInt(br.readLine()));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter Basic Pay:");
		try {
			m.setBasicpay(Float.parseFloat(br.readLine()));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter House Rent Allowance:");
		try {
			m.setHra(Float.parseFloat(br.readLine()));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter Special Allowance:");
		try {
			m.setSp_allowance(Float.parseFloat(br.readLine()));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter Bonus:");
		try {
			m.setBonus(Float.parseFloat(br.readLine()));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter Provident Fund:");
		try {
			m.setPf_amnt(Float.parseFloat(br.readLine()));
		} catch (NumberFormatException | IOException e) {
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
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");

			int EmpId = 0;
			String Emp_Dept;
			float Basic_pay, Hra, Da, Bonus, Pf;

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");
			System.out.println("Connection Established");
			System.out.println("Enter Employee ID to Generate Payslip:");
			EmpId = Integer.parseInt(br.readLine());

			PreparedStatement stmt1 = con.prepareStatement(
					"update managepayroll set Gross=(basic_pay + hra + da + bonus) -pf where emp_id=" + EmpId + "");
			PreparedStatement stmt2 = con.prepareStatement("select * from managepayroll where emp_id=" + EmpId + "");
			stmt1.executeLargeUpdate();
			System.out.println("*************************************************");
			System.out.println("|------------PAY SLIP FOR EMPLOYEE--------------|");
			System.out.println("*************************************************");
			ResultSet rs = stmt2.executeQuery("select * from managepayroll where emp_id=" + EmpId + "");

			while (rs.next()) {
				System.out.printf("\nEmployeee ID           :" + rs.getInt(1));
				System.out.printf("\nEmployeee Department   :" + rs.getString(2));
				System.out.printf("\nEmployeee Basic Pay    :" + rs.getFloat(3));
				System.out.printf("\nEmployeee HRA          :" + rs.getFloat(4));
				System.out.printf("\nEmployeee DA           :" + rs.getFloat(5));
				System.out.printf("\nEmployeee Bonus        :" + rs.getFloat(6));
				System.out.printf("\nEmployeee PF           :" + rs.getFloat(7));
				System.out.printf("\nEmployeee Net Payable  :" + rs.getFloat(8));
				System.out.println("(In Hand Amount)");
				System.out.println("\n**************************************************");
				System.out.println("|------------******FSS-Corp*******---------------|");
				System.out.println("**************************************************");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static void recordattendance() {
		// TODO Auto-generated method stub
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			Class.forName("com.mysql.cj.jdbc.Driver");
			float emp_wd, emp_wh, n_leaves = 0;
			int emp_id = 0;
			System.out.println("Enter Employee Id :");
			emp_id = Integer.parseInt(br.readLine());
			System.out.println("Enter no of Hours Employee Worked :");
			emp_wh = Float.parseFloat(br.readLine());
			System.out.println("Enter no of Days Employee Worked:");
			emp_wd = Float.parseFloat(br.readLine());
			System.out.println("Enter no.of Leaves taken :");
			n_leaves = Float.parseFloat(br.readLine());

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pms", "root", "40540633@Sai");

			PreparedStatement stmt = con.prepareStatement("insert into empatendance values(?,?,?,?)");

			stmt.setInt(1, emp_id);
			stmt.setFloat(2, emp_wh);
			stmt.setFloat(3, emp_wd);
			stmt.setFloat(4, n_leaves);

			int i = stmt.executeUpdate();

			System.out.println("|--------Employee Attendance Details Recorded Successfully--------|");

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}

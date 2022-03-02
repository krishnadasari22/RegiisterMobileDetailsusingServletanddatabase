package com.mobiledetails;
import java.io.*;  
import java.sql.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
  
@SuppressWarnings("serial")
public class MobileDetails extends HttpServlet
{  
     public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
     {  
          response.setContentType("text/html");  
          PrintWriter out = response.getWriter();  
          String model = request.getParameter("model");  
          int price = Integer.parseInt(request.getParameter("price"));
          String company = request.getParameter("company");  
          String color = request.getParameter("color");
          
          try
          {  
               //load the driver
               Class.forName("oracle.jdbc.driver.OracleDriver");  
               //create connection object
               Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306","root","abcd");  
               //create the prepared statement object
               PreparedStatement ps=con.prepareStatement("insert into MobileDetails values(?,?,?,?)");  
  
               ps.setString(1,model);  
               ps.setInt(2,price);  
               ps.setString(3,company);  
               ps.setString(4, color);
  
               int i = ps.executeUpdate();  
               if(i>0)  
               out.print("<font color='green' size='4'>Record inserted successfully...</font>");

               //create the statement object
               Statement stmt = con.createStatement();
               String sql;
               sql = "SELECT * FROM MobileDetails";
               ResultSet rs = stmt.executeQuery(sql);
               out.println("<table border=1 >");
               out.println("<caption><h2>Mobile Details</h2></caption>");

               out.println("<tr style='background-color:#ffffb3; color:red'>");
               out.println("<th>Model Id</th>");
               out.println("<th>Price(Rs.)</th>");
               out.println("<th>Company</th>");
               out.println("<th>Color</th>");
               out.println("</tr>");
               // Extract data from result set
               while(rs.next())
               {
                    //Retrieve by column name
                    String mModel = rs.getString("model");
                    int mPrice = rs.getInt("price");
                    String mCompany = rs.getString("company");
                    String mColor = rs.getString("color");

                    //Display values
                    out.println("<tr style='background-color:#b3ffd9;'>");
                    out.println("<td>" + mModel + "</td>");
                    out.println("<td>" + mPrice + "</td>");
                    out.println("<td>" + mCompany + "</td>");
                    out.println("<td>" + mColor + "</td>");
                    out.println("</tr>");
               }
               out.println("</table>");
               out.println("<a href='register.html'>Home</a>");

               // Clean-up environment
               rs.close();
               stmt.close();
               con.close();
          }
          catch (Exception ex)
          {
               ex.printStackTrace();
          }  
          out.close();  
     }  
}

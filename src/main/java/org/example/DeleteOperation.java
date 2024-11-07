package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.*;

public class DeleteOperation implements RequestHandler<String , String> {
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

    private Connection getConnected() throws SQLException{
        return DriverManager.getConnection(DB_URL , USERNAME , PASSWORD);
    }

    public void getTable(Connection con){
        String query = "select * from employee;";
       try{
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(query);
           while (rs.next()){
               int id = rs.getInt("id");
               String name = rs.getString("name");
               String department = rs.getString("dept");
               double salary = rs.getDouble("salary");
               System.out.println("-------------------------------");
               System.out.println("id - " + id);
               System.out.println("name - " + name);
               System.out.println("dept - " + department);
               System.out.println("salary - " + salary);
           }
           st.close();
       }
       catch (SQLException e){
           System.out.println(e.getMessage());
       }
    }
    @Override
    public String handleRequest(String s, Context context) {
        try{
            Connection con = getConnected();
            PreparedStatement pstmt = con.prepareStatement(s);
            pstmt.setInt(1,5);
            int rows_affected = pstmt.executeUpdate();
            System.out.println(rows_affected + " rows affected.");
            getTable(con);
            pstmt.close();
            con.close();
        }
        catch(SQLException e){
            return e.getMessage();
        }
        return "";
    }
}

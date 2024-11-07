package org.example;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.*;

public class CrudOperationRds implements RequestHandler<String , String> {
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL , USERNAME , PASSWORD);
    }

    @Override
    public String handleRequest(String s, Context context) {
        String sql = "select * from employee;";
        try{
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("dept");
                Double salary = rs.getDouble("salary");

                System.out.println("id: " + id);
                System.out.println("name: " + name);
                System.out.println("dept: " + dept);
                System.out.println("salary: " + salary);
                System.out.println();
                System.out.println("--------------------------------------------------");
            }
        } catch (SQLException e) {
            return "Error" + e.getMessage();
        }
        return "";
    }

}

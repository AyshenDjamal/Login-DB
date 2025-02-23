package org.example;

import java.sql.*;

// Database Connect
public class DBConnection {
    private static String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static String NAME = "postgres";
    private static String PASSWORD = "Admin";

    public static Connection connect(){
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        }catch(SQLException e){
            System.out.println("Xeta: "+ e.getMessage());
        }
        return conn;
    }


    // Datalar Elave Edilir
    public static void insertData(int id, String full_name, String email, String password){
        String sql = "INSERT INTO login_users(id, full_name, email, password) VALUES (?, ?, ?, ?)";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql) // SQL kodu run etmek ucun Statement yaradiram
        ){
            pstmt.setInt(1, id);
            pstmt.setString(2, full_name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.executeUpdate();
            System.out.println("Ugurla Qeydiyyatdan kecdiniz!");
        }catch (SQLException e){
            System.out.println("User Movcuddur ve ya Diger Sistem Xetasi: "+e.getMessage());
        }
    }


    //Data Oxunur
    public static void getUsers(){
        String sql = "SELECT * FROM login_users";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
        ){
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                System.out.println("ID: "+id+" , Name: "+name+" , Email: "+email+" , Password: "+password);
            }

        }catch (SQLException e){
            System.out.println("Oxuma Xetasi: "+e.getMessage());
        }
    }


    //Update User
    public static void updateUser(int id, String full_name, String email, String paswword) {
        String sql = "UPDATE login_users SET full_name =?, email = ?, password = ? WHERE id = ?";

        try (
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, full_name);
            pstmt.setString(2, email);
            pstmt.setString(3, paswword);
            pstmt.setInt(4, id);

            int rowsCount = pstmt.executeUpdate();

            if (rowsCount > 0) {
                System.out.println("User Melumatlari Ugurla Deyisdirildi!");
            } else {
                System.out.println("Useri ID-si tapilmadi");
            }


        } catch (SQLException e) {
            System.out.println("Update Xetasi: " + e.getMessage());
        }
    }

    //Delete User
    public static void deleteUser(int id) {
        String sql = "DELETE FROM login_users WHERE id = ?";

        try (
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, id);

            int rowcount = pstmt.executeUpdate();
            if (rowcount > 0) {
                System.out.println("User ugurla silindi!");
            } else {
                System.out.println("ID tapilmadi");
            }

        } catch (SQLException e) {
            System.err.println("Delete Xetasi: " + e.getMessage());
        }
    }


        //Yoxlanilir Sistemde Email ve ya Parol Duzgundurmu
        public static Boolean sign (String em, String p){
            String sql = "SELECT email, password FROM login_users";

            try (
                    Connection conn = DBConnection.connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    ResultSet rs = pstmt.executeQuery();
            ) {
                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    if (em.equals(email) && p.equals(password)) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Xeta" + e.getMessage());
            }
            return false;
        }

        //Update Password
        public static void updatePassword(String email, String password) {
            String sql = "UPDATE login_users SET password = ? WHERE email = ?";

            try (
                    Connection conn = DBConnection.connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
                pstmt.setString(1, password);
                pstmt.setString(2,email);

                int rowCount = pstmt.executeUpdate();

                if (rowCount > 0) {
                    System.out.println("Password Ugurla Deyishdirildi!");
                } else {
                    System.out.println("Password Duzgun Secilmedi");
                }

            } catch (SQLException e) {
                System.out.println("Password Xetasi: " + e.getMessage());
            }
        }
}

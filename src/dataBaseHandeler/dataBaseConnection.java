/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataBaseHandeler;

import Model.Member;
import Model.book;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author Abdelkarem Homidi
 */
public class dataBaseConnection {
     Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/librarymanagementsystem","root","");
            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    
    }
      public static ObservableList<book> getDatausers(){
        Connection conn = ConnectDb();
        ObservableList<book> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from book");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new book(rs.getString("id"), rs.getString("Title"), rs.getString("Auther"), rs.getString("Publisher"),rs.getString("Availble")));               
            }
        } catch (Exception e) {
        }
        return list;
    }
      public static ObservableList<Member> getDataMembers(){
        Connection conn = ConnectDb();
        ObservableList<Member> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from members");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Member(rs.getString("id"), rs.getString("name"), rs.getString("email"),Integer.parseInt(rs.getString("mobile"))));               
            }
        } catch (Exception e) {
        }
        return list;
    }
    
}

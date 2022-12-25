/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package View;

import com.jfoenix.effects.JFXDepthManager;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dataBaseHandeler.dataBaseConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Abdelkarem Homidi
 */
public class MainWindowController implements Initializable {
       @FXML
    private HBox book_info;
           @FXML
    private HBox member_info;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text memberName;
    @FXML
    private Text memberContact;
    @FXML
    private TextField bookIdInput;
    @FXML
    private TextField memberIdInput;
    Connection conn = null; 
    PreparedStatement pst = null;
    ResultSet rs=null;
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //it will create a shadow effect for a given node and a specified depth level. depth levels are {0,1,2,3,4,5}
       JFXDepthManager.setDepth(book_info,1);
        JFXDepthManager.setDepth(member_info,1);
    } 
           @FXML
    void addBook(ActionEvent event) {
        loadWindow("addBook/addBookInterface.fxml","Add member");
    }

    @FXML
    void addMember(ActionEvent event) {
        loadWindow("addMember/addMemberInterface.fxml","Add member");
    }

    @FXML
    void showBookTable(ActionEvent event) {
        loadWindow("bookViewing/viewBook.fxml","Add member");

    }

    @FXML
    void showMemberTable(ActionEvent event) {
        loadWindow("memberViewing/memberTableViewing.fxml","Add member");

    }
   
    void loadWindow(String loc,String Title){
        try{
             Parent parent =FXMLLoader.load(getClass().getClassLoader().getResource(loc));
             Stage stage=new Stage(StageStyle.DECORATED);
             stage.setTitle(Title);
             stage.setScene(new Scene(parent));
             stage.show();
        } catch (IOException ex) {
              Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    //we call it every time we call the loadBookInformation method to clear the previous book's information that we looked for before
    private void clearBookCache(){
        bookName.setText("");
        bookAuthor.setText("");
    }
     //we call it every time we call the loadMemberInformation method to clear the previous book's information that we looked for before
    private void clearMemberCache(){
        memberName.setText("");
        memberContact.setText("");
    }
    //looking for a member in the database using its ID and then display the name and the author of it is the main dashbored
    @FXML
    private void loadMemberInformation(ActionEvent event) {
        clearBookCache();
         String MName="";
        String CName="";
        String id=memberIdInput.getText();
        String query="select* from members where id ='"+id+"'";
        conn=dataBaseConnection.ConnectDb();
           try {
               pst =  (PreparedStatement)conn.prepareStatement(query);
               ResultSet rs = pst.executeQuery();
               while(rs.next()){
                    MName=rs.getString("name");
                    CName=rs.getString("mobile");
                    memberName.setText(MName);
                    memberContact.setText(CName);
  
            }
               conn.close();
           } catch (SQLException ex) {
               Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
           }
 
        
    }
    //looking for a Book in the database using its ID and then display the name and the author of it is the main dashbored
    @FXML
    private void loadBookInformation(ActionEvent event) {
        clearBookCache();
        String bName="not Available";
        String aName="not Availble";
        String id=bookIdInput.getText();
        String query="select * from book where id ='"+id+"'";
        conn=dataBaseConnection.ConnectDb();
           try {
               pst = (PreparedStatement) conn.prepareStatement(query);
               ResultSet rs = pst.executeQuery();
               while(rs.next()){
                    bName=rs.getString("Title");
                    aName=rs.getString("Auther");
                    bookName.setText(bName);
                    bookAuthor.setText(aName);
  
            }
               conn.close();
           } catch (SQLException ex) {
               Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
           }
          }
    }

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
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

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
    PreparedStatement pst2 = null;
    ResultSet rs=null;
    boolean isReadyForSubmission=false;
    @FXML
    private TextField bookId;
    @FXML
    private ListView<String> issueBookList;
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
        //clearBookCache();
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
        //clearBookCache();
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

    @FXML
    private void IssueBook(ActionEvent event) {
        Alert alter=new Alert(Alert.AlertType.CONFIRMATION);
        alter.setTitle("Confirme issue operation");
        alter.setHeaderText(null);
        alter.setContentText("Are you sure to issue the book"+bookName.getText()+"\n to"+memberName.getText()+"?");
        Optional<ButtonType>response=alter.showAndWait();   
        if(response.get()==ButtonType.OK){
            conn = dataBaseConnection.ConnectDb();
            String query1="insert into issuedbook(bookID,memberID,renew_count)values(?,?,1)";
            String queryUpadte="UPDATE `book` SET `Availble`='not Available' WHERE id='"+bookIdInput.getText()+"'";
               try {
            pst = (PreparedStatement)conn.prepareStatement(query1);
            pst.setString(1, bookIdInput.getText());
            pst.setString(2, memberIdInput.getText());
            pst.execute();
            pst2 =(PreparedStatement)conn.prepareStatement(queryUpadte);
            pst2.execute();
            conn.close();
            JOptionPane.showMessageDialog(null, "The book has been issued sucessfully!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
        }

    @FXML
    private void loadIssuedBook(ActionEvent event) {
        ObservableList<String>issueData=FXCollections.observableArrayList();
        String id=bookId.getText();
        String query1="select * from issuedbook where bookID='"+id+"'";
        conn = dataBaseConnection.ConnectDb();
        try {
               pst = (PreparedStatement) conn.prepareStatement(query1);
               ResultSet rs = pst.executeQuery();
               while(rs.next()){
                   String MemberID=rs.getString("memberID");
                   Timestamp issueTime=rs.getTimestamp("issueTime");
                   int renewCount= rs.getInt("renew_count");
                   issueData.add("issue Date and Time:"+issueTime.toGMTString());
                   issueData.add("Renew Count:"+renewCount);
                   
                   
                   issueData.add("Book information:");
                   query1="select * from book where id='"+id+"'";
                   pst = (PreparedStatement) conn.prepareStatement(query1);
                   ResultSet rs2 = pst.executeQuery();
                   while(rs2.next()){
                        issueData.add("Book Title:"+rs2.getString(2));
                        issueData.add("Book Author:"+rs2.getString(3));
                        issueData.add("Book Publisher:"+rs2.getString(4));
                       
                   }
                   
                   
                   issueData.add("Member information:");
                   query1="select * from members where id='"+MemberID+"'";
                   pst = (PreparedStatement) conn.prepareStatement(query1);
                   ResultSet rs3 = pst.executeQuery();
                   while(rs3.next()){
                        issueData.add("Member Name:"+rs3.getString(2));
                        issueData.add("Member E-mail:"+rs3.getString(3));
                        issueData.add("Member Mobile:"+rs3.getString(4));
                   }
                   isReadyForSubmission=true;
            }
               issueBookList.getItems().setAll(issueData);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    private void loadSubmission(ActionEvent event) {
        if(!isReadyForSubmission){
            return;
        }
        String id=bookId.getText();
        String query1="delete from issuedbook where bookID='"+id+"'";
        String query2="UPDATE `book` SET `Availble`='Available' WHERE id='"+id+"'";
        conn = dataBaseConnection.ConnectDb();
                try {
            pst = (PreparedStatement)conn.prepareStatement(query1);
            pst.execute();
            pst2 =(PreparedStatement)conn.prepareStatement(query2);
            pst2.execute();
            JOptionPane.showMessageDialog(null, "The book has been submitted scessfully!!");
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }

    @FXML
    private void renewBook(ActionEvent event) {
        if(!isReadyForSubmission){
            Alert alter =new Alert(Alert.AlertType.ERROR);
            alter.setTitle("Failed");
            alter.setHeaderText(null);
            alter.setContentText("Please select book to renew");
            alter.showAndWait();
            return;
        }
        Alert alter =new Alert(Alert.AlertType.CONFIRMATION);
        alter.setTitle("Confirm renew operations");
        alter.setHeaderText(null);
        alter.setContentText("Are you sure to renew the book ?");
        Optional<ButtonType>respone=alter.showAndWait();
        if(respone.get()==ButtonType.OK){
            String query1="UPDATE `issuedbook` SET `issueTime`=current_timestamp,`renew_count`= renew_count+1 WHERE bookID='"+bookId.getText()+"'";
            conn=dataBaseConnection.ConnectDb();
                 try {
            pst = (PreparedStatement)conn.prepareStatement(query1);
            pst.execute();
            conn.close();
            JOptionPane.showMessageDialog(null, "The book has been issued sucessfully!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
            
        }
        
        
    }
    
    

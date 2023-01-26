/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package bookViewing;

import View.MainWindowController;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dataBaseHandeler.dataBaseConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import bookViewing.ViewBookController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author Abdelkarem Homidi
 * 
 *
 */



public class EditBookController implements Initializable {

    @FXML
    private TextField ID;
    @FXML
    private TextField Title;
    @FXML
    private TextField Author;
    @FXML
    private TextField Publisher;
    Connection conn =null;
    PreparedStatement pst = null;
    @FXML
    private Button miniButton;
    @FXML
    private Button closeButton;
    
    

    /**
     * Initializes the controller class.
     */
    public void infoEdit(Model.book book){
        Title.setText(book.getTitle());
        ID.setText(book.getID());
        Author.setText(book.getAuthor());
        Publisher.setText(book.getPublisher());
        
    
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    //this methode is called when the user clicks on the "Save" button it Modoify the information of a book already exisite in the database and then affect these changes to it
    @FXML
    private void saveModification(ActionEvent event) {
        conn = dataBaseConnection.ConnectDb();
        String update="UPDATE `book` SET Title=?,Auther=?,Publisher=? WHERE id=?";
        try {
            pst =  (PreparedStatement)conn.prepareStatement(update);
            pst.setString(1, Title.getText());
            pst.setString(2, Author.getText());
            pst.setString(3, Publisher.getText());
            pst.setString(4, ID.getText());
            pst.executeUpdate();
            conn.close();
            
            
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
           
        }
       
    }
    

    @FXML
    private void cancelModification(ActionEvent event) {
    }
       @FXML
    private void closeWindow(ActionEvent event) {
       Stage stage=(Stage)closeButton.getScene().getWindow();
        stage.close(); 
    }

    @FXML
    private void minimizeWindow(ActionEvent event) {
         Stage stage=(Stage)miniButton.getScene().getWindow();
        stage.setIconified(true);
    }
    
}

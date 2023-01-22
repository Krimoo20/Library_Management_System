/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package addBook;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dataBaseHandeler.dataBaseConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Abdelkarem Homidi
 */
public class AddBookInterfaceController implements Initializable {
    @FXML
    private TextField Author;

    @FXML
    private TextField ID;

    @FXML
    private TextField Publisher;

    @FXML
    private TextField Title;
    Connection conn =null;
    PreparedStatement pst = null;
    @FXML
    private Button closeButton;
    @FXML
    private Button miniButton;
    //this methode is called when the user clicks on the "Save" button it add a new book to the database
    @FXML
    public void addBook(ActionEvent event) {
        conn = dataBaseConnection.ConnectDb();
        String sql = "insert into book (id,Title,Auther,Publisher,Availble)values(?,?,?,?,'Available')";
        try {
            pst =  (PreparedStatement)conn.prepareStatement(sql);
            pst.setString(1, ID.getText());
            pst.setString(2, Title.getText());
            pst.setString(3, Author.getText());
            pst.setString(4, Publisher.getText());
            pst.execute();
            conn.close();
            JOptionPane.showMessageDialog(null, "Book Add succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeWindow(ActionEvent event) {
        javafx.application.Platform.exit();
    }

    @FXML
    private void minimizeWindow(ActionEvent event) {
         Stage stage=(Stage)miniButton.getScene().getWindow();
        stage.setIconified(true);
    }
    
}

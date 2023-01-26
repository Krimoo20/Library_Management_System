/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package addMember;

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
public class AddMemberInterfaceController implements Initializable {
      @FXML
    private TextField ID;

    @FXML
    private TextField email;

    @FXML
    private TextField mobile;

    @FXML
    private TextField name;
     Connection conn =null;
    PreparedStatement pst = null;
    @FXML
    private Button miniButton;
    @FXML
    private Button closeButton;
    
    //this methode is called when the user clicks on the "Save" button it add a new Member to the database
    @FXML
    void addMember(ActionEvent event) {
           conn = dataBaseConnection.ConnectDb();
        String sql = "insert into members (id,name,email,mobile)values(?,?,?,? )";
        try {
            pst =  (PreparedStatement)conn.prepareStatement(sql);
            pst.setString(1, ID.getText());
            pst.setString(2, name.getText());
            pst.setString(3, email.getText());
            pst.setString(4, mobile.getText());
            pst.execute();
            conn.close();
            JOptionPane.showMessageDialog(null, "Member Add succes");
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
        Stage stage=(Stage)closeButton.getScene().getWindow();
        stage.close(); 
    }

    @FXML
    private void minimizeWindow(ActionEvent event) {
         Stage stage=(Stage)miniButton.getScene().getWindow();
        stage.setIconified(true);
    }
    
}

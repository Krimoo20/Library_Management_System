/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package memberViewing;

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

/**
 * FXML Controller class
 *
 * @author Abdelkarem Homidi
 */
public class EditMemberController implements Initializable {

    @FXML
    private TextField ID;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField mobile;
    Connection conn =null;
    PreparedStatement pst = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void infoEdit(Model.Member member){
         name.setText(member.getName());
        ID.setText(member.getID());
        email.setText(member.getEmail());
        mobile.setText(Integer.toString(member.getMobileNumber()));
        
    }

    @FXML
    private void saveModification(ActionEvent event) {
          conn = dataBaseConnection.ConnectDb();
        String update="UPDATE `members` SET name=?,email=?,mobile=? WHERE id=?";
        try {
            pst =  (PreparedStatement)conn.prepareStatement(update);
            pst.setString(1, name.getText());
            pst.setString(2, email.getText());
            pst.setString(3, mobile.getText());
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
    
}

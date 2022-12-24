/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Abdelkarem Homidi
 */
public class MainWindowController implements Initializable {
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
            
    
  
    
}

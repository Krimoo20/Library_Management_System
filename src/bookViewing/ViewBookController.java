/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package bookViewing;

import Model.book;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dataBaseHandeler.dataBaseConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import Model.book;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Abdelkarem Homidi
 */
public class ViewBookController implements Initializable {
     @FXML
    private TableColumn<book,String> authorCol;

    @FXML
    private TableColumn<book, String> idCol;

    @FXML
    private TableColumn<book, String> publisherCol;

    @FXML
    private TableColumn<book, String> titleCol;

    @FXML
    private TableView<book> viewingBook;
    String query=null;
    Connection connection=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    ObservableList <book> List = FXCollections.observableArrayList();
    @FXML
    private TableColumn<book, String> isAliveCol;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
             loadData();
         } catch (SQLException ex) {
             Logger.getLogger(ViewBookController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }    

    private void loadData() throws SQLException {
        connection=dataBaseConnection.ConnectDb();
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isAliveCol.setCellValueFactory(new PropertyValueFactory<>("isAlive"));
        List=dataBaseConnection.getDatausers();
        viewingBook.setItems(List);
        connection.close();
        
    }

    @FXML
    private void deleteBook(ActionEvent event) {
        book selected= viewingBook.getSelectionModel().getSelectedItem();
        if(selected==null){
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setContentText("There is no book selected please select a book and try again!!");
            return;
        }
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting book");
        alert.setContentText("Are you sure want to delete "+selected.getTitle()+"?");
        Optional <ButtonType>answer=alert.showAndWait();
        if(answer.get()==ButtonType.OK){
             connection=dataBaseConnection.ConnectDb();
             String query="delete from book where id=?";
             try{
             pst =  (PreparedStatement)connection.prepareStatement(query);
             pst.setString(1, selected.getID());
             pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "book has been deleted");
             }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e);
                 
             }
        }else{
        Alert alert2 =new Alert(Alert.AlertType.ERROR);
        alert2.setContentText("deletion cancelled");
        }
    }   
    
}
 
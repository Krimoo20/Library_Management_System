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
import View.MainWindowController;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import bookViewing.EditBookController;

/**
 * FXML Controller class
 *
 * @author Abdelkarem Homidi
 */
public class ViewBookController implements Initializable {
     @FXML
    public TableColumn<book,String> authorCol;

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
    //this method when it is invoked it loads the data of the books  from the database to the view table element 
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
     //this methode is used to delete a book directly from the viewing table insted of going to the database and delete the book from it 
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
             loadData();
             }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e);
                 
             }
        }else{
        Alert alert2 =new Alert(Alert.AlertType.ERROR);
        alert2.setContentText("deletion cancelled");
        }
    }   
 //this methode is used to modifiy the information of a book directly from the viewing table insted of going to the database and delete the book from it, by using an interface to do that 
    @FXML
    private void editBook(ActionEvent event) throws SQLException {
        book selected= viewingBook.getSelectionModel().getSelectedItem();
        if(selected==null){
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setContentText("There is no book selected please select a book and try again!!");
            return;
        }
         try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("editBook.fxml"));
             
            Parent parentt;
            parentt = loader.load();
             
             EditBookController controller=loader.getController();
             controller.infoEdit(selected);
             Stage stage=new Stage(StageStyle.DECORATED);
             stage.setTitle("Edit Book");
             stage.setScene(new Scene(parentt));
             stage.show();
             
        } catch (IOException ex) {
              Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    }
    

 
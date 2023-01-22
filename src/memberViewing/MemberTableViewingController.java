/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package memberViewing;

import Model.Member;
import Model.book;
import View.MainWindowController;
import bookViewing.EditBookController;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dataBaseHandeler.dataBaseConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Abdelkarem Homidi
 */
public class MemberTableViewingController implements Initializable {
    @FXML
    private TableView<Member> MemberViewing;

    @FXML
    private TableColumn<Member, String> emailCol;

    @FXML
    private TableColumn<Member,String> idCol;

    @FXML
    private TableColumn<Member, String> nameCol;

    @FXML
    private TableColumn<Member, Integer> numberCol;
    
    String query=null;
    Connection connection=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
     ObservableList <Member> List = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }    
  //this method when it is invoked it loads the data of the books  from the database to the view table element 
    private void loadData() {
         connection=dataBaseConnection.ConnectDb();
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        List=dataBaseConnection.getDataMembers();
        MemberViewing.setItems(List);
    }
    //this methode is used to delete a Member directly from the viewing table insted of going to the database and delete the Member from it
    @FXML
    private void deletMember(ActionEvent event) {
               Member selected= MemberViewing.getSelectionModel().getSelectedItem();
        if(selected==null){
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setContentText("There is no member selected please select a member and try again!!");
            return;
        }
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting a Member");
        alert.setContentText("Are you sure want to delete "+selected.getName()+"?");
        Optional <ButtonType>answer=alert.showAndWait();
        if(answer.get()==ButtonType.OK){
             connection=dataBaseConnection.ConnectDb();
             String query="delete from members where id=?";
             try{
             pst =  (PreparedStatement)connection.prepareStatement(query);
             pst.setString(1, selected.getID());
             pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Member has been deleted");
             loadData();
             }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e);
                 
             }
        }else{
        Alert alert2 =new Alert(Alert.AlertType.ERROR);
        alert2.setContentText("deletion cancelled");
        }
    }
    //this methode is used to modifiy the information of a Member directly from the viewing table insted of going to the database and delete the Member from it, by using an interface to do that
    @FXML
    private void EditMember(ActionEvent event) {
         Member selected= MemberViewing.getSelectionModel().getSelectedItem();
        if(selected==null){
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setContentText("There is no book selected please select a book and try again!!");
            return;
        }
         try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("editMember.fxml"));
             
            Parent parentt;
            parentt = loader.load();
             
             EditMemberController controller=loader.getController();
             controller.infoEdit(selected);
             Stage stage=new Stage(StageStyle.DECORATED);
             stage.setTitle("Edit Member");
             stage.setScene(new Scene(parentt));
             stage.show();
             
        } catch (IOException ex) {
              Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
          }
        
    }
    
}

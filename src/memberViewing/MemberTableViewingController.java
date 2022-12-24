/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package memberViewing;

import Model.Member;
import Model.book;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import dataBaseHandeler.dataBaseConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private void loadData() {
         connection=dataBaseConnection.ConnectDb();
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        List=dataBaseConnection.getDataMembers();
        MemberViewing.setItems(List);
    }
    
}

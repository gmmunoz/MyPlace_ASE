package application;

import java.net.URL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MyController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	 
	@FXML
	private AnchorPane backgroundRoot;
	
	@FXML
	    private ImageView MyPlaceLogo;

	@FXML
	    private TextField txtUserName;

	    @FXML
	    private PasswordField Password;

	    @FXML
	    private Button loginBut;

	    @FXML
	    private Button forgetPwd;

	    @FXML
	    private Button signupBut;

	    private DataConnection dcon = null;

		public MyController() {
			dcon = new DataConnection();
		}
		
		 //action to register
	    @FXML
	    void signUp(ActionEvent event) throws Exception{
	    	try {
	    		System.out.println("You clicked Sign Up button!");
	    		
	    		//now load Register page
	    		AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/RegisterFXML.fxml"));
	    		backgroundRoot.getChildren().setAll(pane);
		    	
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    	}    	
	  
	    }  

	    //attempt to login
	    @FXML
	    void logIn(ActionEvent event) {
	    	// decipher inputed username & password
	    				String userName = txtUserName.getText().trim();
	    				String passwd = Password.getText().trim();

	    				// SQL connect now
	    				//String sql = "SELECT * user WHERE userName = ? AND passwd = ?";
	    				try {
	    					
	    					if (dcon.validAccount(userName, passwd) == true) {
	    						System.out.println("You logged in successfully!");
	    						
	    						AccountTracker currUser = new AccountTracker(userName);
	    						

	    			    		System.out.println(dcon.loadPlaces(userName, 2));
	    			    		
	    						//once we include places we will need to query for all places associated with this user and save those
	    						dcon.close();
	    						AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/MainFramework.fxml"));
	    			    		backgroundRoot.getChildren().setAll(pane);
	    			    		
	    					} else {
	    						// show error --> try again
	    						System.out.println("You entered wrong password or username!");
	    			    		   
	    			    		AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/WrongTryAgainFXML.fxml"));
	    			    		backgroundRoot.getChildren().setAll(pane);
	    					    				    	
	    						
	    					}	 

	    				} catch (Exception ex) {
	    					ex.printStackTrace();
	    				}
	    }
	    
	    public String getUserLoggedIn() {
	    	return userLoggedIn;
	    }
	    
	    

	    @FXML
	    void retrievePwd(ActionEvent event) {
	    	try {
	    			
	    		System.out.println("You clicked Forgot Password button!");
	    		   
		    	Parent root = FXMLLoader.load(getClass().getResource("../fxml/forgotPwdFXML.fxml"));	    
		    	Scene scene = new Scene(root);	    
		    	Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();	    
		    	stage.setTitle("Forgot Password");
		    	stage.setScene(scene);	  
		    	stage.show();
		    	
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    	}    	
	    }
}

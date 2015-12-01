import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import org.apache.jena.query.ResultSet;

public class Browser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2954326234544882086L;

	public void buildGUI() {		
		DBConnection connection = new DBConnection();
		ResultSet results = connection.read("http://localhost:3030/ds/query", "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}");
		
		//local variables
		TableView table = new TableView(results);
		JSplitPane splitPane = new JSplitPane();
		splitPane.add(table,JSplitPane.RIGHT);
				  
		//add components and properties to frame 
		setLayout(new BorderLayout());
		add(splitPane, BorderLayout.CENTER);
		setSize(1000, 600);
		setMinimumSize(new Dimension(400, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main (String[] args){ 
		 EventQueue.invokeLater(new Runnable() {
			 public void run(){ 
				 Browser browser = new Browser();
				 browser.buildGUI();
			 }
		 });
	 }
}

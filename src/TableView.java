import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

public class TableView extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 217932103388268935L;
	private DefaultTableModel dataModel;
	private String[] header = { "Subject", "Predicate", "Object" };
	private DBConnection connection;
	private String query;
	private JTextField addressTextField;
	private String dataSet;
	JTable table;

	
	public TableView()
	{
		dataSet = "";
		connection = new DBConnection();
		query = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}";
		addressTextField = new JTextField("http://");
		addressTextField.addActionListener(new GoButtonAction());
		dataModel = new DefaultTableModel(null, header);
	    table = new JTable(dataModel);
	    update();
	    JScrollPane scrollpane = new JScrollPane(table);
	    setLayout(new BorderLayout());
	    add(scrollpane, BorderLayout.CENTER);
	    add(addressTextField, BorderLayout.NORTH);
	    table.addMouseListener(new TableMouseListener());
	    table.setEnabled(false); 
	}
	
	public void update(){
		
		if (dataModel.getRowCount() > 0) {
		    for (int i = dataModel.getRowCount() - 1; i > -1; i--) {
		        dataModel.removeRow(i);
		    }
		}
		
		if(dataSet != ""){
			ResultSet results = connection.read(dataSet, query);
			while (results.hasNext()){
		    	QuerySolution sol = results.next();
		    	RDFNode object = sol.get("object");
				RDFNode predicate = sol.get("predicate");
				RDFNode subject = sol.get("subject");
			    
			    dataModel = (DefaultTableModel) table.getModel();
				dataModel.addRow(new Object[] { subject, predicate, object });	
		    }
		}
		dataModel.fireTableDataChanged();
	}
	
	class TableMouseListener extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e) {
			int col = table.columnAtPoint(e.getPoint());
			int row = table.rowAtPoint(e.getPoint());
			Object value = table.getModel().getValueAt(row, col);
			if (e.getClickCount() == 2 && col != 1) {
				query = "SELECT ?subject ?predicate ?object WHERE { VALUES ?subject {" + " <" + value.toString() + "> " + "}?subject ?predicate ?object .}";	
				System.out.println(query);
				update();
			}
	    }
	}
	
	public class GoButtonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		String text = addressTextField.getText();
		dataSet = text;
		query = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}";
		update();
		}
	}
}

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	
	public TableView(ResultSet result)
	{
		ResultSet results = result;
		dataModel = new DefaultTableModel(null, header);
	    JTable table = new JTable(dataModel);
	    JScrollPane scrollpane = new JScrollPane(table);
	    add(scrollpane, BorderLayout.CENTER);
	    
	    while (results.hasNext()){
	    	QuerySolution sol = results.next();
	    	RDFNode object = sol.get("object");
			RDFNode predicate = sol.get("predicate");
			RDFNode subject = sol.get("subject");
		    
		    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.addRow(new Object[] { subject, predicate, object });	
	    }
	}
	
	
	
	/**
	while (rs.hasNext()) {
		// Moves onto the next result
		QuerySolution sol = rs.nextSolution();
		// Return the value of the named variable in this binding.
		// A return of null indicates that the variable is not present in
		// this solution
		RDFNode object = sol.get("object");
		RDFNode predicate = sol.get("predicate");
		RDFNode subject = sol.get("subject");

		// Fill the table with the data
		DefaultTableModel tableModel = (DefaultTableModel) _instance.getModel();
		tableModel.addRow(new Object[] { subject, predicate, object });
		**/
}

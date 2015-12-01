import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private DBConnection connection;
	private String query;
	
	JTable table;

	
	public TableView()
	{
		connection = new DBConnection();
		query = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}";
		dataModel = new DefaultTableModel(null, header);
	    table = new JTable(dataModel);
	    update();
	    JScrollPane scrollpane = new JScrollPane(table);
	    add(scrollpane, BorderLayout.CENTER);
	    table.addMouseListener(new TableMouseListener());
	    table.setEnabled(false); 
	}
	
	public void update(){
		ResultSet results = connection.read("http://localhost:3030/ds/query", query);
		if (dataModel.getRowCount() > 0) {
		    for (int i = dataModel.getRowCount() - 1; i > -1; i--) {
		        dataModel.removeRow(i);
		    }
		}
		
		while (results.hasNext()){
	    	QuerySolution sol = results.next();
	    	RDFNode object = sol.get("object");
			RDFNode predicate = sol.get("predicate");
			RDFNode subject = sol.get("subject");
		    
		    dataModel = (DefaultTableModel) table.getModel();
			dataModel.addRow(new Object[] { subject, predicate, object });	
	    }
		dataModel.fireTableDataChanged();
	}
	
	class TableMouseListener extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e) {
			int col = table.columnAtPoint(e.getPoint());
			int row = table.rowAtPoint(e.getPoint());
			Object value = table.getModel().getValueAt(row, col);
			if (e.getClickCount() == 2 && col == 2 && table.getModel().getValueAt(row, col) instanceof String ) {
				query = "SELECT ?subject ?predicate ?object WHERE { VALUES ?subject {" + " <" + value.toString() + "> " + "}?subject ?predicate ?object .}";	
				System.out.println(query);
				update();
			}
	    }
	}	
}

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;

public class DBConnection {

	
	public void write(){
		/**
		Model model = ModelFactory.createDefaultModel();
		Resource resource = model.createResource("dshtrh.//sdfgdsfsgd").addProperty(VCARD.FN, "Arjen");
		**/
	}
	
	public ResultSet read(String dataset, String query){
		QueryExecution qe = QueryExecutionFactory.sparqlService(
                dataset, query);
        ResultSet results = qe.execSelect();
        //qe.close();
        return results;
	}
	
	public void update(){
		
	}
}

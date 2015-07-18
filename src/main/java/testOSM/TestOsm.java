/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testOSM;


import java.nio.charset.Charset;
import org.neo4j.gis.spatial.osm.OSMImporter;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.batchinsert.BatchInserter;

public class TestOsm {
    
    private static final String DB_PATH = "/Users/amhg/Documents/TU-Clausthal/CUARTO/R_PROJECT/community/data/graph.db";
    
    public static void main(final String[] args){
        
        OSMImporter importer = new OSMImporter("clz_map.osm");
        importer.setCharset(Charset.forName("UTF-8"));
        BatchInserter batchInserter = BatchInserter.inserter(DB_PATH);
        
        try{
            importer.importFile(batchInserter, "clz_map.osm", false);
            GraphDatabaseService db = new EmbeddedGraphDatabase(DB_PATH);
            importer.reIndex(db);
            db.shutdown();
            }
        
        catch(Exception e){
            System.out.println(e.getMessage());
            }
            
        batchInserter.shutdown();
        
          
    }
    
}

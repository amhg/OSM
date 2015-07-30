/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amhg;


import org.neo4j.gis.spatial.osm.OSMImporter;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class TestOsm {
    
    private static final String DB_PATH = "/Users/amhg/Documents/TU-Clausthal/CUARTO/R_PROJECT/community/data/graph.db";

    private static final File DB_FILE = new File( DB_PATH ).getAbsoluteFile();

    public static void main(final String[] args) throws IOException {
        
        OSMImporter importer = new OSMImporter("clz_map.osm");
        importer.setCharset(Charset.forName("UTF-8"));
        Map<String, String> config = new HashMap<>();
        config.put("dbms.pagecache.memory", "4096M" );
        config.put("dump_configuration", "true");
        config.put("use_memory_mapped_buffers", "true");

        // for future reference 2.3 uses file, 2.2 uses path
        //BatchInserter batchInserter = BatchInserters.inserter(DB_FILE, config);
        BatchInserter batchInserter = BatchInserters.inserter(DB_PATH, config);

        try {
            importer.importFile(batchInserter, "clz_map.osm", false);
            batchInserter.shutdown();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        // for future reference 2.3 uses file, 2.2 uses path
        // GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        GraphDatabaseService db = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(DB_PATH)
                .setConfig(GraphDatabaseSettings.pagecache_memory, "4096M")
                .newGraphDatabase();
        importer.reIndex(db);
        db.shutdown();
          
    }
    
}

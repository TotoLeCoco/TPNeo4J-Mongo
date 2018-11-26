/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpneo4j.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.bson.Document;

/**
 *
 * @author Fouad El Ouaryaghli
 */
public class MongoClass {
    
    static MongoClient mc = new MongoClient("127.0.0.1");
    static MongoDatabase dt =  mc.getDatabase("dbDocuments");
    //static MongoCollection<Document> coll = new MongoCollection<Document>;
    
    public void datastoreMongo(){
        dt.createCollection("index");
        ArrayList<Document> list = new ArrayList<>();
        StringTokenizer st;
        for (String s: Neo4jClass.listArticle){
            st = new StringTokenizer(s);
            Document document = new Document();
            document.append("idDocument", st.nextToken());
            while (st.hasMoreTokens())
                document.append("motsCles", st.nextToken());
            list.add(document);
        }
        coll.insertMany(list);        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpneo4j.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;

/**
 *
 * @author Fouad El Ouaryaghli
 */
public class MongoClass {
    
    static MongoClient mc = new MongoClient("127.0.0.1");
    static MongoDatabase dt = null;
    static MongoCollection<Document> coll = null;
    
    
    
}

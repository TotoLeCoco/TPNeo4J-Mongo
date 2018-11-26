/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpneo4j.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import static tpneo4j.mongo.MongoClass.dt;
import static tpneo4j.mongo.Neo4jClass.driver;
import static tpneo4j.mongo.Neo4jClass.listArticle;

/**
 *
 * @author Fouad El Ouaryaghli
 */
public class TPNeo4JMongo {

    /**
     * @param args the command line arguments
     */
    
    //Neo
    static Driver driver = GraphDatabase.driver("bolt://192.168.56.50");
    static Session session = driver.session();
    ArrayList<String> listArticle = new ArrayList<>();
    
    //Mongo
    static MongoClient mc = new MongoClient("192.168.56.50");
    static MongoDatabase dt =  mc.getDatabase("dbDocuments");
    
    
    public static void listeTitre(){
        StatementResult result = session.run("MATCH (a:Article) RETURN id(a),a.titre order by id(a) asc");
        /*System.out.println("toto 1");
        dt.createCollection("index");
        System.out.println("toto 1");*/
        MongoCollection<Document> coll = dt.getCollection("index");
        while(result.hasNext()){
                // lecture de la ligne suivante 
                Record record = result.next();
                ArrayList<String> listArticle = new ArrayList<>();
                //String ch = record.get("id(a)").asInt()+" "+ record.get("a.titre").asString();
                String chaine = record.get("a.titre").asString().toLowerCase();
                StringTokenizer st = new StringTokenizer(chaine,",'-::;.()+[]{}?! ");
                while (st.hasMoreTokens()){
                    
                    listArticle.add(st.nextToken());  
                    
                }            
                //String nvlChaine = chaine.trim();
                Document doc = new Document();
                doc.append("idDocument", record.get("id(a)").asInt());
                doc.append("motsCles", listArticle);
                coll.insertOne(doc);
        }
    }
    
    static void listDoc(){
        MongoCollection<Document> coll = dt.getCollection("index");
        if ( coll != null) {
            FindIterable<Document> documents = coll.find();
            for (Document doc : documents){
                System.out.println(doc.toJson(new JsonWriterSettings(true)));
            }
        }
        else {
            System.out.println("Sélectionner une collection avant");
        }
    }
    
    static void structureMiroir(){
        MongoCollection<Document> coll = dt.getCollection("index");
        MongoCollection<Document> coll2 = dt.getCollection("indexInverse");
        Document document;
        if (coll != null) {
            FindIterable<Document> documents = coll.find();
            ArrayList<String> listMots = new ArrayList<>();
            for (Document doc : documents){
                listMots = (ArrayList) doc.get("motsCles");
                for (String s : listMots){
                    document = coll2.find(eq("mot",s)).first();
                    if (document != null)
                        document.append("idDocument", Arrays.asList(doc.getString("idDocument")));
                        //document.append("idDocument", Updates.addToSet("idDocument", doc.getInteger("idDocument")));
                    else {
                        document = new Document();
                        document.append("mot", s);
                        document.append("idDocument", Arrays.asList(doc.getString("idDocument")));
                    }
                    coll2.insertOne(document);
                }
            }
        }
        else {
            System.out.println("Collection vide");
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        
        structureMiroir();
                    
 /*        Scanner sc = new Scanner(System.in);
           String selection;
        do {
           
            System.out.println("1 - Selection d'une BDD MongoDB");
            System.out.println("2 - Selection d'une collection");
            System.out.println("3 - Liste des documents");
            System.out.println("4 - Recherche dans la collection à partir du nom");
            System.out.println("5 - Déconnexion");
            
            selection = sc.nextLine();
            switch(selection) {
                case "1":
                    listeTitre();
                    break;
/*                case "2":
                    selectCollection();
                    break;
                case "3":
                    listDoc();
                    break;
                case "4":
                    searchOnDoc();
                    break;
                case "5":
                    disconnect();
                    break;
                default:
                    System.out.println("Selectionner une option entre 1 et 5 !");
            }
         
        }while (!selection.equals("5"));
        */
    }
        
    }
    
}

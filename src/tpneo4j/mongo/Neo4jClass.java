/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpneo4j.mongo;
import java.util.ArrayList;
import org.neo4j.driver.v1.*;

/**
 *
 * @author Fouad El Ouaryaghli
 */
public class Neo4jClass {
    static Driver driver ;
    static Session session ;
    static ArrayList<String> listArticle;
    
    //connexion Neo4j
    public static void connexion(){
        
        driver = GraphDatabase.driver("bolt://192.168.56.50");
        session = driver.session();
    }
    // Déconnexion Neo4j
    public static void deconnexion(){
       
        session.close();
        driver.close();
    }
    
    // recupere les ids et titres de tous les article 
    public void listeTitre(){
        StatementResult result = session.run("MATCH (a:Article) RETURN id(a),a.titre order by id(a) asc");
        while(result.hasNext()){
                // lecture de la ligne suivante 
                Record record = result.next();
                String ch = record.get("id(a)").asInt()+" - "+ record.get("a.titre").asString();
                listArticle.add(ch);
        }
    }
    
    
    
    
    
}

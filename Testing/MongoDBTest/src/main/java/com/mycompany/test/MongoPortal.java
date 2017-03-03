/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;
import java.util.Set;

public class MongoPortal {

    public static void main(String args[]) throws UnknownHostException {
        MongoPortal port = new MongoPortal();
    }

    public MongoPortal() throws UnknownHostException {

        MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1@ds011291.mlab.com:11291/easylog");
        MongoClient client = new MongoClient(uri);

        DB db = client.getDB("easylog");
        
        	Set<String> collections = db.getCollectionNames();

	for (String collectionName : collections) {
		System.out.println(collectionName);
	}
       
        DBCollection collection = db.getCollection("onMovement");
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            System.out.println("herdNumber: "+obj.getString("herdNumber"));
            System.out.println("barNumber: " +obj.getString("barNumber"));
            System.out.println("sex: "+obj.getString("sex"));
            System.out.println("Colour: " + obj.getString("colour"));
            System.out.println("dateOfBirth: " +obj.getString("dateOfBirth"));
            System.out.println("breed: " + obj.getString("breed"));
        }
    }
}

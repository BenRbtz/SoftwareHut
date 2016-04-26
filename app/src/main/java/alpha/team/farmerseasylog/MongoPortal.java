package alpha.team.farmerseasylog;




import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;

/**
 * Created by yakuza on 13/04/16.
 */
public class MongoPortal {

    public MongoPortal() {
        try {
            MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1@ds011291.mlab.com:11291/easylog");
            Log.v("1Connection: ", uri.getURI());
            MongoClient client = new MongoClient(uri);
            Log.v("Connected: ", uri.getURI());
            // if database doesn't exists, MongoDB will create it for you
            DB db = client.getDB(uri.getDatabase());
            Log.v("Connection", "Got the database");
            DBCollection table = db.getCollection("test");
            BasicDBObject document = new BasicDBObject();
            document.put("name", "mkyong");
            document.put("age", 30);
            document.put("createdDate", new Date());
            table.insert(document);
            Log.v("Connection: ", "Done");
            client.close();
        } catch (MongoException e) {

            Log.v("ERROR", e.getMessage());
        }
    }
}

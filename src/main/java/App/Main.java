package App;

import Database.SqlQueries;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.time.OffsetDateTime;

public class Main {
    public static void main(String[] args) {
        /*App.User user = new App.User("Donald Trump", "14-06-1946", "donaldtrump@aol.com");
        System.out.println(user);*/
        /*App.User user2 = new App.User("Marcel Pavel","14-01-2000","keksformony@twitch.tv");
        System.out.println(user2);*/

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fullName", "Donal Trump");
        jsonObject.put("birthDate", "14-06-1946");
        jsonObject.put("email", "donaldtrump@aol.com");



        try {
            // Please make sure to refactor the name of the output directory
            FileWriter myFileWriter = new FileWriter("output\\output-" + OffsetDateTime.now().toString().replace(":", "-") + ".json");
            myFileWriter.write(jsonObject.toJSONString());
            myFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

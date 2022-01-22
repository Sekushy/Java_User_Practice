package Database;

import App.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class DbConnection {

    private Connection connection;

    public DbConnection(String connectionString) {
        establishDatabaseConnection(connectionString);
    }

    public void establishDatabaseConnection(String connectionString) {
        try {
            connection = DriverManager.getConnection(connectionString);
            System.out.println("Connection to the database has been established.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertUserIntoDatabase(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SqlQueries.insertIntoDatabase(user));
            System.out.println("User " + user.getFullName() + " has been successfully added to the database.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertMultipleUsersIntoDatabase(ArrayList<User> listOfUsers) {
        try {
            Statement statement = connection.createStatement();
            for (User user : listOfUsers) {
                statement.executeUpdate((SqlQueries.insertIntoDatabase(user)));
                System.out.println("User " + user.getFullName() + " has been added to the database.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printAllUsersInDatabase() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SqlQueries.selectAllUsersInDatabase());

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " | " +
                        resultSet.getString("email") + " | " +
                        resultSet.getString("age"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getVersionBasedOnEmailFromDatabase(String email) {
        String version = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SqlQueries.selectUserBasedOnFilter("email = '" + email + "'"));
            version = resultSet.getString("version");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return version;
    }

    public User getUserBasedOnEmailFromDatabase(String email) {
        User user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SqlQueries.selectUserBasedOnFilter("email = '" + email + "'"));
            user = new User(resultSet.getString("name"),
                    resultSet.getString("birthdate"),
                    resultSet.getString("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


    /**
     * @param updatedField - Represents the field in the database that we want to modify
     * @param updatedValue - Represents the value we want to change this data to
     * @param email - Is used as a filter, in our case the email will always be unique
     */
    public void updateUserDataByEmail(String updatedField, String updatedValue, String email) {
        User user = getUserBasedOnEmailFromDatabase(email);
        try {
            Statement statement = connection.createStatement();
            statement.execute(SqlQueries.updateUserBasedOnFilter(updatedField, updatedValue, "email = '" + email +"'"));
            System.out.println("Updated has been performed successfully.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUserFromDatabaseByEmail(String email) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(SqlQueries.deleteUserFromDatabaseByEmail(email));
            System.out.println("User with email " + email + " has been successfully deleted from the database.");
        } catch (SQLException throwables) {
            System.out.println("No user found with email " + email + ". Please recheck data and try again.");
        }
    }

    public ArrayList<User> getAllUsersInDatabase() {
        ArrayList<User> listOfUsers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SqlQueries.selectAllUsersInDatabase());
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("birthDate"), resultSet.getString("email"));
                listOfUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfUsers;
    }

    private String prettifyJsonObject(String uglyJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);
        return gson.toJson(je);
    }

    public void exportDatabaseAsJson() {
        // Method will return all existing users in the database as a single json file with all the information found in the users table.
        ArrayList<User> listOfAllUsers = getAllUsersInDatabase();
        JSONArray jsonArray = new JSONArray();

        for (User user : listOfAllUsers) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fullName", user.getFullName());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("age", user.getAge());
            jsonObject.put("birthDate", user.getBirthDate());
            jsonObject.put("version", getVersionBasedOnEmailFromDatabase(user.getEmail()));
            jsonArray.add(jsonObject);
        }

        String fileName = "output\\output-" + OffsetDateTime.now().toString().replace(":", "-") + ".json";
        String jsonPrettifiedString = prettifyJsonObject(jsonArray.toJSONString());
        try {
            FileWriter myFileWriter = new FileWriter(fileName);
            myFileWriter.write(jsonPrettifiedString);
            myFileWriter.close();
            System.out.println("File " + fileName + " has been successfully created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importDatabaseFromJson(String pathToFile) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(pathToFile);
            JSONArray userList = (JSONArray) jsonParser.parse(fileReader);
            userList.forEach(user -> parseUserObject((JSONObject) user));
            System.out.println("File has been imported successfully into the database.");
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Please check to see if the path is correct.");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseUserObject(JSONObject jsonUser) {
        User user = new User(jsonUser.get("fullName").toString(),
                jsonUser.get("birthDate").toString(),
                jsonUser.get("email").toString());
        insertUserIntoDatabase(user);
    }
}

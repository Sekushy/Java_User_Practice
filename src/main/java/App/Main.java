package App;

import Database.DbConnection;

// Today's assignment - Read and do your best to understand the code presented to you.
// Determine what everything in the code does to the best of your ability and whenever you are unsure, call me over to have a look together.
// The purpose of this exercise is for you to become comfortable with reading code written by others and code which presents new concepts.

public class Main {

    public static void main(String[] args) {
        // You will have to create a new database that fits the model chosen for the code you have been given.
        // To do this, you will first need to create a new database as shown yesterday
        // Open sqlite3 from the desktop folder you have created and open the terminal window
        // Type the following command --> .open users.db
        // Type the following command --> CREATE TABLE "users" ("name"	varchar(20) NOT NULL, "email"	varchar(25) NOT NULL UNIQUE, "age"	INTEGER, "birthDate"	varchar(100) NOT NULL, "version"	varchar(150));
        // After your database is created, copy the location to it and replace "PATH_TO_YOUR_DATABASE" with your location
        String connectionString = "<PATH_TO_YOUR_DATABASE>>";

        // A .json file will be provided which contains dummy data (the people referenced there are not real people)
        // Take the .json file and add it into the "importer" folder in your project (if you can't find it call me over)
        // Copy the path to the json file and replace the string that is given as a parameter in the importDatabaseFromJson method.
        // DON'T FORGET THE ADD THE FOLDER NAME AS WELL!!
        // i.e connection.importDatabaseFromJson("importer\\......");
        DbConnection connection = new DbConnection(connectionString);
        connection.importDatabaseFromJson("<PATH_TO_YOUR_FILE>>");
    }
}

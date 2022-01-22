package Database;

import App.User;

import java.time.OffsetDateTime;

public final class SqlQueries {

    public static String selectAllUsersInDatabase() {
        return "SELECT * FROM users";
    }

    public static String insertIntoDatabase(User user) {
        return "INSERT INTO users VALUES ('" + user.getFullName() +
                "' , '" + user.getEmail() +
                "' , " + user.getAge() +
                ", '" + user.getBirthDate() +
                "', '" + OffsetDateTime.now().toString().replace(":", "-") + "')";
    }

    public static String deleteUserFromDatabaseByEmail(String email) {
        return "DELETE FROM users WHERE email = '" + email + "'";
    }

    public static String selectUserBasedOnFilter(String filter) {
        return "SELECT * FROM users WHERE " + filter;
    }

    public static String updateUserBasedOnFilter(String updatedField, String updatedValue, String filter) {
        return "UPDATE users  SET " +
                updatedField + " = '" + updatedValue +
                "' WHERE " + filter;
    }
}

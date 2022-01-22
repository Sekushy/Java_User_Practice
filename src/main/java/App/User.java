package App;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class User {
    // Atribute:
    private String fullName;
    private String birthDate;
    private String email;
    private int age;

    // Constructor = metoda care poarta denumirea clasei
    // si ne ajuta cu initializarea (constructia) obiectului
    // Note: daca nu definim un constructor in mod explicit,
    // compilatorul va defini unul.

    public User(String fullName, String birthDate, String email) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.age = calculateAge(getCurrentDate(), birthDate);
    }

    // GETTERS

    public String getFullName() {
        return fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getCurrentDate() {
        //afisare data curenta
        //schimbare format data
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currentDate = dateTime.format(formatter);
        return currentDate;
    }

    public ArrayList<Integer> splitDate(String date) {
        ArrayList<Integer> dates = new ArrayList<>();

        //cea mai simpla forma de lista, vector[]
        //Integer.pareseInt = transforma un string in int
        String[] temp = date.split("-");
        int day = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int year = Integer.parseInt(temp[2]);
        dates.add(day);
        dates.add(month);
        dates.add(year);
        return dates;
    }

    public ArrayList<Integer> splitDate(String date, String delimiter) {
        ArrayList<Integer> dates = new ArrayList<>();

        String[] temp = date.split(delimiter);
        int day = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int year = Integer.parseInt(temp[2]);
        dates.add(day);
        dates.add(month);
        dates.add(year);
        return dates;
    }

    public int calculateAge(String currentDate, String birthDate) {
        ArrayList<Integer> formattedCurrentDate = splitDate(currentDate);
        ArrayList<Integer> formattedBirthDate = splitDate(birthDate);
        int ageYears = formattedCurrentDate.get(2) - formattedBirthDate.get(2);

        if (formattedCurrentDate.get(1) < formattedBirthDate.get(1)) {
            ageYears--;
        } else if (formattedCurrentDate.get(1) > formattedBirthDate.get(1)) {
            // Will refactor later
        } else {
            if (formattedCurrentDate.get(0) < formattedBirthDate.get(0)) {
                ageYears--;
            }
        }
        return ageYears;
    }

    public JSONObject convertUserToJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fullName", fullName);
        jsonObject.put("birthDate", birthDate);
        jsonObject.put("email", email);
        jsonObject.put("age", age);
        return jsonObject;
    }

    // Method is purely used in order to prettify the outputted json file.
    // You can simply take this syntax at face value and not bother with it.
    private String prettifyJsonObject(String uglyJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);
        return gson.toJson(je);
    }

    public void writeUserToJsonFile() {
        String fileName = "output\\output-" + OffsetDateTime.now().toString().replace(":", "-") + ".json";
        String jsonPrettifiedString = prettifyJsonObject(convertUserToJsonObject().toJSONString());
        try {
            FileWriter myFileWriter = new FileWriter(fileName);
            myFileWriter.write(jsonPrettifiedString);
            myFileWriter.close();
            System.out.println("File " + fileName + " has been successfully created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "User {" + '\n' +
                '\t' + "\"fullName\" : \"" + fullName + '\"' + ',' + '\n' +
                '\t' + "\"birthDate\" : \"" + birthDate + '\"' + ',' + '\n' +
                '\t' + "\"email\" : \"" + email + '\"' + ',' + '\n' +
                '\t' + "\"age\" : " + '\"' + age + '\"' + '\n' +
                '}';
    }
}
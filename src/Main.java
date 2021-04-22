import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    /**
     * Propogated {@link IOException} here
     * {@link #readFile} and {@link #writeOutput} methods should be called here
     * A {@link Scheduler} instance must be instantiated here
     */
    public static void main(String[] args) throws IOException {
        // create Scheduler object and send json input in it.
        Scheduler sch = new Scheduler(readFile(args[0]));

        writeOutput("solution_dynamic.json", sch.scheduleDynamic());
        writeOutput("solution_greedy.json", sch.scheduleGreedy());
    }

    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Assignment}s obtained by reading the given json file
     * @throws FileNotFoundException If the given file does not exist
     */
    private static Assignment[] readFile(String filename) throws FileNotFoundException {
        try {
            // create Gson object
            Gson gson = new Gson();
            // create a reader
            JsonReader jsReader = new JsonReader(new FileReader(filename));
            // convert JSON file to array that holds Assignment objects
            Assignment[] assignment1 = gson.fromJson(jsReader, Assignment[].class);
            Arrays.sort(assignment1);
            // close reader
            jsReader.close();
            return assignment1;

        } catch (IOException ex) {
            throw new FileNotFoundException();
        }
    }

    /**
     * @param filename  json filename to write
     * @param arrayList a list of {@link Assignment}s to write into the file
     * @throws IOException If something goes wrong with file creation
     */

    private static void writeOutput(String filename, ArrayList<Assignment> arrayList) throws IOException {
        //we can write an arraylist to a file with using setPrettyPrinting method which is best to write as a json template.
        Collections.reverse(arrayList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(gson.toJson(arrayList.toArray()));
        writer.close();
    }
}

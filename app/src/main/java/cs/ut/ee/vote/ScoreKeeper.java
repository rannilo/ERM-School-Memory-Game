package cs.ut.ee.vote;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class ScoreKeeper {
    public static final String FILENAME = "scores.txt";
    public static final String TAG = "ScoreKeeper";
    public static final HashMap<Integer, Integer> scores = new HashMap<>();

    public static String readFile(Context context) throws IOException {
        Log.d(TAG, "readFile called");
        File file = new File(context.getFilesDir(), FILENAME);
        if (!file.exists()) {
            Log.e(TAG, "File doesn't exist. Creating one instead");
            resetScores(context);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            Log.d(TAG, "read from file: " + line);
            sb.append(line + ";");
        }
        if (sb.length() < 3) {
            Log.e(TAG, "File wrong. Creating new file");
            resetScores(context);
        }
        reader.close();
        return sb.toString();
    }

    public static void writeFile(Context context) throws IOException {
        Log.d(TAG, "Writing to file");
        File file = new File(context.getFilesDir(), FILENAME);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (Integer key : scores.keySet()){
            String line = Integer.toString(key) + ":" + Integer.toString(scores.get(key)) + "\n";
            Log.d(TAG, "writing to file line: " + line);
            writer.write(line);
            writer.flush();
        }
        writer.close();
        readFile(context);
    }

    public static void resetScores(Context context) throws IOException {
        Log.d(TAG, "Creating new file");
        for (Integer key : scores.keySet()){
            scores.put(key, 0);
        }
        writeFile(context);
    }

    public static void setScores(String scoresString) {
        String[] lines = scoresString.split(";");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String[] items = line.split(":");
            try {
                scores.put(Integer.parseInt(items[0].trim()), Integer.parseInt(items[1]));
            } catch (Exception ex) {
                scores.put(i, 0);
            }
        }
    }

    public static void init(Context mContext) throws IOException {
        Log.d(TAG, "Init called");
        for(int i = 1; i<=12; i++){
            scores.put(i, 0);
        }
        String fileString = readFile(mContext);
        setScores(fileString);
    }

    public static int picNrToId(int picNr) {
        switch (picNr) {
            case R.raw.pic1:
                return 1;
            case R.raw.pic2:
                return 2;
            case R.raw.pic3:
                return 3;
            case R.raw.pic4:
                return 4;
            case R.raw.pic5:
                return 5;
            case R.raw.pic6:
                return 6;
            case R.raw.pic7:
                return 7;
            case R.raw.pic8:
                return 8;
            case R.raw.pic9:
                return 9;
            case R.raw.pic10:
                return 10;
            case R.raw.pic11:
                return 11;
            case R.raw.pic12:
                return 12;
            default:
                return -1;
        }
    }

    public static void logScores() {
        for (Integer key : scores.keySet()) {
            Log.d(TAG, Integer.toString(key));
        }
        for (Integer value : scores.values()) {
            Log.d(TAG, Integer.toString(value));
        }
    }
}

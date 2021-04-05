package com.example.sauna;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SaunaList extends ArrayList<Sauna> {

    private static SaunaList instance = null;

    private SaunaList() {
        // Exists to prevent additional instantiations.
    }

    public static SaunaList getInstance() {
        if (instance == null) {
            instance = new SaunaList();
        }
        return instance;
    }

    public static String toString(String _SEP_) {
        String list = "";
        for (Sauna S : instance) {
            list += S.toString(_SEP_) + "\n";
        }
        return list;
    }

    public static String toPrintString() {
        String list = "";
        for (Sauna S : instance) {
            list += S.toPrintString() + "\n";
        }
        return list;
    }

    protected static void addSauna(Sauna sauna) {
        instance.add(sauna);
    }

    protected static void removeSauna(Sauna sauna) {
        instance.remove(sauna);
    }

    protected static Sauna getSauna(String code) {
        for (Sauna s : instance) {
            if (s.getProductCode().equals(code))
                return s;
        }
        return null;
    }

    protected static void saveToFile(String path) throws IOException {
        File file = new File(path, "sauna-list.txt");
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(toString(Sauna.fileLineSeparator).getBytes());
        stream.close();
    }

    protected static boolean loadFromFile(String path) throws Exception {
        //File file = new File(path, "sauna-list.txt");
        final boolean[] success = {false};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    URL url = new URL(path);
                    Scanner sc = new Scanner(url.openStream());
                    instance = new SaunaList();
                    while (sc.hasNextLine()) {
                        ArrayList<String> s = new ArrayList<>();
                        String line;
                        for (int i = 0; i < 7; i++) {
                            line = sc.nextLine();
                            s.add(line);
                        }
                        addSauna(new Sauna(s.get(0), s.get(1), s.get(2), Integer.parseInt(s.get(3)),
                              s.get(4), Double.parseDouble(s.get(5)), s.get(6)));
                    }
                    success[0] = true;
                } catch (Exception e) {
                    success[0] = false;
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
        return success[0];
    }

    public static double getMedianPrice() {
        ArrayList<Double> prices = new ArrayList<>();
        for (Sauna s : SaunaList.getInstance()) {
            prices.add(s.getPrice());
        }
        Collections.sort(prices);
        if (prices.size() % 2 != 0)
            return prices.get((int) Math.round(prices.size() / 2 - 0.1));
        else {
            double avg = (prices.get((int) Math.round(prices.size() / 2))
                  + prices.get((int) Math.round(prices.size() / 2 + 1))) / 2;
            return avg;
        }
    }


}

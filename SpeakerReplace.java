/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okosora;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Acer
 */
public class SpeakerReplace {

    public static void main(String[] args) {
        final File FOLD = new File("d:\\okosora\\conversion\\");

        listFilesForFolder(FOLD);
    }

    public static void listFilesForFolder(final File FOLDER) {
        final File KEYFILE = new File("d:\\okosora\\doracsenge1021.txt");
        for (final File fileEntry : FOLDER.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                String filename = fileEntry.getName();
                System.out.println(filename);
                if (filename.endsWith("txt")) {
                    replaceSpeaker(fileEntry, KEYFILE, "UTF-8");
                }
            }
        }
    }

    private static void replaceSpeaker(File fileEntry, final File KEYFILE, String utf8) {
        StringBuilder b = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileEntry), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {

                BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(KEYFILE), "UTF-8"));
                String line2;
                while ((line2 = reader2.readLine()) != null) {
                    String[] piece = line2.split("\t");
                    String name = piece[0];
                    String abbrev = piece[1];
                    String key = piece[2];
                    if (fileEntry.getName().equals(name) &&line.contains(abbrev)) {
                        line = line.replaceAll(abbrev, key);
                    }
                    
                   
                }
                line = line.replaceAll("\\(\\(", "\\(");
                line = line.replaceAll("\\)\\)", "\\)");
                b.append(line + "\n");
                reader2.close();

            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Problem with file: " + fileEntry);
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    fileEntry.toString().replace("txt", "replace.txt")), utf8));
            writer.write(b.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okosora;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Acer
 */
public class VocabCompare {

    public static void main(String[] args) {

        //magyarlanc morfologiai outputjai
        final File fold = new File("d:\\okosora\\annotations\\november_minbizt\\");

        compare(fold);

    }

    private static void compare(File fold) {

        for (final File fileEntry1 : fold.listFiles()) {
            String filename1 = fileEntry1.getName();
            Set<String> list1 = new HashSet<>();
            if (filename1.endsWith(".out") //&& filename1.contains("KG-jav(1)")
                    ) {
                String[][][] dokument1 = read(fold + "/" + filename1);

                for (String[][] sentences : dokument1) {
                    for (String[] word : sentences) {
                        list1.add(word[0]);

                    }

                }

                for (File fileEntry2 : fold.listFiles()) {
                    String filename2 = fileEntry2.getName();
                    Set<String> list2 = new HashSet<>();

                    if (filename2.endsWith(".out") //&& filename2.contains("O_S") 
                            && !filename1.equals(filename2)) {

                        String[][][] dokument2 = read(fold + "/" + filename2);

                        for (String[][] sentences2 : dokument2) {
                            for (String[] word2 : sentences2) {
                                list2.add(word2[0]);
                            }

                        }

                        int wordnum1 = list1.size();
                        int wordnum2 = list2.size();
                        int sameword1 = 0;
                        int sameword2 = 0;

                        for (String string1 : list1) {
                    //        System.out.println("1-\t" + string1);
                            for (String string2 : list2) {
                                if (string1.equals(string2)) {
                                    sameword1++;
                                }
                            }
                        }

                        for (String string2 : list2) {
                    //        System.out.println("2-\t" + string2);
                            for (String string1 : list1) {
                                if (string1.equals(string2)) {
                                    sameword2++;
                                }
                            }
                        }

                        System.out.println(filename1 + "---" + filename2);
                        System.out.println(sameword1 / (double) wordnum1);
                        System.out.println(sameword2 / (double) wordnum2);

                    }
                }

            }
        }
    }

    static String[][][] read(String file) {
        BufferedReader bufferedReader = null;
        String line = null;

        List<String[]> sentence = null;
        List<String[][]> sentences = null;
        sentence = new ArrayList<String[]>();
        sentences = new ArrayList<String[][]>();

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));

            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().equals("")) {
                    sentences.add(sentence.toArray(new String[sentence.size()][]));
                    sentence = new ArrayList<String[]>();
                } else {
                    sentence.add(line.split("\t"));
                }
            }
            bufferedReader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sentences.toArray(new String[sentence.size()][][]);
    }

}

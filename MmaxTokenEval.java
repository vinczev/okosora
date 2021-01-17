/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okosora;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Acer
 */
public class MmaxTokenEval {

    public static void main(String[] args) {
        File fold = new File("d:\\okosora\\annotations\\minbizt_dec\\");

        for (final File file1 : fold.listFiles()) {
            if (file1.getName().contains("uncertainty")) {
                for (final File file2 : fold.listFiles()) {
                    if (file2.getName().contains("uncertainty")) {
                        System.out.print(file1.getName() + " --- " + file2.getName() + "\t");

//for uncertainty and politeness
                        ArrayList<MmaxTokenObject> list1 = new ArrayList<>();
                        ArrayList<MmaxTokenObject> list2 = new ArrayList<>();

                        list1 = extractAnnot(file1);
                        list2 = extractAnnot(file2);

//   eval(list1, list2);
                        evalMainLevel(list1, list2);
                        //      evalType(list1, list2);

                        //for gossip
                        ArrayList<MmaxTokenGossipObject> list3 = new ArrayList<>();
                        ArrayList<MmaxTokenGossipObject> list4 = new ArrayList<>();
                        //     list3 = extractGossipAnnot(file1);
                        //     list4 = extractGossipAnnot(file2);
                        //  evalGossip(list3, list4);
                        // evalMainGossipLevel(list3, list4);
                        //   evalTypeGossip(list3, list4);
                        //    System.out.println(list1.size() + "\t" + list2.size());

                        //  System.out.println(file1);
                        //  countType(list3);
                    }
                }
            }
        }
    }

    private static ArrayList<MmaxTokenObject> extractAnnot(File file) {

        ArrayList<MmaxTokenObject> list = new ArrayList<>();
        Document document = null;
        NodeList markables = null;
        Node markable = null;

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(file);

            markables = document.getElementsByTagName("markable");

            for (int i = 0; i < markables.getLength(); ++i) {

                String type = "";
                String span = "";
                String id1 = "";
                markable = markables.item(i);
                NamedNodeMap markableNodeMap = null;

                markableNodeMap = markable.getAttributes();

                type = markableNodeMap.getNamedItem("type").getTextContent();

                id1 = markableNodeMap.getNamedItem("id").getTextContent();

                span = markableNodeMap.getNamedItem("span").getTextContent();
                span = span.replaceAll("word_", "");

                for (int m = 0; m < markables.getLength(); ++m) {
                    Node markable2 = markables.item(m);
                    NamedNodeMap markableNodeMap2 = null;

                    markableNodeMap2 = markable2.getAttributes();
                    String span2 = markableNodeMap2.getNamedItem("span").getTextContent();
                    span2 = span2.replaceAll("word_", "");
                    String id2 = markableNodeMap2.getNamedItem("id").getTextContent();

                    //      if (span.equals(span2) && !id1.equals(id2)) {
                    //          System.out.println(span);
                    //      }
                }

                int start = 0;
                int stop = 0;

                if (span.contains("..")) {
                    start = Integer.parseInt(span.split("\\.\\.")[span.split("\\.\\.").length - 2]);
                    stop = Integer.parseInt(span.split("\\.\\.")[span.split("\\.\\.").length - 1]);

                } else {
                    start = Integer.parseInt(span);
                    stop = Integer.parseInt(span);
                }

                for (int k = start; k <= stop; k++) {
                    MmaxTokenObject mmaxObj = new MmaxTokenObject();
                    mmaxObj.setType(type);
                    mmaxObj.setNumber(k);

                    Document document2 = null;
                    NodeList words = null;
                    Node word = null;
                    //File file2 = new File(file.toString().replace("_politeness_markables", ""));
                    // File file2 = new File(file.toString().replace("_uncertainty_markables", ""));

                    File file2 = new File("d:\\okosora\\annotations\\politeness\\voice_1106_042910mp.txt.xml");
                    StringBuilder text = new StringBuilder();

                    document2 = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .parse(file2);

                    words = document2.getElementsByTagName("word");

                    for (int j = 0; j < words.getLength(); ++j) {
                        String id = "";
                        word = words.item(j);
                        NamedNodeMap wordNodeMap = null;

                        wordNodeMap = word.getAttributes();

                        id = wordNodeMap.getNamedItem("id").getTextContent();
                        id = id.replace("word_", "");

                        if (Integer.parseInt(id) == mmaxObj.getNumber()) {

                            text.append(word.getTextContent());
                        }
                    }

                    mmaxObj.setText(text.toString());

                    list.add(mmaxObj);
                }

            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //       for (MmaxTokenObject mmaxObject : list) {
        //           System.out.println(mmaxObject);
        //       }
        return list;

    }

    private static void eval(ArrayList<MmaxTokenObject> list1, ArrayList<MmaxTokenObject> list2) {

        try {
            Document document2 = null;
            NodeList words = null;
            Node word = null;
            //File file2 = new File(file.toString().replace("_politeness_markables", ""));
            // File file2 = new File(file.toString().replace("_uncertainty_markables", ""));

            File file2 = new File("d:\\okosora\\annotations\\politeness\\voice_1106_042910mp.txt.xml");
            StringBuilder text = new StringBuilder();

            document2 = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(file2);

            words = document2.getElementsByTagName("word");

            int TP = 0;
            int FP = 0;
            int FN = 0;
            int TN = 0;
            double precision = 0;
            double recall = 0;
            double fScore = 0;
            double kappa = 0;
            double a = 0;
            double b = 0;
            double exp = 0;
            double obs = 0;
            double acc = 0;

            for (MmaxTokenObject mmaxObject1 : list1) {

                for (MmaxTokenObject mmaxObject2 : list2) {

                    if (//mmaxObject1.getType().equals(mmaxObject2.getType())
                            // && 
                            mmaxObject1.getNumber() == mmaxObject2.getNumber()) {
                        TP++;
                    }

                }

            }

            FN = list1.size() - TP;
            FP = list2.size() - TP;
            TN = words.getLength() - TP - FP - FN;
            int sum = TP + FP + FN + TN;

            precision = TP / (double) (TP + FP);
            recall = TP / (double) (TP + FN);

            fScore = 2 * (precision * recall) / (double) (precision + recall);
            acc = (TP + TN) / (double) sum;

            System.out.print("precision:\t" + precision + "\trecall:\t" + recall + "\tF-score:\t" + fScore + "\tAccuracy:\t"
                    + acc + "\t");

            a = ((TP + FN) / (double) sum) * ((TP + FP) / (double) sum);
            b = ((FP + TN) / (double) sum) * ((FN + TN) / (double) sum);

            exp = a + b;
            obs = (TP + TN) / (double) sum;

            kappa = (obs - exp) / (double) (1 - exp);
            System.out.println("Kappa:\t" + kappa);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static ArrayList<MmaxTokenGossipObject> extractGossipAnnot(File file) {
        ArrayList<MmaxTokenGossipObject> list = new ArrayList<>();
        Document document = null;
        NodeList markables = null;
        Node markable = null;

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(file);

            markables = document.getElementsByTagName("markable");

            for (int i = 0; i < markables.getLength(); ++i) {
                String type = "";
                String span = "";
                boolean normative = false;
                boolean relation = false;
                String source = "nincs";
                String target1 = "nincs";
                String target2 = "nincs";
                String polarity = "nincs";
                String id1 = "";
                //  MmaxTokenGossipObject mmaxGObj = new MmaxTokenGossipObject();
                markable = markables.item(i);
                NamedNodeMap markableNodeMap = null;

                markableNodeMap = markable.getAttributes();

                type = markableNodeMap.getNamedItem("type").getTextContent();
                //  mmaxGObj.setType(type);

                id1 = markableNodeMap.getNamedItem("id").getTextContent();

                //  source = markableNodeMap.getNamedItem("source").getTextContent();
                //   mmaxGObj.setSource(source);
                span = markableNodeMap.getNamedItem("span").getTextContent();
                span = span.replaceAll("word_", "");

                int start = 0;
                int stop = 0;

                if (span.contains("..")) {
                    start = Integer.parseInt(span.split("\\.\\.")[span.split("\\.\\.").length - 2]);
                    stop = Integer.parseInt(span.split("\\.\\.")[span.split("\\.\\.").length - 1]);

                } else {
                    start = Integer.parseInt(span);
                    stop = Integer.parseInt(span);
                }

                for (int m = 0; m < markables.getLength(); ++m) {
                    Node markable2 = markables.item(m);
                    NamedNodeMap markableNodeMap2 = null;

                    markableNodeMap2 = markable2.getAttributes();
                    String span2 = markableNodeMap2.getNamedItem("span").getTextContent();
                    span2 = span2.replaceAll("word_", "");
                    String id2 = markableNodeMap2.getNamedItem("id").getTextContent();

                    if (span.equals(span2) && !id1.equals(id2)) {
                        //       System.out.println(span);
                    }
                }

                for (int k = start; k <= stop; k++) {

                    if (!type.endsWith("_reaction")) {
                        MmaxTokenGossipObject mmaxGObj = new MmaxTokenGossipObject();

                        mmaxGObj.setType(type);
                        mmaxGObj.setNumber(k);
                        source = markableNodeMap.getNamedItem("source").getTextContent();

                        target1 = markableNodeMap.getNamedItem("target1").getTextContent();

                        polarity = markableNodeMap.getNamedItem("polarity").getTextContent();

                        if (markableNodeMap.getNamedItem("relation").getTextContent().equals("yes")) {
                            relation = true;

                        }

                        if (markableNodeMap.getNamedItem("normative").getTextContent().equals("yes")) {
                            normative = true;
                        }

                        if (relation) {
                            target2 = markableNodeMap.getNamedItem("target2").getTextContent();
                        }

                        mmaxGObj.setSource(source);
                        mmaxGObj.setTarget1(target1);
                        mmaxGObj.setPolarity(polarity);

                        mmaxGObj.setRelation(relation);
                        mmaxGObj.setNormative(normative);

                        mmaxGObj.setTarget2(target2);

                        Document document2 = null;
                        NodeList words = null;
                        Node word = null;
                        //File file2 = new File(file.toString().replace("_gossip_markables", ""));
                        File file2 = new File("d:\\okosora\\annotations\\gossip\\voice_1106_042910mp.txt.xml");
                        StringBuilder text = new StringBuilder();

                        document2 = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                                .parse(file2);

                        words = document2.getElementsByTagName("word");

                        for (int j = 0; j < words.getLength(); ++j) {
                            String id = "";
                            word = words.item(j);
                            NamedNodeMap wordNodeMap = null;

                            wordNodeMap = word.getAttributes();

                            id = wordNodeMap.getNamedItem("id").getTextContent();
                            id = id.replace("word_", "");

                            if (Integer.parseInt(id) == mmaxGObj.getNumber()) {

                                text.append(word.getTextContent());
                            }
                        }

                        mmaxGObj.setText(text.toString());

                        list.add(mmaxGObj);

                    }
                }
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //       for (MmaxTokenGossipObject mmaxGossipObject : list) {
        //           System.out.println(mmaxGossipObject);
        //       }
        return list;
    }

    private static void evalGossip(ArrayList<MmaxTokenGossipObject> list3, ArrayList<MmaxTokenGossipObject> list4) {

        int TP = 0;
        int FP = 0;
        int FN = 0;
        int TN = 0;
        double precision = 0;
        double recall = 0;
        double fScore = 0;
        double a = 0;
        double b = 0;
        double obs = 0;
        double exp = 0;
        double acc = 0;

        try {
            Document document2 = null;
            NodeList words = null;
            Node word = null;
            //File file2 = new File(file.toString().replace("_politeness_markables", ""));
            // File file2 = new File(file.toString().replace("_uncertainty_markables", ""));

            File file2 = new File("d:\\okosora\\annotations\\politeness\\voice_1106_042910mp.txt.xml");
            StringBuilder text = new StringBuilder();

            document2 = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(file2);

            words = document2.getElementsByTagName("word");

            for (MmaxTokenGossipObject mmaxGObject3 : list3) {

                for (MmaxTokenGossipObject mmaxGObject4 : list4) {

                    if (//mmaxGObject3.getType().equals(mmaxGObject4.getType())
                            //   &&
                            mmaxGObject3.getNumber() == mmaxGObject4.getNumber() //   && mmaxGObject3.getPolarity().equals(mmaxGObject4.getPolarity())
                            //    && mmaxGObject3.getSource().equals(mmaxGObject4.getSource())
                            //  && (mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget1())
                            //          && mmaxGObject3.getTarget2().equals(mmaxGObject4.getTarget2()) ||
                            //          mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())
                            //          && mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2()))
                            //      && mmaxGObject3.isNormative() == (mmaxGObject4.isNormative())
                            //       && mmaxGObject3.isRelation() == (mmaxGObject4.isRelation())
                            ) {
                        TP++;
                    }

                }

            }

            FN = list3.size() - TP;
            FP = list4.size() - TP;
            TN = words.getLength() - TP - FP - FN;
            int sum = TP + FN + FP + TN;

            precision = TP / (double) (TP + FP);
            recall = TP / (double) (TP + FN);

            fScore = 2 * (precision * recall) / (double) (precision + recall);
            acc = (TP + TN) / (double) sum;

            System.out.print("precision:\t" + precision + "\trecall:\t" + recall + "\tF-score:\t" + fScore + "\tAccuracy:\t"
                    + acc + "\t");

            double kappa = 0;

            a = ((TP + FN) / (double) sum) * ((TP + FP) / (double) sum);
            b = ((FP + TN) / (double) sum) * ((FN + TN) / (double) sum);
            exp = a + b;
            obs = (TP + TN) / (double) sum;

            kappa = (obs - exp) / (double) (1 - exp);

            System.out.println("Kappa:\t" + kappa);

            System.out.println("     | yes | no |");
            System.out.println(" yes |" + TP + " | " + FP + "|");
            System.out.println(" no  |" + FN + " | " + TN + "|");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void evalTypeGossip(ArrayList<MmaxTokenGossipObject> list3, ArrayList<MmaxTokenGossipObject> list4) {

        Set<String> types = new HashSet<>();
        for (MmaxTokenGossipObject mmaxTokenGossipObject : list4) {
            types.add(mmaxTokenGossipObject.getType());
        }

        for (String type : types) {

            System.out.print(type + "\t");
            int TP = 0;
            int FP = 0;
            int FN = 0;
            int TN = 0;
            double precision = 0;
            double recall = 0;
            double fScore = 0;
            double a = 0;
            double b = 0;
            double obs = 0;
            double exp = 0;
            double acc = 0;

            try {
                Document document2 = null;
                NodeList words = null;
                Node word = null;
                //File file2 = new File(file.toString().replace("_politeness_markables", ""));
                // File file2 = new File(file.toString().replace("_uncertainty_markables", ""));

                File file2 = new File("d:\\okosora\\annotations\\politeness\\voice_1106_042910mp.txt.xml");
                StringBuilder text = new StringBuilder();

                document2 = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(file2);

                words = document2.getElementsByTagName("word");

                for (MmaxTokenGossipObject mmaxGObject3 : list3) {

                    for (MmaxTokenGossipObject mmaxGObject4 : list4) {

                        if (mmaxGObject3.getType().equals(type)
                                && mmaxGObject4.getType().equals(type)
                                && mmaxGObject3.getNumber() == mmaxGObject4.getNumber()
                                //   && mmaxGObject3.getPolarity().equals(mmaxGObject4.getPolarity())
                                //   && mmaxGObject3.getSource().equals(mmaxGObject4.getSource())
                                && (mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget1())
                                && mmaxGObject3.getTarget2().equals(mmaxGObject4.getTarget2())
                                || mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())
                                && mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())) //   && mmaxGObject3.isNormative() == (mmaxGObject4.isNormative())
                                //   && mmaxGObject3.isRelation() == (mmaxGObject4.isRelation())
                                ) {
                            TP++;
                        }

                        if (mmaxGObject3.getType().equals(type)
                                && !mmaxGObject4.getType().equals(type)
                                && mmaxGObject3.getNumber() == mmaxGObject4.getNumber()
                                //    && mmaxGObject3.getPolarity().equals(mmaxGObject4.getPolarity())
                                //    && mmaxGObject3.getSource().equals(mmaxGObject4.getSource())
                                && (mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget1())
                                && mmaxGObject3.getTarget2().equals(mmaxGObject4.getTarget2())
                                || mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())
                                && mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())) //  && mmaxGObject3.isNormative() == (mmaxGObject4.isNormative())
                                //  && mmaxGObject3.isRelation() == (mmaxGObject4.isRelation())
                                ) {
                            FP++;
                        }

                        if (!mmaxGObject3.getType().equals(type)
                                && mmaxGObject4.getType().equals(type)
                                && mmaxGObject3.getNumber() == mmaxGObject4.getNumber()
                                //    && mmaxGObject3.getPolarity().equals(mmaxGObject4.getPolarity())
                                //    && mmaxGObject3.getSource().equals(mmaxGObject4.getSource())
                                && (mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget1())
                                && mmaxGObject3.getTarget2().equals(mmaxGObject4.getTarget2())
                                || mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())
                                && mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())) //    && mmaxGObject3.isNormative() == (mmaxGObject4.isNormative())
                                //    && mmaxGObject3.isRelation() == (mmaxGObject4.isRelation())
                                ) {
                            FN++;
                        }

                        if (!mmaxGObject3.getType().equals(type)
                                && !mmaxGObject4.getType().equals(type)
                                && mmaxGObject3.getNumber() == mmaxGObject4.getNumber()
                                //   && mmaxGObject3.getPolarity().equals(mmaxGObject4.getPolarity())
                                //    && mmaxGObject3.getSource().equals(mmaxGObject4.getSource())
                                && (mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget1())
                                && mmaxGObject3.getTarget2().equals(mmaxGObject4.getTarget2())
                                || mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())
                                && mmaxGObject3.getTarget1().equals(mmaxGObject4.getTarget2())) //     && mmaxGObject3.isNormative() == (mmaxGObject4.isNormative())
                                //     && mmaxGObject3.isRelation() == (mmaxGObject4.isRelation())
                                ) {
                            TN++;
                        }

                    }

                }

                int sum = TP + FN + FP + TN;

                precision = TP / (double) (TP + FP);
                recall = TP / (double) (TP + FN);

                fScore = 2 * (precision * recall) / (double) (precision + recall);
                acc = (TP + TN) / (double) sum;

                System.out.print("precision:\t" + precision + "\trecall:\t" + recall + "\tF-score:\t" + fScore
                        + "\tAccuracy:\t" + acc + "\t");

                double kappa = 0;

                a = ((TP + FN) / (double) sum) * ((TP + FP) / (double) sum);
                b = ((FP + TN) / (double) sum) * ((FN + TN) / (double) sum);
                exp = a + b;
                obs = (TP + TN) / (double) sum;

                kappa = (obs - exp) / (double) (1 - exp);

                System.out.println("Kappa:\t" + kappa);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void evalType(ArrayList<MmaxTokenObject> list1, ArrayList<MmaxTokenObject> list2) {
        Set<String> types = new HashSet<>();
        for (MmaxTokenObject mmaxTokenObject : list1) {
            types.add(mmaxTokenObject.getType());
        }

        for (String type : types) {

            System.out.print(type + "\t");

            try {
                Document document2 = null;
                NodeList words = null;
                Node word = null;
                //File file2 = new File(file.toString().replace("_politeness_markables", ""));
                // File file2 = new File(file.toString().replace("_uncertainty_markables", ""));

                File file2 = new File("d:\\okosora\\annotations\\politeness\\voice_1106_042910mp.txt.xml");
                StringBuilder text = new StringBuilder();

                document2 = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(file2);

                words = document2.getElementsByTagName("word");

                int TP = 0;
                int FP = 0;
                int FN = 0;
                int TN = 0;
                double precision = 0;
                double recall = 0;
                double fScore = 0;
                double kappa = 0;
                double a = 0;
                double b = 0;
                double exp = 0;
                double obs = 0;
                double acc = 0;

                for (MmaxTokenObject mmaxObject1 : list1) {

                    for (MmaxTokenObject mmaxObject2 : list2) {

                        if (mmaxObject1.getType().equals(type)
                                && mmaxObject2.getType().equals(type)
                                && mmaxObject1.getNumber() == mmaxObject2.getNumber()) {
                            TP++;
                        }

                        if (mmaxObject1.getType().equals(type)
                                && !mmaxObject2.getType().equals(type)
                                && mmaxObject1.getNumber() == mmaxObject2.getNumber()) {
                            FP++;
                        }

                        if (!mmaxObject1.getType().equals(type)
                                && mmaxObject2.getType().equals(type)
                                && mmaxObject1.getNumber() == mmaxObject2.getNumber()) {
                            FN++;
                        }

                        if (!mmaxObject1.getType().equals(type)
                                && !mmaxObject2.getType().equals(type)
                                && mmaxObject1.getNumber() == mmaxObject2.getNumber()) {
                            TN++;
                        }

                    }

                }

                int sum = TP + FP + FN + TN;

                precision = TP / (double) (TP + FP);
                recall = TP / (double) (TP + FN);

                fScore = 2 * (precision * recall) / (double) (precision + recall);
                acc = (TP + TN) / (double) sum;

                System.out.print("precision:\t" + precision + "\trecall:\t" + recall + "\tF-score:\t" + fScore
                        + "\tAccuracy:" + acc + "\t");

                a = ((TP + FN) / (double) sum) * ((TP + FP) / (double) sum);
                b = ((FP + TN) / (double) sum) * ((FN + TN) / (double) sum);

                exp = a + b;
                obs = (TP + TN) / (double) sum;

                kappa = (obs - exp) / (double) (1 - exp);
                System.out.println("Kappa:\t" + kappa);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void countType(ArrayList<MmaxTokenGossipObject> list3) {
        Set<String> types = new HashSet<>();
        for (MmaxTokenGossipObject mmaxTokenGossipObject : list3) {
            types.add(mmaxTokenGossipObject.getType());
        }

        for (String type : types) {
            int cntr = 0;

            for (MmaxTokenGossipObject mmaxTokenGossipObject : list3) {
                if (mmaxTokenGossipObject.getType().equals(type)) {
                    cntr++;
                }
            }
            System.out.println(type + ":\t" + cntr);
        }
    }

    private static void evalMainLevel(ArrayList<MmaxTokenObject> list1, ArrayList<MmaxTokenObject> list2) {

        int TP = 0;
        int FP = 0;
        int FN = 0;
        int TN = 0;
        double precision = 0;
        double recall = 0;
        double fScore = 0;
        double kappa = 0;
        double a = 0;
        double b = 0;
        double exp = 0;
        double obs = 0;
        double acc = 0;

        Set<Integer> list1num = new HashSet<>();
        Set<Integer> list2num = new HashSet<>();

        for (MmaxTokenObject mmaxObject1 : list1) {
            list1num.add(mmaxObject1.getNumber());
        }

        for (MmaxTokenObject mmaxObject2 : list2) {

            list2num.add(mmaxObject2.getNumber());
        }

        try {
            Document document2 = null;
            NodeList words = null;
            Node word = null;
            //File file2 = new File(file.toString().replace("_politeness_markables", ""));
            // File file2 = new File(file.toString().replace("_uncertainty_markables", ""));

            File file2 = new File("d:\\okosora\\mmax_annotations\\voice_0798_051310mp_NS.txt.xml");
            StringBuilder text = new StringBuilder();

            document2 = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(file2);

            words = document2.getElementsByTagName("word");

            int size = words.getLength();

            for (int i = 0; i < size; i++) {
                if (list1num.contains(i) && list2num.contains(i)) {
                    TP++;
                }
                if (list1num.contains(i) && !list2num.contains(i)) {
                    FP++;
                }

                if (!list1num.contains(i) && list2num.contains(i)) {
                    FN++;
                }

                if (!list1num.contains(i) && !list2num.contains(i)) {
                    TN++;
                }

            }

            int sum = TP + FP + FN + TN;

            precision = TP / (double) (TP + FP);
            recall = TP / (double) (TP + FN);

            fScore = 2 * (precision * recall) / (double) (precision + recall);
            acc = (TP + TN) / (double) sum;

            System.out.print("precision:\t" + precision + "\trecall:\t" + recall + "\tF-score:\t" + fScore
                    + "\tAccuracy:\t" + acc + "\t");

            a = ((TP + FN) / (double) sum) * ((TP + FP) / (double) sum);
            b = ((FP + TN) / (double) sum) * ((FN + TN) / (double) sum);

            exp = a + b;
            obs = (TP + TN) / (double) sum;

            kappa = (obs - exp) / (double) (1 - exp);
            System.out.println("Kappa:\t" + kappa);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void evalMainGossipLevel(ArrayList<MmaxTokenGossipObject> list1, ArrayList<MmaxTokenGossipObject> list2) {

        int TP = 0;
        int FP = 0;
        int FN = 0;
        int TN = 0;
        double precision = 0;
        double recall = 0;
        double fScore = 0;
        double kappa = 0;
        double a = 0;
        double b = 0;
        double exp = 0;
        double obs = 0;
        double acc = 0;

        Set<Integer> list1num = new HashSet<>();
        Set<Integer> list2num = new HashSet<>();

        for (MmaxTokenGossipObject mmaxObject1 : list1) {
            list1num.add(mmaxObject1.getNumber());
        }

        for (MmaxTokenGossipObject mmaxObject2 : list2) {

            list2num.add(mmaxObject2.getNumber());
        }

        try {
            Document document2 = null;
            NodeList words = null;
            Node word = null;
            //File file2 = new File(file.toString().replace("_politeness_markables", ""));
            // File file2 = new File(file.toString().replace("_uncertainty_markables", ""));

            File file2 = new File("d:\\okosora\\mmax_annotations\\voice_0728_050210mp_SzE.txt.xml");
            StringBuilder text = new StringBuilder();

            document2 = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(file2);

            words = document2.getElementsByTagName("word");

            int size = words.getLength();

            for (int i = 0; i < size; i++) {
                if (list1num.contains(i) && list2num.contains(i)) {
                    TP++;
                }
                if (list1num.contains(i) && !list2num.contains(i)) {
                    FP++;
                }

                if (!list1num.contains(i) && list2num.contains(i)) {
                    FN++;
                }

                if (!list1num.contains(i) && !list2num.contains(i)) {
                    TN++;
                }

            }

            int sum = TP + FP + FN + TN;

            precision = TP / (double) (TP + FP);
            recall = TP / (double) (TP + FN);

            fScore = 2 * (precision * recall) / (double) (precision + recall);
            acc = (TP + TN) / (double) sum;

            System.out.print("precision:\t" + precision + "\trecall:\t" + recall + "\tF-score:\t" + fScore
                    + "\tAccuracy:\t" + acc + "\t");

            a = ((TP + FN) / (double) sum) * ((TP + FP) / (double) sum);
            b = ((FP + TN) / (double) sum) * ((FN + TN) / (double) sum);

            exp = a + b;
            obs = (TP + TN) / (double) sum;

            kappa = (obs - exp) / (double) (1 - exp);
            System.out.println("Kappa:\t" + kappa);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MmaxTokenEval.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

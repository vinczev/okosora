package okosora;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class Txt2Mmax {

	public static void main(String[] args) {

		final File folder = new File("d:\\okosora\\conversion\\");
		listFilesForFolder(folder);
	}

	public static void listFilesForFolder(final File folder) {

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
			
				
//!!!!			//I.) endswith "out", II.) endwith "xml"
				
				if (fileEntry.toString().endsWith(".xml")
                                     && !fileEntry.toString().contains("_sentence_level")
                                        ) {
											
				//I.) a magyarlancos out-os kimenetére először le kell futtatni a sentcenceLevel és generate kódokat (57. és 59. sor)
				
				//II.) majd utána újrafuttatni a kódot, csak bemenetként a sentence_level nelkuli xml -t kell megadni
				//és a generateXml-t futtatni rá (58. sor)
				//keletkezik jópár fájl, de csak 3 kell nekik, az mmax, a sentence_level.xml és a sima xml
				
				
				//I.:
				//	sentenceLevel(fileEntry.toString());
				//	generate(fileEntry);
                     
				//II.:	
					
					generateXml(fileEntry.toString());
				
				}
                                
                                //out-bol generate es sentenceLevel
                                //sima (sentence_level nelkuli xml-bol generateXml
				
				System.out.println(fileEntry);
			}
		}
	}
        
        private static void sentenceLevel(String fileEntry) {
            	try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;

			docBuilder = docFactory.newDocumentBuilder();

			int wordCntr = 0;
                        int sentCntr = 0;
                        int wordinsentCntr = 0;
                        int start = 0;
                        int stop = 0;


			// root elements
			Document doc = docBuilder.newDocument();
			Element markables = doc.createElement("markables");
			
                        doc.appendChild(markables);

			BufferedReader reader = null;
			String line = null;

			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileEntry), "UTF-8"));

			while ((line = reader.readLine()) != null) {

				if (!line.equals("")) {
                                    wordinsentCntr++;
                                    wordCntr++;
                                    System.out.println(wordinsentCntr);
                                    if (wordinsentCntr == 1) {
                                        start = wordCntr;
                                    }
                                } else {
                                    stop = wordCntr;
					Node w = doc.createElement("markable");
					markables.appendChild(w);

					Attr attr = doc.createAttribute("id");
					attr.setValue("markable_" + sentCntr);
					((Element) w).setAttributeNode(attr);
                                        
                                        Attr span = doc.createAttribute("span");
					span.setValue("word_" + start + ".." + "word_" + stop);
					((Element) w).setAttributeNode(span);
                                        
                                        Attr level = doc.createAttribute("mmax_level");
					level.setValue("sentences");
					((Element) w).setAttributeNode(level);
                                        
                                        sentCntr++;
                                        wordinsentCntr = 0;

					
				}
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			DOMSource source = new DOMSource(doc);
			String out = fileEntry.toString().replace(".out", "") + "_sentence_level";
			StreamResult result = new StreamResult(new File(out));

			// Output to console for testing
			//	 StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			reader.close();
                        
                        replacer2(out);
                        
                        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        }

	private static void generateXml(String fileEntry) {

		BufferedWriter writer = null;
		String[] name = fileEntry.split("\\\\");
		System.out.println(name[name.length - 1]);
		String mmax = name[name.length - 1];
		String out = fileEntry.toString().replace(".xml", "") + ".mmax";

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					out), "UTF-8"));
			System.out.println("Writing...");

			writer.write("<?xml version=\"1.0\"?>" + "\n");
			writer.write("<mmax_project>" + "\n");
			writer.write("<turns></turns>" + "\n");
			writer.write("<words>" + mmax + "</words>" + "\n");
			writer.write("<gestures></gestures>" + "\n");
			writer.write("<keyactions></keyactions>" + "\n");
			writer.write("</mmax_project>" + "\n");

			writer.flush();

			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private static void generate(File filename) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;

			docBuilder = docFactory.newDocumentBuilder();

			int wordCntr = 1;


			// root elements
			Document doc = docBuilder.newDocument();
			Element words = doc.createElement("words");
			
                        doc.appendChild(words);

			BufferedReader reader = null;
			String line = null;

			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "UTF-8"));

			while ((line = reader.readLine()) != null) {

				if (!line.equals("")) {

					Node w = doc.createElement("word");
					w.appendChild(doc.createTextNode(line.split("\t")[0]));
					words.appendChild(w);

					Attr attr = doc.createAttribute("id");
					attr.setValue("word_" + wordCntr);
					((Element) w).setAttributeNode(attr);

					wordCntr++;
				}
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			DOMSource source = new DOMSource(doc);
			String out = filename.toString() + ".conv";
			StreamResult result = new StreamResult(new File(out));

			// Output to console for testing
			//	 StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			reader.close();

			replacer(out);


		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private static void replacer(String out) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String line;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					out.replace(".out.conv", "") + ".xml"), "UTF-8"));

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(out), "UTF-8"));


			while ((line = reader.readLine()) != null) {

				line = line.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", 
						"<?xml version=\'1.0\' encoding=\'UTF-8\'?>" + "\n" + 
						"<!DOCTYPE words SYSTEM \"words.dtd\">");

				line = line.replace("\"/>", "\"></word>");
				line = line.replace("/word>", "/word>" + "\n");
				line = line.replace("\"words.dtd\"><words><word", "\"words.dtd\">" + "\n" + "<words>"
				+ "\n" + "<word");

				writer.write(line);
				writer.flush();
			}

			reader.close();
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
        
        private static void replacer2(String out) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String line;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					out + ".xml"), "UTF-8"));

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(out), "UTF-8"));


			while ((line = reader.readLine()) != null) {

				line = line.replace( 
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>", 
                                        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                                              //  + "<!DOCTYPE markables SYSTEM \"markables.dtd\">");

				line = line.replace("<markables>", "<!DOCTYPE markables SYSTEM \"markables.dtd\"><markables xmlns=\"www.eml.org/NameSpaces/sentence\">");

				writer.write(line);
				writer.flush();
			}

			reader.close();
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
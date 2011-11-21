/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex;


 
 import org.pdfbox.cos.COSDocument;
 import org.pdfbox.pdfparser.PDFParser;
 import org.pdfbox.pdmodel.PDDocument;
 import org.pdfbox.pdmodel.PDDocumentInformation;
 import org.pdfbox.util.PDFTextStripper;

 import java.io.File;
 import java.io.FileInputStream;
 import java.io.PrintWriter;

 public class PDFConverter {

     PDFParser parser;
     String parsedText =null;
     PDFTextStripper pdfStripper;
     PDDocument pdDoc;
     COSDocument cosDoc;
     PDDocumentInformation pdDocInfo;

      
     public PDFConverter() {
     }

     // Extract text from PDF Document
     public String pdftoText(String pdfName) {
   
         System.out.println("Parsing text from PDF file " + pdfName + "....");
         File pdfFile = new File(pdfName);
   
         if (!pdfFile.isFile()) {
             System.out.println("File " + pdfName + " does not exist.");
             return null;
         }
   
         try {
             parser = new PDFParser(new FileInputStream(pdfFile));
         } catch (Exception e) {
             System.out.println("Unable to open PDF Parser.");
             e.printStackTrace();
         }
   
         try {
             parser.parse();
             cosDoc = parser.getDocument();
             pdfStripper = new PDFTextStripper();
             pdDoc = new PDDocument(cosDoc);
             parsedText = pdfStripper.getText(pdDoc);
         } catch (Exception e) {
             System.out.println("An exception occured in parsing the PDF Document.");
             e.printStackTrace();
             try {
                    if (cosDoc != null) cosDoc.close();
                    if (pdDoc != null) pdDoc.close();
                } catch (Exception e1) {
                e.printStackTrace();
             }
             return null;
         }
         System.out.println("Done.");
         return parsedText;
     }
     /*
     // Write the parsed text from PDF to a file
     void writeTexttoFile(String pdfText, String fileName) {
   
         System.out.println("\nWriting PDF text to output text file " + fileName + "....");
         try {
             PrintWriter pw = new PrintWriter(fileName);
             pw.print(pdfText);
             pw.close();  
         } catch (Exception e) {
             System.out.println("An exception occured in writing the pdf text to file.");
             e.printStackTrace();
         }
         System.out.println("Done.");
     }

      * 
      */
     //Extracts text from a PDF Document and writes it to a text file
     public static void main(String args[]) {
   
         PDFConverter pdfTextParserObj = new PDFConverter();
         String pdfToText = pdfTextParserObj.pdftoText("/Users/anderson/Documents/Dropbox/Livros/CASE STUDY - Patterns for Distributed Scrum.pdf");
   
         if (pdfToText == null) {
             System.out.println("PDF to Text Conversion failed.");
         }
         else {
             System.out.println("\nThe text parsed from the PDF Document....\n" + pdfToText);
             //pdfTextParserObj.writeTexttoFile(pdfToText, args[1]);
         }
     }
      
      
 }
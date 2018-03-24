package poczta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.swing.table.DefaultTableModel;

public class Poczta_pop3 {

   public static NoSuchProviderException ErrorCode = null;
   public static MessagingException ErrorCode2 = null;
   public static Exception ErrorCode3 = null;
   public static Session emailSession;
   
   public static DefaultTableModel usunWiersze(DefaultTableModel model){
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
       return model;
    }

   public static Store check(String host, String storeType, String user, String password,Store store) 
   {
      try {     
      Properties properties = new Properties();

      properties.put("imap.gmail.com.host", host);
      properties.put("imap.gmail.com.port", "995");
      properties.put("imap.gmail.com.starttls.enable", "true");
      properties.setProperty("mail.store.protocol", "imaps");
      Session emailSession = Session.getDefaultInstance(properties);
  
      store = emailSession.getStore("imaps");

      store.connect(host, user, password);

      } catch (NoSuchProviderException e) {
          e.printStackTrace();
          ErrorCode = e;
      } catch (MessagingException e) {
         e.printStackTrace();
         ErrorCode2 = e;
      } catch (Exception e) { 
         e.printStackTrace();
         ErrorCode3 = e;
      }
      return store;
   }

   
    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }
   
    private static String getTextFromMimeMultipart(
        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        
    return result;
}
    
        public static void run(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection connection = DriverManager.getConnection("jdbc:derby:myDb;create=false;user=test;password=test");
            Statement stm = connection.createStatement();

            // tworzenie tabelki. Na razie nie potrzebne. Robie to ręcznie w folderze z aplikacja
            /*/stm.execute("CREATE TABLE px_email("+
                 "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"+
                 "subject VARCHAR(256)," +
                 "text VARCHAR(256)," +
                 "email VARCHAR(256))");/*/ 

            //stm = connection.createStatement();
            //stm.execute("INSERT INTO px_email(subject, text, email) VALUES('test', 'test2', 'm.normans@gmail.com') ");
            stm.close();   
            connection.close();
       } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
       }
    }

    public static String ConverDate(Date Date){
        String date = Date.toString();
        String clearDate="";
        switch (date.substring(4,7)) {
        case "Jan":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Styczeń "+date.substring(date.length()-4,date.length());
          break;
        case "Feb":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Luty "+date.substring(date.length()-4,date.length());
          break;
        case "Mar":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Marzec "+date.substring(date.length()-4,date.length());
          break;
        case "Apr":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Kwiecień "+date.substring(date.length()-4,date.length());
          break;
        case "May":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Maj "+date.substring(date.length()-4,date.length());
          break;
        case "Jun":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Czerwiec "+date.substring(date.length()-4,date.length());
          break;          
        case "Jul":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Lipiec "+date.substring(date.length()-4,date.length());
          break;
        case "Aug":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Sierpień "+date.substring(date.length()-4,date.length());
          break;     
        case "Sep":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Wrzesień "+date.substring(date.length()-4,date.length());
          break;     
        case "Oct":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Październik "+date.substring(date.length()-4,date.length());
          break;     
        case "Nov":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Listopad "+date.substring(date.length()-4,date.length());
          break; 
        case "Dec":
          clearDate=date.substring(date.length()-20,date.length()-18)+" Grudzień "+date.substring(date.length()-4,date.length());
          break; 
          
   }
        
        
        return clearDate;
    }
    
   
    public static void Odbierz(String host, String storeType, String user, String password, Store store, int imap) throws MessagingException, IOException, ClassNotFoundException, SQLException{
      check(host, storeType, user, password, store);
      Folder emailFolderOutBox,emailFolderInBox = null;
      Message message = null;
      String Content,email = "";
      Address[] froms,recipients = null;
      ArrayList<String> toAddresses = new ArrayList<String>();
      Klient klient = Klient.klient;
      
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      Connection connection = DriverManager.getConnection("jdbc:derby:"+System.getProperty("user.dir")+"\\DB;create=false");
      Statement stm = connection.createStatement();

            // tworzenie tabelki. Na razie nie potrzebne. Robie to ręcznie w folderze z aplikacja
            /*/stm.execute("CREATE TABLE px_email("+
                 "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"+
                 "subject VARCHAR(256)," +
                 "text VARCHAR(256)," +
                 "email VARCHAR(256))");/*/ 

            //stm = connection.createStatement();
            //stm.execute("INSERT INTO px_email(subject, text, email) VALUES('test', 'test2', 'm.normans@gmail.com') ");


  
      javax.mail.Folder[] folders = store.getDefaultFolder().list("*");
      /*/for (javax.mail.Folder folder : folders) {
            if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
                //System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
            } // wszystkie foldery
      }/*/
      emailFolderInBox = store.getFolder("INBOX");
      emailFolderOutBox = store.getFolder("[Gmail]/Wysłane");
      
      emailFolderInBox.open(Folder.READ_WRITE);
      emailFolderOutBox.open(Folder.READ_WRITE);
      Message[] messagesInBox = emailFolderInBox.getMessages();
      Message[] messagesOutBox = emailFolderOutBox.getMessages();
      DefaultTableModel model = (DefaultTableModel) klient.jTable1.getModel();
      usunWiersze(model);
      stm.execute("DELETE FROM px_inbox");
      stm.execute("DELETE FROM px_outbox");
      for (int i = 0, n = messagesInBox.length; i < n; i++) {
        message = messagesInBox[i];

        Content = getTextFromMessage(message);
        froms = message.getFrom(); 
         
        email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
        stm.execute("INSERT INTO px_inbox(subject, contents, sender, date) VALUES('"+message.getSubject()+"','"+Content+"','"+email+"','"+ConverDate(message.getReceivedDate())+"')");
        //if (imap == 1) model.addRow(new Object[]{email,message.getSubject(),Content,message.getReceivedDate()});  
        //if (imap == 0) model.addRow(new Object[]{toAddresses.get(i),message.getSubject(),Content,message.getReceivedDate()});                               
      }
      
      for (int i = 0, n = messagesOutBox.length; i < n; i++) {
        message = messagesOutBox[i];
        
        Content = getTextFromMessage(message);
        froms = message.getFrom();
        
       for (Address address : message.getRecipients(Message.RecipientType.TO)) {
            toAddresses.add(address.toString());
       }
                
        stm.execute("INSERT INTO px_outbox(subject, contents, receiver, date) VALUES('"+message.getSubject()+"','"+Content+"','"+toAddresses.get(i)+"','"+ConverDate(message.getReceivedDate())+"')");
      }

      //close the store and folder objects
      emailFolderInBox.close(false);
      emailFolderOutBox.close(false);
      store.close();
      stm.close();   
      connection.close();
   }


}
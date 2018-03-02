
package poczta;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Timer;
import javax.xml.bind.DatatypeConverter;



public class Login extends javax.swing.JFrame {
    
    public static String login = "";
    public static String pass = "";
    public static ImageIcon iconImage;
    File file;
    private Component frame;
    DataInputStream in;
    DataOutputStream out;
    byte[] bytesEncodedLogin,bytesEncodedPass,valueDecodedLogin,valueDecodedPass;
    public Login() throws FileNotFoundException, IOException {
        initComponents();
        jLabel4.setVisible(false);
        iconImage = new ImageIcon(this.getClass().getResource("poczta.png"));
        this.setIconImage(iconImage.getImage());
        
        File f = new File("file.txt");
        if(f.exists() && !f.isDirectory() && f.length()>=1) { 
            try {
                readFromFile("file.txt");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
        
    }
 

    public void writeToFile(String login, String pass) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("file.txt"));
        
        bytesEncodedLogin = Base64.encodeBase64(login.getBytes());
        bytesEncodedPass = Base64.encodeBase64(pass.getBytes());
        
        if (bytesEncodedLogin.length > 0 && bytesEncodedPass.length > 0) out.write("1"+new String(bytesEncodedLogin)+ "#$#%" + new String(bytesEncodedPass)+ "##$%" + new String(bytesEncodedLogin)
        + "#$###" + new String(bytesEncodedPass));
        out.close();
    }
    
    public void readFromFile(String file) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        String isCheck = "0";
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
              /*/  while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }/*/
                isCheck = line.substring(0, 1); 
                if (isCheck.equals("1")){
                    
                    jCheckBox1.setSelected(true);               
                    String login = line.substring(1,line.indexOf("#$#%"));
                    String password = line.substring(line.indexOf("#$#%")+4,line.indexOf("##$%"));
                    String test = line.substring(line.indexOf("##$%")+4,line.indexOf("#$###"));
                    String test2 = line.substring(line.indexOf("#$###")+4,line.length());                
                    bytesEncodedLogin = test.getBytes();
                    bytesEncodedPass = test2.getBytes();
                    if (!bytesEncodedLogin.equals("")) valueDecodedLogin = Base64.decodeBase64(bytesEncodedLogin);
                    jTextField1.setText(new String(valueDecodedLogin));
                    if (!bytesEncodedPass.equals("")) valueDecodedPass = Base64.decodeBase64(bytesEncodedPass);
                    jPasswordField1.setText(new String(valueDecodedPass));
                } else jCheckBox1.setSelected(false); 

                
            } finally {
                br.close();
            }
            
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Logowanie");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 60)); // NOI18N
        jLabel1.setText("Poczta");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("E-mail:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Hasło:");

        jPasswordField1.setName(""); // NOI18N

        jButton1.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jButton1.setText("Zaloguj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Zapamiętaj mnie");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Łączenie...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPasswordField1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Poczta_pop3 poczta = new Poczta_pop3();
        login = jTextField1.getText();
        pass = jPasswordField1.getText();
        String host = "imap.gmail.com";// change accordingly
        String mailStoreType = "imap";
        if (jTextField1.getText().isEmpty() || jPasswordField1.getText().isEmpty()) JOptionPane.showMessageDialog(frame, "Wypełnij lub popraw e-mail i hasło!", "Błąd logowania", JOptionPane.ERROR_MESSAGE); 
        else if (jTextField1.getText().indexOf("@") <=0) JOptionPane.showMessageDialog(frame, "Niepoprawny adres e-mail!", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
        else{
            if (poczta.ErrorCode == null && poczta.ErrorCode2 == null && poczta.ErrorCode3 == null){
               jButton1.setEnabled(false);
               jTextField1.setEnabled(false);
               jPasswordField1.setEnabled(false);
               Klient klient = null;
               try {
                    klient = new Klient();
                } catch (MessagingException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
               } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
               try {
                    if (jCheckBox1.isSelected()) writeToFile(login, pass);
                    else writeToFile("", "");
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                  
                JOptionPane.showMessageDialog(frame, "Poprawnie zalogowano do poczty!");  
                this.setVisible(false);
                klient.setVisible(true);
            }else {
               jTextField1.setText("");
               jPasswordField1.setText("");
               if (poczta.ErrorCode != null) JOptionPane.showMessageDialog(frame, poczta.ErrorCode, "Błąd logowania", JOptionPane.ERROR_MESSAGE); 
               else if (poczta.ErrorCode2 != null) JOptionPane.showMessageDialog(frame, poczta.ErrorCode2, "Błąd logowania", JOptionPane.ERROR_MESSAGE); 
               else if (poczta.ErrorCode3 != null) JOptionPane.showMessageDialog(frame, poczta.ErrorCode3, "Błąd logowania", JOptionPane.ERROR_MESSAGE); 
               else JOptionPane.showMessageDialog(frame, "Wystąpiły niespodziewane błędy!", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Login().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

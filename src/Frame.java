import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Frame extends JFrame {

    PrivateKey privateKey;
    PublicKey publicKey;

    public Frame() {

        this.setTitle("Hello world!");
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        JLabel textToEncryptLabel = new JLabel("Text to encrypt: ");
        c.gridx = 0;
        c.gridy = 0;
        this.add(textToEncryptLabel, c);

        JTextField textToEncryptField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        this.add(textToEncryptField, c);

        JLabel textToDecryptLabel = new JLabel("Text to decrypt: ");
        c.gridx = 0;
        c.gridy = 1;
        this.add(textToDecryptLabel, c);

        JTextField textToDecryptField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 1;
        this.add(textToDecryptField, c);

        JButton encryptButton = new JButton("Encrypt");
        c.gridx = 0;
        c.gridy = 2;
        this.add(encryptButton, c);

        JButton decryptButton = new JButton("Decrypt");
        c.gridx = 1;
        c.gridy = 2;
        this.add(decryptButton, c);

        JLabel encryptedTextField = new JLabel("Encrypted text: ");
        c.gridx = 0;
        c.gridy = 3;
        this.add(encryptedTextField, c);

        JTextField encryptedTextOutput = new JTextField(20);
        c.gridx = 1;
        c.gridy = 3;
        this.add(encryptedTextOutput, c);

        JLabel decryptedText = new JLabel("Decrypted text: ");
        c.gridx = 0;
        c.gridy = 4;
        this.add(decryptedText, c);

        JTextField decryptedTextOutPut = new JTextField(20);
        c.gridx = 1;
        c.gridy = 4;
        this.add(decryptedTextOutPut, c);

        JButton generateKeyPair = new JButton("Generate key pair");
        c.gridx = 0;
        c.gridy = 5;
        this.add(generateKeyPair, c);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem selectPrivateKey = new JMenuItem("Select private key");
        JMenuItem selectPublicKey = new JMenuItem("Select public key");
        menu.add(selectPrivateKey);
        menu.add(selectPublicKey);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        selectPrivateKey.addActionListener(e -> {
            String privateKey = fileSelector();
            if(privateKey == null) return;
            this.privateKey = Encryptor.convertToPrivateKey(privateKey);
            JOptionPane.showMessageDialog(null, "Private key selected!");
            System.out.print(this.privateKey);
        });

        selectPublicKey.addActionListener(e -> {
            String publicKey = fileSelector();
            if(publicKey == null) return;
            this.publicKey = Encryptor.convertToPublicKey(publicKey);
            JOptionPane.showMessageDialog(null, "Public key selected!");
            System.out.print(this.publicKey);
        });

        encryptButton.addActionListener(e -> {
            String text = textToEncryptField.getText();
            byte[] encryptedBytes = Encryptor.encrypt("RSA", this.publicKey, text.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            if (encryptedText.equals("")) {
                encryptedText = "Error encrypting text!";
            }
            encryptedTextOutput.setText(encryptedText);
        });

        decryptButton.addActionListener(e -> {
            String encryptedText = textToDecryptField.getText();
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = Encryptor.decrypt("RSA", this.privateKey, encryptedBytes);
            String decryptedTextOutput = new String(decryptedBytes);
            if (decryptedTextOutput.equals("")) {
                decryptedTextOutput = "Error decrypting text!";
            }
            decryptedTextOutPut.setText(decryptedTextOutput);
        });

        generateKeyPair.addActionListener(e -> {
            KeyPair keyPair = Encryptor.generatePrivateKey();
            if(keyPair == null){
                decryptedTextOutPut.setText("Error generating key pair!");
                return;
            }
            try {
                String privateKey = Encryptor.convertToString(keyPair.getPrivate());
                String publicKey = Encryptor.convertToString(keyPair.getPublic());

                BufferedWriter privateKeyWriter = new BufferedWriter(new java.io.FileWriter("privateKey.txt"));
                BufferedWriter publicKeyWriter = new BufferedWriter(new java.io.FileWriter("publicKey.txt"));

                privateKeyWriter.write(privateKey);
                publicKeyWriter.write(publicKey);

                privateKeyWriter.flush(); privateKeyWriter.close();
                publicKeyWriter.flush(); publicKeyWriter.close();

            } catch (Exception ex){
                decryptedTextOutPut.setText(ex.getMessage());
            }
        });
        this.setVisible(true);
    }

    public String fileSelector() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);

        if(fileChooser.getSelectedFile() == null) return null;

        File file = fileChooser.getSelectedFile();

        BufferedReader bufferedReader = null;

        if (JFileChooser.APPROVE_OPTION == 0) {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                return bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);

            } finally {
                try {
                    if (bufferedReader != null) bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}

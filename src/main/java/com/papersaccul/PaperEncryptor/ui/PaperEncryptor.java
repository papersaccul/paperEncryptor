package com.papersaccul.PaperEncryptor.ui;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import com.formdev.flatlaf.FlatDarkLaf;
import com.papersaccul.PaperEncryptor.encryption.CaesarCipher;
import com.papersaccul.PaperEncryptor.encryption.EncryptionAlgorithm;
import com.papersaccul.PaperEncryptor.encryption.PaperCipher;
import com.papersaccul.PaperEncryptor.encryption.RailFenceCipher;
import com.papersaccul.PaperEncryptor.encryption.XORCipher;
import com.papersaccul.PaperEncryptor.encryption.asciiCipher;
import com.papersaccul.PaperEncryptor.encryption.base64Cipher;
import com.papersaccul.PaperEncryptor.encryption.binCipher;
import com.papersaccul.PaperEncryptor.encryption.md5Cipher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.util.Arrays;
import java.net.URL;

public class PaperEncryptor extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JComboBox<String> algorithmComboBox;
    private JComboBox<String> charsetComboBox;
    private JTextField keyField;
    private JRadioButton encryptMode;
    private JRadioButton decryptMode;
    private JLabel keyLabel;
    private JButton copyButton;
    private JButton processButton; 

    public PaperEncryptor() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Paper Encryptor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        inputTextArea = new JTextArea(5, 20);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        outputTextArea = new JTextArea(5, 20);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        algorithmComboBox = new JComboBox<>(new String[]{"Caesar", "XOR", "Paper", "Bin", "Base64", "Rail Fence", "MD5", "ASCII"});
        charsetComboBox = new JComboBox<>(new String[]{"UTF-8", "UTF-16", "UTF-32", "ASCII",  "KOI8-R", "KOI8-U", "Windows-1251", "Windows-1252", "UTF-16BE", "UTF-16LE", "UTF-32BE", "UTF-32LE", "US-ASCII", "ISO-8859-2", "ISO-8859-3", "ISO-8859-4", "ISO-8859-6", "ISO-8859-7", "ISO-8859-8", "ISO-8859-9", "ISO-8859-10", "ISO-8859-13", "ISO-8859-14", "ISO-8859-16"});
        keyField = new JTextField(20);
        encryptMode = new JRadioButton("Encrypt", true);
        decryptMode = new JRadioButton("Decrypt");
        keyLabel = new JLabel("Key:");
        copyButton = new JButton("Copy");
        processButton = new JButton("Process"); 

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(encryptMode);
        modeGroup.add(decryptMode);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Input Text:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(inputTextArea, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Output Text:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(outputTextArea, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(algorithmComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Charset:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(charsetComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(keyLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(keyField, gbc);

        inputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                processText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                processText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                processText();
            }
        });

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processText();
            }
        });
        algorithmComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                java.util.List<String> noKeyAlgorithms = Arrays.asList("Base64", "Bin", "MD5", "ASCII");
                if (noKeyAlgorithms.contains(selectedAlgorithm)) {
                    keyField.setVisible(false);
                    keyLabel.setVisible(false);
                } else {
                    keyField.setVisible(true);
                    keyLabel.setVisible(true);
                }
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String outputText = outputTextArea.getText();
                StringSelection stringSelection = new StringSelection(outputText);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });


        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(encryptMode, gbc);
        gbc.gridx = 1;
        mainPanel.add(decryptMode, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        mainPanel.add(copyButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(processButton, gbc);
        URL iconURL = getClass().getResource("resources/paperEncryptorLogo.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Icon not found"); 
        }

        add(mainPanel);
        setVisible(true);
    }

    private void processText() {
        String inputText = inputTextArea.getText();
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        String selectedCharset = (String) charsetComboBox.getSelectedItem();
        String key = keyField.getText();

        EncryptionAlgorithm algorithm = null;
        switch (selectedAlgorithm) {
            case "Caesar":
                algorithm = new CaesarCipher();
                break;
            case "XOR":
                algorithm = new XORCipher();
                break;
            case "Paper":
                algorithm = new PaperCipher();
                break;
            case "Bin":
                algorithm = new binCipher();
                break;
            case "Base64":
                algorithm = new base64Cipher();
                break;
            case "Rail Fence":
                algorithm = new RailFenceCipher();
                break;
            case "MD5":
                algorithm = new md5Cipher();
                break;
            case "ASCII":
                algorithm = new asciiCipher();
                break;
        }

        if (algorithm != null) {
            String result = "";
            if (encryptMode.isSelected()) {
                result = algorithm.encrypt(inputText, key, selectedCharset);
            } else if (decryptMode.isSelected()) {
                result = algorithm.decrypt(inputText, key, selectedCharset);
            }
            outputTextArea.setText(result);
        }
    }

    public static void main(String[] args) {
        new PaperEncryptor();
    }
}


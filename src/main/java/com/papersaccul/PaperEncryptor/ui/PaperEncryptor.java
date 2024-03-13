package com.papersaccul.PaperEncryptor.ui;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf; 
import com.papersaccul.PaperEncryptor.encryption.CaesarCipher;
import com.papersaccul.PaperEncryptor.encryption.EncryptionAlgorithm;
import com.papersaccul.PaperEncryptor.encryption.PaperCipher;
import com.papersaccul.PaperEncryptor.encryption.RailFenceCipher;
import com.papersaccul.PaperEncryptor.encryption.XORCipher;
import com.papersaccul.PaperEncryptor.encryption.base64Cipher;
import com.papersaccul.PaperEncryptor.encryption.binCipher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.nio.charset.Charset;
import java.util.Arrays;

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
        algorithmComboBox = new JComboBox<>(new String[]{"Caesar Cipher", "XOR Cipher", "Paper Cipher", "Bin Cipher", "Base64", "Rail Fence Cipher"});
        charsetComboBox = new JComboBox<>(new String[]{"UTF-8", "ASCII", "ISO-8859-1", "UTF-16", "UTF-32", "KOI8-R", "Windows-1251", "Windows-1252", "ISO-8859-5", "ISO-8859-15"});
        keyField = new JTextField(20);
        encryptMode = new JRadioButton("Encrypt", true);
        decryptMode = new JRadioButton("Decrypt");
        keyLabel = new JLabel("Key:");
        copyButton = new JButton("Copy");

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

        JButton processButton = new JButton("Process");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextArea.getText();
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                String selectedCharset = (String) charsetComboBox.getSelectedItem();
                String key = keyField.getText();

                EncryptionAlgorithm algorithm = null;
                switch (selectedAlgorithm) {
                    case "Caesar Cipher":
                        algorithm = new CaesarCipher();
                        break;
                    case "XOR Cipher":
                        algorithm = new XORCipher();
                        break;
                    case "Paper Cipher":
                        algorithm = new PaperCipher();
                        break;
                    case "Bin Cipher":
                        algorithm = new binCipher();
                        break;
                    case "Base64":
                        algorithm = new base64Cipher();
                        break;
                    case "Rail Fence Cipher":
                        algorithm = new RailFenceCipher();
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
        });

        algorithmComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                java.util.List<String> noKeyAlgorithms = Arrays.asList("Base64", "Bin Cipher");
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
        mainPanel.add(processButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(copyButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PaperEncryptor();
    }
}

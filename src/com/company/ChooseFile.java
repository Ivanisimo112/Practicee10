package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class ChooseFile {
    private static JFrame jFrame;
    private static File textFile;

    public static void main(String[] args) {
        jFrame = jFrame();
        jFrame.setVisible(true);
    }

    private static JFrame jFrame() {
        JFrame jFrame = new JFrame("Відкривачка файлів");
        jFrame.setSize(500, 350);
        jFrame.setLocation(530, 250);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(menuPanel());
        menuPanel().setVisible(true);
        return jFrame;
    }

    private static JPanel menuPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel("Відкривачка файлів");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        JButton button = new JButton("Вибрати файл");
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                jFrame.setVisible(false);
                try {
                    findTxtFilePanel().setVisible(true);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        return panel;
    }

    private static JPanel findTxtFilePanel() throws FileNotFoundException {
        JPanel jPanel = new JPanel(new GridLayout());
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int showOpenDialog = jFileChooser.showOpenDialog(null);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setDialogTitle("Виберіть текстовий файл");
        FileNameExtensionFilter restrict = new FileNameExtensionFilter(".txt", "txt");
        jFileChooser.addChoosableFileFilter(restrict);

        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            textFile = jFileChooser.getSelectedFile();
            jFrame.add(infoPanel());
            jFrame.setVisible(true);
            infoPanel().setVisible(true);
        } else if (showOpenDialog == JFileChooser.CANCEL_OPTION) {
            jFrame.setVisible(false);
        }
        return jPanel;
    }

    private static JPanel infoPanel() throws FileNotFoundException {
        JPanel jPanel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(20, 20);
        textArea.setEditable(false);
        textArea.setCaretPosition(textArea.getDocument().getLength());
        FileReader fileReader = new FileReader(textFile);
        Scanner scanner = new Scanner(fileReader);


        while (scanner.hasNextLine()) {
            textArea.append(scanner.nextLine() + "\n");
        }

        try {
            fileReader.close();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        jPanel.add(scrollPane, BorderLayout.CENTER);
        JButton ok = new JButton("OК");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPanel.setVisible(false);
                jFrame.setVisible(false);
            }
        });
        jPanel.add(ok, BorderLayout.SOUTH);
        return jPanel;
    }
}

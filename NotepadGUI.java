import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotepadGUI extends JFrame {
    private JTextArea textArea;
    private boolean isDarkMode = false;
    private FileHandler fileHandler;
    
    public NotepadGUI() {
        setTitle("Advanced Notepad");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Text Area
        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem darkModeItem = new JMenuItem("Dark Mode");
        
        JMenuItem encryptItem = new JMenuItem("Encrypt");
        JMenuItem decryptItem = new JMenuItem("Decrypt");
        encryptItem.addActionListener(e -> encryptText());
        decryptItem.addActionListener(e -> decryptText());

        fileMenu.add(encryptItem);
        fileMenu.add(decryptItem);
        
        fileHandler = new FileHandler(textArea);
        

        openItem.addActionListener(e -> openFile());
        saveItem.addActionListener(e -> saveFile());
        exitItem.addActionListener(e -> System.exit(0));
        darkModeItem.addActionListener(e -> toggleDarkMode());

        JMenuItem findReplaceItem = new JMenuItem("Find & Replace");
        findReplaceItem.addActionListener(e -> openFindReplaceDialog());
        fileMenu.add(findReplaceItem);
        
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(darkModeItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                textArea.write(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void toggleDarkMode() {
        if (isDarkMode) {
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
        } else {
            textArea.setBackground(Color.DARK_GRAY);
            textArea.setForeground(Color.WHITE);
        }
        isDarkMode = !isDarkMode;
    }
    
    private void encryptText() {
    try {
        String encryptedText = Encryption.encrypt(textArea.getText());
        textArea.setText(encryptedText);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void decryptText() {
    try {
        String decryptedText = Encryption.decrypt(textArea.getText());
        textArea.setText(decryptedText);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    private void openFindReplaceDialog() {
    JDialog dialog = new JDialog(this, "Find & Replace", true);
    dialog.setSize(300, 150);
    dialog.setLayout(new GridLayout(3, 2));

    JLabel findLabel = new JLabel("Find:");
    JTextField findField = new JTextField();
    JLabel replaceLabel = new JLabel("Replace:");
    JTextField replaceField = new JTextField();
    JButton findButton = new JButton("Find");
    JButton replaceButton = new JButton("Replace");

    findButton.addActionListener(e -> findText(findField.getText()));
    replaceButton.addActionListener(e -> replaceText(findField.getText(), replaceField.getText()));

    dialog.add(findLabel);
    dialog.add(findField);
    dialog.add(replaceLabel);
    dialog.add(replaceField);
    dialog.add(findButton);
    dialog.add(replaceButton);
    dialog.setVisible(true);
}
    
    private void findText(String keyword) {
    String content = textArea.getText();
    int index = content.indexOf(keyword);
    
    if (index != -1) {
        textArea.setCaretPosition(index);
        textArea.select(index, index + keyword.length());
        textArea.requestFocus();
    } else {
        JOptionPane.showMessageDialog(this, "Text not found.");
    }
}

private void replaceText(String find, String replace) {
    String content = textArea.getText();
    textArea.setText(content.replace(find, replace));
}

    public static void main(String[] args) {
        new NotepadGUI();
    }
}

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextArea;

public class FileHandler {
    private File file;
    private JTextArea textArea;
    private Timer timer;

    public FileHandler(JTextArea textArea) {
        this.textArea = textArea;
        this.timer = new Timer(true);
        startAutoSave();
    }

    public void setFile(File file) {
        this.file = file;
    }

    private void startAutoSave() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (file != null) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(textArea.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 5000, 5000); // Auto-save every 5 seconds
    }
}

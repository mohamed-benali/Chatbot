import FileChooser.MyFileChooser;
import FileChooser.MyFileChooserImpl;
import main.Exceptions.NotBpmnTypeException;
import main.Entity.Parser.Parser;
import main.Exceptions.NoFreelingKeyException;
import main.Exceptions.SentenceAnalyzerException;
import main.Exceptions.SpinnerChief_SentenceParaphraserException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BpmnForm {
    private JButton selectBPMNModelButton;
    private JTextField progressText;
    private JTextField selectedFileText;
    private JPanel app;
    private JButton generateChatbotButton;
    private JProgressBar progressBar;


    private String bpmnPath;

    public BpmnForm() {
        bpmnPath = "";

        //progressBar.setValue(0);
        progressBar.setStringPainted(true);

        selectBPMNModelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFileChooser myFileChooser = new MyFileChooserImpl();
                myFileChooser.chooseFilePath();
                String fileName = myFileChooser.getFileName();
                bpmnPath = myFileChooser.getFilePath();


                selectedFileText.setText(fileName);
                JOptionPane.showMessageDialog(null, fileName);

            }
        });
        generateChatbotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parser parser = null;
                try {
                    progressText.setText("Reading BPMN...");
                    parser = new Parser(bpmnPath);
                    progressText.setText("Parsing BPMN");
                    parser.parse();
                    progressText.setText("Uploading Chatbot into DialogFlow");
                    parser.translateIntoDialogFlow();
                    progressText.setText("Done");
                    progressBar.setValue(100);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "File does not exist");
                    progressText.setText("File does not exist");
                    ex.printStackTrace();
                } catch (NotBpmnTypeException ex) {
                    JOptionPane.showMessageDialog(null, "File type is not a .bpmn");
                    progressText.setText("File type is not a .bpmn");
                    ex.printStackTrace();
                } catch (SentenceAnalyzerException |
                        InterruptedException |
                        SpinnerChief_SentenceParaphraserException |
                        NoFreelingKeyException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    progressText.setText(ex.getMessage());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    progressText.setText("Something went wrong");
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new BpmnForm().app);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(768, 480);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}

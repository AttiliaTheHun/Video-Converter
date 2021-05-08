package attilathehun.videoconverter;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;
import javax.swing.filechooser.FileFilter;

public class MainFrame extends JFrame {
    private static File chosenFile;
    public static LogGenerator logger;
    public static void main(String[] args){
        MainFrame frame = new MainFrame("Video Converter");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                AudioRickrollPlayer audioPlayer = new AudioRickrollPlayer();
                audioPlayer.start();
            }
        });
      //  frame.setSize(450,450);
        JPanel mainBox = new JPanel();
        mainBox.setLayout(new BoxLayout(mainBox, BoxLayout.Y_AXIS));
        JPanel horizontalPanel1 = new JPanel();
        horizontalPanel1.setLayout(new BoxLayout(horizontalPanel1, BoxLayout.X_AXIS));
        JLabel outputFileNameLabel = new JLabel("Output File Name:");
        horizontalPanel1.add((outputFileNameLabel));
        JTextField outputFileNameField = new JTextField(18);
        horizontalPanel1.add(outputFileNameField);
        JPanel horizontalPanel2 = new JPanel();
        horizontalPanel2.setLayout(new BoxLayout(horizontalPanel2, BoxLayout.X_AXIS));
        JLabel outputFileTypeLabel = new JLabel("Output File Type:");
        horizontalPanel2.add(outputFileTypeLabel);
        String[] availableOutputFileTypes = {"MP4", "MKV", "AVI"};
        JComboBox availableOutputFileTypesBox = new JComboBox(availableOutputFileTypes);
        horizontalPanel2.add(availableOutputFileTypesBox);
        JPanel horizontalPanel3 = new JPanel();
        horizontalPanel3.setLayout(new BoxLayout(horizontalPanel3, BoxLayout.X_AXIS));
        JButton selectFileButton = new JButton("Select File");
        selectFileButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setFileFilter(new FileFilter() {

                    public String getDescription() {
                        return "Video Files (*.mp4) (*.avi) (*.mkv)";
                    }

                    public boolean accept(File f) {
                        if (f.isDirectory()) {
                            return true;
                        } else {
                            String filename = f.getName().toLowerCase();
                            return filename.endsWith(".mp4") || filename.endsWith(".avi") || filename.endsWith(".mkv");
                        }
                    }
                });
                int returnValue = fileChooser.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    setChosenFile(selectedFile);
                    logger.log("File selected: " + selectedFile.getAbsolutePath());
                }
            }
        });
        horizontalPanel3.add(selectFileButton);
        JButton convertFileButton = new JButton("Convert");
        convertFileButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(MainFrame.hasFileChosen()){

                    String outputFileType = (String) availableOutputFileTypesBox.getItemAt(availableOutputFileTypesBox.getSelectedIndex());
                    Converter converter = new Converter();

                    converter.setInputFile(MainFrame.getChosenFile());
                    String outputFileName;
                    if(outputFileNameField.getText().trim().length() == 0){
                        outputFileName = MainFrame.getChosenFile().getName();
                        if(outputFileName.contains(".")){
                            outputFileName = outputFileName.substring(0, outputFileName.lastIndexOf("."));
                        }

                    }else{
                        outputFileName = outputFileNameField.getText().trim();
                    }

                    File outputFile = new File(chosenFile.getParent() + "/" + outputFileName + "." + outputFileType.toLowerCase(Locale.ROOT));
                    if(outputFile.exists()){
                        int overwrite = JOptionPane.showConfirmDialog(null, "The file named " + outputFileName +" already exists, should we overwrite it?", "Overwrite file?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if(overwrite == 2){
                            logger.log(("Conversion cancelled"));
                            return;
                        }
                    }
                    converter.setOutputFileName(outputFileName);
                    converter.setOutputFileType(outputFileType);

                    logger.log("Checking file integrity...");
                    logger.log("Loading libraries...");
                    logger.log("Converting using " + converter.getLibName(outputFileType) + "...");
                    logger.log("Checking output file integrity...");
                    logger.log("Saving file to the disk...");
                    converter.convert();
                }
            }
        });
        horizontalPanel3.add(convertFileButton);
        JButton openOutputFolderButton = new JButton("Open Output Folder");
        openOutputFolderButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                File outputFolder = new File(chosenFile.getParent());
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(outputFolder);
                }catch(Exception ex){
                    ex.printStackTrace(System.out);
                }
            }
        });
        horizontalPanel3.add(openOutputFolderButton);
        JPanel logAreaContainer = new JPanel();
        logAreaContainer.setLayout(new FlowLayout());
        JTextArea logTextArea = new JTextArea(10, 22);;
        logTextArea.setEnabled(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        new SmartScroller(logScrollPane, SmartScroller.HORIZONTAL, SmartScroller.END);
        MainFrame.logger = new MainFrame.LogGenerator(logTextArea);
        logAreaContainer.add(logScrollPane);
        mainBox.add(horizontalPanel1);
        mainBox.add(horizontalPanel2);
        mainBox.add(logAreaContainer);
        mainBox.add(horizontalPanel3);
        frame.add(mainBox);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public MainFrame(String title){
        super(title);
    }

    public static File getChosenFile() {
        return chosenFile;
    }

    public static void setChosenFile(File chosenFile) {
        MainFrame.chosenFile = chosenFile;
    }

    public static boolean hasFileChosen(){
        if(MainFrame.chosenFile == null){
            return false;
        }
        return true;
    }

    public static class LogGenerator{
        private JTextArea logArea;
        private String inputFileType;
        private String outputFileType;
        private String inputFileName;
        private String outputFileName;

        public LogGenerator(JTextArea logArea){
            this.logArea = logArea;
        }

        public void log(String message){
            logArea.setText(logArea.getText() + message + "\n");
        }
    }

}

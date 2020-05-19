package FileChooser;

import FileChooser.MyFileChooser;

import java.awt.*;

public class MyFileChooserImpl implements MyFileChooser {

    private String fileName;
    private String directoryPath;

    public MyFileChooserImpl() {

    }

    @Override
    public void chooseFilePath() {
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);

        String fileName = dialog.getFile();
        String directoryPath = dialog.getDirectory();

        this.directoryPath = directoryPath;
        this.fileName = fileName;

        System.out.println(this.getFilePath());
    }

    @Override
    public String getFilePath() {
        return this.directoryPath + this.fileName;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }
}

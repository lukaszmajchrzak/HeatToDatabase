package HeatToDatabase;


import java.io.File;

public class ReportListener  {

    private String fileName;
    private String filePath;
    private String backupPath;


    public boolean Listen() {
        File files = new File(filePath);

        for (String file : files.list()) {
            if (file.equals(this.fileName)) {
                return true;
            }
        }
        return false;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getBackupPath() {
        return backupPath;
    }
}


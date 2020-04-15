package HeatToDatabase;


import java.io.File;

public class ReportListener  {

    private String fileName;
    private String filePath;
    private String backupPath;


    public boolean Listen() {
        File files = new File(filePath);
//        System.out.println("Checking if file: " + this.fileName + " exist in: " + this.filePath);
        if(files.list() != null)
        for (String file : files.list()) {
            if (file.equals(this.fileName)) {
                return true;
            }
        }
        return false;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
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


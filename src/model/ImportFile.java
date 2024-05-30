package model;

import java.io.*;

public class ImportFile {

    public static void copyFile(String sourceFilePath, String destinationFolderPath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File destinationFolder = new File(System.getProperty("user.home") + File.separator + "Documents"
                + destinationFolderPath);

        // Create the "documents" folder if it doesn't exist
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        // Check if the source file exists
        if (!sourceFile.exists()) {
            System.out.println("Source file does not exist.");
            return;
        }

        // Create the destination file
        String destinationFilePath = destinationFolderPath + File.separator + sourceFile.getName();
        File destinationFile = new File(destinationFilePath);

        // Copy the file
        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
        }
    }
}

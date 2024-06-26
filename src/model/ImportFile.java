package model;

import java.io.*;

/**
 * ImportFile contains the process of importing
 * a file to a given directory path.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */

public class ImportFile {

    /**
     * Copies a file given paths for the source
     * file and the destination folder.
     *
     * @param sourceFilePath a String containing the path of the source file
     * @param destinationFolderPath a String containing the path of the destination folder
     */
    public static void copyFile(final String sourceFilePath, final String destinationFolderPath) {
        File sourceFile = new File(sourceFilePath);
        File destinationFolder = new File(destinationFolderPath);

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

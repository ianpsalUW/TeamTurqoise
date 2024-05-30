package model;

public class Document {

    String myDirectory;

    String myFileName;

    public Document(final String theDirectory, final String theFileName) {
        myDirectory = theDirectory;
        myFileName = theFileName;
    }

    public String getDirectory() {
        return myDirectory;
    }

    public String getFileName() {
        return myFileName;
    }
}

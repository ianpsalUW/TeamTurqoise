package main;

import java.util.ArrayList;

public class About {

    private static final double VERSION_NUMBER = 0.1;
    private final ArrayList<String> myDevelopers;

    public About() {
        myDevelopers = new ArrayList<>();
    }

    public void add(String theDev) {
        myDevelopers.add(theDev);
    }

    public double getVersion() {
        return VERSION_NUMBER;
    }

    public String devToString() {
        StringBuilder msg = new StringBuilder();
        for (String dev : myDevelopers) {
            msg.append(dev).append("\n");
        }

        return msg.toString();
    }
}

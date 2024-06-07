package view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 * Uses apache poi, PDFbox, and Tika libraries to display various file formats natively on the application.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */
public class DocumentFrame extends JFrame {

    /**
     * This is the field for the text area.
     */

    private JTextArea myTextArea;

    /**
     * This is the method for creating the file display frame.
     * @param theFilePath is the path to the file to display.
     */

    public DocumentFrame(final String theFilePath) {
        // Set up the frame
        setTitle("Document Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Determine file extension and display accordingly
        String extension = getFileExtension(theFilePath);

        switch (extension) {
            case "txt":
                displayTextFile(new File(theFilePath));
                break;
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
                displayImageFile(new File(theFilePath));
                break;
            case "pdf":
                displayPdfFile(new File(theFilePath));
                break;
            case "doc":
            case "docx":
                displayWordFile(new File(theFilePath));
                break;
            case "xls":
            case "xlsx":
                displayExcelFile(new File(theFilePath));
                break;
            case "ppt":
            case "pptx":
                displayPowerPointFile(new File(theFilePath));
                break;
            default:
                displayUnsupportedFileMessage();
                break;
        }
    }

    /**
     * This is the method to get the file extension.
     * @param theFilePath is the path to the file.
     * @return the file extension.
     */
    private String getFileExtension(final String theFilePath) {
        String extension = "";
        int i = theFilePath.lastIndexOf('.');
        if (i > 0) {
            extension = theFilePath.substring(i + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * This is the method to display a text file.
     * @param theFile is the file to display.
     */
    private void displayTextFile(final File theFile) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try (BufferedReader reader = new BufferedReader(new FileReader(theFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                myTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            myTextArea.setText("Error reading theFile: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);


    }

    /**
     * this is the method to display an image file.
     * @param theFile is the file to display.
     */

    private void displayImageFile(final File theFile) {
        JLabel myImageLabel = new JLabel();
        JScrollPane scrollPane = new JScrollPane(myImageLabel);

        try {
            BufferedImage image = ImageIO.read(theFile);
            myImageLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            myImageLabel.setText("Error reading image theFile: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This is the method to display a PDF.
     * @param theFile is the file to display.
     */

    private void displayPdfFile(final File theFile) {
        JPanel pdfPanel = new JPanel() {
            private BufferedImage renderedImage;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (renderedImage != null) {
                    g.drawImage(renderedImage, 0, 0, null);
                }
            }

            {
                try {
                    PDDocument document = Loader.loadPDF(theFile);
                    PDFRenderer pdfRenderer = new PDFRenderer(document);
                    int pageCount = document.getNumberOfPages();
                    // Render all pages into a single image
                    int totalHeight = 0;
                    int maxWidth = 0;
                    // Initial scale
                    double scale = 1.0;
                    for (int i = 0; i < pageCount; i++) {
                        PDPage page = document.getPage(i);
                        float width = page.getMediaBox().getWidth();
                        float height = page.getMediaBox().getHeight();
                        int scaledWidth = (int) (width * scale);
                        int scaledHeight = (int) (height * scale);
                        if (scaledWidth > maxWidth) {
                            maxWidth = scaledWidth;
                        }
                        totalHeight += scaledHeight;
                    }
                    renderedImage = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = renderedImage.createGraphics();
                    int y = 0;
                    for (int i = 0; i < pageCount; i++) {
                        PDPage page = document.getPage(i);
                        float width = page.getMediaBox().getWidth();
                        float height = page.getMediaBox().getHeight();
                        int scaledWidth = (int) (width * scale);
                        int scaledHeight = (int) (height * scale);
                        BufferedImage image = pdfRenderer.renderImage(i, (float) scale);
                        g2d.drawImage(image, 0, y, scaledWidth, scaledHeight, null);
                        y += scaledHeight;
                    }
                    g2d.dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Dimension getPreferredSize() {
                // Set the preferred size based on the rendered image dimensions
                if (renderedImage != null) {
                    return new Dimension(renderedImage.getWidth(), renderedImage.getHeight());
                }
                return super.getPreferredSize();
            }
        };

        JScrollPane scrollPane = new JScrollPane(pdfPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This is the method to display an MS Word file.
     * @param theFile is the file to display.
     */
    private void displayWordFile(final File theFile) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try (FileInputStream fis = new FileInputStream(theFile)) {
            XWPFDocument docx = new XWPFDocument(fis);
            StringBuilder sb = new StringBuilder();
            for (XWPFParagraph paragraph : docx.getParagraphs()) {
                sb.append(paragraph.getText()).append("\n");
            }
            myTextArea.setText(sb.toString());
        } catch (IOException e) {
            myTextArea.setText("Error reading Word theFile: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This is the method to display an MS Excel file.
     * @param theFile the file to display.
     */

    private void displayExcelFile(final File theFile) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try (FileInputStream fis = new FileInputStream(theFile)) {
            Workbook workbook;
            if (theFile.getName().endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                workbook = new XSSFWorkbook(fis);
            }
            StringBuilder sb = new StringBuilder();
            for (Sheet sheet : workbook) {
                sb.append("Sheet: ").append(sheet.getSheetName()).append("\n");
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        sb.append(cell.toString()).append("\t");
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
            myTextArea.setText(sb.toString());
        } catch (IOException e) {
            myTextArea.setText("Error reading Excel theFile: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This is the method to display an MS PowerPoint file. Currently, does not work.
     * @param theFile is the file to display.
     */

    private void displayPowerPointFile(final File theFile) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try (FileInputStream fis = new FileInputStream(theFile)) {
            XMLSlideShow ppt = new XMLSlideShow(fis);
            StringBuilder sb = new StringBuilder();
            for (XSLFSlide slide : ppt.getSlides()) {
                sb.append("Slide: ").append(slide.getSlideNumber()).append("\n");
                sb.append(slide.getTitle()).append("\n");
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape textShape) {
                        sb.append(textShape.getText()).append("\n");
                    }
                }
                sb.append("\n");
            }
            myTextArea.setText(sb.toString());
        } catch (IOException e) {
            myTextArea.setText("Error reading PowerPoint theFile: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This is the method to display that the file type is unsupported by the application.
     */

    private void displayUnsupportedFileMessage() {
        JLabel label = new JLabel("Unsupported file type.");
        add(label, BorderLayout.CENTER);
    }

}

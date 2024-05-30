package view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class DocumentFrame extends JFrame {

    private JTextArea myTextArea;

    public DocumentFrame(String filePath) {
        // Set up the frame
        setTitle("Document Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Determine file extension and display accordingly
        String extension = getFileExtension(filePath);

        switch (extension) {
            case "txt":
                displayTextFile(new File(filePath));
                break;
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
                displayImageFile(new File(filePath));
                break;
            case "pdf":
                displayPdfFile(new File(filePath));
                break;
            case "doc":
            case "docx":
                displayWordFile(new File(filePath));
                break;
            case "xls":
            case "xlsx":
                displayExcelFile(new File(filePath));
                break;
            case "ppt":
            case "pptx":
                displayPowerPointFile(new File(filePath));
                break;
            default:
                displayUnsupportedFileMessage();
                break;
        }
    }

    private String getFileExtension(String filePath) {
        String extension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i + 1).toLowerCase();
        }
        return extension;
    }

    private void displayTextFile(File file) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                myTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            myTextArea.setText("Error reading file: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    private void displayImageFile(File file) {
        JLabel myImageLabel = new JLabel();
        JScrollPane scrollPane = new JScrollPane(myImageLabel);

        try {
            BufferedImage image = ImageIO.read(file);
            myImageLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            myImageLabel.setText("Error reading image file: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    private void displayPdfFile(File file) {
        JPanel pdfPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try (PDDocument document = PDDocument.load(new FileInputStream(file))) {
                    PDFRenderer pdfRenderer = new PDFRenderer(document);
                    BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300);
                    g.drawImage(image, 0, 0, null);
                } catch (IOException e) {
                    g.drawString("Error reading PDF file: " + e.getMessage(), 10, 20);
                }
            }
        };

        JScrollPane scrollPane = new JScrollPane(pdfPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void displayWordFile(File file) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try {
            String text;
            if (file.getName().endsWith(".doc")) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    HWPFDocument doc = new HWPFDocument(fis);
                    WordExtractor extractor = new WordExtractor(doc);
                    text = extractor.getText();
                }
            } else {
                try (FileInputStream fis = new FileInputStream(file)) {
                    XWPFDocument doc = new XWPFDocument(fis);
                    text = doc.getDocument().getBody().getText();
                }
            }
            myTextArea.setText(text);
        } catch (IOException e) {
            myTextArea.setText("Error reading Word file: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    private void displayExcelFile(File file) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook;
            if (file.getName().endsWith(".xls")) {
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
            myTextArea.setText("Error reading Excel file: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    private void displayPowerPointFile(File file) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try (FileInputStream fis = new FileInputStream(file)) {
            XMLSlideShow ppt = new XMLSlideShow(fis);
            StringBuilder sb = new StringBuilder();
            for (XSLFSlide slide : ppt.getSlides()) {
                sb.append("Slide: ").append(slide.getSlideNumber()).append("\n");
                sb.append(slide.getTitle()).append("\n");
                slide.getShapes().forEach(shape -> sb.append(shape.getText()).append("\n"));
                sb.append("\n");
            }
            myTextArea.setText(sb.toString());
        } catch (IOException e) {
            myTextArea.setText("Error reading PowerPoint file: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
    }

    private void displayUnsupportedFileMessage() {
        JLabel label = new JLabel("Unsupported file type.");
        add(label, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java DocumentFrame <file path>");
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            DocumentFrame viewer = new DocumentFrame(args[0]);
            viewer.setVisible(true);
        });
    }
}

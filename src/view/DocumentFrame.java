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
                    PDDocument document = Loader.loadPDF(file);
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

    private void displayWordFile(File file) {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(myTextArea);

        try (FileInputStream fis = new FileInputStream(file)) {
            XWPFDocument docx = new XWPFDocument(fis);
            StringBuilder sb = new StringBuilder();
            for (XWPFParagraph paragraph : docx.getParagraphs()) {
                sb.append(paragraph.getText()).append("\n");
            }
            myTextArea.setText(sb.toString());
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
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape textShape) {
                        sb.append(textShape.getText()).append("\n");
                    }
                }
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
        DocumentFrame viewer = new DocumentFrame("C:\\Users\\billl\\Downloads\\bitch.pdf");
        viewer.setVisible(true);
    }
}

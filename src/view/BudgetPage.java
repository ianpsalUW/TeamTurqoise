package view;

import model.Project;
import model.Purchase;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BudgetPage extends JFrame {

    private final Project project;
    private final JLabel totalLabel;
    private final JButton editTotalButton;
    private final JLabel currentAmtLabel;
    private final JButton viewSpending;
    private final JButton addSpending;
    private final JButton backButton;

    public BudgetPage(Project project) {
        this.project = project;

        setTitle(project.getName() + " - Budget");
        setSize(700, 500);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(10, 25)));

        JPanel totalPanel = new JPanel();
        totalPanel.setMinimumSize(new Dimension(700, 35));
        totalPanel.setMaximumSize(new Dimension(700, 35));
        totalPanel.setBackground(new Color(64, 224, 208));
        totalLabel = new JLabel("Total Budget: " + project.getSpending().getTotal());
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        editTotalButton = new JButton("Edit");
        editTotalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editTotalButton.setMinimumSize(new Dimension(100, 30));
        editTotalButton.setMaximumSize(new Dimension(100, 30));
        totalPanel.add(totalLabel);
        totalPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        totalPanel.add(editTotalButton);
        mainPanel.add(totalPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 15)));

        currentAmtLabel = new JLabel("Current Spending: " +
                project.getBudget().getBudget().setScale(2, RoundingMode.HALF_EVEN));
        currentAmtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentAmtLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        mainPanel.add(currentAmtLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 25)));

        viewSpending = new JButton("View Expenditure List");
        viewSpending.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewSpending.setMinimumSize(new Dimension(350, 40));
        viewSpending.setMaximumSize(new Dimension(350, 40));
        mainPanel.add(viewSpending);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 25)));

        addSpending = new JButton("Add Expenditure");
        addSpending.setAlignmentX(Component.CENTER_ALIGNMENT);
        addSpending.setMinimumSize(new Dimension(350, 40));
        addSpending.setMaximumSize(new Dimension(350, 40));
        mainPanel.add(addSpending);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 25)));

        backButton = new JButton("Back to Project");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMinimumSize(new Dimension(150, 30));
        backButton.setMaximumSize(new Dimension(150, 30));
        mainPanel.add(backButton);

        add(mainPanel);
        addListeners();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void addListeners() {
        editTotalButton.addActionListener(event -> {
            String input = JOptionPane.showInputDialog("Enter new budget:");
            BigDecimal newTotal = new BigDecimal(input);
            project.getBudget().setBudget(newTotal);
            totalLabel.setText("Total Budget: " + project.getBudget().getBudget());
        });

        viewSpending.addActionListener(event -> displayExpenditures());

        addSpending.addActionListener(event -> displayAdding());

        backButton.addActionListener(event -> {
            dispose();
            new ProjectFrame(project);
        });
    }

    private void displayExpenditures() {
        JFrame costFrame = new JFrame("Expenditures");
        costFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        costFrame.setSize(400,300);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        costFrame.add(scroll);

        mainPanel.removeAll();
        for (Purchase cost : project.getSpending().getPurchases()) {
            JButton costButton = getCostButton(cost, costFrame);
            mainPanel.add(costButton);
        }
        mainPanel.revalidate();
        mainPanel.repaint();

        costFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        costFrame.setResizable(false);
        costFrame.setLocationRelativeTo(null);
        costFrame.setVisible(true);
    }

    private JButton getCostButton(Purchase cost, JFrame costFrame) {
        JButton costButton = new JButton();
        costButton.setText(cost.getName() + "    |    $" +
                cost.getPrice().setScale(2, RoundingMode.HALF_EVEN));
        costButton.setMinimumSize(new Dimension(350, 40));
        costButton.setMaximumSize(new Dimension(350, 40));

        costButton.addActionListener(event -> {
            String[] options = {"Edit", "Delete"};
            var selection = JOptionPane.showOptionDialog(null,
                    "What do you want to do?", "Options",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);

            if (selection == 0) {
                JTextField enterName = new JTextField(cost.getName());
                JTextField enterCost = new JTextField(String.valueOf(cost.getPrice()));
                Object[] msg = {
                        "Edit Name:", enterName,
                        "Edit Cost:", enterCost
                };

                int option = JOptionPane.showConfirmDialog(null, msg,
                        "Edit Expenditure", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    BigDecimal newCost = new BigDecimal(enterCost.getText());
                    cost.setName(enterName.getText());
                    BigDecimal newCurrent1 = project.getSpending().getTotal().subtract(cost.getPrice());
                    cost.setPrice(newCost);
                    BigDecimal newCurrent2 = newCurrent1.add(cost.getPrice());
                    project.getSpending().setTotal(newCurrent2);
                    currentAmtLabel.setText("Current Spending: " + project.getSpending().getTotal());
                    costFrame.dispose();
                }
            } else if (selection == 1) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Do you want to delete this?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    project.getSpending().removePurchase(cost.getName());

                    BigDecimal newCurrent = project.getSpending().getTotal().subtract(cost.getPrice());
                    project.getSpending().setTotal(newCurrent);
                    currentAmtLabel.setText("Current Spending: "
                            + project.getSpending().getTotal());
                    costFrame.dispose();
                }
            }
        });
        return costButton;
    }

    private void displayAdding() {
        JTextField enterName = new JTextField();
        JTextField enterCost = new JTextField();
        Object[] msg = {
                "Enter Name:", enterName,
                "Enter Cost:", enterCost
        };

        int option = JOptionPane.showConfirmDialog(null, msg,
                "Add Expenditure", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            BigDecimal newCost = new BigDecimal(enterCost.getText());

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            Purchase newSpending = new Purchase(enterName.getText(), newCost, date.format(format));

            project.getSpending().addPurchase(newSpending);
            currentAmtLabel.setText("Current Spending: " + project.getSpending().getTotal());
        }
    }
}

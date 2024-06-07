package view;

import model.Project;
import model.Purchase;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BudgetPage displays a Project's budget, current amount
 * spent and list of expenditures which can be added,
 * edited, and deleted as needed.
 *
 * @version JDK 21.0
 * @author Christian Pineda
 */

public class BudgetPage extends JFrame {

    /**
     * Instance field of the current project being examined.
     */
    private final Project myProject;

    /**
     * Instance field of the total budget displayed.
     */
    private final JLabel myTotalLabel;

    /**
     * Instance field of the Edit Budget button.
     */
    private final JButton myEditTotalButton;

    /**
     * Instance field of the current amount spent displayed.
     */
    private final JLabel myCurrentAmtLabel;

    /**
     * Instance field of the View Expenditures button.
     */
    private final JButton myViewSpending;

    /**
     * Instance field of the Add Expenditure button.
     */
    private final JButton myAddSpending;

    /**
     * Instance field of the back button.
     */
    private final JButton myBackButton;

    /**
     * Instance field of the remaining budget displayed.
     */
    private final JLabel myRemainingLabel;


    /**
     * Constructor which initializes the main JFrame.
     *
     * @param theProject the current myProject being examined
     */
    public BudgetPage(final Project theProject) {
        myProject = theProject;

        setTitle(theProject.getName() + " - Budget");
        setSize(700, 500);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(10, 25)));

        JPanel totalPanel = new JPanel();
        totalPanel.setMinimumSize(new Dimension(700, 35));
        totalPanel.setMaximumSize(new Dimension(700, 35));
        totalPanel.setBackground(new Color(64, 224, 208));
        myTotalLabel = new JLabel("Total Budget: $" +
                theProject.getBudget().getBudget().setScale(2, RoundingMode.HALF_EVEN));
        myTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        myTotalLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        myEditTotalButton = new JButton("Edit");
        myEditTotalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myEditTotalButton.setMinimumSize(new Dimension(100, 30));
        myEditTotalButton.setMaximumSize(new Dimension(100, 30));
        totalPanel.add(myTotalLabel);
        totalPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        totalPanel.add(myEditTotalButton);
        mainPanel.add(totalPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 15)));

        myCurrentAmtLabel = new JLabel("Current Spending: $" +
                theProject.getSpending().getTotal());
        myCurrentAmtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        myCurrentAmtLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        mainPanel.add(myCurrentAmtLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 20)));

        BigDecimal remaining =
                theProject.getBudget().getBudget().subtract(theProject.getSpending().getTotal());
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            myRemainingLabel = new JLabel("Remaining Budget: -$" + remaining.abs());
        } else {
            myRemainingLabel = new JLabel("Remaining Budget: $" + remaining);
        }
        myRemainingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        myRemainingLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        mainPanel.add(myRemainingLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));

        myViewSpending = new JButton("View Expenditure List");
        myViewSpending.setAlignmentX(Component.CENTER_ALIGNMENT);
        myViewSpending.setMinimumSize(new Dimension(350, 40));
        myViewSpending.setMaximumSize(new Dimension(350, 40));
        mainPanel.add(myViewSpending);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 25)));

        myAddSpending = new JButton("Add Expenditure");
        myAddSpending.setAlignmentX(Component.CENTER_ALIGNMENT);
        myAddSpending.setMinimumSize(new Dimension(350, 40));
        myAddSpending.setMaximumSize(new Dimension(350, 40));
        mainPanel.add(myAddSpending);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 25)));

        myBackButton = new JButton("Back to Project");
        myBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myBackButton.setMinimumSize(new Dimension(150, 30));
        myBackButton.setMaximumSize(new Dimension(150, 30));
        mainPanel.add(myBackButton);

        add(mainPanel);
        addListeners();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Adds action listeners to the initialized JButtons.
     */
    private void addListeners() {
        myEditTotalButton.addActionListener(event -> {
            String input = JOptionPane.showInputDialog("Enter new budget:");
            BigDecimal newTotal = new BigDecimal(input);
            myProject.getBudget().setBudget(newTotal);
            myProject.getChangelog().budgetEdited();

            updateBudgetDisplay();
        });

        myViewSpending.addActionListener(event -> displayExpenditures());

        myAddSpending.addActionListener(event -> displayAdding());

        myBackButton.addActionListener(event -> {
            dispose();
            new ProjectFrame(myProject);
        });
    }

    /**
     * Displays all expenditures of a myProject on a separate window.
     */
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
        for (Purchase cost : myProject.getSpending().getPurchases()) {
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

    /**
     * Initializes the options of all expenditures.
     *
     * @param theCost the expenditure
     * @param theCostFrame the frame which displays all expenditures
     * @return an initialized JButton for the given expenditure
     */
    private JButton getCostButton(final Purchase theCost, final JFrame theCostFrame) {
        JButton costButton = new JButton();
        costButton.setText(theCost.getName() + "    |    $" +
                theCost.getPrice().setScale(2, RoundingMode.HALF_EVEN));
        costButton.setMinimumSize(new Dimension(350, 40));
        costButton.setMaximumSize(new Dimension(350, 40));

        costButton.addActionListener(event -> {
            String[] options = {"Edit", "Delete"};
            var selection = JOptionPane.showOptionDialog(null,
                    "What do you want to do?", "Options",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);

            if (selection == 0) {
                JTextField enterName = new JTextField(theCost.getName());
                JTextField enterCost = new JTextField(String.valueOf(theCost.getPrice()));
                Object[] msg = {
                        "Edit Name:", enterName,
                        "Edit Cost:", enterCost
                };

                int option = JOptionPane.showConfirmDialog(null, msg,
                        "Edit Expenditure", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    BigDecimal newCost = new BigDecimal(enterCost.getText());
                    theCost.setName(enterName.getText());
                    theCost.setPrice(newCost);
                    myProject.getSpending().setTotal();

                    myProject.getChangelog().purchaseEdited(enterName.getText());
                    updateBudgetDisplay();
                    theCostFrame.dispose();
                }
            } else if (selection == 1) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Do you want to delete this?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    myProject.getSpending().removePurchase(theCost.getName());

                    myProject.getSpending().setTotal();

                    myProject.getChangelog().purchaseRemoved(theCost.getName());
                    updateBudgetDisplay();
                    theCostFrame.dispose();
                }
            }
        });
        return costButton;
    }

    /**
     * Goes through the process of adding a new expenditure.
     */
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

            myProject.getSpending().addPurchase(newSpending);
            myProject.getChangelog().purchasedItem(enterName.getText());
            updateBudgetDisplay();
        }
    }

    /**
     * Updates the BudgetPage's total budget, current amount spent, and remaining budget.
     */
    private void updateBudgetDisplay() {
        myTotalLabel.setText("Total Budget: $" + myProject.getBudget().getBudget());
        myCurrentAmtLabel.setText("Current Spending: $" + myProject.getSpending().getTotal());

        BigDecimal remaining =
                myProject.getBudget().getBudget().subtract(myProject.getSpending().getTotal());

        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            myRemainingLabel.setText("Remaining Budget: -$" + remaining.abs());
        } else {
            myRemainingLabel.setText("Remaining Budget: $" + remaining);
        }
    }
}

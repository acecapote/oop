import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class NationalBureauofinvestigation extends JFrame implements ActionListener {
    JLabel usernameLabel = new JLabel("Username:");
    JTextField usernameField = new JTextField(20);
    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(20);
    JButton signUpButton = new JButton("Sign up");
    JButton logInButton = new JButton("Log in");

    public NationalBureauofinvestigation() {
        setTitle("National Bureau of Investigation");
        setSize(600, 366);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(signUpButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(logInButton, gbc);

        add(panel);
        usernameLabel.setFont(new Font("Calibre", Font.BOLD, 15));
        usernameField.setFont(new Font("Calibre", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Calibre", Font.BOLD, 15));
        passwordField.setFont(new Font("Calibre", Font.PLAIN, 14));
        signUpButton.setFont(new Font("Calibre", Font.BOLD, 10));
        logInButton.setFont(new Font("Calibre", Font.BOLD, 10));

        signUpButton.addActionListener(this);
        logInButton.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            if (username.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.");
                return;
            }
            try {                
                PrintWriter out = new PrintWriter(new FileWriter("user and password.txt"));
                out.println(username);
                out.println(password);
                out.close();

                JOptionPane.showMessageDialog(this, "Sign up successful!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == logInButton) {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            if (username.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.");
                return;
            }

            try {                
                Scanner in = new Scanner(new File("user and password.txt"));
                String savedUsername = in.nextLine();
                String savedPassword = in.nextLine();
                in.close();
                if (username.equals(savedUsername) && new String(password).equals(savedPassword)) {
                    JOptionPane.showMessageDialog(this, "Log in successful!");
                    while (true) {
                        String[] options = { "Complete your account details", "Search for existing account", "Update existing account", "Delete account",
                                "Log out" };
                        Font font = new Font("Adobe Caslon Pro", Font.BOLD, 12);

                        UIManager.put("OptionPane.messageFont", font);
                        int gisChoice = JOptionPane.showOptionDialog(this, "Is your nbi clearance expired or expiring? renew it now?",
                                "National Bureau of investigation",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options);

                        if (gisChoice == 0) {
                            String id = JOptionPane.showInputDialog(this, "Enter your ID number:");
                            String name = JOptionPane.showInputDialog(this, "Enter your full name:");
                            String ad = JOptionPane.showInputDialog(this, "Enter your address:");
                            String bd = JOptionPane.showInputDialog(this, "Enter your birthday:");


                            if (name.isEmpty() || id.isEmpty() || ad.isEmpty() || bd.isEmpty() ) {
                                JOptionPane.showMessageDialog(this, "Please fill out all fields.");
                            }
                            else {
                                PrintWriter out = new PrintWriter(new FileWriter("yourinfo.txt", true));                                       
                                out.println(id + ", " + name + "," + ad + ", " + bd + ", ");
                                out.close();

                                JOptionPane.showMessageDialog(this, "Your information added successfully!!!");
                            }
                        } else if (gisChoice == 2) {
                            String id = JOptionPane.showInputDialog(this,
                                    "Enter your ID number of the data you want to update:");
                            BufferedReader br = new BufferedReader(new FileReader("yourinfo.txt"));
                            String line;
                            StringBuilder sb = new StringBuilder();
                            boolean found = false;
                            while ((line = br.readLine()) != null) {
                                String[] parts = line.split(", ");
                                if (parts[0].equals(id)) {
                                    found = true;
                                    String name = JOptionPane.showInputDialog(this, "Enter your name:", parts[1]);
                                    String address = JOptionPane.showInputDialog(this, "Enter your address:", parts[2]);
                                    String birthday = JOptionPane.showInputDialog(this, "Enter your birthday:", parts[3]);
                                    parts[0] = id;
                                    parts[1] = name;
                                    parts[2] = address;
                                    parts[3] = birthday;
                                    line = parts[0] + ", " + parts[1] + ", " + parts[2] + ", " + parts[3] ;
                                }
                                sb.append(line).append("\n");
                            }
                            br.close();
                            if (!found) {
                                JOptionPane.showMessageDialog(this, "Error: Data with ID " + id + " not found.");
                            } else {                               
                                PrintWriter out = new PrintWriter(new FileWriter("yourinfo.txt"));
                                out.println(sb.toString());
                                out.close();
                            }

                        } else if (gisChoice == 1) {
                            String id = JOptionPane.showInputDialog(this,
                                    "Enter your ID number of the data you want to search for:");                            
                            BufferedReader br = new BufferedReader(new FileReader("yourinfo.txt"));
                            String line;
                            String result = "Data not found.";
                            while ((line = br.readLine()) != null) {
                                String[] parts = line.split(", ");
                                if (parts[0].equals(id)) {
                                    result = "ID: " + parts[0] + "\n" + "name: " + parts[1] + "\n" + "address: " + parts[2] + "\n" + "birthday: " + parts[3] ;
                                    break;
                                }
                            }
                            br.close();

                            JOptionPane.showMessageDialog(this, result);
                        } else if (gisChoice == 3) {
                            String id = JOptionPane.showInputDialog(this,
                                    "Enter your ID number of the data you want to delete:");                            
                            BufferedReader br = new BufferedReader(new FileReader("yourinfo.txt"));
                            String line;
                            boolean found = false;
                            StringBuilder sb = new StringBuilder();
                            while ((line = br.readLine()) != null) {
                                String[] parts = line.split(", ");
                                if (!parts[0].equals(id)) {
                                    sb.append(line).append("\n");
                                } else {
                                    found = true;
                                }
                            }
                            br.close();
                            if (!found) {
                                JOptionPane.showMessageDialog(this,
                                        "Error: Data with ID " + id + " does not exist!");
                            } else {
                                
                                PrintWriter out = new PrintWriter(new FileWriter("yourinfo.txt"));
                                out.println(sb.toString());
                                out.close();

                                JOptionPane.showMessageDialog(this, "Data deleted successfully!");
                            }
                        } else if (gisChoice == 4) {                            
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        NationalBureauofinvestigation nBureauofInvestigation = new NationalBureauofinvestigation();
        nBureauofInvestigation.setVisible(true);
    }
}
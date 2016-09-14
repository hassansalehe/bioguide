/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * mainForm.java
 *
 * Created on 21.Eyl.2010, 10:18:51
 */
package bioguide.gui;

import bioguide.base.BioBricks;
import bioguide.base.Parts;
import bioguide.base.PathFinder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.BorderLayout;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author labuser
 */
public class mainForm extends javax.swing.JFrame {

    Parts deviceGraph;
    BioBricks bricksGraph;
    // DB
    private Statement st = null;
    private ResultSet rsInput = null, rsOutput = null, rs = null;
    private Connection conn = null;
    java.sql.PreparedStatement pstmt = null;
    // Local holders
    // I/O object
    IO_Info io = new IO_Info();
    //Inputs
    Vector<String> I_Activities = new Vector<String>();
    Vector<String> I_Inducers = new Vector<String>();
    Vector<String> I_Repressors = new Vector<String>();
    Vector<String> I_Inhibitors = new Vector<String>();
    Vector<String> I_Activators = new Vector<String>();
    //Outputs
    Vector<String> O_Regulator = new Vector<String>();
    Vector<String> O_Inducers = new Vector<String>();
    Vector<String> O_Repressors = new Vector<String>();
    Vector<String> O_Inhibitors = new Vector<String>();
    Vector<String> O_Activators = new Vector<String>();
    Vector<String> O_Reporters = new Vector<String>();
    //Matching part
    static Vector<String> matchedPartIDs;
    static Vector<String> matchedPartInputIDs;
    static Vector<String> matchedPartOutputIDs;
    PathFinder pathFinder;

    /* Returns the found parts */
    public Vector<String> getMatchedParts() {
        return matchedPartIDs;
    }

    /*Gets DB Connection*/
    private void getConnection() {
        //private Statement st = null;
        //private ResultSet rs = null;
        //private Connection conn = null;

        try {
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "igem_db";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "mrrIVL";
            Class.forName(driver).newInstance();
            conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);

        } catch (SQLException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* revokes all I/O data from DB*/
    public void revokeDataFromDB() {

        getConnection();
        //new String [] { "Promoter", "Activity", "Inducer", "Activator", "Repressor", "Inhibitor",
        //"Promoter2", "Activity2", "Inducer2", "Activator2", "Repressor2", "Inhibitor2"}

        /*   //Inputs
        - Vector I_Activities = new Vector();
        - Vector I_Inducers = new Vector();
        - Vector I_Repressors = new Vector();
        - Vector I_Inhibitors = new Vector();
        - Vector I_Activators = new Vector();

        //Outputs
        - Vector O_Inducers = new Vector();
        - Vector O_Repressors = new Vector();
        - Vector O_Inhibitors = new Vector();
        - Vector O_Activators = new Vector();
        - Vector O_Reporters = new Vector();

         */
        try {
            /* *****************Get Activities************************* */
            // Get Activities
            String SELECT = "select i.`Activity` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            //String results = rs.getString(1);
            //I_Activities.addElement(rs.getString(1));
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Activities.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Activities.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Activities.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Activities.addElement(rs.getString(1));
                    }
                }
            }

            // Get Activities2
            SELECT = "select i.`Activity2` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            //String results = rs.getString(1);
            //I_Activities.addElement(rs.getString(1));
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Activities.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Activities.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Activities.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Activities.addElement(rs.getString(1));
                    }
                }
            }
            /* *****************Get Inducers************************* */
            //Get Inducers1
            SELECT = "select i.`Inducer` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Inducers.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Inducers.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Inducers.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Inducers.addElement(rs.getString(1));
                    }
                }
            }
            //Get Inducers2
            SELECT = "select i.`Inducer2` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Inducers.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Inducers.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Inducers.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Inducers.addElement(rs.getString(1));
                    }
                }
            }

            /* *****************Get Repressors************************* */
            //Get Repressors1
            SELECT = "select i.`Repressor` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Repressors.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Repressors.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Repressors.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Repressors.addElement(rs.getString(1));
                    }
                }
            }
            //Get Repressors2
            SELECT = "select i.`Repressor2` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Repressors.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Repressors.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Repressors.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Repressors.addElement(rs.getString(1));
                    }
                }
            }
            /* *****************Get Inhibitors************************* */
            //Get Inhibitors1
            SELECT = "select i.`Inhibitor` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Inhibitors.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Inhibitors.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Inhibitors.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Inhibitors.addElement(rs.getString(1));
                    }
                }
            }
            //Get Inhibitors2
            SELECT = "select i.`Inhibitor` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Inhibitors.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Inhibitors.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Inhibitors.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Inhibitors.addElement(rs.getString(1));
                    }
                }
            }

            /* *****************Get Activators************************* */
            //Get Activators1
            SELECT = "select i.`Activator` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Activators.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Activators.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Activators.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Activators.addElement(rs.getString(1));
                    }
                }
            }
            //Get Activators2
            SELECT = "select i.`Activator2` from inputs i";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < I_Activators.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(I_Activators.elementAt(j)) && rs.getString(1).toLowerCase().contains(I_Activators.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        I_Activators.addElement(rs.getString(1));
                    }
                }
            }

            /* **********************************************Outputs**************************************************************** */


             /* *****************Get Regulators************************* */
            //Get Regulator1
            SELECT = "select o.`Regulator` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Regulator.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Regulator.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Regulator.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Regulator.addElement(rs.getString(1));
                    }
                }
            }

            //Get Regulator2
            SELECT = "select o.`Regulator2` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Regulator.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Regulator.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Regulator.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Regulator.addElement(rs.getString(1));
                    }
                }
            }

            /* *****************Get Inducers************************* */
            //Get Inducers1
            SELECT = "select o.`Inducer` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Inducers.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Inducers.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Inducers.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Inducers.addElement(rs.getString(1));
                    }
                }
            }
            //Get Inducers2
            SELECT = "select o.`Inducer` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Inducers.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Inducers.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Inducers.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Inducers.addElement(rs.getString(1));
                    }
                }
            }
            /* *****************Get Repressors************************* */
            //Get Repressors1
            SELECT = "select o.`Repressor` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Repressors.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Repressors.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Repressors.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Repressors.addElement(rs.getString(1));
                    }
                }
            }
            //Get Repressors2
            SELECT = "select o.`Repressor` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Repressors.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Repressors.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Repressors.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Repressors.addElement(rs.getString(1));
                    }
                }
            }

            /* *****************Get Inhibitors************************* */
            //Get Inhibitors
            SELECT = "select o.`Inhibitor` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Inhibitors.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Inhibitors.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Inhibitors.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Inhibitors.addElement(rs.getString(1));
                    }
                }
            }
            //Get Inhibitors2
            SELECT = "select o.`Inhibitor` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Inhibitors.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Inhibitors.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Inhibitors.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Inhibitors.addElement(rs.getString(1));
                    }
                }
            }

            /* *****************Get Activators************************* */
            //Get Activators
            SELECT = "select o.`Activator` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Activators.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Activators.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Activators.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Activators.addElement(rs.getString(1));
                    }
                }
            }
            //Get Inhibitors2
            SELECT = "select o.`Activator2` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Activators.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Activators.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Activators.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Activators.addElement(rs.getString(1));
                    }
                }
            }

            /* *****************Get Reporters************************* */
            //Get Reporters
            SELECT = "select o.`Reporter` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Reporters.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Reporters.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Reporters.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Reporters.addElement(rs.getString(1));
                    }
                }
            }
            //Get Reporters2
            SELECT = "select o.`Reporter` from outputs o";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    boolean present = false;
                    for (int j = 0; j < O_Reporters.size(); j++) {
                        if (rs.getString(1).equalsIgnoreCase(O_Reporters.elementAt(j)) && rs.getString(1).toLowerCase().contains(O_Reporters.elementAt(j).toLowerCase())) {
                            present = true;
                        }
                    }
                    if (present == false) {
                        O_Reporters.addElement(rs.getString(1));
                    }
                }
            }





        } catch (SQLException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /** Initializes the applet mainForm */
    public void init() {

        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {

                public void run() {
                    initComponents();

                    bricksGraph = new BioBricks(null);

                    jPanel4_biobrickGraph.setLayout(new BorderLayout());
                    jPanel4_biobrickGraph.add(bricksGraph, BorderLayout.CENTER);
                    bricksGraph.setSize(jPanel4_biobrickGraph.getSize());

                    deviceGraph = new Parts(null);
                    deviceGraph.setSize(jPanel3_deviceGraph.getSize());
                    jPanel3_deviceGraph.setLayout(new BorderLayout());
                    jPanel3_deviceGraph.add(deviceGraph, BorderLayout.CENTER);

                    jPanel3_deviceGraph.setVisible(true);
                    bricksGraph.setParts(deviceGraph);
                    deviceGraph.setBrick(bricksGraph);

                    // Initialize shortest path matrix
                    pathFinder  = new PathFinder();
                    pathFinder.InitializeGraphMatrix();
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Get Data From DB
        revokeDataFromDB();
        //I_Activities.add("active");
        //Load to combo boxes
        /**********************Inputs Combo Boxes***************************/
        I_cmbActivity.setModel(new javax.swing.DefaultComboBoxModel(I_Activities));
        I_cmbInducer.setModel(new javax.swing.DefaultComboBoxModel(I_Inducers));
        I_cmbRepressor.setModel(new javax.swing.DefaultComboBoxModel(I_Repressors));
        I_cmbInhibitor.setModel(new javax.swing.DefaultComboBoxModel(I_Inhibitors));
        I_cmbActivator.setModel(new javax.swing.DefaultComboBoxModel(I_Activators));

        /**********************Outputs Combo Boxes***************************/
        O_cmbInducer.setModel(new javax.swing.DefaultComboBoxModel(O_Inducers));
        O_cmbRepressor.setModel(new javax.swing.DefaultComboBoxModel(O_Repressors));
        O_cmbInhibitor.setModel(new javax.swing.DefaultComboBoxModel(O_Inhibitors));
        O_cmbActivator.setModel(new javax.swing.DefaultComboBoxModel(O_Activators));
        O_cmbReporter.setModel(new javax.swing.DefaultComboBoxModel(O_Reporters));
        O_cmbRegulator.setModel(new javax.swing.DefaultComboBoxModel(O_Regulator));
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        igem_dbPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("igem_dbPU").createEntityManager();
        ınputsQuery = java.beans.Beans.isDesignTime() ? null : igem_dbPUEntityManager.createQuery("SELECT ı FROM Inputs ı");
        ınputsList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : ınputsQuery.getResultList();
        jPanel1 = new javax.swing.JPanel();
        I_txtActivator = new javax.swing.JTextField();
        I_txtRepressor = new javax.swing.JTextField();
        I_txtInhibitor = new javax.swing.JTextField();
        I_txtActivity = new javax.swing.JTextField();
        I_txtInducer = new javax.swing.JTextField();
        I_cmbActivity = new javax.swing.JComboBox();
        I_cmbInducer = new javax.swing.JComboBox();
        I_cmbRepressor = new javax.swing.JComboBox();
        I_cmbInhibitor = new javax.swing.JComboBox();
        I_cmbActivator = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        O_txtInducer = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        O_txtRepressor = new javax.swing.JTextField();
        O_txtInhibitor = new javax.swing.JTextField();
        O_txtReporter = new javax.swing.JTextField();
        O_txtActivator = new javax.swing.JTextField();
        O_cmbRepressor = new javax.swing.JComboBox();
        O_cmbInducer = new javax.swing.JComboBox();
        O_cmbInhibitor = new javax.swing.JComboBox();
        O_cmbActivator = new javax.swing.JComboBox();
        O_cmbReporter = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        O_txtRegulator = new javax.swing.JTextField();
        O_cmbRegulator = new javax.swing.JComboBox();
        jPanel3_deviceGraph = new javax.swing.JPanel();
        jPanel4_biobrickGraph = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        check = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnShowParts = new javax.swing.JButton();
        btnShortestPath = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1200, 600));
        setResizable(false);

        I_txtActivator.setFont(new java.awt.Font("Arial", 1, 10));

        I_txtRepressor.setFont(new java.awt.Font("Arial", 1, 10));

        I_txtInhibitor.setFont(new java.awt.Font("Arial", 1, 10));

        I_txtActivity.setFont(new java.awt.Font("Arial", 1, 10));
        I_txtActivity.setInheritsPopupMenu(true);

        I_txtInducer.setFont(new java.awt.Font("Arial", 1, 10));

        I_cmbActivity.setFont(new java.awt.Font("Arial", 1, 10));
        I_cmbActivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I_cmbActivityActionPerformed(evt);
            }
        });

        I_cmbInducer.setFont(new java.awt.Font("Arial", 1, 10));
        I_cmbInducer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        I_cmbInducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I_cmbInducerActionPerformed(evt);
            }
        });

        I_cmbRepressor.setFont(new java.awt.Font("Arial", 1, 10));
        I_cmbRepressor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        I_cmbRepressor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I_cmbRepressorActionPerformed(evt);
            }
        });

        I_cmbInhibitor.setFont(new java.awt.Font("Arial", 1, 10));
        I_cmbInhibitor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        I_cmbInhibitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I_cmbInhibitorActionPerformed(evt);
            }
        });

        I_cmbActivator.setFont(new java.awt.Font("Arial", 1, 10));
        I_cmbActivator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        I_cmbActivator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I_cmbActivatorActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel2.setText("Inducer:");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel1.setText("Activity:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel5.setText("Activator:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel4.setText("Inhibitor:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel3.setText("Repressor:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(I_txtInhibitor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(I_txtRepressor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(I_txtInducer, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(I_cmbInducer, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(I_cmbRepressor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(I_cmbInhibitor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(I_txtActivator, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(I_cmbActivator, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(I_txtActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(I_cmbActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_txtActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_cmbActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_txtInducer, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_cmbInducer, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_txtRepressor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_cmbRepressor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_txtInhibitor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_cmbInhibitor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_txtActivator, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I_cmbActivator, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        O_txtInducer.setFont(new java.awt.Font("Arial", 1, 10));
        O_txtInducer.setAutoscrolls(false);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel13.setText("Reporter:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel7.setText("Inducer:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel6.setText("Repressor:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel10.setText("Inhibitor:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel9.setText("Activator:");

        O_txtRepressor.setFont(new java.awt.Font("Arial", 1, 10));
        O_txtRepressor.setAutoscrolls(false);
        O_txtRepressor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                O_txtRepressorActionPerformed(evt);
            }
        });

        O_txtInhibitor.setFont(new java.awt.Font("Arial", 1, 10));
        O_txtInhibitor.setAutoscrolls(false);

        O_txtReporter.setFont(new java.awt.Font("Arial", 1, 10));
        O_txtReporter.setAutoscrolls(false);

        O_txtActivator.setFont(new java.awt.Font("Arial", 1, 10));
        O_txtActivator.setAutoscrolls(false);

        O_cmbRepressor.setFont(new java.awt.Font("Arial", 1, 10));
        O_cmbRepressor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        O_cmbRepressor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                O_cmbRepressorActionPerformed(evt);
            }
        });

        O_cmbInducer.setFont(new java.awt.Font("Arial", 1, 10));
        O_cmbInducer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        O_cmbInducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                O_cmbInducerActionPerformed(evt);
            }
        });

        O_cmbInhibitor.setFont(new java.awt.Font("Arial", 1, 10));
        O_cmbInhibitor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        O_cmbInhibitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                O_cmbInhibitorActionPerformed(evt);
            }
        });

        O_cmbActivator.setFont(new java.awt.Font("Arial", 1, 10));
        O_cmbActivator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        O_cmbActivator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                O_cmbActivatorActionPerformed(evt);
            }
        });

        O_cmbReporter.setFont(new java.awt.Font("Arial", 1, 10));
        O_cmbReporter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        O_cmbReporter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                O_cmbReporterActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 1, 10));
        jLabel16.setText("Regulator");

        O_txtRegulator.setFont(new java.awt.Font("Arial", 1, 10));

        O_cmbRegulator.setFont(new java.awt.Font("Arial", 1, 10));
        O_cmbRegulator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        O_cmbRegulator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                O_cmbRegulatorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(O_txtActivator, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(O_txtInhibitor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(O_txtRepressor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(O_cmbActivator, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(O_cmbInhibitor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(O_cmbRepressor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(O_txtReporter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O_cmbReporter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(O_txtInducer, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(O_txtRegulator, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(O_cmbRegulator, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(O_cmbInducer, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(455, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(O_txtRegulator, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O_cmbRegulator, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(O_txtInducer, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(O_cmbInducer, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(O_cmbRepressor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O_txtRepressor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(O_txtInhibitor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O_cmbInhibitor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(O_txtActivator, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O_cmbActivator, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(O_cmbReporter, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(O_txtReporter, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3_deviceGraph.setPreferredSize(new java.awt.Dimension(600, 421));

        javax.swing.GroupLayout jPanel3_deviceGraphLayout = new javax.swing.GroupLayout(jPanel3_deviceGraph);
        jPanel3_deviceGraph.setLayout(jPanel3_deviceGraphLayout);
        jPanel3_deviceGraphLayout.setHorizontalGroup(
            jPanel3_deviceGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        jPanel3_deviceGraphLayout.setVerticalGroup(
            jPanel3_deviceGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );

        jPanel4_biobrickGraph.setPreferredSize(new java.awt.Dimension(600, 421));

        javax.swing.GroupLayout jPanel4_biobrickGraphLayout = new javax.swing.GroupLayout(jPanel4_biobrickGraph);
        jPanel4_biobrickGraph.setLayout(jPanel4_biobrickGraphLayout);
        jPanel4_biobrickGraphLayout.setHorizontalGroup(
            jPanel4_biobrickGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        jPanel4_biobrickGraphLayout.setVerticalGroup(
            jPanel4_biobrickGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 10));
        jLabel12.setText("Enter the input and output or just one of those to text boxes [can select those from the list ], then click on the \" Show Parts \" buton to see    the   parts those have specified      I/O     properties.");
        jLabel12.setRequestFocusEnabled(false);

        check.setFont(new java.awt.Font("Arial", 1, 10));
        check.setText("Has Input and Output");
        check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel8.setText("INPUTS");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel11.setText("OUTPUTS");

        jLabel14.setFont(new java.awt.Font("Wide Latin", 0, 17));
        jLabel14.setText("BioGUIDE");

        jLabel15.setFont(new java.awt.Font("Vladimir Script", 3, 21));
        jLabel15.setText("to enlight your path...");

        btnShowParts.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        btnShowParts.setText("Show Parts");
        btnShowParts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowPartsMouseClicked(evt);
            }
        });
        btnShowParts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowPartsActionPerformed(evt);
            }
        });

        btnShortestPath.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        btnShortestPath.setText("Shortest Path");
        btnShortestPath.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShortestPathMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 461, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(326, 326, 326))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(339, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addGap(458, 458, 458))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3_deviceGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4_biobrickGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(check))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnShortestPath, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnShowParts, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnShowParts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnShortestPath, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, 0, 129, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(check))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3_deviceGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4_biobrickGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        btnShowParts.getAccessibleContext().setAccessibleName("Show Parts");
        btnShortestPath.getAccessibleContext().setAccessibleName("ShortestPath");

        getAccessibleContext().setAccessibleParent(this);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1145)/2, (screenSize.height-636)/2, 1145, 636);
    }// </editor-fold>//GEN-END:initComponents

    // Transfer the vector to array.
    public String[] getPartIds(Vector<String> vpartIDs) {
        //mainForm mf  =new mainForm();

        /* Send the vector to array */
        int length = vpartIDs.size();
        String[] partIDs = new String[length];
        for (int i = 0; i < length; i++) {
            partIDs[i] = vpartIDs.elementAt(i);
        }
        return partIDs;

    }
    private String Conditions(String Checker) {
        return Checker;
    }

    public void findMatchedParts() {

        matchedPartInputIDs = new Vector<String>();
        matchedPartOutputIDs = new Vector<String>();
        matchedPartIDs = new Vector<String>();

        try {
            //System.out.println(io.I_Activity + io.I_Activator + io.O_Repressor);
            getConnection();
            /*
            select i.`InputID`, ip.partID, ip.`InputID`,i.`Promoter`,i.`Activity`
            from inputs i, inputtopart ip
            Where i.`InputID` = ip.`InputID` and (i.`Promoter` like '%placI%' or i.`Activity` like '%lp%')
             */
            /******** Revoke the parts have the specified I/O properties **********/
            String SELECTi = "", SELECTo = "";
            String FROMi = "", FROMo = "";
            String WHEREi = "", WHEREo = "";
            String Check = "or";
            if (check.isSelected()) {
                Check = "and";
            }

            boolean isInputSelected = io.I_Activator != null || io.I_Activity != null || io.I_Inducer != null
                    || io.I_Inhibitor != null || io.I_Repressor != null;
            boolean isOutputSelected = io.O_Activator != null || io.O_Inducer != null || io.O_Inhibitor != null
                    || io.O_Reporter != null || io.O_Repressor != null || io.O_Regulator != null;

//            if (Check.equals("and")) {
//                SELECT = " SELECT distinct op.partID, ip.partID ";
//                FROM = " FROM inputs i, inputtopart ip, outputs o, outputfrompart op ";
//                WHERE = " WHERE i.`InputID` = ip.`InputID` and o.`outputID` = op.`outputID` and ";
//
//                fetchSecond = true;
//            } else {
//                if (isInputSelected && isOutputSelected) {
//                    SELECT = " SELECT distinct op.partID, ip.partID ";
//                    FROM = " FROM inputs i, inputtopart ip, outputs o, outputfrompart op ";
//                    WHERE = " WHERE i.`InputID` = ip.`InputID` and o.`outputID` = op.`outputID` and ";
//
//                    fetchSecond = true;
//                }
                if (isInputSelected) {
                    SELECTi = " SELECT distinct ip.partID ";
                    FROMi = " FROM inputs i, inputtopart ip";
                    WHEREi = " WHERE i.`InputID` = ip.`InputID` and ";
                }
                if (isOutputSelected) {
                    SELECTo = "SELECT distinct op.partID";
                    FROMo = " FROM outputs o, outputfrompart op ";
                    WHEREo = " WHERE o.`outputID` = op.`outputID` and ";
                }

            SELECTi = SELECTi
                    + FROMi
                    + WHEREi;
            SELECTo = SELECTo
                    + FROMo
                    + WHEREo;

            String SelectInput ="";
            boolean hasInput = false;

            if(io.I_Activity != null)
            {
                SelectInput += "i.`Activity` like '%" + io.I_Activity + "%'" + " or "
                + "  i.`Activity2` like '%" + io.I_Activity + "%'";
                hasInput = true;
            }

            if (io.I_Inducer != null) {
                SelectInput +=(hasInput ? " or " : "") +  "  i.`Inducer` like '%" + io.I_Inducer + "%'" + " or "
                        + "  i.`Inducer2` like '%" + io.I_Inducer + "%'" ;
                hasInput = true;
            }
            if (io.I_Repressor != null) {
                SelectInput += (hasInput ? " or " : "") + "  i.`Repressor` like '%" + io.I_Repressor + "%'" + " or "
                        + "  i.`Repressor2` like '%" + io.I_Repressor + "%'";
                hasInput = true;
            }

            if (io.I_Inhibitor != null) {
                SelectInput += (hasInput ? " or " : "") + "  i.`Inhibitor` like '%" + io.I_Inhibitor + "%'" + " or " +
                    "  i.`Inhibitor2` like '%" + io.I_Inhibitor + "%'";
                hasInput = true;
            }
            if (io.I_Activator != null) {
                SelectInput += (hasInput ? " or " : "") +  "  i.`Activator` like '%" + io.I_Activator + "%'" + " or "
                    + "  i.`Activator2` like '%" + io.I_Activator + "%'";
                hasInput = true;
            }


            String SelectOutput = "";
            boolean hasOutput = false;
            if(io.O_Regulator != null)
            {
                SelectOutput += "o.`Regulator` like '%" + io.O_Regulator + "%'" + " or "
                + "  o.`Regulator2` like '%" + io.O_Regulator + "%'";
                hasOutput = true;
            }

            if (io.O_Inducer != null) {
                SelectOutput =  "o.`Inducer`    like '%" + io.O_Inducer + "%' or " +
                    " o.`Inducer2`    like '%" + io.O_Inducer + "%'";
                hasOutput = true;
            }

            if (io.O_Repressor != null) {
                    SelectOutput = SelectOutput +  (hasOutput ? " or ": "") + " o.`Repressor`  like '%" + io.O_Repressor + "%' or " +
                    " o.`Repressor2`  like '%" + io.O_Repressor + "%'";
                    hasOutput = true;
            }


            if (io.O_Inhibitor != null) {
                    SelectOutput = SelectOutput + (hasOutput ? " or ": "") + " o.`Inhibitor`  like '%" + io.O_Inhibitor + "%' or " +
                    " o.`Inhibitor2`  like '%" + io.O_Inhibitor + "%'";
                    hasOutput = true;
            }

            if (io.O_Activator != null) {
                SelectOutput = SelectOutput + (hasOutput ? " or ": "") + " o.`Activator`  like '%" + io.O_Activator + "%' or " +
                    " o.`Activator2`  like '%" + io.O_Activator + "%'";
                hasOutput = true;
            }


            if (io.O_Reporter != null) {
                SelectOutput = SelectOutput + (hasOutput ? " or ": "") + " o.`Reporter`  like '%" + io.O_Reporter + "%' or " +
                    " o.`Reporter2`   like '%" + io.O_Reporter + "%'";
                hasOutput = true;
            }


            boolean iExec = true, oExec = true;
            String IQuery = "";
            String OQuery = "";

            if (hasInput)
            {
                IQuery = SELECTi + "( " + SelectInput + ")";
            }
            else
            {
                iExec = false;
            }

            if (hasOutput)
            {
                OQuery = SELECTo + "( " + SelectOutput + ")";
            }
            else
            {
                oExec = false;
            }

           // System.out.println("SelecInput = " + IQuery);
            //System.out.println("SelecOutput = " + OQuery);

            if (iExec) {
                pstmt = conn.prepareStatement(IQuery);
                rsInput = pstmt.executeQuery();


                while (rsInput.next()) {
                    matchedPartInputIDs.add(rsInput.getString(1));
                }
            }

            if (oExec) {
                pstmt = conn.prepareStatement(OQuery);
                rsOutput = pstmt.executeQuery();


                while (rsOutput.next()) {
                    matchedPartOutputIDs.add(rsOutput.getString(1));
                }
            }

            if (!iExec && !oExec)
            {
            } else if (!iExec && Check.equals("or"))
            {
                matchedPartIDs = matchedPartOutputIDs;
            } else if (!oExec && Check.equals("or"))
            {
                matchedPartIDs = matchedPartInputIDs;
            } else
            {
                if (Check.equals("or"))
                {
                    matchedPartIDs = matchedPartInputIDs;
                    for(int i = 0; i<matchedPartOutputIDs.size(); i++)
                    {
                        if (!matchedPartIDs.contains(matchedPartOutputIDs.elementAt(i)))
                        {
                            matchedPartIDs.add(matchedPartOutputIDs.elementAt(i));
                        }
                    }
                } else
                {
                    for(int i = 0; i<matchedPartOutputIDs.size(); i++)
                    {
                        if (matchedPartInputIDs.contains(matchedPartOutputIDs.elementAt(i)))
                        {
                            matchedPartIDs.add(matchedPartOutputIDs.elementAt(i));
                        }
                    }
                }
            }


            /*for (int i = 0 ; i < matchedPartIDs.size() ; i++)
            {
            System.out.println("match" + i + " -> " + matchedPartIDs.elementAt(i));
            }*/

        } catch (SQLException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void I_cmbActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I_cmbActivityActionPerformed
        I_txtActivity.setText((String) I_cmbActivity.getSelectedItem());
    }//GEN-LAST:event_I_cmbActivityActionPerformed

    private void I_cmbInducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I_cmbInducerActionPerformed
        I_txtInducer.setText((String) I_cmbInducer.getSelectedItem());
    }//GEN-LAST:event_I_cmbInducerActionPerformed

    private void I_cmbRepressorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I_cmbRepressorActionPerformed
        I_txtRepressor.setText((String) I_cmbRepressor.getSelectedItem());
    }//GEN-LAST:event_I_cmbRepressorActionPerformed

    private void I_cmbInhibitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I_cmbInhibitorActionPerformed
        I_txtInhibitor.setText((String) I_cmbInhibitor.getSelectedItem());
    }//GEN-LAST:event_I_cmbInhibitorActionPerformed

    private void I_cmbActivatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I_cmbActivatorActionPerformed
        I_txtActivator.setText((String) I_cmbActivator.getSelectedItem());
    }//GEN-LAST:event_I_cmbActivatorActionPerformed

    private void O_cmbInducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_O_cmbInducerActionPerformed
        O_txtInducer.setText((String) O_cmbInducer.getSelectedItem());
    }//GEN-LAST:event_O_cmbInducerActionPerformed

    private void O_cmbRepressorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_O_cmbRepressorActionPerformed
        O_txtRepressor.setText((String) O_cmbRepressor.getSelectedItem());
    }//GEN-LAST:event_O_cmbRepressorActionPerformed

    private void O_cmbInhibitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_O_cmbInhibitorActionPerformed
        O_txtInhibitor.setText((String) O_cmbInhibitor.getSelectedItem());
    }//GEN-LAST:event_O_cmbInhibitorActionPerformed

    private void O_cmbActivatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_O_cmbActivatorActionPerformed
        O_txtActivator.setText((String) O_cmbActivator.getSelectedItem());
    }//GEN-LAST:event_O_cmbActivatorActionPerformed

    private void O_cmbReporterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_O_cmbReporterActionPerformed
        O_txtReporter.setText((String) O_cmbReporter.getSelectedItem());
    }//GEN-LAST:event_O_cmbReporterActionPerformed

    private void O_txtRepressorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_O_txtRepressorActionPerformed
         O_txtRepressor.setText((String) O_cmbRepressor.getSelectedItem());
    }//GEN-LAST:event_O_txtRepressorActionPerformed

    private void btnShowPartsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowPartsActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_btnShowPartsActionPerformed

    private void btnShowPartsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowPartsMouseClicked

        io = new IO_Info();
        // Inputs
        if (I_txtActivity.getText().trim().length() > 0 && I_txtActivity.getText() != null) {
            io.I_Activity = I_txtActivity.getText();
        }
        if (I_txtInducer.getText().trim().length() > 0 && I_txtInducer.getText() != null) {
            io.I_Inducer = I_txtInducer.getText();
        }
        if (I_txtRepressor.getText().trim().length() > 0 && I_txtRepressor.getText() != null) {
            io.I_Repressor = I_txtRepressor.getText();
        }
        if (I_txtInhibitor.getText().trim().length() > 0 && I_txtInhibitor.getText() != null) {
            io.I_Inhibitor = I_txtInhibitor.getText();
        }
        if (I_txtActivator.getText().trim().length() > 0 && I_txtActivator.getText() != null) {
            io.I_Activator = I_txtActivator.getText();
        }

        // Outputs
        if (O_txtInducer.getText().trim().length() > 0 && O_txtInducer.getText() != null) {
            io.O_Inducer = O_txtInducer.getText();
        }
        if (O_txtRepressor.getText().trim().length() > 0 && O_txtRepressor.getText() != null) {
            io.O_Repressor = O_txtRepressor.getText();
        }
        if (O_txtInhibitor.getText().trim().length() > 0 && O_txtInhibitor.getText() != null) {
            io.O_Inhibitor = O_txtInhibitor.getText();
        }
        if (O_txtActivator.getText().trim().length() > 0 && O_txtActivator.getText() != null) {
            io.O_Activator = O_txtActivator.getText();
        }
        if (O_txtReporter.getText().trim().length() > 0 && O_txtReporter.getText() != null) {
            io.O_Reporter = O_txtReporter.getText();
        }
        if (O_txtRegulator.getText().trim().length() > 0 && O_txtRegulator.getText() != null) {
            io.O_Regulator = O_txtRegulator.getText();
        }

        //System.out.println(io.I_Activator + " and " + io.O_Activator);
        findMatchedParts();

        //BuildGraph bg = new BuildGraph();
        //bg.HighlightParts(matchedPartIDs);
        //DeviceGraph dg = new DeviceGraph(getPartIds(matchedPartIDs));

        // Highlight Part nodes.
        deviceGraph.paintNodesEvent(getPartIds(matchedPartIDs));
}//GEN-LAST:event_btnShowPartsMouseClicked

    private void checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkActionPerformed

    private void O_cmbRegulatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_O_cmbRegulatorActionPerformed
        O_txtRegulator.setText((String) O_cmbRegulator.getSelectedItem());
    }//GEN-LAST:event_O_cmbRegulatorActionPerformed

    private void btnShortestPathMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShortestPathMouseClicked

        deviceGraph.paintNodesPathEvent(pathFinder.ShortestPath());
        // Highlight Shostest Path
        //bricksGraph.paintNodesEvent(pathFinder.ShortestPath());

    }//GEN-LAST:event_btnShortestPathMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox I_cmbActivator;
    private javax.swing.JComboBox I_cmbActivity;
    private javax.swing.JComboBox I_cmbInducer;
    private javax.swing.JComboBox I_cmbInhibitor;
    private javax.swing.JComboBox I_cmbRepressor;
    private javax.swing.JTextField I_txtActivator;
    private javax.swing.JTextField I_txtActivity;
    private javax.swing.JTextField I_txtInducer;
    private javax.swing.JTextField I_txtInhibitor;
    private javax.swing.JTextField I_txtRepressor;
    private javax.swing.JComboBox O_cmbActivator;
    private javax.swing.JComboBox O_cmbInducer;
    private javax.swing.JComboBox O_cmbInhibitor;
    private javax.swing.JComboBox O_cmbRegulator;
    private javax.swing.JComboBox O_cmbReporter;
    private javax.swing.JComboBox O_cmbRepressor;
    private javax.swing.JTextField O_txtActivator;
    private javax.swing.JTextField O_txtInducer;
    private javax.swing.JTextField O_txtInhibitor;
    private javax.swing.JTextField O_txtRegulator;
    private javax.swing.JTextField O_txtReporter;
    private javax.swing.JTextField O_txtRepressor;
    private javax.swing.JButton btnShortestPath;
    private javax.swing.JButton btnShowParts;
    private javax.swing.JCheckBox check;
    private javax.persistence.EntityManager igem_dbPUEntityManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3_deviceGraph;
    private javax.swing.JPanel jPanel4_biobrickGraph;
    private java.util.List<bioguide.Inputs> ınputsList;
    private javax.persistence.Query ınputsQuery;
    // End of variables declaration//GEN-END:variables
}

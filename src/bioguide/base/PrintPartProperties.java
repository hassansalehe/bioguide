/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.base;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Akif
 */
public class PrintPartProperties {

    //1
    //Clicked Part Object
    static ClickedPart clickedPart = new ClickedPart();
    // DB
   private static Statement st = null;
   private static ResultSet rs = null;
   private static Connection conn = null;
   private static java.sql.PreparedStatement pstmt = null;
   public static PartInfo part = new PartInfo();
    /***********************  Revoke Part Properties   ****************************************/
    public static void revokePartProperties (String partID)
    {//printPartProperties

        try {
            getConnection();
            //System.out.println("Connection get");
            //System.out.print(partID + " , ");
            // Get Activities
            String SELECT = "Select * from parts Where partID like '%" + partID + "%'";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();
            //System.out.println(part.shortDesc + "-->");
            //int i = 1;
            // Get Part Properties
            while (rs.next())
            {
                part.partID =         "PART ID.................." + ".......: " + partID;
                part.partName =       "PART NAME................" + ".......: " +  rs.getString(2);
                part.shortDesc =      "SHORT DESCRIPTION :" + "\n-------------------\n " +  rs.getString(3);
                part.Desc =           "DESCRIPTION.............." + ".......: " +  rs.getString(4);
                part.partType =       "PART TYPE................" + ".......: " +  rs.getString(5);
                part.partSequence =   "PART SEQUENCE :"             + "\n-------------------\n" +  rs.getString(6);
                part.subPart =        "SUBPART.................." + ".......: " +  rs.getString(7);
                part.confirm = rs.getBoolean(8);
                part.Confirm =        "CONFIRMED................" + ".......: " +  part.confirm;
                part.nickName =       "NICK NAME................" + ".......: " +  rs.getString(9);
                part.qualitativeExp = "QUALITATIVE EXPERIENCE..." + ".......: " +  rs.getString(10);
                part.DNAStatus =      "DNA STATUS..............." + ".......: "  +  rs.getString(11);
                part.groupFavourite = "GROUP FAVORITE..........." + ".......: " +  rs.getString(12);
                part.starRating =     "STAR RATING.............." + ".......: " +  rs.getString(13);
                part.partLength = rs.getInt(14);
                part.PartLength =     "PART LENGTH.............." + ".......: " +  part.partLength;
                part.Bricks =         "BRICKS..................." + ".......: " +  rs.getString(15);
                part.BrickIDs =       "BRICK IDs................" + ".......: " +  rs.getString(16);
                //++i;
            }

            // Get Input properties
           SELECT = "Select i.Promoter,i.Activity ,i.Inducer ,i.Activator ,i.Repressor ,i.Inhibitor ," +
                    "i.Promoter2 ,i.Activity2 ,i.Inducer2 ,i.Activator2,i.Repressor2,i.Inhibitor2 " +
                    "FROM inputtopart ip, inputs i" +
                    " WHERE ip.partID = '" + partID + "' AND ip.InputID = i.InputID";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.Input = "\n:::::::::" + " INPUTS " + ":::::::::\n";

            while (rs.next())
            {
                if (rs.getString(1) != null)
                    part.Input += "    PROMOTER : " + rs.getString(1) + " \n ";
                if (rs.getString(2) != null)
                    part.Input += "   ACTIVITY : " + rs.getString(2) + " \n ";
                if (rs.getString(3) != null)
                    part.Input += "   INDUCER : " + rs.getString(3) + " \n ";
                if (rs.getString(4) != null)
                    part.Input += "   ACTIVATOR : " + rs.getString(4) + " \n ";
                if (rs.getString(5) != null)
                    part.Input += "   REPRESSOR : " + rs.getString(5) + " \n ";
                if (rs.getString(6) != null)
                    part.Input += "   INHIBITOR : " + rs.getString(6) + " \n ";
                if (rs.getString(7) != null)
                    part.Input += "   PROMOTER-2 : " + rs.getString(7) + " \n ";
                if (rs.getString(8) != null)
                    part.Input += "   ACTIVITY-2 : " + rs.getString(8) + " \n ";
                if (rs.getString(9) != null)
                    part.Input += "   INDUCER-2 : " + rs.getString(9) + " \n ";
                if (rs.getString(10) != null)
                    part.Input += "   ACTIVATOR-2 : " + rs.getString(10) + " \n ";
                if (rs.getString(11) != null)
                    part.Input += "   REPRESSOR-2 : " + rs.getString(11) + " \n ";
                if (rs.getString(12) != null)
                    part.Input += "   INHIBITOR-2 : " + rs.getString(12);
            }


           // Get OutpUt properties
           SELECT = "Select o.`Reporter`,o.`Regulator`,o.`Inducer`,o.`Activator`,o.`Repressor`,o.`Inhibitor`, " +
                    " o.`Reporter2`,o.`Regulator2`,o.`Inducer2`,o.`Activator2`,o.`Repressor2`,o.`Inhibitor2`,o.`WorkingCondition` " +
                    " from outputfrompart op, outputs o " +
                    " Where op.`partID` = '" + partID + "' and op.`OutputID`= o.`OutputID` ";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.Output = ":::::::::" + " OUTPUTS " + ":::::::::\n";

            while (rs.next())
            {
                if (rs.getString(1) != null)
                    part.Output += "   REPORTER : " + rs.getString(1) + " \n ";
                if (rs.getString(2) != null)
                    part.Output += "    REGULATOR : " + rs.getString(2) + " \n ";
                if (rs.getString(3) != null)
                    part.Output += "   INDUCER : " + rs.getString(3) + " \n ";
                if (rs.getString(4) != null)
                    part.Output += "   ACTIVATOR : " + rs.getString(4) + " \n ";
                if (rs.getString(5) != null)
                    part.Output += "   REPRESSOR : " + rs.getString(5) + " \n ";
                if (rs.getString(6) != null)
                    part.Output += "   INHIBITOR : " + rs.getString(6) + " \n ";
                if (rs.getString(7) != null)
                    part.Output += "   REPORTER-2 : " + rs.getString(7) + " \n ";
                if (rs.getString(8) != null)
                    part.Output += "   REGULATOR-2 : " + rs.getString(8) + " \n ";
                if (rs.getString(9) != null)
                    part.Output += "   INDUCER-2 : " + rs.getString(9) + " \n ";
                if (rs.getString(10) != null)
                    part.Output += "   ACTIVATOR-2 : " + rs.getString(10) + " \n ";
                if (rs.getString(11) != null)
                    part.Output += "   REPRESSOR-2 : " + rs.getString(11) + " \n ";
                if (rs.getString(12) != null)
                    part.Output += "   INHIBITOR-2 : " + rs.getString(12) + " \n ";
                if (rs.getString(12) != null)
                    part.Output += "   WORKING CONDITION : " + rs.getString(13);
            }

           // Get RFC status
           SELECT = " SELECT pr.`RFC10`,pr.`RFC21`,pr.`RFC23`,pr.`RFC25` " +
                    " FROM partrfcstatus pr " +
                    " WHERE partID = '" + partID + "'";

            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.RFC = ":::::::::" + " RFC STATUS " + ":::::::::\n";
            while (rs.next())
            {
                    part.RFC += "RFC-10 : " + rs.getBoolean(1) + " // ";
                    part.RFC += "RFC-21 : " + rs.getBoolean(2) + " // ";
                    part.RFC += "RFC-23 : " + rs.getBoolean(3) + " // ";
                    part.RFC += "RFC-25 : " + rs.getBoolean(4) + " // ";
            }

           // Get part categories
           SELECT = " SELECT ca.`Cname1`, ca.`Cname2`,ca.`Cname3`,ca.`Cname4` " +
                    " FROM partcategories pc, categories ca " +
                    " WHERE pc.`partID` = '" + partID +" ' AND pc.`categoryID` = ca.`categoryID` ";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.Category =  ":::::::::" + " PART CATEGORIES " + ":::::::::\n";
            part.Categories = new ArrayList<String>();
            while (rs.next())
            {
                if (rs.getString(1) != null)
                    part.Category += rs.getString(1) + " // ";
                if (rs.getString(2) != null)
                    part.Category += rs.getString(2) + " // ";
                if (rs.getString(3) != null)
                    part.Category += rs.getString(3) + " // ";
                if (rs.getString(4) != null)
                    part.Category += rs.getString(4) + " // ";

                part.Categories.add(part.Category) ;
                part.Category = "        > ";
            }

           // Get part parameters
           SELECT = " SELECT p.pName,pp.subParam " +
                    " FROM parameterz p, partparam pp " +
                    " WHERE pp.partID = '"+ partID + "' AND pp.parameterID = p.parameterID";

            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.Parameter = ":::::::::" + " PART PARAMETERS " + ":::::::::\n";
            part.Parameters = new ArrayList<String>();
            while (rs.next())
            {
                if (rs.getString(1) != null)
                    part.Parameter += rs.getString(1) + " // ";
                if (rs.getString(2) != null)
                    part.Parameter += rs.getString(2) + " // ";
                part.Parameters.add(part.Parameter) ;
                part.Parameter = "        > ";
            }

            // Get Cell
           SELECT = "SELECT ce.cName " +
                    "FROM partcells pc, cell ce "+
                    "WHERE pc.partID ='" + partID + "' AND pc.cellID = ce.cellID";

            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.Cell =  ":::::::::" + " PART CELLS      " + ":::::::::\n";
            part.Cells = new ArrayList<String>();
            while (rs.next())
            {
                if (rs.getString(1) != null)
                    part.Cell += rs.getString(1) + " // ";
                part.Cells.add(part.Cell) ;
                part.Cell = "        > ";
            }

           // Get Part Resistant
           SELECT = "SELECT r.`rName` " +
                    "FROM partresistants pr, resistant r " +
                    "WHERE pr.`partID` = '" + partID + "' AND pr.`resistantID` = r.`resistantID`";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.Resistant = ":::::::::" + " PART RESISTANTS " + ":::::::::\n";
            part.Resistants = new ArrayList<String>();
            while (rs.next())
            {
                if (rs.getString(1) != null)
                    part.Resistant += rs.getString(1) + " // ";
                part.Resistants.add(part.Resistant) ;
                part.Resistant = "        > ";
            }

            // Get part Designers
           SELECT = "SELECT d.dNames,d.Mail " +
                    "FROM designers d, partdesigner pd " +
                    "WHERE pd.partID = '" + partID + "' AND pd.DesignerID = d.DesignerID";

            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.Designer = ":::::::::" + " PART DESIGNERS  " + ":::::::::\n";
            part.Designers = new ArrayList<String>();
            while (rs.next())
            {
                if (rs.getString(1) != null)
                    part.Designer += "NAME : " + rs.getString(1) + " \n";
                if (rs.getString(2) != null)
                    part.Designer += "CONTACT : " + rs.getString(2) + " // ";
                part.Designers.add(part.Designer) ;
                part.Designer = "        > ";
            }

           // Get Plasmids
           SELECT = "SELECT p.`pName` " +
                    "FROM plasmid p, partplasmids pp " +
                    "WHERE pp.`partID` = '" + partID + "' AND p.`plasmidID` = pp.`plasmidID`";
            pstmt = conn.prepareStatement(SELECT);
            rs = pstmt.executeQuery();

            part.Plasmid = ":::::::::" + " PART PLASMIDS   " + ":::::::::\n";
            part.Plasmids = new ArrayList<String>();
            while (rs.next())
            {
                if (rs.getString(1) != null)
                    part.Plasmid += rs.getString(1) + " // ";
                part.Plasmids.add(part.Plasmid) ;
                part.Plasmid = "        > ";
            }

            /******************** PRINTERS *******************************/
            /*    System.out.println(part.partID);
                //System.out.println(part.shortDesc);
                System.out.println(part.Desc);
                System.out.println(part.partType);
                System.out.println(part.partSequence);
                System.out.println(part.subPart);
                System.out.println(part.Confirm);
                System.out.println(part.nickName);
                System.out.println(part.qualitativeExp);
                System.out.println(part.DNAStatus);
                System.out.println(part.groupFavourite);
                System.out.println(part.starRating);
                System.out.println(part.PartLength);
            System.out.println(part.Bricks);
            System.out.println(part.BrickIDs);
            System.out.println(part.Input);
            System.out.println(part.Output);
            System.out.println(part.RFC);
            for (int i = 0; i < part.Categories.size(); i++)
            {
                System.out.println(part.Categories.get(i));
            }

            for (int i = 0; i < part.Parameters.size(); i++)
            {
                System.out.println(part.Parameters.get(i));
            }

            for (int i = 0; i < part.Cells.size(); i++)
            {
                System.out.println(part.Cells.get(i));
            }

            for (int i = 0; i < part.Resistants.size(); i++)
            {
                System.out.println(part.Resistants.get(i));
            }

            for (int i = 0; i < part.Designers.size(); i++)
            {
                System.out.println(part.Designers.get(i));
            }

            for (int i = 0; i < part.Plasmids.size(); i++)
            {
                System.out.println(part.Plasmids.get(i));
            }*/
        } catch (SQLException ex) {
            Logger.getLogger(Parts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /***********************  Revoke Part Properties   ****************************************/

    /*Gets DB Connection*/
    private static void getConnection()
    {
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
            conn = (Connection) DriverManager.getConnection(url+dbName, userName, password);

        } catch (SQLException ex) {
            Logger.getLogger(Parts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Parts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Parts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Parts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

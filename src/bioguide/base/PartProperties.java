/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PartProperties.java
 *
 * Created on Oct 25, 2010, 12:15:05 AM
 */

package bioguide.base;

/**
 *
 * @author Akif
 */
public class PartProperties extends javax.swing.JFrame {

    /** Creates new form PartProperties */
    public PartProperties() {
        initComponents();
        //java.awt.EventQueue.invokeLater(event.postEvent(
        // Image box
        //image.setIcon(new javax.swing.ImageIcon(getClass().getResource("BioGuide/src/bioguide/resources1.png")));
        
        // NOI18N

        //tblPart.add(PrintPartProperties.part.partID, 2);
        txtPart.setText(PrintPartProperties.part.partID + "\n");
        //txtPart.append(PrintPartProperties.part.partID + "\n");
        //txtPart.append(PrintPartProperties.part.shortDesc + "\n");
        txtPart.append(PrintPartProperties.part.partType + "\n");
        //txtPart.append(PrintPartProperties.part.partSequence + "\n");
        txtPart.append(PrintPartProperties.part.subPart + "\n");
        txtPart.append(PrintPartProperties.part.Confirm + "\n");
        txtPart.append(PrintPartProperties.part.nickName + "\n");
        txtPart.append(PrintPartProperties.part.qualitativeExp + "\n");
        txtPart.append(PrintPartProperties.part.DNAStatus + "\n");
        txtPart.append(PrintPartProperties.part.groupFavourite + "\n");
        txtPart.append(PrintPartProperties.part.starRating + "\n");
        txtPart.append(PrintPartProperties.part.PartLength + "\n");
        txtPart.append(PrintPartProperties.part.Bricks + "\n");
        txtPart.append(PrintPartProperties.part.BrickIDs + "\n");
        txtPart.append(PrintPartProperties.part.Input + "\n");
        txtPart.append(PrintPartProperties.part.Output + "\n");
        txtShortDesc.append(PrintPartProperties.part.shortDesc + "\n");
        txtPartSequence.append(PrintPartProperties.part.partSequence + "\n");
            for (int i = 0; i <PrintPartProperties.part.Categories.size(); i++)
            {
                txtPart.append(PrintPartProperties.part.Categories.get(i) + "\n");
            }

            for (int i = 0; i < PrintPartProperties.part.Parameters.size(); i++)
            {
                txtPart.append(PrintPartProperties.part.Parameters.get(i) + "\n");
            }

            for (int i = 0; i < PrintPartProperties.part.Cells.size(); i++)
            {
                txtPart.append(PrintPartProperties.part.Cells.get(i) + "\n");
            }

            for (int i = 0; i < PrintPartProperties.part.Resistants.size(); i++)
            {
                txtPart.append(PrintPartProperties.part.Resistants.get(i) + "\n");
            }

            for (int i = 0; i < PrintPartProperties.part.Plasmids.size(); i++)
            {
                txtPart.append(PrintPartProperties.part.Plasmids.get(i) + "\n");
            }
        
            for (int i = 0; i < PrintPartProperties.part.Designers.size(); i++)
            {
                txtPart.append(PrintPartProperties.part.Designers.get(i) + "\n");
            }

                /*
                System.out.println(part.partID);
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
            }

         */
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtPart = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPartSequence = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtShortDesc = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        txtPart.setColumns(20);
        txtPart.setEditable(false);
        txtPart.setFont(new java.awt.Font("Monospaced", 3, 13));
        txtPart.setLineWrap(true);
        txtPart.setRows(5);
        jScrollPane1.setViewportView(txtPart);

        txtPartSequence.setColumns(20);
        txtPartSequence.setEditable(false);
        txtPartSequence.setFont(new java.awt.Font("Monospaced", 3, 13));
        txtPartSequence.setLineWrap(true);
        txtPartSequence.setRows(5);
        jScrollPane2.setViewportView(txtPartSequence);

        txtShortDesc.setColumns(20);
        txtShortDesc.setEditable(false);
        txtShortDesc.setFont(new java.awt.Font("Monospaced", 3, 13));
        txtShortDesc.setLineWrap(true);
        txtShortDesc.setRows(5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtShortDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtShortDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(332, 332, 332)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
        );

        setBounds(100, 100, 688, 590);
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PartProperties().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea txtPart;
    private javax.swing.JTextArea txtPartSequence;
    private javax.swing.JTextArea txtShortDesc;
    // End of variables declaration//GEN-END:variables

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.gui;

/**
 *
 * @author labuser
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        mainForm mF = new mainForm();
        mF.init();
        mF.setBounds(0, 0, 1280 , 400);
        mF.setVisible(true);


        //DeviceGraph cc = new DeviceGraph(null);
        //cc.start();
        //BuildGraph bg = new BuildGraph();

        //bg.setSize(50, 50);
        //bg.setLocation(1000, 420);
        // mF.setContentPane(bg);
        //bg.start();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import y.base.Edge;
import y.base.Graph;
import y.base.Node;
import y.io.GMLIOHandler;
import y.io.IOHandler;
import y.view.Arrow;
import y.view.EdgeLabel;
import y.view.EdgeRealizer;
import y.view.EditMode;
import y.view.Graph2D;
import y.view.Graph2DSelectionListener;
import y.view.Graph2DView;
import y.view.ImageNodeRealizer;
import y.view.NodeLabel;

/**
 *
 * @author Akif
 */

public class runner {

 // Create a new, empty graph.
//Graph graph = new Graph();
// Create a complete copy of 'graph'.
//Graph graphCopy = new Graph(graph);

Graph2DView view;

 public runner()
 {
      //setLayout(new BorderLayout());
      view = new Graph2DView();
      EditMode mode = new EditMode();
      view.addViewMode(mode);
      //add(view);

      buildGraph();
      configureDefaultRealizers();

 }
protected void configureDefaultRealizers()
{
      Graph2D graph = view.getGraph2D();

      //change the looks of the default edge
      EdgeRealizer er = graph.getDefaultEdgeRealizer();
      //a standard (target) arrow
      er.setArrow(Arrow.STANDARD);
      //set diamond source arrow
      er.setSourceArrow(Arrow.WHITE_DIAMOND);
      //a label for the edge
      EdgeLabel elabel = new EdgeLabel("LABEL");
      er.addLabel(elabel);

      //change the looks (and type) of the default node

      //register an image with ImageNodeRealizer.
      //must be a path name relative to your java CLASSPATH.
      ImageNodeRealizer ivr = new ImageNodeRealizer();

      //set the image
     // ivr.setImageURL(getClass().getResource("resources/yicon.png"));
      //set node size equals to half of original image size
      ivr.setToImageSize();
      ivr.setSize(ivr.getWidth()/2, ivr.getHeight()/2);
      //set a label text
      ivr.setLabelText("yFiles");

      //set the label model to be 8-pos (eight available positions around node)
      ivr.getLabel().setModel(NodeLabel.EIGHT_POS);

      //set the label position (S == South of Node)
      ivr.getLabel().setPosition(NodeLabel.S);


     //use it as default node realizer
     graph.setDefaultNodeRealizer(ivr);

}

public void buildGraph()
{       
            Graph2D graph = view.getGraph2D();
            //change the looks of the default edge
            EdgeRealizer er = graph.getDefaultEdgeRealizer();
            //a standard (target) arrow
            er.setArrow(Arrow.DELTA);
            //set diamond source arrow
            er.setSourceArrow(Arrow.DELTA);
            er.setLineColor(Color.PINK);

       try {
            IOHandler ioh = new GMLIOHandler();
            ioh.read(graph, "out.gml");

            Edge [] edges = graph.getEdgeArray();
            //Node [] nodes = graph.getNodeArray();

            for (int i = 0; i < edges.length; i++)
            {
                EdgeRealizer er1 = graph.getRealizer(edges[i]);
                er1.setArrow(Arrow.DELTA);
                er1.setLabelText("");
            }

            view.fitContent();
            view.setFitContentOnResize(true);
            view.setToolTipText("Parts Graph");
           // graph.addGraph2DSelectionListener(this);

        } catch (IOException ex) {
            Logger.getLogger(runner.class.getName()).log(Level.SEVERE, null, ex);
        }

}

public final void addContentTo( final JRootPane rootPane )
{
    // rootPane.setContentPane(this);
}

public void start()
{
     JFrame graphFrame = new JFrame(getClass().getName());
     graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     addContentTo(graphFrame.getRootPane());
     graphFrame.pack();
     graphFrame.setLocation(0, 0);
     graphFrame.setVisible(true);
     graphFrame.setBounds(1, 1, 1200, 700);
     //System.out.println(view.getGraph2D().selectedNodes().size());//while(true){System.out.println("hassa");}
}

/*public static void main(String[] args) {
     EventQueue.invokeLater(new Runnable() {
       public void run() {
         (new runner()).start();
       }
     });
   }
*/

/* Highlight the parts contains the specified I/O informations */
public void HighlightParts ()
{
    mainForm mf  =new mainForm();
    Vector <String> partIDs = mf.getMatchedParts();
    

}

/* Get the partIDs to highlight */
public void HighlightParts (Vector <String> partIDs)
{
    mainForm mf  =new mainForm();
    
}

}

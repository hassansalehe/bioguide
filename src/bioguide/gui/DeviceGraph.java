/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.gui;



import java.awt.*;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import y.base.Edge;

import y.base.Node;
import y.io.GMLIOHandler;
import y.io.IOHandler;
import y.view.Graph2DView;
import y.view.EditMode;

import y.view.Arrow;
import y.view.EdgeRealizer;
import y.view.NodeRealizer;
import y.view.ImageNodeRealizer;
import y.view.NodeLabel;
import y.view.EdgeLabel;
import y.view.Graph2D;
import y.view.LineType;

public final class DeviceGraph extends  JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JPanel content;

    static private Vector<Device> devices;

        Graph2DView brickView;
        int event = 0;
        public boolean visible = true;

            public DeviceGraph(String []bioB)
            {
              setLayout(new BorderLayout());
              brickView = new Graph2DView();
              EditMode mode = new EditMode();
              brickView.addViewMode(mode);
              add(brickView);
              
              if (bioB.length <= 0)
              {
                System.out.println("No such a device");
                visible = false;
              }
              else
              {
                  buildGraph(bioB);
                  configureDefaultRealizers();
              }
            }

            protected void configureDefaultRealizers()
            {
              Graph2D graph = brickView.getGraph2D();

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
//              ivr.setImageURL(getClass().getResource("resources/1.png"/*yicon.png"*/));
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

            /* ViewMode vm = new ViewMode();

             vm.setActiveView(brickView);
             vm.init();
             MouseListener [] ls  = brickView.getMouseListeners();
             brickView.addViewMode(vm);
             ls[0].mouseClicked(null);*/

           }

           void buildGraph(String[] bioB)
           {
              // getNodeNames();
             Graph2D graph = brickView.getGraph2D();

            // Node [] nodes = new Node[nodelist.length];
            /* Node v = graph.createNode();
             //get the "graphics realizer" of v2 from the graph
             NodeRealizer vr = graph.getRealizer(v);
             vr.setLocation(300,500);
             vr.setSize(100,30);
             vr.setFillColor(Color.RED);
             vr.setLabelText("BBa_JI060020");

             int level = 200;
             Node w;
             */
              //change the looks of the default edge
              EdgeRealizer er = graph.getDefaultEdgeRealizer();
              //a standard (target) arrow
              er.setArrow(Arrow.DELTA);
              //set diamond source arrow
              er.setSourceArrow(Arrow.DELTA);
              er.setLineColor(Color.PINK);

                  IOHandler ioh = new GMLIOHandler();

              //System.out.println();
                try {
                    ioh.read(graph, "NodesGraph_Biobrick_Compatibility.gml");
                    //NodesGraph_IO_Compatibility
                    //NodesGraph_Biobrick_Compatibility
                    //NodesGraph_Biobrick_Comp_Chem
                    //out
                    Edge [] edges = graph.getEdgeArray();
                    EdgeRealizer rm = null;
                    for(int i = 0; i < edges.length; i++)
                    {
                    rm = graph.getRealizer(edges[i]);
                    rm.setArrow(Arrow.DELTA);
                    rm.setLabelText("");
                    //System.out.println(rm.isVisible());

                    }

                    //brickView.fitWorldRect();
                   //brickView.fitContent();

                    brickView.setFitContentOnResize(true);
                    brickView.setToolTipText("BIOBRICKS GRAPH");

             if(bioB != null){

                Node []nds = graph.getNodeArray();
                Edge []edgs = graph.getEdgeArray();
                int last = 0;

                if(bioB.length > 1)
                {

                    for(int i = 0; i < bioB.length-1; i++)
                    {
                        Node src = null;
                        Node tgt = null;

                        for(int x = 0; x < nds.length; x++)
                        {
                            NodeRealizer rs = graph.getRealizer(nds[x]);
                            if(rs.getLabelText().equals(bioB[i]))
                            {
                                src = nds[x];
                                if(i == 0)
                                    rs.setFillColor(Color.magenta);
                                else
                                    rs.setFillColor(Color.green);

                                rs.setSize(50, 50);
                                rs.getLabel().setFontSize(23);
                                rs.getLabel().setFontStyle(3);
                                rs.repaint();
                                break;
                            }
                        }
                        int ixm = i+1;
                        for(int x = 0; x < nds.length; x++)
                        {
                            NodeRealizer rs = graph.getRealizer(nds[x]);
                            if(rs.getLabelText().equals(bioB[ixm]))
                            {
                                tgt = nds[x];
                                last = ixm;
                                break;
                            }
                        }

                        for(int j = 0; j < edgs.length; j++)
                        {
                            if((edgs[j].source() == src) && (edgs[j].target() == tgt))
                            {
                                EdgeRealizer es =  graph.getRealizer(edgs[j]);
                                es.setLineColor(Color.GRAY);
                                es.repaint();
                                es.setLineType(LineType.LINE_4);
                                break;

                            }
                        }
                    }
                }

                for(int x = 0; x < nds.length; x++)
                {
                    NodeRealizer rs = graph.getRealizer(nds[x]);
                    if(rs.getLabelText().equals(bioB[last]))
                    {
                        rs.setFillColor(Color.yellow);
                        rs.setSize(50, 50);
                        rs.getLabel().setFontSize(23);
                        rs.getLabel().setFontStyle(3);
                    }
                }
                }
                    //brickView.setViewPoint(10, 10);
                    //brickView.setZoom(5);
                    //brickView.zoomToArea(0, 0, 4000, 4000);
                    //System.out.println(brickView.getGraph2D().getURL());
                    //graph.selectAllNodesAndBends(true);

        //            graph.addGraph2DSelectionListener(this);



                    // InputStream inp = new InputStream(new FileReader("NodesGraph_Biobrick_Comp_Chem.gml"));
                    // for(int i = 0; i < nodelist.length; i++)
                    //{
                    //      if((i % 20) < 10)
                    //        /*w*/nodes[i] = graph.createNode(((i%10)+1)*90, (i/10)*90, 60, 20, nodelist[i].Name);
                    //  else /*w*/nodes[i] = graph.createNode(((i%10)+1)*90, (i/10)*90, 60, 30, nodelist[i].Name);
                    /*    graph.createEdge(v,w);
                    graph.createEdge(v,v);
                    graph.createEdge(w,w);*/
                    // }
                    // int pm = 0;
                    //for(int i = 0; i < nodelist.length; i++)
                    //{
                    //  int j = nodelist[i].index;
                    //for(int k = 0; k < j; k++)
                    //{pm++;
                    //  graph.createEdge(nodes[i], nodes[nodelist[i].Edges[k]] );
                    //}
                    //}
                    //System.out.println(nodelist.length + " "+ pm);
                    //ourNode nd = null;
                    //int max = 0;
                    //int index = 0;
                    //for(int a = 0; a < nodelist.length; a++)
                    //{
                    //  System.out.println(a + "   "+ nodelist[a].index);
                    //if(nodelist[a].index > max)
                    //{
                    //  max = nodelist[a].index;
                    //nd = nodelist[a];
                    //System.out.println(max);
                    //index = a;
                    //NodeRealizer vr = graph.getRealizer(nodes[index]);
                    // vr.setFillColor(Color.RED);
                    //   }
                    //}
                    //vr.setLabelText("BBa_JI060020");
                    // int level = 200;
                    //vr.setLabelText("BBa_JI060020");
                    //create some edges and new nodes
                    /*   for(int i = 0; i < 50; i++)
                    {
                    if(i < 25)
                    {
                    if((i % 2) == 0)
                    w = graph.createNode(200 -13*i, 100 + i*17, 80, 30, "Node " + (i+1));
                    else w = graph.createNode(400+ 14*i, 100 + i*17, 80, 30, "Node " + (i+1));
                    }
                    else {
                    if((i % 2) == 0)
                    w = graph.createNode(200 -13*(50 -i), 100 + i*17, 80, 30, "Node " + (i+1));
                    else w = graph.createNode(400+ 14*(50-i), 100 + i*17, 80, 30, "Node " + (i+1));
                    }
                    graph.createEdge(v,w);
                    graph.createEdge(v,v);
                    graph.createEdge(w,w);
                    }*/
                } catch (IOException ex) {
                    Logger.getLogger(DeviceGraph.class.getName()).log(Level.SEVERE, null, ex);
                }

           }

           public void start()
           {
             JFrame frame = new JFrame(getClass().getName());
             frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
             addContentTo(frame.getRootPane());
             frame.pack();
             //frame.setLocationRelativeTo(null);
             mainForm mf = new mainForm();
             //frame.setLocation(mf.getX(), mf.getY() + mf.getHeight());
            //frame.setLocation(1,410);
             if (visible == false)
                 frame.setVisible(false);
             else
                frame.setVisible(true);
             //int y =  mf.getY() + mf.getHeight();
             frame.setBounds(mf.getX(), 400 , 1280, 420);
             //System.out.println(brickView.getGraph2D().selectedNodes().size());//while(true){System.out.println("hassa");}
           }
    /*   public void start()
       {
         JDialog frame = new JDialog();
         frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
         addContentTo(frame.getRootPane());
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
         frame.setBounds(1, 1, 1200, 700);
         //System.out.println(brickView.getGraph2D().selectedNodes().size());//while(true){System.out.println("hassa");}
       }*/
       public final void addContentTo( final JRootPane rootPane )
       {
         rootPane.setContentPane(this);
       }
}

class Device {
        String partID;
        String brickIDs;
    }

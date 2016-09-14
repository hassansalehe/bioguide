/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.base;

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
import y.view.Graph2DSelectionEvent;
import y.view.Graph2DView;
import y.view.EditMode;

import y.view.Arrow;
import y.view.DefaultGraph2DRenderer;
import y.view.EdgeRealizer;
import y.view.NodeRealizer;
import y.view.ImageNodeRealizer;
import y.view.NodeLabel;
import y.view.EdgeLabel;
import y.view.Graph2D;
import y.view.Graph2DSelectionListener;
import y.view.LineType;
/**
 *
 * @author 
 */
public class BioBricks extends  JPanel  implements Graph2DSelectionListener
{
        Graph2DView brickView;
        int event = 0;

    static Parts parts = null;
    Color orgBioNodeColor;
    Color orgBioEdgeColor;
    Dimension orgBioNodeDim;
    LineType orgBioEdgeLine;
    int EdgeLayer;
    int NodeLayer;

    // Graph visualizers
    Graph2DView partsView;

    Color orgDevNodeColor;
    Color orgDevEdgeColor;
    java.awt.Dimension orgDevNodeDim;
    LineType orgDevEdgeline;
    NodeRealizer[] lastEventDeviceNodes;
    EdgeRealizer[] lastEventDeviceEdges;

    int DeviceFontSize;
    int DeviceFont;
    //

    static private NodeRealizer[] lastEventBioNodes;
    static private EdgeRealizer[] lastEventBioEdges;

    int BioFontSize;
    int BioFont;
    
    // from parts
    //static String Nodes [];
    //static String Edges [];
    static Edge [] edges;
    static Node [] nodes;
    static Graph2D graph;

    //1
    //Clicked Part Object
    static ClickedPart clickedPart = new ClickedPart();

            public BioBricks(String []bioB)
            {
              setLayout(new BorderLayout());
              brickView = new Graph2DView();
              EditMode mode = new EditMode();
              mode.allowNodeCreation(false);
              mode.allowEdgeCreation(false);
              
              brickView.addViewMode(mode);
              add(brickView);

              /*/from parts
              partsView = new Graph2DView();
              partsView.addViewMode(mode);
              add(partsView);*/

              buildGraph(bioB);
              configureDefaultRealizers();
            }

    BioBricks() {

    }

            protected void configureDefaultRealizers()
            {
              Graph2D graph = brickView.getGraph2D();

              /*/ from parts
              Graph2D graph1 = partsView.getGraph2D();
              graph1.getDefaultEdgeRealizer();*/

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
              ivr.setImageURL(getClass().getResource("resources/1.png"/*yicon.png"*/));
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
             graph = brickView.getGraph2D();

             /*/ from parts
             Graph2D graph1 = partsView.getGraph2D();
             graph1.getDefaultEdgeRealizer();*/
             
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
                    ioh.read(graph, "data/out.gml");
                    edges = graph.getEdgeArray();

                    // from parts
                    //this.Edges = new String [edges.length];
                    //this.Edges = graph.getEdgeArray();


                    EdgeRealizer rm = null;
                    for(int i = 0; i < edges.length; i++)
                    {
                    rm = graph.getRealizer(edges[i]);
                    rm.setArrow(Arrow.DELTA);
                    rm.setLabelText("");
                    rm.setLineColor(Color.lightGray);
                    rm.setLineType(LineType.DASHED_1);
                    //rm.setVisible(false);
                    //System.out.println(rm.isVisible());
                    }

                    nodes = graph.getNodeArray();

                    // from parts
                    //this.Nodes = new String[nodes.length];
                    
                    NodeRealizer nm = null;
                    for(int i = 0; i < nodes.length; i++)
                    {
                    nm = graph.getRealizer(nodes[i]);
                    nm.getLabel().setFontSize(20);
                    //rm.setVisible(false);
                    //System.out.println(rm.isVisible());
                    }

                DefaultGraph2DRenderer dgr = new DefaultGraph2DRenderer();
                // Drawing edges first implies that nodes are drawn last, i.e., on top of the edges.
                dgr.setDrawEdgesFirst(true);

                /*/ from parts
                // Register the freshly configured DefaultGraph2DRenderer with the given view.
                partsView.setGraph2DRenderer(dgr);
                //partsView.fitWorldRect();
                partsView.fitContent();
                partsView.setFitContentOnResize(true);
                partsView.setToolTipText("PATH GRAPH");*/
 
               // Register the freshly configured DefaultGraph2DRenderer with the given view.
                brickView.setGraph2DRenderer(dgr);

                    EdgeLayer = rm.getLayer();
                    NodeLayer = rm.getTargetRealizer().getLayer();
                    orgBioNodeColor = rm.getSourceRealizer().getFillColor();
                    orgBioEdgeColor = rm.getLineColor();
                    orgBioNodeDim = new Dimension();
                    orgBioNodeDim.setSize(rm.getSourceRealizer().getWidth(), rm.getSourceRealizer().getHeight());
                    orgBioEdgeLine = rm.getLineType();
                    lastEventBioNodes = null;
                    lastEventBioEdges = null;
                    BioFontSize = rm.getSourceRealizer().getLabel().getFontSize();
                    BioFont = rm.getSourceRealizer().getLabel().getFontStyle();

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

                                rs.setSize(80, 80);
                                rs.getLabel().setFontSize(28);
                                rs.getLabel().setFontStyle(3);
                                rs.getLabel().setFontStyle(Font.BOLD);
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
                                es.setLineColor(Color.green);
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
                        rs.setSize(80, 80);
                        rs.getLabel().setFontSize(28);
                        rs.getLabel().setFontStyle(3);
                        rs.getLabel().setFontStyle(Font.BOLD);
                    }
                }
                }

                    graph.addGraph2DSelectionListener(this);
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
                    Logger.getLogger(BioBricks.class.getName()).log(Level.SEVERE, null, ex);
                }

           }

            void updateGraph(String [] bioB)
            {
             if(bioB != null){
                 Graph2D graph = brickView.getGraph2D();

                 Vector <NodeRealizer> nodeR = new Vector<NodeRealizer>();
                 Vector<EdgeRealizer> edgeR = new Vector<EdgeRealizer>();
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
                                rs.setSize(80, 80);
                                rs.getLabel().setFontSize(28);
                                rs.getLabel().setFontStyle(3);
                                rs.getLabel().setFontStyle(Font.BOLD);
                                rs.setLayer((byte)(NodeLayer+1));
                                rs.setVisible(true);
                                rs.repaint();
                                nodeR.addElement(rs);
                                break;
                                // rs.setSize(0, 0);
                                //rs.setSize(70, 70);
                                //rs.getLabel().setFontSize(23);
                                //rs.getLabel().setFontStyle(3);
                                //rs.moveBy(13, 13);
                                //rs.setLocation(x, x);
                                //rs.setTransparent(true);
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
                                es.setLineColor(Color.green);
                                es.setLineType(LineType.LINE_7);
                                es.setLayer((byte)(EdgeLayer+1));
                                es.setVisible(true);
                                es.repaint();
                                edgeR.addElement(es);
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
                        rs.setSize(80, 80);
                        rs.getLabel().setFontSize(28);
                        rs.getLabel().setFontStyle(3);
                        rs.getLabel().setFontStyle(Font.BOLD);
                        rs.setLayer((byte)(EdgeLayer+1));
                        nodeR.addElement(rs);

                    }
                  }
                    lastEventBioNodes = new NodeRealizer[nodeR.size()];
                    lastEventBioEdges = new EdgeRealizer[edgeR.size()];
                 for(int i = 0; i <nodeR.size(); i++)
                     lastEventBioNodes[i] = nodeR.get(i);
                    
                 for(int j = 0; j < edgeR.size(); j++)
                     lastEventBioEdges[j] = edgeR.get(j);
                }
            }

           public void start()
           {
             JFrame frame = new JFrame(getClass().getName());
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             addContentTo(frame.getRootPane());
             frame.pack();
             frame.setLocationRelativeTo(null);
             frame.setVisible(true);
             frame.setBounds(1, 1, 1200, 700);
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

    public void onGraph2DSelectionEvent(Graph2DSelectionEvent gdse) {
            if(gdse.isNodeSelection()){

                if(event == 0)
                {
                    event++;

                    if(lastEventBioNodes != null)
                    {
                        for(int i = 0; i < lastEventBioNodes.length; i++)
                        {
                            lastEventBioNodes[i].setFillColor(orgBioNodeColor);
                            lastEventBioNodes[i].setWidth(orgBioNodeDim.getWidth());
                            lastEventBioNodes[i].setHeight(orgBioNodeDim.getHeight());
                            lastEventBioNodes[i].setLayer((byte)NodeLayer);
                            lastEventBioNodes[i].getLabel().setFontSize(BioFontSize);
                            //lastEventBioNodes[i].getLabel().setf.setFont(BioFont);
                        }
                    }
                    Node subject = (Node)gdse.getSubject();
                    NodeRealizer nr = gdse.getGraph2D().getRealizer(subject);
                    lastEventBioNodes = new NodeRealizer[1];
                    lastEventBioNodes[0] = nr;

 //1
                    /***********************  Print Part Properties   ****************************************/
                    // Get clicked part ID
                   // if (!nr.getLabelText().toString().contains("BBa"))
                      //  clickedPart.partID = "ExternalInput : " + nr.getLabelText().toString();
                    //else
                        clickedPart.partID = nr.getLabelText().toString();

                    PrintPartProperties.revokePartProperties(clickedPart.partID);

                    /***********************  Print Part Properties   ****************************************/

                    if(lastEventBioEdges != null)
                    {
                        for(int i = 0; i < lastEventBioEdges.length; i++)
                        {
                            lastEventBioEdges[i].setLineType(orgBioEdgeLine);
                            lastEventBioEdges[i].setLineColor(orgBioEdgeColor);
                            lastEventBioEdges[i].setLayer((byte)EdgeLayer);
                        }
                    }
                    lastEventBioEdges = null;
                     /***************************/
                    NodeRealizer []  lastEventDeviceNodes = parts.getLastEventDeviceNodes();

                    if(lastEventDeviceNodes != null)
                    {
                        for(int i = 0; i < lastEventDeviceNodes.length; i++)
                        {
                            lastEventDeviceNodes[i].setFillColor(parts.orgDevNodeColor);
                            lastEventDeviceNodes[i].setWidth(parts.orgDevNodeDim.getWidth());
                            lastEventDeviceNodes[i].setHeight(parts.orgDevNodeDim.getHeight());
                            lastEventDeviceNodes[i].getLabel().setFontSize(parts.DeviceFontSize);
                            lastEventDeviceNodes[i].getLabel().setFontStyle(parts.DeviceFont);
                            lastEventDeviceNodes[i].setLayer((byte)NodeLayer);
                        }
                    }

                    lastEventDeviceNodes = null;

                    parts.setLastEventDeviceNodes(lastEventBioNodes);

                    EdgeRealizer []  lastEventDeviceEdges = parts.getLastEventDeviceEdges();
                    if(lastEventDeviceEdges != null)
                    {
                        for(int i = 0; i < lastEventDeviceEdges.length; i++)
                        {
                            lastEventDeviceEdges[i].setLineType(parts.orgDevEdgeline);
                            lastEventDeviceEdges[i].setLineColor(parts.orgDevEdgeColor);
                        }
                    }
                    lastEventDeviceEdges = null;

                    parts.setLastEventDeviceEdges(lastEventBioEdges);

                /************************/

                    parts.highLightGraph(nr.getLabelText());

                    parts.repaint();
                    this.repaint();
                /************************/
                nr.setSize(80, 80);
                nr.setFillColor(Color.green);
                nr.getLabel().setFontStyle(Font.BOLD);
                nr.getLabel().setFontSize(28);
                /*
                EdgeCursor ec = subject.outEdges();
                NodeCursor nc = subject.neighbors();


                int zz = ec.size();
                for(int i = 0; i < zz; i++)
                {
                    EdgeRealizer er = gdse.getGraph2D().getRealizer(ec.edge());
                    ec.next();
                    er.setLineColor(Color.green);
                    er.repaint();
                    er.setLineType(LineType.LINE_3);
                    //er.getLabel().setRatio(16);
                }

                for(int i = 0; i < zz; i++)
                {
                    NodeRealizer er = gdse.getGraph2D().getRealizer(nc.node());
                    nc.next();
                    er.setFillColor(Color.green);
                    //er.moveBy(5, 5);
                    //System.out.println(er.getHeight());
                    //er.setSize(er.getHeight()+10, er.getWidth()+10);
                    er.setSize(50, 50);

                    er.getLabel().setFontSize(23);
                    er.getLabel().setFontStyle(3);
                }
                 *
                 */

               gdse.getGraph2D().unselectAll();

                  //bricks.updateGraph(bioB);
                  //BioBricks sk = new BioBricks(bioB);
                  //content.add(sk, BorderLayout.WEST);
                  //Thread aa = new Thread();

                   /* EventQueue.invokeLater(new Runnable() {
           public void run() {
             (new BioBricks(partse)).start();
           }aa
         });*/

                   //System.out.println(nr.getLabel()+"  **");
                   //System.out.println(3);

                }else {
                    event--;
                }
            }

        }

    public NodeRealizer[] getLastEventBioNodes()
    {
        return lastEventBioNodes;
    }

    public EdgeRealizer[] getLastEventBioEdges() {
        return lastEventBioEdges;
        }

    public void setLastEventBioNodes(NodeRealizer[] nr)
    {
        lastEventBioNodes = nr;
    }

    public void setLastEventBioEdges(EdgeRealizer[] er) {
        lastEventBioEdges = er;
        }
    public void setParts(Parts pt)
    {
        parts = pt;
    }
    
    // from parts
    // Highlight the pathway
    /*
   public void paintNodesEvent(String [] path) {

                 Vector<NodeRealizer>nodeR = new Vector<NodeRealizer>();
                 Graph2D grp = partsView.getGraph2D();
                 Node [] nds = grp.getNodeArray();

                 if(lastEventDeviceNodes != null)
                 {
                        for(int i = 0; i < lastEventDeviceNodes.length; i++)
                        {
                            lastEventDeviceNodes[i].setFillColor(orgDevNodeColor);
                            lastEventDeviceNodes[i].setWidth(orgDevNodeDim.getWidth());
                            lastEventDeviceNodes[i].setHeight(orgDevNodeDim.getHeight());
                            lastEventDeviceNodes[i].getLabel().setFontSize(DeviceFontSize);
                            lastEventDeviceNodes[i].getLabel().setFontStyle(DeviceFont);
                        }
                        lastEventDeviceNodes = null;
                 }
                for(int mm = 0; mm <path.length; mm++)
                {
                        String partID = path[mm];
                        for(int xy = 0; xy < nds.length; xy++)
                        {
                            NodeRealizer nr = grp.getRealizer(nds[xy]);
                            if(nr.getLabelText().equals(partID))
                            {
                                nr.setFillColor(Color.yellow);
                                nr.setSize(80, 80);
                                nr.getLabel().setFontSize(28);
                                nr.getLabel().setFontStyle(3);
                                nr.getLabel().setFontStyle(Font.BOLD);
                                nodeR.add(nr);
                                break;
                            }
                        }

                }

                 if(nodeR.size()> 0) {
                         lastEventDeviceNodes = new NodeRealizer[nodeR.size()];
                         for(int i = 0; i < nodeR.size(); i++)
                         {
                             lastEventDeviceNodes[i] = nodeR.get(i);
                         }
                    }
                 this.repaint();
        }*/
    
}

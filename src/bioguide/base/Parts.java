/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.base;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
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
 * @author Hassan
 */
public class Parts extends JPanel implements Graph2DSelectionListener
{
        Graph2DView partsView;
        int event = 0;

            static BioBricks bricks = null;
        Timer tmer;

    Color orgDevNodeColor;
    Color orgDevEdgeColor;
    java.awt.Dimension orgDevNodeDim;
    LineType orgDevEdgeline;
    NodeRealizer[] lastEventDeviceNodes;
    EdgeRealizer[] lastEventDeviceEdges;

    int DeviceFontSize;
    int DeviceFont;
    int EdgeLayer;
    int NodeLayer;
    //Clicked Part Object

    // from parts
    static Edge [] edges;
    static Node [] nodes;
    static Graph2D graph;

    //
    static private NodeRealizer[] lastEventBioNodes;
    static private EdgeRealizer[] lastEventBioEdges;
    static ClickedPart clickedPart = new ClickedPart();

    static private Vector<Device> devices;

        public Parts(String bid)
        {
          setLayout(new BorderLayout());
          partsView = new Graph2DView();
          MyEditMode mode = new MyEditMode();
          mode.allowNodeCreation(false);
          mode.allowEdgeCreation(false);
          mode.allowNodeEditing(false);
          mode.allowMoving(false);
          mode.allowMoveSelection(false);
          mode.allowMovePorts(false);
          mode.allowMoveLabels(false);
          //mode.allowMouseInput(false);
          partsView.addViewMode(mode);
          add(partsView);

          buildGraph(bid);
          configureDefaultRealizers();


        }

        protected void configureDefaultRealizers()
        {
          Graph2D graph = partsView.getGraph2D();

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

         vm.setActiveView(view);
         vm.init();
         MouseListener [] ls  = view.getMouseListeners();
         view.addViewMode(vm);
         ls[0].mouseClicked(null);*/

       }

       void buildGraph(String bid)
       {
           devices = this.readBrickIDs();
          // getNodeNames();
         Graph2D graph = partsView.getGraph2D();

          EdgeRealizer er = graph.getDefaultEdgeRealizer();
          //a standard (target) arrow
          er.setArrow(Arrow.DELTA);
          //set diamond source arrow
          er.setSourceArrow(Arrow.DELTA);
          er.setLineColor(Color.PINK);

              IOHandler ioh = new GMLIOHandler();
              //IOHandler ioh = new XGMLIOHandler();

          //System.out.println();
            try {
                ioh.read(graph, "data/middle.gml");
                edges = graph.getEdgeArray();
                EdgeRealizer rm = null;
                for(int i = 0; i < edges.length; i++)
                {
                rm = graph.getRealizer(edges[i]);
                rm.setArrow(Arrow.DELTA);
                rm.setLabelText("");
                rm.setLineColor(Color.lightGray);
                rm.setLineType(LineType.DASHED_1);
                byte av = 0;
                rm.setLayer(av);
                }
                nodes = graph.getNodeArray();
                NodeRealizer nm = null;
                for(int i = 0; i < nodes.length; i++)
                {
                nm = graph.getRealizer(nodes[i]);
                nm.getLabel().setFontSize(20);
                }

                 DefaultGraph2DRenderer dgr = new DefaultGraph2DRenderer();
                // Drawing edges first implies that nodes are drawn last, i.e., on top of the edges.
                dgr.setDrawEdgesFirst(true);

               // Register the freshly configured DefaultGraph2DRenderer with the given view.
                partsView.setGraph2DRenderer(dgr);

                //partsView.fitWorldRect();
               partsView.fitContent();
                partsView.setFitContentOnResize(true);
                partsView.setToolTipText("DEVICES GRAPH");

                orgDevNodeColor = rm.getSourceRealizer().getFillColor();
                orgDevEdgeColor = rm.getLineColor();
                orgDevNodeDim = new Dimension();
                orgDevEdgeline = rm.getLineType();
                orgDevNodeDim.setSize(rm.getSourceRealizer().getWidth(), rm.getSourceRealizer().getHeight());
                lastEventDeviceNodes = null;
                lastEventDeviceEdges = null;
                DeviceFontSize = rm.getSourceRealizer().getLabel().getFontSize();
                DeviceFont = rm.getSourceRealizer().getLabel().getFontStyle();

                if(bid != null)
                    highLightGraph(bid);

                graph.addGraph2DSelectionListener(this);
            } catch (IOException ex) {
                Logger.getLogger(Parts.class.getName()).log(Level.SEVERE, null, ex);
            }

       }

     /*  public void start()
       {
         JFrame frame = new JFrame(getClass().getName());

         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         addContentTo(frame.getRootPane());
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
         frame.setBounds(1, 1, 1200, 700);
         //System.out.println(partsView.getGraph2D().selectedNodes().size());//while(true){System.out.println("hassa");}
       }*/
    public final void addContentTo( final JRootPane rootPane )
       {
         rootPane.setContentPane(this);
       }

     /*  public static void main(String[] args) {
         EventQueue.invokeLater(new Runnable() {
           public void run() {
             (new Parts()).start();
           }
         });
       }*/

    public void onGraph2DSelectionEvent(Graph2DSelectionEvent gdse) {
            if(gdse.isNodeSelection()){

                if(event == 0)
                {
                    event++;

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
                    }
                    Node subject = (Node)gdse.getSubject();
                    NodeRealizer nr = gdse.getGraph2D().getRealizer(subject);
                    lastEventDeviceNodes = new NodeRealizer[1];
                    lastEventDeviceNodes[0] = nr;
                    //1
                    /***********************  Print Part Properties   ****************************************/
                    // Get clicked part ID
                    //if (!nr.getLabelText().toString().contains("BBa"))
                      //  clickedPart.partID = "ExternalInput : " + nr.getLabelText().toString();
                    //else
                        clickedPart.partID = nr.getLabelText().toString();

                    // Send the ID to be printed to the the PrintPartProperties class.
                    PrintPartProperties.revokePartProperties(clickedPart.partID);

                    /***********************  Print Part Properties   ****************************************/

                    if(lastEventDeviceEdges != null)
                    {
                        for(int i = 0; i < lastEventDeviceEdges.length; i++)
                        {
                            lastEventDeviceEdges[i].setLineType(orgDevEdgeline);
                            lastEventDeviceEdges[i].setLineColor(orgDevEdgeColor);
                        }
                    }
                    lastEventDeviceEdges = null;
                     /***************************/
                    NodeRealizer lastEventBioNodes[] = bricks.getLastEventBioNodes();

                    if(lastEventBioNodes != null)
                    {
                        for(int i = 0; i < lastEventBioNodes.length; i++)
                        {
                            lastEventBioNodes[i].setFillColor(bricks.orgBioNodeColor);
                            lastEventBioNodes[i].setWidth(bricks.orgBioNodeDim.getWidth());
                            lastEventBioNodes[i].setHeight(bricks.orgBioNodeDim.getHeight());
                            lastEventBioNodes[i].getLabel().setFontSize(bricks.BioFontSize);
                            lastEventBioNodes[i].getLabel().setFontStyle(bricks.BioFont);
                        }
                    }

                    lastEventBioNodes = null;
                    bricks.setLastEventBioNodes(lastEventBioNodes);

                     EdgeRealizer lastEventBioEdges[] = bricks.getLastEventBioEdges();
                    if(lastEventBioEdges != null)
                    {
                        for(int i = 0; i < lastEventBioEdges.length; i++)
                        {
                            lastEventBioEdges[i].setLineType(bricks.orgBioEdgeLine);
                            lastEventBioEdges[i].setLineColor(bricks.orgBioEdgeColor);
                        }
                    }
                    lastEventBioEdges = null;
                    bricks.setLastEventBioEdges(lastEventBioEdges);

                    bricks.repaint();
                    this.repaint();

                /************************/
                 String []bios = null;
                for(int mm = 0; mm <devices.size(); mm++)
                {
                    if(devices.elementAt(mm).partID.equals(nr.getLabelText()))
                    {
                        bios = devices.elementAt(mm).brickIDs.split("\\+");
                        for(int t = 0; t < bios.length; t++)
                            bios[t] = bios[t].trim();
                        break;
                    }
                }
                /************************/
                nr.setSize(80, 80);
                nr.setFillColor(Color.green);
                nr.getLabel().setFontSize(28);
                nr.getLabel().setFontStyle(Font.BOLD);
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
              final String []bioB = bios;

              if(bioB != null)
              {
                  bricks.updateGraph(bioB);
                }
              // Load the frame on click
                    java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new PartProperties().setVisible(true);
                    }
                    });


                }else {
                    event--;
                }


            }

        }

    public Vector<Device> readBrickIDs()
        {
            Vector<Device> devices = new Vector<Device>();
        try {
            
            BufferedReader buf = new BufferedReader(new FileReader("data/Plate1.txt"));
            String line;
            Device device = null;
            while ((line = buf.readLine()) != null) {
                line = line.trim();
                if (line.contains("PartID")) {
                    device = new Device();
                    device.partID = (line.substring(line.indexOf(':') + 1)).trim();
                    int readsome = 0;
                    while (readsome < 4) {
                        readsome++;
                        line = buf.readLine();
                        line = line.trim();
                        if (line.contains("BrickIDs")) {
                            device.brickIDs = (line.substring(line.indexOf(':') + 1)).trim();
                            devices.addElement(device);
                            break;
                        }
                    }
                }
            }
            buf.close();
            buf = new BufferedReader(new FileReader("data/Plate2.txt"));
            while ((line = buf.readLine()) != null) {
                line = line.trim();
                if (line.contains("PartID")) {
                    device = new Device();
                    device.partID = (line.substring(line.indexOf(':') + 1)).trim();
                    int readsome = 0;
                    while (readsome < 4) {
                        readsome++;
                        line = buf.readLine();
                        line = line.trim();
                        if (line.contains("BrickIDs")) {
                            device.brickIDs = (line.substring(line.indexOf(':') + 1)).trim();
                            devices.addElement(device);
                            break;
                        }
                    }
                }
            }
            buf.close();
            buf = new BufferedReader(new FileReader("data/Plate3.txt"));
            while ((line = buf.readLine()) != null) {
                line = line.trim();
                if (line.contains("PartID")) {
                    device = new Device();
                    device.partID = (line.substring(line.indexOf(':') + 1)).trim();
                    int readsome = 0;
                    while (readsome < 4) {
                        readsome++;
                        line = buf.readLine();
                        line = line.trim();
                        if (line.contains("BrickIDs")) {
                            device.brickIDs = (line.substring(line.indexOf(':') + 1)).trim();
                            devices.addElement(device);
                            break;
                        }
                    }
                }
            }
            buf.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Parts.class.getName()).log(Level.SEVERE, null, ex);
        }
            return devices;
        }

    public void highLightGraph(String brickID) {

                 Vector<NodeRealizer>nodeR = new Vector<NodeRealizer>();
                 Graph2D grp = partsView.getGraph2D();
                 Node [] nds = grp.getNodeArray();
                for(int mm = 0; mm <devices.size(); mm++)
                {
                    if(devices.elementAt(mm).brickIDs.contains(brickID))
                    {
                        String partID = devices.elementAt(mm).partID;
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
                }
                 if(nodeR.size()> 0) {
                         lastEventDeviceNodes = new NodeRealizer[nodeR.size()];
                         for(int i = 0; i < nodeR.size(); i++)
                         {
                             lastEventDeviceNodes[i] = nodeR.get(i);
                         }
                    }
        }

    public void paintNodesEvent(String[] partIDs) {

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
                for(int mm = 0; mm <partIDs.length; mm++)
                {
                        String partID = partIDs[mm];
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
                                nr.setVisible(true);
                                nr.repaint();
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
        }

    // from parts
    public void paintNodesPathEvent(String[] partIDs) {

                 Vector<NodeRealizer>nodeR = new Vector<NodeRealizer>();
                 Vector<EdgeRealizer> edgeR = new Vector<EdgeRealizer>();
                 Graph2D grp = partsView.getGraph2D();
                 Node [] nds = grp.getNodeArray();
                Edge []edgs = grp.getEdgeArray();
                
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
                 // Node realizer
                for(int mm = 0; mm <partIDs.length; mm++)
                {
                    Node src = null;
                    Node tgt = null;
                    String partID = partIDs[mm];
                    for(int xy = 0; xy < nds.length; xy++)
                    {
                        NodeRealizer nr = grp.getRealizer(nds[xy]);
                        if(nr.getLabelText().equals(partID))
                        {
                            src = nds[xy];
                            nr.setFillColor(Color.yellow);
                            nr.setSize(80, 80);
                            nr.getLabel().setFontSize(28);
                            nr.getLabel().setFontStyle(3);
                            nr.getLabel().setFontStyle(Font.BOLD);
                            nr.setVisible(true);
                            nr.repaint();
                            nodeR.add(nr);
                            break;
                        }
                    }
                    if(mm < partIDs.length-1)
                    {
                        String target = partIDs[mm+1];
                        for(int xy = 0; xy < nds.length; xy++)
                        {
                            NodeRealizer nr = grp.getRealizer(nds[xy]);
                            if(nr.getLabelText().equals(target))
                            {
                                tgt = nds[xy];
                                break;
                            }
                        }
                        //Edge Realizer
                        for(int j = 0; j < edgs.length; j++)
                        {                           
                            if((edgs[j].source() == src) && (edgs[j].target() == tgt))
                            {
                                EdgeRealizer es =  grp.getRealizer(edgs[j]);
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
                 
                 if(nodeR.size()> 0) {
                         lastEventDeviceNodes = new NodeRealizer[nodeR.size()];
                         //
                         lastEventBioEdges = new EdgeRealizer[edgeR.size()];
                         for(int i = 0; i < nodeR.size(); i++)
                         {
                             lastEventDeviceNodes[i] = nodeR.get(i);
                         }
                         /*
                         for(int j = 0; j < edgeR.size(); j++)
                         {
                             lastEventBioEdges[j] = edgeR.get(j);
                         }*/
                    }
                 this.repaint();
        }
    public NodeRealizer[] getLastEventDeviceNodes()
    {
        return lastEventDeviceNodes;
    }

    public EdgeRealizer[] getLastEventDeviceEdges() {
        return lastEventDeviceEdges;
        }

    public void setLastEventDeviceNodes(NodeRealizer[] nr)
    {
        lastEventDeviceNodes = nr;
    }

    public void setLastEventDeviceEdges(EdgeRealizer[] er) {
        lastEventDeviceEdges = er;
        }

    public void setBrick(BioBricks bio)
    {
        bricks = bio;
    }
}

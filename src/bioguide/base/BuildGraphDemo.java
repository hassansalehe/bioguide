/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.base;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
  import javax.swing.*;
import y.base.Edge;
import y.base.EdgeCursor;

  import y.base.Node;
import y.base.NodeCursor;
import y.io.GMLIOHandler;
import y.io.IOHandler;
import y.io.TGFIOHandler;
import y.io.XGMLIOHandler;
import y.io.YGFIOHandler;
import y.layout.BufferedLayouter;
import y.layout.hierarchic.HierarchicGroupLayouter;
import y.layout.organic.OrganicLayouter;
import y.view.Graph2DSelectionEvent;
  import y.view.Graph2DView;
  import y.view.EditMode;

  import y.view.Arrow;
  import y.view.EdgeRealizer;
  import y.view.NodeRealizer;
  import y.view.ImageNodeRealizer;
  import y.view.NodeLabel;
  import y.view.EdgeLabel;
  import y.view.Graph2D;
import y.view.Graph2DSelectionListener;
import y.view.ViewMode;
import y.view.hierarchy.GroupLayoutConfigurator;
import yext.graphml.graph2D.GraphMLIOHandler;

  /**
   * <p>
   *  Demonstrates simple usage of {@link Graph2DView}, {@link Graph2D}
   *  and {@link EditMode}.
   * </p>
   * <p>
   *  This demo creates an initial graph by adding nodes and edges
   *  to the {@link Graph2D} displayed by the main {@link Graph2DView}
   *  view using API calls. It further shows how some graphical node
   *  and edge properties can be set (see {@link #buildGraph()}).
  * </p>
   * <p>
   *  Additionally it is shown how the appearance of the default nodes
  *  and edges can be set (see {@link #configureDefaultRealizers()}).
   *  This applies to new nodes and edges added to the initial graph.
   *  Editing the initial graph is possible due to the {@link EditMode}
   *  added to the view.
   * </p>
   */
  public class BuildGraphDemo extends JPanel implements Graph2DSelectionListener
  {
    Graph2DView view;

    public BuildGraphDemo()
    {
      setLayout(new BorderLayout());
      view = new Graph2DView();
      EditMode mode = new EditMode();
      view.addViewMode(mode);
      add(view);

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
      ivr.setImageURL(getClass().getResource("resources/yicon.png"));
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

   void buildGraph()
   {
      // getNodeNames();
	   Graph2D graph = view.getGraph2D();
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
        	ioh.read(graph, "out.gml");
            Edge [] edges = graph.getEdgeArray();
            for(int i = 0; i < edges.length; i++)
            {
            EdgeRealizer rm = graph.getRealizer(edges[i]);
            rm.setArrow(Arrow.DELTA);
            rm.setLabelText("");
            //System.out.println(rm.isVisible());

            }
            //view.fitWorldRect();
           view.fitContent();
            view.setFitContentOnResize(true);
            view.setToolTipText("HASSAN Mang");
            
            //view.setViewPoint(10, 10);
            //view.setZoom(5);
            //view.zoomToArea(0, 0, 4000, 4000);
            //System.out.println(view.getGraph2D().getURL());
            //graph.selectAllNodesAndBends(true);

            graph.addGraph2DSelectionListener(this);
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
            Logger.getLogger(BuildGraphDemo.class.getName()).log(Level.SEVERE, null, ex);
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
     //System.out.println(view.getGraph2D().selectedNodes().size());//while(true){System.out.println("hassa");}
   }

   public final void addContentTo( final JRootPane rootPane )
   {
     rootPane.setContentPane(this);
   }

   public static void main(String[] args) {
     EventQueue.invokeLater(new Runnable() {
       public void run() {
         (new BuildGraphDemo()).start();
       }
     });
   }

    public void onGraph2DSelectionEvent(Graph2DSelectionEvent gdse) {
        if(gdse.isNodeSelection()){
            Node subject = (Node)gdse.getSubject();
            NodeRealizer nr = gdse.getGraph2D().getRealizer(subject);
            
            System.out.println(nr.getLabel());
            nr.setFillColor(Color.yellow);
            EdgeCursor ec = subject.outEdges();
            NodeCursor nc = subject.neighbors();
            

            int zz = ec.size();
            for(int i = 0; i < zz; i++)
            {
                EdgeRealizer er = gdse.getGraph2D().getRealizer(ec.edge());
                ec.next();
                er.setLineColor(Color.yellow);
            }

            for(int i = 0; i < zz; i++)
            {
                NodeRealizer er = gdse.getGraph2D().getRealizer(nc.node());
                nc.next();
                er.setFillColor(Color.yellow);
            }
           gdse.getGraph2D().unselectAll();
            
        }
    }
 }
  /* private void getNodeNames()
      {
        try {
            FileInputStream fstream = null;
            fstream = new FileInputStream("graphNodes.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int indx = 0;
            nodelist = new ourNode[371];

            while ((strLine = br.readLine()) != null) {
                String []sub = strLine.split(" --> ");
                int i = 0;
                for(; i<indx; i++)
                {
                    if(nodelist[i].Name.equals(sub[0]))
                        break;
                }
                if(indx == i)
                {
                    nodelist[i] = new ourNode();
                    nodelist[i].Name = sub[0];
                    nodelist[i].Edges = new int[100];
                    nodelist[i].index = 0;
                    indx++;
                }

                int j = 0;
                for(; j<indx; j++)
                {
                    if(nodelist[j].Name.equals(sub[1]))
                        break;
                }
                if(indx == j)
                {
                    nodelist[j] = new ourNode();
                    nodelist[j].Name = sub[1];
                    nodelist[j].Edges = new int[100];
                    nodelist[j].index = 0;
                    indx++;
                }
                int x = nodelist[i].index;
                nodelist[i].Edges[x] = j;
                nodelist[i].index = x+1;
            }
            ourNode [] nd = new ourNode[indx];
            System.arraycopy(nodelist, 0, nd, 0, indx);

            nodelist = new ourNode[indx];
            System.arraycopy(nd, 0, nodelist, 0, indx);

            in.close();
            br.close();

        } catch (IOException ex) {
            Logger.getLogger(BuildGraphDemo.class.getName()).log(Level.SEVERE, null, ex);
        }


   }*/
/*
class ourNode
{
    String Name;
    int [] Edges;
    int index;
}
*/

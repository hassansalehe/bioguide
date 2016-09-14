/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import bioguide.gui.*;
/**
 *
 * @author Akif
 */
public class PathFinder {
    //BioBricks deviceGraph;
    Parts deviceGraph;
    String [] inputs;
    String [] outputs;
    String [] Nodes;
    String [] Edges;
    ArrayList<String> path = new ArrayList<String>();
    ArrayList<String> pathHolder = new ArrayList<String>();
    ArrayList<String[]> pathes = new ArrayList<String[]>();;
    int [][] graph;
    int p[];
    ShortestPathAlgorithm sp = new ShortestPathAlgorithm();
    
    // To initialize the graph
    public void InitializeGraphMatrix()
    {
        Nodes = new String [Parts.nodes.length];
        Edges = new String [Parts.edges.length];
        graph = new int [Nodes.length][Nodes.length];
        String [] holder = new String[2];
        // yFiles node data structures to array
        InitializeNodes();
        // Initialize the graph
        for (int i = 0; i < graph.length; i++)
            for (int j = 0; j < graph.length; j++)
                graph[i][j] = Integer.MAX_VALUE;

        // Find the possible paths in graph
        for (int i = 0; i < graph.length; i++)
            for (int j = 0; j < graph.length; j++)
            {
                 String source = Nodes[i];
                 String target = Nodes[j];
                 for (int k = 0; k < Edges.length; k++)
                 {
                    holder = Edges [k].split(" -> ");
                    if (source.equals(holder[0]) && target.equals(holder[1]))
                    {
                        graph[i][j] = 1;
                    }
                 }
                 if (graph[i][i] != 1)
                     graph[i][i] = 0;
            }

    }
    // To find possible paths in a graph and to send each input and output to graph
    private void SPF()
    {
        //pathes = new ArrayList<String[]>();
        // Vectors from DB to array - matched I/O information
        inputs = new String [mainForm.matchedPartInputIDs.size()];
        outputs = new String [mainForm.matchedPartOutputIDs.size()];
        vectorsToArray();
        printNodesEdges();
        
        // Traverse the graph to find Shortest Path

        System.out.println("Called Dijkstar");
        // find the previous nodes array
       // p = sp.dijkstra(graph);
        //printGraph(graph);
        
        // Send each I/O to be traversed in graph and send pathes to path array list
        int inputIndexes[] = new int[inputs.length];
        int outputIndexes[] = new int[outputs.length];
        for (int i = 0; i < inputs.length; i++)
        {
            inputIndexes[i] =NodeIndex(inputs[i]);
        }
        for (int i = 0; i < outputs.length; i++)
        {
            outputIndexes[i] =NodeIndex(outputs[i]);
        }
        for (int i = 0; i < inputs.length; i++)
        {
            //to refresh path
            p = sp.dijkstra(graph, inputIndexes[i]);

            /*System.out.println(" ::: previous nodes ::: ");
            for (int k = 0; k < p.length; k++)
                System.out.println(p[k]);
            */
            if(!path.isEmpty())
                break;
            for (int j = 0; j < outputs.length; j++ )
                findPathes(inputIndexes[i],outputIndexes[j]);
        }

    }
    // To find the pathes between atomic I/O
    private void findPathes(int in, int out)
    {
        int initialNode = in;
        int finalNode = out;
        int previousNode =  p[out];
        pathHolder.clear();
        
        ArrayList<Integer> visited = new ArrayList<Integer>();


        if(initialNode != finalNode && previousNode == -1)
        {
            path.clear();
            return;
        }
        else {
            visited.add(out);
            path.add(Nodes[out]);
            pathHolder.add(Nodes[out]);
                while(previousNode != initialNode && previousNode != -1)
                {
                    if(visited.indexOf(previousNode)!= -1)
                    {
                        path.clear();
                        break;
                    }

                     visited.add(previousNode);
                    path.add(Nodes[previousNode]);
                    pathHolder.add(Nodes[previousNode]);
                    previousNode = p[previousNode];

                    for(int i = 0 ; i < path.size(); i++)
                    {
                        System.out.print(path.get(i)+"  ");
                    }
                    //System.out.println("END path");
                }
            }
            if(previousNode != -1){
           
                path.add(Nodes[initialNode]);
                pathHolder.add(Nodes[initialNode]);
                System.out.println("+++++++ASSIGNMENT OF INITIAL NODE");
                     for(int i = 0 ; i < pathHolder.size(); i++)
         {
             System.out.print(/*"path size : " + pathHolder.size() +*/pathHolder.get(i)+"  ");

         }
                System.out.println("Initial = "+Nodes[initialNode] +" Final = " +Nodes[finalNode]);
                System.out.println("END");
            }
            else {
                path.clear();
                System.out.println("No path found for "+Nodes[initialNode]+"-- . . . -->  "+Nodes[finalNode]);
            }   
         //////////////////
        String [] aa = new String [pathHolder.size()];
        System.out.println(" pathHolder size : " + pathHolder.size() );
         for(int i = 0 ; i < pathHolder.size(); i++)
         {
             System.out.print(/*"path size : " + pathHolder.size() +*/pathHolder.get(i)+"  ");
             aa[i] = (pathHolder.get(i).toString());
             //System.out.print(" array " + i +  aa[i]);
         }
        pathes.add(aa);
        pathHolder.clear();
        System.out.print("END path :\narray is : " );
            for (int k = 0; k < aa.length; k++)
            {
                System.out.print(aa[k] + " ");
            }
        System.out.println();
    }

    // To find node index
    public int NodeIndex (String node)
    {
        int index = 0;
        for (int i = 0; i < Nodes.length; i++)
        {
            System.out.println("Loking for node... " + node + " at index :" + i + " Node " + Nodes [i]);
            if (node.equals(Nodes[i].toString()))
            {
                System.out.println("Node found : " + node + " at index :" + i + " Node " + Nodes [i]);
                index = i;
                //return index;
                break;
            }
        }
        return index;
    }
    // To send the path to be painted
    public String [] ShortestPath ()
    {
        path.clear();
        pathes.clear();
        
        SPF();

        // Finally send the sequence to be painted
        int min = pathes.get(0).length;
        int minIndex = 0;
        for (int i  = 0; i < pathes.size(); i ++)
        {
            if (pathes.get(i).length < min)
                minIndex = i;
        }
        
        String [] holder = new String [pathes.get(minIndex).length];
        holder = pathes.get(minIndex);
        System.out.print(" minIndex: " + minIndex + " ::: shortest path : ");
        for(int i= 0 ; i <holder.length; i++)
            System.out.print(holder[i] + " ");

        return pathes.get(minIndex);
    }
    // Copy vectors to array
    public void vectorsToArray()
    {
        mainForm.matchedPartInputIDs.copyInto(inputs);
        mainForm.matchedPartOutputIDs.copyInto(outputs);
    }
    // Nodes to array from yfiles data structure
    public void InitializeNodes()
    {
        // Nodes
        for (int i = 0; i < Parts.nodes.length; i++)
         {
             Nodes[i] = Parts.nodes[i].toString();
         }
        // Edges
         for (int i = 0; i < Parts.edges.length; i++)
         {
             Edges[i] = Parts.edges[i].toString();
         }
    }
    // To print nodes and edges
    public void printNodesEdges()
    {
        for (int i = 0; i < inputs.length; i++)
            System.out.println("in " + i + " -> "+ inputs[i]);
        for (int i = 0; i < outputs.length; i++)
            System.out.println("out " + i + " -> " + outputs[i]);
        
        // Nodes
            System.out.println( "node length : " + Parts.nodes.length);
         for (int i = 0; i < Parts.nodes.length; i++)
         {
             //System.out.println( "node : " + i + " -> " + BioBricks.nodes[i]);
             //Nodes[i] = BioBricks.nodes[i].toString();
             System.out.println( "node : " + i + " -> " + Nodes[i]);
         }
        // Edges
            System.out.println( "edge length : " + Parts.edges.length);
         for (int i = 0; i < Parts.edges.length; i++)
         {
             //System.out.println( "edge : " + i + " -> " + BioBricks.edges[i]);
             //Edges[i] = BioBricks.edges[i].toString();
             System.out.println( "edge : " + i + " -> " + Edges[i]);
         }
    }
    // To print graph
    public void printGraph(int [][] graph)
    {
        for (int i = 0; i < graph.length; i++)
        {
            for (int j = 0; j < graph.length; j++)
            {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }
}
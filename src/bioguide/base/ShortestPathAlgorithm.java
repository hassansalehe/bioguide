package bioguide.base;

public class ShortestPathAlgorithm{

     public int [] dijkstra(int graph[][], int nodeIndex ){
        /* for(int x = 0; x < graph.length; x++)
             System.out.print("  "+ x);
         for(int x = 0; x < graph.length; x++)
         {
             System.out.println();
             System.out.print(x+ " ");
             for(int y= 0; y < graph[x].length; y++)
                 if(graph[x][y] == Integer.MAX_VALUE)
                     System.out.print(graph[x][y] + "  ");
                 else
                     System.out.print("   "+graph[x][y] + "    ");
         }*/

         /*int ShortestPathAlgorithm[][] ={
             {0,1,2,5,0,0},
             {7,0,2,3,1,0},
             {3,2,0,3,0,0},
             {0,3,6,0,1,5},
             {0,1,0,1,0,2},
             {0,0,0,8,4,0}

          };
*/
         /*
             int graph[][] ={
             {0,1,11,5},
             {7,0,12,1},
             {3,2,0,3},
             {5,3,6,0}
          }*/

             int d[] = new int[ graph.length ];  //cost from source
             int dC[] = new int[ graph.length ];  //queue check if visited
             int p[] = new int[ graph.length ]; //previous node

             for( int i = 0; i < graph.length; i++ )
             {
                 d[ i ] = 100000; dC[ i ] = 1; p[ i ] = -1;
             }

             d[ nodeIndex ] = 0; dC[ nodeIndex ] = 0;

             int i = 0, min = 100000, pos = nodeIndex; //You can change the min to 1000 to make it the largest number

             while( i < graph.length ){

                     //extract minimum
                     for( int j = 0; j < dC.length; j++ ){
                             if( min > d[ j ] && dC[ j ] != -1 ){
                                     min = d[ j ]; pos = j;
                             }
                     }
                     
                     // mark visited node
                     dC[ pos ] = -1;

                     //relax
                     for( int j = 0; j < graph.length; j++ ){
                             if( d[ j ] > graph[ pos ][ j ] + d[ pos ] ){
                                     d[ j ] = graph[ pos ][ j ] + d[ pos ];
                                     p[ j ] = pos;
                             }
                     }
                     i++; min = 100000;
             }
             for( int j = 0; j < p.length; j++ ){
                     System.out.print( p[ j ] + "     " );
             }
             System.out.print( "\n" );

             for( int j = 0; j < d.length; j++ ){
                     System.out.print( d[ j ] + " " );
             }
             System.out.print( "\n" );

             return p;
     }

}

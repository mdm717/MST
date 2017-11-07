package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		PartialTreeList L = new PartialTreeList();
		for(int i=0; i<graph.vertices.length; i++)
		{
			Vertex vertex = graph.vertices[i];
			PartialTree T = new PartialTree(vertex);
			for(Vertex.Neighbor nbr=vertex.neighbors; nbr!=null; nbr=nbr.next)
			{
				PartialTree.Arc arc = new PartialTree.Arc(vertex, nbr.vertex, nbr.weight);
				T.getArcs().insert(arc);
			}
			L.append(T);
		}	
		
		return L;
	}
	
	

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		ArrayList<PartialTree.Arc> arcList = new ArrayList<PartialTree.Arc>();
		do{
			PartialTree pt = ptlist.remove();
			PartialTree.Arc leastArc = pt.getArcs().deleteMin();
			while(leastArc!=null)
			{
				Vertex vert1 = leastArc.v1;
				Vertex vert2 = leastArc.v2;
				PartialTree vertexCheck = ptlist.removeTreeContaining(vert1);
				if(vertexCheck==null)
				{
					vertexCheck = ptlist.removeTreeContaining(vert2);
				}
				if(vertexCheck!=null)
				{
					pt.merge(vertexCheck);
					arcList.add(leastArc);
					ptlist.append(pt);
					break;		
				}
				leastArc = pt.getArcs().deleteMin();
			}
		} while(ptlist.size()>1);
		return arcList;
	}
}

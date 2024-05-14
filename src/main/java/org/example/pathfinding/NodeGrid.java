package org.example.pathfinding;

import java.util.ArrayList;
import java.util.Collections;

public class NodeGrid
{
    public PNode[][] Grid;
    public NodeGrid(int[][] rows, int rowWidth, int columnHeight)
    {
        Grid = new PNode[rowWidth][columnHeight];

        for(int x1 = rowWidth; x1 > 0; --x1)
        {
            for(int y1 = columnHeight; y1 > 0; --y1)
            {
                int idx = x1-1;
                int idy = y1-1;

                PNode curr = new PNode();
                curr.x = idx;
                curr.y = idy;
                Grid[idx][idy] = curr;
            }
        }

        for(int x1 = rowWidth; x1 > 0; --x1)
        {
            for(int y1 = columnHeight; y1 > 0; --y1)
            {
                int idx = x1-1;
                int idy = y1-1;
                PNode curr = Grid[idx][idy];

                if(rows[idx][idy] > 0)
                    continue;

                if(idx != 0 && rows[idx-1][idy] == 0) curr.addNeighbor(Grid[idx-1][idy]);
                if(x1 != rowWidth && rows[idx+1][idy] == 0) curr.addNeighbor(Grid[idx+1][idy]);
                if(idy != 0 && rows[idx][idy-1] == 0) curr.addNeighbor(Grid[idx][idy-1]);
                if(y1 != columnHeight && rows[idx][idy+1] == 0) curr.addNeighbor(Grid[idx][idy+1]);
            }
        }
    }

    public ArrayList<PNode> genPath(PNode start, PNode target) throws Exception
    {
        ArrayList<PNode> open = new ArrayList<PNode>();
        ArrayList<PNode> toReset = new ArrayList<PNode>();

        start.setH(0);
        start.setG(0);
        open.add(start);

        while(open.size() != 0)
        {
            PNode node = open.get(0);
            for (int i = 1; i < open.size(); i ++) {
                if (open.get(i).getF() < node.getF() || open.get(i).getF() == node.getF()) {
                    if (open.get(i).getH() < node.getH())
                        node = open.get(i);
                }
            }

            open.remove(node);
            if(!node.closed && !node.isHSet()) toReset.add(node);
            node.closed = true;

            if(node == target)
            {
                //TODO: retrace path and return it

                ArrayList<PNode> result = retracePath(start, target);
                performCleanup(toReset);
                return result;
            }

            ArrayList<PNode> neighbours = node.Neighbors;

            for(int i = 0; i < neighbours.size(); ++i)
            {
                PNode neighbour = neighbours.get(i);
                if(neighbour.closed)
                {
                    continue;
                }

                int newCostToNeighbour = node.getG() + node.manhattan(neighbour);
                if(!neighbour.isGSet() || newCostToNeighbour < neighbour.getG() || !open.contains(neighbour))
                {

                    if(!node.closed && !node.isHSet()) toReset.add(node);

                    neighbour.setG(newCostToNeighbour);
                    neighbour.setH(neighbour.manhattan(target));
                    neighbour.parent = node;

                    if(!open.contains(neighbour))
                    {
                        open.add(neighbour);
                    }
                }
            }
        }
        performCleanup(toReset);
        return null;
    }

    private ArrayList<PNode> retracePath(PNode start, PNode end)
    {
        ArrayList<PNode> path = new ArrayList<PNode>();

        PNode curr = end;
        while(curr != start)
        {
            path.add(curr);
            curr = curr.parent;
        }
        path.add(start);

        Collections.reverse(path);

        return path;
    }

    private void performCleanup(ArrayList<PNode> toClean)
    {
        for(int i = toClean.size(); i > 0; --i)
        {
            PNode curr = toClean.get(i-1);
            curr.parent = null;
            curr.hReset();
            curr.gReset();
            curr.closed = false;
        }
    }
}

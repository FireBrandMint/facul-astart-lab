package org.example.pathfinding;

import java.util.ArrayList;

public class PNode
{
    public int x,y;
    private int g = 2147483647;
    private int h = 2147483647;
    public ArrayList<PNode> Neighbors = new ArrayList<PNode>();

    public boolean closed = false;

    public PNode parent = null;

    public void addNeighbor(PNode neighbor)
    {
        Neighbors.add(neighbor);
    }

    public boolean isGSet()
    {
         return g != 2147483647;
    }

    public boolean isHSet()
    {
        return h != 2147483647;
    }

    public void gReset()
    {
        g = 2147483647;
    }

    public void hReset()
    {
        h = 2147483647;
    }

    public int getG() throws Exception
    {
        if (!isGSet()) throw new Exception("e1");
        return g;
    }

    public int getH() throws Exception
    {
        if (!isHSet()) throw new Exception("e1");
        return h;
    }

    public int getF() throws Exception
    {
        return getG() + getH();
    }

    public void setG(int value)
    {
        g = value;
    }
    public void setH(int value)
    {
        h = value;
    }

    public int manhattan(PNode target)
    {
        return Math.abs(this.x - target.x) + Math.abs(this.y - target.y);
    }
}

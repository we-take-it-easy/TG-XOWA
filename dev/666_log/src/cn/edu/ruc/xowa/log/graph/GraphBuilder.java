package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.page.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder
{
    private static GraphBuilder ourInstance = new GraphBuilder();

    public static GraphBuilder getInstance()
    {
        return ourInstance;
    }

    List<GraphNode> rootNodes = null;
    Map<String, GraphNode> pointerNodes = null;
    Map<String, GraphNode> allNodes = null;

    private GraphBuilder()
    {
        this.rootNodes = new ArrayList<>();
        this.pointerNodes = new HashMap<>();
        this.allNodes = new HashMap<>();
    }

    public void goTo (Page from, Page to)
    {

    }

    public void goBackward (Page from, Page to)
    {

    }

    public void goForward (Page from, Page to)
    {

    }
}

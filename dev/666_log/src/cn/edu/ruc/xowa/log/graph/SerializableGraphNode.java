package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.page.Url;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableGraphNode implements Serializable
{
    private String name;
    private List<Url> links;
    private List<String> parentNames;
    private List<String> childNames;

    public SerializableGraphNode (GraphNode graphNode)
    {
        this.name = graphNode.getName();
        this.links = new ArrayList<>(graphNode.getLinks());
        this.parentNames = new ArrayList<>(graphNode.getParents().keySet());
        this.childNames = new ArrayList<>(graphNode.getParents().keySet());
    }

    public String getName()
    {
        return name;
    }

    public List<Url> getLinks()
    {
        return links;
    }

    public List<String> getParentNames()
    {
        return parentNames;
    }

    public List<String> getChildNames()
    {
        return childNames;
    }
}

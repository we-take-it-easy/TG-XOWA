package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.page.Url;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraphNode
{
    private String name;
    private Set<Url> links;
    private Map<String, GraphNode> parents = new HashMap<>();
    private Map<String, GraphNode> children = new HashMap<>();

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLinks(Set<Url> links)
    {
        this.links = links;
    }

    public void addParents(GraphNode parent)
    {
        if(!this.getParents().containsKey(parent.getName()))
        {
            this.parents.put(parent.getName(), parent);
        }
    }

    public void addChildren(GraphNode child)
    {
        if (!this.getChildren().containsKey(child.getName()))
        {
            this.children.put(child.getName(),child);
        }
    }

    public String getName()
    {
        return name;
    }

    public Set<Url> getLinks()
    {
        return links;
    }

    public Map<String, GraphNode> getParents()
    {
        return parents;
    }

    public Map<String, GraphNode> getChildren()
    {
        return children;
    }
}

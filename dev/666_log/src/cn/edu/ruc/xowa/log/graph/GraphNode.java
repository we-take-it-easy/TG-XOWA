package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.page.Url;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphNode
{
    private String name;
    private Set<Url> links;
    private Set<String> linkNames;
    private Map<String, GraphNode> parents;
    private Map<String, GraphNode> children;
    protected GraphNode(){}

    protected GraphNode(String name, Set<Url> links){
        this.name = name;
        this.links = links;
        this.parents = new HashMap<>();
        this.children = new HashMap<>();
    }

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
        //System.out.println("PARENTS: "+parent.getName());
        if(!this.getParents().containsKey(parent.getName()))
        {
            this.parents.put(parent.getName(), parent);
        }
    }

    public void addChildren(GraphNode child)
    {
        //System.out.println("CHILD: "+child.getName());
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

    /*public Set<String> getLinkNames(){
        for(Url link: this.links){
            this.linkNames.add(link.getKeyWord());
        }
        return linkNames;
    }*/

    public Map<String, GraphNode> getParents()
    {
        return parents;
    }

    public Map<String, GraphNode> getChildren()
    {
        return children;
    }

    public void reset()
    {
        System.out.println("11111111111111");
        this.name = new String();
        this.links= new HashSet<>();
        this.children = new HashMap<>();
        this.parents = new HashMap<>();
    }
}

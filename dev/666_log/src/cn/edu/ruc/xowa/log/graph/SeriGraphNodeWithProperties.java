package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.page.Url;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeriGraphNodeWithProperties implements Serializable
{
    private String name;
    private List<Url> links;
    private List<String> parentNames;
    private List<String> childNames;
    private Double diversity;
    private Double normality;


    public SeriGraphNodeWithProperties(GraphNodeWithProperties graphNodeWithProperties){
        this.name = graphNodeWithProperties.getName();
        this.links = new ArrayList<>(graphNodeWithProperties.getLinks());
        this.parentNames = new ArrayList<>(graphNodeWithProperties.getParents().keySet());
        this.childNames = new ArrayList<>(graphNodeWithProperties.getChildren().keySet());
        this.diversity = graphNodeWithProperties.getDiversity();
        this.normality = graphNodeWithProperties.getNormality();
    }

    public String getName() { return name; }
    public List<Url> getLinks() { return links; }
    public List<String> getParentNames() { return parentNames; }
    public List<String> getChildNames() { return childNames; }
    public Double getDiversity() { return diversity; }
    public Double getNormality() { return normality; }
}

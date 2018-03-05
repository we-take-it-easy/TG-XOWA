package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.page.Url;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphNodeWithProperties
{
    private String name;
    private Set<Url> links;
    private Map<String, GraphNodeWithProperties> parents;
    private Map<String, GraphNodeWithProperties> children;

    private Double diversity;
    private Double normality;

    public GraphNodeWithProperties(String name, Double diversity, Double normality)
    {
        this.name = name;
        this.diversity = diversity;
        this.normality = normality;
        this.parents = new HashMap<>();
        this.children = new HashMap<>();
    }

    public GraphNodeWithProperties(GraphNode graphNode) throws IOException, SolrServerException
    {
        this.name = graphNode.getName();
        this.diversity = setDiversity(graphNode);
        this.normality = setNormality(graphNode);
        this.links = graphNode.getLinks();
        this.parents = graphNode.getParents();
        this.children = graphNode.getChildren();
    }

    //start solr
    private static final String urlString = "http://localhost:8983/solr/wikipediaCollection";
    private static SolrClient client = new HttpSolrClient(urlString);

    public void addParents(GraphNodeWithProperties parent)
    {
        //System.out.println("PARENTS: "+parent.getName());
        if(!this.getParents().containsKey(parent.getName()))
        {
            this.parents.put(parent.getName(), parent);
        }
    }

    public void addChildren(GraphNodeWithProperties child)
    {
        //System.out.println("CHILD: "+child.getName());
        if (!this.getChildren().containsKey(child.getName()))
        {
            this.children.put(child.getName(),child);
        }
    }

    public String getName() { return name; }

    public Double getDiversity() { return diversity; }

    public Double getNormality() { return normality; }

    public Set<Url> getLinks() { return links; }

    public Map<String, GraphNodeWithProperties> getParents() { return parents; }

    public Map<String, GraphNodeWithProperties> getChildren() { return children; }

    public Double setDiversity(GraphNode graphNode) throws IOException, SolrServerException
    {
        Double diver = 0.0;
        List<String> nodesNameList= new SerializableGraphNode(graphNode).getChildNames();
        if (!nodesNameList.isEmpty())
        {
            SolrQuery query = new SolrQuery();
            //double numFound;
            double sum = 0.0;
            int count = 0;
            if (nodesNameList.size() == 1)
            {
                query.setQuery("REVISION_TEXT: "+"\""+ nodesNameList.get(0) +"\"");
                QueryResponse resp = client.query(query);
                sum = resp.getResults().getNumFound();

                diver = sum;
            }
            else{
                for(int i=0;i<nodesNameList.size();i++)
                {
                    for(int j=i+1;j<nodesNameList.size();j++)
                    {
                        query.setQuery("REVISION_TEXT: "+"\""+ nodesNameList.get(i) +"\""+" "+"&&"+" "+"REVISION_TEXT: "+"\""+nodesNameList.get(j)+"\"");
                        QueryResponse resp = client.query(query);
                        //numFound = resp.getResults().getNumFound();
                        sum += resp.getResults().getNumFound();
                        count ++;

                        System.out.println("numFound: "+ resp.getResults().getNumFound());
                    }
                }
             diver = sum/count;
            }
        }
        return diver;
    }

    public Double setNormality(GraphNode graphNode) throws IOException, SolrServerException
    {
        Double normality;

        double sum = 0.0;
        List<String> nodesNameList= new SerializableGraphNode(graphNode).getChildNames();
        if (!nodesNameList.isEmpty())
        {
            SolrQuery query = new SolrQuery();
            for (int i=0; i<nodesNameList.size(); i++){
                query.setQuery("REVISION_TEXT: "+"\""+ nodesNameList.get(0) +"\"");
                QueryResponse resp = client.query(query);
                //numFound = resp.getResults().getNumFound();
                sum += resp.getResults().getNumFound();
            }
        }
        normality = sum/nodesNameList.size();
        return normality;
    }
}

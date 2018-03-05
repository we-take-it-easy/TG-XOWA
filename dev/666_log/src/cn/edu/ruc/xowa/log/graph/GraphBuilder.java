package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.database.DBAccess;
import cn.edu.ruc.xowa.log.page.Page;
import cn.edu.ruc.xowa.log.page.Url;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class GraphBuilder
{
    //graphBuild
    private static GraphBuilder ourInstance = new GraphBuilder();

    public static GraphBuilder getInstance()
    {
        return ourInstance;
    }
    //properties
    private String userName = null;
    private String sessionId = null;
    private GraphNode pointerNode;
    private GraphNode rootNode;
    private Map<String, GraphNode> allNodes;
    private Boolean searchOrNot;

    //DB
    private DBAccess dbAccess;

    //start solr
    private static final String urlString = "http://localhost:8983/solr/wikipediaCollection";
    private static SolrClient client = new HttpSolrClient(urlString);

    private GraphBuilder()
    {
        //this.pageNode = new GraphNode();
        this.pointerNode = new GraphNode();
        this.rootNode = new GraphNode();
        this.allNodes = new HashMap<>();

        this.searchOrNot = false;
        this.dbAccess = new DBAccess();
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
        System.out.println("user login: " + userName);
    }

    public void start (String sessionId)
    {
        this.sessionId = sessionId;
        System.out.println("start session: " + sessionId);
    }

    public void end () throws IOException, SolrServerException
    {
        System.out.println("root node: "+rootNode+" :C: "+rootNode.getName());
        System.out.println("allNodes: ");
        for (GraphNode itm : allNodes.values())
        {
            System.out.println(itm.getName()+" :C: "+itm.getChildren().keySet()+" :P: "+itm.getParents().keySet());
            for (GraphNode child: itm.getChildren().values())
            {
                System.out.println("Child: "+child.getName());
            }
            for (GraphNode parent: itm.getParents().values())
            {
                System.out.println("Parent: "+parent.getName());
            }
        }
        Map<String, GraphNodeWithProperties> allNodeWithProperties = getAllNodesWithProperties(allNodes);
        try
        {
            this.dbAccess.insertSessionAllnodes(this.sessionId, this.allNodes, allNodeWithProperties);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        //this.pageNode = null;
        this.pointerNode = null;
        this.rootNode = null;
        this.allNodes = null;
        System.out.println("end session...");
    }

    public void clear ()
    {
        //this.pageNode = null;
        this.pointerNode = null;
        this.rootNode = null;
        this.allNodes = null;
        System.out.println("give up session, clear...");
    }

    public void goTo (Page page)
    {
        //System.out.println("page: "+page.getUrl().getKeyWord()+" "+page.getUrl());
        try
        {
            GraphNode pageNode = new GraphNode(page.getUrl().getKeyWord(), page.getLinks());

            //if pointerNode 为空
            if(pointerNode.getName() == null)
            {
                System.out.println("case1");

                rootNode = new GraphNode(page.getUrl().getKeyWord(), page.getLinks());
                System.out.println("rootNode: "+ rootNode.getName()+" :C: "+rootNode.getChildren()+" :P: "+rootNode.getParents());

                pointerNode = new GraphNode(page.getUrl().getKeyWord(), page.getLinks());
                System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                System.out.println("current pointerNode: "+ pointerNode.getName() +":C:"+pointerNode.getChildren().keySet()+":P:"+pointerNode.getParents().keySet());

                allNodes.put(pointerNode.getName(),pointerNode);
            }else
            {
                System.out.println("case2");
                //rootNode
                if(rootNode.getName() != null)
                {
                    // if rootNodes中存在page's root
                    System.out.println("case2-1");
                    if(rootNode.getName().equals(page.getRoot().getKeyWord())){
                        //if pageNode存在于allNodes中
                        System.out.println("case2-1-1");
                        if(allNodes.containsKey(page.getUrl().getKeyWord()))
                        {
                            System.out.println("case2-1-1-1");
                            pointerNode = allNodes.get(page.getUrl().getKeyWord());
                            System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                            System.out.println("current pointerNode: "+ pointerNode.getName() +":C:"+pointerNode.getChildren().keySet()+":P:"+pointerNode.getParents().keySet());
                        }else
                        {
                            //pageNode不存在于allNodes中
                            System.out.println("case2-1-1-2");
                            boolean flag = false;
                            for(Url url : pointerNode.getLinks())
                            {
                                if(url.getKeyWord().equals(page.getUrl().getKeyWord())){
                                    System.out.println("current pointerNode has the url: "+url);
                                    flag = true;
                                    //System.out.println(flag);
                                    break;
                                }
                            }
                            if (flag){
                                //pageNode存在于pointerNode页面的links中
                                System.out.println("case2-1-1-2-1");
                                pageNode.addParents(pointerNode);
                                pointerNode.addChildren(pageNode);

                                System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                                System.out.println("current pointerNode: "+pointerNode.getName()+":C:"+pointerNode.getChildren().keySet()+":P:"+pointerNode.getParents().keySet());

                                allNodes.put(pageNode.getName(), pageNode);
                                pointerNode = pageNode;
                            }
                            else{
                                //pageNode不存在于pointerNode页面的links中
                                System.out.println("case2-1-1-2-2");
                                boolean flag1 = false;
                                GraphNode tmpNode = new GraphNode();
                                for(GraphNode node: allNodes.values())
                                {
                                    Set<Url> links = node.getLinks();
                                    for (Url link:links)
                                    {
                                        if (link.getKeyWord().equals(pageNode.getName()))
                                        {
                                            flag1 = true;
                                            tmpNode = node;
                                        }
                                    }
                                }
                                if (flag1){
                                    System.out.println("case2-1-1-2-2-1");
                                    //pageNode不存在于pointerNode页面的links中，但pageNode存在于allNodes中某个node的links中
                                    pointerNode = tmpNode;
                                    pointerNode.addChildren(pageNode);
                                    pageNode.addParents(pointerNode);
                                    allNodes.put(pageNode.getName(), pageNode);

                                    System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                                    System.out.println("current pointerNode: "+pointerNode.getName()+" :C: "+pointerNode.getChildren().keySet()+" :P: "+pointerNode.getParents().keySet());

                                }
                                else
                                {
                                    //pageNode不存在于pointerNode页面的links中，且pageNode不存在于allNodes中某个node的links中
                                    //说明pageNode是通过搜索得来的
                                    System.out.println("case2-1-1-2-2-2");
                                    if (searchOrNot)
                                    {//从SEARCH_LIST_URL中load页面
                                        System.out.println("case2-1-1-2-2-2-1");
                                        pointerNode.addChildren(pageNode);
                                        pageNode.addParents(pointerNode);
                                        allNodes.put(pageNode.getName(),pageNode);
                                        System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                                        System.out.println("current pointerNode: "+pointerNode.getName()+" :C: "+pointerNode.getChildren().keySet()+" :P: "+pointerNode.getParents().keySet());

                                    }else{
                                        System.out.println("case2-1-1-2-2-2-2");
                                        pointerNode.addChildren(pageNode);
                                        pageNode.addParents(pointerNode);
                                        allNodes.put(pageNode.getName(),pageNode);
                                        System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                                        System.out.println("current pointerNode: "+pointerNode.getName()+" :C: "+pointerNode.getChildren().keySet()+" :P: "+pointerNode.getParents().keySet());
                                        pointerNode = pageNode;
                                    }
                                }
                            }
                        }

                    }
                    //：NO (OPEN A NEW TAB)
                    else
                    {
                        //if page.url 在 pointerNode.links 中
                        System.out.println("case2-1-2");
                        boolean flag = false;
                        for(Url url : pointerNode.getLinks())
                        {
                            if(url.getKeyWord().equals(page.getUrl().getKeyWord())){
                                System.out.println("current pointerNode has the url: "+url);
                                flag = true;
                                //System.out.println(flag);
                                break;
                            }
                        }
                        if(flag)
                        {
                            System.out.println("case2-1-2-1");
                            allNodes.put(pageNode.getName(), pageNode);
                            pointerNode.addChildren(pageNode);
                            pageNode.addParents(pointerNode);
                            //pointerNode = allNodes.get(pageNode.getName());
                            System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                            System.out.println("current pointerNode: "+pointerNode.getName()+" :C: "+pointerNode.getChildren().keySet()+" :P: "+pointerNode.getParents().keySet());
                        }
                        //多个tab时，从非pointerNode(此前load的node)中load新页面
                        else
                        {
                            System.out.println("case2-1-2-2");
                            System.out.println("multi-tab allNodes: "+allNodes.keySet());
                            System.out.println("loaded page: "+ page.getUrl() + " root: "+page.getRoot());
                            boolean flag2 = false;
                            GraphNode tmpNode = new GraphNode();
                            for (GraphNode node:allNodes.values())
                            {
                                Set<Url> links = node.getLinks();
                                for (Url link: links)
                                {
                                    if(link.getKeyWord().equals(pageNode.getName()))
                                    {
                                        flag2 = true;
                                        tmpNode = node;
                                    }
                                }
                            }
                            //if(allNodes.keySet().contains(page.getRoot().getKeyWord()))
                            if(flag2)
                            {
                                System.out.println("case2-1-2-2-1");
                                //pointerNode = allNodes.get(page.getUrl().getKeyWord());
                                pointerNode = tmpNode;
                                pointerNode.addChildren(pageNode);
                                pageNode.addParents(pointerNode);
                                allNodes.put(pageNode.getName(), pageNode);
                                //pointerNode = pageNode;

                                System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                                System.out.println("current pointerNode: "+pointerNode.getName()+" :C: "+pointerNode.getChildren().keySet()+" :P: "+pointerNode.getParents().keySet());
                            }
                            else
                            {
                                System.out.println("case2-1-2-2-2");
                            }
                        }
                    }
                }else
                {
                    System.out.println("case2-2");
                    rootNode = pageNode;
                }
            }
            searchOrNot = false;

            System.out.println("allNodes: "+ allNodes.keySet());
            System.out.println("-----------------------------------------");
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }
    public void searchTo(Page page)
    {
        //Set<Url> links = page.getLinks();
        //GraphNode tmPointNode = pointerNode;
        searchOrNot = true;
        System.out.println(searchOrNot);
        System.out.println("111111111111");
    }

    public void popupTo (Page page)
    {
        GraphNode pageNode = new GraphNode(page.getUrl().getKeyWord(), page.getLinks());
        //System.out.println("url: " + page.getUrl()+"root: "+page.getRoot());
        //System.out.println("current pointerNode: "+pointerNode.getName()+" :C: "+pointerNode.getChildren().keySet()+" :P: "+pointerNode.getParents().keySet());
        //System.out.println("current pageNode: "+pageNode.getName()+" :C: "+pageNode.getChildren().keySet()+" :P: "+pageNode.getParents().keySet());
        boolean flag = false;
        for (Url url : pointerNode.getLinks()){
            if (url.getKeyWord().equals(pageNode.getName())){
                flag = true;
            }
        }
        if (flag){
            System.out.println("case1");
            pointerNode.addChildren(pageNode);
            pageNode.addParents(pointerNode);
            allNodes.put(pageNode.getName(), pageNode);
            System.out.println("current pointerNode: "+pointerNode.getName()+" :C: "+pointerNode.getChildren().keySet()+" :P: "+pointerNode.getParents().keySet());
            System.out.println("current pageNode: "+pageNode.getName()+" :C: "+pageNode.getChildren().keySet()+" :P: "+pageNode.getParents().keySet());

        }else {
            System.out.println("case2");
            boolean flag1 = false;
            GraphNode tmpNode = new GraphNode();
            for (GraphNode node:allNodes.values())
            {
                Set<Url> links = node.getLinks();
                for (Url link: links)
                {
                    if(link.getKeyWord().equals(pageNode.getName()))
                    {
                        flag1 = true;
                        tmpNode = node;
                    }
                }
            }
            if (flag1){
                System.out.println("case2-1");
                pointerNode = tmpNode;
                pointerNode.addChildren(pageNode);
                pageNode.addParents(pointerNode);
                allNodes.put(pageNode.getName(), pageNode);
                System.out.println("current pointerNode: "+pointerNode.getName()+" :C: "+pointerNode.getChildren().keySet()+" :P: "+pointerNode.getParents().keySet());
                System.out.println("current pageNode: "+pageNode.getName()+" :C: "+pageNode.getChildren().keySet()+" :P: "+pageNode.getParents().keySet());
            }
        }
    }

    public void goBackward (Page from, Page to)
    {
        System.out.println("go backward:");
        System.out.println("from page " + from.getUrl());
        System.out.println("to page " + to.getUrl());
    }

    public void goForward (Page from, Page to)
    {
        System.out.println("go forward:");
        System.out.println("from page " + from.getUrl());
        System.out.println("to page " + to.getUrl());
    }
    /*
    public void drawPath(String sessionId, Canvas canvas)
    {
        DBAccess dbAccess = new DBAccess();
        DataVisualizing dataVisualizing = new DataVisualizing(canvas);
        try
        {
            Map<String, GraphNode> allNodes = dbAccess.getSessionAllnodes(sessionId);
            Set<String> graphNodes = allNodes.keySet();
            for (String node: graphNodes){
                dataVisualizing.drawNodes(node);
                dataVisualizing.drawPaths(allNodes.get(node));
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }*/

    public Double getGiniImpurity(Map<String, GraphNode> allNodes){
        int numNodes, numEdges, numEdgeSingleNode;
        double gini = 0.0;
        Map<String, SerializableGraphNode> serializableAllNodes = new HashMap<>();

        for (Map.Entry<String, GraphNode> entry : allNodes.entrySet())
        {
            serializableAllNodes.put(entry.getKey(), new SerializableGraphNode(entry.getValue()));
        }
        numEdges = 0;
        List<Integer> listEdgeSingleNode = new ArrayList<>();
        for(Map.Entry<String, SerializableGraphNode> entry: serializableAllNodes.entrySet())
        {
            numEdgeSingleNode = entry.getValue().getParentNames().size()+ entry.getValue().getChildNames().size();
            listEdgeSingleNode.add(numEdgeSingleNode);
            numEdges += entry.getValue().getChildNames().size();
        }
        double sum = 0.0;
        for (Integer itm: listEdgeSingleNode)
        {
            sum += (itm/numEdges) * (itm/numEdges);
        }
         numNodes = allNodes.keySet().size();
        gini = sum * numNodes;
        return gini;
    }

    public Map<String, GraphNodeWithProperties> getAllNodesWithProperties(Map<String, GraphNode> allNodes) throws IOException, SolrServerException
    {
        Map<String, GraphNodeWithProperties> graphNodeWithProperties = new HashMap<>();
        for (Map.Entry<String, GraphNode> entry : allNodes.entrySet())
        {
            graphNodeWithProperties.put(entry.getKey(), new GraphNodeWithProperties(entry.getValue()));
        }

        return  graphNodeWithProperties;
    }
}
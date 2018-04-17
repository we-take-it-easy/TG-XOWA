package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.crawler.HttpUtils;
import cn.edu.ruc.xowa.log.database.DBAccess;
import cn.edu.ruc.xowa.log.page.Page;
import cn.edu.ruc.xowa.log.page.Url;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

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
    private static SolrClient client = new HttpSolrClient (SolrConfig.urlString);

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

    public boolean isStarted ()
    {
        return this.sessionId != null && !this.sessionId.isEmpty();
    }

    public void start (String sessionId)
    {
        this.clear();
        this.sessionId = sessionId;
        System.out.println("start session: " + sessionId);
    }

    public void end ()
    {
        System.out.println("root node: "+rootNode+" :C: "+rootNode.getName());
        System.out.println("allNodes: ");
        try
        {
            for (GraphNode node : allNodes.values())
            {
                node.setDiversity(CalculateDiversity(node));
                node.setNormality(CalculateNormality(node));
                System.out.println(node.getName()+" :C: "+node.getChildren().keySet()+" :P: "+node.getParents().keySet() +" diversity: "+ node.getDiversity()+ " normality: "+ node.getNormality());
            }
            System.out.println("gini: "+ CalculateGiniImpurity(allNodes));
            this.dbAccess.insertSessionAllnodes(this.sessionId,this.userName, this.allNodes, CalculateGiniImpurity(allNodes));
        } catch (SQLException | SolrServerException | IOException e)
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
        this.sessionId = null;
        this.pointerNode = new GraphNode();
        this.rootNode = new GraphNode();
        if (this.allNodes != null)
        {
            this.allNodes.clear();
        }
        this.allNodes = new HashMap<>();
        this.searchOrNot = false;
        System.out.println("clear...");
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

    public static double CalculateGiniImpurity(Map<String, GraphNode> allNodes){
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
            numEdges = numEdges + entry.getValue().getChildNames().size();
        }
        System.out.println("listEdgeSingleNode: "+ listEdgeSingleNode);
        double sum = 0.0;
        for (Integer itm: listEdgeSingleNode)
        {
            double f = ((double)itm/numEdges) * ((double)itm/numEdges);
            if (f != Double.NaN)
            {
                sum += f;
            }
        }
        numNodes = allNodes.keySet().size();
        if (numEdges != 0)
        {
            gini = sum * numNodes;
        }
        return gini;
    }

    public static double CalculateDiversity(GraphNode graphNode) throws IOException, SolrServerException
    {
        double diver = 0.0;
        List<String> nodesNameList= new ArrayList<>(graphNode.getChildren().keySet());
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
                //System.out.println("diversity: "+ diver);
            }
        }
        return diver;
    }

    public static double CalculateNormality(GraphNode graphNode) throws IOException, SolrServerException
    {
        double normality;

        double sum = 0.0;
        List<String> nodesNameList= new ArrayList<>(graphNode.getChildren().keySet());
        if (!nodesNameList.isEmpty())
        {
            SolrQuery query = new SolrQuery();
            for (int i=0; i<nodesNameList.size(); i++){
                query.setQuery("REVISION_TEXT: "+"\""+ nodesNameList.get(i) +"\"");
                QueryResponse resp = client.query(query);
                //numFound = resp.getResults().getNumFound();
                sum += (double)resp.getResults().getNumFound();
            }
            normality = sum/nodesNameList.size();
        }
        else normality = 0.0;
        //System.out.println("normality: "+ normality);
        return normality;
    }

    public List<String> GetEntityNames(String keyword) throws IOException,ParserException
    {
        List<String> entities = new ArrayList<>();
        String transKeyword = null;
        if (keyword.equals("in other words")) transKeyword = "in+other+words";
        else if (keyword.equals("that is to say")) transKeyword = "that+is+to+say";
        else if (keyword.equals("in plain English")) transKeyword = "in+plain+english";
        else if (keyword.equals("namely")) transKeyword = "namely";
        //in+other+words
        String html = HttpUtils.Instance().getPageContent("https://en.wikipedia.org/w/index.php?title=Special:Search&limit=50&profile=default&fulltext=1&searchToken=ew6grhl6iyet3idku9lvggeqx&search=%22"+transKeyword+"%22");
        //System.out.println(html);
        Parser parser = Parser.createParser(html, "utf-8");
        NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);

        NodeList links = parser.extractAllNodesThatMatch(linkFilter);
        for (Node node : links.toNodeArray())
        {
            if (node != null && node instanceof LinkTag)
            {
                LinkTag link = (LinkTag) node;
                //System.out.println(link.getText());
                if (link.getLink().startsWith("/wiki/") && !link.getLink().contains("Main_Page") && !link.getLink().contains("Portal:Contents")
                        && !link.getLink().contains("Portal:Featured_content") && !link.getLink().contains("Portal:Current_events")
                        && !link.getLink().contains("Special:Random") && !link.getLink().contains("Help:Contents")
                        && !link.getLink().contains("Wikipedia:About") && !link.getLink().contains("Wikipedia:Community_portal")
                        && !link.getLink().contains("Special:RecentChanges") && !link.getLink().contains("/Wikipedia:File_Upload_Wizard")
                        && !link.getLink().contains("Special:SpecialPages") && !link.getLink().contains("Wikipedia:About")
                        && !link.getLink().contains("Wikipedia:General_disclaimer") && !link.getLink().contains("Help:Searching")
                        && !link.getLink().contains("Special:MyTalk") && !link.getLink().contains("Special:MyContributions"))
                {
                    entities.add(link.getLinkText());
                    //System.out.println(link.getLinkText());
                }
            }
        }
        return entities;
    }

    public void saveQuestions(int userId,String entityName, String question, String answer)
    {
        try
        {
            this.dbAccess.insertQuesForUser2(userId,entityName, question, answer);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void saveStepQuestion(String description, String curQus, int certainty, String changed)
    {
        if (this.sessionId != null && !this.sessionId.isEmpty())
        {
            try
            {
                this.dbAccess.insertStepQuestion(sessionId, description, curQus, certainty, changed);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println("session id is null, please begin a session first.");
        }
    }

    public void saveSessionQuestions(String plan, String specific, String q3)
    {
        if (this.sessionId != null && !this.sessionId.isEmpty())
        {
            try
            {
                this.dbAccess.insertSessionQuestion(sessionId, plan, specific, q3);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println("session id is null, please begin a session first.");
        }
    }

    public boolean updateSessionQuestion(String answer, String multifacet)
    {
        try
        {
            int affected = DBAccess.Instance().updateSessionQuestion(sessionId,answer,multifacet);
            return affected == 1;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSessionQuestion(String multifacet)
    {
        try
        {
            int affected = DBAccess.Instance().updateSessionQuestion(sessionId, null, multifacet);
            return affected == 1;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void saveSentences(int userId, String entityName, String flag, String deletedSentence)
    {
        try
        {
            this.dbAccess.insertDeletedForPages2(userId, entityName, flag, deletedSentence);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<String> GetTask()
    {
        List<String> task = new ArrayList<>();
        task = this.dbAccess.getRandomTask();
        return task;
    }
}
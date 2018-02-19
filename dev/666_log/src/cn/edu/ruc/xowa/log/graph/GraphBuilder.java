package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.page.Page;

import java.util.HashMap;
import java.util.Map;

public class GraphBuilder
{
    private static GraphBuilder ourInstance = new GraphBuilder();

    public static GraphBuilder getInstance()
    {
        return ourInstance;
    }

    private String userName = null;
    private String sessionId = null;
    //private GraphNode pageNode;
    private GraphNode pointerNode;
    private GraphNode rootNode;
    private Map<String, GraphNode> allNodes;

    private GraphBuilder()
    {
        //this.pageNode = new GraphNode();
        this.pointerNode = new GraphNode();
        this.rootNode = new GraphNode();
        this.allNodes = new HashMap<>();
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

    public void end ()
    {
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

                rootNode.setName(page.getUrl().getKeyWord());
                rootNode.setLinks(page.getLinks());
                System.out.println("rootNode: "+ rootNode.getName());

                pointerNode = new GraphNode(page.getUrl().getKeyWord(), page.getLinks());
                System.out.println("pointNode: "+pointerNode.getName());
                allNodes.put(pageNode.getName(),pageNode);
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
                            System.out.println("current pointerNode: "+ pointerNode.getName() +":C:"+pointerNode.getChildren().keySet()+":P:"+pointerNode.getParents().keySet());
                        }else
                        {
                            System.out.println("case2-1-1-2");
                            pageNode.addParents(pointerNode);
                            pointerNode.addChildren(pageNode);

                            System.out.println("current pageNode: "+pageNode.getName()+":C:"+pageNode.getChildren().keySet()+":P:"+pageNode.getParents().keySet());
                            System.out.println("current pointerNode: "+pointerNode.getName()+":C:"+pointerNode.getChildren().keySet()+":P:"+pointerNode.getParents().keySet());

                            allNodes.put(pageNode.getName(), pageNode);
                            pointerNode = pageNode;
                        }

                    }
                    //：NO (OPEN A NEW TAB)
                    else
                    {
                        //if page.url 在 pointerNode.links 中
                        System.out.println("case2-1-2");
                        //System.out.println("pointerNode.getName(): "+pointerNode.getName());
                        //System.out.println("pointerNode.getLinks(): "+pointerNode.getLinks());
                        if(pointerNode.getLinks().contains(page.getUrl()))
                        {
                            System.out.println("case2-1-2-1");
                            allNodes.put(pageNode.getName(), pageNode);
                            pointerNode.addChildren(pageNode);
                            pageNode.addParents(pointerNode);
                            pointerNode = allNodes.get(pageNode.getName());
                        }
                        //多个tab时，从非pointerNode(此前load的node)中load新页面
                        else
                        {
                            //if
                            System.out.println("case2-1-2-2");
                            if(allNodes.containsKey(page.getRoot().getKeyWord()))
                            {
                                System.out.println("case2-1-2-2-1");
                                allNodes.put(pageNode.getName(), pageNode);
                                pointerNode = allNodes.get(page.getRoot().getKeyWord());
                                pointerNode.addChildren(pageNode);
                                pageNode.addParents(pointerNode);

                                pointerNode = allNodes.get(pageNode.getName());
                            }
                            else
                            {
                                //......
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

            //pointerNode.put(page.getUrl().getKeyWord(), );
            System.out.println("allNodes: "+ allNodes.keySet());
            for (String itm : allNodes.keySet()){
                System.out.println("itm name: "+itm+" value: "+ allNodes.get(itm).getName());
                //System.out.println("children: "+allNodes.get(itm).getChildren());
                //System.out.println("parents: "+allNodes.get(itm).getParents());
            }
            System.out.println("-----------------------------------------");
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        //System.out.println("go to:");
        //System.out.println("url:  " + page.getUrl());
        //System.out.println("prev: " + page.getPrevious());
        //System.out.println("root: " + page.getRoot());
        //System.out.println("#link:" + page.getLinks().size());
    }

    public void popupTo (Page page)
    {
        //System.out.println("popup to:");
        //System.out.println("url:  " + page.getUrl());
        //System.out.println("prev: " + page.getPrevious());
        //System.out.println("root: " + page.getRoot());
        //System.out.println("#link:" + page.getLinks().size());
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
}

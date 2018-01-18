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

    private String userName = null;
    private String sessionId = null;
    private List<GraphNode> rootNodes = null;
    private Map<String, GraphNode> pointerNodes = null;
    private Map<String, GraphNode> allNodes = null;

    private GraphBuilder()
    {
        this.rootNodes = new ArrayList<>();
        this.pointerNodes = new HashMap<>();
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
        System.out.println("end session...");
    }

    public void clear ()
    {
        System.out.println("give up session, clear...");
    }

    public void goTo (Page page)
    {
        System.out.println("go to:");
        System.out.println("url:  " + page.getUrl());
        System.out.println("prev: " + page.getPrevious());
        System.out.println("root: " + page.getRoot());
        System.out.println("#link:" + page.getLinks().size());
    }

    public void popupTo (Page page)
    {
        System.out.println("popup to:");
        System.out.println("url:  " + page.getUrl());
        System.out.println("prev: " + page.getPrevious());
        System.out.println("root: " + page.getRoot());
        System.out.println("#link:" + page.getLinks().size());
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

package cn.edu.ruc.xowa.log.graph;

import cn.edu.ruc.xowa.log.page.Url;

import java.util.List;
import java.util.Map;

public class GraphNode
{
    private String name;
    private List<Url> links;
    private Map<String, GraphNode> parents;
    private Map<String, GraphNode> children;
}

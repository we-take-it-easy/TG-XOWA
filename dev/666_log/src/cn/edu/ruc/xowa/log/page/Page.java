package cn.edu.ruc.xowa.log.page;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

/**
 * 一个在xowa中打开的页面，包括在tab和popup中打开的
 */
public class Page
{
    private Url url;
    private Url previous;
    private Url root;
    private Set<Url> links;
    private String html;

    public Page(Url url)
    {
        this.url = url;
        this.links = new HashSet<>();
    }

    public Url getUrl()
    {
        return url;
    }

    public Url getPrevious()
    {
        return previous;
    }

    public void setPrevious(Url previous)
    {
        this.previous = previous;
    }

    public Url getRoot()
    {
        return root;
    }

    public void setRoot(Url root)
    {
        this.root = root;
    }

    public Set<Url> getLinks()
    {
        return links;
    }

    public String getHtml()
    {
        return html;
    }

    /**
     * 参数为改页面的html内容，该函数将从html中解析出所有wiki页面的链接，非wiki链接会被排除
     * @param html
     */
    public void setHtml(String html)
    {
        this.html = html;
        // parse the html and get links.
        Parser parser = Parser.createParser(html, "utf-8");
        NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
        try
        {
            NodeList linkList = parser.extractAllNodesThatMatch(linkFilter);
            for (Node node : linkList.toNodeArray())
            {
                if (node != null && node instanceof LinkTag)
                {
                    LinkTag link = (LinkTag) node;
                    Url url = new Url(link.getLink());
                    if (url.getType() == UrlType.NORMAL_PAGE_URL)
                    {
                        this.links.add(url);
                    }
                }
            }
        } catch (ParserException e)
        {
            e.printStackTrace();
        }
    }
}

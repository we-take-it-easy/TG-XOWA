package cn.edu.ruc.xowa.log.domain;

import java.util.List;

public class Page
{
    private Url url;
    private Url previous;
    private Url from;
    private PageType type;
    private List<Url> links;
    private String html;

    public Page(Url url)
    {
        this.url = url;
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

    public Url getFrom()
    {
        return from;
    }

    public void setFrom(Url from)
    {
        this.from = from;
    }

    public PageType getType()
    {
        return type;
    }

    public void setType(PageType type)
    {
        this.type = type;
    }

    public List<Url> getLinks()
    {
        return links;
    }

    public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
        // parse the html and get links.
    }
}

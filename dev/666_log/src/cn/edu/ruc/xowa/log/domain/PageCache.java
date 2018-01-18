package cn.edu.ruc.xowa.log.domain;

import java.util.HashMap;
import java.util.Map;

public class PageCache
{
    private static PageCache ourInstance = new PageCache();

    public static PageCache getInstance()
    {
        return ourInstance;
    }

    private Map<Url, Page> cache = null;

    private PageCache()
    {
        this.cache = new HashMap<>();
    }

    public void putPage (Url url, Page page)
    {
        this.cache.put(url, page);
    }

    public Page getPage (Url url, PageType pageType)
    {
        Page page = this.cache.get(url);
        if (page != null && page.getType() == pageType)
        {
            return page;
        }
        return null;
    }

    public void evictPage (Url url, PageType pageType)
    {
        Page page = this.cache.get(url);
        if (page != null && page.getType() == pageType)
        {
            this.cache.remove(url);
        }
    }
}

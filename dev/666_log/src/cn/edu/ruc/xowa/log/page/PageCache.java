package cn.edu.ruc.xowa.log.page;

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

    public Page getPage (Url url)
    {
        return this.cache.get(url);
    }

    public void evictPage (Url url)
    {
        Page page = this.cache.get(url);
        if (page != null)
        {
            this.cache.remove(url);
        }
    }
}

package cn.edu.ruc.xowa.log.domain;

public class Url
{
    // url中的最后一段，即搜索关键词或者实体名称
    private String keyWord = "";
    // url的类型
    private UrlType type = UrlType.UNKNOWN_TYPE_URL;
    // 完整、原始的的url
    private String full = "";

    public Url(String full)
    {
        this.full = full;
        if (full.contains("wiki/Special:Search") || full.contains("wiki/Special:XowaSearch"))
        {
            this.type = UrlType.SEARCH_LIST_URL;
            String[] splits = full.split("search=");
            if (splits.length == 2)
            {
                this.keyWord = splits[1].split("&")[0];
            }
        }
        else if (full.contains("wiki/Special:"))
        {
            this.type = UrlType.SPACIAL_PAGE_URL;
            String[] splits = full.split("wiki/Special:");
            if (splits.length == 2)
            {
                this.keyWord = splits[1];
            }
        }
        else if (full.contains("/wiki/"))
        {
            this.type = UrlType.NORMAL_PAGE_URL;
            String[] splits = full.split("wiki/");
            if (splits.length == 2)
            {
                this.keyWord = splits[1];
            }
        }
        else
        {
            this.type = UrlType.UNKNOWN_TYPE_URL;
        }
    }

    public String getKeyWord()
    {
        return keyWord;
    }

    public UrlType getType()
    {
        return type;
    }

    public String getFull()
    {
        return full;
    }

    @Override
    public int hashCode()
    {
        return this.full.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Url)
        {
            Url u = (Url) o;
            return this.full.equalsIgnoreCase(u.full);
        }
        if (o instanceof String)
        {
            return this.full.equalsIgnoreCase((String)o);
        }
        return false;
    }
}

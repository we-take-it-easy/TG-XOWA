package cn.edu.ruc.xowa.log.domain;

public class Url
{
    private String keyWord = "";
    private UrlType type = UrlType.UNKNOWN_TYPE_URL;
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
            this.type = UrlType.UNKNOWN_TYPE_URL;
            String[] splits = full.split("wiki/Special:");
            if (splits.length == 2)
            {
                this.keyWord = splits[1];
            }
        }
        else
        {
            this.type = UrlType.NORMAL_PAGE_URL;
            String[] splits = full.split("wiki/");
            if (splits.length == 2)
            {
                this.keyWord = splits[1];
            }
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
}

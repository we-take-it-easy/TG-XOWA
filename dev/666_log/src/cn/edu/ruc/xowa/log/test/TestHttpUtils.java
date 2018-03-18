package cn.edu.ruc.xowa.log.test;

import cn.edu.ruc.xowa.log.crawler.HttpUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.IOException;

public class TestHttpUtils
{
    public static void main(String[] args)
    {
        try
        {
            String html = HttpUtils.Instance().getPageContent("https://en.wikipedia.org/w/index.php?title=Special:Search&limit=50&profile=default&fulltext=1&searchToken=ew6grhl6iyet3idku9lvggeqx&search=%22" +
                    "in+other+words" +
                    "%22");
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
                            && !link.getLink().contains("Wikipedia:General_disclaimer") && !link.getLink().contains("Help:Searching"))
                    {
                        System.out.println(link.getLinkText());
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ParserException e)
        {
            e.printStackTrace();
        }
    }
}

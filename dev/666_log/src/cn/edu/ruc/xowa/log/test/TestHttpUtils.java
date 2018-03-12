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
            String html = HttpUtils.Instance().getPageContent("https://en.wikipedia.org/w/index.php?title=Special:Search&profile=default&fulltext=1&searchToken=ew6grhl6iyet3idku9lvggeqx&search=%22in+other+words%22");
            //System.out.println(html);
            Parser parser = Parser.createParser(html, "ut-8");
            NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);

            NodeList links = parser.extractAllNodesThatMatch(linkFilter);
            for (Node node : links.toNodeArray())
            {
                if (node != null && node instanceof LinkTag)
                {
                    LinkTag link = (LinkTag) node;
                    //System.out.println(link.getText());
                    if (link.getLink().startsWith("/wiki/"))
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

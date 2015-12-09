package businesslayer;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gunbo on 12/3/2015.
 */
public class ScrapeYahooNewsComments{

    private boolean scrapingDone = false;
    private ArrayList<Person> persons = new ArrayList<>();
    private String url;
    private int maxComments = 0;

    public ScrapeYahooNewsComments(String url, int maxComments) {
        this.url = url;
        this.maxComments = maxComments;

        try {
            Document document = Jsoup.connect(url).userAgent("Mozilla").get();

            Scanner scanner = new Scanner(document.toString());

            Pattern pattern = Pattern.compile("contentId: '(?:.*)',");
            String contentId = null;
            boolean foundContentId = false;

            String line;
            Matcher matcher;
            for (int i = 0; i < 400; i++){
                line = scanner.nextLine();
                matcher = pattern.matcher(line);
                if (matcher.find()){
                    contentId = line.replaceAll("contentId: '","");
                    contentId = contentId.replaceFirst("',","");
                    contentId = contentId.replaceAll("\\s+","");
                    foundContentId = true;
                    break;
                }
            }

            int commentCount = Integer.parseInt(document.select("span#total-comment-count").get(0).text());

            if (!foundContentId){
                throw new Exception("Could not find contentId");
            }

            Integer count = null;
            Integer offset = 0;
            Elements names = null;
            Elements commentContents = null;

            if (maxComments == 0){
                maxComments = commentCount;
            }

            while(commentCount > 1) {
                if (commentCount >= 100) {
                    count = 100;
                    commentCount = commentCount - 100;

                } else {
                    count = commentCount;
                    commentCount = commentCount - count;
                }
                document = Jsoup.connect("http://news.yahoo.com/_xhr/contentcomments/get_comments/?content_id= " + contentId + "&_device=full&count=" + count.toString() + "&isNext=true&offset=" + offset + "&_media.modules.content_comments.switches._enable_view_others=1&_media.modules.content_comments.switches._enable_mutecommenter=1&enable_collapsed_comment=1").ignoreContentType(true).userAgent("Mozilla").get();
                offset = count + offset;
                commentContents = document.select("p[class*=comment-content]");
                names = document.select("span[data-guid]");

                for(int i = 0; i < commentContents.size(); i++){
                    String nickname = new Scanner(names.get(i).toString()).nextLine();
                    nickname = nickname.substring(nickname.indexOf(">")+1,nickname.indexOf("&lt"));

                    String comment = commentContents.get(i).toString();
                    comment = comment
                            .substring(comment.indexOf(">")+3, comment.indexOf("&lt")-3)
                            .replaceAll("(?:\\<br\\ \\\\\\>\\\\n)", "\n")
                            .replaceAll("(?:\\\\u)(?:201)?.", " ");

                    persons.add(new Person(nickname,comment));
                    maxComments--;
                    if (maxComments == 0){
                        return;
                    }
                }



            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scrapingDone = true;
        System.out.println("Done Scraping");

    }




    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMaxComments() {
        return maxComments;
    }

    public void setMaxComments(int maxComments) {
        this.maxComments = maxComments;
    }

    public boolean isScrapingDone() {
        return scrapingDone;
    }

    public void setScrapingDone(boolean scrapingDone) {
        this.scrapingDone = scrapingDone;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
}


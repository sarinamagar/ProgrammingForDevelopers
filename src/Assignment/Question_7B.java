package Assignment;
/**Write multithreaded web crawler**/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Question_7B {
    static class WebCrawler {

        private List<String> urls = new ArrayList<>();// Array List of Strings to store links that have already been visisted
        private ExecutorService executorService; // ExecutorService is used to run new threads
        private int maxDdepth; // stores the max depth at which the crawler operates

        public WebCrawler(int maxThreads, int maxDepth) {
            this.executorService = Executors.newFixedThreadPool(maxThreads);//declaring no of threads = maxThreads
            this.maxDdepth = maxDepth;
        }

        public void crawl(String url) {
            crawl(url, 0); //calling the main crawler function
        }

        private void crawl(final String link, final int depth) {
            if (urls.contains(link) || depth > maxDdepth) { //checking whether the link has already been visited or the max depth has been reached
                return;
            }
            urls.add(link); // adding currently crawling link to list of visited links
            executorService.execute(new Runnable() { // running a new thread for process
                public void run() {
                    System.out.println("Crawler link: " + link + "\n depth: " + depth); //printing currently crawling link and depth
                    try {
                        Document document = Jsoup.connect(link).get(); // rtrieving document
                        Elements links = document.select("a[href]");// selecting all the links from the document
                        for (Element link : links) {// iterating through all the links
                            String href = link.attr("abs:href"); // getting the absolute url of the link
                            crawl(href, depth + 1);// calling the crawl method recursively with the new link and depth + 1
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // Main method to start the program
        public static void main(String[] args) {
            String link = "https://schoolworkspro.com/";// starting url
            int threads = 10;// maximum number of threads
            int depth = 3; // maximum depth of crawling
            WebCrawler crawler = new WebCrawler(threads, depth);// creating an instance of WebCrawler class
            crawler.crawl(link);// calling the crawl method with the starting url
        }
    }
}
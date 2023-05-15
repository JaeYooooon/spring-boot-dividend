package zerobase.dividend;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import zerobase.dividend.model.Company;
import zerobase.dividend.scraper.Scraper;
import zerobase.dividend.scraper.YahooFinanceScraper;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class DividendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendApplication.class, args);

//        System.out.println("Main -> " + Thread.currentThread().getName());

//        Scraper scraper = new YahooFinanceScraper();
//        var result = scraper.scrap(Company.builder().ticker("O").build());
//        var result = scraper.scrapCompanyByTicker("MMM");
//        System.out.println(result);

//        Trie trie = new PatriciaTrie();
//
//        AutoComplete autoComplete = new AutoComplete(trie);
//        AutoComplete autoComplete1 = new AutoComplete(trie);
//
//        autoComplete.add("hello");
//        autoComplete1.add("hello");
//
//        System.out.println(autoComplete.get("hello"));
//        System.out.println(autoComplete1.get("hello"));
    }
}

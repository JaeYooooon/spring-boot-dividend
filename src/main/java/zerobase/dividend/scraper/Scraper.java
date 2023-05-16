package zerobase.dividend.scraper;

import zerobase.dividend.model.*;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}

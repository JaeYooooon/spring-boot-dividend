package zerobase.dividend.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zerobase.dividend.model.Company;
import zerobase.dividend.model.ScrapedResult;
import zerobase.dividend.model.constants.CacheKey;
import zerobase.dividend.persist.CompanyRepository;
import zerobase.dividend.persist.DividendRepository;
import zerobase.dividend.persist.entity.CompanyEntity;
import zerobase.dividend.persist.entity.DividendEntity;
import zerobase.dividend.scraper.Scraper;

import java.util.List;

import static zerobase.dividend.model.constants.CacheKey.*;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    private final Scraper yahooFinanceScraper;

//    @Scheduled(cron = "0/5 * * * * *")
//    public void test() {
//        System.out.println("now -> " + Thread.currentThread().getName() + " : " + System.currentTimeMillis());
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void test1() throws InterruptedException{
//        Thread.sleep(10000); //10초간 일시 정지
//        System.out.println(Thread.currentThread().getName() + " 테스트 1 : " + LocalDateTime.now());
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void test2() throws InterruptedException{
//        System.out.println(Thread.currentThread().getName() + " 테스트 2 : " + LocalDateTime.now());
//    }

    @CacheEvict(value = KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling(){
//        log.info("scraping scheduler is started");
        List<CompanyEntity> companies = this.companyRepository.findAll();
        for(var company : companies){
            log.info("scraping scheduler is started -> " + company.getName());

            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(
                                                            new Company(company.getTicker(), company.getName()));
            scrapedResult.getDividends().stream().map(e -> new DividendEntity(company.getId(), e))
                    .forEach(e-> {
                        boolean exists = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                        if(!exists){
                            this.dividendRepository.save(e);
                        }
                    });
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}

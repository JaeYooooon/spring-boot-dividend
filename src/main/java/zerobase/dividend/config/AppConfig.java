package zerobase.dividend.config;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.table.TableRowSorter;

@Configuration
public class AppConfig {
    @Bean
    public Trie<String, String> trie(){
        return new PatriciaTrie<>();
    }
}
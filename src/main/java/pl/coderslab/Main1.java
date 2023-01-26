package org.example;

import com.github.slugify.Slugify;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main1 {

    final static Slugify slg = Slugify.builder().build();

    public static void main(String[] args) throws IOException {


        Document doc = null;

        try {

            doc = Jsoup.connect("https://www.infoworld.com".concat("/category/java/")).get();

        } catch (IOException e) {

            e.printStackTrace();

        }

        doc.select("div.article h3 a").forEach(System.out::println);
        Map<String, String> map = doc.select("div.article h3 a")

                .stream()

                .collect(Collectors.toMap(e -> String.join("-", UUID.randomUUID().toString(), slg.slugify(e.text())),

                        e -> "https://www.infoworld.com".concat(e.attr("href"))));


        for (String s : map.keySet()) {

            Elements select1 = doc.select("div[id=drr-container]");

            FileUtils.writeStringToFile(new File(Paths.get("articles", s.concat(".txt")).toString()),

                    select1.text(), "utf-8");


        }
    }
}
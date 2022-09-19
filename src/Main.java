import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.Match;
import info.debatty.java.stringsimilarity.*;
import info.debatty.java.stringsimilarity.experimental.Sift4;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.intuit.fuzzymatcher.domain.ElementType.ADDRESS;
import static com.intuit.fuzzymatcher.domain.ElementType.NAME;

public class Main {
    public static void main2(String[] args) {
        System.out.println("Hello world!");

        String[][] input = {
                {"1", "John Juma", "Westlands, Nairobi"},
                {"2", "John Doe", "Nakuru"},
                {"3", "John Mwajuma", "Westlands"}
        };

        List<Document> documentList = Arrays.asList(input).stream().map(contact -> {
            return new Document.Builder(contact[0])
                    .addElement(new Element.Builder<String>().setValue(contact[1]).setType(NAME).createElement())
                    .addElement(new Element.Builder<String>().setValue(contact[2]).setType(ADDRESS).createElement())
                    .createDocument();
        }).collect(Collectors.toList());

        MatchService matchService = new MatchService();
        Map<String, List<Match<Document>>> result = matchService.applyMatchByDocId(documentList);

        result.entrySet().forEach(entry -> {
            entry.getValue().forEach(match -> {
                System.out.println("Data: " + match.getData() + " Matched With: " + match.getMatchedWith() + " Score: " + match.getScore().getResult());
            });
        });
    }

    public static void main1(String[] args) {
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        String target = "John Juma";
        String source = "Juma John";
        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
        double score = service.score(source, target);

        System.out.println("Score: "+score);
    }

    public static void main(String[] args) {
        JaroWinkler l = new JaroWinkler();
        //NGram l = new NGram(4);
        //QGram l = new QGram(2);
        //RatcliffObershelp l = new RatcliffObershelp();
        /*Sift4 l = new Sift4();
        l.setMaxOffset(5);*/
        //Levenshtein l = new Levenshtein();
        //NormalizedLevenshtein l = new NormalizedLevenshtein();
        //Damerau l = new Damerau();
        //OptimalStringAlignment l = new OptimalStringAlignment();
        //LongestCommonSubsequence l = new LongestCommonSubsequence();
        //info.debatty.java.stringsimilarity.MetricLCS l = new info.debatty.java.stringsimilarity.MetricLCS();


        System.out.println(l.distance("John Juma", "John Juma"));
        System.out.println(l.distance("John Juma", "Juma John"));
        System.out.println(l.distance("John Juma", "Jonh Juma"));
        System.out.println(l.distance("John Juma", "John Mwangi"));
        System.out.println(l.distance("John Juma", "Juma J"));
        System.out.println(l.distance("John Juma", "John Mwangi Juma"));
        System.out.println(l.distance("John Juma", "J0hn Juma"));
    }
}
package uk.co.flax.luwak;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefHash;
import uk.co.flax.luwak.presearcher.TermFilteredPresearcher;
import uk.co.flax.luwak.presearcher.WildcardNGramPresearcherComponent;
import uk.co.flax.luwak.queryparsers.LuceneQueryParser;
import uk.co.flax.luwak.termextractor.querytree.QueryTree;

// Test how presearching works
// build with
//   mvn clean package dependency:copy-dependencies -DskipTests -pl luwak -am
// run with
//   java -cp luwak/target/dependency/\*:luwak/target/luwak-1.6.0-SNAPSHOT.jar uk.co.flax.luwak.Tester 'Tim OR title:Bob*'
public class Tester {

    public static void main(String[] args) throws Exception {
        String q = args.length > 0 ? args[0] : "tim OR title:Tim*";
        Query query = new LuceneQueryParser("exact").parse(q, null);
        System.err.println(query.toString());
        System.err.println();
        TermFilteredPresearcher presearcher = new TermFilteredPresearcher(new WildcardNGramPresearcherComponent()) {
            @Override
            protected Map<String, BytesRefHash> collectTerms(QueryTree tree) {
                Map<String, BytesRefHash> stringBytesRefHashMap = super.collectTerms(tree);
                for (String field : stringBytesRefHashMap.keySet()) {
                    System.err.print("Field " + field + " : ");
                    BytesRefHashIterator bytesRefHashIterator = new BytesRefHashIterator(stringBytesRefHashMap.get(field));
                    try {
                        BytesRef ref;
                        while ((ref = bytesRefHashIterator.next()) != null) {
                            System.err.print(ref.utf8ToString());
                            System.err.print(" ");
                        }
                    } catch (IOException ioe) { }
                    System.err.println();
                }
                return stringBytesRefHashMap;
            }
        };
        Document document = presearcher.indexQuery(query, null);
        System.err.println();
        System.err.println(document.toString());
    }
}

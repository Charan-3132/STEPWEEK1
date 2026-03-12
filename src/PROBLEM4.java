import java.util.*;

public class PROBLEM4 {

    private HashMap<String, Set<String>> ngramIndex = new HashMap<>();
    private HashMap<String, List<String>> documentNgrams = new HashMap<>();
    private int n = 5;

    public void addDocument(String documentId, String text) {

        List<String> words = Arrays.asList(text.split("\\s+"));
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.size() - n; i++) {
            String gram = String.join(" ", words.subList(i, i + n));
            ngrams.add(gram);

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(documentId);
        }

        documentNgrams.put(documentId, ngrams);
    }

    public void analyzeDocument(String documentId) {

        List<String> ngrams = documentNgrams.get(documentId);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {
            Set<String> docs = ngramIndex.getOrDefault(gram, new HashSet<>());

            for (String doc : docs) {
                if (!doc.equals(documentId)) {
                    matchCount.put(doc, matchCount.getOrDefault(doc, 0) + 1);
                }
            }
        }

        for (String doc : matchCount.keySet()) {
            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Compared with " + doc);
            System.out.println("Matching n-grams: " + matches);
            System.out.println("Similarity: " + similarity + "%");

            if (similarity > 60) {
                System.out.println("PLAGIARISM DETECTED");
            } else if (similarity > 10) {
                System.out.println("Suspicious");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {

        PROBLEM4 detector = new PROBLEM4();

        String essay1 = "machine learning is a powerful tool for data analysis and pattern recognition";
        String essay2 = "machine learning is a powerful tool used for pattern recognition and analysis";
        String essay3 = "football is a popular sport played all around the world";

        detector.addDocument("essay_089.txt", essay1);
        detector.addDocument("essay_092.txt", essay2);
        detector.addDocument("essay_200.txt", essay3);

        detector.analyzeDocument("essay_092.txt");
    }
}
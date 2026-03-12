
import java.util.*;

public class problem_3 {

    class DNSEntry {
        String domain;
        String ipAddress;
        long expiryTime;

        DNSEntry(String domain, String ipAddress, long ttl) {
            this.domain = domain;
            this.ipAddress = ipAddress;
            this.expiryTime = System.currentTimeMillis() + ttl * 1000;
        }
    }

    private int capacity = 5;
    private LinkedHashMap<String, DNSEntry> cache;
    private int hits = 0;
    private int misses = 0;

    public problem_3() {
        cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > capacity;
            }
        };
    }

    public synchronized String resolve(String domain) {

        long currentTime = System.currentTimeMillis();

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (currentTime < entry.expiryTime) {
                hits++;
                return "Cache HIT → " + entry.ipAddress;
            } else {
                cache.remove(domain);
            }
        }

        misses++;
        String newIP = queryUpstreamDNS(domain);
        cache.put(domain, new DNSEntry(domain, newIP, 300));

        return "Cache MISS → Query upstream → " + newIP;
    }

    private String queryUpstreamDNS(String domain) {
        Random r = new Random();
        return "172.217.14." + (200 + r.nextInt(50));
    }

    public void cleanExpired() {
        long currentTime = System.currentTimeMillis();

        Iterator<Map.Entry<String, DNSEntry>> it = cache.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, DNSEntry> entry = it.next();
            if (currentTime > entry.getValue().expiryTime) {
                it.remove();
            }
        }
    }

    public String getCacheStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;
        return "Hit Rate: " + hitRate + "%";
    }

    public static void main(String[] args) {

        problem_3 dns = new problem_3();

        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        System.out.println(dns.resolve("google.com"));

        System.out.println(dns.getCacheStats());
    }
}
package Assignment;
/**Design and Implement LFU caching**/
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Question_4A {
    //The LFUCache class is generic and has two type parameters K and V for the key and value types respectively.
    static class LFUCache<K, V> {

        /**The class has the following fields:
         * capacity: an integer representing the maximum number of items that the cache can hold.
         * cacheMap: a Map that maps keys to cache items.
         * frequencyMap: a Map that maps frequency counters to sets of cache items that have the same frequency counter.
         * minimumFrequency: an integer representing the lowest frequency counter that has been encountered so far.**/
        private final int capacity;
        private final Map<K, CacheItem<K, V>> cacheMap;
        private final Map<Integer, Set<CacheItem<K, V>>> frequencyMap;
        private int minimumFrequency;

        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.cacheMap = new HashMap<>();
            this.frequencyMap = new HashMap<>();
            this.minimumFrequency = 0;
        }
        /**get(K key): retrieves the value associated with the specified key from the cache, or null if the key is not present.
         * If the key is present, the frequency counter for the cache item associated with the key is updated.**/
        public V get(K key) {
            if (!cacheMap.containsKey(key)) {
                return null;
            }
            CacheItem<K, V> item = cacheMap.get(key);
            updateFrequency(item);
            return item.getValue();
        }

        /**put(K key, V value): inserts a new cache item with the specified key and value into the cache. If the cache is already
         * at capacity, the least frequently used item is removed from the cache. If the key already exists in the cache,
         * the corresponding value is updated and the frequency counter for the cache item is updated.**/
        public void put(K key, V value) {
            if (capacity == 0) {
                return;
            }
            if (cacheMap.containsKey(key)) {
                CacheItem<K, V> item = cacheMap.get(key);
                item.setValue(value);
                updateFrequency(item);
            } else {
                if (cacheMap.size() >= capacity) {
                    removeLFUItem();
                }
                CacheItem<K, V> newItem = new CacheItem<>(key, value);
                cacheMap.put(key, newItem);
                minimumFrequency = 1;
                addToFrequencyMap(newItem);
            }
        }

        /**Every time a cache item is accessed, the updateFrequency method is called. It adds the item to the frequency map
         *  and changes the item's frequency counter when given a CacheItemK, V> object as an input argument. Using the
         *  getFrequency function of the CacheItem class, the procedure first retrieves the frequency counter of the item.
         *  The set of cached items with the same frequency counter is then retrieved from the frequencyMap. The setFrequency
         *  function of the CacheItem class is used to update the item's frequency counter by incrementing it by 1 and removing
         *  the current cache item from the list of items. The method then uses the addToFrequencyMap function to add the changed
         *  item to the frequency map.**/
        private void updateFrequency(CacheItem<K, V> item) {
            int frequency = item.getFrequency();
            Set<CacheItem<K, V>> items = frequencyMap.get(frequency);
            items.remove(item);
            if (items.isEmpty() && frequency == minimumFrequency) {
                minimumFrequency = frequency + 1;
            }
            item.setFrequency(frequency + 1);
            addToFrequencyMap(item);
        }


        /**The addToFrequencyMap method is called by the updateFrequency method to add the updated cache item to the frequency map.
         *  The method takes a CacheItem<K, V> object as its input argument and adds it to the set of items with the same frequency
         *  counter in the frequencyMap. If the frequency counter is not present in the map, the method creates a new empty set of
         *  cache items.**/
        private void addToFrequencyMap(CacheItem<K, V> item) {
            int frequency = item.getFrequency();
            Set<CacheItem<K, V>> items = frequencyMap.getOrDefault(frequency, new LinkedHashSet<>());
            items.add(item);
            frequencyMap.put(frequency, items);
        }
        /**When the cache is full, the put method invokes the removeLFUItem method to remove the least frequently used item.
         * The method extracts the frequencyMap's set of cached objects with the lowest frequency counter. It then uses the iterator
         * to extract the item from the set that is utilized the least frequently (). next() function. If the set is empty,
         * the method updates the minimumFrequency and removes the item from the set along with its key-value pair from the cacheMap.**/
        private void removeLFUItem() {
            Set<CacheItem<K, V>> items = frequencyMap.get(minimumFrequency);
            CacheItem<K, V> lfuItem = items.iterator().next();
            items.remove(lfuItem);
            cacheMap.remove(lfuItem.getKey());
            if (items.isEmpty()) {
                frequencyMap.remove(minimumFrequency);
            }
        }

        /**The class also has a private static inner class CacheItem<K, V> that represents a cache item. Each CacheItem has a key,
         *  a value, and a frequency counter.**/

        private static class CacheItem<K, V> {
            private final K key;
            private V value;
            private int frequency;

            CacheItem(K key, V value) {
                this.key = key;
                this.value = value;
                this.frequency = 1;
            }

            K getKey() {
                return key;
            }

            V getValue() {
                return value;
            }

            void setValue(V value) {
                this.value = value;
            }

            int getFrequency() {
                return frequency;
            }

            void setFrequency(int frequency) {
                this.frequency = frequency;
            }
        }
    }
    public static void main(String[] args) {
        // create a new cache with a capacity of 3
        LFUCache<String, Integer> cache = new LFUCache<>(3);

        // add some key-value pairs to the cache
        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);

        // retrieve a value from the cache
        Integer value1 = cache.get("key1");
        System.out.println("Value of key1: " + value1);

        // add another key-value pair to the cache
        cache.put("key4", 4);

        // retrieve the value of the removed item
        Integer value2 = cache.get("key2");
        System.out.println("Value of key2: " + value2); // should print null

        // add another key-value pair to the cache
        cache.put("key5", 5);

        // retrieve the value of the least frequently used item
        Integer value3 = cache.get("key3");
        System.out.println("Value of key3: " + value3); // should print null
    }

}
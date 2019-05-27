import java.util.ArrayList;

/** A concrete hash map implementation using separate chaining */
public class ChainHashMap<K,V> extends AbstractHashMap<K,V> {
  // a fixed capacity array of UnsortedTableMap that serve as buckets
  private UnsortedTableMap<K,V>[] table; // initialized within createTable

  /** Default */
  public ChainHashMap() {
    super();
  }

  /** Construct with given capacity */
  public ChainHashMap(int cap) {
    super(cap);
  }

  /** Constructs with given capacity and prime */
  public ChainHashMap(int cap, int p) {
    super(cap, p);
  }

  /** Creates an empty table having lengh equal to current capacity */
  protected void createTable() {
    table = (UnsortedTableMap<K,V>[]) new UnsortedTableMap[capacity];
  }

  /** Returns value associated with key k in bucket with hash value h, or else null */
  protected V bucketGet(int h, K k) {
    UnsortedTableMap<K,V> bucket = table[h];
    if (bucket == null) return null;
    return bucket.get(k);
  }

  /** Associates key k with value v in bucket with hash value h; returns old value */
  protected V bucketPut(int h, K k, V v) {
    UnsortedTableMap<K,V> bucket = table[h];
    if (bucket == null) {
      table[h] = new UnsortedTableMap<>();
      bucket = table[h];
    }
    int oldSize = bucket.size();
    V answer = bucket.put(k, v);
    n += bucket.size() - oldSize; // size may have increased
    return answer;
  }

  /** Removes entry having key from bucket with hash value h */
  protected V bucketRemove(int h, K k) {
    UnsortedTableMap<K,V> bucket = table[h];
    if (bucket == null) return null;
    int oldSize = bucket.size();
    V answer = bucket.remove(k);
    n -= (oldSize - bucket.size()); // size may have decreased
    return answer;
  }

  /** Returns an iterable collection of all key-value entries of the map */
  public Iterable<Entry<K,V>> entrySet() {
    ArrayList<Entry<K,V>> buffer = new ArrayList<>();
    for (int h = 0; h < capacity; h++) {
      if (table[h] != null) {
        for (Entry<K,V> entry : table[h].entrySet()) {
          buffer.add(entry);
        }
      }
    }
    return buffer;
  }
}
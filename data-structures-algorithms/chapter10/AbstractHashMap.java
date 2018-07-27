import java.util.Random;
import java.util.ArrayList;

/** AbstractHashMap extends AbstractMap */
public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {
  protected int n = 0; // number of entries in the directory
  protected int capacity; // length of the table
  private int prime; // prime factor
  private long scale, shift; // the shift and scaling factors

  /** Constructs with given capacity and prime */
  public AbstractHashMap(int cap, int p) {
    prime = p;
    capacity = cap;
    Random rand = new Random();
    scale = rand.nextInt(prime - 1) + 1;
    shift = rand.nextInt(prime);
    createTable();
  }

  /** COnstructs with given capacity */
  public AbstractHashMap(int cap) {
    this(cap, 109345121); // default prime
  }

  /** Constructor default */
  public AbstractHashMap() {
    this(17); // default capacity
  }

  /** Returns size */
  public int size() {
    return n;
  }

  /** Returns the value associated with the specified key */
  public V get(K key) {
    return bucketGet(hashValue(key), key);
  }

  /** Removes the entry with the specified key, and returns its value */
  public V remove(K key) {
    return bucketRemove(hashValue(key), key);
  }

  /** Associates given value with given key, replacing a previous value */
  public V put(K key, V value) {
    V answer = bucketPut(hashValue(key), key, value);
    if (n > capacity / 2) { // keep load factor <= 0.5
      resize(2 * capacity - 1); // or find a nearby prime
    }
    return answer;
  }

  /** Map key to indice from [0 -> N - 1] in Map */
  private int hashValue(K key) {
    return (int) ((Math.abs(key.hashCode() * scale + shift)) % prime) % capacity;
  }

  /** Resize new Map */
  private void resize(int newCap) {
    ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
    for (Entry<K,V> e : entrySet()) {
      buffer.add(e);
    } 
    capacity = newCap;
    createTable(); // based on updated capacity
    n = 0; // will be recomputed while reinserting entries
    for (Entry<K,V> e : buffer) {
      put(e.getKey(), e.getValue());
    }
  }

  /** Create table based on capacity */
  protected abstract void createTable();

  /** Get bucket at indice h (maybe probe next indice if use Linear Probing) and have key equal to k */
  protected abstract V bucketGet(int h, K k);

  /** Put bucket with key and value into indice h (maybe probe next indice if use Linear Probing)*/
  protected abstract V bucketPut(int h, K k, V v);

  /** Remove bucket at indice h (maybe probe next indice if use Linear Probing) and have key equal to k */
  protected abstract V bucketRemove(int h, K k);
}
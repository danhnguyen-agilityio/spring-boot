/** Concrete ProbeHashMap class that uses linear probing for collision resolution */
public class ProbeHashMap<K,V> extends AbstractHashMap<K,V> {
  private MapEntry<K,V>[] table; // a fixed array of entries
  private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null); // sentinel

  /** Default */
  public ProbeHashMap() {
    super();
  }

  /** Given capacity */
  public ProbeHashMap(int cap) {
    super(cap);
  }

  /** Given capacity and prime */
  public ProbeHashMap(int cap, int pr) {
    super(cap, p);
  }

  /** Creates an empty table having length equal to current capacity */
  protected void createTable() {
    table = (MapEntry<K,V>[]) new MapEntry[capacity]; // safe cast
  }

  /** Returns true if location either empty or the defunct sentinel */
  private boolean isAvailable(int j) {
    return table[j] == null || table[j] == DEFUNCT;
  }

  /** Returns index with key k, or -(a + 1) such that k could be added at index a */
  private int findSlot(int h, K k) {
    int avail = -1; // no slot available
    int j = h; // index while scanning table
    do {
      if (isAvailable(j)) { // may be either empty or defunct
        if (avail == -1) avail = j; // this is the first available slot
        if (table[j] == null) break; // if empty, search fails immediately
      } else if (table[j].getKey().equals(k)) {
        return j; // successful match
      }
      j = (j + 1) % capacity; // keep looking (cyclically)
    } while (j != h); // stop if we return to the start
    return - (avail + 1); // search has failed
  }

  /** Returns value associated with key k in bucket with hash value h, or else null */
  protected V bucketGet(int h, K k) {
    int j = findSlot(h, k);
    if (j < 0) return null; // no match found
    return table[j].getValue();
  }

  /** Associates key k with value v in bucket with hash value h, or else null  */
  protected V bucketPut(int h, K k, V v) {
    int j = findSlot(h, k);
    if (j >= 0) { // this key has an existing entry
      return table[j].setValue(v);
    }
    table[-(j+1)] = new MapEntry<>(k, v); // convert to proper index
    n++;
    return null;
  }

  /** Removes entry having key k from bucket with hash value h */
  protected V bucketRemove(int h, K k) {
    int j = findSlot(h, k);
    if (j < 0) return null; // nothing to remove
    V answer = table[j].getValue();
    table[j] = DEFUNCT; // mark this slot as deactivated
    n--;
    return answer;
  }
}
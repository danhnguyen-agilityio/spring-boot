import java.util.Iterator;

/** AbstractMap base class */
public abstract class AbstractMap<K,V> implements Map<K,V> {

  //------------nested MapEntry class------------------
  protected static class MapEntry<K,V> implements Entry<K,V> {
    private K k;
    private V v;

    /** Constructs with given key and value */
    public MapEntry(K key, V value) {
      k = key;
      v = value;
    }

    /** Get key */
    public K getKey() {
      return k;
    }

    /** Get value */
    public V getValue() {
      return v;
    }

    /** Set key */
    protected void setKey(K key) {
      k = key;
    }

    /** Set value */
    protected V setValue(V value) {
      V old = v;
      v = value;
      return old;
    }
  }
  //------------end of nested MapEntry class------------------

  /** Returns a boolean indicating whether Map is empty */
  public boolean isEmpty() {
    return size() == 0;
  }

  /** Class KeyIterator support for create KeyIterable */
  private class KeyIterator implements Iterator<K> {
    private Iterator<Entry<K,V>> entries = entrySet().iterator();

    /** Returns a boolean indicating  whether have next key */
    public boolean hasNext() {
      return entries.hasNext();
    }

    /** Returs next key */
    public K next() {
      return entries.next().getKey();
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /** Class KeyIterable support for public keySet method */
  private class KeyIterable implements Iterable<K> {
    public Iterator<K> iterator() {
      return new KeyIterator();
    }
  }

  /** Returns an iterable collection containing all the keys stored in M */
  public Iterable<K> keySet() {
    return new KeyIterable();
  }

  /** Class ValueIterator support for create ValueIterable */
  private class ValueIterator implements Iterator<V> {
    private Iterator<Entry<K,V>> entries = entrySet().iterator();

    /** Returns a boolean indicating  whether have next value */
    public boolean hasNext() {
      return entries.hasNext();
    }

    /** Returns next value */
    public V next() {
      return entries.next().getValue();
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /** Class ValueIterable support for public values method */
  private class ValueIterable implements Iterable<V> {
    public Iterator<V> iterator() {
      return new ValueIterator();
    }
  }

  /** Returns an iterable collection containing all the keys stored in M */
  public Iterable<V> values() {
    return new ValueIterable();
  }
}
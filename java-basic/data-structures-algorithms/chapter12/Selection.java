import java.util.List;
import java.util.ArrayList;

/** Selection demo */
public class Selection {
  /** Select smallest element th k from S, k from 1 to n */
  public static Integer quickSelect(List<Integer> S, int k) {
    int n = S.size();
    if (n == 1) {
      S.get(0);
    }
    int pivot = S.get(n - 1);
    List<Integer> L = new ArrayList<>(); 
    List<Integer> E = new ArrayList<>(); 
    List<Integer> G = new ArrayList<>();
    for (Integer x : S) {
      if (x < pivot) {
        L.add(x);
      } else if (x == pivot) {
        E.add(x);
      } else {
        G.add(x);
      }
    }
    if (k <= L.size()) {
      return quickSelect(L, k);
    } else if (k <= L.size() + E.size()) {
      return E.get(0);
    } else {
      return quickSelect(G, k - L.size() - E.size());
    }
  }

  public static void main(String args[]) {
    List<Integer> S = new ArrayList<>();
    S.add(6);
    S.add(5);
    S.add(2);
    S.add(1);
    S.add(3);
    S.add(4);
    S.add(7);
    S.add(8);
    System.out.println(quickSelect(S, 7));
  }
}
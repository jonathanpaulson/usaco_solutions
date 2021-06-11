import java.util.*;
import java.io.*;

public class race {
  // S(n) = 1+2+...+n = n*(n+1)/2
  static long S(long n) {
    return n*(n+1)/2;
  }
  // A+(A+1)+(A+2)+...+B = \sum_{i=A}^B i
  static long sum(long A, long B) {
    return A<=B ? S(B)-S(A-1) : 0;
  }

  // Given that we start at speed S, can we slow down to speed X in at most L meters?
  // How many meters does it take to stop given current speed is S and target is X?
  // (S-1) + (S-2) + ... + X meters
  // We are allowed to go over, so as long as we have (S-1)+(S-2)+...+(X+1)+1 m we are OK.
  static boolean ok(long S, long X, long L) {
    if(S <= X) { return true; }
    long need = sum(X+1, S-1)+1;
    return need <= L;
  }
  static long solve(long k, long x) {
    // Greedy. Go as fast as you can but make sure you can stop in time.
    long speed = 0;
    long left = k;
    long t = 0;
    while(left>0) {
      if(ok(speed+1, x, left-(speed+1))) {
        // speed up if we can
        speed += 1;
      } else if(ok(speed, x, left-speed)) {
        // otherwise keep the same speed if we can
      } else {
        // slow down if we have to
        // check that we have enough space left to stop in time.
        assert(ok(speed-1, x, left-(speed-1)));
        speed-=1;
      }
      assert(speed >= 1);
      left -= speed;
      t++;
    }
    return t;
  }
  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new FileReader("race.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("race.out")));
    String[] nk = in.readLine().split(" ");
    long k = Long.parseLong(nk[0]);
    int n = Integer.parseInt(nk[1]);
    for(int i=0; i<n; i++) {
      long x = Long.parseLong(in.readLine());
      out.println(solve(k,x));
      out.flush();
    }
  }
}

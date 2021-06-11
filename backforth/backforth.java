import java.util.*;
import java.io.*;

public class backforth {
  // The possible amounts that could be left in the tank at the first barn
  static HashSet<Integer> possible = new HashSet<Integer>();
  static void solve(int trips_left, ArrayList<Integer> buckets_here, ArrayList<Integer> buckets_there, int tank_here, int tank_there) {
    if(trips_left == 0) {
      // We completed all the trips, so the amount currently in the tank at the first barn is possible
      // We started at the first barn and made four trips, so we also end at the first barn,
      // so @tank_here represents the amount in the tank at the first barn
      possible.add(tank_here);
    } else {
      for(int taken_idx=0; taken_idx < buckets_here.size(); taken_idx++) {
        int bucket = buckets_here.get(taken_idx);
        // Leave the taken bucket at our destination
        ArrayList<Integer> new_buckets_dest = new ArrayList<Integer>(buckets_there);
        new_buckets_dest.add(bucket);
        // Remove the taken bucket from our current barn
        ArrayList<Integer> new_buckets_src = new ArrayList<Integer>();
        for(int i=0; i<buckets_here.size(); i++) {
          if(i != taken_idx) {
            new_buckets_src.add(buckets_here.get(i));
          }
        }
        // Move @bucket gallons of milk from the current tank to the other tank
        int new_tank_dest = tank_there + bucket;
        int new_tank_src = tank_here - bucket;
        // Since we went to the other barn, "here" now refers to the "dest" barn and
        // "there" now refers to the "src" barn
        solve(trips_left-1, new_buckets_dest, new_buckets_src, new_tank_dest, new_tank_src);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new FileReader("backforth.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("backforth.out")));
    String[] first_barn = in.readLine().split(" ");
    String[] second_barn = in.readLine().split(" ");
    ArrayList<Integer> buckets_here = new ArrayList<Integer>();
    for(int i=0; i<10; i++) {
      buckets_here.add(Integer.parseInt(first_barn[i]));
    }
    ArrayList<Integer> buckets_there = new ArrayList<Integer>();
    for(int i=0; i<10; i++) {
      buckets_there.add(Integer.parseInt(second_barn[i]));
    }
    solve(4, buckets_here, buckets_there, 1000, 1000);
    out.println(possible.size());
    out.flush();
  }
}

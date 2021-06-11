#include <iostream>
#include <set>
#include <cstdio>
#include <vector>
#include <cassert>
using namespace std;
using ll = int64_t;

// The possible amounts that could be left in the tank at the first barn
set<ll> possible;
void solve(ll trips_left, const vector<ll>& buckets_here, const vector<ll>& buckets_there, ll tank_here, ll tank_there) {
  if(trips_left == 0) {
    // We completed all the trips, so the amount currently in the tank at the first barn is possible
    // We started at the first barn and made four trips, so we also end at the first barn,
    // so @tank_here represents the amount in the tank at the first barn
    possible.insert(tank_here);
  } else {
    for(ll taken_idx=0; taken_idx < buckets_here.size(); taken_idx++) {
      ll bucket = buckets_here[taken_idx];
      // Leave the taken bucket at our destination
      vector<ll> new_buckets_dest(buckets_there);
      new_buckets_dest.push_back(bucket);
      // Remove the taken bucket from our current barn
      vector<ll> new_buckets_src;
      for(ll i=0; i<buckets_here.size(); i++) {
        if(i != taken_idx) {
          new_buckets_src.push_back(buckets_here[i]);
        }
      }
      // Move @bucket gallons of milk from the current tank to the other tank
      ll new_tank_dest = tank_there + bucket;
      ll new_tank_src = tank_here - bucket;
      // Since we went to the other barn, "here" now refers to the "dest" barn and
      // "there" now refers to the "src" barn
      solve(trips_left-1, new_buckets_dest, new_buckets_src, new_tank_dest, new_tank_src);
    }
  }
}

int main() {
  assert(freopen("backforth.in", "r", stdin) != nullptr);
  assert(freopen("backforth.out", "w", stdout) != nullptr);
  vector<ll> buckets_here;
  for(ll i=0; i<10; i++) {
    ll bucket;
    cin >> bucket;
    buckets_here.push_back(bucket);
  }
  vector<ll> buckets_there;
  for(ll i=0; i<10; i++) {
    ll bucket;
    cin >> bucket;
    buckets_there.push_back(bucket);
  }
  solve(4, buckets_here, buckets_there, 1000, 1000);
  cout << possible.size() << endl;
}

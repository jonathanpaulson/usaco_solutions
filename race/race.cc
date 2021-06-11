#include <iostream>
#include <vector>
#include <cassert>
#include <cstdio>
using namespace std;
using ll = long long;

// S(n) = 1+2+...+n = n*(n+1)/2
ll S(ll n) {
  return n*(n+1)/2;
}
// A+(A+1)+(A+2)+...+B = \sum_{i=A}^B i
ll sum(ll A, ll B) {
  return A<=B ? S(B)-S(A-1) : 0;
  //return A<=B ? (A+B)*(B-A+1)/2 : 0;
}
// Given that we start at speed S, can we slow down to speed X in at most L meters?
// How many meters does it take to stop given current speed is S and target is X?
// (S-1) + (S-2) + ... + X meters
// We are allowed to go over, so as long as we have (S-1)+(S-2)+...+(X+1)+1 m we are OK.
bool ok(ll S, ll X, ll L) {
  if(S <= X) { return true; }
  ll need = sum(X+1, S-1)+1;
  bool ans = (need <= L);
  return ans;
}
ll solve(ll k, ll x) {
  // Greedy. Go as fast as you can but make sure you can stop in time.
  ll speed = 0;
  ll left = k;
  ll t = 0;
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
int main() {
  assert(freopen("race.in", "r", stdin) != nullptr);
  assert(freopen("race.out", "w", stdout) != nullptr);
  ll k,n;
  cin >> k >> n;
  vector<ll> X(n, 0);
  for(ll i=0; i<n; i++) {
    cin >> X[i];
  }
  for(ll i=0; i<n; i++) {
    cout << solve(k, X[i]) << endl;
  }
}

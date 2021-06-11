# S(n) = 1+2+...+n = n*(n+1)/2
def S(n: int) -> int:
    return n*(n+1)//2

# A+(A+1)+(A+2)+...+B = \sum_{i=A}^B i
def sum_(A: int, B: int) -> int:
    return S(B)-S(A-1) if A<=B else 0

# Given that we start at speed S, can we slow down to speed X in at most L meters?
# How many meters does it take to stop given current speed is S and target is X?
# (S-1) + (S-2) + ... + X meters
# We are allowed to go over, so as long as we have (S-1)+(S-2)+...+(X+1)+1 m we are OK.
def ok(S: int, X: int, L: int) -> bool:
  if S <= X:
      return True
  need = sum_(X+1, S-1)+1
  return need <= L

line1,*rest = open("race.in").readlines()
k,n = [int(v) for v in line1.split()]
X = []
for line in rest:
    X.append(int(line))

def solve(k, x):
    # Greedy. Go as fast as you can but make sure you can stop in time.
    speed = 0
    left = k
    t = 0
    while left > 0:
        if ok(speed+1, x, left-(speed+1)):
            # speed up if we can
            speed += 1
        elif ok(speed, x, left-speed):
            # otherwise keep the same speed if we can
            pass
        else:
            # slow down if we have to
            # check that we have enough space left to stop in time.
            assert ok(speed-1, x, left-(speed-1))
            speed -= 1
        assert speed >= 1
        left -= speed
        t += 1
    return t

with open('race.out', 'w') as fout:
    for x in X:
        print(solve(k,x), file=fout)

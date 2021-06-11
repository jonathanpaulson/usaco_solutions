# The possible amounts that could be left in the tank at the first barn
possible = set()
def solve(trips_left, buckets_here, buckets_there, tank_here, tank_there):
    if trips_left == 0:
        # We completed all the trips, so the amount currently in the tank at the first barn is possible
        # We started at the first barn and made four trips, so we also end at the first barn,
        # so @tank_here represents the amount in the tank at the first barn
        possible.add(tank_here)
    else:
        for taken_idx,bucket in enumerate(buckets_here):
            # Leave the taken bucket at our destination
            new_buckets_dest = buckets_there + [bucket]
            # Remove the taken bucket from our current barn
            new_buckets_src = [b for i,b in enumerate(buckets_here) if i!=taken_idx]
            # Move @bucket gallons of milk from the current tank to the other tank
            new_tank_dest = tank_there + bucket
            new_tank_src = tank_here - bucket

            # Since we went to the other barn, "here" now refers to the "dest" barn and
            # "there" now refers to the "src" barn
            solve(trips_left-1, new_buckets_dest, new_buckets_src, new_tank_dest, new_tank_src)

first_barn, second_barn = open('backforth.in').readlines()
buckets_here = [int(x) for x in first_barn.split()]
buckets_there = [int(x) for x in second_barn.split()]
# We end up taking 4 trips back and forth
# Each tank starts with 1000 gallons of milk
# It is impossible to ever run out of milk in either tank,
#  since we only take 4 trips and each bucket holds at most 100 gallons
solve(4, buckets_here, buckets_there, 1000, 1000)
with open('backforth.out', 'w') as fout:
    print(len(possible), file=fout)

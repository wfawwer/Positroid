# Positroid

NOTE: THIS IS SUPER OUTDATED: SEE https://arxiv.org/abs/1701.08483 FOR A MUCH FASTER ALGORITHM

Some code created for research work over 2016 and 2017 summers. positroid.java checks the concordancy of two positroids, with input format of the form 

n k

permutation

i.e

6 2

513462

6 3

514362

where n represents the ground set, k represents the rank and permutation the decorated permutation of the positroid. permuter.java takes as input a single positroid and returns all positroids concordant to it.


Note: this code is pretty sloppy. This is partially due to the fact that the runtime of this is somewhere on the order of 
O(n!n!n^2 logn), due to the large amount of structure that needs to be checked in a positroid. So, I ended up just copying over some code used for progamming competition, and as a result we end up passing around permutations as strings and must constantly convert from string to array and back. 

WHY IS THIS OUTDATED?

It is a known result in the field that M is concordant to M' if every flat of M is contained in a flat of M'. As per https://arxiv.org/abs/1701.08483 we give a criteria for the flats of a positroid. Then, a positroid given by permutation A is concordant (<) to a positroid given by permutation B iff every interval [x, B^{-1}(x)] is covered by intervals of the form 
[x, A^{-1}(x)] and so we can check concordancy in n^2 time. This is a very happy result. 

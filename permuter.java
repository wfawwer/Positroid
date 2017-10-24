import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class permuter {

	public static void main(String[] args) throws IOException {

		BufferedReader f = new BufferedReader(new FileReader("positroid.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("positroid.out")));
		StringTokenizer st = new StringTokenizer(f.readLine());
		// N1 the number of elements [n] we are working with
		int N1 = Integer.parseInt(st.nextToken());
		// K1 the rank of the  first positroid
		int K1 = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(f.readLine());
		// P1 = assoc. permutation of positroid
		int P1 = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(f.readLine());
		ArrayList<Integer> testorderings = new ArrayList<Integer>();


		int[] permutation1 = new int[N1];
		for(int i=permutation1.length-1; i>=0;i--){
			permutation1[i]=P1%10;
			P1=P1/10;
		}

		int[][] flags1 = new int[N1][K1];
		induceNecklace(flags1, permutation1);
		// stores the grassmann necklace of permutation1 in flags1
		for(int i=0; i<N1;i++){
			for(int j=0; j<K1;j++){
				System.out.print(flags1[i][j]);
			}
			System.out.println();
		}
		// will store permutations in A1
		int[] A1 = new int[N1];
		for(int i=1; i<=N1;i++){
			A1[i-1]=i;
		}
		boolean[] used1 = new boolean[N1];
		ArrayList<Integer> bases1 = new ArrayList<Integer>();
		enumerate(A1,K1,0,0,used1,flags1,bases1);
		System.out.println();
		System.out.println();
		for(int i=0; i<bases1.size();i++){
			System.out.println(bases1.get(i));
		}
		generate(N1,A1,testorderings);
		for(int z=0; z<testorderings.size();z++){
			int P2 = testorderings.get(z);
			int N2 = N1;
			int[] permutation2 = new int[N2];

			for(int i=permutation2.length-1; i>=0;i--){
				permutation2[i]=P2%10;
				P2=P2/10;
			}
			int K2 = 0;
			for(int i =0; i<permutation2.length;i++){
				if(permutation2[i]<i+1){
					K2++;
				}

			}

			int[][] flags2 = new int[N2][K2];
			induceNecklace(flags2, permutation2);
			//we can enumerate to make sets of positroid//
			//then for everything generated
			// will store permutations in A2
			int[] A2 = new int[N2];
			for(int i=1; i<=N2;i++){
				A2[i-1]=i;
			}

			boolean[] used2 = new boolean[N2];

			ArrayList<Integer> bases2 = new ArrayList<Integer>();
			enumerate(A2,K2,0,0,used2,flags2,bases2); 
			ArrayList<Integer> orderings = new ArrayList<Integer>();
			generate(N1,A1,orderings);
			boolean works = true;
			// for every possible Gale Ordering on [n]
			for(int i=0; i<orderings.size();i++){
				int comp = orderings.get(i);
				ArrayList<Integer> compform = new ArrayList<Integer>();
				while(comp!=0){
					compform.add(0,comp%10);
					comp=comp/10;
				} 
				// determine lexicographic order amongst bases 
				final ArrayList<Integer> fixedcompform = compform; 
				
				int small1 = 0;
				int small2 = 0;
				// finds minimal element under the orderings 
				try{
				small1 = Collections.min(bases1, new Comparator<Integer>(){
					@Override
					public int compare(Integer o1, Integer o2) {
						ArrayList<Integer> o1form = new ArrayList<Integer>();
						ArrayList<Integer> o2form = new ArrayList<Integer>();

						while(o1!=0){
							o1form.add(o1%10);
							o1=o1/10;
						}

						while(o2!=0){
							o2form.add(o2%10);
							o2=o2/10;
						}

						for(int j=o1form.size()-1; j>=0;j--){
							for(int k=0; k<fixedcompform.size();k++){
								if(fixedcompform.get(k)==o1form.get(j)){
									o1form.set(j, k+1);
									break;
								}
							}

						}
						int[] o1sortable = new int[o1form.size()];
						for(int j=0; j<o1form.size();j++){
							o1sortable[j]=o1form.get(j);
						}
						Arrays.sort(o1sortable);
						for(int j=o2form.size()-1; j>=0;j--){
							for(int k=0; k<fixedcompform.size();k++){
								if(fixedcompform.get(k)==o2form.get(j)){
									o2form.set(j, k+1);
									break;
								}
							}
						}
						int[] o2sortable = new int[o2form.size()];
						for(int j=0; j<o2form.size();j++){
							o2sortable[j]=o2form.get(j);
						}
						Arrays.sort(o2sortable);
						int starto1 = 0;
						for(int j=0; j<o1sortable.length;j++){
							starto1+=o1sortable[j];
							starto1*=10;
						}
						starto1=starto1/10;
						int starto2 = 0;
						for(int j=0; j<o2sortable.length;j++){
							starto2+=o2sortable[j];
							starto2*=10;
						}
						starto2=starto2/10;

						return starto1-starto2;


					}



				});
				
				} catch (NoSuchElementException e) {
					small1 = -1;
				}
				try{
				small2 = Collections.min(bases2, new Comparator<Integer>(){
					@Override
					public int compare(Integer o1, Integer o2) {
						ArrayList<Integer> o1form = new ArrayList<Integer>();
						ArrayList<Integer> o2form = new ArrayList<Integer>();

						while(o1!=0){
							o1form.add(o1%10);
							o1=o1/10;
						}

						while(o2!=0){
							o2form.add(o2%10);
							o2=o2/10;
						}
						//smeared o1 and o2 acros the arrays
						for(int j=o1form.size()-1; j>=0;j--){
							for(int k=0; k<fixedcompform.size();k++){
								if(fixedcompform.get(k)==o1form.get(j)){
									o1form.set(j, k+1);
									break;
								}
							}

						}
						int[] o1sortable = new int[o1form.size()];
						for(int j=0; j<o1form.size();j++){
							o1sortable[j]=o1form.get(j);
						}
						Arrays.sort(o1sortable);
						for(int j=o2form.size()-1; j>=0;j--){
							for(int k=0; k<fixedcompform.size();k++){
								if(fixedcompform.get(k)==o2form.get(j)){
									o2form.set(j, k+1);
									break;
								}
							}
						}
						int[] o2sortable = new int[o2form.size()];
						for(int j=0; j<o2form.size();j++){
							o2sortable[j]=o2form.get(j);
						}
						Arrays.sort(o2sortable);
						//substitute and rearrrange into the lexicographic minimums//
						int starto1 = 0;
						for(int j=0; j<o1sortable.length;j++){
							starto1+=o1sortable[j];
							starto1*=10;
						}
						starto1=starto1/10;
						int starto2 = 0;
						for(int j=0; j<o2sortable.length;j++){
							starto2+=o2sortable[j];
							starto2*=10;
						}
						starto2=starto2/10;

						return starto1-starto2;


					}



				});
				} catch (NoSuchElementException e) {
					small2 = -1;
				}
				
				// check to make sure the concordancy criteria is in right direction
				if(K1<K2){
					if(!isConcordant(small1, small2)){
						works = false;
						break;
					}
				}
				if(K2<K1){
					if(small1 != -1 && small2 != -1){
						if(!isConcordant(small2, small1)){
							works = false;
							break;
						}
					}
				}

			}
			if(works==false && Math.abs(K2-K1)==1){
				for(int j=0; j<permutation2.length;j++){
					System.out.print(permutation2[j]);
				}
				System.out.println();
			}
			else{
				if(Math.abs(K2-K1)==1){
					System.out.print("              ");
					for(int j=0; j<permutation2.length;j++){
						System.out.print(permutation2[j]);
					}
					System.out.println();
				}
			}
		} 


	}
	public static void test(int[] A, int[][] flags, int N, ArrayList<Integer> bases){
		// checks to see if the bases are valid
		boolean works = true;
		for(int i=0; i<flags.length;i++){
			//orering i given by i+1, then i++ subtracting N if go over//
			int marker = 0;
			for(int j=0; j<A.length;j++){
				if(A[j]>=i+1){
					marker = j;
					break;
				}
			}
			for(int j=0; j<flags[i].length;j++){
				//recalibrate flags[i][j] and A[marker] under the permutation ordering//
				//we know min is i//
				int flag = (flags[i][j]-i-1+N)%N+1;
				int mark = (A[marker]-i-1+N)%N+1;
				if(flag>mark){ //not under >1, but under the relevant  gale orderijng
					works = false;
				}
				marker++;	
				if(marker>=A.length){
					marker = marker-A.length;	
				}
			}	
		}
		// if successfull, add it to list of bases
		if(works){
			int start = 0;
			for(int i=0; i<A.length;i++){
				start+=A[i];
				start=start*10;
			}
			start=start/10;
			bases.add(start);
		}
	}
	public static void enumerate(int[] A, int k, int start, int currLen, boolean[] used, int[][] flags,ArrayList<Integer> bases){
		// fills bases with all the valid bases of the positroid
		if(currLen==k){
			int[] test = new int[k];
			int counter = 0;
			for(int i=0; i<A.length;i++){
				if(used[i]==true){
					test[counter]=A[i];
					counter++;		
				}
			}	
			// tests to see if this is a basis
			test(test,flags,A.length,bases);
			return;
		}
		if(start == A.length){
			return;
		}	
		// recurse on whether we utilized used[start] in the basis
		used[start]=true;
		enumerate(A,k,start+1,currLen+1,used, flags,bases);
		used[start]=false;
		enumerate(A,k,start+1,currLen,used, flags,bases);
	}
	public static void generate(int N, int[] A, ArrayList<Integer> orderings){
		// generates all N-sets of the int array A and stores them in orderings
		if(N==1){
			// we are done recurring, place contents of A into ordering
			int start = 0;
			for(int i=0; i<A.length;i++){
				start+=A[i];
				start=start*10;
			}
			start=start/10;
			orderings.add(start);
		}
		else{
			for(int i=0; i<N-1;i++){
				generate(N-1,A, orderings);
				if(N%2==0){
					int temp = A[i];
					A[i]=A[N-1];
					A[N-1]=temp;
				}
				else{
					int temp = A[0];
					A[0]=A[N-1];
					A[N-1]=temp;
				}

			}
			generate(N-1,A,orderings);

		}

	}

	public static void induceNecklace(int[][] flags, int[] permutation){
		// constructs the grassmann necklace of a permutation and stores it in flags
		ArrayList<Integer> afterloc = new ArrayList<Integer>();
		
		// constructs G_1
		for(int i=0; i<permutation.length;i++){
			if(permutation[i]<i+1){
				afterloc.add(permutation[i]);
			}
		}

		Collections.sort(afterloc);
		for(int i=0; i<flags.length;i++){
			// Applies permutation operation to G_i to get G_{i+1}
			for(int j=0; j<flags[i].length;j++){

				flags[i][j]=afterloc.get(j);
			}
			int remover = i+1;
			int marker = 0;
			boolean switched = false;
			for(int j=0; j<afterloc.size();j++){
				if(afterloc.get(j)==remover){
					marker = j;
					switched = true;
				}
			}
			if(switched)
				afterloc.set(marker, permutation[i]);
			final int start = i;
			final int N = flags.length;

			// Renormalizes G_k to lexicographic minimal form
			Collections.sort(afterloc,new Comparator<Integer>(){
				@Override
				public int compare(Integer o1, Integer o2) {
					int temp1 = (o1-start-1+N)%N+1;
					int temp2 = (o2-start-1+N)%N+1;
					return temp1-temp2;
				}
			});

		}

	}
	public static boolean isConcordant(int o1, int o2){
		// tests to see if two bases represented by ints are concordant
		boolean isConcordant = true;
		
		// gets length of integers
		String firststring = Integer.toString(o1);
		String secondstring = Integer.toString(o2);
		int[] firstarr = new int[firststring.length()];
		int[] secondarr = new int[secondstring.length()];
		
		// stores them in arrays
		for(int i=0; i<firstarr.length;i++){
			firstarr[i]=o1%10;
			o1 = o1/10;
		}
		for(int i=0; i<secondarr.length;i++){
			secondarr[i]= o2%10;
			o2 = o2/10;
		}
		
		// then loops through and checks concordancy criteria on individual digits
		for(int j=0; j<firstarr.length;j++){
			boolean ispresent = false;
			for(int k=0; k<secondarr.length;k++){
				if(secondarr[k]==firstarr[j]){
					ispresent = true;
					break;
				}
			}
			if(ispresent == false){
				isConcordant = false;
				break;
			}
		}
		return isConcordant;
	}

	

}


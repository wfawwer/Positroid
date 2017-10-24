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
import java.util.StringTokenizer;

public class positroid {

	public static void main(String[] args) throws IOException {

		 BufferedReader f = new BufferedReader(new FileReader("positroid.in"));
		 PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("positroid.out")));
		 StringTokenizer st = new StringTokenizer(f.readLine());
		 // N1 K1 P1 are [n], rank of positroid and permutation of positroid resp.
		 int N1 = Integer.parseInt(st.nextToken());
		 int K1 = Integer.parseInt(st.nextToken());
		 st = new StringTokenizer(f.readLine());
		 int P1 = Integer.parseInt(st.nextToken());
		 st = new StringTokenizer(f.readLine());
		 // same for N2, K2, P2
		 int N2 = Integer.parseInt(st.nextToken());
		 int K2 = Integer.parseInt(st.nextToken());
		 st = new StringTokenizer(f.readLine());
		 int P2 = Integer.parseInt(st.nextToken());
		 int[][] flags1 = new int[N1][K1];
		 int[][] flags2 = new int[N2][K2];
	
		 int[] permutation1 = new int[N1];
		 int[] permutation2 = new int[N2];
		 for(int i=permutation1.length-1; i>=0;i--){
			 permutation1[i]=P1%10;
			 P1=P1/10;
		 }
		 for(int i=permutation2.length-1; i>=0;i--){
			 permutation2[i]=P2%10;
			 P2=P2/10;
		 }
		 
		 
		 induceNecklace(flags1, permutation1);
		 induceNecklace(flags2, permutation2);
		 //we can enumerate to make sets of positriod//
		 //then for everything generated
		 int[] A1 = new int[N1];
		 for(int i=1; i<=N1;i++){
			 A1[i-1]=i;
		 }
		 int[] A2 = new int[N2];
		 for(int i=1; i<=N2;i++){
			 A2[i-1]=i;
		 }
		 boolean[] used1 = new boolean[N1];
		 boolean[] used2 = new boolean[N2];
		 ArrayList<Integer> bases1 = new ArrayList<Integer>();
		 ArrayList<Integer> bases2 = new ArrayList<Integer>();
		
		 enumerate(A1,K1,0,0,used1,flags1,bases1);
		 enumerate(A2,K2,0,0,used2,flags2,bases2);
		 Integer[] bases1a = new Integer[bases1.size()]; 
		 Integer[] bases2a = new Integer[bases2.size()];
		 
		 for(int i=0; i<bases1.size();i++){
			 bases1a[i]=bases1.get(i);
			System.out.println(bases1a[i]);
		 }
		 System.out.println("afafa");
		 System.out.println();
		 for(int i=0; i<bases2.size();i++){
			 bases2a[i]=bases2.get(i);
			 //System.out.println(bases2a[i]);
		 }
		 
		 ArrayList<Integer> orderings = new ArrayList<Integer>();
		 generate(N1,A1,orderings);
		 for(int i=0; i<orderings.size();i++){
			 int comp = orderings.get(i);
			 ArrayList<Integer> compform = new ArrayList<Integer>();
			 while(comp!=0){
					compform.add(0,comp%10);
					comp=comp/10;
				} 
			
			 final ArrayList<Integer> fixedcompform = compform; 
			 Arrays.sort(bases1a, new Comparator<Integer>(){
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
			 
			 Arrays.sort(bases2a, new Comparator<Integer>(){
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
			 
			 if(!isConcordant(bases1a[0],bases2a[0])){
				 
				 System.out.println(bases1a[0]+" "+bases2a[0]);
				 for(int j=0; j<compform.size();j++){
					 System.out.print(compform.get(j)+" ");
				 }
				 System.out.println();
				 System.out.println();
				 
			 }
			
			 
			 
			 
		 }
		 
		 
		 
		 
	}
	public static void test(int[] A, int[][] flags, int N, ArrayList<Integer> bases){
		// checks to see if A is a basis against flags on [n]- if so, add it to bases list
		boolean hugeWorks = true;
		int start = 0;
		for(int i=0; i<A.length;i++){
			start+=A[i];
			start=start*10;
			
		}
		start=start/10;
		System.out.println(start);
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
				int flog = (flags[i][j]-i-1+N)%N+1;
				int mark = (A[marker]-i-1+N)%N+1;
				if(flog>mark){ //not under >1, but under the relevant  gale orderijng
					hugeWorks = false;
				}
				marker++;	
				if(marker>=A.length){
					marker = marker-A.length;	
				}
			}	
		}
		if(hugeWorks){
			
		bases.add(start);
		}
	}
	public static void enumerate(int[] A, int k, int start, int currLen, boolean[] used, int[][] flags, ArrayList<Integer> bases){
		if(currLen==k){
			int[] test = new int[k];
			int counter = 0;
			for(int i=0; i<A.length;i++){
				if(used[i]==true){
					test[counter]=A[i];
					counter++;		
				}
			}	
			test(test,flags,A.length,bases);
			return;
		}
		if(start == A.length){
			return;
		}	
		used[start]=true;
		enumerate(A,k,start+1,currLen+1,used, flags,bases);
		used[start]=false;
		enumerate(A,k,start+1,currLen,used, flags,bases);
	}
	public static void generate(int N, int[] A, ArrayList<Integer> orderings){
		// generates all N-subsets of A and stores them in orderings
		if(N==1){
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
		for(int i=0; i<permutation.length;i++){
			if(permutation[i]<i+1){
				afterloc.add(permutation[i]);
			}
		}

		Collections.sort(afterloc);
		for(int i=0; i<flags.length;i++){
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
		boolean isConcordant = true;
		 String firststring = Integer.toString(o1);
		 String secondstring = Integer.toString(o2);
		 int[] firstarr = new int[firststring.length()];
		 int[] secondarr = new int[secondstring.length()];
		 
		 for(int i=0; i<firstarr.length;i++){
			 firstarr[i]=o1%10;
			 o1 = o1/10;
		 }
		 for(int i=0; i<secondarr.length;i++){
			 secondarr[i]= o2%10;
			 o2 = o2/10;
		 }
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

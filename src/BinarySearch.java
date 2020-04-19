
public class BinarySearch {
	
	public static boolean solution(int low, int high,int[] a, int target) {
		if(low <= high) {
			int mid = (low+high)/2;
			if(a[mid] == target) {
				return true;
			}
			if(target < a[mid])
				return solution(low, mid-1, a, target);
			else
				return solution(mid+1,high,a,target);
		}
		return false;
	}

	public static void main(String[] args) {
		int[] a = new int[] {1,2,3,4,5};
		for(int i:a) {
			int target = i;
			boolean result = solution(0, a.length-1, a, target);
			if(result) {
				System.out.println("found");
			}
			else {
				System.out.println("not found");
			}
		}

	}

}

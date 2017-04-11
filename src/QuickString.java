import java.util.List;

public class QuickString {
	
	ObjectDef<Integer> objectDef;
	Integer freeRunInterval;
	

	QuickString() {
		objectDef = new ObjectDef<Integer>();
		
	}

	

	

	/**
	 * 
	 * @param objectDef
	 *            objectDef which contains ArrayList to be sorted
	 */
	public List<String> quickSort(List<Integer> nums) {
		objectDef.setObjectLst(nums);
		recursiveQuickSort(objectDef, 0, nums.size() - 1);
		return objectDef.getSortingActionLst();
	}

	/**
	 * Recursive logic for quicksort
	 *
	 * @param objectDef
	 *            objectDef which contains ArrayList
	 * @param startIndex
	 *            start index of the ArrayList
	 * @param endIndex
	 *            end index of the ArrayList
	 */
	public static void recursiveQuickSort(ObjectDef<Integer> objectDef, Integer startIndex, Integer endIndex) {

		Integer index = partition(objectDef, startIndex, endIndex);

		// Call quicksort recursively with left part of the partitioned ArrayList
		if (startIndex < index - 1) {
			recursiveQuickSort(objectDef, startIndex, index - 1);
		}

		// Call quicksort recursively with right part of the partitioned ArrayList
		if (endIndex > index) {
			recursiveQuickSort(objectDef, index, endIndex);
		}
	}

	/**
	 * Divides ArrayList from pivot, left side contains elements less than Pivot
	 * while right side contains elements greater than pivot.
	 *
	 * @param objectDef
	 *            objectDef which contains ArrayList to partitioned
	 * @param left
	 *            lower bound of the ArrayList
	 * @param right
	 *            upper bound of the ArrayList
	 * @return the partition index
	 */
	public static Integer partition(ObjectDef<Integer> objectDef, Integer left, Integer right) {

		Integer pivotInt = left;

		while (left <= right) {

			// searching number which is greater than pivot, bottom up
			// call compare method to compare elements
			while (objectDef.Compare(left, pivotInt) < 0) {
				left++;
			}

			// searching number which is less than pivot, top down
			// call compare method to compare elements
			while (objectDef.Compare(right, pivotInt) > 0) {
				right--;
			}

			// swap the values
			if (left <= right) {

				// call swap method to swap elements
				objectDef.Swap(left, right);

				// increment left index and decrement right index
				left++;
				right--;
			}
		}
		return left;
	}

	private static void programFailure(String failureMsg) {
		System.out.println(">> Program failed because of following reason >>");
		System.out.println(failureMsg);
		System.exit(1);
	}
	
	
	public List<String> doQuickSort(List<Integer> nums) {
		objectDef.setObjectLst(nums);
		//recursiveQuickSort(objectDef, 0, nums.size() - 1);		
		quickSort1(objectDef,0, nums.size() - 1);
		return objectDef.getSortingActionLst();
	}
	
	// thank you, Wikipedia!
	public void quickSort1(ObjectDef<Integer> objectDef,int low, int high) {
		if( low >= high )
			return;
		
		// partition
		int pivot = high;
		int i = low;
		for( int j = low; j < high; j++ ) {
			if( objectDef.Compare(j, pivot) == 1 ) {
				objectDef.Swap(i, j);
				i++;
			}
		}
		objectDef.Swap(i, high);

		// sort the pieces
		quickSort1(objectDef,low, i-1);
		quickSort1(objectDef,i+1, high);
	}

	public ObjectDef<Integer> getObjectDef() {
		return objectDef;
	}

	public void setObjectDef(ObjectDef<Integer> objectDef) {
		this.objectDef = objectDef;
	}	
	

}
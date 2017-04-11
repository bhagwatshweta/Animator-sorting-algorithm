import java.util.List;

public class BubbleInt {

	ObjectDef<Integer> objectDef;
	Integer freeRunInterval;
	

	BubbleInt() {
		objectDef = new ObjectDef<Integer>();
		
	}



	

	List<String> bubbleSort(List<Integer> nums) {

		/*
		 * In bubble sort, we traverse the ArrayList from first to
		 * ArrayList.size() - 1 position and compare the element with the next
		 * element. Element is swapped with the next one if the next one is
		 * greater.
		 * 
		 */

		objectDef.setFreeRunInterval(freeRunInterval);

		/*
		 * Thread shortThread = new Thread() { public void run() {
		 */
		int n = nums.size();
		// objectDef = new ObjectDef<Integer>();
		objectDef.setObjectLst(nums);

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {

				// call compare method to compare elements
				if (objectDef.Compare(j - 1, j) > 0) {

					// call swap method to swap elements
					objectDef.Swap(j - 1, j);

				}

			}
		}

		/*for (PostSortListner psl : postSortListeners)
			psl.calledPostSort();*/
		
		/* } */

		/* }; */

		// shortThread.start();

		return objectDef.getSortingActionLst();

	}

	public List<String> doBubbleSort(List<Integer> nums) {
	
		
		objectDef.setObjectLst(nums);
		for( int i=0; i<nums.size() - 1; i++ ) {
			for( int j=1; j<nums.size() - i; j++ ) {
				if( objectDef.Compare(j-1, j) == -1 ) {
					objectDef.Swap(j-1, j);
				}
			}
		}
		return objectDef.getSortingActionLst();
	}

	private static void programFailure(String failureMsg) {
		System.out.println(">> Program failed because of following reason >>");
		System.out.println(failureMsg);
		System.exit(1);
	}

	

	public Integer getFreeRunInterval() {
		return freeRunInterval;
	}

	public void setFreeRunInterval(Integer freeRunInterval) {
		this.freeRunInterval = freeRunInterval;
	}

	public ObjectDef<Integer> getObjectDef() {
		return objectDef;
	}

	public void setObjectDef(ObjectDef<Integer> objectDef) {
		this.objectDef = objectDef;
	}



}

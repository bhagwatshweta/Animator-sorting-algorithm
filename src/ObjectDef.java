import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectDef<T extends Comparable<T>> {

	private List<T> objectLst = new ArrayList<T>();
	private List<String> sortingActionLst = new ArrayList<String>();
	int compareCount = 0;
	int swapCount = 0;
	static boolean showStatisics = false;

	Integer freeRunInterval;

	ObjectDef() {

	}

	public List<T> getObjectLst() {
		return objectLst;
	}

	public void setObjectLst(List<T> objectLst) {
		this.objectLst = objectLst;
	}

	public List<String> getSortingActionLst() {
		return sortingActionLst;
	}

	public void setSortingActionLst(List<String> sortingActionLst) {
		this.sortingActionLst = sortingActionLst;
	}

	public int Append(T object) {
		objectLst.add(object);
		int pos = objectLst.size() - 1;
		return pos;
	}

	public T Remove(int pos) {
		T object = objectLst.get(pos);
		objectLst.remove(pos);
		return object;
	}

	public int Size() {
		int size = objectLst.size();
		return size;
	}

	public T At(int pos) {
		T object = objectLst.get(pos);
		return object;
	}

	public int Compare(int firstPos, int secondPos) {

		if (showStatisics == true) {
			System.out.println("Comparing " + firstPos + " to " + secondPos);
		}
		sortingActionLst.add("cm," + firstPos + "," + secondPos);
		compareCount++;
		T firstEle = objectLst.get(firstPos);
		T secondEle = objectLst.get(secondPos);

		int compareToInt = firstEle.compareTo(secondEle);

		if (compareToInt < 0)
			return 1;
		else if (compareToInt > 0)
			return -1;
		return 0;
		
	}

	public void Swap(int firstPos, int secondPos) {

		if (showStatisics == true) {
			System.out.println("Swapping " + firstPos + " and " + secondPos);
		}

		sortingActionLst.add("sw," + firstPos + "," + secondPos);
		swapCount++;

		Collections.swap(objectLst, firstPos, secondPos);

	}

	public Integer getFreeRunInterval() {
		return freeRunInterval;
	}

	public void setFreeRunInterval(Integer freeRunInterval) {
		this.freeRunInterval = freeRunInterval;
	}

	public int getCompareCount() {
		return compareCount;
	}

	public void setCompareCount(int compareCount) {
		this.compareCount = compareCount;
	}

	public int getSwapCount() {
		return swapCount;
	}

	public void setSwapCount(int swapCount) {
		this.swapCount = swapCount;
	}	

}

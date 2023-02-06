package Homework;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// this program is to show how many comparisons and exchanges
// there are for multiple popular sorting algorithms to show their
// efficiencies and how they stack up against each other

public class HWSorts {

  public static void main(String[] args) throws IOException {

    System.out.println("The results are in the form of\n" + "1-16 normal, backwards and random &\n"
        + "1-2000 normal, backwards, and random\n");

    // comment out the other files to change which file you want to focus on.

    ArrayList<File> TextToFile = new ArrayList<File>();// add all files to one method for reading
    // File file1 = new File("TextFiles\\16normal.txt");
    // TextToFile.add(file1);
    // File file2 = new File("TextFiles\\16backwards.txt");
    // TextToFile.add(file2);
    // File file3 = new File("TextFiles\\16random.txt");
    // TextToFile.add(file3);
    // File file4 = new File("TextFiles\\2knormal.txt");
    // TextToFile.add(file4);
    File file5 = new File("TextFiles\\2kbackwards.txt");
    TextToFile.add(file5);
    // File file6 = new File("TextFiles\\2krandom.txt");
    // TextToFile.add(file6);
    // getting the info...
    for (File file : TextToFile) {// for each file...
      Scanner sc = new Scanner(file);// scan
      ArrayList<Integer> intAL = new ArrayList<>();// initialize the array list
      while (sc.hasNextLine()) {// scan each line until no more lines
        intAL.add(sc.nextInt());// add the integers from the scan into the array list

      }
      int[] arr = new int[intAL.size()];// create an integer array
      for (int i = 0; i < arr.length; i++) {// add the contents of the array list to the integer
                                            // array
        arr[i] = intAL.get(i);

      }
      sc.close();// close scan

      // Uncomment which sort you want to look at.

      // selectionSort(arr);
      // System.out.println("-----------------------------");
      // bubbleSort(arr);
      // System.out.println("-----------------------------");
      // insertionSort(arr);
      // System.out.println("-----------------------------");
      // shellSort(arr, arr.length);
      // System.out.println("-----------------------------");
      downheap(arr);
      // lines 57 to 63 are for quick sort
      // Sort(arr, arr.length); //lines 57 to 63 are for quick sort
      System.out.println("exchanges: " + exchanges);
      System.out.println("comparisons: " + comparisons);
      //
      // for (int i = 0; i < arr.length; i++)
      // System.out.print(arr[i] + " ");
      // System.out.println();
    }

  }

  public static void selectionSort(int[] array) {

    int temp, min, exchanges = 0, comparisons = 0;
    int numberOfItems = array.length;

    for (int pass = 0; pass != numberOfItems - 1; pass++) {
      min = pass;
      for (int index = pass + 1; index != numberOfItems; index++) {
        comparisons++;
        if (array[index] < array[min])
          min = index;
      } // end inner loop
      temp = array[min];
      array[min] = array[pass];
      array[pass] = temp;
      exchanges++;


    } // end outer loop
    System.out.println("Selection Sort: Number of Exchanges: " + exchanges);
    System.out.println("Selection Sort: Number of Comparisons: " + comparisons);
    System.out.println("Sorted file:");
    for (int i = 0; i < array.length; i++)
      System.out.print(array[i] + " ");
    System.out.println();
  } // end selection sort

  public static void bubbleSort(int[] array) {
    int temp, exchanges = 0, comparisons = 0;
    int numberOfItems = array.length;
    boolean swapped = true;

    for (int pass = 1; pass != numberOfItems; pass++) {
      if (swapped) {
        swapped = false;
        for (int index = 0; index != numberOfItems - pass; index++) {
          comparisons++;
          if (array[index] > array[index + 1]) {
            temp = array[index];
            array[index] = array[index + 1];
            array[index + 1] = temp;
            swapped = true;
            exchanges++;
          }
        }
      } else
        break;
    } // end sort
    System.out.println("Bubble Sort: Number of Exchanges: " + exchanges);
    System.out.println("Bubble Sort: Number of Comparisons: " + comparisons);
    System.out.println("Sorted file:");
    for (int i = 0; i < array.length; i++)
      System.out.print(array[i] + " ");
    System.out.println();
  }// end

  public static void insertionSort(int array[]) {
    int exchanges = 0, comparisons = 0;
    int numberOfItems = array.length;

    for (int pass = 1; pass < numberOfItems; pass++) {
      int key = array[pass];
      int i = pass - 1;
      while ((i > -1) && (array[i] > key)) {
        comparisons++;
        array[i + 1] = array[i];
        i--;
        exchanges++;
      } // end inner loop
      array[i + 1] = key;
    } // end outer loop
    System.out.println("Insertion Sort: Number of Exchanges: " + exchanges);
    System.out.println("Insertion Sort: Number of Comparisons: " + comparisons);
    System.out.println("Sorted file:");
    for (int i1 = 0; i1 < array.length; i1++)
      System.out.print(array[i1] + " ");
    System.out.println();
  }

  // shell sort

  public static int comparisons = 0;
  public static int exchanges = 0;

  public static void shellSort(int array[], int num) {
    int H = num / 2;
    H = 3 * H + 1;
    while (H > 0) {
      SegmentedInsertionSort(array, num, H);
      H = H / 2;
    } // end while
    System.out.println("Shell Sort: Number of Exchanges: " + exchanges);
    System.out.println("Shell Sort: Number of Comparisons: " + comparisons);
    System.out.println("Sorted file:");
    for (int i = 0; i < array.length; i++)
      System.out.print(array[i] + " ");
    System.out.println();
  }

  public static void SegmentedInsertionSort(int array[], int num, int H) {
    for (int i = H; i < num; i++) {
      int current = array[i];
      int j = i;
      while (j > H - 1 && (array[j - H] > current)) {
        comparisons++;
        array[j] = array[j - H];
        j = j - H;
        exchanges++;
      }
      array[j] = current;
    }
  }
  // quick sort

  public static int partition(int[] arr, int lo, int hi) {
    int pivot = arr[lo];
    while (lo < hi) {
      while (pivot < arr[hi] && lo < hi) {
        hi = hi - 1;
        comparisons++;
      }
      if (hi != lo) {
        arr[lo] = arr[hi];
        lo = lo + 1;
        exchanges++;
      }
      while (arr[lo] < pivot && lo < hi) {
        lo = lo + 1;
        comparisons++;
      }
      if (hi != lo) {
        arr[hi] = arr[lo];
        hi = hi - 1;
        exchanges++;
      }
    } // end while
    arr[hi] = pivot;
    int pivotPoint = hi;
    return pivotPoint;
  }

  public static void quickSort(int[] A, int lo, int hi) {
    int pivotPoint = partition(A, lo, hi);
    if (lo < pivotPoint) {
      quickSort(A, lo, pivotPoint - 1);
    }
    if (pivotPoint < hi) {
      quickSort(A, pivotPoint + 1, hi);
    }
  }

  public static void Sort(int[] A, int N) {
    quickSort(A, 0, N - 1);
  }

  public static void downheap(int A[]) {
    int n = A.length;

    // Build heap (reAange Aay)
    for (int i = n / 2 - 1; i >= 0; i--)
      heapify(A, n, i);

    // One by one extract an element from heap
    for (int i = n - 1; i >= 0; i--) {
      // Move current root to end
      int temp = A[0];
      A[0] = A[i];
      A[i] = temp;

      // call max heapify on the reduced heap
      heapify(A, i, 0);
    }
  }

  // To heapify a subtree rooted with node i which is
  // an index in A[]. n is size of heap
  static void heapify(int A[], int n, int i) {
    int largest = i; // Initialize largest as root
    int leftChild = 2 * i + 1; // left = 2*i + 1
    int rightChild = 2 * i + 2; // right = 2*i + 2
    comparisons++;
    // If left child is larger than root
    if (leftChild < n && A[leftChild] > A[largest])
      largest = leftChild;

    // If right child is larger than largest so far
    if (rightChild < n && A[rightChild] > A[largest])
      largest = rightChild;

    // If largest is not root
    if (largest != i) {
      int swap = A[i];
      A[i] = A[largest];
      A[largest] = swap;
      exchanges++;
      // Recursively heapify the affected sub-tree
      heapify(A, n, largest);
    }
  }

}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class HackerrankProblems {

	static int squares(int a, int b) {
		int res = 0;
		int from = (int) Math.ceil(Math.sqrt(a));

		int count = 0;
		count = from;
		count *= count;
		while (count <= b) {
			res++;
			from++;
			count = from;
			count *= count;
		}
		return res;
	}

	static int[] cutTheSticks(int[] arr) {
		int shortestStick = shortStick(arr);
		int cutSticks = 0;
		List<Integer> list = new ArrayList<Integer>();

		while (shortestStick != 0) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != 0 && arr[i] - shortestStick >= 0) {
					arr[i] = arr[i] - shortestStick;
					cutSticks++;
				}
			}
			list.add(cutSticks);
			cutSticks = 0;
			shortestStick = shortStick(arr);
		}
		int res[] = new int[list.size()];
		int index = 0;
		for (int el : list) {
			res[index] = el;
			index++;
		}
		return res;
	}

	static int shortStick(int arr[]) {
		int min = 0;
		Arrays.sort(arr);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != 0) {
				min = arr[i];
				break;
			}
		}
		return min;
	}

	public static int nonDivisibleSubset(int k, List<Integer> s) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> mapTwo = new HashMap<Integer, Integer>();
		int count = 0;
		if (k == 1 || s.size() == 1) {
			return 1;
		}
		for (int i = 0; i < s.size(); i++) {
			int temp = s.get(i) % k;
			s.remove(i);
			s.add(i, temp);
			int key = temp;
			if (map.containsKey(key)) {
				map.put(key, map.get(key) + 1);
			} else {
				map.put(key, 1);
			}
		}

		for (int i = 0; i < s.size(); i++) {
			int temp = s.get(i);
			if ((k % 2 == 0) && (temp == 0 || temp / 2 == (k - temp) / 2)) {
				map.put(temp, 1);
			} else if (map.containsKey(k - temp) && map.containsKey(temp) && map.get(temp) > map.get(k - temp)) {
				map.remove(k - temp);
			} else if (map.containsKey(k - temp) && map.containsKey(temp) && map.get(temp) < map.get(k - temp)) {
				map.remove(temp);
			}
		}
		for (int value : map.values()) {
			count += value;
		}
		return count;
	}

	static long repeatedString(String s, long n) {
		long lenS = s.length();
		long remainder = n % lenS;
		long pieces = n / lenS;
		long res = 0;

		for (int i = 0; i < lenS; i++) {
			if (s.charAt(i) == 'a') {
				res++;
			}
		}

		res = res * pieces;
		if (remainder != 0) {
			for (int i = 0; i < remainder; i++) {
				if (s.charAt(i) == 'a') {
					res++;
				}
			}
		}
		return res;
	}

	static int jumpingOnClouds(int[] c) {
		int res = 1;

		for (int i = 1; i < c.length; i++) {
			if (i + 1 < c.length && c[i] == 0 && c[i + 1] != 0) {
				res++;
			} else if (i + 2 < c.length && c[i + 2] == 0) {
				res++;
				i++;
			}
		}
		return res;
	}

	static int equalizeArray(int[] arr) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++) {
			if (map.containsKey(arr[i])) {
				int value = map.get(arr[i]) + 1;
				map.put(arr[i], value);
			} else {
				map.put(arr[i], 1);
			}
		}

		int max = 0;
		for (int num : map.values()) {
			if (num > max) {
				max = num;
			}
		}
		return arr.length - max;
	}

	public static void parseArr(String s) {
		String newString = "{";
		newString += s.replace(' ', ',');
		newString += "}";
		System.out.println(newString);
	}

	static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles) {
		int attackNum = (n - 1) * 2;

		if (n == 1) {
			return 0;
		}

		if (r_q == 1 || r_q == n || c_q == 1 || c_q == n) {
			attackNum += (n - 1);
		} else {
			attackNum += upperRight(n, r_q, c_q) + upperLeft(n, r_q, c_q)
					+ downRight(n, r_q, c_q) + downLeft(n, r_q, c_q);
		}

		int j = 0;
		int obsR = 0;
		int obsC = 0;
		if (k != 0) {
			obstacles = closerObstacles(n, k, r_q, c_q, obstacles);
			for (int i = 0; i < obstacles.length; i++) {
				while (j != 2) {
					if (j == 0)
						obsR = obstacles[i][j];
					else
						obsC = obstacles[i][j];
					j++;
				}
				j = 0;

				if (r_q == obsR) {
					if (obsC < c_q) {
						attackNum -= obsC;
					} else {
						attackNum -= ((n - obsC) + 1);
					}
				} else if (c_q == obsC) {
					if (obsR < r_q) {
						attackNum -= obsR;
					} else {
						attackNum -= ((n - obsR) + 1);
					}
				} else if (Math.abs(r_q - obsR) == Math.abs(c_q - obsC)) {
					if (r_q - obsR > 0 && c_q - obsC > 0) {
						attackNum -= downLeft(n, obsR, obsC) + 1;

					} else if (r_q - obsR > 0 && c_q - obsC < 0) {
						attackNum -= downRight(n, obsR, obsC) + 1;

					} else if (r_q - obsR < 0 && c_q - obsC > 0) {
						attackNum -= upperLeft(n, obsR, obsC) + 1;

					} else {
						attackNum -= upperRight(n, obsR, obsC) + 1;

					}
					System.out.println("diagonal" + "(" + obsR + "," + obsC + ")");
				}
			}
		}
		return attackNum;
	}

	public static int[][] closerObstacles(int n,int obsNum, int r_q, int c_q, int obstacles[][]) {
		List<Integer> obstacleList = new ArrayList<Integer>();
		int obsR;
		int obsC;
		int posObsUpperR = n+1;
		int posObsDownR = 0;
		int posObsRightC = n+1;
		int posObsLeftC = 0;
		int posUpperRightDiagR = n+1;
		int posUpperRightDiagC = n+1;
		int posUpperLeftDiagR = n+1;
		int posUpperLeftDiagC = 0;
		int posDownRightDiagR = 0;
		int posDownRightDiagC = n+1;
		int posDownLeftDiagR = 0;
		int posDownLeftDiagC = 0;
		
		
		for (int i = 0; i < obsNum; i++) {
			
			obsR = obstacles[i][0];
			obsC = obstacles[i][1];
			if(obsC == c_q ) { 
				if(obsR > r_q) { // upper on the same col
					if(posObsUpperR > (obsR - r_q)) {
						posObsUpperR = obsR;
					}
				}else if(obsR < r_q) { // down on the same col
					if(posObsDownR < obsR) {
						posObsDownR = obsR;
					}
				}
				
			}else if(obsR == r_q ) { 
				if(obsC > c_q) { //  left of queen on the same row
					if(posObsRightC > obsC) {
						posObsRightC = obsC;
					}
					
				}else if(obsC < c_q) { // right of queen on the same row
					if(posObsLeftC < obsC) {
						posObsLeftC = obsC;
					}
				}
			}else if(Math.abs(r_q - obsR) == Math.abs(c_q - obsC)) {
				if (r_q - obsR < 0 && c_q - obsC < 0) { //obs on the upper right
					if(posUpperRightDiagR > obsR) {
						posUpperRightDiagR = obsR;
						posUpperRightDiagC = obsC;
					}

				} else if (r_q - obsR > 0 && c_q - obsC < 0) { //obs on the down right
					if(posDownRightDiagC > obsC) {
						posDownRightDiagR = obsR;
						posDownRightDiagC = obsC;
					}

				} else if (r_q - obsR < 0 && c_q - obsC > 0) { // obs on the upper left
					if(posUpperLeftDiagR > obsR) {
						posUpperLeftDiagR = obsR;
						posUpperLeftDiagC = obsC;
					}

				} else { // obs on the down left
					if(posDownLeftDiagR < obsR) {
						posDownLeftDiagR = obsR;
						posDownLeftDiagC = obsC;
					}
				}
			}
		}
		if(posObsUpperR != n+1) {
			obstacleList.add(posObsUpperR);
			obstacleList.add(c_q);
		}
		if(posObsDownR != 0) {
			obstacleList.add(posObsDownR);
			obstacleList.add(c_q);
		}
		if(posObsRightC != n+1) {
			obstacleList.add(r_q);
			obstacleList.add(posObsRightC);
		}
		if(posObsLeftC != 0) {
			obstacleList.add(r_q);
			obstacleList.add(posObsLeftC);
		}
		if(posUpperRightDiagR != n+1 && posUpperRightDiagC != n+1) {
			obstacleList.add(posUpperRightDiagR);
			obstacleList.add(posUpperRightDiagC);
		}
		if(posUpperLeftDiagR != n+1 && posUpperLeftDiagC != 0) {
			obstacleList.add(posUpperLeftDiagR);
			obstacleList.add(posUpperLeftDiagC);
		}
		if(posDownRightDiagC != n+1 && posDownRightDiagR != 0) {
			obstacleList.add(posDownRightDiagR);
			obstacleList.add(posDownRightDiagC);
		}
		if(posDownLeftDiagR != 0 && posDownLeftDiagC != 0) {
			obstacleList.add(posDownLeftDiagR);
			obstacleList.add(posDownLeftDiagC);
		}
		int lenObsList = obstacleList.size();
		
		int newObsArr[][] = new int[lenObsList / 2][2];
		int j = 0;
		for(int i=0; i<lenObsList/2; i++) {
			newObsArr[i][0] = obstacleList.get(j++);
			newObsArr[i][1] = obstacleList.get(j++);
		}
	
		return newObsArr;

	}

	public static int upperRight(int n, int r, int c) {

		int tempR = r + 1;
		int tempC = c + 1;
		int countDiag = 0;

		while (true) {
			if (tempR <= n && tempC <= n) {
				countDiag++;

			} else {
				break;
			}
			tempR++;
			tempC++;
		}
		return countDiag;
	}

	public static int upperLeft(int n, int r, int c) {
		int countDiag = 0;
		int tempR = r + 1;
		int tempC = c - 1;

		while (true) {
			if (tempR <= n && tempC >= 1) {
				countDiag++;

			} else {
				break;
			}
			tempR++;
			tempC--;
		}
		return countDiag;
	}

	public static int downRight(int n, int r, int c) {
		int countDiag = 0;
		int tempR = r - 1;
		int tempC = c + 1;

		while (true) {
			if (tempR >= 1 && tempC <= n) {
				countDiag++;

			} else {
				break;
			}
			tempR--;
			tempC++;
		}
		return countDiag;
	}

	public static int downLeft(int n, int r, int c) {
		int countDiag = 0;
		int tempR = r - 1;
		int tempC = c - 1;

		while (true) {
			if (tempR >= 1 && tempC >= 1) {
				countDiag++;

			} else {
				break;
			}
			tempR--;
			tempC--;
		}

		return countDiag;
	}

	public static int[][] parseTextFile(String fileName, int n) {
		FileReader fileReader = null;
		int[][] arr = new int[n][n];

		try {
			fileReader = new FileReader(new File("C:\\Users\\Cagatay\\Desktop\\" + fileName + ".txt"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BufferedReader br = new BufferedReader(fileReader);

		String line;
		try {
			int i = 0;
			while ((line = br.readLine()) != null) {
				String arR[] = line.split(" ");
				arr[i][0] = Integer.parseInt(arR[0]);
				arr[i][1] = Integer.parseInt(arR[1]);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arr;
	}

	public static void printArray(int arr[][], int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	static int[] acmTeam(int n,int m,String[] topic) {
        int max = 0;
        int oldMax = 0;
        int teamNum = 0;
        int subjectNum = m;
        int studentNum = n;
        int arr[] = new int[2]; 

        for(int i=0; i<topic.length; i++){
            int index = 0;
            for(int j=i+1; j<topic.length; j++){
            	while(index != m) {
            		if(topic[i].charAt(index) == '1' || topic[j].charAt(index) == '1'){
            			oldMax++;
            		}
                index++;
            	}
            	if(oldMax > max) {
            		max = oldMax;
            		teamNum = 1;
            	}
            	else if(max == oldMax) {
                	teamNum++;
                }
            	oldMax = 0;
            	index = 0;
            }
        }
        arr[0] = max;
        arr[1] = teamNum;
        return arr;
    }
	
	static String organizingContainers(int[][] container) {
		
		int b[] = new int[container.length];
		int sum = 0;
		int contLen = container.length;
		int a[] = new int[contLen];
		
		for(int i=0; i<contLen; i++) {
			int x = container[i].length;
			for(int j=0; j<x; j++) {
				b[j] += container[i][j];
				a[i] += container[i][j];
			}
		}
		String res = "Possible";
		Arrays.sort(a);
		Arrays.sort(b);
		
		for(int i=0; i<a.length; i++) {
				if(a[i] != b[i]) {
					res = "Impossible";
					break;
				}
		}
		return res;
    }
	
	static String encryption(String s) {
        int lenS = s.length();
        String res = "";

        int row = (int)Math.sqrt(lenS);
        int col = row + 1;
        if(row * col < lenS) {
        	row = Math.max(row, col);
        	col = row;
        }else if(row * row == lenS) {
        	col = row;
        }
        String resArr[] = new String[row];
        int index = 0;
        int a = col;
        for(int i=0; i<lenS; i+=a) {
        	for(int j=i; j<i+a && j<lenS; j++) {
        		res += s.charAt(j);
        	}
        	if(i + a >= lenS)
        		a = 1;
        	if(index > row - 1)
        		break;
        	resArr[index++] = res;
        	res = "";
        }
        res = "";
        for(int i=0; i<col; i++) {
        	for(int j=0; j<row; j++) {
        		if(i < resArr[j].length()) {
        			res += resArr[j].charAt(i);
        		}
        	}
        	res += " ";
        }
        
        return res;
    }
	
	public static String biggerIsGreater(String w) {
		
		char[] charArray = w.toCharArray();
		int arrLen = charArray.length;
		String res = "";
		Queue<Integer> indexStack = new LinkedList<Integer>();
		int sortAfterIndex = 0;
		boolean checkReplaced = false;
		
		outer : for(int i=arrLen-1; i>=0; i--) {			
			for(int j=arrLen-1; j>i; j--) {
				indexStack.add(j);
			}
			
			while(!indexStack.isEmpty()) {
				int temp = indexStack.poll();
				if(charArray[temp] > charArray[i]) {	
					char oldValue = charArray[i];
					charArray[i] = charArray[temp];
					charArray[temp] = oldValue;
					sortAfterIndex = i;
					checkReplaced = true;
					break outer;
				}
			}	
		}
		
		for(int i=sortAfterIndex; i<arrLen; i++) {
			int j = i - 1;
			char cur = charArray[i];
			while(j > sortAfterIndex && charArray[j] > cur) {
				charArray[j+1] = charArray[j--];
			}
			charArray[j+1] = cur;	
		}
		
		for(int i=0; i<arrLen; i++) {
			res += charArray[i];
		}
		
		if(res.equals(w) || !checkReplaced) {
			res = "no answer";
		}
		
		return res;
    }
	
	static boolean checkKap(long num){
        int digit = 0;
        int digitTotal = 0;
        long tempNum = num;
        int numOne = 0;
        int numTwo = 0;
        int count = 1;
        int tempPart = 0;
        long temp = num;
        int digitCount = 1;
        if(num == 1) {
        	return true;
        }
        while(num != 0){
            num /= 10;
            digit++;
        }
        num = temp * temp;
        while(num != 0){
            num /= 10;
            digitTotal++;
        }
        num = temp;
        temp *= temp;
           	while(digitCount <= digit){
            	long remainder = temp % 10;
            	numTwo += remainder * count;
            	count *= 10;
            	temp /= 10;
            	digitCount++;
            }
            count = 1;
            digitCount = 1;
            while(digitCount <= digitTotal - digit ){
            	long remainder = temp % 10;
            	numOne += remainder * count;
            	count *= 10;
            	temp /= 10;
            	digitCount++;
            }  
        if((numOne + numTwo) == num){
            return true;
        }
        return false;
    }
	
	static int beautifulTriplets(int d, int[] arr) {
        int count = 0;
        int tripletNum = 0;
        int startIndex = 0;
        for(int i=0; i<arr.length; i++){
            int newTriplet[] = new int[3];
            newTriplet[count++] = arr[i];
            for(int j=i+1; j<arr.length; j++){
                if(arr[i] + d == arr[j] && count != 3){
                    newTriplet[count] = arr[j];
                    count++;
                    i = j;
                }
                if(count == 3) {
                    tripletNum++;
                    break;
                }
            }    
            count = 0;
            i = startIndex++;
        }
        return tripletNum;
    }
	
	static String timeInWords(int h, int m) {
        String arrayFirst[] = {"one","two","three","four","five","six","seven","eight",
        		"nine","ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"};            
        String arraySecond[] = {"twenty","thirty","fourty","fifty","sixty"};
        String hour = "";
        String minute = "";
        String res = "";
        hour = arrayFirst[h - 1];
        
        if(m == 0){
            res = hour + " o' clock";
        }else if(m == 15){
            res = "quarter past " + hour;
        }else if(m == 45){
            res = "quarter to " + arrayFirst[h];
        }else if(m <= 30 && m != 15){
            if(m == 30){
                res = "half past " + hour;
            }else if(m == 1){
                res = "one minute past " + hour;
            }else if(m < 20){
                res = arrayFirst[m-1] + " minutes past " + hour;             
            }
            else if(m >= 20){
                int remainder = m % 10;
                res = arraySecond[0] + " " + arrayFirst[remainder-1] + 
                " minutes" + " past " + hour;
            }
        }else if(m > 30 && m != 45){
            int remainder = (60 - m) % 10;
            int firstDigit = (60 - m) / 10;
            if(firstDigit == 1){
                res = arrayFirst[(60 - m) - 1] + " minutes to " + arrayFirst[h];
            }else if((60 - m) == 1){
                res = arrayFirst[(60 - m) - 1] + " minute to " + arrayFirst[h];
            }else if((60 - m) >= 20) {
            	res = arraySecond[0] + " " + arrayFirst[remainder - 1] + " minutes to " + arrayFirst[h];
            }
            else {
                res = arrayFirst[(60-m) - 1] + " minutes to " + arrayFirst[h];
            }
        }
        return res;
    }
	
	static int chocolateFeast(int n, int c, int m) {
        int totalChoc = 0;
        totalChoc = n / c;
        int remainder = totalChoc % m;
        int receiveByWrapper = totalChoc / m;
        totalChoc += receiveByWrapper;
        
        while(receiveByWrapper > 0) {
        	receiveByWrapper += remainder;
        	remainder = receiveByWrapper % m;
        	totalChoc += receiveByWrapper / m;	
        	receiveByWrapper /= m;
        }
        return totalChoc;
    }
	
	static int[] serviceLane(int n, int[] width, int[][] cases) {
        int startIndex = 0;
        int endIndex = 0;
        int minWidth;
        int[] minWidthArray = new int[cases.length];
        for(int i=0; i<cases.length; i++){
            startIndex = cases[i][0];
            endIndex = cases[i][1];
            minWidth = width[startIndex];
            for(int j=startIndex; j<=endIndex; j++){
                if(width[j] < minWidth){
                    minWidth = width[j];
                }
            }
            System.out.println(minWidth);
            minWidthArray[i] = minWidth;
        }
        return minWidthArray;
    }
	
	static int workbook(int n, int k, int[] arr) {
        int pageNum  = 0;
        int specialProb = 0;
        if(n == 1 && k == 1) {
        	return arr[0];
        }
        for(int i=0; i<arr.length; i++) {
        	int problemNum = arr[i];
        	pageNum++;
        	for(int j=1; j<=problemNum; j++) {
        		if(pageNum == j) {
        			specialProb++;
        		}
        		if(j % k == 0) {
        			if(j == problemNum)
        				continue;
        			pageNum++;
        		}
        	}
        }
        return specialProb;
    }
	
	static int flatlandSpaceStations(int n, int[] c) {
        List<Integer> minDistances = new ArrayList<Integer>();
        
        for(int i=0; i<n; i++){
            int min = n;
            for(int j=0; j<c.length; j++){
                int distance = Math.abs(i - c[j]);
                if(distance < min){
                    min = distance;
                }
            }
            minDistances.add(min);
        }
        return Collections.max(minDistances);
    }
	
	static int fairRations(int[] B) {
        int res = 0;
        boolean pos = false;
        for(int i=0; i<B.length; i++){
            if(i == (B.length - 1)){
                if(B[i] % 2 != 0){
                    res = -1;
                }
                break;
            }
            if(B[i] % 2 != 0){
                res += 2;
                B[i] += 1;
                B[i+1] += 1;
            }
        }
        return res;
    }
	
	static String[] cavityMap(String[] arr){
		char newChar = 'X';

        for(int i=1; i<arr.length-1; i++){
            for(int j=1; j<arr.length-1; j++){
            	
                char topNum = arr[i-1].charAt(j);
                char leftNum = arr[i].charAt(j-1);
                char rightNum = arr[i].charAt(j+1);
                char downNum = arr[i+1].charAt(j);
                
                if(arr[i].charAt(j) > topNum && arr[i].charAt(j) > leftNum &&
                		arr[i].charAt(j) > rightNum && arr[i].charAt(j) > downNum){
                	
                    char[] charArr = arr[i].toCharArray();
                    charArr[j] = newChar;
                    arr[i] = String.valueOf(charArr);
                }
            }
        }

        return arr;
    }
	
//	static int[] stones(int n, int a, int b) {
//        ArrayList<int[]> arrayList = new ArrayList<int[]>();
//        int value = a;
//        int arrayNum = (int) Math.pow(2, n-1);
//        List<Integer> resultList = new ArrayList<Integer>();
//        int[] result = new int[n];
//        
//        for(int i=0; i<arrayNum; i++) {
//        	int[] arr = new int[n];
//        	arr[0] = 0;
//        	arrayList.add(new int[n]);
//        }
//        
//        int count = arrayNum / 2;
//        int oldCount = count;
//        int resultIndex = 0;
//        
//        for(int i=1; i<n; i++) {
//        	for(int j=0; j<arrayNum; j++) {
//        		arrayList.get(j)[i] = arrayList.get(j)[i-1] + value;
//        		count--;
//        		int val = arrayList.get(j)[i];
//        		if(i == (n-1) && !resultList.contains(val)) {
//        			result[resultIndex++] =  val;
//        			resultList.add(val);
//        		}
//        		if(count == 0) {
//        			if(value == a) {
//        				value = b;
//        			}else {
//        				value = a;
//        			}
//        			count = oldCount;
//        		}
//        	}
//        	oldCount /= 2;
//        	count = oldCount;
//        }      
//        return result;
//    }
	
//	static int[] stones(int n, int a, int b) {
//		
//		int value = a;
//		int resultArraySize = n;
//		int[] resultArray = new int[resultArraySize];
//		int count = resultArraySize / 2;
//		int oldCount = count;
//		int step = 0;
//		List<Integer> resultList = new ArrayList<Integer>();
//		
//		while(step != n-1) {
//			for(int i=0; i<resultArraySize; i++) {
//				resultArray[i] += value;
//				count--;
//				if(count == 0) {
//					if(value == a) {
//						value = b;
//					}else {
//						value = a;
//					}
//					count = oldCount;
//				}	
//			}
//			oldCount /= 2;
//			count = oldCount;
//			step++;
//		}
//		
//		for(int i=0; i<resultArray.length; i++) {
//			if(!resultList.contains(resultArray[i])) {
//				resultList.add(resultArray[i]);
//			}
//		}
//		int[] result = new int[resultList.size()];
//		
//		for(int i=0; i<resultList.size(); i++) {
//			result[i] = resultList.get(i);
//		}
//		
//		return result;
//	}
	
	static int[] stones(int n, int a, int b) {

	       	List<Integer> resultList = new ArrayList<Integer>();
	       	int stones = n - 1;
	        int minDiff = Math.min(a,b);
	        int maxDiff = Math.max(a,b);

	        int current = minDiff * stones;
	        int max = maxDiff * stones;
	        int difference = maxDiff - minDiff;

	        if(minDiff == maxDiff){
	            resultList.add(current);
	        }else{
	            while(current <= max){
	                resultList.add(current);
	                current += difference;
	            }
	        }

	        int[] resultArr = new int[resultList.size()];
	        for(int i=0; i<resultArr.length; i++){
	            resultArr[i] = resultList.get(i);
	        }
	        return resultArr;
	    }
	
	static String gridSearch(String[] G, String[] P) {
        int patternCol = P[0].length();
        int patternRow = P.length;
        int gridCol = G[0].length();
        int gridRow = G.length;
        int patternSize = patternCol * patternRow;
        int countMatches = 0;
        int countRow = 0;
        String matchedOnGrid = "";
        List<String> matchedList = new ArrayList<String>();

        outer:for(int i=0; i<gridRow; i++){
            for(int j=0; j<gridCol; j++){
                if(G[i].charAt(j) == P[0].charAt(0)){  
                    int index = i;
                    int startColIndex = j;
                    int startRowIndex = i;
                    if(startRowIndex + patternRow > gridRow || startColIndex + patternCol > gridCol) {
                    	continue;
                    }
                    while(countRow != patternRow) {
                    	matchedOnGrid = G[index].substring(startColIndex, startColIndex + patternCol);
                    	index++;
                    	if(matchedOnGrid.equals(P[countRow++])) {
                    		countMatches++;
                    		if(countMatches == patternRow) {
                    			break outer;
                    		}
                    	}else {
                    		countMatches = 0;
                    		break;
                    	}	
                    }
                    countRow = 0;
                }
            }
        }
        if(countMatches == patternRow){
            return "YES";
        }
        return "NO";
    }
	
	static String happyLadybugs(String b) {
        HashMap<Character,Integer> colorMap = new HashMap<Character,Integer>();
        char color;
        int underscore = 0;
        int bLen = b.length();
        boolean alreadyHappy = true;
        
        for(int i=0; i<bLen; i++){
        	color = b.charAt(i);
        	if(color == '_') {
        		underscore++;
        		continue;
        	}
            if(colorMap.containsKey(color)) {
            	int value = colorMap.get(color);
            	value++;
            	colorMap.replace(color, value);
            }else {
            	colorMap.put(color, 1);
            }
            
            if(i != 0 && i < bLen - 1) {
            	char prevColor = b.charAt(i-1);
            	char nextColor = b.charAt(i+1);
            	
            	if(prevColor != color && nextColor != color) {
            		alreadyHappy = false;
            	}
            }
        }
        
        if(colorMap.containsValue(1) || (underscore == 0 && !alreadyHappy)) {
        	return "NO";
        }return "YES";
    }
	
	static long strangeCounter(long t) {
		int rem = 3;
		
		while(t > rem) {
			t = t - rem;
			rem *= 2;
		}
		return rem-t+1;
    }
	
	static double log2(double x) {
		return Math.floor((Math.log(x) / Math.log(2)));
	}
	
	static int surfaceArea(int[][] A) {
        int totalSurfaces = 0;
        int row = A.length;
        int col = A[0].length;
        int differ = 0;
        
        if(row == 1 && col == 1) {
        	return (A[0][0] * 4) + 2;
        }else if(row == 1) {
        	for(int i=0; i<col-1; i++) {
        		for(int j=0; j<row; j++) {
        			totalSurfaces += A[j][i] * 2;
        			if(i == 0) {
        				totalSurfaces += A[j][i];
        			}
        			if(A[j][i+1] < A[j][i]) {
        				differ = A[j][i] - A[j][i+1];
        				totalSurfaces += differ;
        			}else {
        				differ = A[j][i+1] - A[j][i];
        				totalSurfaces += differ;
        			}
        		}
        	}
        	totalSurfaces += A[0][col-1] * 3;
        	totalSurfaces += col * 2;
        	return totalSurfaces;
        }else if(col == 1) {
        	for(int i=0; i<row-1; i++) {
        		for(int j=0; j<col; j++) {
        			totalSurfaces += A[i][j] * 2;
        			if(i == 0) {
        				totalSurfaces += A[i][j];
        			}
        			if(A[i+1][j] < A[i][j]) {
        				differ = A[i][j] - A[i+1][j];
        				totalSurfaces += differ;
        			}else {
        				differ = A[i+1][j] - A[i][j];
        				totalSurfaces += differ;
        			}
        		}
        	}
        	totalSurfaces += A[row-1][0] * 3;
        	totalSurfaces += row * 2;
        	return totalSurfaces;
        }    
        
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                if((i == 0 && j == 0) || (i == 0 && j == col-1) || 
                		(i == row-1 && j == 0) || (i == row-1 && j == col-1)){
                	totalSurfaces += A[i][j] * 2;
                	if(i == 0 && j == 0) { // top left corner 
                		if(A[i][j+1] < A[i][j]) {
                			differ = A[i][j] - A[i][j+1];
                			totalSurfaces += differ;
                		}
                		if(A[i+1][j] < A[i][j]) {
                			differ = A[i][j] - A[i+1][j];
                			totalSurfaces += differ;
                		}
                	}else if(i == 0 && j == col-1) { // top right corner 
                		if(A[i][j-1] < A[i][j]) {
                			differ = A[i][j] - A[i][j-1];
                			totalSurfaces += differ;
                		}
                		if(A[i+1][j] < A[i][j]) {
                			differ = A[i][j] - A[i+1][j];
                			totalSurfaces += differ;
                		}
                	}else if(i == row-1 && j == 0) { // down left corner
                		if(A[i][j+1] < A[i][j]) {
                			differ = A[i][j] - A[i][j+1];
                			totalSurfaces += differ;
                		}
                		if(A[i-1][j] < A[i][j]) {
                			differ = A[i][j] - A[i-1][j];
                			totalSurfaces += differ;
                		}
                	}else { // down right corner
                		if(A[i][j-1] < A[i][j]) {
                			differ = A[i][j] - A[i][j-1];
                			totalSurfaces += differ;
                		}
                		if(A[i-1][j] < A[i][j]) {
                			differ = A[i][j] - A[i-1][j];
                			totalSurfaces += differ;
                		}
                	}
                    
                }else if((i == 0 && (j != 0 && j != col-1)) || (j == 0 && (i != 0 && i != row-1)) || 
                		(i == row-1 && (j != 0 && j != col-1)) || (j == col-1 && (i != 0 && i != row-1))){
                	totalSurfaces += A[i][j];
                	if(i == 0 && (j != 0 && j != col-1)) { // top edge 
                		if(A[i][j-1] < A[i][j]) {
                			differ = A[i][j] - A[i][j-1];
                			totalSurfaces += differ;
                		}
                		if(A[i][j+1] < A[i][j]) {
                			differ = A[i][j] - A[i][j+1];
                			totalSurfaces += differ;
                		}
                		if(A[i+1][j] < A[i][j]) {
                			differ = A[i][j] - A[i+1][j];
                			totalSurfaces += differ;
                		}
                	}else if(j == 0 && (i != 0 && i != row-1)) { // left edge 
                		if(A[i+1][j] < A[i][j]) {
                			differ = A[i][j] - A[i+1][j];
                			totalSurfaces += differ;
                		}
                		if(A[i][j+1] < A[i][j]) {
                			differ = A[i][j] - A[i][j+1];
                			totalSurfaces += differ;
                		}
                		if(A[i-1][j] < A[i][j]) {
                			differ = A[i][j] - A[i-1][j];
                			totalSurfaces += differ;
                		}
                	}else if(i == row-1 && (j != 0 && j != col-1)) { // down edge
                		if(A[i-1][j] < A[i][j]) {
                			differ = A[i][j] - A[i-1][j];
                			totalSurfaces += differ;
                		}
                		if(A[i][j+1] < A[i][j]) {
                			differ = A[i][j] - A[i][j+1];
                			totalSurfaces += differ;
                		}
                		if(A[i][j-1] < A[i][j]) {
                			differ = A[i][j] - A[i][j-1];
                			totalSurfaces += differ;
                		}
                		
                	}else { // right edge
                		if(A[i-1][j] < A[i][j]) {
                			differ = A[i][j] - A[i-1][j];
                			totalSurfaces += differ;
                		}
                		if(A[i+1][j] < A[i][j]) {
                			differ = A[i][j] - A[i+1][j];
                			totalSurfaces += differ;
                		}
                		if(A[i][j-1] < A[i][j]) {
                			differ = A[i][j] - A[i][j-1];
                			totalSurfaces += differ;
                		}
                	}
                	
                }else {
                	if(A[i-1][j] < A[i][j]) {
                		differ = A[i][j] - A[i-1][j];
            			totalSurfaces += differ;
            		}
            		if(A[i+1][j] < A[i][j]) {
            			differ = A[i][j] - A[i+1][j];
            			totalSurfaces += differ;
            		}
            		if(A[i][j-1] < A[i][j]) {
            			differ = A[i][j] - A[i][j-1];
            			totalSurfaces += differ;
            		}
            		if(A[i][j+1] < A[i][j]) {
            			differ = A[i][j] - A[i][j+1];
            			totalSurfaces += differ;
            		}
                }
            }
        }
        totalSurfaces += row * col * 2;        
        
        return totalSurfaces;
    }
	
	static int[] absolutePermutation(int n, int k) {
        int[] res = new int[n];
        int[] neg = {-1};
        boolean add = false;	
        
	        if(k == 0) {
	        	for(int i=0; i<n; i++) {
	        		res[i] = i + 1;	
	        	}
	        }else if((n/k) % 2 != 0) {
	        	res = neg;
	        }else {
	        	add = true;
	        	for(int j=1; j<n+1; j++) {
		        	if(add) {
		        		res[j-1] = j + k;
		        	}else {
		        		res[j-1] = j - k;
		        	}
		        	if(j % k == 0) {
		        		if(add) {
		        			add = false;
		        		}else {
		        			add = true;
		        		}
		        	}
	        	}
	        }
      return res;
    }
	
//	static int[] absolutePermutation(int n, int k) {
//        int[] arr = new int[n];
//        int[] noPer = {-1};
//        int temp = n;
//        boolean hasZero = false;
//        int[] tempArr;
//
//        if(k > n || k < 0){
//            return noPer;
//        }else{
//            for(int i=0; i<n; i++){
//                arr[i] = Math.abs((i + 1) - k);
//                if(hasZero) {
//                    if(Math.abs((i+1) - arr[i]) != k) {
//                        return noPer;
//                    }
//                }
//                if(arr[i] == 0){
//                    int index = 0;
//                    while(index < n){    
//                        arr[index] = Math.abs((index + 1) + k);
//                        index++;
//                    }
//                    hasZero = true;
//                }
//            }
//            tempArr = Arrays.copyOf(arr, n);
//            Arrays.sort(tempArr);
//            
//            for(int i=0; i<n; i++) {
//                if(tempArr[i] != (i+1)) {
//                    return noPer;
//                }
//            }
//            return arr;
//        }
//	}
	
	static String superReducedString(String s) {
        String res = "";
        String empty = "Empty String";
        Stack<Character> stack = new Stack<Character>();
        List<Character> list = new ArrayList<Character>();
        
        Character ch = ' ';
        list.add(s.charAt(0));
        
        for(int i=1; i<s.length(); i++) {
        	ch = s.charAt(i);
        	if(!list.isEmpty() && list.get(list.size() - 1) == ch) {
        		list.remove(list.size() - 1);
        		continue;
        	}
        	list.add(ch);
        }
        
        for(int i=0; i<list.size(); i++) {
        	res += list.get(i);
        }
        if(list.isEmpty()) {
        	return empty;
        }
        
        return res;
    }
	
	static void insertionSort1(int n, int[] arr) {
        int insertedEl = arr[n - 1];
        boolean inserted = false;

        for(int i = n - 1; i > 0; i--){
            int oldValue = arr[i - 1];
            arr[i] = oldValue;
            if(insertedEl > oldValue){
                arr[i] = insertedEl;
                inserted = true;
            }

            for(int j=0; j<n; j++){
                System.out.print(arr[j] + " ");
            }
            System.out.println();
            if(inserted){
                break;
            }
        }
        if(!inserted){
        	arr[0] = insertedEl;
        	for(int j=0; j<n; j++){
                System.out.print(arr[j] + " ");
            }
        }
    }
	
	static String hackerrankInString(String s) {
        String hackRank = "hackerrank";
        int index = 0;
        char elToSearch = hackRank.charAt(index);
        boolean res = false;

        for(int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            if(ch == elToSearch && index < 9){
                index++;
                elToSearch = hackRank.charAt(index);
            }
            if(index == 9){
                res = true;
            }
        }
        if(res){
            return "YES";
        }return "NO";
    }
	
	static String funnyString(String s) {
        boolean funny = true;
        String reversed = "";
        for(int i = s.length() - 1; i>=0; i--) {
        	reversed += s.charAt(i);
        
        }
        for(int i=0; i<s.length() - 1; i++){
        	int firstDiffer = Math.abs(s.charAt(i) - s.charAt(i + 1));
        	int secondDiffer = Math.abs(reversed.charAt(i) - reversed.charAt(i+1));
        	System.out.println("first: " + firstDiffer + " second: " + secondDiffer);
            if(firstDiffer != secondDiffer){
                funny = false;
            }
        }
        if(funny){
            return "Funny";
        }return "Not Funny";
    }
	
	static int gemstones(String[] arr) {
        List<Character> gemStones = new ArrayList<Character>();
        List<Character> tempList = new ArrayList<Character>();
            for(int j=0; j<arr[0].length(); j++){
                if(!gemStones.contains(arr[0].charAt(j))){
                    gemStones.add(arr[0].charAt(j));
                }
            }
            
            for(int i=1; i<arr.length; i++) {
            	for(int j=0; j<arr[i].length(); j++) {
            		if(gemStones.contains(arr[i].charAt(j)) && !tempList.contains(arr[i].charAt(j))) {
            			tempList.add(arr[i].charAt(j));
            		}
            	}
            	gemStones.removeAll(gemStones);
            	
            	while(!tempList.isEmpty()) {
            		gemStones.add(tempList.get(tempList.size()-1));
            		tempList.remove(tempList.size()-1);
            	}
            }         
        return gemStones.size();
    }
	
	static int theLoveLetterMystery(String s) {
        int length = s.length();
        int res = 0;
        int ch = 0;
        char a = 'a';
        int pos = (int)a;

        for(int i=0; i<length; i++){
            ch = (int)s.charAt(i);
            if(ch - pos < 0){
                return 0;
            }else{
                
                if(i < (length / 2)){
                	res += ch - pos;
                    pos++;
                }else if(i >= length / 2){
                    pos--;
                    res += ch - pos;
                }
            }
        }
        return res;
    }
	
//	static int anagram(String s) {
//        HashMap<Character, Integer> firstChars = new HashMap<Character, Integer>();
//        HashMap<Character, Integer> secondChars = new HashMap<Character, Integer>();
//        int res = s.length() / 2;
//        
//        for(int i=0; i<s.length(); i++){
//        	char ch = s.charAt(i);
//            if(i < (s.length() / 2)){
//                if(firstChars.containsKey(ch)) {
//                	int oldValue = firstChars.get(ch);
//                	firstChars.replace(ch, oldValue, oldValue + 1);
//                }else {
//                	firstChars.put(ch, 1);
//                }
//            }else {
//            	if(secondChars.containsKey(ch)) {
//                	int oldValue = secondChars.get(ch);
//                	secondChars.replace(ch, oldValue, oldValue + 1);
//                }else {
//                	secondChars.put(ch, 1);
//                }
//            }
//        }
//        for(int key : firstChars.keySet()) {
//        	if(secondChars.containsKey(key)) {
//        		
//        	}
//        }
//        return res;
//    }
	
	static int anagramSecond(String s) {
        int lengthS = s.length();
        int res = lengthS / 2;
        char[] firstString = new char[res];
        char[] secondString = new char[res];
        char ch = ' ';
        

        if(lengthS % 2 == 1){
            return -1;
        }
        for(int i=0; i<lengthS; i++){
            ch = s.charAt(i);
            if(i < (lengthS / 2)){
                firstString[i] = ch;
            }else{
                secondString[i - res] = ch;
            }
        }

        for(int i=0; i<firstString.length; i++){
            for(int j=0; j<secondString.length; j++){
                if(firstString[i] == secondString[j]){
                    res--;
                    secondString[j] = ' ';
                    break;
                }
            }
        }
        return res;
    }
	
	static int beautifulBinaryString(String b) {
        int res = 0;
        String str = "010";
        Stack<Integer> startingIndexes = new Stack<Integer>();
        boolean checkSequence = false;
        int sequenceNum = 0;
        int value = 0;

        for(int i=0; i<b.length() - 2; i++){
            if(b.charAt(i) == str.charAt(0) && b.charAt(i+1) == str.charAt(1) && 
                b.charAt(i+2) == str.charAt(2)){
                    res++;
                    startingIndexes.push(i);
            }
        }
        if(!startingIndexes.isEmpty()) {
        	value = startingIndexes.pop();
        }
        while(!startingIndexes.isEmpty()){
        	int secondValue = startingIndexes.pop();
            if((value - secondValue) == 2) {
            	checkSequence = true;
            	continue;
            }
            if(checkSequence) {
            	sequenceNum++;
            	checkSequence = false;
            }
            value = secondValue;
        }
        res -= sequenceNum;
        if(checkSequence) {
        	res -= 1;
        }
        return res;
    }
	
	static int[] quickSort(int[] arr) {
        int[] smallers = new int[arr.length];
        int[] equals = new int[arr.length];
        int[] greaters = new int[arr.length];

        int pivot = arr[0];
        int indexSmallers = 0;
        int indexEquals = 0;
        int indexGreaters = 0;
        int numSmallers = 0;
        int numEquals = 0;
        int numGreaters = 0;

        for(int i=0; i<arr.length; i++){
            if(arr[i] < pivot){
                smallers[indexSmallers++] = arr[i];
                numSmallers++;
            }else if(arr[i] == pivot){
                equals[indexEquals++] = arr[i];
                numEquals++;
            }else{
                greaters[indexGreaters++] = arr[i];
                numGreaters++;
            }
        }
        indexSmallers = 0;
        indexEquals = 0;
        indexGreaters = 0;
        int index = 0;
        while((indexSmallers != numSmallers)  ||
        		(indexEquals != numEquals)  || (indexGreaters != numGreaters)){
            if(indexSmallers != numSmallers){
                arr[index] = smallers[indexSmallers++];
            }else if(indexEquals != numEquals){
                arr[index] = equals[indexEquals++];
            }else if(indexGreaters != numGreaters){
                arr[index] = greaters[indexGreaters++];
            }
            index++;
        }

        return arr;
    }
	
	static int[] closestNumbers(int[] arr) {
        List<Integer> smallestPairs = new ArrayList<Integer>();
		Arrays.sort(arr);
		int smallestDiffer = Math.abs(arr[0] - arr[1]);
		smallestPairs.add(arr[0]);
		smallestPairs.add(arr[1]);
		int differ = 0;
		for(int i=0; i<arr.length-1; i++) {
			differ = Math.abs(arr[i] - arr[i+1]);
			if(differ < smallestDiffer) {
				smallestDiffer = differ;
				if(!smallestPairs.isEmpty()) {
					smallestPairs.removeAll(smallestPairs);
				}
				smallestPairs.add(arr[i]);
				smallestPairs.add(arr[i+1]);
			}else if(differ == smallestDiffer) {
				smallestPairs.add(arr[i]);
				smallestPairs.add(arr[i+1]);
			}
		}
		int[] res = new int[smallestPairs.size()];
		for(int i=0; i<smallestPairs.size(); i++) {
			res[i] = smallestPairs.get(i);
		}
		
		return res;
    }
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		int[] arrExp = {-5,15,25,71,63};
		int[] arr = closestNumbers(arrExp);
		for(int i=0; i<arr.length; i++) {
			System.out.println(arr[i]);
		}	
	}
}


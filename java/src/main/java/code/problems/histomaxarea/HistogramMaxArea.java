package code.problems.histomaxarea;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * https://www.youtube.com/watch?v=J2X70jj_I1o&list=PL_z_8CaSLPWdeOezg68SKkeLN4-T_jNHd&index=7
 */
public class HistogramMaxArea {

    private Integer[] data;

    private Map<Integer, Integer> valueRightIndex = new HashMap<>();

    public HistogramMaxArea(Integer[] data) {
        this.data = data;
    }

    public Integer getMaxArea() {
        Integer max = -1;

        for (int i=0;i< data.length;i++) {
            Integer maxWidthUnits = findWidthUnitsForIndex(i);
            Integer area = maxWidthUnits * data[i];

            if (max < area) {
                max = area;
            }
        }

        return max;
    }

    public Integer getMaxAreaReuse() {

        Integer max = -1;

        for (int i=0;i< data.length;i++) {

            Integer previousRightIndex = valueRightIndex.getOrDefault(data[i], -1);
            if (previousRightIndex != -1 && previousRightIndex >= i) {
                System.out.println("Skipping processing for index " + i + " number: " + data[i]);
                continue;
            }

            Integer maxWidthUnits = findWidthUnitsForIndex(i);
            Integer area = maxWidthUnits * data[i];

            if (max < area) {
                max = area;
            }
        }

        return max;
    }

    public Integer getMaxAreaParallel() {
        return IntStream.range(0, data.length)
                .parallel()
                .unordered()
                .map(n -> findWidthUnitsForIndex(n) * data[n])
                .max()
                .getAsInt();
    }

    private Integer findWidthUnitsForIndex(Integer index) {
        Integer indexForSmallerLeft = findIndexForSmallerLeft(index);
        Integer indexForSmallerRight = findIndexForSmallerRight(index);

        valueRightIndex.put(data[index], indexForSmallerRight);

        return indexForSmallerRight - indexForSmallerLeft -1;
    }

    private Integer findIndexForSmallerLeft(Integer index) {

        if (index == 0)
            return -1;

        int i = index - 1;
        while(i>=0) {
            if (data[i] < data[index]){
                break;
            }

            i--;
        }

        return i;
    }

    private Integer findIndexForSmallerRight(Integer index) {

        if (index == data.length -1)
            return data.length;

        int i = index + 1;

        while (i < data.length) {
            if (data[i] < data[index]) {
                break;
            }

            i++;
        }

        return i;
    }

}

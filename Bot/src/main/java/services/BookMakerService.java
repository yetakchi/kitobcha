package services;


public class BookMakerService {

    public int[] booklet(String messageText) {
        int start = 1, end;
        String[] pages = messageText.split(",");

        // Check for number that entered on count of pages
        if (pages.length < 2) {
            end = Integer.parseInt(messageText.trim());
        } else {
            start = Integer.parseInt(pages[0].trim());
            end = Integer.parseInt(pages[1].trim());
        }

        return new int[]{start, end};
    }

    public String[] book(int start, int end) {
        // median
        int divergence = divergence(start, end);

        // Create array for saving real pages of book
        int[] a = new int[divergence + 1];
        for (int i = 1; i <= divergence; i++) {
            a[i] = (start + i) - 1;
        }

        String front = "";
        String back = "";

        int quarter = divergence / 4;
        for (int i = divergence; i >= 1; i -= 2) {
            if (quarter > 0) {
                front = front.concat(a[i] + "," + (a[divergence - i + 1]) + (divergence / 2 + 2 == i ? "" : ","));
            } else {
                back = back.concat(a[i] + "," + (a[divergence - i + 1]) + (i == 2 ? "" : ","));
            }

            quarter--;

            System.out.println(a[i] + "," + (a[divergence - i + 1]) + ", " + i + " ");
        }

        int size = divergence / 4;
        return new String[]{front, back, String.valueOf(size)};
    }

    /*
     * if (x % 4 != 0) ([int] x / 4 + 1) * 4        || 2n + 1
     * if (x % 4 == 0) ([int] x / 4) * 4            || 2n
     * ([int] x / 4 + ildiz(q)) * 4                 || q = x % 4
     * ([int] x / 4 + [int] (4 + q - 1) / 4) * 4    || q x % 4
     */
    private int divergence(int start, int end) {
        int median = (end - start) + 1;
        int quotient = median / 4;
        int remainder = median % 4;

        return (quotient + (remainder + 4 - 1) / 4) * 4;
    }
}

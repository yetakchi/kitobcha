package tests;

import org.junit.jupiter.api.Test;
import services.BookMakerService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class BookMakerServiceTest {

    BookMakerService service = new BookMakerService();

    @Test
    void lessThanFour() {
        String[] result = service.book(1, 3);
        System.out.println(Arrays.toString(result));
        assertNotNull(result);
    }

    @Test
    void equalToFour() {
        String[] result = service.book(1, 4);
        System.out.println(Arrays.toString(result));
        assertNotNull(result);
    }

    @Test
    void greaterThanFour() {
        String[] result = service.book(1, 7);
        System.out.println(Arrays.toString(result));
        assertNotNull(result);
    }
}
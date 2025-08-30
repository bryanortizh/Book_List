package fr.eilco.booksprojects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import fr.eilco.booksprojects.model.Book;
import fr.eilco.booksprojects.serverOperations.GetRequest;
import fr.eilco.booksprojects.serverOperations.callback.BookGetRequestCallback;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28) 
public class GetRequestTest {

    @Test
    public void testRetrBooks() {
        GetRequest getRequest = new GetRequest();
        String searchName = "velo";

        getRequest.retrBooks(searchName, new BookGetRequestCallback() {
            @Override
            public void onSuccess(List<Book> books) {
                assertNotNull(books);
                assertFalse(books.isEmpty());

                assertEquals(100, books.size());
            }

            @Override
            public void onFailure(Throwable t) {
                assertFalse("La requête a échoué: " + t.getMessage(), true);
            }
        });
    }
}

package fr.eilco.booksprojects.serverOperations.apiService;


import fr.eilco.booksprojects.serverOperations.valueApi.BooksApiResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookApiService {
    @GET("search.json")
    Call<BooksApiResponse> getBooks(
            @Query("q") String searchName,
            @Query("limit") int limit);



    @GET("b/isbn/{isbn}-M.jpg")
    Call<ResponseBody> getImageBook(
            @Path("isbn") String isbn);


}

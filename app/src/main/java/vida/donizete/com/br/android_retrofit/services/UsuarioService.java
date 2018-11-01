package vida.donizete.com.br.android_retrofit.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vida.donizete.com.br.android_retrofit.entities.Usuario;

public interface UsuarioService {

    @GET("usuario/busca/{nome}")
    Call<Usuario> getUsuario(@Path("nome") String nome);

    @POST("usuario/delete")
    Call<Boolean> deleteUsuario(@Body Usuario u);

    @POST("usuario/inserir")
    Call<Boolean> putUsuario(@Body Usuario u);
}

package vida.donizete.com.br.android_retrofit.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vida.donizete.com.br.android_retrofit.entities.Produto;

public interface ProdutoService {

    @GET("produto/busca")
    Call<List<Produto>> buscaProdutos();

    @GET("produto/busca/{id}")
    Call<List<Produto>> buscaProduto(@Path("id") Integer id_usuario);

    @GET("produto/inserir/{id_usuario}/{id_produto}")
    Call<Boolean> inserirProdutoUsuario(@Path("id_usuario") Integer id_usuario, @Path("id_produto") Integer id_produto);

    @GET("produto/delete/{id}")
    Call<Boolean> deleteProdutoUsuario(@Path("id") Integer id_produto);
}

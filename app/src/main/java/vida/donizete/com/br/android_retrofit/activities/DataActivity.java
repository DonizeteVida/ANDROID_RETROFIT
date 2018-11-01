package vida.donizete.com.br.android_retrofit.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vida.donizete.com.br.android_retrofit.R;
import vida.donizete.com.br.android_retrofit.adapter.ProdutoAdapter;
import vida.donizete.com.br.android_retrofit.callback.ProdutoCallback;
import vida.donizete.com.br.android_retrofit.entities.Produto;
import vida.donizete.com.br.android_retrofit.services.ProdutoService;
import vida.donizete.com.br.android_retrofit.utils.RetrofitInstance;

public class DataActivity extends AppCompatActivity implements ProdutoCallback {

    @BindView(R.id.recyclerViewData)
    RecyclerView recyclerViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);

        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));

        doRecycler();
    }

    @Override
    public void onItemViewClick(final Produto produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Sim, salvar !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent result = new Intent();
                result.putExtra("produto", produto.getId());
                setResult(RESULT_OK, result);
                finish();
            }
        }).setNegativeButton("Não, outro produto !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DataActivity.this, "Okay...", Toast.LENGTH_SHORT).show();
            }
        }).setTitle("Deseja salvar o produto " + produto.getNome().toLowerCase() + " ?").show();
    }

    private void doRecycler() {
        ProdutoService produtoService = RetrofitInstance.getInstance().create(ProdutoService.class);

        Call<List<Produto>> result = produtoService.buscaProdutos();

        result.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    List<Produto> lista = response.body();

                    recyclerViewData.setAdapter(new ProdutoAdapter(lista, DataActivity.this, DataActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {

            }
        });
    }
}

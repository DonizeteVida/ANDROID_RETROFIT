package vida.donizete.com.br.android_retrofit.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import vida.donizete.com.br.android_retrofit.utils.Perfil;
import vida.donizete.com.br.android_retrofit.utils.RetrofitInstance;

public class MainActivity extends AppCompatActivity implements ProdutoCallback {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.floatActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    ProdutoService produtoService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolbar();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        produtoService = RetrofitInstance.getInstance().create(ProdutoService.class);

        doRecycler();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRecycler();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, DataActivity.class);
                startActivityForResult(a, 1);
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.include_bar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar a = getSupportActionBar();
            a.setTitle("Simples consulta com retrofit");
        }

    }

    private void doRecycler() {
        Call<List<Produto>> getTeste = produtoService.buscaProduto(Perfil.getUsuario().getId());

        getTeste.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    List<Produto> lista = response.body();

                    recyclerView.setAdapter(new ProdutoAdapter(lista, MainActivity.this, MainActivity.this));
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Integer id = data.getIntExtra("produto", 0);

            Call<Boolean> result = produtoService.inserirProdutoUsuario(Perfil.getUsuario().getId(), id);

            result.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Boolean teste = response.body();

                        if (teste) {
                            doRecycler();
                            Toast.makeText(MainActivity.this, "Produto adicionado com sucesso !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onItemViewClick(Produto produto) {

    }
}

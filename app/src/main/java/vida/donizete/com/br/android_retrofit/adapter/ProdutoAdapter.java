package vida.donizete.com.br.android_retrofit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vida.donizete.com.br.android_retrofit.R;
import vida.donizete.com.br.android_retrofit.callback.ProdutoCallback;
import vida.donizete.com.br.android_retrofit.entities.Produto;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoHolder> {

    private List<Produto> produtos;
    private Context context;
    private ProdutoCallback produtoCallback;

    public ProdutoAdapter(List<Produto> produtos, Context context, ProdutoCallback produtoCallback) {
        this.produtos = produtos;
        this.context = context;
        this.produtoCallback = produtoCallback;
    }

    @NonNull
    @Override
    public ProdutoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.produto_adapter, parent, false);
        return new ProdutoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoHolder holder, int position) {
        final Produto p = produtos.get(position);

        holder.cod_produto.setText(p.getId() + "");
        holder.nome_produto.setText(p.getNome());
        holder.preco_produto.setText("R$ " + p.getPreco());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtoCallback.onItemViewClick(p);
            }
        });

        if (position % 2 == 0) {
            holder.constraintLayout.setBackgroundColor(holder.constraintLayout.getResources().getColor(R.color.colorCinza));
        }
    }

    @Override
    public int getItemCount() {
        return produtos != null ? produtos.size() : 0;
    }

    class ProdutoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nome_produto)
        TextView nome_produto;

        @BindView(R.id.cod_produto)
        TextView cod_produto;

        @BindView(R.id.preco_produto)
        TextView preco_produto;

        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;

        public ProdutoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

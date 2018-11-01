package vida.donizete.com.br.android_retrofit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vida.donizete.com.br.android_retrofit.R;
import vida.donizete.com.br.android_retrofit.entities.Usuario;
import vida.donizete.com.br.android_retrofit.services.UsuarioService;
import vida.donizete.com.br.android_retrofit.utils.Perfil;
import vida.donizete.com.br.android_retrofit.utils.RetrofitInstance;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_login)
    EditText edit_login;

    @BindView(R.id.edit_pass)
    EditText edit_pass;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    public void onClickButton() {
        if (doTeste()) {
            UsuarioService usuarioService = RetrofitInstance.getInstance().create(UsuarioService.class);

            Call<Usuario> getUsuario = usuarioService.getUsuario(edit_login.getText().toString().trim());

            progressBar.setVisibility(View.VISIBLE);

            getUsuario.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        Usuario u = response.body();

                        if (u != null && u.getSenha().equals(edit_pass.getText().toString().trim())) {
                            Perfil.setUsuario(u);
                            Intent a = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(LoginActivity.this, "Usuario e/ou senha incorretos ! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private List<EditText> getList() {
        List<EditText> a = new ArrayList<>();
        a.add(edit_login);
        a.add(edit_pass);
        return a;
    }

    private Boolean doTeste() {
        Boolean teste = true;

        List<EditText> a = getList();

        for (EditText b : a) {
            if (b.getText().toString().trim().equals("")) {
                b.setError("O campo é necessário !");
                teste = false;
            }
        }

        return teste;
    }


}

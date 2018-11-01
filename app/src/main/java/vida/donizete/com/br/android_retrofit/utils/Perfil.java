package vida.donizete.com.br.android_retrofit.utils;

import vida.donizete.com.br.android_retrofit.entities.Usuario;

public class Perfil {

    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Perfil.usuario = usuario;
    }
}

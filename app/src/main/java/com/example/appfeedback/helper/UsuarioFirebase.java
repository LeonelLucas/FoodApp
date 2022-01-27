package com.example.appfeedback.helper;

import com.example.appfeedback.configuration.ConffireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFirebase {

    public static String getidUsuario(){
        FirebaseAuth autentification = ConffireBase.Auth();
        return autentification.getCurrentUser().getUid();
    }

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConffireBase.Auth();
        return usuario.getCurrentUser();
    }

    public static boolean atualizarTipoUsuario(String tipo){
        try{
            FirebaseUser user  = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(tipo)
                    .build();
            user.updateProfile(profile);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

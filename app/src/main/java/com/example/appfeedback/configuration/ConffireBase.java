package com.example.appfeedback.configuration;

import android.os.storage.StorageManager;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ConffireBase {
    private static DatabaseReference database;
    private static FirebaseAuth auth;
    private static StorageReference referenciaStogare;

    public static String getidUsuario(){
        FirebaseAuth autentification = Auth();
        return autentification.getCurrentUser().getUid();
    }

    //Verifica se databaseReference está vazio
    public static DatabaseReference Database(){
        if (database == null){
            database= FirebaseDatabase.getInstance().getReference();
        }
        return database;
    }

    //Verifica se firebaseAuth está vazio
    public static FirebaseAuth Auth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static StorageReference getFirebaseStorage(){

        if(referenciaStogare == null){
            referenciaStogare = FirebaseStorage.getInstance().getReference();
        }

       return  referenciaStogare;

    }
}

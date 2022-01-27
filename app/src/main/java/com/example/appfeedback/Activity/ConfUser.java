package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appfeedback.R;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.model.Empresa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ConfUser extends AppCompatActivity {

    EditText nomeEmpres, seguimento, tempoEntrega, taxaEntrega;
    private String idUsuarioLogado;
    private DatabaseReference firebaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_user);

        inicializarComponentes();
        firebaseRef = ConffireBase.Database();
        idUsuarioLogado = UsuarioFirebase.getidUsuario();
        recuperarDadosEmpresa();
    }

    public void inicializarComponentes(){

        nomeEmpres = findViewById(R.id.editEmpresaNome);
        seguimento = findViewById(R.id.editEmpresaCategoria);
        tempoEntrega = findViewById(R.id.editTempoEntrega);
        taxaEntrega = findViewById(R.id.EditTaxaEntrega);

    }

    private void recuperarDadosEmpresa(){
        DatabaseReference empresaRef = firebaseRef.child("empresas").child(idUsuarioLogado);
        empresaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    Empresa empresa = snapshot.getValue(Empresa.class);
                    nomeEmpres.setText(empresa.getNome());
                    seguimento.setText(empresa.getCategoria());
                    tempoEntrega.setText(empresa.getTempo());
                    taxaEntrega.setText(empresa.getPrecoEntrega().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void validarDadosEmpresa(View view) {
        String nome = nomeEmpres.getText().toString();
        String segmento = seguimento.getText().toString();
        String entrega = tempoEntrega.getText().toString();
        String taxa = taxaEntrega.getText().toString();

        if (!nome.isEmpty()) {
            if (!segmento.isEmpty()) {
                if (!entrega.isEmpty()) {
                    if (!taxa.isEmpty()) {
                        Empresa empresa = new Empresa();
                        empresa.setIdUsuario(idUsuarioLogado);
                        empresa.setNome(nome);
                        empresa.setCategoria(segmento);
                        empresa.setTempo(entrega);
                        empresa.setPrecoEntrega(Double.parseDouble(taxa));
                        empresa.Salvar();
                        finish();
                    } else {
                        exibirMensagem("Digite o valor da taxa de entrega");
                    }
                } else {
                    exibirMensagem("Digite o tempo de entrega");
                }
            } else {
                exibirMensagem("Digite um seguimento para a empresa");
            }
        } else {
            exibirMensagem("Digite um nome para a empresa");
        }
    }

    private void exibirMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {

                switch (requestCode){
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        localImagem
                                );
                        break;
                }

                if(imagem != null){
                    imagePerfilEmpresa.setImageBitmap(imagem);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70,baos);
                    byte[] dadosImagem = baos.toByteArray();

                   final StorageReference imagemRef = storageReference
                    .child("imagens")
                            .child("Empresas")
                            .child(idUsuarioLogado + ".jpeg");
                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ConfUser.this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                   Uri url = task.getResult();
                                }
                            });
                            Toast.makeText(ConfUser.this, "Imagem salva", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }catch(Exception e ){

                e.printStackTrace();
            }
        }
    }
*/
}
package com.huso.yazilimyapimi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class paraekle extends AppCompatActivity {
    EditText paraekleedittext;
    Button paraeklebuton;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paraekle);

        paraekleedittext=findViewById(R.id.paraekle_edittext);
        paraeklebuton=findViewById(R.id.paraekle_buton);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

    }
    public void paraeklebutonu(View view){
        String paramiktari=paraekleedittext.getText().toString();
        String parayiekleyenkullanici=firebaseUser.getUid();

        firebaseFirestore.collection("Profiller").document(parayiekleyenkullanici).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                String ad=documentSnapshot.getString("ad");
                String soyad=documentSnapshot.getString("soyad");
                Double para=Double.parseDouble(paramiktari);
                if (ad!=null && soyad!=null){
                    HashMap<String,Object> paradata=new HashMap();
                    paradata.put("para",para);
                    paradata.put("paraekleyenkisi",parayiekleyenkullanici);
                    firebaseFirestore.collection("Para").add(paradata).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(paraekle.this,"Para Onaylanmak Icin Gonderilmistir.",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(paraekle.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });



                }
                else{
                    Toast.makeText(paraekle.this,"!!!!!Profil bilgileriniz bos lütfen duzenleyiniz!!!!!",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
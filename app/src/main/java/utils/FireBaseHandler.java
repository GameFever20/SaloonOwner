package utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by bunny on 12/06/17.
 */

public class FireBaseHandler {
    private FirebaseDatabase mDatabase;


    public FireBaseHandler() {
        //hello he
        mDatabase = FirebaseDatabase.getInstance();

    }

    public void uploadOrder() {


    }

    public void downloadOrder() {

    }

    public void uploadOrderList() {

    }

    public void downloadOrderList() {

    }


    public void isRegisteredSaloon(String saloonUID, final OnSaloonInfoCheckListner onSaloonInfoCheckListner) {
        DatabaseReference myRef = mDatabase.getReference().child("registeredsaloon");

        Query myref2 = myRef.orderByChild("saloonUID").equalTo(saloonUID);

        myref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String saloonName = dataSnapshot.child("saloonName").getValue(String.class);

                if (saloonName != null) {
                    if (!saloonName.isEmpty()) {


                        onSaloonInfoCheckListner.onIsRegistered(true, saloonName);

                    } else {

                        onSaloonInfoCheckListner.onIsRegistered(false, "");

                    }

                } else {
                    onSaloonInfoCheckListner.onIsRegistered(false, "");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                onSaloonInfoCheckListner.onIsRegistered(false, "");

            }
        });


    }

    public void downloadSaloon(String saloonUID, final OnSaloonDownload onSaloonDownload) {

        DatabaseReference myRef = mDatabase.getReference().child("saloon/" + saloonUID);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //String saloonName = dataSnapshot.child("saloonName").getValue(String.class);


                onSaloonDownload.onSaloon();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }

    public void uploadSaloonShwcaseImage(Uri imageUri, String saloonUID, String imageName , final OnSaloonImageListner onSaloonImageListner) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();


        StorageReference riversRef = storageRef.child("saloon_image/" + saloonUID + "/" + imageName);
        UploadTask uploadTask = riversRef.putFile(imageUri);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                onSaloonImageListner.onImageUploaded(false ,null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                onSaloonImageListner.onImageUploaded(true , downloadUrl);
            }
        });

    }

    public void downloadSaloonShwcaseImage(String saloonUID, String imageName , final OnSaloonImageListner onSaloonImageListner) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference islandRef = storageRef.child("saloon_image/" + saloonUID + "/" + imageName);
         File localFile =null;
        try {
             localFile = File.createTempFile("images", "jpg");
        } catch (Exception exception) {

        }

        final File finalLocalFile = localFile;
        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                onSaloonImageListner.onImageDownLoaded(true , finalLocalFile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                onSaloonImageListner.onImageDownLoaded(false , null);

            }
        });

    }


    public interface OnSaloonInfoCheckListner {

        public void onIsRegistered(boolean isRegistered, String saloonName);

        public void onIsSaloonUpdated();

    }

    public interface OnSaloonDownload {
        public void onSaloon();

    }


    public interface OnSaloonImageListner{
        public void onImageDownLoaded(boolean isSucessful ,File imageFile);
        public void onImageUploaded(boolean isSucessful ,Uri downloadUri);

    }


}

